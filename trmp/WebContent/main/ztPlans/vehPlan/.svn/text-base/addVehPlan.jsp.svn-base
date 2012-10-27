<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%
String groupID = rd.getStringByDI("TA_ZT_JHCLs","TID",0).equals("")?rd.getString("tid"):rd.getStringByDI("TA_ZT_JHCLs","TID",0);
String clqdje = rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDCLZJ",0);
String clxfje = rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFCLZJ",0);
String clzj = rd.getStringByDI("TA_TDJDXXZJB_ZTs","CLZJ",0);
%>

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
<title>车调计调</title>
<script type="text/javascript">
var rows;
jQuery(document).ready(function(){

	rows = jQuery("#carDiv table").size();
});

function addVeh(){

	jQuery("#carDiv").append('<table class="datas select-car" id="car" width="100%">'+
			  '<tr>'+
				'<td>选择车辆：</td>'+
				'<td colspan="3"><input type="hidden" name="TA_ZT_JHCL/TID['+rows+']" value="<%=groupID %>"/>'+
				  '<select name="TA_ZT_JHCL/SF['+rows+']" id="pro'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="1"></select>'+
				  '<select name="TA_ZT_JHCL/CITY['+rows+']" id="city'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="2"></select>'+
				  '<select name="TA_ZT_JHCL/CD['+rows+']" id="team'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="3" ></select>'+
				  '<select name="TA_ZT_JHCL/CX['+rows+']" id="type'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="4" ></select>'+
				  '<select name="TA_ZT_JHCL/CP['+rows+']" id="car_id'+rows+'" USEDATA="dataSrc'+rows+'" SUBCLASS="5"  onchange="queryDriver(this.value,'+rows+');"></select>'+
				'</td>'+
			  '</tr>'+
			  '<tr>'+
			    '<td>司机姓名：</td>'+
				'<td><input type="text" name="TA_ZT_JHCL/SJXM['+rows+']" value="" maxlength="10" id="driver'+rows+'"/></td>'+
				'<td>司机电话：</td>'+
				'<td><input type="text" name="TA_ZT_JHCL/SJDH['+rows+']" value="" maxlength="11" id="phone'+rows+'"/></td>'+
			  '</tr>'+
			  '<tr>'+
			  	'<td>接团时间：</td>'+
				'<td><input name="TA_ZT_JHCL/BEGINTIME['+rows+']" onFocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true})"/></td>'+
				'<td>送团时间：</td>'+
				'<td><input name="TA_ZT_JHCL/ENDTIME['+rows+']"  onFocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true})"/></td>'+
			  '</tr>'+
			  '<tr>'+
			    '<td>接团航班车次：</td>'+
				'<td><input type="text" name="TA_ZT_JHCL/JTHBCC['+rows+']" value="" /></td>'+
				'<td>送团航班车次：</td>'+
				'<td><input type="text" name="TA_ZT_JHCL/STHBCC['+rows+']" value=""  /></td>'+
			  '</tr>'+
			  '<tr>'+
			    '<td>接团地点：</td>'+
				'<td><input type="text" name="TA_ZT_JHCL/JTDD['+rows+']" value="" /></td>'+
				'<td>送团地点：</td>'+
				'<td><input type="text" name="TA_ZT_JHCL/STDD['+rows+']" value=""  /></td>'+
			  '</tr>'+
			  '<tr >'+
			  	'<td>价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</td>'+
			  	'<td><input type="text" name="TA_ZT_JHCL/JG['+rows+']" onkeydown="checkNum();" class="smallerInput jg" onchange="SumPrice();"/></td>'+
			  	'<td><font color="red">签单金额：</font></td>'+
			  	'<td><input type="text" name="TA_ZT_JHCL/QDJE['+rows+']" onkeydown="checkNum();" class="smallerInput qd" onchange="SumPrice();"/>/元'+
					'<font color="red">现付金额：</font>'+
					'<input type="text" name="TA_ZT_JHCL/XFJE['+rows+']" onkeydown="checkNum();" class="smallerInput xf" readonly="readonly"/>/元'+
					'<input type="hidden" name="TA_ZT_JHCL/JHZT['+rows+']" value="Y"/>'+
					'<input type="hidden" name="TA_ZT_JHCL/ZDR['+rows+']" value="<%=sd.getString("userno") %>"/>'+
			  	'</td>'+
			  '</tr>'+
			'</table>');
	var linkage = new Linkage("dataSrc"+rows, "<%=request.getContextPath()%>/main/data/Carz.xml");
	linkage.init();
	rows++;
}

function delVeh() {
	if(rows > 0) {
		var idx = parseInt(rows-1);
		jQuery("#carDiv #car:eq("+idx+")").remove();
		rows--;
	}

	SumPrice();
}

