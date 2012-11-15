/**
 *
 *
 */
package com.cots.util;

//System Package Imports;
//import COM.DREAM.BIZSDK.UTIL.XML.XMLDocument;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

/**
 * This class represents a XML File.
 * version 	0.1
 * author 	chuguanghua
 * date	        2003-2-10
 */

public class XMLFile extends XMLDocument {
    protected String filePath = null;

    public XMLFile() {
    }

    public XMLFile(String filePath) throws FactoryConfigurationError
            , ParserConfigurationException, SAXException, IOException {
        setFile(filePath);
    }

    public XMLFile(File f) throws FactoryConfigurationError
            , ParserConfigurationException, SAXException, IOException {
        setFile(f);
    }

    /**
     * Set the path of this xml file object. This will cause the previous
     * DOM Document object be released. And a new DOM Document Object will
     * be created on the new xml formated file.
     */
    public void setFile(String filePath) throws FactoryConfigurationError
            , ParserConfigurationException, SAXException, IOException {
        this.doc = null;
        this.doc = XMLFile.parse(filePath);
        this.currentNode = doc.getDocumentElement();
        this.filePath = filePath;
    }

    public void setFile(File f) throws FactoryConfigurationError
            , ParserConfigurationException, SAXException, IOException {
        this.doc = null;
        this.doc = XMLFile.parse(f);
        this.currentNode = doc.getDocumentElement();
        this.filePath = f.getAbsolutePath();
    }

    /**
     * Get the file path.
     *
     * @return a String object that is the path of the xml file.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * get the displayName of the file;
     *
     * @return the xml file's displayName;
     */
    public String getName(){
        return FileUtil.getFileName(this.filePath);
    }

    protected void setFilePath(String path) {
        this.filePath = path;
    }

    /**
     * close this xml file.
     */
    public void close()
            throws TransformerConfigurationException, TransformerException, IOException {
        saveAs(filePath);
    }

    public void save()
            throws TransformerConfigurationException, TransformerException, IOException {
        saveAs(filePath);
    }

    /**
     * save this xml file to another dir.
     */
    public void saveAs(String path)
            throws TransformerConfigurationException, TransformerException, IOException {
        Document doc = null;
        if ((doc = getDocument()) == null) {
            return;
        } else {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource ds = new DOMSource(doc);

            t.setOutputProperty(OutputKeys.ENCODING, "GB2312");
            t.setOutputProperty(OutputKeys.METHOD, "xml");

            FileWriter fw = new FileWriter(new File(path));
            StreamResult ss = new StreamResult(fw);
            t.transform(ds, ss);
            fw.close();
        }
    }


    /**
     * @param path
     * @param xmlRoot
     * @return
     * @throws TransformerConfigurationException
     *
     * @throws TransformerException
     * @throws IOException
     */
    public static XMLFile newXMLFile(String path, String xmlRoot)
            throws TransformerConfigurationException, TransformerException, IOException {
        XMLFile f = new XMLFile();
        f.setFilePath(path);
        Document doc = XMLFile.newDocument(xmlRoot);
        f.setDocument(doc);
        XMLFile.saveDoc2File(doc, path);
        return f;
    }

    /**
     * @param path
     * @param xmlRoot
     * @return
     * @throws TransformerConfigurationException
     *
     * @throws TransformerException
     * @throws IOException
     */
    public static XMLFile newXMLFile(String path, String xmlRoot, String encoding)
            throws TransformerConfigurationException, TransformerException, IOException {
        XMLFile f = new XMLFile();
        f.setFilePath(path);
        Document doc = XMLFile.newDocument(xmlRoot);
        f.setDocument(doc);
        XMLFile.saveDoc2File(doc, path, encoding);
        return f;
    }

    /**
     * open the xml file and parse the file, then return a DOMDocument object.
     *
     * @param filePath String object that is the path of the xml formated file.
     * @return a DOM Document object on the xml file.
     */
    public static Document parse(String filePath) throws FactoryConfigurationError
            , ParserConfigurationException, SAXException, IOException {
        File f = new File(filePath);
        return XMLFile.parse(f);
    }

    /**
     * parse a File object, then return a DOMDocument object.
     *
     * @param f a File object that is a xml formated file.
     * @return a DOM Document object on the xml file.
     */
    public static Document parse(File f) throws FactoryConfigurationError
            , ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(f);
        return doc;
    }


    /**
     * parse a string to a DOMDocument object;
     *
     * @param str
     * @return
     * @throws FactoryConfigurationError
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Document parseString(String str) throws FactoryConfigurationError
            , ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
        Document doc = db.parse(bais);
        return doc;
    }

    /**
     * save a Document object to a file.
     *
     * @param doc  the Document object to be saved;
     * @param path the path of the file to save the Document;
     * @throws TransformerConfigurationException
     *
     * @throws TransformerException
     * @throws IOException
     */
    public static void saveDoc2File(Document doc, String path)
            throws TransformerConfigurationException, TransformerException, IOException {
        if (doc == null) {
            return;
        } else {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();

            t.setOutputProperty(OutputKeys.ENCODING, "GB2312");
            t.setOutputProperty(OutputKeys.METHOD, "xml");

            DOMSource ds = new DOMSource(doc);
            FileWriter fw = new FileWriter(new File(path));
            StreamResult ss = new StreamResult(fw);
            t.transform(ds, ss);
            fw.close();
        }
    }

    /**
     * save a Document object to a file using a specified encoding;
     *
     * @param doc      the Document object to be saved;
     * @param path     the file path;
     * @param encoding the encoding used to save this Document.
     * @throws TransformerConfigurationException
     *
     * @throws TransformerException
     * @throws IOException
     */
    public static void saveDoc2File(Document doc, String path, String encoding)
            throws TransformerConfigurationException, TransformerException, IOException {
        if (doc == null) {
            return;
        } else {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.ENCODING, encoding);
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            DOMSource ds = new DOMSource(doc);
            FileWriter fw = new FileWriter(new File(path));
            StreamResult ss = new StreamResult(fw);
            t.transform(ds, ss);
            fw.close();
        }
    }
}