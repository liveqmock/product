/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.util.XMLFile;

import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-11-6
 * Time: 16:40:45
 * Version: 1.0
 */
public final class ImplicitParameter extends Parameter{

    public final static String REQUEST_STR = "request";
    public final static String APPLICATION_STR = "applicaiton";
    public final static String SESSION_STR = "session";
    public final static String RESPONSE_STR = "response";
    public final static String CONFIG_STR = "config";
    public final static String PAGE_STR = "page";

    public final static ImplicitParameter REQUEST = new ImplicitParameter(REQUEST_STR);
    public final static ImplicitParameter APPLICATION = new ImplicitParameter(APPLICATION_STR);
    public final static ImplicitParameter SESSION = new ImplicitParameter(SESSION_STR);
    public final static ImplicitParameter RESPONSE = new ImplicitParameter(RESPONSE_STR);
    public final static ImplicitParameter CONFIG = null;
    public final static ImplicitParameter PAGE = null;


    private ImplicitParameter(String name){
        this.name = name;
        if(REQUEST_STR.equals(name)){
            type = "javax.servlet.http.HttpServletRequest";
        }else if(SESSION_STR.equals(name)){
            type = "javax.servlet.http.HttpSession";
        }else if(APPLICATION_STR.equals(name)){
            type = "javax.servlet.ServletContext";
        }else if(RESPONSE_STR.equals(name)){
            type = "javax.servlet.http.HttpServletResponse";
        }
    }

    public Class getTypeClass() {
        try {
            return Class.forName(this.type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object create(){
        return null;
    }

    /**
     * const passing value;
     *
     * @return ParameterPassing.IN
     */
    public int getTarget(){
        return ParameterTarget.NONE;
    }

    /**
     *
     * @param passing
     */
    public void setPassing(int passing){

    }

    /**
     *
     * @return
     */
    public int getSource(){
        return ParameterSource.WEBCONTAINER;
    }

    /**
     *
     * @param source
     */
    public void setSource(int source){

    }

    /**
     * get the value from the parameters cache;
     *
     * @param data
     * @return
     */
    public Object getValue(HashMap data){
        return data.get(name);
    }

    /**
     * this method does nothing;
     *
     * @param holder
     * @param action
     */
    public void save(XMLFile holder,Element action){

    }
}
