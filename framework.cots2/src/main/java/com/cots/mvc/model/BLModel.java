/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model;

import com.cots.blc.BLC;
import com.cots.util.XMLFile;
import com.cots.mvc.controller.Action;
import com.cots.mvc.model.parameter.ParameterRef;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.w3c.dom.Element;

/**
 * Description:
 *
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 14:20:23
 * Version: 1.0
 */
public final class BLModel extends Model{

    //the underly BLC object which contains the this model;
    protected BLC underlyingBLC;

    //the  displayName of the BL method
    protected String methodName;

    //the class displayName of the BL method;
    protected String className;

    //the java.lang.reflect.Method of the bl method.
    protected Method method;

    public BLModel(){

    }

    public BLModel(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public BLModel(BLC blc,String method){
        this.underlyingBLC = blc;
        this.methodName = method;        
    }

    public void setClassName(String className){
        this.className = className;
    }

    public String getClassName(){
        return this.className;
    }

    /**
     * set the corresponding method displayName in the BLC object;
     *
     * @param methodName
     */
    public void setMethodName(String methodName){
        this.methodName = methodName;
    }

    /**
     *
     * @return
     */
    public String getMethodName(){
        return this.methodName;
    }

    /**
     * set the underlying blc to this BLModel object;
     *
     * @param blc
     */
    public void setBLC(BLC blc){
        this.underlyingBLC = blc;
    }

    /**
     * get the underlying BLC object;
     *
     * @return the BLC object that holds the BL method;
     */
    public BLC getBLC() {
        return underlyingBLC;
    }

    /**
     * invoke the target BL Method.
     *
     * @param params
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object invoke(Object[] params)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(method==null){
            Class clazz = underlyingBLC.getClass();
            method =clazz.getMethod(methodName,paramTypes);
        }

        return method.invoke(underlyingBLC,params);
    }

    /**
     *
     *
     * @param holder
     * @param parent
     */
    public void save(XMLFile holder, Element parent) {
        Element model = holder.appendChild(parent,Action.BLMODEL);
        if(this.on!=null && this.on.length()>0){
            model.setAttribute(Action.ON,this.on);
        }
        model.setAttribute(Action.CLASS,this.className);
        model.setAttribute(Action.METHOD,this.methodName);
        int size = this.parameters_ref.size();
        for(int i=0;i<size;i++){
            Element param_ref = holder.appendChild(model,"param-ref");
            param_ref.setAttribute("name",((ParameterRef)(this.parameters_ref.get(i))).getName());
        }
        if(this.returnName!=null){
            Element ret = holder.appendChild(model,"return");
            ret.setAttribute("name",this.returnName);
        }
    }
}
