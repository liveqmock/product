package com.cots.bean;

import com.cots.bean.display.Display;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;

/**
 * User: chugh
 * Date: 2004-10-15
 * Time: 23:08:00
 */
public class BeanProperty {
    //name of the property;
    protected String name;

    //type of the property;
    protected String type;

    protected Class typeClass;

    protected boolean primitive = true;

    protected boolean isArray;

    protected String format;

    //whether to persitent this field to db;
    protected boolean persitent=true;

    //the matching db column name;
    protected String columnName;

    protected boolean isKey;

    protected boolean readOnly;

    protected String getMethodName;

    protected String setMethodName;

    protected String displayType;

    protected String alias;

    protected String maxLength;
    protected String minLength;

    protected String minValue;
    protected String maxValue;

    protected String maxValueObject;
    protected String minValueObject;
    protected boolean nullable;

    protected Display display;

    protected String fkTable;
    protected String fkName;
    protected String fkLabel;
    protected String fkDAOName;

    protected List valuesEnum;

    protected Method propertySetMethod;
    protected Method propertyGetMethod;

    public BeanProperty(String name,String type){
        this.name = name;
        this.type = type;
        Class cls = PrimitiveType.getTypeClass(type);
        if(cls != null){
            typeClass = cls;
        }else{
            try{
                typeClass = Class.forName(type);
                primitive = false;
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * get the name of the attribute
     *
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * get the type of this attribute;
     *
     * @return
     */
    public String getType(){
        return this.type;
    }

    public Class getTypeClass(){
        return this.typeClass;
    }

    public boolean isPrimitive(){
        return primitive;
    }

    public void setTypeClass(Class typeClass){
        this.typeClass = typeClass;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getMinLength() {
        return minLength;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public List getValuesEnum() {
        return valuesEnum;
    }

    public void addValue(BeanPropertyValue bpv){
        if(valuesEnum==null){
            valuesEnum = new ArrayList();
        }

        valuesEnum.add(bpv);
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * check if the property is a key field;
     *
     * @return
     */
    public boolean isKey(){
        return isKey;
    }

    /**
     * set the key flag of the field;
     *
     * @param key true if the field is a key field, false otherwise;
     */
    public void setKey(boolean key){
        this.isKey = key;
    }

    /**
     * whether this property is readonly;
     *
     * @return true if the property is read only, false otherwise;
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * set the read only flag of the field.
     *
     * @param readOnly true if this field should be read only,false otherwise;
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    /**
     * 
     * @return
     */
    public String getFormat() {
        return format;
    }

    /**
     *
     * @param format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     *
     * @return
     */
    public String getDisplayType() {
        return displayType;
    }

    /**
     *
     * @param displayType
     */
    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    /**
     *
     * @param value
     */
    public void addValue(String value){
        if(valuesEnum==null){
            valuesEnum = new ArrayList();
        }
        valuesEnum.add(value);
    }

    /**
     *
     * @param m
     */
    void setPropertySetMethod(Method m){
        this.propertySetMethod= m;
        if(m!=null && setMethodName == null){
            setMethodName = m.getName();
        }
    }

    /**
     *
     * @return
     */
    public Method getPropertySetMethod(){
        return this.propertySetMethod;
    }

    /**
     * get the column name of the property; if there is no column name is
     * spcified, then the name of the field is returned;
     *
     * @return the column name of the property;
     */
    public String getColumnName(){
        if(this.columnName!=null){
            return this.columnName;
        }else{
            return this.name;
        }
    }

    /**
     * set the column name of the property;
     *
     * @param colName the new column name;
     */
    public void setColumnName(String colName){
        this.columnName = colName;
    }

    /**
     * whether this property should be persistented;
     *
     * @return true if yes, false otherwise;
     */
    public boolean isPersitent(){
        return this.persitent;
    }

    /**
     * set the persistence of the property;
     *
     * @param persitent
     */
    public void setPersitent(boolean persitent){
        this.persitent = persitent;
    }

    public String getFkTable() {
        return fkTable;
    }

    public void setFkTable(String fkTable) {
        this.fkTable = fkTable;
    }

    public String getFkName() {
        return fkName;
    }

    public void setFkName(String fkName) {
        this.fkName = fkName;
    }

    public String getFkLabel() {
        return fkLabel;
    }

    public void setFkLabel(String fkLabel) {
        this.fkLabel = fkLabel;
    }

    public String getFkDAOName() {
        return fkDAOName;
    }

    public void setFkDAOName(String fkDAOName) {
        this.fkDAOName = fkDAOName;
    }

    /**
     * set the get Method object;
     *
     * @param m
     */
    void setPropertyGetMethod(Method m){
        this.propertyGetMethod= m;
        if(m!=null && getMethodName==null){
            getMethodName = m.getName();
        }
    }

    /**
     * return the Method to get the value of the property;
     *
     * @return the get Method object;
     */
    public Method getPropertyGetMethod(){
        return this.propertyGetMethod;
    }

    /**
     * reutrn the name of the get method of the Property;
     *
     * @return the name of the get method;
     */
    public String getGetMethodName() {
        return getMethodName;
    }

    public void setGetMethodName(String getMethodName) {
        if(getMethodName!=null && getMethodName.length()>0){
            this.getMethodName = getMethodName;
        }
    }

    public void setSetMethodName(String setMethodName) {
        if(setMethodName!=null && setMethodName.length()>0){
            this.setMethodName = setMethodName;
        }
    }

    /**
     * return the name of the set method of the property;
     *
     * @return the name of the set method;
     */
    public String getSetMethodName() {
        return setMethodName;
    }

    /**
     * set a value to this property of a Bean object;
     *
     * @param bean the bean that holds this Property;
     * @param value the new value to be set;
     * @return true if the value is really set, false otherwise;
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public boolean setValue(Object bean,Object value)
            throws IllegalAccessException, InvocationTargetException {
        if(value!=null){
            this.propertySetMethod.invoke(bean,new Object[]{value});
            return true;
        }else{
            return false;
        }
    }

    /**
     * get the value of the property from the
     *
     * @param bean the Bean object from which to get the value;
     * @return the value of this Property
     * @throws IllegalAccessException if can't access the property;
     * @throws InvocationTargetException if Exception is thrown in the get method of the property;
     */
    public Object getValue(Object bean) throws IllegalAccessException, InvocationTargetException {
        return this.propertyGetMethod.invoke(bean,new Object[0]);
    }
}
