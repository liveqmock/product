/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.assign;

import com.dream.bizsdk.common.workflow.definition.WFApplication;
import com.dream.bizsdk.common.databus.BizData;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-1
 * Time: 11:42:25
 */
public class ApplicationCaller {

    public static void call(WFApplication app, BizData d) {
        String url = app.getUrl();
        String type = app.getType();
        if ("bl".compareToIgnoreCase(type) == 0) {
            int pos = url.lastIndexOf('.');
            String className = url.substring(0, pos);
            String methodName = url.substring(pos + 1);
            callBL(className, methodName, d);
        } else if ("java".compareToIgnoreCase(type) == 0) {
            int pos = url.lastIndexOf('.');
            String className = url.substring(0, pos);
            String methodName = url.substring(pos + 1);
            callJava(className, methodName, d);
        } else if ("ws".compareToIgnoreCase(type) == 0) {
            int pos = url.lastIndexOf('.');
            callWebService(url, d);
        }
    }

    private static void callBL(String className, String methodName, BizData d) {

    }

    private static void callJava(String className, String methodName, BizData d) {

    }

    private static void callWebService(String url, BizData d) {
    }
}
