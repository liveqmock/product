/*
 * InetIP4Address.java
 *
 * Created on 2003年4月6日, 上午10:21
 */

package com.dream.bizsdk.common.util;

/**
 * @author chuguanghua
 */
public class InetIP4Address {
    public final static int IP_BYTES = 4;
    public final static int IP_CHAR_LENGTH = IP_BYTES * 4 - 1;
    String ip = null;
    byte[] bytes = new byte[InetIP4Address.IP_BYTES];

    /**
     * Creates a new instance of InetIP4Address
     */
    public InetIP4Address(String ip) throws Exception {
        this.ip = ip;
        parseIP(this.ip);
    }

    public int getNumber() {
        int sum = 0;
        for (int i = 0; i < InetIP4Address.IP_BYTES; i++) {
            sum += this.bytes[i] * 256;
        }
        return sum;
    }

    protected boolean parseIP(String ip) throws Exception {
        char[] chars = ip.toCharArray();
        StringBuffer[] bytes = new StringBuffer[InetIP4Address.IP_BYTES];
        int k = 0;
        for (int i = 0; i < InetIP4Address.IP_BYTES; i++) {
            bytes[i] = new StringBuffer();
        }
        if (chars.length > InetIP4Address.IP_CHAR_LENGTH) {
            throw new Exception("Illeagle IP4 Address.");
        }
        bytes[0] = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '.') {
                k++;
            } else {
                bytes[k].append(chars[i]);
            }
        }
        if (k != (InetIP4Address.IP_BYTES - 1)) {
            throw new Exception("Illeagle IP4 Address!");
        } else {
            for (int i = 0; i < InetIP4Address.IP_BYTES; i++) {
                int bv = 0;
                try {
                    bv = Integer.valueOf(new String(bytes[i])).intValue();
                } catch (Exception e) {
                    throw new Exception("Illeagle IP4 Address!");
                }
                if (bv < 0 || bv > 255) {
                    throw new Exception("Illeagle IP4 Address!");
                }
                this.bytes[i] = (byte) bv;
            }
        }
        return true;
    }

    public int compareTo(InetIP4Address ip) {
        if (ip == null) {
            return 0;
        } else {
            int n1 = this.getNumber();
            int n2 = ip.getNumber();
            if (n1 > n2) {
                return 1;
            } else if (n1 == n2) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
