package com.dream.app.wl;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

import java.sql.SQLException;

/**
 * <p>Title: engine</p>
 * <p>Description: This is the platform of the business development kit.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: dream</p>
 *
 * @author chuguanghua
 * @version 1.0
 */

public class Project extends DBBLC {

    public Project() {
        entityName = "WLProject";
    }

    public int queryByPMID(BizData rd, BizData sd) throws SQLException {
        rd.add(entityName, "pmID", sd.getString("empID"));
        return super.query(rd, (BizData) null);
    }

}