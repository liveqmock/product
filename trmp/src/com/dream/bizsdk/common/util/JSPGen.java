package com.dream.bizsdk.common.util;

import com.dream.bizsdk.common.database.datadict.*;

import java.io.File;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class JSPGen {

    public final static int LIST_JSP = 1;
    public final static int INFO_U_JSP = 2;
    public final static int INFO_R_JSP = 3;
    public final static int INFO_N_JSP = 4;

    public final static int TEXT = 10;
    public final static int RADIO = 11;
    public final static int CHECKBOX = 12;
    public final static int SELECT = 13;

    protected String entityName;
    protected String entitiesName;
    protected DataDict dc = null;
    protected String root = "c:\\";


    int size = 20;

    /**
     * Constructor,
     *
     * @param root is the directory in which generated JSPs will be saved
     * @param dc   the data dictionary;
     */
    public JSPGen(String root, DataDict dc) {
        if (root != null) {
            this.root = root;
            if (this.root.charAt(this.root.length() - 1) != File.separatorChar) {
                this.root += File.separatorChar;
            }
        }
        this.dc = dc;
    }

    public File createFile(String fileName) {
        File f = new File(root + fileName);
        return f;
    }

    public void writeInfoFieldN(PrintStream out, DBColumnDef fld, boolean twoCols) {
        String width1 = null;
        String width2 = null;
        int dispType = JSPGen.TEXT;
        String dType = fld.getDispType();

        int len = fld.getLength();

        String tableName = fld.getTableName();
        String fieldName = fld.getName();

        if (dType != null) {
            if (dType.compareTo("T") == 0) {
                dispType = JSPGen.TEXT;
            } else if (dType.compareTo("S") == 0) {
                dispType = JSPGen.SELECT;
            } else if (dType.compareTo("R") == 0) {
                dispType = JSPGen.RADIO;
            } else if (dType.compareTo("C") == 0) {
                dispType = JSPGen.CHECKBOX;
            }
        }
        if (twoCols) {
            width1 = "15";
            width2 = "35";
        } else {
            width1 = "20";
            width2 = "80";
        }

        out.print("        <td class=\"head\" width=\"" + width1 + "%\">");
        out.print(fld.getDisplayName() == null ? fld.getName() : fld.getDisplayName());
        out.println("</td>");
        out.println("        <td class=\"content\"  width=\"" + width2 + "%\">");
        switch (dispType) {
            case JSPGen.TEXT:
                out.print("          <input name=\"<%=tName%>/" + fld.getName() + "\" type=\"text\" value=\"\" size=\"");
                if (len < size) {
                    if (len >= 4) {
                        out.print(new Integer(len).toString());
                    } else {
                        out.print(new Integer(4).toString());
                    }
                } else {
                    out.print(new Integer(size).toString());
                }
                out.print("\" maxlength=\"<%=dc.getFieldLength(tName,\"" + fieldName + "\")%>\" ");
                out.print(" class=\"<%=dc.getCSSClass(tName,\"" + fieldName + "\")%>\"");
                out.println(">");
                break;
            case JSPGen.RADIO:
                out.println("         <% rows=bdc.getTableRowsCount(\"" + tableName + "/" + fieldName + "\");");
                out.println("          rowIDs=bdc.getRowIDs(\"" + tableName + "/" + fieldName + "\");");
                out.println("         for(int i=0;i<rows;i++){%>");
                out.print("           <input type=\"radio\" name=\"<%=tName%>/" + fieldName + "\" value=\"<%=rowIDs[i]%>\" >");
                out.println("<%=bdc.getString(tName\"" + fieldName + "\",\"" + fieldName + "\",rowIDs[i])%>");
                out.println("         <%}%>");
                break;
            case JSPGen.CHECKBOX:
                out.println("         <% rows=bdc.getTableRowsCount(\"" + tableName + "/" + fieldName + "\");");
                out.println("          rowIDs=bdc.getRowIDs(\"" + tableName + "/" + fieldName + "\");");
                out.println("         for(i=0;i<rows;i++){%>");
                out.print("           <input type=\"checkbox\" name=\"<%=tName%>/" + fieldName + "\" value=\"<%=rowIDs[i]%>\" >");
                out.println("<%=bdc.getString(tName\"" + fieldName + "\",\"" + fieldName + "\",rowIDs[i])%>");
                out.println("         <%}%>");
                break;
            case JSPGen.SELECT:
                String source = fld.getSource();
                String refTable = fld.getRefTableName();
                String refCol = fld.getRefCode();
                if (source == null) {
                    out.println("          <select name=\"<%=tName%>/" + fld.getName() + "\">");
                    out.println("          <%rows=rd.getTableRowsCount(\"" + refTable + "s\");");
                    out.println("            for(int i=0;i<rows;i++){%>");
                    out.println("              <option value=\"<%=rd.getStringByDI(\"" + refTable + "s\",\"" + refCol + "\",i)%>\"><%=rd.getStringByDI(\"" + refTable + "s\",\"******\",i)%></option>");
                    out.println("          <%}%>");
                    out.println("          </select>");
                } else if (source.compareToIgnoreCase("fk") == 0) {
                    out.println("          <select name=\"<%=tName%>/" + fld.getName() + "\">");
                    out.println("          <%rows=rd.getTableRowsCount(\"" + refTable + "s\");");
                    out.println("             for(int i=0;i<rows;i++){%>");
                    out.println("              <option value=\"<%=rd.getStringByDI(\"" + refTable + "s\",\"" + refCol + "\",i)%>\"><%=rd.getStringByDI(\"" + refTable + "s\",\"******\",i)%></option>");
                    out.println("          <%}%>");
                    out.println("          </select>");
                } else if (source.compareToIgnoreCase("busi") == 0) {
                    out.println("          <select name=\"<%=tName%>/" + fld.getName() + "\">");
                    out.println("          <%rows=bdc.getTableRowsCount(\"" + tableName + "/" + fieldName + "\");");
                    out.println("              rowIDs=bdc.getRowIDs(\"" + tableName + "/" + fieldName + "\");");
                    out.println("             for(int i=0;i<rows;i++){%>");
                    out.println("               <option value=\"<%=rowIDs[i]%>\"><%=bdc.getString(\"" + tableName + "/" + fieldName + "\",\"" + fieldName + "\",rowIDs[i])%></option>");
                    out.println("          <%}%>");
                    out.println("          </select>");
                } else {
                }
                break;
            default:
                break;
        }
        out.println("        </td>");
    }

    public void writeInfoFieldR(PrintStream out, DBColumnDef fld, boolean twoCols) {

        String width1 = null;
        String width2 = null;

        String tableName = fld.getTableName();
        String fieldName = fld.getName();
        String source = fld.getSource();

        if (twoCols) {
            width1 = "15";
            width2 = "35";
        } else {
            width1 = "20";
            width2 = "80";
        }

        out.print("        <td class=\"head\" width=\"" + width1 + "%\">");
        out.print(fld.getDisplayName() == null ? fld.getName() : fld.getDisplayName());
        out.println("</td>");
        out.println("        <td class=\"content\" width=\"" + width2 + "%\">");
        if (source != null && source.compareToIgnoreCase("busi") == 0) {
            out.println("          <%=bdc.getString(tName,\"" + fieldName + "\",rd.getString(tName,\"" + fieldName + "\",0))%>&nbsp;");
        } else {
            out.println("          <%=rd.getString(tName,\"" + fieldName + "\",0)%>&nbsp;");
        }
        out.println("        </td>");
    }

    public void writeInfoFieldU(PrintStream out, DBColumnDef fld, boolean twoCols) {
        String width1 = null;
        String width2 = null;
        int dispType = JSPGen.TEXT;
        String dType = fld.getDispType();

        int len = fld.getLength();

        String tableName = fld.getTableName();
        String fieldName = fld.getName();

        if (dType != null) {
            if (dType.compareTo("T") == 0) {
                dispType = JSPGen.TEXT;
            } else if (dType.compareTo("S") == 0) {
                dispType = JSPGen.SELECT;
            } else if (dType.compareTo("R") == 0) {
                dispType = JSPGen.RADIO;
            } else if (dType.compareTo("C") == 0) {
                dispType = JSPGen.CHECKBOX;
            }
        }
        if (twoCols) {
            width1 = "15";
            width2 = "35";
        } else {
            width1 = "20";
            width2 = "80";
        }

        out.print("        <td class=\"head\" width=\"" + width1 + "%\">");
        out.print(fld.getDisplayName() == null ? fld.getName() : fld.getDisplayName());
        out.println("</td>");
        out.println("        <td class=\"content\"  width=\"" + width2 + "%\">");
        switch (dispType) {
            case JSPGen.TEXT:
                out.print("          <input name=\"<%=tName%>/" + fld.getName() + "\" type=\"text\" value=\"<%=rd.getString(tName,\"" + fld.getName() + "\",0)%>\" size=\"");
                if (len < size) {
                    if (len >= 4) {
                        out.print(new Integer(len).toString());
                    } else {
                        out.print(new Integer(4).toString());
                    }
                } else {
                    out.print(new Integer(size).toString());
                }
                out.print("\" maxlength=\"<%=dc.getFieldLength(tName,\"" + fieldName + "\")%>\" ");
                out.print(" class=\"<%=dc.getCSSClass(tName,\"" + fieldName + "\")%>\"");
                out.println(">");
                break;
            case JSPGen.RADIO:
                out.println("         <% rows=bdc.getTableRowsCount(\"" + tableName + "/" + fieldName + "\");");
                out.println("         String[] rowIDs=bdc.getRowIDs(\"" + tableName + "/" + fieldName + "\");");
                out.println("         for(i=0;i<rows;i++){%>");
                out.print("           <input type=\"radio\" name=\"<%=tName%>/" + fieldName + "\" value=\"<%=rowIDs[i]%>\" <%=rowIDs[i].compareTo(rd.getString(tName,\"" + fld.getName() + "\",0)==0?\"checked\":\"\"%> >");
                out.println("<%=bdc.getString(tName\"" + fieldName + "\",\"" + fieldName + "\",rowIDs[i])%>");
                out.println("         <%}%>");
                break;
            case JSPGen.CHECKBOX:
                out.println("         <% rows=bdc.getTableRowsCount(\"" + tableName + "/" + fieldName + "\");");
                out.println("         String[] rowIDs=bdc.getRowIDs(\"" + tableName + "/" + fieldName + "\");");
                out.println("         for(i=0;i<rows;i++){%>");
                out.print("           <input type=\"checkbox\" name=\"<%=tName%>/" + fieldName + "\" value=\"<%=rowIDs[i]%>\"  <%=rowIDs[i].compareTo(rd.getString(tName," + fld.getName() + "\",0)==0?\"checked\":\"\"%> >");
                out.println("<%=bdc.getString(tName\"" + fieldName + "\",\"" + fieldName + "\",rowIDs[i])%>");
                out.println("         <%}%>");
                break;
            case JSPGen.SELECT:
                String source = fld.getSource();
                String refTable = fld.getRefTableName();
                String refCol = fld.getRefCode();
                if (source == null) {
                    out.println("          <select name=\"<%=tName%>/" + fld.getName() + "\">");
                    out.println("          </select>");
                } else if (source.compareToIgnoreCase("fk") == 0) {
                    out.println("          <select name=\"<%=tName%>/" + fld.getName() + "\">");
                    out.println("          <%rows=rd.getTableRowsCount(\"" + refTable + "s\");");
                    out.println("            for(i=0;i<rows;i++){");
                    out.println("              String code = rd.getStringByDI(\" " + refTable + "s\",\"" + refCol + "\",i); %>");
                    out.println("              <option value=\"<%=code%>\" <%=code.compareTo(rd.getString(tName,\"" + fld.getName() + "\",0))==0?\"selected\":\"\"%> ><%=rd.getStringByDI(\"" + refTable + "s\",\"******\",i)%></option>");
                    out.println("          <%}%>");
                    out.println("          </select>");
                } else if (source.compareToIgnoreCase("busi") == 0) {
                    out.println("          <select name=\"<%=tName%>/" + fld.getName() + "\">");
                    out.println("          <%rows=bdc.getTableRowsCount(\"" + tableName + "/" + fieldName + "\");");
                    out.println("             String[] rowIDs=bdc.getRowIDs(\"" + tableName + "/" + fieldName + "\");");
                    out.println("             for(i=0;i<rows;i++){%>");
                    out.println("               <option value=\"<%=rowIDs[i]%>\"  <%=rowIDs[i].compareTo(rd.getString(tName,\"" + fld.getName() + "\",0))==0?\"selected\":\"\"%> ><%=bdc.getString(\"" + tableName + "/" + fieldName + "\",\"" + fieldName + "\",rowIDs[i])%></option>");
                    out.println("          <%}%>");
                    out.println("          </select>");
                } else {
                    out.println("          <select name=\"<%=tName%>/" + fld.getName() + "\">");
                    out.println("          </select>");
                }
                break;
            default:
                break;
        }
        out.println("        </td>");
    }

    /**
     * @param tableName
     * @param fileName
     * @param twoColumns
     * @param withMenu
     * @param inSubDir
     * @throws FileNotFoundException
     */
    public void writeReadJSP(String tableName, String fileName, boolean twoColumns, boolean withMenu, int inSubDir)
            throws FileNotFoundException {
        int i = 0;
        int len = 0;
        DBTableDef tab = getTableDef(tableName);
        if (tab == null) {
            return;
        }
        DBColumnDef[] fields = tab.getSortedColumns();
        len = fields.length;

        File f = createFile(fileName);
        PrintStream out = new PrintStream(new FileOutputStream(f));

        out.println("<%@page contentType=\"text/html;charset=gb2312\"%>");
        out.println("<%@include file=\"/jsp/common.jsp\"%>");
        out.println("<%");
        out.println("  String tName=\"" + tableName + "\";");
        out.println("%>");
        out.println("<html>");
        out.println("  <head>");
        out.print("    <title>");
        out.print("Information of " + tableName);
        out.println("    </title>");
        out.println("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
        out.print("    <link rel=\"stylesheet\" href=\"../");
        for (int j = 0; j < inSubDir; j++) {
            out.print("../");
        }
        out.println("css/style.css\" type=\"text/css\">");
        if (withMenu) {
            out.print("    <link rel=\"stylesheet\" href=\"../");
            for (int j = 0; j < inSubDir; j++) {
                out.print("../");
            }
            out.println("css/menu.css\" type=\"text/css\">");
        }
        out.println("  </head>");
        out.println("  <body>");
        if (withMenu) {
            out.println("  <menu:showBar/>");
        }
        out.println("    <table class=\"hci\" border=\"0\" cellpadding=\"0\"  cellspacing=\"1\" width=\"100%\">");
        while (i < len) {
            out.println("      <tr>");
            if (twoColumns) {
                writeInfoFieldR(out, fields[i++], twoColumns);
                if (i < len) {
                    writeInfoFieldR(out, fields[i], twoColumns);
                } else {
                    if ((len % 2) != 0) {
                        out.println("        <td class=\"head\" width=\"15%\">&nbsp;</td>");
                        out.println("        <td class=\"content\" width=\"35%\">&nbsp;</td>");
                    }
                }
            } else {
                writeInfoFieldR(out, fields[i], twoColumns);
            }
            out.println("      </tr>");
            i++;
        }
        out.println("    </table>");
        if (withMenu) {
            out.println("  <menu:show/>");
        }
        out.println("  </body>");
        out.println("</html>");
        out.flush();
        out.close();
    }


    public void writeNewJSP(String tableName, String fileName, String action, boolean twoColumns, boolean withMenu, String daoName, int inSubDir)
            throws FileNotFoundException {
        int i = 0;
        int len = 0;
        DBTableDef tab = getTableDef(tableName);
        if (tab == null) {
            return;
        }
        DBColumnDef[] fields = tab.getSortedColumns();
        len = fields.length;

        File f = createFile(fileName);
        PrintStream out = new PrintStream(new FileOutputStream(f));

        out.println("<%@page contentType=\"text/html;charset=gb2312\"%>");
        out.println("<%@include file=\"/jsp/common.jsp\"%>");
        out.println("<%@page import=\"com.dream.bizsdk.common.SysVar\"%>");
        out.println("<%@page import=\"com.dream.bizsdk.common.database.datadict.DataDict\"%>");
        out.println("<%@page import=\"java.util.HashMap\"%>");
        out.println("<%");
        out.println("  int rows=0;");
        out.println("  String[] rowIDs=null;");
        out.println("  String daoName=\"" + daoName + "\";");
        out.println("  String tName=\"" + tableName + "\";");
        out.println("  HashMap dicts=(HashMap)application.getAttribute(SysVar.APP_SDCS);");
        out.println("  dc=(DataDict)dicts.get(daoName);");
        out.println("%>");

        out.println("<html>");
        out.println("  <head>");
        out.print("    <title>");
        out.print("New record of " + tableName);
        out.println("</title>");
        out.println("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
        out.print("    <link rel=\"stylesheet\" href=\"../");
        for (int j = 0; j < inSubDir; j++) {
            out.print("../");
        }
        out.println("css/style.css\" type=\"text/css\">");

        if (withMenu) {
            out.print("    <link rel=\"stylesheet\" href=\"../");
            for (int j = 0; j < inSubDir; j++) {
                out.print("../");
            }
            out.println("css/menu.css\" type=\"text/css\">");
        }

        out.print("    <script src=\"../");
        for (int j = 0; j < inSubDir; j++) {
            out.print("../");
        }
        out.println("js/forms.js\" type=\"text/javascript\"></script>");
        out.println("    <script language=\"javascript\">");
        out.println("    <!--");
        writeJSInitForm(out);
        writeJSCheckForm(out);
        out.println("    //-->");
        out.println("    </script>");
        out.println("  </head>");
        out.println("  <body onload=\"init_form(document.forms(0))\">");
        if (withMenu) {
            out.println("  <menu:showBar/>");
        }
        out.println("  <form name=\"info\" method=\"post\" action=\"" + action + "\" onsubmit=\"return check_form(document.forms(0))\">");
        out.println("    <table class=\"hci\" border=\"0\" cellpadding=\"0\"  cellspacing=\"1\" width=\"100%\">");
        while (i < len) {
            out.println("      <tr>");
            if (twoColumns) {
                writeInfoFieldN(out, fields[i++], twoColumns);
                if (i < len) {
                    writeInfoFieldN(out, fields[i], twoColumns);
                } else {
                    if ((len % 2) != 0) {
                        out.println("        <td class=\"head\" width=\"15%\">&nbsp;</td>");
                        out.println("        <td class=\"content\" width=\"35%\">&nbsp;</td>");
                    }
                }
            } else {
                writeInfoFieldN(out, fields[i], twoColumns);
            }
            out.println("      </tr>");
            i++;
        }
        out.println("    </table>");
        out.println("    <input type=\"submit\" name=\"btnSubmit\" value=\"提交\">");
        out.println("    <input type=\"reset\" name=\"btnReset\" value=\"重置\">");
        out.println("  </form>");
        if (withMenu) {
            out.println("  <menu:show/>");
        }
        out.println("  </body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

    public void writeUpdateJSP(String tableName, String fileName, String action, boolean twoColumns, boolean withMenu, String daoName, int inSubDir)
            throws FileNotFoundException {
        int i = 0;
        int len = 0;
        DBTableDef tab = getTableDef(tableName);
        if (tab == null) {
            return;
        }
        DBColumnDef[] fields = tab.getSortedColumns();
        len = fields.length;

        File f = createFile(fileName);
        PrintStream out = new PrintStream(new FileOutputStream(f));

        out.println("<%@page contentType=\"text/html;charset=gb2312\"%>");
        out.println("<%@include file=\"/jsp/common.jsp\"%>");
        out.println("<%@page import=\"com.dream.bizsdk.common.SysVar\"%>");
        out.println("<%@page import=\"com.dream.bizsdk.common.database.datadict.DataDict\"%>");
        out.println("<%@page import=\"java.util.HashMap\"%>");
        out.println("<%");
        out.println("  int i=0,rows=0;");
        out.println("  String daoName=\"" + daoName + "\";");
        out.println("  String tName=\"" + tableName + "\";");
        out.println("  HashMap dicts=(HashMap)application.getAttribute(SysVar.APP_SDCS);");
        out.println("  dc=(DataDict)dicts.get(daoName);");
        out.println("%>");

        out.println("<html>");
        out.println("  <head>");
        out.print("    <title>");
        out.print("Update record of " + tableName);
        out.println("</title>");
        out.println("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
        out.print("    <link rel=\"stylesheet\" href=\"../");
        for (int j = 0; j < inSubDir; j++) {
            out.print("../");
        }
        out.println("css/style.css\" type=\"text/css\">");
        if (withMenu) {
            out.print("    <link rel=\"stylesheet\" href=\"../");
            for (int j = 0; j < inSubDir; j++) {
                out.print("../");
            }
            out.println("css/menu.css\" type=\"text/css\">");
        }

        out.print("    <script src=\"../");
        for (int j = 0; j < inSubDir; j++) {
            out.print("../");
        }
        out.println("js/forms.js\" type=\"text/javascript\"></script>");
        out.println("    <script language=\"javascript\">");
        out.println("    <!--");
        writeJSInitForm(out);
        writeJSCheckForm(out);
        out.println("    //-->");
        out.println("    </script>");
        out.println("  </head>");
        out.println("  <body onload=\"init_form(document.forms(0))\">");
        if (withMenu) {
            out.println("  <menu:showBar/>");
        }
        out.println("  <form name=\"info\" method=\"post\" action=\"" + action + "\" onsubmit=\"return check_form(document.forms(0))\">");
        out.println("    <table class=\"hci\" border=\"0\" cellpadding=\"0\"  cellspacing=\"1\" width=\"100%\">");
        while (i < len) {
            out.println("      <tr>");
            if (twoColumns) {
                writeInfoFieldU(out, fields[i++], twoColumns);
                if (i < len) {
                    writeInfoFieldU(out, fields[i], twoColumns);
                } else {
                    if ((len % 2) != 0) {
                        out.println("        <td class=\"head\" width=\"15%\">&nbsp;</td>");
                        out.println("        <td class=\"content\" width=\"35%\">&nbsp;</td>");
                    }
                }
            } else {
                writeInfoFieldU(out, fields[i], twoColumns);
            }
            out.println("      </tr>");
            i++;
        }
        out.println("    </table>");
        out.println("    <input type=\"submit\" name=\"btnSubmit\" value=\"提交\">");
        out.println("    <input type=\"reset\" name=\"btnReset\" value=\"重置\">");
        out.println("  </form>");
        if (withMenu) {
            out.println("  <menu:show/>");
        }
        out.println("  </body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

    public void writeListJSP(String tableName, String fileName, String action, boolean selectCB, boolean pagination, boolean search,
                             boolean withMenu, String daoName, int inSubDir)
            throws FileNotFoundException {
        DBTableDef tab = getTableDef(tableName);
        if (tab == null) {
            return;
        }

        File f = createFile(fileName);
        PrintStream out = new PrintStream(new FileOutputStream(f));

        out.println("<%@page contentType=\"text/html;charset=gb2312\"%>");
        out.println("<%@include file=\"/jsp/common.jsp\"%>");

        out.println("<%@page import=\"com.dream.bizsdk.common.SysVar\"%>");
        out.println("<%@page import=\"com.dream.bizsdk.common.database.datadict.DataDict\"%>");
        out.println("<%@page import=\"com.dream.bizsdk.common.databus.BizData\"%>");
        out.println("<%@page import=\"java.util.HashMap\"%>");
        out.println("<%");
        out.println("  int i=0;");
        if (pagination) {
            out.println("  int ipage=0;");
            out.println("  int pages=0;");
            out.println("  int pageSize=10;");
            out.println("  int rowsCount=0;");
            out.println("  int rows=0;");
        }
        out.println("  String[] rowIDs=null;");
        out.println();
        out.println("  String daoName=\"" + daoName + "\";");
        out.println("  String tName=\"" + tableName + "\";");
        out.println("  String rs=\"" + tableName + "s\";");
        out.println();
        out.println("  BizData bdc = (BizData)application.getAttribute(SysVar.APP_BDC);");
        out.println("  HashMap dicts=(HashMap)application.getAttribute(SysVar.APP_SDCS);");
        out.println("  DataDict dc=(DataDict)dicts.get(daoName);");

        if (pagination) {
            out.println("  ipage=rd.getIntAttr(rs,\"pageNO\");");
            out.println("  pages=rd.getIntAttr(rs,\"pagesCount\");");
        }
        out.println("%>");

        out.println("<html>");
        out.println("  <head>");
        out.print("    <title>");
        out.print("Rrecords of " + tableName);
        out.println("</title>");
        out.println("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
        out.print("    <link rel=\"stylesheet\" href=\"../");
        for (int j = 0; j < inSubDir; j++) {
            out.print("../");
        }
        out.println("css/style.css\" type=\"text/css\">");

        if (withMenu) {
            out.print("    <link rel=\"stylesheet\" href=\"../");
            for (int j = 0; j < inSubDir; j++) {
                out.print("../");
            }
            out.println("css/menu.css\" type=\"text/css\">");
        }

        out.print("    <script src=\"../");
        for (int j = 0; j < inSubDir; j++) {
            out.print("../");
        }
        out.println("js/forms.js\" type=\"text/javascript\"></script>");
        out.println("    <script language=\"javascript\">");
        out.println("    <!--");
        writeJSInitForm(out);
        out.println();
        writeJSCheckForm(out);
        out.println();
        writeJSDelete(out);
        out.println();
        writeJSUpdate(out);
        out.println();
        if (pagination) {
            writeJSQueryPage(out);
            out.println();
            writeJSGoto(out);
            out.println();
        }
        out.println("    //-->");
        out.println("    </script>");
        out.println("  </head>");
        out.println("  <body>");
        if (withMenu) {
            out.println("  <menu:showBar/>");
        }
        writeListForm(out, action, pagination, selectCB, search, tab);
        if (withMenu) {
            out.println("  <menu:show/>");
        }
        out.println("  </body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

    /**
     * @param out
     * @param tab
     */
    public void writeSearchFields(PrintStream out, DBTableDef tab) {
        int i = 0;
        int len = 0;
        DBColumnDef[] fields = tab.getSortedColumns();
        len = fields.length;
        out.println("      <table class=\"hci\" border=\"0\" cellpadding=\"0\" width=\"100%\">");
        out.println("        <tr>");
        while (i < len) {
            if (fields[i].isSearch()) {
                writeSearchField(out, fields[i]);
            }
            i++;
        }
        out.println("        <td align=\"left\"><input type=\"button\" name=\"btnQuery\" value=\"查询\" class=\"button\"></td>");
        out.println("        </tr>");
        out.println("      </table>");
    }

    /**
     * @param out
     * @param fld
     */
    public void writeSearchField(PrintStream out, DBColumnDef fld) {
        int dispType = JSPGen.TEXT;
        String dType = fld.getDispType();
        //int vType=fld.getType();
        int len = fld.getLength();

        String tableName = fld.getTableName();
        String fieldName = fld.getName();
        //String className=dc.getCSSClass(tableName,fld.getName());

        //boolean isNullable = fld.isNullable();
        if (dType != null) {
            if (dType.compareTo("T") == 0) {
                dispType = JSPGen.TEXT;
            } else if (dType.compareTo("S") == 0) {
                dispType = JSPGen.SELECT;
            } else if (dType.compareTo("R") == 0) {
                dispType = JSPGen.RADIO;
            } else if (dType.compareTo("C") == 0) {
                dispType = JSPGen.CHECKBOX;
            }
        }

        out.print("        <td>");
        out.print(fld.getDisplayName() == null ? fld.getName() : fld.getDisplayName());
        switch (dispType) {
            case JSPGen.TEXT:
                //update here;
                out.print("<input name=\"<%=tName%>/" + fld.getName() + "\" type=\"text\" value=\"<%=rd.getString(tName,\"" + fld.getName() + "\",0)%>\" size=\"");
                if (len < size) {
                    if (len >= 4) {
                        out.print(new Integer(len).toString());
                    } else {
                        out.print(new Integer(4).toString());
                    }
                } else {
                    out.print(new Integer(size).toString());
                }
                out.print(">");
                break;
            case JSPGen.RADIO:
                out.println("<input type=\"radio\" name=\"<%=tName%>/" + fieldName + "\" value=\"\" >不指定");
                out.println("         <% rows=bdc.getTableRowsCount(\"" + tableName + "/" + fieldName + "\");");
                out.println("         String " + fieldName + "=rd.getString(tName,\" " + fieldName + "\",0);");
                out.println("         rowIDs=bdc.getRowIDs(\"" + tableName + "/" + fieldName + "\");");
                out.println("         for(i=0;i<rows;i++){%>");
                out.print("           <input type=\"radio\" name=\"<%=tName%>/" + fieldName + "\" value=\"<%=rowIDs[i]%>\" <%=rowIDs[i].compareTo(" + fieldName + ")==0?\"checked\":\"\")%> >");
                out.println("<%=bdc.getString(tName\"" + fieldName + "\",\"" + fieldName + "\",rowIDs[i])%>");
                out.print("         <%}%>");
                break;
            case JSPGen.CHECKBOX:
                out.println("<input type=\"checkbox\" name=\"<%=tName%>/" + fieldName + "\" value=\"\" >不指定");
                out.println("         <% rows=bdc.getTableRowsCount(\"" + tableName + "/" + fieldName + "\");");
                out.println("         String " + fieldName + "=rd.getString(tName,\" " + fieldName + "\",0);");
                out.println("         rowIDs=bdc.getRowIDs(\"" + tableName + "/" + fieldName + "\");");
                out.println("         for(i=0;i<rows;i++){%>");
                out.print("           <input type=\"checkbox\" name=\"<%=tName%>/" + fieldName + "\" value=\"<%=rowIDs[i]%>\" <%=rowIDs[i].compareTo(" + fieldName + ")==0?\"checked\":\"\")%> >");
                out.println("<%=bdc.getString(tName\"" + fieldName + "\",\"" + fieldName + "\",rowIDs[i])%>");
                out.print("         <%}%>");
                break;
            case JSPGen.SELECT:
                String refTable = fld.getRefTableName();
                String refCol = fld.getRefCode();
                String source = fld.getSource();
                if (source == null) {

                } else if (source.compareToIgnoreCase("FK") == 0) {
                    out.println("<select name=\"<%=tName%>/" + fieldName + "\" value=\"<%=rd.get(tName,\"" + fieldName + "\",0)%>\">");
                    out.println("          <option value=\"\" <%=rd.getStringByDI(tName,\"" + fieldName + "\",0).compareTo(\"\")==0?\"selected\":\"\"%> >请选择...</option>");
                    out.println("          <%rows=rd.getTableRowsCount(\"" + refTable + "s\");");
                    out.println("            String " + fieldName + "=rd.getString(tName,\" " + fieldName + "\",0);");
                    out.println("            for(i=0;i<rows;i++){%>");
                    out.println("              <option value=\"<%=rd.getStringByDI(\"" + refTable + "s\",i,\"" + refCol + "\")%>\" <%=rd.getStringByDI(\"" + refTable + "s\",i,\"" + refCol + "\").compareTo(" + fieldName + ")==0?\"selected\":\"\"%> ><%=rd.getStringByDI(\"" + refTable + "s\",index,\"******\")%></option>");
                    out.println("          <%}%>");
                    out.print("        </select>");
                } else if (source.compareToIgnoreCase("busi") == 0) {
                    out.println("<select name=\"<%=tName%>/" + fieldName + "\" value=\"<%=rd.getString(tName,\"" + fieldName + "\",0)%>\">");
                    out.println("          <option value=\"\" <%=rd.getStringByDI(tName,\"" + fieldName + "\",0).compareTo(\"\")==0?\"selected\":\"\"%> >请选择...</option>");
                    out.println("         <% rows=bdc.getTableRowsCount(\"" + tableName + "/" + fieldName + "\");");
                    out.println("         String " + fieldName + "=rd.getString(tName,\" " + fieldName + "\",0);");
                    out.println("         rowIDs=bdc.getRowIDs(\"" + tableName + "/" + fieldName + "\");");
                    out.println("         for(i=0;i<rows;i++){%>");
                    out.print("           <option  value=\"<%=rowIDs[i]%>\" <%=bdc.getString(tName,\"" + fieldName + "\",rowIDs[i]).compareTo(" + fieldName + ")==0?\"selected\":\"\"%> >");
                    out.println("<%=bdc.getString(tName,\"" + fieldName + "\",rowIDs[i])%></option>");
                    out.println("         <%}%>");
                    out.print("        </select>");
                }
                break;
            default:
                break;
        }
        out.println("</td>");
    }


    public void writeListForm(PrintStream out, String action, boolean page, boolean cbxSelect, boolean search, DBTableDef tab) {
        int i = 0;
        int len = 0;
        DBColumnDef[] fields = tab.getSortedColumns();
        len = fields.length;
        String pk = tab.getPKName();

        out.println("    <form name=\"list\" action=\"" + action + "\" onsubmit=\"return check_form(document.forms(0))\" method=\"post\">");
        if (page) {
            out.println("      <input type=\"hidden\" name=\"<%=tName%>/@pageNO\" value=\"<%=ipage%>\">");
        }
        if (search) {

            writeSearchFields(out, tab);
        }
        if (page) {
            out.println("      <div id=\"pages\" align=\"right\">");
            out.println("        <font size=\"2\">");
            out.println("        页:<%=ipage%>/<%=pages%>,");
            out.println("        <A href=\"javascript:queryPage(1);\">首页</A>&nbsp;<A href=\"javascript:queryPage(<%=(ipage-1)<1?1:(ipage-1)%>);\">上一页</A>&nbsp;");
            out.println("        <A href=\"javascript:queryPage(<%=(ipage>=pages)?pages:(ipage+1)%>);\">下一页</A>&nbsp;");
            out.println("        <A href=\"javascript:queryPage(<%=pages%>);\">末页</A>&nbsp;");
            out.println("        转到第<input type=\"text\" name=\"gotoPage\" size=\"4\"  value=\"\">页<input type=\"button\" name=\"btnGoto\" value=\"转到\" onclick=\"javascript:gotoPage();\">");
            out.println("        </font>");
            out.println("      </div>");
        }

        out.println("      <table class=\"hci\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" width=\"100%\">");
        out.println("        <tr class=\"head\">");
        if (cbxSelect) {
            out.println("          <td><input type=\"checkbox\" name=\"cbxSelectAll\" value=\"0\" onclick=\"javascript:ClickOnCheckBox(document.forms(0).elements('cbxSelectAll'),document.forms(0));\">全选</td>");
        }
        while (i < len) {
            out.print("          <td>");
            out.print(fields[i].getDisplayName());
            out.println("</td>");
            i++;
        }
        out.println("        </tr>");
        i = 0;
        out.println("       <% rows=rd.getTableRowsCount(rs);");
        out.println("        for(int index=0;index<rows;index++){%>");
        out.println("        <tr class=\"content\">");
        if (cbxSelect) {
            out.println("          <td><input type=\"checkbox\" name=\"<%=tName%>/" + pk + "[index]\" value=\"<%=rd.getStringByDI(tName,\"" + pk + "\",index)%>\" ></td>");
        }
        while (i < len) {
            String source = fields[i].getSource();
            out.print("          <td>");
            if (source == null || source.compareTo("Y") == 0) {
                out.print("<%=rd.getStringByDI(rs,\"" + fields[i].getName() + "\",index)%>");
            } else if (source.compareTo("B") == 0) {
                out.print("<%=bdc.getString(rs,\"" + fields[i].getName() + "\",rd.getStringByDI(rs,\"" + fields[i].getName() + "\",index))%>");
            } else {
                out.print("<%=rd.getStringByDI(rs,\"" + fields[i].getName() + "\",index)%>");
            }
            out.println("</td>");
            i++;
        }
        out.println("        </tr>");
        out.println("        <%}%>");
        out.println("      </table>");
        if (page) {
            out.println("      <div id=\"pages\" align=\"right\">");
            out.println("        <font size=\"2\">");
            out.println("        页:<%=ipage%>/<%=pages%>,");
            out.println("        <A href=\"javascript:queryPage(1);\">首页</A>&nbsp;<A href=\"javascript:queryPage(<%=(ipage-1)<1?1:(ipage-1)%>);\">上一页</A>&nbsp;");
            out.println("        <A href=\"javascript:queryPage(<%=(ipage>=pages)?pages:(ipage+1)%>);\">下一页</A>&nbsp;");
            out.println("        <A href=\"javascript:queryPage(<%=pages%>);\">末页</A>&nbsp;");
            out.println("        转到第<input type=\"text\" name=\"gotoPage\" size=\"4\"  value=\"\">页<input type=\"button\" name=\"btnGoto\" value=\"转到\" onclick=\"javascript:gotoPage();\">");
            out.println("        </font>");
            out.println("      </div>");
        }
        out.println("      <input type=\"button\" class=\"button\" name=\"btnDelete\" value=\"Delete\" onclick=\"javascript:deleteRows()\">");
        out.println("      <input type=\"button\" class=\"button\" name=\"btnUpdate\" value=\"Update\" onclick=\"javascript:update()\">");
        out.println("    </form>");
    }


    /**
     * write the QueryPage function.
     */
    public void writeJSQueryPage(PrintStream out) {
        out.println("        function queryPage(page){");
        out.println("          document.forms(0).elements(\"<%=tName%>/@pageNO\").value=page;");
        out.println("          document.forms(0).submit();");
        out.println("        }");
    }

    /**
     * write CheckForm function here;
     */
    public void writeJSCheckForm(PrintStream out) {
        out.println("        function check_form(frm){");
        out.println("          return validate(frm);");
        out.println("        }");
    }

    public void writeJSGoto(PrintStream out) {
        out.println("        function gotoPage(){");
        out.println("          var pno=document.forms(0).elements(\"gotoPage\").value;");
        out.println("          if(pno==\"\" || !IsNumber(pno)){");
        out.println("             alert(\"对不起，你需要输入一个整数页码！\");");
        out.println("          }else{");
        out.println("             queryPage(pno);");
        out.println("          }");
        out.println("        }");
    }

    public void writeJSDelete(PrintStream out) {
        out.println("        function deleteRows(){");
        out.println("          var selectedCount=countOfSelectedCheckBox(document.forms(0));");
        out.println("          if(selectedCount<1){");
        out.println("             alert(\"对不起，您需要选择一行记录才能执行删除操作！\");");
        out.println("           }else{");
        out.println("              document.forms(0).action=\"\";");
        out.println("              document.forms(0).submit();");
        out.println("           }");
        out.println("        }");
    }

    public void writeJSUpdate(PrintStream out) {
        out.println("        function update(){");
        out.println("          var selectedCount=countOfSelectedCheckBox(document.forms(0));");
        out.println("          if(selectedCount!=1){");
        out.println("             alert(\"对不起，您需要选择并且只能选一行记录进行更新！\");");
        out.println("           }else{");
        out.println("              var selected=getSelectValue(document.forms(0));");
        out.println("              document.forms(0).action=\"******\";");
        out.println("              document.forms(0).submit();");
        out.println("           }");
        out.println("        }");
    }


    /**
     * write InitForm function here;
     */
    public void writeJSInitForm(PrintStream out) {
        out.println("        function init_form(frm){");
        out.println("            init(frm);");
        out.println("        }");
    }

    public DBTableDef getTableDef(String tableName) {
        return dc.getTableDef(tableName);
    }
}