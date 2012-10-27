<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="com.trmdj.businessPlan.groupMng.GroupLineDetail"%>
<%@include file="/common.jsp"%>
<%
String roleID = sd.getString("USERROLEST");
boolean isTrue = false;
if(!"".equals(roleID)){
	
	roleID = roleID.substring(1,roleID.length()-1);
	String[] roleIDs = roleID.split(",");
	for(int i=0;i<roleIDs.length;i++){
		if("admin".equals(roleIDs[i].trim()) || "transferForDj".equals(roleIDs[i].trim())){
			isTrue = true;
			break;
		}
	}
}
String canEdit = rd.getString("canEdit");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>

<script type="text/javascript">
	//jQuery(document).ready(function(){
	window.onload = function(){
		<%
		int XCMXRow = rd.getTableRowsCount("groupDetailInfo");
		if(XCMXRow >0){
		for(int i = 0; i<XCMXRow; i++){
		%>
			
	    	CKEDITOR.replace('XCMX<%=i%>');
	        CKEDITOR.config.height =80;
	        CKEDITOR.config.width =550;
	        CKEDITOR.config.toolbarStartupExpanded = false;
	        CKEDITOR.config.resize_enabled = false;
	    <%
	    }}
	    %>
	};

	function add_XCMX(){
		var QJBL = Math.floor(Math.random()*1024);
		var XCMXRows = parseInt(jQuery(".XCMXType").size());
			  jQuery("#store1").append('<tr class="XCMXType">'+
					  '<td><font size="6">D'+(XCMXRows+1)+'</font><input type="hidden" name="TA_DJ_LINEDETAI4G/RQ['+XCMXRows+']" value="D'+(XCMXRows+1)+'"/></td>'+
					      '<td>'+
					       	 '<textarea id="XCMX'+QJBL+'" name="TA_DJ_LINEDETAI4G/XCMX['+XCMXRows+']" rows="0" cols="0">'+
					   	 	'</textarea>'+ 
					       '</td>'+
					       '<td align="center">'+
					        '<input type="checkbox" name="TA_DJ_LINEDETAI4G/BREAKFAST['+XCMXRows+']" value="Y"/>早</br>'+
					       	'<input type="checkbox" name="TA_DJ_LINEDETAI4G/CMEAL['+XCMXRows+']" value="Y"/>中</br>'+
					       	'<input type="checkbox" name="TA_DJ_LINEDETAI4G/SUPPER['+XCMXRows+']" value="Y"/>晚</br>'+
					       '</td>'+
					      '<td align="center"><input type="text" name="TA_DJ_LINEDETAI4G/ZS['+XCMXRows+']" value="" class="smallerInput"/></td>'+
					   '</tr>');
					CKEDITOR.replace('XCMX'+QJBL+'');
					CKEDITOR.config.height =80;
			        CKEDITOR.config.width =550;
			        CKEDITOR.config.toolbarStartupExpanded = false;
			        CKEDITOR.config.resize_enabled = false;
	}
	function del_XCMX(){
		var tableRows = jQuery(".XCMXType").size();
		if(tableRows -= 1 > 1){
			var idx=parseInt(jQuery("tr.XCMXType").size()-1);
			jQuery("tr.XCMXType:eq("+idx+")").remove();
		}
	}

function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		    event.returnValue=false;
}

