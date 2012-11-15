/**
 *all rights reserved,@copyright 2003
 */
package com.cots.ide.html;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.apache.xpath.XPathAPI;

import javax.xml.transform.TransformerException;
import java.util.Map;
import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-2-22
 * Time: 16:51:58
 * Version: 1.0
 */
public class HTMLForm {
    //the action of the form;
    private String action;

    //parameters in the form, the parameter may be of type primitive,bean,
    private Map params = new HashMap();


    /**
     * ge the action of the form;
     * @return the action's form;
     */
    public String getAction() {
        return action;
    }

    /**
     * set the action of the form;
     *
     * @param action the new value of the form's action;
     */
    void setAction(String action) {
        this.action = action;
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
     * @param name the displayName attribute of the target element in the form;
     * @param value the value attribute of the target element in the form;
     */
    void addRawParam(String name,String value){
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
     * initialize this object according to a <form> element;
     *
     * @param formEle the <form> element in the html document;
     */
    public void init(Element formEle)throws HTMLParseException{
        int count;
        action = formEle.getAttribute("action");

        try {
            //extract <input>
            NodeList inputs = XPathAPI.selectNodeList(formEle,"//INPUT");
            count = inputs.getLength();
            for(int i=0;i<count;i++){
                Element input = (Element)inputs.item(i);
                String name = input.getAttribute("name");
                String value = input.getAttribute("name");
                addRawParam(name,value);
            }

            //extract <select>
            NodeList selects = XPathAPI.selectNodeList(formEle,"//SELECT");
            count = selects.getLength();
            for(int i=0;i<count;i++){
                Element select = (Element)selects.item(i);
                String name = select.getAttribute("displayName");
                String value = select.getAttribute("value");
                addRawParam(name,value);
            }
        } catch (TransformerException e) {
            throw new HTMLParseException(e);
        }
    }
}
