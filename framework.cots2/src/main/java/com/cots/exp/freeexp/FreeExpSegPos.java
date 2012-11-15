/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp.freeexp;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-28
 * Time: 17:57:56
 * Version: 1.0
 */
public class FreeExpSegPos implements FreeExpSeg{
    public FreeExpSegPos(){

    }

    public Object getValue(Object obj, int index)  {
        return Integer.toString(index);
    }
}
