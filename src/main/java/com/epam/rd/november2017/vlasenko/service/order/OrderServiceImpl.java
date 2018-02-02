package com.epam.rd.november2017.vlasenko.service.order;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.BookDao;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.OrderDao;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.BookDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.OrderDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.transaction.TransactionBody;
import com.epam.rd.november2017.vlasenko.dao.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.entity.Book;
import com.epam.rd.november2017.vlasenko.entity.Order;
import com.epam.rd.november2017.vlasenko.entity.view.UnitedView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.TRANSACTION;
import static com.epam.rd.november2017.vlasenko.entity.Order.Status.NEW;

public class OrderServiceImpl implements OrderService<UnitedView, Integer> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private TransactionHandler transaction = TRANSACTION;
    private OrderDao orderDao = new OrderDaoImpl(transaction);
    private BookDao bookDao = new BookDaoImpl(transaction);

    @Override
    public boolean deleteOrder(Integer orderID) throws SQLException {
        return transaction.doInTransaction(() -> {
            Order order = orderDao.find(orderID);
            Book book = bookDao.find(order.getBookId());
            book.setCount(book.getCount() + order.getBookCount());
            bookDao.update(book.getId(), book);
            return orderDao.delete(orderID);
        });
    }

    @Override
    public synchronized boolean createOrder(Integer userId, Integer bookId, Integer bookCount) throws SQLException {
        return transaction.doInTransaction(() -> {
            if (!this.changeBookCount(bookId, bookCount)) {
                return false;
            }
            Order order = new Order(userId, bookId, NEW, bookCount);
            orderDao.create(order);
            return true;
        });
    }

    private synchronized boolean changeBookCount(Integer bookId, Integer bookCount) throws SQLException {
        Book book = bookDao.find(bookId);
        Integer realCountOfBooks = book.getCount();
        if (realCountOfBooks >= bookCount) {
            realCountOfBooks = realCountOfBooks - bookCount;
            book.setCount(realCountOfBooks);
            bookDao.update(bookId, book);
            return true;
        }
        return false;
    }

    @Override
    public List<UnitedView> findClientNewOrders(Integer userId) throws SQLException {
        return transaction.doInTransaction(() -> (List<UnitedView>) orderDao.findClientOrdersByStatus(userId, Order.Status.NEW));
    }

    @Override
    public List<UnitedView> findClientActiveOrders(Integer userId) throws SQLException {
        return transaction.doInTransaction(() -> {
            List<UnitedView> activeOrders = (List<UnitedView>) orderDao.findClientOrdersByStatus(userId, Order.Status.RECEIVED);
            for(UnitedView order: activeOrders) {
                try {
                    long plannedReturn = dateFormat.parse(order.getPlannedReturn()).getTime();
                    long currentTime = new Date().getTime();
                    if(plannedReturn > currentTime) {
                        order.setPenalty(0);
                    }
                } catch (ParseException e) {
                    e.getStackTrace();
                }
            }
            return activeOrders;
        });
    }

    @Override
    public List<UnitedView> findNewOrders() throws SQLException {
        return transaction.doInTransaction(() -> (List<UnitedView>) orderDao.findOrdersByStatus(NEW));
    }

    @Override
    public List<UnitedView> findActiveOrders() throws SQLException {
        return transaction.doInTransaction(() -> (List<UnitedView>) orderDao.findOrdersByStatus(Order.Status.RECEIVED));
    }

    @Override
    public List<UnitedView> findExpiredOrders() throws SQLException {
        return (List<UnitedView>) transaction.doInTransaction(() -> orderDao.findExpiredOrders());
    }

    @Override
    public List<UnitedView> findAllClientOrders(Integer userId) throws SQLException {
        return transaction.doInTransaction(() -> (List<UnitedView>) orderDao.findClientOrders(userId));
    }

    @Override
    public synchronized boolean cancelNewOrder(Integer orderID) throws SQLException {
        Boolean success = transaction.doInTransaction(() -> {
            Order order = orderDao.find(orderID);
            Book book = bookDao.find(order.getBookId());
            book.setCount(book.getCount() + order.getBookCount());
            bookDao.update(book.getId(), book);
            order.setStatus(Order.Status.CANCELED);
            return orderDao.update(orderID, order);
        });
        return success;
    }

    @Override
    public synchronized boolean acceptOrder(Integer orderId, String plannedReturn, Integer penalty) throws SQLException {
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
    public synchronized boolean returnOrder(Integer orderId) throws SQLException {
        Boolean success = transaction.doInTransaction(() -> {
            Order order = orderDao.find(orderId);
            Book book = bookDao.find(order.getBookId());
            book.setCount(book.getCount() + order.getBookCount());
            bookDao.update(book.getId(), book);
            order.setStatus(Order.Status.RETURNED);
            order.setReturned(dateFormat.format(new Date()));
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
            return LocalDate.now().toEpochDay() < date.getTime();
        } catch (ParseException e) {
            return false;
        }
    }
}
