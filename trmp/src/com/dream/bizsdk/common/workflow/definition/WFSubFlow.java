/**
 * copyright 2004,primton ltd.All rights reserved.
 */


package com.dream.bizsdk.common.workflow.definition;

/**
 * Module:  WFSubFlow.java
 * <p/>
 * Description:
 * WFSubFlow class represents a subflow acitivity in a  workflow
 * definition. The workflow execute engine should create a new workflow
 * instance according to the subflow name. If asynExec is true,
 * then after the subflow is created, this activity is considered to be
 * finished, otherwise it stays in runing state until the sub
 * workflow is finished.
 * <p/>
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 10:27:35
 * Purpose: Defines the Class WFSubFlow
 */

public class WFSubFlow extends WFAbstractActivity {
    /**
     * the name of this subflow activity;
     */
    protected String subflowName;

    /**
     * the execution mode of the subflow;
     */
    protected boolean asynExec;

    public WFSubFlow() {

    }

    public WFSubFlow(WFProcess process) {
        super(process);
    }

    public WFSubFlow(String subflowName) {
        this.subflowName = subflowName;
    }

    public WFSubFlow(String subflowName, boolean asynExec) {
        this.subflowName = subflowName;
        this.asynExec = asynExec;
    }

    /**
     * get the name of the sub workflow;
     *
     * @return the name of the subworkflow;
     */
    public String getSubflowName() {
        return this.subflowName;
    }

    /**
     * get the execution mode of this sub workflow;
     *
     * @return the execution mode;
     */
    public boolean isAsynExec() {
        return this.asynExec;
    }

    /**
     * set the name of the sub workflow;
     *
     * @param subflowName the sub workflow;
     */
    void setSubflowName(String subflowName) {
        this.subflowName = subflowName;
    }

    /**
     * set the subworkflow  execution mode;
     *
     * @param asynExec the execution mode;
     */
    void setAsynExec(boolean asynExec) {
        this.asynExec = asynExec;
    }
}