function addTrave(){
	var tztsRows = jQuery(".traveType").size();
	jQuery("#listStore").append(
		'<tr class="traveType">'+
		'<td>'+
			'<select name="TA_DJ_TZTS/SFID['+tztsRows+']" id="TA_DJ_TZTS/SFID'+tztsRows+'" USEDATA="dataSrc'+tztsRows+'" SUBCLASS="1" onchange="chageKYD(this.value,'+tztsRows+');"></select></br>'+
			'<select name="TA_DJ_TZTS/CSID['+tztsRows+']" id="TA_DJ_TZTS/CSID'+tztsRows+'" USEDATA="dataSrc'+tztsRows+'" SUBCLASS="2"></select></br>'+
			'<select name="TA_DJ_TZTS/ZTSID['+tztsRows+']" id="TA_DJ_TZTS/ZTSID'+tztsRows+'" USEDATA="dataSrc'+tztsRows+'" SUBCLASS="3"></select></br>'+
			'</td>'+
			'<td>成人：<input type="text" name="TA_DJ_TZTS/CRRS['+tztsRows+']" value="0" class="smallInput crrs" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="3"/><br>'+
			'儿童：<input type="text" name="TA_DJ_TZTS/ETRS['+tztsRows+']" value="0" class="smallInput etrs" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="3"/></td>'+
		'<td ><input type="text" name="TA_DJ_TZTS/YSZK['+tztsRows+']" value="0" class="smallInput yskzj" onchange="sumPrice()" onkeydown="checkNum()" maxlength="6"/></td>'+
		'<td ><input type="text" name="TA_DJ_TZTS/YSKQDJE['+tztsRows+']" value="0" class="smallInput yskqd" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="6"/></td>'+
		'<td ><input type="text" name="TA_DJ_TZTS/YSKXFJE['+tztsRows+']" value="0" class="smallInput yskxf" onkeydown="checkNum()" maxlength="6" readonly="readonly"/></td>'+
		 '<td><select name="TA_DJ_TZTS/KYD['+tztsRows+']" class="kyd">'+
			  		<%
			  			for(int i=0;i<rd.getTableRowsCount("XZQHs");i++){
			  				String XZQHID = rd.getStringByDI("XZQHs","ID",i);
			  				String XZQHPNAME = rd.getStringByDI("XZQHs","NAME",i);
			  		%>
			  			'<option value="<%=XZQHID %>"><%=XZQHPNAME %></option>'+
			  		<%		
			  			}
			  		%>
			  	  '</select></td>'+
	 '</tr>');
	
	var linkage = new Linkage("dataSrc"+tztsRows, "<%=request.getContextPath()%>/main/data/Traved.xml");
	linkage.init();
	tztsRows++;
}
function delTraveRow(){
	var tztsRows = jQuery(".traveType").size();
	if(tztsRows > 0){
		var idx = tztsRows-1;
		jQuery("tr.traveType:eq("+idx+")").remove();
		sumPrice();
	}
}
function tableMove(){
	var tableRows = jQuery(".valueType").size();
	jQuery("#store").append('<tr class="valueType">'+
			'<td>'+
			  '<select name="TA_DJ_TDJG/JGLX['+tableRows+']">'+
			  <%
			  int typeRows = rd.getTableRowsCount("JGLX");
			  for(int j=0;j<typeRows;j++){
				  String dmz = rd.getStringByDI("JGLX","dmz",j);
				  String dmsm = rd.getStringByDI("JGLX","dmsm1",j);
			  %>
			    '<option value="<%=dmz %>"><%=dmsm %></option>'+
			  <%
			  }%>
			  
			  '</select>'+
			'</td>'+
			'<td><input name="TA_DJ_TDJG/JG['+tableRows+']" value="0" onkeydown="checkNum()" class="smallerInput priceRow" id="price_th[0]" maxlength="5"></input></td>'+
			'<td><input name="TA_DJ_TDJG/DFC['+tableRows+']" value="0"  onkeydown="checkNum()" class="smallerInput" maxlength="5"/></td>'+
			'<td >'+
			  '<textarea name="TA_DJ_TDJG/bz['+tableRows+']" rows="2" cols="54" onpropertychange="if(this.value.length>100){this.value=this.value.slice(0,100)}"></textarea>'+
			'</td>'+
		  '</tr>');
}

