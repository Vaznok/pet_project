package com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.UserDao;
import com.epam.rd.november2017.vlasenko.entity.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String ADD_USER = "INSERT INTO users (email, nick_name, password, role, block, first_name, last_name, contact) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String FIND_USER_ID = "SELECT * FROM users WHERE id = ?;";
    private static final String FIND_USER_EMAIL = "SELECT * FROM users WHERE email = ?;";
    private static final String FIND_USER_NICKNAME = "SELECT * FROM users WHERE nick_name = ?;";
    private static final String FIND_USERS_BY_ROLE = "SELECT * FROM users WHERE role = ?;";
    private static final String FIND_USERS_BY_BLOCK = "SELECT * FROM users WHERE block = ? AND role <> 'ADMINISTRATOR';";
    private static final String FIND_ALL_USER = "SELECT * FROM users;";
    private static final String UPDATE_USER = "UPDATE users SET email=?, nick_name=?, password=?, role=?, block=?, first_name=?, " +
            "last_name=?, contact=? WHERE id=?;";
    private static final String REMOVE_USER = "DELETE FROM users WHERE id=?;";
    private static final String FIND_AUTHORIZED_USER = "SELECT * FROM users WHERE email=? AND password=?;";

    private DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Iterable<User> findByBlock(boolean isBlocked) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_USERS_BY_BLOCK)) {
            stat.setBoolean(1, isBlocked);

            return selectUsersQuery(stat);
        }
    }

    @Override
    public Iterable<User> findByRole(User.Role role) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_USERS_BY_ROLE)) {
            stat.setString(1, role.name());

            return selectUsersQuery(stat);
        }
    }

    @Override
    public User findUserByEmail(String email) throws SQLException {

        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_USER_EMAIL)) {
            stat.setString(1, email);

            return selectUserQuery(stat);
        }

    }

    @Override
    public User findUserByNickName(String nickName) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_USER_NICKNAME)) {
            stat.setString(1, nickName);

            return selectUserQuery(stat);
        }

    }

    @Override
    public User findAuthorizedUser(String email, String password) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_AUTHORIZED_USER)) {
            stat.setString(1, email);
            stat.setString(2, password);

            return selectUserQuery(stat);
        }
    }

    @Override
    public void create(User user) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(ADD_USER)) {
            stat.setString(1, user.getEmail());
            stat.setString(2, user.getNickName());
            stat.setString(3, user.getPassword());
            stat.setString(4, user.getRole().name());
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
    public User find(Integer id) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_USER_ID)) {
            stat.setInt(1, id);

            return selectUserQuery(stat);
        }
    }

    @Override
    public Iterable<User> findAll() throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(FIND_ALL_USER)) {

            return selectUsersQuery(stat);
        }
    }

    @Override
    public Iterable<User> find(Iterable<Integer> ids) throws SQLException {
        List<User> list = new LinkedList<>();

        for (Integer id : ids) {
            User foundUser = find(id);
            list.add(foundUser);
        }
        return list;
    }

    @Override
    public boolean update(Integer id, User user) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(UPDATE_USER)) {
            stat.setString(1, user.getEmail());
            stat.setString(2, user.getNickName());
            stat.setString(3, user.getPassword());
            stat.setString(4, user.getRole().name());
            stat.setBoolean(5, user.isBlocked());
            stat.setString(6, user.getFirstName());
            stat.setString(7, user.getLastName());
            stat.setString(8, user.getContact());
            stat.setInt(9, id);

            int checkUpdate = stat.executeUpdate();

            return checkUpdate != 0;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        try (PreparedStatement stat = dataSource.getConnection().prepareStatement(REMOVE_USER)) {
            stat.setInt(1, id);

            int checkUpdate = stat.executeUpdate();

            return checkUpdate != 0;
        }
    }

    private User selectUserQuery(PreparedStatement stat) throws SQLException {
        User foundUser = null;
        try (ResultSet rs = stat.executeQuery()) {
            while (rs.next()) {
                foundUser = new User(rs.getString("email"),
                        rs.getString("nick_name"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role")),
                        rs.getBoolean("block"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("contact"));
                foundUser.setId(rs.getInt("id"));
            }
        }
        return foundUser;
    }

    private Iterable<User> selectUsersQuery(PreparedStatement stat) throws SQLException {
        List<User> list = new LinkedList<>();
        try (ResultSet rs = stat.executeQuery()) {
            while (rs.next()) {
                User foundUser = new User(rs.getString("email"),
                        rs.getString("nick_name"),
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role")),
                        rs.getBoolean("block"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("contact"));
                foundUser.setId(rs.getInt("id"));
                list.add(foundUser);
            }
        }
        return list;
    }
}
