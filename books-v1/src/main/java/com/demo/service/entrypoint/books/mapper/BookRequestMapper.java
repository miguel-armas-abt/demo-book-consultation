package com.demo.service.entrypoint.books.mapper;

import com.demo.service.entrypoint.books.dto.request.BookInsertRequestDto;
import com.demo.service.entrypoint.books.repository.books.wrapper.BookInsertRequestWrapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {

  BookInsertRequestWrapper toWrapper(BookInsertRequestDto book);
}
