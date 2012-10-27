/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.web;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.ServletOutputStream;
import java.io.*;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-29
 * Time: 9:22:58
 */
public class MyHttpServletResponse extends HttpServletResponseWrapper {
    private File myFile;
    private ServletOutputStream os;
    private PrintWriter w;

    public MyHttpServletResponse(HttpServletResponse ares, File file) {
        super(ares);
        this.myFile = file;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (os == null) {
            return (os = new MyServletOutputStream(myFile));
        } else {
            return os;
        }
    }

    public PrintWriter getWriter() throws IOException {
        if (w == null) {
            return (w = new PrintWriter(new FileWriter(myFile)));
        } else {
            return w;
        }
    }

    public void setContentLength(int i) {

    }


    public void flushBuffer() throws IOException {
        if (os != null) {
            os.flush();
        } else if (w != null) {
            w.flush();
        }
    }

    public void resetBuffer() {
    }

    public boolean isCommitted() {
        return false;
    }
}