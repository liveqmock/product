<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<script type="text/javascript">
function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		    event.returnValue=false;
}
function checkNum1(obj)
{   
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}
function sumPrice(tab){
	var qd=0;
	var xf=0;
	var rs=0;
	var rtf=0;
	var gpj=0;
	var cbdj=0;
	var cbxj=0;
	var rtfxj=0;
	var gpjxj=0;
	var lrxj=0;
	var rsxj=0;
	jQuery("#"+tab+" .rs").each(function(i){
		rs+=parseFloat(this.value,10);
		qd+=parseFloat(jQuery("#"+tab+" .qd:eq("+i+")").val(),10);
		rtf+=parseFloat(jQuery("#"+tab+" .rtf:eq("+i+")").val(),10);
		cbdj+=parseFloat(jQuery("#"+tab+" .cbdj:eq("+i+")").val(),10);
		gpj+=parseFloat(jQuery("#"+tab+" .gpj:eq("+i+")").val(),10);
	});
	rsxj=rs;
	rtfxj=rs*rtf;
	cbxj=cbdj*rs;
	gpjxj=gpj*rs;
	lrxj=gpjxj-cbxj;
	if(cbdj>gpj){
		alert("成本单价应小于或等于挂牌价！");
		jQuery("#"+tab+" .cbdj").val(0);
	}
	if(qd>cbxj){
		alert("签单金额大于成本！");
		jQuery("#"+tab+" .qd").val(0);
	}else{
		xf=cbxj-qd;
		jQuery("#"+tab+" .xf").val(Math.round(xf*100)/100);
	}
	jQuery("#"+tab+" .rsxj").val(Math.round(rsxj*100)/100);
	jQuery("#"+tab+" .rtfxj").val(Math.round(rtfxj*100)/100);
	jQuery("#"+tab+" .cbxj").val(Math.round(cbxj*100)/100);
	jQuery("#"+tab+" .lrxj").val(Math.round(lrxj*100)/100);
	
	var yjgs=0;
	var tcje = lrxj/100*(100-<%=rd.getStringByDI("bxLCBL","DMSM2",0)%>);
	var tc=0;
	<%
	int newtcRows=rd.getTableRowsCount("bxTCBL");
	for(int t=0;t<newtcRows;t++){
	%>
		if(jQuery("#"+tab+" .tcfs").is(":checked")){
			jQuery("#"+tab+" .tc"+<%=t%>).attr("readonly","readonly");
			<%if("N".equals(rd.getString("qprszt"))){%>
			if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==4){
				jQuery("#"+tab+" .tc"+<%=t%>).val(0);
			}else{
				tc=<%=rd.getStringByDI("bxTCBL","dmsm2",t)%>/100*tcje;//成员提成 = 提成比例/100*提成金额
				jQuery("#"+tab+" .tc"+<%=t%>).val(Math.round(tc*100)/100);
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==3){
					yjgs+=parseFloat(jQuery("#"+tab+" .tc"+<%=t%>).val());
					jQuery("#"+tab+" .yjgs1").val(Math.round(yjgs*100)/100);
				}
			}
			<%}else{%>
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==3){
					jQuery("#"+tab+" .yjgs1").val(0);
					jQuery("#"+tab+" .tc"+<%=t%>).val(0);
				}else{
					tc=<%=rd.getStringByDI("bxTCBL","dmsm2",t)%>/100*tcje;//成员提成 = 提成比例/100*提成金额
					jQuery("#"+tab+" .tc"+<%=t%>).val(Math.round(tc*100)/100);
				}
			<%}%>
		}
		if(jQuery("#"+tab+" .tcfs1").is(":checked")){
			jQuery("#"+tab+" .tc"+<%=t%>).removeAttr("readonly");
			if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==3){
				yjgs+=parseFloat(jQuery("#"+tab+" .tc"+<%=t%>).val());
				jQuery("#"+tab+" .yjgs1").val(Math.round(yjgs*100)/100);
			}

		}
	<%}%>
	jQuery("#"+tab+" .yjgs1").each(function(i){
		 var yjxj = parseFloat(jQuery("#"+tab+" .yjgs1:eq("+i+")").val());
		 var lr10 = jQuery("#"+tab+" .lrxj:eq("+i+")").val()/100*<%=rd.getStringByDI("bxLCBL","DMSM2",0)%>;
		 var rtfd = parseFloat(jQuery("#"+tab+" .rtfxj:eq("+i+")").val());
		jQuery("#"+tab+" .yjgs:eq("+i+")").val(Math.round((yjxj+lr10+rtfd)*100)/100);
	});

	var dytczj=0;
	var sjtczj=0;
	var qptczj=0;
	var gstczj=0;
	
	jQuery(".jiadian").each(function(i){//获取购物点隐藏域数量 遍历
		var jiadian= "jiadian";
		<%
		int tcRows=rd.getTableRowsCount("bxTCBL");
		for(int t=0;t<tcRows;t++){
		%>
			if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==1){
				dytczj+=parseFloat(jQuery("#"+jiadian+i+" .tc"+<%=t%>).val());
				jQuery(".tchj"+<%=t%>).val(Math.round(dytczj*100)/100);//输出导游提成
			}
			if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==2){
				sjtczj+=parseFloat(jQuery("#"+jiadian+i+" .tc"+<%=t%>).val());
				jQuery(".tchj"+<%=t%>).val(Math.round(sjtczj*100)/100);//输出司机提成
			}
			<%if("N".equals(rd.getString("qprszt"))){%>
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==3){
					gstczj+=parseFloat(jQuery("#"+jiadian+i+" .tc"+<%=t%>).val());
					jQuery(".tchj"+<%=t%>).val(Math.round(gstczj*100)/100); //输出公司提成
					jQuery(".yjgshj").val(Math.round(gstczj*100)/100);
				}
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==4){
					jQuery(".tchj"+<%=t%>).val(0);
				}
			<%}else{%>
				jQuery(".yjgshj").val(0);
				if(<%=rd.getStringByDI("bxTCBL","dmz",t)%>==4){
					qptczj+=parseFloat(jQuery("#"+jiadian+i+" .tc"+<%=t%>).val());
					jQuery(".tchj"+<%=t%>).val(Math.round(qptczj*100)/100);//输出全陪提成
				}
			<%}%>
		<%}%>
		
	});

	var rtfhj=0;
	var cbhj=0;
	var lrhj=0;
	var qdhj=0;
	var xfhj=0;
	var rshj=0;
	jQuery(".rtfxj").each(function(i){
		rtfhj+=parseFloat(this.value);
		qdhj+=parseFloat(jQuery(".qd:eq("+i+")").val());
		xfhj+=parseFloat(jQuery(".xf:eq("+i+")").val());
		rshj+=parseFloat(jQuery(".rsxj:eq("+i+")").val());
		cbhj+=parseFloat(jQuery(".cbxj:eq("+i+")").val());
		lrhj+=parseFloat(jQuery(".lrxj:eq("+i+")").val());
	});
	jQuery(".rtfhj").val(Math.round(rtfhj*100)/100);
	jQuery(".cbhj").val(Math.round(cbhj*100)/100);
	jQuery(".lrhj").val(Math.round(lrhj*100)/100);
	jQuery(".qdhj").val(Math.round(qdhj*100)/100);
	jQuery(".xfhj").val(Math.round(xfhj*100)/100);
	jQuery(".rshj").val(Math.round(rshj*100)/100);


	
	var yjgszj=0;
	var state = "<%=rd.getString("qprszt")%>";
	if(state=="Y"){//有全陪
		//公司总计=人头费+利润合计的10%
		yjgszj+=parseFloat(jQuery(".rtfhj").val())+(parseFloat(jQuery(".lrhj").val())/100*<%=rd.getStringByDI("bxLCBL","DMSM2",0)%>);
		jQuery(".yjgszj").val(Math.round(yjgszj*100)/100);
	}
	
	if(state=="N"){//无全陪
		//公司总计=公司合计+人头费合计+利润合计的10%
		yjgszj+=parseFloat(jQuery(".yjgshj").val())+parseFloat(jQuery(".rtfhj").val())+parseFloat(jQuery(".lrhj").val())/100*<%=rd.getStringByDI("bxLCBL","DMSM2",0)%>;
		jQuery(".yjgszj").val(Math.round(yjgszj*100)/100);
	}
}
function delTab(tbName){
	jQuery("#"+tbName).remove();
	sumPrice(jQuery("#"+tbName).val());
}
function doPlusSub(){
	document.ztGeneralPlusRbt.submit();
	reload();
}
function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}
function addPlus(){
	var num=jQuery(".select-plus").size();
	if(jQuery("#scenery"+(num-1)).val()==""){
		alert("请填写完整后再添加");
		return false;
	}
	jQuery("#JiadianDiv").append('<table class="datas select-plus" width="100%" id="jiadian'+num+'">'+
			  '<tr>'+
				'<td colspan="6"><span>加点报销</span>&nbsp;&nbsp;<span class="showPointer" onclick="delTab(\'jiadian'+num+'\')">删除</span></td>'+
			  '</tr>'+
			  '<tr>'+
			  		'<td colspan="6">'+
			  			'加点项目:'+
			  			'<select name="TA_ZT_BXJIADIAN/SFID['+num+']" id="pro'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="1"></select>'+
				  		'<select name="TA_ZT_BXJIADIAN/CSID['+num+']" id="city'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="2"></select>'+
				  		'<select name="TA_ZT_BXJIADIAN/JDID['+num+']" id="scenery'+num+'" class="scenery" USEDATA="dataSrc'+num+'" SUBCLASS="3"></select>'+
				  		'<!-- 记录加点数量 隐藏域 --><input type="hidden" name="" value="" class="jiadian"/>'+
				  		'<input type="hidden" name="" value="" class="smallInput yjgs1"/>'+
			  		'</td>'+
			 '</tr>'+
		'<tr id="first-tr" style="text-align: center"> '+
	  	'<td>挂牌价</td><td>成本单价</td><td>人数</td><td>人头费</td><td colspan="2">成本支付方式</td></tr>'+
	  '<tr style="text-align: center">'+
	  	'<td><input onkeydown="checkNum1(this)" value="0" name="TA_ZT_BXJIADIAN/GPJ['+num+']" onchange="sumPrice(\'jiadian'+num+'\')"  class="smallInput gpj">元/人</td>'+
		'<td><input onkeydown="checkNum1(this)" value="0" name="TA_ZT_BXJIADIAN/CBDJ['+num+']" onchange="sumPrice(\'jiadian'+num+'\')"  class="smallInput cbdj"/>元/人</td>'+
	  	'<td><input onkeydown="checkNum()" value="0" name="TA_ZT_BXJIADIAN/RS['+num+']" onchange="sumPrice(\'jiadian'+num+'\')"  class="smallInput rs"/>人</td>'+
	  	'<td><input onkeydown="checkNum()" value="0"  name="TA_ZT_BXJIADIAN/RTF['+num+']" onchange="sumPrice(\'jiadian'+num+'\')"  class="smallInput rtf"/>元/人</td>'+
	  	'<td> <font color="red">签单：</font><input type="text" onkeydown="checkNum1(this)" name="TA_ZT_BXJIADIAN/JDQDXJ['+num+']" value="0" onchange="sumPrice(\'jiadian'+num+'\')" readonly="readonly" class="smallInput qd" />/元&nbsp;</td>'+
	  	'<td> <font color="red">现付：</font><input type="text" onkeydown="checkNum1(this)" name="TA_ZT_BXJIADIAN/JDXFXJ['+num+']" value="0" onchange="sumPrice(\'jiadian'+num+'\')" readonly="readonly" class="smallInput xf"/>/元&nbsp;</td>'+
	 ' </tr>'+
		'<tr id="first-tr">'+
			'<td colspan="3" align="center">提成成员</td>'+
			'<td colspan="3" align="center">提成方式：&nbsp;&nbsp;<input type="radio" name="TA_ZT_BXJIADIAN/TCFS['+num+']" value="1" class="tcfs" checked="checked" onchange="sumPrice(\'jiadian'+num+'\')"/>比例提成<input type="radio" name="TA_ZT_BXJIADIAN/TCFS['+num+']" class="tcfs1" value="2" onchange="sumPrice(\'jiadian'+num+'\')"/>固定金额提成'+
		'</tr>'+
	<%
	int plusTcRows=rd.getTableRowsCount("bxTCBL");
	for(int t=0;t<plusTcRows;t++){
		String DMZ = rd.getString("bxTCBL","DMZ",t);
		String DMSM = rd.getStringByDI("bxTCBL","DMSM1",t);
		String TCBL = rd.getStringByDI("bxTCBL","DMSM2",t);
	%>
		'<tr>'+
		'<td colspan="3" align="center"><%=DMSM %>(<%=TCBL %>%)</td>'+
		'<td colspan="3" align="center">'+
		'<input type="hidden" name="bxTCBL'+num+'/DMZ[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMZ",t) %>" />'+
		'<input type="hidden" name="bxTCBL'+num+'/TCMC[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMSM1",t) %>" />'+
		'<input type="hidden" name="bxTCBL'+num+'/TCBL[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMSM2",t) %>" />'+
		'<input type="text" name="bxTCBL'+num+'/TCJE[<%=t %>]"  value="0" class="tc<%=t %>" onkeydown="checkNum()" onchange="sumPrice(\'jiadian'+num+'\')"/>/元'+
		'</td>'+
	'</tr>'+
	<%
	}
	%>
	'<tr>'+
	'<td align="right" colspan="7">'+
		'<input  type="hidden" value="0" name=""  readonly="readonly" class="smallInput rsxj"/>'+
		'<font color="red">人头费：</font>   <input  type="text" value="0"  name="TA_ZT_BXJIADIAN/RTFXJ['+num+']" readonly="readonly" class="smallInput rtfxj"/>/元&nbsp;'+
		'<font color="red">成本：</font>   <input  type="text" value="0" name="TA_ZT_BXJIADIAN/CBJE['+num+']"  readonly="readonly" class="smallInput cbxj"/>/元&nbsp;'+
		'<font color="red">加点净利：</font>   <input  type="text" value="0"  name="TA_ZT_BXJIADIAN/LR['+num+']" readonly="readonly" class="smallInput lrxj"/>/元&nbsp;'+
		'<font color="red">应交公司现金：</font><input type="text" name="TA_ZT_BXJIADIAN/YJGS['+num+']" value="0" class="smallInput yjgs"/>'+
		'</td>'+
	'<tr></table>');
	var linkage = new Linkage("dataSrc"+num, "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
	linkage.init();
}
</script>
<title>加点计划</title>
</head>

<body>
<form action="ztGeneralPlusRbt." name="ztGeneralPlusRbt" method="post">
<div id="lable"><span >加点报销</span></div>
<div id="bm-table">
<div id="JiadianDiv">

  <%
  	int plusRows = rd.getTableRowsCount("plusInfo"); 
  	if(0 < plusRows){
		out.print("<input type='hidden' name='state' value='Edit'>");
  	for(int i = 0;i < plusRows; i++){
  		String JDID = rd.getStringByDI("plusInfo","JDID",i);//景点ID
  		String GPJ = rd.getStringByDI("plusInfo","GPJ",i).equals("")?"0":rd.getStringByDI("plusInfo","GPJ",i);//挂牌价
  		String CBDJ = rd.getStringByDI("plusInfo","CBDJ",i).equals("")?"0":rd.getStringByDI("plusInfo","CBDJ",i);//成本单价
  		String RS = rd.getStringByDI("plusInfo","RS",i).equals("")?"0":rd.getStringByDI("plusInfo","RS",i);//人数
  		String RTF = rd.getStringByDI("plusInfo","RTF",i).equals("")?"0":rd.getStringByDI("plusInfo","RTF",i);//人头费
  		String JDQDXJ = rd.getStringByDI("plusInfo","JDQDXJ",i).equals("")?"0":rd.getStringByDI("plusInfo","JDQDXJ",i);//加点签单小计
  		String JDXFXJ = rd.getStringByDI("plusInfo","JDXFXJ",i).equals("")?"0":rd.getStringByDI("plusInfo","JDXFXJ",i);//加点现付小计
  		String CBJE = rd.getStringByDI("plusInfo","CBJE",i).equals("")?"0":rd.getStringByDI("plusInfo","CBJE",i);//成本金额
  		String LR = rd.getStringByDI("plusInfo","LR",i).equals("")?"0":rd.getStringByDI("plusInfo","LR",i);//利润
  		String RTFXJ = rd.getStringByDI("plusInfo","RTFXJ",i).equals("")?"0":rd.getStringByDI("plusInfo","RTFXJ",i);//人头费小计
		
  		String TCFS = rd.getStringByDI("plusInfo","TCFS",i);//提成方式
		String DYTC = rd.getStringByDI("plusInfo","DYTC",i).equals("")?"0":rd.getStringByDI("plusInfo","DYTC",i);//导游提成
		String SJTC = rd.getStringByDI("plusInfo","SJTC",i).equals("")?"0":rd.getStringByDI("plusInfo","SJTC",i);//司机提成
		String QPTC = rd.getStringByDI("plusInfo","QPTC",i).equals("")?"0":rd.getStringByDI("plusInfo","QPTC",i);//全陪提成
		String GSTC = rd.getStringByDI("plusInfo","GSTC",i).equals("")?"0":rd.getStringByDI("plusInfo","GSTC",i);//公司提成
		String YJGS = rd.getStringByDI("plusInfo","YJGS",i).equals("")?"0":rd.getStringByDI("plusInfo","YJGS",i);//公司
		String DYZB = rd.getStringByDI("plusInfo","DYZB",i);//导游占比
		String SJZB = rd.getStringByDI("plusInfo","SJZB",i);//司机占比
		String QPZB = rd.getStringByDI("plusInfo","QPZB",i);//全陪占比
  %>
  <table class="datas select-plus" width="100%" id="jiadian<%=i %>">
  <tr>
	<td colspan="6"><span>加点报销</span>&nbsp;&nbsp;<%if(i==0){%><span class="showPointer" onclick="addPlus();">添加</span><%}else{ %><span class="showPointer" onclick="delTab('jiadian<%=i %>')">删除</span><%} %></td>
  </tr>
  <tr>
  		<td colspan="6">
  			加点项目:
  			<select name="TA_ZT_BXJIADIAN/SFID[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
	  		<select name="TA_ZT_BXJIADIAN/CSID[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
	  		<select name="TA_ZT_BXJIADIAN/JDID[<%=i %>]" id="scenery<%=i %>" class="scenery" USEDATA="dataSrc<%=i %>" SUBCLASS="3"></select>
	  		<!-- 记录加点数量 隐藏域 --><input type="hidden" name="" value="" class="jiadian"/>
	  		<input type="hidden" name="" value="" class="smallInput yjgs1"/>
  		</td>
  </tr>
  <tr id="first-tr" style="text-align: center"> 
  	<td>挂牌价</td>
  	<td>成本单价</td>
  	<td>人数</td>
  	<td>人头费</td>
  	<td colspan="2">支付方式</td>
  </tr>
  <tr style="text-align: center">
  	<td><input onkeydown="checkNum1(this)" value="<%=GPJ %>" name="TA_ZT_BXJIADIAN/GPJ[<%=i %>]" onchange="sumPrice('jiadian<%=i %>')"  class="smallInput gpj">元/人</td>
	<td><input onkeydown="checkNum1(this)" value="<%=CBDJ %>" name="TA_ZT_BXJIADIAN/CBDJ[<%=i %>]" onchange="sumPrice('jiadian<%=i %>')"  class="smallInput cbdj"/>元/人</td>
  	<td><input onkeydown="checkNum()" value="<%=RS %>" name="TA_ZT_BXJIADIAN/RS[<%=i %>]" onchange="sumPrice('jiadian<%=i %>')"  class="smallInput rs"/>人</td>
  	<td><input onkeydown="checkNum()" value="<%=RTF %>"  name="TA_ZT_BXJIADIAN/RTF[<%=i %>]" onchange="sumPrice('jiadian<%=i %>')"  class="smallInput rtf"/>元/人</td>
  	<td> <font color="red">签单：</font><input type="text" onkeydown="checkNum1(this)" name="TA_ZT_BXJIADIAN/JDQDXJ[<%=i %>]" value="<%=JDQDXJ %>" onchange="sumPrice('jiadian<%=i %>')" readonly="readonly" class="smallInput qd" />/元&nbsp;</td>
  	<td> <font color="red">现付：</font><input type="text" onkeydown="checkNum1(this)" name="TA_ZT_BXJIADIAN/JDXFXJ[<%=i %>]" value="<%=JDXFXJ %>" onchange="sumPrice('jiadian<%=i %>')" readonly="readonly" class="smallInput xf"/>/元&nbsp;</td>
  </tr>
	<tr id="first-tr">
		<td colspan="3" align="center">提成成员</td>
		<td colspan="3" align="center">提成方式：&nbsp;&nbsp;<input type="radio" name="TA_ZT_BXJIADIAN/TCFS[<%=i %>]" value="1" class="tcfs" <%if("1".equals(TCFS)||null!=TCFS){ %>checked="checked"<%} %> onchange="sumPrice('jiadian<%=i %>')"/>比例提成<input type="radio" name="TA_ZT_BXJIADIAN/TCFS[<%=i %>]" class="tcfs1" value="2" <%if("2".equals(TCFS)){ %>checked="checked"<%} %> onchange="sumPrice('jiadian<%=i %>')"/>固定金额提成
	</tr>
	<%
	int d_tcRows=rd.getTableRowsCount("bxTCBL");
	for(int t=0;t<d_tcRows;t++){
		String DMZ = rd.getString("bxTCBL","DMZ",t);
		String DMSM = rd.getStringByDI("bxTCBL","DMSM1",t);
		String TCBL = rd.getStringByDI("bxTCBL","DMSM2",t);
	%>
	<tr>
		<td colspan="3" align="center"><%=DMSM %>(<%=TCBL %>%)</td>
		<td colspan="3" align="center">
		
		<input type="hidden" name="bxTCBL<%=i %>/DMZ[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMZ",t) %>" />
		<input type="hidden" name="bxTCBL<%=i %>/TCMC[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMSM1",t) %>" />
		<input type="hidden" name="bxTCBL<%=i %>/TCBL[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMSM2",t) %>" />
		<input type="text" name="bxTCBL<%=i %>/TCJE[<%=t %>]"  value="<%if("导游".equals(DMSM)){out.print(DYTC);}else if("司机".equals(DMSM)){out.print(SJTC);}else if("全陪".equals(DMSM)){out.print(QPTC);}else if("公司".equals(DMSM)){out.print(GSTC);} %>" class="tc<%=t %>" onkeydown="checkNum()" onchange="sumPrice('jiadian<%=i %>')"/>/元
		</td>
	</tr>
	<%
	}
	%>
