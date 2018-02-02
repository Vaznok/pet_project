package com.epam.rd.november2017.vlasenko.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.LOCALE_NAME;

@WebServlet(name = "changeLocaleServer", urlPatterns = "/locale")
public class ChangeLocaleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String locale = req.getParameter(LOCALE_NAME);
        resp.addCookie(new Cookie(LOCALE_NAME, locale));
    }
}
