package com.cots.mvc.model.parameter;

import com.cots.bean.PrimitiveType;
import com.cots.util.XMLFile;

import java.util.*;
import java.text.ParseException;

import org.w3c.dom.Element;

/**
 * Description:
 *
 * User: chugh
 * Date: 2004-10-27
 * Time: 20:31:29
 * Version: 1.0
 */
public final class ListParameter  extends Parameter {
    public String valueType="String";

    public ListParameter(){
        type ="java.util.ArrayList";
    }

    public ListParameter(String name){
        this.name = name;
        type ="java.util.ArrayList";
    }

    public ListParameter(String name,String type){
        this.name = name;
        if(type!=null && type.length()>0){
            this.type = type;
        }else{
            this.type ="java.util.ArrayList";
        }
    }

    public Object create() {
        if("java.util.List".equals(type)){
            return new ArrayList();
        }else if("java.uti.LinkedList".equals(type)){
            return new LinkedList();
        }else if("java.uti.Vector".equals(type)){
            return new Vector();
        }else{
            return new ArrayList();
        }
    }

    public void setValueType(String valueType){
        this.valueType = valueType;
    }

    public String getValueType(){
        return this.valueType;
    }

    public Class getTypeClass(){
        if("java.util.List".equals(type)){
            return java.util.List.class;
        }else if("java.util.ArrayList".equals(type)){
            return java.util.ArrayList.class;
        }else if("java.util.LinkedList".equals(type)){
            return java.util.LinkedList.class;
        }else if("java.util.Vector".equals(type)){
            return java.util.Vector.class;
        }else{
            return java.util.ArrayList.class;
        }
    }

    /**
     *
     *
     * @param values
     * @return
     * @throws ParseException
     */
    public Object create(String[] values) throws ParseException{
        List list = null;
        if(PrimitiveType.isPrimitive(valueType)){
            if("java.util.ArrayList".equals(type)){
                list = new ArrayList();
            }else if("java.util.LinkedList".equals(type)){
                list = new LinkedList();
            }else{
                list = new ArrayList();
            }
            Object[] array = PrimitiveType.createObjectArray(valueType,values);
            if(array!=null){
                int count = array.length;
                for(int i=0;i<count;i++){
                    list.add(array[i]);
                }
            }
        }
        return list;
    }

    /**
     * get the value from the parameters cache;
     * 
     * @param data
     * @return
     */
    public Object getValue(HashMap data){
        return data.get(name);
    }

    /**
     *
     * @param holder
     * @param parent
     */
    public void save(XMLFile holder, Element parent) {
        Element param = holder.appendChild(parent,"list");
        this.saveCommonAttrs(param);
        if(valueType!=null && valueType.length()>0){
            param.setAttribute("valueType",valueType);
        }        
    }
}