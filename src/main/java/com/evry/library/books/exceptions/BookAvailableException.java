package com.evry.library.books.exceptions;

public class BookAvailableException extends RuntimeException {

  public BookAvailableException(Integer id) {
    super("Book is available with id " + id);
  }

}