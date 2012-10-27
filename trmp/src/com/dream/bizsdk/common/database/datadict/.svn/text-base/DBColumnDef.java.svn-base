package com.dream.bizsdk.common.database.datadict;

import java.io.Serializable;

/**
 * Title:        engine
 * Description:  This is the platform of the business development kit.
 * Copyright:    Copyright (c) 2002
 * Company:      dream
 *
 * @author chuguanghua
 * @version 1.0
 */

public class DBColumnDef implements Serializable {

    private String tableName;
    private String name;
    private String colType = StrType.OTHER;
    private int type = Types.UNKNOWN;
    private int colLen;
    private int colPrecision;
    private boolean isPK;
    private boolean isNullable;
    private String refTableName;
    private String refCode;
    private String refName;
    private String displayName;
    private String displayType;
    private int displaySeq = -1;
    private String datasource;
    private String format;
    private boolean canSearch;

    public DBColumnDef() {

    }

    /**
     * @param tableName
     * @param colName
     * @param colType
     * @param isNullable
     * @param isPK
     * @param colLen
     * @param precision
     * @param refTableName
     * @param refColName
     * @param displayName
     * @param displayType
     * @param displaySeq
     * @param datasource
     * @param canSearch
     * @param format
     */
    public DBColumnDef(String tableName
                       , String colName
                       , String colType
                       , boolean isNullable
                       , boolean isPK
                       , int colLen
                       , int precision
                       , String refTableName
                       , String refColName
                       , String displayName
                       , String displayType
                       , int displaySeq
                       , String datasource
                       , boolean canSearch
                       , String format) {
        this.tableName = tableName;
        this.name = colName;
        this.displayName = displayName;
        this.colType = colType;
        this.isNullable = isNullable;
        this.colLen = colLen;
        this.colPrecision = precision;
        this.refTableName = refTableName;
        this.refCode = refColName;
        this.displayName = displayName;
        this.displayType = displayType;
        this.displaySeq = displaySeq;
        this.datasource = datasource;
        this.isPK = isPK;
        this.canSearch = canSearch;
        this.format = format;

        this.type = Types.typeFromName(colType);
    }

    /**
     * @param tableName
     * @param colName
     * @param colType
     * @param isNullable
     * @param isPK
     * @param colLen
     * @param precision
     * @param refTableName
     * @param refCode
     * @param refName
     * @param displayName
     * @param displayType
     * @param displaySeq
     * @param datasource
     * @param canSearch
     * @param format
     */
    public DBColumnDef(String tableName
                       , String colName
                       , String colType
                       , boolean isNullable
                       , boolean isPK
                       , int colLen
                       , int precision
                       , String refTableName
                       , String refCode
                       , String refName
                       , String displayName
                       , String displayType
                       , int displaySeq
                       , String datasource
                       , boolean canSearch
                       , String format) {
        this.tableName = tableName;
        this.name = colName;
        this.displayName = displayName;
        this.colType = colType;
        this.isNullable = isNullable;
        this.colLen = colLen;
        this.colPrecision = precision;
        this.refTableName = refTableName;
        this.refCode = refCode;
        this.refName = refName;
        this.displayName = displayName;
        this.displayType = displayType;
        this.displaySeq = displaySeq;
        this.datasource = datasource;
        this.isPK = isPK;
        this.canSearch = canSearch;
        this.format = format;

        this.type = Types.typeFromName(colType);
    }

    /**
     * get the name of the field;
     */
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * get the name of the field;
     */
    public String getName() {
        return new String(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the type of the field;
     */
    public int getType() {
        return type;
    }

    public void setStrType(String name) {
        colType = name;
        type = Types.typeFromName(name);
    }

    public String getStrType() {
        return colType;
    }

    /**
     * get the length of the entity;
     */
    public int getLength() {
        return colLen;
    }


    public void setLength(int len) {
        this.colLen = len;
    }

    public int getPrecistion() {
        return colPrecision;
    }

    public void setPrecision(int p) {
        this.colPrecision = p;
    }


    public boolean isPK() {
        return isPK;
    }

    public void setPK(boolean v) {
        isPK = v;
    }

    public String getRefTableName() {
        return refTableName;
    }

    public void setRefTableName(String name) {
        this.refTableName = name;
    }

    public String getRefCode() {
        return refCode;
    }

    public String getRefName() {
        return refName;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public String getDispType() {
        return displayType;
    }

    public void setDispType(String dispType) {
        this.displayType = dispType;
    }

    public int getDispSeq() {
        return displaySeq;
    }

    public void setDispSeq(int s) {
        this.displaySeq = s;
    }

    public String getSource() {
        return datasource;
    }

    public void setSource(String source) {
        this.datasource = source;
    }

    public void setSearch(boolean search) {
        this.canSearch = search;
    }

    public boolean isSearch() {
        return this.canSearch;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}