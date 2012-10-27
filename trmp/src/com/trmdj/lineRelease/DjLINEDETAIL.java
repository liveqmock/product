package com.trmdj.lineRelease;

import org.apache.log4j.Logger;

import com.dream.bizsdk.common.blc.DBBLC;

public class DjLINEDETAIL extends DBBLC {
	private static Logger log = Logger.getLogger(DjLINEDETAIL.class);
	public DjLINEDETAIL() {
		this.entityName = "TA_DJ_LINEDETAIL";
	}
}