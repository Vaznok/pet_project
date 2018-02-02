package com.epam.rd.november2017.vlasenko.filter;

import com.epam.rd.november2017.vlasenko.controller.LibrarianServlet;
import com.epam.rd.november2017.vlasenko.entity.User;
import com.epam.rd.november2017.vlasenko.service.authentication.AuthenticationServiceImpl;
import com.epam.rd.november2017.vlasenko.service.encryption.EncryptionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.LOCALE_NAME;
import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.SESSION_USER_ATTRIBUTE_NAME;
import static java.util.Objects.nonNull;

@WebFilter("/*")
public class LoginFilter extends BaseFilter{
    private AuthenticationServiceImpl authentication = new AuthenticationServiceImpl();
    private EncryptionServiceImpl encryption = new EncryptionServiceImpl();

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if (nonNull(session)) {
            if(session.getAttribute(LOCALE_NAME) != null) {
                chain.doFilter(request, response);
                return;
            }
        }
        Cookie[] cookies = request.getCookies();
        if (nonNull(cookies)) {
            Integer emailIndex = null;
            Integer passwordIndex = null;
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("email")) {
                    emailIndex = i;
                }
                if (cookies[i].getName().equals("password")) {
                    passwordIndex = i;
                }
            }
            if (nonNull(emailIndex) && nonNull(passwordIndex)) {
                try {
                    String decryptedEmail = encryption.decrypt(cookies[emailIndex].getValue());
                    String decryptedPassword = encryption.decrypt(cookies[passwordIndex].getValue());
                    User user = authentication.getUser(decryptedEmail, decryptedPassword);
                    request.getSession().setAttribute(SESSION_USER_ATTRIBUTE_NAME, user);

                    if (user == null) {
                        cookies[emailIndex].setMaxAge(0);
                        response.addCookie(cookies[emailIndex]);
                        cookies[passwordIndex].setMaxAge(0);
                        response.addCookie(cookies[passwordIndex]);
                    }

                } catch (SQLException e) {
                    response.setStatus(500);
                }
            }
        }
        chain.doFilter(request, response);
    }
}