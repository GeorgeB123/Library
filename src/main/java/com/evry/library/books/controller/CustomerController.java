package com.evry.library.books.controller;

import com.evry.library.books.dao.CustomerDao;
import com.evry.library.books.exceptions.CustomerNotFoundException;
import com.evry.library.books.dao.CustomerRepository;
import com.evry.library.books.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController implements CustomerDao {

    @Autowired
    public CustomerRepository customerRepository;

    @Override
    @GetMapping("/all")
    public @ResponseBody
    Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer addCustomer(String name, String phoneNumber, String email, String address) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setHasBook(false);
        return customerRepository.save(customer);
    }

    @Override
    @PostMapping("/add")
    public @ResponseBody
    RedirectView addCustomerRedirect(@RequestParam String name, @RequestParam String phoneNumber, @RequestParam String email, @RequestParam String address) {
        addCustomer(name, phoneNumber, email, address);
        return new RedirectView("/customers");
    }

    @Override
    @GetMapping("/find/{id}")
    public @ResponseBody
    Customer findCustomerById(@PathVariable Integer id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);

        if (foundCustomer.isPresent()) {
            return foundCustomer.get();
        }
        return null;
    }

    @Override
    public Customer updateCustomer(Customer customer, Integer id) {
        Optional<Customer> customerFromDB = customerRepository.findById(id);
        if (customerFromDB.isPresent()) {
            Customer existingCustomer = customerFromDB.get();
            if (!customer.getName().isEmpty()) {
                existingCustomer.setName(customer.getName());
            }
            if (!customer.getEmail().isEmpty()) {
                existingCustomer.setEmail(customer.getEmail());
            }
            if (!customer.getPhoneNumber().isEmpty()) {
                existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            }
            if (!customer.getAddress().isEmpty()) {
                existingCustomer.setAddress(customer.getAddress());
            }
            return customerRepository.save(existingCustomer);
        }
        throw new CustomerNotFoundException(id);
    }

    @Override
    @PostMapping("/update/{id}")
    public @ResponseBody
    RedirectView updateCustomerRedirect(@Valid Customer customer, @PathVariable Integer id) {
        updateCustomer(customer, id);
        return new RedirectView("/customer/" + id);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        try {
            customerRepository.deleteById(id);
        } catch (RuntimeException runtimeException) {
            throw new CustomerNotFoundException(id);
        }
    }

    @Override
    @GetMapping("/delete/{id}")
    public RedirectView deleteCustomerByIdRedirect(@PathVariable Integer id) {
        deleteCustomerById(id);
        return new RedirectView("/customers");
    }

    public void deleteAll() { customerRepository.deleteAll();}
}
