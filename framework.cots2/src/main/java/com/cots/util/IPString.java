/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.util.StringTokenizer;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-29
 * Time: 10:15:21
 * Version: 1.0
 */
public class IPString {

    /**
     * get the long value of a IP String. if the String is not a valid IP address,
     * then 0 is returned.
     *
     * @param ip the source ip String.
     * @return the long value matching the IP address.
     */
    public static long getLongValue(String ip) {
        long v = 0;
        if (ip != null) {
            StringTokenizer st = new StringTokenizer(ip, ".");
            if (st.countTokens() == 4) {
                try {
                    while (st.hasMoreTokens()) {
                        v *= 256;
                        v += Integer.parseInt(st.nextToken());
                    }
                } catch (NumberFormatException e) {
                    v = 0;
                }
            }
        }
        return v;
    }

    /**
     * test if a ip string is a valid ipv4,address.
     *
     * @param ip the source ip address.
     * @return true if the ip is valid, false otherwise.
     */
    public static boolean isIP(String ip) {
        if (ip == null) {
            return false;
        }

        boolean yes = true;

        StringTokenizer st = new StringTokenizer(ip, ".");
        if (st.countTokens() == 4) {
            try {
                while (st.hasMoreTokens()) {
                    int v = Integer.parseInt(st.nextToken());
                    if (v < 0 || v > 255) {
                        yes = false;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                yes = false;
            }
        } else {
            yes = false;
        }
        return yes;
    }


    /**
     * test if ip1 appears after ip2;
     *
     * @param ip1
     * @param ip2
     * @return
     */
    public static boolean after(String ip1, String ip2) {
        if (getLongValue(ip1) >= getLongValue(ip2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * test if ip1 appears before ip2;
     *
     * @param ip1
     * @param ip2
     * @return
     */
    public static boolean before(String ip1, String ip2) {
        if (getLongValue(ip1) <= getLongValue(ip2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * test if a ip address appears within a ip range;
     *
     * @param ip  the ip address to be tested.
     * @param ip1 the start address of the ip range;
     * @param ip2 the end address of the ip range.
     * @return true if the ip appears within ip1 and ip2, false otherwise.
     */
    public static boolean within(String ip, String ip1, String ip2) {
        //if ip1 == null && ip2 == null, then there no ip range limit;
        if(ip1==null && ip2==null){
            return true;
        }

        long v = getLongValue(ip);
        long start = getLongValue(ip1);
        long end = getLongValue(ip2);


        if (v >= start && v <= end || v>=start && end<=0) {
            return true;
        } else {
            return false;
        }
    }
}