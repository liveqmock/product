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
 * Date: 2003-12-13
 * Time: 13:58:45
 */
public class BDC extends DBBLC {
    public BDC() {
        entityName = "DRMBizDataDict";
    }

    /**
     * @param data
     * @return
     */
    public int save(BizData data, BizData sd) throws SQLException {
        int count = 1;
        String tableName = (String) data.get(entityName, "tableName", 0);
        String colName = (String) data.get(entityName, "colName", 0);
        if (tableName == null || colName == null) {
            return SysError.BL_PARAM_ERROR;
        }

        BizData d = new BizData();
        String rs = entityName + "s";
        String[] rowIDs = data.getRowIDs(rs);
        int rows = rowIDs.length;
        d.add(entityName, "tableName", 0, tableName);
        d.add(entityName, "colName", 0, colName);
        d.addRowAttr(entityName, "0", "DML", "delete");
        for (int i = 1; i <= rows; i++) {
            String code = (String) data.get(rs, "colCode", rowIDs[i - 1]);
            String value = (String) data.get(rs, "colValue", rowIDs[i - 1]);
            if (code != null && value != null) {
                d.add(entityName, "tableName", count, tableName);
                d.add(entityName, "colName", count, colName);
                d.add(entityName, "colCode", count, code);
                d.add(entityName, "colValue", count, value);
                d.addRowAttr(entityName, new Integer(count).toString(), "DML", "insert");
                count++;
            }
        }
        return coreDAO.dmTable(entityName, d);
    }
}
