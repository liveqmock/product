/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.util.HashMap;

/**
 * Description:
 *
 *
 * User: chuguanghua
 * Date: 2005-5-22
 * Time: 13:47:22
 * Version: 1.0
 */
public class ObjectWrapper {
    private Object object;
    HashMap attributes = new HashMap();

    public ObjectWrapper(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public Object setAttribute(String name,Object value){
        return attributes.put(name,value);
    }

    public Object removeAttribute(String name){
        return attributes.remove(name);
    }

    public Object getAttribute(String name){
        return attributes.get(name);
    }
}
