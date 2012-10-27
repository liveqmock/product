<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
int onRows = rd.getTableRowsCount("rsInsurance");
int  infoRows= rd.getTableRowsCount("TA_ZT_INSURANCEs");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>

<title>报名订单修改</title>
<%
int rows = rd.getTableRowsCount("rsOrderPrice");
%>
<script type="text/javascript">
$(function(){
	$("#insuranceList select").change(function(){
	    var val=$(this).val();
	    var val1 = $(this).attr("id").split('-')[1];
	    $("#bxid"+val1).val(val.split('-')[0]);
        //$("#bxmc"+val1).val(val.split('-')[1]);
        $("#bxcb"+val1).val(val.split('-')[2]);
        $("#jyjg"+val1).val(val.split('-')[3]);
        $("#cbfs1"+val1).val(val.split('-')[4]);
        $("#bxrs"+val1).val('');
        if(val.split('-')[4]==1){
        	$("#cbfs"+val1).text("按天数计算");
        }else if(val.split('-')[4]==0){
        	$("#cbfs"+val1).text("按实际成本计算");
        }
        
	    $("#insuranceList select[id!="+$(this).attr("id")+"]").each(function(){
	          if(val==$(this).val()&&'9999'!=val){
	                alert("该保险已经存在！请重新选择");
	                $("#select-"+val1+"-").val('9999');
	                $("#bxid"+val1).val('');
	                //$("#bxmc"+val1).val('');
	                $("#bxcb"+val1).val('');
	                $("#jyjg"+val1).val('');
	                $("#cbfs"+val1).text('');
	                $("#cbfs1"+val1).val('');
	                $("#bxrs"+val1).val('');
	          }
	    });
	});

	$(".insuranceAdd").click(function(){
		var dRows = $(".insurances").size();
		$("#insuranceList").append('<tr class="insurances" id="insurances'+dRows+'">'+
				'<td>'+
				'<img src="<%=request.getContextPath()%>/images/printClose.gif" class="delInsurances" onclick="delInsurances('+dRows+');"/>&nbsp;'+
				'<select class="changeInsurance" id="select-'+dRows+'-">'+
				 '<option value="9999">--未指定--</option>'+
					<%
					for(int i = 0; i< infoRows; i++){
						String bxId = rd.getStringByDI("TA_ZT_INSURANCEs", "ID", i);
						String bxMc = rd.getStringByDI("TA_ZT_INSURANCEs", "BXLBMC", i);
						String bxCb = rd.getStringByDI("TA_ZT_INSURANCEs", "BXCB", i);
						String jyJg = rd.getStringByDI("TA_ZT_INSURANCEs", "JYJG", i);
						String cbFs = rd.getStringByDI("TA_ZT_INSURANCEs", "CBFS", i);
					%>
						'<option value="<%=bxId%>-<%=bxMc%>-<%=bxCb%>-<%=jyJg%>-<%=cbFs%>"><%=bxMc %></option>'+
					<%
					}
					%>
				'</select>'+
				'<input type="hidden" id="cbfs1'+dRows+'"  class="cbfs" >'+
				'<input type="hidden" name="TA_ZT_GINSURANCE/INSURANCETYPE['+dRows+']" id="bxid'+dRows+'" /></td>'+
				'<td><input type="text"  id="jyjg'+dRows+'" onchange="addM()" name="TA_ZT_GINSURANCE/INSURANCEPRICE['+dRows+']" class="bxjg" readonly="readonly"/></td>'+
				'<td class="thjsj" style="display: none;"><input type="text" id="bxcb'+dRows+'" class="bxcb" name="TA_ZT_GINSURANCE/INSURANCECOST['+dRows+']" readonly="readonly"/></td>'+
				'<td id="cbfs'+dRows+'"></td>'+
				'<td><input type="text" onchange="addM()" class="bxrs" onkeydown="checkNum()" name="TA_ZT_GINSURANCE/PERSONS['+dRows+']" id="bxrs'+dRows+'"/></td>'+
			'</tr>');
			$("#insuranceList select").bind("change", function(){
			 	var val=$(this).val();
			    var val1 = $(this).attr("id").split('-')[1];
			    $("#bxid"+val1).val(val.split('-')[0]);
		        //$("#bxmc"+val1).val(val.split('-')[1]);
		        $("#bxcb"+val1).val(val.split('-')[2]);
		        $("#jyjg"+val1).val(val.split('-')[3]);
		        $("#cbfs1"+val1).val(val.split('-')[4]);
		        $("#bxrs"+val1).val('');
		        if(val.split('-')[4]==1){
		        	$("#cbfs"+val1).text("按天数计算");
		        }if(val.split('-')[4]==0){
		        	$("#cbfs"+val1).text("按实际成本计算");
		        }
		        
			$("#insuranceList select[id!="+$(this).attr("id")+"]").each(function(){
		          if(val==$(this).val()&&'9999'!=val){
		                alert("该保险已经存在！请重新选择");
		                $("#select-"+val1+"-").val('9999');
		                $("#bxid"+val1).val('');
		                //$("#bxmc"+val1).val('');
		                $("#bxcb"+val1).val('');
		                $("#jyjg"+val1).val('');
		                $("#cbfs"+val1).text('');
		                $("#cbfs1"+val1).val('');
		                $("#bxrs"+val1).val('');
		          }
		    });
		});
			
	});

		$(".insuranceDel").click(function(){
			var iRows = $(".insurances").size()-1;
			$(".insurances:eq("+parseInt(iRows)+")").remove();
			addM();
		});
});

