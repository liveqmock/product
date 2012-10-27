package com.dream.bizsdk.common.databus;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 *
 * User: divine
 * Date: 2004-10-3
 * Time: 22:01:38
 */
public class BaseData implements Serializable{
    protected Map fields = new HashMap();

    public BaseData(){

    }

    /**
     *
     * @param name
     * @param value
     */
    public void put(String name,Object value){
        fields.put(name,value);
    }

    /**
     *
     * @param name
     * @param ch
     */
    public void putChar(String name,char ch){
        fields.put(name,new Character(ch));
    }

    /**
     *
     * @param name
     * @param b
     */
    public void putByte(String name,byte b){
        fields.put(name,new Byte(b));
    }

    /**
     *
     * @param name
     * @param s
     */
    public void putShort(String name,short s){
        fields.put(name,new Short(s));
    }

    /**
     *
     * @param name
     * @param i
     */
    public void putInt(String name,int i){
        fields.put(name,new Integer(i));
    }

    /**
     *
     * @param name
     * @param l
     */
    public void putLong(String name,long l){
        fields.put(name,new Long(l));
    }

    /**
     *
     * @param name
     * @param f
     */
    public void putFloat(String name,float f){
        fields.put(name,new Float(f));
    }

    /**
     *
     * @param name
     * @param d
     */
    public void putDouble(String name,double d){
        fields.put(name,new Double(d));
    }

    /**
     *
     * @param name
     * @return
     */
    public Object get(String name){
        return fields.get(name);
    }

    /**
     *
     * @param name
     * @return
     */
    public char getChar(String name){
        return ' ';
    }

    /**
     *
     * @param name
     * @return
     */
    public byte getByte(String name){
        return 0;
    }

    /**
     *
     * @param name
     * @return
     */
    public short getShort(String name){
        return 0;
    }

    /**
     *
     * @param name
     * @return
     */
    public int getInt(String name){
        return 0;
    }

    /**
     *
     * @param name
     * @return
     */
    public long getLong(String name){
        return 0;
    }

    /**
     *
     * @param name
     * @return
     */
    public float getFloat(String name){
        return 0;
    }

    /**
     *
     * @param name
     * @return
     */
    public double getDouble(String name){
        return 0;
    }

    /**
     *
     * @param name
     * @return
     */
    public String getString(String name){
        return null;
    }

    /**
     *
     * @param name
     * @return
     */
    public Date getDate(String name){
        return null;
    }

    /**
     *
     * @return
     */
    int size(){
        return fields.size();
    }

    /**
     * 
     * @param name
     * @return
     */
    public Object remove(String name){
        return fields.remove(name);
    }

    /**
     * return the key set;
     *
     * @return
     */
    public String[] keys(){
        return (String[])fields.keySet().toArray(new String[fields.size()]);
    }

    /**
     * check if a value is of single type;
     *
     * @param name
     * @return
     */
    public boolean isSingle(String name){
        Object v = get(name);
        if(!(v instanceof Map) && !(v instanceof List)) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * check if an item is an array;
     *
     * @param name
     * @return
     */
    public boolean isArray(String name){
        Object v = get(name);
        if(v instanceof List) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * check if an item is of Table type;
     * @param name
     * @return
     */
    public boolean isTable(String name){
        Object v = get(name);
        if(v instanceof Table) {
            return true;
        }else{
            return false;
        }
    }
}