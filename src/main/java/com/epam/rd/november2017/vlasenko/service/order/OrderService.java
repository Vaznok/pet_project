package com.epam.rd.november2017.vlasenko.service.order;

import java.sql.SQLException;

public interface OrderService<T, ID> {
    boolean createOrder(ID userId, ID bookId);

    Iterable<T> showClientOrders(ID user);

    Iterable<T> showNewOrders() throws SQLException;

    Iterable<T> showExpiredOrders();

    boolean cancelOrder(ID orderID) throws SQLException;

    boolean acceptOrder(Integer orderId, String plannedReturn, Integer penalty) throws SQLException;
    
    String validateOrderConfirmation(String plannedDate, String penalty);
}
