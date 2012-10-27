package com.trmdj.lineRelease;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;

public class FBJHTMPBLC extends DBBLC {
	private static Logger log = Logger.getLogger(FBJHTMPBLC.class);

	public FBJHTMPBLC() {
		this.entityName = "TA_DJ_FBJHTMP";
	}

	
}
