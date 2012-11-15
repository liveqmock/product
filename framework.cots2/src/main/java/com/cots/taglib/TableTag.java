/**
 *all rights reserved,@copyright 2003
 */
package com.cots.taglib;

import com.cots.exp.freeexp.Parser;
//import com.cots.dao.Page;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-17
 * Time: 10:21:39
 * Version: 1.0
 */
public final class TableTag extends BaseTag {

    private String width;

    private String cellspacing;

    private String cellpadding;

    private String border;

    private Object valObject;

    private String oddClass ="odd";

    private String evenClass="even";

    private String headClass="head";

    private String multiPage="false";

    //loop variable;
    private String var;

    protected ArrayList columns = new ArrayList();

    private Selector selector;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getCellspacing() {
        return cellspacing;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public String getCellpadding() {
        return cellpadding;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border){
        this.border = border;
    }

    public String getOddClass() {
        return oddClass;
    }

    public void setOddClass(String oddClass) {
        this.oddClass = oddClass;
    }

    public String getEvenClass() {
        return evenClass;
    }

    public void setEvenClass(String evenClass) {
        this.evenClass = evenClass;
    }

    public String getHeadClass() {
        return headClass;
    }

    public void setHeadClass(String headClass) {
        this.headClass = headClass;
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public String getMultiPage() {
        return multiPage;
    }

    public void setMultiPage(String multiPage) {
        this.multiPage = multiPage;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public int doStartTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        valObject = pageContext.findAttribute(id);
        if(!(valObject instanceof List)){
            Logger.getLogger(TableTag.class.getName()).warn("Object by id \""+id+"\" is null or not a java.util.List object");
            valObject = new ArrayList();
        }
        try {
            writer.print("<table ");
            if(border!=null){
                writer.print("border=\"");
                writer.print(border);
                writer.print("\" ");
            }
            if(width!=null){
                writer.print("width=\"");
                writer.print(width);
                writer.print("\" ");
            }
            if(cellspacing!=null){
                writer.print("cellspacing=\"");
                writer.print(cellspacing);
                writer.print("\" ");
            }
            if(cellpadding!=null){
                writer.print("cellpadding=\"");
                writer.print(cellpadding);
                writer.print("\" ");
            }
            writeStyle(writer);
            writeLang(writer);

            writer.write(">");
        } catch (IOException e) {
            throw new JspException(e);
        }
        return Tag.EVAL_BODY_INCLUDE;
    }

    /**
     *
     * @return
     * @throws JspException
     */
    public int doEndTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        Column col = null;
        Object o = null;
        try {
            List list = (List)valObject;
            int count = columns.size();

            writer.print("<tr");
            if(headClass!=null){
                writer.print(" class=\"");
                writer.print(headClass);
                writer.print("\"");
            }
            writer.print(">");

            //if selector is not null;
            if(selector!=null){
                writer.write("<td>");
                writer.write("&nbsp;");
                writer.print("</td>");
            }

            for (int i = 0; i < count; i++) {
                //o = list.get(i);
                col = (Column) columns.get(i);
                writeColumn(writer,col);
                writer.print(col.getTitle());
                writer.print("</td>");
            }
            writer.println("</tr>");


            int size = list.size();
            if(size>0){
                for (int i=0;i<size;i++){
                    o = list.get(i);

                    //set the current object into the pageContext;
                    pageContext.setAttribute(var,o);
                    writer.print("<tr class=\"");
                    if(i%2 == 0){
                        writer.print(evenClass);
                    }else{
                        writer.print(oddClass);
                    }
                    writer.print("\"");
                    writer.print(">");

                    //writer a selector;
                    if(selector!=null){
                        writeSelector(writer,i,o);
                    }

                    for (int j = 0; j < count; j++) {
                        col = (Column) columns.get(j);
                        writeColumn(writer,col);
                        writer.print(col.getValue(o,i));
                        writer.print("</td>");
                    }
                    writer.println("</tr>");
                }
            }else{
                writer.print("<tr><td colspan=\""+(columns.size()+(selector!=null?1:0))+
                        "\" align=\"center\"><font size=\"4\"><i>ÎÞ¼ÇÂ¼</i></font></td></tr>");
            }
            writer.print("</table>");
        } catch (IOException e) {
            throw new JspException(e);
        } catch (IllegalAccessException e) {
            throw new JspException("no access to \""+col.getProperty()+"\" of class \""+o.getClass()+"\"");
        } catch (NoSuchMethodException e) {
            throw new JspException("no property to \""+e.getMessage()+"\" of class \""+o.getClass()+"\"");
        } catch (InvocationTargetException e) {
            throw new JspException("failed to get value of \""+col.getProperty()+"\" of class \""
                    +o.getClass()+"\"");
        } finally{
            columns.clear();
            selector = null;
        }
        return Tag.EVAL_PAGE;
    }

    /**
     * @param col
     */
    public void addColumn(Column col) {
        columns.add(col);
    }

    private void writeColumn(JspWriter writer,Column col)
            throws IOException{
        writer.print("<td");
        String width = col.getWidth();
        if(width!=null){
            writer.print(" width=\"");
            writer.print(width);
            writer.print("\"");
        }
        String style = col.getStyle();
        if(style!=null){
            writer.print(" style=\"");
            writer.print(style);
            writer.print("\"");
        }
        String align = col.getAlign();
        if(align!=null){
            writer.print(" align=\"");
            writer.print(align);
            writer.print("\"");
        }
        String valign = col.getValign();
        if(valign!=null){
            writer.print(" valign=\"");
            writer.print(valign);
            writer.print("\"");
        }
        String bgcolor = col.getBgcolor();
        if(bgcolor!=null){
            writer.print(" bgcolor=\"");
            writer.print(bgcolor);
            writer.print("\"");
        }
        writer.print(">");
    }

    private void writeSelector(JspWriter writer,int index,Object o) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        writer.print("<td");
        String width = selector.getWidth();
        if(width!=null){
            writer.print(" width=\"");
            writer.print(width);
            writer.print("\"");
        }
        String style = selector.getStyle();
        if(style!=null){
            writer.print(" style=\"");
            writer.print(style);
            writer.print("\"");
        }
        String align = selector.getAlign();
        if(align!=null){
            writer.print(" align=\"");
            writer.print(align);
            writer.print("\"");
        }
        String valign = selector.getValign();
        if(valign!=null){
            writer.print(" valign=\"");
            writer.print(valign);
            writer.print("\"");
        }
        String bgcolor = selector.getBgcolor();
        if(bgcolor!=null){
            writer.print(" bgcolor=\"");
            writer.print(bgcolor);
            writer.print("\"");
        }
        writer.print(">");
        writer.print("<input type=\"");
        writer.print(selector.getType());
        writer.print("\" name=\"");
        writer.print(Parser.getValue(selector.getName(),null,o,index));
        writer.print("\" value=\"");
        Object v = Parser.getValue(selector.getValue(),null,o,index);
        writer.print(v);
        writer.print("\"");

        String onclick = selector.getOnclick();
        if(onclick!=null){
            writer.print(" onclick=\"");
            writer.print(onclick);
            writer.print("\"");
        }

        String ondblclick = selector.getOndblclick();
        if(ondblclick!=null){
            writer.print(" ondblclick=\"");
            writer.print(ondblclick);
            writer.print("\"");
        }

        String onfocus = selector.getOnfocus();
        if(onfocus!=null){
            writer.print(" onfocus=\"");
            writer.print(onfocus);
            writer.print("\"");
        }

        String onblur = selector.getOnblur();
        if(onblur!=null){
            writer.print(" onblur=\"");
            writer.print(onblur);
            writer.print("\"");
        }

        Set checkerSet = selector.getCheckerSet();
        if(checkerSet!=null && checkerSet.contains(v)){
            writer.print(" checked");
        }
        writer.print(">");
        writer.print("</td>");
    }

//    private void writePagination(JspWriter out,int cols,Page page){
//
//    }
}
