package com.epam.rd.november2017.vlasenko.service.book;

import com.epam.rd.november2017.vlasenko.entity.Book;

import java.sql.SQLException;

public interface BookService {
    Iterable<Book> findAll() throws SQLException;

    Object find(Integer id) throws SQLException;
}
