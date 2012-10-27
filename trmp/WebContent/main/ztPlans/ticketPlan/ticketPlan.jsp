<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
String groupID = rd.getStringByDI("TA_ZT_JHPWs","TID",0).equals("")?rd.getString("tid"):rd.getStringByDI("TA_ZT_JHPWs","TID",0);
%>

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
			  '<select name="TA_ZT_JHPWMX'+tableId+'/CPLX['+trItems+']" class="sel-type">'+
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
			'<td><input type="text" name="TA_ZT_JHPWMX'+tableId+'/CFZ['+trItems+']" class="smallInput" />至<input type="text" name="TA_ZT_JHPWMX'+tableId+'/MDZ['+trItems+']" class="smallInput"/></td>'+
			'<td><input type="text" name="TA_ZT_JHPWMX'+tableId+'/BEGINTIME['+trItems+']" class="smallerInput" onfocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true})"/></td>'+
			'<td><input type="text" name="TA_ZT_JHPWMX'+tableId+'/zs['+trItems+']" class="smallInput zs" value="0" onkeydown="checkNum();" onchange="sumMoney();"/>*<input name="TA_ZT_JHPWMX'+tableId+'/DJ['+trItems+']" type="text" class="smallInput dj" value="0" onkeydown="checkNum();" onchange="sumMoney();"/></td>'+
			'<td><input type="text" name="TA_ZT_JHPWMX'+tableId+'/SXF['+trItems+']" class="smallInput sxf" value="0" onkeydown="checkNum();" onchange="sumMoney();"/></td>'+
			'<td><input type="text" name="TA_ZT_JHPWMX'+tableId+'/cc['+trItems+']" class="smallInput" value=""/></td>'+
			'<td><input type="text" name="TA_ZT_JHPWMX'+tableId+'/QDJE['+trItems+']" class="smallInput qd" onkeydown="checkNum();" value="0" onchange="sumMoney();"/></td>'+
			'<td><input type="text" name="TA_ZT_JHPWMX'+tableId+'/XFJE['+trItems+']" class="smallInput xf" onkeydown="checkNum();" value="0" readonly="readonly"/></td>'+
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
			  '<td colspan="3"><input type="hidden" name="TA_ZT_JHPW/TID['+ticketRows+']" value="<%=groupID %>"/>'+
			  '				   <input type="hidden" name="TA_ZT_JHPW/JHZT['+ticketRows+']" value="Y"/>'+
			    '<select name="TA_ZT_JHPW/sf['+ticketRows+']" id="pro'+ticketRows+'" USEDATA="dataSrc'+ticketRows+'" SUBCLASS="1"></select>'+
			    '<select name="TA_ZT_JHPW/CITY['+ticketRows+']" id="city'+ticketRows+'" USEDATA="dataSrc'+ticketRows+'" SUBCLASS="2"></select>'+
			  	'<select name="TA_ZT_JHPW/DGD['+ticketRows+']" id="dgd'+ticketRows+'" USEDATA="dataSrc'+ticketRows+'" SUBCLASS="3" onchange="getTicketInfo(this.value,'+ticketRows+')"></select>'+
			  	'<input name="TA_ZT_JHPW/ZDR['+ticketRows+']" type="hidden" value="<%=sd.getString("userno") %>"/>'+
			  '</td>'+
			'</tr>'+
			'<tr>'+
			  '<td>联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;&nbsp;人：</td>'+
			  '<td><input type="text" name="TA_ZT_JHPW/LXR['+ticketRows+']" value="" maxlength="10" id="lxrname'+ticketRows+'"/></td>'+
			  '<td>联系电话：</td>'+
			  '<td><input type="text" name="TA_ZT_JHPW/LXRDH['+ticketRows+']" value="" maxlength="11" id="lxrphone'+ticketRows+'"/></td>'+
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
					  '<select name="TA_ZT_JHPWMX'+ticketRows+'/CPLX[0]" class="sel-type">'+
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
					'<td><input type="text" name="TA_ZT_JHPWMX'+ticketRows+'/CFZ[0]" class="smallInput" />至<input type="text" name="TA_ZT_JHPWMX'+ticketRows+'/MDZ[0]" class="smallInput"/></td>'+
					'<td><input type="text" name="TA_ZT_JHPWMX'+ticketRows+'/BEGINTIME[0]" class="smallerInput"'+
						'onfocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true})"/></td>'+
					'<td><input type="text" name="TA_ZT_JHPWMX'+ticketRows+'/zs[0]" class="smallInput zs" value="0" onkeydown="checkNum();" onchange="sumMoney();"/>*'+
						'<input type="text" name="TA_ZT_JHPWMX'+ticketRows+'/DJ[0]" class="smallInput dj" value="0" onkeydown="checkNum();" onchange="sumMoney();"/></td>'+
					'<td><input type="text" name="TA_ZT_JHPWMX'+ticketRows+'/SXF[0]" class="smallInput sxf" onkeydown="checkNum();" value="0" onchange="sumMoney();"/></td>'+
					'<td><input type="text" name="TA_ZT_JHPWMX'+ticketRows+'/cc[0]" class="smallInput" value=""/></td>'+
					'<td><input type="text" name="TA_ZT_JHPWMX'+ticketRows+'/QDJE[0]" class="smallInput qd"  onkeydown="checkNum();" value="0" onchange="sumMoney();" /></td>'+
					'<td><input type="text" name="TA_ZT_JHPWMX'+ticketRows+'/XFJE[0]" class="smallInput xf"  onkeydown="checkNum();" value="0" readonly="readonly"/></td>'+
				  '</tr>'+
				'</table>'+
			  '</td>'+
			'</tr>'+
			'<tr>'+
			  '<td align="right" colspan="4">'+
						'<font color="red">手续费小计：</font><input name="TA_ZT_JHPW/SXFHJ['+ticketRows+']" value="0" type="text" readonly="readonly" class="smallInput sxfxj"/>/元&nbsp;&nbsp;&nbsp;'+
						'<font color="red">签单小计：</font><input name="TA_ZT_JHPW/QDxJ['+ticketRows+']" value="0"  type="text" readonly="readonly"  class="smallInput qdxj"/>/元&nbsp;&nbsp;&nbsp;'+
						'<font color="red">现付小计：</font><input name="TA_ZT_JHPW/XFxJ['+ticketRows+']" value="0" type="text" readonly="readonly"  class="smallInput xfxj"/>/元&nbsp;&nbsp;&nbsp;'+
						'<font color="red">合计：</font>    <input name="TA_ZT_JHPW/HJ['+ticketRows+']" value="0" type="text" readonly="readonly" class="smallInput hj"/>/元'+
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

			//张数
			var piece = jQuery("#ticketItem"+i+" .trItems .zs:eq("+j+")").val();
			if('' == piece){
				piece = 0;
				jQuery("#ticketItem"+i+" .trItems .zs:eq("+j+")").val("0")
			}
			//单价
			var price = jQuery("#ticketItem"+i+" .trItems .dj:eq("+j+")").val();
			if('' == price){
				piece = 0;
				jQuery("#ticketItem"+i+" .trItems .dj:eq("+j+")").val("0");
			}
			//手续费
			var sxf = jQuery("#ticketItem"+i+" .trItems .sxf:eq("+j+")").val();
			if('' == sxf){
				sxf = 0;
				jQuery("#ticketItem"+i+" .trItems .sxf:eq("+j+")").val("0");
			}
			sxfxj += parseInt(sxf);//手续费小计

			//签单
			var qd = jQuery("#ticketItem"+i+" .trItems .qd:eq("+j+")").val();
			if(qd > total){
				alert("签单金额必须小于合计金额!");
				return false;
			}
			if('' == qd){
				qd = 0;
				jQuery("#ticketItem"+i+" .trItems .qd:eq("+j+")").val("0");
			}
			qdxj += parseInt(qd);

			var total = parseInt(piece)*parseInt(price)+parseInt(sxf);
			//现付
			var xf = total - qd;
			jQuery("#ticketItem"+i+" .trItems .xf:eq("+j+")").val(xf);
			xfxj += parseInt(xf);
			
			
			zongjia = parseInt(zongjia) + parseInt(total);
			
			
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

	//alert(rowID);
	var obj=jQuery.ajax({url:"djQueryLXRByID.?TA_TICKET/TICKET_ID="+thisVal,
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
<title>票务计调</title>
</head>

<body>
<form name="ticketForm" method="post" action="ztSaveTicket.">
<div id="lable"><span >票务计调</span>
<%
	String flag=rd.getString("flag");
	if(!"view".equals(flag)){
%>
				<span class="showPointer" id="addTicket" onclick="addTicket();">添加</span>
				<span  class="showPointer" id="delTicket" onclick="delTicket();">删除</span>
<%} %>
</div>
<div id="ticketDiv">
<%
int ticketRows = rd.getTableRowsCount("TA_ZT_JHPWs");
if(ticketRows <= 0){
%>
  <table class="datas select-ticket" width="95%" id="ticket0">
    <tr>
	  <td>请选择代购点：</td>
	  <td colspan="3"><input type="hidden" name="TA_ZT_JHPW/TID[0]" value="<%=groupID %>"/>
	  				  <input type="hidden" name="TA_ZT_JHPW/JHZT[0]" value="Y"/>
	    <select name="TA_ZT_JHPW/sf[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
	    <select name="TA_ZT_JHPW/CITY[0]" id="city0" USEDATA="dataSrc0" SUBCLASS="2"></select>
	  	<select name="TA_ZT_JHPW/DGD[0]" id="dgd0" USEDATA="dataSrc0" SUBCLASS="3" onchange="getTicketInfo(this.value,'0')"></select>
	  	<input name="TA_ZT_JHPW/ZDR[0]" type="hidden" value="<%=sd.getString("userno") %>"/>
	  </td>
	</tr>
	<tr>
	  <td>联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;&nbsp;人：</td>
	  <td><input type="text" name="TA_ZT_JHPW/LXR[0]" value="" maxlength="10" id="lxrname0"/></td>
	  <td>联系电话：</td>
	  <td><input type="text" name="TA_ZT_JHPW/LXRDH[0]" value="" maxlength="11" id="lxrphone0"/></td>
	</tr>
	<tr class="none">
	  <td colspan="4">
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
			  <select name="TA_ZT_JHPWMX0/CPLX[0]" class="sel-type">
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
			<td><input type="text" name="TA_ZT_JHPWMX0/CFZ[0]" class="smallInput" />至<input type="text" name="TA_ZT_JHPWMX0/MDZ[0]" class="smallInput"/></td>
			<td><input type="text" name="TA_ZT_JHPWMX0/BEGINTIME[0]" class="smallerInput" 
				onfocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/></td>
			<td><input type="text" name="TA_ZT_JHPWMX0/zs[0]" class="smallInput zs" value="0" onkeydown="checkNum();" onchange="sumMoney();"/>*<input type="text" name="TA_ZT_JHPWMX0/DJ[0]" class="smallInput dj" value="0" onkeydown="checkNum();" onchange="sumMoney();"/></td>
			<td><input type="text" name="TA_ZT_JHPWMX0/SXF[0]" class="smallInput sxf" onkeydown="checkNum();" value="0" onchange="sumMoney();"/></td>
			<td><input type="text" name="TA_ZT_JHPWMX0/cc[0]" class="smallInput" value=""/></td>
			<td><input type="text" name="TA_ZT_JHPWMX0/QDJE[0]" class="smallInput qd"  onkeydown="checkNum();" value="0" onchange="sumMoney();" /></td>
			<td><input type="text" name="TA_ZT_JHPWMX0/XFJE[0]" class="smallInput xf"  onkeydown="checkNum();" value="0" readonly="readonly"/></td>
		  </tr>
		</table>
	  </td>
	</tr>
	<tr> 
	  <td align="right" colspan="4">
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
		String id = rd.getStringByDI("TA_ZT_JHPWs","id",i);
%>
<table class="datas select-ticket" width="95%" id="ticket<%=i %>">
    <tr>
	  <td>请选择代购点：</td>
	  <td colspan="3"><input type="hidden" name="TA_ZT_JHPW/TID[<%=i %>]" value="<%=groupID %>"/>
	  				  <input type="hidden" name="TA_ZT_JHPW/JHZT[<%=i %>]" value="Y"/>
	    <select name="TA_ZT_JHPW/sf[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
	    <select name="TA_ZT_JHPW/CITY[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
	  	<select name="TA_ZT_JHPW/DGD[<%=i %>]" id="dgd<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3" onchange="getTicketInfo(this.value,'<%=i %>')"></select>
	  	<input name="TA_ZT_JHPW/ZDR[<%=i %>]" type="hidden" value="<%=sd.getString("userno") %>"/>
	  </td>
	</tr>
	<tr>
	  <td>联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;&nbsp;人：</td>
	  <td><input type="text" name="TA_ZT_JHPW/LXR[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHPWs","LXR",i) %>" maxlength="10" id="name<%=i %>"/></td>
	  <td>联系电话：</td>
	  <td><input type="text" name="TA_ZT_JHPW/LXRDH[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHPWs","LXrdh",i) %>" maxlength="11" id="phone<%=i %>"/></td>
	</tr>
	<tr class="none">
	  <td colspan="4">
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
	int detailRows = rd.getTableRowsCount("TA_ZT_JHPWMXs");
	int idx = 0;
	for(int k=0;k<detailRows;k++) {
		String planID = rd.getStringByDI("TA_ZT_JHPWMXs","JHID",k);
		if(planID.equals(id)){
			String ticketType = rd.getStringByDI("TA_ZT_JHPWMXs","CPLX",k);
			String stationB = rd.getStringByDI("TA_ZT_JHPWMXs","cfz",k);
			String stationE = rd.getStringByDI("TA_ZT_JHPWMXs","mdz",k);
			String bt = rd.getStringByDI("TA_ZT_JHPWMXs","BEGINTIME",k);
			String piceOfNum = rd.getStringByDI("TA_ZT_JHPWMXs","zs",k);
			String price = rd.getStringByDI("TA_ZT_JHPWMXs","dj",k);
			String sxf = rd.getStringByDI("TA_ZT_JHPWMXs","sxf",k);
			String cc = rd.getStringByDI("TA_ZT_JHPWMXs","cc",k);
			String qd = rd.getStringByDI("TA_ZT_JHPWMXs","QDJE",k);
			String xf = rd.getStringByDI("TA_ZT_JHPWMXs","XFJE",k);
	%>
		  <tr class="trItems">
			<td>
			  <select name="TA_ZT_JHPWMX<%=i %>/CPLX[<%=idx %>]" class="sel-type">
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
			<td><input type="text" name="TA_ZT_JHPWMX<%=i %>/CFZ[<%=idx %>]" class="smallInput" value="<%=stationB %>"/>至<input type="text" name="TA_ZT_JHPWMX<%=i %>/MDZ[<%=idx %>]" class="smallInput" value="<%=stationE %>"/></td>
			<td><input type="text" name="TA_ZT_JHPWMX<%=i %>/BEGINTIME[<%=idx %>]" class="smallerInput" value="<%=bt %>"
				onfocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/></td>
			<td><input type="text" name="TA_ZT_JHPWMX<%=i %>/zs[<%=idx %>]" class="smallInput zs" value="<%=piceOfNum %>" onkeydown="checkNum();" onchange="sumMoney();" />*<input type="text" name="TA_ZT_JHPWMX<%=i %>/DJ[<%=idx %>]" class="smallInput dj" value="<%=price %>" onkeydown="checkNum();" onchange="sumMoney();" /></td>
			<td><input type="text" name="TA_ZT_JHPWMX<%=i %>/SXF[<%=idx %>]" class="smallInput sxf" value="<%=sxf %>" onkeydown="checkNum();" onchange="sumMoney();"/></td>
			<td><input type="text" name="TA_ZT_JHPWMX<%=i %>/cc[<%=idx %>]" class="smallInput" value="<%=cc %>"/></td>
			<td><input type="text" name="TA_ZT_JHPWMX<%=i %>/QDJE[<%=idx %>]" class="smallInput qd" value="<%=qd %>" onkeydown="checkNum();" onchange="sumMoney();" /></td>
			<td><input type="text" name="TA_ZT_JHPWMX<%=i %>/XFJE[<%=idx %>]" class="smallInput xf" value="<%=xf %>" readonly="readonly" onkeydown="checkNum();"/></td>
		  </tr>
	<%	idx ++;
		}
	}%>
		</table>
	  </td>
	</tr>
	<tr> 
	  <td align="right" colspan="4">
				<font color="red">手续费小计：</font><input name="TA_ZT_JHPW/SXFHJ[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHPWs","SXFHJ",i) %>" type="text" readonly="readonly" class="smallInput sxfxj"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">签单小计：</font><input name="TA_ZT_JHPW/QDxJ[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHPWs","QDXJ",i) %>" type="text" readonly="readonly"  class="smallInput qdxj"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">现付小计：</font><input name="TA_ZT_JHPW/XFxJ[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHPWs","XFXJ",i) %>" type="text" readonly="readonly"  class="smallInput xfxj"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">合计：</font>    <input name="TA_ZT_JHPW/HJ[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHPWs","HJ",i) %>" type="text" readonly="readonly" class="smallInput hj"/>/元
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
			<font color="red">手续费总计：</font><input type="text" name="TA_TDJDXXZJB_ZT/SXFZJ" value="<%=rd.getStringByDI("TA_TDJDXXZJB_ZTs","SXFzJ",0) %>" class="smallerInput sxfzj" readonly="readonly"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">签单总计：</font><input type="text" name="TA_TDJDXXZJB_ZT/QDPWZJ" value="<%=rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDPWZJ",0) %>" class="smallerInput qdzj" readonly="readonly"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付总计：</font><input type="text" name="TA_TDJDXXZJB_ZT/XFPWZJ" value="<%=rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFPWZJ",0) %>" class="smallerInput xfzj" id="xfzj" readonly="readonly"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">总计：</font><input type="text" name="TA_TDJDXXZJB_ZT/PWZJ"  value="<%=rd.getStringByDI("TA_TDJDXXZJB_ZTs","PWZJ",0) %>" class="smallerInput zj" readonly="readonly" />/元
		</td>
	</tr>
</table>
<div align="center" id="last-sub">
	<%if(!"view".equals(flag)){ %>
	<input type="button" id="button" value="提交" onclick="saveTicket();"/>
	<input type="button" id="button" value="关闭" onclick="reload();"/>
	<%} %>
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
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("TA_ZT_JHPWs","SF",i) %>",1);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("TA_ZT_JHPWs","CITY",i)%>",2);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("TA_ZT_JHPWs","DGD",i)%>",3);
<%	}
}else{%>
	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Ticketz.xml");
	linkage.init();
<%
}%>
</script>