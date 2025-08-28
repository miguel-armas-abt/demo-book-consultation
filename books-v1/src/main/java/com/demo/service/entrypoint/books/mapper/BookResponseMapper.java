package com.demo.service.entrypoint.books.mapper;

import com.demo.service.entrypoint.books.dto.response.BookResponseDto;
import com.demo.service.entrypoint.books.repository.books.wrapper.BookResponseWrapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookResponseMapper {

  BookResponseDto toDto(BookResponseWrapper book);
}
