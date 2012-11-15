/**
 *all rights reserved,@copyright 2003
 */
package com.cots.blc;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-21
 * Time: 9:18:04
 * Version: 1.0
 */
public class BLEntry {
    private String className;
    private String methodName;
    private String description;

    public BLEntry(String methodName,String desc){
        this.methodName = methodName;
        this.description = desc;
    }

    public BLEntry(String className,Element ele){
        this.className = className;
        String temp = ele.getAttribute("method");
        if(temp!=null && temp.length()>0){
            methodName = temp;
        }
        temp = ele.getAttribute("desc");
        if(temp!=null && temp.length()>0){
            description = temp;
        }
    }

    /**
     * get the class displayName of this bl method;
     *
     * @return
     */
    public String getClassName() {
        return className;
    }

    /**
     * get the method displayName;
     * @return
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * get the description of this bl;
     * @return
     */
    public String getDescription() {
        return description;
    }
}
