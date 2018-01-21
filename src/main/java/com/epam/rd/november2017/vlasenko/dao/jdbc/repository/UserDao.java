package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;

import java.sql.SQLException;

public interface UserDao<T, ID> extends CrudDao<T, ID>{

    void orderBook(ID userId, ID bookId) throws SQLException;

    Iterable<T> findBorrowedBooks(ID userId) throws SQLException, NoSuchEntityException;

    T findAuthorizedUser(char[] email, char[] password) throws SQLException, NoSuchEntityException;
}
