package com.demo.service.entrypoint.books.repository.books.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BookResponseWrapper implements Serializable {

  private Long id;
  private String title;
  private Integer pageCount;
  private String publishDate;
}
