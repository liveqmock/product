<%@include file="/common.jsp" %>
<%@page import="java.io.File"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.io.FileOutputStream"%>

<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
	<%
				out.clear();
				response.setContentType("image/jpeg");
				response.addHeader("pragma", "NO-cache");
				response.addHeader("Cache-Control", "no-cache");
				response.addDateHeader("Expries", 0);
				Object obj = rd.get("TA_DJ_QRJs", "CONFIRM_PIC",0);
				oracle.sql.BLOB blob = null;
				blob = (oracle.sql.BLOB) obj;
				InputStream in=blob.getBinaryStream();
				byte [] buffer=new byte [6];
				in.read(buffer);
				int read=0;
				OutputStream writer = response.getOutputStream();
				while((read=in.read(buffer))!=-1){
					writer.write(buffer,0,read);
				}
				writer.close();
			%>  
</body>
</html>