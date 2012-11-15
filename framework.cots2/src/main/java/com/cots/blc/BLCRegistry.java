/**
 *all rights reserved,@copyright 2003
 */
package com.cots.blc;

import com.cots.util.Cache;
import com.cots.util.XMLFile;
import com.cots.util.RefreshFailedException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.HashMap;
import java.io.IOException;

import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

/**
 * Description:
 *  This class maintains the BLC registry.
 *
 * User: chuguanghua
 * Date: 2005-4-21
 * Time: 9:08:10
 * Version: 1.0
 */
public class BLCRegistry implements Cache{
    private String configFile;
    private XMLFile registry;

    private HashMap blcs = new HashMap();

    public BLCRegistry(String configFile){
        init(configFile);
    }

    public boolean init(String configFile) {
        this.configFile = configFile;
        try{
            return refresh();
        }catch(RefreshFailedException e){
            e.getCause().printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return true if finished
     */
    public boolean refresh() throws RefreshFailedException{
        try {
            blcs.clear();
            registry = new XMLFile(configFile);
            NodeList blcNodes = registry.selectNodeList("/blcs/blc");
            if(blcNodes !=null ){
                int count = blcNodes.getLength();
                for(int i=0;i<count;i++){
                    BLCEntry blc = new BLCEntry((Element)blcNodes.item(i));
                    blcs.put(blc.getClassName(),blc);
                }
            }
            return true;
        } catch (ParserConfigurationException e) {
            throw new RefreshFailedException(e);
        } catch (SAXException e) {
            throw new RefreshFailedException(e);
        } catch (IOException e) {
            throw new RefreshFailedException(e);
        }
    }

    public BLCEntry getEntry(String blcClassName){
        return (BLCEntry)blcs.get(blcClassName);
    }

    /**
     * get all the blc registry.
     * 
     * @return
     */
    public BLCEntry[] getEntries(){
        return (BLCEntry[])blcs.values().toArray(new BLCEntry[blcs.size()]);
    }

    /**
     * check whether a class is thread safe or not.
     *
     * @param className the displayName of the target class or interface.
     * @return true if target class or interface is thread safe, false otherwise.
     */
    public boolean isThreadSafeBLC(String className){
        boolean threadSafe = true;
        Object o = blcs.get(className);
        if(o!=null){
            threadSafe = ((BLCEntry)o).isThreadSafe();
        }
        return threadSafe;
    }

    /**
     * add an BLC entry to this registry;
     *
     * @param blc
     * @throws IOException
     * @throws TransformerException
     */
    public synchronized void addEntry(BLCEntry blc) throws IOException, TransformerException {
        String className = blc.getClassName();
        String desc = blc.getDescription();
        boolean threadSafe = blc.isThreadSafe();
        NodeList list = registry.selectNodeList("/blcs/blc[@class=\""+className+"\"]");
        if(list!=null && list.getLength()>0){
            for(int i=list.getLength()-1;i>=0;i--){
                list.item(i).getParentNode().removeChild(list.item(i));
            }
        }
        Element ele = registry.appendChild(registry.getDocument().getDocumentElement(),"blc");
        ele.setAttribute("class",className);
        if(desc!=null){
            ele.setAttribute("desc",desc);
        }
        if(!threadSafe){
            ele.setAttribute("threadSafe",desc);
        }

        //svae the bl entries of the BLC object;
        BLEntry[] bl = blc.getBLEntries();
        for(int i=0;i<bl.length;i++){
            String methodName = bl[i].getMethodName();
            desc = bl[i].getDescription();
            Element blEle = registry.appendChild(ele,"bl");
            blEle.setAttribute("method",methodName);
            if(desc!=null && desc.length()>0){
                blEle.setAttribute("desc",desc);
            }
        }

        registry.save();
        blcs.put(blc.getClassName(),blc);
    }

    /**
     *
     * @param name
     * @return
     */
    public String getImplClass(String name){
        BLCEntry blc = (BLCEntry)blcs.get(name);
        if(blc == null){
            return name;
        }else{
            return blc.getImplClassName();
        }
    }
}
