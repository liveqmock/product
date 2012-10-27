<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%
	String nextPage=request.getParameter("nextPage");
	//create request data object
	BizData rd = new BizData();
	request.setAttribute(SysVar.REQ_DATA,rd);
	RequestDispatcher disp = request.getSession().getServletContext().getRequestDispatcher(nextPage);
	disp.forward(request,response);	
%>