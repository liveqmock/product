/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.execute;

import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.databus.Util;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysError;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-2
 * Time: 9:09:25
 */
public class ProcessInsDAO {
    public final static String TABLE = "WFProcessIns";

    private DAO dao;

    public ProcessInsDAO(DAO dao) {
        this.dao = dao;
    }

    public ProcessInsDAO() {

    }

    public DAO getDAO() {
        return this.dao;
    }

    public void setDAO(DAO dao) {
        this.dao = dao;
    }

    /**
     * @param pi
     * @return
     */
    public int insert(ProcessIns pi) {
        BizData d = Util.loadFromBean(ProcessInsDAO.TABLE, pi);
        try {
            return dao.insert(d);
        } catch (SQLException sqle) {
            return SysError.DB_ERROR;
        }
    }

    /**
     * @param pi
     * @return
     */
    public int delete(ProcessIns pi) {
        long id = pi.getID();
        StringBuffer sb = new StringBuffer();
        sb.append("delete from ").append(ProcessInsDAO.TABLE);
        sb.append(" where piID=").append(id);
        try {
            return dao.executeUpdate(new String(sb));
        } catch (SQLException sqle) {
            return SysError.DB_ERROR;
        }
    }

    /**
     * @param pi
     * @return
     */
    public int update(ProcessIns pi) {
        BizData d = Util.loadFromBean(ProcessInsDAO.TABLE, pi);
        d.addAttr(ProcessInsDAO.TABLE, "piID", "oldValue", pi.getID());
        try {
            return dao.update(d);
        } catch (SQLException sqle) {
            return SysError.DB_ERROR;
        }
    }

    /**
     * @param piID
     * @return
     */
    public ProcessIns getProcessIns(long piID) {
        BizData d = new BizData();
        d.add(ProcessInsDAO.TABLE, "piID", new Long(piID));
        try {
            int rows = dao.expand(ProcessInsDAO.TABLE, d);
            if (rows != 1) {
                return null;
            } else {
                return (ProcessIns) Util.saveToBean(ProcessInsDAO.TABLE, d, ProcessIns.class);
            }
        } catch (SQLException sqle) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ProcessIns", sqle);
        } catch (IllegalAccessException iae) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ProcessIns", iae);
        } catch (InstantiationException ie) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ProcessIns", ie);
        }
        return null;
    }

    /**
     * @param where
     * @return
     */
    public List queryProcessIns(String where) {

        StringBuffer sb = new StringBuffer();
        sb.append("select * from ").append(ProcessInsDAO.TABLE);
        if (where != null && where.length() > 0) {
            sb.append(" where ").append(where);
        }

        BizData d = new BizData();

        try {
            dao.executeQuery(new String(sb), ProcessInsDAO.TABLE, d);
            return Util.saveToBeans(ProcessInsDAO.TABLE, d, ProcessIns.class);
        } catch (SQLException sqle) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ProcessIns", sqle);
        } catch (IllegalAccessException iae) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ProcessIns", iae);
        } catch (InstantiationException ie) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ProcessIns", ie);
        }
        return null;
    }
}
