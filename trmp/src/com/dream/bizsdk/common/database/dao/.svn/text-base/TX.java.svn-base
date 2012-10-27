/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.database.dao;

import javax.transaction.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-5-20
 * Time: 8:20:07
 */
public class TX {
    private UserTransaction utx;

    public TX(UserTransaction utx) {
        this.utx = utx;
    }

    /**
     * begin a transaction.
     *
     * @throws NotSupportedException
     * @throws SystemException
     */
    public void begin() throws NotSupportedException, SystemException {
        utx.begin();
    }

    /**
     * commit a transaction;
     *
     * @throws RollbackException
     * @throws HeuristicMixedException
     * @throws HeuristicRollbackException
     * @throws SystemException
     */
    public void commit() throws RollbackException, HeuristicMixedException,
            HeuristicRollbackException, SystemException {
        utx.commit();
    }

    /**
     * rollback a transaction;
     *
     * @throws SystemException
     */
    public void rollback() throws SystemException {
        utx.rollback();
    }

    /**
     * get the UserTransaction associated with the current TX object;
     *
     * @return
     */
    public UserTransaction getUserTransaction() {
        return utx;
    }
}
