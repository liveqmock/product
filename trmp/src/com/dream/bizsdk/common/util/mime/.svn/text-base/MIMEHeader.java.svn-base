/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.mime;

import com.dream.bizsdk.common.util.encoding.Base64;
import com.dream.bizsdk.common.util.encoding.QPEncoder;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-19
 * Time: 16:51:16
 */
public class MIMEHeader {
    protected String name = null;
    protected String value = null;

    public MIMEHeader() {

    }

    public MIMEHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = decode2(value);
    }

    protected String decode2(String src) {
        int s = 0;
        int e = 0;

        String ss = "=?";
        String es = "?=";

        StringBuffer sb = new StringBuffer(1024);

        src = src.trim();

        s = src.indexOf(ss);
        while (s >= 0) {
            String temp = src.substring(e, s);
            sb.append(temp);
            e = src.indexOf(es, s);
            if (e >= 0) {
                temp = src.substring(s, e + 2);
                sb.append(decode(temp));
                e = e + 2;
                s = src.indexOf(es, e);
            } else {
                e = s;
                break;
            }
        }
        sb.append(src.substring(e));
        return new String(sb);
    }

    protected String decode(String src) {
        int len = 0;

        len = src.length();
        if (len < 9) {
            return src;
        } else {
            if (src.charAt(0) == '=' && src.charAt(1) == '?' && src.charAt(len - 1) == '=' && src.charAt(len - 2) == '?') {
                try {
                    int charSetPos = src.indexOf('?', 2);
                    String charSet = src.substring(2, charSetPos);
                    int encBeginPos = charSetPos + 1;
                    int encEndPos = src.indexOf('?', encBeginPos);
                    String enc = src.substring(encBeginPos, encEndPos);
                    String encoded = src.substring(encEndPos + 1, len - 2);
                    //base64 encoding;
                    if (enc.compareToIgnoreCase("B") == 0) {
                        return Base64.decode(encoded, charSet);
                    } else if (enc.compareToIgnoreCase("Q") == 0) {
                        //if other encoding,reutrn source directly currently;
                        QPEncoder qp = new QPEncoder();
                        return qp.decode(src);
                    } else {
                        return src;
                    }
                } catch (Exception e) {
                    return src;
                }
            } else {
                return src;
            }
        }
    }
}
