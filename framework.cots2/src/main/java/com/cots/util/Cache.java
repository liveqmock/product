/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-1-23
 * Time: 13:48:51
 * Version: 1.0
 */
public interface Cache {

    /**
     * initialize this cache.
     *
     * @param root the root path;
     * @return true if refresh this caceh successfully. false otherwise.
     */
    boolean init(String root);

    /**
     * refresh this cache;
     *
     * @return true of refresh the cache successfully, false otherwise;
     */
    boolean refresh() throws RefreshFailedException;

}