function submitAddGroup(){
	document.groupInfoForm.submit();
	reload();
}
function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}

function ddVehPlan(){
	
	jQuery(".jg").each(function (i){

		var jg = jQuery("input.jg:eq("+i+")").val();
		if(jg == ''){
			alert("请输入价格！");
			return false;
		}

		var car = jQuery("#car_id"+i).val();
		if('' == car){
			alert("请选择车辆！");
			return false;
		}
	});

	
	//return ;
	document.vehform.action="ztSaveVehPlan.";
	document.vehform.submit();
	reload();
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
		if('' == jg)
			jg = 0;
		var qd = jQuery("input.qd:eq("+i+")").val();
		if('' == qd)
			qd = 0;
		if(parseInt(jg) < parseInt(qd)) {
			jQuery("input.qd:eq("+i+")").val('0');
			qd = 0;
		}
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
<div id="lable"><span >车调计调</span> <span class="showPointer" onclick="addVeh();" id="addCar">添加</span> <span class="showPointer" onclick="delVeh();"  id="delCar">删除</span></div>
<div id="bm-table">
<div id="carDiv">
<%
int rows = rd.getTableRowsCount("TA_ZT_JHCLs");
if(rows > 0){
	for(int i=0;i<rows;i++){
%>
<table class="datas select-car" id="car" width="100%">
  <tr>
	<td>选择车辆：</td>
	<td colspan="3"><input type="hidden" name="TA_ZT_JHCL/TID[<%=i %>]" value="<%=groupID %>"/>
	  <select name="TA_ZT_JHCL/SF[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
	  <select name="TA_ZT_JHCL/CITY[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
	  <select name="TA_ZT_JHCL/CD[<%=i %>]" id="team<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3" ></select>
	  <select name="TA_ZT_JHCL/CX[<%=i %>]" id="type<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="4" ></select>
	  <select name="TA_ZT_JHCL/CP[<%=i %>]" id="car_id<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="5" onchange="queryDriver(this.value,'<%=i %>');"></select>
	</td>
  </tr>
  <tr>
    <td>司机姓名：</td>
	<td><input type="text" name="TA_ZT_JHCL/SJXM[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHCLs","SJXM",i) %>" maxlength="10" id="driver<%=i %>"/></td>
	<td>司机电话：</td>
	<td><input type="text" name="TA_ZT_JHCL/SJDH[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHCLs","SJDH",i) %>" maxlength="11" id="phone<%=i %>"/></td>
  </tr>
  <tr>
  	<td>接团时间：</td>
	<td><input name="TA_ZT_JHCL/BEGINTIME[<%=i %>]" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" value="<%=rd.getStringByDI("TA_ZT_JHCLs","BEGINTIME",i) %>"/></td>
	<td>送团时间：</td>
	<td><input name="TA_ZT_JHCL/ENDTIME[<%=i %>]"  onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})" value="<%=rd.getStringByDI("TA_ZT_JHCLs","ENDTIME",i) %>"/></td>
  </tr>
  <tr>
    <td>接团航班车次：</td>
	<td><input type="text" name="TA_ZT_JHCL/JTHBCC[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHCLs","JTHBCC",i) %>"  /></td>
	<td>送团航班车次：</td>
	<td><input type="text" name="TA_ZT_JHCL/STHBCC[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHCLs","STHBCC",i) %>"  /></td>
  </tr>
  <tr>
    <td>接团地点：</td>
	<td><input type="text" name="TA_ZT_JHCL/JTDD[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHCLs","JTDD",i) %>"  /></td>
	<td>送团地点：</td>
	<td><input type="text" name="TA_ZT_JHCL/STDD[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHCLs","STDD",i) %>"  /></td>
  </tr>
  <tr >
  	<td>价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</td>
  	<td><input type="text" name="TA_ZT_JHCL/JG[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHCLs","jg",i) %>" onkeydown="checkNum();" class="smallerInput jg" onchange="SumPrice();"/></td>
  	<td><font color="red">签单金额：</font></td>
  	<td><input type="text" name="TA_ZT_JHCL/QDJE[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHCLs","QDJE",i) %>" onkeydown="checkNum();" class="smallerInput qd" onchange="SumPrice();"/>/元
		<font color="red">现付金额：</font>
		<input type="text" name="TA_ZT_JHCL/XFJE[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHCLs","XFJE",i) %>" onkeydown="checkNum();" class="smallerInput xf" readonly="readonly"/>/元
		<input type="hidden" name="TA_ZT_JHCL/JHZT[<%=i %>]" value="Y"/>
		<input type="hidden" name="TA_ZT_JHCL/ZDR[<%=i %>]" value="<%=sd.getString("userno") %>"/>
  	</td>
  </tr>
