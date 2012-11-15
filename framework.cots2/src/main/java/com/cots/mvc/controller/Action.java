package com.cots.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.List;

/**
 * An Action is a command object that will be executed to respond to a
 * client's Http Request.
 *
 * User: chugh
 * Date: 2004-10-10
 * Time: 21:28:15
 * Version: 1.0
 */
public interface Action {
    String DEFAULT_ACTION="DefaultAction";

    String CUSTOM_ACTION="CustomAction";

    String URI = "uri";
    String DESCRIPTION = "description";
    String ON = "on";
    String BLMODEL = "BLModel";
    String GENERICMODEL = "GenericModel";
    String DISPATCHES = "dispatches";
    String NAME = "name";
    String TYPE = "type";
    String VALUETYPE="valueType";
    String REFBEAN = "ref-bean";
    String CLASS = "class";
    String METHOD = "method";
    String PARAMREF = "param-ref";
    String MODELS = "models";
    String PARAMETERS = "parameters";
    String PARAMETER = "parameter";
    String VALUE = "value";
    String PASSING = "passing";
    String SOURCE = "source";
    String TARGET = "target";
    String ARRAY = "array";
    String TRANSACTION = "transaction";
    String TRANSACTIONLEVEL = "transactionLevel";
    String FROMPAGE = "input";
    String RETURN = "return";
    String FORMAT = "format";
    String CONTEXT = "context";
    String SCREEN = "screen";

    String WSMODEL = "WSModel";
    String EJBMODEL = "EJBModel";
    String ENDPOINT = "endpoint";
    String SERVICENAME = "service";
    String OPERATION = "operation";

    String ERRORS = "__cotsErrors__";

    /**
     * get the displayName of this action;
     *
     * @return the displayName of the action;
     */
    String getName();

    /**
     * execute this action to respond to a specific Http Request;
     *
     * @param request the HttpServletRequest object;
     * @param response the HttpServletResponse object;
     * @throws ServletException
     * @throws JspException
     * @throws IOException
     */
    void run(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,JspException,IOException;

    void save() throws IOException;

    boolean isOK();

    List getErrorMsgs();
}
