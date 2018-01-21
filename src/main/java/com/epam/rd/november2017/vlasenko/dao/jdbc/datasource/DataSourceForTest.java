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

public class DataSourceForTest extends SimpleDataSource {
    private static Logger logger = LoggerFactory.getLogger(DataSourceForTest.class.getSimpleName());

    @Override
    public Connection getConnection() throws SQLException {
        logger.debug("Attempt to get JDBC connection!");
        Properties props = new Properties();
        try (InputStream in = DataSourceBoneCp.class.getClassLoader().getResourceAsStream("db/test.properties")) {
            props.load(in);
        } catch (IOException e) {
            logger.warn("Unsuccessful attempt to read properties from liquibase.properties file!");
        }

        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.warn("Driver class wasn't found!", e.getMessage());
        }

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
        logger.debug("Jdbc connection is gotten!");
        return conn;
    }
}
