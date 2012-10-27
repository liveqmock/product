/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.socket;

import com.dream.bizsdk.common.blc.BLResult;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.databus.Util;
import com.dream.bizsdk.common.util.xml.XMLDocument;

import java.net.Socket;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-17
 * Time: 17:10:57
 */
public class SocketDataWriter {

    /**
     * write a BLRresult Object to a socket. The BLResult Object will be first
     * converted into a xml String, then be writed into the socket;
     *
     * @param s  the target socket object;
     * @param br the source BLResult object;
     * @throws IOException
     */
    public static void write(Socket s, BLResult br) throws IOException {
        byte[] bytes = SocketDataWriter.getXMLBytes(br);
        SocketDataWriter.write(s, bytes);
    }

    /**
     * write a byte array to socket;
     *
     * @param s
     * @param bytes
     * @throws IOException
     */
    public static void write(Socket s, byte[] bytes) throws IOException {
        OutputStream os = s.getOutputStream();

        int size = bytes.length;
        String str = Integer.toString(size);
        StringBuffer sb = new StringBuffer(str);
        size = sb.length();
        for (int i = size; i < 10; i++) {
            sb.append(' ');
        }
        os.write(sb.toString().getBytes());

        os.write(bytes);
    }

    /**
     * The converted xml looks like the following:
     * <bizsdk>
     * <retcode>0</retcode>
     * <reqdata><bizdata>......</bizdata></reqdata>
     * <sesdata><bizdata>......</bizdata></sesdata>
     * </bizsdk>
     *
     * @param br
     * @return
     */
    private static byte[] getXMLBytes(BLResult br) {
        int retCode = br.retCode;
        BizData rd = br.rd;
        BizData sd = br.sd;
        XMLDocument xml = new XMLDocument(XMLDocument.newDocument("bizsdk"));
        xml.appendChild("retcode", Integer.toString(retCode));
        xml.appendChild("reqdata");
        xml.appendChild("sesdata");
        XMLDocument.insertDocument(xml.getDocument(), "/bizsdk/reqdata", Util.toXML(rd));
        XMLDocument.insertDocument(xml.getDocument(), "/bizsdk/sesdata", Util.toXML(sd));
        String s = xml.toString();
        return s.getBytes();
    }
}