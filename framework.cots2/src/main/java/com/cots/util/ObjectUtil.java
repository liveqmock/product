/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.util;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * User: chugh
 * Date: 2005-9-11
 * Time: 12:53:06
 */
public class ObjectUtil {
    public final static Object getField(Object obj,String fieldName){
        if(obj==null || fieldName== null || fieldName.length()<1){
            return null;
        }

        Class cls = obj.getClass();
        try {
            Field f = cls.getField(fieldName);
            return f.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {

        }

        String methodName = "get"+Character.toUpperCase(fieldName.charAt(0)) +fieldName.substring(1);
        try {
            Method m = cls.getMethod(methodName, new Class[0]);
            return m.invoke(obj,new Object[0]);
        } catch (NoSuchMethodException e) {
            //no handler here, will look for the field again;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }


        return null;
    }

    public final static Object getField2(Object obj,String fieldName){
        if(obj==null || fieldName== null || fieldName.length()<1){
            return null;
        }

        Class cls = obj.getClass();

        String methodName = "get"+Character.toUpperCase(fieldName.charAt(0)) +fieldName.substring(1);
        try {
            Method m = cls.getMethod(methodName, new Class[0]);
            return m.invoke(obj,new Object[0]);
        } catch (NoSuchMethodException e) {
            //no handler here, will look for the field again;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }


        return null;
    }

    public final static void setField(Object obj,String fieldName,Object value,Class valueClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(obj==null || fieldName== null || fieldName.length()<1){
            return;
        }

        String methodName = "set"+Character.toUpperCase(fieldName.charAt(0)) +fieldName.substring(1);
        Method m = obj.getClass().getMethod(methodName, new Class[]{valueClass});
        m.invoke(obj,new Object[]{value});
    }

    public static Object getField(HashMap map,String objfieldName){
        int objectNamePos = objfieldName.indexOf(".");
        if(objectNamePos<0){
            return map.get(objfieldName);
        }
        String objectName = objfieldName.substring(0,objectNamePos);
        String fieldName = objfieldName.substring(objectNamePos+1);
        Object obj = map.get(objectName);
        return getField(obj,fieldName);
    }
}
