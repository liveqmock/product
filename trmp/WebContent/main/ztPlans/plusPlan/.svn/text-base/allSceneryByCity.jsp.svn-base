<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
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
%>

<%
int rows = rd.getTableRowsCount("TA_SCENERY_POINTs");
if(rows>0){
%>
景点名称：
<%
for(int i=0;i<rows;i++){
	String cityID = rd.getStringByDI("TA_SCENERY_POINTs","CITY_ID",i);
	String sceneryName = rd.getStringByDI("TA_SCENERY_POINTs","cmpny_name",i);
	String sceneryID = rd.getStringByDI("TA_SCENERY_POINTs","scenery_id",i);
%>
 
	
	  	 <input type="hidden" name="TA_ZT_JHJIADJD<%=cityID %>/JDID[<%=i%>]" value="<%=sceneryID %>"/>
	  	 <input type="hidden" name="TA_ZT_JHJIADJD<%=cityID %>/JDMC[<%=i%>]" value="<%=sceneryName %>"/>
	  	 <input type="checkbox" name="TA_ZT_JHJIADJD<%=cityID %>/ISCHECK[<%=i%>]" value="Y" /><font color="red"><%=sceneryName %></font>
	
<%
}}else{
%>
<font color="red">当前地区无购物点,请重新选择。</font>
<%}%>