function delTabRow(){
	var tableRows = jQuery(".valueType").size();
	if(tableRows > 0){
		var idx = tableRows-1;
		jQuery("tr.valueType:eq("+idx+")").remove();
	}
}
function sumPrice(){
	
	var crrs=0;
	var etrs=0;
	var yskzj=0;
	var ysk=0;
	var yskqd=0;
	
	jQuery(".crrs").each(function(i){
		crrs+=parseInt(jQuery(".crrs:eq("+i+")").val(),10);
		etrs+=parseInt(jQuery(".etrs:eq("+i+")").val(),10);
		yskzj+=parseInt(jQuery(".yskzj:eq("+i+")").val(),10);
		ysk=jQuery(".yskzj:eq("+i+")").val();
		yskqd=jQuery(".yskqd:eq("+i+")").val();
		
		if(""==yskqd){
			jQuery(".yskqd:eq("+i+")").val(ysk);
			yskqd=ysk;
			jQuery(".yskxf:eq("+i+")").val(ysk-yskqd);
		}

		if(0==yskqd){
			jQuery(".yskxf:eq("+i+")").val(ysk-yskqd);
		}
		
		if(""!=yskqd && parseFloat(yskqd)<=parseFloat(ysk)){
			jQuery(".yskqd:eq("+i+")").val(yskqd);
			jQuery(".yskxf:eq("+i+")").val(ysk-yskqd);
		}

		if(""!=yskqd && parseFloat(yskqd)>parseFloat(ysk)){
			jQuery(".yskqd:eq("+i+")").val(ysk);
			yskqd=ysk;
			jQuery(".yskxf:eq("+i+")").val(ysk-yskqd);
		}
	});
	jQuery("#crrszj").val(crrs);
	jQuery("#etrszj").val(etrs);
	jQuery("#yskzjs").val(yskzj);

}

function submitUpdateGroup(){
	var xlmc=jQuery("#xlmc").val();
	if(''==xlmc){
		alert("请输入线路名称");
		return false;
	}
	
	var ts=jQuery("#dayCounts").val();
	if(''==ts || 0==ts){
		alert("请输入旅游天数");
		return false;
	}
	
	var bDate = jQuery("#bDate").val();
	var eDate = jQuery("#eDate").val();
	if(bDate == '' || eDate == ''){
		alert("请输入团开始时间或结束时间！");
		return false;
	}

	var ztsSize = jQuery(".traveType").size();
	for(var i=0;i<ztsSize;i++){

		var zts = jQuery("#ZTSID"+parseInt(ztsSize-1)).val();
		if('' == zts){
			alert("请选择组团社！");
			return false;
		}
	}
	document.djUpdateGroupInfo.submit();
	reload();
}
function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}
function chageKYD(tab,tad){
	jQuery(".kyd:eq("+tad+")").get(0).value=tab;
}
</script>



<title>修改团信息</title>
</head>

<body >
<form action="djUpdateGroupInfo." name="djUpdateGroupInfo" method="post">
<div class="width-98">

<div id="list-table">

