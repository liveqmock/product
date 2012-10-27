package com.trm.plan;

import java.sql.Connection;
import java.sql.SQLException;
import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class ShopPlanBLC extends DBBLC{
	public ShopPlanBLC(){
		this.entityName="TA_G_GW";
	}
	public int getAllShop(BizData rd,BizData sd){
		String city_id=rd.getString("city");
		String sql="select a.shop_point_id,a.cmpny_name,a.city_id from ta_shoppoint a where a.city_id="+city_id;
		try {
			coreDAO.executeQuery(sql, "shopList", rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int p_getShopInfo(BizData rd,BizData sd){
		String id=rd.getString("ID");
		String sql="select a.privince_id,a.city_id from ta_g_gw a where a.groupid="+id+" group by a.privince_id,a.city_id";
		String sql2="select m.privince_id,m.city_id,m.shop_point_id,m.cmpny_name,case when m.shop_point_id=n.shoppointid then 'Y' else 'N' end isck\n" +
				"from (\n" +
				"select distinct x.privince_id,y.city_id,y.shop_point_id,y.cmpny_name\n" +
				"from\n" +
				"(select a.id,a.groupid,a.privince_id,a.city_id,a.shoppointid from ta_g_gw a where a.groupid="+id+") x,\n" +
				"(select s.shop_point_id,s.city_id,s.cmpny_name from ta_shoppoint s) y\n" +
				"where x.city_id(+)=y.city_id\n" +
				") m,(select a.id,a.groupid,a.privince_id,a.city_id,a.shoppointid from ta_g_gw a where a.groupid="+id+") n\n" +
				"where m.shop_point_id=n.shoppointid(+)";
		try {
			coreDAO.executeQuery(sql, "p_shop_city", rd);
			coreDAO.executeQuery(sql2, "p_shop_info", rd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public int p_editShop(BizData rd,BizData sd){
		Connection conn=coreDAO.getConnection();
		String[] rows=rd.getRowIDs("SHOP");
		String groupid=rd.getStringByDI("TA_GROUP_BASE", "ID", 0);
		try{
			coreDAO.beginTrasct(conn);
			if("up".equals(rd.getString("state"))){
				String sql="delete from TA_G_GW a where a.groupid="+groupid;
				coreDAO.executeUpdate(sql, conn);
			}
			if(null!=rows){
				BizData rd2=new BizData();
				for(int c=0;c<rows.length;c++){
					String cityid=rd.getString("SHOP", "CITY_ID", rows[c]);
					String[] rows_shop=rd.getRowIDs("TA_G_GW"+cityid);
					if(null!=rows_shop){
						for(int s=0;s<rows_shop.length;s++){
						int id=this.queryMaxIDByPara("TA_G_GW", "ID", null);
						rd.add("TA_G_GW"+cityid, "ID",rows_shop[s],id);
						rd.add("TA_G_GW"+cityid, "PRIVINCE_ID",rows_shop[s],rd.getString("SHOP", "PRIVINCE_ID", rows[c]));
						rd.add("TA_G_GW"+cityid, "CITY_ID",rows_shop[s],cityid);
						rd.add("TA_G_GW"+cityid, "GROUPID",rows_shop[s],groupid);
						rd2.copyEntity(rd, "TA_G_GW"+cityid, "TA_G_GW");
						}
					}
					coreDAO.insert("TA_G_GW",rd2,conn);
				}
				coreDAO.commitTrasct(conn);
			}
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
