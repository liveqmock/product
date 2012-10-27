<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%
BizData rd = (BizData) request.getAttribute(SysVar.REQ_DATA);
int returnVal = (Integer)rd.get(SysVar.RETURN_VALUE);
String returnInfo = rd.getString("newBaseInfo");
if(returnVal > 0){
	out.print(returnInfo);
	out.flush();
}
else
{
	out.print(returnInfo);
}
%>