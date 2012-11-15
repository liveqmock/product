/**
 *all rights reserved,@copyright 2003
 */
package com.cots.ide.html;

import org.cyberneko.html.parsers.DOMParser;
import org.xml.sax.SAXException;

import java.io.*;

import com.cots.util.XMLFile;

import javax.xml.transform.TransformerException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-2-22
 * Time: 16:50:23
 * Version: 1.0
 */
public final class HTMLParser {
    /**
     * parse a html file;
     *
     * @param path the path of the html file;
     * @return
     */
    public static HTMLDocument parseFile(String path) throws HTMLParseException{
        DOMParser parser = new DOMParser();
        try{
            parser.parse(path);
            return new HTMLDocument(parser.getDocument());
        }catch(SAXException e){
            throw new HTMLParseException(e);
        }catch(IOException e){
            throw new HTMLParseException(e);
        }
    }

    /**
     * parse a html File object;
     *
     * @param file html File object;
     * @return
     */
    public static HTMLDocument parseFile(File file){
        return null;
    }

    /**
     * parse a html String;
     *
     * @param content
     * @return
     */
    public static HTMLDocument parseString(String content){
        return null;
    }

    /**
     * parse a html byte stream;
     *
     * @param stream
     * @param charset
     * @return
     */
    public static HTMLDocument parseStream(InputStream stream,String charset){
        return null;
    }

    /**
     * parse a html character stream;
     *
     * @param reader
     * @return
     */
    public static HTMLDocument parseStream(Reader reader){
        return null;
    }

    public static void main(String[] argc) throws HTMLParseException, IOException, TransformerException {
        HTMLDocument doc = parseFile("D:\\tomcat5019\\webapps\\cots\\error.jsp");
        System.out.println(doc.toString());
        XMLFile.saveDoc2File(doc.getDocument(),"c:\\doc.xml");
//        System.out.println(doc.getForms().length);
//        HTMLForm form = doc.getForms()[0];
//
//        String[] paramNames = form.getParamNames();
//        System.out.println(doc.getForms()[0].getAction());
//        System.out.println(paramNames[0]);
    }
}