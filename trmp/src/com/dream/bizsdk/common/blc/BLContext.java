/**
 * �������Ȩ��chugh���У�����������Ȩ�� @copyright2001��
 */

package com.dream.bizsdk.common.blc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dream.app.district.DistrictBean;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.asyn.ACProxy;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.database.dao.DAODef;
import com.dream.bizsdk.common.database.dao.TX;
import com.dream.bizsdk.common.database.datadict.DataDict;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.event.EventInitializationException;
import com.dream.bizsdk.common.event.EventManager;
import com.dream.bizsdk.common.fax.FaxProxy;
import com.dream.bizsdk.common.smtp.ISMTPProxy;
import com.dream.bizsdk.common.smtp.SMTPProxy;
import com.dream.bizsdk.common.util.string.StringConvertor;
import com.dream.bizsdk.common.util.xml.XMLFile;


/**
 * The BlContext class supplys a context for BLC (Business Logic Compoment) to
 * run within).
 * A BlContext object contains following object:
 * one ore more DAO object;
 * one workflow object;
 * one business data dictionary;
 * one error no-error msg map;
 * one system functions map;
 * one role-function map;
 * one BLCsPool object;
 * one config values map;
 * one logger object;
 *
 * @author divine
 */

public class BLContext implements IBLContext {
    protected boolean _isRemote;

    protected boolean _isDebug;

    protected boolean _isReflection = true;

    protected String _name;

    protected String proxyPath;

    protected HashMap callableBLContext = new HashMap();

    protected EventManager eventManager;
    /**
     * the path of system config file
     */
    protected String configFilePath;
    /**
     * daos defined in this bl context;
     */
    protected HashMap _daos = new HashMap();
    /**
     * definition of daos,such as user,password,url,connection count;
     */
    protected HashMap _daoDef = new HashMap();
    /**
     * bdc of this BLContext;should be defined in core dao
     */
    protected BizData _bdc;
    /**
     * BLC pool object
     */
    protected BLCsPool _blcPool;
    /**
     * error message definition,should be defined in core dao;
     */
    protected BizData _errors;
    /**
     * workflow engine in this bl context;
     */
    //protected Workflow _wf;
    /**
     * logger of the object;
     */
    protected Logger _log;
    /**
     * this contains the configuration values;
     */
    protected HashMap configValues = new HashMap();

    protected HashMap smtpProxy = new HashMap();

    protected HashMap proxy = new HashMap();

    protected BizData _rowPrivil;

    protected BizData _colPrivil;
    
    private List<DistrictBean> districtBeans = new ArrayList<DistrictBean>();
    private List<DistrictBean> areaBeans = new ArrayList<DistrictBean>();
	private List<DistrictBean> provinceBeans = new ArrayList<DistrictBean>();
    private List<DistrictBean> cityBeans = new ArrayList<DistrictBean>();

	private Logger log;

    private ProxyBuilder pg;

    private FaxProxy fp;

    private ACProxy acProxy;

    private BLContextConfig config;
    

    /**
     * @param config
     */
    public BLContext(BLContextConfig config) throws Exception {
        this.config = config;
        init();
    }

