/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.assign;

import com.cots.blc.BLContext;
import com.cots.wf.definition.WFParticipant;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-13
 * Time: 13:32:15
 */
public class PerformerResolverImpl implements PerformerResolver {
    BLContext context;

    public PerformerResolverImpl() {

    }

    public PerformerResolverImpl(BLContext context) {
        this.context = context;
    }

    /**
     * @return
     */
    public BLContext getContext() {
        return context;
    }

    /**
     * @param context
     */
    public void setContext(BLContext context) {
        this.context = context;
    }

    /**
     * @param p
     * @return
     */
    public String resolve(WFParticipant p) {
        return p.getUrl();
    }
}