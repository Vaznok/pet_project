package com.epam.rd.november2017.vlasenko.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class WelcomeTag extends TagSupport {

    private String name;

    @Override
    public int doStartTag() throws JspException {
        String prefix = "Welcome, ";
        try {
            if (name.length() > 0) {
                pageContext.getOut().write(prefix + name);
            } else {
                pageContext.getOut().write(prefix + "guest");
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public void release() {
        super.release();
        this.name = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
