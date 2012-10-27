/**
 *All rights reserved,@2003;
 */
package com.dream.bizsdk.common.util;

import com.dream.bizsdk.common.databus.BizData;

import java.util.Date;
import java.util.Hashtable;
import java.lang.Integer;
import java.lang.Double;

/**
 * relational operator: >,>=,=,<,<=,<>
 * operators type prefix:
 * s(S)-String,
 * i(I)-Int
 * f(F)-float;
 * d(D)-date,use the date format of BizData class;
 * t(T)-time,use the time format of BizData class;
 * an Operator can be a FPath to a BizData object;
 * In the following example,the expression will be evaluated to true;
 * BizData d=new BizData();
 * d.add("amount",1);
 * Expression e = new Expression("iamount>1);
 * System.out.println(e.getValue());--println(false);
 * Expression e = new Expression("iamount>=1);
 * System.out.println(e.getValue());--println(true);
 */
public class ExpUtil {
    public final static int STRING = 0;
    public final static int INT = 1;
    public final static int DOUBLE = 2;
    public final static int DATE = 3;
    public final static int TIME = 4;


    public final static int G = 0;
    public final static int GE = 1;
    public final static int E = 2;
    public final static int LE = 3;
    public final static int L = 4;
    public final static int NE = 5;

    protected int srcVarType = 0;//0-String,1-int,2-double,3-date;
    protected String srcVarName = null;
    protected Object srcVarValue = null;
    protected String targetStringVal = null;
    protected int targetIntVal = 0;
    protected double targetDblVal = 0;
    protected Date targetDateVal = null;
    protected int oper = ExpUtil.E;//oper values: >(0),>=(1),=(2),<(3),<=(4)
    protected boolean isOK = false;//if a expression has been successfullu parsed, the status will be set to true;
    protected boolean value = false;
    protected boolean isTargetNull = false;


    /**
     * 构造一个表达式
     */
    public ExpUtil(String exp, BizData data) {
        try {
            String s = exp.trim();
            parse(s, data);
            srcVarValue = data.getByPath(srcVarName);
            if (srcVarValue == null && isTargetNull) {
                value = true;
            } else {
                if (srcVarType == ExpUtil.DATE) {
                    srcVarValue = BizData.sdfDate.parse(srcVarValue.toString());
                } else if (srcVarType == ExpUtil.TIME) {
                    srcVarValue = BizData.sdfTime.parse(srcVarValue.toString());
                }
                value = exec();
            }
        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
        }
    }

    public ExpUtil(String exp, Hashtable bds) {
        try {
            BizData data = null;
            String bdName = null;
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
            parse(s, data);
            srcVarValue = data.getByPath(srcVarName);
            if (srcVarValue == null && isTargetNull) {
                value = true;
            } else {
                if (srcVarType == ExpUtil.DATE) {
                    srcVarValue = BizData.sdfDate.parse(srcVarValue.toString());
                } else if (srcVarType == ExpUtil.TIME) {
                    srcVarValue = BizData.sdfTime.parse(srcVarValue.toString());
                }
                value = exec();
            }
        } catch (Exception e) {
            e.printStackTrace();
            isOK = false;
        }
    }


    /**
     * 运行这个表达式。如果该表达式成立，则返回真，否则返回false;
     */
    protected boolean exec() throws Exception {
        if (!isOK) {
            return false;
        }
        if (srcVarValue == null && isTargetNull) {
            return true;
        } else if (srcVarValue != null && !isTargetNull) {
            return compare(srcVarValue, srcVarType);
        } else {
            return false;
        }
    }

    protected boolean compare(Object srcValue, int type) throws Exception {
        int cmp = 0;
        switch (type) {
            case ExpUtil.STRING:
                String temp = srcValue.toString();
                cmp = temp.compareTo(targetStringVal);
                break;
            case ExpUtil.INT:
                int itemp = Integer.valueOf(srcValue.toString()).intValue();
                cmp = itemp - targetIntVal;
                break;
            case ExpUtil.DOUBLE:
                double ntemp = Double.valueOf(srcValue.toString()).doubleValue();
                double res = ntemp - targetDblVal;
                if (res > 0.00000001) {
                    cmp = 1;
                } else if (res < -0.00000001) {
                    cmp = -1;
                } else {
                    cmp = 0;
                }
                break;
            case ExpUtil.DATE:
            case ExpUtil.TIME:
                cmp = ((Date) srcValue).compareTo(targetDateVal);
                break;
            default:
                throw new Exception("Unsupported value type error.");
        }
        switch (oper) {
            case ExpUtil.G:
                if (cmp > 0) {
                    return true;
                } else {
                    return false;
                }
            case ExpUtil.GE:
                if (cmp >= 0) {
                    return true;
                } else {
                    return false;
                }
            case ExpUtil.E:
                if (cmp == 0) {
                    return true;
                } else {
                    return false;
                }
            case ExpUtil.LE:
                if (cmp <= 0) {
                    return true;
                } else {
                    return false;
                }
            case ExpUtil.L:
                if (cmp < 0) {
                    return true;
                } else {
                    return false;
                }
            case ExpUtil.NE:
                if (cmp == 0) {
                    return false;
                } else {
                    return true;
                }
            default:
                throw new Exception("Unsupported relational operation!");
        }
    }

