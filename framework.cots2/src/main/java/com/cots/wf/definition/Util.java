/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.definition;

import com.cots.util.XMLFile;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-31
 * Time: 10:14:26
 */
public class Util {

    /**
     * Load a process from a workflow definition file(.xml).
     *
     * @param wfName the displayName of the workflow, also the file displayName of the xml file;
     * @return the WFProcess ojbect based on the xml file;
     */
    public static WFProcess getProcess(String wfName) {
        WFProcess proc = new WFProcess();
        XMLFile xml = null;

        try {
            xml = new XMLFile(WFProcess.HOME_PATH + wfName + ".xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Element e = xml.getDocument().getDocumentElement();

        loadProcessAttributes(proc, e);

        Element c = (Element) xml.selectSingleNode(e, "applications");
        loadApplications(proc, xml, c);

        c = (Element) xml.selectSingleNode(e, "participants");
        loadParticipants(proc, xml, c);

        c = (Element) xml.selectSingleNode(e, "datafields");
        loadDataFields(proc, xml, c);

        c = (Element) xml.selectSingleNode(e, "activities");
        loadActivities(proc, xml, c);

        c = (Element) xml.selectSingleNode(e, "transitions");
        loadTransitions(proc, xml, c);

        /*
        c = (Element)xml.selectSingleNode(e,"events");
        loadEvents(proc,xml,c);
        */

        return proc;
    }

    /**
     * load the attributes from the definition file;
     *
     * @param proc the WFProcess object;
     * @param e    the <workflow> element in the definition xml file;
     */
    private static void loadProcessAttributes(WFProcess proc, Element e) {
        String value = e.getAttribute("id");
        if (value.length() > 0) {
            proc.setID(value);
        }

        value = e.getAttribute("name");
        if (value.length() > 0) {
            proc.setName(value);
        }

        value = e.getAttribute("lifeCycle");
        if (value.length() > 0) {
            proc.setLifeCycle(Long.parseLong(value));
        }

        value = e.getAttribute("description");
        if (value.length() > 0) {
            proc.setDescription(value);
        }
    }

    /**
     * load all the activities from the definition file into the WF Object;
     *
     * @param proc the WFPorcess Object;
     * @param xml  the definition xml file;
     * @param e    the <activities> element;
     */
    private static void loadActivities(WFProcess proc, XMLFile xml, Element e) {
        NodeList nl = xml.selectNodeList(e, "activity");
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element n = (Element) nl.item(i);
            WFAbstractActivity act = loadActivity(proc, xml, n);
            if (act != null) {
                //set common attributes;
                String attr = n.getAttribute("id");
                act.setID(attr);
                attr = n.getAttribute("lifeCycle");
                if (attr.length() > 0) {
                    act.setLifeCycle(Integer.parseInt(attr));
                }
                attr = n.getAttribute("joinMode");
                act.setJoinMode(attr);
                attr = n.getAttribute("splitMode");
                act.setSplitMode(attr);
                attr = n.getAttribute("isStart");
                act.setStart(attr.compareToIgnoreCase("true") == 0 ? true : false);
                attr = n.getAttribute("isEnd");
                act.setEnd(attr.compareToIgnoreCase("true") == 0 ? true : false);

                proc.addActivity(act);
            }
        }
    }

    /**
     * load a specified activity from the xml file;
     *
     * @param xml the definition file;
     * @param e   a <atctivity> element;
     * @return WFActivity object based on the <activity> element;
     */
    private static WFAbstractActivity loadActivity(WFProcess proc, XMLFile xml, Element e) {
        int gCount = 0;
        WFAbstractActivity act = null;
        Element[] a = new Element[4];
        Element g = null;
        a[0] = (Element) xml.selectSingleNode(e, "generic");
        a[1] = (Element) xml.selectSingleNode(e, "route");
        a[2] = (Element) xml.selectSingleNode(e, "application");
        a[3] = (Element) xml.selectSingleNode(e, "subprocess");
        for (int i = 0; i < 4; i++) {
            if (a[i] != null) {
                g = a[i];
                gCount++;
            }
        }
        if (gCount == 1) {
            String nodeName = g.getNodeName();
            if (nodeName.compareToIgnoreCase("generic") == 0) {
                WFGenericActivity ga = new WFGenericActivity();
                String value = xml.getSingleNodeValue(g, "action");
                ga.setAction(value);
                value = xml.getSingleNodeValue(g, "participant");
                ga.setPerformerID(value);
                ga.setParticipant(proc.getParticipant(value));
                act = ga;
            } else if (nodeName.compareToIgnoreCase("route") == 0) {
                Element gr = null;
                if ((gr = (Element) xml.selectSingleNode(g, "decision")) != null) {
                    WFRouteDecision dec = new WFRouteDecision();
                    String condition = gr.getAttribute("condition");
                    dec.setExpression(condition);
                    act = dec;
                } else if ((gr = (Element) xml.selectSingleNode(g, "and")) != null) {
                    act = new WFRouteAnd();
                } else if ((gr = (Element) xml.selectSingleNode(g, "xor")) != null) {
                    act = new WFRouteXOr();
                }
            } else if (nodeName.compareToIgnoreCase("applicaiton") == 0) {
                Node tn = g.getFirstChild();
                if (tn != null) {
                    String applicationID = tn.getNodeValue();
                    WFApplicationActivity act2 = new WFApplicationActivity();
                    act2.setApplicationID(applicationID);
                    act = act2;
                }
            } else if (nodeName.compareToIgnoreCase("subprocess") == 0) {
                Node tn = g.getFirstChild();
                if (tn != null) {
                    String procID = tn.getNodeValue();
                    WFSubFlow act2 = new WFSubFlow();
                    act2.setSubflowName(procID);
                    String syn = g.getAttribute("synchronized");
                    if (syn != null && syn.compareToIgnoreCase("true") == 0) {
                        act2.setAsynExec(false);
                    } else {
                        act2.setAsynExec(true);
                    }
                    act = act2;
                }
            } else {

            }
        }
        return act;
    }

    /**
     * load all the transitions from the definition file;
     *
     * @param proc  the WFProcess object;
     * @param xml   the definition file;
     * @param trsts the <transitions> element;
     */
    private static void loadTransitions(WFProcess proc, XMLFile xml, Element trsts) {
        NodeList nl = xml.selectNodeList(trsts, "transition");
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element n = (Element) nl.item(i);
            WFTransition pt = new WFTransition();
            String value = xml.getSingleNodeValue(n, "fromActivity");
            pt.setFromActivityName(value);
            value = xml.getSingleNodeValue(n, "toActivity");
            pt.setToActivityName(value);
            value = xml.getSingleNodeValue(n, "condition");
            pt.setCondition(value);
            value = xml.getSingleNodeValue(n, "id");
            pt.setID(value);

            NodeList nl2 = xml.selectNodeList(n, "extendedAttributes");
            int count2 = nl2.getLength();
            for (int j = 0; j < count2; j++) {
                Element ea = (Element) nl2.item(j);
                String name = xml.getSingleNodeValue(ea, "name");
                value = xml.getSingleNodeValue(ea, "value");
                pt.setExtendedAttribute(name, value);
            }
            proc.addTransition(pt);
        }
    }

