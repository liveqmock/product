/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util;

import com.dream.bizsdk.common.database.datadict.DataDict;

import java.io.OutputStream;
import java.io.InputStream;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-18
 * Time: 14:31:42
 */
public class JSPTemplate {
    private DataDict dd;

    public JSPTemplate() {
    }

    public JSPTemplate(DataDict dd) {
        this.dd = dd;
    }

    public void newJSP(String target, String template) {

    }

    protected String readNextEntity(InputStream is, OutputStream os) {
        return null;
    }

    protected void writeNewRecord(OutputStream os, String tableName) {

    }

    protected void writeNewRecord(OutputStream os, String tableName, String[] cols) {

    }

    protected void writeUpdateRecord(OutputStream os, String tableName) {

    }

    protected void writeUpdateRecord(OutputStream os, String tableName, String[] cols) {

    }

    protected void writeListRecord(OutputStream os, String tableName) {

    }

    protected void writeListRecord(OutputStream os, String tableName, String[] cols) {

    }

    protected void writeSearchRecord(OutputStream os, String tableName) {

    }

    protected void writeSearchRecord(OutputStream os, String tableName, String[] cols) {

    }
}