/**
 * copyright 2004,primton ltd.All rights reserved.
 */


package com.cots.wf.definition;

import java.util.HashMap;

/**
 * Module:  WFApplication.java
 * Description:
 * <p/>
 * <p/>
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 11:20:44
 * Purpose: Defines the Class WFApplication
 */

public class WFApplication {
    private String id;
    private String type;
    private String url;
    private String description;
    private HashMap extendedAttributes;

    public WFApplication() {

    }

    public String getID() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDescription() {
        return this.description;
    }

    public String getExtendedAttribute(String name) {
        return (String) extendedAttributes.get("name");
    }

    void setID(String id) {
        this.id = id;
    }

    void setType(String type) {
        this.type = type;
    }

    void setUrl(String url) {
        this.url = url;
    }

    void setDescription(String desc) {
        this.description = desc;
    }

    void setExtendedAttribute(String name, String value) {
        extendedAttributes.put(name, value);
    }
}