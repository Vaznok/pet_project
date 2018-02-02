package com.epam.rd.november2017.vlasenko.service.authentication;

import java.sql.SQLException;
import java.util.Locale;

public interface AuthenticationService<T> {
    String validateSyntax(String email, String password, Locale locale);

    T getUser(String email, String password) throws SQLException;
}