<tr>
	<td align="right" colspan="7">
		 <input  type="hidden" value="<%=RS %>" name=""  readonly="readonly" class="smallInput rsxj"/>
		<font color="red">人头费：</font>   <input  type="text" value="<%=RTFXJ %>"  name="TA_ZT_BXJIADIAN/RTFXJ[<%=i %>]" readonly="readonly" class="smallInput rtfxj"/>/元
		<font color="red">成本：</font>   <input  type="text" value="<%=CBJE %>" name="TA_ZT_BXJIADIAN/CBJE[<%=i %>]"  readonly="readonly" class="smallInput cbxj"/>/元
		<font color="red">加点净利：</font>   <input  type="text" value="<%=LR %>"  name="TA_ZT_BXJIADIAN/LR[<%=i %>]" readonly="readonly" class="smallInput lrxj"/>/元
		<font color="red">应交公司现金：</font><input type="text" name="TA_ZT_BXJIADIAN/YJGS[<%=i %>]" value="<%=YJGS %>" class="smallInput yjgs"/>
	</td>
</tr>
</table>
<% }
}else{%>
<table class="datas select-plus" width="100%" id="jiadian0">
  <tr>
	<td colspan="6"><span>加点报销</span>&nbsp;&nbsp;<span class="showPointer" onclick="addPlus();">添加</span></td>
  </tr>
  <tr>
  		<td colspan="6">
  			加点项目:
  			<select name="TA_ZT_BXJIADIAN/SFID[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
	  		<select name="TA_ZT_BXJIADIAN/CSID[0]" id="city0" USEDATA="dataSrc0" SUBCLASS="2"></select>
	  		<select name="TA_ZT_BXJIADIAN/JDID[0]" id="scenery0" class="scenery" USEDATA="dataSrc0" SUBCLASS="3"></select>
	  		<!-- 记录加点数量 隐藏域 --><input type="hidden" name="" value="" class="jiadian"/>
	  		<input type="hidden" name="" value="" class="smallInput yjgs1"/>
  		</td>
  </tr>
  <tr id="first-tr" style="text-align: center"> 
  	<td>挂牌价</td>
  	<td>成本单价</td>
  	<td>人数</td>
  	<td>人头费</td>
  	<td colspan="2">成本支付方式</td>
  </tr>
  <tr style="text-align: center">
  	<td><input onkeydown="checkNum1(this)" value="0" name="TA_ZT_BXJIADIAN/GPJ[0]" onchange="sumPrice('jiadian0')"  class="smallInput gpj">元/人</td>
	<td><input onkeydown="checkNum1(this)" value="0" name="TA_ZT_BXJIADIAN/CBDJ[0]" onchange="sumPrice('jiadian0')"  class="smallInput cbdj"/>元/人</td>
  	<td><input onkeydown="checkNum()" value="0" name="TA_ZT_BXJIADIAN/RS[0]" onchange="sumPrice('jiadian0')"  class="smallInput rs"/>人</td>
  	<td><input onkeydown="checkNum()" value="0"  name="TA_ZT_BXJIADIAN/RTF[0]" onchange="sumPrice('jiadian0')"  class="smallInput rtf"/>元/人</td>
  	<td> <font color="red">签单：</font><input type="text" onkeydown="checkNum1(this)" name="TA_ZT_BXJIADIAN/JDQDXJ[0]" value="0" onchange="sumPrice('jiadian0')" readonly="readonly" class="smallInput qd" />/元&nbsp;</td>
  	<td> <font color="red">现付：</font><input type="text" onkeydown="checkNum1(this)" name="TA_ZT_BXJIADIAN/JDXFXJ[0]" value="0" onchange="sumPrice('jiadian0')" readonly="readonly" class="smallInput xf"/>/元&nbsp;</td>
  </tr>
	<tr id="first-tr">
		<td colspan="3" align="center">提成人员</td>
		<td colspan="3" align="center">提成方式：&nbsp;&nbsp;<input type="radio" name="TA_ZT_BXJIADIAN/TCFS[0]" value="1" class="tcfs" checked="checked" onchange="sumPrice('jiadian0')"/>比例提成<input type="radio" name="TA_ZT_BXJIADIAN/TCFS[0]" class="tcfs1" value="2"  onchange="sumPrice('jiadian0')"/>固定金额提成
	</tr>
	<%
	int d_tcRows=rd.getTableRowsCount("bxTCBL");
	for(int t=0;t<d_tcRows;t++){
		String DMZ = rd.getString("bxTCBL","DMZ",t);
		String DMSM = rd.getStringByDI("bxTCBL","DMSM1",t);
		String TCBL = rd.getStringByDI("bxTCBL","DMSM2",t);
	%>
	<tr>
		<td colspan="3" align="center"><%=DMSM %>(<%=TCBL %>%)</td>
		<td colspan="3" align="center">
		
		<input type="hidden" name="bxTCBL0/DMZ[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMZ",t) %>" />
		<input type="hidden" name="bxTCBL0/TCMC[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMSM1",t) %>" />
		<input type="hidden" name="bxTCBL0/TCBL[<%=t %>]" value="<%=rd.getStringByDI("bxTCBL","DMSM2",t) %>" />
		<input type="text" name="bxTCBL0/TCJE[<%=t %>]"  value="0" class="tc<%=t %>" onkeydown="checkNum()" onchange="sumPrice('jiadian0')"/>/元
		</td>
	</tr>
	<%
	}
	%>
