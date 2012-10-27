  <%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>编辑代码维护</title>
<link href="<%=request.getContextPath() %>/css/table.css" rel="stylesheet" type="text/css" />

<script language="javascript">
var adding=false;
var table;
function enableAllInput(name,rows){
	//var elements=document.getElementsByName(name);
	
	for(i=0;i<rows-1;i++)
	{
		
		document.getElementById(name+"["+i+"]").readOnly=false;
	}
}
function AddSignRow(){ 
 
	table = document.getElementById("contetTable");
	var rowID=table.rows.length;
	if(!adding){
		enableAllInput("DMSM/DMZ",rowID);
		enableAllInput("DMSM/DMSM1",rowID);
		enableAllInput("DMSM/DMSM2",rowID);
		adding=true;
	}
	//alert(rowID);
	//添加行
	
	var newTR = table.insertRow(table.rows.length);
	newTR.id = "contentRow" + rowID;
	if(rowID%2==0)
	newTR.className="STYLE18";
	else
	newTR.className="STYLE19";
	newTR.align="center";
	//添加列:复选框
	var newNameTD=newTR.insertCell(0);
	//添加列内容
	newNameTD.innerHTML = "<input value='" + rowID + "' type='checkbox'/>";
	//添加列:代码
	var newNameTD=newTR.insertCell(1);
	//添加列内容
	newNameTD.innerHTML = "<input name='DMSM/DMZ[" + rowID + "]' id='DMSM/DMZ[" + rowID + "]' type='text'/>";
	//添加列:说明1
	var newsm1=newTR.insertCell(2);
	//添加列内容
	newsm1.innerHTML = "<input name='DMSM/DMSM1[" + rowID + "]' id='DMSM/DMSM1[" + rowID + "]' type='text' />";

	//添加列:说明2
	var newsm2=newTR.insertCell(3);
	//添加列内容
	newsm2.innerHTML = "<input name='DMSM/DMSM2[" + rowID + "]' id='DMSM/DMSM2[" + rowID + "]' type='text'  />";

	//添加列：有效标记
	var newzt=newTR.insertCell(4);
	//添加列内容
	newzt.innerHTML = "<input type='radio' name='DMSM/YXBZ["+rowID+"]' id='DMSM/YXBZ[" + rowID + "]' value='1' checked='checked'/>有效 <input type='radio' name='DMSM/YXBZ[" + rowID + "]' id='DMSM/YXBZ[" + rowID + "]' value='2' />无效";
	}



function submitModify(){
	document.modifyDMSMForm.action="modifyDMSM.";
	document.modifyDMSMForm.submit();
}
	
function refreshParent(){

	window.dialogArguments.location.href="queryById.?dispMod=update&DMLB/DMLB=<%=rd.getStringByDI("DMLB","DMLB",0)%>&DMSM/DMLB=<%=rd.getStringByDI("DMLB","DMLB",0)%>";
	
}

function DeleteSignRow(){
	
	table = document.getElementById("contetTable");

	var flags=document.getElementsByName("flagCheckbox");

	for(i=flags.length-1;i>=0;i-- ){
		if(flags[i].checked==true){
			//alert(flags[i]);
			
			table.deleteRow(document.getElementById("contentRow"+flags[i].value).rowIndex);
			
		}
	}
	}


</script>
<base target="_self">
</head>

<body onunload="refreshParent();">
<form name="modifyDMSMForm" method="post">
<table width="98%" border="0" cellspacing="0" cellpadding="0"
	align="center">
	<tr>
		<td height="30" background="<%=request.getContextPath() %>/images/tab_05.gif">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="12" height="30"><img src="<%=request.getContextPath() %>/images/tab_03.gif"
					width="12" height="30" /></td>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="180" valign="middle">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10"><img src="<%=request.getContextPath() %>/images/tb.gif" width="16"
									height="16" /></td>
								<td><span class="STYLE3">编辑<font color="blue">[<%=rd.getStringByDI("DMLB","LBSM",0)%>]</font>的代码值</span></td>
							</tr>
						</table>
						</td>
						<td width="54%">
						<table border="0" align="right" cellpadding="0" cellspacing="0">
							<tr>
								<td width="60">
								<table width="90%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td><img src="<%=request.getContextPath() %>/images/22.gif" width="14" height="14" /></td>
										<td><a href="###" onclick="AddSignRow();"> 新增</a></td>
									</tr>
								</table>
								</td>

								<td width="72">
								<table width="88%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td><img src="<%=request.getContextPath() %>/images/11.gif" width="14" height="14" /></td>
										<td><a href="###" onclick="DeleteSignRow();">删除</a></td>
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
				<td height="340" valign="top">
				<table id="contetTable" width="100%" border="0" cellpadding="0"
					cellspacing="1" bgcolor="b5d6e6" align="center">
					<tr class="STYLE1" align="center">
						<td width="5%" height="22"><input type="hidden" name="DMLB/DMLB" value="<%=rd.getStringByDI("DMLB","DMLB",0) %>" /></td>
						<td width="20%" height="22">代码值</td>
						<td width="15%" height="22">说明1</td>
						<td width="15%" height="22">说明2</td>
						<td width="25%" height="22">有效标记</td>
					</tr>
					<%
         			int rows=rd.getTableRowsCount("DMSMs");
          			for(int i=0;i<rows;i++){
          			%>
					<tr id="contentRow<%=i %>" class="<%if(i%2==0) out.print("STYLE19");else out.print("STYLE18"); %>" align="center">
						<td><input type="checkbox" name="flagCheckbox" value="<%=i %>" /></td>
						<td height="20"><input type="text" name="DMSM/DMZ[<%=i %>]" id="DMZ" value="<%=rd.getStringByDI("DMSMs","DMZ",i) %>" /></td>
						<td><input type="text" name="DMSM/DMSM1[<%=i %>]" id="DMSM1" value="<%=rd.getStringByDI("DMSMs","DMSM1",i) %>" "readonly"/></td>
						<td height="20"><input type="text" name="DMSM/DMSM2[<%=i %>]" id="DMSM2" value="<%=rd.getStringByDI("DMSMs","DMSM2",i) %>" "readonly"/></td>
						<td height="20"><input type="radio" name="DMSM/YXBZ[<%=i %>]" id="DMSM/YXBZ[<%=i %>]" value="1"
							<%if(rd.getStringByDI("DMSMs","YXBZ",i).equals("1")) out.print("checked"); %> />有效<input
							type="radio" name="DMSM/YXBZ[<%=i %>]" id="DMSM/YXBZ[<%=i %>]" value="2"
							<%if(rd.getStringByDI("DMSMs","YXBZ",i).equals("2")) out.print("checked"); %> />无效
						</td>
					</tr>
					<% }%>
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
	<tr class="STYLE19">
		<td height="40" colspan="6" align="center" valign="bottom"><input
			type="button" class="btn" onclick="submitModify();"
			name="query" value="更新" />&nbsp;&nbsp; 
		<input type="button" class="btn" name="query" value="关闭"
			onclick="parent.window.close();" />&nbsp;&nbsp;</td>
	</tr>
</table>
</form>
</body>
</html>
