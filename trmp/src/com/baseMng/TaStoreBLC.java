package com.baseMng;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;

public class TaStoreBLC extends DBBLC {
	
	private Logger log = null;
	public TaStoreBLC(){		
		this.log = Logger.getLogger(TaStoreBLC.class);
		this.entityName = "ta_store";		
	}
	
}
