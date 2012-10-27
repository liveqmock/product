<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.text.DecimalFormat"%>
<%
	DecimalFormat decf = new DecimalFormat("0000");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>代码维护</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/table.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<script language="javascript">
function refreshTree(){

	window.parent.zd_left.location="queryZd.?DMLB/DMLB=&DMLB@orderBy=DMLB";
}
function submitNewDMLB(){	
	if(document.getElementById("dmlb").value!=""&&document.getElementById("lbsm").value!=""){
	document.newDMLB.action="newDMLB."; 
	document.newDMLB.submit();
	}
}

function submitDelDMLB(){
	if(document.getElementById("dmlb").value!=""&&document.getElementById("lbsm").value!=""){
		if(confirm("删除操作将会同时删除下面的具体代码，是否确定继续执行？")){
		document.newDMLB.action="deleteDMLB.?dispMod=update&DMSM/DMLB=<%=rd.getStringByDI("DMLBs", "DMLB", 0)%>";
		document.newDMLB.submit();
		}
		}
}
function submitUpdateDMLB(){
	if(document.getElementById("dmlb").value!=""&&document.getElementById("lbsm").value!="")
	{
	document.newDMLB.action="updateDMLB.?dispMod=update";
	document.newDMLB.submit();
	}
}

function showdmwh(){
	if(document.getElementById("dmlb").value!=""&&document.getElementById("lbsm").value!=""){
		TB_show('代码维护','loadDMSM.?DMLB/DMLB=<%=rd.getString("DMLBs", "DMLB", 0)%>&DMSM/DMLB=<%=rd.getString("DMLBs", "DMLB", 0)%>*TB_iframe=true&height=450&width=700','');
	}
}

</script>

	

</head>

<body 
	<%if (rd.getString("dispMod").equals("update"))
				out.print("onload=\"refreshTree();\"");%> >
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	align="center" >
	<tr>
		<td height="30" background="<%=request.getContextPath() %>/images/tab_05.gif">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="12" height="30"><img src="<%=request.getContextPath() %>/images/tab_03.gif"
					width="12" height="30" /></td>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="46%" valign="middle">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20"><img src="<%=request.getContextPath() %>/images/tb.gif" width="16"
									height="16" /></td>
								<td><span class="STYLE3">代码维护窗口</span></td>
							</tr>
						</table>
						</td>
						<td width="54%">
						<table border="0" align="right" cellpadding="0" cellspacing="0">
							<tr>
								<td width="120">
								<table width="90%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td><a href="getMaxId.?action=new&disable=disable"><img
											src="<%=request.getContextPath() %>/images/22.gif" width="14" height="14" border="0" />&nbsp;<font
											color="red">添加代码类别</font></a></td>
									</tr>
								</table>
								</td>

							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
				<td width="16"><img src="<%=request.getContextPath() %>/images/tab_07.gif" width="16"
					height="30" /></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="8" background="<%=request.getContextPath() %>/images/tab_12.gif">&nbsp;</td>
				<td height="400" valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" align="center">
					<tr class="STYLE19">
						<td height="40" colspan="5" align="right">
						<form name="newDMLB" method="post">
						<table align="center">
							<tr>
								<td height="40">代码类别编码：<input name="DMLB/DMLB" type="text" id="dmlb"
									class="input_txt" size="10" readonly
									value="<%=rd.getStringByDI("DMLB", "DMLB", 0) %>" />
									<input type="hidden" name="DMLB/DMLB[0]@oldValue" value="<%=rd.getStringByDI("DMLB", "DMLB", 0) %>"/>
									&nbsp;&nbsp;
								代码类别名称：<input name="DMLB/LBSM" type="text" class="input_txt"
									size="10" value="<%=rd.getStringByDI("DMLBs", "LBSM", 0)%>" id="lbsm"/></td>
							</tr>
							<tr>
							<td align="center">
								有效标记：	
								<input type='radio' name="DMLB/YXBJ"  value="1"  <%if(rd.getStringByDI("DMLBs","YXBJ",0).equals("1")) out.print("checked"); %>/>有效
								<input type='radio' name="DMLB/YXBJ"  value="2" <%if(rd.getStringByDI("DMLBs","YXBJ",0).equals("2")) out.print("checked"); %> />无效
							</td>
							</tr>
							
							<tr align="center">
								<td height="40"><input type="button" class="btn" name="query" value="更 新" onclick="<% if (rd.getString("action").equals("new"))
									out.print("submitNewDMLB();");
								else 
									out.print("submitUpdateDMLB();");%>" />&nbsp;&nbsp;
								<input type="button" class="btn" name="query" value="删 除" onclick="submitDelDMLB();" />&nbsp;&nbsp;
								<input type="button" class="btn" name="dmwh" value="代码维护" onclick="showdmwh();"  />								 								 
								</td>
							</tr>
						</table>
						</form>
						</td>
					</tr>
					<tr class="STYLE1" align="center">
						<td width="25%" height="22">代码</td>
						<td width="25%" height="22">说明1</td>
						<td width="25%" height="22">说明2</td>
						<td width="25%" height="22">状态</td>
					</tr>
					<%
						int rows = rd.getTableRowsCount("DMSMs");
						for (int i = 0; i < rows; i++) {
					%>
					<tr class="<%if (i % 2 == 0) out.print("STYLE19"); else out.print("STYLE18");%>" align="center">
						<td><%=rd.getStringByDI("DMSMs", "DMZ", i)%></td>
						<td><%=rd.getStringByDI("DMSMs", "DMSM1", i)%></td>
						<td><%=rd.getStringByDI("DMSMs", "DMSM2", i)%></td>
						<td><% if("1".equals(rd.getStringByDI("DMSMs", "YXBZ", i)))out.print("有效");else out.print("无效");%></td>
					</tr>
					<%
						}
					%>

				</table>
				</td>
				<td width="8" background="<%=request.getContextPath() %>/images/tab_15.gif">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td height="35" background="<%=request.getContextPath() %>/images/tab_19.gif">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="12" height="35"><img src="<%=request.getContextPath() %>/images/tab_18.gif"
					width="12" height="35" /></td>
				<td>&nbsp;</td>
				<td width="16"><img src="<%=request.getContextPath() %>/images/tab_20.gif" width="16"
					height="35" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
