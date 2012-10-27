package com.dream.bizsdk.common.asyn;

import com.dream.bizsdk.common.pool.PoolItem;
import com.dream.bizsdk.common.pool.GenericPool;
import com.dream.bizsdk.common.blc.IBLContext;

import java.rmi.RemoteException;

/**
 * Pool of workers to perform asyn call to BLContext;
 * Date: 2004-3-2
 * Time: 21:14:39
 */
public class WorkersPool extends GenericPool {
    public static final int DEFAULT_WORKERS = 20;

    protected IBLContext context = null;

    public WorkersPool(IBLContext context) {
        this.context = context;
        try {
            //the config is located in BLContext <asynCall><workers></workers></asynCall>
            String asynWorkers = context.getConfigValue("asynCall", "workers");
            initialSize = Integer.valueOf(asynWorkers).intValue();
        } catch (RemoteException re) {
            initialSize = WorkersPool.DEFAULT_WORKERS;
        }
        try {
            create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * create a new PoolItem;
     *
     * @return
     */
    public PoolItem newPoolItem() {
        return new Worker(context);
    }
}
