<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import=" java.sql.Blob"%>
<%@page import="java.io.InputStreamReader"%>
<%@include file="/common.jsp"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<script type="text/javascript">

function tableMove(){
	var tableRows = jQuery(".valueType").size();
	jQuery("#store").append('<tr class="valueType"><td><select name="TA_DJ_XLJG/JGLX['+tableRows+']">'+
			  <%
			  int typeRows = rd.getTableRowsCount("JGLX");
			  for(int j=0;j<typeRows;j++){
				  String dmz = rd.getStringByDI("JGLX","dmz",j);
				  String dmsm = rd.getStringByDI("JGLX","dmsm1",j);
			  %>
			    '<option value="<%=dmz %>"><%=dmsm %></option>'+
			  <%
			  }%>
			  '</select></td>'+
			'<td><input name="TA_DJ_XLJG/JG['+tableRows+']" value="0"  id="price_th[0]" onkeydown="checkNum()" class="smallerInput" maxlength="5"/></td>'+
			'<td><input name="TA_DJ_XLJG/DFC['+tableRows+']" value="0" onkeydown="checkNum()"class="smallerInput" maxlength="5"/></td>'+
			'<td ><textarea name="TA_DJ_XLJG/bz['+tableRows+']" rows="2" cols="55" onpropertychange="if(this.value.length>100){this.value=this.value.slice(0,100)}"> </textarea></td>'+
		  '</tr>');
	tableRows ++;
}

function delTabRow(){
	var tableRows = jQuery(".valueType").size();
	if(tableRows > 0){
		var idx = parseInt(tableRows-1);
		jQuery("tr.valueType:eq("+idx+")").remove();
		tableRows --;
	}
}

jQuery(document).ready(function(){
	jQuery(".personNum").change(function(){
		var tmps = 0;
		jQuery("store").each(
			function(){
				tmps+=parseInt(this.value);
			}
		);
		var a=tmps-jQuery("div.personInfo").size()>0?tmps-jQuery("div.personInfo").size():Math.abs(tmps-jQuery("div.personInfo").size());
		if(tmps-jQuery("div.personInfo").size()>0){
		  for(var i=0;i<a;i++){
			row += 1;
			jQuery("#tourist").append('');
		  }
		}else{
			row = row - a;
			for(var d=a;d>0;d--){
				var idx=parseInt(jQuery("div.personInfo").size()-1);
				jQuery("div.personInfo:eq("+idx+")").remove();
				}
			}
		}
	);
});

function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		    event.returnValue=false;
}

	function commit(){
		document.lineInfoForm.submit();
		reload();
	}

	function reload(){
		parent.parent.location.reload(); 
		parent.parent.GB_hide(); 
	}

	function showplan(num){
		
		if(num==1){
			document.getElementById("plan-data1").style.display="block";
			document.getElementById("plan-data2").style.display="block";
		}
		if(num==2){
			document.getElementById("plan-data1").style.display="block";
			document.getElementById("plan-data2").style.display="none";
		}
	}
</script>

<script type="text/javascript">
    window.onload = function()
    {
        CKEDITOR.replace('XLMX');
    };
</script>
<title>修改线路信息</title>
</head>

<body>
<form action="djEditLine." name="lineInfoForm" method="post">
<div class="width-98">
<div id="top-select">
<div class="select-div" onclick="commit();">
  <span id="ok-icon"></span> 
  <span class="text">提交</span>
</div>
<div class="select-div" onclick="javascript:reset();">
  <span id="reset-icon"></span> 
  <span	class="text">重置</span>
</div>
	<span class="tishi">带<font color="red">*</font>号为必填项</span>
