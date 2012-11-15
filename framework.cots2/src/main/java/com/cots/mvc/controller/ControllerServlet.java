/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import com.cots.exp.Builder;
import com.cots.bean.BeanFactory;
import com.cots.blc.BLContext;
import com.cots.blc.BLCPool;
import com.cots.blc.BLCRegistry;
import com.cots.blc.Initializer;
import com.cots.util.FileUtil;
import com.cots.util.XMLFile;
import com.cots.mvc.controller.dynaction.DynaActionBuilder;
import com.cots.mvc.controller.access.LoginControl;
import com.cots.mvc.controller.access.LoginControlImpl;
import com.cots.mvc.controller.access.AccessControlImpl;
import com.cots.mvc.dispatch.ScreenMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.jsp.JspException;
import java.io.*;
import java.util.Properties;
import java.util.List;
import java.util.HashMap;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

/**
 * Description:
 *  This is the front controller of the cots MVC framework. HttpRequests accepted by
 * this servlet should be requests for an Action object. The action maybe a DefaultAction,
 * CustomAction.
 *
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 13:23:57
 * Version:1.0
 */
public final class ControllerServlet extends HttpServlet {

    //the requested action displayName, the displayName maybe set by AuthFilter when filtering;
    public static final String ACTION = "__cotsaction__";
    //the displayName of the attribute set in  ServletContext with the value of a BLContext object;
    public static final String CONTEXT = "com.cots.blcontext";
    //the displayName of the attribute set in ServletContext with the value of a BLCRegistry object.
    public static final String BLCREGISTRY = "com.cots.blcregistry";
    //the displayName of the attribute set in ServletContext with the value of a ActionMapping object.
    public static final String ACTION_MAPPING = "com.cots.actionmapping";

    ///dynactions;
    public static final String DYNA_ACTIONS = "com.cots.dynactions";
    //the displayName of the attribute set in ServletContext with the value of a BeanFactory object.
    public static final String BEAN_FACTORY = "com.cots.beanfactory";
    //the displayName of the attribute set in ServletContext with the value of a ScreenMapping;
    public static final String SCREEN_MAPPING = "com.cots.screenmapping";

    public static String ERROR_FORWARD="/error.jsp";
    public static String LOGIN_FORWARD="/login.jsp";

    private Logger accessLogger;

    //actions mapping;
    private ActionMapping actionMapping;

    //screens mapping;
    private ScreenMapping screenMap;

    //blcontext object;
    private BLContext blContext;

    //blcs pool;
    private BLCPool blcPool;

    //dynamically built actions map;
    private HashMap dynaActions = new HashMap();

    //dynamical actions builder object;
    private DynaActionBuilder dynaActionBuilder;

