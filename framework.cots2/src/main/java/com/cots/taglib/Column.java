/**
 *all rights reserved,@copyright 2003
 */
package com.cots.taglib;

import com.cots.util.ClassUtil;
import com.cots.exp.freeexp.Parser;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-19
 * Time: 12:41:39
 * Version: 1.0
 */
public final class Column {
    protected String property;

    protected String title;

    protected String value;

    protected String align;

    protected String width;

    protected String valign;

    protected String format;

    protected String style;

    protected String bgcolor;

    private Method  getMethod;

    public Column(){

    }

    public Column(String property){
        this.property = property;
    }

    public Column(String property,String value){
        this.property = property;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getValign() {
        return valign;
    }

    public void setValign(String valign) {
        this.valign = valign;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

//    public Object getValue(Object o)
//            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
//        if(property!=null){
//            if(getMethod == null){
//                getMethod = ClassUtil.getMethod(o.getClass(),"get"+Character.toUpperCase(property.charAt(0))+
//                        property.substring(1),
//                        new Class[0]);
//            }
//            return getMethod.invoke(o,new Object[0]);
//        }else{
//            return Parser.getValue(this.value, o);
//        }
//    }

    /**
     *
     *
     * @param o
     * @param index
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object getValue(Object o,int index)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        if(property!=null){
            if(getMethod == null){
                getMethod = ClassUtil.getMethod(o.getClass(),"get"+Character.toUpperCase(property.charAt(0))+
                        property.substring(1),
                        new Class[0]);
            }
            return getMethod.invoke(o,new Object[0]);
        }else{
            return Parser.getValue(this.value,format,o,index);
        }
    }
}
