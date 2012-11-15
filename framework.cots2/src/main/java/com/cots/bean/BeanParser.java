/**
 *all rights reserved,@copyright 2003
 */
package com.cots.bean;

import com.cots.util.XMLDocument;
import com.cots.util.ClassUtil;


import java.util.HashSet;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-15
 * Time: 10:14:48
 * Version: 1.0
 */
public class BeanParser {
    /**
     * parse a Bean Class to a XMLDocument;
     *
     * @param beanClz the Class of a Bean;
     * @return
     */
    public static Document parseClassToDoc(Class beanClz){
        String clsName;

        String qname = beanClz.getName();
        int index = qname.lastIndexOf('.');
        if(index>0){
            clsName = qname.substring(index+1);
        }else{
            clsName = qname;
        }

        String tableName = null;
        Object obj = ClassUtil.getStaticField(beanClz, "TABLE");
        if(obj instanceof String){
            tableName = (String)obj;
        }

        /**
         * get the key columns;
         */
        HashSet keys = new HashSet();
        obj = ClassUtil.getStaticField(beanClz, "KEYS");
        if(obj instanceof String){
            StringTokenizer st = new StringTokenizer((String)obj,",");
            while(st.hasMoreElements()){
                keys.add(st.nextToken());
            }
        }

        XMLDocument doc = XMLDocument.newXMLDocument("bean");
        //check if the document is creatd
        if(doc==null){
            return null;
        }
        Element root = doc.getDocument().getDocumentElement();
        root.setAttribute("name",clsName);
        root.setAttribute("class",qname);
        if(tableName!=null && tableName.length() > 0){
            root.setAttribute("table",tableName);
        }
        BeanProperty[] bps = BeanUtil.getProperties(beanClz);
        int count = bps.length;
        for(int i=0;i<count;i++){
            String name = bps[i].getName();
            String type = bps[i].getType();
            Element p = doc.appendChild(root,"property");
            p.setAttribute("name",name);
            p.setAttribute("type",type);
            if(keys.contains(name)){
                p.setAttribute("key","true");
            }
        }
        return doc.getDocument();
    }

    /**
     * parse a class to a Bean Object;
     *
     * @param clz the source Class object;
     * @return a Bean Object based on the Class;
     */
    public static Bean parseClassToBean(Class clz){
        String clsName;

        /*get the class name*/
        String qname = clz.getName();
        int index = qname.lastIndexOf('.');
        if(index>0){
            clsName = qname.substring(index+1);
        }else{
            clsName = qname;
        }

        String tableName = clsName;
        Object obj = ClassUtil.getStaticField(clz, "TABLE");
        if(obj instanceof String){
            tableName = (String)obj;
        }

        boolean saveParent = false;
        obj = ClassUtil.getStaticField(clz, "INCLUDE_PARENT");
        if("true".equalsIgnoreCase((String)obj)){
            saveParent = true;
        }

        /**
         * get the key columns;
         */
        HashSet keys = new HashSet();
        obj = ClassUtil.getStaticField(clz, "KEYS");
        if(obj instanceof String){
            StringTokenizer st = new StringTokenizer((String)obj,",");
            while(st.hasMoreElements()){
                keys.add(st.nextToken());
            }
        }

        Bean bean = null;
        try{
            bean= new Bean(clsName,qname,null);
            bean.setTableName(tableName);
            bean.setSaveParent(saveParent);
        }catch(ClassNotFoundException e){
            //this exception should not be thrown because the Class should alawys exists;
            e.printStackTrace();
        }

        BeanProperty[] bps = BeanUtil.getProperties(clz);
        int count = bps.length;
        try{
            for(int i=0;i<count;i++){
                if(keys.contains(bps[i].getName())){
                    bps[i].setKey(true);
                }
                bean.addProperty(bps[i]);
            }
        }catch(NoSuchPropertyException e){
            //this exception should not be thrown because the fiels should alawys exists;
            e.printStackTrace();
        }
        return bean;
    }
}