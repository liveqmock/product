/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.pool;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-12
 * Time: 13:24:14
 */
public abstract class KeyedPool extends AbstractPool {
    private HashMap items = new HashMap();

    public void addPoolItem(PoolItem pi) {
        synchronized (items) {
            items.put(pi.getName(), pi);
        }
    }

    public PoolItem getPoolItem() {
        return null;
    }

    public PoolItem getPoolItem(String name) {
        PoolItem pi = null;
        pi = (PoolItem) items.get(name);
        if (pi == null) {
            try {
                pi = newPoolItem(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pi != null) {
            addPoolItem(pi);
        }
        return pi;
    }

    public synchronized void removeItem(PoolItem pi) {
        synchronized (items) {
            items.remove(pi.getName());
        }
    }

    public void freeItem(PoolItem pi) {

    }

    public int size() {
        return items.size();
    }

    public PoolItem newPoolItem() {
        return null;
    }

    public synchronized void release() {
        synchronized (items) {
            Iterator it = items.values().iterator();
            while (it.hasNext()) {
                ((PoolItem) it.next()).release();
            }
        }
    }
}