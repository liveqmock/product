/**
 *all rights reserved,@copyright 2003
 */
package com.cots.socket;

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
public class SocketServer extends Thread {
    private ServerSocket ss;
    private SocketServlet servlet;
    private int port = 9001;
    private InetAddress bindAddress;
    private WorkerThreadPool wtp;
    private int backlog = 100;

    public SocketServer() {

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

    public SocketServlet getServlet() {
        return servlet;
    }

    public void setServlet(SocketServlet servlet) {
        this.servlet = servlet;
    }

    /**
     * init this server.
     *
     * @param config
     */
    public void init(ServerConfig config) {
        setPort(port = config.getPort());
        setBindAddress(bindAddress = config.getBindAddress());
        wtp = new WorkerThreadPool(config.getWorkerThreads());
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

        //listen to clients binding request;
        while (true) {
            try {
                s = ss.accept();
                WorkerThread wt = wtp.getWorker();
                wt.setSocket(s);
                wt.setServlet(servlet);
                wt.run();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}