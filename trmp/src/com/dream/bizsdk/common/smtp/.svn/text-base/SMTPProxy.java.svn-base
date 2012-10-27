/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.smtp;

import java.io.Serializable;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-19
 * Time: 11:12:29
 */
public class SMTPProxy implements ISMTPProxy, Serializable {
    protected String userID = null;
    protected String password = null;
    protected String mailServer = null;
    protected int mailPort = 25;
    protected String fromMail = null;
    protected String fromName = null;
    protected boolean needAuth = true;

    /**
     * @param userID
     * @param password
     * @param mailServer
     * @param mailPort
     * @param fromMail
     * @param fromName
     */
    public SMTPProxy(String userID, String password, String mailServer, int mailPort, String fromMail, String fromName, boolean needAuth) {
        this.userID = userID;
        this.password = password;
        this.mailServer = mailServer;
        this.mailPort = mailPort;
        this.fromMail = fromMail;
        this.fromName = fromName;
        this.needAuth = needAuth;
    }

    /**
     * send a mail with one or more attached files, and the body is text/plain format;
     *
     * @param to               mail recipient email address, mulitple recipient addresses are separated by ";"
     * @param cc               cc email address,multiple recipients addresses are separeted by ";"
     * @param subject          mail subject;
     * @param body             mail body,only text;
     * @param attachedFilePath local attached files' path,multiple pathes are separated by ";"
     */
    public boolean sendMail(String to, String cc, String subject, String body, String attachedFilePath) {
        try {
            SMTP smtp = new SMTP();
            smtp.setUsername(userID);
            smtp.setPassword(password);
            smtp.setMailServer(mailServer);
            smtp.setMailPort(mailPort);
            smtp.setFrom(fromMail);
            smtp.setFromName(fromName);
            smtp.setTo(to);
            smtp.setCc(cc);
            smtp.setSubject(subject);
            smtp.setBody(body);
            smtp.setHtmlContent(false);
            smtp.setAuthLogin(needAuth);
            smtp.setAttachedFile(attachedFilePath);
            smtp.sendMail();
            return true;
        } catch (SMTPException e) {
            return false;
        }
    }

    /**
     * @param to
     * @param cc
     * @param subject
     * @param body
     */
    public boolean sendMail(String to, String cc, String subject, String body) {
        try {
            SMTP smtp = new SMTP();
            smtp.setUsername(userID);
            smtp.setPassword(password);
            smtp.setMailServer(mailServer);
            smtp.setMailPort(mailPort);
            smtp.setFrom(fromMail);
            smtp.setFromName(fromName);
            smtp.setTo(to);
            smtp.setCc(cc);
            smtp.setSubject(subject);
            smtp.setBody(body);
            smtp.setHtmlContent(false);
            smtp.setAuthLogin(needAuth);
            smtp.sendMail();
            return true;
        } catch (SMTPException e) {
            return false;
        }
    }

    /**
     * Send a mail whose content is of html type, and without attached file;
     *
     * @param to
     * @param cc
     * @param subject
     * @param body
     */
    public boolean sendHtmlMail(String to, String cc, String subject, String body) {
        try {
            SMTP smtp = new SMTP();
            smtp.setUsername(userID);
            smtp.setPassword(password);
            smtp.setMailServer(mailServer);
            smtp.setMailPort(mailPort);
            smtp.setFrom(fromMail);
            smtp.setFromName(fromName);
            smtp.setTo(to);
            smtp.setCc(cc);
            smtp.setSubject(subject);
            smtp.setBody(body);
            smtp.setHtmlContent(true);
            smtp.setAuthLogin(needAuth);
            smtp.sendMail();
            return true;
        } catch (SMTPException e) {
            return false;
        }
    }

    /**
     * Send a mail whose content is of html type, and with one or more attached files;
     *
     * @param to
     * @param cc
     * @param subject
     * @param body
     * @param attachedFilePath
     */
    public boolean sendHtmlMail(String to, String cc, String subject, String body, String attachedFilePath) {
        try {
            SMTP smtp = new SMTP();
            smtp.setUsername(userID);
            smtp.setPassword(password);
            smtp.setMailServer(mailServer);
            smtp.setMailPort(mailPort);
            smtp.setFrom(fromMail);
            smtp.setFromName(fromName);
            smtp.setTo(to);
            smtp.setCc(cc);
            smtp.setSubject(subject);
            smtp.setBody(body);
            smtp.setHtmlContent(true);
            smtp.setAuthLogin(needAuth);
            smtp.setAttachedFile(attachedFilePath);
            smtp.sendMail();
            return true;
        } catch (SMTPException e) {
            return false;
        }
    }
}