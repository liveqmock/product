/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.encoding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.CharArrayWriter;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-19
 * Time: 20:33:46
 */
public class QPEncoder {

    public static int LINELENGTH = 76;

    public String decode(char[] chars) {

        int len = chars.length;
        byte[] bytes = new byte[1];
        char[] ch = new char[2];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (int i = 0; i < len; i++) {
            char b = chars[i];
            if (b == '=') {
                if (i < (len - 2)) {
                    ch[0] = chars[i + 1];
                    ch[1] = chars[i + 2];
                    int value = Integer.valueOf(new String(ch), 16).intValue();
                    bytes[0] = (byte) value;
                    try {
                        baos.write(bytes);
                    } catch (IOException ioe) {

                    }
                    i = i + 2;
                }
            } else {
                bytes[0] = (byte) b;
                try {
                    baos.write(bytes);
                } catch (IOException ioe) {

                }
            }
        }
        try {
            return new String(baos.toByteArray(), "gb2312");
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
    }

    public String decode(String src) {
        char[] chars = src.toCharArray();
        return decode(chars);
    }

    public byte[] decode(byte[] bytes) {
        return null;
    }

    public String encode(String src, String charSet) {
        int len = 0;
        int lineLength = 0;
        byte b = 0;
        int value = 0;
        byte[] bytes = null;
        CharArrayWriter caw = new CharArrayWriter();

        try {
            bytes = src.getBytes(charSet);
        } catch (UnsupportedEncodingException uee) { //if this charset is not supported, return the source;
            return src;
        }

        len = bytes.length;

        for (int i = 0; i < len; i++) {
            b = bytes[i];
            if (b < 0) {
                value = b + 256;
                String hex = "=" + Integer.toHexString(value);
                try {
                    caw.write(hex.toCharArray());
                    lineLength += hex.length();
                } catch (IOException ioe) {

                }
            } else if (b == 0x3d) {
                try {
//                    if(lineLength>(QPEncoder.LINELENGTH-1)){
//
//                    }
                    caw.write("=3D");
                    lineLength += 3;
                } catch (IOException ioe) {

                }
            } else if (b >= 33 && b <= 60 || b >= 62 && b <= 126) {
                caw.write((int) b);
                lineLength++;
            } else if (b == 9) {
                try {
                    caw.write("=09");
                    lineLength += 3;
                } catch (IOException ioe) {

                }
            } else if (b == 32) {
                try {
                    caw.write("=20");
                    lineLength += 3;
                } catch (IOException ioe) {

                }
            } else if (b == 0x0D) {
                try {
                    caw.write("=0D");
                    lineLength += 3;
                } catch (IOException ioe) {

                }
            } else if (b == 0x0A) {
                try {
                    caw.write("=0A");
                    lineLength += 3;
                } catch (IOException ioe) {

                }
            }
        }
        return new String(caw.toCharArray());
    }

    public String encode(byte[] bytes) {
        return null;
    }


    public String decode(String bytes, String charSet) {
        return null;
    }

    public static void main(String[] argc) {
        QPEncoder qp = new QPEncoder();
        System.out.println(qp.decode("=3D=3D=3D=3D"));
        System.out.println(qp.encode("ÖÐ¹ú", "gb2312"));
    }
}
