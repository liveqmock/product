/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.execute;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-2
 * Time: 9:37:22
 */
public interface ProcessInsEventListener {
    public void onProcessInsStarted(ProcessInsEvent pie);

    public void onProcessInsFinished(ProcessInsEvent pie);

    public void onProcessInsCanceled(ProcessInsEvent pie);

    public void onProcessInsSuspended(ProcessInsEvent pie);
}
