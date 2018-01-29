package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.entity.Book;
import com.epam.rd.november2017.vlasenko.service.book.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    private static final String REQ_ATTR_BOOKS = "books";
    private static final String CATALOG_JSP = "catalog.jsp";

    private BookServiceImpl bookService = new BookServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(CatalogServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            List<Book> bookList = (List<Book>) bookService.findAll();
            req.setAttribute(REQ_ATTR_BOOKS, bookList);
            RequestDispatcher dispatcher = req.getRequestDispatcher(CATALOG_JSP);
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            logger.error("Book catalog doesn't work!", e);
            resp.sendError(500);
        }
    }
}
