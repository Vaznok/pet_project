package com.epam.rd.november2017.vlasenko.service.registration;

import java.sql.SQLException;
import java.util.Locale;

public interface RegistrationService<T> {
    //return value 'null' means that validateOrderConfirmation is successful otherwise return a description of the inconsistencies
    String validate(T user, Locale locale) throws SQLException;

    void createUser(T user) throws SQLException;
}
