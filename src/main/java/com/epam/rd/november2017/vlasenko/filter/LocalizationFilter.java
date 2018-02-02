package com.epam.rd.november2017.vlasenko.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.epam.rd.november2017.vlasenko.config.GlobalConfig.LOCALE_NAME;
import static java.util.Objects.nonNull;

@WebFilter("/*")
public class LocalizationFilter extends BaseFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest landRequest = new HttpServletRequestWrapper(request) {
            @Override
            public Locale getLocale() {
                Cookie[] cookies = request.getCookies();
                String localeStr = null;
                if (nonNull(cookies)) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals(LOCALE_NAME)) {
                            localeStr = cookie.getValue();
                        }
                    }
                }
                if(localeStr == null) {
                    return super.getLocale();
                }
                return new Locale(localeStr);
            }
        };
        //I have decided to store only language without country
        String language = landRequest.getLocale().toString().split("_")[0];
        response.addCookie(new Cookie("locale", language));
        chain.doFilter(landRequest, response);
    }
}
