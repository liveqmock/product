package com.dream.bizsdk.web.tag;

import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;

/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 2004-3-22
 * Time: 19:37:49
 * To change this template use File | Settings | File Templates.
 */
public class BaseTag implements Tag {
    protected BodyContent bc = null;
    protected PageContext pc = null;
    protected Tag parent = null;

    public void setPageContext(PageContext pageContext) {
        pc = pageContext;
    }

    public void setParent(Tag tag) {
        parent = tag;
    }

    public Tag getParent() {
        return parent;
    }

    public int doStartTag() throws JspException {
        return 0;
    }

    public int doEndTag() throws JspException {
        bc = null;
        pc = null;
        parent = null;
        return 0;
    }

    public void release() {
    }
}
