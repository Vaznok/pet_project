package com.epam.rd.november2017.vlasenko.dao.jdbc.transaction;

public interface TransactionBody <T> {
    <T> T doBody();
}
