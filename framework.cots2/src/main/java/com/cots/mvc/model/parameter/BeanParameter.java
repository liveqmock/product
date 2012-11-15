/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.bean.Bean;
import com.cots.bean.BeanPropertyException;
import com.cots.util.XMLFile;
import com.cots.mvc.controller.Action;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * Description:
 *
 * User: chugh
 * Date: 2004-10-27
 * Time: 20:31:41
 * Version: 1.0
 */
public final class BeanParameter extends Parameter{
    //the reference bean obejct;
    protected Bean bean;
    //whether this bean should be an array.
    protected boolean array;

    public Object create() throws IllegalAccessException, InstantiationException {
        return bean.getBeanClass().newInstance();
    }

    public BeanParameter(String name,Bean bean){
        this.name = name;
        this.bean = bean;
    }

    public BeanParameter(String name,String type){
        this.name = name;
        this.type = type;
    }

    /**
     * get the referenced bean object.
     *
     * @return
     */
    public Bean getBean(){
        return this.bean;
    }

    /**
     * set the referenced bean;
     *
     * @param bean the refernced bean;
     */
    public void setBean(Bean bean){
        this.bean = bean;
    }

    /**
     * return the class displayName of the underlying bean.
     *
     * @return the bean's class displayName;
     */
    public String getType(){
        if(bean!=null){
            return bean.getClassName();
        }else{
            return type;
        }
    }

    /**
     * get the Class object from the type;
     *
     * @return
     */
    public Class getTypeClass(){
        if(!array){
            return bean.getBeanClass();
        }else{
            try{
                return Class.forName("[L"+getType()+";");
            }catch(ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * check whether this parameter is array or not.
     *
     * @return true if this bean parameter is array, false otherwise.
     */
    public boolean isArray() {
        return array;
    }

    /**
     * whether this parameter is array or not.
     *
     * @param array
     */
    public void setArray(boolean array) {
        this.array = array;
    }

    /**
     *
     * @param request
     * @return
     */
    public Object create(HttpServletRequest request)
            throws IllegalAccessException, BeanPropertyException, InvocationTargetException, InstantiationException {
        return bean.create(name,request);
    }

    /**
     * create an array of beans.
     *
     * @param request
     * @return
     * @throws IllegalAccessException
     * @throws BeanPropertyException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public Object createArray(HttpServletRequest request)
            throws IllegalAccessException, BeanPropertyException, InvocationTargetException, InstantiationException {
        return bean.createArray(name,request);
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
     * save this parameter.
     *
     * @param holder the xml file that will holds the owner action.
     * @param parent the <parameters> element.
     */
    public void save(XMLFile holder, Element parent) {
        Element param = holder.appendChild(parent,"bean");

        saveCommonAttrs(param);

        param.setAttribute(Action.REFBEAN,this.bean.getName());
        if(array){
            param.setAttribute(Action.ARRAY,"true");
        }
    }
}
