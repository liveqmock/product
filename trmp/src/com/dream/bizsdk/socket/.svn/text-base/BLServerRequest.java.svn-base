/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.socket;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.databus.Util;
import com.dream.bizsdk.common.util.xml.XMLDocument;
import com.dream.bizsdk.common.blc.BLResult;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-16
 * Time: 18:24:32
 */
public class BLServerRequest {
    protected String reqName;
    protected BizData reqData;
    protected BizData sesData;

    public BLServerRequest() {

    }

    public BLServerRequest(String reqName) {
        this.reqName = reqName;
    }

    public BLServerRequest(String reqName, BizData reqData) {
        this.reqName = reqName;
        this.reqData = reqData;
    }

    public BLServerRequest(String reqName, BizData reqData, BizData sesData) {
        this.reqName = reqName;
        this.reqData = reqData;
        this.sesData = sesData;
    }

    /**
     * get the name of the request;
     *
     * @return
     */
    public String getReqName() {
        return this.reqName;
    }

    /**
     * get the request BizData object associated with this request;
     *
     * @return a BizData object;
     */
    public BizData getReqData() {
        return this.reqData;
    }

    /**
     * get the session data object of this request;
     *
     * @return
     */
    public BizData getSesData() {
        return this.sesData;
    }

    /**
     * convert this object to string
     *
     * @return
     */
    public String toString() {
        XMLDocument xml = new XMLDocument(XMLDocument.newDocument("bizsdk"));
        xml.appendChild("reqname", reqName);
        xml.appendChild("reqdata");
        xml.appendChild("sesdata");
        XMLDocument.insertDocument(xml.getDocument(), "/bizsdk/reqdata", Util.toXML(reqData));
        XMLDocument.insertDocument(xml.getDocument(), "/bizsdk/sesdata", Util.toXML(sesData));
        return xml.toString();
    }

    /**
     * execute a request;
     *
     * @param s the socket that has connect to the remote server;
     * @return the result of this Request;
     * @throws IOException
     */
    public BLResult request(Socket s) throws IOException {
        int bytesRead = 0;
        int totalBytesRead = 0;
        byte[] buf = new byte[8196];
        byte[] lenBuf = new byte[10];
        ByteArrayOutputStream baos = new ByteArrayOutputStream(8196);

        String str = toString();
        byte[] bytes = str.getBytes();
        OutputStream os = s.getOutputStream();

        int size = bytes.length;
        str = Integer.toString(size);
        StringBuffer sb = new StringBuffer(str);
        size = sb.length();
        for (int i = size; i < 10; i++) {
            sb.append(' ');
        }
        os.write(sb.toString().getBytes());
        os.write(bytes);

        InputStream is = s.getInputStream();
        bytesRead = is.read(lenBuf);
        size = Integer.parseInt(new String(lenBuf, 0, bytesRead).trim());

        while ((bytesRead = is.read(buf)) >= 0) {
            baos.write(buf, 0, bytesRead);
            totalBytesRead += bytesRead;
            if (totalBytesRead >= size) {
                break;
            }
        }
        str = new String(baos.toByteArray());

        XMLDocument doc = new XMLDocument(XMLDocument.fromString(str));
        baos.close();
        int retCode = getRetCodeFromXML(doc);
        BizData reqData = getReqDataFromXML(doc);
        BizData sesData = getSesDataFromXML(doc);
        return new BLResult(retCode, reqData, sesData);
    }

    /**
     * get the return code from a XML document;
     *
     * @param doc
     * @return
     */
    private int getRetCodeFromXML(XMLDocument doc) {
        String value = doc.getSingleNodeValue("/bizsdk/retcode");
        if (value != null && value.length() > 0) {
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }

    /**
     * get the request data from a XML document;
     *
     * @param doc
     * @return
     */
    private BizData getReqDataFromXML(XMLDocument doc) {
        BizData bd = null;
        Element e = (Element) doc.selectSingleNode("/bizsdk/reqdata/bizdata");
        if (e != null) {
            Document d = XMLDocument.fromNode(e);
            bd = Util.fromXML(d);
        }
        return bd;

    }

    /**
     * get the session Data from a xml document;
     *
     * @param doc
     * @return
     */
    private BizData getSesDataFromXML(XMLDocument doc) {
        BizData bd = null;
        Element e = (Element) doc.selectSingleNode("/bizsdk/reqdata/bizdata");
        if (e != null) {
            Document d = XMLDocument.fromNode(e);
            bd = Util.fromXML(d);
        }
        return bd;
    }
}
