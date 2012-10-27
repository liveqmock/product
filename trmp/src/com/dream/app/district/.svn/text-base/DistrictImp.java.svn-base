/**
 * 
 */
package com.dream.app.district;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Divine
 *
 */
public class DistrictImp implements DistrictItfc {
	
	/**
	 * 根据ID查询所有的子孙ID
	 * @param flag 遍历的依据关键字，如ID
	 * @param beans bean的集合
	 */
	public List<DistrictBean> adownDistrict(String id, List<DistrictBean> beans){
		
		List<DistrictBean> allBeans = new ArrayList<DistrictBean>();
		recursionDistrict(id, beans, allBeans);
		
		return allBeans;
	}
	
	
	private void recursionDistrict(String id, List<DistrictBean> beans, List<DistrictBean> allBeans){
		
		List<DistrictBean> thisTimeAdded = new ArrayList<DistrictBean>();
		for(int i=0;i<beans.size();i++) {
			
			DistrictBean bean = (DistrictBean)beans.get(i);
			String pId = bean.getpId();
			if(id.equals(pId)){
				allBeans.add(bean);
				thisTimeAdded.add(bean);
			}
		}
		if(thisTimeAdded.size()>0) {
			for(int i=0;i<thisTimeAdded.size();i++) {
				
				DistrictBean bean = (DistrictBean)thisTimeAdded.get(i);
				String thisId = bean.getId();
				recursionDistrict(thisId, beans, allBeans);
			}
		}
	}
	
	public String upDistrict(String id, String endId, List<DistrictBean> beans){
		
		for(int i=0; i<beans.size(); i++) {
			
			DistrictBean bean = (DistrictBean)beans.get(i);
			String thisId = bean.getId();
			if(id.equals(thisId)){
				String thisPId = bean.getpId();
				if(thisPId.equals(endId))
					return thisId;
				else
					return upDistrict(thisPId, endId, beans);
			}
		}
		return "";
	}
	
	
	public static void main(String[] a){
		
		List<DistrictBean> beans = new ArrayList<DistrictBean>();
		DistrictBean bean = new DistrictBean();
		bean.setId("1");
		bean.setpId("0");
		bean.setName("江苏省");
		beans.add(bean);
		bean = new DistrictBean();
		bean.setId("2");
		bean.setpId("1");
		bean.setName("南京市");
		beans.add(bean);
		bean = new DistrictBean();
		bean.setId("3");
		bean.setpId("1");
		bean.setName("无锡市");
		beans.add(bean);
		bean = new DistrictBean();
		bean.setId("4");
		bean.setpId("2");
		bean.setName("玄武区");
		beans.add(bean);
		bean = new DistrictBean();
		bean.setId("5");
		bean.setpId("3");
		bean.setName("崇安区");
		beans.add(bean);
		bean = new DistrictBean();
		bean.setId("6");
		bean.setpId("2");
		bean.setName("雨花区");
		beans.add(bean);
		bean = new DistrictBean();
		bean.setId("7");
		bean.setpId("4");
		bean.setName("月苑小区");
		beans.add(bean);
		DistrictItfc district = new DistrictImp();
		String s = district.upDistrict("2", "0", beans);
		System.out.println(s);
	}


}
