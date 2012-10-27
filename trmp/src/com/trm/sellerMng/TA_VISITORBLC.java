package com.trm.sellerMng;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;

public class TA_VISITORBLC extends DBBLC {
	
	private Logger log = null;
	public TA_VISITORBLC(){		
		this.log = Logger.getLogger(TA_VISITORBLC.class);
		this.entityName = "TA_ZT_VISITOR";		
	}
	
	
}
