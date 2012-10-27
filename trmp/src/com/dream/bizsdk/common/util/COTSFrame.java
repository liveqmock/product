/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util;

import com.dream.bizsdk.common.blc.AbstractBLC;
import com.dream.bizsdk.common.blc.BLContext;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.web.BLServlet;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-27
 * Time: 9:33:00
 */
public class COTSFrame extends AbstractBLC {

    /**
     * refresh the system;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int refresh(BizData rd, BizData sd) {
        int retValue = -1;
        HttpServletRequest req = (HttpServletRequest) rd.get(SysVar.HTTP_REQ);
        ServletContext sc = req.getSession().getServletContext();
        try {
            BLServlet ws = (BLServlet) sc.getAttribute(SysVar.APP_BLSERVLET);
            BLContext blcontext = (BLContext) sc.getAttribute(SysVar.APP_BLCONTEXT);
            blcontext.reinit();
            ws.initContext();
            retValue = SysError.PASS;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retValue;
    }
}
