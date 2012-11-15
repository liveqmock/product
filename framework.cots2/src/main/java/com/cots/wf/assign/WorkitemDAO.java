/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.assign;

import com.cots.dao.DAO;

import java.sql.SQLException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-1
 * Time: 12:46:23
 */
public class WorkitemDAO {

    private DAO dao;

    public WorkitemDAO() {

    }

    public WorkitemDAO(DAO dao) {
        this.dao = dao;
    }

    /**
     * set the dao object usesd by this sub dao;
     *
     * @param dao
     */
    public void setDAO(DAO dao) {
        this.dao = dao;
    }

    /**
     * get the dao object used by this object;
     *
     * @return
     */
    public DAO getDAO() {
        return this.dao;
    }

    /**
     * insert a new Workitem;
     *
     * @param item
     */
    public void insertWorkitem(Workitem item) throws SQLException {
        dao.insert(item);
    }

    /**
     * delete a workitem;
     *
     * @param item
     */
    public void deleteWorkitem(Workitem item) throws SQLException {
        long wiID = item.getWorkitemID();
        dao.executeUpdate("delete from " + Workitem.TABLE + " where workitemID=" + wiID);
    }

    /**
     * delete a workitem by workitem id;
     *
     * @param workitemID
     */
    public void deleteWorkitem(long workitemID) throws SQLException {
        dao.executeUpdate("delete from " + Workitem.TABLE + " where workitemID=" + workitemID);
    }

    /**
     * update a workitem status;
     *
     * @param workitemID
     * @param newStatus
     */
    public int updateWorkitemStatus(long workitemID, int newStatus) throws SQLException {
        String sql = "update " + Workitem.TABLE + " set status='" + newStatus + "' where workitemID=" + workitemID;
        return dao.executeUpdate(sql);
    }

    /**
     * update the status of workitems associated with a specfic activity instance.
     *
     * @param activityID
     * @param oldStatus
     * @param newStatus
     * @return
     * @throws SQLException
     */
    public int updateAIWorkitemStatus(long activityID, int oldStatus, int newStatus) throws SQLException {
        StringBuffer sb = new StringBuffer(512);
        sb.append("update ").append(Workitem.TABLE);
        sb.append(" set status='").append(newStatus).append("'");
        sb.append(" where status=").append(oldStatus);
        sb.append(" and activityID=" + activityID);
        return dao.executeUpdate(new String(sb));
    }

    /**
     * update the status of worktems assoicated with a specific activity instance.
     *
     * @param activityID
     * @param newStatus
     * @return
     * @throws SQLException
     */
    public int updateAIWorkitemStatus(long activityID, int newStatus) throws SQLException {
        StringBuffer sb = new StringBuffer(512);
        sb.append("update ").append(Workitem.TABLE);
        sb.append(" set status='").append(newStatus).append("'");
        sb.append(" where ");
        sb.append(" activityID=" + activityID);
        return dao.executeUpdate(new String(sb));
    }

    /**
     * upadte all workitems assoicated with a process instance.
     *
     * @param processID
     * @param oldStatus
     * @param newStatus
     * @return
     * @throws SQLException
     */
    public int updatePIWorkitemStatus(long processID, int oldStatus, int newStatus) throws SQLException {
        StringBuffer sb = new StringBuffer(512);
        sb.append("update ").append(Workitem.TABLE);
        sb.append(" set status='").append(newStatus).append("'");
        sb.append(" where status=").append(oldStatus);
        sb.append(" and processID=" + processID);
        return dao.executeUpdate(new String(sb));
    }

    /**
     * @param processID
     * @param newStatus
     * @return
     * @throws SQLException
     */
    public int updatePIWorkitemStatus(long processID, int newStatus) throws SQLException {
        StringBuffer sb = new StringBuffer(512);
        sb.append("update ").append(Workitem.TABLE);
        sb.append(" set status='").append(newStatus).append("'");
        sb.append(" where ");
        sb.append(" processID=" + processID);
        return dao.executeUpdate(new String(sb));
    }

    /**
     * @param workitemID
     * @return
     * @throws SQLException
     */
    public Workitem getWorkitem(long workitemID) throws SQLException {
        Workitem wi = new Workitem();
        wi.setWorkitemID(workitemID);
        dao.expand(wi);
        return wi;
    }
}
