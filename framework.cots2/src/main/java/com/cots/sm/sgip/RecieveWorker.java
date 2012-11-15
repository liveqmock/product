/**
 *all rights reserved,@copyright 2003
 */
package com.cots.sm.sgip;

import java.net.Socket;

/**
 * Description:
 *    A RecieveWorker is a work thread that process messages from
 * SMG(Internet Short message Gateway).
 *    A typical process is depcited as following:
 *       ->bind
 *       ->delvier (->UseRepoert)
 *       ->unbind;
 *
 * User: chugh
 * Date: 2005-8-5
 * Time: 8:48:40
 * Version: 1.0
 */
public class RecieveWorker extends Thread{
    protected boolean auth;
    protected Socket sock;

    public RecieveWorker(Socket sock){
        this.sock = sock;
    }

    public void run(){
        //process the message recieve;
    }

    protected void onBind(){

    }

    protected void onDeliver(){

    }

    protected void onUnbind(){

    }

    protected void onUserReport(){

    }
}
