/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.util.xml.XMLFile;
import com.dream.bizsdk.common.databus.BizData;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.io.IOException;
import java.io.File;

import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * Description:
 * BLRequest holds the name of the request and the Models list to process the request,
 * and the dipatch(view) list to display the result to client.
 * A web application has one requests.xml located in /WEB-INF/ to depict all requests
 * definitions in this application.
 * User: chuguanghua
 * Date: 2003-12-27
 * Time: 14:43:47
 */
public final class BLRequests {
    protected String filePath;

    private HashMap requests = new HashMap();

    protected ArrayList modelsBefore = new ArrayList();

    protected ArrayList modelsAfter = new ArrayList();

    protected ArrayList dispatchesAfter = new ArrayList();

    protected ArrayList dispatchesBefore = new ArrayList();

    public BLRequests(String filePath) throws IOException, ParserConfigurationException, SAXException {
        this.filePath = filePath;
        init();
    }

    /**
     * get the names BLRequest object;
     *
     * @param request
     * @return
     */
    public BLRequest getRequest(String request) {
        return (BLRequest) requests.get(request);
    }

    /**
     * init this Object;
     *
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    protected void init() throws IOException, ParserConfigurationException, SAXException {
    	recursion(filePath);
    }
    
    /**
     * 
     * @param requestsFile
     * @param i
     * @param count
     * @param j
     * @throws FactoryConfigurationError
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private void initRequestsFiles(File requestsFile, int i, int count, int j) throws FactoryConfigurationError, ParserConfigurationException, SAXException, IOException{
    	
    	if (requestsFile.isFile() && requestsFile.getName().toLowerCase().endsWith("-reqs.xml")) {
            XMLFile xml = new XMLFile(requestsFile);
            NodeList nl = xml.selectNodeList("requests/request");
            i = 0;
            count = nl.getLength();
            while (i < count) {

                Element n = (Element) nl.item(i);
                String desc = xml.getSingleNodeValue(n, "desc");
                String name = xml.getSingleNodeValue(n, "name");
                BLRequest req = new BLRequest(name);
                req.setDesc(desc);

                initDispatches(n, xml, req);
                initModels(n, xml, req);
                initValidation(n, xml, req);
                initFieldValidations(n, xml, req);
                initDataFilters(n, xml, req);

                String tx = n.getAttribute("tx");
                if (tx.length() > 0 && "true".equalsIgnoreCase(tx)) {
                    req.setTX(true);
                }
                requests.put(name, req);
                i++;
            }
            initBeforeRequestModels(xml);
            initAfterRequestModels(xml);
            initBeforeRequestDispatches(xml);
            initAfterRequestDispatches(xml);
        }
        j++;
        populateGroupModels();
        populateGroupDispatches();
    }
    
    private void recursion(String filePath) throws FactoryConfigurationError, ParserConfigurationException, SAXException, IOException{
    	
    	int i = 0;
        int count = 0;
        int j = 0;
    	File f = new File(filePath);
    	File[] files = f.listFiles();
    	for(int ii=0;ii<files.length;ii++){
    		if(files[ii].isDirectory()){
    			recursion(files[ii].getPath());
    		}else{
    			initRequestsFiles(files[ii], i, count, j);
    		}
    	}
    }

    /**
     * Init dispatches for a specified request;
     *
     * @param n   a request node in the requests.xml
     * @param xml the XMLFile object for requests.xml
     */
    protected void initDispatches(Node n, XMLFile xml, BLRequest req) {
        int i = 0;
        int count = 0;

        NodeList dispNodes = xml.selectNodeList(n, "dispatch");
        count = dispNodes.getLength();

        while (i < count) {
            Element d = (Element) dispNodes.item(i++);
            String url = d.getAttribute("url");
            String type = d.getAttribute("type");
            String on = d.getAttribute("on");
            String def = d.getAttribute("default");
            req.addDispatch(new Dispatch(url, type, on, def));
        }
    }

    /**
     * Init models for a specified request;
     *
     * @param n   a request node in the requests.xml
     * @param xml the XMLFile object for requests.xml
     */
    protected void initModels(Node n, XMLFile xml, BLRequest req) {
        int i = 0;
        int count = 0;
        NodeList mdNodes = xml.selectNodeList(n, "model");
        count = mdNodes.getLength();
        while (i < count) {
            Element d = (Element) mdNodes.item(i++);
            String mdName = d.getAttribute("name");
            String on = d.getAttribute("on");
            String asyn = d.getAttribute("asyn");
            req.addModel(new Model(mdName, on, asyn != null && asyn.compareToIgnoreCase("true") == 0 ? true : false));
        }
    }

    /**
     * @param n
     * @param xml
     */
    protected void initValidation(Node n, XMLFile xml, BLRequest req) {
        int i = 0;
        int count = 0;

        NodeList mdNodes = xml.selectNodeList(n, "validate/on");
        count = mdNodes.getLength();

        while (i < count) {
            Element d = (Element) mdNodes.item(i);
            String cond = d.getAttribute("cond");
            String msg = d.getAttribute("message");
            req.addValidation(new Validation(cond, msg));
            i++;
        }
    }

