/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.ejb;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.database.datadict.DataDict;

import com.dream.bizsdk.common.util.string.StringConvertor;
import com.dream.bizsdk.common.util.xml.XMLFile;
import com.dream.bizsdk.common.util.Resource;
import com.dream.bizsdk.common.blc.*;
import com.dream.bizsdk.common.smtp.ISMTPProxy;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Properties;
import java.util.Enumeration;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;

import org.w3c.dom.NodeList;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Description:
 * BLContextEJB will wrap a BLContext object into a EJB object, so that all BLC can be deployed
 * on an Application server to provide BL Method call to different clients.
 * In one word, BLContextEJB will act as a proxy between clients and the BLContext object.
 * 
 * User: chuguanghua
 * Date: 2003-12-24
 * Time: 11:33:35
 */
public class BaseBLContextEJB implements SessionBean {

    transient BLContext context;
    SessionContext sc;
    String name;


    /**
     * Subclasses only need to write a constructor and provide the config file name in it.
     * The full name of config file  must end with ".xml".
     */
    public BaseBLContextEJB() {

    }

    /**
     * @param sc
     */
    public void setSessionContext(SessionContext sc) {
        this.sc = sc;
    }

    /**
     * @throws CreateException
     */
    public void ejbCreate(String name) throws CreateException {
        this.name = name;
        try{
            startup();
        }catch(Exception e){
            e.printStackTrace();
            throw new CreateException("can't create the BLContext object");
        }
    }

    public void ejbRemove() {
        if(context!=null){
            //context.release();
        }
    }

