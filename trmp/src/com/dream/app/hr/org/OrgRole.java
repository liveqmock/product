/**
 *
 */
package com.dream.app.hr.org;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 *
 */

public class OrgRole extends DBBLC {

    public OrgRole() {
        super.entityName = "HROrgRole";
    }

    /**
     * 插入一条机构-角色对应纪录，
     * in DRMRole-Recrods,待插入担任记录；
     * in orgID-String,指定的职位编号；
     *
     * @return 1 if succeed(insert this record),otherwise negative value.
     */
    public int save(BizData rd, BizData sd, Connection conn)
            throws SQLException {
        int i = 0;
        String orgID = rd.get("HROrganization", "orgID", 0).toString();
        if (orgID == null) {
            return 0;
        }
        super.delete("orgID=" + orgID, conn);    //delete old ones;
        BizData d = new BizData();                  //prepare data to be added;
        int rows = rd.getTableRowsCount("DRMRole");
        String[] rowIDs = rd.getRowIDs("DRMRole");
        while (i < rows) {
            String roleID = (String) rd.get("DRMRole", "roleID", rowIDs[i]);
            if (roleID != null) {
                d.add(entityName, "orgID", rowIDs[i], orgID);
                d.add(entityName, "roleID", rowIDs[i], roleID);
            }
            i++;
        }
        return coreDAO.insert(entityName, d, conn);     //inset data;
    }

    /**
     * 查询与一个机构相对应的所有角色
     * in orgID-String,指定的职位编号；
     * out DRMRoles-records;与指定职位对应的角色列表；
     *
     * @return 1 if succeed(insert this record),otherwise negative value.
     */
    public int queryByOrg(BizData rd, BizData sd) throws SQLException {
        String orgID = rd.getString("HROrganization", "orgID", 0);   //check if orgID is valid
        if (orgID.length() == 0) {
            BizData d = new BizData();
            d.add("DRMRole", "roleID", (String) null);
            return coreDAO.select(rd, d, true);   //select all roles;
        } else {
            String sql = "select r.RoleID \"roleID\",r.roleName \"roleName\",orr.orgID \"orgID\" from (select roleID,orgID from HROrgRole where orgID=" + orgID + ") orr right outer join DRMRole r on orr.roleID=r.roleID";
            return coreDAO.executeQuery(sql, "DRMRoles", rd);
        }
    }

    public int countByRole(BizData data) throws SQLException {
        String roleID = data.getString("DRMRole", "roleID", 0);
        if (roleID == null) {
            return 0;
        } else {
            //double ' character in the roleID;
            roleID = roleID.replaceAll("'", "''");
            return coreDAO.count(entityName, "roleID='" + roleID + "'");
        }
    }

}