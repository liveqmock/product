/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.util.XMLFile;
import org.w3c.dom.Element;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-7-5
 * Time: 8:13:42
 * Version: 1.0
 */
public class CustomerValidater implements ParamValidater{

    private String className;
    private String paramNamesString;
    private String[] paramNames;
    private Class[] paramTypes;
    private String message;
    private String methodName="validate";
    private String msgKey;

    //private Object validater;
    private Method method;

    public CustomerValidater() {
    }

    public CustomerValidater(String className, String paramNames) {
        this.className = className;
        this.paramNamesString = paramNames;
        if(paramNames!=null){
            this.paramNames = paramNames.split(",");
        }else{
            this.paramNames = new String[0];            
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParamNamesString() {
        return paramNamesString;
    }

    public void setParamNamesString(String paramNamesString) {
        this.paramNamesString = paramNamesString;
    }

    public String[] getParamNames() {
        return paramNames;
    }

    public void setParamNames(String[] paramNames) {
        this.paramNames = paramNames;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMessage() {
        String msg = message;
        if(msgKey!=null){
            msg = ValidateMessages.getMessage(msgKey,null);
        }
        return msg;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        if(msgKey!=null && msgKey.length()>0){
            this.msgKey = msgKey;
        }
    }

    public void save(XMLFile holder, Element parent) {
        Element v = holder.appendChild(parent,"validater");
        v.setAttribute("class",className==null?"":className);
        if(!"validate".equalsIgnoreCase(methodName)){
            v.setAttribute("method",methodName==null?"":methodName);
        }
        v.setAttribute("param",paramNamesString);
        if(message!=null){
            v.setAttribute("message",message);
        }
    }

    public String validate(Object[] values) {
        if( method==null){
            try {
               Class cls= Class.forName(className);
               method = cls.getMethod(methodName,this.paramTypes);
            } catch (ClassNotFoundException e) {
                Logger.getLogger(CustomerValidater.class).error("class \""+
                        className+"\" not found.",e);
                return "failed to validate, see log for detailed message!";
            } catch (NoSuchMethodException e) {
                Logger.getLogger(CustomerValidater.class).error("method \""+
                        methodName+"\" not found in class \""+className+"\"",e);
                return "failed to validate, see log for detailed message!";
            }
        }
        try {
            Object r = method.invoke(null,values);
            return r==null?null:r.toString();
        } catch (IllegalAccessException e) {
            Logger.getLogger(CustomerValidater.class).error("cant' access \""+
                    methodName+"\" in class \""+className+"\"",e);
            return "failed to validate, see log for detailed message!";
        } catch (InvocationTargetException e) {
            Logger.getLogger(CustomerValidater.class).error("InvocationTargetException in method\""+
                    methodName+"\" in class \""+className+"\"",e.getTargetException());
            return "failed to validate, see log for detailed message!";
        }
    }
}