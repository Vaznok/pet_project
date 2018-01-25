package com.epam.rd.november2017.vlasenko.service.registration;

import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.entity.User;

import java.sql.SQLException;

public interface Registration<T> {
    //return value 'null' means that validation is successful otherwise return a description of the inconsistencies
    String validate(T user) throws SQLException, NoSuchEntityException;

    void createUser(T user) throws SQLException, NoSuchEntityException;
}
