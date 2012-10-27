package com.trm.util;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

/**删除计划表 报销表信息
 * @author Administrator
 *
 */
public class DeleteTableBLC extends DBBLC {
	/**删除地接计划表信息
	 * @param rd
	 * @param sd
	 * @throws SQLException
	 */
	public void deleteDjTables(BizData rd,BizData sd) throws SQLException{
		String tabName = rd.getString("tabName");
		String groupId = rd.getString("groupId");
		BizData ct=new BizData();
		if("djPlanHotel".equals(tabName)){//删除计划酒店
			ct.add("TA_DJ_JHHOTEL", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHHOTEL");
			ct.add("TA_DJ_JHHOTELJG", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHHOTELJG");
		}else if("djPlanDinner".equals(tabName)){//删除餐厅计划
			ct.add("TA_DJ_JHCT", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHCT");
		}else if("djPlanTicket".equals(tabName)){//删除票务计划
			ct.add("TA_DJ_JHPW", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHPW");
			ct.add("TA_DJ_JHPWMX", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHPWMX");
		}else if("djPlanCar".equals(tabName)){//删除车辆计划
			ct.add("TA_DJ_JHCL", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHCL");
		}else if("djPlanTrave".equals(tabName)){//删除地接计划
			ct.add("TA_DJ_JHDJ", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHDJ");
		}else if("djPlanScenery".equals(tabName)){//删除景点计划
			ct.add("TA_DJ_JHJD", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHJD");
			ct.add("TA_DJ_JHJDJG", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHJDJG");
		}else if("djPlanPlus".equals(tabName)){//删除加点计划
			ct.add("TA_DJ_JHJIAD", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHJIAD");
			ct.add("TA_DJ_JHJIADJD", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHJIADJD");
		}else if("djPlanShop".equals(tabName)){//删除购物计划
			ct.add("TA_DJ_JHGW", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHGW");
			ct.add("TA_DJ_GWRTF", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_GWRTF");
		}else if("djPlanGuide".equals(tabName)){//删除导游计划
			ct.add("TA_DJ_JHDY", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_DJ_JHDY");
		}else if("djPlanOther".equals(tabName)){//删除其他计划
			
		}
		rd.add("groupId", groupId);
	}
	
	
	/**删除组团计划表信息
	 * @param rd
	 * @param sd
	 * @throws SQLException
	 */
	public void deleteZtTables(BizData rd,BizData sd) throws SQLException{
		String tabName = rd.getString("tabName");
		String groupId = rd.getString("groupId");
		BizData ct=new BizData();
		if("ztPlanHotel".equals(tabName)){//删除计划酒店
			ct.add("TA_ZT_JHHOTEL", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHHOTEL");
			ct.add("TA_ZT_JHHOTELJG", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHHOTELJG");
		}else if("ztPlanDinner".equals(tabName)){//删除餐厅计划
			ct.add("TA_ZT_JHCT", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHCT");
		}else if("ztPlanTicket".equals(tabName)){//删除票务计划
			ct.add("TA_ZT_JHPW", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHPW");
			ct.add("TA_ZT_JHPWMX", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHPWMX");
		}else if("ztPlanCar".equals(tabName)){//删除车辆计划
			ct.add("TA_ZT_JHCL", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHCL");
		}else if("ztPlanTrave".equals(tabName)){//删除地接计划
			ct.add("TA_ZT_JHDJ", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHDJ");
		}else if("ztPlanScenery".equals(tabName)){//删除景点计划
			ct.add("TA_ZT_JHJD", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHJD");
			ct.add("TA_ZT_JHJDJG", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHJDJG");
		}else if("ztPlanPlus".equals(tabName)){//删除加点计划
			ct.add("TA_ZT_JHJIAD", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHJIAD");
			ct.add("TA_ZT_JHJIADJD", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHJIADJD");
		}else if("ztPlanShop".equals(tabName)){//删除购物计划
			ct.add("TA_ZT_JHGW", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHGW");
			ct.add("TA_ZT_GWRTF", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_GWRTF");
		}else if("ztPlanGuide".equals(tabName)){//删除导游计划
			ct.add("TA_ZT_JHDY", "TID", groupId);
			coreDAO.delete(ct);
			ct.remove("TA_ZT_JHDY");
		}else if("ztPlanOther".equals(tabName)){//删除其他计划
			
		}
		rd.add("groupId", groupId);
	}
}
