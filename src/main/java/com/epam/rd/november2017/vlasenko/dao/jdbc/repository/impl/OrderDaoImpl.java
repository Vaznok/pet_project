package com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.OrderDao;
import com.epam.rd.november2017.vlasenko.entity.Order;
import com.epam.rd.november2017.vlasenko.service.order.view.View;
import com.epam.rd.november2017.vlasenko.service.order.view.ViewBuilder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static final String CREATE_ORDER = "INSERT INTO orders (user_id, book_id, status, count) VALUES(?, ?, ?, ?);";
    private static final String FIND_ORDER_ID = "SELECT * FROM orders WHERE order_id = ?";
    private static final String FIND_ORDER_ALL = "SELECT * FROM orders";
    private static final String UPDATE_ORDER = "UPDATE orders SET user_id=?, book_id=?, received=?, planned_return=?, returned=?, penalty=?, status=?, count=? WHERE order_id=?;";
    private static final String REMOVE_ORDER = "DELETE FROM orders WHERE order_id=?;";
    private static final String SHOW_BORROWED_BOOKS = "SELECT * FROM orders INNER JOIN books ON orders.book_id=books.id " +
            "WHERE orders.status='NEW' AND orders.user_id=2;";
    private static final String JOIN_FIND_BY_ORDER_STATUS = "SELECT * FROM orders INNER JOIN books ON orders.book_id=books.id INNER JOIN users ON orders.user_id=users.id WHERE orders.status=?";

    private DataSource dataSource;

    public OrderDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /*@Override
    public Iterable<User> findBorrowedBooks(Integer userId) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(CREATE_USER_ORDER)) {
            stat.setInt(1, userId);
            stat.setString(2, RECEIVED.name());

            stat.executeQuery();
        }
        return null;
    }*/

    /*@Override
    public void orderBook(Integer userId, Integer bookId) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(CREATE_USER_ORDER)) {
            stat.setInt(1, userId);
            stat.setInt(2, bookId);
            stat.setString(3, NEW.name());

            stat.executeQuery();
        }
    }*/

    @Override
    public Iterable<View> showOrdersByStatus(Order.Status status) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(JOIN_FIND_BY_ORDER_STATUS)) {
            stat.setString(1, status.name());

            return selectViewQuery(stat);
        }
    }

    @Override
    public Iterable<View> showExpiredOrders() {
        return null;
    }

    @Override
    public Iterable<View> showClientOrders(Integer clientId) {
        return null;
    }

    @Override
    public void create(Order order) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(CREATE_ORDER)) {
            stat.setInt(1, order.getUserId());
            stat.setInt(2, order.getBookId());
            stat.setString(3, Order.Status.NEW.name());
            stat.setInt(4, order.getBookCount());

            stat.executeQuery();
        }
    }

    @Override
    public void create(Iterable<Order> orders) throws SQLException {
        for (Order order : orders) {
            create(order);
        }
    }

    @Override
    public Order find(Integer id) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_ORDER_ID)) {
            stat.setInt(1, id);

            Order foundOrder = null;
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    foundOrder = new Order(rs.getInt("user_id"),
                            rs.getInt("book_id"),
                            rs.getString("received"),
                            rs.getString("planned_return"),
                            rs.getString("returned"),
                            rs.getInt("penalty"),
                            Order.Status.valueOf(rs.getString("status")),
                            rs.getInt("count"));
                    foundOrder.setOrderId(rs.getInt("order_id"));
                }
            }
            return foundOrder;
        }
    }

    @Override
    public Iterable<Order> findAll() throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_ORDER_ALL)) {
            return selectOrderQuery(stat);
        }
    }

    @Override
    public Iterable<Order> find(Iterable<Integer> ids) throws SQLException {
        List<Order> list = new LinkedList<>();

        for (Integer id : ids) {
            Order foundUser = find(id);
            list.add(foundUser);
        }
        return list;
    }

    @Override
    public boolean update(Integer id, Order order) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(UPDATE_ORDER)) {
            stat.setInt(1, order.getUserId());
            stat.setInt(2, order.getBookId());
            stat.setString(3, order.getReceived());
            stat.setString(4, order.getPlanedReturn());
            stat.setString(5, order.getReturned());
            if(order.getPenalty() == null) {
                stat.setNull(6, 0);
            } else {
                stat.setInt(6, order.getPenalty());
            }
            stat.setString(7, order.getStatus().name());
            stat.setInt(8, order.getBookCount());
            stat.setInt(9, id);

            int checkUpdate = stat.executeUpdate();

            return checkUpdate != 0;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(REMOVE_ORDER)) {
            stat.setInt(1, id);

            int checkUpdate = stat.executeUpdate();

            return checkUpdate != 0;
        }
    }

    private Iterable<Order> selectOrderQuery(PreparedStatement stat) throws SQLException {
        try (ResultSet rs = stat.executeQuery()) {
            List<Order> list = new LinkedList<>();
            while (rs.next()) {
                Order foundOrder = new Order(rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getString("received"),
                        rs.getString("plannedReturn"),
                        rs.getString("returned"),
                        rs.getInt("penalty"),
                        Order.Status.valueOf(rs.getString("Status")),
                        rs.getInt("count"));
                foundOrder.setOrderId(rs.getInt("order_id"));
                list.add(foundOrder);
            }
            return list;
        }
    }

    private Iterable<View> selectViewQuery(PreparedStatement stat) throws SQLException {
        try (ResultSet rs = stat.executeQuery()) {
            List<View> list = new LinkedList<>();
            while (rs.next()) {
                View view = new ViewBuilder()
                        //Order data
                        .buildOrderId(rs.getInt("order_id"))
                        .buildReceived(rs.getString("received"))
                        .buildPlanedReturn(rs.getString("planned_return"))
                        .buildReturned(rs.getString("returned"))
                        .buildPenalty(rs.getInt("penalty"))
                        .buildOrderBookCount(rs.getInt("orders.count"))
                        //Book data
                        .buildBookId(rs.getInt("book_id"))
                        .buildBookName(rs.getString("books.name"))
                        .buildAuthor(rs.getString("author"))
                        .buildPublisher(rs.getString("publisher"))
                        .buildPublicationDate(rs.getString("publication_date"))
                        .buildTotalBookCount(rs.getInt("books.count"))
                        //User data
                        .buildUserId(rs.getInt("user_id"))
                        .buildEmail(rs.getString("email"))
                        .buildNickName(rs.getString("nick_name"))
                        .buildFirstName(rs.getString("first_name"))
                        .buildLastName(rs.getString("last_name"))
                        .buildContact(rs.getString("contact"))
                        .build();
                list.add(view);
            }
            return list;
        }
    }
}
