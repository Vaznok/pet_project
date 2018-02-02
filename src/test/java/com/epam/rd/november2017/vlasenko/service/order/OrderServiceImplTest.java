package com.epam.rd.november2017.vlasenko.service.order;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.BookDao;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.OrderDao;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.BookDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.OrderDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.transaction.TransactionHandler;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.TRANSACTION_TEST;

public class OrderServiceImplTest {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private TransactionHandler transaction = TRANSACTION_TEST;
    private OrderDao orderDao = new OrderDaoImpl(transaction);
    private BookDao bookDao = new BookDaoImpl(transaction);

    private OrderServiceImpl sut = new OrderServiceImpl();

    /*@BeforeEach
    public void truncateTableDb() throws SQLException {
        transaction.doInTransaction(() -> {
            try (Statement stat = transaction.getConnection().createStatement()) {
                stat.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
                stat.executeUpdate("TRUNCATE TABLE orders");
                stat.executeUpdate("TRUNCATE TABLE books");
                return null;
            }
        });
    }*/

    @Test
    public void correctBookCountAfterCreationOrder() {

    }

    @Test
    public void resourceBundle() {
        ResourceBundle bundleEn = ResourceBundle.getBundle("messages_en");
        ResourceBundle bundleRus = ResourceBundle.getBundle("messages", new Locale("ru_RU"));


    }
}
