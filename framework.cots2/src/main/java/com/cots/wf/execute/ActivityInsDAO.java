/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.execute;

import com.cots.dao.DAO;

import java.sql.SQLException;
import java.util.List;

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
        try {
            return dao.insert(ai);
        } catch (SQLException sqle) {
            return -1;
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
            return -1;
        }
    }

    /**
     * @param ai
     * @return
     */
    public int update(ActivityIns ai) {
        try {
            return dao.update(ai);
        } catch (SQLException sqle) {
            return -1;
        }
    }

    /**
     * @param activityID
     * @return
     */
    public ActivityIns getActivityIns(long activityID) {
        return null;
    }

    /**
     * @param where
     * @return
     */
    public List query(String where) {
        return null;
    }
}
