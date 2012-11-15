/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.bean.PrimitiveType;

/**
 * Description:
 *      A Model may have several parameters. These parameter must have been
 * defined in the parent action before hand.
 *      If user specify the member variable type, then this type will override
 * the orginal Parameter's type.
 *
 * User: chuguanghua
 * Date: 2004-12-17
 * Time: 13:52:24
 * Version: 1.0
 */
public class ParameterRef {
    //the displayName of the referenced parameter;
    protected String name;

    //the type to override the refernced parameter type.
    protected String type;

    protected String orginalType;

    public ParameterRef(){

    }

    public ParameterRef(String name) {
        this.name = name;
    }

    public ParameterRef(String name,String type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * get the Class object of the target type;
     * 
     * @return
     */
    public Class getTypeClass(){
        try{
            if(PrimitiveType.isPrimitive(type)){
                return PrimitiveType.getTypeClass(type);
            }else{
                return Class.forName(type);
            }
        }catch(ClassNotFoundException e){
            return null;
        }
    }

    /**
     * get the orginal type;
     *
     * @return
     */
    public String getOrginalType() {
        return orginalType;
    }

    /**
     * set the original type;
     *
     * @param orginalType
     */
    public void setOrginalType(String orginalType) {
        this.orginalType = orginalType;
    }

    /**
     * get the value expression;
     *
     * @return
     */
//    public ObjectExpression getValueExpression() {
//        return valueExpression;
//    }

    /**
     * set the value expression;
     *
     * @param valueExpression
     */
//    public void setValueExpression(ObjectExpression valueExpression) {
//        this.valueExpression = valueExpression;
//    }
}
