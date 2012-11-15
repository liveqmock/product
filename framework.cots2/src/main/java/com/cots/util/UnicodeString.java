/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.CharArrayReader;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-2
 * Time: 12:44:22
 */
public class UnicodeString {

    public static String toUnicodeString(String str){
        return toUnicodeString(str,'\\');
    }
    /**
     *
     * @param str
     * @param prefix
     * @return
     */
    public static String toUnicodeString(String str,char prefix){
        if(str==null){
            return null;
        }else{
            try{
                CharArrayWriter caw = new CharArrayWriter(1024);
                int len = str.length();
                for(int i=0;i<len;i++){
                    char ch = str.charAt(i);
                    if(ch==prefix){
                        caw.write(toUnicodeValue(ch,prefix));
                    }else if(ch<127){
                        caw.write(ch);
                    }else{
                        caw.write(toUnicodeValue(ch,prefix));
                    }

                }
                return caw.toString();
            }catch(IOException e){
                return null;
            }
        }

    }

    /**
     * convert a char to it's unicode value string;
     *
     * @param ch
     * @return
     */
    public static String toUnicodeValue(char ch,char prefix){
        StringBuffer sb = new StringBuffer(6);
        String unicode = Integer.toHexString(ch);
        sb.append(prefix);
        sb.append('u');
        int len = unicode.length();
        for(int i=0;i<4-len;i++){
            sb.append("0");
        }
        sb.append(unicode);
        return new String(sb);
    }

    /**
     *
     * @param str
     * @return
     */
    public static String fromUnicodeValue(String str){
        return fromUnicodeValue(str,'\\');
    }

    /**
     *
     * @param str
     * @return
     */
    public static String fromUnicodeValue(String str,char prefix){
        CharArrayWriter caw = new CharArrayWriter(1024);
        CharArrayReader car = new CharArrayReader(str.toCharArray());

        int ch;
        try{
            while((ch=readChar(car,prefix))!=-1){
                caw.write((char)ch);
            }
            return caw.toString();
        }catch(IOException e){
            throw new IllegalArgumentException("not a unicode value string");
        }
    }

    /**
     *
     *
     * @param caw
     * @return
     * @throws IOException
     */
    public static int readChar(CharArrayReader caw,char prefix) throws IOException{
        int r = caw.read();
        char[] unicode= new char[4];
        if(r==-1){
            return -1;
        }else{
            char ch =(char)r;
            if(ch==prefix){
                r = caw.read();
                switch(r){
                    case 'u':
                        int count = caw.read(unicode);
                        if(count!=4){
                            throw new IOException("not a unicode string");
                        }
                        return Integer.parseInt(new String(unicode),16);
                    default:
                        throw new IOException("not a unicode string");
                }
            }else{
                return ch;
            }
        }
    }
    
    /**
     *
     * @param argc
     */
    public static void main(String[] argc){
        String t ="eos@ÖÐ¹ú";
        System.out.println(UnicodeString.fromUnicodeValue(UnicodeString.toUnicodeString(t,'@'),'@'));
    }
}