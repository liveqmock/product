/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-6
 * Time: 16:47:42
 * Version: 1.0
 */
public class UnknownImplicitParameterException extends Exception{

    public UnknownImplicitParameterException(String name){
        super("unknow implicit parameter:"+name);
    }
}
