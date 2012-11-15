/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 17:43:44
 * Version: 1.0
 */
public class NoSuchParamException extends Exception{
    public NoSuchParamException(String paramName){
        super("Parameter \""+paramName+"\" not found.");
    }
}
