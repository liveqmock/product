/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.databus;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-5
 * Time: 9:18:28
 */
class PropertyMethod {
    private String name;
    private Method m;

    public PropertyMethod(String name, Method m) {
        this.name = name;
        this.m = m;
    }

    public String getName() {
        return this.name;
    }

    public Method getMethod() {
        return this.m;
    }

    public Object getValue(Object o) {
        try {
            return m.invoke(o, new Object[0]);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public void setValue(Object o, Object v) {
        try {
            Object[] vs = new Object[1];
            vs[0] = v;
            m.invoke(o, vs);
        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        }
    }
}