</div>
 <div class="add-table"> 
	<table border="0">
	  <tr >
		<td align="right">线路名称：</td>
  		<td colspan="5">
  			<input type="hidden" name="TA_DJ_LINEMNG/XLID" value="<%=rd.getStringByDI("TA_DJ_LINEMNGs","XLID",0) %>"/>
  			<input type="hidden" name="TA_DJ_LINEMNG/XLID[0]@oldValue" value="<%=rd.getStringByDI("TA_DJ_LINEMNGs","XLID",0) %>"/>
  			<input type="hidden" name="TA_DJ_LINEMNG/userno" value="<%=rd.getStringByDI("TA_DJ_LINEMNGs","userno",0) %>"/>
		  	<input type="text" name="TA_DJ_LINEMNG/XLMC" value="<%=rd.getStringByDI("TA_DJ_LINEMNGs","XLMC",0) %>" id="line_name" style="width:500px;" maxlength="75"/>
		  	<span>*</span> 
	  	</td>
	  </tr>
	  <tr>	
	  	<td align="right">线路区域：</td>
        <td>
		  <select name="TA_DJ_LINEMNG/XLQY">
	    	<%
	  		String XLQY = rd.getStringByDI("TA_DJ_LINEMNGs","XLQY",0);
   		    for(int i=0;i<rd.getTableRowsCount("XLQY");i++) {
   		    	String dmz = rd.getStringByDI("XLQY","dmz",i);
   		  	%>
   		    <option value="<%=dmz %>" <%if(dmz.equals(XLQY)){ %> selected="selected" <%} %>><%=rd.getStringByDI("XLQY","dmsm1",i) %></option>
   		  	<%
   		    }
	  		%>
	  	  </select>
		</td>
		<td align="right">天&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：</td>
        <td><input type="text" name="TA_DJ_LINEMNG/tS" value="<%=rd.getStringByDI("TA_DJ_LINEMNGs","ts",0) %>" id="DAY_COUNTS" onkeydown="checkNum();" class="smallInput" maxlength="2"/></td>
        <td></td><td></td>
	  </tr>
	  <tr>
        <td  align="right">是否自备车：</td>
        <td>
          <%
        	String SFZBC = rd.getStringByDI("TA_DJ_LINEMNGs","SFZBC",0);
          %>
		  <input type="radio" name="TA_DJ_LINEMNG/SFZBC" value="1" <%if("1".equals(SFZBC)){ %>checked="checked"<%} %>/>是
	      <input type="radio" name="TA_DJ_LINEMNG/SFZBC" value="2" <%if("2".equals(SFZBC)){ %>checked="checked"<%} %>/>否
        	
		</td>
      	<td  align="right">加点购物：</td>
       	<td>
       	<%
        	String jd = rd.getStringByDI("TA_DJ_LINEMNGs","jd",0);
       		String gw = rd.getStringByDI("TA_DJ_LINEMNGs","gw",0);
        %>
   		  <input type="checkbox" name="TA_DJ_LINEMNG/JD" value="1" <%if("1".equals(jd)){ %>checked="checked"<%} %>/>加点
   		  <input type="checkbox" name="TA_DJ_LINEMNG/GW" value="1" <%if("1".equals(gw)){ %>checked="checked"<%} %>/>购物
   		</td>
   		<td  align="right">游客类型：</td>
        <td >
        	<%
        	String yklx = rd.getStringByDI("TA_DJ_LINEMNGs","YKLX",0);
        	%>
        	<input type="radio" name="TA_DJ_LINEMNG/YKLX" value="1" <%if("1".equals(yklx)){ %>checked="checked"<%} %>/>团队
        	<input type="radio" name="TA_DJ_LINEMNG/YKLX" value="2" <%if("2".equals(yklx)){ %>checked="checked"<%} %>/>散客
        </td>
	  </tr>
	  <tr>
	 	<td  align="right">出发交通：</td>
        <td >
	  	  <select name="TA_DJ_LINEMNG/CFJTGJ">
	  		<%
	  		String CFJTGJ = rd.getStringByDI("TA_DJ_LINEMNGs","CFJTGJ",0);
	  		for(int i=0;i<rd.getTableRowsCount("JTGJ");i++) {
   		    	String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  		%>
	  		<option value="<%=dmz %>" <%if(dmz.equals(CFJTGJ)){ %> selected="selected" <%} %>><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
	  		<%
	  		}%>
	  	  </select>
	  	</td>
	  	<td align="right">返回交通：</td>
       	 <td >
   		  <select name="TA_DJ_LINEMNG/FHJTGJ">
	  		<%
	  		String FHJTGJ = rd.getStringByDI("TA_DJ_LINEMNGs","FHJTGJ",0);
	  		for(int i=0;i<rd.getTableRowsCount("JTGJ");i++) {
   		    	String dmz = rd.getStringByDI("JTGJ","dmz",i);
	  		%>
	  		<option value="<%=dmz %>" <%if(dmz.equals(FHJTGJ)){ %> selected="selected" <%} %>><%=rd.getStringByDI("JTGJ","dmsm1",i) %></option>
	  		<%
	  		}%>
	  	  </select>
   		</td>
      	<td align="right">发班计划：</td>
  		<td>
	  	  <select name="TA_DJ_LINEMNG/FBJH" id="select-condition" onchange="showplan(this.value)"  >
	  		<option value="">***请选择***</option>
	  		<%
	  		String FBJH = rd.getStringByDI("TA_DJ_LINEMNGs","FBJH",0);
	  		for(int i=0;i<rd.getTableRowsCount("FBJH");i++) {
   		    	String dmz = rd.getStringByDI("FBJH","dmz",i);
	  		%>
	  		<option value="<%=dmz %>" <%if(dmz.equals(FBJH)){ %> selected="selected" <%} %>><%=rd.getStringByDI("FBJH","dmsm1",i) %></option>
	  		<%
	  		}%>
	  	  </select><span>*</span>
	  	</td>
     </tr>
	</table>
