<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<title>车辆费用报销</title>
<script type="text/javascript">
var rows;
jQuery(document).ready(function(){

	rows = jQuery("#carDiv table").size();
});

function addVeh(){

	jQuery("#carDiv").append('<table class="datas select-car" id="car" width="100%">'+
			  '<tr>'+
				'<td>选择车辆：</td>'+
				'<td colspan="3"><input type="hidden" name="TA_ZT_JHCL/TID['+rows+']" value="<%=rd.getStringByDI("bxVehList","TID",0) %>"/>'+
				  '<select name="TA_ZT_BXCL/SF['+rows+']" id="pro'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="1"></select>'+
				  '<select name="TA_ZT_BXCL/CITY['+rows+']" id="city'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="2"></select>'+
				  '<select name="TA_ZT_BXCL/CD['+rows+']" id="team'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="3" ></select>'+
				  '<select name="TA_ZT_BXCL/CX['+rows+']" id="type'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="4" ></select>'+
				  '<select name="TA_ZT_BXCL/CP['+rows+']" id="car_id'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="5" onchange="queryDriver(this.value,'+rows+');"></select>'+
				'</td>'+
			  '</tr>'+
			  '<tr>'+
			    '<td>司机姓名：</td>'+
				'<td><input type="text" name="TA_ZT_BXCL/SJXM['+rows+']" value="" maxlength="10" id="driver'+rows+'"/></td>'+
				'<td>司机电话：</td>'+
				'<td><input type="text" name="TA_ZT_BXCL/SJDH['+rows+']" value="" maxlength="11" id="phone'+rows+'"/></td>'+
			  '</tr>'+
			  '<tr>'+
			  	'<td>接团时间：</td>'+
				'<td><input name="TA_ZT_BXCL/BEGINTIME['+rows+']" onFocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true})"/></td>'+
				'<td>送团时间：</td>'+
				'<td><input name="TA_ZT_BXCL/ENDTIME['+rows+']"  onFocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true})"/></td>'+
			  '</tr>'+
			  '<tr>'+
			    '<td>接团航班车次：</td>'+
				'<td><input type="text" name="TA_ZT_BXCL/JTHBCC['+rows+']" value="" id="driver'+rows+'"/></td>'+
				'<td>送团航班车次：</td>'+
				'<td><input type="text" name="TA_ZT_BXCL/STHBCC['+rows+']" value="" id="phone'+rows+'"/></td>'+
			  '</tr>'+
			  '<tr>'+
			    '<td>接团地点：</td>'+
				'<td><input type="text" name="TA_ZT_BXCL/JTDD['+rows+']" value="" id="driver'+rows+'"/></td>'+
				'<td>送团地点：</td>'+
				'<td><input type="text" name="TA_ZT_BXCL/STDD['+rows+']" value="" id="phone'+rows+'"/></td>'+
			  '</tr>'+
			  '<tr>'+
			  	'<td colspan="4" align="left">备注：<textarea rows="" cols="100" name="TA_ZT_BXCL/BZ['+rows+']"> </textarea></td>'+
			 '</tr>'+
			  '<tr >'+
			  	'<td>价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</td>'+
			  	'<td><input type="text" name="TA_ZT_BXCL/JG['+rows+']" value="0" onkeydown="checkNum();" class="jg" onchange="SumPrice();"/></td>'+
			  	'<td><font color="red">签单金额：</font></td>'+
			  	'<td><input type="text" name="TA_ZT_BXCL/QDJE['+rows+']" value="0" onkeydown="checkNum();" class="qd" onchange="SumPrice();"/>/元'+
					'<font color="red">现付金额：</font>'+
					'<input type="text" name="TA_ZT_BXCL/XFJE['+rows+']" value="0" onkeydown="checkNum();" class="xf" readonly="readonly"/>/元'+
					'<input type="hidden" name="TA_ZT_BXCL/BXZT['+rows+']" value="Y"/>'+
					'<input type="hidden" name="TA_ZT_BXCL/BXR['+rows+']" value="<%=sd.getString("userno") %>"/>'+
			  	'</td>'+
			  '</tr>'+
			'</table>');
	var linkage = new Linkage("dataSrc"+rows, "<%=request.getContextPath()%>/main/data/Carz.xml");
	linkage.init();
	rows++;
}

