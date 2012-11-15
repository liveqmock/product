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
 * Time: 18:20:38
 * Version: 1.0
 */
public class CmdReport extends Command {
    private String SubmitSequenceNumber;    //12 bytes;
    private byte reportType;
    private String userNumber;
    private byte status;
    private byte errorCode;

    public String getSubmitSequenceNumber() {
        return SubmitSequenceNumber;
    }

    public void setSubmitSequenceNumber(String submitSequenceNumber) {
        SubmitSequenceNumber = submitSequenceNumber;
    }

    public byte getReportType() {
        return reportType;
    }

    public void setReportType(byte reportType) {
        this.reportType = reportType;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getErrorCode() {
        return errorCode;
    }

    /**
     *
     * @param errorCode
     */
    public void setErrorCode(byte errorCode) {
        this.errorCode = errorCode;
    }

    /**
     *
     * @param os
     * @throws IOException
     */
    public void sendBody(BufferedOutputStream os) throws IOException {
        os.write(getText(SubmitSequenceNumber,12));
        os.write(reportType);
        os.write(getText(userNumber,21));
        os.write(status);
        os.write(errorCode);
    }

    /**
     *
     * @param is
     * @throws IOException
     */
    public void readBody(BufferedInputStream is) throws IOException {
        byte[] buf = new byte[12];
        int read = is.read(buf);
        SubmitSequenceNumber = new String(buf,0,read);
        reportType = (byte)is.read();
        buf = new byte[21];
        read = is.read(buf);
        userNumber = new String(buf,0,read);
        status = (byte)is.read();
        errorCode = (byte)is.read();
    }

    /**
     *
     */
    public void sumLength(){
        totalLength = headerLength ;
    }
}
