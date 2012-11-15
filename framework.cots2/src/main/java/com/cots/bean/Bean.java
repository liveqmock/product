package com.cots.bean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Enumeration;
import java.util.HashSet;
import java.text.ParseException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Array;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * Description:
 *
 * User: chugh
 * Date: 2004-10-15
 * Time: 23:06:10
 * Version: 1.0
 */
public class Bean {
    //name of the bean;
    protected String name;

    //the corresponding table name;
    protected String tableName;

    //sql based bean object;
    protected String sql;

    //the class name of the bean;
    protected String className;

    //the Class object of the bean;
    protected Class beanClass;

    //if the bean has BLob fields;
    protected boolean hasBLOBCols;

    //if the bean has CLob fields;
    protected boolean hasCLOBCols;

    //the names of the key fields;
    protected List keyCols = new ArrayList();

    //the names of none-key fields;
    protected List generalCols = new ArrayList();

    //list of the fields that is writable;
    protected List setableCols = new ArrayList();

    //list of the fields that need to be persistented;
    protected List persistentCols = new ArrayList();

    //list of all the fields
    protected List properties = new ArrayList();

    protected boolean saveParent;

    private Logger log = Logger.getLogger(Bean.class);

    private BeanFactory factory;

    public Bean(String name,String className,BeanFactory bf) throws ClassNotFoundException{
        this.name = name;
        this.className = className;
        this.beanClass = Class.forName(className);
        this.factory = bf;

        //set the default value to the tableName;
        int index = className.lastIndexOf('.');
        if(index>=0){
            this.tableName = className.substring(index+1);
        }else{
            this.tableName = className;
        }
    }

    void setFactory(BeanFactory bf){
        this.factory = bf;
    }

    /**
     * get the name of the bean, the name is the id of the Bean;
     * @return
     */
    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the name of the bean class;
     * 
     * @return the qualified name of the bean;
     */
    public String getClassName(){
        return this.className;
    }

    /**
     * get the the name of the table matching this bean.
     * null if this bean has no matching DB table;
     * @return the name of the table;
     */
    public String getTableName(){
         return this.tableName;
    }

    /**
     * set the name of the table;
     *
     * @param tableName the name of the table;
     */
    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    /**
     * get the Class object of the bean.
     *
     * @return the Class of the bean;
     */
    public Class getBeanClass(){
        return beanClass;
    }

    /**
     * return if the bean has BLob fields;
     *
     * @return true if the bean has BLob columns,false otherwise;
     */
    public boolean hasBLOBCols() {
        return hasBLOBCols;
    }

    /**
     * check if the bean has CLob fields;
     *
     * @return true if the bean has clob columns, false otherwise;
     */
    public boolean hasCLOBCols() {
        return hasCLOBCols;
    }

    /**
     *
     * 
     * @return
     */
    public String getSql() {
        return sql;
    }

    /**
     *
     *
     * @param sql
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * add a property to the bean;
     *
     * @param a
     * @throws NoSuchPropertyException
     */
    public void addProperty(BeanProperty a) throws NoSuchPropertyException{
        String attrName = a.getName();
        String attrType = a.getType();
        Class paramClass = a.getTypeClass();
        if(paramClass == null){
            throw new NoSuchPropertyException(className,attrName,attrType);
        }
        if(!a.readOnly){
            String methodName;
            if(a.setMethodName==null){
                methodName = "set"+Character.toUpperCase(attrName.charAt(0))+attrName.substring(1);
            }else{
                methodName = a.setMethodName;
            }
            try{
                Method m = beanClass.getMethod(methodName,new Class[]{paramClass});
                a.setPropertySetMethod(m);
            }catch(NoSuchMethodException e){
                throw new NoSuchPropertyException(className,attrName,attrType);
            }
        }

        //try getXXX;
        String methodName;
        if(a.getMethodName==null){
            methodName = "get"+Character.toUpperCase(attrName.charAt(0))+attrName.substring(1);
        }else{
            methodName = a.getMethodName;
        }
        try{
            Method getMethod = beanClass.getMethod(methodName,new Class[0]);
            a.setPropertyGetMethod(getMethod);
        }catch(NoSuchMethodException e1){
            //try isXXX;
            methodName = "is"+Character.toUpperCase(attrName.charAt(0))+attrName.substring(1);
            try{
                Method m = beanClass.getMethod(methodName,new Class[0]);
                a.setPropertyGetMethod(m);
            }catch(NoSuchMethodException e2){
                throw new NoSuchPropertyException(className,attrName,attrType);
            }
        }


        //check if the property is a key property;
        if(a.isKey()){
            keyCols.add(a);
        } else {
            //if is byte[] or char[] properties, then add it at the top of the columns list;
            if("byte[]".equalsIgnoreCase(attrType) || "char[]".equalsIgnoreCase(attrType)){
                generalCols.add(0,a);
            }else{
                generalCols.add(a);
            }
        }

        if(!a.isReadOnly()){
            setableCols.add(a);
        }

        if(a.isPersitent()){
            if("byte[]".equalsIgnoreCase(attrType) || "char[]".equalsIgnoreCase(attrType)){
                persistentCols.add(0,a);
            }else{
                persistentCols.add(a);
            }
        }

        if("byte[]".equals(attrType)){
            this.hasBLOBCols=true;
        }

        if("char[]".equals(attrType)){
            this.hasCLOBCols=true;
        }

        properties.add(a);
    }