<table class="datas"  id="listStore">
 <tr id="first-tr">
	<td width="15%">组&nbsp;团&nbsp;社</td>
    <td width="10%">人&nbsp;&nbsp;&nbsp;&nbsp;数</td>
	<td width="10%">预算款</td>
	<td width="10%">预算款签单金额</td>
	<td width="10%">预算款现付金额</td>
	<td width="8%">客&nbsp;源&nbsp;地</td>
  </tr>
  <%
  int TraveRows = rd.getTableRowsCount("TA_DJ_TZTSs");
  for(int i=0;i<TraveRows;i++){
	  String SFID=rd.getStringByDI("TA_DJ_TZTSs","SFID",i);
      String CSID=rd.getStringByDI("TA_DJ_TZTSs","CSID",i);
      String ZTSID=rd.getStringByDI("TA_DJ_TZTSs","ZTSID",i);
      String YSZK=rd.getStringByDI("TA_DJ_TZTSs","YSZK",i);
      String CRRS=rd.getStringByDI("TA_DJ_TZTSs","CRRS",i);
      String ETRS=rd.getStringByDI("TA_DJ_TZTSs","ETRS",i);
      String KYD=rd.getStringByDI("TA_DJ_TZTSs","KYD",i);
      
      String YSKQDJE=rd.getStringByDI("TA_DJ_TZTSs","YSKQDJE",i);
      String YSKXFJE=rd.getStringByDI("TA_DJ_TZTSs","YSKXFJE",i);
  %>
  <tr class="traveType">
	<td>
	  <select name="TA_DJ_TZTS/SFID[<%=i %>]" id="TA_DJ_TZTS/SFID[<%=i %>]"  USEDATA="dataSrc<%=i %>" SUBCLASS="1" onchange="chageKYD(this.value,<%=i %>);"></select></br>
	  <select name="TA_DJ_TZTS/CSID[<%=i %>]" id="TA_DJ_TZTS/CSID[<%=i %>]" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select></br>
	  <select name="TA_DJ_TZTS/ZTSID[<%=i %>]"  id="TA_DJ_TZTS/ZTSID[<%=i %>]" USEDATA="dataSrc<%=i %>" SUBCLASS="3"></select></br>	
	</td>
	<td>成人：<input type="text" name="TA_DJ_TZTS/CRRS[<%=i %>]" value="<%=CRRS %>" class="smallInput crrs" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="3"/><br>
		儿童：<input type="text" name="TA_DJ_TZTS/ETRS[<%=i %>]" value="<%=ETRS %>" class="smallInput etrs" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="3"/></td>
	<td ><input type="text" name="TA_DJ_TZTS/YSZK[<%=i %>]" value="<%=YSZK %>" class="smallInput yskzj" onchange="sumPrice()" onkeydown="checkNum()" maxlength="6"/></td>
	<td ><input type="text" name="TA_DJ_TZTS/YSKQDJE[<%=i %>]" value="<%=YSKQDJE %>" class="smallInput yskqd" onchange="sumPrice()"  onkeydown="checkNum()" maxlength="6"/></td>
	<td ><input type="text" name="TA_DJ_TZTS/YSKXFJE[<%=i %>]" value="<%=YSKXFJE %>" class="smallInput yskxf" onkeydown="checkNum()" maxlength="6" readonly="readonly"/></td>
	<td>
		<select name="TA_DJ_TZTS/KYD[<%=i %>]" class="kyd">
		<%
		for(int j=0;j<rd.getTableRowsCount("XZQHs");j++){
			String XZQHID = rd.getStringByDI("XZQHs","ID",j);
			String XZQHPNAME = rd.getStringByDI("XZQHs","NAME",j);
		%>
			<option value="<%=XZQHID %>" <%if(XZQHID.equals(KYD)) { %>selected="selected" <%} %> ><%=XZQHPNAME %></option>
		<%		
		}
		%>
		</select>
	</td>
		</tr>
		<%} %>
</table>
</div>
<div id="list-table">
<table  class="datas">
	<tr>
		<td colspan="11" align="right">
			<font color="red">成人总人数：</font><input name="TA_DJ_GROUP/CRRS"  type="text" value="<%="".equals(rd.getStringByDI("TA_DJ_GROUPs","CRRS",0))?0:rd.getStringByDI("TA_DJ_GROUPs","CRRS",0)%>"  readonly="readonly" class="smallInput" id="crrszj"/>人&nbsp;&nbsp;
			<font color="red">儿童总人数：</font><input name="TA_DJ_GROUP/ETRS"  type="text" value="<%="".equals(rd.getStringByDI("TA_DJ_GROUPs","ETRS",0))?0:rd.getStringByDI("TA_DJ_GROUPs","ETRS",0)%>"  readonly="readonly"  class="smallInput" id="etrszj"/>人&nbsp;&nbsp;
			<font color="red">应收账款总计：</font><input name="TA_DJ_GROUP/YSKZJ" type="text" value="<%="".equals(rd.getStringByDI("TA_DJ_GROUPs","YSKZJ",0))?0:rd.getStringByDI("TA_DJ_GROUPs","YSKZJ",0)%>"  readonly="readonly" class="smallInput" id="yskzjs"/>/元&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
