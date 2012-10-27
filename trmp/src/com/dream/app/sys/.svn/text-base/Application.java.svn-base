package com.dream.app.sys;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.util.script.ScriptEngine;
import com.dream.bizsdk.common.database.datadict.DataDictTool;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.blc.AbstractBLC;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.web.BLServlet;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 2004-5-5
 * Time: 15:57:36
 * To change this template use File | Settings | File Templates.
 */
public class Application extends AbstractBLC {

    /**
     * install the current application, that is run all the script files;
     * and create the data dictionary.
     *
     * @param rd
     * @param sd
     * @return
     */
    public int install(BizData rd, BizData sd) throws SQLException {
        int retVal = 0;
        int count = 0;
        Connection conn = null;
        ScriptEngine se = (ScriptEngine) _context.getBLCsPool().getPoolItem("com.dream.bizsdk.common.util.script.ScriptEngine");
        //failed to run the scripts file;
        if ((retVal = se.exec(rd, sd)) < 0) {
            return retVal;
        }
        DataDictTool ddt = new DataDictTool();
        DAO[] daos = _context.getAllDAOs();
        count = daos.length;
        try {
            for (int i = 0; i < count; i++) {
                conn = daos[i].getConnection();
                ddt.createDict(conn);
                conn.close();
                conn = null;
            }
            BLServlet bls = (BLServlet) (((HttpServletRequest) rd.get(SysVar.HTTP_REQ)).getSession().getServletContext().getAttribute(SysVar.APP_BLSERVLET));
            bls.initContext();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return SysError.DB_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return SysError.UNKNOWN_ERROR;
        } finally {
            //free the db link;
            if (conn != null) {
                conn.close();
            }
        }
        return 0;
    }
}