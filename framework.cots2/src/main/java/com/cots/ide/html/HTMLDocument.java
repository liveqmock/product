/**
 *all rights reserved,@copyright 2003
 */
package com.cots.ide.html;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.apache.xpath.XPathAPI;

import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-2-22
 * Time: 16:50:44
 * Version: 1.0
 */
public class HTMLDocument {
    private Document doc;

    private List forms = new ArrayList();

    private List anchors = new ArrayList();

    public HTMLDocument() {
    }


    public HTMLDocument(Document doc) throws HTMLParseException{
        this.doc = doc;
        parseForms();
        parseAnchors();
    }

    /**
     * parse all the forms in this HTMLDocument;
     * @throws HTMLParseException
     */
    private void parseForms() throws HTMLParseException{
        try {
            NodeList formsNodes = XPathAPI.selectNodeList(doc,"//FORM");
            int count = formsNodes.getLength();
            for(int i=0;i<count;i++){
                HTMLForm form = new HTMLForm();
                form.init((Element)formsNodes.item(i));
                forms.add(form);
            }
        } catch (TransformerException e) {
            throw new HTMLParseException(e);
        }
    }

    /**
     * parse all the anchors in this HTMLDocument
     * @throws HTMLParseException
     */
    private void parseAnchors()throws HTMLParseException{
        try {
            NodeList anchorsNodes = XPathAPI.selectNodeList(doc,"//FORM");
            int count = anchorsNodes.getLength();
            for(int i=0;i<count;i++){
                HTMLAnchor anchor = new HTMLAnchor();
                anchor.init((Element)anchorsNodes.item(i));
                anchors.add(anchor);
            }
        } catch (TransformerException e) {
            throw new HTMLParseException(e);
        }
    }

    /**
     * get all the forms in a html document;
     *
     * @return
     */
    public HTMLForm[] getForms(){
        return (HTMLForm[])forms.toArray(new HTMLForm[forms.size()]);
    }

    /**
     * get all the <A> elements in a html document;
     *
     * @return
     */
    public HTMLAnchor[] getAnchors(){
        return (HTMLAnchor[])anchors.toArray(new HTMLAnchor[anchors.size()]);
    }

    /**
     * get the associated document;
     * 
     * @return
     */
    public Document getDocument(){
        return doc;
    }
}
