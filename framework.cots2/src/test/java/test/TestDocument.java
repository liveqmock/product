/**
 *all rights reserved,@copyright 2003
 */
package test;

import junit.framework.TestCase;
import com.cots.util.XMLDocument;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-8-1
 * Time: 17:38:01
 * Version: 1.0
 */
public class TestDocument extends TestCase{
    public void test1() throws IOException, ParserConfigurationException, SAXException {
        Document doc = XMLDocument.fromString("<root><a><![CDATA[sssssss]]></a></root>");
        System.out.println(XMLDocument.getNodeValue(doc,"/root/a"));

    }

}
