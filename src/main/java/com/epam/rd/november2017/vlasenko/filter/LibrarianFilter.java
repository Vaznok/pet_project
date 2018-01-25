package com.epam.rd.november2017.vlasenko.filter;

import com.epam.rd.november2017.vlasenko.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.rd.november2017.vlasenko.entity.User.Role.ADMINISTRATOR;
import static com.epam.rd.november2017.vlasenko.entity.User.Role.LIBRARIAN;

@WebFilter(servletNames = "LibrarianServlet")
public class LibrarianFilter extends BaseFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        try {
            User user = (User) session.getAttribute("user");
            String userRole = user.getRole();
            if((userRole.equals(LIBRARIAN.name()) || userRole.equals(ADMINISTRATOR.name())) && !user.isBlocked()) {
                chain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NullPointerException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
