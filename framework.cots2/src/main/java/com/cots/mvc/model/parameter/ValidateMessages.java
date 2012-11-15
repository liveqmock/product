/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import java.util.PropertyResourceBundle;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * Description:
 * <p/>
 * User: chugh
 * Date: 2005-8-12
 * Time: 19:22:58
 * Version: 1.0
 */
public class ValidateMessages {
    public static final String FILE_NAME="ValidateMessages.properties";

    private static PropertyResourceBundle messages;

    static{
        try{
            //the FILE_NAME file should locate in the root of the classpath;
            InputStream is = ValidateMessages.class.getClassLoader().
                    getResourceAsStream(FILE_NAME);

            if(is!=null){
                messages = new PropertyResourceBundle(is);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     *
     * @param key
     * @param params
     * @return
     */
    public static String getMessage(String key,Object[] params){
        String message=null;

        if(messages!=null){
            String tmplt = messages.getString(key);
            if(tmplt!=null){
                message = MessageFormat.format(tmplt,params);
            }
        }
        return message;
    }
}