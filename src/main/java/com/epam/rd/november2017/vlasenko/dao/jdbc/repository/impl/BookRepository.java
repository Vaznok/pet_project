package com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceBoneCp;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.CrudRepository;
import com.epam.rd.november2017.vlasenko.entity.Book;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BookRepository implements CrudRepository<Book, Integer> {
    private static final String ADD_BOOK_SQL = "INSERT INTO books (name, author, publisher, publication_date, count) VALUES(?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE count = count + ?";
    private static final String FIND_BOOK_SQL = "SELECT * FROM books WHERE id = ?;";
    private static final String UPDATE_BOOK_SQL = "UPDATE books SET name=?, author=?, publisher=?, publication_date=?, count=? WHERE id=?";
    private static final String REMOVE_BOOK_SQL = "DELETE FROM books WHERE id=?";

    private DataSource dataSource = new DataSourceBoneCp();

    @Override
    public void create(Book book) throws IOException, SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stat = conn.prepareStatement(ADD_BOOK_SQL)) {

            stat.setString(1, book.getName());
            stat.setString(2, book.getAuthor());
            stat.setString(3, book.getPublisher());
            stat.setString(4, book.getPublicationDate());
            stat.setInt(5, book.getCount());
            //in case row is duplicated
            stat.setInt(6, book.getCount());

            stat.executeQuery();

            conn.commit();
        }
    }

    @Override
    public void create(Iterable<Book> objects) throws IOException, SQLException {
        try (Connection conn = dataSource.getConnection()) {
            for (Book book : objects) {
                create(book, conn);
            }
            conn.commit();
        }
    }

    private void create(Book book, Connection conn) throws IOException, SQLException {
        try (PreparedStatement stat = conn.prepareStatement(ADD_BOOK_SQL)) {

            stat.setString(1, book.getName());
            stat.setString(2, book.getAuthor());
            stat.setString(3, book.getPublisher());
            stat.setString(4, book.getPublicationDate());
            stat.setInt(5, book.getCount());
            stat.setInt(6, book.getCount());

            stat.executeQuery();
        }
    }

    @Override
    public Book find(Integer id) throws IOException, SQLException {
        Book foundBook = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stat = conn.prepareStatement(FIND_BOOK_SQL)) {
            stat.setInt(1, id);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                foundBook = new Book(rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("publication_date"),
                        rs.getInt("count"));
            }
            conn.commit();
        }
        if (foundBook == null) {
            throw new NullPointerException();
        }
        return foundBook;
    }

    @Override
    public Iterable<Book> find(Iterable<Integer> ids) throws IOException, SQLException {
        List<Book> list = new LinkedList<>();
        try (Connection conn = dataSource.getConnection()) {
            for (Integer id : ids) {
                Book foundBook = find(id, conn);
                list.add(foundBook);
            }
            conn.commit();
        }
        return list;
    }

    private Book find(Integer id, Connection conn) throws IOException, SQLException {
        Book foundBook = null;
        try (PreparedStatement stat = conn.prepareStatement(FIND_BOOK_SQL)) {
            stat.setInt(1, id);

            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                foundBook = new Book(rs.getString("name"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("publication_date"),
                        rs.getInt("count"));
            }
        }
        if (foundBook == null) {
            throw new NullPointerException();
        }
        return foundBook;
    }

    @Override
    public void update(Integer id, Book book) throws IOException, SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stat = conn.prepareStatement(UPDATE_BOOK_SQL)) {

            stat.setString(1, book.getName());
            stat.setString(2, book.getAuthor());
            stat.setString(3, book.getPublisher());
            stat.setString(4, book.getPublicationDate());
            stat.setInt(5, book.getCount());
            stat.setInt(6, id);

            int checkUpdate = stat.executeUpdate();

            if (checkUpdate == 0) {
                throw new NullPointerException();
            }
            conn.commit();
        }
    }

    @Override
    public void delete(Integer id) throws IOException, SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stat = conn.prepareStatement(REMOVE_BOOK_SQL)) {

            stat.setInt(1, id);

            int checkUpdate = stat.executeUpdate();

            if (checkUpdate == 0) {
                throw new NullPointerException();
            }
            conn.commit();
        }
    }
}