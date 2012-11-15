/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-5
 * Time: 10:53:09
 * Version: 1.0
 */
public abstract class AbstractAction implements Action{
    //the displayName of this action;
    protected String name;

    //the status of this action;
    protected boolean ok=true;

    //error messages when compose this action object;
    protected List errorMsgs;

    //fitlers that should be called on this action.
    protected List filters;

    protected List listeners;

    protected String regFile;

    /**
     * if this action is ready for process.
     *
     * @return
     */
    public boolean isOK() {
        return ok;
    }

    public void save() throws IOException{

    }

    /**
     * get the error msgs during the initialization of this action;
     * @return
     */
    public List getErrorMsgs(){
        return errorMsgs;
    }

    public List getFilters() {
        return filters;
    }

    public void setFilters(List filters) {
        this.filters = filters;
    }

    public List getListeners() {
        return listeners;
    }

    public void setListeners(List listeners) {
        this.listeners = listeners;
    }

    /**
     * test whether a Action Filter should be called on this action.
     *
     * @param filter
     */
    public void addFilter(ActionFilter filter){
        if(filters == null){
            filters = new ArrayList();
        }

        filters.add(filter);
    }

    /**
     *
     * @param listener
     */
    public void addListener(ActionListener listener){
        if(listeners == null){
            listeners = new ArrayList();
        }

        listeners.add(listener);
    }

    /**
     * check if this action is filterd out by the filters;
     *
     * @param request
     * @param data
     * @return
     */
    public boolean isFiltered(HttpServletRequest request,HashMap data){
        if(filters!=null){
            int size = filters.size();
            for(int i=0;i<size;i++){
                ActionFilter af = (ActionFilter)filters.get(i);
                if(!af.filter(request,name)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * add an initialization error message to this action;
     *
     * @param msg
     */
    void addErrorMsg(String msg){
        if(errorMsgs==null){
            errorMsgs = new ArrayList();
        }
        errorMsgs.add(msg);
    }

    /**
     *
     *
     * @param request
     * @param data
     */
    void begin(HttpServletRequest request, HashMap data){
        if(listeners != null){
            int size = listeners.size();
            for(int i=0;i<size;i++){
                ((ActionListener)listeners.get(i)).actionBegin(name,request);
            }
        }
    }

    /**
     *
     *
     * @param request
     * @param data
     */
    void done(HttpServletRequest request, HashMap data){
        if(listeners != null){
            int size = listeners.size();
            for(int i=0;i<size;i++){
                ((ActionListener)listeners.get(i)).actionDone(name,request);
            }
        }
    }

    /**
     *
     *
     * @param request
     * @param data
     */
    void failed(HttpServletRequest request, HashMap data,Throwable t){
        if(listeners != null){
            int size = listeners.size();
            for(int i=0;i<size;i++){
                ((ActionListener)listeners.get(i)).actionFailed(name,request,t);
            }
        }
    }
}