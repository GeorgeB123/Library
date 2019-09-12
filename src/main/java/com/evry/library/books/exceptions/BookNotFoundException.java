package com.evry.library.books.exceptions;

public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(Integer id) {
    super("Could not find book with id " + id);
  }

}