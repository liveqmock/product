/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.definition;

import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-31
 * Time: 10:28:06
 */
public class WFAction {
    protected String name;
    protected String type;
    protected String url;
    protected HashMap extendedAttributes;

    public WFAction() {

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return type;
    }

    public HashMap getExtendedAttributes() {
        return extendedAttributes;
    }

    void setName(String name) {
        this.name = name;
    }

    void setType(String type) {
        this.type = type;
    }

    void setUrl(String url) {
        this.url = url;
    }

    void addExtendedAttributes(String name, String value) {
        extendedAttributes.put(name, value);
    }

}
