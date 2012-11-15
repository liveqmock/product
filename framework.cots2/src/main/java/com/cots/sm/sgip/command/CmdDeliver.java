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
 * Time: 18:18:26
 * Version: 1.0
 */
public class CmdDeliver extends Command {
    private String SPNumber; //21 bytes;
    private String userNumber;  //21 bytes;

    private byte TP_pid;
    private byte TP_udhi;
    private byte messageCoding;
    private byte messageType;
    private int messageLength;
    private String messageContent;

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

    public byte getTP_pid() {
        return TP_pid;
    }

    public void setTP_pid(byte TP_pid) {
        this.TP_pid = TP_pid;
    }

    public byte getTP_udhi() {
        return TP_udhi;
    }

    public void setTP_udhi(byte TP_udhi) {
        this.TP_udhi = TP_udhi;
    }

    public byte getMessageCoding() {
        return messageCoding;
    }

    public void setMessageCoding(byte messageCoding) {
        this.messageCoding = messageCoding;
    }

    public byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
    }

    public int getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(int messageLength) {
        this.messageLength = messageLength;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * send the body of the message
     *
     * @param os the socket output steam to write the message;
     * @throws IOException
     */
    public void sendBody(BufferedOutputStream os) throws IOException {
        os.write(getText(SPNumber,21));
        os.write(getText(userNumber,21));
        os.write(TP_pid);
        os.write(TP_udhi);
        os.write(this.messageCoding);
        os.write(this.messageType);
        os.write(int2bytes(this.messageLength));
        os.write(messageContent.getBytes());
    }

    /**
     * read the body of this command;
     *
     * @param is the socket input stream to read from;
     * @throws IOException
     */
    public void readBody(BufferedInputStream is) throws IOException {
        byte[] buf = new byte[21];
        int read = is.read(buf);
        SPNumber = new String(buf,0,read);
        read = is.read(buf);
        userNumber = new String(buf,0,read);
        TP_pid = (byte)is.read();
        TP_udhi = (byte)is.read();
        messageCoding = (byte)is.read();
        messageType = (byte)is.read();

        buf = new byte[4];
        is.read(buf);
        messageLength = bytes2int(buf);
        buf = new byte[messageLength];
        //todo:message type checking;

        messageContent = new String(buf);
    }

    public void sumLength(){
        totalLength = headerLength +50+messageLength;
    }

}
