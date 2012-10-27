<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script src="<%=request.getContextPath() %>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>


<style type="text/css">
	body{margin: 0;padding: 0;font-size: 12px;}
	#top{background: url(<%=request.getContextPath()%>/images/ToolBarBg.gif) repeat-x;height: 35px;text-align: center;line-height: 35px;}
	#print{background: url(<%=request.getContextPath()%>/images/Print.gif) no-repeat 0 7px;width: 55px;text-indent: 15px;float: left;cursor: pointer;}
	#word{background: url(<%=request.getContextPath()%>/images/word.gif) no-repeat 0 7px;width: 82px;text-indent: 15px;float: left;cursor: pointer;}
	#close{background: url(<%=request.getContextPath()%>/images/printClose.gif) no-repeat 0 11px;width: 49px;text-indent: 10px;float: left;cursor: pointer;}
	#center-top{background: url(<%=request.getContextPath()%>/images/body_04.jpg) no-repeat;width: 793px;height: 33px;text-align: center}
	#center-cen{background: url(<%=request.getContextPath()%>/images/body_05.jpg) repeat-y;width: 793px;}
	#button{background: url(<%=request.getContextPath()%>/images/body_06.jpg) no-repeat;width: 793px;}
</style> 


<script type="text/javascript">
$(function() {
	$("#tabs").tabs({selected:<%=rd.getString("target").equals("")?request.getParameter("target"):rd.getString("target")%>});
});
function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
		    event.returnValue=false;
}
	function findByLike1(){
		document.find_form1.action="moneyClear1.?qkzt=N&pageSize=10&pageNO=1";
		document.find_form1.submit();
	}
	function findByLike2(){
		document.find_form2.action="moneyClear2.?qkzt=Y&pageSize=10&pageNO=1";
		document.find_form2.submit();
	}

function sumRs(){
	var yingfu=0;
	var yifu=0;
	var bcfk=0;
	var ye=0;
	var yifuje=0;
	jQuery(".yingfu").each(function(i){
		yingfu=parseInt(this.value,10);
		yifu=parseInt(jQuery(".yifu:eq("+i+")").val(),10);
		bcfk=parseInt(jQuery(".bcfk:eq("+i+")").val(),10);
		yifuje=bcfk+yifu;
		if(yifuje>yingfu){//本次付款+已付款 不能大于应付款
			alert("超出还款总额，请查仔细");
			jQuery(".bcfk:eq("+i+")").val(0);
		}else{
			ye=yingfu-yifu-bcfk;
			jQuery(".ye:eq("+i+")").val(ye);//余额计算
			jQuery(".yifuje:eq("+i+")").val(yifuje);
		}
	});
}
function addSub(tid){
	var yingfu=0;
	var yifu=0;
	var bcfk=0;
	var ye=0;
	jQuery(".yingfu").each(function(i){
		yingfu=parseInt(this.value,10);
		yifu=parseInt(jQuery(".yifu:eq("+i+")").val(),10);
		bcfk=parseInt(jQuery(".bcfk:eq("+i+")").val(),10);
		
		if((bcfk+yifu)>yingfu){//本次付款+已付款 不能大于应付款
			alert("超出还款总额，请查仔细");
			jQuery(".bcfk:eq("+i+")").val(0);
			return false;
		}else{
			ye=yingfu-yifu-bcfk;
			jQuery(".ye:eq("+i+")").val(ye);//余额计算
		}
	});
	document.find_form1.action="addMoneyClear.?groupID="+tid;
	document.find_form1.submit();

}
</script>
</head>
<body>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1" onclick="findByLike1();">未清款</a></li>
		<li><a href="#tabs-2" onclick="findByLike2();">已清款</a></li>
	</ul>
<div id="tabs-1">
<form name="find_form1" method="post">
  <table class="datas" width="90%">
	<tr>
	  <td>开始日期：</td>
	  <td><input type="text" name="dateb"  onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
	  <td>结束日期：</td>
	  <td><input type="text" name="datee"  onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
	</tr>
	<tr>
	  <td>团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
	  <td><input type="text" name="tid" ></input></td>
	  <td>订单号：</td>
	  <td><input type="text" name="ddh" ></input></td>
	</tr>
	<tr>
	 <td>批发商名称：</td>
	  <td><input type="text" name="pfs" ></input></td>
	  	<td>营业部名称：</td>
	  <td><input type="text" name="mdNm" ></input></td>

	</tr>
	<tr>
		<td colspan="4" align="center">
			<a href="###" onclick="findByLike1();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a>
		</td>
	</tr>
  </table>
	
