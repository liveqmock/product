package com.dream.app.sys;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;

public class Role extends DBBLC {
	// 打印日志对象
	private Logger log = null;

    public Role() {
        entityName = "DRMROLE";
        
        // 初始化日志对象
        log = Logger.getLogger(Role.class);
    }

    /**
     * delete one or more DRMRole records from the database;
     * in DRMRole-record,each record need to contain only the roleID field;
     *
     * @return count of records deleted.
     */
    public int delete(BizData data, BizData sd) {
        String[] rowIDs = data.getRowIDs("DRMRole");
        Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			for (int i = 0; i < rowIDs.length; i++) {
				String roleID = data.getString("DRMRole", "roleID", rowIDs[i]);
				//删除用户角色关联表
				data.add("DRMUSERROLE", "ROLEID", roleID);
				data.add("DRMUSERROLE", "ORGID", sd.getString("orgid"));
				coreDAO.deleteEntity("DRMUSERROLE", data, conn);
				data.remove("DRMUSERROLE");
				//删除角色菜单关联表
				data.add("DRMROLEMENU", "ROLEID", roleID);
				data.add("DRMROLEMENU", "ORGID", sd.getString("orgid"));
				coreDAO.deleteEntity("DRMROLEMENU", data, conn);
				data.remove("DRMROLEMENU");
				//删除角色表
				data.add("DRMRole", "ORGID", rowIDs[i], sd.getString("orgid"));
			}
			coreDAO.delete(data, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException sqle) {
            try{
            	coreDAO.rollbackTrasct(conn);
            }catch (SQLException e){
            	e.printStackTrace();
            }
            sqle.printStackTrace();
        } finally {
            if (conn != null) {
            	try{
                 	conn.close();
                }catch (SQLException e){
                	e.printStackTrace();
                }
            }
        }
       
        return 98;
    }

    /**
     * modify a Role's status.
     * in userID - String,the id of the target user;
     * in status - Int, the new status;
     *
     * @return NO_THIS_USER or 1;
     */
    public int modifyRoleStatus(BizData data, BizData sd) throws Exception {
        String roleID = data.getString("roleID");
        int status = data.getInt("status");
        if (roleID == null) {
            return SysError.BL_PARAM_ERROR;
        }
        data.clear();
        data.add("DRMRole", "roleID", 0, (String) null);
        data.add("DRMRole", "roleStatus", 0, new Integer(status));
        data.addAttr("DRMRole", "roleID", 0, "oldValue", roleID);
        return coreDAO.update(data);
    }

    public int queryFuncsNotAdded2Role(BizData data, BizData sd) throws SQLException {
        String roleID = (String) data.get("DRMRole", "roleID", 0);
        if (roleID == null) {
            return SysError.BL_PARAM_ERROR;
        }

        data.remove("DRMFunction");
        String sql = "select funcNO as \"funcNO\",funcName as \"funcName\" from DRMFUNCTION where funcStatus<>0 and funcNO not in (select b.funcNo funcNO from DRMROLEFUNCTION a inner join DRMFUNCTION b on a.funcNO=b.funcNO where roleID='" + roleID + "')";
        int count1 = coreDAO.executeQuery(sql, "DRMFunctions", data);
        return count1;
    }

    /**
     * Query the roles assigned to the role;
     * in roleID-String, the id of the user;
     * out DRMRunction-Records
     */
    public int queryRoleFuncs(BizData data, BizData sd) throws SQLException {
    	
        String sql = "select a.funcno \"funcNO\",funcName \"funcName\",funcParams \"funcParams\" \n" +
        		"from DRMFUNCTION a,DRMROLEFUNCTION  b where a.funcNO=b.funcNO and b.roleID='" + data.getString("DRMRole", "roleID", 0) + "'";
        return coreDAO.executeQuery(sql, "DRMRoleFunctions", data);
    }

    /**
     * add functionss to the user;
     * in roleID-String, the id of the role;
     * in DRMFunction-Records,each record need to contain only the funcID field;
     */
    public int addFuncs2Role(BizData data, BizData sd) throws SQLException {
        int i = 0;
        String roleID = (String) data.get("DRMRole", "roleID", 0);
        if (roleID == null) {
            return SysError.BL_PARAM_ERROR;
        }
        String[] rowIDs = data.getRowIDs("DRMFunction");
        BizData d2 = new BizData();
        if (rowIDs.length <= 0) {//no function specified;
            return 0;
        }
        while (i < rowIDs.length) {
            d2.add("DRMRoleFunction", "roleID", rowIDs[i], roleID);
            d2.add("DRMRoleFunction", "funcNO", rowIDs[i], data.get("DRMFunction", "funcNO", rowIDs[i]));
            i++;
        }
        int rows = coreDAO.save(d2);
        if (rows < rowIDs.length) {
            return -1;
        } else {
            return rows;
        }
    }

    /**
     * delete functions from a role;
     * in roleID-String, the id of the user;
     * in DRMRoleFunctions-Records,each record need to contain only the funcID field;
     */
    public int deleteFuncsFromRole(BizData data, BizData sd) throws SQLException {
        int i = 0;
        String roleID = (String) data.get("DRMRole", "roleID", 0);
        if (roleID == null) {
            return SysError.BL_PARAM_ERROR;
        }
        String[] rowIDs = data.getRowIDs("DRMRoleFunction");
        if (rowIDs.length <= 0) {//no function to be added;
            return 0;
        }
        while (i < rowIDs.length) {
            data.add("DRMRoleFunction", "roleID", rowIDs[i], roleID);
            i++;
        }
        int rows = coreDAO.delete("DRMRoleFunction", data);
        if (rows < rowIDs.length) {
            return -1;
        } else {
            return rows;
        }
    }

