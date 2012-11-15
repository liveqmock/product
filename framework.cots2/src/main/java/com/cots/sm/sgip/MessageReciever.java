/**
 *all rights reserved,@copyright 2003
 */
package com.cots.sm.sgip;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-3
 * Time: 19:31:42
 * Version: 1.0
 */
public class MessageReciever extends Thread{
    private int listenPort;

    //default to 50;
    private int backLog = 50;

    private ServerSocket ss;

    private boolean cont = true;

    public int getListenPort() {
        return listenPort;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public int getBackLog() {
        return backLog;
    }

    public void setBackLog(int backLog) {
        this.backLog = backLog;
    }

    public void start(){
        try {
            ss = new ServerSocket(listenPort, backLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        Socket work;
        try{
            while(cont){
                work = ss.accept();
                new RecieveWorker(work).start();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
