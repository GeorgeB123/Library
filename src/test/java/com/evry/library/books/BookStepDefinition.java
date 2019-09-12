package com.evry.library.books;

import com.evry.library.books.dao.BookDao;
import com.evry.library.books.dao.BookRepository;
import com.evry.library.books.dao.BookRepositoryCustom;
import com.evry.library.books.dao.CustomerDao;
import com.evry.library.books.exceptions.BookNotCreatedException;
import com.evry.library.books.exceptions.ExceptionCollection;
import com.evry.library.books.model.Book;
import com.evry.library.books.model.BookStatusEnum;
import com.evry.library.books.model.Customer;
import io.cucumber.java8.En;
import io.cucumber.java.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class BookStepDefinition implements En {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ExceptionCollection exceptionCollection;

    private Book book;

    private Customer customer;

    public BookStepDefinition() {

        Given("I want to create a new book", () -> {
            Assert.assertTrue(true);
        });
        When("I create a new book with title {string}, author {string}, status {string}", (String arg0, String arg1, String arg2) -> {
            book = bookDao.addBook(arg0, arg1, BookStatusEnum.valueOf(arg2));
        });
        When("I create a new book with no title, author {string}, status {string}", (String arg0, String arg1) -> {
            exceptionCollection.expectException();
            try {
                bookDao.addBook(null, arg0, BookStatusEnum.valueOf(arg1));
            } catch (RuntimeException e) {
                exceptionCollection.add(e);
            }
        });

        When("I create a new book with title {string}, author {string}, and incorrect status {string}", (String arg0, String arg1, String arg2) -> {
            exceptionCollection.expectException();
            try {
                bookDao.addBook(arg0, arg1, BookStatusEnum.valueOf(arg2));
            } catch (RuntimeException e) {
                exceptionCollection.add(e);
            }
        });
        Then("A new book is created title {string}, author {string}, status {string}", (String arg0, String arg1, String arg2) -> {
            Assert.assertEquals(book.getTitle(), arg0);
            Assert.assertEquals(book.getAuthor(), arg1);
            Assert.assertEquals(book.getStatus(), BookStatusEnum.valueOf(arg2));
        });

        Then("No book is created and exception thrown", () -> {
            Assert.assertTrue(!exceptionCollection.getExceptions().isEmpty());
        });

        Given("I have created a book title {string}, author {string}, status {string}", (String arg0, String arg1, String arg2) -> {
            book = bookDao.addBook(arg0, arg1, BookStatusEnum.valueOf(arg2));
        });
        When("I delete that book", () -> {
            bookDao.deleteBookById(book.getId());
        });
        Then("That book has been deleted", () -> {
            Assert.assertNull(bookDao.findBookById(book.getId()));
        });

        When("I fetch that book", () -> {
            book = bookDao.findBookById(book.getId());
        });
        Then("I can see that book with title {string}, author {string}, status {string}", (String arg0, String arg1, String arg2) -> {
            Assert.assertEquals(book.getTitle(), arg0);
            Assert.assertEquals(book.getAuthor(), arg1);
            Assert.assertEquals(book.getStatus(), BookStatusEnum.valueOf(arg2));
        });

        When("I want to update the book title to {string}", (String arg0) -> {
            book.setTitle(arg0);
            book = bookDao.updateBook(book, book.getId());
        });
        Then("That book will be updated with title {string}, author {string}, status {string}", (String arg0, String arg1, String arg2) -> {
            Assert.assertEquals(book.getTitle(), arg0);
            Assert.assertEquals(book.getAuthor(), arg1);
            Assert.assertEquals(book.getStatus(), BookStatusEnum.valueOf(arg2));
        });

        And("A customer with name {string}, number {string}, email {string}, address {string} wants to borrow this book", (String arg0, String arg1, String arg2, String arg3) -> {
            customer = customerDao.addCustomer(arg0, arg1, arg2, arg3);
        });
        When("The customer borrows the book", () -> {
            book.setOwnerId(customer.getId());
            book = bookDao.borrowBook(book.getId(), book);
            customer = customerDao.findCustomerById(customer.getId());
        });
        And("The customer returns the book", () -> {
            book = bookDao.returnBook(book.getId());
            customer = customerDao.findCustomerById(customer.getId());
        });
        Then("That book will be updated with the customer's user id and have status {string}", (String arg0) -> {
            Assert.assertEquals(book.getOwnerId(), customer.getId());
            Assert.assertEquals(book.getStatus(), BookStatusEnum.valueOf(arg0));
        });
        And("The customer will have a book registered", () -> {
            Assert.assertTrue(customer.getHasBook());
        });

        Then("That book will have status {string}", (String arg0) -> {
            Assert.assertEquals(book.getStatus(), BookStatusEnum.valueOf(arg0));
        });
        And("The customer will not have a book registered", () -> {
            Assert.assertTrue(!customer.getHasBook());
        });
    }

    @After
    public void cleanUp() {
        bookDao.deleteAll();
        customerDao.deleteAll();
    }
}
