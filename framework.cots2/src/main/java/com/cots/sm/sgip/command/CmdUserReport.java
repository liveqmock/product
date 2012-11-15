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
 * Time: 18:52:46
 * Version: 1.0
 */
public class CmdUserReport extends Command {
    private String SPNumber;
    private String userNumber;
    private byte userCondition;


    public String getSPNumber() {
        return SPNumber;
    }

    public void setSPNumber(String SPNumber) {
        this.SPNumber = SPNumber;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public byte getUserCondition() {
        return userCondition;
    }

    public void setUserCondition(byte userCondition) {
        this.userCondition = userCondition;
    }

    public void sendBody(BufferedOutputStream os) throws IOException {
        os.write(getText(SPNumber,21));
        os.write(getText(userNumber,21));
        os.write(userCondition);
    }

    public void readBody(BufferedInputStream is) throws IOException {
        byte[] buf = new byte[21];
        int read = is.read(buf);
        SPNumber = new String(buf,0,read);

        read = is.read(buf);
        userNumber = new String(buf,0,read);

        userCondition = (byte)is.read();
    }

    public void sumLength(){
        totalLength = headerLength+ 43;
    }

}
