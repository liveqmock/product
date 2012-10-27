/**
 * 
 */
package com.dream.app.district;

import java.util.List;


/**
 * @author Divine
 *
 */
public interface DistrictItfc {
	
	/**
	 * 根据ID查询所有的子孙ID
	 * @param flag 遍历的依据关键字，如ID
	 * @param beans bean的集合
	 */
	public List<DistrictBean> adownDistrict(String id, List<DistrictBean> beans);
	
	/**
	 * 根据ID向上递归得到顶级ID
	 * @param id
	 * @param beans
	 * @return
	 */
	public String upDistrict(String id, String endId, List<DistrictBean> beans);
	
	
}
