package com.trm.plan;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class SceneryAttemperBLC extends DBBLC{
	public SceneryAttemperBLC(){
		this.entityName="TA_G_SCENERY";
	}
	
	/**
	 * 根据景点ID查询所有价格
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryPriceByScenery(BizData rd,BizData sd) {
		
		String sceneryID = rd.getString("sceneryID");
		String dwj = rd.getString("dwj");
		String sql = "select s.scenery_id,p.price_type dwj,p.price,d.dmsm1 priceName,p.price_name priceNMC\n" +
				"from ta_scenery_point s,ta_scenery_point_price p,dmsm d\n" +
				"where s.scenery_id=p.scenery_id and d.dmz=p.price_name and d.dmlb=12\n" +
				"and p.scenery_id='"+sceneryID+"' and p.price_type='"+dwj+"'";
		try {
			coreDAO.executeQuery(sql, "rsPriceByScenery", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 432;
	}
	
	public int saveScnery(BizData rd,BizData sd) {
		
		String action = rd.getString("action");//判断是新增还是修改
		Connection conn = coreDAO.getConnection();
		try {
			coreDAO.beginTrasct(conn);
			String groupID = rd.getStringByDI("TA_GROUP_BASE", "ID", 0);
			//更新团表
			coreDAO.update("TA_GROUP_BASE", rd, conn);
			BizData rd2 = new BizData();
			String[] areaIDs = rd.getRowIDs("TA_G_SCENRYXZQH");
			if("edit".equals(action)){
				//根据团号查询
				rd2.add("TA_G_SCENRYXZQH", "GROUPID", groupID);
				coreDAO.select(rd2);//包含了团下面所有的行政区划
				rd2.remove("TA_G_SCENRYXZQH");
				String sql = "delete from TA_G_SCENRYXZQH where GROUPID="+groupID;
				coreDAO.executeUpdate(sql, conn);
				//团景点行政区划ID
				for(int i=0;i<rd2.getTableRowsCount("TA_G_SCENRYXZQHs");i++) {

					String id = rd2.getStringByDI("TA_G_SCENRYXZQHs", "id", i);
					//查询
					rd2.add("TA_G_SCENERY", "AREAID", id);
					coreDAO.select(rd2);
					rd2.remove("TA_G_SCENERY");
					sql = "delete from TA_G_SCENERY where AREAID="+id;
					coreDAO.executeUpdate(sql, conn);
					for(int j=0;j<rd2.getTableRowsCount("TA_G_SCENERYs");j++){
						
						String sId = rd2.getStringByDI("TA_G_SCENERYs", "id", j);
						sql = "delete from TA_G_SCENERY_MNY where G_SCENERYID="+sId;
						coreDAO.executeUpdate(sql, conn);
					}
				}
			}
			for(int k=0;k<areaIDs.length;k++) {
				
				//插入团景点行政区划表
				int xzqhSceneryID = queryMaxIDByPara("TA_G_SCENRYXZQH", "ID", null);
				rd.add("TA_G_SCENRYXZQH", "ID", areaIDs[k], xzqhSceneryID);
				rd.add("TA_G_SCENRYXZQH", "USER_NO", areaIDs[k], sd.getString("userno"));
				String cityID = rd.getString("TA_G_SCENRYXZQH", "CITY_ID", areaIDs[k]);
				//插团景点表
				String[] rowIDs = rd.getRowIDs("TA_G_SCENERY"+cityID);
				int tmp = 0;
				for(int i=0;i<rowIDs.length;i++) {

					//景点ID，页面中的checkbox
					String sceneryID = rd.getString("TA_G_SCENERY"+cityID, "SCENERY_ID", rowIDs[i]);
					if("".equals(sceneryID))
						continue;
					int gSceneryID = queryMaxIDByPara("TA_G_SCENERY", "ID", null);//团景点主键
					rd2.add("TA_G_SCENERY", "ID", tmp, gSceneryID);
					//团ID
					rd2.add("TA_G_SCENERY", "GROUPID", tmp, groupID);
					//景点行政区划ID
					rd2.add("TA_G_SCENERY", "AREAID", tmp, xzqhSceneryID);
					//现付金额小计
					String qd = rd.getString("QDJE"+sceneryID);
					//签单金额小计
					String xf = rd.getString("XFJE"+sceneryID);
					rd2.add("TA_G_SCENERY", "XFJE", tmp, xf);
					rd2.add("TA_G_SCENERY", "QDJE", tmp, qd);
					//景点ID
					rd2.add("TA_G_SCENERY", "SCENERY_ID", tmp, rd.getString("TA_G_SCENERY"+cityID, "SCENERY_ID", rowIDs[i]));
					//淡旺季
					rd2.add("TA_G_SCENERY", "DWJ", tmp, rd.getString("dwj"+sceneryID));
					//业务员
					rd2.add("TA_G_SCENERY", "YWYXM", tmp, rd.getString("TA_G_SCENERY"+cityID, "YWYXM", rowIDs[i]));
					//业务员电话
					rd2.add("TA_G_SCENERY", "YWYDH", tmp, rd.getString("TA_G_SCENERY"+cityID, "YWYDH", rowIDs[i]));
					//备注
					rd2.add("TA_G_SCENERY", "COMMENTS", tmp, rd.getString("TA_G_SCENERY"+cityID, "COMMENTS", rowIDs[i]));
					//景点实施价格记录数
					String[] sceneryPriceRowIDs = rd.getRowIDs("TA_G_SCENERY_MNY_"+sceneryID);
					int persons = 0;
					int tmp2 = 0;
					for(int j=0;j<sceneryPriceRowIDs.length;j++) {
						
						String priceNameID = rd.getString("TA_G_SCENERY_MNY_"+sceneryID, "PRICENAMEID", sceneryPriceRowIDs[j]);
						String price = rd.getString("TA_G_SCENERY_MNY_"+sceneryID, "PRICES", sceneryPriceRowIDs[j]);
						String pCount = rd.getString("TA_G_SCENERY_MNY_"+sceneryID, "PERSONS", sceneryPriceRowIDs[j]);
						//景点实施价格表主键
						int priceID = queryMaxIDByPara("TA_G_SCENERY_MNY", "ID", null);
						int personCounts = Integer.parseInt(pCount);
						persons += personCounts;
						rd2.add("TA_G_SCENERY_MNY", "ID", tmp2, priceID);
						rd2.add("TA_G_SCENERY_MNY", "G_SCENERYID", tmp2, gSceneryID);
						rd2.add("TA_G_SCENERY_MNY", "PRICENAMEID", tmp2, priceNameID);
						rd2.add("TA_G_SCENERY_MNY", "PRICES", tmp2, price);
						rd2.add("TA_G_SCENERY_MNY", "PERSONS", tmp2, pCount);
						tmp2 ++;
					}
					//插景点价格表
					coreDAO.insert("TA_G_SCENERY_MNY", rd2, conn);
					rd2.remove("TA_G_SCENERY_MNY");
					rd2.add("TA_G_SCENERY", "SJYYRS", tmp, persons);
					tmp ++;
				}
				coreDAO.insert("TA_G_SCENERY", rd2, conn);
				rd2.remove("TA_G_SCENERY");
			}
			rd2 = null;
			coreDAO.insert("TA_G_SCENRYXZQH",rd,conn);
			coreDAO.commitTrasct(conn);
		} catch (SQLException e) {
			try {
				coreDAO.rollbackTrasct(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try{
				if(null != conn){
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 234;
	}
	
	/**
	 * 查询该团的计调实施记录
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryForUpdate(BizData rd,BizData sd) {
		
		String groupID = rd.getString("id");
		String sql = "select a.scenery_id scIDbase,b.scenery_id checkedSCID,a.cmpny_name,a.city_id,a.business_name,a.business_mobile,\n" +
				"b.id,b.xfje,b.qdje,b.sjyyrs,b.ywydh,b.ywyxm,b.comments,b.dwj from (\n" +
				"select p.scenery_id,p.cmpny_name,p.city_id,p.business_name,p.business_mobile \n" +
				"from ta_scenery_point p,ta_g_scenryxzqh x \n" +
				"where p.city_id=x.city_id \n" +
				"and x.groupid="+groupID+
				" )a, (\n" +
				"select * from ta_g_scenery s\n" +
				") b\n" +
				"where a.scenery_id=b.scenery_id(+)\n" +
				"order by b.scenery_id";
		try {
			//景点实施的行政区划记录
			coreDAO.select(rd);
			//行政区划下的所有景点
			coreDAO.executeQuery(sql, "rsALLGroupScenery", rd);
			sql = "select m.*,s.id gsid,d.dmsm1 priceNM,s.qdje,s.xfje\n" +
					"from ta_g_scenryxzqh x,TA_G_SCENERY s,TA_G_SCENERY_MNY m,dmsm d\n" +
					"where x.id=s.areaid and m.g_Sceneryid=s.id\n" +
					"and m.pricenameid=d.dmz and d.dmlb=12\n" +
					"and x.groupid="+groupID;
			coreDAO.executeQuery(sql, "rsALLPriceOfScenery", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 824;
	}
}
