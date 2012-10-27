/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.serialize;

import com.dream.bizsdk.common.util.encoding.Base64;

import java.io.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-6
 * Time: 14:25:08
 */
public class ObjectSerializer {
    /**
     * save an Object to a byte array;
     *
     * @param obj the object to be serialized;
     * @return the byte array contains the Object,null if the obj can't be serialized;
     */
    public static byte[] save(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return bytes;
    }

    /**
     * @param obj
     * @return
     */
    public static String save2Base64(Object obj) {
        byte[] bytes = save(obj);
        return Base64.encodeBytes(bytes);
    }

    /**
     * read an Object from a byte array, note that this mehtod will read from the beginning
     * of the byte array, if no Object is read from the array, then null value is returned;
     *
     * @param bytes the byte array that may contain an object;
     * @return the Object read from the array,null if no object is read.
     */
    public static Object load(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return obj;
    }

    public static Object loadFromBase64(String str) {
        byte[] bytesb64 = str.getBytes();
        byte[] bytes = Base64.decode(bytesb64);
        return load(bytes);
    }
}
