/**
 *all rights reserved,@copyright 2003
 */
package com.cots.sm.sgip.command;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-3
 * Time: 18:08:40
 * Version: 1.0
 */
public class CmdUnbind extends Command{

    public CmdUnbind(){
        this.commandID = Command.SGIP_UNBIND;
    }

    public void sendBody(BufferedOutputStream os) throws IOException {

    }

    public void readBody(BufferedInputStream is) throws IOException {

    }

    public void sumLength(){
        totalLength = headerLength;
    }
}
