package com.dream.bizsdk.web;

import java.util.HashMap;

/**
 * User: divine
 * Date: 2004-7-18
 * Time: 20:24:24
 */
public class BLServletMap {
    private static HashMap map = new HashMap();

    /**
     * add a new BLServlet object ot this map;
     *
     * @param context
     * @param blServlet
     */
    public static void add(String context, BLServlet blServlet) {
        map.put(context, blServlet);
    }

    /**
     * remove a named BLServlet object;
     *
     * @param context the name of the BLServlet to be removed;
     */
    public static void remove(String context) {
        map.remove(context);
    }

    /**
     * get a BLServlet Object by the name;
     *
     * @param context the name of the BLServlet;
     * @return
     */
    public static BLServlet get(String context) {
        return (BLServlet) map.get(context);
    }
}
