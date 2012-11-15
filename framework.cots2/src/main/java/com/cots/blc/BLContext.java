/**
 *all rights reserved,@copyright 2003
 */
package com.cots.blc;

import com.cots.dao.DAO;
import com.cots.dao.JdbcDAO;
import com.cots.dao.JtaDAO;
import com.cots.dao.AbstractDAO;
import com.cots.dao.privil.PrivilManager;
import com.cots.dao.sql.BaseStatement;
import com.cots.dao.helper.ObjectHelperFactory;
import com.cots.util.*;
import com.cots.bean.BeanFactory;
import com.cots.mail.SmtpProxy;
import com.cots.mail.ISmtpProxy;

import javax.xml.parsers.ParserConfigurationException;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.*;

import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * Description:
 *  A BLContext object is created upon a registry file(xml formated). this file has the following structure:
 *      <blcontext>
 *          <system>
 *              <runtimebuild>
 *                  <basedir/>
 *                  <classpath/>
 *              </runtimebuild>
 *              <charset/>
 *              <debug>true|false</debug>
 *          </system>
 *          <daos>
 *              <dao displayName="common">
 *                  <dsjndi/>
 *                  <user/>
 *                  <password/>
 *              </dao>
 *              <dao displayName="security" ref="common"/>
 *          </daos>
 *          <mail>
 *              <account>
 *                  <user/>
 *                  <password/>
 *                  <smtpSerrver/>
 *                  <smtpPort/>
 *                  <smtpAuth>true|false</stmpAuth>
 *                  <popServer/>
 *                  <popPort/>
 *              </account>
 *          </mail>
 *          <configitems>
 *              <group displayName="">
 *                  <item key="" value=""/>
 *                  <item key="" value=""/>
 *              </group>
 *          </configitems>
 *      </blcontext>
 * User: chugh
 * Date: 2004-10-10
 * Time: 13:25:48
 * Version: 1.0
 */
public final class BLContext implements Cache{

    public final static String BLCONTEXT            ="blcontext";
    public final static String SYSTEM               ="system";
    public final static String RUNTIMEBUILD         ="runtimebuild";
    public final static String BASEDIR              ="basedir";
    public final static String CLASSPATH            ="classpath";
    public final static String CHARSET              ="charset";
    public final static String DEBUG                = "debug";
    public final static String DAOS                 ="daos";
    public final static String DAO                  ="dao";
    public final static String DSJNDI               = "dsjndi";
    public final static String USER                 = "user";
    public final static String PASSWORD             ="password";
    public final static String DBTYPE               ="dbType";
    public final static String INITIALFACTORY       = "initialFactory";
    public final static String PROVIDERURL          = "providerURL";
    public final static String CONFIGITEMS          ="configitems";
    public final static String GROUP                ="group";
    public final static String NAME                 ="name";
    public final static String ITEM                 ="item";
    public final static String ITEMKEY              ="key";
    public final static String ITEMVALUE            ="value";
    public final static String TRANSTYPE            ="transType";
    public final static String TRANSTYPE_JDBC      ="jdbc";
    public final static String TRANSTYPE_TX        ="tx";
    public final static String REF_DAO              ="ref";
    public final static String DEFAULT_DAO         ="default";


    private String name;
    //the path of the config file;
    private String configFile;
    //the blcontext registry xml file.
    private XMLFile registry;

    private BLCPool blcPool;
    //base directory used to save runtime built java source file.
    private String baseDir;
    //classpath used to compile runtime java source file.
    private String classPath;
    //all the config items in this blcontext.
    private HashMap ciGroups;
    //contains all the daos defined in this context;
    private HashMap daos;

    private HashMap refDAOs;

    private ArrayList initializers;
    //debug mode;
    private boolean debug;

    //charset of the application;
    private String charset;

    //bean factory;
    private BeanFactory beanFactory;

    private Logger log;

    private DAO defaultDAO;

    private DirClassLoader loader;