</div>
 <div class="add-table"> 
	<table border="0">
	  <tr >
		<td align="right" width="13%">线路名称：</td>
  		<td align="center" colspan="3" width="55%">
  			<input type="hidden" name="TA_DJ_GROUP/XLID" value="<%=rd.getStringByDI("TA_DJ_LINEMNGs","XLID",0) %>"/>
		  	<%=rd.getStringByDI("TA_DJ_LINEMNGs","XLMC",0)%>
		  
	  	</td>
      	<td align="right" width="10%">团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
   	  	<td width="12%">
   	  	  <%=rd.getStringByDI("TA_DJ_GROUPs","ID",0)%>
  	    </td>
	  </tr>
	  <tr>
  	  	<td  align="right">出发交通：</td>
        <td >
	  		<%
	  		String CFJTGJ = rd.getStringByDI("TA_DJ_GROUPs","B_TRAFFIC",0);
	  		for(int i=0;i<rd.getTableRowsCount("JTGJ");i++) {
   		    	String dmz = rd.getStringByDI("JTGJ","dmz",i);
   		    	if(dmz.equals(CFJTGJ)){out.print(rd.getStringByDI("JTGJ","dmsm1",i));}
	  		}%>
	  	</td>
	  	<td align="right">返回交通：</td>
       	 <td >
   		  <%
	  		String FHJTGJ = rd.getStringByDI("TA_DJ_GROUPs","E_TRAFFIC",0);
	  		for(int i=0;i<rd.getTableRowsCount("JTGJ");i++) {
   		    	String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  		if(dmz.equals(FHJTGJ)){out.print(rd.getStringByDI("JTGJ","dmsm1",i));}
	  		}
	  		%>
   		</td>
   		  <td align="right">用车类型：</td>
 		<td >
        	
	  		<%
	  		String VEHTYPE = rd.getStringByDI("TA_DJ_GROUPs","VEHTYPE",0);
	  		for(int i=0;i<rd.getTableRowsCount("CLLX");i++) {
   		    	String dmz = rd.getStringByDI("CLLX","dmz",i);
   		    	if(dmz.equals(VEHTYPE)){out.print(rd.getStringByDI("CLLX","dmsm1",i));}
	  		}
	  		%>
	  	
        </td>	 
   	
	  </tr>
	   <tr>
		 <td  align="right">开始时间：</td>
        <td >
       	 <%=rd.getStringByDI("TA_DJ_GROUPs","BEGIN_DATE",0) %>
        </td>
        <td align="right">结束时间：</td>
        <td >
        	<%=rd.getStringByDI("TA_DJ_GROUPs","END_DATE",0) %>
        </td>
  		<td  align="right">天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：</td>
        <td >
        	<%=rd.getStringByDI("TA_DJ_GROUPs","TS",0)%>&nbsp; 天
        	<%
        	String ts=rd.getStringByDI("TA_DJ_GROUPs","TS",0);
        	int ws=Integer.parseInt(ts)-1;
        	if(ws >0)
        	{
        	%>
        	<%=ws %>&nbsp; 晚
        	<%} %>
        </td> 	 
		</tr>

		<tr>
		 <td  align="right">用餐标准：</td>
        <td >
        	<%=rd.getStringByDI("TA_DJ_GROUPs","YCBZ",0) %>
        </td>
        <td align="right">住宿标准：</td>
        <td >
        <%
	  		String ZSBZ = rd.getStringByDI("TA_DJ_GROUPs","ZSBZ",0);
	  		for(int i=0;i<rd.getTableRowsCount("JDXJ");i++) {
   		    	String dmz = rd.getStringByDI("JDXJ","dmz",i);
   		    	if(dmz.equals(ZSBZ)){out.print(rd.getStringByDI("JDXJ","dmsm1",i));}
	  		}
	  		%>
        </td>	 
      <td align="right">加点购物：</td>
       	<td>
       		<%
        	String jd = rd.getStringByDI("TA_DJ_GROUPs","JD",0);
       		String gw = rd.getStringByDI("TA_DJ_GROUPs","GW",0);
      		%>
   		  <input type="checkbox" name="TA_DJ_GROUP/JD" value="1" <%if("1".equals(jd)){ %>checked="checked"<%} %>/>加点
   		  <input type="checkbox" name="TA_DJ_GROUP/GW" value="1" <%if("1".equals(gw)){ %>checked="checked"<%} %>/>购物
   		</td>
		</tr>
	  <tr>       	
        <td  align="right">是否自备车：</td>
        <td>
        	<%
        	String SFZBC = rd.getStringByDI("TA_DJ_GROUPs","SFZBC",0);
          	%>
		  <input type="radio" name="TA_DJ_GROUP/SFZBC" value="1" <%if("1".equals(SFZBC)){ %>checked="checked"<%} %>/>是
	      <input type="radio" name="TA_DJ_GROUP/SFZBC" value="2" <%if("2".equals(SFZBC)){ %>checked="checked"<%} %>/>否
		</td>
		<td  align="right">游客类型：</td>
        <td >
        	<%
        		String yklx = rd.getStringByDI("TA_DJ_GROUPs","YKLX",0);
        	%>
        	<input type="radio" name="TA_DJ_GROUP/YKLX" value="1" <%if("1".equals(yklx)){ %>checked="checked"<%} %>/>团队
        	<input type="radio" name="TA_DJ_GROUP/YKLX" value="2" <%if("2".equals(yklx)){ %>checked="checked"<%} %>/>散客
        </td>
		<td align="right" width="12%">进购物店数：</td>
		<td>
   		<%=rd.getStringByDI("TA_DJ_GROUPs","GWDS",0)%>&nbsp;个
	 	</td>
	  </tr>
	  	 <tr id="qp">
	  	<td align="right">全陪姓名：</td>
		<td><%=rd.getStringByDI("TA_DJ_GROUPs","QPXM",0) %></td>
		<td align="right">全陪电话：</td>
		<td><%=rd.getStringByDI("TA_DJ_GROUPs","QPDH",0) %></td>
	  </tr>
	  <tr id="rtf" style="display:none">
	  	<td align="right">人头费：</td>
		<td><input type="text" name="TA_DJ_GROUP/RTF"/></td>
	  </tr>
	</table>
