package com.dream.bizsdk.web.tag;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysVar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 2004-3-24
 * Time: 19:16:17
 * To change this template use File | Settings | File Templates.
 */
public class Menu extends BaseTag {

    public int doStartTag() throws JspException {
        HttpSession session = pc.getSession();
        JspWriter w = pc.getOut();
        String context = ((HttpServletRequest) pc.getRequest()).getContextPath();


        BizData sd = (BizData) session.getAttribute(SysVar.SS_DATA);
        String menuItems = (String) sd.get(SysVar.MENU_ITEMS);
        try {
            if (menuItems != null) {
                w.println("<script language=\"JavaScript\" src=\"" + context + "/js/menu.js\"></script>");
                w.println("<script language=\"JavaScript\" src=\"" + context + "/js/menu_tpl.js\"></script>");
                w.println("<script language=\"JavaScript\">");
                w.println("    <!--//");
                w.print("       var MENU_ITEMS=");
                w.print(menuItems);
                w.println(";");
                w.println("       new menu (MENU_ITEMS, MENU_POS);");
                w.println("     //-->");
                w.println("</script>");
            }
        } catch (IOException ioe) {
            throw new JspException("IOError", ioe);
        }
        return 0;

    }
}
