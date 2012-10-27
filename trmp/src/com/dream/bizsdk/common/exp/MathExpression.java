/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.exp;

import com.dream.bizsdk.common.databus.BizData;

import java.util.EmptyStackException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Stack;
import java.text.ParseException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-3-11
 * Time: 10:50:29
 */
public class MathExpression {
    public static final int TYPE_NUMBER = 10;
    public static final int TYPE_INT = 11;
    public static final int TYPE_FLOAT = 12;
    public static final int TYPE_STRING = 1;
    public static final int TYPE_DATE = 2;
    public static final int TYPE_TIME = 3;

    BizData cxt = null;
    int type = 0;
    Stack v = new Stack();
    Stack op = new Stack();

    public MathExpression(String exp, BizData cxt, int type) throws ParseException {
        this.cxt = cxt;
        this.type = type;
        parse(exp);
    }

    public static String valueOf(String exp, BizData cxt, int type) throws ParseException {
        MathExpression me = new MathExpression(exp, cxt, type);
        return me.getValue();
    }

    public static String valueOf(String exp, Hashtable bds, int type) throws ParseException {
        String bdName = null;
        BizData data = null;

        String s = exp.trim();
        int index = s.indexOf(':');
        if (index > -1) {
            bdName = s.substring(0, index);
            s = s.substring(index + 1);
            data = (BizData) bds.get(bdName);
            if (data == null) {
                data = (BizData) bds.get("default");
            }
        } else {
            data = (BizData) bds.get("default");
        }
        MathExpression me = new MathExpression(s, data, type);
        return me.getValue();
    }

    protected void parse(String exp) throws ParseException {
        int i = 0;
        boolean inStr = false;
        boolean inPath = false;

        StringBuffer operand = new StringBuffer();
        int len = exp.length();
        while (i < len) {
            char ch = exp.charAt(i);
            if (ch == '%') {
                operand.append(ch);
                if (!inStr) {
                    if (inPath) {
                        if (i < len - 1 && exp.charAt(i + 1) == '%') {
                            i++;
                        } else {
                            inPath = false;
                        }
                    } else {
                        inPath = true;
                    }
                }
            } else if (ch == '\"') {
                if (inStr) {
                    if (i < len - 1 && exp.charAt(i + 1) == '\"') {
                        operand.append('\"');
                        i++;
                    } else {
                        inStr = false;
                    }
                } else {
                    inStr = true;
                }
            } else if (inStr) {
                operand.append(ch);
            } else if (inPath) {
                operand.append(ch);
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '(' || ch == ')') {
                if (operand.length() >= 1) {
                    v.push(new String(operand));
                    operand.delete(0, operand.length());
                }
                push(ch);
            } else {
                operand.append(ch);
            }
            i++;
        }
        if (operand.length() >= 1) {
            v.push(new String(operand));
        }
        if (op.size() >= 1) {
            popAll();
        }
    }

    public String getValue() {
        if (v.size() == 1 && op.size() == 0) {
            String value = (String) v.pop();
            if (value.startsWith("%") && value.endsWith("%")) {
                value = cxt.getStringByPath(value.substring(1, value.length() - 1));
            }
            return value;
        } else {
            return null;
        }
    }

    protected void push(char ch) throws ParseException {
        char ch2 = 0;
        Object o = null;
        try {
            o = op.peek();
        } catch (EmptyStackException ese) {
            op.push(new Character(ch));
            return;
        }
        ch2 = ((Character) o).charValue();
        switch (ch) {
            case '+':
            case '-':
                if (ch2 == '*' || ch2 == '/' || ch2 == '+' || ch2 == '-') {
                    pop();
                } else {
                    op.push(new Character(ch));
                    return;
                }
                break;
            case '*':
            case '/':
                if (ch2 == '/' || ch2 == '*') {
                    pop();
                } else {
                    op.push(new Character(ch));
                    return;
                }
                break;
            case '(':
                op.push(new Character(ch));
                return;
            case ')':
                popP();
                return;
        }
        push(ch);
    }

