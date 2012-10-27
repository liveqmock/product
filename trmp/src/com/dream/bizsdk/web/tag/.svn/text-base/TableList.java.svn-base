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
import java.util.*;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-24
 * Time: 14:10:09
 */
public class TableList extends BaseTable {
    protected boolean selector;
    protected String selectorType = "checkbox";
    protected String colsWidth;

    /**
     * @return
     */
    public String getSelector() {
        return Boolean.toString(selector);
    }

    /**
     * @param s
     */
    public void setSelector(String s) {
        if (s != null && s.equalsIgnoreCase("true")) {
            selector = true;
        } else {
            selector = false;
        }
    }

    /**
     * @return
     */
    public String getSelectorType() {
        return selectorType;
    }

    /**
     * @param selectorType
     */
    public void setSelectorType(String selectorType) {
        this.selectorType = selectorType;
    }


    /**
     * @return
     * @throws JspException
     */
    public int doStartTag() throws JspException {
        HashMap dicsts = (HashMap) pc.getServletContext().getAttribute(SysVar.APP_SDCS);
        DataDict dc = (DataDict) dicsts.get(daoName);
        BizData rd = (BizData) pc.getRequest().getAttribute(SysVar.REQ_DATA);
        BizData bdc = (BizData) pc.getServletContext().getAttribute(SysVar.APP_BDC);
        DBTableDef table = dc.getTableDef(tableName);
        JspWriter w = pc.getOut();
        try {
            if (table == null) {    //table not found;
                noThisTable(w);
            } else {  //table found;
                List cols = getListCols(table);
                List colsWidth = computeColWidths(cols.size());
                beginTable(w);
                writeHeader(w, selector, table, cols, colsWidth);
                writeRows(w, selector, tableName, rd, bdc, cols, colsWidth, table);
                endTable(w);
            }
        } catch (IOException ioe) {
            throw new JspException("IOException", ioe);
        }
        return 0;
    }

    /**
     * @param w
     * @param selectBox
     * @param table
     * @param cols
     * @throws IOException
     */
    private void writeHeader(JspWriter w, boolean selectBox, DBTableDef table,
                             List cols, List colsWidth) throws IOException {
        int count;
        int i = 0;

        beginTr(w, true);

        if (selectBox && selectorType.equalsIgnoreCase("checkbox")) {
            beginTd(w, "10", "head");
            w.print("<input type=\"");
            w.print("checkbox");
            w.println("\" value=\"ca\" name=\"s_" + tableName + "\">");
            w.println("Ñ¡Ôñ");
            endTd(w);
        }

        count = cols.size();
        while (i < count) {
            beginTd(w, (String) colsWidth.get(i), "content");
            String colName = (String) cols.get(i);
            DBColumnDef cold = table.getColumn(colName);
            if (cold == null) {
                w.print(colName);
            } else {
                w.print(cold.getDisplayName());
            }
            w.println();
            endTd(w);
            i++;
        }

        endTr(w);
    }

    /**
     * @param w
     * @param selectBox
     * @param list
     * @param rd
     * @param bdc
     * @throws IOException
     */
    private void writeRows(JspWriter w, boolean selectBox, String list, BizData rd,
                           BizData bdc, List cols, List colsWidth, DBTableDef table) throws IOException {
        int count;
        int i = 0;
        int j = 0;

        count = cols.size();
        String[] rowIDs = rd.getRowIDs(list);

        while (j < rowIDs.length) {
            beginTr(w, false);

            if (selectBox) {
                beginTd(w, "10", "head");
                w.print("<input type=\"");
                if (selectorType.equalsIgnoreCase("checkbox")) {
                    w.print("checkbox");
                } else if (selectorType.equalsIgnoreCase("radio")) {
                    w.print("radio");
                }
                w.println("\" value=\"ca\" name=\"s_" + list + "\">");
                w.println("Ñ¡Ôñ");
                endTd(w);
            }

            i = 0;
            while (i < count) {
                beginTd(w, (String) colsWidth.get(i), "content");
                String colName = (String) cols.get(i);
                String v = (String) rd.get(list, colName, rowIDs[j]);
                if (v != null) {
                    DBColumnDef cold = table.getColumn(colName);
                    if (cold != null) {
                        String source = cold.getSource();
                        if (source != null) {
                            if (source.equalsIgnoreCase("busi")) {
                                v = bdc.getString(list + "/" + colName, colName, v);
                            }
                        }
                    }
                } else {
                    v = "&nbsp;";
                }
                w.println(v);
                endTd(w);
                i++;
            }
            endTr(w);
            j++;
        }
    }

    /**
     * @param table
     * @return
     */
    private List getListCols(DBTableDef table) {
        List ts = new ArrayList();
        if (cols == null) {
            DBColumnDef[] defs = table.getSortedColumns();
            for (int i = 0; i < defs.length; i++) {
                ts.add(defs[i].getName());
            }
        } else {
            StringTokenizer st = new StringTokenizer(cols, ",");
            while (st.hasMoreTokens()) {
                ts.add(st.nextToken());
            }
        }
        return ts;
    }
}