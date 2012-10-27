/**
 *all rights reserved,@copyright 2003
 */
package com.dream.app.common;

import com.dream.bizsdk.common.blc.AbstractBLC;
import com.dream.bizsdk.common.blc.BLContext;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.database.dao.DAO;

import java.sql.SQLException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-9-11
 * Time: 14:40:54
 */
public class DB extends AbstractBLC {
    private DAO dao;

    /**
     * initialize this BLC object;
     *
     * @param context
     * @return
     */
    public boolean init(BLContext context) {
        boolean retVal = super.init(context);
        dao = context.getDAO("core");
        return retVal;
    }

    /**
     * @param rd
     * @param sd
     * @throws SQLException
     */
    public int insert(BizData rd, BizData sd) throws SQLException {
        return dao.insert(rd);
    }

    /**
     * @param rd
     * @param sd
     * @throws SQLException
     */
    public int select(BizData rd, BizData sd) throws SQLException {
        return dao.select(rd, rd, sd);
    }

    /**
     * @param rd
     * @param sd
     * @throws SQLException
     */
    public int delete(BizData rd, BizData sd) throws SQLException {
        return dao.delete(rd, sd);
    }

    /**
     * @param rd
     * @param sd
     * @throws SQLException
     */
    public int update(BizData rd, BizData sd) throws SQLException {
        return dao.update(rd, sd);
    }

    /**
     * @param rd
     * @param sd
     * @throws SQLException
     */
    public int dmTable(BizData rd, BizData sd) throws SQLException {
        return dao.dm(rd, sd);
    }
}