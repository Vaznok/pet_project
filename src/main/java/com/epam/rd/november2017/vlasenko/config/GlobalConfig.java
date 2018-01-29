package com.epam.rd.november2017.vlasenko.config;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceBoneCp;
import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;

import javax.sql.DataSource;

public class GlobalConfig {
    public static final DataSource DATA_SOURCE = new DataSourceBoneCp();
    public static final DataSource DATA_SOURCE_TEST = new DataSourceForTest();

    public static final TransactionHandler TRANSACTION = new TransactionHandlerImpl(DATA_SOURCE_TEST);
    public static final TransactionHandler TRANSACTION_TEST = new TransactionHandlerImpl(DATA_SOURCE_TEST);
}
