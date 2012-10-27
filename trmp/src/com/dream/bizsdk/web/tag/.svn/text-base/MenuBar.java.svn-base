/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-3-27
 * Time: 10:42:45
 */
public class MenuBar extends BaseTag {
    private int x;
    private int y;
    private String height = "25";
    private String width = "100%";
    private String bgColor = "#4682B4";

    public String getX() {
        return new Integer(x).toString();
    }

    public void setX(String x) {
        try {
            this.x = Integer.valueOf(x).intValue();
        } catch (Throwable e) {

        }
    }

    public String getY() {
        return new Integer(y).toString();
    }

    public void setY(String y) {
        try {
            this.y = Integer.valueOf(y).intValue();
        } catch (Throwable e) {

        }
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }


    public int doStartTag() throws JspException {
        JspWriter w = pc.getOut();
        try {
            w.print("<table width=\"" + width + "\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" ");
            w.println("style=\"border : 1px solid #FFFFFF;background-color:" + bgColor + "\">");
            w.println("  <tr> ");
            w.println("    <td width=\"100%\" height=\"" + height + "\">&nbsp;</td>");
            w.println("  </tr>");
            w.println("</table>");
        } catch (IOException ioe) {
            throw new JspException("IOError", ioe);
        }
        return 0;
    }

    public void release() {
        this.x = 0;
        this.y = 0;
        this.height = "25";
        this.width = "100%";
    }
}
