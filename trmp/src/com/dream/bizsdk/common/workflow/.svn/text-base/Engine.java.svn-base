/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow;

import com.dream.bizsdk.common.workflow.assign.AssignEngine;
import com.dream.bizsdk.common.workflow.assign.WorkitemDAO;
import com.dream.bizsdk.common.workflow.execute.ExecuteEngine;
import com.dream.bizsdk.common.workflow.execute.ActivityInsDAO;
import com.dream.bizsdk.common.workflow.execute.ProcessInsDAO;
import com.dream.bizsdk.common.workflow.event.ECAManager;
import com.dream.bizsdk.common.database.dao.DAO;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-2
 * Time: 9:53:33
 */
public class Engine {

    /*assign engine*/
    private AssignEngine ae;

    /*execute engine*/
    private ExecuteEngine ee;

    /**
     * eca manager;
     */
    private ECAManager ecam;

    /**
     * dao of the workflow systems;
     */
    private DAO dao;

    /*home directory that will contains the workflow definition file*/
    private String definitionHome;

    public Engine() {

    }

    public Engine(ExecuteEngine ee, AssignEngine ae, ECAManager ecam) {
        this.ae = ae;
        this.ee = ee;
        this.ecam = ecam;
    }

    /**
     * @return
     */
    public ExecuteEngine getExecuteEngine() {
        return this.ee;
    }

    /**
     * @param ee
     */
    public void setExecuteEngine(ExecuteEngine ee) {
        this.ee = ee;
    }

    /**
     * @return
     */
    public AssignEngine getAssignEngine() {
        return this.ae;
    }

    /**
     * @param ae
     */
    public void setAssignEngine(AssignEngine ae) {
        this.ae = ae;
    }

    /**
     * @return
     */
    public ECAManager getECAManager() {
        return this.ecam;
    }

    /**
     * @param ecam
     */
    public void setECAManager(ECAManager ecam) {
        this.ecam = ecam;
    }

    /**
     * @return
     */
    public DAO getDAO() {
        return this.dao;
    }

    /**
     * @param dao
     */
    public void setDAO(DAO dao) {
        this.dao = dao;
    }

    /**
     * @return
     */
    public String getDefinitionHome() {
        return this.definitionHome;
    }

    /**
     * @param home
     */
    public void setDefinitionHome(String home) {
        this.definitionHome = home;
    }

    /**
     * initialize this engine for running;
     */
    public void init() {

        ee.setActivityInsDAO(new ActivityInsDAO(dao));

        ee.setProcessInsDAO(new ProcessInsDAO(dao));

        ae.setWorkitemDAO(new WorkitemDAO(dao));

        //add Assign Engine to the Execute engine as the activity event listener;
        ee.addActivityInsEventListener(ae);

        //add the execute engine to the assigne as the workitem event listener;
        ae.addWorkitemEventListener(ee);

        //add the ecam to the Execute engine as the activity event listener;
        ecam.registerToEngine(ee);
    }

    /**
     * start this engine;
     */
    public void start() {

    }
}