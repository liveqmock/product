<%@ page contentType="text/plain; charset=GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%
BizData rd = (BizData) request.getAttribute(SysVar.REQ_DATA) ;
BizData sd = (BizData) session.getAttribute(SysVar.SS_DATA);

if (sd == null) {
	String loginPage = SysVar.LOGINPAGE.replaceAll(
			SysVar.CONTEXT_PATH, request.getContextPath());
	response.sendRedirect(loginPage);
}
if (rd == null) {
	rd = new BizData();
}
out.print("[{\"name\":\""+rd.getStringByDI("hotelInfo","HOTEL_BUSSINESS",0)+"\",\"phone\":\""+rd.getStringByDI("hotelInfo","HOTEL_BUSSINESS_PHONE",0)+"\"}]");
out.flush();
%>