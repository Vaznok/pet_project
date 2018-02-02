package com.epam.rd.november2017.vlasenko.service.registration;

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

public class RegistrationServiceImpl implements RegistrationService<User> {
    private static final Pattern passwordPattern = Pattern.compile("^.{6,18}$");
    private static final Pattern nickNamePattern = Pattern.compile("^[A-Z][a-z0-9-]{3,14}$");
    private static final Pattern namePattern = Pattern.compile("^[A-Z][a-z-]{3,14}$");
    private static final Pattern contactPattern = Pattern.compile("^.[a-zA-Z-]{8,150}$");

    private TransactionHandler transaction = TRANSACTION;
    private UserDao userDao = new UserDaoImpl(transaction);

    private boolean validatePassword(String password){
        Matcher matcher = passwordPattern.matcher(password);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    private boolean validateNickName(String userName){
        if(userName.isEmpty()) {
            return true;
        }
        Matcher matcher = nickNamePattern.matcher(userName);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    private boolean validateName(String name){
        if(name.isEmpty()) {
            return true;
        }
        Matcher matcher = namePattern.matcher(name);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    private boolean validateContact(String name){
        if(name.isEmpty()) {
            return true;
        }
        Matcher matcher = contactPattern.matcher(name);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    @Override
    public String validate(User user, Locale locale) throws SQLException {
        String result = null;
        String email = user.getEmail();
        String password = user.getPassword();
        String nickName = user.getNickName();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String contact = user.getContact();
        ResourceBundle messages = ResourceBundle.getBundle("i18n.messages", locale);

        if (email.isEmpty() || password.isEmpty() || nickName.isEmpty()) {
            result = messages.getString("no-obligatory-fields-reg.warn");
        } else if (!EmailValidator.getInstance().isValid(email)) {
            result = messages.getString("incorrect-email.warn");
        } else if (transaction.doInTransaction(() -> userDao.findUserByEmail(email)) != null){
            result = messages.getString("account-exist-by-email.msg");
        } else if (!validateNickName(nickName)) {
            result = messages.getString("incorrect-nickname.warn");
        } else if (transaction.doInTransaction(() -> userDao.findUserByNickName(nickName)) != null) {
            result = messages.getString("account-exist-by-nickname.msg");
        } else if (!validatePassword(password)){
            result = messages.getString("incorrect-password.warn");
        } else if (!validateName(firstName)) {
            result = messages.getString("incorrect-firstname.warn");
        } else if (!validateName(lastName)) {
            result = messages.getString("incorrect-lastname.warn");
        } else if (!validateContact(contact)) {
            result = messages.getString("incorrect-contact.warn");
        }
        return result;
    }

    @Override
    public void createUser(User user) throws SQLException {
        transaction.doInTransaction(() -> {
            userDao.create(user);
            return null;
        });
    }
}
