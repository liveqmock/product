/**
 *all rights reserved,@copyright 2003
 */
package com.cots.taglib;

import com.cots.mvc.controller.DefaultAction;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import java.util.List;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-10
 * Time: 12:37:01
 * Version: 1.0
 */
public class ErrorsTag extends BaseTag{

    public int doStartTag() throws JspException{
        Object o = pageContext.findAttribute(DefaultAction.ERRORS);
        if(o instanceof List){
            JspWriter out = pageContext.getOut();

            List errors = (List)o;
            int count = errors.size();
            try{
                for(int i=0;i<count;i++){
                    out.println("<font color=\"red\" size=\"2\">");
                    out.println(errors.get(i));
                    out.println("</font><br/>");
                }
            }catch(IOException e){
                throw new JspException(e);
            }

        }
        return Tag.SKIP_BODY;
    }
}
