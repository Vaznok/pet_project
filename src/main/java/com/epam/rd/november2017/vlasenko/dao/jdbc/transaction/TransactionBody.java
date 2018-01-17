package com.epam.rd.november2017.vlasenko.dao.jdbc.transaction;

import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;

import java.sql.SQLException;

public interface TransactionBody<T> {
    T doBody() throws SQLException, NoSuchEntityException;
}
