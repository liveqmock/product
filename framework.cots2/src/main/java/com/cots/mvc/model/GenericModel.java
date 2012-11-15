package com.cots.mvc.model;

import com.cots.util.XMLFile;
import com.cots.util.ClassUtil;
import com.cots.mvc.controller.Action;
import com.cots.mvc.model.parameter.ParameterRef;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.w3c.dom.Element;

/**
 * Description:
 *
 * User: chugh
 * Date: 2004-10-29
 * Time: 20:57:14
 * Version: 1.0
 */
public final class GenericModel extends Model{

    protected Class cls;
    protected Object oo;

    protected boolean threadSafe;

    protected String constructorSignature;
    protected String[] constructorParams;
    protected Class[] constructorParamTypes;

    protected String className;
    protected String methodName;

    protected Method method;    

    public GenericModel(String clsName,String methodName){
        this.className = clsName;
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * invoke the target method.
     *
     * @param params
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object invoke(Object[] params)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException {

        Object[] methodParams;
        Object oo;
        if(threadSafe){
            methodParams = params;
            oo = this.oo;
        }else{
            int size = this.parameters_ref.size();
            methodParams = new Object[size];
            System.arraycopy(params,0,methodParams,0,size);
            Object[] ctParams = new Object[constructorParams.length];
            System.arraycopy(params,size,ctParams,0,ctParams.length);
            oo = ClassUtil.createObject(className,constructorParamTypes,ctParams);
        }

        if(method == null){
            method = cls.getMethod(methodName,this.paramTypes);
        }
        return method.invoke(oo,methodParams);
    }

    /**
     * save this model to the underling holder;
     * @param holder
     * @param parent
     */
    public void save(XMLFile holder, Element parent) {
        Element model = holder.appendChild(parent,Action.GENERICMODEL);
        if(this.on!=null && this.on.length()>0){
            model.setAttribute(Action.ON,this.on);
        }
        model.setAttribute(Action.CLASS,this.className);
        model.setAttribute(Action.METHOD,this.methodName);
    }

    public String[] getParameterRefNames(){
        int size = this.parameters_ref.size();
        String[] paramNames = new String[size+this.constructorParams.length];

        for(int i=0;i<size;i++){
            paramNames[i] = ((ParameterRef)this.parameters_ref.get(i)).getName();
        }

        System.arraycopy(this.constructorParams,0,paramNames,size,
                this.constructorParams.length);

        return paramNames;
    }


    /**
     *
     *
     * @param cls
     */
    public void setCls(Class cls) {
        this.cls = cls;
    }

    /**
     *
     *
     * @param oo
     */
    public void setOo(Object oo) {
        this.oo = oo;
    }

    public boolean isThreadSafe() {
        return threadSafe;
    }

    public void setThreadSafe(boolean threadSafe) {
        this.threadSafe = threadSafe;
    }

    /**
     * set the constructors signature.
     *
     * @param constructorParams
     */
    public void setConstructorParams(String constructorParams) {
        if(constructorParams != null){
            this.constructorSignature = constructorParams;
            this.constructorParams = constructorParams.split(",");
        }else{
            this.constructorSignature = "";
            this.constructorParams = new String[0];
        }
    }

    public String getConstructorSignature() {
        return constructorSignature;
    }

    public String[] getConstructorParams() {
        return constructorParams;
    }

    public void setConstructorParamTypes(Class[] constructorParamTypes) {
        this.constructorParamTypes = constructorParamTypes;
    }
}
