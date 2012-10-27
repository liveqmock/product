package com.trm.plan;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class RestaurantAttemperBLC extends DBBLC{
	public RestaurantAttemperBLC(){
		this.entityName="TA_G_SCENERY";
	}
	
	/**
	 * 根据景点ID查询所有价格
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryPriceByScenery(BizData rd,BizData sd){
		
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
	
	/**
	 * AJAX请求的餐标查询
	 * @param rd
	 * @param sd
	 * @return
	 */
	public int queryCB(BizData rd,BizData sd){
		
		String restaurantID = rd.getString("id");
		String sql = "select a.dmsm1 dinnerCB,b.dmsm1 bfCB from (\n" +
				"select t.Dinner_Level,d.dmsm1\n" +
				"from ta_dining_room t,dmsm d\n" +
				"where t.DINNER_LEVEL=d.dmz and d.dmlb=9\n" +
				"and t.dining_room_id="+restaurantID+"\n" +
				")a, (\n" +
				"select t.BREAKFAST_LEVEL,d.dmsm1\n" +
				"from ta_dining_room t,dmsm d\n" +
				"where t.Breakfast_Level=d.dmz and d.dmlb=9\n" +
				"and t.dining_room_id="+restaurantID+"\n" +
				")b";
		try {
			coreDAO.executeQuery(sql, "rsForAjAX", rd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 342;
	}
}
