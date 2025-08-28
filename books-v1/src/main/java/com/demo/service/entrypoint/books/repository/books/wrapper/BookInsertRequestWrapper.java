package com.demo.service.entrypoint.books.repository.books.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookInsertRequestWrapper implements Serializable {

  private Long id;
  private String title;
  private Integer pageCount;
  private String publishDate;
}
