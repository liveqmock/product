/**
 *all rights reserved,@copyright 2003
 */

package com.dream.bizsdk.common.database.datadict;

import java.util.Hashtable;
import java.util.Iterator;
import java.io.Serializable;

import java.util.TreeMap;

/**
 * Title:        engine
 * Description:  This is the platform of the business development kit.
 * Copyright:    Copyright (c) 2002
 * Company:      dream
 *
 * @author chuguanghua
 * @version 1.0
 */

public class DBTableDef implements Serializable {

    private String name = new String("");

    private String displayName = new String("");

    private Hashtable fields = new Hashtable();

    private TreeMap colSeqs = new TreeMap();

    public DBTableDef() {
    }

    public DBTableDef(String name, String displayName) {
        this.displayName = displayName;
        this.name = name;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * get the table name;
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the display name;
     *
     * @return
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * set the display name;
     *
     * @param displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * add a Column to the entity;
     */
    public void addColumn(DBColumnDef fld) {
        int seq = 0;
        if (null == fld) {
            return;
        }
        fields.put(fld.getName(), fld);
        seq = fld.getDispSeq();
        if (seq >= 0) {
            colSeqs.put(new Integer(fld.getDispSeq()), fld);
        }
    }

    /**
     * @param fld
     */
    public void deleteColumn(DBColumnDef fld) {
        if (null == fld) {
            return;
        }
        fields.remove(fld.getName());
        fields.remove(new Integer(fld.getDispSeq()));
    }

    /**
     * @param ColumnName
     * @return
     */
    public DBColumnDef getColumn(String ColumnName) {
        return (DBColumnDef) fields.get(ColumnName.toUpperCase());
    }

    /**
     * return an array of the Column object in this entity;
     * if there is no Column in the entity,then exception will be thrown.
     *
     * @return
     */
    public DBColumnDef[] getColumns() {
        return (DBColumnDef[]) (fields.values().toArray(new DBColumnDef[0]));
    }

    /**
     * @return
     */
    public DBColumnDef[] getSortedColumns() {
        int i = 0;
        int count = colSeqs.size();
        DBColumnDef[] cols = new DBColumnDef[count];
        Iterator it = colSeqs.keySet().iterator();
        while (it.hasNext()) {
            cols[i++] = (DBColumnDef) colSeqs.get(it.next());
        }
        return cols;
    }

    /**
     * Get the count of Columns in this entity object;
     *
     * @return count of the Columns in this entity object;
     */
    public int size() {
        return fields.size();
    }

    /**
     * @return
     */
    public DBTableDef duplicate() {
        DBTableDef n = new DBTableDef(name, displayName);
        DBColumnDef[] cols = getColumns();
        for (int i = 0; i < cols.length; i++) {
            n.addColumn(cols[i]);
        }
        return n;
    }

    /**
     * @return
     */
    public String getPKName() {
        int i = 0;
        int count = 0;
        DBColumnDef[] cols = getColumns();
        count = cols.length;
        while (i < count) {
            if (cols[i].isPK()) {
                return cols[i].getName();
            }
            i++;
        }
        return null;
    }
}