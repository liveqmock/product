package com.trm.ztbx;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ZtGuidebxBLC extends DBBLC{
	public ZtGuidebxBLC(){
		this.entityName=("TA_ZT_BXDY");
	}
	

public int queryGuideMng(BizData rd, BizData sd) throws SQLException{
		String FLAG = rd.getString("flag");
		String groupID= rd.getStringByDI("TA_ZT_BXDY", "TID", 0);
		if("init".equals(FLAG)){
			String GuideInit = "select ID,TID,DYID,DYXM,DYLK,DFF from TA_ZT_JHDY where TID = '"+groupID+"'";
			coreDAO.executeQuery(GuideInit,"GuideInfo",rd);
		}
		if("edit".equals(FLAG)||"view".equals(FLAG)){
			String GuideEdit = "select ID,TID,DYID,DYXM,DYLK,DYJTF,DFF,QT ,BZ from TA_ZT_BXDY where TID = '"+groupID+"'";
			coreDAO.executeQuery(GuideEdit,"GuideInfo",rd);	
		}
	
		return 1;
	}
	
	public int updateGuideBx(BizData rd, BizData sd) {
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			String groupID = rd.getStringByDI("TA_ZT_BXDY","TID",0);
			String BXR = rd.getString("TA_ZT_BXDY","BXR",0);
			String BXZT = "Y";
			String STATE = rd.getString("state");
			if("Edit".equals(STATE)){
				BizData data = new BizData();
				data.add("TA_ZT_BXDY", "TID", groupID);
				coreDAO.deleteEntity("TA_ZT_BXDY",data,conn);//先根据团号删除对应导游报销
				data.remove("TA_ZT_BXDY");
			}
			String[]rowIds = rd.getRowIDs("TA_ZT_BXDY");		
			for(int i = 0;i < rowIds.length; i++){
				int BXID = this.queryMaxIDByPara("TA_ZT_BXDY", "ID", null);
				String DYID = rd.getString("TA_ZT_BXDY","DYID",rowIds[i]);
				String DYXM = rd.getString("TA_ZT_BXDY","DYXM",rowIds[i]);
				String DYLK = rd.getString("TA_ZT_BXDY","DYLK",rowIds[i]);
				String DYJTF = rd.getString("TA_ZT_BXDY","DYJTF",rowIds[i]);
				String DFF = rd.getString("TA_ZT_BXDY","DFF",rowIds[i]);
				String QT = rd.getString("TA_ZT_BXDY","QT",rowIds[i]);
				String BZ = rd.getString("TA_ZT_BXDY","BZ",rowIds[i]);
				rd.add("TA_ZT_BXDY", "ID", rowIds[i], BXID);
				rd.add("TA_ZT_BXDY", "TID", rowIds[i], groupID);
				rd.add("TA_ZT_BXDY", "DYID", rowIds[i],DYID);
				rd.add("TA_ZT_BXDY", "DYXM", rowIds[i], DYXM);
				rd.add("TA_ZT_BXDY", "DYLK", rowIds[i], DYLK);
				rd.add("TA_ZT_BXDY", "DYJTF", rowIds[i], DYJTF);
				rd.add("TA_ZT_BXDY", "DFF", rowIds[i], DFF);
				rd.add("TA_ZT_BXDY", "BXR", rowIds[i], BXR);
				rd.add("TA_ZT_BXDY", "BXZT", rowIds[i], BXZT);
				rd.add("TA_ZT_BXDY", "QT", rowIds[i], QT);
				rd.add("TA_ZT_BXDY", "BZ", rowIds[i], BZ);
			}
			coreDAO.insert("TA_ZT_BXDY", rd, conn);
			rd.remove("TA_ZT_BXDY");
			updateTDBXZJXXB(groupID, rd, conn);
			BizData dtt = new BizData();
			dtt.addAttr("TA_ZT_GROUP", "ID", 0, "oldValue", groupID);
			dtt.add("TA_ZT_GROUP", "STATE", "5");//修改团状态为 5  实施中
			coreDAO.update("TA_ZT_GROUP", dtt, conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (null != conn) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
	
	/**
	 * updateTDBXZJXXB:(//团队报销总计信息表)
	 *
	 * @param  @param groupID
	 * @param  @param rd
	 * @param  @param conn
	 * @param  @return
	 * @param  @throws SQLException    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	public int updateTDBXZJXXB(String groupID,BizData rd,Connection conn) throws SQLException{
		BizData data = new BizData();
		data.add("TA_TDBXZJXXB_ZT", "TID", groupID);
		coreDAO.select(data);
		int TDBXrows = data.getTableRowsCount("TA_TDBXZJXXB_ZTs");
		data.remove("TA_TDBXZJXXB_ZT");
		data.remove("TA_TDBXZJXXB_ZTs");
		if(TDBXrows > 0){
			rd.addAttr("TA_TDBXZJXXB_ZT", "TID", 0, "oldValue", groupID);
			coreDAO.update("TA_TDBXZJXXB_ZT", rd, conn);
			rd.remove("TA_TDBXZJXXB_ZT");
		}else{
			rd.add("TA_TDBXZJXXB_ZT", "TID", groupID);
			coreDAO.insert("TA_TDBXZJXXB_ZT",rd, conn);
			rd.remove("TA_TDBXZJXXB_ZT");
		}
		return 999;
		
	}
}
