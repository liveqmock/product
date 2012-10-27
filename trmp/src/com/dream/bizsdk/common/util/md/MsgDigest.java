/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.md;

import com.dream.bizsdk.common.util.encoding.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.DigestException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-6
 * Time: 9:24:49
 */
public class MsgDigest {
    public static final String SHA = "SHA";
    public static final String MD5 = "MD5";

    public static String digest(String msg, String type) throws DigestException {
        try {
            //get a sha digest;
            MessageDigest md = MessageDigest.getInstance(type);
            //set the bytes to be digested;
            md.update(msg.getBytes());
            //digest;
            byte[] dm = md.digest();
            //encode the digested bytes in base64 encoding
            String ds = Base64.encodeBytes(dm);
            return ds;
        } catch (NoSuchAlgorithmException nsae) {
            throw new DigestException();
        }
    }
}
