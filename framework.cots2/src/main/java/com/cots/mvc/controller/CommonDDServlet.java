/**
 *All rights reserved by cots co. ltd.
 */
package com.cots.mvc.controller;

import com.cots.blc.BLContext;
import com.cots.dao.DAO;
import com.cots.bean.Bean;
import com.cots.bean.BeanPropertyException;
import com.cots.bean.BeanProperty;
import com.cots.bean.PrimitiveType;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.sql.SQLException;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

import org.apache.log4j.Logger;

/**
 * 本Servlet接受对单表的纪录插入，需该，删除和查询操作。
 * 客户端请求对一个单表增加一个记录的格式如下：
 *   beanName.ins
 * 其中beanName为该表对应的Java对象的注册名，".ins"为操
 * 作指示符，表示要对该表进行增加纪录操作。
 * 类似的,修改操作的格式如下：
 *   beanName.upd
 * 删除操作的格式如下：
 *   beanName,del;
 * 查询操作的格式如下：
 *   beanName.qry;
 * 应用程序可以在web.xml中修改这些操作指示符：".ins",
 * ".upd",".del",".qry"；同时需要修改页面上的请求URL.
 *
 * 该Servlet必须在ControllerServlet之后启动，否则将不能
 * 正常工作。
 *
 * User: chugh
 * Date: 2005-9-25
 * Time: 14:22:08
 */
public class CommonDDServlet extends HttpServlet{
    //insert operation suffix;
    private String insert_suffix = "ins";
    //delete operation suffix;
    private String delete_suffix = "del";
    //update operation suffix;
    private String update_suffix = "upd";
    //query operation suffix;
    private String query_suffix  = "qry";
    private BLContext context;

    /**
     * init this servlet. The operations suffix can be modified in the web.xml by
     * change the url-mapping pattern. Note that if the default url mapping is changed
     * then the new pattern should be configed as the init-param of this servlet.
     *
     * @param config
     * @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        String aString;
        aString = config.getInitParameter("insert_suffix");
        if(aString!=null && aString.length()>0){
            insert_suffix = aString;
        }
        aString = config.getInitParameter("query_suffix");
        if(aString!=null && aString.length()>0){
            query_suffix = aString;
        }
        aString = config.getInitParameter("delete_suffix");
        if(aString!=null && aString.length()>0){
            delete_suffix = aString;
        }
        aString = config.getInitParameter("update_suffix");
        if(aString!=null && aString.length()>0){
            update_suffix = aString;
        }

        context = (BLContext)config.getServletContext().getAttribute(ControllerServlet.CONTEXT);
        if(context == null){
            throw new ServletException("Can't access BLContex object in this application" +
                    ". Please  ensure that the ControllerServlet has been started before this servlet.");
        }
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        process(request,response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        process(request,response);
    }

    protected void process(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        String uri = request.getRequestURI();
        String beanName = getBeanName(uri);
        String operType = getOperType(uri);

        if(beanName== null || operType == null){
            System.out.println("The uri \""+uri+"\" is not a valid common DD request");
        }

        PrintWriter w = response.getWriter();
        try{
            if(insert_suffix.equalsIgnoreCase(operType)){
                String okCont = request.getParameter("okCont");
                if(okCont!=null){
                    //insert the bean;
                    insert(beanName,request);

                    //redirect the response;
                    String daoName = request.getParameter("dao");
                    if(daoName==null){
                        daoName="";
                    }
                    String contextPath = request.getContextPath();
                    contextPath += "/bean.jsp?bean="+beanName+"&dao="+daoName;
                    response.sendRedirect(contextPath);
                }else{
                    w.println(insert(beanName,request)+" rows inserted!");
                }
            }else if(update_suffix.equalsIgnoreCase(operType)){
                w.println(update(beanName,request)+" rows updated!");
            }else if(delete_suffix.equalsIgnoreCase(operType)){
                w.write(delete(beanName,request)+" rows deleted!");
            }else if(query_suffix.equalsIgnoreCase(operType)){
                w.println(query(beanName,request).size()+" rows selected.");
            }else{
                response.sendError(500,"Unknow operation:"+operType);
            }
        }catch(SQLException e){
            throw new ServletException(e);
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        } catch (InvocationTargetException e) {
            throw new ServletException(e.getTargetException());
        } catch (InstantiationException e) {
            throw new ServletException(e);
        } catch (BeanPropertyException e) {
            throw new ServletException(e);
        } catch (ParseException e) {
            throw new ServletException(e);
        }
    }

    /**
     * get the bean name from the uri which should be a Common DD request;
     *
     * @param uri
     * @return
     */
    protected String getBeanName(String uri){
        if(uri == null || uri.length()<1){
            return null;
        }

        int lastSplash = uri.lastIndexOf('/');
        int lastPeriod = uri.lastIndexOf('.');
        if(lastSplash < 0 || lastPeriod < 0  || lastSplash >= lastPeriod){
            return null;
        }else{
            return uri.substring(lastSplash+1,lastPeriod);
        }
    }

