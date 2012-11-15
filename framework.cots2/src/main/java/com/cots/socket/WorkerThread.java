/**
 *all rights reserved,@copyright 2003
 */
package com.cots.socket;


import java.net.Socket;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-16
 * Time: 18:09:54
 */
public class WorkerThread extends Thread {
    private Socket s;
    private WorkerThreadPool pool;
    private SocketServlet servlet;

    public WorkerThread(WorkerThreadPool pool) {
        this.pool = pool;
    }

    public void setSocket(Socket s) {
        this.s = s;
    }

    public void setServlet(SocketServlet servlet) {
        this.servlet = servlet;
    }

    /**
     * Start this thread. This thread will first recieve and parse data
     * from the socket,then sendBody a direct request to the BLServlet. On
     * recieving the direct request, the servlet will process accoding to
     * the configuration. When the direct request is finished, this thread
     * will then write back the BLResult object.
     */
    public void run() {
        try {
            ServerRequest req = SocketDataParser.parse(s);
            ServerResponse res = new ServerResponse();
            servlet.process(req,res);
            //add self to the pool;
            pool.addWorker(this);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}