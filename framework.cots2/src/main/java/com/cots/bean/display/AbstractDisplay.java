/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.bean.display;

import org.w3c.dom.Element;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.sql.SQLException;

import com.cots.bean.BeanProperty;
import com.cots.blc.BLContext;

/**
 * User: chugh
 * Date: 2005-9-22
 * Time: 18:52:40
 */
public abstract class AbstractDisplay implements Display{
    protected String style;
    protected String name;
    protected String disabled;
    protected String displayName;

    protected boolean isKey;

    protected String onfocus;
    protected String onblur;
    protected String onselect;
    protected String onchange;

    public String getSytle() {
        return style;
    }

    public void setSytle(String sytle) {
        this.style = sytle;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * initialize this Object from an element;
     *
     * @param ele
     */
    public void init(Element ele){
        String aString = ele.getAttribute("style");
        if(aString!=null && aString.length()>0){
            style = aString;
        }
        aString = ele.getAttribute("name");
        if(aString!=null && aString.length()>0){
            displayName = aString;
        }
        aString = ele.getAttribute("disabled");
        if(aString!=null && aString.length()>0){
            disabled = aString;
        }
        aString = ele.getAttribute("onfocus");
        if(aString!=null && aString.length()>0){
            onfocus = aString;
        }
        aString = ele.getAttribute("onblur");
        if(aString!=null && aString.length()>0){
            onblur = aString;
        }
        aString = ele.getAttribute("onselect");
        if(aString!=null && aString.length()>0){
            onselect = aString;
        }
        aString = ele.getAttribute("onchange");
        if(aString!=null && aString.length()>0){
            onchange = aString;
        }
    }

    public void write(JspWriter out,String beanName,Object bean,BeanProperty bp,int dispType,BLContext context) throws IOException,SQLException {
        begin(out,beanName+"."+bp.getName(),dispType);
        writeBody(out,bean,bp,dispType,context);
        end(out,dispType);
    }

    public static Display getInstance(Element ele){
        if(ele==null){
            return new Text();
        }

        String type =ele.getAttribute("type");
        if("text".equalsIgnoreCase(type)){
            Text d = new Text();
            d.init(ele);
            return d;
        }else if("radio".equalsIgnoreCase(type)){
            Radio d = new Radio();
            d.init(ele);
            return d;
        }else if("checkbox".equalsIgnoreCase(type)){
            Checkbox d = new Checkbox();
            d.init(ele);
            return d;
        }else if("Hidden".equalsIgnoreCase(type)){
            Hidden d = new Hidden();
            d.init(ele);
            return d;
        }else if("Password".equalsIgnoreCase(type)){
            Password d = new Password();
            d.init(ele);
            return d;
        }else if("File".equalsIgnoreCase(type)){
            File d = new File();
            d.init(ele);
            return d;
        }else if("Select".equalsIgnoreCase(type)){
            Select d = new Select();
            d.init(ele);
            return d;
        }else if("TextArea".equalsIgnoreCase(type)){
            TextArea d = new TextArea();
            d.init(ele);
            return d;
        }else{
            File d = new File();
            d.init(ele);
            return d;
        }
    }

    /**
     * write this object to a JspWriter object;
     *
     * @param out
     * @throws IOException
     */
    public void begin(JspWriter out,String name,int dispType) throws IOException{
        out.write(" name=\"");
        out.write(name);
        out.write("\"");

        if(style!=null){
            out.write(" style=\"");
            out.write(style);
            out.write("\"");
        }
        if(onfocus!=null){
            out.write(" onfocus=\"");
            out.write(onfocus);
            out.write("\"");
        }
        if(onblur!=null){
            out.write(" onblur=\"");
            out.write(onblur);
            out.write("\"");
        }
        if(onselect!=null){
            out.write(" onselect=\"");
            out.write(onselect);
            out.write("\"");
        }
        if(onchange!=null){
            out.write(" onchange=\"");
            out.write(onchange);
            out.write("\"");
        }
    }

    public abstract void end(JspWriter out,int dispType) throws IOException;

    public abstract void writeBody(JspWriter out,Object obj,BeanProperty bp,int dispType,BLContext context)
            throws IOException, SQLException;
}
