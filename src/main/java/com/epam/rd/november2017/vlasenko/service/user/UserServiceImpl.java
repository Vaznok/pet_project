package com.epam.rd.november2017.vlasenko.service.user;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.UserDao;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.UserDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.entity.User;

import java.sql.SQLException;
import java.util.List;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.TRANSACTION;

public class UserServiceImpl implements UserService<User, Integer> {
    private TransactionHandler transaction = TRANSACTION;
    private UserDao userDao = new UserDaoImpl(transaction);

    @Override
    public List<User> findLibrarians() throws SQLException {
        return (List<User>) transaction.doInTransaction(() -> userDao.findByRole(User.Role.LIBRARIAN));
    }

    @Override
    public boolean deleteLibrarian(Integer id) throws SQLException {
        return transaction.doInTransaction(() -> userDao.delete(id));
    }

    @Override
    public List<User> findBlocked() throws SQLException {
        return (List<User>) transaction.doInTransaction(() -> userDao.findByBlock(true));
    }

    @Override
    public List<User> findNoBlocked() throws SQLException {
        return (List<User>) transaction.doInTransaction(() -> userDao.findByBlock(false));
    }

    @Override
    public boolean unBlockUser(Integer userId) throws SQLException {
        return transaction.doInTransaction(() -> {
            User foundUser = userDao.find(userId);
            foundUser.setBlocked(false);
            return userDao.update(userId, foundUser);
        });
    }

    @Override
    public boolean blockUser(Integer userId) throws SQLException {
        return transaction.doInTransaction(() -> {
            User foundUser = userDao.find(userId);
            foundUser.setBlocked(true);
            return userDao.update(userId, foundUser);
        });
    }
}
