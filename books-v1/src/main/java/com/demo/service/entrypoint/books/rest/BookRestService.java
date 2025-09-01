package com.demo.service.entrypoint.books.rest;

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

  @GetMapping(value = "/books", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Observable<BookResponseDto> findAll(HttpServletRequest servletRequest,
                                             HttpServletResponse servletResponse) {

    return paramValidator.validateHeadersAndGet(servletRequest, DefaultHeaders.class)
        .flatMapObservable(tupleHeaders -> bookService.findAll(tupleHeaders.getValue()))
        .doOnNext(response -> servletResponse.setStatus(200));
  }

  @GetMapping("/books/{id}")
  public Maybe<BookResponseDto> findById(HttpServletRequest servletRequest,
                                         HttpServletResponse servletResponse,
                                         @PathVariable(name = "id") Long id) {

    return paramValidator.validateHeadersAndGet(servletRequest, DefaultHeaders.class)
        .flatMapMaybe(tupleHeaders -> bookService.findById(tupleHeaders.getValue(), id))
        .doOnSuccess(response -> servletResponse.setStatus(200));
  }

  @PostMapping("/books")
  public Completable logout(HttpServletRequest servletRequest,
                            HttpServletResponse servletResponse,
                            @Valid @RequestBody BookInsertRequestDto book) {

    return paramValidator.validateHeadersAndGet(servletRequest, DefaultHeaders.class)
        .flatMapCompletable(tupleHeaders -> bookService.save(tupleHeaders.getValue(), book))
        .doOnComplete(() -> servletResponse.setStatus(201));
  }
}
