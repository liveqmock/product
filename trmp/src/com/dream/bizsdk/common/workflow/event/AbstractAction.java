/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.workflow.event;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.Param;

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
    private ArrayList params = new ArrayList();

    public AbstractAction() {

    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList getParams() {
        return this.params;
    }

    public Param getParam(int i) {
        return (Param) this.params.get(i);
    }

    public void addParam(Param p) {
        params.add(p);
    }

    public abstract void run(BizData data);
}
