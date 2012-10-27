/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.report;

import com.dream.bizsdk.common.exp.MathExpression;
import com.dream.bizsdk.common.databus.BizData;

import java.text.ParseException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-3
 * Time: 13:07:42
 */
public class ExpressionResolver {
    public static String resolveNumberExp(String exp, BizData d) throws ParseException {
        String t;
        if (exp.startsWith("${") && exp.endsWith("}")) {
            t = exp.substring(2, exp.length() - 1);
            MathExpression me = new MathExpression(t, d, MathExpression.TYPE_NUMBER);
            return Integer.toString(Double.valueOf(me.getValue()).intValue());
        } else {
            return exp;
        }
    }

    public static String resolveStringExp(String exp, BizData d) throws ParseException {
        String t;
        if (exp.startsWith("${") && exp.endsWith("}")) {
            t = exp.substring(2, exp.length() - 1);
            MathExpression me = new MathExpression(t, d, MathExpression.TYPE_STRING);
            return me.getValue();
        } else {
            return exp;
        }
    }
}
