package com.epam.rd.november2017.vlasenko.service.order;

import java.sql.SQLException;

public interface OrderService<T, ID> {
    boolean createOrder(ID userId, ID bookId, ID bookCount) throws SQLException;

    Iterable<T> findClientNewOrders(ID userId) throws SQLException;

    Iterable<T> findClientActiveOrders(ID userId) throws SQLException;

    Iterable<T> findNewOrders() throws SQLException;

    Iterable<T> findActiveOrders() throws SQLException;

    Iterable<T> findExpiredOrders() throws SQLException;

    Iterable<T> findAllClientOrders(ID userId) throws SQLException;

    boolean cancelNewOrder(ID orderID) throws SQLException;

    boolean deleteOrder(ID orderID) throws SQLException;

    boolean acceptOrder(ID orderId, String plannedReturn, Integer penalty) throws SQLException;

    boolean returnOrder(ID orderId) throws SQLException;
    
    String validateOrderConfirmation(String plannedDate, String penalty);

}
