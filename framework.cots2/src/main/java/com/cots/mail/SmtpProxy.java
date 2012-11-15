/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mail;

import org.w3c.dom.Element;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Properties;

import com.cots.util.XMLDocument;
import com.cots.util.FileUtil;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-19
 * Time: 11:12:29
 */
public class SmtpProxy implements ISmtpProxy, Serializable {
    protected String name;
    protected String userID;
    protected String password;
    protected String mailServer;
    protected int mailPort = 25;
    protected String fromMail;
    protected String fromName;
    protected boolean needAuth;
    protected boolean htmlContent;
    protected String template;

    /**
     * @param userID
     * @param password
     * @param mailServer
     * @param mailPort
     * @param fromMail
     * @param fromName
     */
    public SmtpProxy(String userID, String password, String mailServer, int mailPort,
                     String fromMail, String fromName, boolean needAuth) {
        this.userID = userID;
        this.password = password;
        this.mailServer = mailServer;
        this.mailPort = mailPort;
        this.fromMail = fromMail;
        this.fromName = fromName;
        this.needAuth = needAuth;
    }

    /**
     *
     * @param e
     */
    public SmtpProxy(Element e ) {
        this.name = e.getAttribute("name");
        this.userID = XMLDocument.getNodeValue(e,"userName");
        this.password = XMLDocument.getNodeValue(e,"password");
        this.mailServer = XMLDocument.getNodeValue(e,"host");

        try{
            this.mailPort = Integer.parseInt(XMLDocument.getNodeValue(e,"port"));
        }catch(NumberFormatException ex){
            ex.printStackTrace();
        }

        this.fromMail = XMLDocument.getNodeValue(e,"fromMail");
        this.fromName = XMLDocument.getNodeValue(e,"fromName");

        String content = XMLDocument.getNodeValue(e,"content");
        if("html".equalsIgnoreCase(content)){
            htmlContent = true;
        }

        template = XMLDocument.getNodeValue(e,"template");

        this.needAuth = "true".equalsIgnoreCase(XMLDocument.getNodeValue(e,"needAuth"));
    }

    /**
     * get the displayName of this BLContext;
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * sendBody a mail with one or more attached files, and the body is text/plain format;
     *
     * @param to               mail recipient email address, mulitple recipient addresses are separated by ";"
     * @param cc               cc email address,multiple recipients addresses are separeted by ";"
     * @param subject          mail subject;
     * @param body             mail body,only text;
     * @param attachedFilePath local attached files' path,multiple pathes are separated by ";"
     */
    public boolean sendMail(String to, String cc, String subject, String body, String attachedFilePath) {
        try {
            Smtp smtp = new Smtp();
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
            smtp.setHtmlContent(htmlContent);
            smtp.setAuthLogin(needAuth);
            smtp.setAttachedFile(attachedFilePath);
            smtp.sendMail();
            return true;
        } catch (SmtpException e) {
            Logger.getLogger(SmtpProxy.class.getName()).error("failed to sendBody mail",e);
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
            Smtp smtp = new Smtp();
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
            smtp.setHtmlContent(htmlContent);
            smtp.setAuthLogin(needAuth);
            smtp.sendMail();
            return true;
        } catch (SmtpException e) {
            Logger.getLogger(SmtpProxy.class.getName()).error("failed to sendBody mail",e);
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
            Smtp smtp = new Smtp();
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
        } catch (SmtpException e) {
            Logger.getLogger(SmtpProxy.class.getName()).error("failed to sendBody mail",e);
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
            Smtp smtp = new Smtp();
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
        } catch (SmtpException e) {
            Logger.getLogger(SmtpProxy.class.getName()).error("failed to sendBody mail",e);
            return false;
        }
    }

    public boolean sendMail(String to, String cc, String subject, Properties props) {
        try {
            Smtp smtp = new Smtp();
            smtp.setUsername(userID);
            smtp.setPassword(password);
            smtp.setMailServer(mailServer);
            smtp.setMailPort(mailPort);
            smtp.setFrom(fromMail);
            smtp.setFromName(fromName);
            smtp.setTo(to);
            smtp.setCc(cc);
            smtp.setSubject(subject);
            String body = FileUtil.replace(template,"${","}",props);
            smtp.setBody(body);
            smtp.setHtmlContent(htmlContent);
            smtp.setAuthLogin(needAuth);
            smtp.sendMail();
            return true;
        } catch (SmtpException e) {
            Logger.getLogger(SmtpProxy.class.getName()).error("failed to sendBody mail",e);
            return false;
        }
    }

}