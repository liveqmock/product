/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.menu;

import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysVar;

import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-3-24
 * Time: 14:56:58
 */
public class MenuBar {
    private HashMap allMenus = new HashMap();
    private Vector mainMenus = new Vector();

    public MenuBar() {

    }


    public void addMenu(Menu m) {
        int i = 0;
        int count;
        String name = m.getName();
        String parentName = m.getParentName();
        allMenus.put(name, m);
        
        // 为根节点时，则执行以下操作
        if ("0".equals(parentName) || parentName.length() < 1) {
            int dispOrder = m.getDispOrder();
            count = mainMenus.size();
            while (i < count) {
                if (((Menu) mainMenus.elementAt(i)).getDispOrder() > dispOrder) {
                    break;
                } else {
                    i++;
                }
            }
            mainMenus.add(i, m);
        }
    }

    public void doTree() {
        Iterator it = allMenus.values().iterator();
        
        while (it.hasNext()) 
        {
            Menu m = (Menu) it.next();
            String parentName = m.getParentName();
            
            // 判断不是为根节点的元素，则进行如下操作
            if (!("0").equals(parentName)) 
            {
                Menu p = (Menu) allMenus.get(parentName);
                p.addSubMenu(m);
            }
        }
    }

    public String serialize() {
        doTree();
        int count = mainMenus.size();
        StringBuffer sb = new StringBuffer(1024);
        sb.append("[");
        for (int i = 0; i < count; i++) {
            Menu m = (Menu) mainMenus.elementAt(i);
            m.serialize(sb);
        }
        sb.append("]");
        return new String(sb);
    }
    

    public String serializeHTML(HashMap roleMenu) {
        doTree();
        int count = mainMenus.size();
        StringBuffer sb = new StringBuffer(1024);
        sb.append("<ul>");
        for (int i = 0; i < count; i++) {
            Menu m = (Menu) mainMenus.elementAt(i);
            m.serializeHTML(sb, roleMenu);
        }
        sb.append("</ul>");
        return new String(sb);
    }

    public static String assembleMenu(BizData data) {
        return assembleMenu("", data);
    }

    public static String assembleMenu(String context, BizData data) {
        String rs = "DRMRoleMenus";
        MenuBar mb = new MenuBar();
        String[] rowIDs = data.getRowIDs(rs);
        for (int i = 0; i < rowIDs.length; i++) {
            String name = (String) data.get(rs, "menuname", rowIDs[i]);
            String label = (String) data.get(rs, "menulabel", rowIDs[i]);
            String url = (String) data.get(rs, "funcNO", rowIDs[i]);
            if (url != null && context != null) {
                url = url.replaceAll(SysVar.CONTEXT_PATH, context);
            }
            String parentName = (String) data.get(rs, "parentMenuName", rowIDs[i]);
            int dispOrder = data.getInt(rs, "dispOrder", rowIDs[i]);

            if (name != null && label != null) {
                mb.addMenu(new Menu(name, parentName, label, url, dispOrder));
            }
        }
        return mb.serialize();
    }

    public static String assembleMenu2HTML(BizData data) {
        HashMap roleMenu = new HashMap();
        int rows = data.getTableRowsCount("DRMRoleMenus");
        for (int i = 0; i < rows; i++) {
        	// BigInteger转换成String类型
            String menuName = data.get("DRMRoleMenus", "menuName", i).toString();
            roleMenu.put(menuName, menuName);
        }

        String rsMenu = "DRMMenus";
        MenuBar mb = new MenuBar();
        String[] rowIDs = data.getRowIDs(rsMenu);
        for (int i = 0; i < rowIDs.length; i++) {
        	// BigInteger转换成String类型
            String name = data.get(rsMenu, "menuname", rowIDs[i]).toString();
            String label = (String) data.get(rsMenu, "menulabel", rowIDs[i]);
            String url = (String) data.get(rsMenu, "funcNO", rowIDs[i]);
            // BigInteger转换成String类型
            String parentName = data.get(rsMenu, "parentMenuName", rowIDs[i]).toString();
            int dispOrder = data.getInt(rsMenu, "dispOrder", rowIDs[i]);
            if (name != null && label != null) {
                mb.addMenu(new Menu(name, parentName, label, url, dispOrder));
            }
        }
        return mb.serializeHTML(roleMenu);
    }
}
