package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import java.io.IOException;
import java.sql.SQLException;

public interface CrudRepository<T, ID> {
    void create(T object) throws IOException, SQLException;

    void create(Iterable<T> objects) throws IOException, SQLException;

    T find(ID id) throws IOException, SQLException;

    Iterable<T> find(Iterable<ID> objects) throws IOException, SQLException;

    void update(ID id, T object) throws IOException, SQLException;

    void delete(ID id) throws IOException, SQLException;
}

