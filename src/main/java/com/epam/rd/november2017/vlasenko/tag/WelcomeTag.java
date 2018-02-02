package com.epam.rd.november2017.vlasenko.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class WelcomeTag extends TagSupport {

    private String name;
    private String lang;

    @Override
    public int doStartTag() throws JspException {
        try {
            if (name.length() > 0) {
                pageContext.getOut().write(name);
            } else if (lang.equals("en")) {
                pageContext.getOut().write("guest");
            } else {
                pageContext.getOut().write("гость");
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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
