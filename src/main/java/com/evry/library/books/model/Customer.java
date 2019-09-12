package com.evry.library.books.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Customer {

    @Column(name = "customer_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name="customer_seq",  sequenceName = "customer_seq", allocationSize=1000)
    private Integer id;

    @Column(name = "customer_name")
    @NotNull
    private String name;

    @Column(name = "customer_phoneNumber")
    @NotNull
    private String phoneNumber;

    @Column(name = "customer_email")
    @NotNull
    private String email;

    @Column(name = "customer_address")
    @NotNull
    private String address;

    @Column(name = "customer_has_book")
    @NotNull
    private Boolean hasBook;
}
