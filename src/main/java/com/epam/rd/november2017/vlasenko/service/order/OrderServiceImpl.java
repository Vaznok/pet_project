package com.epam.rd.november2017.vlasenko.service.order;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.OrderDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.Order;
import com.epam.rd.november2017.vlasenko.service.order.view.View;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderServiceImpl implements OrderService<View, Integer> {
    private TransactionHandler transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private OrderDaoImpl orderDao = new OrderDaoImpl(transaction);

    @Override
    public boolean createOrder(Integer userId, Integer bookId) {
        return false;
    }

    @Override
    public Iterable<View> showClientOrders(Integer user) {
        return null;
    }

    @Override
    public Iterable<View> showNewOrders() throws SQLException {
        return transaction.doInTransaction(() -> orderDao.showOrdersByStatus(Order.Status.NEW));
    }

    @Override
    public Iterable<View> showExpiredOrders() {
        return null;
    }

    @Override
    public boolean cancelOrder(Integer orderID) throws SQLException {
        Boolean success = transaction.doInTransaction(() -> {
            Order toUpdate = orderDao.find(orderID);
            toUpdate.setStatus(Order.Status.CANCELED);
            return orderDao.update(orderID, toUpdate);
        });
        return success;
    }

    @Override
    public boolean acceptOrder(Integer orderId, String plannedReturn, Integer penalty) throws SQLException {
        boolean success = transaction.doInTransaction(() -> {
            Order toUpdate = orderDao.find(orderId);
            toUpdate.setStatus(Order.Status.RECEIVED);
            toUpdate.setPlanedReturn(plannedReturn);
            toUpdate.setPenalty(penalty);
            return orderDao.update(orderId, toUpdate);
        });
        return success;
    }

    @Override
    public String validateOrderConfirmation(String plannedDate, String penalty) {
        if (plannedDate.isEmpty() || penalty.isEmpty()) {
            return "Fields must be filled!";
        } else if(!validateDate(plannedDate)) {
            return "Date must not be in the past!";
        }
        try {
            int num = Integer.valueOf(penalty);
            if (num <= 0 ) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return "Penalty must be integer and higher then '0'!";
        }
        return null;
    }

    private boolean validateDate(String formatedDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(formatedDate);
            return new Date().getTime() <= date.getTime();
        } catch (ParseException e) {
            return false;
        }
    }
}
