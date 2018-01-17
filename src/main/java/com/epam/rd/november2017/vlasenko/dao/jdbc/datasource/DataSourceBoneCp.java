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

    @Override
    public Connection getConnection() throws SQLException {
        logger.info("Attempt to get JDBC connection!");
        Properties props = new Properties();
        try (InputStream in = DataSourceBoneCp.class.getClassLoader().getResourceAsStream("db/liquibase.properties")) {
            props.load(in);
        } catch (IOException e) {
            logger.warn("Unsuccessful attempt to read properties from liquibase.properties file!");
        }

        String drivers = props.getProperty("driver");
        if (drivers != null) {
            System.setProperty("driver", drivers);
        } else {
            logger.warn("Driver for JDBC connection was not found!");
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

        BoneCP connectionPool = new BoneCP(config);
        Connection conn = connectionPool.getConnection();
        conn.setAutoCommit(false);
        logger.info("Jdbc connection is gotten!");
        return conn;
    }
}
