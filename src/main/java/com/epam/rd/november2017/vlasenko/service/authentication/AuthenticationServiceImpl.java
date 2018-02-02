package com.epam.rd.november2017.vlasenko.service.authentication;

import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.UserDao;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.UserDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.entity.User;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.TRANSACTION;

public class AuthenticationServiceImpl implements AuthenticationService<User> {
    private static final Pattern passwordPattern = Pattern.compile("^.{6,18}$");

    private TransactionHandler transaction = TRANSACTION;
    private UserDao userDao = new UserDaoImpl(transaction);

    private boolean validatePassword(String password){
        Matcher matcher = passwordPattern.matcher(password);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    //return value 'null' means that validateOrderConfirmation is successful otherwise return a description of the inconsistencies
    @Override
    public String validateSyntax(String email, String password, Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", locale);
        String result = null;
        if (email.isEmpty() || password.isEmpty()) {
            result = messages.getString("no-obligatory-fields-login.warn");
        } else if (!EmailValidator.getInstance().isValid(email)) {
            result = messages.getString("incorrect-email.warn");
        } else if (!validatePassword(password)) {
            result = messages.getString("incorrect-password.warn");
        }
        return result;
    }

    @Override
    public User getUser(String email, String password) throws SQLException {
        return transaction.doInTransaction(() -> userDao.findAuthorizedUser(email, password));
    }
}
