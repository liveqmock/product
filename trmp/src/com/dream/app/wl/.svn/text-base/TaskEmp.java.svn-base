package com.dream.app.wl;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysError;

import java.sql.SQLException;

/**
 * <p>Title: engine</p>
 * <p>Description: This is the platform of the business development kit.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: dream</p>
 *
 * @author chuguanghua
 * @version 1.0
 */

public class TaskEmp extends DBBLC {

    public TaskEmp() {
        entityName = "WLTaskEmp";
    }

    public int queryByTask(BizData rd, BizData sd) throws SQLException {
        String taskID = (String) rd.get("WLTask", "taskID", 0);
        if (taskID == null) {
            return 0;
        } else {
            rd.add("V_WLTaskEmp", "taskID", 0, taskID);
            return coreDAO.select("V_WLTaskEmp", rd, true);
        }
    }

    public int insert(BizData rd, BizData sd) throws SQLException {
        int i = 0;
        int len = 0;
        String taskID = (String) rd.get("WLTask", "taskID", 0);
        String[] rowIDs = rd.getRowIDs("HREmployee");
        if (rowIDs == null || rowIDs.length < 1) {
            return 0;
        }
        len = rowIDs.length;
        BizData d = new BizData();
        BizData d2 = new BizData();
        while (i < len) {
            String empID = rd.getString("HREmployee", "empID", rowIDs[i]);
            d.add(entityName, "taskID", 0, taskID);
            d.add(entityName, "empID", 0, empID);
            int rows = coreDAO.select(d2, d, true);
            if (rows >= 1) {
                String status = d.getString(entityName, "status", 0);
                if (status.compareTo("1") != 0) {
                    d.addAttr(entityName, "taskID", 0, "oldValue", taskID);
                    d.addAttr(entityName, "empID", 0, "oldValue", empID);
                    d.add(entityName, "status", 0, "1");
                    coreDAO.update(d);
                }
            } else {
                coreDAO.insert(d);
            }
            d.clear();
            i++;
        }
        return len;
    }

    public int disable(BizData rd, BizData sd) throws SQLException {
        int i = 0;
        int len = 0;
        String taskID = rd.getString("WLTask", "taskID", 0);
        String[] rowIDs = rd.getRowIDs("HREmployee");
        if (rowIDs == null || rowIDs.length < 1) {
            return 0;
        }
        len = rowIDs.length;
        BizData d = new BizData();
        while (i < len) {
            d.add(entityName, "taskID", rowIDs[i], taskID);
            d.addAttr(entityName, "taskID", rowIDs[i], "oldValue", taskID);
            d.add(entityName, "empID", rowIDs[i], rd.get("HREmployee", "empID", rowIDs[i]));
            d.addAttr(entityName, "empID", rowIDs[i], "oldValue", rd.get("HREmployee", "empID", rowIDs[i]));
            d.add(entityName, "status", rowIDs[i], "0");
            i++;
        }
        int rows = coreDAO.update(d);
        if (rows < 1) {
            return SysError.UPDATE_ERROR;
        } else {
            return rows;
        }
    }

    public boolean hasThisMember(BizData rd) throws SQLException {
        int rows = 0;
        String taskID = (String) rd.get("WLTask", "taskID", 0);
        String empID = rd.getString("HREmployee", "empID", 0);
        if (taskID == null || empID.length() < 1) {
            return false;
        } else {
            BizData d = new BizData();
            d.add(entityName, "empID", empID);
            d.add(entityName, "taskID", taskID);
            rows = coreDAO.expand(entityName, d);
            if (rows < 1) {
                return false;
            } else {
                if (d.getString("WLTask", "status", 0).compareTo("1") == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}