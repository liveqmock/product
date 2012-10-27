/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.report;

import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.StringConvertor;
import com.dream.bizsdk.web.ReportServlet;
import com.lowagie.text.DocumentException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.xml.sax.SAXException;
import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-1
 * Time: 8:30:09
 */
public class Engine {
    //path to save the report;
    private String reportPath;
    //path that contains the report template;
    private String templatePath;
    //BLContext that data will be extracted from;
    private IBLContext context;

    private Logger log;


    /**
     *
     */
    public Engine() {
        reportPath = ReportServlet.REPORT_PATH;
        templatePath = ReportServlet.TEMPLATE_PATH;
        log = Logger.getLogger("com.dream.report");
    }

    /**
     * @param context
     */
    public Engine(IBLContext context) {
        this.context = context;
        reportPath = ReportServlet.REPORT_PATH;
        templatePath = ReportServlet.TEMPLATE_PATH;
        log = Logger.getLogger("com.dream.report");
    }

    /**
     * @return
     */
    public String getReportPath() {
        return this.reportPath;
    }

    /**
     * @param reportPath
     */
    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    /**
     * @return
     */
    public String getTemplatePath() {
        return this.templatePath;
    }

    /**
     * @param templatePath
     */
    public void setTempaltePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * @return
     */
    public IBLContext getContext() {
        return this.context;
    }

    /**
     * @param context
     */
    public void setContext(IBLContext context) {
        this.context = context;
    }

    /**
     * @param t
     * @param reportName
     */
    public Report buildReport(Template t, String reportName) {
        int count = 0;
        BizData d = new BizData();
        try {
            Report r = new Report(reportPath + reportName + ".pdf", t);
            ReportSql[] sqls = t.getSql();
            count = sqls.length;
            for (int i = 0; i < count; i++) {
                d = context.execQuerySql(sqls[i].getDaoName(), StringConvertor.replace(sqls[i].getSql(), d), sqls[i].getResultName(), d);
            }
            r.setData(d);
            r.buildPdf();
            return r;
        } catch (FileNotFoundException fnfe) {
            log.error("can't open the report file", fnfe);
        } catch (IOException ioe) {
            log.error("can't write the report file", ioe);
        } catch (DocumentException de) {
            log.error("failed to build the report", de);
        } catch (SQLException sqle) {
            log.error("failed to execute SQL", sqle);
        }
        return null;
    }

    /**
     * @param t
     * @param reportName
     * @param d
     */
    public Report buildReport(Template t, String reportName, BizData d) {
        try {
            Report r = new Report(reportPath + reportName + ".pdf", t);
            r.setData(d);
            r.buildPdf();
            return r;
        } catch (FileNotFoundException fnfe) {
            log.error("can't open the report file", fnfe);
        } catch (IOException ioe) {
            log.error("can't write the report file", ioe);
        } catch (DocumentException de) {
            log.error("failed to build the report", de);
        }
        return null;
    }

    /**
     * @param templateName
     * @param reportName
     */
    public Report buildReport(String templateName, String reportName) {
        try {
            return buildReport(new Template(templatePath + templateName + ".htm"), reportName);
        } catch (IOException e) {
            log.error("failed to read the template file" + templateName, e);
        } catch (ParserConfigurationException e) {
            log.error("failed to open the template file" + templateName, e);
        } catch (SAXException e) {
            log.error("failed to parse the template file" + templateName, e);
        }
        return null;
    }

    /**
     * @param templateName
     * @param inputData
     */
    public Report buildReport(String templateName, BizData inputData) {
        try {
            Template t = new Template(templatePath + templateName + ".htm");
            String reportName = t.getReportName(inputData);
            return buildReport(t, reportName);
        } catch (IOException e) {
            log.error("failed to read the template file" + templateName, e);
        } catch (ParserConfigurationException e) {
            log.error("failed to open the template file" + templateName, e);
        } catch (SAXException e) {
            log.error("failed to parse the template file" + templateName, e);
        }
        return null;
    }

    public Report buildReport(String templateName, String reportName, BizData d) {
        try {
            return buildReport(new Template(templatePath + templateName + ".htm"), reportName, d);
        } catch (IOException e) {
            log.error("failed to read the template file" + templateName, e);
        } catch (ParserConfigurationException e) {
            log.error("failed to open the template file" + templateName, e);
        } catch (SAXException e) {
            log.error("failed to parse the template file" + templateName, e);
        }
        return null;
    }
}
