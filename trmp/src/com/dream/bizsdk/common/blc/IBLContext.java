/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.dream.app.district.DistrictBean;
import com.dream.bizsdk.common.database.datadict.DataDict;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.smtp.ISMTPProxy;

/**
 * Description:
 * Interface that is implemented by BLContext and BLContextEJB.
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-24
 * Time: 10:40:52
 */
public interface IBLContext {

    /**
     * get business data dictionary from the blContext object;
     *
     * @return
     */
    BizData getBizDataDict() throws RemoteException;
    
    /**
     * get errors map;
     *
     * @return
     */
    BizData getErrors() throws RemoteException;

    /**
     * get System DataDict object;
     *
     * @return
     */
    DataDict getDataDict() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    BizData getRowPrivil() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    BizData getColPrivil() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    HashMap getAllDataDict() throws RemoteException;

    /**
     * execute a bl in this Context;
     *
     * @param className
     * @param methodName
     * @param rd
     * @param sd
     * @return
     */
    BLResult execBL(String className, String methodName, BizData rd, BizData sd) throws RemoteException;

    /**
     * @param request
     * @param rd
     * @param sd
     * @return
     * @throws RemoteException
     */
    BLResult execRequest(BLRequest request, BizData rd, BizData sd) throws RemoteException;


    /**
     * execute a sql directly;
     *
     * @param daoName
     * @param sql
     * @param resulName
     * @param data
     * @throws RemoteException
     */
    BizData execQuerySql(String daoName, String sql, String resulName, BizData data) throws RemoteException, SQLException;

    /**
     * @param group
     * @param key
     * @return
     * @throws RemoteException
     */
    String getConfigValue(String group, String key) throws RemoteException;

    /**
     * @throws RemoteException
     */
    void reinit() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    boolean isRemote() throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    String getName() throws RemoteException;

    /**
     * @throws RemoteException
     */
    void release() throws RemoteException;

    /**
     * @param name
     * @param rd
     * @throws RemoteException
     */
    void notifyEvent(String name, BizData rd) throws RemoteException;

    /**
     * @return
     * @throws RemoteException
     */
    ISMTPProxy getSMTPProxy() throws RemoteException;
    
    /**
     * add districts to envionment variables
     */
    public void addDistrict2Env();
    
    public List<DistrictBean> getDistrictBeans();
    
    public List<DistrictBean> getProvinceBeans();
    
    public List<DistrictBean> getCityBeans();
    
    
}


