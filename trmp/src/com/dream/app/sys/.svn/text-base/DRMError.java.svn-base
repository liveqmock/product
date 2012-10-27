/**
 *all rights reserved,@copyright 2003
 */
package com.dream.app.sys;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysError;

import java.sql.SQLException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-25
 * Time: 9:24:35
 */
public class DRMError extends DBBLC {
    public DRMError() {
        entityName = "DRMError";
    }

    /**
     * save an error message;
     *
     * @param rd
     * @param sd
     * @return
     */
    public int save(BizData rd, BizData sd) throws SQLException {
        if (rd.getTableRowsCount(entityName) != 1) {
            return SysError.BL_PARAM_ERROR;
        }
        String errCode = (String) rd.get(entityName, "errCode", "0");
        if (errCode == null) {
            return SysError.BL_PARAM_ERROR;
        }
        BizData d = new BizData();
        d.add(entityName, "errCode", "0", errCode);
        d.addRowAttr(entityName, "0", "DML", "delete");
        d.add(entityName, "errCode", "1", errCode);
        d.add(entityName, "errLevel", "1", (String) rd.get(entityName, "errLevel", "0"));
        d.add(entityName, "errMessage", "1", (String) rd.get(entityName, "errMessage", "0"));
        d.addRowAttr(entityName, "1", "DML", "insert");
        return coreDAO.dmTable(entityName, d);
    }
}
