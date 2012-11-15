/**
 *all rights reserved,@copyright 2003
 */
package com.cots.taglib;

import com.cots.util.ClassUtil;
import com.cots.dao.RowSet;
import com.cots.dao.Row;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import java.util.List;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Set;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-11
 * Time: 9:03:19
 * Version: 1.0
 */
public final class SelectTag extends BaseTag{

    protected String selected;

    //this attribute specify a Set object whose elements determine the
    //select state of the radios;
    protected String selector;

    protected String codeField="id";

    protected String nameField="name";

    protected String multiple;

    protected String size;

    private Set selectedValues = new HashSet();

    public String getSelected() {
        return selected;
    }

    public void setSelected(String value) {
        this.selected = value;
        if(value != null && value.length()>0){
            if(selectedValues == null){
                selectedValues = new HashSet();
            }else{
                selectedValues.clear();
            }

            StringTokenizer st = new StringTokenizer(value,",;");
            while(st.hasMoreElements()){
                selectedValues.add(st.nextToken());
            }
        }
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public int doStartTag() throws JspException {
        boolean multi = false;

        try{
            JspWriter writer = pageContext.getOut();
            if(id ==null){
                return Tag.SKIP_BODY;
            }

            writer.print("<select name=\"");
            if(name!=null){
                writer.print(name);
            }else{
                writer.print(id);
            }
            writer.println("\" ");

            writeStyle(writer);

            writeLang(writer);

            writeEvent(writer);

            if("true".equalsIgnoreCase(multiple)){
                writer.print("multiple ");
                multi = true;
                if(size!=null){
                    writer.print("size=\"");
                    writer.print(size);
                    writer.print("\" ");
                }
            }
            writer.println(">");

            //if selector is specified, then find this attribute;
            if(selector!=null){
                Object s = pageContext.findAttribute(selector);
                if(s instanceof Set){
                    selectedValues = (Set)s;
                }
            }

            Object obj = pageContext.findAttribute(id);
            if(obj instanceof List){
                writeList(writer,(List)obj,multi);
            }else if(obj instanceof RowSet){
                writeRowSet(writer,(RowSet)obj,multi);
            }else{
                if(obj == null){
                    throw new JspException("Object by id \""+id+"\" not found");
                } else {
                    throw new JspException("Unknown object type for List tag:"+obj.getClass());
                }
            }
            writer.println("</select>");

            return Tag.SKIP_BODY;
        }catch(IOException e){
            throw new JspException(e);
        }finally{
            selectedValues.clear();
        }
    }

    /**
     * release this tag;
     */
    public void release(){
        super.release();
        selected = null;
        selector = null;
        multiple = null;
        size = null;
        codeField = "id";
        nameField = "name";
    }


    private void writeList(JspWriter writer,List list,boolean multi) throws JspException,IOException{
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
                writer.print("<option value=\"");
                writer.print(code);
                writer.print("\"");
                if(!multi && code.equals(selected) || multi && selectedValues.contains(code)){
                    writer.print(" selected ");
                }
                writer.print(">");
                writer.print(value);
                writer.println("</option>");
            }
        }catch(IllegalAccessException e){
            throw new JspException("failed to get code or name value",e);
        }catch(InvocationTargetException e){
            throw new JspException("failed to get code or name value",e);
        }
    }

    private void writeRowSet(JspWriter writer,RowSet rowSet,boolean multi) throws JspException,IOException{

        int count = rowSet.size();
        if(count < 1){
            return;
        }
        Row row = rowSet.rowAt(0);
        if(row.countCols()<2){
            throw new JspException("RowSet for a list tag must contain at least two columns");
        }

        for(int i=0;i<count;i++){
            Object code = rowSet.getValue(i,0);
            Object value = rowSet.getValue(i,1);
            if(code == null){
                code = "";
            }
            writer.print("<option value=\"");
            writer.print(code);
            writer.print("\"");
            if(!multi && code.equals(selected) || multi && selectedValues.contains(code)){
                writer.print(" selected ");
            }
            writer.print(">");
            writer.print(value);
            writer.println("</option>");
        }
    }
}