</table>
<%	}
}else{%>
<table class="datas select-car" id="car" width="100%">
  <tr>
	<td>选择车辆：</td>
	<td colspan="3"><input type="hidden" name="TA_ZT_JHCL/TID[0]" value="<%=groupID %>"/>
	  <select name="TA_ZT_JHCL/SF[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
	  <select name="TA_ZT_JHCL/CITY[0]" id="city0" USEDATA="dataSrc0" SUBCLASS="2"></select>
	  <select name="TA_ZT_JHCL/CD[0]" id="team0" USEDATA="dataSrc0" SUBCLASS="3" ></select>
	  <select name="TA_ZT_JHCL/CX[0]" id="type0" USEDATA="dataSrc0" SUBCLASS="4" ></select>
	  <select name="TA_ZT_JHCL/CP[0]" id="car_id0" USEDATA="dataSrc0" SUBCLASS="5" onchange="queryDriver(this.value,'0');"></select>
	</td>
  </tr>
  <tr>
    <td>司机姓名：</td>
	<td><input type="text" name="TA_ZT_JHCL/SJXM[0]" value="" maxlength="10" id="driver0"/></td>
	<td>司机电话：</td>
	<td><input type="text" name="TA_ZT_JHCL/SJDH[0]" value="" maxlength="11" id="phone0"/></td>
  </tr>
  <tr>
  	<td>接团时间：</td>
	<td><input name="TA_ZT_JHCL/BEGINTIME[0]" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/></td>
	<td>送团时间：</td>
	<td><input name="TA_ZT_JHCL/ENDTIME[0]"  onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/></td>
  </tr>
  <tr>
    <td>接团航班车次：</td>
	<td><input type="text" name="TA_ZT_JHCL/JTHBCC[0]" value=""  /></td>
	<td>送团航班车次：</td>
	<td><input type="text" name="TA_ZT_JHCL/STHBCC[0]" value=""  /></td>
  </tr>
  <tr>
    <td>接团地点：</td>
	<td><input type="text" name="TA_ZT_JHCL/JTDD[0]" value=""  /></td>
	<td>送团地点：</td>
	<td><input type="text" name="TA_ZT_JHCL/STDD[0]" value=""  	/></td>
  </tr>
  <tr >
  	<td>价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</td>
  	<td><input type="text" name="TA_ZT_JHCL/JG[0]" onkeydown="checkNum();" class="smallerInput jg" onchange="SumPrice();"/></td>
  	<td><font color="red">签单金额：</font></td>
  	<td><input type="text" name="TA_ZT_JHCL/QDJE[0]" onkeydown="checkNum();" class="smallerInput qd" onchange="SumPrice();"/>/元
		<font color="red">现付金额：</font>
		<input type="text" name="TA_ZT_JHCL/XFJE[0]" onkeydown="checkNum();" class="smallerInput xf" readonly="readonly"/>/元
		<input type="hidden" name="TA_ZT_JHCL/JHZT[0]" value="Y"/>
		<input type="hidden" name="TA_ZT_JHCL/ZDR[0]" value="<%=sd.getString("userno") %>"/>
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
	  <input type="text" id="qdzj" name="TA_TDJDXXZJB_ZT/QDCLZJ" readonly="readonly" value="<%="".equals(clqdje)?"0":clqdje %>" class="smallerInput"/>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">现付金额总计：</font>
	  <input type="text" id="xfzj" name="TA_TDJDXXZJB_ZT/XFCLZJ" readonly="readonly" value="<%="".equals(clxfje)?"0":clxfje %>" class="smallerInput"/>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">总计：</font>
	  <input type="text" id="zj" name="TA_TDJDXXZJB_ZT/CLZJ" readonly="readonly" value="<%="".equals(clzj)?"0":clzj %>" class="smallerInput"/>/元</td>
  </tr>
</table>
</div>
<div align="center" id="last-sub">
<%
String flag = rd.getString("flag");
if("".equals(flag)){
%>
	<input type="button" id="button" value="提交" onclick="ddVehPlan();"/>
	<input type="button" id="button" value="关闭" onclick="reload();"/>
<%
}%>
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
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("TA_ZT_JHCLs","SF",i) %>",1);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("TA_ZT_JHCLs","CITY",i)%>",2);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("TA_ZT_JHCLs","CD",i)%>",3);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("TA_ZT_JHCLs","CX",i)%>",4);
		linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("TA_ZT_JHCLs","CP",i)%>",5);
<%	}
}else{%>
	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Carz.xml");
	linkage.init();
<%
}%>
</script>