    protected boolean parse(String exp, BizData data) throws Exception {
        int i = 0;
        int mode = 0;
        oper = ExpUtil.G;
        isOK = false;
        StringBuffer src = new StringBuffer();
        StringBuffer target = new StringBuffer();
        if (exp == null) {
            throw new Exception("Expression invalid error!");
        }
        //int[] index = new int[5];
        int len = exp.length();
        //parse the expression to get the src variable name and relational operatior and target value;
        while (i < len) {
            char ch = exp.charAt(i);
            if (mode == 0) {
                if (ch == '>') {
                    oper = ExpUtil.G;
                    mode = 1;
                } else if (ch == '=') {
                    oper = ExpUtil.E;
                    mode = 1;
                } else if (ch == '<') {
                    oper = ExpUtil.L;
                    mode = 1;
                } else {
                    src.append(ch);
                }
            } else if (mode == 1) {
                if (ch == '>') {
                    if (oper == ExpUtil.L) {
                        oper = ExpUtil.NE;
                    } else {
                        throw new Exception("Expression syntax error!");
                    }
                } else if (ch == '=') {
                    if (oper == ExpUtil.L) {
                        oper = ExpUtil.LE;
                    } else if (oper == ExpUtil.G) {
                        oper = ExpUtil.GE;
                    } else {
                        throw new Exception("Expression syntax error!");
                    }
                } else if (ch == '<') {
                    throw new Exception("Expression syntax error!");
                } else {
                    target.append(ch);
                    mode = 2;
                }
            } else if (mode == 2) {
                if (ch == '>' || ch == '<' || ch == '=') {
                    throw new Exception("Expression systax error!");
                } else {
                    target.append(ch);
                }
            } else {
                throw new Exception("Unexpected error!");
            }
            i++;
        }

        //decide the varialbe type;
        srcVarName = new String(src);
        srcVarName = srcVarName.trim();
        if (srcVarName.startsWith("F") || srcVarName.startsWith("f")) {
            srcVarType = ExpUtil.DOUBLE;
        } else if (srcVarName.startsWith("S") || srcVarName.startsWith("s")) {
            srcVarType = ExpUtil.STRING;
        } else if (srcVarName.startsWith("I") || srcVarName.startsWith("i")) {
            srcVarType = ExpUtil.INT;
        } else if (srcVarName.startsWith("D") || srcVarName.startsWith("d")) {
            srcVarType = ExpUtil.DATE;
        } else if (srcVarName.startsWith("T") || srcVarName.startsWith("t")) {
            srcVarType = ExpUtil.TIME;
        } else {
            throw new Exception("Unknown variable type!");
        }
        srcVarName = srcVarName.substring(1);

        //target value maybe a variable in the BizData;
        if (target.length() > 0 && target.charAt(0) == '$') {
            target.delete(0, 1);
            String targetValue = (String) data.getByPath(new String(target).trim());
            if (targetValue == null) {
                target = new StringBuffer();
            } else {
                target = new StringBuffer(targetValue);
            }
        }

        //parse the target value;
        if (target.length() > 0) {
            switch (srcVarType) {
                case ExpUtil.INT:
                    targetIntVal = Double.valueOf(new String(target).trim()).intValue();
                    break;
                case ExpUtil.DOUBLE:
                    targetDblVal = Double.valueOf(new String(target).trim()).doubleValue();
                    break;
                case ExpUtil.STRING:
                    targetStringVal = new String(target);
                    break;
                case ExpUtil.DATE:
                    targetDateVal = BizData.sdfDate.parse(new String(target).trim());
                    break;
                case ExpUtil.TIME:
                    targetDateVal = BizData.sdfTime.parse(new String(target).trim());
                    break;
                default:
                    break;
            }
        } else {
            isTargetNull = true;
        }
        isOK = true;
        return true;
    }

    public boolean getValue() {
        return value;
    }
}