</div>
	
<div class="add-table"> 
<table class="datas" border="0">
  <tr id="first-tr">
  	<td colspan="8">发班计划时间详细</td>
  </tr>
  <tr id="plan-data1" style="<%if(!"".equals(FBJH)){ %>display: block;<%}else{ %>display: none;<%} %>" >
  	<td align="right">开始日期：</td>
  	<td colspan="3">
  		<input type="text" name="TA_DJ_FBJHTMP/B_DATE" value="<%=rd.getStringByDI("TA_DJ_FBJHTMPs","B_DATE",0) %>" onFocus="WdatePicker({isShowClear:false,readOnly:true});"  id="bDate"/>
  		<input type="hidden" name="TA_DJ_FBJHTMP/XLID" value="<%=rd.getStringByDI("TA_DJ_FBJHTMPs","XLID",0) %>"/>
  		<input type="hidden" name="TA_DJ_FBJHTMP/XLID[0]@oldValue" value="<%=rd.getStringByDI("TA_DJ_FBJHTMPs","XLID",0) %>"/>
  	</td>
  	<td align="right">结束日期：</td>
  	<td colspan="3"><input type="text" name="TA_DJ_FBJHTMP/e_date" value="<%=rd.getStringByDI("TA_DJ_FBJHTMPs","e_DATE",0) %>" onFocus="WdatePicker({isShowClear:false,readOnly:true});"  id="eDate"/></td>
  </tr>
  <tr id="plan-data2" style="<%if(!"".equals(FBJH)){ %>display: block;<%}else{ %>display: none;<%} %>" >
  	<td align="right">周期：</td>
  	<%
  	String sun = rd.getStringByDI("TA_DJ_FBJHTMPs","SUN",0);
  	String mon = rd.getStringByDI("TA_DJ_FBJHTMPs","MON",0);
  	String tues = rd.getStringByDI("TA_DJ_FBJHTMPs","TUES",0);
  	String wed = rd.getStringByDI("TA_DJ_FBJHTMPs","wed",0);
  	String thurs = rd.getStringByDI("TA_DJ_FBJHTMPs","THURS",0);
  	String friday = rd.getStringByDI("TA_DJ_FBJHTMPs","friday",0);
  	String sat = rd.getStringByDI("TA_DJ_FBJHTMPs","sat",0);
  	%>
  	<td><input type="checkbox" name="TA_DJ_FBJHTMP/SUN" value="Y" <%if("Y".equals(sun)){ %> checked="checked" <%} %>/>星期日</td>
  	<td><input type="checkbox" name="TA_DJ_FBJHTMP/MON" value="Y" <%if("Y".equals(mon)){ %> checked="checked" <%} %>/>星期一</td>
  	<td><input type="checkbox" name="TA_DJ_FBJHTMP/TUES" value="Y" <%if("Y".equals(tues)){ %> checked="checked" <%} %>/>星期二</td>
  	<td><input type="checkbox" name="TA_DJ_FBJHTMP/WED" value="Y" <%if("Y".equals(wed)){ %> checked="checked" <%} %>/>星期三</td>
  	<td><input type="checkbox" name="TA_DJ_FBJHTMP/THURS" value="Y" <%if("Y".equals(thurs)){ %> checked="checked" <%} %>/>星期四</td>
  	<td><input type="checkbox" name="TA_DJ_FBJHTMP/FRIDAY" value="Y" <%if("Y".equals(friday)){ %> checked="checked" <%} %>/>星期五</td>
  	<td><input type="checkbox" name="TA_DJ_FBJHTMP/SAT" value="Y" <%if("Y".equals(sat)){ %> checked="checked" <%} %>/>星期六</td>
  </tr>
