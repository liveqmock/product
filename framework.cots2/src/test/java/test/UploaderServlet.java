/**
 *all rights reserved,@copyright 2003
 */
package test;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-8-26
 * Time: 16:36:24
 * Version: 1.0
 */
public class UploaderServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FileOutputStream fos = new FileOutputStream(new File("c:\\test.dat"));

        ServletInputStream is = request.getInputStream();

        byte[] buf = new byte[4096];
        int read;
        while ((read = is.read(buf)) >= 0) {
            fos.write(buf,0,read);
        }

        fos.flush();
        fos.close();
        is.close();
    }
}
