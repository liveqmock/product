/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import com.cots.mvc.dispatch.*;
import com.cots.bean.BeanFactory;
import com.cots.bean.Bean;
import com.cots.bean.PrimitiveType;
import com.cots.bean.BeanPropertyException;
import com.cots.mvc.model.parameter.*;
import com.cots.mvc.model.Model;
import com.cots.mvc.model.BLModel;
import com.cots.mvc.model.GenericModel;
import com.cots.mvc.model.ejb.EJBModel;
import com.cots.mvc.model.ejb.StatefulSessionEJBModel;
import com.cots.mvc.model.ejb.StatelessSessionEJBModel;
import com.cots.mvc.model.ejb.CreateMethod;
import com.cots.mvc.model.ws.WSModel;
import com.cots.exp.*;
import com.cots.util.XMLDocument;
import com.cots.util.XMLFile;
import com.cots.blc.BLC;
import com.cots.blc.BLCPool;
import com.cots.blc.BLContext;
import com.cots.blc.BLException;
import com.cots.dao.DAO;
import com.cots.dao.TransactionLevel;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.jsp.JspException;
import javax.xml.transform.TransformerException;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.text.ParseException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.xml.sax.SAXException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 13:29:28
 * Version: 1.0
 */
public final class DefaultAction extends AbstractAction {

    private static int INDEX = 1;

    private static int PASS_NO_DATA = 0;
    private static int PASS_IN_DATA = 1;
    private static int PASS_OUT_DATA = 2;

    private XMLFile holder;


    //the description of this action;
    protected String description;

    //the input url of this action;
    protected String input;

    //the parameters of this action;
    protected HashMap parameters = new HashMap();

    //the models of this action;
    protected ArrayList models = new ArrayList();

    //dispatches of this action;
    protected ArrayList dispatches = new ArrayList();

    //parameter validators;
    protected ArrayList paramValidaters;

    //the target Screen object if all the dispatches is of type include;
    protected Screen screen;

    //the displayName of the screen;
    protected String screenName;

    //the bean factory used by this action;
    protected BeanFactory beanFactory;

    //if transaction support should be required for this action;
    protected boolean transatcionRequired;

    //the transaction isolation level;
    protected String transactionLevel;

    //the target dao this action depends on, if not defined, then the default dao is used;
    protected String targetDAO;

    //the BLContext for this action;
    protected BLContext context;

    //expression builder object;
    Builder expressionBuilder;

    Logger log;

    //BLCPool object;
    BLCPool blcPool;


    public DefaultAction() {
        log = Logger.getLogger(this.getClass());
    }

    public DefaultAction(String name) {
        this();
        this.name = name;
    }

    /**
     *
     * @param context
     */
    public void setBLContext(BLContext context){
        this.context = context;
    }

    /**
     * set the holder object which is a xml file object for this action.
     *
     * @param holder the XMLFile object that holds the definition of this object;
     */
    public void setHolder(XMLFile holder) {
        this.holder = holder;
        this.regFile = holder.getFilePath();
    }

    /**
     * get the URI of the input page;
     *
     * @return the uri of the input page;
     */
    public String getInput() {
        return input;
    }

    /**
     * set the URI of the input page;
     *
     * @param input the uri of the input page;
     */
    public void setInput(String input) {
        this.input = input;
    }

    public ArrayList getParamValidaters() {
        return paramValidaters;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return
     */
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * @param beanFactory
     */
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * @return
     */
    public Builder getExpressionBuilder() {
        return expressionBuilder;
    }

    /**
     * @param expressionBuilder
     */
    public void setExpressionBuilder(Builder expressionBuilder) {
        this.expressionBuilder = expressionBuilder;
    }

    /**
     * @return
     */
    public BLCPool getBlcPool() {
        return blcPool;
    }

    /**
     * @param blcPool
     */
    public void setBlcPool(BLCPool blcPool) {
        this.blcPool = blcPool;
    }

    /**
     * return all the Class of the parameter;
     *
     * @return
     */
    public HashMap getParameterTypes() {
        HashMap types = new HashMap();
        Iterator it = parameters.keySet().iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            Parameter p = (Parameter) parameters.get(name);
            types.put(p.getName(), p.getTypeClass());
        }
        return types;
    }

    /**
     * set the displayName of the action;
     *
     * @param name the displayName of the action;
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the displayName of the action;
     *
     * @return the displayName of the action;
     */
    public String getName() {
        return this.name;
    }

    /**
     * get the displayName of the target DAO object this action depends on;
     *
     * @return the target DAO displayName;
     */
    public String getTargetDAO() {
        return targetDAO;
    }

    /**
     * set the displayName of the taget DAO object which this action depends on.
     *
     * @param targetDAO the displayName of the target DAO;
     */
    public void setTargetDAO(String targetDAO) {
        this.targetDAO = targetDAO;
    }

    /**
     * check if the transaction is required for this action;
     *
     * @return
     */
    public boolean isTransatcionRequired() {
        return transatcionRequired;
    }

    /**
     * set if transaction is required for this action;
     *
     * @param transatcionRequired true if transaction is required for this action, false otherwise;
     */
    public void setTransatcionRequired(boolean transatcionRequired) {
        this.transatcionRequired = transatcionRequired;
    }

    /**
     * get the transaction level for this acrtion;
     *
     * @return
     */
    public String getTransactionLevel() {
        return transactionLevel;
    }


    public HashMap getParameters() {
        return parameters;
    }

    public ArrayList getModels() {
        return models;
    }

    public ArrayList getDispatches() {
        return dispatches;
    }

    /**
     * get the transaction level for this action;
     *
     * @param transactionLevel
     */
    public void setTransactionLevel(String transactionLevel) {
        this.transactionLevel = transactionLevel;
    }

    /**
     * addAttribute a models to the end of models lit;
     *
     * @param m
     */
    public boolean addModel(Model m) throws ExprSyntaxException {
        return addModel(models.size(), m);
    }


    /**
     * remove a model at the specified index;
     *
     * @param index the taget index at which to delete model
     */
    public void removeModel(int index){
        models.remove(index);
    }
    /**
     * addAttribute a model at a specified index;
     *
     * @param index
     * @param m
     */
    public boolean addModel(int index, Model m) throws ExprSyntaxException {
        String on = m.getOn();
        if (on != null && on.length() > 0) {
            String id = getName() + "_model_" + models.size();
            BooleanExpression be = expressionBuilder.buildBoolean(id, on, this.getParameterTypes());
            m.setExpression(be);
        }
        if (m instanceof BLModel) {
            BLModel blmodel = (BLModel) m;
            BLC blc = blmodel.getBLC();
            if (blc == null) {
                try {
                    blc = blcPool.getBLC(blmodel.getClassName());
                } catch (ClassNotFoundException e) {
                    this.ok = false;
                    log.error("can't find BLC " + blmodel.getClassName(), e);
                    return false;
                } catch (IllegalAccessException e) {
                    this.ok = false;
                    log.error("can't access BLC " + blmodel.getClassName(), e);
                    return false;
                } catch (InstantiationException e) {
                    this.ok = false;
                    log.error("can't create BLC object" + blmodel.getClassName(), e);
                    return false;
                }
                blmodel.setBLC(blc);
            }
        }
        models.add(index, m);
        return true;
    }


