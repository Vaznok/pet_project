package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;

import java.sql.SQLException;

public interface CrudDao<T, ID> {
    void create(T object) throws SQLException;

    void create(Iterable<T> objects) throws SQLException;

    T find(ID id) throws SQLException, NoSuchEntityException;

    Iterable<T> findAll () throws SQLException;

    Iterable<T> find(Iterable<ID> objects) throws SQLException, NoSuchEntityException;

    void update(ID id, T object) throws SQLException, NoSuchEntityException;

    void delete(ID id) throws SQLException, NoSuchEntityException;
}
