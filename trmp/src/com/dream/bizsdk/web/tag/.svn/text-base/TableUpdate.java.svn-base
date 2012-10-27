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
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-24
 * Time: 14:09:58
 */
public class TableUpdate extends BaseTable {
    /**
     * @return
     * @throws javax.servlet.jsp.JspException
     */
    public int doStartTag() throws JspException {
        int i = 0;
        int len;
        String width1;
        String width2;

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
                boolean twoColumns = twoCols != null && "true".compareToIgnoreCase(twoCols) == 0 ? true : false;
                DBColumnDef[] colDefs = table.getSortedColumns();
                len = colDefs.length;
                Set rdCols = filterReadCols();

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
                        writeColumn(w, tableName, colDefs[i++], width1, width2, dc, bdc, rd, rdCols);
                        if (i < len) {
                            writeColumn(w, tableName, colDefs[i], width1, width2, dc, bdc, rd, rdCols);
                        } else {
                            if ((len % 2) != 0) {
                                w.println("  <td class=\"head\" width=\"15%\">&nbsp;</td>");
                                w.println("  <td class=\"content\" width=\"35%\">&nbsp;</td>");
                            }
                        }
                    } else {
                        writeColumn(w, tableName, colDefs[i], width1, width2, dc, bdc, rd, rdCols);
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
     * @param out
     * @param tableName
     * @param fld
     * @param width1
     * @param width2
     * @param dc
     * @param bdc
     * @param rd
     * @throws IOException
     */
    private void writeColumn(JspWriter out, String tableName, DBColumnDef fld,
                             String width1, String width2, DataDict dc, BizData bdc, BizData rd, Set rdCols)
            throws IOException {
        String dType = fld.getDispType();

        int len = fld.getLength();
        String fieldName = fld.getName();

        int dispType = getDispType(dType);
        boolean readOnly = rdCols != null && rdCols.contains(fieldName) ? true : false;

        beginTd(out, width1, "head");
        out.print("    ");
        out.println(fld.getDisplayName() == null ? fld.getName() : fld.getDisplayName());
        endTd(out);
        beginTd(out, width2, "content");
        switch (dispType) {
            case BaseTable.TEXT:
                writeTextColumn(out, tableName, fieldName, len, dc, rd, readOnly);
                break;
            case BaseTable.RADIO:
                writeRadioColumn(out, tableName, fieldName, bdc, rd, readOnly);
                break;
            case BaseTable.CHECKBOX:
                writeCheckboxColumn(out, tableName, fieldName, bdc, rd, readOnly);
                break;
            case BaseTable.SELECT:
                String source = fld.getSource();
                String refTable = fld.getRefTableName();
                String refCode = fld.getRefCode();
                String refName = fld.getRefName();
                writeSelect(out, tableName, fieldName, refTable, refCode, refName, source, bdc, rd, readOnly);
                break;
            default:
                break;
        }
        endTd(out);
    }

    /**
     * @param out
     * @param tableName
     * @param fieldName
     * @param len
     * @param dc
     * @param rd
     * @throws IOException
     */
    private void writeTextColumn(JspWriter out, String tableName, String fieldName, int len
                                 , DataDict dc, BizData rd, boolean readOnly) throws IOException {
        int size = 24;
        String value = rd.getString(tableName, fieldName, "0");

        out.print("    <input name=\"" + tableName + "/" + fieldName + "\" type=\"text\" size=\"");
        if (len < size) {
            if (len >= 4) {
                out.print(new Integer(len).toString());
            } else {
                out.print(new Integer(4).toString());
            }
        } else {
            out.print(new Integer(size).toString());
        }
        out.print("\" maxlength=\"" + dc.getFieldLength(tableName, fieldName) + "\"");
        out.print(" class=\"" + dc.getCSSClass(tableName, fieldName) + "\"");
        out.print(" value=\"");
        out.print(value);
        out.print("\"");
        if (readOnly) {
            out.println(" readonly");
        }
        out.println(">");
    }

    /**
     * @param out
     * @param tableName
     * @param fieldName
     * @param bdc
     * @param rd
     * @throws IOException
     */
    private void writeRadioColumn(JspWriter out, String tableName, String fieldName, BizData bdc
                                  , BizData rd, boolean readOnly) throws IOException {
        String value = rd.getString(tableName, fieldName, "0");
        String t1 = tableName + "/" + fieldName;
        int rows = bdc.getTableRowsCount(t1);
        String[] rowIDs = bdc.getRowIDs(t1);
        for (int i = 0; i < rows; i++) {
            StringBuffer sb = new StringBuffer(0);
            sb.append("    <input type=\"radio\" name=\"").append(tableName).append("/");
            sb.append(fieldName).append("\" value=\"").append(rowIDs[i]).append("\"");
            if (rowIDs[i].compareTo(value) == 0) {
                sb.append(" checked");
            }
            if (readOnly) {
                out.println(" readonly");
            }
            sb.append(">");
            out.print(new String(sb));
            out.println(bdc.getString(t1, fieldName, rowIDs[i]));
        }
    }

    /**
     * @param out
     * @param tableName
     * @param fieldName
     * @param bdc
     * @param rd
     * @throws IOException
     */
    private void writeCheckboxColumn(JspWriter out, String tableName, String fieldName, BizData bdc
                                     , BizData rd, boolean readOnly) throws IOException {
        String value = rd.getString(tableName, fieldName, "0");
        String t1 = tableName + "/" + fieldName;
        int rows = bdc.getTableRowsCount(t1);
        String[] rowIDs = bdc.getRowIDs(t1);
        for (int i = 0; i < rows; i++) {
            StringBuffer sb = new StringBuffer(0);
            sb.append("    <input type=\"checkbox\" name=\"").append(tableName).append("/");
            sb.append(fieldName).append("\" value=\"").append(rowIDs[i]).append("\"");
            if (rowIDs[i].compareTo(value) == 0) {
                sb.append(" checked");
            }
            if (readOnly) {
                out.println(" readonly");
            }
            sb.append(">");
            out.print(new String(sb));
            out.println(bdc.getString(t1, fieldName, rowIDs[i]));
        }
    }

    /**
     * @param out
     * @param tableName
     * @param fieldName
     * @param refTable
     * @param refCode
     * @param refName
     * @param source
     * @param bdc
     * @param rd
     * @throws IOException
     */
    private void writeSelect(JspWriter out, String tableName, String fieldName, String refTable, String refCode,
                             String refName, String source, BizData bdc, BizData rd, boolean readOnly) throws IOException {
        String t1;
        String v;
        int rows;
        String value = rd.getString(tableName, fieldName, "0");
        StringBuffer s = new StringBuffer("    <select name=\"").append(tableName).append("/").append(fieldName).append("\"");
        if (readOnly) {
            s.append(" disabled");
        }
        s.append(">");
        out.println(new String(s));
        if (source == null) {
            t1 = refTable + "s";
            rows = rd.getTableRowsCount(t1);
            for (int i = 0; i < rows; i++) {
                StringBuffer sb = new StringBuffer();
                sb.append("     <option value=\"");
                sb.append(v = rd.getStringByDI(t1, refCode, i));
                sb.append("\"");
                if (v.compareTo(value) == 0) {
                    sb.append(" selected");
                }
                sb.append(">");
                sb.append(rd.getStringByDI(t1, refName, i));
                sb.append("</option>");
                out.println(new String(sb));
            }
        } else if (source.compareToIgnoreCase("fk") == 0) {
            t1 = refTable + "s";
            rows = rd.getTableRowsCount(t1);
            for (int i = 0; i < rows; i++) {
                StringBuffer sb = new StringBuffer();
                sb.append("     <option value=\"");
                sb.append(v = rd.getStringByDI(t1, refCode, i));
                sb.append("\"");
                if (v.compareTo(value) == 0) {
                    sb.append(" selected");
                }
                sb.append(">");
                sb.append(rd.getStringByDI(t1, refName, i));
                sb.append("</option>");
                out.println(new String(sb));
            }
        } else if (source.compareToIgnoreCase("busi") == 0) {
            t1 = tableName + "/" + fieldName;
            rows = bdc.getTableRowsCount(t1);
            String[] rowIDs = bdc.getRowIDs(t1);
            for (int i = 0; i < rows; i++) {
                StringBuffer sb = new StringBuffer();
                sb.append("      <option value=\"");
                sb.append(rowIDs[i]);
                sb.append("\"");
                if (rowIDs[i].compareTo(value) == 0) {
                    sb.append(" selected");
                }
                sb.append(">");
                sb.append(bdc.getString(t1, fieldName, rowIDs[i]));
                sb.append("</option>");
                out.println(new String(sb));
            }
        }
        out.println("    </select>");
    }
}
