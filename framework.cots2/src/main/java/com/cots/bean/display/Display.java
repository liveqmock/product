package com.cots.bean.display;

import com.cots.bean.BeanProperty;
import com.cots.blc.BLContext;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.sql.SQLException;

/**
 * User: chugh
 * Date: 2005-9-22
 * Time: 19:43:51
 */
public interface Display {
    int DISPLAY_READONLY=0;
    int DISPLAY_NEW=1;
    int DISPLAY_UPDATE=2;

    void write(JspWriter out,String beanName,Object bean,BeanProperty bp,int dispType,BLContext context)
            throws IOException,SQLException;

    String getDisplayName();
}
