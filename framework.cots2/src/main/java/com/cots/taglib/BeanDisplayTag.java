/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.taglib;

import com.cots.blc.BLContext;
import com.cots.mvc.controller.ControllerServlet;
import com.cots.bean.BeanFactory;
import com.cots.bean.Bean;
import com.cots.bean.BeanProperty;
import com.cots.bean.display.Display;
import com.cots.bean.display.Text;
import com.cots.bean.display.TextArea;

import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * User: chugh
 * Date: 2005-9-17
 * Time: 11:20:43
 */
public class BeanDisplayTag  extends BaseTag{
    public final static String DISP_NEW="new";
    public final static String DISP_UPDATE="update";
    public final static String DISP_READONLY="readonly";
    public final static String DISP_LIST="list";

    //the displayName of the bean to be dispalyed;
    private String beanName;

    //what type of the display: for new bean, for exising bean, for readOnlyBean; for list bean;
    private String dispType;

    private String columns;

    public BeanDisplayTag(){

    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getDispType() {
        return dispType;
    }

    public void setDispType(String dispType) {
        this.dispType = dispType;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public int doStartTag() throws JspException{
        JspWriter out = pageContext.getOut();
        if(beanName == null){
            beanName = id;
        }
        if(beanName==null || beanName.length()<1){
            return Tag.SKIP_BODY;
        }

        BLContext context = (BLContext)pageContext.getServletContext().getAttribute(ControllerServlet.CONTEXT);
        BeanFactory bf = context.getBeanFactory();
        Bean bean = bf.getByName(beanName);
        if(bean == null){
            return Tag.SKIP_BODY;
        }

        Object obj = pageContext.findAttribute(id);

        //get all the setable properties;
        BeanProperty[] bps = bean.getSetableColumns();
        if(bps == null || bps.length<1){
            return Tag.SKIP_BODY;
        }
        try{
            int dt = Display.DISPLAY_NEW;
            if(DISP_UPDATE.equalsIgnoreCase(dispType)){
                dt = Display.DISPLAY_UPDATE;
            }else if(DISP_READONLY.equalsIgnoreCase(dispType)){
                dt = Display.DISPLAY_READONLY;
            }

            ScriptValidate sv = new ScriptValidate();
            out.print("<table border=\"1\" style=\"\" cellspacing=\"0\" cellpadding=\"0\">");
            for(int i=0;i<bps.length;i++){
                Display display = bps[i].getDisplay();
                out.println("<tr>");
                out.println("<td>");
                String alias = display.getDisplayName();
                if(alias == null || alias.length()<1){
                    alias = bps[i].getName();
                }
                out.println(alias);
                out.println("</td>");
                out.println("<td>");
                display.write(out,beanName,obj,bps[i],dt,context);
                if(display instanceof Text || display instanceof TextArea){
                    sv.append(alias,beanName+"."+bps[i].getName(),bps[i].getType(),bps[i].isNullable(),bps[i].getFormat());
                }
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            String validate = sv.toString();
            if(validate.length()>0){
                out.println(validate);
            }
        }catch(IOException e){
            throw new JspException(e);
        }catch(SQLException e){
            throw new JspException(e);
        }

        return Tag.SKIP_BODY;
    }

    public int doEndTag(){
        return Tag.SKIP_BODY;
    }

    public void release() {
        super.release();
        beanName = null;
        dispType = null;
        columns = null;
    }
}

class ScriptValidate{
    StringBuffer sb = new StringBuffer(512);
    public ScriptValidate(){
        sb.append("var v;\n");
    }

    public void append(String displayName,String name,String type,boolean nullable,String format){
        sb.append("v = document.forms(0).elements(\"");
        sb.append(name);
        sb.append("\").value;\n");
        if(!nullable){
            sb.append("if(v==\"\"){\n");
            sb.append("  alert(\"").append(displayName).append(" 不能为空！\");\n");
            sb.append("  document.forms(0).elements(\"").append(name).append("\").focus();\n");
            sb.append("  return false;\n");
            sb.append("}\n");
            checkType(name,displayName,type,format,false);
        }else{
            checkType(name,displayName,type,format,true);
        }

    }

    public void checkType(String name,String displayName,String type,String format,boolean nullable){
        if("int".equalsIgnoreCase(type) || "integer".equalsIgnoreCase(type)
                || "byte".equalsIgnoreCase(type) || "short".equalsIgnoreCase(type)
                || "long".equalsIgnoreCase(type) ){
            checkInt(name,displayName,nullable);
        }else if("float".equalsIgnoreCase(type) || "double".equalsIgnoreCase(type)){
            checkFloat(name,displayName,nullable);
        }else if(type!=null && type.toLowerCase().indexOf("date")>=0){
            checkDate(name,displayName,format,nullable);
        }
    }

    private void checkInt(String name,String displayName,boolean nullable){
        sb.append("if(");
        if(nullable){
            sb.append("v!=\"\" && ");
        }
        sb.append("!isInt(v)){\n");
        sb.append("  alert(\"").append(displayName).append(" 不是一个有效的整数！\");\n");
        sb.append("  document.forms(0).elements(\"").append(name).append("\").focus();\n");
        sb.append("  return false;\n");
        sb.append("}\n");
    }

    private void checkFloat(String name,String displayName,boolean nullable){
        sb.append("if(");
        if(nullable){
            sb.append("v!=\"\" && ");
        }
        sb.append("!isFloat(v)){\n");
        sb.append("  alert(\"").append(displayName).append(" 不是一个有效的浮点数！\");\n");
        sb.append("  document.forms(0).elements(\"").append(name).append("\").focus();\n");
        sb.append("  return false;\n");
        sb.append("}\n");
    }

    private void checkDate(String name,String displayName,String format,boolean nullable){
        if(format!=null || format.length()<1){
            format = "YYYY-MM-DD";
        }
        sb.append("if(");
        if(nullable){
            sb.append("v!=\"\" && ");
        }
        sb.append("!isDate(v,\"").append(format).append("\")){\n");
        sb.append("  alert(\"").append(displayName).append(" 不是一个有效的日期！该日期的格式需要为：").append(format).append("\");\n");
        sb.append("  document.forms(0).elements(\"").append(name).append("\").focus();\n") ;
        sb.append("  return false;\n");
        sb.append("}\n");
    }

    public String toString(){
        if(sb.length()>0){
            return "<script type=\"text/javascript\">\nfunction validateBean(){\n"+new String(sb)+" return true;\n}\n</script>\n";
        }else{
            return "";
        }
    }
}