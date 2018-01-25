package com.epam.rd.november2017.vlasenko.service.registration;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.UserDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionBody;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.User;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.SQLException;

public class RegistrationService implements Registration<User>{
    private static final int PASSWORD_LEN_MIN = 6;
    private static final int PASSWORD_LEN_MAX = 18;

    private TransactionHandler transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private UserDaoImpl userDao = new UserDaoImpl(transaction);

    @Override
    public String validate(User user) throws SQLException {
        String result = null;
        String email = user.getEmail();
        String password = user.getPassword();
        String nickName = user.getNickName();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String contact = user.getContact();

        if (email.isEmpty() || password.isEmpty() || nickName.isEmpty()) {
            result = "Email, password, and nick-name fields must be filled!";
        } else if (!EmailValidator.getInstance().isValid(email)) {
            result = "Please, use real email!";
        } else if (transaction.doInTransaction(() -> userDao.findUserByEmail(email)) != null){

        } else if (password.length() < PASSWORD_LEN_MIN || password.length() > PASSWORD_LEN_MAX) {
            result = String.format("Password must consist of at least %d and maximum %d symbols!", PASSWORD_LEN_MIN, PASSWORD_LEN_MAX);
        } else {
            result = null;
        }
        return result;
    }

    @Override
    public void createUser(User user) throws SQLException, NoSuchEntityException {
        transaction.doInTransaction(() -> {
            userDao.create(user);
            return null;
        });
    }
}
