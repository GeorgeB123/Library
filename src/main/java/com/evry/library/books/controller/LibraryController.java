package com.evry.library.books.controller;

import com.evry.library.books.dao.BookDao;
import com.evry.library.books.dao.BookRepository;
import com.evry.library.books.dao.CustomerRepository;
import com.evry.library.books.exceptions.*;
import com.evry.library.books.model.Book;
import com.evry.library.books.model.BookStatusEnum;
import com.evry.library.books.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class LibraryController {

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    public CustomerRepository customerRepository;

}
