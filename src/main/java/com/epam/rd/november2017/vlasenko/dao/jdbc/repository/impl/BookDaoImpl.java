package com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.BookDao;
import com.epam.rd.november2017.vlasenko.entity.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private static final String ADD_BOOK = "INSERT INTO books (name, author, publisher, publication_date, count) VALUES(?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE count = count + ?;";
    private static final String FIND_BOOK_ID = "SELECT * FROM books WHERE id = ?;";
    private static final String FIND_BOOK_NAME = "SELECT * FROM books WHERE name LIKE ?;";
    private static final String FIND_BOOK_AUTHOR = "SELECT * FROM books WHERE author LIKE ?;";
    private static final String FIND_ALL_BOOK = "SELECT * FROM books;";
    private static final String UPDATE_BOOK = "UPDATE books SET name=?, author=?, publisher=?, publication_date=?, count=? WHERE id=?;";
    private static final String REMOVE_BOOK = "DELETE FROM books WHERE id=?;";

    private DataSource dataSource;

    public BookDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Book book) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(ADD_BOOK)) {
            stat.setString(1, book.getName());
            stat.setString(2, book.getAuthor());
            stat.setString(3, book.getPublisher());
            stat.setString(4, book.getPublicationDate());
            stat.setInt(5, book.getCount());
            //in case row is duplicated
            stat.setInt(6, book.getCount());

            stat.executeQuery();
        }
    }

    @Override
    public void create(Iterable<Book> objects) throws SQLException {
        for (Book book : objects) {
            create(book);
        }
    }

    @Override
    public Book find(Integer id) throws SQLException {
        Book foundBook = null;
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_BOOK_ID)) {
            stat.setInt(1, id);

            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    foundBook = new Book(rs.getString("name"),
                            rs.getString("author"),
                            rs.getString("publisher"),
                            rs.getString("publication_date"),
                            rs.getInt("count"));
                    foundBook.setId(rs.getInt("id"));
                }
            }
        }
        return foundBook;
    }

    @Override
    public Iterable<Book> findByName(String name) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_BOOK_NAME)) {
            name = "%" + name + "%";
            stat.setString(1, name);

            return selectUserQuery(stat);
        }
    }

    @Override
    public Iterable<Book> findByAuthor(String author) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_BOOK_AUTHOR)) {
            author = "%" + author + "%";

            stat.setString(1, author);

            return selectUserQuery(stat);
        }
    }

    @Override
    public Iterable<Book> findAll() throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_ALL_BOOK)) {
            return selectUserQuery(stat);
        }
    }

    @Override
    public Iterable<Book> find(Iterable<Integer> ids) throws SQLException {
        List<Book> list = new LinkedList<>();

        for (Integer id : ids) {
            Book foundBook = find(id);
            list.add(foundBook);
        }
        return list;
    }

    @Override
    public boolean update(Integer id, Book book) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(UPDATE_BOOK)) {
            stat.setString(1, book.getName());
            stat.setString(2, book.getAuthor());
            stat.setString(3, book.getPublisher());
            stat.setString(4, book.getPublicationDate());
            stat.setInt(5, book.getCount());
            stat.setInt(6, id);

            int checkUpdate = stat.executeUpdate();

            return checkUpdate != 0;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(REMOVE_BOOK)) {
            stat.setInt(1, id);

            int checkUpdate = stat.executeUpdate();

            return checkUpdate != 0;
        }
    }

    private Iterable<Book> selectUserQuery(PreparedStatement stat) throws SQLException {
        try (ResultSet rs = stat.executeQuery()) {
            List<Book> list = new LinkedList<>();
            while (rs.next()) {
                Book foundBook = new Book(rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("publication_date"),
                        rs.getInt("count"));
                foundBook.setId(rs.getInt("id"));
                list.add(foundBook);
            }
            return list;
        }
    }
}