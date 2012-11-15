/**
 *all rights reserved,@copyright 2003
 */
package com.cots.ide.html;

import org.w3c.dom.Element;

import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-2-22
 * Time: 16:55:57
 * Version: 1.0
 */
public class HTMLAnchor {
    private String action;

    private Map params = new HashMap();

    public String getAction() {
        return action;
    }

    /**
     * set the href of the anchor;
     *
     * @param href
     */
    public void setHref(String href) {
        String uri = href;
        String queryString = "";
        int index = uri.indexOf('?');
        if(index >=0){
            uri = uri.substring(0,index);
            queryString = uri.substring(index+1);
        }
        int period = uri.lastIndexOf(".");
        if(period>=0){
            index = uri.lastIndexOf('/',period);
            if(index<0){
                action = uri.substring(0,period);
            }else{
                action = uri.substring(index,period);
            }
        }
        setQueryString(queryString);
    }

    private void setQueryString(String queryString){
        StringTokenizer st = new StringTokenizer(queryString,"&");
        while(st.hasMoreElements()){
            String token = st.nextToken();
            addRawParam(token);
        }
    }

    /**
     * return the names of all the parameter;
     *
     * @return
     */
    public String[] getParamNames(){
       return (String[])params.keySet().toArray(new String[params.size()]);
    }

    /**
     * get the value of a named parameter in this form;
     *
     * @param name the displayName of the parameter;
     * @return the value of the parameter;
     */
    public String getParamValue(String name){
        return (String)params.get(name);
    }

    /**
     * add a raw parameter to a form;
     * a raw parameter correspondes to a <input>,<select> ... elements in a html document;
     *
     * @param param the displayName attribute of the target element in the form;
     */
    void addRawParam(String param){
        String name;
        String value;
        int index = param.indexOf("=");
        if(index>=0){
            name = param.substring(0,index);
            value = param.substring(index+1);
        }else{
            name = param;
            value = "";
        }

        try {
            name = URLDecoder.decode(name,"GBK");
            value = URLDecoder.decode(value,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        int period = name.indexOf('.');
        if(period<0){
            if(name.endsWith("[]")){
                params.put(name.substring(0,name.length()-2),null);
            }else{
                params.put(name,value);
            }
        }else{
            params.put(name.substring(0,period),null);
        }
    }


    /**
     * initialize this element node;
     *
     * @param aNode the <A> element;
     */
    public void init(Element aNode){
        String href = aNode.getAttribute("href");
        setHref(href);
    }
}
