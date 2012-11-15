package com.cots.mvc.model.parameter;

/**
 * 
 * User: chuguanghua
 * Date: 2005-5-24
 * Time: 21:07:52
 */
public class ParameterTarget {
    public final static int NONE = 0;
    public final static int REQUEST = 1;
    public final static int SESSION = 2;
    public final static int APPLICATION = 3;

    public final static String REQUEST_STR="request";
    public final static String SESSION_STR="session";
    public final static String APPLICATION_STR="application";
    public final static String NONE_STR="none";

    public static String getString(int target){
        switch(target){
            case REQUEST:
                return REQUEST_STR;
            case SESSION:
                return SESSION_STR;
            case APPLICATION:
                return APPLICATION_STR;
            case NONE:
                return NONE_STR;
            default:
                return NONE_STR;
        }
    }
}
