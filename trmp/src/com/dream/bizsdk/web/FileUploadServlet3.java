/*
 * FileUploadServlet.java
 *
 * Created on 2003年4月2日, 上午9:44
 */
package com.dream.bizsdk.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.*;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author chuguanghua
 */
public class FileUploadServlet3 extends HttpServlet {

    private DataSource ds;

    /**
     * Initializes the servlet.
     * This servlet has following init parameters,one is the "defVirpath",the default
     * virtual path to save uploaded files; the second is the "maxFileSize",
     * specify the maximum size of any uploaded files in bytes,<=0 means no limit on the
     * file size.The third is the "log",0 value means not to log;The fouth one
     * is fileIDAsFileName specifying that whether the generated file id as the
     * file name(the extension name will not be changed; The fivth is "overwrite".
     * This parameter is valid when fileIDAsFileName is false(0 value).
     */
    public void init(ServletConfig config) throws ServletException {

        super.init(config);


        String dsJndi = this.getInitParameter("dsJndi");

        try {
            InitialContext ic = new InitialContext();

            ds = (DataSource) ic.lookup(dsJndi);
        } catch (NamingException ne) {
            throw new ServletException(ne);
        }
    }

    /**
     * Destroys the servlet.
     */
    public void destroy() {
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "File upload servlet.";
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        //this servlet doest not accept Get request;
        process(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        process(request, response);
    }

    /**
     * upload files and check the size of the files and insert log if necessary;
     *
     * @param request  the request object;
     * @param response the response object;
     */
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bt = 0;
        int size = 0;

        String id = request.getParameter("id");
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            File f = File.createTempFile("img", "tmp");
            FileOutputStream fos = new FileOutputStream(f);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            conn = ds.getConnection();
            ps = conn.prepareStatement("select img from imgtest where id=?");
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                InputStream is = rs.getBinaryStream(1);
                if (is != null) {
                    while ((bt = is.read()) >= 0) {
                        bos.write(bt);
                        size++;
                    }
                    bos.flush();
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));

                    response.setHeader("Content-Type", "image/jpeg");
                    response.setHeader("Content-Length", String.valueOf(size));
                    BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(response.getOutputStream());
                    byte abyte0[] = new byte[1024];
                    for (boolean flag = false; !flag;) {
                        int i = bis.read(abyte0);
                        if (i == -1) {
                            flag = true;
                        } else {
                            bufferedoutputstream.write(abyte0, 0, i);
                        }
                    }
                    bufferedoutputstream.flush();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            response.sendError(503);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }
}