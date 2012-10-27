package com.dream.bizsdk.web.tag;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysVar;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;


/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 2004-3-22
 * Time: 19:37:29
 * To change this template use File | Settings | File Templates.
 */
public class DataItem extends BaseTag {
    protected String name;
    protected String field;
    protected String row;
    protected String col;
    protected String format;


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

    public String getCol() {
        return this.col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getForamt() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return
     * @throws JspException
     */
    public int doStartTag() throws JspException {
        BizData data = (BizData) pc.getRequest().getAttribute(SysVar.REQ_DATA);
        JspWriter w = pc.getOut();
        Object v = null;
        try {
            if (name != null) {
                if (field != null) {
                    if (row != null) {
                        v = data.get(name, field, row);
                    } else {
                        v = data.get(name, field, "0");
                    }
                } else if (row != null) {
                    if (col != null) {
                        int r = Integer.valueOf(row).intValue();
                        int c = Integer.valueOf(col).intValue();
                        v = data.get(name, r, c);
                    } else {
                        int r = Integer.valueOf(row).intValue();
                        v = data.get(name, r);
                    }
                } else {
                    v = data.get(name);
                }
            }
            w.print(getValueString(data, v, format));
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
        col = null;
        format = null;
        return 0;
    }

    public void release() {
    }

    /**
     * @param data
     * @param v
     * @param format
     * @return
     */
    private String getValueString(BizData data, Object v, String format) {
        String value = null;
        if (format == null) {
            value = data.getStringFromObject(v);
        } else {
            if (v instanceof java.lang.Number) {
                DecimalFormat nf = new DecimalFormat(format);
                value = nf.format(v);
            } else if (v instanceof java.util.Date) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                value = sdf.format(v);
            } else {
                value = data.getStringFromObject(v);
            }
        }
        return value;
    }
}
