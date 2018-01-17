package com.epam.rd.november2017.vlasenko.dao.jdbc.transaction;

public interface TransactionHandler {
    <T> T doInTransaction(TransactionBody<T> doBody);
}
