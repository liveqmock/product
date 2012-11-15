/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.bean;

/**
 * User: chugh
 * Date: 2005-9-17
 * Time: 23:29:57
 */
public class BeanPropertyValue {
    private String value;
    private String label;

    public BeanPropertyValue() {
    }

    public BeanPropertyValue(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
