/**
 *all rights reserved,@copyright 2003
 */
package com.cots.exp.freeexp;

import java.util.HashMap;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-28
 * Time: 17:27:24
 * Version: 1.0
 */
public class FreeExp {

    private ArrayList segs;

    private String name;

    public FreeExp() {
        segs = new ArrayList();
    }

    public FreeExp(String name) {
        segs = new ArrayList();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSeg(FreeExpSeg seg){
        segs.add(seg);
    }

    public String invoke(Object o,int index)
            throws IllegalAccessException, InvocationTargetException{
        StringBuffer sb = new StringBuffer(256);
        int count = segs.size();
        for(int i=0;i<count;i++){
            FreeExpSeg seg = (FreeExpSeg)segs.get(i);
            sb.append(seg.getValue(o,index));
        }
        return new String(sb);
    }
}
