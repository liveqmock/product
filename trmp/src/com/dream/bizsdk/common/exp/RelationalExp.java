/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.exp;

import com.dream.bizsdk.common.databus.BizData;

import java.util.Date;
import java.util.Hashtable;
import java.text.ParseException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-3-11
 * Time: 17:47:47
 */
public class RelationalExp {

    public static final int G = 0;
    public static final int GE = 1;
    public static final int E = 2;
    public static final int LE = 3;
    public static final int L = 4;
    public static final int NE = 5;

    public static final int TYPE_NUMBER = 10;
    public static final int TYPE_INT = 11;
    public static final int TYPE_FLOAT = 12;
    public static final int TYPE_STRING = 1;
    public static final int TYPE_DATE = 2;
    public static final int TYPE_TIME = 3;


    String left;
    String right;
    int op = 0;
    int type = 0;
    BizData ctx;
    Hashtable bds;

    public RelationalExp(String exp, BizData ctx) {
        this.ctx = ctx;
        parse(exp);
    }

    public RelationalExp(String exp, Hashtable bds) {
        this.bds = bds;
        parse(exp);
    }


    public static boolean valueOf(String exp, BizData ctx) throws ParseException {
        RelationalExp re = new RelationalExp(exp, ctx);
        return re.getValue();
    }

    protected void parse(String exp) {
        int i = 1;
        int count = 0;
        int leftIndex = 0;
        int rightIndex = 0;
        StringBuffer sb = new StringBuffer();
        String op = null;

        //String uExp = exp.toUpperCase();
        char ch = exp.charAt(0);
        if (ch == 's' || ch == 'S') {
            type = RelationalExp.TYPE_STRING;
        } else if (ch == 'd' || ch == 'D') {
            type = RelationalExp.TYPE_DATE;
        } else if (ch == 'T' || ch == 'T') {
            type = RelationalExp.TYPE_TIME;
        } else {
            type = RelationalExp.TYPE_NUMBER;
        }
        count = exp.length();
        while (i < count) {
            ch = exp.charAt(i);
            if (ch == '>' || ch == '=' || ch == '<' || ch == '!') {
                leftIndex = i;
                char nextCh = exp.charAt(i + 1);
                if (nextCh == '>' || nextCh == '=' || nextCh == '<' || nextCh == '!') {
                    sb.append(ch).append(nextCh);
                    rightIndex = i + 2;
                } else {
                    sb.append(ch);
                    rightIndex = i + 1;
                }
                left = exp.substring(1, leftIndex);
                right = exp.substring(rightIndex);
                op = new String(sb);
                if (op.compareTo(">") == 0) {
                    this.op = RelationalExp.G;
                } else if (op.compareTo(">=") == 0) {
                    this.op = RelationalExp.GE;
                } else if (op.compareTo("=") == 0 || op.compareTo("==") == 0) {
                    this.op = RelationalExp.E;
                } else if (op.compareTo("<=") == 0) {
                    this.op = RelationalExp.LE;
                } else if (op.compareTo("<") == 0) {
                    this.op = RelationalExp.L;
                } else if (op.compareTo("!=") == 0 || op.compareTo("<>") == 0) {
                    this.op = RelationalExp.NE;
                }
                break;
            }
            i++;
        }
    }

    public boolean getValue() throws ParseException {
        String v1 = null;
        String v2 = null;
        if (ctx != null) {
            v1 = MathExpression.valueOf(left.trim(), ctx, type);
            v2 = MathExpression.valueOf(right.trim(), ctx, type);
        } else {
            v1 = MathExpression.valueOf(left.trim(), bds, type);
            v2 = MathExpression.valueOf(right.trim(), bds, type);
        }

        if (type == RelationalExp.TYPE_NUMBER) {
            double dv1 = Double.valueOf(v1).doubleValue();
            double dv2 = Double.valueOf(v2).doubleValue();
            return compare(dv1 - dv2, op);
        } else if (type == RelationalExp.TYPE_STRING) {
            int cmp = v1.compareTo(v2);
            return compare(cmp, op);
        } else if (type == RelationalExp.TYPE_DATE) {
            Date dt1 = BizData.sdfDate.parse(v1);
            Date dt2 = BizData.sdfDate.parse(v2);
            int cmp = dt1.compareTo(dt2);
            return compare(cmp, op);
        } else if (type == RelationalExp.TYPE_TIME) {
            Date dt1 = BizData.sdfTime.parse(v1);
            Date dt2 = BizData.sdfTime.parse(v2);
            int cmp = dt1.compareTo(dt2);
            return compare(cmp, op);
        }
        return false;
    }

    protected boolean compare(double cmp, int op) {
        switch (op) {
            case RelationalExp.G:
                if (cmp > 0) {
                    return true;
                } else {
                    return false;
                }
            case RelationalExp.GE:
                if (cmp >= 0) {
                    return true;
                } else {
                    return false;
                }
            case RelationalExp.E:
                if (cmp == 0) {
                    return true;
                } else {
                    return false;
                }
            case RelationalExp.LE:
                if (cmp <= 0) {
                    return true;
                } else {
                    return false;
                }
            case RelationalExp.L:
                if (cmp < 0) {
                    return true;
                } else {
                    return false;
                }
            case RelationalExp.NE:
                if (cmp != 0) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }
}