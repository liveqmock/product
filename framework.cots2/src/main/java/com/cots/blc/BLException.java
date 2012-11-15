/**
 *all rights reserved,@copyright 2003
 */
package com.cots.blc;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-25
 * Time: 15:55:18
 * Version: 1.0
 */
public class BLException extends RuntimeException{
    private String errCode;

    public BLException(){
        super();
    }

    public BLException(Throwable t){
        super(t);
    }

    public BLException(String msg){
        super(msg);
    }

    public BLException(String msg,Throwable t){
        super(msg,t);
    }

    public void setErrCode(String errCode){
        this.errCode = errCode;
    }

    public String getErrCode(){
        return this.errCode;
    }
}