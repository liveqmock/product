/**
 *all rights reserved,@copyright 2003
 */
package com.cots.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-16
 * Time: 18:28:30
 */
public class SocketDataParser {

    /**
     * parse the data from a socket and create a ServerRequest object
     * from these data.
     *
     * @param s the source socket object;
     * @return a new ServerRequest object;
     */
    public static ServerRequest parse(Socket s) throws IOException {
        return new ServerRequest();
    }

    /**
     * Get the request displayName from the xml document, the XPATH is
     * /bizsdk/reqname
     *
     * @param doc the source XMLDocument;
     * @return the request displayName;
     */
//    private String getReqName(XMLDocument doc) {
//        return doc.getSingleNodeValue("/bizsdk/reqname");
//    }

    /**
     * Get the request Data object from the xml document object:
     * First find the element by XPath: /bizsdk/reqdata/bizdata,
     * then convert this element to a Document object, and construct
     * a BizData object from this XMLDocument object;
     *
     * @param doc the source XMLDocument object;
     * @return the generated BizData object, may be null if the XMLDocument
     *         doest not contains such a serialized bizdata object;
     */
//    private BizData getReqData(XMLDocument doc) {
//        BizData bd = null;
//        Element e = (Element) doc.selectSingleNode("/bizsdk/reqdata/bizdata");
//        if (e != null) {
//            Document d = XMLDocument.fromNode(e);
//            bd = Util.fromXML(d);
//        }
//        return bd;
//    }

    /**
     * Get the session data object from a XML Document object;
     * First find the element by XPath:/bizsdk/sesdata/bizdata,
     * then convert this element to a Document object, and construct
     * a BizData object from this XMLDocument object;
     *
     * @param doc the source XML Document object;
     * @return the BizData object
     */
//    private BizData getSesData(XMLDocument doc) {
//        BizData bd = null;
//        Element e = (Element) doc.selectSingleNode("/bizsdk/sesdata/bizdata");
//        if (e != null) {
//            Document d = XMLDocument.fromNode(e);
//            bd = Util.fromXML(d);
//        }
//        return bd;
//    }

    /**
     * recieve data from socket and conver the data to a xml document
     * object.
     *
     * @param s
     * @return
     * @throws IOException
     */
//    private Document getDOMDocument(Socket s) throws IOException {
//        int bytesRead = 0;
//        int totalBytesRead = 0;
//        int size = 0;
//        byte[] buf = new byte[8196];
//        byte[] lenBuf = new byte[10];
//        ByteArrayOutputStream bo = new ByteArrayOutputStream(8196);
//
//        InputStream is = s.getInputStream();
//        bytesRead = is.read(lenBuf);
//        size = Integer.parseInt(new String(lenBuf, 0, bytesRead).trim());
//
//        while ((bytesRead = is.read(buf)) >= 0) {
//            bo.write(buf, 0, bytesRead);
//            totalBytesRead += bytesRead;
//            if (totalBytesRead >= size) {
//                break;
//            }
//        }
//        String str = new String(bo.toByteArray());
//        return XMLDocument.fromString(str);
//    }
}