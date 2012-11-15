/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.dispatch;

import com.cots.exp.StringExpression;

/**
 * Description:
 *      A RedirectParameter represents a parameter of a redirec url. For example, the following
 * redirect url:  /cots/changeMyPassword.do?newPassword=XXXX&comfirmPassword=YYYY, has two parameters:
 * one is newPassword, the other is comfirmPassword. their value can be static or determined at runtime.
 *
 * User: chugh
 * Date: 2004-10-10
 * Time: 13:33:50
 * Version: 1.0
 */
public final class RedirectParameter{
    //the displayName of the parameter;
    private String name;
    //the value of the parameter;
    private String value;

    //the String expression to determine this parameter's value;
    private StringExpression expression;

    public RedirectParameter(){

    }

    public RedirectParameter(String name,String defValue){
        this.name = name;
        this.value = defValue;
    }

    /**
     * get the displayName of this parameter;
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set the displayName of this parameter;
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the value of the parameter;
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * set the parameter; the value can be a const String value,or an
     * java expression that will be evaluated as a String value;
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * get the compiled Expression from the value;
     *
     * @return
     */
    public StringExpression getExpression() {
        return expression;
    }

    /**
     * set the compiled expression from the value.
     *
     * @param expression
     */
    public void setExpression(StringExpression expression) {
        this.expression = expression;
    }
}
