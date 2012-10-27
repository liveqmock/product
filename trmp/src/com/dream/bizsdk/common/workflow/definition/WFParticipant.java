/**
 * copyright 2004,primton ltd.All rights reserved.
 */


package com.dream.bizsdk.common.workflow.definition;

import java.util.HashMap;

/**
 * Module:  WFParticipant.java
 * Description:
 * <p/>
 * <p/>
 * Author:  chuguanghua
 * Created: 2004Äê3ÔÂ31ÈÕ 10:22:15
 * Purpose: Defines the Class WFParticipant
 */

public class WFParticipant {
    /**
     * Id of the participant;
     */
    private String id;

    /**
     * type of the participarnt;
     */
    private String type;


    /**
     * url of the participant;
     */
    private String url;

    /**
     * extended attributes;
     */
    private HashMap extendedAttributes = new HashMap();

    /**
     * description of the participant;
     */
    private String description;

    public WFParticipant() {

    }

    public WFParticipant(String id, String type, String desc) {
        this.id = id;
        this.type = type;
        this.description = desc;
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

    public String getExtendedAttribyte(String name) {
        return (String) extendedAttributes.get("name");
    }
/*
    public String getRole(){
        return this.role;
    }
*/
    public String getDescription() {
        return this.description;
    }

    void setID(String id) {
        this.id = id;
    }

    void setType(String type) {
        this.type = type;
    }

    void setDescritpion(String description) {
        this.description = description;
    }

    void setUrl(String url) {
        this.url = url;
    }

    void setExtendedAttribute(String name, String value) {
        extendedAttributes.put(name, value);
    }
}