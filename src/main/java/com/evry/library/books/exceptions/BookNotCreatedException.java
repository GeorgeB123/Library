package com.evry.library.books.exceptions;

public class BookNotCreatedException extends RuntimeException {

  public BookNotCreatedException() {
    super("Book could not be created");
  }

}