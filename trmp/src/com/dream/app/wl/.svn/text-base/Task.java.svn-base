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

public class Task extends DBBLC {

    public Task() {
        entityName = "WLTask";
    }

    public int queryByPrj(BizData rd, BizData sd) throws SQLException {
        String prjNO = (String) rd.get("WLProject", "prjNO", 0);
        if (prjNO == null) {
            return queryAll(rd, sd);
        } else {
            BizData d = new BizData();
            d.copyEntity(rd, entityName);
            d.add(entityName, "prjNO", 0, prjNO);
            return coreDAO.select(rd, d, true);
        }
    }

}