/**
 *all rights reserved,@copyright 2003
 */
package com.cots.uploader;

import com.cots.util.FileUtil;
import com.cots.util.MimeTypeMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-4-10
 * Time: 15:22:18
 * Version: 1.0
 */
public class ResponseUtil {

    /**
     * write a File to HttpServletResponse object.
     *
     * @param response
     * @param filePath
     * @throws IOException
     */
    public final static void writeFile(HttpServletResponse response,String filePath,boolean inline)
            throws IOException{
        String name = FileUtil.getFileName(filePath);
        FileInputStream fis = new FileInputStream(filePath);
        writeStream(response,fis,name,MimeTypeMapping.getMimeType(FileUtil.getExtName(name)),inline);
    }

    /**
     * write a file to HttpServletResponse object;
     *
     * @param response
     * @param file
     * @throws IOException
     */
    public final static void writeFile(HttpServletResponse response,File file)
            throws IOException{
        writeFile(response,file.getAbsolutePath(),false);
    }

    /**
     *
     * @param response
     * @param file
     * @param inline
     * @throws IOException
     */
    public final static void writeFile(HttpServletResponse response,File file,boolean inline)
            throws IOException{
        writeFile(response,file.getAbsolutePath(),inline);
    }

    /**
     * write a input stream to a HttpServletResponse object;
     *
     * @param response
     * @param is
     * @param fileName
     * @throws IOException
     */
    public static final void writeStream(HttpServletResponse response,InputStream is,String fileName)
            throws IOException{
        writeStream(response,is,fileName,"application/octet-stream");
    }

    public static final void writeStream(HttpServletResponse response,InputStream is,String fileName,boolean inline)
                throws IOException{
            writeStream(response,is,fileName,"application/octet-stream",inline);
    }

    /**
     * write a InputStream to a HttpServletResponse object;
     *
     * @param response
     * @param is
     * @param fileName
     * @param contentType
     * @throws IOException
     */
    public static final void writeStream(HttpServletResponse response,InputStream is,String fileName,
                                         String contentType) throws IOException{
        writeStream(response,is,fileName,contentType,false);
    }

    /**
     *
     *
     * @param response
     * @param is
     * @param fileName
     * @param contentType
     * @param inline
     * @throws IOException
     */
    public static final void writeStream(HttpServletResponse response,InputStream is,String fileName,
                                         String contentType,boolean inline) throws IOException{

        BufferedInputStream bis = new BufferedInputStream(is);
        try{
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Length", String.valueOf(is.available()));
            response.setHeader("Content-Disposition", (inline?"inline":"attachment")+"; filename=" + fileName);
            byte abyte0[] = new byte[4096];
            for (boolean flag = false; !flag;) {
                int i = bis.read(abyte0);
                if (i == -1) {
                    flag = true;
                } else {
                    bos.write(abyte0, 0, i);
                }
            }
            bos.flush();
            bos.close();
        }finally{
            bis.close();
        }
    }

    /**
     * write a html segment to the response object.
     *
     * @param response the target response object;
     * @param html the html object to be written;
     * @throws IOException
     */
    public static final void writeHtml(HttpServletResponse response,String html)
            throws IOException{
        response.setContentType("text/html;charset=GB2312");
        PrintWriter writer = response.getWriter();
        writer.println(html);
        writer.flush();
        writer.close();
    }
}
