/*
 * All rights reserved,@copyright2001
 */

package com.dream.bizsdk.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;

import com.dream.app.district.DistrictItfc;
import com.dream.app.security.SecurityMngmt;
import com.dream.app.security.SecurityMngmtInterface;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.blc.BLContext;
import com.dream.bizsdk.common.blc.BLContextCollection;
import com.dream.bizsdk.common.blc.BLRequest;
import com.dream.bizsdk.common.blc.BLRequests;
import com.dream.bizsdk.common.blc.BLResult;
import com.dream.bizsdk.common.blc.BLWSPublisher;
import com.dream.bizsdk.common.blc.DataFilter;
import com.dream.bizsdk.common.blc.Dispatch;
import com.dream.bizsdk.common.blc.FieldValidations;
import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.blc.ValidateException;
import com.dream.bizsdk.common.blc.Validations;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.database.datadict.DataDict;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.timer.TaskManager;
import com.dream.bizsdk.common.util.ParamNameException;
import com.dream.bizsdk.common.util.RequestParams;
import com.dream.bizsdk.common.util.crypto.MsgCipher;
import com.dream.bizsdk.common.util.string.StringConvertor;
import com.dream.bizsdk.common.util.string.StringUtil;
import com.dream.bizsdk.common.util.xml.XMLFile;
import com.dream.bizsdk.ejb.IBLContextHome;
import com.dream.bizsdk.ejb.IBLContextRemote;
import com.dream.bizsdk.socket.BLServer;
import com.dream.bizsdk.socket.BLServerConfig;

/**
 * BLServlet实现MVC中的C部分，这个Servlet接受客户端请求，根据FPath组装
 * 客户端请求数据为一个BizData对象，然后调用指定的BL方法，最后再调用指
 * 定的View逻辑。如果BL方法执行错误(有异常抛出），将返回到一个统一的错误页面, BL
 * 方法也可返回一个小于0的整数来显示错误信息(该错误信息可以在一个数据 库表中统一定义和存储,包括错误编号，错误信息，错误级别)。
 * 
 * @author divine
 * @version 1.0
 */

public class BLServlet extends HttpServlet {
	/**
	 * whether this servlet is working on debug mode,in debug mode, HttpSession
	 * is not a must,but to the contrary in Production mode;
	 */
	private boolean isDebug;

	/**
	 * whether this server is using a local BLContext object,or remote ejb
	 * reference;
	 */
	private boolean isLocal = true;

	/**
	 * security admin object;
	 */
	private ISecurityManager smAdmin;

	/**
	 * BLContext object;
	 */
	private IBLContext context;
	
	/**
	 * district object;
	 */
	private DistrictItfc district;

	/**
	 * encoding;
	 */
	private String cp = "GBK";

	/**
	 * Requests map;
	 */
	private BLRequests requests;

	/**
	 * Tool used to parse parameters from HttpRequest into a BizData object;
	 */
	private RequestParams rp = new RequestParams();

	/**
	 * logind users Map;
	 */
	private HashMap loginedUsers = new HashMap();

	/**
	 * access to the published BL method;
	 */
	private BLWSPublisher blWSPublisher = new BLWSPublisher();

	/**
	 * Timed Task Manager;
	 */
	private TaskManager tm;

	/**
	 * Socket server;
	 */
	private BLServer blserver;

	/**
	 * Log4j logger;
	 */
	private Logger log;

	/**
	 * default constructor;
	 */
	public BLServlet() {
	}

	/**
	 * 初始化当前应用。
	 * 
	 * @throws ServletException
	 */
	public void init() throws ServletException {
		try {
			System.out.println("starting COTS pwoered application");

			// first initialize Log4j for log operation of later use;
			initLog4j();
			log = Logger.getLogger(BLServlet.class);

			// initialize this application context,mainly the BLContext object;
			initContext();
			

			/* create the Socket server if necessary */
			String sock = context.getConfigValue("system", "socketServer");
			if (sock != null && sock.equalsIgnoreCase("true")) {
				blserver = new BLServer(this);
				blserver.init(new BLServerConfig());
				blserver.start();
			}

			System.out.println("COTS powers the application successfully!");
		} catch (Throwable t) {
			System.out.println("Sorry, exceptions during startup"
					+ ", see the following message for detailed information:");
			t.printStackTrace();
			if (log != null) {
				log.error("initialization error", t);
			}
			throw new ServletException(t);
		}
	}

