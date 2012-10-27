/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.crypto;

import com.dream.bizsdk.common.util.encoding.Base64;
import com.dream.bizsdk.common.util.serialize.ObjectSerializer;
import com.dream.bizsdk.common.databus.EncryptException;

import javax.crypto.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-6
 * Time: 10:08:39
 */
public class MsgCipher {
    public static final String DES = "DES";
    public static final String BLOWFISH = "Blowfish";
    public static final String AES = "AES";
    public static final String DESEDE = "DESede";
    public static final String RSA = "RSA";

    protected Cipher cf = null;
    protected Key _key = null;
    protected String type = null;

    public MsgCipher(String type) throws EncryptException {
        this.type = type;
        try {
            cf = Cipher.getInstance(type);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e);
        } catch (NoSuchPaddingException e) {
            throw new EncryptException(e);
        }
        _key = generateKey(type);
    }

    public MsgCipher(String type, Key key) throws EncryptException {
        this.type = type;
        try {
            cf = Cipher.getInstance(type);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e);
        } catch (NoSuchPaddingException e) {
            throw new EncryptException(e);
        }
        _key = key;
    }

    /**
     * @param msg
     * @return
     */
    public String encrypt(String msg) throws EncryptException {
        byte[] bytes = encrypt(msg.getBytes());
        return Base64.encodeBytes(bytes);
    }

    public byte[] encrypt(byte[] msg) throws EncryptException {
        try {
            cf.init(Cipher.ENCRYPT_MODE, _key);
            byte[] resBytes = cf.doFinal(msg);
            return resBytes;
        } catch (InvalidKeyException e) {
            throw new EncryptException(e);
        } catch (IllegalBlockSizeException e) {
            throw new EncryptException(e);
        } catch (BadPaddingException e) {
            throw new EncryptException(e);
        }
    }

    /**
     * @param msg
     * @return
     */
    public String decrypt(String msg) throws EncryptException {
        byte[] s = Base64.decode(msg.getBytes());
        byte[] bytes = decrypt(s);
        return new String(bytes);
    }

    public byte[] decrypt(byte[] msg) throws EncryptException {
        try {
            cf.init(Cipher.DECRYPT_MODE, _key);
            byte[] resBytes = cf.doFinal(msg);
            return resBytes;
        } catch (InvalidKeyException e) {
            throw new EncryptException(e);
        } catch (IllegalBlockSizeException e) {
            throw new EncryptException(e);
        } catch (BadPaddingException e) {
            throw new EncryptException(e);
        }
    }

    /**
     * @param type
     * @return
     */
    public static Key generateKey(String type) {
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(type);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return kg.generateKey();
    }

    /**
     * @param key
     * @return
     */
    public byte[] wrapKey(Key key) {
        try {
            cf.init(Cipher.WRAP_MODE, _key);
            byte[] resBytes = cf.wrap(key);
            return Base64.encode(resBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Key unwrapKey(byte[] wrappedKey) {
        try {
            byte[] bytes = Base64.decode(wrappedKey);
            cf.init(Cipher.UNWRAP_MODE, _key);
            Key k = cf.unwrap(bytes, type, Cipher.SECRET_KEY);
            return k;
        } catch (InvalidKeyException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return null;
    }

    /**
     * @return
     */
    public Key getKey() {
        return _key;
    }

    /**
     * @return
     */
    public String getKeyString() {
        String keyString = null;
        if (_key != null) {
            byte[] bytes = ObjectSerializer.save(_key);
            keyString = Base64.encodeBytes(bytes);
        }
        return keyString;
    }

    /**
     * @param key
     */
    public void setKey(Key key) {
        _key = key;
    }

    public static Key getKeyFromString(String keyString) {
        Key key = null;
        if (keyString != null) {
            byte[] bytes = keyString.getBytes();
            byte[] kb = Base64.decode(bytes);
            key = (Key) ObjectSerializer.load(kb);
        }
        return key;
    }
}