    protected String getOperType(String uri){
        if(uri == null || uri.length()<1){
            return null;
        }

        int lastPeriod = uri.lastIndexOf('.');
        if(lastPeriod < 0 ){
            return null;
        }else{
            return uri.substring(lastPeriod+1);
        }
    }

    /**
     * return count of rows inserted into the database.
     * -1 if the current user has no privilege to insert this record;
     * 
     * @param beanName
     * @param request
     * @return
     * @throws ServletException
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws BeanPropertyException
     */
    private int insert(String beanName,HttpServletRequest request)
            throws ServletException, SQLException, IllegalAccessException,
            InvocationTargetException, InstantiationException, BeanPropertyException{

//        HttpSession session = request.getSession(true);
//        String userID = (String)session.getAttribute(AAFilter.flagAttribute);
//        HashSet userRoles = (HashSet)session.getAttribute("userRoles");
//        Map userData = (Map)session.getAttribute("userData");

        DAO dao;
        Bean bean = context.getBeanFactory().getByName(beanName);
        if(bean == null){
            throw new ServletException("The bean \""+beanName+"\" was not regiestered.");
        }

        String daoName = request.getParameter("dao");
        if(daoName != null && daoName.trim().length()>0){
            dao = context.getDAO(daoName);
        }else{
            dao = context.getDAO();
        }
        if(dao == null){
            throw new ServletException("can't find dao named \""+daoName+"\"");
        }

        Object obj = bean.create(beanName,request);

        //if(canInsert(obj,userRoles){
//        String addWhere =
        return dao.insert(obj);
        //}else{
        //return    -1;
        //}
    }

    private int delete(String beanName,HttpServletRequest request)
            throws SQLException, ServletException, IllegalAccessException, InvocationTargetException,
            InstantiationException, BeanPropertyException{

        HttpSession session = request.getSession(true);
        String userID = (String)session.getAttribute(AAFilter.flagAttribute);
        HashSet userRoles = (HashSet)session.getAttribute("userRoles");
        Map userData = (Map)session.getAttribute("userData");

        DAO dao;
        Bean bean = context.getBeanFactory().getByName(beanName);
        if(bean == null){
            throw new ServletException("The bean \""+beanName+"\" was not registterd.");
        }

        String daoName = request.getParameter("dao");
        if(daoName != null){
            dao = context.getDAO(daoName);
        }else{
            dao = context.getDAO();
        }
        String addWhere = null;
        if(userID != null && userRoles!=null){
            addWhere = context.getPrivilManager().getDeletePrivil(userID,(String[])userRoles.toArray(new String[0]),
                    userData,bean.getTableName());
        }
        Object obj = bean.create(beanName,request);
        return dao.delete(obj,addWhere);
    }

    private int update(String beanName,HttpServletRequest request)
            throws SQLException, ServletException, IllegalAccessException, InvocationTargetException,
            InstantiationException, BeanPropertyException{
        HttpSession session = request.getSession(true);
        String userID = (String)session.getAttribute(AAFilter.flagAttribute);
        HashSet userRoles = (HashSet)session.getAttribute("userRoles");
        Map userData = (Map)session.getAttribute("userData");

        DAO dao;
        Bean bean = context.getBeanFactory().getByName(beanName);
        if(bean == null){
            throw new ServletException("The bean \""+beanName+"\" was not registterd.");
        }

        BeanProperty[] keys = bean.getKeyColumns();
        if(keys == null || keys.length<1){
            throw new ServletException("can't update a table without keys registered.");
        }

        String daoName = request.getParameter("dao");
        if(daoName != null){
            dao = context.getDAO(daoName);
        }else{
            dao = context.getDAO();
        }

        String addWhere = null;
        if(userID != null && userRoles!=null){
            addWhere = context.getPrivilManager().getUpdatePrivil(userID,(String[])userRoles.toArray(new String[0]),
                    userData,bean.getTableName());
        }

        Object obj = bean.create(beanName,request);
        return dao.update(obj,addWhere);
    }