<div id="list-table">
	<table class="datas" >
	<%
		int roww1 = rd.getTableRowsCount("moneyclear");
		for(int i = 0;i < roww1; i++) {
			String groupID = rd.getStringByDI("moneyclear","tid",i);
	%>
		<tr id="first-tr">
			<td width="10%">团号</td>
			<td width="10%">线路名称</td>
			<td width="10%">供应商</td>
			<td width="10%">日期</td>
			<td width="10%">人数</td>
			<td width="10%">应付款</td>
			<td width="10%">清款状态</td>
		</tr>

		<tr>
            <td><%=rd.getStringByDI("moneyclear","tid",i) %></td>
	  		<td><%=rd.getStringByDI("moneyclear","xlmc",i) %></td>
	  		<td><%=rd.getStringByDI("moneyclear","userfrom",i) %></td>
	  		<td>(<%=rd.getStringByDI("moneyclear","days",i) %>天)<%=rd.getStringByDI("moneyclear","bdate",i) %>至<%=rd.getStringByDI("moneyclear","edate",i) %></td>	  
	  		<td><%=rd.getStringByDI("moneyclear","rs",i) %></td>
	  		<td><%=rd.getStringByDI("moneyclear","yfkhj",i) %></td>
	  		<td><%=rd.getStringByDI("moneyclear","js_zt",i) %></td>
         </tr>	
         </table>
         
		<table class="datas" >
		<tr id="first-tr">
			<td>营业部名称</td>
			<td>订单号</td>
			<td>应付金额</td>
			<td>已付金额</td>
			<td>本次付款</td>
			<td colspan="2">备注</td>
		</tr>
		<tr>
            <td width="10%"><%=rd.getStringByDI("moneyclear","orgname",i) %></td>
	  		<td width="10%"><%=rd.getStringByDI("moneyclear","ddh",i) %></td>
	  		<td width="10%"><input type="text" class="yingfu" name="DDH<%=groupID %>/YINGFU[<%=i %>]"  value="<%=rd.getStringByDI("moneyclear","yfkhj",i) %>"  readonly="readonly"  / ></td>
	  		<td width="10%"><input type="text"  class="yifu"  name="DDH<%=groupID %>/YIFU[<%=i %>]" value="<%=rd.getStringByDI("moneyclear","hj",i) %>"  readonly="readonly"  / ></td>	  
	  		<td width="10%"><input type="text"  class="bcfk" name="DDH<%=groupID %>/BCFK[<%=i %>]"  value="<%=rd.getStringByDI("moneyclear","fk",i) %>"  onkeydown="checkNum()" onchange="sumRs()" / ></td>
	  		<td colspan="2" width="20%"><%=rd.getStringByDI("moneyclear",",REMARK",i) %><input type="hidden"  id="detail" name="DDH<%=groupID %>/BZ[<%=i %>]" size="40"/>
	  			<input type="hidden" class="ye" name="DDH<%=groupID %>/YE[<%=i %>]"  />	
	  			<input type="hidden" class="yifuje" name="DDH<%=groupID %>/YIFUJE[<%=i %>]" />	
				<input type="hidden" name="DDH<%=groupID %>/DDH[<%=i %>]"  value="<%=rd.getStringByDI("moneyclear","ddh",i)%>"/>
						  		
	  		</td>
         </tr>		 
	<tr>
		<td colspan="7" width="100%">
			<input type="button"  value="提交" onclick="addSub('<%=rd.getStringByDI("moneyclear","tid",i)%>');"/>
		</td>
	</tr>
	<%} %>
	</table>
</div>

</form>
</div>






<div id="tabs-2">
<form name="find_form2" method="post">
  <table class="datas" width="90%">
	<tr>
	  <td>开始日期：</td>
	  <td><input type="text" name="dateb"  onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
	  <td>结束日期：</td>
	  <td><input type="text" name="datee"  onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
	</tr>
	<tr>
	  <td>团&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
	  <td><input type="text" name="tid" ></input></td>
	  <td>订单号：</td>
	  <td><input type="text" name="ddh" ></input></td>
	</tr>
	<tr>
	 <td>批发商名称：</td>
	  <td><input type="text" name="pfs" ></input></td>
	  	<td>营业部名称：</td>
	  <td><input type="text" name="mdNm" ></input></td>

	</tr>
	<tr>
			<td colspan="4" align="center">
			<a href="###" onclick="findByLike2();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a>
		</td>
	</tr>
  </table>
</form>	
<form name="group_form" method="post">
	
<div id="list-table">
	<table class="datas" >
	<%
		int roww2 = rd.getTableRowsCount("moneyclear");
		for(int i = 0;i < roww2; i++) {
 	%>
		<tr id="first-tr">
			<td width="10%">团号</td>
			<td width="10%">线路名称</td>
			<td width="10%">供应商</td>
			<td width="10%">日期</td>
			<td width="10%">人数</td>
			<td width="10%">应付款</td>
			<td width="10%">清款状态</td>
		</tr>

		<tr>
            <td><%=rd.getStringByDI("moneyclear","tid",i) %></td>
	  		<td><%=rd.getStringByDI("moneyclear","xlmc",i) %></td>
	  		<td><%=rd.getStringByDI("moneyclear","userfrom",i) %></td>
	  		<td>(<%=rd.getStringByDI("moneyclear","days",i) %>天)<%=rd.getStringByDI("moneyclear","bdate",i) %>至<%=rd.getStringByDI("moneyclear","edate",i) %></td>	  
	  		<td><%=rd.getStringByDI("moneyclear","rs",i) %></td>
	  		<td><%=rd.getStringByDI("moneyclear","yfkhj",i) %></td>
	  		<td><%=rd.getStringByDI("moneyclear","js_zt",i) %></td>
         </tr>	
         </table>
         
		<table class="datas" >
		<tr id="first-tr">
			<td>营业部名称</td>
			<td>订单号</td>
			<td>应付金额</td>
			<td>已付金额</td>
			<td>本次付款</td>
			<td colspan="2">备注</td>
		</tr>
		<tr>
            <td width="10%"><%=rd.getStringByDI("moneyclear","orgname",i) %></td>
	  		<td width="10%"><%=rd.getStringByDI("moneyclear","ddh",i) %></td>
	  		<td width="10%"><%=rd.getStringByDI("moneyclear","yfkhj",i) %></td>
	  		<td width="10%"><%=rd.getStringByDI("moneyclear","hj",i) %></td>	  
	  		<td width="10%"><%=rd.getStringByDI("moneyclear","fk",i) %></td>
	  		<td colspan="2" width="20%"><%=rd.getStringByDI("moneyclear",",REMARK",i) %></td>
         </tr>	
	<%}%>
	</table>
</div>
</form>
</div>




</div>
</body>
</html>