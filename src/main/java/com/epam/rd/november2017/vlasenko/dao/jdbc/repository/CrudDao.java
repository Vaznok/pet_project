package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import java.sql.SQLException;

public interface CrudDao<T, ID> {
    void create(T object) throws SQLException;

    void create(Iterable<T> objects) throws SQLException;

    T find(ID id) throws SQLException;

    Iterable<T> findAll () throws SQLException;

    Iterable<T> find(Iterable<ID> objects) throws SQLException;

    boolean update(ID id, T object) throws SQLException;

    boolean delete(ID id) throws SQLException;
}