    /**
     * addAttribute a dispatch to the end of the dispatches list;
     *
     * @param dispatch
     */
    public void addDispatch(AbstractDispatch dispatch) throws ExprSyntaxException {
        addDispatch(dispatches.size(),dispatch);
    }

    /**
     * add a RedirectDispatch;
     *
     * @param dispatch
     * @throws ExprSyntaxException
     */
    public void addDispatch(RedirectDispatch dispatch) throws ExprSyntaxException {
        addDispatch(dispatches.size(),dispatch);
    }

    /**
     * add dispatch at a specified position.
     *
     * @param index    the position for the new Dispatch;
     * @param dispatch the new Dispatch to be added;
     */
    public void addDispatch(int index, AbstractDispatch dispatch)throws ExprSyntaxException  {
        String on = dispatch.getOn();
        if (on != null && on.length() > 0) {
            String id = getName() + "_disp_" + dispatches.size();
            BooleanExpression be = expressionBuilder.buildBoolean(id, on, this.getParameterTypes());
            dispatch.setExpression(be);
        }

        if(dispatch instanceof RedirectDispatch){
            RedirectDispatch rd = (RedirectDispatch)dispatch;
            List params = rd.getParams();
            int size = params.size();
            for(int i=0;i<size;i++){
                RedirectParameter rp = (RedirectParameter)params.get(i);
                String value = rp.getValue();
                StringExpression se = rp.getExpression();

                if(value!=null && value.length()>0){
                    if(se == null){ // check if the expression have been built.
                        try {
                            StringExpression exp = expressionBuilder.buildString(this.name + "_disp_" + index +
                                    "_rdparam_" + rp.getName(), value, this.getParameterTypes());
                            rp.setExpression(exp);
                        } catch (ExprSyntaxException e) {
                            addErrorMsg(e.getExpression()+" failed, reason: "+e.getMessage());
                            if (log.isEnabledFor(Priority.ERROR)) {
                                log.error("can't build the String Expression: " + value, e);
                            }
                            ok = false;
                            throw e;
                        }
                    }
                }
            }
        }

        dispatches.add(dispatch);
    }

    public void removeDispatch(int index){
        dispatches.remove(index);
    }

    /**
     * addAttribute a dispatch to the end of the list
     *
     * @param p
     */
    public void addParameter(Parameter p) throws ClassNotFoundException {
        if (p instanceof BeanParameter) {
            BeanParameter bp = (BeanParameter) p;
            if (bp.getBean() == null) {
                String type = bp.getType();
                Bean bean;
                if (type != null) {
                    bean = beanFactory.getByClass(Class.forName(type));
                } else {
                    bean = beanFactory.getByName(bp.getName());
                }
                bp.setBean(bean);
            }
        }
        parameters.put(p.getName(), p);
    }

    /**
     * remove a parameter from this action;
     *
     * @param name
     * @return
     */
    public Parameter removeParameter(String name) {
        return (Parameter) parameters.remove(name);
    }

    /**
     * get the Class type of an array of Parameters;
     *
     * @param params the parameters displayName array;
     * @return
     */
    public Class[] getParamTypes(ParameterRef[] params) {
        String name;
        int count = params.length;
        Class[] clazz = new Class[count];
        for (int i = 0; i < count; i++) {
            if (params[i].getType() == null) {
                Parameter p = (Parameter) parameters.get((name = params[i].getName()));
                if (p != null) {
                    clazz[i] = p.getTypeClass();
                } else {
                    if (log.isEnabledFor(Priority.ERROR)) {
                        log.error("No such parameter: " + name);
                    }
                    ok = false;
                }
            } else {
                clazz[i] = params[i].getTypeClass();
                if (clazz[i] == null) {
                    if (log.isEnabledFor(Priority.ERROR)) {
                        log.error("Can't find class: " + params[i].getType());
                    }
                    ok = false;
                }
            }
        }
        return clazz;
    }

    /**
     * get the Class[] objects matching names;
     *
     * @param params the parameter names array;
     * @return the Class[] array;
     */
    public Class[] getParamTypes(String[] params) {
        int count = params.length;
        Class[] clazz = new Class[count];
        for (int i = 0; i < count; i++) {
            Parameter p = (Parameter) parameters.get(params[i]);
            if (p != null) {
                clazz[i] = p.getTypeClass();
            }
        }
        return clazz;
    }

