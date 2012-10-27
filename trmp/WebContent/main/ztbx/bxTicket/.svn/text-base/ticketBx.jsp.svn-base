<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

//添加一种票务类型
function addRows(tableId){
	
	var trItems = jQuery("#ticket"+tableId+" .trItems").size();
	jQuery("#ticketItem"+tableId).append('<tr class="trItems">'+
			'<td>'+
			  '<select name="TA_ZT_BXPWMX'+tableId+'/CPLX['+trItems+']" class="sel-type">'+
			<%
			for(int j=0;j<rd.getTableRowsCount("JTGJ");j++) {
				String dmz = rd.getStringByDI("JTGJ","dmz",j);
				String dmsm = rd.getStringByDI("JTGJ","dmsm1",j);
			%>
				'<option value="<%=dmz %>"><%=dmsm %></option>'+
			<%
			}%>
			  '</select>'+
			'</td>'+
			'<td><input type="text" name="TA_ZT_BXPWMX'+tableId+'/CFZ['+trItems+']" class="smallInput" />至<input type="text" name="TA_ZT_BXPWMX'+tableId+'/MDZ['+trItems+']" class="smallInput"/></td>'+
			'<td><input type="text" name="TA_ZT_BXPWMX'+tableId+'/BEGINTIME['+trItems+']" class="smallerInput" onfocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true})"/></td>'+
			'<td><input type="text" name="TA_ZT_BXPWMX'+tableId+'/zs['+trItems+']" class="smallInput zs" value="0" onkeydown="checkNum();"  onchange="sumMoney();"/>*<input name="TA_ZT_BXPWMX'+tableId+'/DJ['+trItems+']" type="text" class="smallInput dj" value="0" onkeydown="checkNum();"  onchange="sumMoney();"/></td>'+
			'<td><input type="text" name="TA_ZT_BXPWMX'+tableId+'/SXF['+trItems+']" class="smallInput sxf" value="0" onkeydown="checkNum();"  onchange="sumMoney();"/></td>'+
			'<td><input type="text" name="TA_ZT_BXPWMX'+tableId+'/cc['+trItems+']" class="smallInput" value=""/></td>'+
			'<td><input type="text" name="TA_ZT_BXPWMX'+tableId+'/QDJE['+trItems+']" class="smallInput qd" onkeydown="checkNum();" value="0"  onchange="sumMoney();"/></td>'+
			'<td><input type="text" name="TA_ZT_BXPWMX'+tableId+'/XFJE['+trItems+']" class="smallInput xf" onkeydown="checkNum();" value="0"  onchange="sumMoney();" readonly="readonly"/></td>'+
		  '</tr>');
}

//删除一种票务类型
function delRows(tableId){

	var idx = jQuery("#ticket"+tableId+" .trItems").size()-1;
	if(idx > 0) {
		jQuery("#ticket"+tableId+" .trItems:eq("+idx+")").remove();
	}
	sumMoney();
}

