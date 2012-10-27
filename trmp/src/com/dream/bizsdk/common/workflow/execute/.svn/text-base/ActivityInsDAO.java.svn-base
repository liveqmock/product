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
 * Time: 9:09:33
 */
public class ActivityInsDAO {
    public static final String TABLE = "WFActivityIns";

    private DAO dao;

    public ActivityInsDAO() {

    }

    public ActivityInsDAO(DAO dao) {
        this.dao = dao;
    }

    public DAO getDAO() {
        return dao;
    }

    public void setDAO(DAO dao) {
        this.dao = dao;
    }

    /**
     * @param ai
     * @return
     */
    public int insert(ActivityIns ai) {
        BizData d = Util.loadFromBean(ActivityInsDAO.TABLE, ai);
        try {
            return dao.insert(d);
        } catch (SQLException sqle) {
            return SysError.DB_ERROR;
        }
    }

    /**
     * @param ai
     * @return
     */
    public int delete(ActivityIns ai) {
        long id = ai.getID();
        StringBuffer sb = new StringBuffer();
        sb.append("delete from ").append(ActivityInsDAO.TABLE);
        sb.append(" where aiID=").append(id);
        try {
            return dao.executeUpdate(new String(sb));
        } catch (SQLException sqle) {
            return SysError.DB_ERROR;
        }
    }

    /**
     * @param ai
     * @return
     */
    public int update(ActivityIns ai) {
        BizData d = Util.loadFromBean(ProcessInsDAO.TABLE, ai);
        d.addAttr(ActivityInsDAO.TABLE, "aiID", "oldValue", ai.getID());
        try {
            return dao.update(d);
        } catch (SQLException sqle) {
            return SysError.DB_ERROR;
        }
    }

    /**
     * @param activityID
     * @return
     */
    public ActivityIns getActivityIns(long activityID) {
        BizData d = new BizData();
        d.add(ActivityInsDAO.TABLE, "aiID", new Long(activityID));
        try {
            int rows = dao.expand(ProcessInsDAO.TABLE, d);
            if (rows != 1) {
                return null;
            } else {
                return (ActivityIns) Util.saveToBean(ActivityInsDAO.TABLE, d, ActivityIns.class);
            }
        } catch (SQLException sqle) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ActivityIns", sqle);
        } catch (IllegalAccessException iae) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ActivityIns", iae);
        } catch (InstantiationException ie) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ActivityIns", ie);
        }
        return null;

    }

    /**
     * @param where
     * @return
     */
    public List query(String where) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from ").append(ActivityInsDAO.TABLE);
        if (where != null && where.length() > 0) {
            sb.append(" where ").append(where);
        }

        BizData d = new BizData();

        try {
            dao.executeQuery(new String(sb), ActivityInsDAO.TABLE, d);
            return Util.saveToBeans(ActivityInsDAO.TABLE, d, ActivityIns.class);
        } catch (SQLException sqle) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ActivityIns", sqle);
        } catch (IllegalAccessException iae) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ActivityIns", iae);
        } catch (InstantiationException ie) {
            Logger log = Logger.getLogger(this.getClass());
            log.error("can't get ActivityIns", ie);
        }
        return null;
    }
}
