/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.bean.display;

import org.w3c.dom.Element;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * User: chugh
 * Date: 2005-9-22
 * Time: 18:59:31
 */
public class File extends Input {
    protected String size;

    public File() {
        type="file";
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void init(Element ele) {
        this.displayName = ele.getAttribute("displayName");
        this.style = ele.getAttribute("style");
        this.size = ele.getAttribute("size");
    }

    public void write(JspWriter out) throws IOException {
        out.print("<input type=\"file\" displayName=\"");
        out.print(displayName);
        out.print("\" style=\"");
        out.print(style);
        out.print("\" size=\"");
        out.print(size);
        out.print("\">");
    }
}
