package com.cots.mvc.controller;

import java.util.HashSet;

/**
 * Cots framework
 * <p/>
 * User: chugh
 * Date: 2005-4-8
 * Time: 9:19:07
 * Version: 1.0
 */
public class OnlineHTTPRequests {
    private static HashSet requests = new HashSet();

    /**
     * add a new http request to this object;
     * @param request
     */
    public static synchronized void add(HTTPRequest request){
        requests.add(request);
    }

    /**
     * remove a particular http request from this collection;
     * @param request
     * @return
     */
    public static synchronized boolean remove(HTTPRequest request){
        return requests.remove(request);
    }

    /**
     * return the count of Online http requests currently;
     * @return
     */
    public static int size(){
        return requests.size();
    }

    /**
     * get the array of all the online request;
     *
     * @return the current online requests;
     */
    public synchronized static HTTPRequest[] getRequests(){
        return (HTTPRequest[])requests.toArray(new HTTPRequest[requests.size()]);
    }
}