function queryDriver(vehID,idx){

	var obj=jQuery.ajax({url:"ztQueryDriverByVehID.?ta_car/car_id="+vehID,
		async:true,
		dataType:"json",
		cache:false,
		success:function(data){
			jQuery("input#driver"+idx).val(data[0].name);
			jQuery("input#phone"+idx).val(data[0].phone);
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) { 
            alert(errorThrown); 
   	 	}
	});
}

function delVeh() {
	if(rows > 0) {
		var idx = parseInt(rows-1);
		jQuery("#carDiv #car:eq("+idx+")").remove();
		rows--;
	}
}

function submitAddGroup(){
	document.groupInfoForm.submit();
	reload();
}
function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}

function ddVehBx(){

	document.vehform.action="ztSaveVehBx.";
	document.vehform.submit();
	reload();
}

function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		    event.returnValue=false;
}

function SumPrice(){

	var sumJG = 0;
	var sumQD = 0;
	var sumXF = 0;
	jQuery("#carDiv table").each(function (i){
		var jg = jQuery("input.jg:eq("+i+")").val();
		var qd = jQuery("input.qd:eq("+i+")").val();
		var xf = parseInt(jg)-parseInt(qd);
		jQuery("input.xf:eq("+i+")").val(xf);

		sumJG = parseInt(sumJG) + parseInt(jg);
		sumQD = parseInt(sumQD) + parseInt(qd);
		sumXF = parseInt(sumXF) + parseInt(xf);

		jQuery("#qdzj").val(sumQD);
		jQuery("#xfzj").val(sumXF);
		jQuery("#zj").val(sumJG);
	});
}
</script>


</head>

