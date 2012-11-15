/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.ejb;

import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-24
 * Time: 10:36:44
 * Version: 1.0
 */
public class EJBHomeCache{
    private static HashMap homes = new HashMap(64);

    public static Object get(String name){
        return homes.get(name);
    }

    public static void put(String name,Object home){
        homes.put(name,home);
    }

    public static Object remove(String name){
        return homes.remove(name);
    }

    public static int size(){
        return homes.size();
    }
}