    public void ejbActivate() {
        try{
            startup();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void ejbPassivate() {
        //context.release();
    }


    /**
     * get business data dictionary from the blContext object;
     *
     * @return
     */
    public BizData getBizDataDict() throws RemoteException {
        return context.getBizDataDict();
    }

    /**
     * get errors map;
     *
     * @return
     */
    public BizData getErrors() throws RemoteException {
        return context.getErrors();
    }

    /**
     * get System DataDict object;
     *
     * @return
     */
    public DataDict getDataDict() throws RemoteException {
        return context.getDataDict();
    }

    /**
     * @return
     * @throws RemoteException
     */
    public HashMap getAllDataDict() throws RemoteException {
        return context.getAllDataDict();
    }


    /**
     * execute a bl in this Context;
     *
     * @param className
     * @param methodName
     * @param rd
     * @param sd
     * @return
     */
    public BLResult execBL(String className, String methodName, BizData rd, BizData sd) throws RemoteException {
        return context.execBL(className, methodName, rd, sd);
    }

    /**
     * execute a query sql
     *
     * @param daoName
     * @param sql
     * @param resultName
     * @param data
     * @return
     * @throws RemoteException
     */
    public BizData execQuerySql(String daoName, String sql, String resultName, BizData data) throws RemoteException {
        try {
            return context.execQuerySql(daoName, sql, resultName, data);
        } catch (SQLException sqle) {
            throw new RemoteException("sql exception", sqle);
        }
    }

    /**
     * @param breq
     * @param rd
     * @param sd
     * @return
     * @throws RemoteException
     */
    public BLResult execRequest(BLRequest breq, BizData rd, BizData sd) throws RemoteException {
        return context.execRequest(breq, rd, sd);
    }

    /**
     * @param group
     * @param key
     * @return
     * @throws RemoteException
     */
    public String getConfigValue(String group, String key) throws RemoteException {
        return context.getConfigValue(group, key);
    }

    /**
     * @throws RemoteException
     */
    public void reinit() throws RemoteException {
        try {
            context.reinit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public boolean isRemote() throws RemoteException {
        return context.isRemote();
    }

    /**
     * @param context
     * @throws RemoteException
     */
    public void addCallableBLContext(IBLContextRemote context) throws RemoteException {
        this.context.addCallableBLContext(context);
    }

    /**
     * add a remote callable BLContext object to this BLContext;
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    public IBLContextRemote getCallableBLContext(String name) throws RemoteException {
        return (IBLContextRemote) context.getCallableBLContext(name);
    }

    /**
     * delete a remote callable BLContext object;
     *
     * @param name
     * @throws RemoteException
     */
    public void removeCallableBLContext(String name) throws RemoteException {
        context.removeCallableBLContext(name);
    }

    /**
     * @return
     * @throws RemoteException
     */
    public String getName() throws RemoteException {
        return context.getName();
    }

    /**
     * release this blcontext object;
     *
     * @throws RemoteException
     */
    public void release() throws RemoteException {
        //context.release();
    }

    /**
     * @return
     */
    public BizData getRowPrivil() {
        return context.getRowPrivil();
    }

    /**
     * @return
     */
    public BizData getColPrivil() {
        return context.getColPrivil();
    }

    /**
     * @param name
     * @param rd
     */
    public void notifyEvent(String name, BizData rd) {
        context.notifyEvent(name, rd);
    }

    /**
     * @return
     */
    public ISMTPProxy getSMTPProxy() {
        return context.getSMTPProxy();
    }


    /**
     * startup this context,create the underlying BLContext object.
     * This Statefull EJB will delgate clients' call to the BLContext
     * object.
     */
    private void startup() throws Exception{
        int i = 0;
        int count = 0;


        Hashtable pps = new Hashtable();

        BLContextConfig config = new BLContextConfig();

        String base="META-INF/"+name+"/";

        //get config files from the META-INF sub directory;
        ClassLoader cl = this.getClass().getClassLoader();
        File contextConfig = Resource.getResourceAsFile(cl, base+"blcontext-local.xml");
        File eventConfig = Resource.getResourceAsFile(cl, base+"event-register.xml");
        File log4jConfig = Resource.getResourceAsFile(cl, base+"log4j.properties");
        File remoteConfig = Resource.getResourceAsFile(cl, base+"callable-remote.xml");

        config.setContextConfig(contextConfig);
        config.setEventConfig(eventConfig);
        config.setLog4jConfig(log4jConfig);

        //config log4j if necessary;
        if (Logger.getRootLogger() == null && log4jConfig != null) {
            Properties sp = System.getProperties();

            Enumeration e = sp.keys();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                Object v = sp.get(key);
                if (v instanceof String) {
                    String sv = (String) v;
                    sv = sv.replace('\\', '/');
                    sp.put(key, sv);
                }
            }
            StringConvertor.replaceFile(log4jConfig, sp);
            Properties p = new Properties();
            p.load(new FileInputStream(log4jConfig));
            PropertyConfigurator.configure(p);
        }

        context = new BLContext(config);

        //this is a remote BLContext object;
        context.setRemote(true);
        try {
            if (remoteConfig != null) {
                XMLFile xml = new XMLFile(remoteConfig);
                NodeList nl = xml.selectNodeList("/callable/remote/");
                if (nl != null && (count = nl.getLength()) > 0) {
                    while (i < count) {
                        String remoteName = xml.getSingleNodeValue("/blcontext-remote/name");
                        String jndiName = xml.getSingleNodeValue("/blcontext-remote/ejbJNDIName");
                        pps.put("java.naming.factory.initial"
                                , xml.getSingleNodeValue("/blcontext-remote/java.naming.factory.initial"));
                        pps.put("java.naming.provider.url"
                                , xml.getSingleNodeValue("/blcontext-remote/java.naming.provider.url"));
                        InitialContext ic = new InitialContext(pps);
                        Object ref = ic.lookup(jndiName);
                        IBLContextHome home = (IBLContextHome)
                                PortableRemoteObject.narrow(ref, IBLContextHome.class);

                        IBLContextRemote ctx = home.create(remoteName);
                        if (context.isRemote()) {
                            ((IBLContextRemote) context).addCallableBLContext(ctx);
                        } else {
                            context.addCallableBLContext(ctx);
                        }
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            //failure to read the callable xml file will not affect the creat of this ejb object;
            e.printStackTrace();
        }
    }
}
