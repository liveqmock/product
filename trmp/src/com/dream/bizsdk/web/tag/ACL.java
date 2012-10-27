package com.dream.bizsdk.web.tag;

import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.databus.BizData;

import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.JspException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 2004-3-21
 * Time: 13:38:15
 * To change this template use File | Settings | File Templates.
 */
public class ACL extends BaseTag {
    private HashMap rf = null;
    private Vector ur = null;
    private String func = null;

    public void doInitBody() throws JspException {
        ServletContext sc = pc.getServletContext();
        HttpSession session = pc.getSession();
        rf = (HashMap) sc.getAttribute(SysVar.APP_RFUNCTIONS);
        ur = (Vector) ((BizData) session.getAttribute(SysVar.SS_DATA)).get(SysVar.USER_ROLES);
    }

    public int doAfterBody() throws JspException {
        return BodyTag.SKIP_BODY;
    }

    public int doStartTag() throws JspException {
        int i = 0;
        doInitBody();
        try {
            int size = ur.size();
            while (i < size) {
                String roleID = (String) ur.elementAt(i);
                if (rf.get(roleID + "_" + func) != null) {
                    return BodyTag.EVAL_BODY_INCLUDE;
                }
                i++;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return BodyTag.SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        rf = null;
        ur = null;
        func = null;
        return 0;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }


    public void release() {
        super.release();
    }
}
