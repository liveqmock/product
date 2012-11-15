/**
 *all rights reserved,@copyright 2003
 */
package com.cots.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * Description:
 *  Column tag is a child tag of Table Tag;
 *
 * User: chuguanghua
 * Date: 2005-3-19
 * Time: 12:37:27
 * Version: 1.0
 */
public final class ColumnTag extends BodyTagSupport{
    //displayed in the head;
    protected String title;

    //the displayName of the field of the Java Object to be shown in <td>
    protected String property;

    //the align of the td;
    protected String align;

    //the width of the td>
    protected String width;

    //the valign of the <td>
    protected String valign;

    //the format for the data in the <td>
    protected String format;

    //the style of the <td>
    protected String style;

    //the bgcolor of the <td>
    protected String bgcolor;

    public String getProperty() {
        return property;
    }

    public void setProperty(String prop) {
        this.property = prop;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getValign() {
        return valign;
    }

    public void setValign(String valign) {
        this.valign = valign;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    /**
     * called by the jsp engine when the tag met;
     *
     * @return
     * @throws JspException
     */
    public int doStartTag() throws JspException {
        Tag parent = this.getParent();
        //the column tag must be child of a TableTag;
        if(parent instanceof TableTag){
            if(property!=null){
                Column col = new Column();

                col.setProperty(property);
                col.setTitle(title);
                col.setValign(valign);
                col.setAlign(align);
                col.setWidth(width);
                col.setFormat(format);

                TableTag table = (TableTag)parent;
                table.addColumn(col);
                return Tag.SKIP_BODY;
            }else{
                return BodyTag.EVAL_BODY_BUFFERED;
            }
        }else{
            return Tag.SKIP_BODY;
        }
    }

    /**
     * called by jsp engine when the tag is ended;
     *
     * @return Tag.EVAL_PAGE
     */
    public int doEndTag(){
        if(bodyContent!= null){
            Column col = new Column();

            col.setValue(bodyContent.getString());
            col.setFormat(format);
            col.setTitle(title);
            col.setValign(valign);
            col.setAlign(align);
            col.setWidth(width);

            TableTag table = (TableTag)getParent();
            table.addColumn(col);
        }
        return Tag.EVAL_PAGE;
    }
}