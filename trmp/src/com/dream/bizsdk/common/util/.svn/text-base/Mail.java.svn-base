/*
 * Mail.java
 *
 * Created on 2003年11月11日, 下午4:47
 */

package com.dream.bizsdk.common.util;

import com.dream.bizsdk.common.smtp.SMTP;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.blc.BLContext;

/**
 * @author chuguanghua
 */
public class Mail {
    protected BLContext _context;

    protected String smtpServer;
    protected String smtpPort;
    protected String user;
    protected String password;
    protected String mailAccount;

    /**
     * default constructor
     */
    public Mail() {
    }

    /**
     * Creates a new instance of Mail
     */
    public Mail(BLContext context) {
        setBLContext(context);
    }

    /**
     * set the blcontext of this mail object;
     */
    public void setBLContext(BLContext context) {
        //set the next context;
        _context = context;
        smtpServer = _context.getConfigValue("mailproxy", "smtpServer");
        smtpPort = _context.getConfigValue("mailproxy", "smtpPort");
        user = _context.getConfigValue("mailproxy", "smtpUser");
        password = _context.getConfigValue("mailproxy", "smtpPassword");
        mailAccount = _context.getConfigValue("mailproxy", "mailAccount");
    }

    /**
     * Get the BLContext object that this object is running within
     */
    public BLContext getBLContext() {
        return _context;
    }

    /**
     * in tos(toID,toType)-records
     * in ccs(ccID,ccType)-records;
     * in msg-String;
     * in subject-String;
     */
    public boolean send(BizData d) {
        SMTP smtp = new SMTP(user, password);
        return send(smtp, d);
    }

    /**
     * send a mail to employees;
     */
    public void sendMail2Emps(BizData data) {
    }

    /**
     *
     */
    public void sendMail2Depts(BizData data) {
    }

    /**
     *
     */
    public void sendMail2Coms(BizData data) {
    }

    /**
     *
     */
    public void sendMail2Roles(BizData data) {
    }

    /**
     *
     */
    public void sendMail2Posts(BizData data) {
    }

    /**
     * in to(ID,Type)-records
     * in cc(ID,Type)-records;
     * in msg-String;
     * in subject-String;
     */
    protected boolean send(SMTP smtp, BizData d) {
        int i = 0;
        int toCount = 0;
//        int ccCount=0;
        String email = null;
        String subject = null;
        String msg = null;

        toCount = d.getTableRowsCount("to");
        if (toCount < 1) {
            return false;
        }
        String[] rowIds = d.getRowIDs("to");
        while (i < toCount) {
            email = getEmailAddress(d.getString("to", "id", rowIds[i]), d.getString("to", "type", rowIds[i]));
            smtp.setTo(email);
            i++;
        }
        
        /**process ccs of this email*/
        //ccCount=d.getTableRowsCount("cc");
        rowIds = d.getRowIDs("cc");
        i = 0;
        while (i < toCount) {
            email = getEmailAddress(d.getString("cc", "id", rowIds[i]), d.getString("cc", "type", rowIds[i]));
            smtp.setCc(email);
            i++;
        }
        msg = d.getString("msg");
        subject = d.getString("subject");
        smtp.setSubject(subject);
        smtp.setBody(msg);
        try {
            smtp.sendMail();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get a mail's reciver's email address according to the reciver's id andtype.
     *
     * @param id   the id of the reciver;
     * @param type the type of reciver;
     * @return the email address,or null of such reciver doest not exist or this
     *         reciver has no email address.
     */
    protected String getEmailAddress(String id, String type) {
        return null;
    }
}