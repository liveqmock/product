/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.ejb;

import com.dream.bizsdk.common.blc.IBLContext;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Description:
 * BLContext Proxy EJB remote interface.
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-24
 * Time: 11:38:31
 */
public interface IBLContextRemote extends IBLContext, EJBObject {

    /**
     * @param context
     * @throws RemoteException
     */
    void addCallableBLContext(IBLContextRemote context) throws RemoteException;

    /**
     * @param name
     * @return
     * @throws RemoteException
     */
    IBLContextRemote getCallableBLContext(String name) throws RemoteException;


    /**
     * @param name
     * @throws RemoteException
     */
    void removeCallableBLContext(String name) throws RemoteException;

}
