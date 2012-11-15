/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.dispatch;

import com.cots.util.XMLFile;
import com.cots.util.Cache;

import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.apache.log4j.Logger;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-20
 * Time: 16:20:34
 * Version: 1.0
 */
public final class ScreenMapping implements Cache{
    private String baseDir;
    private HashMap screens = new HashMap();
    private Logger log = Logger.getLogger(ScreenMapping.class);

    /**
     *
     * @param baseDir
     */
    public ScreenMapping(String baseDir){
        this.baseDir = baseDir;
        init(this.baseDir);
    }

    public synchronized boolean refresh(){
        return init(this.baseDir);
    }
    /**
     *
     */
    public boolean init(String baseDir){
        boolean succ = true;
        File dir = new File(baseDir);
        if(dir.exists() && dir.isDirectory()){
            File[] subFiles = dir.listFiles();
            int count = subFiles.length;
            for(int i=0;i<count;i++){
                File subFile = subFiles[i];
                if(subFile.isDirectory()){  //dive into sub directorys;
                    init(subFile.getAbsolutePath());
                }else{
                    try {
                        XMLFile xml = new XMLFile(subFile);
                        NodeList nl = xml.selectNodeList("/managed-screens/screen");
                        int length = nl.getLength();
                        for(int j=0;j<length;j++){
                            Element screenEle = (Element)nl.item(j);
                            String name = screenEle.getAttribute("name");
                            if(name.length()<1){
                                continue;
                            }

                            Screen screen = new Screen(name);
                            //initialize pre screen views;
                            initScreenViews(screenEle,screen,true);
                            //initialize post screen views;
                            initScreenViews(screenEle,screen,false);
                            screens.put(name,screen);
                        }
                    } catch (ParserConfigurationException e) {
                        log.error("can't parse Screen Mapping files",e);
                        succ = false;
                        break;
                    } catch (SAXException e) {
                        log.error("failed to parse "+subFile.getAbsolutePath(),e);
                        succ = false;
                    } catch (IOException e) {
                        log.error("failed to parse "+subFile.getAbsolutePath(),e);
                        succ = false;
                    }
                }
            }
        }
        return succ;
    }

    /**
     * initialize screen views.
     *
     * @param screenEle
     * @param screen
     * @param pre
     */
    private void initScreenViews(Element screenEle,Screen screen,boolean pre){
        NodeList viewsList;
        if(pre){
            viewsList = screenEle.getElementsByTagName("preView");
        }else{
            viewsList = screenEle.getElementsByTagName("postView");
        }
        int views = viewsList.getLength();
        for(int k=0;k<views;k++){
            Element view = (Element)viewsList.item(k);
            String name = view.getAttribute("name");
            if(name.length()<0){
                name = null;
            }
            String uri = view.getAttribute("uri");
            if(uri.length()<1){
                continue;
            }else{
                ScreenView sv = new ScreenView(name,uri);
                if(pre){
                    screen.addPreView(sv);
                }else{
                    screen.addPostView(sv);
                }
            }
        }
    }

    /**
     * add a new screen to this cache;
     *
     * @param scr the new Screen to be added;
     */
    public void add(Screen scr){
        screens.put(scr.getName(),scr);
    }

    /**
     * remove a named Screen object;
     *
     * @param name the displayName of the Screen
     * @return the removed Screen object,null if there is no screen of the displayName;
     */
    public Screen remove(String name){
        return (Screen)screens.remove(name);
    }

    /**
     * get a named Screen
     *
     * @param screenName the displayName of the Screen to get;
     * @return the named Screen object;
     */
    public Screen get(String screenName){
        return (Screen)screens.get(screenName);
    }
}