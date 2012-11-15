/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.bean.PrimitiveType;
import com.cots.util.XMLFile;
import com.cots.mvc.controller.Action;

import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-1
 * Time: 9:14:45
 * Version: 1.0
 */
public final class GenericParameter extends Parameter{

    private Class typeClass;

    public GenericParameter(){

    }

    public GenericParameter(String name,String type) throws ClassNotFoundException{
        this.name = name;
        this.type = type;
        if(PrimitiveType.isPrimitive(type)){
            typeClass = PrimitiveType.getTypeClass(type);
        }else{
            typeClass = Class.forName(type);
        }
    }

    public Class getTypeClass() {
        return typeClass;
    }

    /**
     * create an object;
     *
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object create() throws IllegalAccessException, InstantiationException {
        return typeClass.newInstance();
    }

    /**
     * get the value;
     *
     * @param data parameters' cache;
     * @return
     */
    public Object getValue(HashMap data){
        return data.get(name);
    }

    public void save(XMLFile holder, Element parent) {
        Element param = holder.appendChild(parent,"generic");
        this.saveCommonAttrs(param);
    }
}
