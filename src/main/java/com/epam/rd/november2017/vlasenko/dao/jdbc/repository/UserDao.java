package com.epam.rd.november2017.vlasenko.dao.jdbc.repository;

import com.epam.rd.november2017.vlasenko.entity.User;

import java.sql.SQLException;

public interface UserDao extends CrudDao<User, Integer>{

    User findAuthorizedUser(String email, String password) throws SQLException;

    User findUserByEmail(String email) throws SQLException;

    User findUserByNickName (String nickName)  throws SQLException;
}
