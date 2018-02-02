package com.epam.rd.november2017.vlasenko.service.book;

import java.sql.SQLException;

public interface BookService<T, ID> {
    Iterable<T> findAllExisted() throws SQLException;

    T find(ID id) throws SQLException;

    boolean deleteBook(ID bookId) throws SQLException;

    Iterable<T> findAll() throws SQLException;

    void createBook(T book) throws SQLException;

    String validate(T book) throws SQLException;
}
