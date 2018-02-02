package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.OrderDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.TRANSACTION_TEST;
import static com.epam.rd.november2017.vlasenko.entity.Order.Status.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDaoImplTest {
    private TransactionHandler transaction = TRANSACTION_TEST;
    private OrderDaoImpl sut = new OrderDaoImpl(transaction);

    @BeforeEach
    public void truncateTableDb() throws SQLException {
        transaction.doInTransaction(() -> {
            try (Statement stat = transaction.getConnection().createStatement()) {
                stat.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
                stat.executeUpdate("TRUNCATE TABLE orders");
                return null;
            }
        });
    }

    @Test
    public void createNewOrder_FindCreatedOrderById() throws SQLException {
        Order order = new Order(1, 2, NEW, 2);

        Order foundOrder = transaction.doInTransaction(() -> {
            sut.create(order);
            return sut.find(1);
        });

        assertEquals(order.getUserId(), foundOrder.getUserId());
        assertEquals(order.getBookId(), foundOrder.getBookId());
        assertEquals(order.getStatus(), foundOrder.getStatus());
        assertEquals(order.getBookCount(), foundOrder.getBookCount());

    }

    @Test
    public void createNewOrders_FindCreatedOrders() throws SQLException {
        List<Order> orderList = new ArrayList<Order>(){
            {
                add(new Order(1, 2, NEW, 2));
                add(new Order(1, 1, NEW, 1));
                add(new Order(2, 2, NEW, 2));
            }
        };

        List<Order> ordersFromDb = transaction.doInTransaction(() -> {
            sut.create(orderList);
            return null;
        });
    }

    @Test
    public void updateOrderById_FindUpdatedOrder() throws SQLException {
        Order createOrder = new Order(1, 2, NEW, 2);
        Order updateOrder = new Order(1, 1, NEW, 1);

        Order foundOrder = transaction.doInTransaction(() -> {
            sut.create(createOrder);
            sut.update(1, updateOrder);
            return sut.find(1);
        });

        assertEquals(updateOrder.getUserId(), foundOrder.getUserId());
        assertEquals(updateOrder.getBookId(), foundOrder.getBookId());
        assertEquals(updateOrder.getStatus(), foundOrder.getStatus());
        assertEquals(updateOrder.getBookCount(), foundOrder.getBookCount());
    }
}
