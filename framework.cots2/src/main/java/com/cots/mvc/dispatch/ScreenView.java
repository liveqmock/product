/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.dispatch;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-20
 * Time: 16:02:01
 * Version: 1.0
 */
public class ScreenView {

    //displayName of the screen view,can be null;
    String name;

    //the context-relative uri of this view;
    String uri;

    public ScreenView(String name,String uri){
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }
}
