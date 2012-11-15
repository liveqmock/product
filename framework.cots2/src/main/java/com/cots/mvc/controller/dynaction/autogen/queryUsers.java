package com.cots.mvc.controller.dynaction.autogen;


import com.cots.mvc.controller.DynaAction;
import com.cots.mvc.controller.AbstractAction;
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
import java.io.IOException;


public final class queryUsers extends AbstractAction implements DynaAction{
  private String name="queryUsers";
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
    int pages;
    __tmp=request.getParameter("pages");
    if(__tmp==null){
        pages=0;
    }else{
        pages=Integer.parseInt(__tmp);
    }
    int page;
    __tmp=request.getParameter("page");
    if(__tmp==null){
        page=0;
    }else{
        page=Integer.parseInt(__tmp);
    }
    com.cots.common.security.Manager blc=(com.cots.common.security.Manager)blcPool.getBLC("com.cots.common.security.Manager");
    //blc.queryUsers(user,page);
    request.setAttribute("user",user);
    //request.setAttribute("pages",pages);
    //request.setAttribute("page",page);
    javax.servlet.RequestDispatcher dispatcher;
    dispatcher=request.getRequestDispatcher("/user/UsersList.jsp");
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
