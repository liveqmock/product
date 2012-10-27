<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<title>行政区划</title>
</head>
	<frameset  cols="210,*" framespacing="0" frameborder="no" >
  		<frame id="region_left" name="region_left" src="<%=request.getContextPath()%>/baseMng/xzqh/showTree.?XZQH/ID=" scrolling="auto"></frame>
  		<frame id="region_right" name="region_right" src="<%=request.getContextPath()%>/baseMng/rightInitPage.jsp" scrolling="auto"></frame>
  	</frameset>
</html>