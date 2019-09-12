package com.evry.library.books.exceptions;

public class CustomerNoBooksException extends RuntimeException {

  public CustomerNoBooksException(Integer id) {
    super("Customer " + id + " has no books");
  }
}