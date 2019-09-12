package com.evry.library.books.exceptions;

public class CustomerNotFoundException extends RuntimeException {

  public CustomerNotFoundException(Integer id) {
    super("Could not find customer with id " + id);
  }
}