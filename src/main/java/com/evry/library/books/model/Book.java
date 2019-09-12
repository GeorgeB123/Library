package com.evry.library.books.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Book {

    @Column(name = "book_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(name="book_seq", sequenceName="book_seq", allocationSize=1000)
    private Integer id;

    @Column(name = "book_title")
    @NotNull
    private String title;

    @Column(name = "book_author")
    @NotNull
    private String author;

    @Column(name = "book_status")
    @NotNull
    private BookStatusEnum status;

    @Column(name = "book_owner_id")
    private Integer ownerId;
}
