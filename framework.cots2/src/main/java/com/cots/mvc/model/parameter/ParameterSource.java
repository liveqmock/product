/**
 *all rights reserved,@copyright 2003
 */
package com.cots.mvc.model.parameter;

/**
 * Description:
 *
 * User: chugh
 * Date: 2004-10-27
 * Time: 19:37:54
 * Version: 1.0 
 */
public final class ParameterSource {
    public final static int REQUEST=0;
    public final static int SESSION=1;
    public final static int APPLICATION=2;
    public final static int NEW=3;
    public final static int WEBCONTAINER=4;
    public final static int CODE=5;

    public final static String REQUEST_STR="request";
    public final static String SESSION_STR="session";
    public final static String APPLICATION_STR="application";
    public final static String NEW_STR="new";
    public final static String WEBCONTAINER_STR="webcontainer";
    public final static String CODE_STR="code";


    public static String getString(int source){
        switch(source){
            case REQUEST:
                return REQUEST_STR;
            case SESSION:
                return SESSION_STR;
            case APPLICATION:
                return APPLICATION_STR;
            case NEW:
                return NEW_STR;
            case WEBCONTAINER:
                return WEBCONTAINER_STR;
            default:
                return CODE_STR;
        }
    }
}
