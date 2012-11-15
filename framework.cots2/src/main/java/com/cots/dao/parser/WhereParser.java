/**
 *all rights reserved,@copyright 2003
 */
package com.cots.dao.parser;

import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-5-18
 * Time: 10:05:32
 * Version: 1.0
 */
public class WhereParser {
    public String parser(String where,HashMap fields){
        StringBuffer sb = new StringBuffer(512);


        return new String(sb);
    }

    private int tokenBegin(String where,int index,int length,StringBuffer token){
        while(index<length){
            char ch = where.charAt(index);
            if(isSeparator(ch)){
                return index;
            }else{
                token.append(ch);
                index++;
            }
        }
        return index;
    }


    private boolean isSeparator(char ch){
        switch(ch){
            case '.':
            case '(':
            case ')':
            case ',':
            case ' ':
            case '>':
            case '<':
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
            default:
                return false;
        }
    }
}