/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.event;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.xml.XMLFile;
import com.dream.bizsdk.common.blc.IBLContext;

import java.util.ArrayList;
import java.io.File;

import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-24
 * Time: 14:42:11
 */
public class EventManager {
    private XMLFile registry;

    private ListenerRepository lr = new ListenerRepository();

    private Logger log = Logger.getLogger("com.dream.event");

    private IBLContext context;

    public EventManager() {

    }

    public EventManager(IBLContext context) {
        this.context = context;
    }

    public ListenerRepository getListenerRepository() {
        return lr;
    }

    public IBLContext getContext() {
        return context;
    }

    public void setContext(IBLContext context) {
        this.context = context;
    }

    public void init(String configFilePath) throws EventInitializationException {
        init(new File(configFilePath));
    }

    /**
     * initialize this EventManager according to a register file;
     *
     * @param configFile the path of the Event Register file;
     * @throws EventInitializationException
     */
    public void init(File configFile) throws EventInitializationException {
        try {
            registry = new XMLFile(configFile);
            NodeList nl = registry.selectNodeList("/events/event");
            int eventCount = nl.getLength();
            for (int i = 0; i < eventCount; i++) {
                Element e = (Element) nl.item(i);
                String name = e.getAttribute("name");
                if (name != null) {
                    //listeners to the event;
                    NodeList ln = registry.selectNodeList(e, "listeners/listener");
                    int lCount = ln.getLength();
                    for (int j = 0; j < lCount; j++) {
                        Element e2 = (Element) ln.item(j);
                        String className = e2.getAttribute("class");
                        try {
                            if (className != null && className.length() > 0) {
                                Class c = Class.forName(className);
                                EventListener el = (EventListener) c.newInstance();
                                lr.addListener(name, el);
                            }
                        } catch (ClassNotFoundException cnf) {
                            log.error("can't find class " + className, cnf);
                        } catch (InstantiationException e1) {
                            log.error("can't create object of class " + className, e1);
                        } catch (IllegalAccessException e1) {
                            log.error("can't access object of class " + className, e1);
                        } catch (ClassCastException e1) {
                            log.error("the registed class " + className + " is not a subclass of " + EventListener.class.getName(), e1);
                        }
                    }

                    //direct actions to the event;
                    NodeList an = registry.selectNodeList(e, "actions/action");
                    int aCount = an.getLength();
                    for (int j = 0; j < aCount; j++) {
                        Element e2 = (Element) an.item(j);
                        String lname = e2.getAttribute("name");
                        String className = e2.getAttribute("class");
                        String methodName = e2.getAttribute("method");
                        EventListenerImpl el = new EventListenerImpl(lname, context, className, methodName);
                        lr.addListener(name, el);

                        if (log.isDebugEnabled()) {
                            log.debug("action " + lname + " registered to event " + name);
                        }
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("Event " + name + " registered.");
                    }
                }
            }
        } catch (Exception nie) {
            throw new EventInitializationException(nie);
        }
    }

    /**
     * @param e
     */
    protected void onEvent(Event e) {
        if (log.isInfoEnabled()) {
            log.info("accepted event " + e.getName());
        }

        //set the contex of the BLContext;
        e.setContext(context);

        String name = e.getName();
        ArrayList al = lr.getListeners(name);
        int count = al.size();

        for (int i = 0; i < count; i++) {
            EventListener el = (EventListener) al.get(i);
            el.onEvent(e);
        }
    }

    /**
     * @param name
     * @param data
     */
    protected void onEvent(String name, BizData data) {
//        if(log.isInfoEnabled()){
//            log.info("accepted event "+name);
//        }

        Event e = new Event(name, data);
        e.setContext(context);
        ArrayList al = lr.getListeners(name);
        int count = al.size();

        for (int i = 0; i < count; i++) {
            EventListener el = (EventListener) al.get(i);
            el.onEvent(e);
        }
    }

    /**
     * Call this method to notify a nameed event to the target Listeners;
     *
     * @param name the name of the event;
     * @param data the main data assoctated with the event;
     */
    public void notifyEvent(String name, BizData data) {
        onEvent(name, data);
    }

    /**
     * Call this method to notify a named event to the target Listeners;
     *
     * @param name the name of the Event;
     * @param d    the main data associated with the event;
     * @param sd   the session data associated with the event;
     */
    public void notifyEvent(String name, BizData d, BizData sd) {
        onEvent(new Event(name, d, sd));
    }

    /**
     * Call this method to notify event to the targetListeners;
     *
     * @param e
     */
    public void notifyEvent(Event e) {
        onEvent(e);
    }

    /**
     * @param eventName
     */
    public void addEvent(String eventName) {
        lr.addListener(eventName, null);
    }

    /**
     * @param eventName
     * @param l
     */
    public void addListener(String eventName, EventListener l) {
        lr.addListener(eventName, l);
    }

    /**
     * @param eventName
     * @param className
     * @param methodName
     */
    public void addListener(String eventName, String className, String methodName) {
        lr.addListener(eventName, new EventListenerImpl("BLListener", context, className, methodName));
    }
}