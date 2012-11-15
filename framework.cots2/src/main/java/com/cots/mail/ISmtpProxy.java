package com.cots.mail;

import java.io.Serializable;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 2004-5-2
 * Time: 16:58:59
 */
public interface ISmtpProxy extends Serializable {

    boolean sendMail(String to, String cc, String subject, String body, String attachedFilePath);

    boolean sendMail(String to, String cc, String subject, String body);

    boolean sendHtmlMail(String to, String cc, String subject, String body);

    boolean sendHtmlMail(String to, String cc, String subject, String body, String attachedFilePath);

    boolean sendMail(String to, String cc, String subject, Properties props);
}