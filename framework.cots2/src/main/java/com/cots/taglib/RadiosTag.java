/**
 *all rights reserved,@copyright 2003
 */
package com.cots.taglib;

import com.cots.dao.RowSet;
import com.cots.dao.Row;
import com.cots.util.ClassUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import java.util.List;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-17
 * Time: 10:15:27
 * Version: 1.0
 */
public final class RadiosTag extends BaseTag{
    protected String checked;

    //this attribute specify a Set object whose elements determine the
    //check state of the radios;
    protected String checker;

    protected String codeField="id";

    protected String nameField="name";

    public String getChecked() {
        return checked;
    }

    public void setChecked(String value) {
        this.checked = value;
    }


    public String getCodeField() {
        return codeField;
    }

    public void setCodeField(String codeField) {
        this.codeField = codeField;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public int doStartTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        if(id ==null){         //id can't be null;
            return Tag.SKIP_BODY;
        }

        try{
            //find the attribute;
            Object obj = pageContext.findAttribute(id);
            if(obj instanceof List){
                writeList(writer,(List)obj);
            }else if(obj instanceof RowSet){
                writeRowSet(writer,(RowSet)obj);
            }else{
                if(obj == null){
                    throw new JspException("Object by id \""+id+"\" not found");
                } else {
                    throw new JspException("Unknown object type for Radios tag:"+obj.getClass());
                }
            }

            return Tag.SKIP_BODY;
        }catch(IOException e){
            throw new JspException(e);
        }
    }

    /**
     * release this tag;
     */
    public void release(){
        super.release();
        checked = null;
        codeField = "id";
        nameField = "name";
    }


    private void writeList(JspWriter writer,List list) throws JspException,IOException{
        int count = list.size();
        Method getCode;
        Method getName;
        Class cls = list.get(0).getClass();
        try{
            getCode = ClassUtil.getMethod(cls,
                    "get"+Character.toUpperCase(codeField.charAt(0))+codeField.substring(1),new Class[0]);
            getName = ClassUtil.getMethod(cls,
                    "get"+Character.toUpperCase(nameField.charAt(0))+nameField.substring(1),new Class[0]);
        }catch(NoSuchMethodException e){
            throw new JspException("no get methods for "+codeField+" and/or "+nameField+" in class "+
                    cls.getName());
        }

        try{
            for(int i=0;i<count;i++){
                Object o = list.get(i);
                Object code = getCode.invoke(o,new Object[0]);
                Object value = getName.invoke(o,new Object[0]);
                if(code == null){
                    code = "";
                }
                writer.print("<input type=\"radio\" ");
                writer.print(" name=\"");
                if(name!=null){
                    writer.print(name);
                }else {
                    writer.print(id);
                }

                writeStyle(writer);
                writeLang(writer);
                writeEvent(writer);

                writer.print("\" value=\"");
                writer.print(code);
                writer.print("\"");
                if(code.equals(checked)){
                    writer.print(" checked ");
                }
                writer.print(">");
                writer.print(value);
            }
        }catch(IllegalAccessException e){
            throw new JspException("failed to get code or name value",e);
        }catch(InvocationTargetException e){
            throw new JspException("failed to get code or name value",e);
        }
    }

    private void writeRowSet(JspWriter writer,RowSet rowSet) throws JspException,IOException{

        int count = rowSet.size();
        if(count < 1){
            return;
        }
        Row row = rowSet.rowAt(0);
        if(row.countCols()<2){
            throw new JspException("RowSet for a list tag must contains at least two columns");
        }

        for(int i=0;i<count;i++){
            Object code = rowSet.getValue(i,0);
            Object value = rowSet.getValue(i,1);
            if(code == null){
                code = "";
            }
            writer.print("<input type=\"radio\" ");
            writer.print(" name=\"");
            if(name!=null){
                writer.print(name);
            }else {
                writer.print(id);
            }
            writer.print("\" value=\"");
            writer.print(code);
            writer.print("\"");

            writeStyle(writer);
            writeLang(writer);
            writeEvent(writer);

            if(code.equals(checked)){
                writer.print(" checked ");
            }
            writer.print(">");
            writer.print(value);
        }
    }
}