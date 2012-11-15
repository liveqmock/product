/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.controller;

import com.cots.util.*;
import com.cots.bean.BeanFactory;
import com.cots.exp.Builder;
import com.cots.blc.BLCPool;
import com.cots.blc.BLContext;
import com.cots.mvc.dispatch.ScreenMapping;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.io.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Description:
 *      Action Mapping maintains a cache of Actions defined in this Application.
 * An application has a collection of actions. At the startup time, those actions
 * will be parsed from a xml file and added to this Mapping object.
 *
 * User: chuguanghua
 * Date: 2004-10-10
 * Time: 13:31:57
 * Version: 1.0
 */
public final class ActionMapping implements Cache{


    //actions map, the key is the displayName of the Action,the value is Action object;
    private HashMap actions = new HashMap();

    //bean factory used by this mapping;
    private BeanFactory beanFactory;

    //expression Builder used by this mapping;
    private Builder expressionBuilder;

    //blcs pool;
    private BLCPool blcPool;

    //log object;
    private Logger log;

    //screen mapping object;
    private ScreenMapping screenMap;

    //blcontext object;
    private BLContext context;

    //the config root;
    private String configRoot;

    private String[] fileNames;

    private String[] filePathes;


    public ActionMapping() {
        log = Logger.getLogger(this.getClass());
    }

    /**
     * get the BeanFactory used by this Mapping;
     *
     * @return BeanFactory object used by this Mapping;
     */
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * set the BeanFactory that will be used by this Mapping;
     *
     * @param beanFactory the new BeanFactory that will be used;
     */
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * get the ExpressionBuilder that is used by this Mapping.
     *
     * @return the ExpressionBuilder object that is used by this object;
     */
    public Builder getExpressionBuilder() {
        return expressionBuilder;
    }

    /**
     * set the ExpressionBuilder that will be used by this mapping;
     *
     * @param expressionBuilder the new ExpressionBuilder object;
     */
    public void setExpressionBuilder(Builder expressionBuilder) {
        this.expressionBuilder = expressionBuilder;
    }

    /**
     * get the BLC pool object;
     *
     * @return
     */
    public BLCPool getBlcPool() {
        return blcPool;
    }

    /**
     * set the BLC object that will be used by this mapping;
     *
     * @param blcPool the new BLCPool Object;
     */
    public void setBlcPool(BLCPool blcPool) {
        this.blcPool = blcPool;
    }

    /**
     * get the BLContext that is used by this Mapping;
     *
     * @return the BLContext object that is used;
     */
    public BLContext getContext() {
        return context;
    }

    /**
     * set the BLContext that will be used by this Mapping;
     *
     * @param context the BlContext object that will be used;
     */
    public void setContext(BLContext context) {
        this.context = context;
    }

    /**
     * 
     *
     * @return
     */
    public String getConfigRoot(){
        return this.configRoot;
    }

    /**
     * get the ScreenMapping object that this mapping is using;
     *
     * @return the ScreenMapping object that is using;
     */
    public ScreenMapping getScreenMap() {
        return screenMap;
    }

    /**
     * set the ScreenMapping that will be used by this Mapping object;
     *
     * @param screenMap the new ScreenMapping that will be used;
     */
    public void setScreenMap(ScreenMapping screenMap) {
        this.screenMap = screenMap;
    }

    /**
     *
     *
     * @param rootPath
     */
    public boolean init(String rootPath){
        configRoot = FileUtil.getRegularPath(rootPath);
        return refresh();
    }

    /**
     * refresh this cache;
     *
     */
    public boolean refresh(){
        File[] defXMLs = FileUtil.filerFiles(configRoot,".xml");
        context.newLoader();
        expressionBuilder.setLoader(context.getLoader());        
        return init(defXMLs);
    }

