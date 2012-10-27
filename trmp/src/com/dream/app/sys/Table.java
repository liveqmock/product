/**
 *all rights reserved,@copyright 2003
 */
package com.dream.app.sys;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.util.JSPGen;
import com.dream.bizsdk.common.util.xml.XMLFile;
import com.dream.bizsdk.common.util.string.StringUtil;
import com.dream.bizsdk.common.util.script.ScriptBuilder;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.database.datadict.DBColumnDef;
import com.dream.bizsdk.common.database.datadict.DataDictTool;
import com.dream.bizsdk.common.database.datadict.DataDict;
import com.dream.bizsdk.common.database.sql.ISQLBuilder;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-7
 * Time: 9:46:32
 */
public class Table extends DBBLC {
    protected String columns = "DRMColumn";

    public Table() {
        entityName = "DRMTABLE";
    }

    /**
     * @param data
     * @return
     */
    public int queryTable(BizData data) throws SQLException {
        String daoName = (String) data.get("daoName");
        DAO dao = _context.getDAO(daoName);
        if (dao == null) {
            return SysError.NO_THIS_DAO;
        }

        String tableName = (String) data.get("tableName");
        if (tableName == null) {
            return SysError.BL_PARAM_ERROR;
        }
        //data.clear();
        data.add(entityName, "tableName", tableName);
        data.add(columns, "tableName", tableName);

        return dao.select(data);
    }

