<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="../../common.jsp"%>
<%@ taglib uri="/WEB-INF/tld/FCKeditor.tld" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>线路行程明细</title>

		<%
		Object obj = rd.get("TA_ZT_LINEDETAILs", "XCMX",0);
		Blob blob = null;
		int read=0;
		if(null != obj){
			blob = (Blob) obj;
			java.io.InputStream in=blob.getBinaryStream();
			InputStreamReader isr = new InputStreamReader(in, "GBK");
			char[] chars = new char[1];
			read = 0;
			while ((read = isr.read(chars)) != -1) {
				out.print(new String(chars));
			}
		}
		%>
	
</div>

</body>
</html>