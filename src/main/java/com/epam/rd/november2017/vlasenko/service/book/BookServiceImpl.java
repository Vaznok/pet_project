package com.epam.rd.november2017.vlasenko.service.book;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.BookDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionBody;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.Book;

import java.sql.SQLException;

public class BookServiceImpl implements BookService {
    private TransactionHandlerImpl transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private BookDaoImpl bookDao = new BookDaoImpl(new DataSourceForTest());

    @Override
    public Iterable<Book> findAll() throws SQLException {
        return transaction.doInTransaction(() -> bookDao.findAll());
    }

    @Override
    public Book find(Integer id) throws SQLException {
        return transaction.doInTransaction(() -> bookDao.find(id));
    }
}
