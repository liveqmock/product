/**
 *all rights reserved,@copyright 2003
 */
package com.cots.sm.sgip;

import com.cots.sm.sgip.command.CmdBind;
import com.cots.sm.sgip.command.CmdSubmit;
import com.cots.sm.sgip.command.CmdUnbind;
import com.cots.sm.sgip.response.Response;
import com.cots.sm.sgip.response.ResUnbind;

import java.net.Socket;
import java.net.InetAddress;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-3
 * Time: 19:09:41
 * Version: 1.0
 */
public class MessageSender {
    private String smgIP;
    private int smgPort;

    private String loginName;
    private String loginPwd;

    private String SPNumber;

    public String getSmgIP() {
        return smgIP;
    }

    public void setSmgIP(String smgIP) {
        this.smgIP = smgIP;
    }

    public int getSmgPort() {
        return smgPort;
    }

    public void setSmgPort(int smgPort) {
        this.smgPort = smgPort;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getSPNumber() {
        return SPNumber;
    }

    public void setSPNumber(String SPNumber) {
        this.SPNumber = SPNumber;
    }

    public void sendMessage(String userNumber,String message) throws IOException {
        Socket sock = new Socket(InetAddress.getByName(smgIP),smgPort);

        //binding;
        CmdBind bind = new CmdBind();
        bind.setLoginName(loginName);
        bind.setLoginName(loginPwd);
        bind.send(sock);

        //get the bind_resp;
        Response res = new Response();
        res.read(sock);
        if(res.getResult() !=0 ){   //error;
            System.out.println("bind failed!");
        }else{
            System.out.println("binded,begin to send message");
        }

        System.out.println(Integer.toHexString(res.getCommandID()));

        //construct a message;
        CmdSubmit submit = new CmdSubmit();

        submit.setUserNumber(new String[]{userNumber});

        submit.setMessageContent(message);

        submit.setExpireTime(new SimpleDateFormat("yyMMddHHMMSS").format(new Date())+"032+");

        //send the mesage;
        System.out.println("sending...");
        submit.send(sock);

        res.read(sock);
        if(res.getResult() !=0 ){   //error;
            System.out.println("sorry, failed to send!");
        }else{
            System.out.println("message sent!");
        }

        System.out.println("unbinding...");
        CmdUnbind unbind = new CmdUnbind();
        unbind.send(sock);


        new ResUnbind().read(sock);
        System.out.println("unbinded!");
    }
}
