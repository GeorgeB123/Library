package com.evry.library.books.dao;

import com.evry.library.books.model.Customer;
import org.springframework.web.servlet.view.RedirectView;

public interface CustomerDao {

    Iterable<Customer> getAllCustomers();

    Customer addCustomer(String name, String phoneNumber3, String email, String address);

    RedirectView addCustomerRedirect(String name, String phoneNumber3, String email, String address);

    Customer findCustomerById(Integer id);

    RedirectView updateCustomerRedirect(Customer customer, Integer id);

    Customer updateCustomer(Customer customer, Integer id);

    void deleteCustomerById(Integer id);

    RedirectView deleteCustomerByIdRedirect(Integer id);

    public void deleteAll();
}
