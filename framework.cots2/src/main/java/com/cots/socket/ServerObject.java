package com.cots.socket;

import java.util.HashMap;

/**
 * Cots framework
 * <p/>
 * User: chugh
 * Date: 2005-4-9
 * Time: 10:05:27
 * Version: 1.0
 */
public class ServerObject {
    protected HashMap attributes;

    public void setAttribute(String name,Object value){
        attributes.put(name,value);
    }

    public Object getAttribute(String name){
        return attributes.get(name);
    }

}