function addTicket() {

	var ticketRows = jQuery("#ticketDiv .select-ticket").size();
	jQuery("#ticketDiv").append(
			'<table class="datas select-ticket" width="95%" id="ticket'+ticketRows+'">'+
		    '<tr>'+
			  '<td>请选择代购点：</td>'+
			  '<td colspan="3"><input type="hidden" name="TA_ZT_BXPW/TID['+ticketRows+']" value="<%=rd.getStringByDI("bxPwList","TID",0) %>"/>'+
			  '				   <input type="hidden" name="TA_ZT_BXPW/JHZT['+ticketRows+']" value="Y"/>'+
			    '<select name="TA_ZT_BXPW/sf['+ticketRows+']" id="pro'+ticketRows+'" USEDATA="dataSrc'+ticketRows+'" SUBCLASS="1"></select>'+
			    '<select name="TA_ZT_BXPW/CITY['+ticketRows+']" id="city'+ticketRows+'" USEDATA="dataSrc'+ticketRows+'" SUBCLASS="2"></select>'+
			  	'<select name="TA_ZT_BXPW/DGD['+ticketRows+']" id="dgd'+ticketRows+'" USEDATA="dataSrc'+ticketRows+'" SUBCLASS="3" onchange="getTicketInfo(this.value,'+ticketRows+')"></select>'+
			  	'<input name="TA_ZT_BXPW/ZDR['+ticketRows+']" type="hidden" value="<%=sd.getString("userno") %>"/>'+
			  '</td>'+
			'</tr>'+
			'<tr>'+
			  '<td>联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;&nbsp;人：</td>'+
			  '<td><input type="text" name="TA_ZT_BXPW/LXR['+ticketRows+']" value="" maxlength="10" id="lxrname'+ticketRows+'"/></td>'+
			  '<td>联系电话：</td>'+
			  '<td><input type="text" name="TA_ZT_BXPW/LXRDH['+ticketRows+']" value="" maxlength="11" id="lxrphone'+ticketRows+'"/></td>'+
			'</tr>'+
			'<tr class="none">'+
			  '<td colspan="4">'+
				'<table class="datas items" style="text-align: center" id="ticketItem'+ticketRows+'">'+
				  '<tr id="first-tr">'+
					'<td>车票类型&nbsp;&nbsp;'+
					'<img src="<%=request.getContextPath() %>/images/add.gif" alt="添加" onclick="addRows(\''+ticketRows+'\');"/>&nbsp;&nbsp;'+
					'<img src="<%=request.getContextPath() %>/images/printClose.gif" alt="删除" onclick="delRows(\''+ticketRows+'\');"/>'+
					'</td>'+
					'<td>起始站</td>'+
					'<td>出发时间</td>'+
					'<td>张数*单价</td>'+
					'<td>手续费</td>'+
					'<td>车次</td>'+
					'<td>签单</td>'+
					'<td>现付</td>'+
				  '</tr>'+
				  '<tr class="trItems">'+
					'<td>'+
					  '<select name="TA_ZT_BXPWMX'+ticketRows+'/CPLX[0]" class="sel-type">'+
					<%
					for(int j=0;j<rd.getTableRowsCount("JTGJ");j++) {
						String dmz = rd.getStringByDI("JTGJ","dmz",j);
						String dmsm = rd.getStringByDI("JTGJ","dmsm1",j);
					%>
						'<option value="<%=dmz %>"><%=dmsm %></option>'+
					<%
					}%>
					  '</select>'+
					'</td>'+
					'<td><input type="text" name="TA_ZT_BXPWMX'+ticketRows+'/CFZ[0]" class="smallInput" />至<input type="text" name="TA_ZT_BXPWMX'+ticketRows+'/MDZ[0]" class="smallInput"/></td>'+
					'<td><input type="text" name="TA_ZT_BXPWMX'+ticketRows+'/BEGINTIME[0]" class="smallerInput"'+
						'onfocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true})"/></td>'+
					'<td><input type="text" name="TA_ZT_BXPWMX'+ticketRows+'/zs[0]" class="smallInput zs" value="0" onkeydown="checkNum();" onchange="sumMoney();"/>*'+
						'<input type="text" name="TA_ZT_BXPWMX'+ticketRows+'/DJ[0]" class="smallInput dj" value="0" onkeydown="checkNum();" onchange="sumMoney();"/></td>'+
					'<td><input type="text" name="TA_ZT_BXPWMX'+ticketRows+'/SXF[0]" class="smallInput sxf" onkeydown="checkNum();" value="0" onchange="sumMoney();"/></td>'+
					'<td><input type="text" name="TA_ZT_BXPWMX'+ticketRows+'/cc[0]" class="smallInput" value=""/></td>'+
					'<td><input type="text" name="TA_ZT_BXPWMX'+ticketRows+'/QDJE[0]" class="smallInput qd"  onkeydown="checkNum();" value="0" onchange="sumMoney();" /></td>'+
					'<td><input type="text" name="TA_ZT_BXPWMX'+ticketRows+'/XFJE[0]" class="smallInput xf"  onkeydown="checkNum();" value="0" readonly="readonly"/></td>'+
				  '</tr>'+
				'</table>'+
			  '</td>'+
			'</tr>'+
			'<tr>'+
	  			'<td colspan="8" align="left">备注：<textarea rows="" cols="110" name="TA_ZT_BXPW/BZ['+ticketRows+']"> </textarea></td>'+
	  		'</tr>'+
			'<tr>'+
			  '<td align="right" colspan="4">'+
						'<font color="red">手续费小计：</font><input name="TA_ZT_BXPW/SXFHJ['+ticketRows+']" value="0" type="text" readonly="readonly" class="smallInput sxfxj"/>/元&nbsp;&nbsp;&nbsp;'+
						'<font color="red">签单小计：</font><input name="TA_ZT_BXPW/QDxJ['+ticketRows+']" value="0"  type="text" readonly="readonly"  class="smallInput qdxj"/>/元&nbsp;&nbsp;&nbsp;'+
						'<font color="red">现付小计：</font><input name="TA_ZT_BXPW/XFxJ['+ticketRows+']" value="0" type="text" readonly="readonly"  class="smallInput xfxj"/>/元&nbsp;&nbsp;&nbsp;'+
						'<font color="red">合计：</font>    <input name="TA_ZT_BXPW/HJ['+ticketRows+']" value="0" type="text" readonly="readonly" class="smallInput hj"/>/元'+
			  '</td>'+
			'</tr>'+
		  '</table>');
	var linkage = new Linkage("dataSrc"+ticketRows, "<%=request.getContextPath()%>/main/data/Ticketz.xml");
	linkage.init();
}

