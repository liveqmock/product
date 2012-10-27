package com.dream.app.sys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.database.dao.DataPrivil;
import com.dream.bizsdk.common.database.dao.UserDataPrivil;
import com.dream.bizsdk.common.databus.BizData;


public class User extends DBBLC {

    public User() {
        entityName = "DRMUSER";
        version = 1;
    }

    /**
     * 插入一个用户记录
     * in DRMUser(userID,userType)
     *
     * @param data
     * @return id if the newly created user;
     */
    public int insert(BizData data, BizData sd) throws SQLException {
        int i = 0;
        int rows = data.getTableRowsCount("DRMUser");
        if (rows != 1) {
            return SysError.BL_PARAM_ERROR;
        }
        String userID = data.getString("DRMUser", "userID", 0);
        String orgID =sd.getString("orgID");
        rows = data.getTableRowsCount("DRMRole");
        String[] rowIDs = data.getRowIDs("DRMRole");
        while (i < rows) {
            data.add("DRMUserRole", "userID", i, userID);
            data.add("DRMUserRole", "roleID", i, data.getString("DRMRole", "roleID", rowIDs[i]));
            data.add("DRMUserRole", "orgID", i, orgID);
            i++;
        }
        BizData d = new BizData();
        d.copyEntity(data, "DRMUser");
        d.copyEntity(data, "DRMUserRole");
        d.addAttr("DRMUser", "NO", 0);
        d.addAttr("DRMUserRole", "NO", 1);
        rows = coreDAO.insert(d);
        if (rows <= 0) {
            return SysError.INSERT_ERROR;
        } else {
            return rows;
        }
    }

