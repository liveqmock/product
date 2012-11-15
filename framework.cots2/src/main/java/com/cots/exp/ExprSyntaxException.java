/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-14
 * Time: 19:11:06
 * Version: 1.0
 */
public class ExprSyntaxException extends Exception{
    /**
     * the original expression;
     */
    private String expr;

    public ExprSyntaxException(String expr,String msg){
        super(msg);
        this.expr = expr;
    }

    /**
     * return the expression assoicated with this Exception;
     *
     * @return the orginal expression;
     */
    public String getExpression(){
        return this.expr;
    }
}
