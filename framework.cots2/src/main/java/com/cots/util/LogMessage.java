/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-20
 * Time: 13:16:55
 * Version: 1.0
 */
public class LogMessage {
    private static ResourceMessage logMessages;

    static{
        try{
            logMessages = new ResourceMessage("cotsLogResource");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * get a message;
     *
     * @param key
     * @param params
     * @return
     */
    public static String get(String key,Object[] params){
        String msg = null;
        if(logMessages!=null){
            msg = logMessages.getMessage(key,params);
        }

        if(msg == null){
            msg = key;
        }

        return msg;
    }
}