    private boolean logAccess;

    private boolean logOnlineRequests;

    private String loginListener;

    private String loginControlImpl;

    private String accessControlImpl;

    private List actionFilters = new ArrayList();

    private List actionListeners = new ArrayList();

    private Map smtps = new HashMap();

    private PrivilManager privilManager = new PrivilManager();

    public BLContext(String registryFile,BeanFactory beanFactory) throws IOException{
        log = Logger.getLogger(BLContext.class);
        ciGroups=new HashMap();
        daos = new HashMap();
        refDAOs = new HashMap();
        this.beanFactory = beanFactory;

        if(!init(registryFile)){
            throw new IOException("failed to read/parse the config file");
        }

        //create the BLCPool for this BLContext;
        blcPool = new BLCPool();
        blcPool.setContext(this);

        //create the classloader;
        newLoader();
    }

    /**
     * recreate the classloader for runtime builder;
     */
    public DirClassLoader newLoader(){
        loader = new DirClassLoader(this.getBaseDir(),Thread.currentThread().getContextClassLoader());
        return loader;
    }

    /**
     * get the displayName of the context;
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * get the base directory to save the dynmically generated java source and class file.
     * if this string contains ${XXX}, then ${XXX} will be replaced by System.getProperty("XXX");
     *
     * @return the base directory.
     */
    public String getBaseDir() {
        return FileUtil.replace(baseDir,"${","}",System.getProperties());
    }

    public DirClassLoader getLoader() {
        return loader;
    }

    /**
     * get the base directory.
     *
     * @param baseDir the base directory.
     */
    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * get the BeanFactory object in the context;
     * @return the BeanFactory object;
     */
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public String getLoginListener() {
        return loginListener;
    }

    public void setLoginListener(String loginListener) {
        this.loginListener = loginListener;
    }

    /**
     * get the class path used to compile the java class at runtime.
     * if this string contains ${XXX}, then ${XXX} will be replaced by System.getProperty("XXX");
     * 
     * @return the classpath.
     */
    public String getClassPath() {
        return FileUtil.replace(classPath,"${","}",System.getProperties());
    }

    /**
     * set the classpath used to compile runtime built java source files.
     *
     * @param classPath the new classpath.
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     * get the blcpool;
     *
     * @return
     */
    public BLCPool getBLCPool() {
        return blcPool;
    }

    public PrivilManager getPrivilManager(){
        return privilManager;
    }