	/**
	 * process http GET request;
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		process(request, response);
	}

	/**
	 * process http POST request;
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		process(request, response);
	}

	/**
	 * called by the container when the servlet is destroyed;
	 */
	public void destroy() {
		// release the IBNContext Object if necessary
		if (context != null) {
			try {
				context.release();
				if (log.isInfoEnabled()) {
					log.info("BLContext closed.");
				}
			} catch (RemoteException re) {
				log.error("exception when closing BLContext.", re);
			}
		}
	}

	/**
	 * process HTTP requests;
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String uri = null;
		BizData sessionData = null;
		HttpSession session = null;

		try {

			/* get the request URI and the request name and the BLRequest object */;
			uri = request.getRequestURI();
			String requestName = StringUtil.getRequestNameFromURI(uri);
			BLRequest breq = requests.getRequest(requestName);

			if (log.isDebugEnabled()) {
				log.debug("accepted HTTP request: " + requestName);
			}

			// this new BizData object will hold all the parameters from this
			// HtpServetRequest.
			// and if isLocal is true,then current HttpServletRequest is added
			// to this data object too.
			BizData data = (BizData) request.getAttribute(SysVar.REQ_DATA);
			if (data == null)
				data = new BizData();
			// System.out.println(request.toString());
			// validate the current session;if not valid then redirect to login
			// page;
			session = request.getSession(true);
			if (!isDebug && session == null) { // can't create a session object
												// for this request;
				dispatch(request, response, null, data, null,
						SysError.NO_SESSION);
				return;
			}

			// get the current user's session data from the HttpSession object;
			sessionData = (BizData) session.getAttribute(SysVar.SS_DATA);

			/*
			 * //support to sigle sign on; if (sessionData == null) { //get
			 * userID from the cookie; Cookie[] c = request.getCookies(); int
			 * count = c.length; for (int i = 0; i < count; i++) { if
			 * ("userID".compareToIgnoreCase(c[i].getName()) == 0) { sessionData
			 * = (BizData) loginedUsers.get(c[i].getValue()); break; } } }
			 */
			// check user's session data object;
			if (!isDebug && sessionData == null) { // the current session has
													// not logined into the
													// system;
				String loginPage = SysVar.LOGINPAGE.replaceAll(
						SysVar.CONTEXT_PATH, request.getContextPath());
				response.sendRedirect(loginPage);
				return;
			} else {
				if (sessionData == null) {
					sessionData = new BizData();
				}
			}

			// process request data and add them to a BizData object;
			if (!processRequestParams(request, data)) {
				data.add(SysVar.ERROR_NO, new Integer(SysError.BL_PARAM_ERROR));
				dispatch(request, response, null, data, sessionData,
						SysError.PARAM_PARSE_ERROR);
				return;
			}

			// further process;
			process2(request, response, session, breq, data, sessionData);

