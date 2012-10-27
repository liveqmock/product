/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util;

import java.io.*;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-6-30
 * Time: 15:42:22
 */
public class FileUtil {

    /**
     * copy a file object;
     *
     * @param src    the source File object;
     * @param target the target file path;
     * @throws java.io.FileNotFoundException if the source file not found;
     * @throws java.io.IOException
     */
    public static void copyFile(File src, String target) throws FileNotFoundException, IOException {
        int read = 0;
        byte[] bytes = new byte[4096];
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(target)));
        while ((read = in.read(bytes)) >= 0) {
            out.write(bytes, 0, read);
        }
        in.close();
        out.flush();
        out.close();
    }

    /**
     * copy a file from one path to another;
     *
     * @param src    the source file path;
     * @param target the target file path;
     * @throws FileNotFoundException if the source file not found;
     * @throws IOException
     */
    public static void copyFile(String src, String target) throws FileNotFoundException, IOException {
        int read = 0;
        byte[] bytes = new byte[4096];
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(src)));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(target)));
        while ((read = in.read(bytes)) >= 0) {
            out.write(bytes, 0, read);
        }
        in.close();
        out.flush();
        out.close();
    }
}