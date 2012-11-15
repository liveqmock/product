/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp;

import java.util.ArrayList;

/**
 * Description:
 *
 * User: chuguanghua
 * Date: 2004-11-4
 * Time: 8:58:52
 */
final class Parser {

    public final static char PARAM_REF_PREFIX='$';

    /**
     * parse a Expression String. The String may contains parameter refernce. a reference begins with
     * PARAM_REF_PREFIX char.
     *
     * @param exp the orgianl Expression String;
     * @param list the list of referenced parameter names;
     * @return the parsed expression String;
     */
    public static String parse(String exp,ArrayList list){
        int i=0;
        StringBuffer param_name = new StringBuffer();
        StringBuffer parsed = new StringBuffer();

        //clear the list;
        list.clear();

        int length = exp.length();
        while(i<length){
            char ch = exp.charAt(i);
            if(ch == '"'){
                i=beginString(exp,i,length,parsed);
            }else if(ch == PARAM_REF_PREFIX){
                i=getParamRef(exp,i,length,param_name);
                String name = new String(param_name);
                parsed.append(name);
                list.add(name);
            }else{
                parsed.append(ch);
                i++;
            }
        }

        return new String(parsed);
    }

    /**
     *
     * @param source should not be null;
     * @param from note that the following must be true: source.charAt(from)=='"'
     * @param length  the total length of the source: should be equal to source.length();
     * @return
     */
    private static int beginString(String source,int from,int length,StringBuffer sb){
        char pchar = 0;

        sb.append(source.charAt(from));

        int index = from+1;

        for(;index<length;index++){
            char ch = source.charAt(index);
            //append the char to the buffer;
            sb.append(ch);

            switch(ch){
                case '"':
                    if(pchar!='\\'){
                        return index+1;
                    }
                    break;
                default:
                    break;
            }
            pchar = ch;
        }
        return index;
    }

    /**
     * get the current parameter ref displayName;
     *
     * @param exp the expression String;
     * @param from   exp.charAt(from)=='$';
     * @param length the length of the exp:
     * @param sb
     * @return
     */
    private static int getParamRef(String exp,int from,int length,StringBuffer sb){
        int index = from+1;

        sb.delete(0,sb.length());

        for(;index<length;index++){
            char ch = exp.charAt(index);
            if(isSeparator(ch)){
                return index;
            }else{
                sb.append(ch);
            }
        }
        return index;
    }

    /**
     * check if a char is a speparater char;
     *
     * @param ch
     * @return
     */
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

            case '!':
                return true;
            default:
                return false;
        }
    }
}
