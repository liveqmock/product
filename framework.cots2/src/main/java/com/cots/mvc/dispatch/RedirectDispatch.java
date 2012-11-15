/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.dispatch;

import com.cots.exp.StringExpression;
import com.cots.util.XMLFile;
import com.cots.mvc.controller.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.URLEncoder;

import org.w3c.dom.Element;

/**
 * Description:
 *      Redirect type dispatch. HttpServletResponse.sendRedirect(...) will be executed
 * for this dispatch.
 * 
 * User: chugh
 * Date: 2004-10-10
 * Time: 13:33:50
 * Version: 1.0
 */
public final class RedirectDispatch extends AbstractDispatch{

    protected ArrayList params = new ArrayList();

    protected String targetContext;

    public RedirectDispatch(String uri){
        if(uri.charAt(0)!='/'){
            this.uri = "/"+uri;
        }else{
            this.uri = uri;
        }
    }

    /**
     *
     * @param uri
     * @param targetContext
     */
    public RedirectDispatch(String uri,String targetContext){
        if(uri.charAt(0)!='/'){
            this.uri = "/"+uri;
        }else{
            this.uri = uri;
        }

        setTargetContext(targetContext);
    }


    /**
     * get the target ServletContext to which this RedirectDispatch will go.
     *
     * @return the Target Context path. 
     */
    public String getTargetContext() {
        return targetContext;
    }

    /**
     * set the target context when redirect the current Response.
     * if targetContext is null or empty, then redirect to the same
     * context.
     *
     * if targetContext is "/", then redirect to the defautl context;
     *
     * otherwise target to the context specified by the targetContext.
     *
     * @param targetContext
     */
    public void setTargetContext(String targetContext) {
        int len;
        if(targetContext!=null){
            if((len = targetContext.length())>0){
                if(len == 1 && targetContext.charAt(0)=='/'){
                    this.targetContext = "";
                }else if(targetContext.charAt(0)!='/'){
                    this.targetContext = "/"+targetContext;
                }else{
                    this.targetContext = targetContext;
                }
            }else{
                this.targetContext = null;
            }
        }
    }

    /**
     * make a call to HttpServletResponse.sendRedirect(....);
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public boolean go(HttpServletRequest request,HttpServletResponse response,HashMap data) throws IOException{

        String targetURI;

        if(targetContext==null){
            targetURI = request.getContextPath()+this.uri;
        }else{
            targetURI = targetContext+this.uri;
        }

        int size = params.size();
        if(size>0){
            String value;

            if(targetURI.indexOf('?')<0){
                targetURI+="?";
            }

            StringBuffer uri= new StringBuffer(targetURI);

            //create the redirect URL;
            for(int i=0;i<size;i++){
                RedirectParameter rdParam = (RedirectParameter)params.get(i);
                String paramName = rdParam.getName();
                uri.append(paramName);
                uri.append("=");

                StringExpression exp = rdParam.getExpression();
                if(exp!=null){
                    value = exp.valueOf(data);
                    if(value!=null){
                        uri.append(URLEncoder.encode(value,"GBK"));
                    }
                }
                if(i<size-1){
                    uri.append("&");
                }
            }
            response.sendRedirect(response.encodeRedirectURL(new String(uri)));
        }else{
            response.sendRedirect(response.encodeRedirectURL(targetURI));
        }
        return false;
    }

    public void save(XMLFile holder, Element parent) {
       Element disp = holder.appendChild(parent,"redirect");
        if(this.on!=null && this.on.length()>0){
            disp.setAttribute(Action.ON,this.on);
        }
        disp.setAttribute(Action.URI,this.uri);
        int size = params.size();
        for(int i=0;i<size;i++){
            RedirectParameter rp = (RedirectParameter)params.get(i);
            Element param = holder.appendChild(disp,Action.PARAMREF);
            param.setAttribute(Action.NAME,rp.getName());
            param.setAttribute(Action.VALUE,rp.getValue());
        }
    }

    /**
     * add a redirect parameter to this dispatch.
     *
     * @param name
     * @param value
     */
    public void addParameter(String name,String value){
        params.add(new RedirectParameter(name,value));
    }

    /**
     * add a parameter to this redirect dispatch.
     * 
     * @param rp
     */
    public void addParameter(RedirectParameter rp){
        params.add(rp);
    }

    /**
     * get all the parameters of this redirect dispatch.
     *
     * @return list of all the parameters.
     */
    public ArrayList getParams() {
        return params;
    }
}