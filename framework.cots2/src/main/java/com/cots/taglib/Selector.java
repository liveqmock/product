/**
 *all rights reserved,@copyright 2003
 */
package com.cots.taglib;

import java.util.Set;

/**
 * Description:
 *
 *
 * User: chuguanghua
 * Date: 2005-3-22
 * Time: 10:31:50
 * Version: 1.0
 */
public final class Selector {
    private String name;
    private String type;
    private String width;
    private String align;
    private String valign;
    private String value;
    private String checkor;
    private String style;
    private String bgcolor;


    //on click event;
    protected String onclick;
    //on focus event;
    protected String onfocus;
    //on blur event;
    protected String onblur;
    //on double click;
    protected String ondblclick;

    protected String onchange;

    private Set checkerSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getValign() {
        return valign;
    }

    public void setValign(String valign) {
        this.valign = valign;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCheckor() {
        return checkor;
    }

    public void setCheckor(String checkor) {
        this.checkor = checkor;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public Set getCheckerSet() {
        return checkerSet;
    }

    void setCheckerSet(Set checkerSet) {
        this.checkerSet = checkerSet;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnfocus() {
        return onfocus;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public String getOnblur() {
        return onblur;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public String getOndblclick() {
        return ondblclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }
}
