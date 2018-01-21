package com.epam.rd.november2017.vlasenko.filter;

import com.epam.rd.november2017.vlasenko.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/account")
public class AccountFilter extends BaseFilter {
    private static final String REDIRECT = "welcome.jsp";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);

        try {
            User user = (User) session.getAttribute("user");
            //each user has access to this page. So it is enough just to check if user is blocked.
            if (!user.isBlocked()) {
                chain.doFilter(request, response);
            }
        } catch (NullPointerException e) {
            response.sendRedirect(REDIRECT);
        }
    }
}