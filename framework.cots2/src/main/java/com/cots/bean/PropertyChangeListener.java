/**
 *all rights reserved,@copyright 2003
 */
package com.cots.bean;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-30
 * Time: 12:48:32
 * Version: 1.0
 */
public interface PropertyChangeListener {
    /**
     * add a changed property to this listener;
     * @param name
     */
    public void addChangedProperty(String name);


    /**
     * get all the changed properties names;
     */
    public void getChangedProperties();
}