<tr>
	<td align="right" colspan="7">
		<input  type="hidden" value="0" name=""  readonly="readonly" class="smallInput rsxj"/>
		<font color="red">人头费：</font>   <input  type="text" value="0"  name="TA_ZT_BXJIADIAN/RTFXJ[0]" readonly="readonly" class="smallInput rtfxj"/>/元
		<font color="red">成本：</font>   <input  type="text" value="0" name="TA_ZT_BXJIADIAN/CBJE[0]"  readonly="readonly" class="smallInput cbxj"/>/元
		<font color="red">加点净利：</font>   <input  type="text" value="0"  name="TA_ZT_BXJIADIAN/LR[0]" readonly="readonly" class="smallInput lrxj"/>/元
		<font color="red">应交公司现金：</font><input type="text" name="TA_ZT_BXJIADIAN/YJGS[0]" value="0" class="smallInput yjgs"/>
	</td>
</tr>
</table>
<%
}%>
</div>
<table class="datas" >
  <tr>
	<td align="center"><span>费用合计</span></td>
  </tr>
  
  <tr>
  	<td colspan="2">
		<table class="datas "  style="text-align: center">
			<tr id="first-tr">
				<td>提成成员</td>
				<td>提成金额合计&nbsp;</td>
			</tr>
			
			<%
			int d_tcRows=rd.getTableRowsCount("bxTCBL");
			for(int t=0;t<d_tcRows;t++){
				String DMZ = rd.getStringByDI("bxTCBL","DMZ",t);
				String DMSM = rd.getStringByDI("bxTCBL","DMSM1",t);
						int HZRows = rd.getTableRowsCount("bxInitCYHZ");
						if(0 >= HZRows){
			%>
			<tr >
				<td><%=DMSM %></td>
				<td>
				<input type="hidden" name="TA_JDBXTCCYHZ_ZT/CYID[<%=t %>]" value="<%=DMZ %>"/>
				<input type="hidden" name="TA_JDBXTCCYHZ_ZT/CYMC[<%=t %>]" value="<%=DMSM %>" />
				<input type="text"  readonly="readonly" name="TA_JDBXTCCYHZ_ZT/CYJEHZ[<%=t %>]" value="0" class="tchj<%=t %>" onkeydown="checkNum()"/>/元
				</td>
			</tr>		
			<%
						}else{
							for(int a = 0; a < HZRows; a++){
								String CYMC = rd.getStringByDI("bxInitCYHZ","CYMC",a);
								String CYJEHZ = rd.getStringByDI("bxInitCYHZ","CYJEHZ",a).equals("")?"0":rd.getStringByDI("bxInitCYHZ","CYJEHZ",a);
								if(CYMC.equals(DMSM)){
			%>
			<tr >
				<td><%=DMSM %></td>
				<td>
				<input type="hidden" name="TA_JDBXTCCYHZ_ZT/CYID[<%=t %>]" value="<%=DMZ %>"/>
				<input type="hidden" name="TA_JDBXTCCYHZ_ZT/CYMC[<%=t %>]" value="<%=DMSM %>" />
				<input type="text" readonly="readonly" name="TA_JDBXTCCYHZ_ZT/CYJEHZ[<%=t %>]" value="<%=CYJEHZ %>" class="tchj<%=t %>" onkeydown="checkNum()"/>/元
				</td>
			</tr>
			<%
							
						}
					}
				}
			}
			%>
			
		</table>
		</td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">人数合计：</font><input  type="text" name="TA_TDBXZJXXB_ZT/JDRSHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","JDRSHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","JDRSHJ",0) %>"   readonly="readonly" class="smallInput rshj"/>/元&nbsp;
			<font color="red">签单合计：</font><input type="text" name="TA_TDBXZJXXB_ZT/JDQDHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","JDQDHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","JDQDHJ",0) %>" readonly="readonly" class="smallInput qdhj"/>/元&nbsp;
			<font color="red">现付合计：</font><input type="text" name="TA_TDBXZJXXB_ZT/JDXFHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","JDXFHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","JDXFHJ",0) %>" readonly="readonly" class="smallInput xfhj"/>/元&nbsp;
			<font color="red">人头费合计：</font><input type="text" name="TA_TDBXZJXXB_ZT/JDRTFHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","JDRTFHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","JDRTFHJ",0) %>"   readonly="readonly" class="smallInput rtfhj"/>/元&nbsp;
		</td>
	</tr>
	<tr>
		<td align="right">
			
			<font color="red">成本合计：</font><input  type="text" name="TA_TDBXZJXXB_ZT/JDCBHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","JDCBHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","JDCBHJ",0) %>"   readonly="readonly" class="smallInput cbhj"/>/元&nbsp;
			<font color="red">&nbsp;&nbsp;加点净利合计：</font>   <input  name="TA_TDBXZJXXB_ZT/JDLRHJ" type="text" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","JDLRHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","JDLRHJ",0) %>"   readonly="readonly" class="smallInput lrhj"/>/元&nbsp;
			<font color="red">应交公司现金合计：</font><input  type="text" name="TA_TDBXZJXXB_ZT/JDYJGSHJ" value="<%=rd.getString("TA_TDBXZJXXB_ZTs","JDYJGSHJ",0).equals("")?"0":rd.getStringByDI("TA_TDBXZJXXB_ZTs","JDYJGSHJ",0) %>" class="smallInput yjgszj"/>/元&nbsp;
			<input  type="hidden" class="smallInput yjgshj"/>
		</td>
  </tr>
</table>
</div>
<div align="center" id="last-sub">
	<input type='hidden' name="BXR" value="<%=sd.getString("userno") %>">
	<input type='hidden' name="" class="TCCY" value="<%=rd.getString("TCCY") %>">
	<input type="hidden" name="TID" class="groupID" value="<%=rd.getString("TA_ZT_BXJIADIAN","TID",0) %>">
	<%String view = rd.getString("flag");  if(!"view".equals(view)){%>
	<input type="button" id="button" value="提交" onclick="doPlusSub();"/>
	<%} %>
	<input type="button" id="button" value="关闭" onclick="reload();"/>
</div>
</form>
</body>
</html>
<script type="text/javascript">
<%
if(plusRows > 0){
for(int i=0;i<plusRows;i++){
%>
	var linkage = new Linkage("dataSrc<%=i %>", "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("plusInfo","SFID",i) %>",1);
	linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("plusInfo","CSID",i)%>",2);
	linkage.initLinkage("dataSrc<%=i%>","<%=rd.getStringByDI("plusInfo","JDID",i)%>",3);
<%
}
}else{
%>
var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
linkage.init();
<%
}%>
</script>