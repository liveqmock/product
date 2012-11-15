/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-13
 * Time: 16:06:25
 */
public class ResourceMessage {
    private ResourceBundle resource;

    /**
     * intialize this object and load the underlying resource bundle;
     *
     * @param resName the resource bundle displayName;
     * @throws IOException
     */
    public ResourceMessage(String resName)throws IOException{
        init(resName);
    }

    /**
     * get a formated message by the key.
     *
     * @param key the message key;
     * @param values parameters to format the message;
     * @return
     */
    public String getMessage(String key,Object[] values){
        String message = (String)resource.getString(key);
        return MessageFormat.format(message,values);
    }

    /**
     *
     *
     * @param resName
     * @throws IOException
     */
    protected void init(String resName) throws IOException{
        resource = PropertyResourceBundle.getBundle(resName);
    }
}