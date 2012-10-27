/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.ejb;

import javax.ejb.EJBHome;

/**
 * Description:
 * BLContext proxy EJB home interface.
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-24
 * Time: 11:32:29
 */
public interface IBLContextHome extends EJBHome {
    IBLContextRemote create(String name) throws java.rmi.RemoteException, javax.ejb.CreateException;
}
