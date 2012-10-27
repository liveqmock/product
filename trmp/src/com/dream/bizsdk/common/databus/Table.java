/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.databus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.Serializable;

/**
 * User: divine
 * Date: 2004-10-3
 * Time: 21:27:15
 */
public class Table implements Serializable{
    protected String name;
    protected Map rows = new HashMap();

    public Table(){

    }

    /**
     *
     * @param name
     */
    public Table(String name){
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     *
     *
     * @param id
     * @return
     */
    public Row getRow(Object id){
        return (Row)rows.get(id);
    }

    /**
     * fetch a row Object, and create it if not exists;
     *
     * @param id
     * @return
     */
    public Row fetchRow(Object id){
        Row r = getRow(id);
        if(r == null){
            r = new Row(id);
            rows.put(id,r);
        }
        return r;
    }

    /**
     *
     * @return
     */
    public Row[] getRows(){
        return (Row[])rows.values().toArray(new Row[rows.size()]);
    }

    /**
     * set
     * @param field
     * @param value
     */
    public void addField(String field,Object value){
        int i=0;
        int count;
        Row[] rows = getRows();
        count = rows.length;
        while(i<count){
            rows[i].put(field,value);
            i++;
        }
    }

    /**
     * set
     * @param field
     */
    public void removeField(String field){
        int i=0;
        int count;
        Row[] rows = getRows();
        count = rows.length;
        while(i<count){
            rows[i].remove(field);
            i++;
        }
    }

    /**
     * get a new row object;
     *
     * @param rowId
     * @return
     */
    public Row newRow(Object rowId){
        Row r = new Row(rowId);
        rows.put(rowId,r);
        return r;
    }

    /**
     *
     * @param rowId
     * @return
     */
    public Row remove(Object rowId){
        return (Row)rows.remove(rowId);
    }

    /**
     *
     * @return
     */
    public Set getRowIDs(){
        return rows.keySet();
    }

    /**
     *
     * @param newKey
     */
    public void changeRowID(String newKey){
        int i=0;
        int count;
        Row[] rows = getRows();
        count = rows.length;
        while(i<count){
            Object newId = rows[i].get(newKey);
            rows[i].setId(newId);
            i++;
        }
    }
}
