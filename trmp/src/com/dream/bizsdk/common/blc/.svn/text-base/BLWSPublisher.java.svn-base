/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.util.xml.XMLFile;

import javax.xml.parsers.ParserConfigurationException;
import java.util.HashSet;
import java.io.IOException;

import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-27
 * Time: 13:25:51
 */
public class BLWSPublisher {
    //hash set for the published BL method;
    private HashSet blPublished = new HashSet();
    private HashSet blcPublished = new HashSet();
    private boolean isAllPublished = false;


    public BLWSPublisher() {

    }

    public void init(String filePath) throws IOException, ParserConfigurationException, SAXException {
        int count;
        String blcName;

        XMLFile xml = new XMLFile(filePath);
        Element e = (Element) xml.selectSingleNode("/ws-bl/publish");
        if (e == null) {
            return;
        } else {
            String publishAll = e.getAttribute("includeAll");
            if (publishAll.compareToIgnoreCase("true") == 0) {
                isAllPublished = true;
            } else {
                NodeList nl = xml.selectNodeList("/ws-bl/publish/blc");
                count = nl.getLength();
                for (int i = 0; i < count; i++) {
                    Element blc = (Element) nl.item(i);
                    blcName = blc.getAttribute("name");
                    if (blc.getAttribute("includeAll").compareToIgnoreCase("true") == 0) {
                        blcPublished.add(blcName);
                    } else {
                        NodeList nl2 = xml.selectNodeList(blc, "bl");
                        int count2 = nl2.getLength();
                        for (int j = 0; j < count2; j++) {
                            Element bl = (Element) nl2.item(j);
                            String blName = bl.getAttribute("name");
                            if (blName.length() > 0) {
                                blPublished.add(blName);
                            }
                        }
                    }
                }
            }
        }
    }

    public void setAllPublished(boolean all) {
        isAllPublished = all;
    }

    public boolean isAllPublished() {
        return isAllPublished;
    }

    public void setBLCPublished(String blcName) {
        blcPublished.add(blcName);
    }

    public void removePublishedBLC(String blcName) {
        blcPublished.remove(blcName);
    }

    public boolean isBLCPublished(String blcName) {
        if (isAllPublished) {
            return true;
        } else {
            if (blcPublished.contains(blcName)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void addBL(String className, String methodName) {
        blPublished.add(className + "." + methodName);
    }

    public void removeBL(String className, String methodName) {
        blPublished.remove(className + "." + methodName);
    }

    public boolean isBLPublished(String className, String methodName) {
        if (isAllPublished) {
            return true;
        } else {
            if (blcPublished.contains(className)) {
                return true;
            } else {
                if (blPublished.contains(className + "." + methodName)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}