function delInsurances(nRows){
	$("#insurances"+nRows).remove();
	addM();
}





$(function(){
	$(".personNum").change(function(){
		var tmps = 0;
		$("input.personNum").each(
			function(){
				tmps+=parseFloat(this.value);
			}
		);
		var a=tmps-$("div.personInfo").size()>0?tmps-$("div.personInfo").size():Math.abs(tmps-$("div.personInfo").size());
		if(tmps-$("div.personInfo").size()>0) {
		  for(var i=0;i<a;i++){
		 	var row = $("div.personInfo").size();
		 	$("#tourist").append('<div class="personInfo" align=""left"">姓名：<input name="TA_ZT_VISITOR/VISITOR_NM['+parseInt(row)+']" class="smallInput"/>'+
					'&nbsp;性别：<select name="TA_ZT_VISITOR/SEX['+parseInt(row)+']"> '+
					'<option value="0">不详</option>'+
					'<option value="1" selected="selected">男</option>'+
					'<option value="2">女</option>'+
					'</select>'+
					'&nbsp;证件号：<input name="TA_ZT_VISITOR/LICENSE_NO['+parseInt(row)+']"/>'+
					'&nbsp;有效期：<input name="TA_ZT_VISITOR/ZJYXQ['+parseInt(row)+']" class="smallInput"/>'+
					'签证地：&nbsp;&nbsp;'+
					'<input name="TA_ZT_VISITOR/PASSPORTPS['+parseInt(row)+']" type="text" class="smallInput"/>'+
					'&nbsp;出生地：'+
					'<input name="TA_ZT_VISITOR/BORNSITE['+parseInt(row)+']" type="text" class="smallInput"/>'+
					'&nbsp;电话：<input name="TA_ZT_VISITOR/TEL['+parseInt(row)+']" class="smallerInput"/>'+
					'&nbsp;地址：&nbsp;'+
					'<input name="TA_ZT_VISITOR/ADDRESS['+parseInt(row)+']" type="text" class="smallerInput"/>'+
					'&nbsp;&nbsp;<input name="TA_ZT_VISITOR/ISLEADER['+parseInt(row)+']" type="checkbox" value="1" class="zylxr" onclick="zylxr('+parseInt(row)+')"/>'+
					'&nbsp;主'+
					'&nbsp;<input name="TA_ZT_VISITOR/ISCHILD['+parseInt(row)+']" value="1" type="checkbox"/>童&nbsp;'+
					'<input name="TA_ZT_VISITOR/ISINSURANCE['+parseInt(row)+']" type="checkbox" value="1" class="bxbox"/>保&nbsp;'+
					'</div>');
		  }
		}else{
			for(var d=a;d>0;d--){
				var idx=parseFloat($("div.personInfo").size()-1);
				$("div.personInfo:eq("+idx+")").remove();
				}
			}
		addM();
	});
	$("#showVisitor").hover(function(){
		$(this).css({cursor:'pointer'});
		});
	$("#showVisitor").click(function(){
		$("div.personInfo").toggle();
		});
});


