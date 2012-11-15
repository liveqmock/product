/**
 *all rights reserved,@copyright 2003
 */
package com.cots.util;

import java.util.HashMap;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-12
 * Time: 10:14:42
 * Version: 1.0
 */
public class MimeTypeMapping {
    private static HashMap mapping = new HashMap();

    static{
        mapping.put("doc","application/ms-word");
        mapping.put("pdf","application/pdf");
        mapping.put("xsl","application/ms-excel");
    }

    /**
     * get mime type for file entension displayName;
     *
     * @param ext the ext displayName;
     * @return
     */
    public static String getMimeType(String ext){
        String mime = (String)mapping.get(ext);
        if(mime==null){
            mime = "application/octet-stream";
        }
        return mime;
    }
}
