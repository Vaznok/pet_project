package com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl;

import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.UserDao;
import com.epam.rd.november2017.vlasenko.entity.User;
import com.epam.rd.november2017.vlasenko.entity.UserRole;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.epam.rd.november2017.vlasenko.entity.OrderStatus.NEW;
import static com.epam.rd.november2017.vlasenko.entity.OrderStatus.RECEIVED;

public class UserDaoImpl implements UserDao<User, Integer> {
    private static final String ADD_USER = "INSERT INTO users (email, nick_name, password, role, block, first_name, last_name, contact) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String FIND_USER_ID = "SELECT * FROM users WHERE id = ?;";
    private static final String CREATE_USER_ORDER = "INSERT INTO users_books (user_id, book_id, status) VALUES(?, ?, ?);";
    private static final String SHOW_BORROWED_BOOKS = "SELECT * FROM orders INNER JOIN books ON orders.book_id=books.id WHERE orders.status='NEW' AND orders.user_id=2;";
    private static final String FIND_USER_ALL = "SELECT * FROM users;";
    private static final String UPDATE_USER = "UPDATE users SET email=?, nick_name=?, password=?, role=?, block=?, first_name=?, last_name=?, contact=? WHERE id=?;";
    private static final String REMOVE_USER = "DELETE FROM users WHERE id=?;";

    private DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void librarian() {

    }

    @Override
    public void orderBook(Integer userId, Integer bookId) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(CREATE_USER_ORDER)) {
            stat.setInt(1, userId);
            stat.setInt(2, bookId);
            stat.setString(3, NEW.name());

            stat.executeQuery();
        }
    }

    @Override
    public Iterable<User> findBorrowedBooks(Integer userId) throws SQLException, NoSuchEntityException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(CREATE_USER_ORDER)) {
            stat.setInt(1, userId);
            stat.setString(2, RECEIVED.name());

            stat.executeQuery();
        }
        return null;
    }

    @Override
    public void create(User user) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(ADD_USER)) {
            stat.setString(1, user.getEmail());
            stat.setString(2, user.getNickName());
            stat.setString(3, user.getPassword());
            stat.setString(4, user.getRole());
            stat.setBoolean(5, user.isBlocked());
            stat.setString(6, user.getFirstName());
            stat.setString(7, user.getLastName());
            stat.setString(8, user.getContact());

            stat.executeQuery();
        }
    }

    @Override
    public void create(Iterable<User> users) throws SQLException {
        for (User user : users) {
            create(user);
        }
    }

    @Override
    public User find(Integer id) throws SQLException, NoSuchEntityException {
        User user = null;
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_USER_ID)) {
            stat.setInt(1, id);

            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    user = new User(rs.getString("email"),
                                    rs.getString("nick_name"),
                                    rs.getString("password"),
                                    UserRole.valueOf(rs.getString("role")),
                                    rs.getBoolean("block"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("contact"));
                }
            }
            if (user == null) {
                throw new NoSuchEntityException();
            }
        }
        return user;
    }

    @Override
    public Iterable<User> findAll() throws SQLException {
        List<User> list = new LinkedList<>();
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_USER_ALL)) {
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    User foundUser = new User(rs.getString("email"),
                                            rs.getString("nick_name"),
                                            rs.getString("password"),
                                            UserRole.valueOf(rs.getString("role")),
                                            rs.getBoolean("block"),
                                            rs.getString("first_name"),
                                            rs.getString("last_name"),
                                            rs.getString("contact"));
                    list.add(foundUser);
                }
            }
        }
        return list;
    }

    @Override
    public Iterable<User> find(Iterable<Integer> ids) throws SQLException, NoSuchEntityException {
        List<User> list = new LinkedList<>();

        for (Integer id : ids) {
            User foundUser = find(id);
            list.add(foundUser);
        }
        return list;
    }

    @Override
    public void update(Integer id, User user) throws SQLException, NoSuchEntityException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(UPDATE_USER)) {
            stat.setString(1, user.getEmail());
            stat.setString(2, user.getNickName());
            stat.setString(3, user.getPassword());
            stat.setString(4, user.getRole());
            stat.setBoolean(5, user.isBlocked());
            stat.setString(6, user.getFirstName());
            stat.setString(7, user.getLastName());
            stat.setString(8, user.getContact());
            stat.setInt(9, id);

            int checkUpdate = stat.executeUpdate();

            if (checkUpdate == 0) {
                throw new NoSuchEntityException();
            }
        }
    }

    @Override
    public void delete(Integer id) throws SQLException, NoSuchEntityException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(REMOVE_USER)) {
            stat.setInt(1, id);

            int checkUpdate = stat.executeUpdate();

            if (checkUpdate == 0) {
                throw new NoSuchEntityException();
            }
        }
    }
}
