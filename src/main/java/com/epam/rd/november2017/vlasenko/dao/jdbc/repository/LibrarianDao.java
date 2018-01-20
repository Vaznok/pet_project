package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;

import java.sql.SQLException;

public interface LibrarianDao<T, ID> {
    Iterable<T> findBorrowedBooks() throws SQLException, NoSuchEntityException;
}
