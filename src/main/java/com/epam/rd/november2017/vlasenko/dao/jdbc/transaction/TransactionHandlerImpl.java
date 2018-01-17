package com.epam.rd.november2017.vlasenko.dao.jdbc.transaction;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.SimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

public class TransactionHandlerImpl extends SimpleDataSource implements TransactionHandler {
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T> T doInTransaction(TransactionBody<T> body) {
        T result = null;
        try (Connection conn = dataSource.getConnection()){
            connectionHolder.set(conn);
            result = body.doBody();
            conn.commit();
            return result;
        } catch (Exception e) {

        }
        return result;
    }

    @Override
    public Connection getConnection() {
        return connectionHolder.get();
    }
}
