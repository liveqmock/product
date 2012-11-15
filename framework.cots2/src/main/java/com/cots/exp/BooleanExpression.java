/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp;

import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-4
 * Time: 14:25:12
 */
public interface BooleanExpression extends Expression{
    boolean valueOf(HashMap data);
}