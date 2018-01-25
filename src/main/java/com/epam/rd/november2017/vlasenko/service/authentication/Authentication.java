package com.epam.rd.november2017.vlasenko.service.authentication;

import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;

import java.sql.SQLException;

public interface Authentication<T> {
    String validateSyntax(String email, String password);

    T getUser(String email, String password) throws SQLException, NoSuchEntityException;
}
