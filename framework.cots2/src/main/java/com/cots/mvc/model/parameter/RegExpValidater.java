/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.util.XMLFile;
import org.w3c.dom.Element;

import java.util.regex.Pattern;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-7-5
 * Time: 8:12:38
 * Version: 1.0
 */
public class RegExpValidater implements ParamValidater{

    private String regex;
    private String paramName;
    private String message;
    private String msgKey;

    private Pattern pattern;


    public RegExpValidater(){

    }

    public RegExpValidater(String regexp) {
        this.regex = regexp;
        pattern = Pattern.compile(regexp);
    }

    public String getRegexp() {
        return regex;
    }

    public void setRegexp(String regexp) {
        this.regex = regexp;
        pattern = Pattern.compile(regexp);
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
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
        Element v = holder.appendChild(parent,"valid");
        v.setAttribute("regex",regex==null?"":regex);
        v.setAttribute("param",paramName);
        if(message!=null){
            v.setAttribute("message",message);
        }
    }

    /**
     * check if the target value matchs the regexp;
     *
     * @param value the target value;
     * @return true if matches, false otherwise
     */
    public boolean match(String value){
        return pattern.matcher(value).matches();
    }

    /**
     * validate this target value;
     *
     * @param value the target value to be validated, if not a String object, then its toString
     * method will be called.
     * @return true if the target matches the given regex;
     */
    public boolean validate(Object value) {
        if(value==null){
            return false;
        }else{
            return match(value.toString());
        }
    }
}