			if (log.isDebugEnabled()) {
				log.debug("finished HTTP request: "
						+ requestName
						+ " from user: "
						+ (sessionData != null ? sessionData
								.getString("userID") : "n/a"));
			}
		} catch (Throwable e) {
			log.error("Unexpected exception when processing HTTP request", e);
		}
	}

	/**
	 * process a specified request;
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param breq
	 * @param data
	 * @param sessionData
	 * @throws IOException
	 * @throws ServletException
	 */
	public void process2(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, BLRequest breq,
			BizData data, BizData sessionData) throws IOException,
			ServletException {

		String msg = null;
		BLResult br = null;

		// filter the data;
		data = filterData(breq, data);

		// validate request params;
		if ((msg = validateRequestParams(breq, data, sessionData)) != null) { // parameter
																				// is
																				// not
																				// valid;
			// validate the request parameter;
			data.add(SysVar.ERROR_MESSAGE, msg);
			dispatch(request, response, null, data, sessionData,
					SysError.PARAM_VALIDATE_ERROR);
		} else { // parameter is valid;
			// check whether the current user has access to the target request;
			// if (!isDebug && smAdmin.checkAccess(session, breq.getName()) < 0)
			// { //no access
			/*
			 * data.add(SysVar.ERROR_NO, new Integer(SysError.NO_ACCESS));
			 * request.setAttribute(SysVar.REQ_DATA, data); RequestDispatcher
			 * disp = request.getRequestDispatcher(SysVar.ERRORPAGE);
			 * disp.forward(request, response);
			 */
			// } else { //has access
			/*
			 * if the current IBLContext is local reference, then add the HTTP
			 * Request object to the BizData object
			 */
			if (isLocal) {
				data.add(SysVar.HTTP_REQ, request);
			}

			// call the bl to response the request;
			br = callBL(request, breq, data, sessionData);
			// get the processed data object(request data and session data);
			if (!isLocal) { // if the context is a local reference, then data
							// and sessionData is up to date
				data = br.rd;
				sessionData = br.sd;
			}

			// dispatch the current request;
			dispatch(request, response, breq, data, sessionData, br.retCode);
		}
	}

	// }

	/**
	 * filter data for a named request; If there is no Data Filters for the
	 * reuest, then the source BizData object is returned unchanged, otherwise a
	 * new BizData object with only the filtered data is returned;
	 * 
	 * @param request
	 *            the BLRequest object;
	 * @param src
	 *            the source BizData object;
	 * @return the data after filtered;
	 */
	private final BizData filterData(BLRequest request, BizData src) {
		int count;
		List l = request.getFilters();
		if (l != null) {
			count = l.size();
			BizData target = new BizData();
			for (int i = 0; i < count; i++) {
				DataFilter df = (DataFilter) l.get(i);
				df.filter(src, target);
			}
			return target;
		} else {
			return src;
		}
	}

	/**
	 * 处理通过web service发出的请求，
	 * 
	 * @param serviceName
	 *            the name of the request name;
	 * @param data
	 *            the request data object;
	 * @param sessionData
	 *            the session data object;
	 * @return the result of the request;
	 */
	public final BLResult processDirectRequest(String serviceName,
			BizData data, BizData sessionData) {
		String msg = null;
		BLResult br = null;

		BLRequest breq = requests.getRequest(serviceName);

		// filter the data;
		data = filterData(breq, data);

		// chech if the request data is valid according to the request settings;
		if ((msg = validateRequestParams(breq, data, sessionData)) != null) { // parameter
																				// is
																				// not
																				// valid;
			// validate the request parameter;
			data.add(SysVar.ERROR_MESSAGE, msg);
			br = new BLResult(SysError.PARAM_VALIDATE_ERROR, data, sessionData);
		} else { // parameter is valid;
			// check if the current user has access to the target request;
			if (!isDebug && smAdmin.checkAccess(sessionData, serviceName) < 0) { // no
																					// access
				data.add(SysVar.ERROR_NO, new Integer(SysError.NO_ACCESS));
				br = new BLResult(SysError.NO_ACCESS, data, sessionData);
			} else { // has access
				// call the bl to response the request;
				br = callBL(null, breq, data, sessionData);
			}
		}
		return br;
	}

	/**
	 * @param className
	 * @param methodName
	 * @param rd
	 * @param sd
	 * @return
	 * @throws RemoteException
	 */
	public final BLResult processBLWebService(String className,
			String methodName, BizData rd, BizData sd) throws RemoteException,
			Throwable {
		BLResult br;

		// check if this bl has been published;
		if (blWSPublisher.isBLPublished(className, methodName)) {
			br = context.execBL(className, methodName, rd, sd);
		} else {
			br = new BLResult(SysError.BL_NOT_EXIST_OR_PUBLISHED, rd, sd);
		}
		return br;
	}

	/**
	 * 解析请求数据的名称(FPath),并根据解析后的FPath组装请求的数据为 一个BizData对象。
	 * 如果一个参数包含多个值并且该参数名称以"[]"结尾，则该参数被解析一个 一维数组；否则只有第一个值被添加到数据对象中。
	 * 
	 * @param request
	 *            the HttpServletRequest object.
	 * @param data
	 *            the request data object,should not be null;
	 * @return true if all the data is parsed successfully, false other wise;
	 */
	private final boolean processRequestParams(HttpServletRequest request,
			BizData data) {
		try {
			data.add(SysVar.CURRENT_CONTEXT_PATH, request.getContextPath());
			return rp.parse(request, cp, data);
		} catch (UnsupportedEncodingException e) {
			log.error("the charset is not supported:" + cp, e);
			return false;
		} catch (ParamNameException pne) {
			data.add(SysVar.ERROR_MESSAGE, pne.getMessage());
			return false;
		}
	}

	/**
	 * validate request data according to the request configuration;
	 * 
	 * @param req
	 *            the BLRequest object;
	 * @param rd
	 *            the current request data object;
	 * @param sd
	 *            the current session data object;
	 * @return null of the validattion passed, otherwise a String object
	 *         contains the error message;
	 */
	private final String validateRequestParams(BLRequest req, BizData rd,
			BizData sd) {
		String msg = null;
		/* prepare data for validation */
		Hashtable dds = new Hashtable();
		dds.put("rd", rd);
		dds.put("default", rd);
		if (sd != null) {
			dds.put("sd", sd);
		}
		try {
			/* conditional validations */
			Validations vs = req.getValidateions();
			if (vs != null) {
				vs.validate(dds);
			}

			/* field validations */
			FieldValidations fieldVs = req.getFieldValidateions();
			if (fieldVs != null) {
				fieldVs.validate(rd);
			}

			// up to here, the validation passed.
		} catch (ValidateException ve) { // validation failed
			msg = ve.getMessage();
			if (msg == null) {
				msg = "Sorry, failed to validate your data , please check your input.";
			}
		}
		return msg;
	}

	/**
	 * 执行BL方法来响应客户端的请求。
	 * 
	 * @param request
	 * @param breq
	 * @param data
	 * @param sessionData
	 * @return
	 */
	private final BLResult callBL(HttpServletRequest request, BLRequest breq,
			BizData data, BizData sessionData) {

		try {
			BLResult br = context.execRequest(breq, data, sessionData);

			if (br.sd.isModified()) {
				request.getSession().setAttribute(SysVar.SS_DATA, br.sd);
			}

			return br;
		} catch (Throwable t) {
			data.add(SysVar.ERROR_NO, SysError.UNKNOWN_ERROR);
			data.add(SysVar.THROWN, t);
			return new BLResult(SysError.UNKNOWN_ERROR, data, sessionData);
		}
	}

	/**
	 * 将当前请求forward或者redirec或者include另外一个资源。 转入到下一个资源可以有两种方式，一是forward;
	 * 另一个是redirect,并且优先执行redirect所指定的资源。下一个执行
	 * 的资源可以是一个jsp页面，html页面，或者BL方法请求，或者其他类型 的资源。请求资源的URL可以是相对的，也可以是绝对的，如果是相对的
	 * 的需要考虑ServletContext的露径名。
	 * 
	 * @param request
	 * @param response
	 * @param data
	 * @param retVal
	 * @throws IOException
	 * @throws ServletException
	 */
	public final void dispatch(HttpServletRequest request,
			HttpServletResponse response, BLRequest breq, BizData data,
			BizData sessionData, int retVal) throws IOException,
			ServletException {

		int dispType;

		String contextPath = request.getContextPath();

		// check the return code of the request, if >0 then this request is
		// processed successfully. otherwise
		// failed;
		if (retVal >= 0) { // succcess;
			
			data.add(SysVar.RETURN_VALUE, new Integer(retVal));
			Dispatch[] disp = breq.getDispatch(data, sessionData);
			if (disp.length == 0) {
				response.sendRedirect(SysVar.NOPAGE.replaceAll(
						SysVar.CONTEXT_PATH, contextPath));
			} else {
				// check the type of the dispatch;
				for (int i = 0; i < disp.length; i++) {
					if ((dispType = disp[i].getType()) == Dispatch.REDIRECT) { // redirect
																				// dispatch;
						String url = disp[i].getURL();
						url = url.replaceAll(SysVar.CONTEXT_PATH, contextPath);
						url = StringConvertor.replaceAndEncode(url, data);
						if (log.isDebugEnabled()) {
							log.debug("redirect the request to " + url);
						}
						// response.setCharacterEncoding("GBK");
						response.sendRedirect(url);
					} else {
						RequestDispatcher dispForw;
						String nextpage = disp[i].getURL();
						nextpage = nextpage.replaceAll(SysVar.CONTEXT_PATH,
								contextPath);
						nextpage = StringConvertor.replaceAndEncode(nextpage,
								data);
						if (nextpage.startsWith("/")) { // if startsWith '/'
														// then wen consider it
														// a absolute uri;
							if (log.isDebugEnabled()) {
								log.debug("forward/include the request to "
										+ nextpage);
							}
							dispForw = request.getRequestDispatcher(nextpage);
						} else { // a relative uri to the /jsp/ uri;
							if (log.isDebugEnabled()) {
								log
										.debug("forward/include the request to /jsp/ "
												+ nextpage);
							}
							dispForw = request.getRequestDispatcher("/jsp/"
									+ nextpage);
						}
						request.setAttribute(SysVar.REQ_DATA, data);

						if (dispType == Dispatch.FORWARD) { // forward dispatch;
							dispForw.forward(request, response);
						} else if (dispType == Dispatch.INCLUDE) { // include
																	// dispatch;
							if (i == 0) { // if the first dispatch is include,
											// then set the content type to be
											// "text.html";
								response
										.setContentType("text/html;charset=GB2312");
							}
							dispForw.include(request, response);
						}
					}
				}
			}
		} else { // error;
			if (log.isDebugEnabled()) {
				log.debug("forward to error page due to error code: " + retVal
						+ " with additional message:"
						+ data.get(SysVar.ERROR_MESSAGE));
			}
			data.add(SysVar.ERROR_NO, new Integer(retVal));
			request.setAttribute(SysVar.REQ_DATA, data);
			RequestDispatcher disp = request
					.getRequestDispatcher(SysVar.ERRORPAGE);
			if (disp == null) {
				log.error("trying to forward to " + SysVar.ERRORPAGE
						+ ", but it is not found.");
			} else {
				disp.forward(request, response);
			}
		}
	}

	/**
	 * This function should be called when no http requests is being processed.
	 * 
	 * @param reqs
	 */
	public void setBLRequest(BLRequests reqs) {
		this.requests = reqs;
	}

	/**
	 * Set attributes to the ServletContext, and also initialize the BLContext
	 * object;
	 * 
	 * @throws Exception
	 */
	public synchronized void initContext() throws Exception {
		String temp;
		ServletContext sc = this.getServletContext();
		String path = getServletContext().getRealPath("/WEB-INF");
		path = StringUtil.checkPath(path);

		String usingLocalConfig = this.getInitParameter("usingLocalConfig");
		String localConfigFile = this.getInitParameter("localConfigFile");
		String remoteConfigFile = this.getInitParameter("remoteConfigFile");
		if (context != null) { // relase this context if it is not null;
			context.release();
		}
		try {
			if (usingLocalConfig != null
					&& usingLocalConfig.compareToIgnoreCase("true") == 0) {
				context = getBLContextFromLocal(path + "config"
						+ File.separatorChar + localConfigFile);
			} else {
				isLocal = false;
				context = getBLContextRemote(path + "config"
						+ File.separatorChar + remoteConfigFile);
			}
		} catch (Throwable t) {
			log.fatal(
					"Exception caught when trying to create a BLContext object"
							+ ", COTS framework couldn't be started.", t);
			// throw this exception, and return ;
			throw new Exception("fatal exception", t);
		}

		// register this blcontext to blcontext collection so other blcontext
		// can access
		// this context, and also enable this context to access other blcontext;
		BLContextCollection.register(context);

		// initialize timed task manager;
		tm = new TaskManager(sc);
		tm.setContext(context);
		tm
				.init(path + "config" + File.separatorChar
						+ "timertask-register.xml");

		requests = new BLRequests(path + "requests");

		context.addDistrict2Env();//add district to envionment variables
		DataDict dict = context.getDataDict();
		BizData bdc = context.getBizDataDict();
		BizData errors = context.getErrors();
		HashMap dicts = context.getAllDataDict();
		BizData rowPrivil = context.getRowPrivil();
		BizData colPrivil = context.getColPrivil();
		
		// get system config values from the config values cache;
		String debug = context.getConfigValue("system", "debug");
		if (debug.compareToIgnoreCase("1") == 0) {
			isDebug = true;
		}

		// get the character set;
		cp = context.getConfigValue("system", "charset");
		if (cp == null) {
			cp = new String("GBK");
		}
		// set the StringConvertor's charset used to encode url;
		StringConvertor.CHARSET = cp;

		/** set the time and date format */
		String dateFormat = context.getConfigValue("system", "dateformat");
		String timeFormat = context.getConfigValue("system", "timeformat");
		try {
			BizData.setTimeFormat(timeFormat);
		} catch (IllegalArgumentException iae) {
			if (log.isEnabledFor(Priority.WARN)) {
				log
						.warn(
								"the time format is not valid,default time format is used",
								iae);
			}
		} catch (NullPointerException npe) {
			if (log.isEnabledFor(Priority.WARN)) {
				log
						.warn(
								"the time format is not set,default time format is used",
								npe);
			}
		}

		try {
			BizData.setDateFormat(dateFormat);
		} catch (IllegalArgumentException iae) {
			if (log.isEnabledFor(Priority.WARN)) {
				log
						.warn(
								"the time format is not valid,default time format is used",
								iae);
			}
		} catch (NullPointerException npe) {
			if (log.isEnabledFor(Priority.WARN)) {
				log
						.warn(
								"the date format is not set,default date format is used",
								npe);
			}
		}

		/** set the cipher settings to the BizData class */
		String cipherType = context.getConfigValue("cipher", "algorithm");
		String cipherKey = context.getConfigValue("cipher", "key");
		BizData._cipherKey = MsgCipher.getKeyFromString(cipherKey);
		BizData._cipherType = cipherType;

		// system dispatches;
		if ((temp = context.getConfigValue("systemDispatches", "loginPage")) != null) {
			SysVar.LOGINPAGE = temp;
		}
		if ((temp = context.getConfigValue("systemDispatches", "noPage")) != null) {
			SysVar.NOPAGE = temp;
		}
		if ((temp = context.getConfigValue("systemDispatches", "errorPage")) != null) {
			SysVar.ERRORPAGE = temp;
		}

		/** digest type settings */
		String digestType = context.getConfigValue("digest", "algorithm");
		BizData._digestType = digestType;

		// bl context object;
		sc.setAttribute(SysVar.APP_BLCONTEXT, context);
		// business dictionary;
		sc.setAttribute(SysVar.APP_BDC, bdc);
		// error messages
		sc.setAttribute(SysVar.APP_ERRORS, errors);
		// function tables;

		sc.setAttribute("com.dream.bizsdk.RowPrivil", rowPrivil);
		sc.setAttribute("com.dream.bizsdk.ColPrivil", colPrivil);

		// system data dictionary;
		sc.setAttribute(SysVar.APP_SDC, dict);
		// all the system data dictionary;
		sc.setAttribute(SysVar.APP_SDCS, dicts);
		
		sc.setAttribute(SysVar.APP_BLSERVLET, this);

		temp = context.getConfigValue("system", "hiddenFieldMask");
		if (temp != null) {
			DAO.HIDDEN_FIELD_MASK = temp;
		}

		// initialize Security Manager;
		initSecurityManager();
		
		//copyright controller
		SecurityMngmtInterface sm = new SecurityMngmt();
		//sm.compareDiskSerialNm();
		//sm.compareMac();
		
	}

	/**
	 * Initialize the Security Manager object.
	 * 
	 * @throws Exception
	 */
	protected void initSecurityManager() throws Exception {
		String smClass = this.getInitParameter("SecurityManagerClass");
		if (smClass != null) {
			smAdmin = (ISecurityManager) Class.forName(smClass).newInstance();
			smAdmin.init(this.getServletContext());
		} else { // a default security Manager;
			smAdmin = new SecurityManager(context);
		}
		// security adming object;
		this.getServletContext().setAttribute(SysVar.APP_SM, smAdmin);
	}

	/**
	 * get BLContext object according to local configuration;
	 * 
	 * @return
	 */
	protected IBLContext getBLContextFromLocal(String filePath)
			throws Exception, ServletException, FileNotFoundException {
		File f = new File(filePath);
		return new BLContext(f);
	}

	/**
	 * get BLContext object from a EJBContainer; This method is called if the BL
	 * classed are deployed in a EJBContainer, then a remote IBLContext will be
	 * lookek up in this EJBContainer.
	 * <p/>
	 * the config file of the location of the remote BLContext should be located
	 * in /WEB-INF/config , its name can be configed by this servlet's init
	 * parameter "remoteConfigFile".
	 * 
	 * @return
	 */
	protected IBLContext getBLContextRemote(String filePath)
			throws NamingException, RemoteException, Exception {
		IBLContextRemote context;
		File f = new File(filePath);
		XMLFile xml = new XMLFile(f);
		String name = xml.getSingleNodeValue("/blcontext-remote/name");
		String jndiName = xml
				.getSingleNodeValue("/blcontext-remote/ejbJNDIName");
		Hashtable pps = new Hashtable();
		pps
				.put(
						"java.naming.factory.initial",
						xml
								.getSingleNodeValue("/blcontext-remote/java.naming.factory.initial"));
		pps
				.put(
						"java.naming.provider.url",
						xml
								.getSingleNodeValue("/blcontext-remote/java.naming.provider.url"));
		InitialContext ic = new InitialContext(pps);
		Object ref = ic.lookup(jndiName);
		IBLContextHome home = (IBLContextHome) PortableRemoteObject.narrow(ref,
				IBLContextHome.class);
		context = home.create(name);
		System.out.println(context.getName());
		return context;
	}

	/**
	 * initialize log4j; If the initialize parameter "initlog4j" of this servlet
	 * is configed to be "true", then this method will be called, otherwise it
	 * will never be called. The log4j configuration file is named
	 * log4j.properties located in /WEB-INF/config directory;
	 */
	private void initLog4j() {
		// check if log4j has been initialized.
		String initLog4j = this.getInitParameter("initLog4j");
		if (initLog4j != null
				&& (initLog4j.compareToIgnoreCase("true") == 0 || initLog4j
						.compareToIgnoreCase("1") == 0)) {
			String webInfPath = this.getServletContext().getRealPath(
					"/WEB-INF/config");

			if (webInfPath.charAt(webInfPath.length() - 1) != File.separatorChar) {
				webInfPath += File.separatorChar;
			}
			String log4jConfigFilePath = webInfPath + "log4j.properties";
			PropertyConfigurator.configure(log4jConfigFilePath);
		}
	}

	/**
	 * get a BLRequest object by the name;
	 * 
	 * @param request
	 *            the name of the BLRequest object by the name;
	 * @return the target BLRequest object;
	 */
	public BLRequest getRequest(String request) {
		return requests.getRequest(request);
	}

	/**
	 * add a logined user to this servlet; todo to be finished;
	 * 
	 * @param userID
	 * @param sessionData
	 */
	public void addLoginedUser(String userID, BizData sessionData) {
		loginedUsers.put(userID, sessionData);
	}

	/**
	 * remove a logined user from this servlet; todo to be finsihed;
	 * 
	 * @param userID
	 * @param sessionData
	 */
	public void removedLoginedUser(String userID, BizData sessionData) {
		loginedUsers.remove(userID);
	}
	
}