function addM(){
	var addMCount = $("#addMCount").val();
	if('' == addMCount)
		addMCount = 0;
	var ysk = 0;//应收款
	var rows = <%=rows %>;
	var dfczj=0;
	for(var i=0;i<rows;i++){
		var pNum = document.getElementById("person"+i).value;
		if(''==pNum)
			pNum = 0;
		var msj = document.getElementById("ms"+i).value;
		if('' == msj)
			msj = 0;
		ysk += parseFloat(msj)*parseFloat(pNum);
	}

	$(".dfc").each(function(i){
		var dfcjg=this.value;
		if('' == dfcjg)
			dfcjg = 0;
		var dfcsl=$(".dfcsl:eq("+i+")").val();
		if('' == dfcsl)
			dfcsl = 0;
		dfczj+=parseFloat(dfcjg*dfcsl);
		});
	var bxzj=0;
	var days= $(".days").text();
	if('' == days)
		days = 0;
	$(".bxjg").each(function(i) {
		var bxjg=$(".bxjg:eq("+i+")").val();
		if('' == bxjg)
			bxjg = 0;
		var rs=$(".bxrs:eq("+i+")").val();
		if('' == rs)
			rs = 0;
		var cbfs = $(".cbfs:eq("+i+")").val();
		if(1 == cbfs){
			bxzj+=parseFloat(bxjg*rs*days);
		}else{
			bxzj+=parseFloat(bxjg*rs);
		}
	});
	
	var countNum = parseFloat(ysk)+parseFloat(bxzj)+parseFloat(dfczj)+parseFloat(addMCount);
	$("#yinSk").val(Math.round(countNum*100)/100);
	
	$(".bxbox").each(function(){
		$(this).removeAttr("checked");	
	});
	var maxBx = 0;
	$(".bxrs").each(function(i){
		if($(".bxrs:eq("+i+")").val()>=$(".bxrs:eq("+(i-1)+")").val()){
			maxBx=$(".bxrs:eq("+i+")").val();
		}
	});
	for(var i = 0; i < maxBx; i++){
		$(".bxbox:eq("+i+")").attr("checked","checked");
	}
}


<%
int rows1 = rd.getTableRowsCount("TA_ZT_GATHERs");
%>
var arrays = new Array(<%=rows1%>);
<%
for(int i=0;i<rows1;i++) {
	String gatherID = rd.getStringByDI("TA_ZT_GATHERs","GATHER_ID",i);
	String gahter = rd.getStringByDI("TA_ZT_GATHERs","GATHER",i);
	String gatherTime = rd.getStringByDI("TA_ZT_GATHERs","GATHER_TIME",i);
	String addMC = rd.getStringByDI("TA_ZT_GATHERs","ADD_M_COUNT",i);
%>
	arrays[<%=i %>] = new Array(3);
	arrays[<%=i %>][0] = "<%=gatherID %>";
	arrays[<%=i %>][1] = "<%=gatherTime %>";
	arrays[<%=i %>][2] = "<%=addMC %>";
				
<%
}%>
function selectChange(){
	var value = document.getElementById("gatherID").value;
	var len = arrays.length;
	for(var k=0;k<len;k++){
		if(arrays[k][0] == value){
			document.getElementById("gatherTime").value=arrays[k][1];
			document.getElementById("addMCount").value=arrays[k][2];
		}
	}
}
function doSub(){
	var varNum=0;
	$(".changeInsurance").each(function(i){
		if($(this).val()=='9999')
			varNum+=1;
	});
	if(varNum>0){
		alert("未选择保险类型的请删除");
		return false;
	}
	if($("#gatherID").val()==""){
		alert("请选择集合地点");
		return false;
		}
	var a;
	$(".zylxr").each(function(){
		if($(this).attr('checked')==true){
			a=1;
			}
		});
	if(a==0){
		alert("请选择1位主要联系人");
		return false;
		}
	<%
	if("Y".equals(rd.getString("dzzt"))){
	%>
		window.location.href=="updateOrder.?wtgDz=Y";
	<%
	}else{
	%>
	document.signUPForm.submit();
	<%
	}
	%>
}
function zylxr(num){
	$(".zylxr").each(function(i){
		if(num!=i){
			$(".zylxr:eq("+i+")").removeAttr("checked");
		}
	});
}

