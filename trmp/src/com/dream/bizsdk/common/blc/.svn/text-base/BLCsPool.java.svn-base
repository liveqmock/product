/*本软件版权由divine所有，并保留所有权利。
 * @copyright2001
 */

package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.pool.PoolItem;
import com.dream.bizsdk.common.pool.KeyedPool;
import org.apache.log4j.Logger;

/**
 * @author divine
 */
public class BLCsPool extends KeyedPool {

    protected BLContext _context = null;
    private Logger log = Logger.getLogger("com.dream.blcspool");

    /**
     * Creates a new instance of BeanPool
     */
    public BLCsPool(BLContext context) {
        _context = context;
    }

    /**
     * create a new BLC by name,this name should be a full class name.
     *
     * @param className the full class name of the BLC to be created.
     * @return the PoolItem reference to a BLC object;
     */
    public PoolItem newPoolItem(String className) {
        try {
            AbstractBLC blc = (AbstractBLC) Class.forName(className).newInstance();
            blc.setName(className);
            blc.init(_context);
            return (PoolItem) blc;
        } catch (Exception e) {
            log.error("can't create blc object of class " + className, e);
            return null;
        }
    }
}