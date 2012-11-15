/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp;

import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-2-28
 * Time: 9:00:40
 * Version: 1.0
 */
public interface ObjectExpression extends Expression{
    /**
     * get the object value;
     *
     * @param data
     * @return
     */
    Object valueOf(HashMap data);
}
