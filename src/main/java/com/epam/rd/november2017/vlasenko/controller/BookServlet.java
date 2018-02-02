package com.epam.rd.november2017.vlasenko.controller;

import com.epam.rd.november2017.vlasenko.entity.Book;
import com.epam.rd.november2017.vlasenko.entity.User;
import com.epam.rd.november2017.vlasenko.service.book.BookServiceImpl;
import com.epam.rd.november2017.vlasenko.service.order.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.SESSION_USER_ATTRIBUTE_NAME;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private static final String PARAM_ID = "id";
    private static final String REQ_ATTR_BOOK = "book";
    private static final String BOOK_JSP = "book.jsp";
    private static final String REQ_ATTR_NO_BOOK = "notEnoughBooks";

    private BookServiceImpl bookService = new BookServiceImpl();
    private OrderServiceImpl orderService = new OrderServiceImpl();

    private static Logger logger = LoggerFactory.getLogger(BookServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter(PARAM_ID);
        if (idStr != null) {
            try {
                Integer id = Integer.valueOf(idStr);
                Book book = bookService.find(id);
                if (book != null) {
                    req.setAttribute(REQ_ATTR_BOOK, book);
                    req.getRequestDispatcher(BOOK_JSP).forward(req, resp);
                } else {
                    logger.debug("There is no record in the database by this parametr.");
                    resp.sendError(404);
                }
            } catch (NumberFormatException e) {
                logger.debug("Incorrect URL parameter.", e);
                resp.sendError(404);
            } catch (SQLException e) {
                logger.error("Book detail page doesn't work!", e);
                resp.sendError(500);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookCountStr = req.getParameter("bookCount");
        String bookIdStr = req.getParameter("bookId");
        Integer userId = ((User) req.getSession().getAttribute(SESSION_USER_ATTRIBUTE_NAME)).getId();

        try {
            Integer bookId = Integer.valueOf(bookIdStr);
            Integer bookCount = Integer.valueOf(bookCountStr);

            if(orderService.createOrder(userId, bookId, bookCount)) {
                req.setAttribute(REQ_ATTR_NO_BOOK, "We have gotten your order! Our manager will contact you soon!");
            } else {
                req.setAttribute(REQ_ATTR_NO_BOOK, "Sorry we don't have enough book for you. Please, choose another count!");
            }
            //req.getRequestDispatcher("book?id=" + bookId).forward(req, resp);
            resp.sendRedirect("catalog");
        } catch (NumberFormatException e) {
            logger.warn("Incorrect parameters in order creation!", e);
        } catch (SQLException e) {
            logger.error("Order creation error!", e);
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String bookIdStr = reader.readLine();
        try {
            if (bookIdStr != null) {
                bookIdStr = bookIdStr.split("=")[1];
            } else {
                throw new JspException();
            }
            bookService.deleteBook(Integer.valueOf(bookIdStr));
            PrintWriter writer = resp.getWriter();
            writer.print("redirect page");
        } catch (SQLException e) {
            logger.warn("Unsuccessful book deletion!", e);
            resp.sendError(500);
        } catch (NumberFormatException | JspException e) {
            logger.warn("Incorrect parsing!", e);
            resp.sendError(500);
        }
    }
}
