/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.dispatch;

import com.cots.exp.BooleanExpression;
import com.cots.util.XMLFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;

import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 13:31:09
 * Version: 1.0
 */
public abstract class AbstractDispatch implements Dispatch{

    //the target uri to dispatch;
    protected String uri;

    //the condition specify when to execute this dispatch;
    protected String on;

    //internal Java Object representation of the on member variable;
    protected BooleanExpression booleanExpression;

    /**
     * set the target uri for this dispatch;
     *
     * @param uri
     */
    public void setUri(String uri){
        this.uri = uri;
    }

    /**
     * get the uri of this dispatch;
     *
     * @return
     */
    public String getUri(){
        return uri;
    }

    /**
     *
     * @return
     */
    public String getOn() {
        return on;
    }

    /**
     * 
     * @param on
     */
    public void setOn(String on) {
        this.on = on;
    }

    /**
     * get the compiled BooleanExpression Object of the on variable;
     *
     * @return the BooleanExpression Object;
     */
    public BooleanExpression getExpression() {
        return booleanExpression;
    }

    /**
     * set the boolean expression object. this Expression must match the on variable;
     *
     * @param e
     */
    public void setExpression(BooleanExpression e) {
        this.booleanExpression = e;
    }

    /**
     * exevute this dispatch.
     *
     * @param request the current HttpServletRequest Object;
     * @param response the currrent HttpServletResponse object;
     * @return true if the next dispatch(es) in the parent action should be executed, false otherwise.
     * @throws ServletException
     * @throws IOException
     */
    public abstract boolean go(HttpServletRequest request,HttpServletResponse response,HashMap data)
            throws ServletException,IOException;

    public abstract void save(XMLFile holder,Element parent);
}
