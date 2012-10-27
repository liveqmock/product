/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.web.tag;

import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.database.datadict.DataDict;
import com.dream.bizsdk.common.database.datadict.DBTableDef;
import com.dream.bizsdk.common.database.datadict.DBColumnDef;
import com.dream.bizsdk.common.databus.BizData;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-24
 * Time: 14:10:32
 */
public class TableRead extends BaseTable {

    /**
     * @return
     * @throws javax.servlet.jsp.JspException
     */
    public int doStartTag() throws JspException {
        int i = 0;
        int len;
        String width1;
        String width2;
        ServletContext sc = pc.getServletContext();
        HashMap dicsts = (HashMap) sc.getAttribute(SysVar.APP_SDCS);
        DataDict dc = (DataDict) dicsts.get(daoName);
        ServletRequest request = pc.getRequest();
        BizData rd = (BizData) request.getAttribute(SysVar.REQ_DATA);
        if (rd == null) {
            rd = new BizData();
            request.setAttribute(SysVar.REQ_DATA, rd);
        }

        BizData bdc = (BizData) sc.getAttribute(SysVar.APP_BDC);
        if (rd == null) {   //if the request is not available, create it ans set it to the current request;
            rd = new BizData();
            pc.getRequest().setAttribute(SysVar.REQ_DATA, rd);
        }
        DBTableDef table = dc.getTableDef(tableName);
        JspWriter w = pc.getOut();
        try {
            if (table == null) {    //table not found;
                noThisTable(w);
            } else {  //table found;
                boolean twoColumns = twoCols != null && "true".equalsIgnoreCase(twoCols) ? true : false;
                DBColumnDef[] colDefs = table.getSortedColumns();
                len = colDefs.length;

                if (colsWidth != null) {
                    List l = computeColWidths(2);
                    width1 = (String) l.get(0);
                    width2 = (String) l.get(1);
                } else {
                    if (twoColumns) {
                        width1 = "15%";
                        width2 = "35%";
                    } else {
                        width1 = "20%";
                        width2 = "80%";
                    }
                }

                beginTable(w);
                while (i < len) {
                    w.println("<tr>");
                    if (twoColumns) {
                        writeColumn(w, tableName, colDefs[i++], width1, width2, rd, bdc);
                        if (i < len) {
                            writeColumn(w, tableName, colDefs[i], width1, width2, rd, bdc);
                        } else {
                            if ((len % 2) != 0) {
                                w.println("  <td class=\"head\" width=\"15%\">&nbsp;</td>");
                                w.println("  <td class=\"content\" width=\"35%\">&nbsp;</td>");
                            }
                        }
                    } else {
                        writeColumn(w, tableName, colDefs[i], width1, width2, rd, bdc);
                    }
                    w.println("</tr>");
                    i++;
                }
                endTable(w);
            }
        } catch (IOException ioe) {
            throw new JspException("IOException", ioe);
        }
        return 0;
    }

    /**
     * write a column to the output stream;
     *
     * @param out
     * @param tableName
     * @param fld
     * @param width1
     * @param width2
     * @param rd
     * @throws IOException
     */
    private void writeColumn(JspWriter out, String tableName, DBColumnDef fld,
                             String width1, String width2, BizData rd, BizData bdc)
            throws IOException {

        String fieldName = fld.getName();

        beginTd(out, width1, "head");
        out.print("    ");
        out.println(fld.getDisplayName() == null ? fld.getName() : fld.getDisplayName());
        endTd(out);
        beginTd(out, width2, "content");
        writeValue(out, tableName, fieldName, fld, rd, bdc);
        endTd(out);
    }

    /**
     * write the value of a column to the output;
     *
     * @param w         the outputstream;
     * @param tableName the name of the source record;
     * @param fieldName the fieldName;
     * @param rd        the request Data object;
     * @throws IOException
     */
    private void writeValue(JspWriter w, String tableName, String fieldName, DBColumnDef field, BizData rd
                            , BizData bdc) throws IOException {
        String source = field.getSource();

        w.println("    ");

        if (source.equalsIgnoreCase("fk")) {
            //String refTable = field.getRefTableName();
            //String refCode = field.getRefCode();
            //String refName = field.getRefName();

            w.println(rd.getString(tableName, fieldName, "0"));
        } else if (source.equalsIgnoreCase("busi")) {
            String code = rd.getString(tableName, fieldName, "0");
            w.println(bdc.getString(tableName + "/" + fieldName, fieldName, code));
        } else {
            w.println(rd.getString(tableName, fieldName, "0"));
        }
    }
}