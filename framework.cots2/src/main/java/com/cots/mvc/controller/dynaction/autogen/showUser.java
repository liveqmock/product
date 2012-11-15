package com.cots.mvc.controller.dynaction.autogen;


import com.cots.mvc.controller.DynaAction;
import com.cots.mvc.controller.AbstractAction;
import com.cots.mvc.controller.ControllerServlet;
import com.cots.bean.PrimitiveType;
import com.cots.blc.BLCPool;
import com.cots.blc.BLContext;
import com.cots.dao.DAO;
import com.cots.dao.TransactionLevel;
import com.cots.util.Base64;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import java.io.IOException;


public final class showUser extends AbstractAction implements DynaAction{
  private String name="showUser";
  private BLCPool blcPool;
  private BLContext context;
  private String input;
  public String getName() {
      return name;
  }
  public void setBLCPool(BLCPool pool) {
      blcPool = pool;
  }
  public void setBLContext(BLContext context) {
      this.context = context;
  }
  public void run(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
  DAO dao = null;
  try{
    String        __tmp    =null;
    Object        __value  =null;
    HashSet       __beanIDs=null;
    Enumeration   __names=null;
    int           __len    =0;
    String[]      __tmpValues    =null;
    StringBuffer __tmpUri        =null;
    Iterator      __it        =null;
    HttpSession session=request.getSession();
    ServletContext application=session.getServletContext();
    String op;
    __tmp=request.getParameter("op");
    op=__tmp;
    com.cots.mvc.controller.access.User user=new com.cots.mvc.controller.access.User();
    __value = PrimitiveType.create("String",request.getParameter("user.type"));
    if(__value!=null){
       user.setType((String)__value);
    }
    __value = PrimitiveType.create("String",request.getParameter("user.id"));
    if(__value!=null){
       user.setId((String)__value);
    }
    __value = PrimitiveType.create("String",request.getParameter("user.displayName"));
    if(__value!=null){
       user.setName((String)__value);
    }
    __value = PrimitiveType.create("String",request.getParameter("user.password"));
    if(__value!=null){
       user.setPassword((String)__value);
    }
    String beanName;
    __tmp=request.getParameter("beanName");
    beanName=__tmp;
    java.util.Set userRoles=null;
    java.util.List roles=null;
    List errors = new ArrayList(8);
    if(!(op==null || op.length()<1)){
        errors.add("必须指定显示方式为update或者new");
    }
    if(!("update".equals(op)&&(user==null||user.getId()==null))){
        errors.add("当op指定为更新(update)时,必须指定用户编号(user.id)");
    }
    if(errors.size()>0){
        request.setAttribute(ERRORS,errors);
        RequestDispatcher errorForw = request.getRequestDispatcher(ControllerServlet.ERROR_FORWARD);
        errorForw.forward(request,response);
        return;
     }
    if(op.equals("update")){
    com.cots.common.DBBLC blc=(com.cots.common.DBBLC)blcPool.getBLC("com.cots.common.DBBLC");
    blc.expand(user);
    }
    if(op.equals("update")){
    com.cots.common.security.Manager blc=(com.cots.common.security.Manager)blcPool.getBLC("com.cots.common.security.Manager");
    userRoles=blc.queryUserRoles(user.getId());
    }
    com.cots.common.DBBLC blc=(com.cots.common.DBBLC)blcPool.getBLC("com.cots.common.DBBLC");
    roles=blc.queryAll(beanName);
    request.setAttribute("op",op);
    request.setAttribute("userID",user.getId());
    request.setAttribute("user",user);
    request.setAttribute("beanName",beanName);
    request.setAttribute("userRoles",userRoles);
    request.setAttribute("roles",roles);
    javax.servlet.RequestDispatcher dispatcher;
    dispatcher=request.getRequestDispatcher("/user/User.jsp");
    dispatcher.forward(request,response);
  }catch(Throwable t){
     if(dao!=null){
        try{
          dao.rollback();
        }catch(Exception e){}
     }     throw new ServletException(t);
  }
  }
}
