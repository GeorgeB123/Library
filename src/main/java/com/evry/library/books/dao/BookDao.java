package com.evry.library.books.dao;

import com.evry.library.books.model.Book;
import com.evry.library.books.model.BookStatusEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

public interface BookDao {

    Iterable<Book> getAllBooks();

    Book addBook(String title, String author, BookStatusEnum status);

    RedirectView addBookRedirect(String title, String author, BookStatusEnum status);

    Book findBookById(Integer id);

    Book updateBook(Book book, Integer id);

    RedirectView updateBookRedirect(Book book, Integer id, RedirectAttributes attributes);

    void deleteBookById(Integer id);

    RedirectView deleteBookByIdRedirect(Integer id);

    Book borrowBook(Integer book_id, Book book);

    RedirectView borrowBookRedirect(Integer book_id, Book book);

    Book returnBook(Integer book_id);

    RedirectView returnBookRedirect(Integer book_id);

    List<Book> customerBookList(Integer user_id);

    public void deleteAll();

    RedirectView returnBookForUserRedirect(Integer bookId, Integer userId);
}
