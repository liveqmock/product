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
 * Time: 18:09:10
 * Version: 1.0
 */
public class CmdSubmit extends Command {
    private String SPNumber="09999"; //21 bytes;
    private String chargeNumber="8613350505050";    //21bytes;
    private String[] userNumber;  //21 bytes;
    private String corpID="11234";      //5bytes;
    private String serviceType="123"; //10 bytes;
    private byte feeType=1;
    private String feeValue="00099";    //6bytes;
    private String givenValue="00000";  //6bytes;
    private byte agentFlag=1;
    private byte morelatetoMTFlag;
    private byte priority=10;
    private String expireTime; //16bytes;
    private String scheduleTime;    //16bytes

    private byte reportFlag;

    private byte TP_pid=1;
    private byte TP_udhi=1;
    private byte messageCoding=15;
    private byte messageType;
    private int messageLength;
    private String messageContent;


    public CmdSubmit(){
        this.commandID = Command.SGIP_SUBMIT;
    }
    public void sendBody(BufferedOutputStream os) throws IOException {
        os.write(getText(SPNumber,21));
        os.write(getText(chargeNumber,21));
        os.write(userNumber.length);
        for(int i=0;i<userNumber.length;i++){
            os.write(getText(userNumber[i],21));
        }

        os.write(getText(corpID,5));
        os.write(getText(serviceType,10));
        os.write(feeType);
        os.write(getText(feeValue,6));
        os.write(getText(givenValue,6));
        os.write(agentFlag);
        os.write(morelatetoMTFlag);
        os.write(priority);
        os.write(getText(expireTime,16));
        os.write(getText(scheduleTime,16));
        os.write(reportFlag);
        os.write(TP_pid);
        os.write(TP_udhi);
        os.write(messageCoding);
        os.write(messageType);
        os.write(int2bytes(messageLength));
        os.write(messageContent.getBytes());
    }

    public void readBody(BufferedInputStream is) throws IOException {

    }

    public String getSPNumber() {
        return SPNumber;
    }

    public void setSPNumber(String SPNumber) {
        this.SPNumber = SPNumber;
    }

    public String getChargeNumber() {
        return chargeNumber;
    }

    public void setChargeNumber(String chargeNumber) {
        this.chargeNumber = chargeNumber;
    }

    public byte getUserCount() {
        return (byte)userNumber.length;
    }

    public String[] getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String[] userNumber) {
        this.userNumber = userNumber;
    }

    public String getCorpID() {
        return corpID;
    }

    public void setCorpID(String corpID) {
        this.corpID = corpID;
    }

    public byte getFeeType() {
        return feeType;
    }

    public void setFeeType(byte feeType) {
        this.feeType = feeType;
    }

    public String getFeeValue() {
        return feeValue;
    }

    public void setFeeValue(String feeValue) {
        this.feeValue = feeValue;
    }

    public String getGivenValue() {
        return givenValue;
    }

    public void setGivenValue(String givenValue) {
        this.givenValue = givenValue;
    }

    public byte getAgentFlag() {
        return agentFlag;
    }

    public void setAgentFlag(byte agentFlag) {
        this.agentFlag = agentFlag;
    }

    public byte getMorelatetoMTFlag() {
        return morelatetoMTFlag;
    }

    public void setMorelatetoMTFlag(byte morelatetoMTFlag) {
        this.morelatetoMTFlag = morelatetoMTFlag;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public byte getReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(byte reportFlag) {
        this.reportFlag = reportFlag;
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

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
        this.messageLength = messageContent.getBytes().length;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void sumLength(){
        totalLength = headerLength + 115+getUserCount()*21+getMessageLength();
    }
}
