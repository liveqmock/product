/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.util.XMLFile;
import com.cots.bean.PrimitiveType;

import java.util.*;
import java.text.ParseException;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-2-26
 * Time: 12:29:26
 * Version: 1.0
 */
public final class SetParameter extends Parameter{

    private String valueType="String";

    public SetParameter() {
        type = "java.util.HashSet";
    }

    public SetParameter(String name){
        this.name = name;
        type = "java.util.HashSet";
    }

    public SetParameter(String name,String type){
        this.name = name;
        if(type!=null && type.length()>0){
            this.type = type;
        }else{
            this.type = "java.util.HashSet";
        }
    }

    /**
     * get the class object of the type;
     * @return
     */
    public Class getTypeClass() {
        if("java.util.HashSet".equals(type)){
            return java.util.HashSet.class;
        }else if("java.util.TreeSet".equals(type)){
            return java.util.TreeSet.class;
        }else if("java.util.LinkedHashSet".equals(type)){
            return java.util.LinkedHashSet.class;
        }else{
            return java.util.HashSet.class;
        }
    }

    /**
     * create a new Object of the type;
     *
     * @return
     */
    public Object create(){
        if("java.util.HashSet".equals(type)){
            return new HashSet();
        }else if("java.util.TreeSet".equals(type)){
            return new TreeSet();
        }else if("java.util.LinkedHashSet".equals(type)){
            return new LinkedHashSet();
        }else{
            return new HashSet();
        }
    }

    public Object create(String[] values) throws ParseException{
        Set set = null;
        if(PrimitiveType.isPrimitive(valueType)){
            if("java.util.HashSet".equals(type)){
                set = new HashSet();
            }else if("java.util.TreeSet".equals(type)){
                set = new TreeSet();
            }else if("java.util.LinkedHashSet".equals(type)){
                set = new LinkedHashSet();
            }else{
                set = new HashSet();
            }
            Object[] array = PrimitiveType.createObjectArray(valueType,values);
            if(array!=null){
                int count = array.length;
                for(int i=0;i<count;i++){
                    set.add(array[i]);
                }
            }
        }
        return set;
    }


    public Object getValue(HashMap data){
        return data.get(name);
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public void save(XMLFile holder, Element parent) {
        Element param = holder.appendChild(parent,"map");
        this.saveCommonAttrs(param);
        if(valueType!=null && valueType.length()>0){
            param.setAttribute("valueType",valueType);            
        }
    }
}
