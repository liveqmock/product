/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.event;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-24
 * Time: 14:52:43
 */
public interface EventListener {
    public void onEvent(Event e);

    public String getName();
}
