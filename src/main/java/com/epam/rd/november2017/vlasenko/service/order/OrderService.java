package com.epam.rd.november2017.vlasenko.service.order;

import java.sql.SQLException;

public interface OrderService<T, ID> {
    void createOrder(ID userId, ID bookId, ID bookCount) throws SQLException;

    Iterable<T> findClientNewOrders(ID userId) throws SQLException;

    Iterable<T> findClientActiveOrders(ID userId) throws SQLException;

    Iterable<T> findNewOrders() throws SQLException;

    boolean cancelOrder(ID orderID) throws SQLException;

    boolean acceptOrder(ID orderId, String plannedReturn, Integer penalty) throws SQLException;
    
    String validateOrderConfirmation(String plannedDate, String penalty);
}
