<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
String groupID = rd.getStringByDI("TA_ZT_JHDJs","TID",0).equals("")?rd.getString("tid"):rd.getStringByDI("TA_ZT_JHDJs","TID",0);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript">
function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		    event.returnValue=false;
}

jQuery(document).ready(function(){
	jQuery("#addTrave").click(function(){
		var num=jQuery(".select-trave").size();
		if( jQuery("#djsid"+(num-1)).val()==""){
			alert("请填写完整后再添加");
			return false;
		}
		
		jQuery("#traveDiv").append(
				'<table class="datas select-trave" width="98%" id="trave'+num+'">'+
				  '<tr>'+
					'<td colspan="4">'+
					  '<span  class="showPointer" onclick="delTab(\'trave'+num+'\');sumPrice(\'trave0\');">删除</span>'+
					'</td>'+
				  '</tr>'+
				  '<tr>'+
				    '<td>选择地接社：</td>'+
					'<td>'+
					  '<select name="TA_ZT_JHDJ/SFID['+num+']" id="sf'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="1"></select>'+
					  '<select name="TA_ZT_JHDJ/CSID['+num+']" id="cs'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="2"></select>'+
					  '<select name="TA_ZT_JHDJ/DJSID['+num+']"  id="djsid'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="3"></select>'+
					'</td>'+
				  '</tr>'+
				  '<tr>'+
					'<td>地接社收客人数：</td>'+
					'<td>'+
					'成人人数:<input type="text" class="smallInput crrs" name="TA_ZT_JHDJ/CRRS['+num+']" value="" onkeydown="checkNum()"/>'+
					'儿童人数:<input type="text" class="smallInput etrs" name="TA_ZT_JHDJ/ETRS['+num+']" value="" onkeydown="checkNum()"/>'+
					'</td>'+
				  '</tr>'+
				  '<tr>'+
					'<td>应付团款：</td>'+
					'<td><input type="text" name="TA_ZT_JHDJ/YFZK['+num+']"  value="0" class="smallerInput yfzk" onchange="sumPrice(\'trave'+num+'\');"/><font color="red">*</font>'+
					'&nbsp;&nbsp;<font color="red">现付：</font><input type="text" name="TA_ZT_JHDJ/XFJE['+num+']" value="0"  class="smallerInput xfje" onchange="sumPrice(\'trave'+num+'\');"/>'+
				  	'&nbsp;&nbsp;<font color="red">签单：</font><input type="text" name="TA_ZT_JHDJ/QDJE['+num+']" value="0"  class="smallerInput qdje" onchange="sumPrice(\'trave'+num+'\');" readonly="readonly"/>'+
					'</td>'+
				  '</tr>'+
				  '<tr>'+
					'<td>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>'+
					'<td><textarea name="TA_ZT_JHDJ/BZ['+num+']" cols="50" rows="3" > </textarea></td>'+
				  '</tr>'+
				'</table>');
		var linkage = new Linkage("dataSrc"+num, "<%=request.getContextPath()%>/main/data/Travez.xml");
		linkage.init();
	});
}); 

function delTab(tab){
	 jQuery("#"+tab).remove();
}

function sumPrice(tbName){
	var yfzkzj=0;
	var xfjezj=0;
	var qdjezj=0;
	jQuery("#"+tbName+" .yfzk").each(function(i){
		var yfzk = parseInt(this.value,10);
		var xfje = parseInt(jQuery("#"+tbName+" .xfje:eq("+i+")").val(),10);
		if(yfzk>=xfje){
			var qdje = yfzk-xfje;
			jQuery("#"+tbName+" .qdje:eq("+i+")").val(qdje);
		}else{
			jQuery("#"+tbName+" .xfje:eq("+i+")").val(0);
			sumPrice('trave0');
		}
	});
	jQuery(".yfzk").each(function(i){
		yfzkzj+=parseInt(this.value,10);
		xfjezj+=parseInt(jQuery(".xfje:eq("+i+")").val(),10);
		qdjezj+=parseInt(jQuery(".qdje:eq("+i+")").val(),10);
	});
	jQuery(".yfzkzj").val(yfzkzj);
	jQuery(".xfjezj").val(xfjezj);
	jQuery(".qdjezj").val(qdjezj);
}
function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}