    public BeanProperty[] getKeyColumns(){
        return (BeanProperty[])keyCols.toArray(new BeanProperty[keyCols.size()]);
    }

    public BeanProperty[] getGeneralColumns(){
        return (BeanProperty[])generalCols.toArray(new BeanProperty[generalCols.size()]);
    }

    public List getPersistentCols() {
        return persistentCols;
    }

    public BeanProperty[] getSetableColumns(){
        return (BeanProperty[])setableCols.toArray(new BeanProperty[setableCols.size()]);
    }


    /**
     * get all the column names separated by ',';
     * @return
     */
    public String getColumnNamesString(){
        StringBuffer sb = new StringBuffer(128);
        int size = persistentCols.size();
        for(int i=0;i<size;i++){
            BeanProperty bp = (BeanProperty)persistentCols.get(i);
            String columnName = bp.getColumnName();
            sb.append(columnName).append(",");
        }

        sb.delete(sb.length()-1,sb.length());
        return new String(sb);
    }

    public String[] getColumnNames(){
        return (String[])persistentCols.toArray(new String[persistentCols.size()]);
    }


    public List getProperties(){
        return properties;
    }

    public boolean isSaveParent() {
        return saveParent;
    }

    public void setSaveParent(boolean saveParent) {
        this.saveParent = saveParent;
    }

    /**
     *
     *
     * @param insName;
     * @param request
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object create(String insName,HttpServletRequest request)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, BeanPropertyException {
        String name = null;
        String type = null;
        String format = null;
        String str = null;
        String qname= insName+".";

        Object value = null;
        Object o = null;
        BeanPropertyException bpe = null;

        try{
            o = beanClass.newInstance();
            int count = setableCols.size();
            for(int i=0;i<count;i++){
                BeanProperty a = (BeanProperty)setableCols.get(i);
                name = a.getName();
                type = a.getType();
                format = a.getFormat();
                str = request.getParameter(qname+name);

                try{
                    if(a.isPrimitive()){
                        if(format!=null){
                            value = PrimitiveType.create(type,str,format);
                        }else{
                            value = PrimitiveType.create(type,str);
                        }
                    }else{
                        Bean bean = this.factory.getByClass(a.getTypeClass());
                        value = bean.create(qname+name,request);
                    }
                    a.setValue(o,value);
                }catch(ParseException e){
                    if(bpe == null){
                        bpe = new BeanPropertyException();
                    }
                    String msg = str+" is not a valid \""+type+"\"";
                    if(format!=null){
                        msg +=" with format \""+format+"\"";
                    }
                    bpe.addError(msg);
                }catch(NumberFormatException e){
                    if(bpe == null){
                        bpe = new BeanPropertyException();
                    }
                    String msg = str+" is not a valid \""+type+"\"";
                    if(format!=null){
                        msg +=" with format \""+format+"\"";
                    }
                    bpe.addError(msg);
                }
            }
        }catch(InstantiationException e){
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("cant' create bean:"+className,e);
            }
            throw e;
        }catch(IllegalAccessException e){
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("cant' access property:"+ name+" of bean:"+className);
            }
            throw e;
        }catch(InvocationTargetException e){
            if(log.isEnabledFor(Priority.ERROR)){
                log.error("invocation exception when set property:"+ name+" of bean:"+className
                        ,e.getTargetException());
            }
            throw e;
        }
        if(bpe!=null){
            throw bpe;
        }
        return o;
    }

    /**
     *
     *
     * @param insName;
     * @param request
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object[] createArray(String insName,HttpServletRequest request)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, BeanPropertyException {
        ArrayList beans = new ArrayList();
        String baseName = insName+"[";

        String[] beanIDs = getBeanIDs(insName,request);
        for(int i=0;i<beanIDs.length;i++){
            Object o = create(baseName+beanIDs[i]+"]",request);
            beans.add(o);
        }
        
        return beans.toArray((Object[])Array.newInstance(beanClass,beans.size()));
    }


    /**
     *
     *
     * @param insName
     * @param request
     * @return
     */
    private String[] getBeanIDs(String insName,HttpServletRequest request){
        String name= insName+"[";
        int len = name.length();

        HashSet set = new HashSet();

        Enumeration names = request.getParameterNames();
        while(names.hasMoreElements()){
            String orginalName = (String)names.nextElement();
            if(orginalName.startsWith(name)){
                int index = orginalName.indexOf("].");
                if(index>len){
                    String trueName = orginalName.substring(len,index);
                    set.add(trueName);
                }
            }
        }

        return (String[])set.toArray(new String[set.size()]);
    }
}
