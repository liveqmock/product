/**
 *all rights reserved,@copyright 2003
 */
package com.cots.blc;

/**
 * Description:
 *      This class provides a base implementation of the com.cots.blc.BLC interface.
 *
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 15:31:10
 * Version: 1.0
 */
public abstract class AbstractBLC implements BLC{

    protected BLContext context;    

    /**
     * initialize this BLC Object;
     *
     * @param context the Context for this BLC object;
     */
    public void init(BLContext context) {
        this.context = context;
    }

    /**
     * do nothis at this level.
     */
    public void destroy(){

    }
}
