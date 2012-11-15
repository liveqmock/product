/**
 *all rights reserved,@copyright 2003
 */
package com.cots.sm.sgip.command;

import com.cots.sm.sgip.SGIPMessage;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-3
 * Time: 18:01:26
 * Version: 1.0
 */
public abstract class Command extends SGIPMessage{
    private static int NO=100;

    public static int SGIP_BIND=0x1;
    public static int SGIP_UNBIND=0x2;
    public static int SGIP_SUBMIT=0x3;
    public static int SGIP_DELIVER=0x4;
    public static int SGIP_REPORT=0x5;
    public static int SGIP_TRACE=0x1000;
    public static int SGIP_USERRPT=0x11;

    public Command(){
        this.commandID = getNextSeq();
    }

    public synchronized static int getNextSeq(){
        return NO++;
    }
}
