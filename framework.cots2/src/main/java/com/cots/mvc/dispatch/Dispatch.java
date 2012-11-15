/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.dispatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.util.HashMap;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chugh
 * Date: 2005-4-5
 * Time: 14:38:21
 * Version: 1.0
 */
public interface Dispatch {

    //forward type dispatch;
    String DISP_FORWARD ="forward";
    //include type dispatch;
    String DISP_INCLUDE ="include";
    //redirect type dispatch;
    String DISP_REDIRECT ="redirect";
    //customer writed code to dispatch;
    String DISP_CUSTOM ="custom";

    boolean go(HttpServletRequest request, HttpServletResponse response, HashMap data)
            throws ServletException, IOException;
}
