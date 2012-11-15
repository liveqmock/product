/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.CharArrayWriter;

/**
 * Description:
 *      quoted printable encoder and decoder;
 * 
 * User: chuguanghua
 * Date: 2004-2-19
 * Time: 20:33:46
 */
public class QPEncoder {

    public static int LINELENGTH = 76;

    public static String decode2(char[] chars) {
        byte[] bytes = decode(chars);
        return new String(bytes);
    }

    public static String decode(String src) {
        char[] chars = src.toCharArray();
        return decode2(chars);
    }

    public static byte[] decode(char[] chars) {
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
        return baos.toByteArray();
    }

    public static String encode(String src) {
        byte[] bytes = src.getBytes();
        return encode(bytes);
    }

    public static String encode(String src, String charSet) {
        byte[] bytes = null;

        try {
            bytes = src.getBytes(charSet);
        } catch (UnsupportedEncodingException uee) { //if this charset is not supported, return the source;
            return src;
        }
        return encode(bytes);
    }

    public static String encode(byte[] bytes) {
        int len = 0;
        byte b = 0;
        int value = 0;
        CharArrayWriter caw = new CharArrayWriter();

        len = bytes.length;

        for (int i = 0; i < len; i++) {
            b = bytes[i];
            if (b < 0) {
                value = b + 256;
                String hex = "=" + Integer.toHexString(value);
                try {
                    caw.write(hex.toCharArray());
                } catch (IOException ioe) {

                }
            } else if (b == 0x3d) {
                try {
                    caw.write("=3D");
                } catch (IOException ioe) {

                }
            } else if (b >= 33 && b <= 60 || b >= 62 && b <= 126) {
                caw.write((int) b);

            } else if (b == 9) {
                try {
                    caw.write("=09");

                } catch (IOException ioe) {

                }
            } else if (b == 32) {
                try {
                    caw.write("=20");
                } catch (IOException ioe) {

                }
            } else if (b == 0x0D) {
                try {
                    caw.write("=0D");
                } catch (IOException ioe) {

                }
            } else if (b == 0x0A) {
                try {
                    caw.write("=0A");
                } catch (IOException ioe) {

                }
            }
        }
        return new String(caw.toCharArray());
    }

    public static void main(String[] argc) {
        System.out.println(QPEncoder.decode("=3D=3D=3D=3D"));
        System.out.println(QPEncoder.encode("ÖÐ¹ú"));
    }
}
