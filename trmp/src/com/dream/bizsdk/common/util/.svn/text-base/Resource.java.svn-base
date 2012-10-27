/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import java.io.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-9-27
 * Time: 21:12:23
 */
public final class Resource {
    static Logger log = Logger.getLogger(Resource.class);

    public static File getResourceAsFile(ClassLoader cl, String resourceName) {

        try {
            InputStream is = cl.getResourceAsStream(resourceName);
            if (is != null) {
                File f = File.createTempFile("bizsdkresource", "tmp");
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));

                int bytesRead = 0;
                byte[] buf = new byte[4096];
                while ((bytesRead = is.read(buf)) >= 0) {
                    bos.write(buf, 0, bytesRead);
                }

                bos.close();
                is.close();
                return f;
            } else {
                return null;
            }
        } catch (IOException ioe) {
            if (log.isEnabledFor(Priority.ERROR)) {
                log.error("can't get resource by name " + resourceName, ioe);
            }
            return null;
        }
    }
}