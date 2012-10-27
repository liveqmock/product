/*
 * PoolItem.java
 *
 * Created on 2003年3月18日, 上午11:19
 */

package com.dream.bizsdk.common.pool;

import java.util.Date;

/**
 * @author chuguanghua
 */
public class PoolItem {
    //member variables;
    protected int _ID = -1;
    protected String _name = null;
    protected boolean _isBusy = false;
    protected boolean _isInited = false;
    protected AbstractPool _pool = null;
    protected Date _freeSince = new Date();
    protected Date _busySince = new Date();
    protected boolean removed;

    /**
     * Creates a new instance of PoolItem
     */
    public PoolItem() {
    }

    public PoolItem(AbstractPool pool) {
        _pool = pool;
    }

    public PoolItem(String name) {
        _name = name;
    }

    public PoolItem(AbstractPool pool, String name) {
        _pool = pool;
        _name = name;
    }

    /**
     * get the name of this PoolItem object;
     *
     * @return String, the name of the object.
     */
    public String getName() {
        return _name;
    }

    /**
     * set the object's name.
     *
     * @param name the new name of the object.
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * check if the object is busy.
     *
     * @return true if the object is busy.
     *         false otherwise.
     */
    public boolean isBusy() {
        return _isBusy;
    }

    /**
     * check if the object have been initialized.
     *
     * @return true if this object has been initialized.
     *         false otherwise.
     */
    public boolean isInited() {
        return _isInited;
    }

    /**
     * set the object's busy status to be true.
     */
    protected void use() {
        _isBusy = true;
        _busySince = new Date();
    }

    public Date getBusySince() {
        return _busySince;
    }

    /**
     * free this object.
     */
    public void free() {
        if (!removed) {
            _pool.freeItem(this);
        } else {
            release();
        }
    }

    public Date getFreeSince() {
        return _freeSince;
    }

    /**
     * release this object. In this class,
     * The function does nothing.
     */
    public void release() {
    }

    /**
     * get the owner Pool object of this Pool Item. The owner may be null.
     *
     * @return Pool, the owner Pool object.
     */
    public AbstractPool getOwnerPool() {
        return _pool;
    }

    /**
     * set the new Owner Pool of the Object.
     *
     * @param pool the new owner Pool object.
     */
    public void setOwnerPool(AbstractPool pool) {
        _pool = pool;
    }

    /**
     * initialize this object.
     */
    public void init() {
        _isInited = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
