/**
 *all rights reserved,@copyright 2003
 */
package com.cots.sm;

import java.util.ArrayList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-8-11
 * Time: 8:34:09
 * Version: 1.0
 */
public class MessageCenter {
    public final static String PROTOCOL_SGIP="sgip";
    public final static String PROTOCOL_CMPP="cmpp";

    //the value must be one of "sgip" or "cmpp"
    private String protocol;

    //message incoming listeners list;
    private ArrayList listeners;

    protected void sendMessage(String userNumber,String message){

    }

    protected void addMessageListener(MessageListener l){
       listeners.add(l);
    }
}
