/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.web.tag;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-24
 * Time: 14:04:27
 */
public class BaseTable extends BaseTag {
    public final static int TEXT = 10;          //abbr. T
    public final static int RADIO = 11;         //abbr. R
    public final static int CHECKBOX = 12;      //abbr. C
    public final static int SELECT = 13;        //abbr. S
    public final static int FILE = 14;          //abbr. F
    public final static int LIST = 15;          //abbr. L
    public final static int TEXTAREA = 16;      //abbr. E
    public final static int PASSWORD = 17;      //abbr. P

    protected String daoName;
    protected String tableName;
    protected String cols;
    protected String readCols;
    protected String editCols;
    protected String twoCols;
    protected String border;
    protected String cellspacing;
    protected String cellpadding;
    protected String width;
    protected String bgColor;
    protected String className;
    protected String colsWidth;

    /**
     * @return
     */
    public String getDaoName() {
        return daoName;
    }

    /**
     * @return
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return
     */
    public String getCols() {
        return cols;

    }

    /**
     * @return
     */
    public String getBorder() {
        return border;
    }

    /**
     * @return
     */
    public String getCellSpacing() {
        return cellspacing;
    }

    /**
     * @return
     */
    public String getCellPadding() {
        return cellpadding;
    }

    /**
     * @return
     */
    public String getWidth() {
        return width;
    }

    /**
     * @return
     */
    public String getBgColor() {
        return bgColor;
    }

    public String getClassName() {
        return this.className;
    }

    public String getTwoCols() {
        return this.twoCols;
    }

    public String getReadCols() {
        return this.readCols;
    }

    public String getEditCols() {
        return this.editCols;
    }

    /**
     * @param daoName
     */
    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    /**
     * @param tableName
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @param cols
     */
    public void setCols(String cols) {
        this.cols = cols;
    }

    /**
     * @param cellpadding
     */
    public void setCellPadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    /**
     * @param cellSpacing
     */
    public void setCellSpacing(String cellSpacing) {
        this.cellspacing = cellSpacing;
    }

    /**
     * @param border
     */
    public void setBorder(String border) {
        this.border = border;
    }

    /**
     * @param width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @param bgColor
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setTwoCols(String twoCols) {
        this.twoCols = twoCols;
    }

    public void setReadCols(String readCols) {
        this.readCols = readCols;
    }

    public void setEidtCols(String editCols) {
        this.editCols = editCols;
    }

    /**
     *
     */
    public void release() {
        daoName = null;
        tableName = null;
        cols = null;
        border = null;
        cellspacing = null;
        cellpadding = null;
        className = null;
        colsWidth = null;
    }

    /**
     * write <table> tag according to the current settings;
     *
     * @param w the JspWriter Object;
     * @throws IOException
     */
    protected void beginTable(JspWriter w) throws IOException {
        w.print("<table");
        if (width != null) {
            w.print(" width=\"" + width + "\"");
        }
        if (border != null) {
            w.print(" border=\"" + border + "\"");
        }
        if (cellspacing != null) {
            w.print(" cellspacing=\"" + cellspacing + "\"");
        }
        if (cellpadding != null) {
            w.print(" cellpadding=\"" + cellpadding + "\"");
        }
        if (cellpadding != null) {
            w.print(" cellpadding=\"" + cellpadding + "\"");
        }
        if (className != null) {
            w.print(" class=\"" + className + "\"");
        }
        if (bgColor != null) {
            w.print(" bgcolor=\"" + bgColor + "\"");
        }
        w.println(">");
    }

    /**
     * end of write a html table;
     *
     * @param w
     * @throws IOException
     */
    protected void endTable(JspWriter w) throws IOException {
        w.println("</table>");
    }

    /**
     * begin to write a html tr object;
     *
     * @param w
     * @param isHead
     * @throws IOException
     */
    protected void beginTr(JspWriter w, boolean isHead) throws IOException {
        w.print("<tr");
        if (isHead) {
            w.println(" class=\"head\">");
        } else {
            w.println(" class=\"content\">");
        }
    }

    /**
     * end writing a html tr object;
     *
     * @param w
     * @throws IOException
     */
    protected void endTr(JspWriter w) throws IOException {
        w.println("</tr>");
    }

    /**
     * @param w
     * @param width
     * @param className
     * @throws IOException
     */
    protected void beginTd(JspWriter w, String width, String className) throws IOException {
        w.print("  <td");
        if (width != null) {
            w.print(" width=\"" + width + "\"");
        }
        if (className != null) {
            w.print(" class=\"" + className + "\"");
        }
        w.println(">");
    }

    /**
     * @param w
     * @throws IOException
     */
    protected void endTd(JspWriter w) throws IOException {
        w.println("  </td>");
    }

    /**
     * @param w
     * @throws IOException
     */
    protected void noThisTable(JspWriter w) throws IOException {
        w.println("<!--Table not found exception: " + tableName + " in dao " + daoName + "-->");
    }

    /**
     * @param dType
     * @return
     */
    protected int getDispType(String dType) {
        int dispType = BaseTable.TEXT;
        if (dType != null) {
            if (dType.compareTo("T") == 0) {
                dispType = BaseTable.TEXT;
            } else if (dType.compareTo("S") == 0) {
                dispType = BaseTable.SELECT;
            } else if (dType.compareTo("R") == 0) {
                dispType = BaseTable.RADIO;
            } else if (dType.compareTo("C") == 0) {
                dispType = BaseTable.CHECKBOX;
            } else if (dType.compareTo("L") == 0) {
                dispType = BaseTable.LIST;
            } else if (dType.compareTo("F") == 0) {
                dispType = BaseTable.FILE;
            } else if (dType.compareTo("E") == 0) {
                dispType = BaseTable.TEXTAREA;
            } else if (dType.compareTo("P") == 0) {
                dispType = BaseTable.PASSWORD;
            }
        }
        return dispType;
    }

    /**
     * @return
     */
    public String getColsWidth() {
        return colsWidth;
    }

    /**
     * @param s
     */
    public void setColsWidth(String s) {
        this.colsWidth = s;
    }

    /**
     * get the cols of earch width;
     *
     * @param colsCount
     * @return
     */
    protected List computeColWidths(int colsCount) {
        List ts = new ArrayList();
        if (colsWidth == null) {
            int w = 100 / colsCount;
            String ws = Integer.toString(w) + "%";
            for (int i = 0; i < colsCount; i++) {
                ts.add(ws);
            }
        } else {
            int count = 0;
            StringTokenizer st = new StringTokenizer(colsWidth, ",");
            while (st.hasMoreTokens()) {
                ts.add(st.nextToken());
                count++;
            }
            while (count > colsCount) {
                ts.add("10%");
                count++;
            }
        }
        return ts;
    }

    /**
     * @return
     */
    protected Set filterCols() {
        if (cols != null) {
            Set l = new TreeSet();
            StringTokenizer st = new StringTokenizer(cols, ",");
            while (st.hasMoreTokens()) {
                String t = st.nextToken().toUpperCase();
                l.add(t);
            }
            return l;
        } else {
            return null;
        }
    }

    protected Set filterReadCols() {
        if (cols != null) {
            Set l = new TreeSet();
            StringTokenizer st = new StringTokenizer(readCols, ",");
            while (st.hasMoreTokens()) {
                String t = st.nextToken().toUpperCase();
                l.add(t);
            }
            return l;
        } else {
            return null;
        }
    }

}