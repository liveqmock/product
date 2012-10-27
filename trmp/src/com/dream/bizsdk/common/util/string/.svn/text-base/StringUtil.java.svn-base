package com.dream.bizsdk.common.util.string;

import java.io.File;

/**
 * <p>Title: engine</p>
 * <p>Description: This is the platform of the business development kit.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: dream</p>
 *
 * @author chuguanghua
 * @version 1.0
 */

public final class StringUtil {

    public StringUtil() {
    }

    /**
     * get the request name from a uri string;
     *
     * @param uri the http uri;
     * @return the service name of the uri. for example with the uri /abc/def.do
     *         the servicename will be 'def'.
     */
    public static String getRequestNameFromURI(String uri) {
        String service = StringUtil.substring(uri, '/', 1);
        int index = service.lastIndexOf('.');
        if (index >= 0) {
            return service.substring(0, index);
        } else {
            return service;
        }
    }

    /**
     * get the service type name from a uri string;
     *
     * @param uri the http uri;
     * @return the service name of the uri. for example with the uri /abc/def.do
     *         the servicename will be 'do'.
     */
    public static String getServiceTypeFromURI(String uri) {
        String service = StringUtil.getRequestNameFromURI(uri);
        return StringUtil.substring(service, '.', 1);
    }

    /**
     * get a substring from a source string.
     *
     * @param source    the source string. if this is null, a null value will be
     *                  returned.
     * @param endChar   thos chars that before or after(depends on the direction)
     *                  the 'endchar' will be rerurned as the substring.
     * @param direction the left or right substring. 0 will return a left string,
     *                  other values will return a right substring.
     * @return
     */
    public static String substring(String source, int endChar, int direction) {
        int pos = 0;
        String retVal = null;

        if (null != source) {
            if (direction == 0) {
                pos = source.indexOf(endChar);
                if (pos >= 0) {
                    retVal = source.substring(0, pos);
                } else {
                    retVal = source;
                }
            } else {
                pos = source.lastIndexOf(endChar);
                if (pos >= 0) {
                    retVal = source.substring(pos + 1);
                } else {
                    retVal = source;
                }
            }
        }
        return retVal;
    }

    /**
     * chech a file path, if the path is not ending with a File.sepaterChar, then this char
     * is added automatically;
     *
     * @param path orginal file path string;
     * @return a file path ending with File.separaterChar;
     */
    public static String checkPath(String path) {
        if (path.charAt(path.length() - 1) != File.separatorChar) {
            path = path + File.separatorChar;
        }
        return path;
    }

}