</div>
<%-- 
<div style="text-align: right"> 
 <a href="###" onclick="tableMove();"><img alt="添加" src="<%=request.getContextPath()%>/images/add.gif"> 添加</a>&nbsp;&nbsp;
 <a href="###" onclick="delTabRow();"><img alt="删除" src="<%=request.getContextPath()%>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div> 
<div id="list-table">
<table class="datas" id="store">
  <tr id="first-tr">
	<td width="20%">价格类型</td>
    <td width="15%">价格</td>
    <td width="15%">单房差</td>
    <td width="50%">备注</td>
  </tr>
<%
int priceRows = rd.getTableRowsCount("TA_DJ_TDJGs");
for(int i=0;i<priceRows;i++) {
	
	String priceType = rd.getStringByDI("TA_DJ_TDJGs","JGLX",i);
	String price = rd.getStringByDI("TA_DJ_TDJGs","JG",i);
	String dfc = rd.getStringByDI("TA_DJ_TDJGs","dfc",i);
	String bz = rd.getStringByDI("TA_DJ_TDJGs","bz",i);
%>
  <tr class="valueType">
	<td>
	  <select name="TA_DJ_TDJG/JGLX[<%=i %>]">
	  <%
	  for(int j=0;j<typeRows;j++){
		  String dmz = rd.getStringByDI("JGLX","dmz",j);
		  String dmsm = rd.getStringByDI("JGLX","dmsm1",j);
	  %>
	    <option value="<%=dmz %>" <%if(dmz.equals(priceType)) { %>selected="selected"<%} %> ><%=dmsm %></option>
	  <%
	  }%>
	  
	  </select>
	</td>
	<td><input name="TA_DJ_TDJG/JG[<%=i %>]" value="<%=price %>" id="price_th[0]" onkeydown="checkNum()" class="smallerInput priceRow"  maxlength="5"/></td>
	<td><input name="TA_DJ_TDJG/DFC[<%=i %>]" value="<%=dfc %>" onkeydown="checkNum()" class="smallerInput" maxlength="5"/></td>
	<td >
	  <textarea name="TA_DJ_TDJG/bz[<%=i %>]" rows="2" cols="54" onpropertychange="if(this.value.length>100){this.value=this.value.slice(0,100)}"><%=bz %></textarea>
	</td>
  </tr>
<%	
}
%>
</table>
</div>
--%>
<div style="text-align: right"> 
	<a href="###" onclick="add_XCMX();"><img alt="添加" src="<%=request.getContextPath()%>/images/add.gif"> 添加</a>&nbsp;&nbsp;
	<a href="###" onclick="del_XCMX();"><img alt="删除" src="<%=request.getContextPath()%>/images/del.gif"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div> 
