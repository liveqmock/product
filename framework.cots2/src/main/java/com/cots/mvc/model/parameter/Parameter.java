package com.cots.mvc.model.parameter;

import com.cots.util.XMLFile;
import com.cots.mvc.controller.Action;

import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * Base class for type parameters. A parameter must contains at least four attributes:
 * displayName, type, source and target.
 *
 * User: chugh
 * Date: 2004-10-27
 * Time: 20:31:50
 * Version: 1.0
 */
public abstract class Parameter {
    //the displayName of the parameter.
    protected String name;

    //the type of the parameter.
    protected String type;

    //the source of the parameter;
    protected int source = ParameterSource.REQUEST;

    //the target of the parameter.
    protected int target = ParameterTarget.REQUEST;

    /**
     * set the displayName of this parameter.
     *
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * get the displayName of this parameter.
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * set the type string of the parameter.
     *
     * @param type
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * get the type string of this parameter.
     *
     * @return
     */
    public String getType(){
        return this.type;
    }

    /**
     * get the source of this parameter.
     *
     * @return
     */
    public int getSource(){
        return source;
    }

    /**
     * set the source of this parameter.
     *
     * @param source
     */
    public void setSource(int source){
        this.source = source;
    }

    /**
     * get the target scope of this parameter.
     *
     * @return
     */
    public int getTarget() {
        return target;
    }

    /**
     * set the target scope of this paramete.
     *
     * @param target
     */
    public void setTarget(int target) {
        this.target = target;
    }

    /**
     * save the common attributes of a Parameter object. these common attributes include
     * displayName, source,target;
     *
     * @param paramNode the parameter
     */
    public void saveCommonAttrs(Element paramNode){
        paramNode.setAttribute(Action.NAME,name);

        if(type!=null && type.length()>0){
            paramNode.setAttribute(Action.TYPE,type);
        }

        paramNode.setAttribute(Action.SOURCE,ParameterSource.getString(source));
        paramNode.setAttribute(Action.TARGET,ParameterTarget.getString(target));
    }

    public  abstract void save(XMLFile holder,Element parameters);

    /**
     * get the Class object matching the type; for example, the int.class for type "int".
     *
     * @return
     */
    public abstract Class getTypeClass();

    /**
     * create a new instance of this parameter.
     *
     * @return a new instance of the parameter type.
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public abstract Object create() throws IllegalAccessException, InstantiationException;

    /**
     * get an object from a map with the displayName-key matched;
     *
     * @param data a map that may contains a key of the displayName of this parameter.
     * @return the named object, null of there is no such named key object.
     */
    public abstract Object getValue(HashMap data);
}
