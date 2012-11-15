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
 * Time: 18:02:32
 * Version: 1.0
 */
public class CmdBind extends Command {
    private byte loginType;
    private String loginName="1111";
    private String loginPassword="1234";

    public CmdBind() {
        this.commandID = Command.SGIP_BIND;
        this.loginType = 1;
    }

    public CmdBind(byte loginType) {
        this.loginType = loginType;
    }


    public int getLoginType() {
        return loginType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public void sendBody(BufferedOutputStream os) throws IOException {
        os.write(loginType);
        os.write(getText(loginName,16));
        os.write(getText(loginPassword,16));
    }

    public void readBody(BufferedInputStream is) throws IOException {
        byte[] buf = new byte[16];
        loginType = (byte)is.read();
        int read = is.read(buf);
        loginName = new String(buf,0,read);
        read = is.read(buf);
        loginPassword = new String(buf,0,read);
    }

    public void sumLength(){
        totalLength = headerLength + 33;
    }
}
