/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.web;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-7-27
 * Time: 16:30:14
 */
public class Util {

    /**
     * Send a file to Browser via a HttpServletResponse object.
     *
     * @param response the HttpServletResponse object;
     * @param f        the File object to be sent;
     * @param fileType the type of the file,should be a mime type: applicaton/pdf, etc.
     * @throws IOException
     */
    public static final void sendFile(HttpServletResponse response, File f, String fileType)
            throws IOException {
        long size = f.length();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
        String fileName = f.getName();

        response.setHeader("Content-Type", fileType);
        response.setHeader("Content-Length", String.valueOf(size));
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);

        BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(response.getOutputStream());
        byte abyte0[] = new byte[4096];
        for (boolean flag = false; !flag;) {
            int i = bis.read(abyte0);
            if (i == -1) {
                flag = true;
            } else {
                bufferedoutputstream.write(abyte0, 0, i);
            }
        }
        bufferedoutputstream.flush();
        bis.close();
        bufferedoutputstream.close();
    }

    /**
     * Send a file to Browser via HttpServletResponse object;
     *
     * @param response HttpServletResponse object,
     * @param filePath the path of the File to be sent;
     * @param fileType the mime type of the file;
     * @throws IOException
     */
    public static final void sendFile(HttpServletResponse response, String filePath, String fileType)
            throws IOException {
        sendFile(response, filePath, fileType);
    }
}