/*****************************************************************************
 * This class wraps operations on a XML Document Object.
 * @version 	0.1
 * @author 	chuguanghua
 *
 ****************************************************************************/

package com.dream.bizsdk.common.util.xml;

//System Package Imports;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import java.io.*;

public class XMLDocument {

    public static boolean validating;

    //the DOM Domdocument object
    protected Document doc = null;

    //current node in the DOM Document object doc;
    protected Element currentNode = null;

    /**
     * contruct an instance Without a DOM Document object,
     * so other operations is not valid until a new DOM Document object
     * is set.
     */
    public XMLDocument() {
    }

    /**
     * contruct an instance With a DOM Document object,and the Document element
     * will be set to be the current Node.
     *
     * @param doc the new Document object.
     */
    public XMLDocument(Document doc) {
        this.doc = doc;
        currentNode = doc.getDocumentElement();
    }

    /**
     * check if the DOM Document is available.
     *
     * @return true if a DOM Document is valid,false otherwise.
     */
    public boolean isDocValid() {
        if (doc == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get the current DOM Document object. if there is no valid Document
     * Object, null is returned.
     *
     * @return a Document object or null value;
     */
    public Document getDocument() {
        return doc;
    }

    /**
     * set a new DOM Document object, and set the Documnet's Document
     * element to be the currentNode.
     *
     * @param doc the new Document object;
     */
    public void setDocument(Document doc) {
        this.doc = doc;
        this.currentNode = doc.getDocumentElement();
    }

    /**
     * set the current node in the Document object. Other operations, such as
     * getChildNodes and so on , wiill be based on the current node. The node
     * should be within the current DOMDocument object.
     *
     * @param n Node the node to be set current.
     */
    public void setCurrentNode(Element n) {
        currentNode = n;
    }

    /**
     * Get the current node.
     *
     * @return the current Node object;
     */
    public Element getCurrentNode() {
        return currentNode;
    }

    /**
     * append a named element with a value to be the child of the current node.
     * the result dom should look like the following:
     * <currentNode><name>value</name></currentNode>
     *
     * @param name  the name of the child element;
     * @param value the value of the child element;
     * @return the newly appened element;
     */
    public Element appendChild(String name, String value) {
        if (name == null || value == null) {
            return null;
        } else {
            Element e = doc.createElement(name);
            Node n = doc.createTextNode(value);
            e.appendChild(n);
            currentNode.appendChild(e);
            return e;
        }
    }

    /**
     * append a new child element to the current element and
     * set the new element to be the current node.
     * the result dom should look like the following:
     * <currentNode><name>value</name></currentNode>
     *
     * @param name  the name of the element;
     * @param value the value of the element;
     * @return the newly created child node;
     */
    public Element appendChildCN(String name, String value) {
        Element e = appendChild(name, value);
        if (e != null) {
            setCurrentNode(e);
        }
        return e;
    }


    /**
     * append a named element without a value to the current node.This will not
     * change the current node;
     * The result DOM should look like this:
     * <currentNode><name/></currentNode>
     *
     * @param name the name of the child element;
     * @return the newly appened node;
     */
    public Element appendChild(String name) {
        if (name == null) {
            return null;
        } else {
            Element e = doc.createElement(name);
            currentNode.appendChild(e);
            return e;
        }
    }

    /**
     * append an elemnet to the current node and set the new element to
     * be the current Node.
     * The result DOM should look like this:
     * <currentNode><name/></currentNode>
     *
     * @param name the name of the element to be appended;
     * @return the newly created element;
     */
    public Element appendChildCN(String name) {
        Element e = appendChild(name);
        if (e != null) {
            setCurrentNode(e);
        }
        return e;
    }

    /**
     * append a named element with a value to a specified Element;This
     * will not change the current node;
     * The result DOM will look like following:
     * <parent><name>value</name></parent>
     *
     * @param parent the element to append the child node to;
     * @param name   the name of the child node to be appended;
     * @param value  the value of the child node;
     * @return the newly appended Element;
     */
    public Element appendChild(Element parent, String name, String value) {
        /**check the parameter*/
        if (parent == null || name == null || value == null) {
            return null;
        } else {
            Element e = doc.createElement(name);
            Node n = doc.createTextNode(value);
            e.appendChild(n);
            parent.appendChild(e);
            return e;
        }
    }

    /**
     * append a named element WITHOUT a value to a specified Element;This
     * will not change the current node;
     * The result dom will look like following:
     * <parent><name/></parent>
     *
     * @param parent the element to append the child node to;
     * @param name   the name of the child node to be appended;
     * @return the newly appended Element;
     */
    public Element appendChild(Element parent, String name) {
        /**check the parameter*/
        if (parent == null || name == null) {
            return null;
        } else {
            Element e = doc.createElement(name);
            parent.appendChild(e);
            return e;
        }
    }

    /**
     * Select a NodeList by a XPath, note that this methods use apache's XPath API,
     * but this API is not part of java2 API though it is shipper with jdk1.4.
     *
     * @param xpath an absolute XPATH;
     * @return the result node list;
     */
    public NodeList selectNodeList(String xpath) {
        NodeList nodelist = null;
        try {
            nodelist = org.apache.xpath.XPathAPI.selectNodeList(doc, xpath);
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return nodelist;
    }

    /**
     * Select a NodeList by a XPath under a specified Node, note that this methods
     * use apache's XPath API,but this API is not part of java2 API though it is
     * shipper with jdk1.4.
     *
     * @param node  the Node to start select;
     * @param xpath the relative xpath to the above node;
     * @return the result Node list;
     */
    public NodeList selectNodeList(Node node, String xpath) {
        NodeList nodelist = null;
        try {
            nodelist = org.apache.xpath.XPathAPI.selectNodeList(node, xpath);
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return nodelist;
    }

    /**
     * Select a single(the first) Node using a XPath,
     *
     * @param xpath an absolute XPATH;
     * @return the node selected, null if no such nodes.
     */
    public Node selectSingleNode(String xpath) {
        Node n = null;
        NodeList nl = selectNodeList(xpath);
        if (nl != null && nl.getLength() > 0) {
            n = nl.item(0);
        }
        return n;
    }

    /**
     * Select a single(the first) node under a specified Node using
     * a relative xpath.
     *
     * @param node  the Node to start select;
     * @param xpath the relative xpath to the above node;
     * @return the Node selected, null if no such nodes;
     */
    public Node selectSingleNode(Node node, String xpath) {
        Node n = null;
        NodeList nl = selectNodeList(node, xpath);
        if (nl != null && nl.getLength() > 0) {
            n = nl.item(0);
        }
        return n;
    }

    /**
     * Get the value of a node. If the node has only one child node and the
     * child node is of text_NODE type. then the value of the text node is
     * returned; otherwise null is returned;
     *
     * @param xpath an absolute path;
     * @return
     */
    public String getSingleNodeValue(String xpath) {
        String value = null;
        Node n = selectSingleNode(xpath);

        if (n != null) {
            short type = n.getNodeType();

            if (type == Node.ATTRIBUTE_NODE) {  //if an attribute node is selected;
                value = n.getNodeValue();
            } else if (type == Node.ELEMENT_NODE) { //if an element node is selected;
                NodeList nl = n.getChildNodes();
                int len = nl.getLength();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < len; i++) {
                    Node c = nl.item(i);
                    if (c.getNodeType() == Node.TEXT_NODE) {
                        sb.append(c.getNodeValue());
                    }
                }
                value = new String(sb);
            }
        }
        return value;
    }

    public String getChildElementValue(Element e, String child) {
        String value = null;
        NodeList nl = e.getElementsByTagName(child);
        if (nl != null && nl.getLength() > 0) {
            Element c = (Element) nl.item(0);
            value = getElementValue(c);
        }
        return value;
    }

    public String getElementValue(Element e) {
        Node n = e.getFirstChild();
        if (n != null) {
            return n.getNodeValue();
        } else {
            return null;
        }
    }

    public Element getChildElement(Element e, String child) {
        Element c = null;
        NodeList nl = e.getElementsByTagName(child);
        if (nl != null && nl.getLength() > 0) {
            c = (Element) nl.item(0);

        }
        return c;
    }

    public String getChildElementAttr(Element e, String child, String attr) {
        String value = "";
        Element c = null;
        NodeList nl = e.getElementsByTagName(child);
        if (nl != null && nl.getLength() > 0) {
            c = (Element) nl.item(0);

        }
        if (c != null) {
            value = c.getAttribute(attr);
        }
        return value;
    }

    /**
     * Get the value of a node specified by a xpath. If the node has only
     * one child node and the child node is of text_NODE type,then the value
     * of the text node is returned; otherwise null is returned;
     *
     * @param node  the node to start select;
     * @param xpath the relative xpath to the above node;
     * @return the value of the selected node,null if no this node or this node
     *         has no value.
     */
    public String getSingleNodeValue(Node node, String xpath) {
        String value = null;
        //Node temp=null;
        Node n = selectSingleNode(node, xpath);

        if (n != null) {    //check if a node is selected;
            short type = n.getNodeType();

            if (type == Node.ATTRIBUTE_NODE) {  //if an attribute node is selected;
                value = n.getNodeValue();
            } else if (type == Node.ELEMENT_NODE) { //if an element node is selected;
                NodeList nl = n.getChildNodes();
                int len = nl.getLength();
                if (len == 1 && nl.item(0).getNodeType() == Node.TEXT_NODE) {
                    value = nl.item(0).getNodeValue();
                }
            }
        }
        return value;
    }

    /**
     * set a string value to a node, if the node is an attribute node,
     * then the value of the attribute will be set. If the node is an element,
     * then all child nodes of the element will be removed, and a text node
     * with the new value will be created and appended to the node.
     *
     * @param xpath the node of
     * @param vlaue
     */
    public void setNodeValue(String xpath, String vlaue) {
        String value = null;
        //Node temp=null;
        Node n = selectSingleNode(xpath);

        if (n != null) {    //check if a node is selected;
            short type = n.getNodeType();

            if (type == Node.ATTRIBUTE_NODE) {  //if an attribute node is selected;
                n.setNodeValue(value);
            } else if (type == Node.ELEMENT_NODE) { //if an element node is selected;
                NodeList nl = n.getChildNodes();
                if (nl != null) {
                    int len = nl.getLength();
                    for (int i = 0; i < len; i++) {
                        n.removeChild(nl.item(i));
                    }
                    Text textNode = n.getOwnerDocument().createTextNode(value);
                    n.appendChild(textNode);
                }
            }
        }
    }

    public String toString() {
        try {
            return toString(doc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * create a new document with the specified root name,this document object
     * will look as following:<root/>
     *
     * @param root the root name of the Document;
     */
    public static Document newDocument(String root) {
        Document newDoc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            newDoc = db.newDocument();
            Element e = newDoc.createElement(root);
            newDoc.appendChild(e);
        } catch (ParserConfigurationException e1) {
        }
        return newDoc;
    }

    /**
     * Create a new instance of XMLDocument object, this will first create
     * a DOM document object and then wrap it with a XMLDocument object, and
     * then return the XMLDocument object.
     * The contained DOMDocument object will look like following:
     * <root/>
     *
     * @param root the name of the document element;
     * @return a new XMLDocument object.
     */
    public static XMLDocument newXMLDocument(String root) {
        XMLDocument xmlDoc = null;
        Document newDoc = XMLDocument.newDocument(root);
        //if the Document ojbect is created,then create the XMLDocument object;
        if (newDoc != null) {
            xmlDoc = new XMLDocument(newDoc);
        }
        return xmlDoc;
    }

    /**
     * create an empty Document object;
     *
     * @return
     */
    public static Document newDocument() {
        Document newDoc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            newDoc = db.newDocument();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        }
        return newDoc;
    }

    public static String toString(Document doc)
            throws TransformerConfigurationException, TransformerException {
        String xml = null;
        if (doc != null) {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource ds = new DOMSource(doc);
            CharArrayWriter fw = new CharArrayWriter();
            StreamResult ss = new StreamResult(fw);
            t.transform(ds, ss);
            xml = fw.toString();
            fw.close();
        }
        return xml;
    }

    public static Document fromString(String xml) {
        Document doc = newDocument();
        if (xml != null) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
                doc = db.parse(bais);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return doc;
    }

    public static Document fromXPath(Document doc, String xpath) {
        XMLDocument xml = new XMLDocument(doc);
        Element r = (Element) xml.selectSingleNode(xpath);
        return fromNode(r);
    }

    public static Document fromNode(Element e) {
        Document doc = newDocument();
        Element n = (Element) doc.importNode(e, true);
        doc.appendChild(n);
        return doc;
    }

    public static void insertDocument(Document doc, Element p, Document child) {
        Element n = (Element) doc.importNode(child.getDocumentElement(), true);
        p.appendChild(n);
    }

    public static void insertDocument(Document doc, String xpath, Document child) {
        XMLDocument xml = new XMLDocument(doc);
        Element p = (Element) xml.selectSingleNode(xpath);
        insertDocument(doc, p, child);
    }

    public void removeNodes(String xpath) {
        int count;
        NodeList nl = selectNodeList(xpath);
        if (nl == null || (count = nl.getLength()) < 1) {
            return;
        }
        for (int i = 0; i < count; i++) {
            Node c = nl.item(i);
            Node p = c.getParentNode();
            p.removeChild(c);
        }
    }
}