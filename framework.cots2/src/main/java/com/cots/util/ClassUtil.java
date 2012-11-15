/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.lang.reflect.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-16
 * Time: 12:47:46
 */
public class ClassUtil {

    /**
     * invoke a static method on a class.
     *
     * @param className
     * @param method
     * @param paramTypes
     * @param paramValues
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object invokeStaticMethod(String className,String method,Class[] paramTypes,Object[] paramValues)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class clz = Class.forName(className);
        Method m =clz.getDeclaredMethod(method,paramTypes);
        return m.invoke(null,paramValues);
    }

    /**
     * get the value of a static field in a Class;
     * @param className the qname of the class;
     * @param fieldName the displayName of the field;
     * @return the value of the field, if the field does not exist, then null is returned;
     */
    public static Object getStaticField(String className,String fieldName){
        try{
            Class clz = Class.forName(className);
            return getStaticField(clz,fieldName);
        }catch(ClassNotFoundException e){
            return null;
        }
    }

    /**
     * get a static field from a Class
     *
     * @param clz the source Class;
     * @param fieldName the displayName of the field;
     * @return the value of the Object, if no such field, null is returned;
     */
    public static Object getStaticField(Class clz,String fieldName){
        try {
            Field field = clz.getDeclaredField(fieldName);
            return field.get(null);
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public static void writeParam(PrintStream javaFile,String paramName,Class paramClass){
        if(paramClass  == byte.class){
            javaFile.print("    byte ");
            javaFile.print(paramName);
            javaFile.print("= Byte.parseByte(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == Byte.class){
            javaFile.print("    Byte ");
            javaFile.print(paramName);
            javaFile.print("= (Byte)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == byte[].class){
            javaFile.print("    byte[] ");
            javaFile.print(paramName);
            javaFile.print("= (byte[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == int.class){
            javaFile.print("    int ");
            javaFile.print(paramName);
            javaFile.print("= ((Integer)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).intValue();");
        }else if(paramClass  == Integer.class){
            javaFile.print("    Integer ");
            javaFile.print(paramName);
            javaFile.print("= (Integer)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == int[].class){
            javaFile.print("    int[] ");
            javaFile.print(paramName);
            javaFile.print("= (int[])data.get(\"");
            javaFile.print(paramName);
            javaFile.print("\");");
        }else if(paramClass  == short.class){
            javaFile.print("    short ");
            javaFile.print(paramName);
            javaFile.print("= ((Short)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).shortValue();");
        }else if(paramClass  == Short.class){
            javaFile.print("    Short ");
            javaFile.print(paramName);
            javaFile.print("= (Short)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == short[].class){
            javaFile.print("    short[] ");
            javaFile.print(paramName);
            javaFile.print("= (short[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == float.class){
            javaFile.print("    float ");
            javaFile.print(paramName);
            javaFile.print("= ((Float)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).floatValue();");
        }else if(paramClass  == Float.class){
            javaFile.print("    Float ");
            javaFile.print(paramName);
            javaFile.print("= (Float)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == float[].class){
            javaFile.print("    float[] ");
            javaFile.print(paramName);
            javaFile.print("= (float[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == double.class){
            javaFile.print("    double ");
            javaFile.print(paramName);
            javaFile.print("= ((Double)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).doubleValue();");
        }else if(paramClass  == Double.class){
            javaFile.print("    Double ");
            javaFile.print(paramName);
            javaFile.print("= (Double)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == double[].class){
            javaFile.print("    double[] ");
            javaFile.print(paramName);
            javaFile.print("= (double[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == long.class){
            javaFile.print("    long ");
            javaFile.print(paramName);
            javaFile.print("= ((Long)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).longValue();");
        }else if(paramClass  == Long.class){
            javaFile.print("    Long ");
            javaFile.print(paramName);
            javaFile.print("= (Long)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == long[].class){
            javaFile.print("    long[] ");
            javaFile.print(paramName);
            javaFile.print("= (long[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == char.class){
            javaFile.print("    char ");
            javaFile.print(paramName);
            javaFile.print("= ((Character)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\")).charValue();");
        }else if(paramClass  == Character.class){
            javaFile.print("    Character ");
            javaFile.print(paramName);
            javaFile.print("= (Character)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == char[].class){
            javaFile.print("    char[] ");
            javaFile.print(paramName);
            javaFile.print("= (char[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == java.util.Date.class){
            javaFile.print("    java.util.Date ");
            javaFile.print(paramName);
            javaFile.print("= (java.util.Date)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == java.sql.Date.class){
            javaFile.print("    java.sql.Date ");
            javaFile.print(paramName);
            javaFile.print("= (java.sql.Date)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == java.sql.Time.class){
            javaFile.print("    java.sql.Time ");
            javaFile.print(paramName);
            javaFile.print("= (java.sql.Time)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == java.sql.Timestamp.class){
            javaFile.print("    java.sql.Timestamp ");
            javaFile.print(paramName);
            javaFile.print("= (java.sql.Timestamp)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == java.util.HashMap.class){
            javaFile.print("    java.util.HashMap ");
            javaFile.print(paramName);
            javaFile.print("= (java.util.HashMap)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == String.class){
            javaFile.print("    String ");
            javaFile.print(paramName);
            javaFile.print("= (String)data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else if(paramClass  == String[].class){
            javaFile.print("    String[] ");
            javaFile.print(paramName);
            javaFile.print("= (String[])data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }else{
            String className = paramClass.getName();
            javaFile.print("    ");
            javaFile.print(className);
            javaFile.print(" ");
            javaFile.print(paramName);
            javaFile.print("= ("+className+")data.get(\"");
            javaFile.print(paramName);
            javaFile.println("\");");
        }
    }

    public static Method getMethod(Class cls,String methodName,Class[] params) throws NoSuchMethodException{
        return cls.getMethod(methodName,params);
    }

    /**
     *
     * @param cls
     * @return
     */
    public static String[] getPublicMethodsSig(Class cls,String exclude){
        ArrayList list = new ArrayList();
        HashSet ex = new HashSet();

        if(exclude!=null){
            StringTokenizer st = new StringTokenizer(exclude,";");
            while(st.hasMoreTokens()){
                ex.add(st.nextToken());
            }
        }

        Method[] methods = cls.getMethods();
        for(int i=0;i<methods.length;i++){
            int modifer = methods[i].getModifiers();

            if((modifer & Modifier.PUBLIC)>0){
                StringBuffer sig = new StringBuffer(512);
                String methodName = methods[i].getName();
                if(ex.contains(methodName)){
                    continue;
                }

                sig.append(methods[i].getName());
                sig.append("(");

                Class[] paramClz = methods[i].getParameterTypes();
                for(int j=0;j<paramClz.length;j++){
                    sig.append(paramClz[j].getName());
                    if(j<paramClz.length-1){
                        sig.append(", ");
                    }
                }
                sig.append(")");
                Class rt = methods[i].getReturnType();
                if(rt != Void.TYPE){
                    sig.append(":").append(rt.getName());
                }
                list.add(new String(sig));
            }
        }

        return (String[])list.toArray(new String[list.size()]);
    }

    public static Object createObject(String clsName,Class[] types,Object[] params) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class cls = Class.forName(clsName);
        Constructor ct = cls.getConstructor(types);
        return ct.newInstance(params);
    }
}