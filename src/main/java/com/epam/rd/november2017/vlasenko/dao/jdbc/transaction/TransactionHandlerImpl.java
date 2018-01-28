package com.epam.rd.november2017.vlasenko.dao.jdbc.transaction;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.SimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHandlerImpl extends SimpleDataSource implements TransactionHandler {
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private DataSource dataSource;

    private static Logger logger = LoggerFactory.getLogger(TransactionHandlerImpl.class.getSimpleName());

    public TransactionHandlerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T> T doInTransaction(TransactionBody<T> body) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            connectionHolder.set(conn);
            T result = body.doBody();
            conn.commit();
            return result;
        } catch (SQLException e) {
            connectionHolder.get().rollback();
            logger.info("Exception during transaction!", e);
            throw e;
        } finally {
            connectionHolder.remove();
        }
    }

    @Override
    public Connection getConnection() {
        return connectionHolder.get();
    }
}
