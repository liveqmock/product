package com.dream.bizsdk.ws;

import com.dream.bizsdk.common.blc.BLResult;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.web.BLServlet;
import com.dream.bizsdk.web.BLServletMap;

import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2004-7-18
 * Time: 20:20:18
 * To change this template use File | Settings | File Templates.
 */
public class ReqWebService {
    public ReqWebService() {

    }

    public BLResult service(String appName, String reqname, BizData rd, BizData sd)
            throws RemoteException {
        BLResult result = null;

        BLServlet blServlet = BLServletMap.get(appName);
        if (blServlet != null) {
            result = blServlet.processDirectRequest(reqname, rd, sd);
        } else {
            result = new BLResult(SysError.BL_NOT_AVAILABLE, rd, sd);
        }
        return result;
    }
}
