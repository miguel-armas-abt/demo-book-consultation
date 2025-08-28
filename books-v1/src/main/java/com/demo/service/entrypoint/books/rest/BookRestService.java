package com.demo.service.entrypoint.books.rest;

import java.util.Map;

import com.demo.commons.restserver.utils.RestServerUtils;
import com.demo.commons.validations.BodyValidator;
import com.demo.commons.validations.headers.DefaultHeaders;
import com.demo.commons.validations.ParamValidator;
import com.demo.service.entrypoint.books.dto.request.BookInsertRequestDto;
import com.demo.service.entrypoint.books.dto.response.BookResponseDto;
import com.demo.service.entrypoint.books.service.BookService;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/poc/books/v1")
public class BookRestService {

  private final BookService bookService;
  private final ParamValidator paramValidator;
  private final BodyValidator bodyValidator;

  @GetMapping(value = "/books", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Observable<BookResponseDto> findAll(HttpServletRequest servletRequest,
                                             HttpServletResponse servletResponse) {

    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(servletRequest);

    return paramValidator.validateAndGet(headers, DefaultHeaders.class)
        .flatMapObservable(defaultHeaders -> bookService.findAll(headers))
        .doOnNext(response -> servletResponse.setStatus(200));
  }

  @GetMapping("/books/{id}")
  public Maybe<BookResponseDto> findById(HttpServletRequest servletRequest,
                                         HttpServletResponse servletResponse,
                                         @PathVariable(name = "id") Long id) {

    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(servletRequest);

    return paramValidator.validateAndGet(headers, DefaultHeaders.class)
        .flatMapMaybe(defaultHeaders -> bookService.findById(headers, id))
        .doOnSuccess(response -> servletResponse.setStatus(200));
  }

  @PostMapping("/books")
  public Completable logout(HttpServletRequest servletRequest,
                            HttpServletResponse servletResponse,
                            @Valid @RequestBody BookInsertRequestDto book) {

    Map<String, String> headers = RestServerUtils.extractHeadersAsMap(servletRequest);

    return paramValidator.validateAndGet(headers, DefaultHeaders.class)
        .flatMapCompletable(defaultHeaders -> bookService.save(headers, book))
        .doOnComplete(() -> servletResponse.setStatus(201));
  }
}
