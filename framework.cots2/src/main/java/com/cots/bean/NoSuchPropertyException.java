/**
 *all rights reserved,@copyright 2003
 */
package com.cots.bean;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-10-28
 * Time: 16:32:35
 */
public class NoSuchPropertyException extends Exception{
    public  NoSuchPropertyException(String property){
        super("No property:"+property);
    }

    public  NoSuchPropertyException(String className, String property){
        super("No property:"+property+" in class:"+className);
    }

    public  NoSuchPropertyException(String className, String property,String propertyType){
        super("No property:"+property+" of class:"+propertyType+" in class:"+className);
    }

}
