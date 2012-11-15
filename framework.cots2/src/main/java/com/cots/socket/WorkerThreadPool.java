/**
 *all rights reserved,@copyright 2003
 */
package com.cots.socket;

import java.util.List;
import java.util.ArrayList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-16
 * Time: 18:09:21
 */
public class WorkerThreadPool {

    List workers = new ArrayList();

    private int workersCount = 40;

    public WorkerThreadPool() {

    }

    public WorkerThreadPool(int workers) {
        this.workersCount = workers;
    }

    public void create() {
        for (int i = 0; i < workersCount; i++) {
            newWorker();
        }
    }

    public final synchronized WorkerThread newWorker() {
        WorkerThread wt = new WorkerThread(this);
        workers.add(wt);
        return wt;
    }

    public synchronized WorkerThread getWorker() {
        if (workers.size() > 0) {
            return (WorkerThread) workers.remove(0);
        } else {
            return newWorker();
        }
    }

    public synchronized void addWorker(WorkerThread wt) {
        workers.add(wt);
    }
}