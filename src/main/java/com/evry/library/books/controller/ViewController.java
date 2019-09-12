package com.evry.library.books.controller;

import com.evry.library.books.dao.BookRepositoryCustom;
import com.evry.library.books.model.Book;
import com.evry.library.books.model.BookStatusEnum;
import com.evry.library.books.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private BookController bookController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private BookRepositoryCustom bookRepositoryCustom;

    private List<String> statusList = Arrays.asList(new String[] {"ANY", "AVAILABLE", "BORROWED"});

    @RequestMapping("/")
    public RedirectView booksRedirect() {
        return new RedirectView("/books");
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookController.getAllBooks());
        model.addAttribute("statusList", statusList);
        return "books";
    }

    @GetMapping(value = "/books", params = {"title", "author", "status"})
    public String books(Model model, @RequestParam("title") String title, @RequestParam("author") String author, @RequestParam("status") BookStatusEnum status) {
        model.addAttribute("books", bookRepositoryCustom.searchBook(title, author, status));
        model.addAttribute("searchStatus", status.name());
        model.addAttribute("searchTitle", title);
        model.addAttribute("searchAuthor", author);
        model.addAttribute("statusList", statusList);
        return "books";
    }

    @RequestMapping("/book/{id}")
    public String bookDetails(@PathVariable Integer id, Model model) {
        Book existingBook = bookController.findBookById(id);
        model.addAttribute("book", existingBook);
        if (model.containsAttribute("message")) {
            model.addAttribute("hasMesssage", true);
        }
        return "book";
    }

    @RequestMapping("/book/create")
    public String bookAdd(Model model) {
        return "book-create";
    }

    @RequestMapping("/book/borrow/{id}")
    public String bookBorrow(Model model, @PathVariable Integer id) {
        Book existingBook = bookController.findBookById(id);
        List<Customer> customerList = (List<Customer>) customerController.getAllCustomers();
        model.addAttribute("customerList", customerList);
        model.addAttribute("book", existingBook);
        return "book-borrow";
    }

    @GetMapping(value = "/customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerController.getAllCustomers());
        return "customers";
    }

    @GetMapping(value = "/customers", params = {"name", "email", "address", "phoneNumber"})
    public String customers(Model model, @RequestParam("name") String name, @RequestParam("email") String email,
                            @RequestParam("address") String address, @RequestParam("phoneNumber") String phoneNumber) {
        model.addAttribute("searchName", name);
        model.addAttribute("searchEmail", email);
        model.addAttribute("searchAddress", address);
        model.addAttribute("searchNumber", phoneNumber);
        model.addAttribute("customers", bookRepositoryCustom.searchCustomer(name, email, address, phoneNumber));
        return "customers";
    }

    @RequestMapping("/customer/create")
    public String customerAdd(Model model) {
        return "customer-create";
    }

    @RequestMapping("/customer/{id}")
    public String customerDetails(@PathVariable Integer id, Model model) {
        Customer existingCustomer = customerController.findCustomerById(id);
        model.addAttribute("customer", existingCustomer);
        return "customer";
    }

    @GetMapping(value = "/customer/books/{userId}")
    public String customerBooksBorrowed(Model model, @PathVariable Integer userId) {
        Customer existingCustomer = customerController.findCustomerById(userId);
        model.addAttribute("books", bookRepositoryCustom.findBooksByUserId(userId));
        model.addAttribute("customer", existingCustomer);
        return "customer-books";
    }

    @GetMapping(value = "/customer/books/{userId}", params = {"title", "author"})
    public String customerBooksBorrowed(Model model, @PathVariable Integer userId, @RequestParam("title") String title, @RequestParam("author") String author) {
        Customer existingCustomer = customerController.findCustomerById(userId);
        model.addAttribute("searchTitle", title);
        model.addAttribute("searchAuthor", author);
        model.addAttribute("books", bookRepositoryCustom.searchBookForCustomer(userId, title, author));
        model.addAttribute("customer", existingCustomer);
        return "customer-books";
    }
}