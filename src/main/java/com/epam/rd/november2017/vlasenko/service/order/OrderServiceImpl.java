package com.epam.rd.november2017.vlasenko.service.order;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.OrderDao;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.OrderDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.entity.Order;
import com.epam.rd.november2017.vlasenko.entity.view.UnitedView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.TRANSACTION;
import static com.epam.rd.november2017.vlasenko.entity.Order.Status.NEW;

public class OrderServiceImpl implements OrderService<UnitedView, Integer> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private TransactionHandler transaction = TRANSACTION;
    private OrderDao orderDao = new OrderDaoImpl(transaction);

    @Override
    public void createOrder(Integer userId, Integer bookId, Integer bookCount) throws SQLException {
        transaction.doInTransaction(() -> {
            Order order = new Order(userId, bookId, NEW, bookCount);
            orderDao.create(order);
            return null;
        });
    }

    @Override
    public List<UnitedView> findClientNewOrders(Integer userId) throws SQLException {
        return transaction.doInTransaction(() -> (List<UnitedView>) orderDao.findClientOrdersByStatus(userId, Order.Status.NEW));
    }

    @Override
    public List<UnitedView> findClientActiveOrders(Integer userId) throws SQLException {
        return transaction.doInTransaction(() -> (List<UnitedView>) orderDao.findClientOrdersByStatus(userId, Order.Status.RECEIVED));
    }

    @Override
    public List<UnitedView> findNewOrders() throws SQLException {
        return transaction.doInTransaction(() -> (List<UnitedView>) orderDao.showOrdersByStatus(NEW));
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
            Order order = orderDao.find(orderId);
            order.setStatus(Order.Status.RECEIVED);
            order.setReceived(dateFormat.format(new Date()));
            order.setPlanedReturn(plannedReturn);
            order.setPenalty(penalty);
            return orderDao.update(orderId, order);
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
            Date date = dateFormat.parse(formatedDate);
            return new Date().getTime() <= date.getTime();
        } catch (ParseException e) {
            return false;
        }
    }
}
