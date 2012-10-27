/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.web.tag;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysVar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-3-30
 * Time: 8:46:19
 */
public class DictSelect extends BaseTag {
    protected String name;
    protected String tableName;
    protected String colName;
    protected String withNull;
    protected String value;
    protected String multiple;
    protected String size;
    protected String nullLabel = "Select one...";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColName() {
        return this.colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getWithNull() {
        return withNull;
    }

    public void setWithNull(String withNull) {
        this.withNull = withNull;
    }

    public String getNullLabel() {
        return this.nullLabel;
    }

    public void setNullLabel(String nullLabel) {
        this.nullLabel = nullLabel;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMultiple() {
        return this.multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int doStartTag() throws JspException {
        String rs = tableName + "/" + colName;
        JspWriter w = pc.getOut();
        ServletContext sc = pc.getServletContext();
        BizData bdc = (BizData) sc.getAttribute(SysVar.APP_BDC);
        int rows = bdc.getTableRowsCount(rs);
        try {
            w.print("<select name=\"" + name + "\"");
            if (multiple != null && multiple.compareToIgnoreCase("true") == 0) {
                w.print(" multiple ");
                if (size != null) {
                    w.print(" size=\" ");
                    w.print(size);
                    w.print("\"");
                }
            }
            w.println(">");
            if (rows > 0) {
                //write null value option;
                if (withNull != null && withNull.compareToIgnoreCase("true") == 0) {
                    w.print("<option vlaue=\"\" >");
                    w.print(nullLabel);
                    w.println("</option>");
                }

                //wirte normal options in the dict;
                String[] rowIDs = bdc.getRowIDs(rs);
                for (int i = 0; i < rows; i++) {
                    w.print("<option vlaue=\">");
                    w.print(rowIDs[i]);
                    w.print("\"");
                    if (value != null && rowIDs[i].compareTo(value) == 0) {
                        w.print(" selected ");
                    }
                    w.println(">");
                    w.print(bdc.getString(tableName, colName, rowIDs[i]));
                    w.println("</option>");
                }
            }
            w.println("</select>");
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }
        return 0;
    }

    public int doEndTag() {
        this.name = null;
        tableName = null;
        colName = null;
        withNull = null;
        value = null;
        multiple = null;
        size = null;
        nullLabel = "Select one...";
        return 0;
    }

}
