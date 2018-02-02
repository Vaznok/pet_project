package com.epam.rd.november2017.vlasenko.dao.jdbc.datasource;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceBoneCp extends SimpleDataSource {
    private static Logger logger = LoggerFactory.getLogger(DataSourceBoneCp.class.getSimpleName());
    private static BoneCP connectionPool;
    private static DataSourceBoneCp dataSource;

    static {
        logger.debug("Attempt to get JDBC connection!");
        Properties props = new Properties();
        try (InputStream in = DataSourceBoneCp.class.getClassLoader().getResourceAsStream("db/liquibase.properties")) {
            props.load(in);
        } catch (IOException e) {
            logger.warn("Unsuccessful attempt to read properties from liquibase.properties file!", e.getMessage());
        }

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.warn("Driver class wasn't found!", e.getMessage());
        }

        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMinConnectionsPerPartition(5);
        config.setMaxConnectionsPerPartition(10);
        config.setPartitionCount(1);
        try {
            connectionPool = new BoneCP(config);
        } catch (SQLException e) {
            logger.warn("Fault to create BoneCp connection pool!");
        }
        dataSource = new DataSourceBoneCp();
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = connectionPool.getConnection();
        conn.setAutoCommit(false);
        logger.debug("Jdbc connection is gotten!");
        return conn;
    }

    public static DataSourceBoneCp getDataSource() {
        return dataSource;
    }
}