<body>
<form name="vehform" method="post">
<div id="lable"><span >车辆费用报销</span> <span class="showPointer" onclick="addVeh();" id="addCar">添加</span> <span class="showPointer" onclick="delVeh();"  id="delCar">删除</span></div>
<div id="bm-table">
<div id="carDiv">
<%
int rows = rd.getTableRowsCount("bxVehList");
String qdzj=rd.getStringByDI("bxclJDXXZJB","QDZJ",0);
String xfzj=rd.getStringByDI("bxclJDXXZJB","XFZJ",0);
String zj=rd.getStringByDI("bxclJDXXZJB","ZJ",0);
if(rows > 0){
	for(int i=0;i<rows;i++){
%>
<table class="datas select-car" id="car" width="100%">
  <tr>
	<td>选择车辆：</td>
	<td colspan="3"><input type="hidden" name="TA_ZT_BXCL/TID[<%=i %>]" value="<%=rd.getStringByDI("bxVehList","TID",i) %>"/>
	  <select name="TA_ZT_BXCL/SF[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
	  <select name="TA_ZT_BXCL/CITY[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
	  <select name="TA_ZT_BXCL/CD[<%=i %>]" id="team<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3" ></select>
	  <select name="TA_ZT_BXCL/CX[<%=i %>]" id="type<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="4" ></select>
	  <select name="TA_ZT_BXCL/CP[<%=i %>]" id="car_id<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="5" onchange="queryDriver(this.value,'<%=i %>');"></select>
	</td>
  </tr>
  <tr>
    <td>司机姓名：</td>
	<td><input type="text" name="TA_ZT_BXCL/SJXM[<%=i %>]" value="<%=rd.getStringByDI("bxVehList","SJXM",i) %>" id="driver<%=i %>"/></td>
	<td>司机电话：</td>
	<td><input type="text" name="TA_ZT_BXCL/SJDH[<%=i %>]" value="<%=rd.getStringByDI("bxVehList","SJDH",i) %>" id="phone<%=i %>"/></td>
  </tr>
  <tr>
  	<td>接团时间：</td>
	<td><input name="TA_ZT_BXCL/KSRQ[<%=i %>]" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" value="<%=rd.getStringByDI("bxVehList","BEGINTIME",i) %>"/></td>
	<td>送团时间：</td>
	<td><input name="TA_ZT_BXCL/JSRQ[<%=i %>]"  onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" value="<%=rd.getStringByDI("bxVehList","ENDTIME",i) %>"/></td>
  </tr>
  <tr>
    <td>接团航班车次：</td>
	<td><input type="text" name="TA_ZT_BXCL/JTHBCC[<%=i %>]" value="<%=rd.getStringByDI("bxVehList","JTHBCC",i) %>" id="driver<%=i %>"/></td>
	<td>送团航班车次：</td>
	<td><input type="text" name="TA_ZT_BXCL/STHBCC[<%=i %>]" value="<%=rd.getStringByDI("bxVehList","STHBCC",i) %>" id="phone<%=i %>"/></td>
  </tr>
  <tr>
    <td>接团地点：</td>
	<td><input type="text" name="TA_ZT_BXCL/JTDD[<%=i %>]" value="<%=rd.getStringByDI("bxVehList","JTDD",i) %>" id="driver<%=i %>"/></td>
	<td>送团地点：</td>
	<td><input type="text" name="TA_ZT_BXCL/STDD[<%=i %>]" value="<%=rd.getStringByDI("bxVehList","STDD",i) %>" id="phone<%=i %>"/></td>
  </tr>
  <tr>
  	<td colspan="4" align="left">备注：<textarea rows="" cols="100" name="TA_ZT_BXCL/BZ[<%=i %>]"> <%=rd.getStringByDI("bxVehList","BZ",i) %></textarea></td>
  </tr>
  <tr >
  	<td colspan="4" align="right">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：<input type="text" name="TA_ZT_BXCL/JG[<%=i %>]" value="<%=rd.getStringByDI("bxVehList","JG",i) %>" onkeydown="checkNum();" class="jg" onchange="SumPrice();"/>/元&nbsp;
  	<font color="red">签单金额：</font><input type="text" name="TA_ZT_BXCL/QDJE[<%=i %>]" value="<%=rd.getStringByDI("bxVehList","QDJE",i) %>" onkeydown="checkNum();" class="qd" onchange="SumPrice();"/>/元
		<font color="red">现付金额：</font>
		<input type="text" name="TA_ZT_BXCL/XFJE[<%=i %>]" value="<%=rd.getStringByDI("bxVehList","XFJE",i) %>" onkeydown="checkNum();" class="xf" readonly="readonly"/>/元
		<input type="hidden" name="TA_ZT_BXCL/BXZT[<%=i %>]" value="Y"/>
		<input type="hidden" name="TA_ZT_BXCL/BXR[<%=i %>]" value="<%=sd.getString("userno") %>"/>
  	</td>
  </tr>
</table>
<%	}
}else{%>
<table class="datas select-car" id="car" width="100%">
  <tr>
	<td>选择车辆：</td>
	<td colspan="3"><input type="hidden" name="TA_ZT_BXCL/TID[0]" value="<%=rd.getStringByDI("bxVehList","TID",0) %>"/>
	  <select name="TA_ZT_BXCL/SF[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
	  <select name="TA_ZT_BXCL/CITY[0]" id="city0" USEDATA="dataSrc0" SUBCLASS="2"></select>
	  <select name="TA_ZT_BXCL/CD[0]" id="team0" USEDATA="dataSrc0" SUBCLASS="3" ></select>
	  <select name="TA_ZT_BXCL/CX[0]" id="type0" USEDATA="dataSrc0" SUBCLASS="4" ></select>
	  <select name="TA_ZT_BXCL/CP[0]" id="car_id0" USEDATA="dataSrc0" SUBCLASS="5" onchange="queryDriver(this.value,'0');"></select>
	</td>
  </tr>
  <tr>
    <td>司机姓名：</td>
	<td><input type="text" name="TA_ZT_BXCL/SJXM[0]" value="" maxlength="10" id="driver0"/></td>
	<td>司机电话：</td>
	<td><input type="text" name="TA_ZT_BXCL/SJDH[0]" value="" maxlength="11" id="phone0"/></td>
  </tr>
  <tr>
  	<td>接团时间：</td>
	<td><input name="TA_ZT_BXCL/BEGINTIME[0]" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
	<td>送团时间：</td>
	<td><input name="TA_ZT_BXCL/ENDTIME[0]"  onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
  </tr>
  <tr>
    <td>接团航班车次：</td>
	<td><input type="text" name="TA_ZT_BXCL/JTHBCC[0]" value="" id="driver0"/></td>
	<td>送团航班车次：</td>
	<td><input type="text" name="TA_ZT_BXCL/STHBCC[0]" value="" id="phone0"/></td>
  </tr>
  <tr>
    <td>接团地点：</td>
	<td><input type="text" name="TA_ZT_BXCL/JTDD[0]" value="" id="driver0"/></td>
	<td>送团地点：</td>
	<td><input type="text" name="TA_ZT_BXCL/STDD[0]" value="" id="phone0"/></td>
  </tr>
  <tr>
  	<td colspan="4" align="left">备注：<textarea rows="" cols="100" name="TA_ZT_BXCL/BZ[0]"> </textarea></td>
  </tr>
  <tr >
  	<td colspan="4" align="right">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：<input type="text" name="TA_ZT_BXCL/JG[0]" value="0" onkeydown="checkNum();" class="jg" onchange="SumPrice();"/><font color="red">/元&nbsp;签单金额：</font><input type="text" name="TA_ZT_BXCL/QDJE[0]" value="0" onkeydown="checkNum();" class="qd" onchange="SumPrice();"/>/元
		<font color="red">现付金额：</font>
		<input type="text" name="TA_ZT_BXCL/XFJE[0]" value="0" onkeydown="checkNum();" class="xf" readonly="readonly"/>/元
		<input type="hidden" name="TA_ZT_BXCL/BXZT[0]" value="Y"/>
		<input type="hidden" name="TA_ZT_BXCL/BXR[0]" value="<%=sd.getString("userno") %>"/>
  	</td>
  </tr>
</table>
<%
}%>
</div>