    /**
     * @param rd
     * @param sd
     * @return
     * @throws SQLException
     */
    public int queryRowPrivil(BizData rd, BizData sd) throws SQLException {
        String roleID = (String) rd.get("roleID");
        String daoName = (String) rd.get("daoName");
        String tableName = (String) rd.get("tableName");

        rd.remove("DRMRowPrivil");
        rd.add("DRMRowPrivil", "roleID", roleID);
        rd.add("DRMRowPrivil", "tableName", tableName);
        DAO dao = _context.getDAO(daoName);
        return dao.select("DRMROWPRIVIL", rd, true);
    }

    /**
     * @param rd
     * @param sd
     * @return
     * @throws SQLException
     */
    public int queryColPrivil(BizData rd, BizData sd) throws SQLException {
        String roleID = (String) rd.get("roleID");
        String daoName = (String) rd.get("daoName");
        String tableName = (String) rd.get("tableName");

        rd.remove("DRMColPrivil");
        rd.add("DRMColPrivil", "roleID", roleID);
        rd.add("DRMColPrivil", "tableName", tableName);
        DAO dao = _context.getDAO(daoName);
        int count = dao.select("DRMCOLPRIVIL", rd, true);
        rd.remove("DRMColPrivil");
        String[] rowIDs = rd.getRowIDs("DRMColPrivils");
        for (int i = 0; i < count; i++) {
            rd.add("DRMColPrivil", "privil", (String) rd.get("DRMColPrivils", "colName", rowIDs[i]), rd.get("DRMColPrivils", "privil", rowIDs[i]));
        }
        return count;
    }

    /**
     * @param rd
     * @param sd
     * @return
     * @throws SQLException
     */
    public int saveRowPrivil(BizData rd, BizData sd) throws SQLException {
        String daoName = (String) rd.get("daoName");
        String tableName = (String) rd.get("tableName");
        String roleID = (String) rd.get("roleID");
        if (daoName == null || tableName == null || roleID == null) {
            return SysError.BL_PARAM_ERROR;
        }

        DAO dao = _context.getDAO(daoName);
        Connection conn = coreDAO.getConnection();

        try {
            dao.beginTrasct(conn);
            rd.add("DRMRowPrivil", "roleID", roleID);
            rd.add("DRMRowPrivil", "tableName", tableName);

            dao.executeUpdate("delete from DRMROWPRIVIL where roleID='" + roleID.replaceAll("'", "''") + "' and tableName='" + tableName.replaceAll("'", "''") + "'", conn);
            dao.insert("DRMROWPRIVIL", rd, conn);
            dao.commitTrasct(conn);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            dao.rollbackTrasct(conn);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return 0;
    }

    public int saveColPrivil(BizData rd, BizData sd) throws SQLException {
        String daoName = (String) rd.get("daoName");
        String roleID = (String) rd.get("roleID");
        String tableName = (String) rd.get("tableName");
        DAO dao = _context.getDAO(daoName);

        Connection conn = coreDAO.getConnection();
        try {
            BizData d = new BizData();
            String[] rowIDs = rd.getRowIDs("DRMColPrivils");
            for (int i = 0; i < rowIDs.length; i++) {
                d.add("DRMColPrivil", "roleID", i, roleID);
                d.add("DRMColPrivil", "tableName", i, tableName);
                d.add("DRMColPrivil", "colName", i, rowIDs[i]);
                d.add("DRMColPrivil", "privil", i, rd.get("DRMColPrivils", "privil", rowIDs[i]));
            }
            dao.beginTrasct(conn);
            dao.executeUpdate("delete from DRMCOLPRIVIL where roleID='" + roleID.replaceAll("'", "''") + "' and tableName='" + tableName.replaceAll("'", "''") + "'", conn);
            dao.insert("DRMCOLPRIVIL", d, conn);
            dao.commitTrasct(conn);
        } catch (SQLException sqle) {
            dao.rollbackTrasct(conn);
            sqle.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return 0;
    }
    
    /**
     * 初始化角色
     * @param rd
     * @param sd
     * @return
     */
    public int initRoleInfo(BizData rd, BizData sd) {
    	
    	rd.add("dispMode", rd.getString("dispMode"));
    	rd.add("orgid", sd.getString("orgid"));
    	return 90;
    }
    
    /**
     * 插入角色信息
     * 
     * @param rd 请求数据对象
     * @param sd session数据
     * @return 结果码
     */
    public int insertInfo(BizData rd, BizData sd)
    {
    	// 根据当前企业ID，取最大角色ID
    	int roleId = this.queryMaxIdByOrg(entityName, "roleid", sd.getString("orgid"), null);
    	
    	// 补充角色ID
    	rd.add("drmrole", "roleid", 0, roleId);
    	
    	// 补充角色类型
    	rd.add("drmrole", "roletype", 0, "1");
    	
    	try
        {
    		// 向角色表中插入数据
	        this.insert(rd, sd);
        }
        catch (SQLException e)
        {
        	log.error(e);
        	
	        return SysError.INSERT_ERROR;
        }
        
    	return 999;
    }
    
    /**
     * 根据指定条件查询当前表中所有数据
     * 
     * @param rd 请求数据对象
     * @param sd session数据
     * @return 结果码
     * @throws SQLException 数据库异常
     */
    public int queryAllByWhere(BizData rd, BizData sd) throws SQLException {
        
        // 设置查询条件
        rd.add(entityName, "orgid", 0, sd.getString("orgid"));
          
        int rows = coreDAO.select("DRMROLE", rd);
          
        return rows;
    }
}