package com.dream.bizsdk.common.smtp;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 2004-5-2
 * Time: 16:58:59
 */
public interface ISMTPProxy extends Serializable {
    public boolean sendMail(String to, String cc, String subject, String body, String attachedFilePath);

    public boolean sendMail(String to, String cc, String subject, String body);

    public boolean sendHtmlMail(String to, String cc, String subject, String body);

    public boolean sendHtmlMail(String to, String cc, String subject, String body, String attachedFilePath);
}
