/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp.freeexp;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-19
 * Time: 14:56:45
 * Version: 1.0
 */
public class Parser {

    public static HashMap exps = new HashMap();


    public static String getValue(String source,String format,Object obj,int position)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        HashMap clsMap = (HashMap)exps.get(obj.getClass());
        if(clsMap == null){
            synchronized(exps){
                clsMap = (HashMap)exps.get(obj.getClass());
                if(clsMap == null){
                    clsMap = new HashMap();
                    exps.put(obj.getClass(),clsMap);
                }
            }
        }
        FreeExp exp = (FreeExp)clsMap.get(source);
        if(exp == null){
            synchronized(clsMap){
                exp = (FreeExp)clsMap.get(source);
                if(exp == null){
                    exp = buildExp(source,obj,format);
                    clsMap.put(source,exp);
                }
            }
        }
        return exp.invoke(obj,position);
    }

    protected static FreeExp buildExp(String source,Object obj,String format) throws NoSuchMethodException {
        int propLen;
        int length = source.length();
        int index = 0;
        StringBuffer buf = new StringBuffer(256);
        StringBuffer prop = new StringBuffer(128);
        FreeExp exp = new FreeExp(source);
        while(index<length){
            index = getNextProperty(source,index,length,buf,prop);

            if(buf.length()>0){
                exp.addSeg(new FreeExpSegConst(new String(buf)));
                buf.delete(0,buf.length());
            }

            if((propLen = prop.length())>0){
                if(propLen == 1 && prop.charAt(0)=='I'){
                    exp.addSeg(new FreeExpSegPos());
                }else{
                    Method m = getMethod(new String(prop),obj);
                    exp.addSeg(new FreeExpSegVar(m,format));
                }
            }
        }
        if(buf.length()>0){
            exp.addSeg(new FreeExpSegConst(new String(buf)));
            buf.delete(0,buf.length());
        }
        return exp;
    }


    private static Method getMethod(String name,Object obj)
            throws NoSuchMethodException{
        String methodName = "get"+Character.toUpperCase(name.charAt(0))+name.substring(1);
        Method method = obj.getClass().getMethod(methodName,new Class[0]);

        return method;
    }

    private static int getNextProperty(String source,int begin,int length,StringBuffer buf,StringBuffer prop){
        prop.delete(0,prop.length());
        int index = begin;
        char ch=0;
        while(index < length && (ch=source.charAt(index))!='$'){
            buf.append(ch);
            index=index+1;
        }

        if(ch == '$'){
            index = index+1;
            while(index<length && !isSeparator((ch=source.charAt(index)))){
                prop.append(ch);
                index = index+1;
            }
        }
        return index;
    }

    private static boolean isSeparator(char ch){
        switch(ch){
            case '+':
            case '-':
            case '*':
            case '/':
            case '%':
            case '>':
            case '=':
            case '<':
            case '&':
            case '|':
            case '^':
            case '~':
            case '?':
            case ':':
            case '(':
            case ')':
            case '{':
            case '}':
            case '[':
            case ']':
            case ';':
            case '.':
            case ',':
            case '\'':
            case '"':
            case ' ':
                return true;
            default:
                return false;
        }
    }
}

