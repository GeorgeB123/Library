package com.evry.library.books.dao;

import com.evry.library.books.model.Book;
import com.evry.library.books.model.BookStatusEnum;
import com.evry.library.books.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepositoryCustom {

    List<Book> searchBook(String title, String author, BookStatusEnum status);

    List<Book> searchBookForCustomer(Integer id, String title, String author);

    List<Customer> searchCustomer(String name, String email, String address, String phoneNumber);

    List<Book> findBooksByUserId(Integer user_id);

}
