package com.baseMng;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;

public class ShopBLC extends DBBLC{
	public  ShopBLC(){
		this.entityName="TA_SHOPPOINT";
	}
	public int getAllShop(BizData rd,BizData sd)throws SQLException{
		String orgid = sd.getString("orgid");
		rd.add("TA_SHOPPOINT", "orgid", 0, orgid);
		this.queryPage(rd, sd);
		return 1;
	}
	public int getShopById(BizData rd,BizData sd)throws SQLException{
		
		String shopId = rd.getStringByDI("TA_SHOPPOINT", "SHOP_POINT_ID", 0);
		String sql = "select d.*,x.name\n" +
				"from TA_SHOPPOINT d,xzqh x\n" +
				"where d.city_id=x.id(+)\n" +
				"and d.orgid="+sd.getString("orgid")+"\n" +
						"and d.shop_point_id="+shopId;
		coreDAO.executeQuery(sql, "TA_SHOPPOINTs", rd);
		
		return 1;
	}
	public int getShopByName(BizData rd,BizData sd)throws SQLException{
		
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_SHOPPOINT", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_SHOPPOINT", "pageSize"));
		String name=rd.getString("name");
		String orgid = sd.getString("orgid");
		String sql="select * from TA_SHOPPOINT a where a.CMPNY_NAME like '%"+name+"%' and a.orgid='"+orgid+"'";
		coreDAO.executeQueryPage(sql, "TA_SHOPPOINTs", pageNO, pageSize, rd);
		return 1;
	}
	public int addShop(BizData rd,BizData sd)throws SQLException{
		
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			int id=this.queryMaxIDByPara("TA_SHOPPOINT", "SHOP_POINT_ID", null);
			rd.add("TA_SHOPPOINT", "SHOP_POINT_ID",id);
			/*String []cmdRows=rd.getRowIDs("shop_goods");
			for(int a=0;a<cmdRows.length;a++){
				rd.add("TA_SHOP_GOODS", "GOODS_ID",this.queryMaxIDByPara("TA_SHOP_GOODS", "GOODS_ID", null));
				rd.add("TA_SHOP_GOODS", "SHOP_POINT_ID",id);
				rd.add("TA_SHOP_GOODS", "GOODS_NAME",rd.getString("shop_goods", "goods_name", cmdRows[a]));
				coreDAO.insert("TA_SHOP_GOODS",rd,conn);
			}
			*/
			//生成拼音码
			String cmpName = rd.getString("TA_SHOPPOINT", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_SHOPPOINT", "NAMECODE", 0, nameCode);
			rd.add("TA_SHOPPOINT", "ORGID", 0, sd.getString("orgid"));
			coreDAO.insert("TA_SHOPPOINT", rd, conn);
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
	public int updateShop(BizData rd,BizData sd)throws SQLException{
		String id=rd.getStringByDI("TA_SHOPPOINT", "SHOP_POINT_ID", 0);
		String []cmdRows=rd.getRowIDs("shop_goods");
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			
			//生成拼音码
			String cmpName = rd.getString("TA_SHOPPOINT", "CMPNY_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_SHOPPOINT", "NAMECODE", 0, nameCode);
			
			coreDAO.update("TA_SHOPPOINT",rd,conn);
//			String sql="delete from TA_SHOP_GOODS a where a.SHOP_POINT_ID="+id;
//			coreDAO.executeUpdate(sql, conn);
//			for(int a=0;a<cmdRows.length;a++){
//				rd.add("TA_SHOP_GOODS", "GOODS_ID",this.queryMaxIDByPara("TA_SHOP_GOODS", "GOODS_ID", null));
//				rd.add("TA_SHOP_GOODS", "SHOP_POINT_ID",id);
//				rd.add("TA_SHOP_GOODS", "GOODS_NAME",rd.getString("shop_goods", "goods_name", cmdRows[a]));
//				coreDAO.insert("TA_SHOP_GOODS",rd,conn);
//			}
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
	public int delShop(BizData rd,BizData sd)throws SQLException{
		String id=rd.getString("id");
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
//			String sql="delete from TA_SHOP_GOODS a where a.SHOP_POINT_ID="+id;
//			coreDAO.executeUpdate(sql, conn);
			String sql2="delete from TA_SHOPPOINT a where a.SHOP_POINT_ID="+id;
			coreDAO.executeUpdate(sql2, conn);
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
	public int batchDelShop(BizData rd,BizData sd)throws SQLException{
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String[] rowsId=rd.getRowIDs("shop");
			for(int a=0;a<rowsId.length;a++){
				String id=rd.getString("shop","CHECKBOX",rowsId[a]);
//				String sql="delete from TA_SHOP_GOODS a where a.SHOP_POINT_ID="+id;
//				coreDAO.executeUpdate(sql, conn);
				String sql2="delete from TA_SHOPPOINT a where a.SHOP_POINT_ID="+id;
				coreDAO.executeUpdate(sql2, conn);
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
	public int showRTF(BizData rd,BizData sd)throws SQLException{
		String sql="select a.single_money,b.name,b.id from ta_shoppoint_single_mny a,xzqh b " +
				"where a.shop_point_id="+rd.getString("id")+" and a.xzqh=b.id";
		coreDAO.executeQuery(sql, "rtfInfo", rd);
		String sql2="select a.id,a.name from xzqh a where a.pid=0";
		coreDAO.executeQuery(sql2, "cityInfo", rd);
		return 1;
	}
	public int addRTF(BizData rd,BizData sd)throws SQLException{
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String sql="delete from TA_SHOPPOINT_SINGLE_MNY a where a.SHOP_POINT_ID="+rd.getString("id");
			coreDAO.executeUpdate(sql, conn);
			String rowsId[]=rd.getRowIDs("rtf");
			for(int a=0;a<rowsId.length;a++){
				rd.add("TA_SHOPPOINT_SINGLE_MNY", "SHOP_POINT_ID",rd.getString("id"));
				rd.add("TA_SHOPPOINT_SINGLE_MNY", "XZQH",rd.getString("rtf","xzqh_id",rowsId[a]));
				rd.add("TA_SHOPPOINT_SINGLE_MNY", "SINGLE_MONEY",rd.getString("rtf","price",rowsId[a]));
				coreDAO.insert("TA_SHOPPOINT_SINGLE_MNY",rd,conn);
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
}
