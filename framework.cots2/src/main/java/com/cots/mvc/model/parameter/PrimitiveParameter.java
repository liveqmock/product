/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.bean.PrimitiveType;
import com.cots.util.XMLFile;
import com.cots.mvc.controller.Action;

import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 13:30:31
 * Version: 1.0
 */
public final class PrimitiveParameter extends Parameter{

    protected String format;

    protected boolean isArray;

    protected String value;

    protected Object constValue;

    public PrimitiveParameter(){
        type="String";
    }

    /**
     *
     * @param name
     * @param type
     */
    public PrimitiveParameter(String name,String type){
        this();
        this.name = name;
        if(type!=null && type.length()>0){
            this.type = type;
        }
    }

    public PrimitiveParameter(String name,String type,String format){
        this();
        this.name = name;
        if(type!=null && type.length()>0){
            this.type = type;
        }
        this.format = format;
    }

    /**
     * check if this attribute is array;
     *
     * @return
     */
    public boolean isArray(){
        return this.isArray;
    }

    /**
     *
     * @param isArray
     */
    public void setArray(boolean isArray){
        this.isArray = isArray;
    }

    public void setFormat(String format){
        this.format = format;
    }

    public String getFormat(){
        return this.format;
    }

    public Object getConstValue() {
        return constValue;
    }

    /**
     * set the value of the Primitive
     * @param value
     * @throws ParseException
     */
    public void setValue(String value)  throws ParseException{
        this.value = value;
        if(value != null && value.length()>0){
            this.constValue = PrimitiveType.create(type,value,format);
        }
    }

    /**
     * get the origional value;
     * 
     * @return
     */
    public String getValue(){
        return value;
    }

    /**
     * check if the this primitive is const;
     *
     * @return true if this prameter is const; false otherwise;
     */
    public boolean isConst(){
        return constValue!=null;
    }


    /**
     * get the Class object of the type;
     * @return the Class object;
     */
    public Class getTypeClass(){
        if(!isArray){
            return (Class)PrimitiveType.getTypeClass(type);
        }else{
            return PrimitiveType.getArrayClass(type);
        }
    }

    /**
     * create a primitive object;
     * 
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object create() throws IllegalAccessException, InstantiationException {
        if("java.util.Date".equals(type)){
            return new Date();
        }if("java.sql.Date".equals(type)){
            return new java.sql.Date(new Date().getTime());
        }
        return null;
    }

    /**
     * get the value from Data;
     *
     * @param data
     * @return the value Obejct;
     */
    public Object getValue(HashMap data){
        if(isConst()){
            return constValue;
        }else{
            return data.get(name);
        }
    }

    public void save(XMLFile holder, Element parent) {
        Element param = holder.appendChild(parent,"primitive");
        this.saveCommonAttrs(param);
        if(format!=null && format.length()>0){
            param.setAttribute(Action.FORMAT,this.format);
        }
        if(isArray){
            param.setAttribute(Action.ARRAY,"true");
        }
        if(value!=null && value.length()>0){
            param.setAttribute(Action.VALUE,this.value);
        }
    }

}
