package com.epam.rd.november2017.vlasenko.service.authentication;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.UserDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.User;
import com.epam.rd.november2017.vlasenko.service.encryption.EncryptionService;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.SQLException;

public class AuthenticationService implements Authentication<User> {
    private static final int PASSWORD_LEN_MIN = 6;
    private static final int PASSWORD_LEN_MAX = 18;

    private TransactionHandler transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private UserDaoImpl userDao = new UserDaoImpl(transaction);
    private EncryptionService encryptionService = new EncryptionService();

    //return value 'null' means that validation is successful otherwise return a description of the inconsistencies
    @Override
    public String validateSyntax(String email, String password) {
        String result = null;
        if (email.isEmpty() || password.isEmpty()) {
            result = "Please, fill email and password fields!";
        } else if (!EmailValidator.getInstance().isValid(email)) {
            result = "Please, use real email!";
        } else if (password.length() < PASSWORD_LEN_MIN || password.length() > PASSWORD_LEN_MAX) {
            result = String.format("Password must consist of at least %d and maximum %d symbols!", PASSWORD_LEN_MIN, PASSWORD_LEN_MAX);
        }
        return result;
    }

    @Override
    public User getUser(String email, String password) throws SQLException, NoSuchEntityException {
        return transaction.doInTransaction(() -> userDao.findAuthorizedUser(email, password));
    }
}
