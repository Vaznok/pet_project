package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.UserDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.rd.november2017.vlasenko.entity.UserRole.REGISTERED_USER;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest {
    private TransactionHandlerImpl transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private UserDaoImpl sut = new UserDaoImpl(transaction);

    @BeforeEach
    public void truncateTableDb() throws SQLException, NoSuchEntityException {
        transaction.doInTransaction(() -> {
            try (Statement stat = transaction.getConnection().createStatement()) {
                stat.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
                stat.executeUpdate("TRUNCATE TABLE users");
                return null;
            }
        });
    }

    @Test
    public void createNewUser_FindCreatedUser() throws SQLException, NoSuchEntityException {
        User user = new User("grozniy.vasya@gmail.com", "Gordon", "qwerty", REGISTERED_USER);

        User foundUser = transaction.doInTransaction(() -> {
            sut.create(user);
            return sut.find(1);
        });

        assertEquals(user, foundUser);
    }

    @Test
    public void findUserByNotExistedId_NoSuchEntityExceptionThrown() throws SQLException {
        assertThrows(NoSuchEntityException.class, ()-> {
            transaction.doInTransaction(() -> sut.find(100));
        });
    }

    @Test
    public void createNewUsers_FindCreatedUsers() throws SQLException, NoSuchEntityException {
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
    public void findListOfUserWithNotExistedId_NoSuchEntityExceptionThrown() throws SQLException {
        List<User> usersList = new ArrayList<User>(){
            {
                add(new User("tepliy.dosya@mail.ru", "Dosyan", "12345", REGISTERED_USER));
                add(new User("grisha.the.best@yandex.ru", "Killer", "klinton", REGISTERED_USER));
                add(new User("grozniy.vasya@gmail.com", "Gordon", "qwerty", REGISTERED_USER));
            }
        };
        assertThrows(NoSuchEntityException.class, ()-> transaction.doInTransaction(() -> {
                sut.create(usersList);
                return sut.find(asList(1, 2, 100));
            })
        );
    }

    @Test
    public void updateUserById_FindUpdatedUser() throws SQLException, NoSuchEntityException {
        User createUser = new User("grozniy.vasya@gmail.com", "Gordon", "qwerty", REGISTERED_USER);
        User updateUser = new User("grozniy.vasya@gmail.com", "Gordon", "superpassword", REGISTERED_USER);

        User foundUser = transaction.doInTransaction(() -> {
            sut.create(createUser);
            sut.update(1, updateUser);
            return sut.find(1);
        });

        assertEquals(updateUser, foundUser);
    }

    @Test
    public void updateUserByNotExistedId_NoSuchEntityExceptionThrown() throws SQLException {
        User updateUser = new User("grozniy.vasya@gmail.com", "Gordon", "superpassword", REGISTERED_USER);

        assertThrows(NoSuchEntityException.class, ()-> transaction.doInTransaction(() -> {
                sut.update(100, updateUser);
                return null;
            })
        );
    }

   /* @Test
    public void deleteUserById_FindDeletedUserThrowNoSuchEntityException() throws SQLException {
        User user = new User("grozniy.vasya@gmail.com", "Gordon", "superpassword", REGISTERED_USER);

        assertThrows(NoSuchEntityException.class, () -> transaction.doInTransaction(() -> {
                sut.create(user);
                sut.delete(1);
                return sut.find(1);
            })
        );
    }*/

    @Test
    public void deleteUserByNotExistedId_NoSuchEntityExceptionThrown() throws SQLException {
        assertThrows(NoSuchEntityException.class, ()-> transaction.doInTransaction(() -> {
                sut.delete(100);
                return null;
            })
        );
    }
}

