/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model;

import com.cots.exp.BooleanExpression;
import com.cots.mvc.model.parameter.ParameterRef;
import com.cots.util.XMLFile;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 13:29:45
 * Version: 1.0
 */
public abstract class Model {

    //parameter refereces;
    protected ArrayList parameters_ref = new ArrayList();

    //parameter reference map;
    private HashMap parameters_map = new HashMap();

    //the class object of the referenced parameters;
    protected Class[] paramTypes;

    //the displayName of  return value of this model;
    protected String returnName;

    //the type of the return value.
    protected String returnType;

    //the condition which decide whether the model should be executed.
    protected String on;

    //shoule the model be executed asyn mode;
    protected boolean asynMode;

    protected BooleanExpression booleanExpression;


    /**
     * add a parameter reference;
     *
     * @param ref
     */
    public void addParameterRef(ParameterRef ref){
        parameters_ref.add(ref);
        parameters_map.put(ref.getName(),ref);
    }

    /**
     * set the parameter types of this model;
     *
     * @param paramTypes
     */
    public void setParameterTypes(Class[] paramTypes){
        this.paramTypes = paramTypes;
    }

    /**
     *
     * @return
     */
    public ParameterRef[] getParameterRefs(){
        return (ParameterRef[])this.parameters_ref.toArray(new ParameterRef[this.parameters_ref.size()]);
    }


    /**
     * get a ParameterRef by the displayName;
     * @param name the displayName of the ParameterRef to be get;
     * @return the target ParameterRef;
     */
    public ParameterRef getParameterRef(String name){
        return (ParameterRef)parameters_map.get(name);
    }    

    /**
     * get the referenced parameter names;
     *
     * @return
     */
    public String[] getParameterRefNames(){
        String[] names = new String[this.parameters_ref.size()];
        for(int i=0;i<names.length;i++){
            names[i] = ((ParameterRef)parameters_ref.get(i)).getName();
        }
        return names;
    }

    /**
     *
     *
     * @return
     */
    public String getReturnName(){
        return returnName;
    }

    /**
     * set the displayName of the return value;
     *
     * @param returnName
     */
    public void setReturnName(String returnName){
        this.returnName = returnName;
    }

    /**
     * get the Type of the return value;
     *
     * @return
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * set the return value's type;
     * @param returnType
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * get the condition which specify when to run this model;
     *
     * @return
     */
    public String getOn() {
        return on;
    }

    /**
     * set the condition which specify when to run this model;
     *
     * @param on the new condition;
     */
    public void setOn(String on) {
        this.on = on;
    }

    /**
     * get the Expression object buildBoolean from the on condition;
     *
     * @return the Expression object;
     */
    public BooleanExpression getExpression() {
        return booleanExpression;
    }

    /**
     * set Expression object;
     *
     * @param booleanExpression the new Expression object;
     */
    public void setExpression(BooleanExpression booleanExpression) {
        this.booleanExpression = booleanExpression;
    }

    public boolean isAsynMode() {
        return asynMode;
    }

    public void setAsynMode(boolean asynMode) {
        this.asynMode = asynMode;
    }

    public void save(XMLFile holder,Element parent){

    }
    
    /**
     * invoke this model
     *
     * @param params Parameters' values;
     * @return the returned Object of this Model running;
     */
    public  abstract Object invoke(Object[] params)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,Exception;

}
