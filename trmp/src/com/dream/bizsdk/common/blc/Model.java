/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.exp.Expression;

import java.util.Hashtable;
import java.io.Serializable;

/**
 * Description:
 * A model is a object that represents a <model/> element in
 * the requests.xml file;
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-28
 * Time: 11:22:02
 */
public class Model implements Serializable {
    /**
     * name of the model
     */
    protected String name = null;
    /**
     * trigged condition
     */
    protected String on = null;
    /**
     * asyn call flag
     */
    protected boolean _isAsyn = false;

    private String className;
    private String methodName;

    /**
     * constructor with the name of the Model name;
     *
     * @param name the name of the model;
     */
    public Model(String name) {
        setName(name);
    }

    /**
     * constructor with the name and run condition of the Model
     *
     * @param name the name of the modle;
     * @param on   the run condition;
     */
    public Model(String name, String on) {
        setName(name);
        this.on = on;
        if (this.on != null && this.on.length() < 1) {
            this.on = null;
        }
    }

    /**
     * @param name
     * @param on
     * @param asyn
     */
    public Model(String name, String on, boolean asyn) {
        setName(name);
        this.on = on;
        if (this.on != null && this.on.length() < 1) {
            this.on = null;
        }
        _isAsyn = asyn;
    }

    /**
     * get the name of this model;In our system,it is the name
     * of a bl method;
     *
     * @return the name of the BL method;
     */
    public String getName() {
        return this.name;
    }

    public final String getClassName() {
        return className;
    }

    public final String getMethodName() {
        return methodName;
    }

    /**
     * get the run condition;
     *
     * @return
     */
    public String getOn() {
        return this.on;
    }

    public boolean isAsyn() {
        return _isAsyn;
    }

    /**
     * check if this model need run according to the specified
     * BizData object. The data object is used to evaluate the
     * run condition.
     * If the run conditon is null or evaluated to be true,then
     * return true, false otherwise.
     *
     * @param rd
     * @param sd
     * @return
     */
    public boolean needRun(BizData rd, BizData sd) {
        if (on == null) {
            return true;
        } else {
            Hashtable bds = new Hashtable();
            if (rd != null) {
                bds.put("rd", rd);
                bds.put("default", rd);
            }
            if (sd != null) {
                bds.put("sd", sd);
            }
            return Expression.valueOf(on, bds);
        }
    }

    /**
     * @param bds
     * @return
     */
    public boolean needRun(Hashtable bds) {
        if (on == null) {
            return true;
        } else {
            return Expression.valueOf(on, bds);
        }
    }

    private void setName(String name) {
        this.name = name;
        int index = name.lastIndexOf(".");
        className = name.substring(0, index);
        methodName = name.substring(index + 1);
    }
}
