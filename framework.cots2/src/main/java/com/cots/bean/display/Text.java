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
 * Time: 18:55:45
 */
public class Text extends Input{
    protected String size;
    protected String maxlength;

    public void init(Element ele) {
        super.init(ele);
        String aString = ele.getAttribute("size");
        if(aString!=null && aString.length()>0){
            size = aString;
        }
        aString = ele.getAttribute("maxlength");
        if(aString!=null && aString.length()>0){
            maxlength = aString;
        }
    }

    public void begin(JspWriter out,String name,int dispType) throws IOException {
        super.begin(out,name,dispType);
        if(size!=null){
            out.write(" size=\"");
            out.write(size);
            out.write("\"");
        }
        if(maxlength!=null){
            out.write(" maxlength=\"");
            out.write(maxlength);
            out.write("\"");
        }
    }
}
