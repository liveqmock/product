/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description:
 *   Digest a String or byte[] object.
 *
 * User: chugh
 * Date: 2003-12-6
 * Time: 9:24:49
 */
public class MsgDigest {
    public static final String SHA = "SHA";
    public static final String MD5 = "MD5";

    /**
     * digest a message and encode the digested result in base64;
     *
     * @param msg  msg to be digested;
     * @param type digest method;
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String digest(String msg, String type) throws NoSuchAlgorithmException {
        return digest(msg.getBytes(),type);
    }

    /**
     * digest a message in MD5;
     *
     * @param msg the message to be digested;
     * @return digested information;
     * @throws NoSuchAlgorithmException
     */
    public static String digest(String msg) throws NoSuchAlgorithmException {
        return digest(msg,MD5);
    }

    /**
     * digest a byte array.
     *
     * @param content the content to be digested;
     * @param type the digest algorithm;
     * @return the base64 encoded digest;
     * @throws NoSuchAlgorithmException
     */
    public static String digest(byte[] content,String type) throws NoSuchAlgorithmException {
        //get a sha digest;
        MessageDigest md = MessageDigest.getInstance(type);
        //set the bytes to be digested;
        md.update(content);
        //digest;
        byte[] dm = md.digest();
        //encode the digested bytes in base64 encoding
        String ds = Base64.encodeBytes(dm);

        return ds;
    }

    /**
     * digest a byte array;
     *
     * @param content the byte[] array to be digested;
     * @return the base64 encoded digest;
     * @throws NoSuchAlgorithmException
     */
    public static String digest(byte[] content) throws NoSuchAlgorithmException {
        return digest(content,MD5);
    }
}