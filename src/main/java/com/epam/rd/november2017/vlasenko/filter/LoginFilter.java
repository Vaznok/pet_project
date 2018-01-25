package com.epam.rd.november2017.vlasenko.filter;

import com.epam.rd.november2017.vlasenko.dao.jdbc.exception.NoSuchEntityException;
import com.epam.rd.november2017.vlasenko.entity.User;
import com.epam.rd.november2017.vlasenko.service.authentication.AuthenticationService;
import com.epam.rd.november2017.vlasenko.service.encryption.EncryptionService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

import static java.util.Objects.nonNull;

@WebFilter("/*")
public class LoginFilter extends BaseFilter{
    private final static String SESSION_ATTRIBUTE_USER = "user";
    private static final String PAGE_ERROR = "error.jsp";

    private AuthenticationService authentication = new AuthenticationService();
    private EncryptionService encryption = new EncryptionService();

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        if(nonNull(session)) {
            Enumeration<String> attrNames = session.getAttributeNames();
            while (attrNames.hasMoreElements()) {
                String attrName = attrNames.nextElement();
                if (attrName.equals(SESSION_ATTRIBUTE_USER)) {
                    if(nonNull(session.getAttribute(SESSION_ATTRIBUTE_USER))) {
                        chain.doFilter(request, response);
                        return;
                    }
                }
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
                if(cookies[i].getName().equals("password")) {
                    passwordIndex = i;
                }
                if (nonNull(emailIndex) && nonNull(passwordIndex))
                    try {
                        String decryptedEmail = encryption.decrypt(cookies[emailIndex].getValue());
                        String decryptedPassword = encryption.decrypt(cookies[passwordIndex].getValue());
                        User user = authentication.getUser(decryptedEmail, decryptedPassword);
                        request.getSession().setAttribute(SESSION_ATTRIBUTE_USER, user);
                    } catch (SQLException e) {
                        response.setStatus(500);
                        response.sendRedirect(PAGE_ERROR);
                    } catch (NoSuchEntityException e) {
                        cookies[emailIndex].setMaxAge(0);
                        response.addCookie(cookies[emailIndex]);
                        cookies[passwordIndex].setMaxAge(0);
                        response.addCookie(cookies[passwordIndex]);
                    }
            }
        }
        chain.doFilter(request, response);
    }
}