    /**
     * contruct a BLContext object based upon a config file path;
     *
     * @param configFilePath the system config file path;
     * @throws Exception
     */
    public BLContext(String configFilePath) throws Exception {
        try {
            File f;

            config = new BLContextConfig();
            //save the path of the config file;
            config.setContextConfig(f = new File(configFilePath));
            //create the file and init this BLContext Object;
            String path = StringConvertor.formatPath(f.getParentFile().getAbsolutePath());
            config.setContextConfig(new File(path + "event-register.xml"));

            init();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Construct a BLContext object based upon a config file object;
     *
     * @param f the config file object;
     * @throws Exception
     */
    public BLContext(File f) throws Exception {
        try {
            config = new BLContextConfig();
            //save the path of the config file;
            config.setContextConfig(f);
            //create the file and init this BLContext Object;
            String path = StringConvertor.formatPath(f.getParentFile().getAbsolutePath());
            config.setEventConfig(new File(path + "event-config.xml"));
            //init this BLContext object;
            init();
        } catch (Exception e) {
            System.out.println("BLContext.BLContext():Excetpion caught,can't create and initialize a BLcontext object.");
            throw e;
        }
    }

    /**
     * get a dao object by name,if the dao doest not exist,null is returned;
     *
     * @param name name of the dao to get;
     * @return the target DAO object;
     */
    public DAO getDAO(String name) {
        return (DAO) _daos.get(name);
    }

    /**
     * Get all the dao object in this system;
     *
     * @return the array of all the dao objects;
     */
    public DAO[] getAllDAOs() {
        return (DAO[]) _daos.values().toArray(new DAO[0]);
    }

    /**
     * get a HashMap that contains all the DAO objects;
     *
     * @return
     */
    public HashMap getDAOsMap() {
        return _daos;
    }

    /**
     * get the BLCsPool contained in the BLContext object;
     *
     * @return BLCsPool Object;
     */
    public BLCsPool getBLCsPool() {
        return _blcPool;
    }

    /**
     * get the business data dictionary in the blcontext object;
     * In the returned Business there are two types of Dictionary:
     * one is of structure as:
     * tableName/colName/colCode/colValue;
     * the other is as:
     * tableName+"/"+colName/colName/colCode/colValue);
     * The Business data dictionary is load when the BLContext is created,
     * and will not refresh if the underlying database tables has been changed(
     * records are inserted,deleted,updated from the Business DataItem Dictionary
     * table).
     *
     * @return a BizData objec that contains the above two records list;
     */
    public BizData getBizDataDict() {
        return _bdc;
    }

    /**
     * Get the System DataItem Dictionay Object from this BLContext object,
     * this object is loaded when this BLContext object is created. and
     * changes made to the relative database tables will be reflected
     * when a new BLContext object is created;
     *
     * @return the DataDict object.
     */
    public DataDict getDataDict() {
        return getDAO("core").getDataDict();
    }

    /**
     * get all the datadict object in the system;
     *
     * @return a HashMap object contains all dao's  datadict;
     */
    public HashMap getAllDataDict() {
        HashMap dcs = new HashMap();

        Iterator names = _daos.keySet().iterator();
        while (names.hasNext()) {
            String name = (String) names.next();
            dcs.put(name, getDAO(name).getDataDict());
        }
        return dcs;
    }

    /**
     * Get the error messages of the system, these messages was loaded into
     * memory at the startup of the BLContext object;
     *
     * @return BizData object contains the error messages definition;
     */
    public BizData getErrors() {
        return _errors;
    }

    /**
     * Get the workflow engine in this bl context,the returned object maybe
     * null.
     *
     * @return a Workflow object;
     */
    /**    public Workflow getWorkflow() {
     return _wf;
     }
     */
    /**
     * get row privil;
     *
     * @return
     */
    public BizData getRowPrivil() {
        return _rowPrivil;
    }

    /**
     * return column privil;
     *
     * @return
     */
    public BizData getColPrivil() {
        return _colPrivil;
    }

    /**
     * notify an event to this BLContext;
     *
     * @param name the name of the event;
     * @param rd   the request of the Data;
     */
    public void notifyEvent(String name, BizData rd) {
        eventManager.notifyEvent(name, rd);
    }

    public synchronized void release() {
        //release blc pool;
        //this._blcPool.release();

        //release DAO pool;
        Iterator it = _daos.values().iterator();
        while (it.hasNext()) {
            ((DAO) it.next()).release();
        }
        _daos.clear();

    }

    /**
     * get config values for this bl context object; BLC can set some config values
     * in the system configuration file. see the format of the config file.
     *
     * @param group the name of the config values group;
     * @param key   the key of the config value;
     * @return the target config value.
     */
    public String getConfigValue(String group, String key) {
        return (String) configValues.get(group + "/" + key);
    }

    /**
     * @return
     */
    public ISMTPProxy getSMTPProxy() {
        return (ISMTPProxy) smtpProxy.get("default");
    }

    /**
     * get a named Mail Proxy Object;
     *
     * @return the named Mail proxy;
     */
    public ISMTPProxy getSMTPProxy(String name) {
        return (ISMTPProxy) smtpProxy.get(name);
    }

    /**
     * @return
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * get the name of this BLContext;
     *
     * @return the name of the BLContext;
     */
    public String getName() {
        return _name;
    }

    /**
     * set the name of this blContext object;
     *
     * @param name the name of the BLContext;
     */
    void setName(String name) {
        _name = name;
    }

    public boolean isRemote() {
        return _isRemote;
    }

    public void setRemote(boolean remote) {
        _isRemote = true;
    }

    /**
     * add callable BLContext object;
     *
     * @param context the BLContext to bde added;
     */
    public void addCallableBLContext(IBLContext context) {
        try {
            if (context.isRemote() || _isRemote == false) {
                if (callableBLContext.get(context.getName()) == null) {
                    callableBLContext.put(context.getName(), context);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * remove callable BLContext object;
     *
     * @param name the name of the BLContext object to be removed;
     */
    public void removeCallableBLContext(String name) {
        try {
            callableBLContext.remove(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a callable BLContext from this BLContext;
     *
     * @param contextName the target BLContext name;
     * @return the named BLContext object;
     */
    public IBLContext getCallableBLContext(String contextName) {
        return (IBLContext) callableBLContext.get(contextName);
    }

    /**
     * obtain a UserTransaction proxy;
     *
     * @return a new TX object;
     */
    public TX newTX() {
        TX tx = null;
        String jndi = getConfigValue("UserTransaction", "jndiName");
        try {
            InitialContext ic = new InitialContext();
            UserTransaction utx = (UserTransaction) ic.lookup(jndi);
            tx = new TX(utx);
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        return tx;
    }


    /**
     * add a configuration item to the config file and update the configvalues
     * cache at the same time. Compoments can call this method to register it's
     * own config values at runtime.
     *
     * @param groupName the configuration group name;
     * @param key       the key of the configuration;
     * @param value     the value of the configuration;
     * @return true if succeed,false otherwise.
     */
    public boolean addConfigValue(String groupName, String key, String value) {
        NodeList groupNL = null;
        NodeList keyNL = null;
        Element groupN = null;
        Element keyN = null;
        Element root = null;
        try {
            /**create the Document object*/
            XMLFile xml = new XMLFile(configFilePath);
            Document doc = xml.getDocument();
            //get the root element;
            root = doc.getDocumentElement();

            /**check the existence of the {group} element,and create it
             *if not exists;*/
            groupNL = doc.getElementsByTagName(groupName);
            if (groupNL == null || groupNL.getLength() < 1) {
                groupN = doc.createElement(groupName);
                root.appendChild(groupN);
            } else {
                groupN = (Element) groupNL.item(0);
            }

            /**create the key element */
            keyNL = groupN.getElementsByTagName(key);
            if (keyNL == null || keyNL.getLength() < 1) {
                keyN = doc.createElement(key);
                groupN.appendChild(keyN);
            } else {
                keyN = (Element) keyNL.item(0);
            }

            /**modify or set the node value*/
            Node child = keyN.getFirstChild();
            if (child == null) {
                child = doc.createTextNode(value);
                keyN.appendChild(child);
            } else {
                child.setNodeValue(value);
            }
            //close and flush the xml file;
            xml.close();
            //set the config value to the cache;
            configValues.put(groupName + "/" + key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * execute a BL method in this BLContext, BLContext Object has a member that is a Object
     * of call BLCsPool which caches all the BLC object of the application.
     *
     * @param className
     * @param methodName
     * @param rd
     * @param sd
     * @return
     */
    public BLResult execBL(String className, String methodName, BizData rd, BizData sd) {
        return new BLResult(execRequest(className, methodName, rd, sd), rd, sd);
    }

    /**
     * @param className
     * @param methodName
     * @param rd
     * @param sd
     * @return
     */
    private final int execRequest(String className, String methodName, BizData rd, BizData sd) {
        int retVal = -1;
        //call the bl method via reflect, this is used in debug mode;
        //Reflect method call's performance is very poor compared with
        //direct function call;
        AbstractBLC blc = (AbstractBLC) _blcPool.getPoolItem(className);
        if (_isReflection) {
            try {
                if (blc != null) {
                    /**first make a version 2 call,if BL_NOT_AVAILABLE is returned
                     *then try to make a version2 call*/
                    retVal = blc.execBL(methodName, rd, sd);
                    if (retVal == SysError.BL_NOT_AVAILABLE) {
                        rd.add(SysVar.SS_DATA, sd);
                        retVal = blc.execBL(methodName, rd);
                    }
                } else {
                    retVal = SysError.BL_NOT_AVAILABLE;
                }
            } catch (InvocationTargetException e) {
                rd.add(SysVar.THROWN, e.getTargetException());
                return SysError.UNKNOWN_ERROR;
            }
        } else { //call the bl by proxy, direct function call, not via reflection;
            String name = className + "." + methodName;
            String name2 = name.replace('.', '_');
            Proxy p = (Proxy) proxy.get(name2);

            if (log.isDebugEnabled()) {
                log.debug("runing proxy " + name);
            }

            //there is no proxy instance for this bl method currently,try to create it;
            if (p == null) {
                try {
                    try {
                        p = (Proxy) Class.forName("com.dream.bizsdk.common.blc."+name2).newInstance();
                    } catch (ClassNotFoundException cnfe) {
                        //generate the proxy java source and compile it;
                        pg.gen(name);
                        //create the instance again;
                        p = (Proxy) Class.forName("com.dream.bizsdk.common.blc."+name2).newInstance();
                    }
                    if (p != null) {
                        p.setBLC(blc);
                        synchronized (proxy) {
                            proxy.put(name2, p);
                        }
                    }
                } catch (ClassNotFoundException e2) {
                    log.error("can't create proxy class " + name);
                    return SysError.CANT_CREATE_PROXY_CLASS;
                } catch (IOException ioe) {
                    log.error("can't create proxy class " + name, ioe);
                    return SysError.CANT_CREATE_PROXY_CLASS;
                } catch (IllegalAccessException e) {
                    log.error("can't access the default constructor " + name, e);
                    return SysError.CANT_ACCESS_PROXY_CLASS;
                } catch (InstantiationException e) {
                    log.error("can't access the default constructor " + name, e);
                    return SysError.CANT_CREATE_PROXY_INSTANCE;
                }
            }

            try {
                retVal = p.run(rd, sd);
            } catch (Throwable t) {
                log.error("Exception in calling proxy method", t);
                rd.add(SysVar.THROWN, t);
                return SysError.UNKNOWN_ERROR;
            }

            if (log.isDebugEnabled()) {
                log.debug("finished proxy " + name);
            }
        }

        return retVal;
    }

    /**
     * @param daoName
     * @param sql
     * @param resultName
     * @param data
     * @return
     */
    public BizData execQuerySql(String daoName, String sql, String resultName, BizData data) throws SQLException {
        DAO dao = getDAO(daoName);
        if (dao != null) {
            try {
                dao.executeQuery(sql, resultName, data);
                return data;
            } catch (SQLException e) {
                log.error("SQLException when execute sql:" + sql + " in dao:" + daoName, e);
                throw e;
            }
        } else {
            log.error("No such dao:" + daoName);
            throw new SQLException("NO such dao:" + daoName);
        }
    }

    /**
     * Init this BLContext object, this can be a time consuming operation depends
     * on the scale of the system.
     *
     * @param f the config File objectl
     * @throws FactoryConfigurationError
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws SQLException
     * @throws Exception
     */
    protected void init(File f) throws FactoryConfigurationError
            , ParserConfigurationException, SAXException, IOException, SQLException, Exception {
        String str = null;

        log = Logger.getLogger("com.dream.blcontext");

        if (log.isInfoEnabled()) {
            log.info("creating BLContext");
        }

        try {

            String path = f.getAbsolutePath();
            int index = path.lastIndexOf(File.separatorChar);
            path = path.substring(0, index + 1);

            if (log.isInfoEnabled()) {
                log.info("initializing events");
            }
            eventManager = new EventManager(this);
            String evenRegisterFile = path + "event-register.xml";
            eventManager.init(evenRegisterFile);
            if (log.isInfoEnabled()) {
                log.info("events initialized.");
            }
        } catch (EventInitializationException eie) {
            log.error("Event initialization exception.", eie);
        }

        try {
            readConfigFile(f);
            //check if debug level;
            str = getConfigValue("system", "debug");
            if (str != null && str.compareTo("1") == 0) {
                _isDebug = true;
            }

            //check if use reflection to call bl methods;
            str = getConfigValue("system", "reflection");
            if (str != null && (str.equals("0") || str.equalsIgnoreCase("true"))) {
                _isReflection = false;
            }

            createSMTPProxy();
            createFaxProxy();
            _blcPool = new BLCsPool(this);
            establishDAOs(_log);
            _bdc = loadBizDataDict();
            _errors = loadErrors();
            _rowPrivil = loadRowPrivil();
            _colPrivil = loadColPrivil();

            //create the ProxyGen object;
            if (!_isReflection) {
                String pgRoot = getConfigValue("system", "proxy_class_path");
                pgRoot = StringConvertor.replace(pgRoot,"${","}");

                String pgCP = getConfigValue("system", "proxy_compile_classpath");
                if (pgCP == null) {
                    pgCP = "${java.class.path}";  //use system class path;
                }

                pgCP = StringConvertor.replace(pgCP,"${","}");
                if (log.isDebugEnabled()) {
                    log.debug("Proxy generator created with source file path:" + pgRoot + " and classpath:" + pgCP);
                }
                pg = new ProxyBuilder(pgRoot, pgCP);
            }

            acProxy = new ACProxy(this);

            if (log.isInfoEnabled()) {
                log.info("BLContext created.");
            }
        } catch (FactoryConfigurationError fce) {
            log.error("FactoryConfigurationError", fce);
            throw fce;
        } catch (ParserConfigurationException pce) {
            log.error("ParserConfigurationException", pce);
            throw pce;
        } catch (SAXException saxe) {
            log.error("SAXException", saxe);
            throw saxe;
        } catch (IOException ioe) {
            log.error("IOException", ioe);
            throw ioe;
        } catch (SQLException sqle) {
            log.error("SQLException", sqle);
            throw sqle;
        }
    }

    protected void init() throws FactoryConfigurationError
            , ParserConfigurationException, SAXException, IOException, SQLException, Exception {
        String str = null;

        log = Logger.getLogger("com.dream.blcontext");

        if (log.isInfoEnabled()) {
            log.info("creating BLContext");
        }

        try {
            if (log.isInfoEnabled()) {
                log.info("scheduling Timed task");
            }


            if (log.isInfoEnabled()) {
                log.info("initializing events");
            }
            eventManager = new EventManager(this);
            eventManager.init(config.getEventConfig());
            if (log.isInfoEnabled()) {
                log.info("events initialized.");
            }
        } catch (EventInitializationException eie) {
            log.error("Event initialization exception.", eie);
        }

        try {
            readConfigFile(config.getContextConfig());
            //check if debug level;
            str = getConfigValue("system", "debug");
            if (str != null && str.compareTo("1") == 0) {
                _isDebug = true;
            }

            //check if use reflection to call bl methods;
            str = getConfigValue("system", "reflection");
            if (str != null && (str.equals("0") || str.equalsIgnoreCase("true"))) {
                _isReflection = false;
            }

            createSMTPProxy();
            createFaxProxy();
            _blcPool = new BLCsPool(this);
            establishDAOs(_log);
            _bdc = loadBizDataDict();
            _errors = loadErrors();
            _rowPrivil = loadRowPrivil();
            _colPrivil = loadColPrivil();

            //create the ProxyGen object;
            if (!_isReflection) {
                String pgRoot = getConfigValue("system", "proxy_class_path");
                pgRoot = StringConvertor.replace(pgRoot,"${","}");

                String pgCP = getConfigValue("system", "proxy_compile_classpath");
                if (pgCP == null) {
                    pgCP = "${java.class.path}";  //use system class path;
                }

                pgCP = StringConvertor.replace(pgCP,"${","}");
                if (log.isDebugEnabled()) {
                    log.debug("Proxy generator created with source file path:" + pgRoot + " and classpath:" + pgCP);
                }
                pg = new ProxyBuilder(pgRoot, pgCP);
            }

            acProxy = new ACProxy(this);

            if (log.isInfoEnabled()) {
                log.info("BLContext created.");
            }
        } catch (FactoryConfigurationError fce) {
            log.error("FactoryConfigurationError", fce);
            throw fce;
        } catch (ParserConfigurationException pce) {
            log.error("ParserConfigurationException", pce);
            throw pce;
        } catch (SAXException saxe) {
            log.error("SAXException", saxe);
            throw saxe;
        } catch (IOException ioe) {
            log.error("IOException", ioe);
            throw ioe;
        } catch (SQLException sqle) {
            log.error("SQLException", sqle);
            throw sqle;
        }
    }

    /**
     * reinit this BLContext object, this can be a time consuming operation depends
     * on the scale of the system.
     *
     * @throws FactoryConfigurationError
     */
    public void reinit() {
        String str = null;
        try {
            _daoDef.clear();
            _daos.clear();
            _blcPool.release();
            configValues.clear();
            readConfigFile(config.getContextConfig());
            //check if debug level;
            str = getConfigValue("system", "debug");
            if (str != null && str.compareTo("1") == 0) {
                _isDebug = true;
            }

            //check if use reflection to call bl methods;
            str = getConfigValue("system", "reflection");
            if (str != null && str.compareTo("1") == 0) {
                _isReflection = true;
            }

            recreateDAOs(_log);
            createSMTPProxy();
            createFaxProxy();
            _bdc = loadBizDataDict();
            _errors = loadErrors();
            _rowPrivil = loadRowPrivil();
            _colPrivil = loadColPrivil();

            //create the ProxyGen object;
            if (!_isReflection) {
                String pgRoot = getConfigValue("system", "proxy_class_path");
                pgRoot = StringConvertor.replace(pgRoot,"${","}");
                String pgCP = getConfigValue("system", "proxy_compile_classpath");
                if (pgCP == null) {
                    pgCP = "${java.class.path}";  //use system class path;
                }
                pgCP = StringConvertor.replace(pgCP,"${","}");

                if (log.isDebugEnabled()) {
                    log.debug("Proxy generator created with source file path:" + pgRoot + " and classpath:" + pgCP);
                }
                pg = new ProxyBuilder(pgRoot, pgCP);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * create fax proxy;
     */
    public void createFaxProxy() {
        try{
            fp = new FaxProxy();
            fp.setBitRate(Integer.parseInt(getConfigValue("fax", "bitRate")));
            fp.setDialTone(Boolean.getBoolean(getConfigValue("fax", "dialTone")));
            fp.setFlowControl(getConfigValue("fax", "flowControl"));
            fp.setFlowControlCommand(getConfigValue("fax", "flowControlCommand"));
            fp.setFromNO(getConfigValue("fax", "fromNO"));
            fp.setInitialString(getConfigValue("fax", "initialString"));
            fp.setModemClass(getConfigValue("fax", "modemClass"));
            fp.setPort(getConfigValue("fax", "port"));
        }catch(Exception e){
            log.error("Can't create fax proxy",e);
        }
    }

    /**
     * read configuration parameters in the configuration file.
     *
     * @throws Exception
     */
    protected void readConfigFile(String configFilePath)
            throws FactoryConfigurationError, ParserConfigurationException
            , SAXException, IOException, Exception {
        File f = new File(configFilePath);
        readConfigFile(f);
    }

    /*
     *read config file,and create DAODef objects;
     *@param f-File Object of the config file;
     *@return true if read successfully,false otherwise;
     */
    protected void readConfigFile(File f) throws FactoryConfigurationError
            , ParserConfigurationException, SAXException, IOException, Exception {
        int i = 0;
        int size = 6;
        boolean twoPhaseCommit = false;

        DAODef daoDef = null;

        XMLFile config = new XMLFile(f);
        Node n = config.getDocument().getDocumentElement();
        NodeList nl = n.getChildNodes();
        int count = nl.getLength();
        i = 0;
        while (i < count) {
            Node item = nl.item(i);
            String groupName = item.getNodeName();
            if (groupName.compareTo("daos") == 0) {
                i++;
                continue;
            }
            NodeList children = item.getChildNodes();
            int childrenCount = children.getLength();
            int j = 0;
            while (j < childrenCount) {
                String childName = children.item(j).getNodeName();
                NodeList tmp = children.item(j).getChildNodes();
                if (tmp != null && tmp.getLength() > 0) {
                    String childValue = tmp.item(0).getNodeValue();
                    configValues.put(groupName + "/" + childName, childValue);
                }
                j++;
            }
            i++;
        }

        String twoCommit = getConfigValue("UserTransaction", "enabled");
        if (twoCommit != null && twoCommit.compareToIgnoreCase("true") == 0) {
            twoPhaseCommit = true;
        }

        /**the blcontext name*/
        _name = config.getSingleNodeValue("/blcontext/@name");

        /*get the reflection mode*/
        String temp = getConfigValue("system", "reflection");
        _isReflection = (temp != null && (temp.compareToIgnoreCase("true") == 0 || temp.compareToIgnoreCase("1") == 0) ? true : false);
        nl = config.getDocument().getDocumentElement().getElementsByTagName("dao");
        i = 0;
        count = nl.getLength();
        while (i < count) {
            Node nd = nl.item(i);
            String name = null;
            String dbURL = null;
            String dbUser = null;
            String dbPassword = null;
            String dbConns = null;
            String dbType = null;
            String driver = null;
            String dataSource = null;
            String xa = null;
            String ou = null;
            int initialSize = 0;
        	int minPoolSize = 0;
        	int maxPoolSize = 0;
        	int maxStatements = 0;
        	int maxIdleTime = 0;
            

            Node child = nd.getFirstChild();
            while (child != null) {
                String nodeName = child.getNodeName();
                if (nodeName.compareToIgnoreCase("name") == 0) {
                    name = child.getFirstChild().getNodeValue();
                } else if (nodeName.compareToIgnoreCase("dbURL") == 0) {
                    dbURL = child.getFirstChild().getNodeValue();
                } else if (nodeName.compareToIgnoreCase("dbUser") == 0) {
                    dbUser = child.getFirstChild().getNodeValue();
                } else if (nodeName.compareToIgnoreCase("dbPassword") == 0) {
                    n = child.getFirstChild();
                    if (n == null) {
                        dbPassword = "";
                    } else {
                        dbPassword = n.getNodeValue();
                    }
                } else if (nodeName.compareToIgnoreCase("dbConnections") == 0) {
                    dbConns = child.getFirstChild().getNodeValue();
                } else if (nodeName.compareToIgnoreCase("dbType") == 0) {
                    dbType = child.getFirstChild().getNodeValue();
                } else if (nodeName.compareToIgnoreCase("driver") == 0) {
                    driver = child.getFirstChild().getNodeValue();
                }else if (nodeName.compareToIgnoreCase("initialSize") == 0){
                	String initSize = child.getFirstChild().getNodeValue();
                	initialSize = Integer.parseInt(initSize);
                }else if (nodeName.compareToIgnoreCase("minPoolSize") == 0){
                	String minPS = child.getFirstChild().getNodeValue();
                	minPoolSize = Integer.parseInt(minPS);
                }else if (nodeName.compareToIgnoreCase("maxPoolSize") == 0){
                	String maxPS = child.getFirstChild().getNodeValue();
                	maxPoolSize = Integer.parseInt(maxPS);
                }else if (nodeName.compareToIgnoreCase("maxStatements") == 0){
                	String maxSTMTS = child.getFirstChild().getNodeValue();
                	maxStatements = Integer.parseInt(maxSTMTS);
                }else if (nodeName.compareToIgnoreCase("maxIdleTime") == 0){
                	String maxTime = child.getFirstChild().getNodeValue();
                	maxIdleTime = Integer.parseInt(maxTime);
                } else if (nodeName.compareToIgnoreCase("datasource") == 0) {
                    dataSource = child.getFirstChild().getNodeValue();
                    NamedNodeMap nnm = child.getAttributes();
                    try {
                        xa = nnm.getNamedItem("xa").getNodeValue();
                    } catch (Throwable t) {
                        xa = null;
                    }
                    try {
                        ou = nnm.getNamedItem("overrideUser").getNodeValue();
                    } catch (Throwable t) {
                        ou = null;
                    }
                } 
                
                child = child.getNextSibling();
            }
            if (name == null || dbURL == null || dbUser == null) {
                throw new Exception("Database configuration error,"
                        + "The name,url and user of a DAO must be specfied!");
            }
            if (dbPassword == null) {
                dbPassword = new String("");
            }
            if (dbConns == null) {
                dbConns = "5";
            }
            try {
                size = Integer.valueOf(dbConns).intValue();
            } catch (Exception e) {
                size = 5;
            }
            if (dataSource == null) {
                daoDef = new DAODef(name, dbURL, dbType, dbUser
                        , dbPassword, size, driver, initialSize, minPoolSize, maxPoolSize, maxStatements,maxIdleTime);
            } /*else {
                daoDef = new DAODef(name, dbType, dataSource, xa != null && xa.compareToIgnoreCase("true") == 0 ? true : false
                        , ou != null && ou.compareToIgnoreCase("true") == 0 ? true : false, twoPhaseCommit, dbUser, dbPassword
                        		, initialSize, minPoolSize, maxPoolSize, maxStatements,maxIdleTime );
            }*/
            _daoDef.put(name, daoDef);
            i++;
        }
    }

    /**
     * establish all configured DAOs object;
     *
     * @throws ClassNotFoundException;
     * @throws SQLException;
     */
    protected void establishDAOs(Logger log) throws ClassNotFoundException, Exception {
        Iterator it = _daoDef.values().iterator();
        while (it.hasNext()) {
            DAODef daoDef = (DAODef) it.next();
            try {
                if (this.log.isInfoEnabled()) {
                    this.log.info("creating DAO named " + daoDef.getName());
                }
                DAO dao = new DAO(daoDef);
                _daos.put(daoDef.getName(), dao);
                if (this.log.isInfoEnabled()) {
                    this.log.info("DAO named " + daoDef.getName() + " created.");
                }
            } catch (ClassNotFoundException cnfe) {
                this.log.error("JDBC Driver " + daoDef.getDriver() + " not found.", cnfe);
                ;
                throw cnfe;
            } catch (SQLException e) {
                this.log.error("exception when trying to create DAO " + daoDef.getName(), e);
                ;
                throw e;
            }
        }
    }

    /**
     * create smtp prxoy;
     */
    protected void createSMTPProxy() {
        int port = 25;
        String userID = getConfigValue("mailproxy", "smtpUser");
        String password = getConfigValue("mailproxy", "smtpPassword");
        String mailServer = getConfigValue("mailproxy", "smtpServer");
        String mailPort = getConfigValue("mailproxy", "smtpPort");
        String mailAccount = getConfigValue("mailproxy", "mailAccount");
        String accountName = getConfigValue("mailproxy", "accountName");
        String needAuth = getConfigValue("mailproxy", "needAuth");
        try {
            port = Integer.valueOf(mailPort).intValue();
        } catch (Exception e) {

        }

        SMTPProxy smtp = new SMTPProxy(userID, password, mailServer, port, mailAccount, accountName, needAuth != null && needAuth.compareToIgnoreCase("true") == 0 ? true : false);
        smtpProxy.put("default", smtp);
    }

    /**
     * reestablish all configured DAOs object;
     */
    protected void recreateDAOs(Logger log) throws ClassNotFoundException, Exception {
        _daos.clear();
        Iterator it = _daoDef.values().iterator();
        while (it.hasNext()) {
            DAODef daoDef = (DAODef) it.next();
            try {
                if (this.log.isInfoEnabled()) {
                    this.log.info("creating DAO named " + daoDef.getName());
                }

                DAO dao = new DAO(daoDef);
                _daos.put(daoDef.getName(), dao);
                if (this.log.isInfoEnabled()) {
                    this.log.info("DAO named " + daoDef.getName() + " created.");
                }
            } catch (ClassNotFoundException cnfe) {
                this.log.error("JDBC Driver:" + daoDef.getDriver() + " NOT found.", cnfe);
                throw cnfe;
            } catch (SQLException e) {
                this.log.error("SQLException", e);
                throw e;
            }
        }
    }

    /**
     * load business data dictionary from the core dao;
     */
    protected BizData loadBizDataDict() throws SQLException {

        DAO dao = this.getDAO("core");
    	Connection conn = dao.getConnection();
        ResultSet rs = null;
        BizData data = new BizData();
        try {
            if (this.log.isInfoEnabled()) {
                log.info("loading business data dictionary");
            }
            rs = dao.executeQuery("select tableName as \"tableName\",colName "
                    + "as \"colName\",colCode as \"colCode\",colValue "
                    + "as \"colValue\" from DRMBIZDATADICT", conn);
            if (rs != null) {
                while (rs.next()) {
                    String tableName = rs.getString("tableName");
                    String colName = rs.getString("colName");
                    String colCode = rs.getString("colCode");
                    String colValue = rs.getString("colValue");
                    //biz dictionary 1 with the {table Name} as table name;
                    //users can call get(tableName,colName,colCode) to get a
                    //specific dictionary item's values;
                    data.add(tableName, colName, colCode, colValue);
                    /**biz dictionary 2 with the {table name+"/"+column name} as
                     *the table name,so can call getTableRowsCount(...) to decide
                     *how many values does this column holds in the biz dictionary*/
                    data.add(tableName + "/" + colName, colName, colCode, colValue);
                }
            }
            if (this.log.isInfoEnabled()) {
                log.info("business data dictionary loaded.");
            }
        } catch (SQLException e) {
            log.error("SQLException when loading BizDataDict.", e);
        } finally {
            if (rs != null) {
                rs.close();
                conn.close();
                rs = null;
            }
        }
        return data;
    }

    /**
     * Load error message from the core dao;
     */
    protected BizData loadErrors() throws SQLException {
        DAO dao = this.getDAO("core");
        Connection conn = dao.getConnection();
        ResultSet rs = null;
        try {
            if (this.log.isInfoEnabled()) {
                log.info("loading error messages map");
            }

            BizData data = new BizData();
            rs = dao.executeQuery("select errCode as \"errCode\",errMessage "
                    + "as \"errMessage\",errLevel as \"errLevel\" from DRMERROR", conn);
            if (rs != null) {
                while (rs.next()) {
                    String errCode = rs.getString("errCode");
                    String errMessage = rs.getString("errMessage");
                    int errLevel = rs.getInt("errLevel");
                    /**add error code and error message to the bizdata object*/
                    data.add("DRMError", "errMessage", errCode, errMessage);
                    data.add("DRMError", "errLevel", errCode, new Integer(errLevel));
                }
            }
            if (this.log.isInfoEnabled()) {
                log.info("error messages map loaded.");
            }

            return data;
        } catch (SQLException e) {
            log.error("SQLException when loading Error Messages.", e);
            return null;
        } finally {
            //close statement here;
            if (rs != null) {
            	rs.close();
            	conn.close();
                rs = null;
            }
        }
    }

    protected BizData loadRowPrivil() throws SQLException {
        BizData data = new BizData();
        DAO dao = this.getDAO("core");
        Connection conn = dao.getConnection();
        ResultSet rs = null;
        try {
            if (this.log.isInfoEnabled()) {
                log.info("loading row data privilege settings");
            }

            rs = dao.executeQuery("select roleID ,tableName,subWhere from DRMROWPRIVIL ", conn);
            if (rs != null) {
                while (rs.next()) {
                    String roleID = rs.getString(1);
                    String tableName = rs.getString(2);
                    String subWhere = rs.getString(3);
                    data.add(roleID, tableName, subWhere);
                }
            }
            if (this.log.isInfoEnabled()) {
                log.info("row data privilege setting loaded");
            }
            return data;
        } catch (SQLException e) {
            log.error("SQLException when loading Row Data Privilege settings.", e);
        } finally {
            //close statement here;
            if (rs != null) {
            	rs.close();
                conn.close();
                rs = null;
            }
        }
        return data;
    }

    protected BizData loadColPrivil() throws SQLException {
        DAO dao = this.getDAO("core");
        Connection conn = dao.getConnection();
        BizData data = new BizData();
        ResultSet rs = null;
        try {
            if (this.log.isInfoEnabled()) {
                log.info("loading column data privilege settings");
            }

            rs = dao.executeQuery("select roleID ,tableName,colName,privil from DRMCOLPRIVIL", conn);
            if (rs != null) {
                while (rs.next()) {
                    String roleID = rs.getString(1);
                    String tableName = rs.getString(2);
                    String colName = rs.getString(3);
                    String privil = rs.getString(4);
                    data.add(roleID, tableName, colName, privil);
                }
            }

            if (this.log.isInfoEnabled()) {
                log.info("column data privilege settings loaded.");
            }

            return data;
        } catch (SQLException e) {
            log.error("SQLException when loading Column Data Privilege settings.", e);
        } finally {
            //close statement here;
            if (rs != null) {
            	
                rs.close();
                conn.close();
            }
        }
        return data;
    }

    public BLResult execRequest(BLRequest request, BizData rd, BizData sd)
            throws RemoteException {
        int retCode = 0;

        int i = 0;
        int count;
        TX t = null;

        Models mds = request.getModels();

        if (mds != null) {
            count = mds.getLength();
            //contruct Hashtable object for check whether a model need to be run;
            boolean transact = request.needTX();
            if (transact) {
                t = newTX();
                if (t == null) {      //check if the UserTransaction is available;
                    return new BLResult(SysError.NO_USERTRANSACTION, rd, sd);
                }
            }

            //call all of matchding models;
            while (i < count) {
                Model m = mds.itemAt(i, rd, sd);
                if (m != null) {
                    try {
                        //check if the the model is asyn call;
                        if (m.isAsyn()) {
                            acProxy.asynCall(m.getClassName(), m.getMethodName(), rd, sd);
                        } else {
                            retCode = execRequest(m.getClassName(), m.getMethodName(), rd, sd);
                        }
                    } catch (Throwable e) {
                        retCode = -1;
                        rd.add(SysVar.THROWN, e);
                    }
                    if (retCode < 0) {	//bl method return negative value means error.
                        break;
                    }
                }
                i++;
            }

            //commit or rollback transaction here;
            if (transact) {
                try {
                    if (retCode < 0) {
                        t.rollback();
                    } else {
                        t.commit();
                    }
                } catch (Exception ne) {
                    log.error("can't commit/rollback transaction", ne);
                }
            }
        }

        return new BLResult(retCode, rd, sd);

    }

	public void addDistrict2Env() {
		
		DAO dao = this.getDAO("core");

		log.debug("add district to envionment variables, begin...........");
		Connection conn = dao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from xzqh");
		try {
			ResultSet rs = dao.executeQuery(sql.toString(), conn);
			while(rs.next()){
				
				int layer = rs.getInt("layer");
				DistrictBean bean = new DistrictBean();
				bean.setId(rs.getString("id"));
				bean.setpId(rs.getString("pid"));
				bean.setName(rs.getString("name"));
				bean.setGnw(rs.getString("gnw"));
				bean.setLayer(rs.getString("layer"));
				bean.setPym(rs.getString("pym"));
				bean.setMainCity(rs.getString("MAINCITY"));
				districtBeans.add(bean);
				switch (layer) {
				case 2:
					provinceBeans.add(bean);
					break;
				case 3:
					cityBeans.add(bean);
					break;
				}
			}
			rs.getStatement().close();
			rs.close();
			log.debug("add district to envionment variables, end...........");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				if(null != conn)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

    public List<DistrictBean> getDistrictBeans() {
		return districtBeans;
	}
    
	public List<DistrictBean> getProvinceBeans() {
		return provinceBeans;
	}

	public List<DistrictBean> getCityBeans() {
		return cityBeans;
	}

}















