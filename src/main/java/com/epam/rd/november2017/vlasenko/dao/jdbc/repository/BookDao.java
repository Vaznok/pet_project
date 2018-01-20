package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.entity.Book;

import java.sql.SQLException;

public interface BookDao<T, ID> extends CrudDao<T, ID> {

    Iterable<Book> findByName (String name) throws SQLException, NoSuchEntityException;

    Iterable<Book> findByAuthor (String author) throws SQLException, NoSuchEntityException;

}

