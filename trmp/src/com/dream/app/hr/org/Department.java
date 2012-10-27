
package com.dream.app.hr.org;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;


public class Department extends DBBLC {

    public Department() {
        entityName = "HRDEPARTMENT";
    }
    
    public int query(BizData rd,BizData sd){
    	
    	try {
			super.query(rd, sd);
    		//rd.add("HRDEPARTMENTs", "pdeptid", rd.getStringByDI("HRDEPARTMENT", "pdeptid", 0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 98;
    }
    
    public int update(BizData rd,BizData sd){
    	
		try {
			String deptId = rd.getStringByDI("HRDEPARTMENT", "deptId", 0);
			if(deptId.equals(""))
				deptId = "-1";
			String orgId = sd.getString("orgid");
			
			BizData data = new BizData();
			data.add("HRDEPARTMENT", "deptId", deptId);
			data.add("HRDEPARTMENT", "orgid", orgId);
			int rows = coreDAO.select(data);
			if (rows > 0)
				super.update(rd, sd);
			else {
				if(deptId.equals("-1")){
					rd.add("HRDEPARTMENT", "deptid", queryMaxIDByPara("HRDEPARTMENT", "deptid", null));
					rd.add("HRDEPARTMENT", "pdeptid", rd.getString("pdeptid"));
				}
				rd.add("HRDEPARTMENT", "orgid", orgId);
				rd.removeAttr("HRDEPARTMENT", "deptid", "0", "oldValue");
				super.insert(rd, sd);
			}
			rd.add("msg", "Y");
			rd.add("fwd", rd.getString("fwd"));
			rd.add("HRDEPARTMENT", "orgid", sd.getString("orgid"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 98;
    }
    
    public int queryMaxDeptID(BizData rd,BizData sd){
    	int maxDeptId = queryMaxIDByPara("HRDEPARTMENT", "deptid", null);
    	rd.add("newDeptId", maxDeptId);
    	return 98;
    }
    
    public int insert(BizData rd,BizData sd){
    	
    	return 98;
    }
}