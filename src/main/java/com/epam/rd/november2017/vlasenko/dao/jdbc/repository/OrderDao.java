package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.entity.Order;
import com.epam.rd.november2017.vlasenko.service.order.view.View;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<Order, Integer> {

    Iterable<View> showOrdersByStatus(Order.Status status) throws SQLException;

    Iterable<View> showExpiredOrders();

    Iterable<View> showClientOrders(Integer clientId);
}