    /**
     * init field validataions;
     *
     * @param n
     * @param xml
     */
    protected void initFieldValidations(Node n, XMLFile xml, BLRequest req) {
        int i = 0;
        int count = 0;
        int minLength = 0;
        int maxLength = 0;

        NodeList mdNodes = xml.selectNodeList(n, "validate/field");
        count = mdNodes.getLength();

        while (i < count) {
            Element d = (Element) mdNodes.item(i);
            String fieldName = d.getAttribute("name");
            String type = d.getAttribute("type");
            String required = d.getAttribute("required");
            String minValue = d.getAttribute("minValue");
            String maxValue = d.getAttribute("maxValue");
            String sMaxLength = d.getAttribute("maxLength");
            String sMinLength = d.getAttribute("minLength");

            try {
                minLength = Integer.valueOf(sMinLength).intValue();
            } catch (Exception e) {
                minLength = 0;
            }
            try {
                maxLength = Integer.valueOf(sMaxLength).intValue();
            } catch (Exception e) {
                maxLength = Integer.MAX_VALUE;
            }

            if (fieldName != null && type != null) {
                req.addFieldValidations(new FieldValidation(fieldName, type,
                        required != null && required.compareToIgnoreCase("true") == 0 ? true : false,
                        minValue, maxValue, minLength, maxLength));
            }
            i++;
        }
    }

    /**
     * @param xml
     */
    protected void initBeforeRequestModels(XMLFile xml) {
        int i = 0;
        int count = 0;

        NodeList mdNodes = xml.selectNodeList("/requests/modelsBeforeRequest/request");
        count = mdNodes.getLength();
        while (i < count) {
            Element d = (Element) mdNodes.item(i);
            String startsWith = d.getAttribute("startsWith");
            String endsWith = d.getAttribute("endsWith");
            String contains = d.getAttribute("contains");

            Element m = (Element) xml.selectSingleNode(d, "model");
            if (m != null) {
                String name = m.getAttribute("name");
                String on = m.getAttribute("on");
                String asyn = m.getAttribute("asyn");
                if (name.length() > 0) {
                    GroupModel gm = new GroupModel(name, on.length() > 0 ? on : null,
                            asyn.equalsIgnoreCase("true") ? true : false);
                    if (startsWith.length() > 0) {
                        gm.setStartsWith(startsWith);
                    }
                    if (endsWith.length() > 0) {
                        gm.setEndsWith(endsWith);
                    }
                    if (contains.length() > 0) {
                        gm.setContains(contains);
                    }
                    modelsBefore.add(gm);
                }
            }
            i++;
        }
    }

    /**
     * initialize the data filters for a request element;
     *
     * @param xml the requests definition files;
     * @param n   current request elment;
     */
    protected void initDataFilters(Node n, XMLFile xml, BLRequest req) {
        int count;
        Element e = (Element) n;
        NodeList nl = xml.selectNodeList(e, "filter/data");
        if (nl != null && (count = nl.getLength()) > 0) {
            for (int i = 0; i < count; i++) {
                String name = ((Element) nl.item(i)).getAttribute("name");
                if (name.length() > 0) {
                    DataFilter df = new DataFilter(name);
                    String field = ((Element) nl.item(i)).getAttribute("field");
                    String row = ((Element) nl.item(i)).getAttribute("row");
                    if (field.length() > 0) {
                        df.setField(field);
                    }
                    if (row.length() > 0) {
                        df.setField(row);
                    }
                    req.addDataFilter(df);
                }
            }
        }
    }

    /**
     * @param xml
     */
    protected void initAfterRequestModels(XMLFile xml) {
        int i = 0;
        int count = 0;

        NodeList mdNodes = xml.selectNodeList("/requests/modelsAfterRequest/request");
        count = mdNodes.getLength();
        while (i < count) {
            Element d = (Element) mdNodes.item(i);
            String startsWith = d.getAttribute("startsWith");
            String endsWith = d.getAttribute("endsWith");
            String contains = d.getAttribute("contains");

            Element m = (Element) xml.selectSingleNode(d, "model");
            if (m != null) {
                String name = m.getAttribute("name");
                String on = m.getAttribute("on");
                String asyn = m.getAttribute("asyn");
                if (name.length() > 0) {
                    GroupModel gm = new GroupModel(name, on.length() > 0 ? on : null,
                            asyn.equalsIgnoreCase("true") ? true : false);
                    if (startsWith.length() > 0) {
                        gm.setStartsWith(startsWith);
                    }
                    if (endsWith.length() > 0) {
                        gm.setEndsWith(endsWith);
                    }
                    if (contains.length() > 0) {
                        gm.setContains(contains);
                    }
                    modelsAfter.add(gm);
                }
            }
            i++;
        }
    }

