/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.ws;

import com.dream.bizsdk.common.blc.BLResult;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.web.BLServlet;
import com.dream.bizsdk.web.BLServletMap;

import java.rmi.RemoteException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-2
 * Time: 12:21:12
 */
public class BLWebService {

    public BLWebService() {
    }

    /**
     * @param contextName
     * @param className
     * @param methodName
     * @param rd
     * @param sd
     * @return
     * @throws Throwable
     */
    public BLResult execBL(String contextName, String className, String methodName
                           , BizData rd, BizData sd) throws Throwable {
        BLServlet bl = (BLServlet) BLServletMap.get(contextName);
        if (bl != null) {
            return bl.processBLWebService(className, methodName, rd, sd);
        } else {
            return new BLResult(SysError.BL_NOT_AVAILABLE, rd, sd);
        }
    }

    /**
     * @param contextName
     * @param reqName
     * @param rd
     * @param sd
     * @return
     * @throws RemoteException
     */
    public BLResult execReq(String contextName, String reqName
                            , BizData rd, BizData sd) throws RemoteException {
        BLServlet bl = (BLServlet) BLServletMap.get(contextName);
        if (bl != null) {
            return bl.processDirectRequest(reqName, rd, sd);
        } else {
            return new BLResult(SysError.BL_NOT_AVAILABLE, rd, sd);
        }
    }
}
