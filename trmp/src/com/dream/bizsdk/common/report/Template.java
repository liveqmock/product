/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.report;

import com.dream.bizsdk.common.util.xml.XMLFile;
import com.dream.bizsdk.common.util.string.StringConvertor;
import com.dream.bizsdk.common.databus.BizData;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-1
 * Time: 8:29:55
 */
public class Template {
    private XMLFile xml;

    public Template(String path)
            throws IOException, ParserConfigurationException, SAXException {
        xml = new XMLFile(path);
    }

    public Template(File f)
            throws IOException, ParserConfigurationException, SAXException {
        xml = new XMLFile(f);
    }

    public XMLFile getXMLFile() {
        return xml;
    }

    public Element getBody() {
        return (Element) xml.selectSingleNode("/html/body");
    }

    public ReportSql[] getSql() {
        int count = 0;
        String sql;
        NodeList nl = xml.selectNodeList("/html/head/sqls/sql");
        if (nl != null && (count = nl.getLength()) > 0) {
            ReportSql[] sqls = new ReportSql[count];
            for (int i = 0; i < count; i++) {
                Element e = (Element) nl.item(i);
                String daoName = e.getAttribute("daoName");
                String resultName = e.getAttribute("resultName");
                Node c = nl.item(i).getFirstChild();
                if (c != null) {
                    sql = c.getNodeValue();
                } else {
                    sql = null;
                }
                sqls[i] = new ReportSql(sql, daoName, resultName);
            }
            return sqls;
        } else {
            return new ReportSql[0];
        }
    }

    /**
     * @param d
     * @return
     */
    public String getReportName(BizData d) {
        String title = xml.getSingleNodeValue("/html/head/title");
        if (title == null) {
            title = Long.toString(System.currentTimeMillis());
        }
        return StringConvertor.replace(title, d);
    }
}