    /**
     * initialize this Servlet;
     *
     * @param config
     */
    public void init(ServletConfig config) throws ServletException {

        ServletContext sc = config.getServletContext();
        String webinfPath = sc.getRealPath("/WEB-INF");
        char endingChar = webinfPath.charAt(webinfPath.length() - 1);
        if (endingChar != '\\' && endingChar != '/') {
            webinfPath += '/';
        }

        /** config log4j,if this servlet shoudl config log4j, the file
         * /WEB-INF/config/log4j.properteis should be removed
         */
        String log4j = webinfPath + "config/log4j.properties";
        File log4jFile = new File(log4j);
        if (log4jFile.exists() && log4jFile.isFile()) {
            Properties p = null;
            try {
                p = FileUtil.readPropFile(log4jFile, "${", "}", System.getProperties());
            } catch (IOException e) {
                System.out.println("IOException when reading /WEB-INF/config/log4j.properties");
            }
            PropertyConfigurator.configure(p);
        }
        accessLogger = Logger.getLogger("com.cots.mvc.controller.access");


        //initialize cots managed beans;
        BeanFactory beanFactory = new BeanFactory();
        if (!beanFactory.init(webinfPath + "config/beans")) {
            System.out.println("WARNING: not all BEAN definition files were initialized successfully," +
                    " see log for detailed message");
        }
        sc.setAttribute(BEAN_FACTORY, beanFactory);

        //create and initialize the Screen Mapping;
        screenMap = new ScreenMapping(webinfPath + "config/screens/");
        sc.setAttribute(SCREEN_MAPPING, screenMap);

        //initialize BLContext object;
        try {
            blContext = new BLContext(webinfPath + "config/cots-blcontext.xml", beanFactory);

            //set the blcontext to the ServletContext, so it can be accessed from JSP;
            sc.setAttribute(CONTEXT, blContext);

            blcPool = blContext.getBLCPool();

            //check if the blc registry file exists, create this file if it does not exist.
            String blcRegistryFilePath = webinfPath + "config/blcs.xml";
            File blcRegistryFile = new File(blcRegistryFilePath);
            if (!blcRegistryFile.exists()) {
                XMLFile.newXMLFile(blcRegistryFilePath, "blcs", "GB2312");
            }
            BLCRegistry blr = new BLCRegistry(blcRegistryFilePath);
            sc.setAttribute(BLCREGISTRY, blr);

            blcPool.setBLCRegistry(blr);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        //create expression builder;
        Builder expressionBuilder = new Builder(blContext.getBaseDir());
        expressionBuilder.setClassPath(blContext.getClassPath());
        expressionBuilder.setLoader(blContext.getLoader());

        //initialize the action mapping;
        actionMapping = new ActionMapping();
        actionMapping.setExpressionBuilder(expressionBuilder);
        actionMapping.setBeanFactory(beanFactory);
        actionMapping.setBlcPool(blcPool);
        actionMapping.setScreenMap(screenMap);
        actionMapping.setContext(blContext);
        if (!actionMapping.init(webinfPath + "config/actions")) {
            System.out.println("WARNING: not all ACTION definition files were initialized successfully," +
                    " see log for detailed message");
        }
        sc.setAttribute(ACTION_MAPPING, actionMapping);

        //create the DynaAction Builder;
        dynaActionBuilder = new DynaActionBuilder();
        dynaActionBuilder.setBlcPool(blcPool);
        dynaActionBuilder.setBaseDir(blContext.getBaseDir());
        dynaActionBuilder.setClassPath(blContext.getClassPath());
        dynaActionBuilder.setContext(blContext);

        sc.setAttribute(DYNA_ACTIONS, dynaActions);
        //set the login control impl class displayName;
        try {
            LoginControlImpl.setImplClass(blContext.getLoginControlImpl());
            //initialize the login controll object;
            LoginControlImpl.getInstance().init(blContext);
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        } catch (InstantiationException e) {
            throw new ServletException(e);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        //set the access control impl class;
        try {
            AccessControlImpl.setImplClass(blContext.getAccessControlImpl());
            //initialize the access controll object;
            AccessControlImpl.getInstance().init(blContext);
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        } catch (InstantiationException e) {
            throw new ServletException(e);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        //run custom initializer object; these objects are defined in the blcontext.xml with the xpath
        //of /blcontext/system/initializers
        String[] classNames = blContext.getInitializerClasses();
        doCustomInit(sc, classNames);
    }

    /**
     * do custom initialization.
     *
     * @param sc                 the ServletContext object to initialize within.
     * @param initializerClasses the class names of all the Initializer object.
     */
    private void doCustomInit(ServletContext sc, String[] initializerClasses) {
        for (int i = 0; i < initializerClasses.length; i++) {
            String className = initializerClasses[i];
            try {
                Class clz = Class.forName(className);
                Object obj = clz.newInstance();
                if (obj instanceof Initializer) {
                    Initializer ier = (Initializer) obj;
                    ier.init(sc);
                    sc.setAttribute("com.cots.blc.initializer." + ier.getName(), ier);
                } else {
                    System.out.println("ERROR: user defined initializer class \"" + className + " \" must " +
                            "implements inteface com.cots.blc.Initializer");
                }
            } catch (ClassNotFoundException e) {
                System.out.println("ERROR: Can't find user defined initializer class " + className);
            } catch (IllegalAccessException e) {
                //there is no default constructor(constructor with no arguments or it is not public
                System.out.println("ERROR: Can't access the default constructor of user defined " +
                        "initializer class " + className);
            } catch (InstantiationException e) {
                System.out.println("ERROR: Failed to create instance of user defined " +
                        "initializer class " + className);
            }
        }
    }

    /**
     * do a Http GET;
     *
     * @param request
     * @param response
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    /**
     * do a Http POST;
     *
     * @param request
     * @param response
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    /**
     * called by the web container when this servlet will be destroyed;
     */
    public void destroy(){
        //close the login Control object;
        LoginControlImpl.getInstance().close();
        //close the access contgrol object;
        AccessControlImpl.getInstance().close();
        //shutdown the blc context object;
        blContext.shutdown();
    }

    /**
     * @param request
     * @param response
     */
    public void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long start = 0;
        boolean ok = true;
        HTTPRequest onlineReq = null;

        boolean logAccess = blContext.isLogAccess();
        if (logAccess) {
            start = System.currentTimeMillis();
        }

        //check if the action Name has be setup by the filter;
        String action = (String) request.getAttribute(ACTION);
        if (action == null) {   //otherwise create the action displayName;
            String uri = request.getRequestURI();
            int lastSlash = uri.lastIndexOf('/');
            uri = uri.substring(lastSlash + 1);
            int lastPeriod = uri.lastIndexOf('.');
            if (lastPeriod < 0) {
                lastPeriod = uri.length();
            }
            action = uri.substring(0, lastPeriod);
        }

        /**
         * convert reflective method call to direct method call,
         * a DynAction object is an action that call BLC's methods directly,
         * the performance should be improved a bit;
         */
        Action act = (Action)dynaActions.get(action);
        if(act==null){
            Action act2 = actionMapping.getAction(action);
            if(act2 instanceof DefaultAction){
                synchronized(dynaActionBuilder){
                    //check again if the target action has been built by the previous Thread;
                    if((act = (Action)dynaActions.get(action))==null){
                        act = dynaActionBuilder.build((DefaultAction)act2);
                        if(act!=null){
                            dynaActions.put(action,act);
                        }else{
                            act = act2;
                            dynaActions.put(action,act);
                        }
                    }
                }
            }else{
                act = act2;
                synchronized(dynaActions){
                    dynaActions.put(action,act);
                }
            }
        }

        //check whether to log online request;
        boolean logOnlineRequests = blContext.isLogOnlineRequests();
        if (logOnlineRequests) {
            onlineReq = new HTTPRequest((String) request.getSession().getAttribute(LoginControl.USERID)
                    , request.getRemoteHost(), action);
            OnlineHTTPRequests.add(onlineReq);
        }

        try {

            if (act == null) {  //no this action;
                response.sendError(404, "can't find action \"" + action + "\"");
            } else {    //action exists;
                if (act.isOK()) { //the action may not be ok for process requests.
                    act.run(request, response);
                } else {
                    //this action is initialized with one or more errors,
                    //sendBody these errors to client;
                    ok = false;
                    List errorMsgs = act.getErrorMsgs();
                    request.setAttribute(DefaultAction.ERRORS, errorMsgs);
                    RequestDispatcher rd = request.getRequestDispatcher("/error.jsp?actionInit=true");
                    rd.forward(request, response);
                }
            }
        } catch (IOException e) {
            ok = false;
            throw e;
        } catch (JspException e) {
            ok = false;
            throw new ServletException(e);
        } catch (ServletException e) {
            ok = false;
            throw e;
        } catch (Throwable e) {
            ok = false;
            throw new ServletException(e);
        } finally {
            if (logAccess) {
                long end = System.currentTimeMillis();
                StringBuffer sb = new StringBuffer(request.getRemoteHost());
                sb.append("\t").append(action).append("\t");
                sb.append(ok ? "OK" : "failed");
                sb.append("\t").append(end).append("-").append(start).append("=");
                sb.append(end - start);
                accessLogger.info(sb);
            }

            //when a request is processed, check if need to remove it from the cache.
            if (logOnlineRequests) {
                OnlineHTTPRequests.remove(onlineReq);
            }
        }
    }
}