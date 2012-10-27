/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.socket;

import com.dream.bizsdk.web.BLServlet;

import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-16
 * Time: 17:52:02
 */
public class BLServer extends Thread {
    private BLServlet servlet;
    private ServerSocket ss;
    private int port = 9001;
    private InetAddress bindAddress;
    private WorkerThreadPool wtp;
    private int backlog = 100;

    public BLServer() {

    }

    public BLServer(BLServlet serv) {
        this.servlet = serv;
    }

    /**
     * set the underlying servlet;
     *
     * @param serv
     */
    public void setServlet(BLServlet serv) {
        this.servlet = serv;
    }

    /**
     * get the underlying servlet;
     *
     * @return
     */
    public BLServlet getServlet() {
        return this.servlet;
    }

    /**
     * get the bindaddress
     *
     * @return
     */
    public InetAddress getBindAddress() {
        return bindAddress;
    }

    /**
     * set the bind address;
     *
     * @param addess
     */
    public void setBindAddress(InetAddress addess) {
        this.bindAddress = addess;
    }

    /**
     * get the port;
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * set port number;
     *
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * init this server.
     *
     * @param config
     */
    public void init(BLServerConfig config) {
        setPort(port = config.getPort());
        setBindAddress(bindAddress = config.getBindAddress());
        BLServlet bl = config.getServlet();
        if (bl != null) {
            setServlet(bl);
        }
        wtp = new WorkerThreadPool(config.getWorkerThreads(), servlet);
    }

    /**
     * run this server;
     */
    public void run() {
        Socket s;

        //init server socket;
        try {
            if (bindAddress == null) {
                ss = new ServerSocket(port, backlog);
            } else {
                ss = new ServerSocket(port, backlog, bindAddress);
            }
            ss.setReuseAddress(true);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        //listen to clients bind request;
        while (true) {
            try {
                s = ss.accept();
                WorkerThread wt = wtp.getWorker();
                wt.setSocket(s);
                wt.run();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}