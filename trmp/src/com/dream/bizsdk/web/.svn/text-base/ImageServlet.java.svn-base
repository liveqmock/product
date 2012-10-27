/*
 * ImageServlet.java
 *
 * Created on 2003年9月12日, 下午6:24
 */

package com.dream.bizsdk.web;

import com.dream.bizsdk.common.blc.IBLContext;
import com.dream.bizsdk.common.blc.BLResult;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.jpg.JPGGenerator;
import com.dream.bizsdk.common.util.RequestParams;
import com.dream.bizsdk.common.util.ParamNameException;
import com.dream.bizsdk.common.SysVar;

import javax.servlet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.*;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;

/**
 * This servlet should be started after the BLServlet servlet, othereise this
 * Servlet will not work properly;
 *
 * @author chuguanghua
 */
public class ImageServlet extends HttpServlet {

    public final static String PIE_CHART = "pie";
    public final static String PIE3D_CHART = "pie3d";
    public final static String HIS_CHART = "histogram";
    public final static String LINE_CHART = "line";
    public final static String JPG_WIDTH = "width";
    public final static String JPG_HEIGHT = "height";

    private String cp = "GBK";
    private IBLContext _context = null;
    private JPGGenerator _jg = new JPGGenerator();
    private RequestParams rp = new RequestParams();


    /**
     * Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //create temp dir here;
        try {
            ServletContext cxt = config.getServletContext();
            //this attribute has been set by the servlet BLServlet,so to make this servlet
            //work,the BLServlet must be started before this servlet;
            _context = (IBLContext) cxt.getAttribute("com.dream.bizsdk.BLCONTEXT");
            cp = _context.getConfigValue("system", "charset");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Initlization error!");
        }
    }

    /**
     * Destroys the servlet.
     */
    public void destroy() {
    }


    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Image(format JPEG) servlet.";
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        int width = 400;    //default chart width;
        int height = 300;   //default chart height;

        HttpSession session = request.getSession();

        /**process request parameters*/
        BizData rd = new BizData();
        processRequestParams(request, rd);
        width = ((Integer) rd.get(ImageServlet.JPG_WIDTH)).intValue();
        height = ((Integer) rd.get(ImageServlet.JPG_HEIGHT)).intValue();

        //get the data for the requested image;
        BizData imageData = getImageData(rd);
        if (imageData != null) {
            //make the JPG according to the ImageData
            JFreeChart chart = _jg.makeJPG(imageData);
            //send the jpeg to the client;
            writeJPG2Client(chart, width, height, response, session);
        } else {
            response.sendError(501);
        }
    }

    /**
     * get all the parameters from the given request and store them into the
     * BizData object.
     *
     * @param request the specified request object.
     * @param data    the Bizdata object in which all the params will be stored.
     */
    protected boolean processRequestParams(HttpServletRequest request, BizData data) throws UnsupportedEncodingException {
        boolean retVal = true;

        try {
            if (!rp.parse(request, cp, data)) {
                retVal = false;
            } else {
                String width = (String) data.get(ImageServlet.JPG_WIDTH);
                String height = (String) data.get(ImageServlet.JPG_HEIGHT);
                try {
                    if (width != null) {
                        Integer w = Integer.valueOf(width);
                        data.add(ImageServlet.JPG_WIDTH, w);
                    } else {
                        data.add(ImageServlet.JPG_WIDTH, new Integer(400));
                    }
                } catch (Exception e) {
                    data.add(ImageServlet.JPG_WIDTH, new Integer(400));
                }
                try {
                    if (height != null) {
                        Integer h = Integer.valueOf(height);
                        data.add(ImageServlet.JPG_HEIGHT, h);
                    } else {
                        data.add(ImageServlet.JPG_HEIGHT, new Integer(300));
                    }
                } catch (Exception e) {
                    data.add(ImageServlet.JPG_HEIGHT, new Integer(300));
                }
            }
        } catch (ParamNameException pne) {
            data.add(SysVar.ERROR_MESSAGE, pne.getMessage());
            return false;
        }
        return retVal;
    }

    private BizData getImageData(BizData rd) {
        try {
            /**call the ImageBLC.getImageData method*/
            BLResult br = _context.execBL("com.dream.app.sys.ImageBLC", "getImageData", rd, null);
            BizData imageData = (BizData) br.rd.get("imageData");
            return imageData;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Send a JFreeChar object to clients.
     *
     * @param chart    the JFreeChart object to be sent to the client;
     * @param width    the width of the chart;
     * @param height   the height of the chart;
     * @param response the image response to the client;
     * @param session  the current session object that issue this chart request;
     */
    protected void writeJPG2Client(JFreeChart chart, int width, int height, HttpServletResponse response,
                                   HttpSession session) {
        try {
            if (chart != null) {
                String fileName = ServletUtilities.saveChartAsJPEG(chart, width, height, session);
                ServletUtilities.sendTempFile(fileName, response);
            } else {
                response.sendError(404);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}