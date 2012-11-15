/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp.freeexp;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-28
 * Time: 17:52:03
 * Version: 1.0
 */
public class FreeExpSegVar implements FreeExpSeg {
    private Method method;
    private Format formatter;

    public FreeExpSegVar(Method method) {
        this.method = method;
    }

    /**
     * format should only be specifed with method that returns instance of
     * java.util.Date or java.lang.Number or primitive number, null for any
     * other return type;
     *
     * @param method the Method to get the variable;
     * @param format the format used to format the returned value object;
     */
    public FreeExpSegVar(Method method, String format) {
        this.method = method;

        if (format != null) {
            Class cls = method.getReturnType();
            if (cls.equals(java.util.Date.class) ||
                    cls.equals(java.sql.Date.class) ||
                    cls.equals(java.sql.Time.class) ||
                    cls.equals(java.sql.Timestamp.class)) {
                formatter = new SimpleDateFormat(format);
            } else {
                formatter = new DecimalFormat(format);
            }
        }

    }

    public Object getValue(Object obj, int index) throws IllegalAccessException, InvocationTargetException {
        Object o = method.invoke(obj, new Object[0]);
        if (formatter != null) {
            return formatter.format(o);
        } else {
            return o;
        }
    }
}
