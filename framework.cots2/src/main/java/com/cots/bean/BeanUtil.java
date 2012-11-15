/**
 *all rights reserved,@copyright 2003
 */
package com.cots.bean;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-10-28
 * Time: 16:24:00
 */
public class BeanUtil {

    public static final String PROPERTY_SET_METHOD_PREFIX = "set";
    public static final String PROPERTY_GET_METHOD_PREFIX = "get";
    public static final String PROPERTY_IS_METHOD_PREFIX  = "is";

    /**
     * set a bean's property, the Class of the property must be the same as the Class
     * of v;
     *
     * @param bean         the target bean object;
     * @param propertyName the property name;
     * @param v            the property value;
     * @throws com.cots.bean.NoSuchPropertyException if the bean has no this property;
     * @throws SetPropertyException    if has no acceess to this property,or nested Exception is thrown when
     *                                 call the setMethod
     */
    public static void setBeanProperty(Object bean, String propertyName, Object v)
            throws NoSuchPropertyException, SetPropertyException {
        Class propertyClass = v.getClass();
        setBeanProperty(bean, propertyName, propertyClass, v);
    }


    /**
     * set a bean's property.
     *
     * @param bean          the target bean object;
     * @param propertyName  the property name;
     * @param propertyClass the Class of the property;
     * @param v             the property value;
     * @throws NoSuchPropertyException if the bean has no this property;
     * @throws com.cots.bean.SetPropertyException    if has no acceess to this property,or nested Exception is thrown when
     *                                 call the setMethod
     */
    public static void setBeanProperty(Object bean, String propertyName, Class propertyClass, Object v)
            throws NoSuchPropertyException, SetPropertyException {
        String methodName = BeanUtil.PROPERTY_SET_METHOD_PREFIX + Character.toUpperCase(propertyName.charAt(0))
                + propertyName.substring(1);
        Class[] clz = new Class[1];
        clz[0] = propertyClass;
        try {
            Method m = getMethod(bean, methodName, clz);
            m.invoke(bean, new Object[]{v});
        } catch (NoSuchMethodException e) {
            throw new NoSuchPropertyException(bean.getClass().getName(), propertyName, propertyClass.getName());
        } catch (IllegalAccessException e) {
            throw new SetPropertyException("illegal access to property:" + propertyName);
        } catch (InvocationTargetException e) {
            throw new SetPropertyException(e.getTargetException().getMessage());
        }
    }

