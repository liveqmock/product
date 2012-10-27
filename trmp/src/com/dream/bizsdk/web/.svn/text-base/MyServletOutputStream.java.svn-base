/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.web;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-8-29
 * Time: 9:11:32
 */
public class MyServletOutputStream extends ServletOutputStream {

    private OutputStream os;

    public MyServletOutputStream() {

    }


    public MyServletOutputStream(File f) throws IOException {
        os = new FileOutputStream(f);
    }

    public void write(int b) throws IOException {
        System.out.println(b + " writed");
        os.write(b);
    }

    public void close() throws IOException {
        os.close();
    }

    public void flush() throws IOException {
        os.flush();
    }
}
