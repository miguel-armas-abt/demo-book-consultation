package com.demo.service.entrypoint.books.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BookResponseDto implements Serializable {

  private Long id;
  private String title;
  private Integer pageCount;
  private String publishDate;
}
