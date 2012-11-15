/**
 *all rights reserved,@copyright 2003
 */
package com.cots.sm.sgip;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.net.Socket;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-3
 * Time: 19:06:07
 * Version: 1.0
 */
public abstract class SGIPMessage {
    //reserved;
    protected byte[] reserve={0,0,0,0,0,0,0,0};

    protected int totalLength;
    protected int commandID;
    protected int sourceNodeNO;
    protected int timestamp;
    protected int seqNO;

    protected int headerLength=28;

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public int getCommandID() {
        return commandID;
    }

    public void setCommandID(int commandID) {
        this.commandID = commandID;
    }

    public int getSourceNodeNO() {
        return sourceNodeNO;
    }

    public void setSourceNodeNO(int sourceNodeNO) {
        this.sourceNodeNO = sourceNodeNO;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getSeqNO() {
        return seqNO;
    }

    public void setSeqNO(int seqNO) {
        this.seqNO = seqNO;
    }

    /**
     * send this messsage via a socket;
     *
     * @param sock
     * @throws IOException
     */
    public void send(Socket sock) throws IOException {
        BufferedOutputStream os = new BufferedOutputStream(sock.getOutputStream());
        this.sumLength();
        sendHeader(os);
        sendBody(os);
        os.write(reserve);
        os.flush();
    }

    /**
     * send the message heder;
     *
     * @param os
     * @throws IOException
     */
    protected void sendHeader(BufferedOutputStream os) throws IOException{
        os.write(int2bytes(totalLength));
        os.write(int2bytes(commandID));
        os.write(int2bytes(sourceNodeNO));
        os.write(int2bytes(timestamp));
        os.write(int2bytes(seqNO));
    }

    /**
     * send the body of this message.
     *
     * @param os
     * @throws IOException
     */
    public abstract void sendBody(BufferedOutputStream os) throws IOException;

    /**
     * read a message from a socket;
     *
     * @param sock
     * @throws IOException
     */
    public void read(Socket sock) throws IOException{
        BufferedInputStream is = new BufferedInputStream(sock.getInputStream());
        readHeader(is);
        readBody(is);
        is.read(reserve);
    }

    /**
     * read the message hader;
     *
     * @param is
     * @throws IOException
     */
    public void readHeader(BufferedInputStream is) throws IOException{
        byte[] bytes = new byte[4];

        if(is.read(bytes)==4){
            totalLength = bytes2int(bytes);
        }

        if(is.read(bytes)==4){
            this.commandID = bytes2int(bytes);
        }

        if(is.read(bytes)==4){
            this.sourceNodeNO = bytes2int(bytes);
        }

        if(is.read(bytes)==4){
            this.timestamp = bytes2int(bytes);
        }

        if(is.read(bytes)==4){
            this.seqNO = bytes2int(bytes);
        }
    }

    /**
     * read the message body;
     *
     * @param is
     * @throws IOException
     */
    public abstract void readBody(BufferedInputStream is) throws IOException;

    public abstract void sumLength();

    public static byte[] getText(String str,int len){
        byte[] bytes = new byte[len];
        if(str!=null){
            byte[] content = str.getBytes();
            if(content.length<len){
                System.arraycopy(content,0,bytes,0,content.length);
                for(int i=content.length;i<len;i++){
                    bytes[i]='\0';
                }
            }else{
                System.arraycopy(content,0,bytes,0,len);
            }
        }
        return bytes;
    }

    public static byte[] int2bytes(int v){
        byte[] bytes = new byte[4];
        bytes[3]= (byte)(v & 0x000000FF);
        bytes[2]= (byte)((v & 0x0000FF00)>>8);
        bytes[1]= (byte)((v & 0x00FF0000)>>16);
        bytes[0]= (byte)((v & 0xFF000000)>>>24);

        return bytes;
    }

    public static int  bytes2int(byte[] bytes){
        int v =0xFFFFFFFF;

        int s = (0xFF&bytes[3]);
        v=s;

        s = (0xFF&bytes[2]);
        s=((s<<8));
        v|=s;

        s = (0xFF&bytes[1]);
        s=(s<<16);
        v|=s;

        s = (0xFF&bytes[0]);
        s=s<<24;
        v|=s;

        return v;
    }

    public static void main(String[] argc){
        System.out.println(bytes2int(int2bytes(12345)));
    }
}
