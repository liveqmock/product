/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.assign;

import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.workflow.definition.WFParticipant;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-13
 * Time: 13:32:15
 */
public class PerformerResolverImpl implements PerformerResolver {
    IBLContext context;

    public PerformerResolverImpl() {

    }

    public PerformerResolverImpl(IBLContext context) {
        this.context = context;
    }

    /**
     * @return
     */
    public IBLContext getContext() {
        return context;
    }

    /**
     * @param context
     */
    public void setContext(IBLContext context) {
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