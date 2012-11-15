/**
 *all rights reserved,@copyright 2003
 */
package com.cots.taglib;

import javax.servlet.jsp.tagext.Tag;
import java.util.Set;

/**
 * Description:
 *      Selector tag should be a child of Table Tag. This tag will display a selector(
 * a radio intpu or checkbox tag
 *
 * User: chuguanghua
 * Date: 2005-3-21
 * Time: 9:10:01
 * Version: 1.0
 */
public final class SelectorTag extends BaseTag{
    //type of the selector, maybe one of "radio" and "checkbox"
    protected String type;

    //displayName of the "radio" or "checkbox"
    protected String name;

    //value of the "radio" or "checkbox"
    protected String value;

    protected String align;

    protected String valign;

    protected String width;

    protected String style;

    protected String bgcolor;

    protected String checker;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
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

    public int doStartTag(){
        Tag parent = this.getParent();
        //the column tag must be child of a TableTag;
        if(parent instanceof TableTag){
            if(type==null){
                type = "checkbox";
            }

            Selector selector = new Selector();

            selector.setValign(valign);
            selector.setAlign(align);
            selector.setWidth(width);
            selector.setCheckor(checker);
            selector.setBgcolor(bgcolor);
            selector.setStyle(style);
            selector.setName(name);
            selector.setValue(value);
            selector.setType(type);
            selector.setOnblur(onblur);
            selector.setOnchange(onchange);
            selector.setOnclick(onclick);
            selector.setOndblclick(ondblclick);
            selector.setOnfocus(onfocus);

            if(checker!=null){
                Object o = pageContext.findAttribute(checker);
                if(o instanceof Set){
                    selector.setCheckerSet((Set)o);
                }
            }

            TableTag table = (TableTag)parent;
            table.setSelector(selector);
        }
        return Tag.SKIP_BODY;
    }

    public int doEndTag(){
        return Tag.EVAL_PAGE;
    }
}
