package com.demo.poc.entrypoint.books.repository.books;

import com.demo.poc.commons.core.properties.restclient.RestClient;
import com.demo.poc.commons.core.restclient.StreamingTransformer;
import com.demo.poc.commons.properties.ApplicationProperties;
import com.demo.poc.commons.core.restclient.RetrofitFactory;
import com.demo.poc.commons.core.restclient.utils.HeadersFiller;
import com.demo.poc.entrypoint.books.repository.books.wrapper.BookInsertRequestWrapper;
import com.demo.poc.entrypoint.books.repository.books.wrapper.BookResponseWrapper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface BookRepository {

  String SERVICE_NAME = "books";

  @GET("books")
  Single<List<BookResponseWrapper>> findAll(@HeaderMap Map<String, String> headers);

  default Observable<BookResponseWrapper> findAll(Map<String, String> headers,
                                                    ApplicationProperties properties) {
    RestClient restClient = properties.searchRestClient(SERVICE_NAME);
    Map<String, String> addedHeaders = HeadersFiller.fillHeaders(restClient.getRequest().getHeaders(), headers);
    return findAll(addedHeaders)
        .flatMapObservable(response -> Observable.fromStream(response.stream()));
  }

  @Streaming
  @GET("reactive/books")
  Observable<ResponseBody> findAllReactive(@HeaderMap Map<String, String> headers);

  default Observable<BookResponseWrapper> findAllReactive(Map<String, String> headers,
                                                          ApplicationProperties properties) {
    RestClient restClient = properties.searchRestClient(SERVICE_NAME);
    Map<String, String> addedHeaders = HeadersFiller.fillHeaders(restClient.getRequest().getHeaders(), headers);
    return findAllReactive(addedHeaders)
        .compose(StreamingTransformer.of(BookResponseWrapper.class));
  }

  @GET("books/{id}")
  Single<BookResponseWrapper> findById(@HeaderMap Map<String, String> headers,
                                       @Path(value = "id") Long id);

  default Single<BookResponseWrapper> findById(Map<String, String> headers,
                                               ApplicationProperties properties,
                                               Long id) {
    RestClient restClient = properties.searchRestClient(SERVICE_NAME);
    Map<String, String> addedHeaders = HeadersFiller.fillHeaders(restClient.getRequest().getHeaders(), headers);
    return findById(addedHeaders, id);
  }

  @POST("books")
  Single<ResponseBody> save(@HeaderMap Map<String, String> headers,
                            @Body BookInsertRequestWrapper book);

  default Single<ResponseBody> save(Map<String, String> headers,
                                    ApplicationProperties properties,
                                    BookInsertRequestWrapper book) {
    RestClient restClient = properties.searchRestClient(SERVICE_NAME);
    Map<String, String> addedHeaders = HeadersFiller.fillHeaders(restClient.getRequest().getHeaders(), headers);
    return save(addedHeaders, book);
  }

  @Configuration
  class BookRepositoryConfig {

    @Bean(SERVICE_NAME)
    BookRepository create(OkHttpClient.Builder builder, ApplicationProperties properties) {
      RestClient restClient = properties.searchRestClient(SERVICE_NAME);
      return RetrofitFactory.create(builder, restClient, BookRepository.class);
    }
  }
}
