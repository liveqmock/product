/*
 * FileUploadServlet.java
 *
 * Created on 2003年4月2日, 上午9:44
 */
package com.dream.bizsdk.web;

import com.dream.bizsdk.common.util.uploader.FilePart;
import com.dream.bizsdk.common.util.uploader.MultiPart;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Vector;
import java.io.*;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author chuguanghua
 */
public class FileUploadServlet2 extends HttpServlet {

    /**
     * default virtual path to save uploaded files;
     * This virtual path must be correcly specified as the initParam of this
     * Servlet,otherwise this servlet may not work properly.
     */

    protected int maxFileSize = 1024 * 1024;

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
        response.sendError(505, "Sorry, this servlet does NOT accept GET request.");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**checn the enctype of the request*/
        String encType = request.getContentType();
        if (!encType.toLowerCase().startsWith("multipart/form-data")) {
            response.sendError(505, "Sorry, this servlet ONLY accept content of type MULTIPART/FORM-DATA.");
            return;
        }

        /**begin to upload the file*/
        try {
            upload(request, response);
        } catch (IOException ioe) {
            ioe.printStackTrace(new PrintStream(response.getOutputStream()));
            return;
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(response.getOutputStream()));
            return;
        }

        response.getOutputStream().print("Files uploaded.");
    }

    /**
     * upload files and check the size of the files and insert log if necessary;
     *
     * @param request  the request object;
     * @param response the response object;
     */
    protected void upload(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, Exception {
        int filesCount = 0;

        byte[] bytes = getRequestData(request);

        MultiPart mp = new MultiPart(bytes);
        String id = mp.getParameter("id");

        try {
            Vector files = mp.getFileParts();
            if (files == null || files.size() < 1) {
                throw new IOException("No file uploaded from your request.");
            }
            filesCount = files.size();
            if (filesCount > 0) {
                //fileID=-1;
                FilePart pt = (FilePart) files.elementAt(0);
                //check file size;
                if (maxFileSize > 0 && pt.size() > maxFileSize) {
                    throw new IOException("File size exceeds the allowed maximum size.");
                }
                writeFile2DB(pt, id);
            }
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    /**
     * Get request data from the stream;
     *
     * @param req The request object;
     */
    protected byte[] getRequestData(HttpServletRequest req) {
        int readBytes = 0;
        int totalBytes = req.getContentLength();
        byte[] bytes = new byte[totalBytes];
        try {
            while (readBytes < totalBytes) {
                req.getInputStream();
                readBytes += req.getInputStream().read(bytes, readBytes, totalBytes - readBytes);
            }
            return bytes;
        } catch (IOException e) {
            return null;
        }
    }

    private void writeFile2DB(FilePart pt, String id)
            throws SQLException {

        byte[] bytes = pt.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = ds.getConnection();
            ps = conn.prepareStatement("insert imgtest(id,img) values(?,?)");
            ps.setString(1, id);
            ps.setBinaryStream(2, bais, bytes.length);

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}