function submitDjTrave(){
	jQuery(".yfzk").each(function (i){
		if(''==this.value){
			alert("请输入应付账款！");
			return false;
		}else if(jQuery("#djsid"+i).val()==""){
			alert("请选择地接社！");
			return false;
		}else{
			document.dj_Trave_form.submit();
			reload();
		}
	});
}
</script>
<title>地接社计划</title>
</head>

<body>
	<form action="ztAddGroundPlan." name="dj_Trave_form" method="post">
<div id="lable"><span >地接社计划</span></div>
<div id="bm-table">
<div id="traveDiv">
	<input type="hidden" name="TA_ZT_JHDJ/JHZT" value="Y" />
	<input type="hidden" name="TA_ZT_JHDJ/ZDR" value="<%=sd.getString("USERNO") %>" />
	<input type="hidden" name="TA_ZT_JHDJ/TID" value="<%=rd.getStringByDI("TA_ZT_JHDJ","TID",0) %>" />
	<%
		int Traverows = rd.getTableRowsCount("TA_ZT_JHDJs");
		if(Traverows > 0){
			out.print("<input type='hidden' name='state' value='Edit'>");
			for(int i = 0; i < Traverows; i++){
				String YFZK=rd.getStringByDI("TA_ZT_JHDJs","YFZK",i);
				if("".equals(YFZK)){
					YFZK = "0";
				}
				String XFJE = rd.getStringByDI("TA_ZT_JHDJs","XFJE",i);
				if("".equals(XFJE)){
					XFJE = "0";
				}
				String QDJE = rd.getStringByDI("TA_ZT_JHDJs","QDJE",i);
				if("".equals(QDJE)){
					QDJE = "0";
				}
				String BZ=rd.getStringByDI("TA_ZT_JHDJs","BZ",i);
	%>
	
	<table class="datas select-trave" width="98%" id="trave<%=i %>">
		<tr>
			<td colspan="4">
			<span  class="showPointer" id="addTrave"><%if(i==0){%>添加<%}else{ %>
			<span  class="showPointer" onclick="delTab('trave<%=i %>');sumPrice('trave0');">删除</span><%}%></span>
			</td>
		</tr>
		<tr>
		  <td>选择地接社：</td>
		  <td>
			<select name="TA_ZT_JHDJ/SFID[<%=i %>]" id="sf<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
			<select name="TA_ZT_JHDJ/CSID[<%=i %>]" id="cs<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
			<select name="TA_ZT_JHDJ/DJSID[<%=i %>]"  id="djsid<%=i %>" class="cityid" USEDATA="dataSrc<%=i %>" SUBCLASS="3" ></select>
		  </td>
		</tr>
		<tr>
			<td>地接社收客人数：</td>
			<td>
			成人人数:<input type="text" class="smallInput crrs" name="TA_ZT_JHDJ/CRRS[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHDJs","CRRS",i) %>" onkeydown="checkNum()"/>
			儿童人数:<input type="text" class="smallInput etrs" name="TA_ZT_JHDJ/ETRS[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHDJs","ETRS",i) %>" onkeydown="checkNum()"/>
			</td>
		</tr>
		<tr>
		  <td>应付团款：</td>
		  <td>
		  	<input type="text" name="TA_ZT_JHDJ/YFZK[<%=i %>]" value="<%=YFZK %>"  class="smallerInput yfzk" onchange="sumPrice('trave<%=i %>');"/><font color="red">*</font>
		  	&nbsp;&nbsp;<font color="red">现付：</font><input type="text" name="TA_ZT_JHDJ/XFJE[<%=i %>]" value="<%=XFJE %>"  class="smallerInput xfje" onchange="sumPrice('trave<%=i %>');"/>
		  	&nbsp;&nbsp;<font color="red">签单：</font><input type="text" name="TA_ZT_JHDJ/QDJE[<%=i %>]" value="<%=QDJE %>"  class="smallerInput qdje" onchange="sumPrice('trave<%=i %>');" readonly="readonly"/>
		  </td>
		</tr>
		<tr>
			<td colspan="4"><img src="<%=request.getContextPath()%>/images/notepad.gif" />&nbsp;<textarea name="TA_ZT_JHDJ/BZ[<%=i %>]" cols="50" rows="3"> <%=BZ %></textarea></td>
		</tr>
	</table>
	<%		}
		}
		else {
	%>
	<table class="datas select-trave" width="98%" id="trave0">
	  <tr>
		<td colspan="4">
		  <span class="showPointer" id="addTrave">添加</span>
		</td>
	  </tr>
	  <tr>
	    <td>选择地接社：</td>
		<td>
		  <select name="TA_ZT_JHDJ/SFID[0]" id="sf0" USEDATA="dataSrc0" SUBCLASS="1"></select>
		  <select name="TA_ZT_JHDJ/CSID[0]" id="cs0" USEDATA="dataSrc0" SUBCLASS="2"></select>
		  <select name="TA_ZT_JHDJ/DJSID[0]"  id="djsid0" USEDATA="dataSrc0" SUBCLASS="3"></select>
		</td>
	  </tr>
	<tr style="display: none">
		<td colspan="4" id="traveInfo0"></td>
   </tr>
	  <tr>
		<td>地接社收客人数：</td>
		<td>
		成人人数:<input type="text" class="smallInput crrs" name="TA_ZT_JHDJ/CRRS[0]" value="" onkeydown="checkNum()"/>
		儿童人数:<input type="text" class="smallInput etrs" name="TA_ZT_JHDJ/ETRS[0]" value="" onkeydown="checkNum()"/>
		</td>
	  </tr>
	  <tr>
		<td>应付团款：</td>
		<td>
		<input type="text" name="TA_ZT_JHDJ/YFZK[0]" value="0" class="smallerInput yfzk" onchange="sumPrice('trave0');"/><font color="red">*</font>
		&nbsp;&nbsp;<font color="red">现付：</font><input type="text" name="TA_ZT_JHDJ/XFJE[0]" value="0"  class="smallerInput xfje" onchange="sumPrice('trave0');"/>
		&nbsp;&nbsp;<font color="red">签单：</font><input type="text" name="TA_ZT_JHDJ/QDJE[0]" value="0"  class="smallerInput qdje" onchange="sumPrice('trave0');"/>
		</td>
	  </tr>
	  <tr>
		<td colspan="4"><img src="<%=request.getContextPath()%>/images/notepad.gif" />&nbsp;<textarea name="TA_ZT_JHDJ/BZ[0]" cols="50" rows="3" > </textarea></td>
	  </tr>
	</table>
	<%
		}
	%>
