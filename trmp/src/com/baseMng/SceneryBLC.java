package com.baseMng;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;

public class SceneryBLC extends DBBLC{
	public SceneryBLC(){
		this.entityName="TA_SCENERY_POINT";
	}
	public int getAllScenery(BizData rd,BizData sd) throws SQLException {
		String orgid = sd.getString("orgid");
		rd.add("TA_SCENERY_POINT", "orgid", 0, orgid);
		this.queryPage(rd, sd);
		return 1;
	}
	public int getSceneryByName(BizData rd,BizData sd) throws SQLException {
		String name=rd.getString("name");
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_SCENERY_POINT", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_SCENERY_POINT", "pageSize"));
		String orgid = sd.getString("orgid");
		String sql="select * from TA_SCENERY_POINT a where a.CMPNY_NAME like '%"+name+"%' \n" +
				"and a.orgid='"+orgid+"'";
		coreDAO.executeQueryPage(sql, "TA_SCENERY_POINTs", pageNO, pageSize, rd);
		return 1;
	}
	public int getSceneryById(BizData rd,BizData sd)throws SQLException{
		
		Connection conn = coreDAO.getConnection();
		String id=rd.getStringByDI("TA_SCENERY_POINT", "SCENERY_ID", 0);
		String sql="select b.fileid,b.fileurl from ta_picshare a,DRMFILEUPLOADLOG b \n" +
				"where a.relating_id="+id+" and a.picforwhere=3 and a.fileuploadid=b.fileid";
		coreDAO.executeQuery(sql, "sceneryImg", rd, conn);
		sql="select a.PRICE_NAME,a.PRICE,a.PRICE_TYPE from TA_SCENERY_POINT_PRICE a where a.SCENERY_ID="+id;
		coreDAO.executeQuery(sql, "sceneryPrice", rd, conn);
		sql="select a.PERSON_COUNT,a.RETURN_COUNT from TA_SCENERY_POINT_RT a where a.SCENERY_ID="+id;
		coreDAO.executeQuery(sql, "sceneryFL", rd, conn);
		
		sql = "select d.*,x.name\n" +
				"from TA_SCENERY_POINT d,xzqh x\n" +
				"where d.city_id=x.id(+)\n" +
				"and d.orgid="+sd.getString("orgid")+"\n" +
						"and d.scenery_id="+id;
		coreDAO.executeQuery(sql, "TA_SCENERY_POINTs", rd, conn);
		
		return 1;
	}
	public int upScenery(BizData rd,BizData sd)throws SQLException{
		Connection conn=coreDAO.getConnection();
		String scenery_id=rd.getStringByDI("TA_SCENERY_POINT", "SCENERY_ID", 0);
		coreDAO.beginTrasct(conn);
		try{
			String dj_b_date=rd.getString("dj_b_month")+rd.getString("dj_b_day");
			String dj_e_date=rd.getString("dj_e_month")+rd.getString("dj_e_day");
			String wj_b_date=rd.getString("wj_b_month")+rd.getString("wj_b_day");
			String wj_e_date=rd.getString("wj_e_month")+rd.getString("wj_e_day");
			rd.add("TA_SCENERY_POINT", "LOW_SEASON_B_DATE",dj_b_date);
			rd.add("TA_SCENERY_POINT", "LOW_SEASON_E_DATE",dj_e_date);
			rd.add("TA_SCENERY_POINT", "MID_SEASON_B_DATE",wj_b_date);
			rd.add("TA_SCENERY_POINT", "MID_SEASON_E_DATE",wj_e_date);
			//添加图片
			int[] imgRows=(int[]) rd.get("FILEID");
			if(null!=imgRows){
				for(int a=0;a<imgRows.length;a++){
					rd.add("TA_PICSHARE", "PICID",this.queryMaxIDByPara("TA_PICSHARE", "PICID", null));
					rd.add("TA_PICSHARE", "FILEUPLOADID",imgRows[a]);
					rd.add("TA_PICSHARE", "RELATING_ID",scenery_id);
					rd.add("TA_PICSHARE", "PICFORWHERE",rd.getString("tplx"));
					coreDAO.insert("TA_PICSHARE",rd,conn);
				}
			}
			//删除价格,再增加
			String sql="delete from TA_SCENERY_POINT_PRICE a where a.SCENERY_ID="+scenery_id;
			coreDAO.executeUpdate(sql, conn);
			String[] priceRows=rd.getRowIDs("TA_SCENERY_POINT_PRICE");
			for(int p=0;p<priceRows.length;p++){
				int price_id=this.queryMaxIDByPara("TA_SCENERY_POINT_PRICE", "PRICE_ID", null);
				rd.add("TA_SCENERY_POINT_PRICE","PRICE_ID",p,price_id);
				rd.add("TA_SCENERY_POINT_PRICE", "SCENERY_ID",p,scenery_id);
			}
			coreDAO.insert("TA_SCENERY_POINT_PRICE", rd, conn);
			//删除返利,再增加
			String sql2="delete from TA_SCENERY_POINT_RT a where a.SCENERY_ID="+scenery_id;
			coreDAO.executeUpdate(sql2,conn);
			String[] flRows=rd.getRowIDs("TA_SCENERY_POINT_RT");
			for(int f=0;f<flRows.length;f++){
				int fl_id=this.queryMaxIDByPara("TA_SCENERY_POINT_RT", "RETURN_ID", null);
				rd.add("TA_SCENERY_POINT_RT", "RETURN_ID",f,fl_id);
				rd.add("TA_SCENERY_POINT_RT", "SCENERY_ID",f,scenery_id);
				}
			coreDAO.insert("TA_SCENERY_POINT_RT",rd,conn);
			
			//生成拼音码
			String cmpName = rd.getString("TA_SCENERY_POINT", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_SCENERY_POINT", "NAMECODE", 0, nameCode);
			//修改景点信息
			coreDAO.update("TA_SCENERY_POINT",rd,conn);
			rd.add("city_id", rd.getStringByDI("TA_SCENERY_POINT", "city_id", 0));
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
	public int addScenery(BizData rd,BizData sd)throws SQLException{
		
	//	rd.add("ywlb", rd.getStringByDI("TA_SCENERY_POINT", "ywlb", 0));
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String dj_b_date=rd.getString("dj_b_month")+rd.getString("dj_b_day");
			String dj_e_date=rd.getString("dj_e_month")+rd.getString("dj_e_day");
			String wj_b_date=rd.getString("wj_b_month")+rd.getString("wj_b_day");
			String wj_e_date=rd.getString("wj_e_month")+rd.getString("wj_e_day");
			rd.add("TA_SCENERY_POINT", "LOW_SEASON_B_DATE",dj_b_date);
			rd.add("TA_SCENERY_POINT", "LOW_SEASON_E_DATE",dj_e_date);
			rd.add("TA_SCENERY_POINT", "MID_SEASON_B_DATE",wj_b_date);
			rd.add("TA_SCENERY_POINT", "MID_SEASON_E_DATE",wj_e_date);
			int scenery_id=this.queryMaxIDByPara("TA_SCENERY_POINT", "SCENERY_ID", null);
			//添加图片
			int []imgRows=(int[]) rd.get("FILEID");
			if(null!=imgRows){
				for(int a=0;a<imgRows.length;a++){
					rd.add("TA_PICSHARE", "PICID",this.queryMaxIDByPara("TA_PICSHARE", "PICID", null));
					rd.add("TA_PICSHARE", "FILEUPLOADID",imgRows[a]);
					rd.add("TA_PICSHARE", "RELATING_ID",scenery_id);
					rd.add("TA_PICSHARE", "PICFORWHERE",rd.getString("tplx"));
					coreDAO.insert("TA_PICSHARE", rd,conn);
				}
			}
			//添加景点价格
			String[] priceRows=rd.getRowIDs("TA_SCENERY_POINT_PRICE");
			for(int p=0;p<priceRows.length;p++){
				int price_id=this.queryMaxIDByPara("TA_SCENERY_POINT_PRICE", "PRICE_ID", null);
				rd.add("TA_SCENERY_POINT_PRICE","PRICE_ID",p,price_id);
				rd.add("TA_SCENERY_POINT_PRICE", "SCENERY_ID",p,scenery_id);
			}
			coreDAO.insert("TA_SCENERY_POINT_PRICE",rd,conn);
			//添加返利
			String[] flRows=rd.getRowIDs("TA_SCENERY_POINT_RT");
			for(int f=0;f<flRows.length;f++){
				int fl_id=this.queryMaxIDByPara("TA_SCENERY_POINT_RT", "RETURN_ID", null);
				rd.add("TA_SCENERY_POINT_RT", "RETURN_ID",f,fl_id);
				rd.add("TA_SCENERY_POINT_RT", "SCENERY_ID",f,scenery_id);
				}
			coreDAO.insert("TA_SCENERY_POINT_RT",rd,conn);
			
			//生成拼音码
			String cmpName = rd.getString("TA_SCENERY_POINT", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_SCENERY_POINT", "NAMECODE", 0, nameCode);
			//添加景点
			rd.add("TA_SCENERY_POINT", "SCENERY_ID",scenery_id);
			rd.add("TA_SCENERY_POINT", "ORGID", 0, sd.getString("orgid"));
			coreDAO.insert("TA_SCENERY_POINT",rd,conn);
			rd.add("city_id", rd.getStringByDI("TA_SCENERY_POINT", "city_id", 0));
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
	public int delScenery(BizData rd,BizData sd) throws SQLException {
		
//		rd.add("ywlb", rd.getString("ywlb"));
		String id=rd.getString("id");
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String sql="select a.FILEUPLOADID from TA_PICSHARE a where a.PICFORWHERE=3 and a.RELATING_ID="+id;
			coreDAO.executeQuery(sql, "sceneryImg", rd);
			int rows=rd.getTableRowsCount("sceneryImg");
			for(int a=0;a<rows;a++){
				String sql2="delete from DRMFILEUPLOADLOG a where a.fileid="+rd.getStringByDI("sceneryImg", "FILEUPLOADID", a);
				coreDAO.executeUpdate(sql2, conn);
			}
			//删除TA_PICSHARE表
			String sql3="delete from TA_PICSHARE a where a.PICFORWHERE=3 and a.RELATING_ID="+id;
			coreDAO.executeUpdate(sql3, conn);
			//删除价格
			String sql4="delete from TA_SCENERY_POINT_PRICE a where a.SCENERY_ID="+id;
			coreDAO.executeUpdate(sql4, conn);
			//删除返利
			String sql5="delete from TA_SCENERY_POINT_RT a where a.SCENERY_ID="+id;
			coreDAO.executeUpdate(sql5, conn);
			//删除景点
			String sql6="delete from TA_SCENERY_POINT a where a.SCENERY_ID="+id;
			coreDAO.executeQuery(sql6, conn);
			rd.add("city_id", rd.getString("city_id"));
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
	public int batchDelScenery(BizData rd,BizData sd)throws SQLException{
//		rd.add("ywlb", rd.getString("ywlb"));
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String[] rowsId=rd.getRowIDs("scenery");
			for(int a=0;a<rowsId.length;a++){
				String id=rd.getString("scenery","CHECKBOX",rowsId[a]);
				String sql="select a.FILEUPLOADID from TA_PICSHARE a where a.PICFORWHERE=3 and a.RELATING_ID="+id;
				coreDAO.executeQuery(sql, "sceneryImg", rd);
				int rows=rd.getTableRowsCount("sceneryImg");
				for(int b=0;b<rows;b++){
					String sql2="delete from DRMFILEUPLOADLOG a where a.fileid="+rd.getStringByDI("sceneryImg", "FILEUPLOADID", b);
					coreDAO.executeUpdate(sql2, conn);
				}
				String sql3="delete from TA_PICSHARE a where a.PICFORWHERE=3 and a.RELATING_ID="+id;
				coreDAO.executeUpdate(sql3, conn);
				//删除价格
				String sql4="delete from TA_SCENERY_POINT_PRICE a where a.SCENERY_ID="+id;
				coreDAO.executeUpdate(sql4, conn);
				//删除返利
				String sql5="delete from TA_SCENERY_POINT_RT a where a.SCENERY_ID="+id;
				coreDAO.executeUpdate(sql5, conn);
				//删除景点
				String sql6="delete from TA_SCENERY_POINT a where a.SCENERY_ID="+id;
				coreDAO.executeQuery(sql6, conn);
				rd.remove("sceneryImg");
			}
			rd.add("city_id", rd.getString("city_id"));
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
}
