/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.mime;

import com.dream.bizsdk.common.util.encoding.Base64;
import com.dream.bizsdk.common.util.encoding.QPEncoder;

import java.io.Reader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-19
 * Time: 17:23:55
 */
public class RawMessage {

    /**
     * the status of the read the raw message
     */
    protected boolean inHeader = true;
    /**
     * the content type of this message,decided by the ContentType header
     */
    protected String bodyType = null;

    //protected boolean multipart=false;
    /**
     * charset of the content Type
     */
    protected String charSet = null;
    /**
     * tranfer encoding of the content type
     */
    protected String encoding = "";

    protected StringBuffer sb = new StringBuffer(1024);


    public MIMEMessage read(Reader r) {
        MIMEHeader h = null;
        MIMEMessage mm = new MIMEMessage();
        while ((h = readHeader(r)) != null) {
            mm.add(h);
        }
        MIMEBody body = readBody(r);
        mm.setBody(body);
        return mm;
    }

    /**
     * read next header from the reader; If the last Header is read,then
     * set the member variable inHeader to false;
     *
     * @param r
     * @return the Next MIMEHeader;
     */
    protected MIMEHeader readHeader(Reader r) {
        MIMEHeader h = new MIMEHeader();
        String line = readLine(r);
        if (line != null) {
            int index = line.indexOf(':');
            if (index < 0) {
                inHeader = false;
                return null;
            } else {
                String name = line.substring(0, index);
                String value = line.substring(index + 1);
                h.setName(name);
                ;
                h.setValue(value);
                if (name.compareToIgnoreCase("Content-Type") == 0) {
                    bodyType = value.trim();
                } else if (name.compareToIgnoreCase("Content-Transfer-Encoding") == 0) {
                    encoding = value.trim();
                }
                return h;
            }
        } else {
            inHeader = false;
            return null;
        }
    }

    /**
     * @param r
     * @return
     */
    protected MIMEBody readBody(Reader r) {
        String body = "";
        String line = "";
        if (bodyType.toLowerCase().indexOf("multipart") >= 0) {

        } else {
            while ((line = readLine(r)) != null) {
                /**if the body content-transfer-encoding  is qp,then check the soft line break*/
                if (encoding.compareToIgnoreCase("quoted-printable") == 0) {
                    if (line.charAt(line.length() - 1) == '=') {
                        line = line.substring(0, line.length() - 1);
                    }
                }
                body += line;
            }
        }
        if (encoding.compareToIgnoreCase("base64") == 0) {
            try {
                body = Base64.decode(body, "gb2312");
            } catch (UnsupportedEncodingException e) {

            }
        } else if (encoding.compareToIgnoreCase("quoted-printable") == 0) {
            QPEncoder qp = new QPEncoder();
            body = qp.decode(body);
        }
        return new MIMEBody(body);
    }

    /**
     * @param r
     * @return
     */
    protected String readLine(Reader r) {
        int len = 0;
        char ch = 0;
        int chInt = -1;
        len = sb.length();
        try {
            while ((chInt = r.read()) != -1) {
                ch = (char) chInt;
                if (len >= 2) {
                    if (sb.charAt(len - 2) == '\r' && sb.charAt(len - 1) == '\n') {
                        sb.delete(len - 2, len);
                        len = len - 2;
                        if (ch != '\t' && ch != 40) {
                            String line = new String(sb);
                            sb.delete(0, sb.length());
                            sb.append(ch);
                            return line;
                        }
                    } else {
                        sb.append(ch);
                        len++;
                    }
                } else {
                    sb.append(ch);
                    len++;
                }
            }
            //trim the ending \r\n;
            if (sb.length() >= 2 && sb.charAt(len - 2) == '\r' && sb.charAt(len - 1) == '\n') {
                sb.delete(len - 2, len);
            }
            if (sb.length() < 1) {
                return null;
            } else {
                return new String(sb);
            }
        } catch (IOException ioe) {
            return null;
        }
    }
}