package com.dream.app.sys;


import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.SysVar;

import java.sql.*;
import java.util.Vector;

/**
 * <p>Title: engine</p>
 * <p>Description: This is the platform of the business development kit.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: dream</p>
 *
 * @author chuguanghua
 * @version 1.0
 */

public class SessionData {

    public SessionData() {
    }

    /**
     * @param sessionData
     * @param userID
     * @param userNO
     * @param userType
     * @param conn
     */
    public static void setSessionData(BizData sessionData, String userID, int userNO, String userType, Connection conn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Vector r = new Vector();

        try {

            /**fetch Roles List from Organizaiton*/
            if (userType.compareTo("E") == 0) {
                stmt = conn.prepareStatement("select a.empID \"empID\",a.Name \"empName\",a.orgID \"orgID\",b.name \"orgName\",b.orgSeq \"orgSeq\" from HREmployee a,HROrganization b where a.orgID=b.orgID and a.empID=?");
                stmt.setInt(1, userNO);
                rs = stmt.executeQuery();
                if (rs != null && rs.next()) {
                    int empID = rs.getInt("empID");
                    String empName = rs.getString("empName");
                    String orgID = rs.getString("orgID");
                    String orgName = rs.getString("orgName");
                    String orgSeq = rs.getString("orgSeq");
                    stmt.close();

                    sessionData.add("empID", new Integer(empID).toString());
                    sessionData.add("empName", empName);
                    sessionData.add("orgID", orgID);
                    sessionData.add("orgName", orgName);
                    sessionData.add("orgSeq", orgSeq);

                    getComOfEmp(orgSeq, sessionData, conn);

                    stmt = conn.prepareStatement("select c.roleID \"roleID\" from HREmployee a,DRMUser b,DRMUserRole c where b.userType='E' and a.empID=b.userNO and b.userID=c.UserID and a.empID=?");
                    stmt.setInt(1, empID);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        String roleID = rs.getString(1);
                        sessionData.add("DRMUserRoles", "roleID", roleID, roleID);
                        r.add(roleID);
                    }
                    stmt.close();

                    stmt = conn.prepareStatement("select orr.roleID \"roleID\" from HREmployee e,HROrganization o,HROrgRole orr where e.orgID=o.orgID and o.orgID=orr.orgID and e.empID=?");
                    stmt.setInt(1, empID);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        sessionData.add("DRMUserRoles", "roleID", rs.getString(1), rs.getString(1));
                        r.add(rs.getString(1));
                    }
                    stmt.close();

                    stmt = conn.prepareStatement("select pr.roleID \"roleID\" from HREmployee e,HREmpPost ep,HRPostRole pr where e.empID=ep.empID and ep.postID=pr.postID and e.empID=?");
                    stmt.setInt(1, empID);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        sessionData.add("DRMUserRoles", "roleID", rs.getString(1), rs.getString(1));
                        r.add(rs.getString(1));
                    }
                }
            }
            sessionData.add(SysVar.USER_ROLES, r);
        } catch (SQLException e) {
            System.out.println("Warning:No profile for user:" + userID);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    /**
     * @param orgSeq
     * @param sd
     * @param conn
     * @return
     */
    protected static int getComOfEmp(String orgSeq, BizData sd, Connection conn) {
        int end = 0;
        int start = 0;
        String orgIDStr = null;
        int orgID = 0;
        if (orgSeq == null) {
            return 0;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("select name,type,orgSeq from HROrganization where orgID=?");
            end = orgSeq.length() - 2;
            while (start >= 0) {
                start = orgSeq.lastIndexOf('.', end);
                if (start < 0) {
                    break;
                } else {
                    orgIDStr = orgSeq.substring(start + 1, end + 1);
                    orgID = Integer.valueOf(orgIDStr).intValue();
                    stmt.setInt(1, orgID);
                    rs = stmt.executeQuery();
                    rs.next();
                    String name = rs.getString(1);
                    String type = rs.getString(2);
                    String comSeq = rs.getString(3);
                    if (type.compareTo("C") == 0) {
                        sd.add("comSeq", comSeq);
                        sd.add("comName", name);
                        return 1;
                    }
                }
                end = start - 1;
            }
            orgIDStr = orgSeq.substring(0, end + 1);
            orgID = Integer.valueOf(orgIDStr).intValue();
            stmt.setInt(1, orgID);
            rs = stmt.executeQuery();
            rs.next();
            String name = rs.getString(1);
            String type = rs.getString(2);
            String comSeq = rs.getString(3);
            sd.add("comSeq", comSeq);
            sd.add("comName", name);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
		} finally {
			try {
				if (stmt != null) {

					stmt.close();
					stmt = null;
				}
				if(null != rs){
					rs.close();
					rs = null;
				}
			} catch (SQLException e2) {
			}
		}
    }
}