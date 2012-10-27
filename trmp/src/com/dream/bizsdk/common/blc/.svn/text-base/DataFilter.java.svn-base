/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;

import java.io.Serializable;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-9-11
 * Time: 9:26:47
 */
public class DataFilter implements Serializable {
    protected String name;
    protected String field;
    protected String row;

    public DataFilter() {

    }

    public DataFilter(String name) {
        this.name = name;
    }

    public DataFilter(String name, String field) {
        this.name = name;
        this.field = field;
    }

    public DataFilter(String name, String field, String row) {
        this.name = name;
        this.field = field;
        this.row = row;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return null;
    }

    public String getField() {
        return null;
    }

    public void setField(String field) {
        this.field = field;
    }

    /**
     * filter the target data entity from the source to the target BizData obejct;
     * say the property 'name' equals to "DRMUser', then Only the Data entity named
     * DRMUser will filtered into the target Object,all other data will not be transfered
     * into the target BizData object;
     *
     * @param src    the source BizData Object to be filtered;
     * @param target The target BizData object that will hold the value that was filtered out
     */
    public void filter(BizData src, BizData target) {
        if (name == null) {
            return;
        } else {
            if (field == null) {
                if (row == null) {
                    target.copyEntity(src, name);
                } else {
                    target.copyEntityRow(src, name, row);
                }
            } else {
                if (row == null) {
                    target.copyEntityField(src, name, field);
                } else {
                    target.copyEntityField(src, name, field, row);
                }
            }
        }
    }
}
