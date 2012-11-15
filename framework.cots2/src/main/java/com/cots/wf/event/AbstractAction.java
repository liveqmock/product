/**
 *all rights reserved,@copyright 2003
 */
package com.cots.wf.event;

import com.cots.wf.data.BizData;
import com.cots.wf.data.BizData;

import java.util.ArrayList;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-5-12
 * Time: 10:24:33
 */
public abstract class AbstractAction {
    private String url;

    public AbstractAction() {

    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public abstract void run(BizData data);
}
