/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.execute;

import com.dream.bizsdk.common.workflow.definition.WFTransition;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-4-1
 * Time: 17:35:18
 */
public class TransitionIns {
    public final static int TRST_ENABLED = 1;
    public final static int TRST_DISABLED = 2;

    private static int NEXTID = 1;

    private int id;

    private int status = TransitionIns.TRST_ENABLED;

    private WFTransition trstDef;

    private boolean _isNew = true;

    public TransitionIns(WFTransition trst) {
        this.trstDef = trst;
        this.id = TransitionIns.getNextID();
    }

    public TransitionIns(WFTransition trst, int status) {
        this.trstDef = trst;
        this.id = TransitionIns.getNextID();
        this.status = status;
    }

    public WFTransition getTransitionDef() {
        return this.trstDef;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getID() {
        return this.id;
    }

    public boolean isNew() {
        return _isNew;
    }

    public void setNew(boolean isNew) {
        this._isNew = isNew;
    }

    /**
     * get nextID for this instance;
     *
     * @return
     */
    synchronized private static int getNextID() {
        return TransitionIns.NEXTID++;
    }
}
