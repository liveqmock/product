/*
 * FileUploadServlet.java
 *
 * Created on 2003年4月2日, 上午9:44
 */
package com.dream.bizsdk.web;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dream.bizsdk.common.SysError;
import com.dream.bizsdk.common.SysVar;
import com.dream.bizsdk.common.blc.BLContext;
import com.dream.bizsdk.common.blc.BLRequest;
import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.database.dao.DAO;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.RequestParams;
import com.dream.bizsdk.common.util.string.StringUtil;
import com.dream.bizsdk.common.util.uploader.FilePart;
import com.dream.bizsdk.common.util.uploader.MultiPart;

/**
 * @author chuguanghua
 */
public class FileUploadServlet extends HttpServlet {

    /**
     * default virtual path to save uploaded files;
     * This virtual path must be correcly specified as the initParam of this
     * Servlet,otherwise this servlet may not work properly.
     */
    protected String defVirPath = "/files";

    //the real path of the defVirpath;
    protected String defRealPath;

    protected int maxFileSize;

    protected boolean log = true;

    protected boolean fileIDAsFileName;

    protected boolean overwrite;

    protected boolean createPathIfNotExists;

    protected boolean isDebug;

    protected RequestParams rp = new RequestParams();

    protected String cp = "GBK";

    private BLServlet blServlet;

    private SecurityManager smAdmin;

    public static String LOG_TABLE = "DRMFILEUPLOADLOG";


    /**
     * Initializes the servlet.
     * This servlet has following init parameters,one is the "defVirpath",the default
     * virtual path to save uploaded files; the second is the "maxFileSize",
     * specify the maximum size of any uploaded files in bytes,<=0 means no limit on the
     * file size.The third is the "log",0 value means not to log;The fouth one
     * is fileIDAsFileName specifying that whether the generated file id as the
     * file name(the extension name will not be changed; The fivth is "overwrite".
     * This parameter is valid when fileIDAsFileName is false(0 value).
     *
     * @param config
     */
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        ServletContext sc = config.getServletContext();
        blServlet = (BLServlet) sc.getAttribute(SysVar.APP_BLSERVLET);
        smAdmin = (SecurityManager) sc.getAttribute(SysVar.APP_SM);

        defRealPath = sc.getRealPath(defVirPath);