    /**
     * @param data
     * @return
     */
    public int updateTabUsrInfo(BizData data) throws SQLException {
        int order = 0;
        boolean pk = false;
        boolean cs = false;
        String tableName = null;

        String daoName = (String) data.get("daoName");
        DAO dao = _context.getDAO(daoName);
        if (dao == null) {
            return SysError.NO_THIS_DAO;
        }

        Connection conn = dao.getConnection();
        try {
            tableName = (String) data.get("DRMTable", "tableName", "0");
            data.addAttr("DRMTable", "tableName", "0", "oldValue", tableName);
            dao.update("DRMTable", data, conn);
            int columns = data.getTableRowsCount("DRMColumn");
            for (int i = 0; i < columns; i++) {
                String dispName = (String) data.get("DRMColumn", "displayName", Integer.toString(i));
                String dispType = (String) data.get("DRMColumn", "displayType", Integer.toString(i));
                String dispSeq = (String) data.get("DRMColumn", "displaySeq", Integer.toString(i));
                String isPK = (String) data.get("DRMColumn", "isPK", Integer.toString(i));
                String datasource = (String) data.get("DRMColumn", "datasource", Integer.toString(i));
                String refTab = (String) data.get("DRMColumn", "refTabName", Integer.toString(i));
                String refCol = (String) data.get("DRMColumn", "refColName", Integer.toString(i));
                String colName = (String) data.get("DRMColumn", "colName", Integer.toString(i));
                String canSearch = (String) data.get("DRMColumn", "canSearch", Integer.toString(i));

                try {
                    order = Integer.valueOf(dispSeq).intValue();
                } catch (Exception e) {
                    order = -1;
                }
                if (isPK != null && isPK.compareToIgnoreCase("Y") == 0) {
                    pk = true;
                } else {
                    pk = false;
                }
                if (canSearch != null && canSearch.compareToIgnoreCase("Y") == 0) {
                    cs = true;
                } else {
                    cs = false;
                }
                DataDictTool ddt = new DataDictTool();
                ddt.updateColUserInfo(tableName, colName, dispName, dispType, order, pk, datasource, refTab, refCol, cs, conn);
                dao.loadDict();
            }
        } catch (SQLException e) {
            return SysError.DB_ERROR;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return 0;
    }

    public int getDAONames(BizData data) {
        DAO[] daos = _context.getAllDAOs();
        for (int i = 0; i < daos.length; i++) {
            data.add("daoNames", i, daos[i].getDAODef().getName());
        }
        return 0;
    }

    public int queryAll(BizData data) throws SQLException {
        String daoName = (String) data.get("daoName");
        DAO dao = _context.getDAO(daoName);
        if (dao == null) {
            return SysError.NO_THIS_DAO;
        }
        DBColumnDef[] cds = coreDAO.getDataDict().getTableDef(entityName).getColumns();
        BizData d1 = new BizData();
        BizData d2 = new BizData();
        d1.add(entityName, cds[0].getName(), 0, (String) null);
        int rows = dao.select(d2, d1, true);
        if (rows >= 1) {
            data.copyEntity(d2, entityName + "s");
        }
        return rows;
    }

    public int createDict(BizData data) {
        String daoName = (String) data.get("daoName");
        DAO dao = _context.getDAO(daoName);
        if (dao == null) {
            return SysError.NO_THIS_DAO;
        }

        Connection conn = dao.getConnection();
        try {
            DataDictTool ddt = new DataDictTool();
            ddt.createDict(conn);
        } catch (SQLException sqle) {
        	sqle.printStackTrace();
            return SysError.DB_ERROR;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqle) {

                }
            }
        }
        /*try {
            dao.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return 0;
    }

    public int updateDict(BizData data) {
        String daoName = (String) data.get("daoName");
        DAO dao = _context.getDAO(daoName);
        if (dao == null) {
            return SysError.NO_THIS_DAO;
        }

        Connection conn = dao.getConnection();
        try {
            DataDictTool ddt = new DataDictTool();
            ddt.updateDict(conn);
        } catch (SQLException sqle) {
            return SysError.DB_ERROR;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqle) {

                }
            }
        }
        return 0;
    }

    public int deleteTableFromDict(BizData data) {
        int len = 0;
        String daoName = (String) data.get("daoName");
        DAO dao = _context.getDAO(daoName);
        if (dao == null) {
            return SysError.NO_THIS_DAO;
        }

        len = data.getArrayLength("tableName");

        Connection conn = dao.getConnection();
        try {
            DataDictTool ddt = new DataDictTool();
            for (int i = 0; i < len; i++) {
                String tableName = (String) data.get("tableName", i);
                ddt.deleteTableFromDict(tableName, conn);
            }
        } catch (SQLException sqle) {
            return SysError.DB_ERROR;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqle) {

                }
            }
        }
        return 0;
    }

    public int makeJSP(BizData data) {
        int inSubDir = 0;
        int index = 0;
        String path = null;
        String jspRoot = (String) data.get("jspRoot");
        String tableName = (String) data.get("tableName");
        String daoName = (String) data.get("daoName");

        if (jspRoot == null || tableName == null || daoName == null) {
            return SysError.BL_PARAM_ERROR;
        }
        HttpServletRequest req = (HttpServletRequest) data.get("_httpservletrequest");
        String rootPath = req.getSession().getServletContext().getRealPath(jspRoot);
        if (rootPath == null) {
            return SysError.BL_PARAM_ERROR;
        }

        String saveDir = (String) data.get("save_dir");
        if (saveDir == null) {
            path = rootPath;
        } else {
            if (saveDir.charAt(0) == '/') {
                saveDir = saveDir.substring(1);
            }
            if (saveDir.charAt(saveDir.length() - 1) == '/') {
                saveDir = saveDir.substring(0, saveDir.length() - 1);
            }
            inSubDir = 1;
            while ((index = saveDir.indexOf('/', index)) >= 0) {
                index++;
                inSubDir++;
            }
            if (rootPath.charAt(rootPath.length() - 1) == File.separatorChar) {
                path = rootPath + saveDir;
            } else {
                path = rootPath + File.separatorChar + saveDir;
            }
            File f = new File(path);
            if (!f.exists() || f.isFile()) {
                f.mkdirs();
            }

        }

        DataDict dc = _context.getDAO(daoName).getDataDict();
        JSPGen jg = new JSPGen(path, dc);

        String rdJSP = (String) data.get("rd_jsp");

        String newJSP = (String) data.get("new_jsp");
        String updateJSP = (String) data.get("update_jsp");
        String listJSP = (String) data.get("list_jsp");
        String newAction = (String) data.get("newAction");
        String updateAction = (String) data.get("updateAction");
        String listAction = (String) data.get("listAction");
        try {
            if (rdJSP != null) {
                makeRdJSP(jg, tableName, inSubDir, data);
            }
            if (newJSP != null) {
                makeNewJSP(jg, daoName, tableName, newAction, inSubDir, data);
            }
            if (updateJSP != null) {
                makeUpdateJSP(jg, daoName, tableName, updateAction, inSubDir, data);
            }

            if (listJSP != null) {
                makeListJSP(jg, daoName, tableName, listAction, inSubDir, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SysError.UNKNOWN_ERROR;
        }
        return 0;
    }

    protected void makeRdJSP(JSPGen jg, String tableName, int inSubDir, BizData data) throws FileNotFoundException {
        String twoColumns = (String) data.get("rd_jsp_multi_col");
        String menu = (String) data.get("rd_jsp_menu");
        String rdJSPName = (String) data.get("rd_jsp_name");
        jg.writeReadJSP(tableName, rdJSPName, twoColumns != null ? true : false, menu != null ? true : false, inSubDir);
    }

    protected void makeNewJSP(JSPGen jg, String daoName, String tableName, String newAction, int inSubDir, BizData data) throws FileNotFoundException {
        String twoColumns = (String) data.get("new_jsp_multi_col");
        String menu = (String) data.get("new_jsp_menu");
        String rdJSPName = (String) data.get("new_jsp_name");
        jg.writeNewJSP(tableName, rdJSPName, newAction, twoColumns != null ? true : false, menu != null ? true : false, daoName, inSubDir);
    }

    protected void makeUpdateJSP(JSPGen jg, String daoName, String tableName, String updateAction, int inSubDir, BizData data) throws FileNotFoundException {
        String twoColumns = (String) data.get("update_jsp_multi_col");
        String menu = (String) data.get("update_jsp_menu");
        String rdJSPName = (String) data.get("update_jsp_name");
        jg.writeUpdateJSP(tableName, rdJSPName, updateAction, twoColumns != null ? true : false, menu != null ? true : false, daoName, inSubDir);
    }

    protected void makeListJSP(JSPGen jg, String daoName, String tableName, String listAction, int inSubDir, BizData data) throws FileNotFoundException {
        String selectCB = (String) data.get("selectCB");
        String pagination = (String) data.get("pagination");
        String rdJSPName = (String) data.get("list_jsp_name");
        String search = (String) data.get("search");
        String menu = (String) data.get("list_jsp_menu");
        jg.writeListJSP(tableName, rdJSPName, listAction, selectCB != null ? true : false, pagination != null ? true : false, search != null ? true : false, menu != null ? true : false, daoName, inSubDir);
    }

    public int getAllTablesInDAO(BizData rd, BizData sd) {
        String daoName = (String) rd.get("daoName");
        if (daoName == null) {
            return SysError.BL_PARAM_ERROR;
        }
        DAO dao = _context.getDAO(daoName);
        if (dao == null) {
            return SysError.UNKNOWN_ERROR;
        }
        Connection conn = dao.getConnection();
        try {
            DataDictTool ddt = new DataDictTool();
            Vector tables = ddt.queryTablesInDB(conn);
            rd.remove("dbTables");
            int count = tables.size();
            for (int i = 0; i < count; i++) {
                rd.add("dbTables", "tableName", i, (String) tables.get(i));
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqle) {

                }
            }
        }
        return 0;
    }


    /**
     * generate init data script here for one or more tables here;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int makeTableDataScript(BizData rd, BizData sd) {
        int count = 0;
        int retVal = 0;
        String dbType = null;

        String daoName = (String) rd.get("daoName");
        ArrayList al = (ArrayList) rd.get("tableName");
        if (daoName == null || al == null) {
            return SysError.BL_PARAM_ERROR;
        }
        count = al.size();

        HttpServletRequest req = (HttpServletRequest) rd.get(SysVar.HTTP_REQ);
        if (req == null) {
            return SysError.NO_ACCESS;
        }

        String path = req.getSession().getServletContext().getRealPath("/WEB-INF/sqlscripts/");
        path = StringUtil.checkPath(path);

        XMLFile xml = null;
        String xmlFile = path + "init_dao_" + daoName + ".xml";
        try {
            //note we always CREATE the xml files to ensure that the script order is correct;
            xml = XMLFile.newXMLFile(xmlFile, "scripts", "GB2312");
        } catch (Exception e) {
            e.printStackTrace();
            return SysError.UNKNOWN_ERROR;
        }

        ScriptBuilder sb = new ScriptBuilder();

        try {
            for (int i = 0; i < count; i++) {
                String table = (String) al.get(i);
                DAO dao = _context.getDAO(daoName);
                //here we just generate sql server script,we need to generate other types script too in the
                //future.
                dbType = ISQLBuilder.SQL;
                sb.setTargetDbType(dbType);
                String fileName = "init_data_" + table + ".sql";
                sb.buildScript(path + dbType + File.separatorChar + fileName,
                        table, dao);
                registerScript(xml, dbType, fileName);
            }
            xml.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return SysError.DB_ERROR;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return SysError.UNKNOWN_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return SysError.UNKNOWN_ERROR;
        }
        return retVal;
    }

    /**
     * register a script file to the desc xml file;
     *
     * @param xml
     * @param dbType
     * @param fileName
     */
    protected void registerScript(XMLFile xml, String dbType, String fileName) {
        String xpath = null;
        Element e = null;
        xpath = "/scripts/group[@type=\"" + dbType + "\"]";
        if ((e = (Element) xml.selectSingleNode(xpath)) == null) {
            xml.setCurrentNode(xml.getDocument().getDocumentElement());
            e = xml.appendChild("group");
            e.setAttribute("type", dbType);
            xml.setCurrentNode(e);
            e = xml.appendChild("script");
            e.setAttribute("name", fileName);
        } else {
            Element p = e;
            e = (Element) xml.selectSingleNode(e, "script[@name=\"" + fileName + "\"]");
            if (e == null) {
                xml.setCurrentNode(p);
                e = xml.appendChild("script");
                e.setAttribute("name", fileName);
            }
        }
    }
}