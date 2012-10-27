<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.IOException"%>
<%!
	boolean alwaysStackTrace=false;
	public void printStackTrace(Throwable t,JspWriter w){
	  try{
		w.write(t.getClass().getName());
		w.write("<br>");
		StackTraceElement[] e = t.getStackTrace();
		for(int i=0;i<e.length;i++){
			w.write("&nbsp;&nbsp;&nbsp;&nbsp");
			w.write("at&nbsp;");
			w.write(e[i].toString());
			w.write("<br>");
		}		
	  }catch(IOException ioe){
	  }
	}
%>
<%
	String errorNO="unknown";
	String errorMsg="";
	BizData errors = (BizData)application.getAttribute(SysVar.APP_ERRORS);
	BizData bizdata = (BizData)request.getAttribute(SysVar.REQ_DATA);
	//check if the bizdata is null to avoid null pointer exception;
	if(bizdata!=null){
		errorNO=bizdata.getString(SysVar.ERROR_NO);
	}
	if(errors!=null){
		errorMsg=errors.getString("DRMError","errMessage",errorNO);
	}
	String cMsg = bizdata.getString(SysVar.ERROR_MESSAGE);
	errorMsg+=cMsg;
	Throwable t = (Throwable)bizdata.get(SysVar.THROWN);
	String contextPath = request.getContextPath();
//	String at = request.getParameter("alwaysStackTrace");
//	if(at!=null && "true".equalsIgnoreCase(at)){
//		this.alwaysStackTrace = true;
//	}else{
//		this.alwaysStackTrace = false;
//	}
%>
<html>
<head>
<title>抱歉，发生错误</title>
<script type="text/JavaScript">
<!--
var toShow=true;
function showStackTrace(){
	if(toShow==true){
		stacktrace.style.display="inline";
		toShow=false;
	}else{
		stacktrace.style.display="none";
		toShow=true;
	}
}
//-->
</script>
<STYLE type=text/css>INPUT {
	FONT-SIZE: 12px
}
TD {
	FONT-SIZE: 12px
}
.p2 {
	FONT-SIZE: 12px
}
.p6 {
	FONT-SIZE: 12px; COLOR: #1b6ad8
}
A {
	COLOR: #1b6ad8; TEXT-DECORATION: none
}
A:hover {
	COLOR: red
}
</STYLE>
</head>
<body>
<P align=center>　</P>
<P align=center>　</P>
<TABLE cellSpacing=0 cellPadding=0 width=540 align=center border=0>
  <TBODY>
  <TR>
    <TD vAlign=top height=270>
      <DIV align=center><BR />
       <IMG height=211 src="<%=request.getContextPath() %>/images/error-img/error.gif" width=329 /><BR /><BR />
       <TABLE cellSpacing=0 cellPadding=0 width="80%" border=0>
        <TBODY>
       	 <TR>
          <TD>
            <FONT class=p2>&nbsp;&nbsp;&nbsp; 
          	 <FONT color=#ff0000><IMG height=13 src="<%=request.getContextPath() %>/images/error-img/emessage.gif" width=12 />&nbsp;非常抱歉，在执行过程中发生下面错误：</FONT>
            </FONT>
          </TD>
         </TR>
        <TR>
          <TD height=8></TD>
        </tr>
        <TR>
          <TD>
            <P><FONT color="red" style="font-size: 14px;">错误编号:<%=errorNO%></FONT></P>
            <P><FONT color="red" style="font-size: 14px;">错误信息:<%=errorMsg%></FONT></P>
          </TD>
        </TR>
      </TBODY>
      </TABLE>
     </DIV>
    </TD>
  </TR>
  <TR>
    <TD height=5></TD>
  </tr><TR>
    <TD align=middle>
      <CENTER>
      <TABLE cellSpacing=0 cellPadding=0 width=480 border=0>
        <TBODY>
        <TR>
          <TD width=6><IMG height=26 src="<%=request.getContextPath() %>/images/error-img/left.gif" 
width=7 /></TD>
          <TD background="<%=request.getContextPath() %>/images/error-img/bg.gif">
            <DIV align=center><FONT class=p6>  <A  href="javascript:history.go(-1)">返回出错页</A>　</FONT> </DIV></TD>
          <TD width=7><IMG 
      src="<%=request.getContextPath() %>/images/error-img/right.gif" /></TD></TR></TBODY></TABLE></CENTER></TD></TR></TBODY></TABLE>
<P align=center>　</P>
<P align=center>　</P>
  
  
</body>
</html>