function delTicket(){

	var idx = jQuery("#ticketDiv .select-ticket").size()-1;
	//alert(idx);
	if(idx > 0) {
		jQuery("#ticketDiv .select-ticket:eq("+idx+")").remove();
	}
	sumMoney();
}

function submitAddGroup(){
	document.groupInfoForm.submit();
	reload();
}
function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}

function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		    event.returnValue=false;
}

function sumMoney(){

	var topZj = 0;
	var topSxfhj = 0;
	var topQdhj = 0;
	var topXfhj = 0;
	jQuery("#ticketDiv .select-ticket").each(function(i){
		//合计价格
		var zongjia = 0;
		var sxfxj = 0;
		var qdxj = 0;
		var xfxj = 0;
		jQuery("#ticketItem"+i+" .trItems").each(function(j){
			var piece = jQuery("#ticketItem"+i+" .trItems .zs:eq("+j+")").val();
			if(piece=='')
				piece=0;
			var price = jQuery("#ticketItem"+i+" .trItems .dj:eq("+j+")").val();
			if(price=='')
				price=0;
			//手续费
			var sxf = jQuery("#ticketItem"+i+" .trItems .sxf:eq("+j+")").val();
			if(sxf=='')
				sxf=0;
			var total = parseInt(piece*price);   
			total+=parseInt(sxf);
			zongjia += parseInt(total);
			sxfxj += parseInt(sxf);
			//签单小计
			var qd = jQuery("#ticketItem"+i+" .trItems .qd:eq("+j+")").val();
			if(qd=='')
				qd=0;
			
			if(qd > total){
				alert("签单金额必须小于合计金额!");
				return false;
			}else{
				qdxj += parseInt(qd);
			}
			
			//现付
			var v = parseInt(total)- parseInt(qd);
			if(v=='')
				v=0;
			jQuery("#ticketItem"+i+" .trItems .xf:eq("+j+")").val(v);
			
			
			//现付小计
			var xf = jQuery("#ticketItem"+i+" .trItems .xf:eq("+j+")").val();
			if(xf=='')xf=0;
			xfxj += parseInt(xf);
			
			
		});
		jQuery("#ticket"+i+" .hj").val(zongjia);
		jQuery("#ticket"+i+" .sxfxj").val(sxfxj);
		jQuery("#ticket"+i+" .qdxj").val(qdxj);
		jQuery("#ticket"+i+" .xfxj").val(xfxj);
		//最外层价格计算
		topZj += parseInt(zongjia);
		topSxfhj += parseInt(sxfxj);
		topQdhj += parseInt(qdxj);
		topXfhj += parseInt(xfxj);
	});
	jQuery(".zj").val(topZj);
	jQuery(".sxfzj").val(topSxfhj);
	jQuery(".qdzj").val(topQdhj);
	jQuery(".xfzj").val(topXfhj);
}