    /**
     * the controller will call this method to process a client's
     * Http Request;
     *
     * @param request
     * @param response
     */
    public void run(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, JspException, IOException {

        HashMap data = new HashMap();
        try {

            //populate data;
            populateData(request, response,data);

            //if the action is filtered out, then sendBody http error 403;
            if(!isFiltered(request,data)){
                response.sendError(HttpServletResponse.SC_FORBIDDEN,"request filtered out");
                return;
            }

            begin(request,data);

            //validate the data;
            List msgs = validateData(data);

            if (msgs == null) {  //if msgs is null, then all the vaidations have passed;
                //run all the models;
                runModels(data);

                //dispatch the Http Request;
                dispatch(data, request, response, null, PASS_OUT_DATA);

                done(request,data);
            } else { //validation error;
                dispatchError(request,response,msgs,data);
                failed(request,data,null);
            }
        } catch (ActionParametersException e){  //the parameters of the action is not parsed correcly;
            failed(request,data,e);
            List errors = e.getErrors();
            dispatchError(request,response,errors,data);
        } catch (NoSuchMethodException e) {
            failed(request,data,e);
            throw new ServletException(e);
        } catch (IllegalAccessException e) {
            failed(request,data,e);
            throw new ServletException(e);
        } catch (InvocationTargetException e) {
            failed(request,data,e);
            throw new ServletException("invocation target exception", e.getTargetException());
        } catch (InstantiationException e) {
            failed(request,data,e);
            throw new ServletException("failed to create exception", e);
        } catch (ServletException e) {
            failed(request,data,e);
            throw e;
        } catch (IOException e) {
            failed(request,data,e);
            throw e;
        } catch (JspException e) {
            failed(request,data,e);
            throw e;
        } catch(BLException e) {
            Throwable t = e.getCause();
            if(t!=null){
                failed(request,data,t);
                throw new ServletException(e.getMessage(),t);
            }else{
                failed(request,data,e);
                throw new ServletException(e);
            }
        } catch (Throwable e) {
            failed(request,data,e);
            throw new ServletException(e.getMessage(), e);
        }
    }

    /**
     * dispatch on error messages;
     *
     * @param request
     * @param response
     * @param errors
     * @param data
     * @throws IOException
     * @throws ServletException
     */
    private void dispatchError(HttpServletRequest request, HttpServletResponse response,List errors,HashMap data)
            throws IOException, ServletException {
        request.setAttribute(ERRORS, errors);
        if (input != null) {    //if the input page is specified, then forward to the this page;
            dispatch(data, request, response, input, PASS_IN_DATA);
        } else {  //forward to /error.jsp;
            dispatch(data, request, response, ControllerServlet.ERROR_FORWARD, PASS_NO_DATA);
        }
    }

    /**
     *
     *
     * @throws IOException
     */
    public synchronized void save() throws IOException {
        //if no holder speicifed, return directly;

        try {
            holder = new XMLFile(regFile);
        } catch (ParserConfigurationException e) {
            throw new IOException("ParserConfigurationException"+e.getMessage());
        } catch (SAXException e) {
            throw new IOException("SAXException:"+e.getMessage());
        }

        Document doc = holder.getDocument();
        Element root = doc.getDocumentElement();
        Node n = holder.selectSingleNode(root, Action.DEFAULT_ACTION + "[@" + Action.NAME+"=\""
                +this.getName()+"\"]");
        if (n != null) {
            root.removeChild(n);
        }

        Element action = doc.createElement(Action.DEFAULT_ACTION);
        action.setAttribute(Action.NAME, name);
        if (this.transatcionRequired) {
            action.setAttribute(Action.TRANSACTION, "true");
            if (this.transactionLevel != null && this.transactionLevel.length() > 0) {
                action.setAttribute(Action.TRANSACTIONLEVEL, this.transactionLevel);
            }
        }

        root.appendChild(action); 

        saveParameters(holder,action);
        saveValidaters(holder,action);

        saveModels(holder,action);
        saveDispatches(holder,action);

        try {
            holder.save();
        } catch (TransformerException e) {
            log.error("failed to save action \""+name+"\"",e);
            throw new IOException(e.getMessageAndLocation());
        }
    }

    /**
     *
     *
     * @param holder
     * @param action
     */
    void saveParameters(XMLFile holder, Element action) {
        Element parameters = holder.appendChild(action,"parameters");
        HashMap params = this.getParameters();
        Iterator names = params.keySet().iterator();
        while (names.hasNext()) {
            Parameter param = (Parameter) params.get(names.next());
            param.save(holder, parameters);
        }
    }

    /**
     *
     * @param holder
     * @param action
     */
    void saveValidaters(XMLFile holder, Element action) {
        if(paramValidaters!=null){
            Element validate = holder.appendChild(action,"validate");
            int size = this.paramValidaters.size();
            for(int i=0;i<size;i++){
                Object v = this.paramValidaters.get(i);
                if(v instanceof BooleanValidater){
                   ((BooleanValidater)v) .save(holder,validate);
                }else{
                    ((ParamValidater)v).save(holder,validate);
                }
            }
        }
    }

    /**
     *
     * @param holder
     * @param action
     */
    void saveModels(XMLFile holder, Element action) {
        Element models = holder.appendChild(action,Action.MODELS);
        int size = this.models.size();
        for(int i=0;i<size;i++){
            Model model = (Model)this.models.get(i);
            model.save(holder,models);
        }
    }

    /**
     *
     * @param holder
     * @param action
     */
    void saveDispatches(XMLFile holder, Element action) {
        Element dispatches = holder.appendChild(action,Action.DISPATCHES);
        if(this.screenName!=null){
            dispatches.setAttribute(Action.SCREEN,this.screenName);
        }
        int size = this.dispatches.size();
        for(int i=0;i<size;i++){
            AbstractDispatch model = (AbstractDispatch)this.dispatches.get(i);
            model.save(holder,dispatches);
        }
    }

    /**
     * run models in this action;
     *
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public void runModels(HashMap data)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception {
        DAO dao = null;
        try {
            //check if transaction is required;
            if (this.transatcionRequired) {
                if (targetDAO != null) {
                    dao = context.getDAO(targetDAO);
                } else {
                    dao = context.getDAO();
                }
                dao.beginTrans(TransactionLevel.strToInt(this.transactionLevel));
            }


            int count = models.size();
            for (int i = 0; i < count; i++) {
                Model m = (Model) models.get(i);
                BooleanExpression e = m.getExpression();
                if (e == null || e.valueOf(data)) {
                    Object[] values = getParamValues(data, m);
                    Object v = m.invoke(values);

                    if (v != null) {
                        String name = m.getReturnName();
                        if (name != null) {
                            data.put(name, v);
                        }
                    }
                }
            }

            //if transaction is requrired, then trying to commit the transaciton;
            if (this.transatcionRequired) {
                dao.commit();
                dao = null;
            }
        } catch (InvocationTargetException e) {
            log.error("exception in models", e.getTargetException());
            throw e;
        } finally {
            if (dao != null) {
                try {
                    dao.rollback();
                } catch (Exception e) {
                    log.error("failed to rollback transaction", e);
                }
            }
        }
    }

    /**
     *
     * @param data
     * @param request
     * @param response
     * @param uri         dispatch to a specified uri;
     * @param passingdata
     * @throws ServletException
     * @throws IOException
     */
    protected void dispatch(HashMap data, HttpServletRequest request, HttpServletResponse response,
                            String uri, int passingdata) throws ServletException, IOException {

        boolean goOn;
        HttpSession session = request.getSession(true);
        ServletContext sc = session.getServletContext();

        if (passingdata == PASS_OUT_DATA) {
            Iterator it = parameters.keySet().iterator();
            while (it.hasNext()) {
                Parameter p = (Parameter) parameters.get(it.next());
                int target = p.getTarget();
                if (target  > 0) {
                    String name = p.getName();
                    if(target == ParameterTarget.REQUEST){
                        request.setAttribute(name, data.get(name));
                    }else if(target == ParameterTarget.APPLICATION){
                        sc.setAttribute(name,data.get(name));
                    }else if(target == ParameterTarget.SESSION){
                        session.setAttribute(name,data.get(name));
                    }else{
                        request.setAttribute(name, data.get(name));
                    }
                }
            }
        } else if (passingdata == PASS_IN_DATA) {
            Iterator it = parameters.keySet().iterator();
            while (it.hasNext()) {
                Parameter p = (Parameter) parameters.get(it.next());
                int source = p.getSource();
                if (source == ParameterSource.REQUEST|| source == ParameterSource.SESSION) {
                    String name = p.getName();
                    if(source == ParameterSource.REQUEST){
                        request.setAttribute(name, data.get(name));
                    }else {
                        session.setAttribute(name, data.get(name));                        
                    }
                }
            }
        }

        if (uri == null) {  //no uri is specified;
            //show all the pre screen view;
            if (screen != null) {
                screen.showPreViews(request, response);
            }

            int size = dispatches.size();
            for (int i = 0; i < size; i++) {
                AbstractDispatch disp = (AbstractDispatch) dispatches.get(i);
                BooleanExpression exp = disp.getExpression();
                if (exp == null) {
                    goOn = disp.go(request, response, data);
                } else if (exp.valueOf(data)) {
                    goOn = disp.go(request, response, data);
                } else {
                    goOn = true;
                }

                //check if should go to run the next dispatch;
                if (!goOn) {
                    break;
                }
            }

            //show all the post views;
            if (screen != null) {
                screen.showPostViews(request, response);
            }
        } else {  //dispatch to a specified uri;
            RequestDispatcher rd = request.getRequestDispatcher(uri);
            rd.forward(request, response);
        }
    }

    /**
     * create all the parameters of this action from the HttpServletRequest;
     *
     * @param request
     * @return
     */
    public HashMap populateData(HttpServletRequest request, HttpServletResponse response,HashMap data)
            throws IllegalAccessException, InstantiationException,
            InvocationTargetException, ActionParametersException {
        String name;
        Object o = null;
        ActionParametersException ape = null;
        HttpSession session = request.getSession();
        ServletContext sc = session.getServletContext();

        Iterator it = parameters.keySet().iterator();
        while (it.hasNext()) {
            Parameter p = (Parameter) parameters.get(it.next());
            int source = p.getSource();
            if (source == ParameterSource.REQUEST || source == ParameterSource.SESSION
                    || source == ParameterSource.APPLICATION || source ==ParameterSource.NEW) {
                name = p.getName();
                if (source == ParameterSource.REQUEST) {
                    if (p instanceof PrimitiveParameter) {
                        PrimitiveParameter pp = (PrimitiveParameter) p;
                        try{
                            if (pp.isArray()) {
                                o = PrimitiveType.createArray(pp.getType(), request.getParameterValues(pp.getName()),
                                        pp.getFormat());
                            } else {
                                o = PrimitiveType.create(pp.getType(), request.getParameter(pp.getName()),
                                        pp.getFormat());
                            }
                            data.put(name, o);
                        }catch(ParseException e){
                            if(ape == null){
                                ape = new ActionParametersException();
                            }
                            String msg = e.getMessage()+" is not a valid \""+pp.getType()+"\"";
                            if(pp.getFormat()!=null){
                                msg +=" with format \""+pp.getFormat()+"\"";
                            }
                            ape.addError(msg);
                        }catch(NumberFormatException e){
                            if(ape == null){
                                ape = new ActionParametersException();
                            }
                            String msg = e.getMessage()+" is not a valid \""+pp.getType()+"\"";
                            if(pp.getFormat()!=null){
                                msg +=" with format of \""+pp.getFormat()+"\"";
                            }
                            ape.addError(msg);
                        }
                    } else if (p instanceof BeanParameter) {
                        BeanParameter bp = (BeanParameter) p;
                        try{
                            if (bp.isArray()) {
                                o = bp.createArray(request);
                            } else {
                                o = bp.create(request);
                            }
                            data.put(name, o);
                        }catch(BeanPropertyException e){
                            if(ape == null){
                                ape = new ActionParametersException();
                            }
                            ape.addErrors(e.getErrors());
                        }
                    } else if (p instanceof MapParameter) {
                        MapParameter mp = (MapParameter) p;
                        try{
                            o = mp.create(request);
                            data.put(name, o);
                        }catch(ParseException e){
                            if(ape == null){
                                ape = new ActionParametersException();
                            }
                            String msg = e.getMessage();
                            ape.addError(msg);
                        }
                    } else if (p instanceof ListParameter) {
                        ListParameter lp = (ListParameter) p;
                        try{
                            o = lp.create(request.getParameterValues(lp.getName()));
                            data.put(name, o);
                        }catch(ParseException e){
                            if(ape == null){
                                ape = new ActionParametersException();
                            }
                            String msg = e.getMessage();
                            ape.addError(msg);
                        }
                    } else if (p instanceof SetParameter) {
                        SetParameter sp = (SetParameter) p;
                        try{
                            o = sp.create(request.getParameterValues(sp.getName()));
                            data.put(name, o);
                        }catch(ParseException e){
                            if(ape == null){
                                ape = new ActionParametersException();
                            }
                            String msg = e.getMessage();
                            ape.addError(msg);
                        }
                    }
                } else if (source == ParameterSource.SESSION) {
                    o = session.getAttribute(name);
                    data.put(name, o);
                } else if (source == ParameterSource.APPLICATION) {
                    o = sc.getAttribute(name);
                    data.put(name, o);
                } else if (source == ParameterSource.NEW) {
                    o = p.create();
                    data.put(name, o);
                }
            }
        }
        if(ape!=null){
            throw ape;
        }

        /*add implicit parameter's value*/
        data.put(ImplicitParameter.REQUEST_STR, request);
        data.put(ImplicitParameter.SESSION_STR, session);
        data.put(ImplicitParameter.APPLICATION_STR, sc);
        data.put(ImplicitParameter.RESPONSE_STR, response);

        return data;
    }

    /**
     * validate all the data of a request;
     *
     * @param data a HashMap object contains all the data
     * @return
     */
    private List validateData(HashMap data) {
        List msgs = null;
        if (paramValidaters != null) {
            int count = paramValidaters.size();
            for (int i = 0; i < count; i++) {
                Object v =  paramValidaters.get(i);
                if (v instanceof BooleanValidater){
                    BooleanValidater bv = (BooleanValidater) v;
                    if (!bv.isValid(data)) {
                        if (msgs == null) {
                            msgs = new ArrayList();
                        }
                        msgs.add(bv.getMessage());
                    }
                }else if(v instanceof RegExpValidater){
                    RegExpValidater pv =(RegExpValidater) v;
                    if(!pv.validate(data.get(pv.getParamName()))){
                        if (msgs == null) {
                            msgs = new ArrayList();
                        }
                        msgs.add(pv.getMessage());
                    }
                }else if(v instanceof CustomerValidater){
                    String msg;
                    CustomerValidater cv =(CustomerValidater) v;
                    String[] paramNames = cv.getParamNames();
                    int len = paramNames.length;
                    Object[] params = new Object[count];
                    for(int j=0;j<len;j++){
                        params[j] = data.get(paramNames[j]);
                    }
                    if((msg = cv.validate(params))!=null){
                        if (msgs == null) {
                            msgs = new ArrayList();
                        }
                        msgs.add(msg);
                    }
                }
            }
        }
        return msgs;
    }

    /**
     * get all of the PrimitiveParameter values for a model;
     *
     * @param data
     * @param m
     * @return
     */
    private Object[] getParamValues(HashMap data, Model m) {
        String[] paramNames = m.getParameterRefNames();
        int count = paramNames.length;
        Object[] values = new Object[count];
        for (int i = 0; i < count; i++) {
            values[i] = ((Parameter) parameters.get(paramNames[i])).getValue(data);
        }
        return values;
    }

    /**
     * get a parameter of the specified displayName;
     *
     * @param name the displayName of the parameter to get;
     * @return the target Parameter object;
     */
    public Parameter getParameter(String name) {
        return (Parameter) this.parameters.get(name);
    }

    /**
     * @param xml
     * @param actionNode
     */
    void init(XMLDocument xml, Element actionNode) {
        /*add implicit parameters,to add new implicit parameter, rember to modify the populateData method*/
        try {
            addParameter(ImplicitParameter.REQUEST);
            addParameter(ImplicitParameter.RESPONSE);
            addParameter(ImplicitParameter.SESSION);
            addParameter(ImplicitParameter.APPLICATION);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();     //this should not happen;
        }

        String name = actionNode.getAttribute(Action.NAME);

        if (name != null) {   //displayName can't be null;
            if (log.isInfoEnabled()) {
                log.info("initializing action \"" + name + "\" ...");
            }
            setName(name);
            //check if the transaction is required for this action;
            String trans = actionNode.getAttribute(Action.TRANSACTION);
            if ("true".equals(trans)) {
                setTransatcionRequired(true);
                String level = actionNode.getAttribute(Action.TRANSACTIONLEVEL);
                setTransactionLevel(level);

                //get the target dao of the transaction;
                String targetDAO = actionNode.getAttribute("targetDAO");
                if (targetDAO.length() > 0) {
                    this.targetDAO = targetDAO;
                }
            } else {
                setTransatcionRequired(false);
            }

            String inputUri = xml.getSingleNodeValue(actionNode, Action.FROMPAGE + "/@" + Action.URI);
            if (inputUri != null) {
                setInput(inputUri);
            }
            //init parameters of this action;
            try {
                initActionParameters(xml, actionNode);
                initActionParamValidaters(xml, actionNode);
                initActionModels(xml, actionNode);
            } catch (ParseException e) {
                log.error("Primitive Parameter's const value is not valid", e);
            } catch (ClassNotFoundException e) {
                log.error(e);
            }
            //init models of this action;

            //init dispatches of this action;
            initActionDispatches(xml, actionNode);

            //set the description of this action;
            String description = actionNode.getAttribute(Action.DESCRIPTION);
            if (description.length() > 0) {
                this.setDescription(description);
            }

            //check if this action has been initialized correctly;
            if (ok) {
                if (log.isInfoEnabled()) {
                    log.info("\"" + name + "\" OK!");
                }
            } else {
                if (log.isEnabledFor(Priority.ERROR)) {
                    log.error("\"" + name + "\" failed! See messages above");
                }
            }
        }
    }

    /**
     * @param xml
     * @param actionNode
     */
    void initActionParameters(XMLDocument xml, Element actionNode)
            throws ParseException, ClassNotFoundException{
        NodeList primiParamNodes = xml.selectNodeList(actionNode, "parameters/primitive");
        initActionPrimitiveParameters(primiParamNodes);
        NodeList beanParamNodes = xml.selectNodeList(actionNode, "parameters/bean");
        initActionBeanParameters(beanParamNodes);
        NodeList listParamNodes = xml.selectNodeList(actionNode, "parameters/list");
        initActionListParameters(listParamNodes);

        NodeList mapParamNodes = xml.selectNodeList(actionNode, "parameters/map");
        initActionMapParameters(mapParamNodes);

        NodeList setParamNodes = xml.selectNodeList(actionNode, "parameters/set");
        initActionSetParameters(setParamNodes);

        NodeList genericParamNodes = xml.selectNodeList(actionNode, "parameters/generic");
        initActionGenericParameters(genericParamNodes);

        NodeList exprParamNodes = xml.selectNodeList(actionNode, "parameters/expr");
        initActionExprParameters(exprParamNodes);

    }


    /**
     * initialize action's parameters validations;
     *
     * @param xml
     * @param actionNode
     */
    void initActionParamValidaters(XMLDocument xml, Element actionNode) {
        int count = 0;
        NodeList validNodes = xml.selectNodeList(actionNode, "validate/valid");
        if (validNodes != null && (count = validNodes.getLength()) > 0) {
            for (int i = 0; i < count; i++) {
                Element valid = (Element) validNodes.item(i);
                String ifexp = valid.getAttribute("if");
                String regex = valid.getAttribute("regex");
                String className = valid.getAttribute("class");
                String message = valid.getAttribute("message");
                String paramName = valid.getAttribute("param");
                String methodName = valid.getAttribute("method");
                String msgKey = valid.getAttribute("msgkey");

                if (ifexp.length() > 0) {
                    try {
                        BooleanExpression exp = expressionBuilder.buildBoolean(this.getName() + "_valid_" + i,
                                ifexp, this.getParameterTypes());
                        if (paramValidaters == null) {
                            paramValidaters = new ArrayList(16);
                        }
                        paramValidaters.add(new BooleanValidater(exp, message,msgKey));
                    } catch (ExprSyntaxException e) {
                        addErrorMsg(e.getExpression()+" failed, reason: "+e.getMessage());
                        log.error("boolean expression " + ifexp + " built failed.", e);
                        ok = false;
                    }
                }else if(regex!=null && regex.length()>0){
                    RegExpValidater regexVal = new RegExpValidater(regex);
                    regexVal.setMessage(message);
                    regexVal.setParamName(paramName);
                    regexVal.setMsgKey(msgKey);
                    if (paramValidaters == null) {
                        paramValidaters = new ArrayList(16);
                    }
                    paramValidaters.add(regexVal);
                }else if(className!=null && className.length()>0){
                    CustomerValidater cv = new CustomerValidater(className,paramName);
                    cv.setMsgKey(msgKey);
                    if(message!=null && message.length()>0){
                        cv.setMessage(message);
                    }
                    cv.setParamTypes(this.getParamTypes(cv.getParamNames()));
                    if(methodName!=null && methodName.length()>0){
                        cv.setMethodName(methodName);
                    }
                    if (paramValidaters == null) {
                        paramValidaters = new ArrayList(16);
                    }
                    paramValidaters.add(cv);
                }
            }
        }
    }

    /**
     * @param nl
     */
    void initActionPrimitiveParameters(NodeList nl) throws ParseException, ClassNotFoundException {
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element pn = (Element) nl.item(i);

            String name = pn.getAttribute(Action.NAME);
            String type = pn.getAttribute(Action.TYPE);
            String array = pn.getAttribute(Action.ARRAY);
            String format = pn.getAttribute(Action.FORMAT);
            String value = pn.getAttribute(Action.VALUE);

            //check if the type is a really primitive;
            if(!PrimitiveType.isPrimitive(type)){
                addErrorMsg("primitive parameter \""+name+"\" of type \""+type+
                        "\" is not really primitive.");
                this.ok = false;
            }

            PrimitiveParameter pp = new PrimitiveParameter(name, type);
            if ("true".equals(array)) {
                pp.setArray(true);
            }
            if (format.length() > 0) {
                pp.setFormat(format);
            }
            pp.setValue(value);
            initParameterCommon(pn, pp);

            addParameter(pp);
        }
    }