    public int update(BizData data, BizData sd) throws SQLException {
        int i = 0;
        int rows = data.getTableRowsCount("DRMUser");
        if (rows != 1) {
            return SysError.BL_PARAM_ERROR;
        }
        String userID = data.getString("DRMUser", "userID", 0);
        String orgID =sd.getString("orgID");
        rows = data.getTableRowsCount("DRMRole");
        String[] rowIDs = data.getRowIDs("DRMRole");
        while (i < rows) {
            data.add("DRMUserRole", "userID", i, userID);
            data.add("DRMUserRole", "roleID", i, data.getString("DRMRole", "roleID", rowIDs[i]));
            data.add("DRMUserRole", "orgID", i, orgID);
            i++;
        }
        BizData d = new BizData();
        Connection conn = null;
        try {
            conn = coreDAO.getConnection();
            coreDAO.beginTrasct(conn);
            d.add("DRMUSERROLE", "userID", userID);
            coreDAO.delete(d, conn);
            rows = coreDAO.update("DRMUSER", data, true, conn);
            coreDAO.insert("DRMUSERROLE", data, conn);
            coreDAO.commitTrasct(conn);
        } catch (Exception e) {
            coreDAO.rollbackTrasct(conn);
            rows = SysError.DB_ERROR;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return rows;
    }


    /**
     * 插入一个用户记录,自动产生用户内部编号,并返回该用户编号
     * in DRMUser(userID,userType)
     *
     * @param data
     * @param conn
     * @return id if the newly created user;
     * @throws SQLException ,Exception 如果记录插入失败
     */
    public int insert(BizData data, Connection conn) throws SQLException {
        int rows = data.getTableRowsCount("DRMUser");
        if (rows != 1) {
            return SysError.BL_PARAM_ERROR;
        }
        return coreDAO.insert("DRMUser", data, conn);
    }

    /**
     * lock one or more DRMUser records;
     * in DRMUser-records,each record only need to contain userID column
     *
     * @return count of records returned.
     */
    public int lockUsers(BizData data, BizData sd) {

    	String[] rowIDs = data.getRowIDs("DRMUser");
    	for(int i=0;i<rowIDs.length;i++){
    		
    		data.add("DRMUser", "userStatus", rowIDs[i], "0");
    		data.add("DRMUser", "orgid", rowIDs[i], sd.getString("ORGID"));
    		data.addAttr("drmuser", "orgid", rowIDs[i], "oldValue", sd.getString("ORGID"));
    		data.addAttr("drmuser", "userno", rowIDs[i], "oldValue", data.getString("DRMUser", "userno", i));
    	}
    	try {
			super.update(data, sd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return 98;
    }

    /**
     * lock one or more DRMUser records;
     * in DRMUser-records,each record only need to contain userID column
     *
     * @return count of records returned.
     */
    public int lockUsers(BizData data, Connection conn) throws SQLException {
        int count = 0;
        BizData d = new BizData();
        d.copyEntity(data, "DRMUsers", "DRMUser");
        String[] rowIDs = d.getRowIDs("DRMUser");
        if (rowIDs != null || rowIDs.length > 0) {
            for (int i = 0; i < rowIDs.length; i++) {
                d.add("DRMUser", "userStatus", rowIDs[i], "0");
                d.addAttr("DRMUser", "userID", rowIDs[i], "oldValue", (String) d.get("DRMUser", "userID", rowIDs[i]));
            }
            count = coreDAO.update("DRMUSER", d, conn);
        }
        return count;
    }

    /**
     * @param data
     * @return
     */
    public int unlockUsers(BizData data, BizData sd) throws SQLException {
        String[] rowIDs = data.getRowIDs("DRMUser");
    	for(int i=0;i<rowIDs.length;i++){
    		data.add("DRMUser", "userStatus", rowIDs[i], "1");
    		data.add("DRMUser", "orgid", rowIDs[i], sd.getString("ORGID"));
    		data.addAttr("drmuser", "orgid", rowIDs[i], "oldValue", sd.getString("ORGID"));
    		data.addAttr("drmuser", "userno", rowIDs[i], "oldValue", data.getString("DRMUser", "userno", i));
    	}
    	try {
			super.update(data, sd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return 98;
        
    }

    /**
     * @param data
     * @param conn
     * @return
     */
    public int unlockUsers(BizData data, Connection conn) throws SQLException {
        int count = 0;
        BizData d = new BizData();
        d.copyEntity(data, "DRMUsers", "DRMUser");
        String[] rowIDs = d.getRowIDs("DRMUser");
        if (rowIDs != null || rowIDs.length > 0) {
            for (int i = 0; i < rowIDs.length; i++) {
                d.add("DRMUser", "userStatus", rowIDs[i], "1");
                d.addAttr("DRMUser", "userID", rowIDs[i], "oldValue", (String) d.get("DRMUser", "userID", rowIDs[i]));
            }
            count = coreDAO.update("DRMUSER", d, conn);
        }
        return count;
    }

    /**
     * delete one or more DRMUser records from the database;And then query
     * Users again;
     * in DRMUser-record,each record need to contain only the userID field;
     *
     * @return count of records deleted.
     */
    public int delete(BizData data, BizData sd) throws SQLException {
        BizData d = new BizData();
        d.copyEntity(data, "DRMUsers", "DRMUser");
        String[] rowIDs = d.getRowIDs("DRMUser");
        if (rowIDs == null) {
            return 0;
        }
        for (int i = 0; i < rowIDs.length; i++) {
            d.add("DRMUserRole", "userID", rowIDs[i], d.get("DRMUser", "userID", rowIDs[i]));
        }
        d.addAttr("DRMUserRole", "NO", 0);
        d.addAttr("DRMUser", "NO", 1);
        int count = coreDAO.delete(d);
        return count;
    }

    /**
     * modify my password. The old password should be provided.
     * in userID - String,the id of the user;
     * in oldPass - String,old password of the user;
     * in newPass - String,new Password for the user;
     *
     * @return - NO_THIS_USER or 1;
     */
    public int modifyMyPassword(BizData data, BizData sd) throws SQLException {
        String confirmPass = data.getString("confirmPass");
        String newPass = data.getString("newPass");
        String userID = sd.getString("userID");
        if (newPass == null) {
            newPass = new String("");
        }
        if (confirmPass == null) {
            confirmPass = new String("");
        }
        //password doest not match;
        if (newPass.compareTo(confirmPass) != 0) {
            return -300;
        }

        data.addAttr("DRMUser", "userID", 0, "oldValue", userID);
        data.add("DRMUser", "userPassword", 0, newPass);
        int rows = coreDAO.update("DRMUSER", data);
        if (rows <= 0) {
            return -301;
        } else {
            return rows;
        }
    }

    /**
     * modify my password. The old password should be provided.
     * in userID - String,the id of the user;
     * in oldPass - String,old password of the user;
     * in newPass - String,new Password for the user;
     *
     * @return - NO_THIS_USER or 1;
     */
    public int modifyUserPassword(BizData data) throws SQLException {
        String userNO = (String) data.get("userNO");
        String userType = (String) data.get("userType");
        String userID = (String) data.get("userID");
        String newPass = (String) data.get("newPass");
        String confirmPass = (String) data.get("confirmPass");

        //check the parameter;
        if ((userNO == null || userType == null) && userID == null) {
            return SysError.BL_PARAM_ERROR;
        }

        //init the password if necessary;
        if (newPass == null) {
            newPass = new String("");
        }
        if (confirmPass == null) {
            confirmPass = new String("");
        }
        if (newPass.compareTo(confirmPass) != 0) {
            return -300;
        }
        //set to the data;
        if (userNO != null && userType != null) {
           // data.add("DRMUser", "userNO", 0, null);
            data.addAttr("DRMUser", "userNO", "0", "oldValue", userNO);
           // data.add("DRMUser", "userType", 0, null);
            data.addAttr("DRMUser", "userType", 0, "oldValue", userType);
        }
        if (userID != null) {
            data.addAttr("DRMUser", "userID", 0, "oldValue", userID);
            //data.add("DRMUser", "userID", 0, null);
        }
        data.add("DRMUser", "userPassword", 0, newPass);
        
        int count = coreDAO.update("DRMUSER", data);
        if (count <= 0) {
            return -301;
        } else {
            return count;
        }
    }

    /**
     * Query the roles assigned to the user;
     * in userID-String, the id of the user;
     * out DRMRole-Records
     */
	public int queryUserRoles(BizData data, BizData sd) {

		Connection conn = coreDAO.getConnection();
		ResultSet rs = null;
		int i = 0;
		String userID = (String) data.get("DRMUser", "userID", 0);
		if (userID == null) {
			return SysError.BL_PARAM_ERROR;
		}
		data.remove("DRMUserRole");
		String sql = "select a.roleID as \"roleID\" \n"
				+ " from DRMROLE a,DRMUSERROLE b \n"
				+ "where a.roleID=b.roleID and b.userID='" + userID + "'";
		try {
			rs = coreDAO.executeQuery(sql, conn);
			if (rs != null) {
				while (rs.next()) {
					String roleID = rs.getString(1);
					data.add("DRMUserRoles", "roleID", roleID, roleID);
					i++;
				}
				rs.getStatement().close();
				rs.close();
				rs = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return i;
	}
	
	public int queryMaxUserNo(BizData data, BizData sd){
		int maxUserNo = queryMaxIDByPara("drmuser", "USERNO", null);
		data.add("maxUserNo",maxUserNo);
		return 102;
	}

    public int query(BizData data, BizData sd) throws SQLException {
        int rows = data.getTableRowsCount(entityName);
        if (rows != 1) {
            return 0;
        } else {
            BizData d1 = new BizData();
            d1.add(SysVar.SS_DATA, sd);
            d1.copyEntity(data, entityName);
            try {
                int pageNO = data.getInt("pageNO");
                d1.addAttr("DRMUser", "pageNO", pageNO);
            } catch (Throwable e) {
                d1.addAttr("DRMUser", "pageNO", 1);
            }
            return coreDAO.selectPage(data, d1, sd, true);
        }
    }

    /**
     * @param userID
     */
    public void login(String userID) throws SQLException {
        userID = userID.replaceAll("'", "''");
        String sql = "update drmuser set online='yes' where userID='" + userID + "'";
        coreDAO.executeUpdate(sql, (BizData) null);
    }

    /**
     * @param userID
     */
    public void logout(String userID) throws SQLException {
        userID = userID.replaceAll("'", "''");
        String sql = "update drmuser set online='no' where userID='" + userID + "'";
        coreDAO.executeUpdate(sql, (BizData) null);
    }

    public int getMyDataPrivil(BizData rd, BizData sd) {
        int i = 0;
        int j = 0;
        String daoName = null;
        HashMap ups = new HashMap();
        BizData colPrivil = null;
        BizData rowPrivil = null;
        DataPrivil dp = null;

        Vector ur = (Vector) sd.get(SysVar.USER_ROLES);
        if (ur == null) {
            return 0;
        }
        int count = ur.size();
        DAO[] daos = _context.getAllDAOs();
        while (i < daos.length) {
            daoName = daos[i].getDAODef().getName();

            UserDataPrivil userPrivil = new UserDataPrivil();
            userPrivil.setDAOName(daoName);
            ups.put(daoName, userPrivil);
            dp = daos[i].getDataPrivil();

            colPrivil = dp.getColPrivil();
            rowPrivil = dp.getRowPrivil();
            while (j < count) {
                String roleID = (String) ur.get(j);

                userPrivil.mergeColPrivil(colPrivil, roleID);
                userPrivil.mergeRowPrivil(sd, rowPrivil, roleID);

                j++;
            }
            i++;
        }
        sd.add("_dataPrivil", ups);
        sd.setModified(true);
        return 0;
    }
    
    //获取最大的组团社ID
    public int getMaxTravelagencyID(BizData rd, BizData sd) {
        int id = this.queryMaxIDByPara("DRMUSER", "USERNO", null);
        rd.add("maxUserNm",id);
        return 101;
    }
    
    /**
     * 查询所有用户的userid字符串集合
     */
	public int queryUserids(BizData rd, BizData sd) {
		Connection conn = coreDAO.getConnection();
		ResultSet rs = null;
		String userIDs = "";
		String sql = "select t.userid from drmuser t where t.orgid="+sd.getString("orgid");
		try {
			rs = coreDAO.executeQuery(sql, conn);
			if (rs != null) {
				while (rs.next()) {
					String userid = rs.getString("userid");
					userIDs = userIDs + userid + ",";
				}
				rs.getStatement().close();
				rs.close();
				rs = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
			rd.add("userIDs", userIDs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
	
	/**
	 * 查询各机构下的所有用户(带分页)
	 */
	public int queryByOrgID(BizData rd, BizData sd){
		
		String orgID =sd.getString("orgID");
		int pageNO=1;
		int pageSize=1;
		pageNO=Integer.parseInt((String)rd.getAttr("DRMUSER", "pageNO"));
	 	pageSize=Integer.parseInt((String)rd.getAttr("DRMUSER", "pageSize"));
		StringBuffer sql = new StringBuffer();
		sql.append("select t.userno,t.userid,t.deptid,t.username,t.userpassword,t.licenceno,t.birthday,t.sex,t.mail_address,t.mobieltel,\n")
		   .append("t.userstatus,t.documenttype,t.documentno,t.homeaddress,t.qqmsn,t.lastlogindate,t.userip,t.userstartip,t.userendip,\n")
		   .append("t.userloginonlythisip,t.failurelogins,t.usertype,t.isonline,t.orgid  from drmuser t where t.orgid='")
		   .append(orgID).append("' \n");
		if(!rd.getStringByDI("drmuser", "userName", 0).equals(""))
			sql.append("and t.username like '%").append(rd.getStringByDI("drmuser", "userName", 0)).append("%' \n");
		if(!rd.getStringByDI("drmuser", "userID", 0).equals(""))
			sql.append("and t.userid like '%").append(rd.getStringByDI("drmuser", "userID", 0)).append("%'\n");
		if(!rd.getStringByDI("drmuser", "USERSTATUS", 0).equals(""))
			sql.append("and t.userstatus=").append(rd.getStringByDI("drmuser", "USERSTATUS", 0));
		sql.append(" order by t.userno");
		try {
			coreDAO.executeQueryPage(sql.toString(), "DRMUsers", pageNO, pageSize, rd);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 999;
	}
    
}