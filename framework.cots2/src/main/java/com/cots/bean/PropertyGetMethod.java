/**
 *all rights reserved,@copyright 2003
 */
package com.cots.bean;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-10-28
 * Time: 20:44:18
 */
public class PropertyGetMethod {
    Class propClass;
    String name;
    Method m;

    public PropertyGetMethod(String name,Class propClass,Method m){
        this.name = name;
        this.propClass = propClass;
        this.m = m;
    }

    /**
     * get the name of the property;
     *
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * get the class of the property;
     *
     * @return
     */
    public Class getPropertyClass(){
        return propClass;
    }

    /**
     * get the Method to get the Value of the Property;
     *
     * @return
     */
    public Method getMethod(){
        return this.m;
    }

    /**
     * get the Value of the Property;
     *
     * @param bean the target Bean;
     * @return the value of the Property;
     */
    public Object getValue(Object bean){
        Object value = null;
        try{
            //invoke the underlying methods to get the value of the property;
            value = m.invoke(bean,new Object[0]);
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(InvocationTargetException e){
            e.getTargetException().printStackTrace();
        }
        return value;
    }
}
