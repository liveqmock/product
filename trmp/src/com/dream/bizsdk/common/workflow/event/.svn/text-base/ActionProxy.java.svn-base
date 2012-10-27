/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.event;

import com.dream.bizsdk.common.databus.BizData;

/**
 * Description:
 * An object of this class acts as a proxy between the workflow
 * system and an action. The action is a method that will be executed
 * on an event is fired.
 * Actions can have different types. Generallly, a type of action
 * has a Proxy object.
 * <p/>
 * User: chuguanghua
 * Date: 2004-5-12
 * Time: 10:13:56
 * version 1.0
 */
public class ActionProxy {

    /**
     * exec the target action method; this method may be a java method of a class
     * or web service.
     *
     * @param action the Action object to be called;
     * @param data   the BizData object that contains the parameters to call the method.
     */
    public void exec(AbstractAction action, BizData data) {
        action.run(data);
    }
}
