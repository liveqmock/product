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
 * Time: 16:50:54
 */
public class MIMEMessage {
    protected HashMap headers = new HashMap();
    protected MIMEBody body;

    /**
     * set the body of this message;
     *
     * @param b the message body;
     */
    public void setBody(MIMEBody b) {
        body = b;
    }

    /**
     * get the body of this message;
     *
     * @return the message body;
     */
    public MIMEBody getBody() {
        return body;
    }

    /**
     * add a mime header to this message;
     *
     * @param h the head to be added;
     */
    public void add(MIMEHeader h) {
        headers.put(h.getName(), h);
    }

    /**
     * get all the headers map in this message;
     *
     * @return
     */
    public HashMap getHeaders() {
        return headers;
    }

    /**
     * Get a named MIMEHeader object;
     *
     * @param name
     * @return
     */
    public MIMEHeader getHeader(String name) {
        return (MIMEHeader) headers.get(name);
    }
}
