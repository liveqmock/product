/**
 *
 *
 */
package com.dream.app.sys;

/**
 *
 *
 *
 */

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.blc.BLRequests;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.util.string.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import java.sql.SQLException;

public class Function extends DBBLC {
    public Function() {
        entityName = "DRMFUNCTION";
    }

    public int rewrite(BizData rd, BizData sd) throws SQLException {
        coreDAO.executeUpdate("delete from DRMFUNCTION", (BizData) null);
        HttpServletRequest req = (HttpServletRequest) rd.get("_httpservletrequest");
        ServletContext sc = req.getSession().getServletContext();

        String path = sc.getRealPath("/WEB-INF/requests");
        path = StringUtil.checkPath(path);
        try {
            BLRequests requests = new BLRequests(path);
            BizData d = requests.getReqs();
            coreDAO.insert(d);
            return 0;
        } catch (Exception e) {
            return SysError.DB_ERROR;
        }
    }

}