/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.ejb;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-24
 * Time: 10:50:45
 * Version: 1.0
 */
public class CreateMethod {
    private String methodName;
    private String[] paramNames;
    private Class[] paramClasses;

    public CreateMethod(){
        this.methodName = "create";
        paramNames= new String[0];
        paramClasses = new Class[0];
    }

    public CreateMethod(String methodName){
        this.methodName = methodName;
        paramNames= new String[0];
        paramClasses = new Class[0];
    }

    
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamClasses() {
        return paramClasses;
    }

    public void setParamClasses(Class[] paramClasses) {
        this.paramClasses = paramClasses;
    }

    public String[] getParamNames() {
        return paramNames;
    }

    public void setParamNames(String[] paramNames) {
        this.paramNames = paramNames;
    }

    public String getSignature(){
        StringBuffer sb = new StringBuffer(128);
        sb.append(methodName).append("(");
        if(paramNames.length>0){
            sb.append(paramNames[0]);
        }
        if(paramNames.length>1){
            for(int i=1;i<paramNames.length;i++){
                sb.append(",");
                sb.append(paramNames[i]);
            }
        }
        sb.append(")");
        return new String(sb);
    }

    public Object create(Object home, Object[] params)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class clz = home.getClass();
        Method method =clz.getMethod(methodName,paramClasses);
        Object ejb = method.invoke(home,params);
        return ejb;
    }
}
