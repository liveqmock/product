/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp.freeexp;

import java.lang.reflect.InvocationTargetException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-28
 * Time: 17:49:43
 * Version: 1.0
 */
public interface FreeExpSeg {
    public Object getValue(Object obj,int index) throws IllegalAccessException, InvocationTargetException;
}
