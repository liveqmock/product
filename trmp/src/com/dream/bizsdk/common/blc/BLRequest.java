/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Description:
 * BLRequest holds the name of the request and the Models list to process the request,
 * and the dipatch(view) list to display the result to client.
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-27
 * Time: 14:43:47
 */
public class BLRequest implements Serializable {

    //name of this request;
    protected String name;

    //description of this description;
    protected String desc;

    //whether this request need tx;
    protected boolean txNeeded;

    /**
     * dispatches list
     */
    protected Dispatches dispatches = new Dispatches();

    /**
     * models list
     */
    protected Models models = new Models();

    //conditionla validations;
    protected transient Validations conditionalValidations = new Validations();

    //field validations
    protected transient FieldValidations fieldValidations = new FieldValidations();

    //data filters for this request;
    protected transient ArrayList dataFilters;


    public BLRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * get a dispatch for a specified request.
     *
     * @param data the request BizData object;
     * @return the matching Dispatch;
     */
    public Dispatch[] getDispatch(BizData data, BizData sessionData) {
        return dispatches.getDispatch(data, sessionData);
    }

    /**
     * return the dispatches
     *
     * @return
     */
    public Dispatches getDispatches() {
        return dispatches;
    }

    /**
     * add a dispatch into this request;
     *
     * @param disp
     */
    public void addDispatch(Dispatch disp) {
        dispatches.add(disp);
    }

    /**
     * add a dispatch at a specified index;
     *
     * @param index
     * @param disp
     */
    public void addDispatch(int index, Dispatch disp) {
        dispatches.add(index, disp);
    }

    /**
     * Get Models List that will be process a Request.
     *
     * @return a Models Object;
     */
    public Models getModels() {
        return models;
    }

    /**
     * add a model at this end of this request;
     *
     * @param m model to be added;
     */
    public void addModel(Model m) {
        models.add(m);
    }

    /**
     * add a model at the speific position;
     *
     * @param index position to add the Model
     * @param m     model to be added;
     */
    public void addModel(int index, Model m) {
        models.add(index, m);
    }

    /**
     * @return
     */
    public Validations getValidateions() {
        return conditionalValidations;
    }

    /**
     * @param v
     */
    public void addValidation(Validation v) {
        conditionalValidations.add(v);
    }

    /**
     * get the field validations for a request;
     *
     * @return the FieldValidations object;
     */
    public FieldValidations getFieldValidateions() {
        return fieldValidations;
    }

    /**
     * add a field validation to this request;
     *
     * @param fv Field validation to add;
     */
    public void addFieldValidations(FieldValidation fv) {
        fieldValidations.add(fv);
    }

    /**
     * @return
     */
    public boolean needTX() {
        return txNeeded;
    }

    /**
     * @param v
     */
    public void setTX(boolean v) {
        this.txNeeded = v;
    }

    /**
     * get data filters for this request;
     *
     * @return
     */
    public List getFilters() {
        return dataFilters;
    }

    /**
     * add a data filter to this request
     *
     * @param df
     */
    public void addDataFilter(DataFilter df) {
        if (dataFilters == null) {
            dataFilters = new ArrayList(10);
        }
        dataFilters.add(df);
    }

    /**
     * get the description of this request;
     *
     * @return the description of this request;
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * set the description of this request;
     *
     * @param desc description of this request;
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}