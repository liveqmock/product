/**
 *all rights reserved,@copyright 2003
 */
package com.cots.uploader;

import com.cots.util.Base64;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletInputStream;
import java.util.*;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-12-18
 * Time: 10:47:19
 */
public class HttpUploaderRequest extends HttpServletRequestWrapper{

    private Hashtable parameters ;

    public HttpUploaderRequest(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
        int count;
        parameters = new Hashtable();
        try{
            ServletInputStream is = httpServletRequest.getInputStream();
            byte[] content = getContentBytes(is);
            MultiPart mp = new MultiPart(content);
            List normalParts = mp.getParts(false);
            count = normalParts.size();
            for(int i=0;i<count;i++){
                String paramName = ((Part)normalParts.get(i)).getName();
                String[] values = mp.getParameterValues(paramName,"GBK");
                if(values!=null){
                    parameters.put(paramName,values);
                }
            }

            List fileParts = mp.getParts(true);
            count = fileParts.size();
            for(int i=0;i<count;i++){
                String paramName = ((Part)fileParts.get(i)).getName();
                byte[] bytes = mp.getPart(paramName).getBytes();
                if(bytes.length>0){
                    String value = Base64.encodeBytes(bytes);
                    parameters.put(paramName,value);
                }
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public String getParameter(String name){
        Object o = parameters.get(name);
        if(o instanceof String[]){
            return ((String[])o)[0];
        }else{
            return (String)o;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public String[] getParameterValues(String name){
        Object o = parameters.get(name);
        if(o instanceof String[]){
            return (String[])o;
        }else{
            return new String[]{(String)o};
        }
    }

    /**
     *
     *
     * @return
     */
    public Enumeration getParameterNames(){
        return parameters.keys();
    }

    /**
     *
     * @return
     */
    public Map getParameterMap(){
        return parameters;
    }

    /**
     * get the content bytes from the underlying stream.
     *
     * @param is
     * @return
     * @throws IOException
     */
    private byte[] getContentBytes(ServletInputStream is) throws IOException{
        int read;
        byte[] buf = new byte[4096];
        ByteArrayOutputStream bos = new ByteArrayOutputStream(4096);
        try{
            while((read = is.read(buf))>=0){
                bos.write(buf,0,read);
            }
            return bos.toByteArray();
        }finally{
            try{
                bos.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}