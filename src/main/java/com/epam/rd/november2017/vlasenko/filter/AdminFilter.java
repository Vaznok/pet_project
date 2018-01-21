package com.epam.rd.november2017.vlasenko.filter;

import com.epam.rd.november2017.vlasenko.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.rd.november2017.vlasenko.entity.User.Role.ADMINISTRATOR;

@WebFilter("/admin")
public class AdminFilter extends BaseFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        try {
            User user = (User) session.getAttribute("user");

            if(user.getRole().equals(ADMINISTRATOR.name()) && !user.isBlocked()) {
                chain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NullPointerException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
