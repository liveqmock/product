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
        try {
            return dao.insert(pi);
        } catch (SQLException sqle) {
            return -1;
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
            return -1;
        }
    }

    /**
     * @param pi
     * @return
     */
    public int update(ProcessIns pi) {
        try {
            return dao.update(pi);
        } catch (SQLException sqle) {
            return -1;
        }
    }

    /**
     * @param piID
     * @return
     */
    public ProcessIns getProcessIns(long piID) {
        return null;
    }

    /**
     * @param where
     * @return
     */
    public List queryProcessIns(String where) {
        return null;
    }
}