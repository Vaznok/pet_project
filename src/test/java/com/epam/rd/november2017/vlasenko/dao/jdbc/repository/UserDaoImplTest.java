package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.UserDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.TRANSACTION_TEST;
import static com.epam.rd.november2017.vlasenko.entity.User.Role.REGISTERED_USER;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest {
    private TransactionHandler transaction = TRANSACTION_TEST;
    private UserDao sut = new UserDaoImpl(transaction);

    @BeforeEach
    public void truncateTableDb() throws SQLException {
        transaction.doInTransaction(() -> {
            try (Statement stat = transaction.getConnection().createStatement()) {
                stat.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
                stat.executeUpdate("TRUNCATE TABLE users");
                return null;
            }
        });
    }

    @Test
    public void createNewUser_FindCreatedUserById() throws SQLException {
        User user = new User("grozniy.vasya@gmail.com", "Gordon", "qwerty", REGISTERED_USER);

        User foundUser = transaction.doInTransaction(() -> {
            sut.create(user);
            return sut.find(1);
        });

        assertEquals(user, foundUser);
    }

    @Test
    public void createNewUser_FindCreatedUserByNickName() throws SQLException {
        User user = new User("grozniy.vasya@gmail.com", "Gordon", "qwerty", REGISTERED_USER);

        User foundUser = transaction.doInTransaction(() -> {
            sut.create(user);
            return sut.findUserByNickName("Gordon");
        });

        assertEquals(user, foundUser);
    }

    @Test
    public void createNewUser_FindCreatedUserByEmail() throws SQLException {
        User user = new User("grozniy.vasya@gmail.com", "Gordon", "qwerty", REGISTERED_USER);

        User foundUser = transaction.doInTransaction(() -> {
            sut.create(user);
            return sut.findUserByEmail("grozniy.vasya@gmail.com");
        });

        assertEquals(user, foundUser);
    }

    @Test
    public void createNewUsers_FindCreatedUsers() throws SQLException {
        List<User> usersList = new ArrayList<User>(){
            {
                add(new User("tepliy.dosya@mail.ru", "Dosyan", "12345", REGISTERED_USER));
                add(new User("grisha.the.best@yandex.ru", "Killer", "klinton", REGISTERED_USER));
                add(new User("grozniy.vasya@gmail.com", "Gordon", "qwerty", REGISTERED_USER));
            }
        };

        List<User> usersFromDb = transaction.doInTransaction(() -> {
            sut.create(usersList);
            return (List<User>) sut.find(Arrays.asList(1, 2, 3));
        });

        assertArrayEquals(usersList.toArray(), usersFromDb.toArray());
    }

    @Test
    public void findAuthorizedUser() throws SQLException {
        User user = new User("grozniy.vasya@gmail.com", "Gordon", "qwerty", REGISTERED_USER);

        User foundUser = transaction.doInTransaction(() -> {
            sut.create(user);
            return sut.findAuthorizedUser("grozniy.vasya@gmail.com", "qwerty");
        });

        assertEquals(user, foundUser);

    }

    @Test
    public void updateUserById_FindUpdatedUser() throws SQLException {
        User createUser = new User("grozniy.vasya@gmail.com", "Gordon", "qwerty", REGISTERED_USER);
        User updateUser = new User("grozniy.vasya@gmail.com", "Gordon", "superpassword", REGISTERED_USER);

        User foundUser = transaction.doInTransaction(() -> {
            sut.create(createUser);
            sut.update(1, updateUser);
            return sut.find(1);
        });

        assertEquals(updateUser, foundUser);
    }
}

