<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String groupID = rd.getStringByDI("bx_GuideInfo","ID",0).equals("")?rd.getString("tid"):rd.getStringByDI("TA_ZT_JHDJs","TID",0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>导游费用报账</title>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript">
function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		    event.returnValue=false;
	 		
}
function djGuideSub(){
	if(document.getElementById("tb-dyjtf").value==""){
		alert("导游交通费不能为空!");
		document.getElementById("tb-dyjtf").focus();
		return false;
	}
	if(document.getElementById("tb-dylk").value==""){
		alert("导游领款不能为空!");
		document.getElementById("tb-dylk").focus();
		return false;
		}
	if(document.getElementById("tb-dff").value==""){
			alert("导服费不能为空!");
			document.getElementById("tb-dff").focus();
			return false;
	}
	if(document.getElementById("tb-qt").value==""){
				alert("其他不能为空!或输入0。");
				document.getElementById("tb-qt").focus();
				return false;
		}else{
	document.GuideBxForm.submit();
	reload();
	}
}
function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}
function sunPrice(){
	var dyjtfhj=0;
	var dffhj=0;
	var qthj=0;
	var dyfyhj=0;
	jQuery(".dylk").each(function(i){
		dyjtfhj+=parseInt(jQuery(".dyjtf:eq("+i+")").val(),10);
		dffhj+=parseInt(jQuery(".dff:eq("+i+")").val(),10);
		qthj+=parseInt(jQuery(".qt:eq("+i+")").val(),10);
	});
	//导游费用合计= 导游交通费合计 + 导服费合计 + 其他费用合计
	dyfyhj=dyjtfhj + dffhj + qthj;
	jQuery(".dyfyhj").val(dyfyhj);
}
</script>
</head>

<body>
<form  action="ztEditGuideBx." name="GuideBxForm" method="post">
<div id="bm-table">
<div id="guideDiv">
<table id="guideDiv" class="datas table-info" width="98%" >
  <tr id="first-tr">
	<td width="20%" >导游</td>
	<td width="10%">导游实际领款</td>
	<td width="10%">交通费</td>
	<td width="10%">导服费</td>
	<td width="10%">其他</td>
	<td width="40%">备注</td>
  </tr>
  <%
  int dyRows = rd.getTableRowsCount("GuideInfo");
   	if(0 < dyRows){
   		out.print("<input type='hidden' name='state' value='Edit'>");
   	}
  	for(int i = 0;i < dyRows; i++){
  		String DYXM=rd.getString("GuideInfo","DYXM",i);
  		String DYID=rd.getString("GuideInfo","DYID",i);
  		String DYLK=rd.getString("GuideInfo","DYLK",i);if("".equals(DYLK)){ DYLK = "0";}
  		String DYJTF=rd.getString("GuideInfo","DYJTF",i);if("".equals(DYJTF)){ DYJTF = "0";}
  		String DFF=rd.getString("GuideInfo","DFF",i);if("".equals(DFF)){ DFF = "0";}
  		String QT=rd.getString("GuideInfo","QT",i);if("".equals(QT)){ QT = "0";}
  		String BZ=rd.getString("GuideInfo","BZ",i);if("".equals(BZ)){ BZ = " ";}
  %>
  <tr class="guideType">
  	<td>
		<%=DYXM %>
		<input type="hidden" name="TA_ZT_BXDY/DYID[<%=i%>]" id="dyxm" value="<%=DYID %>"/>
		<input type="hidden" name="TA_ZT_BXDY/DYXM[<%=i%>]" id="dyxm" value="<%=DYXM %>"/>
	</td>
	<td><input type="text" name="TA_ZT_BXDY/DYLK[<%=i%>]" value="<%=DYLK %>" id="tb-dylk" class="dylk smallerInput" onchange="sunPrice()" onkeydown="checkNum()"/></td>
	<td><input type="text" name="TA_ZT_BXDY/DYJTF[<%=i%>]" value="<%=DYJTF %>" id="tb-dyjtf" class="dyjtf smallerInput" onchange="sunPrice()" onkeydown="checkNum()"/></td>
	<td><input type="text" name="TA_ZT_BXDY/DFF[<%=i%>]" value="<%=DFF %>" id="tb-dff" class="dff smallerInput" onchange="sunPrice()" onkeydown="checkNum()"/></td>
	<td><input type="text" name="TA_ZT_BXDY/QT[<%=i%>]" value="<%=QT %>" id="tb-qt" class="qt smallerInput" onchange="sunPrice()" onkeydown="checkNum()"/></td>
	<td><input type="text" name="TA_ZT_BXDY/BZ[<%=i%>]" value="<%=BZ %>" class="smallerInput1"/></td>
  </tr>
  <%} %>
</table>

<%String DYFYHJ = rd.getString("TA_TDBXZJXXB_ZTs","DYFYHJ",0); if("".equals(DYFYHJ)){DYFYHJ="0";} %>
  <table class="datas">
	<tr>
	  <td width="20%" ><font color="red">导游费用合计：</font></td>
	  <td colspan="5" width="80%">
	  	<input type="text" name="TA_TDBXZJXXB_ZT/DYFYHJ" value="<%=DYFYHJ %>" class="dyfyhj smallerInput" readonly="readonly"/>
		<input type="hidden" name="TA_TDBXZJXXB_ZT/TID" value="<%=rd.getStringByDI("TA_ZT_BXDY","TID",0)%>"/>
	  </td>
  	</tr>
  </table>
</div>
</div>
<div align="center" id="last-sub">
	<input type="hidden" name="TA_ZT_BXDY/TID" value="<%=rd.getStringByDI("TA_ZT_BXDY","TID",0)%>"/>
	<input type="hidden" name="TA_ZT_BXDY/BXR" value="<%=sd.getString("userno") %>"/>
	<%String view = rd.getString("flag");  if(!"view".equals(view)){%>
	<input type="button" id="button" value="提交" onclick="djGuideSub();"/>
	<%} %>
	<input type="button" id="button" value="返回" onclick="reload();"/>
</div>
</form>
</body>
</html>