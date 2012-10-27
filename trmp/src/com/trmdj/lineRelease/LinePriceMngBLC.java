package com.trmdj.lineRelease;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;


/**
 * @ClassName: LinePriceMngBLC
 * @Description: TODO(线路管理-查看线路-发布线路-修改线路等信息)
 * @author hejiantx@163.com
 * @date 2011-7-1 下午03:07:17
 *
 */
public class LinePriceMngBLC extends DBBLC {
	private static Logger log = Logger.getLogger(LinePriceMngBLC.class);

	public LinePriceMngBLC() {
		this.entityName = "TA_DJ_XLJG";
	}


	
}
