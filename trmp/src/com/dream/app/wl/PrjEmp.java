package com.dream.app.wl;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
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

public class PrjEmp extends DBBLC {

    public PrjEmp() {
        entityName = "WLProjectEmp";
    }

    public int queryByPrj(BizData rd, BizData sd) throws SQLException {
        String prjNO = (String) rd.get("WLProject", "prjNO", 0);
        if (prjNO == null) {
            return 0;
        } else {
            rd.add("V_WLProjectEmp", "prjNO", prjNO);
            if (coreDAO.select("V_WLProjectEmp", rd, true) >= 1) {
                rd.copyEntity("V_WLProjectEmps", "V_HREmployees");
            }
            return 1;
        }
    }

    public int queryEmpsNotInPrj(BizData rd, BizData sd) throws SQLException {
        StringBuffer sql = new StringBuffer("select e.empID,e.orgName,e.[name],e.gender,e.tel,e.email,e.mobileTel,pe.prjNO from v_hremployee e left outer ");
        sql.append("join (select empID,prjNO from wlprojectemp where prjNO='");
        String prjNO = (String) rd.get("WLProject", "prjNO", 0);
        if (prjNO == null) {
            return 0;
        } else {
            sql.append(prjNO);
            sql.append("' and status=1) pe on e.empID=pe.empID where prjNO is null order by e.orgID");
            int rows = coreDAO.executeQuery(new String(sql), "V_HREmployees", rd);
            return rows;
        }
    }

    public int insert(BizData rd, BizData sd) throws SQLException {
        int i = 0;
        int len = 0;
        String prjNO = (String) rd.get("WLProject", "prjNO", 0);
        String[] rowIDs = rd.getRowIDs("HREmployee");
        if (rowIDs == null || rowIDs.length < 1) {
            return 0;
        }
        len = rowIDs.length;
        BizData d = new BizData();
        BizData d2 = new BizData();
        while (i < len) {
            String empID = rd.getString("HREmployee", "empID", rowIDs[i]);
            d.add(entityName, "prjNO", 0, prjNO);
            d.add(entityName, "empID", 0, empID);
            int rows = coreDAO.select(d2, d, true);
            if (rows >= 1) {
                String status = d.getString(entityName, "status", 0);
                if (status.compareTo("1") != 0) {
                    d.addAttr(entityName, "prjNO", 0, "oldValue", prjNO);
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
        String prjNO = (String) rd.get("WLProject", "prjNO", 0);
        String[] rowIDs = rd.getRowIDs("HREmployee");
        if (rowIDs == null || rowIDs.length < 1) {
            return 0;
        }
        len = rowIDs.length;
        BizData d = new BizData();
        while (i < len) {
            d.add(entityName, "prjNO", rowIDs[i], prjNO);
            d.addAttr(entityName, "prjNO", rowIDs[i], "oldValue", prjNO);
            d.add(entityName, "empID", rowIDs[i], rd.get("HREmployee", "empID", rowIDs[i]));
            d.addAttr(entityName, "empID", rowIDs[i], "oldValue", rd.get("HREmployee", "empID", rowIDs[i]));
            d.add(entityName, "status", rowIDs[i], new Integer(0));
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
        String prjNO = (String) rd.get("WLProject", "prjNO", 0);
        String empID = rd.getString("HREmployee", "empID", 0);
        if (prjNO == null || empID.length() < 1) {
            return false;
        } else {
            BizData d = new BizData();
            d.add(entityName, "empID", empID);
            d.add(entityName, "prjNO", prjNO);
            rows = coreDAO.expand(entityName, d);
            if (rows < 1) {
                return false;
            } else {
                if (d.getString(entityName, "status", 0).compareTo("1") == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}