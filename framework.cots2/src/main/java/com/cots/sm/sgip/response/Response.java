/**
 *all rights reserved,@copyright 2003
 */
package com.cots.sm.sgip.response;

import com.cots.sm.sgip.SGIPMessage;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-3
 * Time: 18:17:29
 * Version: 1.0
 */
public class Response extends SGIPMessage{
    public static final int SGIP_BIND_RESP=0x80000001;
    public static final int SGIP_UNBIND_RESP=0x80000002;
    public static final int SGIP_SUBMIT_RESP=0x80000003;
    public static final int SGIP_DELIVER_RESP=0x80000004;
    public static final int SGIP_REPORT_RESP=0x80000005;
    public static final int SGIP_USERRPT_RESP=0x80000011;
    public static final int SGIP_TRACE_RESP=0x80001000;

    public static final int SUCC=0;

    protected byte result;

    public byte getResult() {
        return result;
    }

    public void setResult(byte result) {
        this.result = result;
    }

    public void sendBody(BufferedOutputStream os) throws IOException {
        os.write(result);
    }

    public void readBody(BufferedInputStream is) throws IOException {
        result = (byte)is.read();
    }

    public void sumLength(){
        totalLength = headerLength+1 ;
    }

}