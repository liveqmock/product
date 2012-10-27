package com.baseMng;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;

public class DinningRoomBLC extends DBBLC{
	public DinningRoomBLC(){
		this.entityName="TA_DINING_ROOM";
	}
	public int getAlldinningRoom(BizData rd,BizData sd)throws SQLException
	{
		// 根据分页规格查询当前城市下的餐厅信息
		rd.add("TA_DINING_ROOM", "orgid", sd.getString("orgid"));
		this.queryPage(rd, sd);
		
		return 1;
	}
	public int getDrByName(BizData rd,BizData sd)throws SQLException{
		
		String name=rd.getString("name");
		String orgid = sd.getString("orgid");
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_DINING_ROOM", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_DINING_ROOM", "pageSize"));
		String sql="select * from TA_DINING_ROOM a where a.CMPNY_NAME like '%"+name+"%' and a.orgid='"+orgid+"'";
		coreDAO.executeQueryPage(sql, "TA_DINING_ROOMs", pageNO, pageSize, rd);
		return 1;
	}
	
	/*添加餐厅信息*/
	public int addDR(BizData rd,BizData sd)throws SQLException
	{
		// 添加机构ID
		rd.add("TA_DINING_ROOM", "orgid", 0, sd.getString("orgid"));
		
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		int ImgRows[]=(int[]) rd.get("FILEID");
		try{
			// 取最大ID
			int dr_id=this.queryMaxIDByPara("TA_DINING_ROOM","DINING_ROOM_ID", null);
			
			if(null!=ImgRows)
			{
				for(int i=0;i<ImgRows.length;i++)
				{
					rd.add("ta_picShare", "picID",this.queryMaxIDByPara("ta_picShare", "picID", null));
					rd.add("ta_picShare", "fileUploadID",ImgRows[i]);
					rd.add("ta_picShare", "relating_id",dr_id);
					rd.add("ta_picShare", "picForWhere",rd.getString("tplx"));
					coreDAO.insert("ta_picShare", rd, conn);
				}
			}
			
			rd.add("TA_DINING_ROOM", "DINING_ROOM_ID",dr_id);
			//生成拼音码
			String cmpName = rd.getString("TA_DINING_ROOM", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_DINING_ROOM", "NAMECODE", 0, nameCode);
			coreDAO.insert("TA_DINING_ROOM", rd, conn);
			coreDAO.commitTrasct(conn);
			
		}catch(Exception e)
		{
			coreDAO.rollbackTrasct(conn);
		}finally
		{
			if(null!=conn){
				conn.close();
			}
		}
		
		this.queryPage(rd, sd);
		return 1;
	}
	//根据ID查询餐厅信息
	public int getDRById(BizData rd,BizData sd)throws SQLException{
		
		Connection conn = coreDAO.getConnection();
		String d_id=rd.getStringByDI("TA_DINING_ROOM", "DINING_ROOM_ID", 0);
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select d.*,x.name\n");
		sqlBuff.append("from ta_dining_room d,xzqh x\n").append("where d.city_id=x.id(+)\n");
		sqlBuff.append("and d.orgid=").append(sd.getString("orgid")).append(" and d.dining_room_id=").append(d_id);
		coreDAO.executeQuery(sqlBuff.toString(), "TA_DINING_ROOMs", rd, conn);
		
		String sql="select b.fileid,b.fileurl from TA_PICSHARE a,DRMFILEUPLOADLOG b \n" +
				"where a.picforwhere=2 and a.relating_id="+d_id+" and a.fileuploadid=b.fileid";
		coreDAO.executeQuery(sql, "drImg", rd, conn);
		conn.close();
		return 1;
	}
	/*修改餐厅信息*/
	public int updateDR(BizData rd,BizData sd)throws SQLException{
		int[] ImgRows=(int[]) rd.get("FILEID");
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			//生成拼音码
			String cmpName = rd.getString("TA_DINING_ROOM", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_DINING_ROOM", "NAMECODE", 0, nameCode);
			coreDAO.update("TA_DINING_ROOM", rd, conn);
			if(null!=ImgRows){
				for(int a=0;a<ImgRows.length;a++){
					rd.add("TA_PICSHARE", "PICID",this.queryMaxIDByPara("TA_PICSHARE", "PICID", null));
					rd.add("TA_PICSHARE", "FILEUPLOADID",ImgRows[a]);
					rd.add("TA_PICSHARE","RELATING_ID",rd.getStringByDI("TA_DINING_ROOM", "DINING_ROOM_ID", 0));
					rd.add("TA_PICSHARE", "PICFORWHERE",rd.getString("tplx"));
					this.coreDAO.insert("TA_PICSHARE",rd,conn);
				}
			}
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		
		return 1;
	}
	/*删除餐厅*/
	public int deleteDR(BizData rd,BizData sd)throws SQLException{
		
		rd.add("ywlb", rd.getString("ywlb"));
		String id=rd.getString("id");
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String sql="select FILEUPLOADID from TA_PICSHARE a where a.PICFORWHERE=2 and a.RELATING_ID="+id;
			coreDAO.executeQuery(sql,"imgRows",rd);
			int rows=rd.getTableRowsCount("imgRows");
			for(int a=0;a<rows;a++){
				String sql2="delete from DRMFILEUPLOADLOG a where a.FILEID="+rd.getStringByDI("imgRows", "FILEUPLOADID", a);
			coreDAO.executeUpdate(sql2, conn);
			}
			String sql3="delete from TA_PICSHARE a where a.PICFORWHERE=2 and a.RELATING_ID="+id;
			coreDAO.executeUpdate(sql3, conn);
			String sql4="delete from TA_DINING_ROOM a where a.DINING_ROOM_ID="+id;
			coreDAO.executeUpdate(sql4, conn);
			coreDAO.commitTrasct(conn);
			rd.add("city_id", rd.getString("city_id"));
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		
		return 1;
		}
	/*批量删除*/
	public int batchDelDR(BizData rd,BizData sd)throws SQLException{
		rd.add("ywlb", rd.getString("ywlb"));
		Connection conn=coreDAO.getConnection();
		String RowsId[]=rd.getRowIDs("DINING_ROOM");
		for(int a=0;a<RowsId.length;a++){
			String id=rd.getString("DINING_ROOM", "CHECKBOX", RowsId[a]);
			String sql="select FILEUPLOADID from TA_PICSHARE a where a.PICFORWHERE=2 and a.RELATING_ID="+id;
			coreDAO.executeQuery(sql,"imgRows",rd);
			int rows=rd.getTableRowsCount("imgRows");
			for(int b=0;b<rows;b++){
				String sql2="delete from DRMFILEUPLOADLOG a where a.FILEID="+rd.getStringByDI("imgRows", "FILEUPLOADID", b);
				coreDAO.executeUpdate(sql2, conn);
			}
			String sql3="delete from TA_PICSHARE a where a.PICFORWHERE=2 and a.RELATING_ID="+id;
			coreDAO.executeUpdate(sql3, conn);
			String sql4="delete from TA_DINING_ROOM a where a.DINING_ROOM_ID="+id;
			coreDAO.executeUpdate(sql4, conn);
			rd.remove("imgRows");
		}
		coreDAO.commitTrasct(conn);
		rd.add("city_id", rd.getString("city_id"));
		try{}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return 1;
	}
}
