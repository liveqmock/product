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
 * Time: 17:33:53
 */
public class PropertySetMethod {
    Class propClass;
    String name;
    Method m;

    public PropertySetMethod(String name,Class propClass,Method m){
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
     * get the Class of the Property;
     *
     * @return
     */
    public Class getPropertyClass(){
        return propClass;
    }

    /**
     * get Method of to set the Property;
     * 
     * @return
     */
    public Method getMethod(){
        return this.m;
    }

    public void setValue(Object bean,Object v){
        try {
            m.invoke(bean,new Object[]{v});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
