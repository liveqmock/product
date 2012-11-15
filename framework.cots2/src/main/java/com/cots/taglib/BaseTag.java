/**
 *all rights reserved,@copyright 2003
 */
package com.cots.taglib;

import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-11
 * Time: 8:59:53
 * Version: 1.0
 */
public class BaseTag implements Tag{

    protected BodyContent bodyContent;
    protected PageContext pageContext;
    protected Tag parent;

    protected Logger logger = Logger.getLogger(BaseTag.class);

    //id of this tag, this is the attribute displayName of the corresponding Data Object;
    protected String id;
    //displayName of this attribute;
    protected String name;
    //style of this element;
    protected String style;
    //lang of this element;
    protected String lang;

    //on click event;
    protected String onclick;

    //on focus event;
    protected String onfocus;

    //on blur event;
    protected String onblur;

    //on double click;
    protected String ondblclick;

    protected String onchange;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public BodyContent getBodyContent() {
        return bodyContent;
    }

    public void setBodyContent(BodyContent bodyContent) {
        this.bodyContent = bodyContent;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnfocus() {
        return onfocus;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public String getOnblur() {
        return onblur;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public String getOndblclick() {
        return ondblclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
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
        return 0;
    }

    public void release() {
        id = null;
        name = null;
        style = null;
        lang = null;

        pageContext = null;
        bodyContent = null;
        parent = null;
    }

    protected void writeStyle(JspWriter writer) throws IOException{
        if( style != null  && style.length()>0){
            writer.print("style=\"");
            writer.print(style);
            writer.print("\" ");
        }
    }

    protected void writeLang(JspWriter writer) throws IOException{
        if( lang != null  && lang.length()>0){
            writer.print("lang=\"");
            writer.print(lang);
            writer.print("\" ");
        }
    }

    protected void writeEvent(JspWriter writer) throws IOException{
        if( onclick != null  && onclick.length()>0){
            writer.print("onclick=\"");
            writer.print(onclick);
            writer.print("\" ");
        }
        if( ondblclick != null  && ondblclick.length()>0){
            writer.print("ondblclick=\"");
            writer.print(ondblclick);
            writer.print("\" ");
        }
        if( onchange != null  && onchange.length()>0){
            writer.print("onchange=\"");
            writer.print(onchange);
            writer.print("\" ");
        }

        if( onfocus != null  && onfocus.length()>0){
            writer.print("onfocus=\"");
            writer.print(onfocus);
            writer.print("\" ");
        }
        if( onblur != null  && onblur.length()>0){
            writer.print("onblur=\"");
            writer.print(onblur);
            writer.print("\" ");
        }
    }
}