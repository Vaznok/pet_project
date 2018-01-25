package com.epam.rd.november2017.vlasenko.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class PrintTag extends TagSupport {

    private String result;

    @Override
    public int doStartTag() throws JspException {
        try {
            if (result.length() > 0) {
                pageContext.getOut().write(result);
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public void release() {
        super.release();
        this.result = null;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
