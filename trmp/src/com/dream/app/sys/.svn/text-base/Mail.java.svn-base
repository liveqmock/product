/**
 *all rights reserved,@copyright 2003
 */
package com.dream.app.sys;

import com.dream.bizsdk.common.blc.AbstractBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.smtp.ISMTPProxy;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-19
 * Time: 12:35:57
 */
public class Mail extends AbstractBLC {
    public static int NO_RECIPIENT = -4000;
    public static int MAIL_SENT = 1;
    public static int MAIL_FAIL = 1;

    public int sendMail(BizData data) {
        boolean succ = false;
        String to = (String) data.get("to");
        String cc = data.getString("cc");
        String subject = data.getString("subject");
        String body = data.getString("body");
        String isHtmlContext = (String) data.get("isHtmlContent");
        if (to == null) {
            return Mail.NO_RECIPIENT;
        }
        ISMTPProxy smtp = _context.getSMTPProxy();

        if (isHtmlContext != null) {
            succ = smtp.sendHtmlMail(to, cc, subject, body);
        } else {
            succ = smtp.sendMail(to, cc, subject, body);
        }
        if (succ) {
            return Mail.MAIL_SENT;
        } else {
            return Mail.MAIL_FAIL;
        }
    }
}
