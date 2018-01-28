package com.epam.rd.november2017.vlasenko.service.authentication;

import java.sql.SQLException;

public interface AuthenticationService<T> {
    String validateSyntax(String email, String password);

    T getUser(String email, String password) throws SQLException;
}
