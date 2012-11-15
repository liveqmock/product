/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.bean.display;

import org.w3c.dom.Element;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

import com.cots.bean.BeanProperty;
import com.cots.blc.BLContext;
import com.cots.util.ObjectUtil;

/**
 * User: chugh
 * Date: 2005-9-22
 * Time: 18:56:15
 */
public class TextArea extends AbstractDisplay{
    protected String rows;
    protected String cols;

    public void init(Element ele) {
        super.init(ele);
        String aString = ele.getAttribute("rows");
        if(aString!=null && aString.length()>0){
            rows = aString;
        }
        aString = ele.getAttribute("cols");
        if(aString!=null && aString.length()>0){
            cols = aString;
        }
    }

    public void begin(JspWriter out,String name,int dispType) throws IOException {
        if(dispType == DISPLAY_READONLY){
            return;
        }

        out.write("<textarea");
        if(rows!=null){
            out.write(" rows=\"");
            out.write(rows);
            out.write("\"");
        }
        if(cols!=null){
            out.write(" cols=\"");
            out.write(cols);
            out.write("\"");
        }
        super.begin(out,name,dispType);
        out.write(">");
    }

    public void end(JspWriter out,int dispType) throws IOException {
        if(dispType == DISPLAY_READONLY){
            return;
        }

        out.write("</textarea>");
    }

    public void writeBody(JspWriter out,Object obj,BeanProperty bp,int dispType,BLContext context) throws IOException {
        Object value = ObjectUtil.getField(obj,bp.getName());
        if(value!=null){
            out.write(value.toString());
        }else{
            out.write("&nbsp;");
        }
    }
}
