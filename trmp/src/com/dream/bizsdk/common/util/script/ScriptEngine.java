/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.script;

import com.dream.bizsdk.common.util.string.StringUtil;
import com.dream.bizsdk.common.util.xml.XMLFile;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.blc.AbstractBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.SysVar;

import java.util.HashMap;
import java.io.File;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-2-25
 * Time: 17:14:59
 */
public class ScriptEngine extends AbstractBLC {

    public void exec(HashMap daos, String rootPath) throws Exception {
        int i = 0;
        int count = 0;
        String xpath = "allscripts/scripts";
        String fileName = null;
        String daoName = null;

        rootPath = StringUtil.checkPath(rootPath);
        File sCatalog = new File(rootPath + "scripts-catalog.xml");
        XMLFile xmlFile = new XMLFile(sCatalog);
        NodeList nl = xmlFile.selectNodeList(xpath);
        count = nl.getLength();
        while (i < count) {
            Node n = nl.item(i);
            fileName = ((Element) n).getAttribute("name");
            daoName = ((Element) n).getAttribute("dao");
            DAO dao = (DAO) daos.get(daoName);
            try {
                execScripts(rootPath + fileName, rootPath, dao);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    public void execScripts(String filePath, String rootPath, DAO dao) throws Exception {
        int i = 0;
        int count = 0;
        String type = dao.getType();
        Script s = ScriptFactory.getScript(type);
        s.setLogFilePath(rootPath + "log.txt");
        String grpXPath = "scripts/group[@type=\"" + type + "\"]/script";

        XMLFile xmlFile = new XMLFile(filePath);
        NodeList nl = xmlFile.selectNodeList(grpXPath);
        count = nl.getLength();
        while (i < count) {
            Node n = nl.item(i);
            String name = ((Element) n).getAttribute("name");
            String single = ((Element) n).getAttribute("single");
            s.run(rootPath + type + File.separatorChar + name, single != null && single.compareToIgnoreCase("yes") == 0 ? true : false, dao);
            i++;
        }
    }

    public int exec(BizData rd, BizData sd) {
        HashMap daos = _context.getDAOsMap();
        HttpServletRequest req = (HttpServletRequest) rd.get(SysVar.HTTP_REQ);
        ServletContext sc = req.getSession().getServletContext();
        String rootPath = sc.getRealPath("/WEB-INF/sqlscripts/");
        try {
            exec(daos, rootPath);
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }
}
