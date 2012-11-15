/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.util;

import com.cots.dao.DAO;
import com.cots.bean.Bean;
import com.cots.bean.BeanProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.sql.SQLException;
import java.lang.reflect.InvocationTargetException;

/*
* Created on Oct 8, 2005
*
* To change the template for this generated file go to
* Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
*/

public class MemoryObjects {
    private Map objects = new HashMap();
    private String key;
    private Class keyClass;
    private Class objClass;
    private DAO dao;
    private Object[] array;
    private String where;

    private MemoryObjects() {
    }


    public synchronized void add(Object obj) throws SQLException{
        //get the key value;
        Object key =ObjectUtil.getField2(obj,this.key);

        Object old = objects.get(key);
        if(old == null){
            //insert this record into underlying database;
            dao.insert(obj);
        }else{
            dao.update(obj);
        }
        objects.put(key, obj);
        //refresh the array;
        array = toArray();
    }


    public synchronized Object remove(Object key) throws SQLException{
        //first get the object from the map;
        Object removed = objects.get(key);
        //then trying to delete from the underlying database;
        if (removed != null) {
            dao.delete(removed);
            //if the record is really deleted, then remove from the map;
            objects.remove(key);
        }
        //refresh the array;
        array = toArray();
        return removed;
    }


    public Object get(Object key) {
        return objects.get(key);
    }

    /**
     * return the size of the objects;
     *
     * @return
     */
    public int size(){
        return objects.size();
    }


    public Object[] getArray() {
        return array;
    }

    public void reload() throws SQLException{
        load(objClass, where,dao);
    }

    public synchronized void clear(){
        objects.clear();
        toArray();
    }

    /**
     * load a specified object into memory.
     *
     * @param key the key of the object to be loaded.
     */
    public synchronized void load(Object key) throws IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException, SQLException {
        Object bean = objClass.newInstance();
        ObjectUtil.setField(bean,this.key,key,keyClass);
        dao.expand(bean);
        objects.put(key,bean);
        array = toArray();
    }


    public void unload() {
        instances.remove(objClass);
    }

    private synchronized void load(Class cls, String where,DAO dao)  throws SQLException{
        this.objClass = cls;
        this.dao = dao;
        this.where = where;

        Bean bean = dao.getObjectHelperFactory().getBeanFactory().getByClass(cls);
        if(bean == null){
            throw new SQLException("bean not found");
        }

        BeanProperty[] keyCols = bean.getKeyColumns();
        if(keyCols==null || keyCols.length!=1){
            throw new SQLException("as a Memory objects, this bean must have a primary key that consists of olny one column.");
        }
        key = keyCols[0].getName();
        keyClass = keyCols[0].getTypeClass();

        //clear the buffer;
        objects.clear();

        List list = dao.query(cls,where);
        int size = list.size();
        for(int i=0;i<size;i++){
            Object obj = list.get(i);
            objects.put(ObjectUtil.getField2(obj,key),obj);
        }

        array = toArray();
    }

    public Object[] toArray() {
        Object[] array = (Object[]) java.lang.reflect.Array.newInstance(objClass
                , objects.size());
        objects.values().toArray(array);
        return array;
    }

    private static Map instances = new HashMap();

    public static MemoryObjects getInstance(Class bean, DAO dao) throws SQLException{
        return getInstance(bean,bean,null,dao);
    }

    public static MemoryObjects getInstance(Object name,Class bean, DAO dao) throws SQLException{
        return getInstance(name,bean,null,dao);
    }

    public static MemoryObjects getInstance(Object name,Class bean, String where,DAO dao) throws SQLException{
        MemoryObjects mo = (MemoryObjects) instances.get(name);
        if (mo == null) {
            synchronized (MemoryObjects.class) {
                mo = (MemoryObjects) instances.get(name);
                if (mo == null) {
                    mo =new MemoryObjects();
                    mo.load(bean,where,dao);
                    instances.put(name, mo);
                }
            }
        }
        return mo;
    }
}