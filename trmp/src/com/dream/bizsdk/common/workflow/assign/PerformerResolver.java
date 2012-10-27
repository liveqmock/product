/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.assign;

import com.dream.bizsdk.common.workflow.definition.WFParticipant;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-13
 * Time: 13:06:28
 */
public interface PerformerResolver {
    public String resolve(WFParticipant p);
}