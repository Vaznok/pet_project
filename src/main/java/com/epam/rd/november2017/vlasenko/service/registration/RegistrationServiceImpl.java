package com.epam.rd.november2017.vlasenko.service.registration;

import com.epam.rd.november2017.vlasenko.dao.jdbc.datasource.DataSourceForTest;
import com.epam.rd.november2017.vlasenko.dao.jdbc.repository.impl.UserDaoImpl;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandler;
import com.epam.rd.november2017.vlasenko.dao.jdbc.transaction.TransactionHandlerImpl;
import com.epam.rd.november2017.vlasenko.entity.User;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService<User> {
    private static final Pattern passwordPattern = Pattern.compile("^{6,18}$");
    private static final Pattern nickNamePattern = Pattern.compile("^[A-Z][a-z0-9-]{3,14}$");
    private static final Pattern namePattern = Pattern.compile("^[A-Z][a-z-]{3,14}$");
    private static final Pattern contactPattern = Pattern.compile("^[a-zA-Z-]{8,150}$");

    private TransactionHandler transaction = new TransactionHandlerImpl(new DataSourceForTest());
    private UserDaoImpl userDao = new UserDaoImpl(transaction);

    private boolean validatePassword(String password){
        Matcher matcher = passwordPattern.matcher(password);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    private boolean validateNickName(String userName){
        Matcher matcher = nickNamePattern.matcher(userName);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    private boolean validateName(String name){
        Matcher matcher = namePattern.matcher(name);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    private boolean validateContact(String name){

        Matcher matcher = contactPattern.matcher(name);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

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
            result = "There is an account for such email!";
        } else if (validateNickName(nickName)) {
            result = "Nickname must consist of at least 3 and maximum 14 letters and numbers. First letter must be capital.";
        } else if (transaction.doInTransaction(() -> userDao.findUserByNickName(nickName)) != null) {
            result = "Such nickname is used. Please, choose another one.";
        } else if (!validatePassword(password)){
            result = "Password, must consist of minimum 6 and maximum 18 symbols!";
        } else if (!validateName(firstName)) {
            result = "First name must consist of at least 3 and maximum 14 letters. First letter must be capital.";
        } else if (!validateName(lastName)) {
            result = "Last name must consist of at least 3 and maximum 14 letters. First letter must be capital.";
        } else if (!validateContact(contact)) {
            result = "Contact must consist of at least 10 and maximum 150 letters and numbers.";
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
