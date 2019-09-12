package com.evry.library.books.dao;

import com.evry.library.books.model.Book;
import com.evry.library.books.model.BookStatusEnum;
import com.evry.library.books.model.Customer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> searchBook(String title, String author, BookStatusEnum status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        if (title != "") {
            predicates.add(cb.like(root.get("title"), "%" + title + "%"));
        }
        if (author != "") {
            predicates.add(cb.like(root.get("author"), "%" + author + "%"));
        }
        if (status.name() != "ANY") {
            predicates.add(cb.equal(root.get("status"), status));
        }
        if (predicates.isEmpty()) {
            query.select(root);
        }
        else {
            query.select(root)
                    .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }
        return entityManager.createQuery(query)
                .getResultList();
    }

    @Override
    public List<Customer> searchCustomer(String name, String email, String address, String phoneNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);
        List<Predicate> predicates = new ArrayList<>();
        if (name != "") {
            predicates.add(cb.like(root.get("name"), "%" + name + "%"));
        }
        if (email != "") {
            predicates.add(cb.like(root.get("email"), "%" + email + "%"));
        }
        if (address != "") {
            predicates.add(cb.like(root.get("address"), "%" + address + "%"));
        }
        if (phoneNumber != "") {
            predicates.add(cb.like(root.get("phoneNumber"), "%" + phoneNumber + "%"));
        }
        if (predicates.isEmpty()) {
            query.select(root);
        }
        else {
            query.select(root)
                    .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }
        return entityManager.createQuery(query)
                .getResultList();
    }

    @Override
    public List<Book> findBooksByUserId(Integer user_id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root)
                .where(cb.equal(root.get("ownerId"), user_id));
        return entityManager.createQuery(query)
                .getResultList();
    }

    @Override
    public List<Book> searchBookForCustomer(Integer id, String title, String author) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("ownerId"), id));
        if (title != "") {
            predicates.add(cb.like(root.get("title"), "%" + title + "%"));
        }
        if (author != "") {
            predicates.add(cb.like(root.get("author"), "%" + author + "%"));
        }
        if (predicates.isEmpty()) {
            query.select(root);
        }
        else {
            query.select(root)
                    .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }
        return entityManager.createQuery(query)
                .getResultList();
    }
}