<table class="datas" width="100%">
  <tr>
	<td><span>费用合计</span></td>
  </tr>
  <tr>
	<td align="right">
	  <font color="red">签单金额总计：</font>
	  <input type="text" id="qdzj" name="TA_TDBXZJXXB_ZT/BXCLQD" readonly="readonly" value="<%="".equals(qdzj)?"0":qdzj%>"/>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">现付金额总计：</font>
	  <input type="text" id="xfzj" name="TA_TDBXZJXXB_ZT/BXCLXF" readonly="readonly" value="<%="".equals(xfzj)?"0":xfzj%>"/>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">总计：</font>
	  <input type="text" id="zj" name="TA_TDBXZJXXB_ZT/CLHJ" readonly="readonly" value="<%="".equals(zj)?"0":zj%>"/>/元</td>
  </tr>
</table>
</div>
<div align="center" id="last-sub">
	<%String view = rd.getString("flag");  if(!"view".equals(view)){%>
	<input type="button" id="button" value="提交" onclick="ddVehBx();"/>
	<%} %>
	<input type="button" id="button" value="关闭" onclick="reload();"/>
</div>
</form>

</body>
</html>

<script type="text/javascript">
<%
if(rows > 0){
	for(int i=0;i<rows;i++){
%>
		var linkage = new Linkage("dataSrc<%=i %>", "<%=request.getContextPath()%>/main/data/Carz.xml");
		linkage.init();
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("bxVehList","SF",i) %>",1);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("bxVehList","CITY",i) %>",2);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("bxVehList","CD",i)%>",3);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("bxVehList","CX",i)%>",4);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("bxVehList","CP",i)%>",5);
<%	}
}else{%>
	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Carz.xml");
	linkage.init();
<%
}%>
</script>