/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.exp.ObjectExpression;
import com.cots.util.XMLFile;
import com.cots.mvc.controller.Action;

import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * Description:
 *
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-17
 * Time: 9:41:25
 * Version: 1.0
 */
public final class ExprParameter extends Parameter{

    protected String expression;

    protected ObjectExpression exprObject;

    public ExprParameter(String name){
        this.name = name;
        this.type ="java.lang.Object";
    }

    public ExprParameter(String name,String type){
        this.name = name;
        if(type!= null && type.length()>0){
            this.type = type;
        }else{
            this.type ="java.lang.Object";
        }
    }

    /**
     * get the expression source;
     *
     * @return
     */
    public String getExpression() {
        return expression;
    }

    /**
     * set the expression source;
     * @param expression
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * get the expression object;
     *
     * @return
     */
    public ObjectExpression getExprObject() {
        return exprObject;
    }

    /**
     * set the expression object;
     *
     * @param exprObject
     */
    public void setExprObject(ObjectExpression exprObject) {
        this.exprObject = exprObject;
    }

    /**
     * get the type class of the parameter;
     *
     * @return
     */
    public Class getTypeClass() {
        if("java.lang.Object".equals(type)){
            return Object.class;
        }else{
            try{
                return Class.forName(type);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * create an object of this instance;
     *
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object create() throws IllegalAccessException, InstantiationException {
        Class typeClass = getTypeClass();
        if(typeClass != null){
            return typeClass.newInstance();
        }else{
            return null;
        }
    }

    /**
     * get the parameters value;
     *
     * @param data parameter's values cache;
     * @return the value;
     */
    public Object getValue(HashMap data) {
        return exprObject.valueOf(data);
    }

    public void save(XMLFile holder, Element parent) {
        Element param = holder.appendChild(parent,"expr");
        this.saveCommonAttrs(param);
        param.setAttribute("expression",expression);
    }
}