    /**
     * initialize the mapping from a base directory.
     * all the valid xml in this directory will be parsed and
     * processed, actions will be created from these xml files;
     *
     * @param filePathes the directory contains the action mapping xml files;
     */
    protected void init(String[] filePathes) throws FileNotFoundException{
        int count = filePathes.length;

        //save the file names;
        this.fileNames = new String[filePathes.length];
        for(int i=0;i<filePathes.length;i++){
            this.fileNames[i] = FileUtil.getFileName(filePathes[i]);
        }

        this.filePathes = new String[filePathes.length];

        System.arraycopy(filePathes,0,this.filePathes,0,filePathes.length);

        InputStream[] iss = new InputStream[count];
        for(int i=0;i<count;i++){
            iss[i] = new FileInputStream(new File(filePathes[i]));
        }
        init(iss,this.filePathes);
    }


    /**
     * initialize files;
     *
     * @param files  files to be initialized;
     * @return true of all the actions in the files are initialized successfully;
     * false otherwise.
     */
    protected boolean init(File[] files){
        boolean succ = true;
        int count = files.length;

        //save the file names;
        this.fileNames = new String[files.length];
        this.filePathes = new String[files.length];
        for(int i=0;i<files.length;i++){
            this.fileNames[i] = FileUtil.getFileName(files[i]);
            this.filePathes[i] = files[i].getAbsolutePath();
        }
        
        InputStream[] iss = new InputStream[count];
        for(int i=0;i<count;i++){
            try{
                iss[i] = new FileInputStream(files[i]);
            }catch(FileNotFoundException e){
                log.error("initialize actions def files error:",e);
                succ = false;
            }
        }
        succ &= init(iss,this.filePathes);
        return succ;
    }

    /**
     * initialize this mapping.
     *
     * @param is InputStream array of the xml definition files
     */
    protected boolean init(InputStream[] is,String[] names){
        boolean succ = true;
        int count = is.length;
        for(int i=0;i<count;i++){
            try {
                if(is[i]!=null){
                    XMLDocument xml = new XMLDocument(XMLDocument.fromInputStream(is[i]));

                    initModule(xml,names[i]);
                }
            } catch (ParserConfigurationException e) {
                if(log.isEnabledFor(Priority.ERROR)){
                    log.error("can't parse action mapping files "+e);
                }
                succ = false;
                //can't continue to parse the left files,so we just ignore them;
                break;
            } catch (IOException e) {
                if(log.isEnabledFor(Priority.ERROR)){
                    log.error("can't parse action mapping file "+e);
                }
                succ = false;
            } catch (SAXException e) {
                if(log.isEnabledFor(Priority.ERROR)){
                    log.error("can't parse action mapping file "+e);
                }
                succ = false;
            }
        }
        return succ;
    }


    /**
     * get a named action.
     * The ControllerServlet will call this method to get action
     * to run client's HttpServletRequest;
     *
     * @param name the displayName of the action to get;
     * @return the target DefaultAction object;
     */
    public Action getAction(String name){
        return (Action)actions.get(name);
    }

    /**
     * add an action to this action mapping;
     *
     * @param action the action to be added.
     */
    public void addAction(Action action){
        actions.put(action.getName(),action);
    }

    /**
     *
     * @return
     */
    public String[] getConfigFiles(){
       String[] names = new String[this.fileNames.length];
       System.arraycopy(this.fileNames,0,names,0,this.fileNames.length);
       return names;
    }

