/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.bean.display;

import com.cots.bean.BeanProperty;
import com.cots.util.ObjectUtil;
import com.cots.blc.BLContext;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * User: chugh
 * Date: 2005-9-22
 * Time: 18:59:57
 */
public abstract class Input extends AbstractDisplay{
    protected String type="text";

    public String getType() {
        return type;
    }

    public void begin(JspWriter out,String name,int dispType) throws IOException {
        if(dispType == DISPLAY_READONLY){
            return;
        }

        out.write("<input type=\"");
        out.write(type);
        out.write("\"");
        if(dispType==DISPLAY_UPDATE && isKey ){
            if("text".equalsIgnoreCase(type) || "password".equalsIgnoreCase(type)){
                out.write(" readonly ");
            }else{
                out.write(" disabled ");
            }
        }

        super.begin(out,name,dispType);
    }

    public void end(JspWriter out,int dispType) throws IOException {
        if(dispType == DISPLAY_READONLY){
            return;
        }
        out.write(">");
    }

    /**
     *
     * @param out
     * @param obj
     * @param bp
     * @throws IOException
     */
    public void writeBody(JspWriter out,Object obj,BeanProperty bp,int dispType,BLContext context) throws IOException {
        Object value = ObjectUtil.getField(obj,bp.getName());

        if(dispType == DISPLAY_READONLY){
            out.write(value.toString());    //todo: should try to format Date/time/timestamp;
        }else{
            out.write(" value=\"");
            if(value!=null){
                out.write(value.toString());
            }
            out.write("\"");
        }
    }
}
