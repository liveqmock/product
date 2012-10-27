/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.pool;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-12
 * Time: 13:01:04
 */
public abstract class AbstractPool {
    private String name;

    public AbstractPool() {

    }

    public AbstractPool(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract PoolItem newPoolItem() throws Exception;

    public abstract PoolItem newPoolItem(String name) throws Exception;

    public abstract void addPoolItem(PoolItem pi);

    public abstract PoolItem getPoolItem();

    public abstract PoolItem getPoolItem(String name);

    public abstract void removeItem(PoolItem pi);

    public abstract void freeItem(PoolItem pi);

    public abstract void release();

    public abstract int size();
}