    /**
     * @param nl
     */
    void initActionBeanParameters(NodeList nl) throws ClassNotFoundException {
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element paramNode = (Element) nl.item(i);

            String name = paramNode.getAttribute(Action.NAME);
            String ref_bean = paramNode.getAttribute(Action.REFBEAN);
            String type = paramNode.getAttribute(Action.TYPE);
            String array = paramNode.getAttribute(Action.ARRAY);
            if (ref_bean.length() < 1) {
                ref_bean = name;
            }
            Bean bean = beanFactory.getByName(ref_bean);
            if (bean != null) {
                BeanParameter bp = new BeanParameter(name, bean);
                bp.setArray("true".equals(array) ? true : false);

                initParameterCommon(paramNode, bp);

                addParameter(bp);
            } else if (type.length() > 0) {
                try {
                    Class cls = Class.forName(type);
                    bean = beanFactory.getByClass(cls);
                    BeanParameter bp = new BeanParameter(name, bean);
                    bp.setArray("true".equals(array) ? true : false);

                    initParameterCommon(paramNode, bp);

                    addParameter(bp);

                } catch (ClassNotFoundException e) {
                    addErrorMsg(type+" not found.");
                    if (log.isEnabledFor(Priority.ERROR)) {
                        log.error("Can't find the referenced bean \"" + ref_bean + "\"");
                    }
                    ok = false;

                }
            } else {
                if (log.isEnabledFor(Priority.ERROR)) {
                    log.error("Can't find the referenced bean \"" + ref_bean + "\"");
                }
                ok = false;
            }
        }
    }

    /**
     * @param nl
     */
    void initActionListParameters(NodeList nl) throws ClassNotFoundException {
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element paramNode = (Element) nl.item(i);

            String name = paramNode.getAttribute(Action.NAME);
            String type = paramNode.getAttribute(Action.TYPE);
            String valueType = paramNode.getAttribute(Action.VALUETYPE);
            ListParameter p = new ListParameter(name, type);
            if(valueType!=null && valueType.length()>0){
                p.setValueType(valueType);
            }

            initParameterCommon(paramNode, p);

            addParameter(p);
        }
    }

    /**
     * initialize the SetParameters of an action;
     *
     * @param nl the <set> child element of the <parameters> element of an action;
     */
    void initActionSetParameters(NodeList nl) throws ClassNotFoundException {
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element paramNode = (Element) nl.item(i);

            String name = paramNode.getAttribute(Action.NAME);
            String type = paramNode.getAttribute(Action.TYPE);
            String valueType = paramNode.getAttribute(Action.VALUETYPE);
            SetParameter p = new SetParameter(name, type);
            if(valueType!=null && valueType.length()>0){
                p.setValueType(valueType);
            }
            
            initParameterCommon(paramNode, p);

            addParameter(p);
        }
    }

    /**
     * initialize action expression parameters;
     *
     * @param nl
     */
    void initActionExprParameters(NodeList nl){
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element paramNode = (Element) nl.item(i);

            String name = paramNode.getAttribute(Action.NAME);
            String type = paramNode.getAttribute(Action.TYPE);
            String expr = paramNode.getAttribute("expr");

            ExprParameter p = new ExprParameter(name, type);
            if (expr != null && expr.length() > 0) {
                try{
                    ObjectExpression oe = expressionBuilder.buildObject(this.name + "expr_param_" + (INDEX++), expr, this.getParameterTypes());
                    p.setExprObject(oe);
                }catch(ExprSyntaxException e){
                    addErrorMsg(e.getExpression()+" failed, "+e.getMessage());
                    log.error(e);
                    ok = false;
                }
            }

            initParameterCommon(paramNode, p);

            try {
                addParameter(p);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    void initActionGenericParameters(NodeList nl){
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element paramNode = (Element) nl.item(i);

            String name = paramNode.getAttribute(Action.NAME);
            String type = paramNode.getAttribute(Action.TYPE);

            try{
                GenericParameter p = new GenericParameter(name, type);
                initParameterCommon(paramNode, p);
                addParameter(p);
            }catch(ClassNotFoundException e){
                addErrorMsg(type+" not found.");
                log.error(e);
                ok = false;
            }
        }
    }

    /**
     * @param nl
     */
    void initActionMapParameters(NodeList nl) throws ClassNotFoundException {
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element paramNode = (Element) nl.item(i);

            String name = paramNode.getAttribute(Action.NAME);
            String type = paramNode.getAttribute(Action.TYPE);
            String valueType = paramNode.getAttribute(Action.VALUETYPE);
            MapParameter p = new MapParameter(name, type);
            if(valueType!=null && valueType.length()>0){
                p.setValueType(valueType);
            }

            initParameterCommon(paramNode, p);

            addParameter(p);
        }
    }

    /**
     * @param paramNode
     * @param p
     */
    void initParameterCommon(Element paramNode, Parameter p) {

        //source of the parameter;
        String source = paramNode.getAttribute(Action.SOURCE);
        if (ParameterSource.REQUEST_STR.equals(source)) {
            p.setSource(ParameterSource.REQUEST);
        } else if (ParameterSource.SESSION_STR.equals(source)) {
            p.setSource(ParameterSource.SESSION);
        } else if (ParameterSource.APPLICATION_STR.equals(source)) {
            p.setSource(ParameterSource.APPLICATION);
        } else if (ParameterSource.NEW_STR.equals(source)) {
            p.setSource(ParameterSource.NEW);
        } else if (ParameterSource.CODE_STR.equals(source)) {
            p.setSource(ParameterSource.CODE);
        }else {
            p.setSource(ParameterSource.REQUEST);
        }

        //target of the parameter;
        String target = paramNode.getAttribute(Action.TARGET);
        if (ParameterTarget.SESSION_STR.equals(target)) {
            p.setTarget(ParameterTarget.SESSION);
        } else if (ParameterTarget.APPLICATION_STR.equals(target)) {
            p.setTarget(ParameterTarget.APPLICATION);
        } else if (ParameterTarget.REQUEST_STR.equals(target)) {
            p.setTarget(ParameterTarget.REQUEST);
        } else if (ParameterTarget.NONE_STR.equals(target)) {
            p.setTarget(ParameterTarget.NONE);
        } else {//default to request;
            p.setTarget(ParameterTarget.REQUEST);
        }
    }

    /**
     * initialize all the models of an action. These models may be of defferent type: BLModel,
     * GenericModel, EJBModel, and so on.
     *
     * @param xml        the action-mapping.xml file;
     * @param actionNode the current action Element;
     */
    void initActionModels(XMLDocument xml, Element actionNode) {
        int index = 0;

        Model model = null;
        Node modelsNode = xml.selectSingleNode(actionNode, Action.MODELS);
        if (modelsNode != null) {
            NodeList childModels = modelsNode.getChildNodes();
            int count = childModels.getLength();
            for (int i = 0; i < count; i++) {
                Node n = childModels.item(i);

                String nodeName = n.getNodeName();

                //check if a BLModel node;
                if (Action.BLMODEL.equals(nodeName)) {
                    Element modelNode = (Element) childModels.item(i);
                    String asyn = modelNode.getAttribute("asynExec");
                    if ("true".equalsIgnoreCase(asyn)) {
                        model.setAsynMode(true);
                    }
                    model = initActionBLModel(modelNode);
                } else if (Action.GENERICMODEL.equals(nodeName)) {
                    Element modelNode = (Element) childModels.item(i);
                    String asyn = modelNode.getAttribute("asynExec");
                    if ("true".equalsIgnoreCase(asyn)) {
                        model.setAsynMode(true);
                    }
                    model = initActionGenericModel(modelNode);
                } else if (Action.WSMODEL.equals(nodeName)) {
                    Element modelNode = (Element) childModels.item(i);
                    String asyn = modelNode.getAttribute("asynExec");
                    if ("true".equalsIgnoreCase(asyn)) {
                        model.setAsynMode(true);
                    }
                    model = initActionWSModel(modelNode);
                } else if (Action.EJBMODEL.equals(nodeName)) {
                    Element modelNode = (Element) childModels.item(i);
                    String asyn = modelNode.getAttribute("asynExec");
                    if ("true".equalsIgnoreCase(asyn)) {
                        model.setAsynMode(true);
                    }
                    model = initActionEJBModel(modelNode);
                } else {
                    continue;
                }
                String on = ((Element) n).getAttribute("on");
                if (on.length() > 0) {
                    model.setOn(on);
                } else {
                    on = null;
                }

                //other types of model;
                index = index + 1;

                if (model != null) {
                    try {
                        addModel(model);
                    } catch (ExprSyntaxException e) {
                        addErrorMsg(e.getExpression()+" failed, reason: "+e.getMessage());
                        if (log.isEnabledFor(Priority.ERROR)) {
                            log.error("expression syntax error: " + model.getOn(), e);
                        }
                        ok = false;
                    }

                    Node returnName = xml.selectSingleNode(n, Action.RETURN + "/@" + Action.NAME);
                    if (returnName != null) {
                        String name = returnName.getNodeValue();
                        model.setReturnName(name);
                        Parameter p = this.getParameter(name);
                        if (p != null) {
                            model.setReturnType(p.getType());
                        } else {
                            addErrorMsg("return value \""+name+"\" must be defined as a parameter.");
                            if (log.isEnabledFor(Priority.ERROR)) {
                                log.error("return value " + name + " not defined in the parameters sector");
                            }
                            ok = false;
                        }
                    }
                }
            }
        }
    }

    /**
     * @param model
     */
    Model initActionBLModel(Element model) {

        String cls = model.getAttribute(Action.CLASS);
        String method = model.getAttribute(Action.METHOD);
        BLC blc = null;
        try {
            if (blcPool != null) {
                blc = blcPool.getBLC(cls);
            }
        } catch (ClassNotFoundException e) {
            addErrorMsg("BLC class \""+cls+"\" not found.");
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("BLC class \"" + cls + "\" was not found", e);
            }
            ok = false;
        } catch (IllegalAccessException e) {
            addErrorMsg("BLC class \""+cls+"\" must have a public default constructor.");
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("BLC class \"" + cls + "\" is not accessible", e);
            }
            ok = false;
        } catch (InstantiationException e) {
            addErrorMsg("failed to create BLC instance of \""+cls+"\".");
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("Can't create BLC object of class \"" + cls + "\"", e);
            }
            ok = false;
        }

        BLModel blModel = new BLModel(blc, method);
        blModel.setClassName(cls);

        //initialize parameters of this model;
        NodeList paramNodes = model.getElementsByTagName(Action.PARAMREF);
        initModelParameters(paramNodes, blModel);

        ParameterRef[] param_refs = blModel.getParameterRefs();

        Class[] param_types = getParamTypes(param_refs);

        blModel.setParameterTypes(param_types);

        return blModel;
    }

    /**
     * initialize a generic model.
     *
     * @param model
     * @return
     */
    Model initActionGenericModel(Element model) {
        boolean threadSafe = true;

        String clsName = model.getAttribute(Action.CLASS);
        String method = model.getAttribute(Action.METHOD);
        Class cls = null;
        Object oo = null;
        try {
            cls = Class.forName(clsName);

            //check if the generic class is thread safe.
            if(blcPool.getBLCRegistry().isThreadSafeBLC(clsName)){
                oo = cls.newInstance();
            }else{
                threadSafe = false;
            }
        } catch (ClassNotFoundException e) {
            addErrorMsg("Class \""+clsName+"\" not found.");
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("Class \"" + clsName + "\" was not found", e);
            }
            ok = false;
        } catch (IllegalAccessException e) {
            addErrorMsg("Class \""+clsName+"\" must have a public default constructor.");
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("Class \"" + clsName + "\" is not accessible", e);
            }
            ok = false;
        } catch (InstantiationException e) {
            addErrorMsg("failed to create instance of \""+clsName+"\".");
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("Can't create object of class \"" + clsName + "\"", e);
            }
            ok = false;
        }

        GenericModel gModel = new GenericModel(clsName, method);
        gModel.setCls(cls);
        gModel.setOo(oo);
        gModel.setThreadSafe(threadSafe);

        //if not thread safe, then the names and types of the constructor muest be set;
        if(!threadSafe){
            String ctSignature = model.getAttribute("constructor");
            gModel.setConstructorParams(ctSignature);
            gModel.setConstructorParamTypes(this.getParamTypes(gModel.getConstructorParams()));
        }

        //initialize parameters of this model;
        NodeList paramNodes = model.getElementsByTagName(Action.PARAMREF);
        initModelParameters(paramNodes, gModel);

        ParameterRef[] param_refs = gModel.getParameterRefs();
        Class[] param_types = getParamTypes(param_refs);
        gModel.setParameterTypes(param_types);

        return gModel;
    }

    /**
     * initialize a web service model.
     *
     * @param model the <WSModel> element;
     * @return WSModel object;
     */
    WSModel initActionWSModel(Element model) {

        String endpoint = model.getAttribute(Action.ENDPOINT);
        String service = model.getAttribute(Action.SERVICENAME);
        String operation = model.getAttribute(Action.OPERATION);

        WSModel ws = new WSModel(endpoint, service, operation);

        //initialize parameters of this model;
        NodeList paramNodes = model.getElementsByTagName(Action.PARAMREF);

        initModelParameters(paramNodes, ws);

        ParameterRef[] param_refs = ws.getParameterRefs();

        Class[] param_types = getParamTypes(param_refs);

        ws.setParameterTypes(param_types);

        return ws;
    }

    /**
     * initialize a web service model.
     *
     * @param model the <EJBModel> element;
     * @return WSModel object;
     */
    EJBModel initActionEJBModel(Element model) {

        String type = model.getAttribute("type");

        String initialFactory = model.getAttribute("initialFactory");
        String providerUrl = model.getAttribute("providerUrl");
        String jndi = model.getAttribute("jndi");
        String home = model.getAttribute("home");
        String remote = model.getAttribute("remote");
        String ejbMethod = model.getAttribute("ejbMethod");

        EJBModel ejb;
        if("SFSB".equalsIgnoreCase(type)){
            ejb = new StatefulSessionEJBModel();
            String createMethod = model.getAttribute("createMethod");
            int begin = createMethod.indexOf('(');
            int end = createMethod.lastIndexOf(')');
            if(begin>0 && end >0){
                String params = createMethod.substring(begin+1,end);
                StringTokenizer st = new StringTokenizer(params,",");
                int count = st.countTokens();
                String[] tokens = new String[count];
                for(int i=0;i<count;i++){
                    tokens[i]=st.nextToken();
                }
                CreateMethod cm = new CreateMethod();
                cm.setParamNames(tokens);
                cm.setParamClasses(this.getParamTypes(tokens));
                ((StatefulSessionEJBModel)ejb).setCreateMethod(cm);
            }else{
                ((StatefulSessionEJBModel)ejb).setCreateMethod(new CreateMethod());
            }
        }else{
            ejb = new StatelessSessionEJBModel();
        }

        ejb.setInitialFactory(initialFactory);
        ejb.setProviderURL(providerUrl);
        ejb.setJndi(jndi);
        ejb.setEjbMethod(ejbMethod);
        ejb.setHome(home);
        ejb.setRemote(remote);

        //initialize parameters of this model;
        NodeList paramNodes = model.getElementsByTagName(Action.PARAMREF);
        initModelParameters(paramNodes, ejb);
        ParameterRef[] param_refs = ejb.getParameterRefs();
        Class[] param_types = getParamTypes(param_refs);
        ejb.setParameterTypes(param_types);

        return ejb;
    }


    /**
     * @param nl
     * @param model
     */
    void initModelParameters(NodeList nl, Model model) {
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element paramNode = (Element) nl.item(i);

            String name = paramNode.getAttribute(Action.NAME);
            String type = paramNode.getAttribute(Action.TYPE);
            if (type != null && type.length() < 1) {
                type = null;
            }
            ParameterRef ref = new ParameterRef(name, type);

            //get the referenced parameter;
            Parameter p = this.getParameter(name);
            if (p != null) {
                ref.setOrginalType(p.getType());
                model.addParameterRef(ref);
            } else {
                addErrorMsg("model parameter \""+name+"\" not defined.");
                if (log.isEnabledFor(Priority.ERROR)) {
                    log.error("can't find parameter: " + name);
                }
                ok = false;
            }
        }
    }

    /**
     * @param xml
     * @param actionNode
     */
    void initActionDispatches(XMLDocument xml, Element actionNode) {
        int index = 0;
        AbstractDispatch disp = null;

        Node dispsNode = xml.selectSingleNode(actionNode, Action.DISPATCHES);

        //check the screen displayName;
        String screenName = xml.getSingleNodeValue(dispsNode, "@" + Action.SCREEN);
        if (screenName != null && screenName.length() > 0) {
            this.screenName = screenName;
        }

        if (dispsNode != null) {
            NodeList dispNodes = dispsNode.getChildNodes();
            int count = dispNodes.getLength();
            for (int i = 0; i < count; i++) {
                Node n = dispNodes.item(i);
                String nodeName = n.getNodeName();

                if (Dispatch.DISP_REDIRECT.equals(nodeName)) {
                    disp = initRedirectDispatch((Element) n, index);
                } else if (Dispatch.DISP_INCLUDE.equals(nodeName)) {
                    disp = initIncludeDispatch((Element) n);
                } else if (Dispatch.DISP_FORWARD.equals(nodeName)) {
                    disp = initForwardDispatch((Element) n);
                } else if (Dispatch.DISP_CUSTOM.equals(nodeName)) {
                    disp = initForwardDispatch((Element) n);
                } else {
                    continue;
                }
                index = index + 1;

                try {
                    addDispatch(disp);
                } catch (ExprSyntaxException e) {
                    addErrorMsg(e.getExpression()+" failed, reason: "+e.getMessage());
                    if (log.isEnabledFor(Priority.ERROR)) {
                        log.error("can't build boolean expression:" + disp.getOn(), e);
                    }
                    ok = false;
                }
            }
        }
    }

    /**
     * init a ForwardDispatch for an Action;
     *
     * @param dispNode
     */
    AbstractDispatch initForwardDispatch(Element dispNode) {
        String uri = dispNode.getAttribute(Action.URI);
        String on = dispNode.getAttribute(Action.ON);
        ForwardDispatch fd = new ForwardDispatch(uri);
        if (on.length() > 0) {
            fd.setOn(on);
        }
        return fd;
    }

    AbstractDispatch initCustomDispatch(Element dispNode) {
        String on = dispNode.getAttribute(Action.ON);
        String cls = dispNode.getAttribute(Action.CLASS);
        CustomDispatch cd = new CustomDispatch(cls);
        if (on.length() > 0) {
            cd.setOn(on);
        }
        return cd;
    }

    /**
     * initialize a include dispatch;
     *
     * @param dispNode
     */
    AbstractDispatch initIncludeDispatch(Element dispNode) {
        String uri = dispNode.getAttribute(Action.URI);
        String on = dispNode.getAttribute(Action.ON);
        IncludeDispatch id = new IncludeDispatch(uri);
        if (on.length() > 0) {
            id.setOn(on);
        }
        return id;
    }

    /**
     * initialize a redirect dispatch. This type dispatch represents a HttpServletResponse.sendRedirect()
     * operation. The redirect dispatch may also contains some parameters, these parameters can be constant values,
     * or reference the parameters of the current action;
     *
     * @param dispNode
     */
    AbstractDispatch initRedirectDispatch(Element dispNode, int no) {
        String uri = dispNode.getAttribute(Action.URI);
        String on = dispNode.getAttribute(Action.ON);
        String context = dispNode.getAttribute(Action.CONTEXT);
        RedirectDispatch rd = new RedirectDispatch(uri);
        if (on.length() > 0) {
            rd.setOn(on);
        }

        if (context.length() > 0) {
            rd.setTargetContext(context);
        }

        NodeList paramNodes = dispNode.getElementsByTagName(Action.PARAMETER);
        int count = paramNodes.getLength();
        for (int i = 0; i < count; i++) {
            Element paramNode = (Element) paramNodes.item(i);
            String name = paramNode.getAttribute(Action.NAME);
            if (name.length() > 0) { //displayName can't be null;
                RedirectParameter rp = new RedirectParameter();
                rp.setName(name);

                String value = paramNode.getAttribute(Action.VALUE);
                if (value.length() > 0) {
                    rp.setValue(value);
                    try {
                        StringExpression exp = expressionBuilder.buildString(this.name + "_disp_" + no +
                                "_rdparam_" + name, value, this.getParameterTypes());
                        rp.setExpression(exp);
                    } catch (ExprSyntaxException e) {
                        addErrorMsg(e.getExpression()+" failed, reason: "+e.getMessage());
                        if (log.isEnabledFor(Priority.ERROR)) {
                            log.error("can't build the String Expression: " + value, e);
                        }
                        ok = false;
                    }
                }
                rd.addParameter(rp);
            }
        }
        return rd;
    }
}