package com.epam.rd.november2017.vlasenko.listener;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.sun.org.apache.xpath.internal.SourceTree;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("\n------------------");
        System.out.println("Method was called!");
        System.out.println("\n------------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("\n------------------");
        System.out.println("Method was called!");
        System.out.println("\n------------------");
    }
}
