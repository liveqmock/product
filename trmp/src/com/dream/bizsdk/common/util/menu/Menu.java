/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.menu;

import java.util.Vector;
import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-3-24
 * Time: 14:36:25
 */
public class Menu {
    private String name = null;
    private String parentName = null;
    private String label = null;
    private String url = null;
    private int dispOrder;

    private Vector subMenus = new Vector();

    public Menu() {

    }

    public Menu(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public Menu(String name, String label, String url) {
        this.name = name;
        this.label = label;
        this.url = url;
    }

    public Menu(String name, String parentName, String label, String url) {
        this.parentName = parentName;
        this.name = name;
        this.label = label;
        this.url = url;
    }

    public Menu(String name, String parentName, String label, String url, int dispOrder) {
        this.parentName = parentName;
        this.name = name;
        this.label = label;
        this.url = url;
        this.dispOrder = dispOrder;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(int dispOrder) {
        this.dispOrder = dispOrder;
    }

    public void addSubMenu(Menu m) {
        int i = 0;
        int count = 0;
        int dispOrder = m.getDispOrder();
        count = subMenus.size();
        while (i < count) {
            if (((Menu) subMenus.elementAt(i)).getDispOrder() > dispOrder) {
                break;
            } else {
                i++;
            }
        }
        subMenus.add(i, m);
    }

    public void serialize(StringBuffer sb) {
        int count = 0;
        sb.append('[');
        sb.append('\'');
        sb.append(label);
        sb.append("','");
        if (url == null) {
            sb.append("#',null");
        } else {
            sb.append(url);
            sb.append("',null");
        }
        if ((count = subMenus.size()) > 0) {
            sb.append(",");
            for (int i = 0; i < count; i++) {
                ((Menu) subMenus.elementAt(i)).serialize(sb);
            }
        }
        sb.append("]");
        sb.append(",");
    }
    
    public void serializeHTML(StringBuffer sb, HashMap roleMenu) {
        int count = 0;
        sb.append("<li>");
        sb.append("<input type=\"checkbox\" name=\"DRMMenu/menuName[" + name + "]\" value=\"" + name + "\"");
        if (roleMenu.get(name) != null) {
            sb.append("checked");
        }
        sb.append(">");
        sb.append(label);
        if ((count = subMenus.size()) > 0) {
            sb.append("<ul>");
            for (int i = 0; i < count; i++) {
                ((Menu) subMenus.elementAt(i)).serializeHTML(sb, roleMenu);
            }
            sb.append("</ul>");
        }
        sb.append("</li>");
    }
}
