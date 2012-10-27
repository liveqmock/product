/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.web;

import com.dream.bizsdk.common.report.*;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.util.string.StringConvertor;
import com.dream.bizsdk.common.util.RequestParams;
import com.dream.bizsdk.common.util.ParamNameException;
import com.dream.bizsdk.common.blc.IBLContext;
import com.lowagie.text.DocumentException;

import java.io.File;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import org.xml.sax.SAXException;
import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-27
 * Time: 15:57:47
 */
public class ReportServlet extends HttpServlet {
    public static String DEFAULT_REPORT_PATH = "/WEB-INF/built_reports";

    public static String REPORT_PATH;

    public static String TEMPLATE_PATH;

    //the directory to save the report template files;
    private String templatePath;

    //the path of the directory to save built report files;
    private String reportPath;

    //IBLContext object;
    private IBLContext context;

    private RequestParams rp = new RequestParams();

    //log4j object;
    private Logger log = null;

    private String cp = "GBK";

    /**
     * @throws ServletException
     */
    public void init() throws ServletException {
        String temp = null;
        templatePath = this.getServletContext().getRealPath("/WEB-INF");
        if (templatePath.charAt(templatePath.length() - 1) != File.separatorChar) {
            templatePath += File.separatorChar;
        }
        templatePath += "reportTemplate" + File.separatorChar;

        ReportServlet.TEMPLATE_PATH = templatePath;

        reportPath = this.getServletConfig().getInitParameter("reportPath");
        if (reportPath == null) {
            reportPath = this.getServletContext().getRealPath(ReportServlet.DEFAULT_REPORT_PATH);
        }
        if (reportPath.charAt(reportPath.length() - 1) != File.separatorChar) {
            reportPath += File.separatorChar;
        }
        ReportServlet.REPORT_PATH = reportPath;

        context = (IBLContext) this.getServletContext().getAttribute(SysVar.APP_BLCONTEXT);
        log = Logger.getLogger("com.dream.report");
        try {
            if ((temp = context.getConfigValue("system", "charset")) != null) {
                cp = temp;
            }
        } catch (RemoteException e) {
            log.error("initialization error", e);
        }
    }

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            process(request, response);
        } catch (Throwable t) {
            log.error("exception in report procession", t);
        }
    }

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            process(request, response);
        } catch (Throwable t) {
            log.error("exception in report procession", t);
        }
    }


    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Template t = null;
        BizData rd = new BizData();

        //get the input data to build the report, note that it is NOT the data in the report itself.
        BizData d = (BizData) request.getAttribute(SysVar.REQ_DATA);

        if (d == null) {
            d = new BizData();
            try {
                rp.parse(request, cp, d);
            } catch (ParamNameException pne) { //the name of the parameter is not a valid FPath;
                log.error("one ore more parameter names is/are not valid", pne);
                dispatchError(d, SysError.PARAM_PARSE_ERROR, request, response);
            }
        }

        //open the template;
        String templateName = d.getString("templateName");
        String useInputData = d.getString("useInputData");
        String rebuild = d.getString("rebuild");
        String filePath = templatePath + templateName + ".htm";
        try {
            t = new Template(filePath);
        } catch (ParserConfigurationException e) {
            log.error("can't open the report template" + templateName, e);
            dispatchError(d, SysError.CANT_OPEN_REPORT_TEMPLATE, request, response);
            return;
        } catch (SAXException e) {
            log.error("can't open the report template" + templateName, e);
            dispatchError(d, SysError.CANT_OPEN_REPORT_TEMPLATE, request, response);
            return;
        }

        String rName = t.getReportName(d);
        String reportFile = reportPath + rName + ".pdf";
        File f = new File(reportFile);

        //check if the report was built already, if no,build it.
        if (!f.exists() || rebuild.compareToIgnoreCase("true") == 0) {
            try {
                int count = 0;

                Report r = new Report(reportFile, t);

                if (useInputData.compareToIgnoreCase("true") != 0) {    //generate the sql(s) in the template to
                    ReportSql[] sqls = t.getSql();                   //create the datasource for the report;
                    count = sqls.length;
                    for (int i = 0; i < count; i++) {
                        //NOTE: that the sql in the template can reference the data in input BizData object;
                        rd = context.execQuerySql(sqls[i].getDaoName(), StringConvertor.replace(sqls[i].getSql(), d), sqls[i].getResultName(), rd);
                    }
                    r.setData(rd);
                } else {  //use the input data as the datasource of the report;
                    r.setData(d);
                }

                r.buildPdf();
            } catch (DocumentException e) {
                log.error("failt to build the report for tempalte " + templateName, e);
                dispatchError(d, SysError.FAILED_BUILD_REPORT, request, response);
                return;
            } catch (FileNotFoundException fnfe) {
                log.error("can't open the report file for template  " + templateName, fnfe);
                dispatchError(d, SysError.CANT_FILE_REPORT, request, response);
                return;
            } catch (IOException ioe) {
                log.error("can't write the report file for template  " + templateName, ioe);
                dispatchError(d, SysError.FAILED_WRITE_REPORT_FILE, request, response);
                return;
            } catch (SQLException sqle) {
                log.error("exception in executing SQL ", sqle);
                dispatchError(d, SysError.FAILED_WRITE_REPORT_FILE, request, response);
                return;
            }
            //send the report pdf file to the client;
        }
        Util.sendFile(response, f, "application/PDF");
    }

    /**
     * dispatch the error page due to error;
     *
     * @param data
     * @param errorNO
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void dispatchError(BizData data, int errorNO, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        data.add(SysVar.ERROR_NO, new Integer(errorNO));
        request.setAttribute(SysVar.REQ_DATA, data);
        RequestDispatcher disp = request.getRequestDispatcher(SysVar.ERRORPAGE);
        if (disp == null) {
            System.out.println("Error in BLServlet.dispatch():"
                    + "Error in BL and will forward to /jsp/error.jsp,but" +
                    " this page is not specifed!");
        } else {
            disp.forward(request, response);
        }
    }
}