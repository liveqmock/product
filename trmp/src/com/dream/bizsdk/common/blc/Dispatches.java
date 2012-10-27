/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.blc;

import com.dream.bizsdk.common.databus.BizData;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2003-12-27
 * Time: 14:56:50
 */
public final class Dispatches implements Serializable {
    protected int count;
    //protected String name=null;
    protected ArrayList disps = new ArrayList();
    protected Dispatch defDisp = null;


    public Dispatches() {

    }

    public Dispatches(String name) {

    }

    /**
     * 向一个转发组中增加一个转发，如果当前该组中默认转发为空，则该
     * 转发将同时作为默认转发；如果当前的默认转发宾不是真正的默认转
     * 发（其方法isDefault（）返回false,则新增加的转发将替代当前的
     * 默认转发。因此如果在配置文件中没有配置默认转发，则最后一个转
     * 发将作为默认转发；如果配置了多个默认转发，则第一个默认转发将
     * 作为实际的默认转发。
     *
     * @param disp 新增加的转发。
     */
    public void add(Dispatch disp) {
        disps.add(disp);
        if (defDisp == null) {
            defDisp = disp;
        } else if (!defDisp.isDefault()) {
            defDisp = disp;
        }
        count++;
    }

    /**
     * @param index
     * @param disp
     */
    public void add(int index, Dispatch disp) {
        disps.add(index, disp);
        if (defDisp == null) {
            defDisp = disp;
        } else if (!defDisp.isDefault()) {
            defDisp = disp;
        }
        count++;
    }

    /**
     * this function is used for convience;
     *
     * @param url
     * @param type
     * @param on
     * @param def
     */
    public void add(String url, String type, String on, String def) {
        int type2 = Dispatch.FORWARD;
        boolean def2 = false;
        if (url == null) {
            return;
        }
        if (type != null) {
            if (type.equalsIgnoreCase("redirect")) {
                type2 = Dispatch.REDIRECT;
            } else if (type.equalsIgnoreCase("include")) {
                type2 = Dispatch.INCLUDE;
            }
        }
        if (on != null && on.length() < 1) {
            on = null;
        }
        if (def != null && def.compareToIgnoreCase("true") == 0) {
            def2 = true;
        }
        add(new Dispatch(type2, url, def2, on));
    }

    /**
     * 当前BL请求执行完毕之后，根据该BL方法返回的数据对象决定执行哪个
     * 转发。
     *
     * @param rd
     * @param sd
     * @return
     */
    public Dispatch[] getDispatch(BizData rd, BizData sd) {
        ArrayList al = new ArrayList(6);
        if (count == 1) {
            return new Dispatch[]{defDisp};
        } else if (count == 0) {
            return new Dispatch[0];
        } else {
            for (int i = 0; i < count; i++) {
                Dispatch disp = (Dispatch) disps.get(i);
                if (disp.needDispatch(rd, sd)) {
                    al.add(disp);
                }
            }
            return (Dispatch[]) al.toArray(new Dispatch[al.size()]);
        }
    }

    /**
     * get count of dispatch in this Dispatches Group;
     *
     * @return
     */
    public int getLength() {
        return count;
    }
}