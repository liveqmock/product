/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.event;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Description:
 * This class keeps a list of ECAObject;
 * User: chuguanghua
 * Date: 2004-5-12
 * Time: 10:44:57
 */
public class ECAList {
    private HashMap list = new HashMap();

    public ECAList() {

    }

    /**
     * add an ECAObject to this list.
     *
     * @param eca the ECAObject to be added;
     */
    public void addECA(ECAObject eca) {
        String eventName = eca.getEventName();
        ArrayList al = (ArrayList) list.get(eventName);
        if (al == null) {
            al = new ArrayList();
            list.put(eventName, al);
        }
        al.add(eca);
    }

    /**
     * @param l
     */
    public void addECAList(ECAList l) {
        int i = 0;
        ECAObject[] a = l.toArray();
        while (i < a.length) {
            addECA(a[i++]);
        }
    }


    /**
     * get the array of ECAObject under a specified eventName;
     *
     * @param eventName
     * @return the ArrayList of ECAObject mathes to this eventName;
     */
    public ArrayList getActions(String eventName) {
        return (ArrayList) list.get(eventName);
    }

    /**
     * @return
     */
    public int size() {
        return list.size();
    }

    /**
     * @return
     */
    public ECAObject[] toArray() {
        int size = size();
        return (ECAObject[]) list.values().toArray(new ECAObject[size]);
    }
}
