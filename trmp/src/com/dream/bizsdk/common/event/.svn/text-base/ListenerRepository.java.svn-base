/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.event;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-24
 * Time: 14:35:28
 */
public class ListenerRepository {
    private HashMap listeners = new HashMap();


    public ListenerRepository() {

    }

    /**
     * add a listener to a specified event;
     *
     * @param eventName the name of the vent;
     * @param l         the Listener to the event;
     */
    public synchronized void addListener(String eventName, EventListener l) {
        if (l != null) {
            ArrayList al = (ArrayList) listeners.get(eventName);
            if (al == null) {
                al = new ArrayList();
                listeners.put(eventName, al);
            }
            al.add(l);
        }
    }

    /**
     * delete all the listeners to a sepcified event;
     *
     * @param eventName the name of the event;
     * @return tha list of the deleted listeners;
     */

    public synchronized ArrayList delListeners(String eventName) {
        return (ArrayList) listeners.remove(eventName);
    }

    /**
     * delete a specified listener;
     *
     * @param eventName    the name of the event;
     * @param listenerName the name of the listener;
     * @return the deleted Listener, null if there is no this listener;
     */
    public synchronized EventListener delListener(String eventName, String listenerName) {
        EventListener el = null;
        ArrayList al = (ArrayList) listeners.get(eventName);
        if (al != null) {
            int count = al.size();
            for (int i = 0; i < count; i++) {
                EventListener el2 = (EventListener) al.get(i);
                if (el2 != null) {
                    String lname = el2.getName();
                    if (lname != null && lname.compareToIgnoreCase(listenerName) == 0) {
                        el = el2;
                        al.remove(i);
                        break;
                    }
                }
            }
        }
        return el;
    }

    /**
     * get all the listeners to a specified event;
     *
     * @param eventName the name of the event;
     * @return the List of all the Listeners;
     */
    public synchronized ArrayList getListeners(String eventName) {
        ArrayList al = (ArrayList) listeners.get(eventName);
        if (al == null) {
            al = new ArrayList();
        }
        return al;
    }
}