    private List query(String beanName,HttpServletRequest request) throws ServletException,
            SQLException, ParseException{

        HttpSession session = request.getSession(true);
        String userID = (String)session.getAttribute(AAFilter.flagAttribute);
        Set userRoles = (Set)session.getAttribute("userRoles");
        Map userData = (Map)session.getAttribute("userData");

        DAO dao;
        Bean bean = context.getBeanFactory().getByName(beanName);
        if(bean == null){
             throw new ServletException("The bean \""+beanName+"\" was not registterd.");
        }

        String addWhere = null;
        if(userID != null && userRoles!=null){
            addWhere = context.getPrivilManager().getSelectPrivil(userID,(String[])userRoles.toArray(new String[0]),userData,bean.getTableName());
        }

        String daoName = request.getParameter("dao");
        if(daoName != null){
            dao = context.getDAO(daoName);
        }else{
            dao = context.getDAO();
        }

        BeanProperty[] bps = bean.getSetableColumns();

        StringBuffer sb = new StringBuffer(128);
        ArrayList values = new ArrayList();
        for(int i=0;i<bps.length;i++){
            String value = request.getParameter(beanName+"."+bps[i].getName());
            if(value!=null && value.length()>0){
                String op = request.getParameter(beanName+"."+bps[i].getName()+".op");
                if(op==null || op.length()<1 || op.equalsIgnoreCase("=")
                        || op.equalsIgnoreCase(">=") || op.equalsIgnoreCase("<=")){
                    if(sb.length()>0){
                        sb.append(" and ");
                    }
                    sb.append(bps[i].getColumnName()).append("=?");
                    values.add(PrimitiveType.create(bps[i].getType(),value));
                }else if(op.equalsIgnoreCase("like")){
                    if(sb.length()>0){
                        sb.append(" and ");
                    }
                    sb.append(bps[i].getColumnName()).append(" like ?");
                    values.add("%"+value+"%");
                }else if(op.equalsIgnoreCase("in")){
                    if(sb.length()>0){
                        sb.append(" and ");
                    }
                    sb.append(bps[i].getColumnName()).append(" in (");

                    String[] vs = request.getParameterValues(beanName+"."+bps[i].getName());
                    for(int j=0;i<vs.length;j++){
                        sb.append("?");
                        values.add(PrimitiveType.create(bps[i].getType(),vs[j]));
                        if(j<vs.length-1){
                            sb.append(",");
                        }
                    }
                    sb.append(" )");
                }else if(op.equalsIgnoreCase("between")){
                    if(sb.length()>0){
                        sb.append(" and ");
                    }
                    sb.append(bps[i].getColumnName());
                    String[] vs = request.getParameterValues(beanName+"."+bps[i].getName());
                    if(vs[0].length() > 0){
                        sb.append(">=? and ");
                        sb.append(bps[i].getName());
                        values.add(PrimitiveType.create(bps[i].getType(),vs[0]));
                    }
                    if(vs[1].length()>0){
                        sb.append("<=?");
                        values.add(PrimitiveType.create(bps[i].getType(),vs[1]));
                    }
                }
            }
        }

        if(addWhere!=null && addWhere.length()>0){
            if(sb.length()>0){
                sb.append(" and ");
            }
            sb.append("(").append(addWhere).append(")");
        }

        StringBuffer select = new StringBuffer(128);
        select.append("select ").append(bean.getColumnNamesString()).append(" from ");
        select.append(bean.getTableName()).append(" where ").append(sb);
        String sql = new String(select);
        Logger.getLogger(CommonDDServlet.class).debug("query sql: "+sql);
        Object[] params = values.toArray();
        return dao.executeQuery(bean.getBeanClass(),sql,params);
    }
}