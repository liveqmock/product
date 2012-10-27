/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.socket;

import com.dream.bizsdk.common.blc.BLResult;
import com.dream.bizsdk.web.BLServlet;

import java.net.Socket;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-16
 * Time: 18:09:54
 */
public class WorkerThread extends Thread {
    protected BLServlet servlet;
    protected BLResult br;
    protected Socket s;
    protected WorkerThreadPool pool;

    public WorkerThread(WorkerThreadPool pool) {
        this.pool = pool;
    }

    public WorkerThread(WorkerThreadPool pool, BLServlet servlet) {
        this.pool = pool;
        this.servlet = servlet;
    }

    public void setSocket(Socket s) {
        this.s = s;
    }

    public BLResult getResult() {
        return this.br;
    }

    /**
     * Start this thread. This thread will first recieve and parse data
     * from the socket,then send a direct request to the BLServlet. On
     * recieving the direct request, the servlet will process accoding to
     * the configuration. When the direct request is finished, this thread
     * will then write back the BLResult object.
     */
    public void run() {
        try {
            SocketDataParser sdp = new SocketDataParser();
            BLServerRequest req = sdp.parse(s);
            this.br = servlet.processDirectRequest(req.reqName, req.reqData, req.sesData);
            SocketDataWriter.write(s, br);
            //should close the socket??????
            s.close();

            //add self to the pool;
            pool.addWorker(this);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}