package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.entity.Order;
import com.epam.rd.november2017.vlasenko.entity.view.UnitedView;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<Order, Integer> {

    Iterable<UnitedView> showOrdersByStatus(Order.Status status) throws SQLException;

    Iterable<UnitedView> findClientOrdersByStatus(Integer clientId, Order.Status status) throws SQLException;
}