/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller.access;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-16
 * Time: 9:21:49
 * Version: 1.0
 */
public class UserAccessException extends Exception{

    String reasonCode;

    public UserAccessException(String reasonCode,String msg){
        super(msg);
        this.reasonCode = reasonCode;
    }

    /**
     *
     * @param reasonCode
     */
    public UserAccessException(String reasonCode){
        this.reasonCode = reasonCode;
    }

    /**
     * get the reason code of this exception;
     *
     * @return the reason code;
     */
    public String getReasonCode(){
        return this.reasonCode;
    }
}
