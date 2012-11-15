/**
 *all rights reserved,@copyright 2003
 */
package com.cots.blc;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-21
 * Time: 9:16:08
 * Version: 1.0
 */
public class BLCEntry {
    private String className;
    private String implClassName;
    private String description;
    private boolean threadSafe=true;
    private ArrayList blEntries = new ArrayList();

    public BLCEntry(Element ele){
        String attr = ele.getAttribute("class");
        if(attr!=null && attr.length()>0){
            this.className = attr;
        }
        attr = ele.getAttribute("desc");
        if(attr!=null && attr.length()>0){
            this.description = attr;
        }
        attr = ele.getAttribute("threadSafe");
        if("false".equalsIgnoreCase(attr)){
            this.threadSafe = false;
        }
        attr = ele.getAttribute("implClass");
        if(attr!=null && attr.length()>0){
            this.implClassName = attr;
        }else{
            this.implClassName = this.className;
        }

        //initialied bl entries of this blc entry;
        initBLEntries(ele);
    }

    public BLCEntry(String className,String desc,boolean threadSafe){
        this.className = className;
        this.description = desc;
        this.threadSafe = threadSafe;
    }

    /**
     * initialize all the bl entries in the registry file.
     *
     * @param ele
     */
    private void initBLEntries(Element ele){
        NodeList nl = ele.getElementsByTagName("bl");
        if(nl!=null){
            int count = nl.getLength();
            for(int i=0;i<count;i++){
                BLEntry bl = new BLEntry(this.className,(Element)nl.item(i));
                blEntries.add(bl);
            }
        }
    }

    /**
     *
     * @return the class displayName of this blc;
     */
    public String getClassName() {
        return className;
    }

    /**
     * 
     * @return
     */
    public String getImplClassName() {
        return implClassName;
    }

    /**
     * whether this BLC is thread safe.
     *
     * @return true if the BLC is thread safe, false otherwise.
     */
    public boolean isThreadSafe() {
        return threadSafe;
    }

    /**
     *
     * @return the description of this BLC;
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return all the bl entries contained in this BLC;
     */
    public BLEntry[] getBLEntries(){
        return (BLEntry[])blEntries.toArray(new BLEntry[blEntries.size()]);
    }

    /**
     * add a bl entries;
     * 
     * @param bl
     */
    public void addBLEntry(BLEntry bl){
        blEntries.add(bl);
    }

    /**
     * get all the bl methods signature;
     *
     * @return
     */
    public String[] getBLNames(){
        String[] names = new String[blEntries.size()];
        for(int i=0;i<names.length;i++){
            names[i] = ((BLEntry)blEntries.get(i)).getMethodName();
        }
        return names;
    }
}