        try {
            IBLContext blContext = (IBLContext) sc.getAttribute(SysVar.APP_BLCONTEXT);

            String debug = blContext.getConfigValue("system", "debug");
            if (debug != null && debug.compareToIgnoreCase("1") == 0) {
                isDebug = true;
            }

            //get the character set;
            cp = blContext.getConfigValue("system", "charset");
            if (cp == null) {
                cp = new String("GBK");
            }

            //defVirPath init param-get the init parameter defVirPath;
            String vp = blContext.getConfigValue("fileUpload", "defVirPath");
            //get the real path of the default virtual path;
            if (vp != null) {
                String rp = sc.getRealPath(vp);
                if (rp != null) {
                    defVirPath = vp;
                    defRealPath = rp;
                }
            }
            //maxFileSize init param.get the init parameter maxFileSize;
            String strMaxFileSize = blContext.getConfigValue("fileUpload", "maxFileSize");
            try {
                int size = Integer.parseInt(strMaxFileSize);
                maxFileSize = size;
            } catch (NumberFormatException nfe) {
            }
            //log init param;get the need log parameter;
            String needLog = blContext.getConfigValue("fileUpload", "log");
            if (needLog != null && needLog.compareTo("0") == 0) {
                log = false;
            }
            //fileIDAsFileName init param;
            String strFileIDAsFileName = blContext.getConfigValue("fileUpload", "fileIDAsName");
            if (strFileIDAsFileName != null && strFileIDAsFileName.compareTo("1") == 0) {
                fileIDAsFileName = true;
            }
            String strCreatePathIfNotExists = blContext.getConfigValue("fileUpload", "createPathIfNotExists");
            if (strCreatePathIfNotExists != null && strCreatePathIfNotExists.compareTo("1") == 0) {
                createPathIfNotExists = true;
            }

            //overwrite init parameter;
            String strOverwrite = blContext.getConfigValue("fileUpload", "overwrite");
            if (strOverwrite != null && strOverwrite.compareTo("1") == 0) {
                overwrite = true;
            }
            /**if not log,then fileID will not be available for the file Name;*/
            if (!log) {
                fileIDAsFileName = false;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
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
        response.sendError(505, "Sorry, this servlet does NOT like GET request.");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int retVal = 0;
        String contextPath = request.getContextPath();

        /**check the enctype of the request*/
        String encType = request.getContentType();
        if (!encType.toLowerCase().startsWith("multipart/form-data")) {
            response.sendError(505, "Sorry, this servlet ONLY accept content of type MULTIPART/FORM-DATA.");
            return;
        }
        /**begin to process the file upload request*/
        HttpSession session = request.getSession(true);
        if (session == null) {  //can't create a session object for this request;
            BizData data = new BizData();
            data.add(SysVar.ERROR_NO, new Integer(SysError.NO_SESSION));
            request.setAttribute(SysVar.REQ_DATA, data);
            RequestDispatcher disp = request.getRequestDispatcher(SysVar.ERRORPAGE);
            if (disp == null) {
                System.out.println("Error in BLServlet.dispatch():"
                        + "Error in BL and will forward to /jsp/error.jsp,but" +
                        " this page is not specifed!");
            } else {
                disp.forward(request, response);
            }
            return;
        }
        /**check the session data object, there must be a valid BizData Object in product mode*/
        BizData sessionData = (BizData) session.getAttribute(SysVar.SS_DATA);
        if (!isDebug && sessionData == null) {
            String loginPage = SysVar.LOGINPAGE.replaceAll(SysVar.CONTEXT_PATH, request.getContextPath());
            response.sendRedirect(loginPage);
            return;
        }else{
            if(sessionData == null){
                sessionData = new BizData();
            }
        }

        /**begin to upload the file*/
        try {
            retVal = upload(request, response, session, sessionData);
        } catch (IOException ioe) {
            String error = ioe.getMessage();
            if (error == null) {
                error = "Unknown message.";
            }
            response.sendRedirect(contextPath + "/jsp/uploadResult.jsp?error=" + URLEncoder.encode(error, "GBK"));
            return;
        } catch (Exception e) {

        }

        if (retVal > 0) {   //no nextrequest is specified,will display page with information of how many files are uploaded;
            response.sendRedirect(contextPath + "/jsp/uploadResult.jsp?uploadedFiles=" + retVal);
        } else if (retVal < 0) {
            blServlet.dispatch(request, response, null, new BizData(), sessionData, retVal);
        }
    }

    /**
     * upload files and check the size of the files and insert log if necessary;
     *
     * @param request  the request object;
     * @param response the response object;
     * @return the count of files uploaded successfully;
     */
    protected int upload(HttpServletRequest request, HttpServletResponse response, HttpSession session, BizData sessionData)
            throws IOException, ServletException, Exception {
        int i = 0;
        int fileID = -1;
        int filesCount = 0;

        String contentType = request.getContentType();
        if (contentType.indexOf("multipart/form-data") < 0) {
            throw new IOException("The request is not a HTTP File Upload request.");
        }

        byte[] bytes = getRequestData(request);

        MultiPart mp = new MultiPart(bytes);

        /*if the nextrequest after the file upload is specified,
        then check access to this request*/
        String uri = request.getRequestURI();
        String blName = StringUtil.getRequestNameFromURI(uri);
        if (blName != null && blName.length() > 0) {
            if (!isDebug && smAdmin.checkAccess(session, blName) < 0) {
               // return SysError.NO_ACCESS;
            }
        }

        /*get upload parameters*/
        String virPath = mp.getParameter("virPath");
        String groupType = mp.getParameter("groupType");
        String groupNO = mp.getParameter("groupNO");
        String needLog = mp.getParameter("needLog");
        //String onlyDB = mp.getParameter("onlyDB");

        String[] pathes = getRealPath(virPath);
        try {
            Vector files = mp.getFileParts();
            if (files == null || files.size() < 1) {
                throw new IOException("No file uploaded from your client.");
            }
            filesCount = files.size();
            
            //edit by divine 2010-10-23
            int[] fileIDs = new int[filesCount]; 
            while (i < filesCount) {
                //fileID=-1;
                FilePart pt = (FilePart) files.elementAt(i);
                //check file size;
                if (maxFileSize > 0 && pt.size() > maxFileSize) {
                    throw new IOException("File size exceeds the allowed maximum size.");
                }
                String extName = pt.getExtName();
                String fileName = pt.getFileName();
                String fullFileName = pt.getFullFileName();
                //check if need to log;
                if (fileIDAsFileName || this.log || needLog != null) {
                    fileID = insertRecord(groupType, groupNO, pathes[0]
                            , pt.size(), fullFileName, fileName, extName);
                }
                if (fileIDAsFileName && fileID < 0) {
                    throw new IOException("Can't create fileID for this file.");
                }

                fileIDs[i] = fileID;
                //save this file;
                saveFile(pt, pathes, fullFileName, fileName, extName, fileID);
                i++;
                
            }
            if (blName != null && blName.length() > 0) {
                BizData rd = new BizData();
                //记录fileID给其他用
                rd.add("FILEID", fileIDs);
                if (!rp.parse(mp, cp, rd)) {
                    return SysError.PARAM_PARSE_ERROR;
                }
                blProcess(mp, request, response, session, blName, rd, sessionData);
                return 0;
            } else {
                return filesCount;
            }
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    /**
     * this request may contains the bl request after the files are uploaded;
     *
     * @param mp
     */
    public boolean blProcess(MultiPart mp, HttpServletRequest req, HttpServletResponse res, HttpSession session
                             , String blName, BizData rd, BizData sessionData)
            throws IOException, ServletException, Exception {
        if (blName != null) {
            BLRequest breq = blServlet.getRequest(blName);
            blServlet.process2(req, res, session, breq, rd, sessionData);
            return true;
        } else {
            return false;
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
            System.out.println(new String(bytes));
            return bytes;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * updated 2004-5-24 by divine;
     *
     * @param pt
     * @param path
     * @param fullFileName
     * @param fileName
     * @param extName
     * @param fileID
     * @return
     * @throws IOException
     */
    protected int saveFile(FilePart pt, String[] path, String fullFileName, String fileName, String extName, int fileID)
            throws IOException {
        int retValue = 0;
        if (fileIDAsFileName) {
            if (fileID >= 0) {
                if (extName != null) {
                    retValue = pt.save(path[1] + fileID + "." + extName, this.overwrite);
                    pt.setVirPath(path[0] + fileID + "." + extName);
                } else {
                    retValue = pt.save(path[1] + fileID, this.overwrite);
                    pt.setVirPath(path[0] + fileID);
                }
            } else {
                throw new IOException("The fileID must be great than zero.");
            }
        } else {
        	File filePath = new File(path[1]);
        	if(!filePath.exists())
        		filePath.mkdir();
            retValue = pt.save(path[1] + fullFileName, this.overwrite);
            pt.setVirPath(path[0] + fullFileName);
        }
        return retValue;
    }

    /**
     * @param groupType
     * @param groupNO
     * @param fileURL
     * @param fileSize
     * @param fullFileName
     * @param fileName
     * @param extName
     * @return
     */
    protected int insertRecord(String groupType, String groupNO, String fileURL
                               , int fileSize, String fullFileName, String fileName, String extName) {
        int fileID = 0;
        //boolean hasRecs =false;
        String url = null;
        try {
            /*Get the BLContext object from the ServletContext;*/
            BLContext ctxt = (BLContext) this.getServletContext().getAttribute(SysVar.APP_BLCONTEXT);

            /*Get the core DAO object from the BLContext object and get the
             *maximum fileID fromthe FileUploadLog table in the core db*/
            DAO dao = ctxt.getDAO("core");
            Integer nextID = (Integer) dao.getFieldValue(FileUploadServlet.LOG_TABLE, "max(fileID)+1",
                    com.dream.bizsdk.common.database.datadict.Types.INT);
            if (nextID == null) {
                fileID = 1;
            } else {
                fileID = nextID.intValue();
            }
            //add the file name to the url;
            if (fileIDAsFileName) {
                url = fileURL + fileID;
                if (extName != null) {
                    url += "." + extName;
                }
            } else {
                url = fileURL + fullFileName;
            }
            /*prepare FileUploadlog record to isnert*/
            BizData d = new BizData();
            d.add(FileUploadServlet.LOG_TABLE, "fileID", new Integer(fileID));
            d.add(FileUploadServlet.LOG_TABLE, "groupType", groupType);
            d.add(FileUploadServlet.LOG_TABLE, "groupNO", groupNO);
            d.add(FileUploadServlet.LOG_TABLE, "fileUrl", url);
            d.add(FileUploadServlet.LOG_TABLE, "size", new Integer(fileSize));
            d.add(FileUploadServlet.LOG_TABLE, "name", fileName);
            d.add(FileUploadServlet.LOG_TABLE, "extName", extName);
            d.add(FileUploadServlet.LOG_TABLE, "uploadTime", new Date());
            if (dao.insert(FileUploadServlet.LOG_TABLE, d) > 0) {    //insert the record
                return fileID;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Get the real path of virtal path; This will query into the
     * FilePathMap table; It the defVirPath does exist,then this function should
     * not return null value.
     *
     * @param virPath the virtaul path;
     * @return two elements String array,The first String is the virtual path,
     *         the second is the real path of the virtual path;
     */
    protected String[] getRealPath(String virPath) {
        String[] retVals = new String[2];
        String realPath = null;
        //get the real path of the virtual path;
        if (virPath != null) {
            if (virPath.charAt(0) != '/') {
                virPath = "/" + virPath;
            }
            realPath = this.getServletContext().getRealPath(virPath);
            if (realPath == null) {
                if (createPathIfNotExists) {
                    String rootPath = this.getServletContext().getRealPath("/");
                    if (rootPath.charAt(rootPath.length() - 1) == File.separatorChar) {
                        realPath = rootPath + virPath.substring(1);
                    } else {
                        realPath = rootPath + virPath;
                    }
                } else {
                    //default values are used;
                    realPath = defRealPath;
                    virPath = defVirPath;
                }
            } else {
                File f = new File(realPath);
                if (!f.exists()) {
                    if (createPathIfNotExists) {
                        f.mkdirs();
                    } else {
                        //default values is used;
                        realPath = defRealPath;
                        virPath = defVirPath;
                    }
                }
            }
        } else {
            //default values are used;
            realPath = defRealPath;
            virPath = defVirPath;
        }
        /*make sure the virtual path and the real path end properly*/
        if (virPath.charAt(virPath.length() - 1) != '/') {
            virPath += '/';
        }
        if (realPath.charAt(realPath.length() - 1) != File.separatorChar) {
            realPath += File.separatorChar;
        }
        //the first string is the virtual path,the second is the real path;
        retVals[0] = virPath;
        retVals[1] = realPath;
        return retVals;
    }
}