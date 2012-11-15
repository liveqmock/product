/**
 * copyright 2004,primton ltd.All rights reserved.
 */


package com.cots.wf.definition;

/**
 * Module:  WFDataField.java
 * Description:
 * <p/>
 * <p/>
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 10:14:29
 * Purpose: Defines the Class WFDataField
 */

public class WFDataField {
    /**
     * data type constants;
     */
    public final static int INT = 1;
    public final static int LONG = 2;
    public final static int FLOAT = 3;
    public final static int DOUBLE = 4;
    public final static int DATE = 5;
    public final static int RECORD = 6;
    public final static int STRING = 7;
    public final static int CHAR = 8;

    protected String id;
    protected String dataType;
    protected String name;

    protected boolean isArray;
    protected Object initialValue;
    protected int length;

    public WFDataField() {

    }

    public String getID() {
        return this.id;
    }

    public boolean isArray() {
        return this.isArray;
    }

    public String getType() {
        return this.dataType;
    }

    public Object getInitialValue() {
        return this.initialValue;
    }

    public int getLength() {
        return this.length;
    }

    public String getName() {
        return this.name;
    }

    void setID(String id) {
        this.id = id;
    }

    void setArray(boolean isArray) {
        this.isArray = isArray;
    }

    void setType(String dataType) {
        this.dataType = dataType;
    }

    void setInitialValue(Object value) {
        this.initialValue = value;
    }

    void setLength(int length) {
        this.length = length;
    }

    void setName(String name) {
        this.name = name;
    }
}