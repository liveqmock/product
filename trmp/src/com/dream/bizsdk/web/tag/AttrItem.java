package com.dream.bizsdk.web.tag;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysVar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 2004-3-22
 * Time: 21:36:34
 */
public class AttrItem extends BaseTag {
    protected String name = null;
    protected String field = null;
    protected String row = null;
    protected String attr = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getRow() {
        return this.row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getAttr() {
        return this.attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    /**
     * @return
     * @throws javax.servlet.jsp.JspException
     */
    public int doStartTag() throws JspException {
        BizData data = (BizData) pc.getRequest().getAttribute(SysVar.REQ_DATA);
        JspWriter w = pc.getOut();
        try {
            if (name != null) {
                if (field != null) {
                    if (row != null) {
                        w.print(data.getAttr(name, field, row, attr).toString());
                    } else {
                        w.print(data.getString(name, field, attr).toString());
                    }
                } else if (row != null) {
                    w.print(data.getRowAttr(name, row, attr).toString());
                } else {
                    w.print(data.getAttr(name, attr).toString());
                }
            }
        } catch (IOException ioe) {
            throw new JspException(ioe);
        } catch (NumberFormatException nfe) {
            throw new JspException(nfe);
        }
        return 0;
    }

    public int doEndTag() throws JspException {
        name = null;
        field = null;
        row = null;
        attr = null;
        return 0;
    }

    public void release() {
    }
}
