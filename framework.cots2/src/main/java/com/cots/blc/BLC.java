/**
 *all rights reserved,@copyright 2003
 */
package com.cots.blc;

/**
 * Description:
 *      All BLC Ojbect must implement this interface directly or indirectly. The only method
 * init(BLContext context) is called only once at time of being created.
 *      All the bl method in a BLC class should be thread safe. otherwise, unexpected result may
 * occur.
 *
 * User: chugh
 * Date: 2004-10-10
 * Time: 13:25:57
 * Version: 1.0
 */
public interface BLC {

    /**
     * called by the framework when the BLC object is created.
     *
     * @param context the BLContext within which this BLC object will work in.
     */
    void init(BLContext context);

    /**
     * called by the framework when the BLC object should be released.
     */
    void destroy();
}