function printDD(lb){
	if('1' == lb)
		window.open('viewConfirmRegedit.?ddh=<%=rd.getStringByDI("rsOrderBase","ddh",0) %>&DMSM/JTGJ=2','确认单','width=800, height=600, top=50, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	if('2' == lb)
		window.open("viewJourney.?ddh=<%=rd.getStringByDI("rsOrderBase","ddh",0) %>&DMSM/JTGJ=2",'行程单','width=800, height=600, top=50, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	if('3' == lb)
		window.open("viewInform.?ddh=<%=rd.getStringByDI("rsOrderBase","ddh",0) %>&DMSM/JTGJ=2",'出团任务单','width=800, height=600, top=50, left=270, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
}
function cek(){
	if($(".jgqdval").val()==0){
		$(".jgqd").attr("colspan","7");
		$(".bxqd").attr("colspan","5");
		$(".thjsj").css({display:"block"});
		$(".thjsj1").css({display:"block"});
		$("#insuranceAddDel").css({display:"none"});
		$(".jgqdval").val(1);
	}else{
		$(".jgqd").attr("colspan","6");
		$(".bxqd").attr("colspan","3");
		$(".thjsj").css({display:"none"});
		$(".thjsj1").css({display:"none"});
		$("#insuranceAddDel").css({display:"block"});
		$(".jgqdval").val(0);
	}
}
</script>
</head>
<body>
<form action="updateOrder.?wtgDz=N" name="signUPForm" method="post">
<input type="hidden" name="tid" value="<%=rd.getStringByDI("rsOrderBase","id",0) %>"/>
<input type="hidden" name="isDoVehPlan" value="<%=rd.getString("isDoVehPlan") %>"/>
<input type="hidden" name="TA_ZT_YKORDER/ID" value="<%=rd.getStringByDI("rsOrderBase","ddh",0) %>"/>
<input type="hidden" name="TA_ZT_YKORDER/ID[0]@oldValue" value="<%=rd.getStringByDI("rsOrderBase","ddh",0) %>"/>
<div style="width: 99.7%">
<div id="lable"><span>报名订单修改(订单号：<%=rd.getStringByDI("rsOrderBase","ddh",0) %>)</span></div>
<div id="bm-table">
	<table class="datas" width="98%">
		<tr>
			<td colspan="4"><span>线路基本信息</span></td>
		</tr>
		<tr>
			<td width="10%">线路名称：</td>
			<td width="35%"><%=rd.getStringByDI("rsOrderBase","line_name",0) %></td>
			<td width="10%">团队编号：</td>
			<td><%=rd.getStringByDI("rsOrderBase","id",0) %>&nbsp;&nbsp;天数：（<label class="days"><%=rd.getStringByDI("rsOrderBase","days",0) %></label>）</td>
		</tr>
		<tr>
			<td >总人数：</td>
			<td ><%=rd.getStringByDI("rsOrderBase","maxpersoncount",0) %> （可成团人数：<%=rd.getStringByDI("rsOrderBase","minpersoncount",0) %>）人</td>
			<td >可售人数：</td>
			<td ><%=rd.getStringByDI("rsOrderBase","spare",0) %>人</td>
		</tr>
		<%
		for(int i=0;i<rd.getTableRowsCount("jhcl");i++){
			String cllx = rd.getStringByDI("jhcl","zws",i);
			String syzws = rd.getStringByDI("jhcl","syzws",i);
			String cp = rd.getStringByDI("jhcl","car_code",i);
	%>
		<tr>
			<td>计划车型：</td>
			<td><%=cllx+" "+cp %></td>
			<td>剩余座位数：</td>
			<td><font color="red"><%=syzws %></font></td>
		</tr>
	<%
		}
	%>
	</table>
</div>	
<div id="bm-table">
<table class="datas" style="border: solid 1px #29A39F;" align="center" id="jgqd" width="98%">
	<tr>
		<td colspan="6" align="left" style="color: #006666;font-weight: bold;margin-left: 3px;" class="jgqd">价格清单<input type="hidden" class="jgqdval" value="0"/></td>
		<td style="color: #006666;font-weight: bold;margin-left: 3px;" onclick="cek();">显示价格</td>
	</tr>
	<tr id="first-tr" >
		<td >价格类型</td>
		<td >门市价</td>
		<td class="thjsj" style="display: none;">同行结算价</td>
		<td >人数</td>
		<td>单房差*个数</td>
		<td >描述</td>
		<td >加价</td>
		<td>占座</td>
	</tr>
<%

for(int i=0;i<rows;i++){
	
	String priceType = rd.getStringByDI("rsOrderPrice","price_type",i);
	String ms = rd.getStringByDI("rsOrderPrice","price_ms",i);
	String th = rd.getStringByDI("rsOrderPrice","price_th",i);
	String zd = rd.getStringByDI("rsOrderPrice","price_zd",i);
	String remark = rd.getStringByDI("rsOrderPrice","remark",i);
	String personCount = rd.getStringByDI("rsOrderPrice","person_count",i);
	String id = rd.getStringByDI("rsOrderPrice","id",i);
	
	String SINGLE_ROOM = rd.getStringByDI("rsOrderPrice","SINGLE_ROOM",i);
	String SINGLE_ROOMS = rd.getStringByDI("rsOrderPrice","SINGLE_ROOMS",i);
	if(rd.getStringByDI("rsOrderBase","ddh",0).equals(id)) {
%>
	<tr>
		<td>
		<input type="hidden" name="TA_ZT_GORDERPRICE/price_type[<%=i %>]" value="<%=priceType %>"/>
<%
		for(int j=0;j<rd.getTableRowsCount("jglx");j++){
			String dmz = rd.getStringByDI("jglx","dmz",j);
			String dmsm1 = rd.getStringByDI("jglx","dmsm1",j);
			if(dmz.equals(priceType)){
%>
<%=dmsm1 %>
<%			}
		}
		%></td>
		<td class="showLayer">￥<%=ms %><input type="hidden" name="TA_ZT_GORDERPRICE/price_ms[<%=i %>]" value="<%=ms %>" id="ms<%=i %>"/></td>
		<td class="thjsj1" style="display: none;">￥<%=th %><input type="hidden" name="TA_ZT_GORDERPRICE/price_th[<%=i %>]" value="<%=th %>"/>
					  <input type="hidden" name="TA_ZT_GORDERPRICE/price_zd[<%=i %>]" value="<%=zd %>"/>
		</td>
		<td><input  type="text" name="TA_ZT_GORDERPRICE/PERSON_COUNT[<%=i %>]" value="<%=personCount %>" id="person<%=i %>" onkeydown="checkNum()" class="smallInput personNum"/></td>
		<td><input type="text" name="TA_ZT_GORDERPRICE/SINGLE_ROOM[<%=i %>]" class="smallInput dfc" value="<%=SINGLE_ROOM %>" readonly="readonly"/>/元<font color="red">*</font>
			<input name="TA_ZT_GORDERPRICE/SINGLE_ROOMS[<%=i %>]" class="smallInput dfcsl" onkeydown="checkNum()" value="<%=SINGLE_ROOMS %>" onchange="addM()">/个</td>
		<td><%=remark %><input type="hidden" name="TA_ZT_GORDERPRICE/remark[<%=i %>]" value="<%=remark %>"/></td>
		<td>否</td>
		<td>占座（1座）</td>
	</tr>
<%	}
}%>
</table>
<table class="datas" style="border: solid 1px #29A39F;" align="center"  id="insuranceList">
	<tr>
		<td colspan="3" align="left" style="color: #006666;font-weight: bold;margin-left: 3px;" class="bxqd">保险清单</td><td id="insuranceAddDel" align="right" style="color: #006666;font-weight: bold;margin-right: 3px;">	
		<a href="###" class="insuranceAdd"><img alt="添加保险" src="<%=request.getContextPath() %>/images/add.gif"/> 添加</a>&nbsp;&nbsp;
       <a href="###" id="insuranceDel"><img alt="添加保险" src="<%=request.getContextPath() %>/images/del.gif"/> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
	<tr id="first-tr" >
		<td >保险类型</td>
		<td >保险价格</td>
		<td class="thjsj" style="display: none;">保险成本</td>
		<td>成本方式</td>
		<td >人数</td>
	</tr>
<%
		for(int i = 0; i < onRows; i++){
			String inId = rd.getStringByDI("rsInsurance", "bxId", i);
		%>
		<tr class="insurances" id="insurances<%=i%>">
			<td>
			<img src="<%=request.getContextPath()%>/images/printClose.gif" class="delInsurances" onclick="delInsurances(<%=i%>);"/>
			<select class="changeInsurance" id="select-<%=i%>-">
			 <option value="9999">--未指定--</option>
				<%
				for(int j = 0; j< infoRows; j++){
					String bxId = rd.getStringByDI("TA_ZT_INSURANCEs", "ID", j);
					String bxMc = rd.getStringByDI("TA_ZT_INSURANCEs", "BXLBMC", j);
					String bxCb = rd.getStringByDI("TA_ZT_INSURANCEs", "BXCB", j);
					String jyJg = rd.getStringByDI("TA_ZT_INSURANCEs", "JYJG", j);
					String cbFs = rd.getStringByDI("TA_ZT_INSURANCEs", "CBFS", j);
					if(bxId.equals(inId)){
				%>
					<option value="<%=bxId%>-<%=bxMc%>-<%=bxCb%>-<%=jyJg%>-<%=cbFs%>" selected="selected"><%=bxMc %></option>
				<%
					}else{
				%>
					<option value="<%=bxId%>-<%=bxMc%>-<%=bxCb%>-<%=jyJg%>-<%=cbFs%>"><%=bxMc %></option>
				<%} }%>
			</select><input type="hidden" id="cbfs1<%=i%>"  class="cbfs" value="<%=rd.getStringByDI("rsInsurance", "bxFs", i)%>">
				<input type="hidden" name="TA_ZT_GINSURANCE/INSURANCETYPE[<%=i%>]" value="<%=rd.getStringByDI("rsInsurance", "bxId", i)%>" id="bxid<%=i%>" /></td>
				<td><input type="text"  id="jyjg<%=i%>" value="<%=rd.getStringByDI("rsInsurance", "bxJyjg", i) %>"  onchange="addM()" onkeydown="checkNum1(this)" name="TA_ZT_GINSURANCE/INSURANCEPRICE[<%=i %>]" class="bxjg" readonly="readonly"/></td>
				<td class="thjsj" style="display: none;"><input type="text"  id="bxcb<%=i%>" class="bxcb" onkeydown="checkNum1(this)" name="TA_ZT_GINSURANCE/INSURANCECOST[<%=i %>]" value="<%=rd.getStringByDI("rsInsurance", "bxCb", i) %>" readonly="readonly"/></td>
				<td id="cbfs<%=i%>"><%if("1".equals(rd.getStringByDI("rsInsurance", "bxFs", i))){out.print("按天计算");}else{out.print("按实际成本计算");} %></td>
				<td><input  type="text" onchange="addM()" class="bxrs" onkeydown="checkNum()" value="<%=rd.getStringByDI("rsInsurance", "bxRs", i) %>" name="TA_ZT_GINSURANCE/PERSONS[<%=i %>]" id="bxrs<%=i %>"/></td>
		</tr>
		<% }%>
</table>
<table class="datas" style="border: solid 1px #29A39F;" align="center" width="98%">
<tr><td></td></tr>
<tr id="first-tr">
<td>集合地点</td>
</tr>
	<tr>
		<td align="left" colspan="7">集合地点：
		  <select name="TA_ZT_YKORDER/GATHER" id="gatherID" onchange="selectChange();">
		  	<option value="-1">******请选择上车地点******</option>
		<%
		String gather = rd.getStringByDI("rsOrderBase","gather_id",0);
		for(int i=0;i<rd.getTableRowsCount("TA_ZT_GATHERs");i++) {
			String gatherID = rd.getStringByDI("TA_ZT_GATHERs","GATHER_ID",i);
			String gatherNm = rd.getStringByDI("TA_ZT_GATHERs","GATHER",i);
			String gatherTime = rd.getStringByDI("TA_ZT_GATHERs","GATHER_TIME",i);
		%>
		  	<option value="<%=gatherID %>" <%if(gatherID.equals(gather)){ %>selected="selected"<%} %> >
		  	  <%=gatherNm %>---<%=gatherTime %>
		  	</option>
		<%
		}
		%>
		  </select>&nbsp;&nbsp;
		时间：<input name="TA_ZT_YKORDER/GATHER_TIME" id="gatherTime" value="<%=rd.getStringByDI("rsOrderBase","gather_time",0) %>" readonly="readonly"/>&nbsp;&nbsp;
		加价：<input name="TA_ZT_YKORDER/ADD_M_COUNT" id="addMCount" value="<%=rd.getStringByDI("rsOrderBase","add_m_count",0) %>" readonly="readonly" value="0"/>
		</td>
	</tr>
</table>

</div>
<div id="bm-table">
<table class="datas" width="98%">
	<tr>
		<td ><span>订单收款</span></td>
	</tr>
	<tr>
		<td align="right">
			房间数：<input name="TA_ZT_YKORDER/ROOMS" type="text" value="<%=rd.getStringByDI("rsOrderBase","rooms",0) %>" />&nbsp;&nbsp;&nbsp;
			应收款：<input name="TA_ZT_YKORDER/YIN_SK" type="text" value="<%=rd.getStringByDI("rsOrderBase","yin_sk",0) %>" id="yinSk" readonly="readonly"/>&nbsp;&nbsp;&nbsp;
			已收款：<input name="TA_ZT_YKORDER/YI_SK" type="text" value="<%=rd.getStringByDI("rsOrderBase","yi_sk",0) %>"/>
			<input type="checkbox" name="TA_ZT_YKORDER/ISCONFIRM" value="1" <%if(rd.getStringByDI("rsOrderBase","ISCONFIRM",0).equals("1")){ %> checked="checked" <%} %>>确认收款
		</td>
	</tr>
</table>
</div>
<div id="tourist" style="width: 99.8%">
	<div><span>游客信息    <span id="showVisitor">显示</span></span></div>
	<div><font color="red">注：证件号码出境或飞机团游客必填，需要是护照或身份证等有效证件；主要联系人必须至少选择一个</font></div>
<%
int row = rd.getTableRowsCount("rsVisitor");
for(int i=0;i<row;i++){
	String vName = rd.getStringByDI("rsVisitor","visitor_nm",i);
	String sex = rd.getStringByDI("rsVisitor","sex",i);
	String sfzmhm = rd.getStringByDI("rsVisitor","LICENSE_NO",i);
	String TEL = rd.getStringByDI("rsVisitor","TEL",i);
	String ADDRESS = rd.getStringByDI("rsVisitor","ADDRESS",i);
	String PASSPORTPS = rd.getStringByDI("rsVisitor","PASSPORTPS",i);
	String BORNSITE = rd.getStringByDI("rsVisitor","BORNSITE",i);
	String ISLEADER = rd.getStringByDI("rsVisitor","ISLEADER",i);
	String ISCHILD = rd.getStringByDI("rsVisitor","ISCHILD",i);
	String ISINSURANCE = rd.getStringByDI("rsVisitor","ISINSURANCE",i);
	String m_id = rd.getStringByDI("rsVisitor","m_id",i);
	String zjyxq = rd.getStringByDI("rsVisitor","zjyxq",i);
	
	if(rd.getStringByDI("rsOrderBase","ddh",0).equals(m_id)){
%>
<div class="personInfo" align="left">姓名：<input name="TA_ZT_VISITOR/VISITOR_NM[<%=i %>]" class="smallInput" value="<%=vName %>"/>
					性别：<select name="TA_ZT_VISITOR/SEX[<%=i %>]"> 
					<option value="0" <%if(sex.equals("0")){ %> selected="selected" <%} %>>不详</option>
					<option value="1" <%if(sex.equals("1")){ %> selected="selected" <%} %>>男</option>
					<option value="2" <%if(sex.equals("2")){ %> selected="selected" <%} %>>女</option>
					</select>
					  证件号：<input name="TA_ZT_VISITOR/LICENSE_NO[<%=i %>]" value="<%=sfzmhm %>"/>
					  有效期：<input name="TA_ZT_VISITOR/ZJYXQ[<%=i %>]" value="<%=zjyxq %>" class="smallInput"/>
					  签证地：
					<input name="TA_ZT_VISITOR/PASSPORTPS[<%=i %>]" value="<%=PASSPORTPS %>" type="text" class="smallInput" />
					 出生地：
					<input name="TA_ZT_VISITOR/BORNSITE[<%=i %>]" value="<%=BORNSITE %>" type="text" class="smallInput" />
					 电话：<input name="TA_ZT_VISITOR/TEL[<%=i %>]" value="<%=TEL %>" class="smallerInput"/>
					 地址：
					<input name="TA_ZT_VISITOR/ADDRESS[<%=i %>]" value="<%=ADDRESS %>" type="text" class="smallerInput"/>
					&nbsp;<input name="TA_ZT_VISITOR/ISLEADER[<%=i %>]" type="checkbox" value="1" <%if(ISLEADER.equals("1")){ %> checked="checked" <%} %> class="zylxr" onclick="zylxr('<%=i%>');"/>
					主
					<input name="TA_ZT_VISITOR/ISCHILD[<%=i %>]" value="1" <%if(ISCHILD.equals("1")){ %> checked="checked" <%} %> type="checkbox" >童
					<input name="TA_ZT_VISITOR/ISINSURANCE[<%=i %>]" <%if(ISINSURANCE.equals("1")){ %> checked="checked" <%} %> type="checkbox" class="bxbox" value="1">保
</div>
<%	}
}%>
</div>
<div id="bm-table">
<table class="datas" width="98%">
	<tr>
		<td colspan="9"><span>报名社相关信息</span></td>
	</tr>
	<tr>
		<td >报名社：</td>
		<td ><input name="TA_ZT_YKORDER/CMPNY_NAME[0]" type="text" value="<%=rd.getStringByDI("rsOrderBase","cmpny_name",0) %>"/></td>
		<td >联系人：</td>
		<td ><input name="TA_ZT_YKORDER/BUSINESS_NAME[0]" type="text" value="<%=rd.getStringByDI("rsOrderBase","business_name",0) %>"/></td>
		<td >电话：</td>
		<td ><input name="TA_ZT_YKORDER/BUSINESS_TEL[0]" type="text" value="<%=rd.getStringByDI("rsOrderBase","business_tel",0) %>"/></td>
	</tr>
		<tr>
		<td >传真：</td>
		<td ><input name="TA_ZT_YKORDER/BUSINESS_FAX[0]" type="text" value="<%=rd.getStringByDI("rsOrderBase","business_fax",0) %>"/></td>
		<td >手机：</td>
		<td ><input name="TA_ZT_YKORDER/BUSINESS_MOBILE[0]" type="text" value="<%=rd.getStringByDI("rsOrderBase","business_mobile",0) %>"/></td>
		<td >MSN/QQ：</td>
		<td ><input name="TA_ZT_YKORDER/QQ_MSN[0]" type="text" value="<%=rd.getStringByDI("rsOrderBase","QQ_MSN",0) %>"/></td>
	</tr>
</table>
</div>
<div id="bm-table">
<table class="datas" width="98%">
	<tr>
		<td colspan="2"><span>订单备注</span></td>
	</tr>
	<tr>
		<td width="8%">备注：</td>
		<td >
		<textarea name="TA_ZT_YKORDER/REMARK[0]" cols="100" rows="5"><%=rd.getStringByDI("rsOrderBase","remark",0) %></textarea><br>
		注：备注不能超过200字符
		</td>
	</tr>
</table>
</div>

<div align="center" id="last-sub">
	<!--<input type="button" id="button" value="打印确认单" onclick="printDD('1');"/>
	<input type="button" id="button" value="打印行程单" onclick="printDD('2');"/>
	<input type="button" id="button" value="出团任务单" onclick="printDD('3');"/>
-->
	<input type="button" id="button" value="提交" onclick="doSub();"/>

	<input type="button" id="button" value="返 回" onclick="window.history.go(-1)"/>

<%
if(!rd.getString("ACTION").equals("edit")){
%>
	<input type="button" id="button" value="返 回" onclick="window.history.go(-1)"/>
<%
}%>
</div>
</div>
</form>
</body>
</html>