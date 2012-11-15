/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.util.XMLFile;
import org.w3c.dom.Element;

import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-7-5
 * Time: 8:10:59
 * Version: 1.0
 */
public interface Validater {

    /**
     * validate method;
     *
     * @param value
     * @return
     */
    boolean validate(Object value);
}
