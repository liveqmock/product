/**
 * All rights reserved,@copyrights 2003.
 */
package com.dream.bizsdk.common.util;

import java.util.Stack;
import java.util.Hashtable;

import com.dream.bizsdk.common.databus.*;

/**
 * 表达式支持的逻辑运算为and 和 or 操作，在运算的两端为一个关系运算。
 * 关系运算支持:>,>=,=,<=,<,<>五种.例如iA>b,tA<>b,fA>=$b.
 * 一个关系运算的左操作数必须以:i(int),t(time),d(date),s(string),f(float)来确定该
 * 数据的类型,称这个为类型标示符,紧接着类型标示符的是一个FPath来引用实际数据在
 * 数据对象中的位置.右操作数可以是一个常量,及数据本身,而不是一个引用数据对象中的
 * 数据的FPath;也可以是一个FPath来引用数据对象中的数据,此时需要以一个$来表示,如上
 * 面的几个例子所示.
 * 如果表达式中的某个FPath对应的数据在数据对象中不存在,则该表达式的值默认为false.
 * 表达式不支持括号运算。 And的优先级比or高。
 */
public class Expression {

    public final static int AND = 0;
    public final static int OR = 1;
    protected static String ANDOP = "&&";
    protected static String OROP = "||";
    protected Stack sk = new Stack();
    protected boolean value = false;


    public Expression(String exp, BizData data) throws Exception {
        parse(exp, data);
        value = exec();
    }

    public Expression(String exp, Hashtable bds) throws Exception {
        parse(exp, bds);
        value = exec();
    }

    public boolean parse(String exp, BizData data) throws Exception {
        int index1 = 0;
        int index2 = 0;
        int index = 0;
        int countOP = 0;
        int op = 0;
        int[] indexesOP = new int[100];
        int[] typeOP = new int[100];
        while (index != -1) {
            index1 = exp.indexOf(ANDOP, index + ANDOP.length());
            index2 = exp.indexOf(OROP, index + OROP.length());
            if (index1 < 0 && index2 < 0) {
                index = -1;
            } else if (index1 < 0 && index2 >= 0) {
                index = index2;
                op = Expression.OR;
            } else if (index1 >= 0 && index2 < 0) {
                index = index1;
                op = Expression.AND;
            } else {
                if (index1 <= index2) {
                    index = index1;
                    op = Expression.AND;
                } else {
                    index = index2;
                    op = Expression.OR;
                }
            }
            if (index >= 0) {
                indexesOP[countOP] = index;
                typeOP[countOP] = op;
                countOP++;
            }
        }
        int i = 0;
        int start = 0;
        while (i < countOP) {
            ExpUtil expUtil = new ExpUtil(exp.substring(start, indexesOP[i]), data);
            sk.push(new Boolean(expUtil.getValue()));
            sk.push(new Integer(typeOP[i]));
            if (i < countOP) {
                start = indexesOP[i] + (typeOP[i] == Expression.AND ? ANDOP.length() : OROP.length());
            }
            i++;
        }
        ExpUtil expUtil = new ExpUtil(exp.substring(start, exp.length()), data);
        sk.push(new Boolean(expUtil.getValue()));
        return true;
    }

    public boolean parse(String exp, Hashtable bds) throws Exception {
        int index1 = 0;
        int index2 = 0;
        int index = 0;
        int countOP = 0;
        int op = 0;
        int[] indexesOP = new int[100];
        int[] typeOP = new int[100];
        while (index != -1) {
            index1 = exp.indexOf(ANDOP, index + ANDOP.length());
            index2 = exp.indexOf(OROP, index + OROP.length());
            if (index1 < 0 && index2 < 0) {
                index = -1;
            } else if (index1 < 0 && index2 >= 0) {
                index = index2;
                op = Expression.OR;
            } else if (index1 >= 0 && index2 < 0) {
                index = index1;
                op = Expression.AND;
            } else {
                if (index1 <= index2) {
                    index = index1;
                    op = Expression.AND;
                } else {
                    index = index2;
                    op = Expression.OR;
                }
            }
            if (index >= 0) {
                indexesOP[countOP] = index;
                typeOP[countOP] = op;
                countOP++;
            }
        }
        int i = 0;
        int start = 0;
        while (i < countOP) {
            ExpUtil expUtil = new ExpUtil(exp.substring(start, indexesOP[i]), bds);
            sk.push(new Boolean(expUtil.getValue()));
            sk.push(new Integer(typeOP[i]));
            if (i < countOP) {
                start = indexesOP[i] + (typeOP[i] == Expression.AND ? ANDOP.length() : OROP.length());
            }
            i++;
        }
        ExpUtil expUtil = new ExpUtil(exp.substring(start, exp.length()), bds);
        sk.push(new Boolean(expUtil.getValue()));
        return true;
    }

    public boolean exec() throws Exception {
        Boolean b1 = null;
        Boolean b2 = null;
        Boolean b3 = null;
        Object val = null;

        Integer op = null;
        b1 = (Boolean) sk.pop();

        try {
            val = sk.pop();
        } catch (Exception e) {
        }
        while (val != null) {
            if (val.getClass().getName().compareTo("java.lang.Integer") != 0) {
                throw new Exception("Error");
            }
            op = (Integer) val;
            b2 = (Boolean) sk.pop();
            if (op.intValue() == Expression.AND) {
                boolean nv = b1.booleanValue() && b2.booleanValue();
                b1 = new Boolean(nv);
                try {
                    val = sk.pop();
                } catch (Exception e) {
                    val = null;
                }
            } else {//or
                try {
                    val = sk.pop();
                } catch (Exception e) {
                    val = null;
                }
                if (val == null) {
                    b1 = new Boolean(b1.booleanValue() || b2.booleanValue());
                } else {
                    op = (Integer) val;
                    if (op.intValue() == Expression.OR) {//FOMAT LIKE _||_||_;
                        b1 = new Boolean(b1.booleanValue() || b2.booleanValue());
                        val = op;
                    } else { //FORMAT LIKE _&&_||_
                        b3 = (Boolean) sk.pop();
                        b2 = new Boolean(b3.booleanValue() && b2.booleanValue());
                        sk.push(b2);
                        val = new Integer(Expression.OR);
                    }
                }
            }
        }
        return b1.booleanValue();
    }

    public boolean getValue() {
        return value;
    }

    public static boolean valueOf(String exp, BizData data) {
        try {
            Expression e = new Expression(exp, data);
            return e.getValue();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean valueOf(String exp, Hashtable bds) {
        try {
            Expression e = new Expression(exp, bds);
            return e.getValue();
        } catch (Exception e) {
            return false;
        }
    }
}