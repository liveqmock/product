/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.mime;

import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-19
 * Time: 17:09:37
 */
public class MIMEMultipart {
    /**
     * parts map,each part is a MIMEMessage object
     */
    protected HashMap parts = new HashMap();
    /**
     * the raw content of this Mulitpart message
     */
    protected String content = null;

    /**
     * set the raw content of this message, the message is
     * automatically parsed into parts;
     *
     * @param content the raw content of the Message;
     */
    public void setContent(String content) {
        this.content = content;
        parse();
    }

    /**
     * get the the pars map;
     *
     * @return
     */
    public HashMap getParts() {
        return parts;
    }

    /**
     * parse the raw content of this message;
     */
    protected void parse() {

    }
}
