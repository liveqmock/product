/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-5-12
 * Time: 10:28:20
 */
public class Param {
    private String name;
    private String type;
    private String defaultValue;
    private boolean variable = true;

    public Param() {

    }

    public Param(String name, String type, String defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public Param(String name, String type, String defaultValue, boolean variable) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.variable = variable;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isVariable() {
        return variable;
    }

    public void setVariable(boolean variable) {
        this.variable = variable;
    }
}

