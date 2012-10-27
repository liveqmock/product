package com.dream.app.wl;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysError;

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

public class PrjType extends DBBLC {

    public PrjType() {
        entityName = "WLPrjType";
    }

    public int save(BizData rd, BizData sd) throws SQLException {
        int rows = 0;
        rows = rd.getTableRowsCount(entityName);
        if (rows < 1) {
            return SysError.BL_PARAM_ERROR;
        }
        String typeID = (String) rd.get(entityName, "typeID", 0);
        if (typeID == null) {
            return SysError.BL_PARAM_ERROR;
        }
        BizData d = new BizData();
        d.add(entityName, "typeID", typeID);
        if (coreDAO.expand(entityName, d) < 1) {
            rows = coreDAO.insert(entityName, rd);
        } else {
            rd.addAttr(entityName, "typeID", 0, "oldValue", typeID);
            rows = coreDAO.update(entityName, rd);
        }
        if (rows < 1) {
            return SysError.SAVE_ERROR;
        } else {
            return rows;
        }
    }

    public int delete(BizData rd, BizData sd) throws SQLException {
        rd.copyEntity(entityName + "s", entityName);
        return super.delete(rd, (BizData) null);
    }

    public int queryByPrj(BizData rd, BizData sd) throws SQLException {
        String typeID = (String) rd.get("WLProject", "typeID", 0);
        if (typeID == null) {
            return queryAll(rd, (BizData) null);
        } else {
            BizData d = new BizData();
            d.add(entityName, "typeID", 0, typeID);
            return coreDAO.select(rd, d, true);
        }
    }
}