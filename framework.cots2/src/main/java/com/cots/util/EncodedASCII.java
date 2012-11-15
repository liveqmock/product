/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.io.UnsupportedEncodingException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-18
 * Time: 15:45:07
 */
public class EncodedASCII {

    public static String toChinese(String src) {
        int len = 0;
        char ch;
        char ch2;
        boolean inChi = false;
        StringBuffer sb = new StringBuffer(1024);
        StringBuffer temp = new StringBuffer(1024);

        len = src.length();
        for (int i = 0; i < len; i++) {
            switch (ch = src.charAt(i)) {
                case '~':
                    if (i <= (len - 2)) {
                        if (inChi) {
                            if ((ch2 = src.charAt(i + 1)) == '}') {
                                convert(sb, temp);
                                inChi = false;
                                ++i;
                            } else {
                                temp.append(ch);
                            }
                        } else {
                            if ((ch2 = src.charAt(i + 1)) == '~') {
                                sb.append('~');
                                ++i;
                            } else if (ch2 == '{') {
                                inChi = true;
                                ++i;
                            } else {
                                sb.append(ch);
                            }
                        }
                    } else {
                        sb.append(ch);
                    }
                    break;
                default:
                    if (!inChi) {
                        sb.append(ch);
                    } else {
                        temp.append(ch);
                    }
                    break;
            }
        }
        return new String(sb);
    }

    public static void convert(StringBuffer main, StringBuffer sub) {
        String hChi = new String(sub);
        byte[] bytes = hChi.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] + 128);
        }
        try {
            String fChi = new String(bytes, "GB2312");
            main.append(fChi);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }
        sub.delete(0, sub.length());
    }
}
