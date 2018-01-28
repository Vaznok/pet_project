package com.epam.rd.november2017.vlasenko.dao.jdbc.transaction;

import java.sql.SQLException;

public interface TransactionBody<T> {
    T doBody() throws SQLException;
}
