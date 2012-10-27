package com.trm.baoxiao;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ShopBXBLC extends DBBLC{
	public ShopBXBLC(){
		
	}
	public int getBXShop(BizData rd,BizData sd){
		String id=rd.getString("id");
		String state="up";
		String sql="select a.id,a.shopid,a.shopid shoppointid,a.pro_id PRIVINCE_ID,a.city_id,a.p_num,a.xfe from ta_bx_shop a where a.groupid="+id;
		String sql2="select b.*,b.bx_shopid shoppointid, c.goods_name,c.goods_id from ta_bx_shop a,ta_bx_shop_goods b,ta_shop_goods c " +
				" where b.goodsid=c.goods_id and a.id=b.bx_shopid and a.groupid="+id;
		String sql3="select b.*,c.dmsm1,c.dmsm2,c.dmz from ta_bx_shop a,ta_bx_shop_jdtc b,dmsm c where a.id=b.bx_shopid and b.cyid=c.dmz and c.dmlb=18 and a.groupid="+id+" order by c.dmz";
		try {
			coreDAO.executeQuery(sql, "shopList", rd);
			coreDAO.executeQuery(sql2, "goodsList", rd);
			coreDAO.executeQuery(sql3, "d_tcList", rd);
			if(rd.getTableRowsCount("shopList")==0){
				state="zc";
				sql="select a.shoppointid id, a.groupid,a.privince_id,a.city_id,a.shoppointid" +
				" from ta_g_gw a where a.groupid="+id;
				sql2="select a.shoppointid,b.goods_id,b.goods_name " +
				" from ta_g_gw a,ta_shop_goods b " +
				" where a.shoppointid=b.shop_point_id and a.groupid="+id;	
			    sql3="select a.dmz,a.dmsm1,a.dmsm2 from dmsm a where a.dmlb=18 order by dmz";
				coreDAO.executeQuery(sql, "shopList", rd);
				coreDAO.executeQuery(sql2, "goodsList", rd);
				coreDAO.executeQuery(sql3, "tcList", rd);
			}
			rd.add("state", state);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int bx_editShop(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String groupid=rd.getString("groupid");
		String sql="";
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				String[] rowsId=rd.getRowIDs("BX_SHOP");
				if(null!=rowsId){
					for(int r=0;r<rowsId.length;r++){
						sql="delete from ta_bx_shop a where a.id="+rd.getString("BX_SHOP", "ID", rowsId[r]);
						coreDAO.executeUpdate(sql, conn);
						sql="delete from ta_bx_shop_goods a where a.BX_SHOPID="+rd.getString("BX_SHOP", "ID", rowsId[r]);
						coreDAO.executeUpdate(sql, conn);
						sql="delete from TA_BX_SHOP_JDTC a where a.BX_SHOPID="+rd.getString("BX_SHOP", "ID", rowsId[r]);
						coreDAO.executeUpdate(sql, conn);
					}
				}
			}
			String []rows=rd.getRowIDs("TA_BX_SHOP");
			if(null!=rows){
				BizData rd2=new BizData();
				for(int a=0;a<rows.length;a++){
					int id=this.queryMaxIDByPara("TA_BX_SHOP", "ID", null);
					rd.add("TA_BX_SHOP", "ID",rows[a],id);
					rd.add("TA_BX_SHOP", "GROUPID",rows[a],groupid);
					rd.add("TA_BX_SHOP", "SQR",rows[a],rd.getString("userno"));
					String[] gRows=rd.getRowIDs("shop"+a);
					if(null!=gRows){
						for(int b=0;b<gRows.length;b++){
							int goods_id=this.queryMaxIDByPara("TA_BX_SHOP_GOODS", "ID", null);
							rd.add("shop"+a, "ID",gRows[b],goods_id);
							rd.add("shop"+a, "BX_SHOPID",gRows[b],id);
						}
						rd2.copyEntity(rd, "shop"+a,"TA_BX_SHOP_GOODS");
						coreDAO.insert("TA_BX_SHOP_GOODS", rd2, conn);
					}
					String[] tcRows=rd.getRowIDs("shop_tc"+a);
					if(null!=tcRows){
						for(int c=0;c<tcRows.length;c++){
							int tc_id=this.queryMaxIDByPara("TA_BX_SHOP_JDTC", "ID", null);
							rd.add("shop_tc"+a, "ID",tcRows[c],tc_id);
							rd.add("shop_tc"+a, "BX_SHOPID",tcRows[c],id);
						}
						rd2.copyEntity(rd, "shop_tc"+a,"TA_BX_SHOP_JDTC");
						coreDAO.insert("TA_BX_SHOP_JDTC",rd2,conn);
					}
				}
				coreDAO.insert("TA_BX_SHOP", rd, conn);
			}
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
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