</div>
<div>
	<table class="datas">
		<tr>
			<td colspan="4" align="right">
				<font color="red">签单总计：</font><input type="text" name="TA_TDJDXXZJB_ZT/DJQDZJ[0]" value="<%="".equals(rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJQDZJ",0))?0:rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJQDZJ",0) %>"  class="smallerInput qdjezj" readonly="readonly"/>
				&nbsp;&nbsp;<font color="red">现付总计：</font><input type="text" name="TA_TDJDXXZJB_ZT/DJXFZJ[0]" value="<%="".equals(rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJXFZJ",0))?0:rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJXFZJ",0) %>"  class="smallerInput xfjezj" readonly="readonly"/>
				&nbsp;&nbsp;<font color="red">应付团款总计：</font><input type="text" name="TA_TDJDXXZJB_ZT/DJZJ[0]" value="<%="".equals(rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJZJ",0))?0:rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJZJ",0) %>" class="smallerInput yfzkzj" readonly="readonly"/>
			</td>
		</tr>
	</table>
</div>
</div>
<div align="center" id="last-sub">
<%if("view".equals(rd.getString("flag"))) {%>
	<input type="button" id="button" value="关闭" onclick="reload();"/>
	<%}else{ %>
	<input type="button" id="button" value="提交" onclick="submitDjTrave()"/>
	<input type="button" id="button" value="关闭" onclick="reload();"/>
<%} %>
</div>
</form>
</body>
</html>
<script type="text/javascript">
<%if(Traverows>0){
	for(int v=0;v<Traverows;v++){
%>
	var linkage = new Linkage("dataSrc<%=v%>", "<%=request.getContextPath()%>/main/data/Travez.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("TA_ZT_JHDJs","SFID",v)%>",1);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("TA_ZT_JHDJs","CSID",v)%>",2);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("TA_ZT_JHDJs","DJSID",v)%>",3);
	
<%}}else{%>
		var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Travez.xml");
		linkage.init();
<%}%>
</script>