function saveTicket() {

	document.ticketForm.submit();
	reload();
}

function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}

function getTicketInfo(thisVal,rowID){
	var obj=jQuery.ajax({url:"ztQueryLXRByID.?TA_TICKET/TICKET_ID="+thisVal,
		async:true,
		dataType:"json",
		cache:false,
		success:function(data){
			jQuery("#lxrname"+rowID).val(data[0].name);
			jQuery("#lxrphone"+rowID).val(data[0].phone);
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
            alert(errorThrown); 
   	 	}
	});
}
</script>
<title>票务报销</title>
</head>

<body>
<form name="ticketForm" method="post" action="ztSaveTicketBx.">
<div id="lable"><span >票务报销</span>
				<span class="showPointer" id="addTicket" onclick="addTicket();">添加</span>
				<span  class="showPointer" id="delTicket" onclick="delTicket();">删除</span></div>
<div id="ticketDiv">
<%
int ticketRows = rd.getTableRowsCount("bxPwList");
String sxfzj=rd.getStringByDI("bxpwJDXXZJB","SXFZJ",0);
String qdzj=rd.getStringByDI("bxpwJDXXZJB","QDZJ",0);
String xfzj=rd.getStringByDI("bxpwJDXXZJB","XFZJ",0);
String zj=rd.getStringByDI("bxpwJDXXZJB","ZJ",0);
if(ticketRows <= 0){
%>
  <table class="datas select-ticket" width="95%" id="ticket0">
    <tr>
	  <td colspan="8">&nbsp;代购点：<input type="hidden" name="TA_ZT_BXPW/TID[0]" value="<%=rd.getStringByDI("bxPwList","TID",0) %>"/>
	  				  <input type="hidden" name="TA_ZT_BXPW/JHZT[0]" value="Y"/>
	    <select name="TA_ZT_BXPW/sf[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
	    <select name="TA_ZT_BXPW/CITY[0]" id="city0" USEDATA="dataSrc0" SUBCLASS="2"></select>
	  	<select name="TA_ZT_BXPW/DGD[0]" id="dgd0" USEDATA="dataSrc0" SUBCLASS="3" onchange="getTicketInfo(this.value,0)"></select>
	  	<input name="TA_ZT_BXPW/ZDR[0]" type="hidden" value="<%=sd.getString("userno") %>"/>
联系人：<input type="text" name="TA_ZT_BXPW/LXR[0]" value="" maxlength="10" id="lxrname0"  class="smallerInput"/>&nbsp;
联系电话：<input type="text" name="TA_ZT_BXPW/LXRDH[0]" value="" maxlength="11" id="lxrphone0"  class="smallerInput"/></td>
	</tr>
	<tr class="none">
	  <td colspan="8">
		<table class="datas items" style="text-align: center" id="ticketItem0">
		  <tr id="first-tr">
			<td>车票类型
			<img src="<%=request.getContextPath() %>/images/add.gif" alt="添加" onclick="addRows('0');"/>
			<img src="<%=request.getContextPath() %>/images/printClose.gif" alt="删除" onclick="delRows('0');"/>
			</td>
			<td>起始站</td>
			<td>出发时间</td>
			<td>张数*单价</td>
			<td>手续费</td>
			<td>车次</td>
			<td>签单</td>
			<td>现付</td>
		  </tr>
		  <tr class="trItems">
			<td>
			  <select name="TA_ZT_BXPWMX/CPLX[0]" class="sel-type">
			<%
			for(int j=0;j<rd.getTableRowsCount("JTGJ");j++) {
				String dmz = rd.getStringByDI("JTGJ","dmz",j);
				String dmsm = rd.getStringByDI("JTGJ","dmsm1",j);
			%>
				<option value="<%=dmz %>"><%=dmsm %></option>
			<%
			}%>
			  </select>
			</td>
			<td><input type="text" name="TA_ZT_BXPWMX/CFZ[0]" class="smallInput" />至<input type="text" name="TA_ZT_BXPWMX/MDZ[0]" class="smallInput"/></td>
			<td><input type="text" name="TA_ZT_BXPWMX/BEGINTIME[0]" class="smallerInput" 
				onfocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/></td>
			<td><input type="text" name="TA_ZT_BXPWMX/zs[0]" class="smallInput zs" value="0" onkeydown="checkNum();" onchange="sumMoney();"/>*<input type="text" name="TA_ZT_BXPWMX/DJ[0]" class="smallInput dj" value="0" onkeydown="checkNum();" onchange="sumMoney();"/></td>
			<td><input type="text" name="TA_ZT_BXPWMX/SXF[0]" class="smallInput sxf" onkeydown="checkNum();" value="0" onchange="sumMoney();"/></td>
			<td><input type="text" name="TA_ZT_BXPWMX/cc[0]" class="smallInput" value=""/></td>
			<td><input type="text" name="TA_ZT_BXPWMX/QDJE[0]" class="smallInput qd"  onkeydown="checkNum();" value="0" onchange="sumMoney();" /></td>
			<td><input type="text" name="TA_ZT_BXPWMX/XFJE[0]" class="smallInput xf"  onkeydown="checkNum();" value="0" readonly="readonly"/></td>
		  </tr>
		</table>
	  </td>
	</tr>
	<tr>
  		<td colspan="8" align="left">备注：<textarea rows="" cols="110" name="TA_ZT_BXPW/BZ[0]"> </textarea></td>
  	 </tr>
	<tr> 
	  <td align="right" colspan="8">
				<font color="red">手续费小计：</font><input name="TA_ZT_JHPW/SXFHJ[0]" value="0" type="text" readonly="readonly" class="smallInput sxfxj"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">签单小计：</font><input name="TA_ZT_JHPW/QDxJ[0]" value="0"  type="text" readonly="readonly"  class="smallInput qdxj"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">现付小计：</font><input name="TA_ZT_JHPW/XFxJ[0]" value="0" type="text" readonly="readonly"  class="smallInput xfxj"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">合计：</font>    <input name="TA_ZT_JHPW/HJ[0]" value="0" type="text" readonly="readonly" class="smallInput hj"/>/元
	  </td>
	</tr>
  </table>
<%
}else{
	for(int i=0;i<ticketRows;i++){
		String id = rd.getStringByDI("bxPwList","id",i);
		String bz = rd.getStringByDI("bxPwList","BZ",i);
%>
<table class="datas select-ticket" width="95%" id="ticket<%=i %>">
    <tr>
	  <td colspan="8">&nbsp;代购点：<input type="hidden" name="TA_ZT_BXPW/TID[<%=i %>]" value="<%=rd.getStringByDI("bxPwList","TID",i) %>"/>
	  				  <input type="hidden" name="TA_ZT_BXPW/BXZT[<%=i %>]" value="Y"/>
	    <select name="TA_ZT_BXPW/sf[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
	    <select name="TA_ZT_BXPW/CITY[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
	  	<select name="TA_ZT_BXPW/DGD[<%=i %>]" id="dgd<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3" onchange="getTicketInfo(this.value,<%=i %>)"></select>
	  	<input name="TA_ZT_BXPW/BXR[<%=i %>]" type="hidden" value="<%=sd.getString("userno") %>"/>
联系人：<input type="text" name="TA_ZT_BXPW/LXR[<%=i %>]" value="<%=rd.getStringByDI("bxPwList","LXR",i) %>" maxlength="10" id="lxrname<%=i%>" class="smallerInput"/>&nbsp;电话：<input type="text" name="TA_ZT_BXPW/LXRDH[<%=i %>]" value="<%=rd.getStringByDI("bxPwList","LXrdh",i) %>" maxlength="11" id="lxrphone<%=i%>" class="smallerInput"/></td>
	</tr>
	<tr class="none">
	  <td colspan="8">
		<table class="datas items" style="text-align: center" id="ticketItem<%=i %>">
		  <tr id="first-tr">
			<td>车票类型
			<img src="<%=request.getContextPath() %>/images/add.gif" alt="添加" onclick="addRows('<%=i %>');"/>
			<img src="<%=request.getContextPath() %>/images/printClose.gif" alt="删除" onclick="delRows('<%=i %>');"/>
			</td>
			<td>起始站</td>
			<td>出发时间</td>
			<td>张数*单价</td>
			<td>手续费</td>
			<td>车次</td>
			<td>签单</td>
			<td>现付</td>
		  </tr>
	<%
	int detailRows = rd.getTableRowsCount("bxPwmxList");
	int idx = 0;
	for(int k=0;k<detailRows;k++) {
		String planID = rd.getStringByDI("bxPwmxList","JHID",k);
		if(planID.equals(id)){
			String ticketType = rd.getStringByDI("bxPwmxList","CPLX",k);
			String stationB = rd.getStringByDI("bxPwmxList","cfz",k);
			String stationE = rd.getStringByDI("bxPwmxList","mdz",k);
			String bt = rd.getStringByDI("bxPwmxList","BEGINTIME",k);
			String piceOfNum = rd.getStringByDI("bxPwmxList","zs",k);
			String price = rd.getStringByDI("bxPwmxList","dj",k);
			String sxf = rd.getStringByDI("bxPwmxList","sxf",k);
			String cc = rd.getStringByDI("bxPwmxList","cc",k);
			String qd = rd.getStringByDI("bxPwmxList","QDJE",k);
			String xf = rd.getStringByDI("bxPwmxList","XFJE",k);
			
			
	%>
		  <tr class="trItems">
			<td>
			  <select name="TA_ZT_BXPWMX<%=i %>/CPLX[<%=idx %>]" class="sel-type">
			<%
			for(int j=0;j<rd.getTableRowsCount("JTGJ");j++) {
				String dmz = rd.getStringByDI("JTGJ","dmz",j);
				String dmsm = rd.getStringByDI("JTGJ","dmsm1",j);
			%>
				<option value="<%=dmz %>" <%if(dmz.equals(ticketType)){ %>selected="selected" <%} %>><%=dmsm %></option>
			<%
			}%>
			  </select>
			</td>
			<td><input type="text" name="TA_ZT_BXPWMX<%=i %>/CFZ[<%=idx %>]" class="smallInput" value="<%=stationB %>"/>至<input type="text" name="TA_ZT_BXPWMX<%=i %>/MDZ[<%=idx %>]" class="smallInput" value="<%=stationE %>"/></td>
			<td><input type="text" name="TA_ZT_BXPWMX<%=i %>/BEGINTIME[<%=idx %>]" class="smallerInput" value="<%=bt %>"
				onfocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/></td>
			<td><input type="text" name="TA_ZT_BXPWMX<%=i %>/zs[<%=idx %>]" class="smallInput zs" value="<%=piceOfNum %>" onkeydown="checkNum();" onchange="sumMoney();" />*<input type="text" name="TA_ZT_BXPWMX<%=i %>/DJ[<%=idx %>]" class="smallInput dj" value="<%=price %>" onkeydown="checkNum();" onchange="sumMoney();" /></td>
			<td><input type="text" name="TA_ZT_BXPWMX<%=i %>/SXF[<%=idx %>]" class="smallInput sxf" value="<%=sxf %>" onkeydown="checkNum();" onchange="sumMoney();"/></td>
			<td><input type="text" name="TA_ZT_BXPWMX<%=i %>/ct[<%=idx %>]" class="smallInput" value="<%=cc %>"/></td>
			<td><input type="text" name="TA_ZT_BXPWMX<%=i %>/QDJE[<%=idx %>]" class="smallInput qd" value="<%=qd %>" onkeydown="checkNum();" onchange="sumMoney();" /></td>
			<td><input type="text" name="TA_ZT_BXPWMX<%=i %>/XFJE[<%=idx %>]" class="smallInput xf" value="<%=xf %>" readonly="readonly" onkeydown="checkNum();"/></td>
		  </tr>
		  
	<%	idx ++;
		}
	}%>
		</table>
	  </td>
	</tr>
 	<tr>
  		<td colspan="8" align="left">备注：<textarea rows="" cols="110" name="TA_ZT_BXPW/BZ[<%=i %>]"><%=bz %></textarea></td>
  	 </tr>
	<tr> 
	  <td align="right" colspan="8">
				<font color="red">手续费小计：</font><input name="TA_ZT_BXPW/SXFHJ[<%=i %>]" value="<%=rd.getStringByDI("bxPwList","SXFHJ",i) %>" type="text" readonly="readonly" class="smallInput sxfxj"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">签单小计：</font><input name="TA_ZT_BXPW/QDxJ[<%=i %>]" value="<%=rd.getStringByDI("bxPwList","QDXJ",i) %>" type="text" readonly="readonly"  class="smallInput qdxj"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">现付小计：</font><input name="TA_ZT_BXPW/XFxJ[<%=i %>]" value="<%=rd.getStringByDI("bxPwList","XFXJ",i) %>" type="text" readonly="readonly"  class="smallInput xfxj"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">合计：</font>    <input name="TA_ZT_BXPW/HJ[<%=i %>]" value="<%=rd.getStringByDI("bxPwList","HJ",i) %>" type="text" readonly="readonly" class="smallInput hj"/>/元
	  </td>
	</tr>
  </table>
<%	}
} %>
</div>
<div id="lable"><span >票务费用合计</span></div>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td>
			<font color="red">手续费总计：</font><input type="text" name="TA_TDBXZJXXB_ZT/SXFZJ" value="<%="".equals(sxfzj)?"0":sxfzj %>" class="smallerInput sxfzj" readonly="readonly"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">签单总计：</font><input type="text" name="TA_TDBXZJXXB_ZT/BXPWQD" value="<%="".equals(qdzj)?"0":qdzj %>" class="smallerInput qdzj" readonly="readonly"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付总计：</font><input type="text" name="TA_TDBXZJXXB_ZT/BXPWXF" value="<%="".equals(xfzj)?"0":xfzj %>" class="smallerInput xfzj" id="xfzj" readonly="readonly"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">总计：</font><input type="text" name="TA_TDBXZJXXB_ZT/PWHJ"  value="<%="".equals(zj)?"0":zj %>" class="smallerInput zj" readonly="readonly" />/元
		</td>
	</tr>
</table>
<div align="center" id="last-sub">
<%if(!"view".equals(rd.getString("flag"))){ %>
	<input type="button" id="button" value="提交" onclick="saveTicket();"/>
	<%} %>
	<input type="button" id="button" value="关闭" onclick="reload();"/>
</div>
</form>
</body>
</html>


<script type="text/javascript">
<%
int rows = ticketRows;
if(rows > 0){
	for(int i=0;i<rows;i++){
%>
		var linkage = new Linkage("dataSrc<%=i %>", "<%=request.getContextPath()%>/main/data/Ticketz.xml");
		linkage.init();
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("bxPwList","SF",i) %>",1);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("bxPwList","CITY",i)%>",2);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("bxPwList","DGD",i)%>",3);
<%	}
}else{%>
	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Ticketz.xml");
	linkage.init();
<%
}%>
</script>