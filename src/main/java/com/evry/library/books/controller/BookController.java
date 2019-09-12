package com.evry.library.books.controller;

import com.evry.library.books.dao.BookDao;
import com.evry.library.books.dao.BookRepository;
import com.evry.library.books.dao.CustomerRepository;
import com.evry.library.books.exceptions.*;
import com.evry.library.books.model.Book;
import com.evry.library.books.model.Customer;
import com.evry.library.books.model.BookStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController implements BookDao {

    @Autowired
    public LibraryController libraryController;

    @Override
    @GetMapping("/all")
    public @ResponseBody
    Iterable<Book> getAllBooks() {
        return libraryController.bookRepository.findAll();
    }

    @Override
    public Book addBook(String title, String author, BookStatusEnum status) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setStatus(status);
        return libraryController.bookRepository.save(book);
    }

    @Override
    @PostMapping("/add")
    public @ResponseBody
    RedirectView addBookRedirect(@RequestParam String title, @RequestParam String author, @RequestParam BookStatusEnum status) {
        addBook(title, author, status);
        return new RedirectView("/");
    }

    @Override
    @GetMapping("/find/{id}")
    public @ResponseBody
    Book findBookById(@PathVariable Integer id) {
        Optional<Book> book = libraryController.bookRepository.findById(id);

        if (book.isPresent()) {
            return book.get();
        }
        return null;
    }

    @Override
    public Book updateBook(Book book, Integer id) {
        Optional<Book> existingBook = libraryController.bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book bookToUpdate = existingBook.get();
            if (book.getTitle() != null) {
                bookToUpdate.setTitle(book.getTitle());
            }
            if (book.getAuthor() != null) {
                bookToUpdate.setAuthor(book.getAuthor());
            }
            return libraryController.bookRepository.save(bookToUpdate);
        }
        throw new BookNotFoundException(id);
    }

    @Override
    @PostMapping("/update/{id}")
    public @ResponseBody
    RedirectView updateBookRedirect(@Valid Book book, @PathVariable Integer id, RedirectAttributes attributes) {
        updateBook(book, id);
        attributes.addFlashAttribute("message", "Success");
        return new RedirectView("/book/" + id);
    }

    @Override
    @PostMapping("/borrow/{bookId}/confirm")
    public @ResponseBody
    RedirectView borrowBookRedirect(@PathVariable Integer bookId, @Valid Book book) {
        borrowBook(bookId, book);
        return new RedirectView("/");
    }

    @Override
    public Book borrowBook(Integer bookId, Book book) {
        Customer customer = checkCustomerExists(book.getOwnerId());
        Book bookToBorrow = checkBookExists(bookId);
        if (bookToBorrow.getStatus() != BookStatusEnum.AVAILABLE) {
            throw new BookUnavailableException(bookId);
        }
        bookToBorrow.setOwnerId(customer.getId());
        bookToBorrow.setStatus(BookStatusEnum.BORROWED);
        customer.setHasBook(true);
        libraryController.customerRepository.save(customer);
        return libraryController.bookRepository.save(bookToBorrow);
    }

    /**
     * Helper function to see if book exists in system.
     *
     * @param bookId
     * @return
     */
    private Book checkBookExists(Integer bookId) {
        Optional<Book> existingBook = libraryController.bookRepository.findById(bookId);
        if (!existingBook.isPresent()) {
            throw new BookNotFoundException(bookId);
        }
        return existingBook.get();
    }

    /**
     * Helper function to see if customer exists in system.
     *
     * @param userId
     * @return
     */
    private Customer checkCustomerExists(Integer userId) {
        Optional<Customer> existingCustomer = libraryController.customerRepository.findById(userId);
        if (!existingCustomer.isPresent()) {
            throw new CustomerNotFoundException(userId);
        }
        return existingCustomer.get();
    }

    @Override
    public Book returnBook(Integer bookId) {
        Book bookToReturn = checkBookExists(bookId);
        Customer customer = checkCustomerExists(bookToReturn.getOwnerId());
        if (bookToReturn.getStatus() != BookStatusEnum.BORROWED) {
            throw new BookAvailableException(bookId);
        }
        bookToReturn.setOwnerId(null);
        bookToReturn.setStatus(BookStatusEnum.AVAILABLE);
        libraryController.bookRepository.save(bookToReturn);
        List<Book> customerBookList = libraryController.bookRepository.findBooksByUserId(customer.getId());
        if (customerBookList.isEmpty()) {
            customer.setHasBook(false);
        }
        libraryController.customerRepository.save(customer);
        return bookToReturn;
    }

    @Override
    @GetMapping("/return/{bookId}")
    public @ResponseBody
    RedirectView returnBookRedirect(@PathVariable Integer bookId) {
        returnBook(bookId);
        return new RedirectView("/");
    }

    @Override
    @GetMapping("/return/{bookId}/user/{userId}")
    public @ResponseBody
    RedirectView returnBookForUserRedirect(@PathVariable Integer bookId, @PathVariable Integer userId) {
        returnBook(bookId);
        return new RedirectView("/customer/books/" + userId);
    }

    @Override
    @GetMapping("/bookList/{userId}")
    public @ResponseBody
    List<Book> customerBookList(@PathVariable Integer userId) {
        List<Book> customerBookList = libraryController.bookRepository.findBooksByUserId(userId);
        if (customerBookList.isEmpty()) {
            throw new CustomerNoBooksException(userId);
        }
        return customerBookList;
    }

    @Override
    public void deleteBookById(Integer id) {
        try {
            Optional<Book> book = libraryController.bookRepository.findById(id);
            if (book.get().getOwnerId() != null) {
                throw new BookUnavailableException(id);
            }
            libraryController.bookRepository.deleteById(id);
        } catch (RuntimeException runtimeException) {
            throw new CustomerNotFoundException(id);
        }
    }

    @Override
    @GetMapping("/delete/{id}")
    public RedirectView deleteBookByIdRedirect(@PathVariable Integer id) {
        deleteBookById(id);
        return new RedirectView("/");
    }

    @Override
    public void deleteAll() { libraryController.bookRepository.deleteAll(); }

}
