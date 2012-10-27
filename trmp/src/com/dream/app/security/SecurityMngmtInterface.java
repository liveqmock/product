/**
 * 
 */
package com.dream.app.security;

import com.dream.bizsdk.common.database.dao.DAO;

/**
 * @author divine
 *
 */
public interface SecurityMngmtInterface {

	/**
	 * 
	 * @return
	 */
	public void compareMac();
	public void compareDiskSerialNm();
	public boolean compareUserCounts(DAO dao,int counts);
}
