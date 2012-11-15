/**
 *all rights reserved,@copyright 2003
 */
package com.cots.report;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

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
    private Logger log;


    /**
     *
     */
    public Engine() {
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
     * @param t
     * @param reportName
     */
    public Report buildReport(Template t, String reportName) {
        return null;
    }

    /**
     * @param t
     * @param reportName
     * @param d
     */
    public Report buildReport(Template t, String reportName, HashMap d) {
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
    public Report buildReport(String templateName, HashMap inputData) {
        try {
            Template t = new Template(templatePath + templateName + ".htm");
            String reportName = null;
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

    public Report buildReport(String templateName, String reportName, HashMap d) {
        return null;
    }
}
