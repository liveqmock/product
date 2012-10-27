/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.socket;

import com.dream.bizsdk.web.BLServlet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-16
 * Time: 18:08:03
 */
public class BLServerConfig {
    protected int port = 9001;
    protected int workerThreads = 30;
    protected InetAddress bindAddress;
    protected long timeout = 20000;
    protected BLServlet servlet;

    /**
     *
     */
    public BLServerConfig() {

    }

    /**
     * @param configFilePath
     */
    public BLServerConfig(String configFilePath) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(new File(configFilePath)));
            port = Integer.parseInt((String) prop.get("port"));
            workerThreads = Integer.parseInt((String) prop.get("workerThreads"));
            timeout = Long.parseLong((String) prop.get("timeout"));
            try {
                bindAddress = InetAddress.getByName((String) prop.get("timeout"));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param prop
     */
    public BLServerConfig(Properties prop) {
        port = Integer.parseInt((String) prop.get("port"));
        workerThreads = Integer.parseInt((String) prop.get("workerThreads"));
        timeout = Long.parseLong((String) prop.get("timeout"));
        try {
            bindAddress = InetAddress.getByName((String) prop.get("timeout"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        servlet = (BLServlet) prop.get("servlet");
    }

    /**
     * get the port number;
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * set the port number;
     *
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * get the count of the worker threads;
     *
     * @return
     */
    public int getWorkerThreads() {
        return this.workerThreads;
    }

    /**
     * set the worker threads count;
     *
     * @param count
     */
    public void setWorkerThreads(int count) {
        this.workerThreads = count;
    }

    /**
     * return the bind address of this server;
     *
     * @return
     */
    public InetAddress getBindAddress() {
        return this.bindAddress;
    }

    /**
     * set the bind address, in xxx.xxx.xxx.xxx format;
     *
     * @param name
     */
    public void setBindAddress(String name) {
        try {
            this.bindAddress = InetAddress.getByName(name);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the timeout value, in milliseconds
     *
     * @return
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * set the timeout value in milliseconds;
     *
     * @param timeout
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * get the underlying servlet;
     *
     * @return
     */
    public BLServlet getServlet() {
        return servlet;
    }

    /**
     * set the underlying blservlet
     *
     * @param servlet
     */
    public void setServlet(BLServlet servlet) {
        this.servlet = servlet;
    }
}
