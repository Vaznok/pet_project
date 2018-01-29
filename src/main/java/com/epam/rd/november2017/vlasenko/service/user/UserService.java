package com.epam.rd.november2017.vlasenko.service.user;

import java.sql.SQLException;

public interface UserService<T, ID> {
    Iterable<T> findLibrarians() throws SQLException;

    boolean deleteLibrarian(ID id) throws SQLException;

    Iterable<T> findBlocked() throws SQLException;

    Iterable<T> findNoBlocked() throws SQLException;

    boolean unBlockUser(ID user) throws SQLException;

    boolean blockUser(ID user) throws SQLException;
}
