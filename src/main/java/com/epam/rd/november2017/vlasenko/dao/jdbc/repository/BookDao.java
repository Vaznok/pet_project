package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.entity.Book;

import java.sql.SQLException;

public interface BookDao extends CrudDao<Book, Integer> {

    Iterable<Book> findByName (String name) throws SQLException;

    Iterable<Book> findByAuthor (String author) throws SQLException;

    Iterable<Book> findAllExisted () throws SQLException;

    boolean isBookExist(Book book) throws SQLException;
}