    /**
     * get the names of all the beans in a configuration files.
     *
     * @param fileName the displayName of the configuration file;
     * @return the bean names array, if the file doest not exist, or is not a valid
     * xml file, then null is returned, otherwise never null;
     */
    public String[] getActionNamesInConfFile(String fileName){
        try{
            File confFile = new File(configRoot+fileName);
            XMLFile xml = new XMLFile(confFile);
            NodeList nl1 = xml.selectNodeList("/action-mapping/DefaultAction");
            NodeList nl2 = xml.selectNodeList("/action-mapping/CustomAction");
            int count1 = 0;
            int count2 = 0;

            if(nl1!=null){
                count1 = nl1.getLength();
            }
            if(nl2!=null){
                count2 = nl2.getLength();
            }
            String[] names = new String[count1+count2];
            for(int i=0;i<count1;i++){
                names[i] = ((Element)(nl1.item(i))).getAttribute("name");
            }
            for(int i=0;i<count2;i++){
                names[count1+i] = ((Element)(nl2.item(i))).getAttribute("name");
            }
            return names;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * create a new DefaultAction from this mapping. The objects that need by the new
     * action will set automatically, thoese objects inlcude ExpressionBuilder,
     * BblcPool and BeanFactory object.
     *
     * @param name the displayName of the DefaultAction;
     * @return a DefaultAction without parameters, models or dispatches;
     */
    public DefaultAction createDefaultAction(String name){
        DefaultAction da = new DefaultAction(name);
        da.setExpressionBuilder(expressionBuilder);
        da.setBlcPool(blcPool);
        da.setBeanFactory(beanFactory);
        actions.put(name,da);
        return da;
    }
    /**
     * return the actions mappings with the action displayName as the key and the Action
     * as the Value;
     *
     * @return the map object;
     */
    public HashMap getActionMappings(){
        return actions;
    }

    /**
     * initialize a Action Definition file(xml format).
     *
     * @param xml
     */
    void initModule(XMLDocument xml,String filename){
        //select all the action nodes;
        Element docNode = xml.getDocument().getDocumentElement();
        if(docNode!=null){
            NodeList childNodes = docNode.getChildNodes();
            int count = childNodes.getLength();
            for(int i=0;i<count;i++){
                Node child = childNodes.item(i);
                String nodeName = child.getNodeName();

                //check if this node is a DefaultAction node;
                if(Action.DEFAULT_ACTION.equals(nodeName)){
                    DefaultAction action = initDefaultAction(xml,(Element)child);
                    action.context = context;
                    action.regFile = filename;

                    initActionFiltersAndListeners(action);

                    actions.put(action.getName(),action);

                }else if(Action.CUSTOM_ACTION.equals(nodeName)){     //if a CustomAction;
                    AbstractAction action = initCustomAction(xml,(Element)child);
                    initActionFiltersAndListeners(action);
                    action.regFile = filename;
                    actions.put(action.getName(),action);
                }

                //add codes to support other types of action;
            }
        }
    }

    /**
     * initialize a DefaultAction object in a definition file;
     *
     * @param xml the action definition file;
     * @param actionNode the current DefaultAction node;
     * @return a DefaultAction object;
     */
    DefaultAction initDefaultAction(XMLDocument xml,Element actionNode){
        DefaultAction da = new DefaultAction();
        da.setExpressionBuilder(expressionBuilder);
        da.setBlcPool(blcPool);
        da.setBeanFactory(beanFactory);
        da.init(xml,actionNode);

        //check the scrren;
        String screenName = da.getScreenName();
        if(screenName!=null){
            da.setScreen(screenMap.get(screenName));
        }
        return da;
    }

    /**
     * initialize a custom action in a definition file;
     * 
     * @param xml the actions definition file;
     * @param actionNode the current action node;
     * @return a initialized CustomAction object;
     */
    CustomAction initCustomAction(XMLDocument xml,Element actionNode){
        CustomAction ca = new CustomAction();
        ca.init(xml,actionNode);
        return ca;
    }

    /**
     *
     * @param action
     */
    void initActionFiltersAndListeners(AbstractAction action){
        List filters = context.getActionFilters();
        for(int k=0;k<filters.size();k++){
            ObjectWrapper ow = (ObjectWrapper)filters.get(k);
            String pattern = (String)ow.getAttribute("action");
            if(Pattern.matches(pattern,action.getName())){
                action.addFilter((ActionFilter)ow.getObject());
            }
        }

        List listeners = context.getActionListeners();
        for(int k=0;k<listeners.size();k++){
            ObjectWrapper ow = (ObjectWrapper)listeners.get(k);
            String pattern = (String)ow.getAttribute("action");
            if(Pattern.matches(pattern,action.getName())){
                action.addListener((ActionListener)ow.getObject());
            }
        }
    }
}