    /**
     * get a property's value from a bean object;
     *
     * @param bean the bean object;
     * @param propertyName the property name;
     * @return the Value of the property;
     * @throws NoSuchPropertyException
     * @throws GetPropertyException
     */
    public static Object getBeanProperty(Object bean, String propertyName)
            throws NoSuchPropertyException, GetPropertyException {
        //try getXXX first;
        String methodName = BeanUtil.PROPERTY_GET_METHOD_PREFIX + Character.toUpperCase(propertyName.charAt(0))
                + propertyName.substring(1);
        Class[] clz = new Class[0];
        try {
            Method m = getMethod(bean, methodName, clz);
            return m.invoke(bean, new Object[0]);
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {
            throw new GetPropertyException("illegal access to property:" + propertyName);
        } catch (InvocationTargetException e) {
            throw new GetPropertyException(e.getTargetException().getMessage());
        }

        //if no getXXX, then try isXXX;
        methodName = BeanUtil.PROPERTY_IS_METHOD_PREFIX + Character.toUpperCase(propertyName.charAt(0))
                + propertyName.substring(1);
        try {
            Method m = getMethod(bean, methodName, clz);
            return m.invoke(bean, new Object[0]);
        } catch (NoSuchMethodException e) {
            throw new NoSuchPropertyException(bean.getClass().getName(), propertyName);
        } catch (IllegalAccessException e) {
            throw new GetPropertyException("illegal access to property:" + propertyName);
        } catch (InvocationTargetException e) {
            throw new GetPropertyException(e.getTargetException().getMessage());
        }
    }


    /**
     *
     * @param clz
     * @return
     */
    public static PropertySetMethod[] getPropertySetMethods(Class clz){
        int count;
        ArrayList al = new ArrayList();

        Method[] ms = clz.getMethods();
        count = ms.length;
        for(int i=0;i<count;i++){
            Method m = ms[i];
            String name = m.getName();
            if(name.startsWith(PROPERTY_SET_METHOD_PREFIX)){
                //the setXXX  method should have only one Parameter;
                Class[] paramClasses = m.getParameterTypes();
                if(paramClasses.length == 1){
                    Class c = paramClasses[0];
                    //note: the name of the property should be the substring from the index 3;
                    if(c == byte[].class || c ==char[].class){
                        al.add(0,new PropertySetMethod(name.substring(3),paramClasses[0],m));
                    }else{
                        al.add(new PropertySetMethod(name.substring(3),paramClasses[0],m));
                    }
                }
            }
        }

        return (PropertySetMethod[])al.toArray(new PropertySetMethod[al.size()]);
    }

    /**
     *
     *
     * @param clz
     * @return
     */
    public static PropertyGetMethod[] getPropertyGetMethods(Class clz){
        boolean startsWithGet = false;
        int index = 2;

        int count;
        ArrayList al = new ArrayList();

        Method[] ms = clz.getMethods();
        count = ms.length;
        for(int i=0;i<count;i++){
            Method m = ms[i];
            String name = m.getName();
            if((startsWithGet=name.startsWith(PROPERTY_GET_METHOD_PREFIX))
                    || name.startsWith(PROPERTY_IS_METHOD_PREFIX)){
                //if the method's name starts with "get",
                //then the property name should be the substring from idnex 3;
                if(startsWithGet){
                    index = 3;
                }

                //the getXXX  or isXXX method should have no Parameter;
                Class[] paramClasses = m.getParameterTypes();
                if(paramClasses.length == 0){
                    Class c = m.getReturnType();
                    if(c == byte[].class || c ==char[].class){
                        al.add(0,new PropertySetMethod(name.substring(index),c,m));
                    }else{
                        al.add(new PropertySetMethod(name.substring(index),c,m));
                    }
                }
            }
        }

        return (PropertyGetMethod[])al.toArray(new PropertyGetMethod[al.size()]);
    }

    /**
     *
     *
     * @param o
     * @param methodName
     * @param paramTypes
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Object o, String methodName, Class[] paramTypes)
            throws NoSuchMethodException {
        Class clz = o.getClass();
        return clz.getMethod(methodName, paramTypes);
    }

    /**
     *
     *
     * @param clz
     * @param methodName
     * @param paramTypes
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Class clz, String methodName, Class[] paramTypes)
            throws NoSuchMethodException {
        return clz.getMethod(methodName, paramTypes);
    }

    /**
     * get all the properties from a Bean Class;
     *
     * @param clz
     * @return
     */
    public static BeanProperty[] getProperties(Class clz){
        HashMap p = new HashMap();
        Method[] methods = clz.getMethods();
        int count = methods.length;
        for(int i =0;i<count;i++){
            String methodName = methods[i].getName();
            if(methodName.startsWith(PROPERTY_SET_METHOD_PREFIX)){
                Class[] paramTypes = methods[i].getParameterTypes();
                if(paramTypes!=null && paramTypes.length==1 && PrimitiveType.isPrimitive(paramTypes[0])){
                    String propName = Character.toLowerCase(methodName.charAt(PROPERTY_SET_METHOD_PREFIX.length()))
                            +methodName.substring(PROPERTY_SET_METHOD_PREFIX.length()+1);
                    BeanProperty bp = (BeanProperty)p.get(propName);
                    if(bp==null){
                        bp = new BeanProperty(propName,PrimitiveType.getTypeName(paramTypes[0]));
                        bp.setMethodName = methodName;
                        p.put(propName,bp);
                    }else{
                        if(bp.getType().equalsIgnoreCase(PrimitiveType.getTypeName(paramTypes[0]))){
                            bp.setMethodName = methodName;
                        }
                    }
                }
            }else if(methodName.startsWith(PROPERTY_GET_METHOD_PREFIX)){
                Class[] paramTypes = methods[i].getParameterTypes();
                Class returnType = methods[i].getReturnType();
                if((paramTypes==null || paramTypes.length==0) && PrimitiveType.isPrimitive(returnType)){
                    String propName = Character.toLowerCase(methodName.charAt(PROPERTY_SET_METHOD_PREFIX.length()))
                            +methodName.substring(PROPERTY_SET_METHOD_PREFIX.length()+1);
                    BeanProperty bp = (BeanProperty)p.get(propName);
                    if(bp==null){
                        bp = new BeanProperty(propName,PrimitiveType.getTypeName(returnType));
                        bp.getMethodName = methodName;
                        p.put(propName,bp);
                    }else{
                        if(bp.getType().equalsIgnoreCase(PrimitiveType.getTypeName(returnType))){
                            bp.getMethodName = methodName;
                        }
                    }
                }
            }else if(methodName.startsWith(PROPERTY_IS_METHOD_PREFIX)){
                Class[] paramTypes = methods[i].getParameterTypes();
                Class returnType = methods[i].getReturnType();
                if((paramTypes==null || paramTypes.length==0) && PrimitiveType.isPrimitive(returnType)){
                    String propName = Character.toLowerCase(methodName.charAt(PROPERTY_SET_METHOD_PREFIX.length()))
                            +methodName.substring(PROPERTY_SET_METHOD_PREFIX.length()+1);
                    BeanProperty bp = (BeanProperty)p.get(propName);
                    if(bp==null){
                        bp = new BeanProperty(propName,PrimitiveType.getTypeName(returnType));
                        bp.getMethodName = methodName;
                        p.put(propName,bp);
                    }else{
                        if(bp.getType().equalsIgnoreCase(PrimitiveType.getTypeName(returnType))){
                            bp.getMethodName = methodName;
                        }
                    }
                }
            }
        }
        return (BeanProperty[])p.values().toArray(new BeanProperty[p.size()]);
    }
}