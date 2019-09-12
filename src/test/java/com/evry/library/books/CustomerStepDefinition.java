package com.evry.library.books;

import com.evry.library.books.dao.BookDao;
import com.evry.library.books.dao.BookRepositoryCustom;
import com.evry.library.books.dao.CustomerDao;
import com.evry.library.books.exceptions.ExceptionCollection;
import com.evry.library.books.model.BookStatusEnum;
import com.evry.library.books.model.Customer;
import cucumber.api.java8.En;
import io.cucumber.java.After;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CustomerStepDefinition implements En {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private ExceptionCollection exceptionCollection;

    @Autowired
    private BookRepositoryCustom bookRepositoryCustom;

    private Customer customer;

    private List<Customer> customerList;

    private String checkStringNotEmpty(String value) {
        if (value.isEmpty()) {
            return null;
        }
        return value;
    }

    public CustomerStepDefinition() {
        Given("I want to create a new customer", () -> {
            Assert.assertTrue(true);
        });
        When("I create a new customer with name {string}, number {string}, email {string}, address {string}", (String name, String number, String email, String address) -> {
            exceptionCollection.expectException();
            try {
                customer = customerDao.addCustomer(checkStringNotEmpty(name), checkStringNotEmpty(number), checkStringNotEmpty(email), checkStringNotEmpty(address));
            } catch (RuntimeException e) {
                exceptionCollection.add(e);
            }
        });
        Then("A new customer is created name {string}, number {string}, email {string}, address {string}", (String name, String number, String email, String address) -> {
            Assert.assertEquals(customer.getName(), name);
            Assert.assertEquals(customer.getPhoneNumber(), number);
            Assert.assertEquals(customer.getEmail(), email);
            Assert.assertEquals(customer.getAddress(), address);
        });
        Given("I have created a customer name {string}, number {string}, email {string}, address {string}", (String arg0, String arg1, String arg2, String arg3) -> {
            customer = customerDao.addCustomer(arg0, arg1, arg2, arg3);
        });
        When("I fetch that customer", () -> {
            customer = customerDao.findCustomerById(customer.getId());
        });
        Then("I can see that customer with name {string}, number {string}, email {string}, address {string}", (String arg0, String arg1, String arg2, String arg3) -> {
            Assert.assertEquals(customer.getName(), arg0);
            Assert.assertEquals(customer.getPhoneNumber(), arg1);
            Assert.assertEquals(customer.getEmail(), arg2);
            Assert.assertEquals(customer.getAddress(), arg3);
        });
        When("I delete that customer", () -> {
            customerDao.deleteCustomerById(customer.getId());
        });
        Then("That customer has been deleted", () -> {
            Assert.assertNull(customerDao.findCustomerById(customer.getId()));
        });
        When("I want to update the customer name to {string} and number to {string}", (String arg0, String arg1) -> {
            customer.setName(arg0);
            customer.setPhoneNumber(arg1);
            customer = customerDao.updateCustomer(customer, customer.getId());
        });
        Then("That customer will be updated with name {string}, number {string}, email {string}, address {string}", (String arg0, String arg1, String arg2, String arg3) -> {
            Assert.assertEquals(customer.getName(), arg0);
            Assert.assertEquals(customer.getPhoneNumber(), arg1);
            Assert.assertEquals(customer.getEmail(), arg2);
            Assert.assertEquals(customer.getAddress(), arg3);
        });
        Given("I want to create a new customer with description {string}", (String arg0) -> {
            Assert.assertTrue(true);
        });
        Then("No new customer is created", () -> {
            Assert.assertTrue(!exceptionCollection.getExceptions().isEmpty());
        });
        When("I fetch that customer with name {string}, number {string} and address {string}", (String arg0, String arg1, String arg2) -> {
            customerList = bookRepositoryCustom.searchCustomer(arg0, "", arg2, arg1);

        });
        Then("I receive a null result", () -> {
            Assert.assertTrue(customerList.isEmpty());
        });
        Given("I want to delete a customer with {string}", (String arg0) -> {
            Assert.assertTrue(true);
        });
        When("I try to delete a customer with name {string}, number {string}, email {string}, address {string}", (String arg0, String arg1, String arg2, String arg3) -> {
            customerList = bookRepositoryCustom.searchCustomer(arg0, arg2, arg3, arg1);
        });
        Then("That customer can not be deleted as does not exist", () -> {
            Assert.assertTrue(customerList.isEmpty());
        });
        When("I want to update the customer name to {string}, number to {string}, email to {string}, address to {string}", (String arg0, String arg1, String arg2, String arg3) -> {
            exceptionCollection.expectException();
            try {
                customer.setName(checkStringNotEmpty(arg0));
                customer.setAddress(checkStringNotEmpty(arg3));
                customer.setEmail(checkStringNotEmpty(arg2));
                customer.setPhoneNumber(checkStringNotEmpty(arg1));
                customer = customerDao.updateCustomer(customer, customer.getId());
            } catch (RuntimeException e) {
                customer = customerDao.findCustomerById(customer.getId());
                exceptionCollection.add(e);
            }
        });
        Then("That customer will be not be updated to name {string}, number {string}, email {string}, address {string}", (String arg0, String arg1, String arg2, String arg3) -> {
            if (arg0.isEmpty()) { Assert.assertNotEquals(customer.getName(), checkStringNotEmpty(arg0)); }
            if (arg1.isEmpty()) { Assert.assertNotEquals(customer.getPhoneNumber(), checkStringNotEmpty(arg1)); }
            if (arg2.isEmpty()) { Assert.assertNotEquals(customer.getEmail(), checkStringNotEmpty(arg2)); }
            if (arg3.isEmpty()) { Assert.assertNotEquals(customer.getAddress(), checkStringNotEmpty(arg3)); }
        });
        And("An error is thrown because {string}", (String arg0) -> {
            Assert.assertTrue(!exceptionCollection.getExceptions().isEmpty());
        });
    }

    @After
    public void cleanUp() {
        bookDao.deleteAll();
        customerDao.deleteAll();
        exceptionCollection.clearExceptions();
    }
}
