package com.trm.comm;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class DmsmBLC extends DBBLC{
	public DmsmBLC(){
		this.entityName = "DMSM";
	}
  /*
   * 查询代码说明
   */
	public int queryDmsm(BizData rd,BizData sd)throws SQLException{
				return this.query(rd, sd);
	}
	/*
	 *更新代码说明 
	 */
	public int updateDMSM(BizData rd, BizData sd) throws SQLException {
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			int rows = coreDAO.executeUpdate("delete from DMSM where DMLB='"
					+ rd.getString("DMLB", "DMLB", 0) + "'", conn);
			// coreDAO.commitTrasct(conn);
			String[] rowIds = rd.getRowIDs("DMSM");

			for (int i = 0; i < rowIds.length; i++) {
				if (!rd.getString("DMSM", "DMZ", rowIds[i]).equals("")
						&& !rd.getString("DMSM", "DMSM1", rowIds[i]).equals(""))
					rd.add("DMSM", "DMLB", rowIds[i], rd.getString("DMLB",
							"DMLB", 0));
				else
					rd.remove("DMSM", Integer.parseInt(rowIds[i]));
			}

			rows += coreDAO.insert("DMSM", rd, conn);
			coreDAO.commitTrasct(conn);
			return rows;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			coreDAO.rollbackTrasct(conn);
		}
		return 0;
	}
}