    /**
     * get a named BLC object;
     *
     * @param qname the qualified class displayName of the BLC;
     * @return  an Object of the target BLC class.
     * @throws IllegalAccessException if can't access the default constructor
     * @throws InstantiationException if can't create an instance of the BLC.
     * @throws ClassNotFoundException if the target BLC doest not exist;
     */
    public BLC getBLC(String qname)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return blcPool.getBLC(qname);
    }

    /**
     * get a named DAO object.
     *
     * @param name the displayName of the dao
     * @return the target DAO object, if the dao doest not exist,  null is returned.
     */
    public DAO getDAO(String name){
        Object dao = daos.get(name);
        if(dao==null){
            dao = refDAOs.get(name);
            dao = daos.get(dao);
        }
        return (DAO)dao;
    }

    /**
     * get the default DAO object;
     *
     * @return the default DAO object.
     */
    public DAO getDAO(){
        return defaultDAO;
    }

    /**
     * get all the daos defined in this BLContext.
     *
     * @return an LocalDAO[] object contains all the daos defined in this blContext, never null;
     */
    public DAO[] getDAOs(){
        return (DAO[])daos.values().toArray(new DAO[daos.size()]);
    }


    /**
     * get the smtp proxy;
     *
     * @param name the displayName of the proxy;
     * @return the ISmtpProxy object.
     */
    public ISmtpProxy getSmtpProxy(String name){
        return (ISmtpProxy)smtps.get(name);
    }
    /**
     *
     * get a config item's value in this BLContext. Config items are dividend into
     * groups, each group has a displayName; and each item has a key and a value. Application
     * can modify these config values at runtime.
     *
     * @param group the displayName of the group to which this item belongs to.
     * @param key the key of the item.
     * @return the value of the item.
     */
    public Object getConfigValue(String group,String key){
        Object value = null;
        HashMap grp = (HashMap)ciGroups.get(group);
        if(grp!=null){
            value = (String)grp.get(key);
        }
        return value;
    }

    /**
     * set the value of a config item.
     *
     * @param group the displayName of the group to which this item belongs to.
     * @param key the key of the item.
     * @param value the value of the item.
     */
    public synchronized Object setConfigValue(String group,String key,Object value){
        HashMap grp = (HashMap)ciGroups.get(group);
        if(grp!=null){
            value = grp.put(key,value);
        }

        return value;
    }


    public boolean isLogAccess() {
        return logAccess;
    }

    public synchronized void setLogAccess(boolean logAccess) {
        this.logAccess = logAccess;
    }

    public boolean isLogOnlineRequests() {
        return logOnlineRequests;
    }

    public void setLogOnlineRequests(boolean logOnlineRequests) {
        this.logOnlineRequests = logOnlineRequests;
    }

    /**
     *
     * @return list of action filters, never null;
     */
    public List getActionFilters() {
        return actionFilters;
    }

    /**
     *
     * @return list of ObjectWrapper, never null;
     */
    public List getActionListeners() {
        return actionListeners;
    }

    /**
     * get the login control impl class displayName;
     *
     * @return
     */
    public String getLoginControlImpl() {
        return loginControlImpl;
    }

    /**
     * get the access control impl class;
     *
     * @return
     */
    public String getAccessControlImpl() {
        return accessControlImpl;
    }

    /**
     * shut down this blcontext, pesitent all the config values. close all dao objects.
     */
    public synchronized void shutdown(){
        //save the configuration;
        save();

        //shutdown the pool;
        blcPool.shutdown();
    }

    /**
     * save this object state to the registry file. This will cause all the dao's prooperties
     * and config values and other settings be written to the registry file.
     */
    public void save(){
        //save the system configuration;
        registry.setNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+RUNTIMEBUILD+"/"+BASEDIR,baseDir);
        registry.setNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+RUNTIMEBUILD+"/"+CLASSPATH,classPath);
        registry.setNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+DEBUG,debug?"true":"false");
        registry.setNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+CHARSET,charset);
        registry.setNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+"logAccess",logAccess?"true":"false");
        registry.setNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+"logOnlineRequests",
                logOnlineRequests?"true":"false");
        if(loginListener!=null){
            registry.setNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+"loginListener",loginListener);
        }

        //save all the daos' configuration;
        Iterator it = daos.values().iterator();
        while(it.hasNext()){
            DAO dao = (DAO)it.next();
            saveDAO(dao);
        }

        it = refDAOs.keySet().iterator();
        while(it.hasNext()){
            String name = (String)it.next();
            String refName = (String)refDAOs.get(name);
            saveRefDAO(name,refName);
        }


        //save all the config items' value to the registry file.
        it = ciGroups.keySet().iterator();
        while(it.hasNext()){
            String grpName = (String)it.next();
            HashMap grp = (HashMap)ciGroups.get(grpName);
            saveGroupConfigItems(grpName,grp);
        }
    }
    

    /**
     * initialize this blcontext object. Cause all the config values be read from the registry file.
     * and all the defined daos be created.
     *
     * @param file the registry file.
     */
    public boolean init(String file){
        boolean succ = true;
        configFile = file;
        try{
            registry = new XMLFile(file);

            //displayName of the context;
            name = registry.getSingleNodeValue("/"+BLCONTEXT+"/@name");

            baseDir = registry.getSingleNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+RUNTIMEBUILD+"/"+BASEDIR);
            classPath = registry.getSingleNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+RUNTIMEBUILD+"/"+CLASSPATH);
            charset = registry.getSingleNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+CHARSET);

            accessControlImpl = registry.getSingleNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/accessControlImpl");
            loginControlImpl = registry.getSingleNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/loginControlImpl");
            loginListener = registry.getSingleNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/loginListener");

            String value = registry.getSingleNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+DEBUG);
            if("true".equals(value)){
                debug = true;
            }

            value = registry.getSingleNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+"logAccess");
            if("true".equals(value)){
                logAccess = true;
            }

            value = registry.getSingleNodeValue("/"+BLCONTEXT+"/"+SYSTEM+"/"+"logOnlineRequests");
            if("true".equals(value)){
                logOnlineRequests = true;
            }

            //create the dir class loader;
            loader = new DirClassLoader(this.getBaseDir(),Thread.currentThread().getContextClassLoader());

            loadConfigItems();

            loadDAOs();

            privilManager.init(getDAO());

            loadSmtps();

            loadInitializers();

        }catch(IOException e){
            log.error("failed to read config file",e);
            succ = false;
        } catch (ParserConfigurationException e) {
            log.error("can't parse config file",e);
            succ = false;
        } catch (SAXException e) {
            log.error("failed to parse config file",e);
            succ = false;
        }
        return succ;
    }

    /**
     * refresh this config file;
     *
     * @return
     */
    public synchronized boolean refresh(){
        return init(configFile);
    }

    /**
     * get the class displayName of all the initializers defined in this context.
     *
     * @return the string array of the initializer class names, never null;
     */
    public String[] getInitializerClasses(){
        if(initializers == null){
            return new String[0];
        }else{
            return (String[])initializers.toArray(new String[initializers.size()]);
        }
    }

    /**
     * initialize all the daos defined in this dao objects;
     */
    private void loadDAOs(){
        NodeList nl = registry.selectNodeList("/"+BLCONTEXT+"/"+DAOS+"/"+DAO);
        int count = nl.getLength();

        for(int i=0;i<count;i++){
            Element e = (Element)nl.item(i);
            String name = e.getAttribute(NAME);
            String def = e.getAttribute(DEFAULT_DAO);

            if(name.length()>0){
                String refDAO = e.getAttribute(REF_DAO);
                if(refDAO.length()>0){
                    refDAOs.put(name,refDAO);
                }else{
                    String jndi = registry.getSingleNodeValue(e,DSJNDI);
                    String dbType = registry.getSingleNodeValue(e,DBTYPE);
                    String initialFactory = registry.getSingleNodeValue(e,INITIALFACTORY);
                    String providerURL = registry.getSingleNodeValue(e,PROVIDERURL);
                    String transType = registry.getSingleNodeValue(e,TRANSTYPE);
                    String driver = registry.getSingleNodeValue(e,"driver");
                    String url = registry.getSingleNodeValue(e,"url");

                    //user should not be empty string;
                    String user = registry.getSingleNodeValue(e,USER);
                    if(user!=null && user.length()<1){
                        user = null;
                    }
                    String password = registry.getSingleNodeValue(e,PASSWORD);

                    //create the LocalDAO object;
                    DAO dao;
                    ObjectHelperFactory ohf = ObjectHelperFactory.getInstance(loader,beanFactory,getBaseDir(),
                            getClassPath(),dbType);
                    if(TRANSTYPE_TX.equalsIgnoreCase(transType)){
                        //a dao that uses UserTransaction to control Transaction;
                        dao = new JtaDAO(name,ohf);
                    }else{  //a dao that uses direct JDBC call to control the transaction;
                        dao = new JdbcDAO(name,ohf);
                    }
                    ((AbstractDAO)dao).setDriver(driver);
                    ((AbstractDAO)dao).setUrl(url);

                    dao.setDSJndiName(jndi);
                    dao.setInitialFactory(initialFactory);
                    dao.setProviderUrl(providerURL);
                    dao.setPassword(password);
                    dao.setUser(user);

                    NodeList pstmts = registry.selectNodeList(e,"predefined-statements/stmt");
                    this.loadPredifinedStatements(dao,pstmts,registry);

                    //look up the underlying datasource for the LocalDAO object;
                    try{
                        dao.lookup();
                        daos.put(name,dao);
                        //check if there is no default dao currently or this DAO is the default one.
                        if(defaultDAO == null || "true".equals(def)){
                            defaultDAO = dao;
                            daos.put("default",dao);
                        }
                    }catch(NamingException ne){
                        if(log.isEnabledFor(Priority.ERROR)){
                            log.error("can't create DAO \""+name
                                    +"\" because the target DataSource not found: "+jndi,ne);
                        }
                    }
                }
            }
        }
    }

    private void loadPredifinedStatements(DAO dao,NodeList nl,XMLFile xml){
        if(nl == null){
            return;
        }
        int count = nl.getLength();
        for(int i=0;i<count;i++){
            Element e = (Element)nl.item(i);
            BaseStatement bs = BaseStatement.create(xml,e);
            if(bs!=null){
                dao.addPredefinedStatement(bs);
            }
        }
    }

    /**
     *
     * 
     */
    public void loadActionFilters(){
        NodeList nl = registry.selectNodeList("/"+BLCONTEXT+"/"+SYSTEM+"/action/filters/filter");
        int count = nl.getLength();

        for(int i=0;i<count;i++){
            Element filterEle = (Element)nl.item(i);
            String clsName = filterEle.getAttribute("class");
            if(clsName!=null && clsName.length()>0){
                String action = filterEle.getAttribute("action");
                if(action == null || action.length()<1){
                    action ="*";
                }

                try {
                    Object o;
                    o = Class.forName(clsName).newInstance();
                    ObjectWrapper filter = new ObjectWrapper(o);
                    filter.setAttribute("action",action);
                    actionFilters.add(filter);

                } catch (IllegalAccessException e) {
                    Logger.getLogger(this.getClass()).error(e);
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    Logger.getLogger(this.getClass()).error(e);
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    Logger.getLogger(this.getClass()).error(e);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     *
     */
    public void loadActionListeners(){
        NodeList nl = registry.selectNodeList("/"+BLCONTEXT+"/"+SYSTEM+"/action/listeners/listener");
        int count = nl.getLength();

        for(int i=0;i<count;i++){
            Element filterEle = (Element)nl.item(i);
            String clsName = filterEle.getAttribute("class");
            if(clsName!=null && clsName.length()>0){
                String action = filterEle.getAttribute("action");
                if(action == null || action.length()<1){
                    action ="*";
                }

                try {
                    Object o;
                    o = Class.forName(clsName).newInstance();
                    ObjectWrapper listener = new ObjectWrapper(o);
                    listener.setAttribute("action",action);
                    actionListeners.add(listener);
                } catch (IllegalAccessException e) {
                    Logger.getLogger(this.getClass()).error(e);
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    Logger.getLogger(this.getClass()).error(e);
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    Logger.getLogger(this.getClass()).error(e);
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * load all the config items' values from the registry file;
     */
    private void loadConfigItems(){
        NodeList nl = registry.selectNodeList("/"+BLCONTEXT+"/"+CONFIGITEMS+"/"+GROUP);
        int count = nl.getLength();

        for(int i=0;i<count;i++){
            Element e = (Element)nl.item(i);
            String name = e.getAttribute(NAME);
            NodeList items = registry.selectNodeList(e,ITEM);
            int count2 = items.getLength();
            for(int j=0;j<count2;j++){
                Element item=(Element)items.item(j);
                String key = item.getAttribute(ITEMKEY);
                String value = item.getAttribute(ITEMVALUE);
                setConfigValue(name,key,value);
            }
        }
    }

    /**
     *
     *
     */
    private void loadInitializers(){
        NodeList nodes = registry.selectNodeList("/"+BLCONTEXT+"/initializers/initializer");
        if(nodes!=null){
            int count = nodes.getLength();
            if(count>0){
                initializers = new ArrayList();
            }
            for(int i=0;i<count;i++){
                Element ele = (Element)nodes.item(i);
                String className = ele.getAttribute("class");
                if(className!=null && className.length()>0){
                    initializers.add(className);
                }
            }
        }
    }

    /**
     * load all the smtp proxies;
     */
    private void loadSmtps(){
        NodeList nodes = registry.selectNodeList("/"+BLCONTEXT+"/smtps/smtp");
        if(nodes!=null){
            int count = nodes.getLength();
            for(int i=0;i<count;i++){
                Element ele = (Element)nodes.item(i);
                SmtpProxy proxy = new SmtpProxy(ele);
                smtps.put(proxy.getName(),proxy);
            }
        }
    }

    /**
     * save all the config items' value in a group.
     *
     * @param group the displayName of the group.
     * @param grp all the values in the group.
     */
    private void saveGroupConfigItems(String group,HashMap grp){
        Iterator it = grp.keySet().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            Object value = (Object)grp.get(key);
            registry.setNodeValue("/"+BLCONTEXT+"/"+CONFIGITEMS+"/"+GROUP+"[@"
                    +NAME+"=\""+group+"\"]/"+ITEM+"[@"+ITEMKEY+"=\""
                    +key+"\" and @"+ITEMVALUE+"=\""+value+"\"]",null);
        }
    }

    /**
     *
     *
     * @param name
     * @param refName
     */
    public void saveRefDAO(String name,String refName){
        if(name!=null){
            String baseXPath = "/"+BLCONTEXT+"/"+DAOS+"/"+DAO+"[@"+NAME+"=\""+name+"\"]/";
            //save the jndi displayName of the dao object;
            registry.setNodeValue(baseXPath+"@ref",refName);
            //save the db Type of the dao object;
        }
    }
    /**
     * save a dao to the registry file;
     *
     * @param dao the target LocalDAO object.
     */
    private void saveDAO(DAO dao){

        String jndi = dao.getDSJndiName();
        String dbType = dao.getDBType();
        String initialFactory = dao.getInitialFactory();
        String providerURL = dao.getProviderUrl();
        String name = dao.getName();
        String user = dao.getUser();
        String password = dao.getPassword();

        if(name!=null){
            String baseXPath = "/"+BLCONTEXT+"/"+DAOS+"/"+DAO+"[@"+NAME+"=\""+name+"\"]/";
            //save the jndi displayName of the dao object;
            registry.setNodeValue(baseXPath+DSJNDI,jndi);
            //save the db Type of the dao object;
            registry.setNodeValue(baseXPath+DBTYPE,dbType);
            //set the initialfactory to look up the datasource;
            if(initialFactory!=null && initialFactory.length()>0){
                registry.setNodeValue(baseXPath+INITIALFACTORY,initialFactory);
            }
            //set the provider url to look up the data source;
            if(providerURL!=null && providerURL.length()>0){
                registry.setNodeValue(baseXPath+PROVIDERURL,providerURL);
            }
            //set the user to get connection from the datasource;
            if(user!=null && user.length()>0){
                registry.setNodeValue(baseXPath+USER,user);
            }

            //set the password used to get a connection from the datasouce;
            if(password!=null && password.length()>0){
                registry.setNodeValue(baseXPath+PASSWORD,password);
            }

            //check if the dao is the default one;
            if(defaultDAO == dao){
                registry.setNodeValue(baseXPath+"@default","true");
            }

            //not a good idea to code like the following.
            if(dao instanceof JdbcDAO){
                registry.setNodeValue(baseXPath+TRANSTYPE,TRANSTYPE_JDBC);
            }else if(dao instanceof JtaDAO){
                registry.setNodeValue(baseXPath+TRANSTYPE,TRANSTYPE_TX);
            }
        }
    }
}

