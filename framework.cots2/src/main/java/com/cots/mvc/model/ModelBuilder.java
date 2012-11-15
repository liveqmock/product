package com.cots.mvc.model;

import org.w3c.dom.*;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.cots.util.XMLDocument;

/**
 *
 * User: chugh
 * Date: 2004-10-14
 * Time: 19:58:16
 */
public final class ModelBuilder {
    private HashMap modelClasses = new HashMap();

    private Logger log = Logger.getLogger(ModelBuilder.class);

    /**
     * default constructor;
     */
    public ModelBuilder(){

    }

    /**
     * initialize this Builder according to a definition file(XML File);
     *
     * @param configFile the path of the config file;
     */
    public boolean init(String configFile) throws FileNotFoundException{
        return init(new FileInputStream(new File(configFile)));
    }

    /**
     * initialize this builder according to a InputStream;
     *
     * @param is an InputStream that contains the model type definition;
     */
    public boolean init(InputStream is){
        int count;
        int countAttrs;

        Document doc = null;
        try{
            doc = XMLDocument.fromInputStream(is);
        }catch(Exception e){
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("failed to parse model type mapping file into a DOMDocument",e);
            }
            return false;
        }
        XMLDocument xml = new XMLDocument(doc);
        NodeList nl = xml.selectNodeList("/model-types/type");
        if(nl!=null && (count=nl.getLength()) > 0){
            for(int i=0;i<count;i++){
                Element type = (Element)nl.item(i);
                String name = type.getAttribute("name");
                String className = type.getAttribute("class");

                ModelType mt = new ModelType(name,className);
                NodeList attrs = xml.selectNodeList(type,"attribute");
                if(attrs!=null && (countAttrs=attrs.getLength())>0){
                    for(int j=0;j<countAttrs;j++){
                        Element attrNode = (Element)attrs.item(j);
                        String attrName = attrNode.getAttribute("name");
                        mt.addAttribute(attrName);
                    }
                }
                modelClasses.put(name,mt);
            }
        }
        return true;
    }

    /**
     * buildBoolean a new Model according to a DOM Element, the displayName of the element
     * specifys the type of the Model, the properties of the Element contains
     * the properties to create the new Model;
     *
     * @param modelNode the Element that contains the properties for the
     * new model.
     * @return
     */
    public Model buildModel(Element modelNode){
        int countAttrs;

        String typeName = modelNode.getNodeName();
        NamedNodeMap attrs = modelNode.getAttributes();
        countAttrs = attrs.getLength();
        Map map = new HashMap();
        for(int i=0;i<countAttrs;i++){
            Node a = attrs.item(i);
            String nodeName = a.getNodeName();
            String nodeValue = a.getNodeValue();
            map.put(nodeName,nodeValue);
        }

        return buildModel(typeName,map);
    }

    /**
     *
     * @param typeName
     * @param attrValues
     * @return
     */
    public Model buildModel(String typeName,Map attrValues){
        int countAttrs;
        Object model;
        String attrName=null;

        ModelType mt = (ModelType)modelClasses.get(typeName);
        String className = mt.getClassName();
        try{
            Class clazz = Class.forName(className);
            model = clazz.newInstance();
            if(!(model instanceof Model)){
                throw new ClassNotFoundException("the class "+className+" is not a subclass of Model ");
            }
        }catch(Exception e){
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't load or create model:"+className,e);
            }
            return null;
        }

        try{
            List attrs = mt.getAttrs();
            countAttrs = attrs.size();
            for(int i=0;i<countAttrs;i++){
//                attrName = (String)attrs.get(i);
//                BeanUtil.setProperty(model,attrName,(String)attrValues.get(attrName));
            }
        }catch(Exception e){
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("can't set property "+ attrName+" to model"+className,e);
            }
            return null;
        }
        return (Model)model;
    }

    /**
     *
     *
     */
    protected static class ModelType{
        private List attrs = new ArrayList();
        private String type;
        private String className;

        public ModelType(String type,String className){
            this.type = type;
            this.className = className;
        }

        public void addAttribute(String attr){
            attrs.add(attr);
        }

        public List getAttrs(){
            return this.attrs;
        }

        public String getType(){
            return this.type;
        }

        public String getClassName(){
            return this.className;
        }
    }
}
