/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.pool;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p/>
 * User: chuguanghua Date: 2004-2-12 Time: 13:06:32
 */
public abstract class GenericPool extends AbstractPool {

	private static String flag = "";
	protected int initialSize;
	protected int maxSize;
	protected int currentSize;
	private List al = new ArrayList();

	public GenericPool() {
		initialSize = 10;
		maxSize = 10;
	}

	public GenericPool(String name) {
		super(name);
		initialSize = 10;
		maxSize = 10;
	}

	public GenericPool(String name, int initialSize, int maxSize) {
		super(name);
		this.initialSize = initialSize;
		this.maxSize = maxSize;
	}

	/**
	 * create the pool object; This will call the newPoolItem() method of the
	 * subclass; (Template pattern);
	 * 
	 * @throws Exception
	 */
	protected void create() throws Exception {

		synchronized (flag) {
			PoolItem pi = null;
			// cleat this pool first;
			al.clear();
			for (int i = 0; i < initialSize; i++) {
				pi = newPoolItem();
				pi.setOwnerPool(this);
				al.add(pi);
			}
			// count of the pool size;
			currentSize = al.size();
		}
	}

	/**
	 * Get the iniitial size of this pool object;
	 * 
	 * @return
	 */
	public int getInitialSize() {
		return initialSize;
	}

	/**
	 * set the initial size of the PoolObject;
	 * 
	 * @param initialSize
	 */
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	/**
	 * Get the max size of the pool object;
	 * 
	 * @return
	 */
	public int getMaxSize() {
		return this.maxSize;
	}

	/**
	 * Set the max size of the pool object;
	 * 
	 * @param maxSize
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * Get the totoal count of poolitem object in this pool object;
	 * 
	 * @return
	 */
	public int size() {
		return currentSize;
	}

	/**
	 * add a pool item objec to this pool object;
	 * 
	 * @param pi
	 */
	public synchronized void addPoolItem(PoolItem pi) {
		al.add(pi);
	}

	/**
	 * Get a pool item from this pool object;
	 * 
	 * @return
	 */
	public PoolItem getPoolItem(boolean remove) {

		synchronized (flag) {
			int size;
			PoolItem pi = null;
			while ((size = al.size()) == 0) {
				try {
					if (currentSize < maxSize) {
						pi = newPoolItem();
						if (!remove) {
							currentSize++;
						} else {
							pi.setRemoved(true);
						}
						return pi;
					}
					wait();
				} catch (InterruptedException ie) {
					return null;
				} catch (Exception e) {

				}
			}
			pi = (PoolItem) al.remove(size - 1);
			if (remove) {
				currentSize--;
				pi.setRemoved(true);
			}
			return pi;
		}
	}
	
	/**
	 * @return
	 */
	public PoolItem getPoolItem() {
		return getPoolItem(false);
	}

	/**
	 * Get a named pool item object from this pool.
	 * 
	 * @param name
	 * @return
	 */
	public PoolItem getPoolItem(String name) {
		return null;
	}

	/**
	 * free a pool item from this pool;
	 * 
	 * @param pi
	 */
	public synchronized void freeItem(PoolItem pi) {
		al.add(pi);
		notify();
	}

	/**
	 * remove a pool item from thsi pool object;
	 * 
	 * @param pi
	 */
	public synchronized void removeItem(PoolItem pi) {
		int size = al.size();
		for (int i = 0; i < size; i++) {
			if (al.get(i).equals(pi)) {
				al.remove(i);
				i--;
				size--;
			}
		}
	}

	/**
	 * release this pool.
	 */
	public synchronized void release() {
		int size = al.size();
		while (size > 0) {
			PoolItem item = (PoolItem) al.get(0);
			item.release();
			al.remove(0);
			size--;
		}
	}

	public PoolItem newPoolItem(String name) {
		return null;
	}
}