    protected String pop() throws ParseException {
        double dVal = 0;
        String sVal = null;
        if (v.size() <= 1) {
            return null;
        } else {
            Character o = (Character) op.pop();
            String v2 = (String) v.pop();
            if (v2.startsWith("%") && v2.endsWith("%")) {
                v2 = cxt.getStringByPath(v2.substring(1, v2.length() - 1));
            }
            String v1 = (String) v.pop();
            if (v1.startsWith("%") && v1.endsWith("%")) {
                v1 = cxt.getStringByPath(v1.substring(1, v1.length() - 1));
            }
            if (type == RelationalExp.TYPE_NUMBER) {
                dVal = comp(v1, v2, o.charValue());
                sVal = new Double(dVal).toString();
            } else if (type == RelationalExp.TYPE_DATE || type == RelationalExp.TYPE_TIME) {
                if (o.charValue() == '+') {
                    sVal = addDate(v1, v2);
                } else if (o.charValue() == '-') {
                    sVal = subDate(v1, v2);
                }
            } else if (type == RelationalExp.TYPE_STRING) {
                if (o.charValue() == '+') {
                    sVal = add(v1, v2);
                }
            }
            v.push(sVal);
            return sVal;
        }
    }

    protected void popP() throws ParseException {
        while (true) {
            Character o = (Character) op.peek();
            if (o.charValue() != '(') {
                pop();
            } else {
                op.pop();
                return;
            }
        }
    }

    protected void popAll() throws ParseException {
        try {
            while (true) {
                if (pop() == null) {
                    break;
                }
            }
        } catch (EmptyStackException ese) {

        }
    }

    protected double comp(String v1, String v2, char op) {
        double d1 = Double.valueOf(v1).doubleValue();
        double d2 = Double.valueOf(v2).doubleValue();

        switch (op) {
            case '+':
                return add(d1, d2);
            case '-':
                return sub(d1, d2);
            case '/':
                return div(d1, d2);
            case '*':
                return mul(d1, d2);
            default:
                return 0;
        }
    }

    protected double add(double v1, double v2) {
        return v1 + v2;
    }

    protected String add(String v1, String v2) {
        return v1 + v2;
    }

    protected String addDate(String v1, String v2) throws ParseException {
        if (type == RelationalExp.TYPE_DATE) {
            Date dt = BizData.sdfDate.parse(v1);
            long ms = (long) (Double.valueOf(v2).doubleValue() * 3600 * 1000);
            Date newDate = new Date(dt.getTime() + ms);
            return BizData.sdfDate.format(newDate);
        } else if (type == RelationalExp.TYPE_TIME) {
            Date dt = BizData.sdfTime.parse(v1);
            long ms = (long) (Double.valueOf(v2).doubleValue() * 3600 * 1000);
            Date newDate = new Date(dt.getTime() + ms);
            return BizData.sdfDate.format(newDate);
        } else {
            return null;
        }
    }

    protected String subDate(String v1, String v2) throws ParseException {
        if (type == RelationalExp.TYPE_DATE) {
            Date dt = BizData.sdfDate.parse(v1);
            long ms = (long) (Double.valueOf(v2).doubleValue() * 3600 * 1000);
            Date newDate = new Date(dt.getTime() - ms);
            return BizData.sdfDate.format(newDate);
        } else if (type == RelationalExp.TYPE_TIME) {
            Date dt = BizData.sdfTime.parse(v1);
            long ms = (long) (Double.valueOf(v2).doubleValue() * 3600 * 1000);
            Date newDate = new Date(dt.getTime() - ms);
            return BizData.sdfDate.format(newDate);
        } else {
            return null;
        }
    }

    protected double sub(double v1, double v2) {
        return v1 - v2;
    }

    protected double mul(double v1, double v2) {
        return v1 * v2;
    }

    protected double div(double v1, double v2) {
        return v1 / v2;
    }
}