    /**
     * @param xml
     */
    protected void initBeforeRequestDispatches(XMLFile xml) {
        int i = 0;
        int count = 0;

        NodeList mdNodes = xml.selectNodeList("/requests/beforeDispatchInclude/request");
        count = mdNodes.getLength();
        while (i < count) {
            Element d = (Element) mdNodes.item(i);
            String startsWith = d.getAttribute("startsWith");
            String endsWith = d.getAttribute("endsWith");
            String contains = d.getAttribute("contains");

            Element m = (Element) xml.selectSingleNode(d, "dispatch");
            if (m != null) {
                String url = m.getAttribute("url");
                String on = m.getAttribute("on");
                if (url.length() > 0) {
                    GroupDispatch gm = new GroupDispatch(url, on.length() > 0 ? on : null);
                    if (startsWith.length() > 0) {
                        gm.setStartsWith(startsWith);
                    }
                    if (endsWith.length() > 0) {
                        gm.setEndsWith(endsWith);
                    }
                    if (contains.length() > 0) {
                        gm.setContains(contains);
                    }
                    dispatchesBefore.add(gm);
                }
            }
            i++;
        }
    }


    protected void initAfterRequestDispatches(XMLFile xml) {
        int i = 0;
        int count = 0;

        NodeList mdNodes = xml.selectNodeList("/requests/afterDispatchInclude/request");
        count = mdNodes.getLength();
        while (i < count) {
            Element d = (Element) mdNodes.item(i);
            String startsWith = d.getAttribute("startsWith");
            String endsWith = d.getAttribute("endsWith");
            String contains = d.getAttribute("contains");

            Element m = (Element) xml.selectSingleNode(d, "dispatch");
            if (m != null) {
                String name = m.getAttribute("url");
                String on = m.getAttribute("on");
                if (name.length() > 0) {
                    GroupDispatch gm = new GroupDispatch(name, on.length() > 0 ? on : null);
                    if (startsWith.length() > 0) {
                        gm.setStartsWith(startsWith);
                    }
                    if (endsWith.length() > 0) {
                        gm.setEndsWith(endsWith);
                    }
                    if (contains.length() > 0) {
                        gm.setContains(contains);
                    }
                    dispatchesAfter.add(gm);
                }
            }
            i++;
        }
    }

    /**
     * add group models to matching requests;
     */
    protected void populateGroupModels() {

        int added;
        int count1 = this.modelsBefore.size();
        int count2 = this.modelsAfter.size();

        Iterator it = this.requests.keySet().iterator();
        while (it.hasNext()) {
            String request = (String) it.next();
            //populate before models for this request;
            added = 0;
            for (int i = 0; i < count1; i++) {
                GroupModel m = (GroupModel) this.modelsBefore.get(i);
                if (m.needRun(request)) {
                    Models ms = ((BLRequest) requests.get(request)).getModels();
                    ms.add(added++, m);
                }
            }

            //populate after models for this request;
            for (int i = 0; i < count2; i++) {
                GroupModel m = (GroupModel) this.modelsAfter.get(i);
                if (m.needRun(request)) {
                    Models ms = ((BLRequest) requests.get(request)).getModels();
                    ms.add(ms.getLength(), m);
                }
            }
        }
    }

    /**
     *
     */
    protected void populateGroupDispatches() {

        int added;
        int count1 = this.dispatchesBefore.size();
        int count2 = this.dispatchesAfter.size();

        Iterator it = this.requests.keySet().iterator();
        while (it.hasNext()) {
            String request = (String) it.next();
            //populate before models for this request;
            added = 0;
            for (int i = 0; i < count1; i++) {
                GroupDispatch m = (GroupDispatch) this.dispatchesBefore.get(i);
                if (m.needDispatch(request)) {
                    Dispatches ms = ((BLRequest) requests.get(request)).getDispatches();
                    ms.add(added++, m);
                }
            }

            //populate after models for this request;
            for (int i = 0; i < count2; i++) {
                GroupDispatch m = (GroupDispatch) this.dispatchesAfter.get(i);
                if (m.needDispatch(request)) {
                    Dispatches ms = ((BLRequest) requests.get(request)).getDispatches();
                    ms.add(ms.getLength(), m);
                }
            }
        }
    }

    /**
     * get all the request of the current application;
     * out:
     * DRMFunction/{funcNO,funcName,funsStatus}[i];
     *
     * @return the BizData object
     */
    public BizData getReqs() {
        int i = 0;
        BizData d = new BizData();
        Iterator it = requests.keySet().iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            BLRequest req = (BLRequest) requests.get(name);
            d.add("DRMFunction", "funcNO", i, name);
            d.add("DRMFunction", "funcName", i, req.getDesc());
            d.add("DRMFunction", "funcStatus", i, "0");
            i++;
        }
        return d;
    }
}