</table>
</div>

<div style="text-align: right"> 
 <a href="###" onclick="tableMove();"><img alt="添加" src="<%=request.getContextPath() %>/images/add.gif"/> 添加</a>&nbsp;&nbsp;
 <a href="###" onclick="delTabRow('store',1,'price_th');"><img alt="删除" src="<%=request.getContextPath() %>/images/del.gif"/> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
</div> 
<div id="list-table">
<table class="datas" id="store">
  <tr id="first-tr">
	<td width="15%">价格类型</td>
    <td width="10%">价格</td>
    <td width="10%">单房差</td>
    <td width="45%">备注</td>
  </tr>
<%
int priceRows = rd.getTableRowsCount("TA_DJ_XLJGs");
for(int i=0;i<priceRows;i++) {
	
	String priceType = rd.getStringByDI("TA_DJ_XLJGs","JGLX",i);
	String price = rd.getStringByDI("TA_DJ_XLJGs","JG",i);
	String dfc = rd.getStringByDI("TA_DJ_XLJGs","dfc",i);
	String bz = rd.getStringByDI("TA_DJ_XLJGs","bz",i);
%>
  <tr class="valueType">
	<td>
	  <select name="TA_DJ_XLJG/JGLX[<%=i %>]">
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
	<td><input name="TA_DJ_XLJG/JG[<%=i %>]" value="<%=price %>" id="price_th[0]" onkeydown="checkNum();" class="smallerInput" maxlength="5"></input></td>
	<td><input name="TA_DJ_XLJG/DFC[<%=i %>]" value="<%=dfc %>" onkeydown="checkNum();" class="smallerInput" maxlength="5"/></td>
	<td >
	  <textarea name="TA_DJ_XLJG/bz[<%=i %>]" rows="2" cols="55" onpropertychange="if(this.value.length>100){this.value=this.value.slice(0,100)}"><%=bz %></textarea>
	</td>
  </tr>
<%	
}%>
</table>
</div>
<div class="add-table">
<table class="datas" width="100%">
  <tr id="first-tr">
  	<td colspan="8">线路行程明细</td>
  </tr>
  <tr>
    <td colspan="8">
      <!-- <FCK:editor id="LINE_DETAIL" width="100%" height="420"></FCK:editor>-->
      <textarea id="XLMX" name="XLMX" rows="20" cols="105" >
       	<%
				Object obj = rd.get("TA_DJ_LINEMNGs", "XLMX",0);
				Blob blob = null;
				int read=0;
				if(null != obj){
					blob = (Blob) obj;
					java.io.InputStream in=blob.getBinaryStream();
					InputStreamReader isr = new InputStreamReader(in, "GBK");
					char[] chars = new char[1];
					read = 0;
					while ((read = isr.read(chars)) != -1) {
						out.print(new String(chars));
					}
				}
		%>
      </textarea> 
     </td>
   </tr>
    <tr>
	 	<td align="right">备注：</td>
		<td colspan="5"><textarea name="TA_DJ_LINEMNG/TBYQ" cols="99" rows="4" onpropertychange="if(this.value.length>200){this.value=this.value.slice(0,200)}"><%=rd.getStringByDI("TA_DJ_LINEMNGs", "TBYQ",0) %></textarea></td>
	 </tr>
</table>
</div>
</div>
</form>

</body>
</html>
