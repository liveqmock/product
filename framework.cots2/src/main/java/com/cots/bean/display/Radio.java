/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.bean.display;

import org.w3c.dom.Element;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * User: chugh
 * Date: 2005-9-22
 * Time: 18:56:33
 */
public class Radio extends Group{
    public Radio() {
        type = "radio";
    }

    public void init(Element ele) {
        super.init(ele);
    }
}
