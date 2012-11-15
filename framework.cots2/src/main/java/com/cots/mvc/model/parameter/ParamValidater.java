/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

import com.cots.util.XMLFile;
import org.w3c.dom.Element;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-7-5
 * Time: 8:41:47
 * Version: 1.0
 */
public interface ParamValidater{

    String getMessage();

    void save(XMLFile holder,Element parent);
}

