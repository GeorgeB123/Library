package com.evry.library.books.exceptions;

public class BookUnavailableException extends RuntimeException {

  public BookUnavailableException(Integer id) {
    super("Book not available with id " + id);
  }

}