    /**
     * load all the participants from the definition file;
     *
     * @param proc the WFProcess object;
     * @param xml  the defintion xml file;
     * @param ps   the <participants> element;
     */
    private static void loadParticipants(WFProcess proc, XMLFile xml, Element ps) {
        NodeList nl = xml.selectNodeList(ps, "applicaiton");
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element n = (Element) nl.item(i);
            WFParticipant pt = new WFParticipant();
            String value = xml.getSingleNodeValue(n, "id");
            pt.setID(value);
            value = xml.getSingleNodeValue(n, "type");
            pt.setType(value);
            value = xml.getSingleNodeValue(n, "url");
            pt.setUrl(value);
            NodeList nl2 = xml.selectNodeList(n, "extendedAttributes");
            int count2 = nl2.getLength();
            for (int j = 0; j < count2; j++) {
                Element ea = (Element) nl2.item(j);
                String name = xml.getSingleNodeValue(ea, "name");
                value = xml.getSingleNodeValue(ea, "value");
                pt.setExtendedAttribute(name, value);
            }
            proc.addParticipant(pt);
        }
    }

    /**
     * load all the applications from the definition file;
     *
     * @param proc the WFProcess object;
     * @param xml  the definition xml file;
     * @param apps the <applicaitons> element;
     */
    private static void loadApplications(WFProcess proc, XMLFile xml, Element apps) {
        NodeList nl = xml.selectNodeList(apps, "applicaiton");
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element n = (Element) nl.item(i);
            WFApplication app = new WFApplication();
            String value = xml.getSingleNodeValue(n, "id");
            app.setID(value);
            value = xml.getSingleNodeValue(n, "type");
            app.setType(value);
            value = xml.getSingleNodeValue(n, "url");
            app.setUrl(value);
            NodeList nl2 = xml.selectNodeList(n, "extendedAttributes");
            int count2 = nl2.getLength();
            for (int j = 0; j < count2; j++) {
                Element ea = (Element) nl2.item(j);
                String name = xml.getSingleNodeValue(ea, "name");
                value = xml.getSingleNodeValue(ea, "value");
                app.setExtendedAttribute(name, value);
            }
            proc.addApplication(app);
        }
    }

/*
    private static void loadEvents(WFProcess proc,XMLFile xml,Element events){

    }
*/

    /**
     * load all data fields definition from the definition file;
     *
     * @param proc       the WFProcess object;
     * @param xml        the definition xml file;
     * @param datafields the <datafields> element;
     */
    private static void loadDataFields(WFProcess proc, XMLFile xml, Element datafields) {
        NodeList nl = xml.selectNodeList(datafields, "datafield");
        int count = nl.getLength();
        for (int i = 0; i < count; i++) {
            Element n = (Element) nl.item(i);
            WFDataField app = new WFDataField();
            String value = xml.getSingleNodeValue(n, "id");
            app.setID(value);
            value = xml.getSingleNodeValue(n, "type");
            app.setType(value);
            value = xml.getSingleNodeValue(n, "length");
            if (value != null && value.length() > 0) {
                app.setLength(Integer.parseInt(value));
            }
            value = xml.getSingleNodeValue(n, "initialValue");
            app.setInitialValue(value);
            proc.addDataField(app);
        }
    }
}
