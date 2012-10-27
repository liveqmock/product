package com.trm.comm;
 
import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.database.datadict.Types;
import com.dream.bizsdk.common.databus.BizData;

public class DmlbBLC extends DBBLC {
	public static int maxId;
	public static boolean init=true;
	public DmlbBLC(){
		this.entityName = "DMLB";
	}
	/*
	 * 加载代码类别
	 */
	public int loadDMLB(BizData rd,BizData sd) throws SQLException{
		return this.query(rd, sd);
	}
	/*
	 * 增加后加载代码类别
	 */
	public int queryZd(BizData rd,BizData sd) throws SQLException{
		return this.query(rd, sd);
	}
	/*
	 * 查询最大ID
	 */
	public int getMaxId(BizData rd,BizData sd) throws SQLException{
		if(init==true){
			maxId=((Integer) this.getFieldMaxValue("DMLB", Types.INT)).intValue();
			init=false;
		}
		maxId=maxId+1;
		System.out.println(maxId); 
		rd.add(entityName, "DMLB",0,maxId);
		System.out.println(rd.getInt(entityName,"DMLB",0));
		return maxId;
	}
	/*
	 * 根据类别ID查询详细信息
	 */
	public int queryById(BizData rd,BizData sd) throws SQLException{
		return this.query(rd, sd);
	}
	/*
	 * 增加代码类别
	 */
	public int newDMLB(BizData rd,BizData sd) throws SQLException{
		return this.insert(rd, sd);
	}
	/*
	 * 删除
	 */
	public int deleteDMLB(BizData rd){
		Connection conn=coreDAO.getConnection();
		try{
		coreDAO.beginTrasct(conn);
		int rows=coreDAO.deleteEntity("DMLB",rd,conn);
		rows+=coreDAO.deleteEntity("DMSM", rd,conn);
		coreDAO.commitTrasct(conn);
		return rows;
		}catch(SQLException sqle){
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	/*
	 * 修改代码类别
	 */
	public int updateDMLB(BizData rd,BizData sd){
		//System.out.print(rd.getString("DMLB","LBSM",0)+"更新");
		Connection conn=coreDAO.getConnection();
		try{
			coreDAO.beginTrasct(conn);
			coreDAO.update("DMLB", rd, conn);
			coreDAO.commitTrasct(conn);
		}catch(SQLException e){
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 1;
		
	}
}
