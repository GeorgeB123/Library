package com.evry.library.books.dao;

import com.evry.library.books.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {

    @Query(value = "SELECT * FROM book b WHERE b.book_owner_id = :user_id", nativeQuery = true)
    List<Book> findBooksByUserId(@Param("user_id") Integer user_id);

}
