/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.exp.BooleanExpression;
import com.cots.util.XMLFile;


import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chugh
 * Date: 2005-2-5
 * Time: 12:26:13
 * Version: 1.0
 */
public final class BooleanValidater {
    //the boolean expression to test if parameter(s) is valid or not;
    private BooleanExpression expression;
    //the message to be shown if the parameter(s) is not valid;
    private String message;

    private String msgKey;

    public BooleanValidater() {
    }

    public BooleanValidater(BooleanExpression expression, String message,String mskKey) {
        this.expression = expression;
        if(message==null || message.length()<1){
            this.message = "expression "+expression.getSource()+" failed";            
        }else{
            this.message = message;
        }
        setMsgKey(msgKey);
    }

    public BooleanExpression getExpression() {
        return expression;
    }

    public void setExpression(BooleanExpression expression) {
        this.expression = expression;
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

    public boolean isValid(HashMap data){
        return expression.valueOf(data);
    }

    public void save(XMLFile holder,Element parent){
        Element v = holder.appendChild(parent,"valid");
        v.setAttribute("if",expression.getSource());
        v.setAttribute("message",message);
    }
}

