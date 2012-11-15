/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp.freeexp;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-28
 * Time: 17:50:38
 * Version: 1.0
 */
public class FreeExpSegConst implements FreeExpSeg{
    private String value;
    public  FreeExpSegConst(String value){
        this.value = value;
    }


    public Object getValue(Object obj,int index) {
        return value;
    }
}