<div class="add-table">
<table class="datas" id="store1" >
  <tr id="first-tr">
  	<td width="6%" align="center">天数</td><td  width="70%" align="center">行程</td><td  width="9%" align="center">供餐</td><td  width="10%" align="center">住宿</td>
  </tr>
   <%
   	   List<GroupLineDetail> list = (List)rd.get("groupDetail");
	   for(int i = 0; i<list.size(); i++){
		   GroupLineDetail gd = list.get(i);
		   String RQ = gd.getRq();//日期
		   String XCMX = gd.getXcmx();//线路明细
		   String BREAKFAST = gd.getBreakfast();//早
		   String CMEAL = gd.getCmeal();//中
		   String SUPPER = gd.getSupper();//晚
		   String ZS = gd.getZs();//住宿
   %>
   <tr class="XCMXType">
  <td><font size="6"><%=RQ %></font><input type="hidden" name="TA_DJ_LINEDETAI4G/RQ[<%=i %>]" value="<%=RQ %>"/></td>
      <td>
       	 <textarea id="XCMX<%=i %>" name="TA_DJ_LINEDETAI4G/XCMX[<%=i %>]" rows="0" cols="0">
   	 	<%=XCMX %>
   	 	</textarea> 
       </td>
       <td align="center">
       	<input type="checkbox" name="TA_DJ_LINEDETAI4G/BREAKFAST[<%=i %>]" value="Y" <%if("Y".equals(BREAKFAST)){ %>checked="checked"<%} %>/>早</br>
       	<input type="checkbox" name="TA_DJ_LINEDETAI4G/CMEAL[<%=i %>]" value="Y" <%if("Y".equals(CMEAL)){ %>checked="checked"<%} %>/>中</br>
       	<input type="checkbox" name="TA_DJ_LINEDETAI4G/SUPPER[<%=i %>]" value="Y" <%if("Y".equals(SUPPER)){ %>checked="checked"<%} %>/>晚</br>
       </td>
       <td align="center"><input type="text" name="TA_DJ_LINEDETAI4G/ZS[<%=i %>]" value="<%=null==ZS?"":ZS %>" class="smallerInput"/></td>
   </tr>
   <%} %>
</table>
<div class="add-table">
<table class="datas" width="100%">
   <tr>
   		<td>备注：</td>
		<td>
			<textarea  name="TA_DJ_GROUP/TSYQ" cols="99" rows="4" onpropertychange="if(this.value.length>200){this.value=this.value.slice(0,200)}"><%=rd.getStringByDI("TA_DJ_GROUPs","TSYQ",0) %></textarea>
		</td>
   </tr>
</table>
</div>


</div>
</form>
</body>
</html>
<script type="text/javascript">
		<%if(TraveRows>0){
			for(int v=0;v<TraveRows;v++){
		%>
			var linkage = new Linkage("dataSrc<%=v%>", "<%=request.getContextPath()%>/main/data/Traved.xml");
			linkage.init();
			linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("TA_DJ_TZTSs","SFID",v)%>",1);
			linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("TA_DJ_TZTSs","CSID",v)%>",2);
			linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("TA_DJ_TZTSs","ZTSID",v)%>",3);
		<%}}else{%>
			var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Traved.xml");
			linkage.init();
		<%}%>
</script>