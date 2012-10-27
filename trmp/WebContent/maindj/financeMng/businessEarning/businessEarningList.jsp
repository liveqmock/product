<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
   
<%
String gID = rd.getString("groupID");
String bDate = rd.getString("bDate");
String eDate = rd.getString("eDate");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>营收清款记录</title>
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>营收清款记录</title>

<script type="text/javascript">


function findByLike(){

	var groupID = $("#groupID").val();
	var bDate = $("#bDate").val();
	var eDate = $("#eDate").val();
	var state = $("#state").val();
	var url="businessEarningList.?pageSize=10&pageNO=1";
	if(groupID != '')
		url += "&groupID="+groupID;
	if(bDate != '')
		url += "&bDate="+bDate;
	if(eDate != '')
		url += "&eDate="+eDate;
	if(state != '')
		url += "&state="+state;
	window.location.href=url;
}
function addLCshop(ztsid)
{
	TB_show('应收款清款','<%=request.getContextPath() %>/maindj/financeMng/businessEarning/beInit4Update.?id='+ztsid+'*TB_iframe=true&height=400&width=500','');
}
function showHistory(ztsid)
{
	TB_show('应收款收款记录','<%=request.getContextPath() %>/maindj/financeMng/businessEarning/showHistory.?id='+ztsid+'*TB_iframe=true&height=400&width=500','');
}
</script>
</head>

<body>
<form name="dj_guide_form" method="post">
<div id="lable"><span>营收清款记录列表</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">团号：</td>
			<td><input type="text" name="groupID" id="groupID" value="<%=rd.getString("groupID") %>"/></td>
			<td>开始日期：</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:false});" id="bDate" value="<%=rd.getString("bDate") %>"></input></td>
			<td>结束日期：</td>
			<td><input type="text" name="eDate" onFocus="WdatePicker({isShowClear:false,readOnly:false});" id="eDate" value="<%=rd.getString("eDate") %>"></input></td>
			<td>收款状态：</td>
		  	<td>
			<select id="state" name="state" > 			
			<option value="" >***请选择***</option>
			<option value="Y"   >已收款</option>
			<option value="N" >未收款</option>
			</select>
		    </td>	
			<td><a href="###" onclick="findByLike()"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>团号</td>
			<td>组团社名称</td>
			<td>清款状态</td>
			<td>收款金额</td>
			<td>操作</td>
		</tr>
	<%
	int rsEarningRows = rd.getTableRowsCount("rsEarning");
	int rsEaning4CountRows = rd.getTableRowsCount("rsEaning4Count");
	
	for(int j=0;j<rsEaning4CountRows; j++){
		String groupIDInner = rd.getStringByDI("rsEaning4Count","tid",j);//拿一个团ID出来
		String counts = rd.getStringByDI("rsEaning4Count","sl",j);//团ID的次数
		int c = Integer.parseInt(counts);

		int sameCount = 0;
		for(int i=0;i<rsEarningRows;i++) {
			String groupID = rd.getStringByDI("rsEarning","tid",i);
			String id = rd.getStringByDI("rsEarning","id",i);
			String traID = rd.getStringByDI("rsEarning","ztsid",i);
			String cmpnyName = rd.getStringByDI("rsEarning","cmpny_name",i);
			
			String yfk = rd.getStringByDI("rsEarning","yfk",i);
			String qkzt_yfk = rd.getStringByDI("rsEarning","qkzt_yfk",i);
	%>
            <%
            if(groupIDInner.equals(groupID) && c > 1 && sameCount == 0){
			%>
		<tr>
			<td rowspan="<%=counts %>"><%=groupID %></td>
			<td><%=cmpnyName %></td>
	  		<td><%=qkzt_yfk %></td>
	  		<td><%=yfk %>元</td>	  
	  		<td>
	  			<a href="###" onclick="addLCshop('<%=id %>');"  >
           		<img alt="应收款结算" src="<%=request.getContextPath()%>/images/edit.gif">[应收款结算]&nbsp;</a>
           		<a href="###" onclick="showHistory('<%=id %>');"  >
           		<img alt="应收款清款记录" src="<%=request.getContextPath()%>/images/edit.gif">[应收款清款记录]&nbsp;</a>
           	</td>
		</tr>
			<%
				sameCount ++;
    		}else if(groupIDInner.equals(groupID) && c > 1 && sameCount > 0){
    		%>	
    		<tr>
			<td><%=cmpnyName %></td>
	  		<td><%=qkzt_yfk %></td>
	  		<td><%=yfk %>元</td>	  
	  		<td>
	  			<a href="###" onclick="addLCshop('<%=id %>');"  >
           		<img alt="应收款结算" src="<%=request.getContextPath()%>/images/edit.gif">[应收款结算]&nbsp;</a>
           		<a href="###" onclick="showHistory('<%=id %>');"  >
           		<img alt="应收款清款记录" src="<%=request.getContextPath()%>/images/edit.gif">[应收款清款记录]&nbsp;</a>
            </td>
         </tr>
    		<%	
    		}
    		else if(groupIDInner.equals(groupID) && c == 1 && sameCount == 0){
    		%>
    	<tr>
    		<td><%=groupID %></td>
			<td><%=cmpnyName %></td>
	  		<td><%=qkzt_yfk %></td>
	  		<td><%=yfk %>元</td>	  
	  		<td>
	  			<a href="###" onclick="addLCshop('<%=id %>');"  >
           		<img alt="应收款结算" src="<%=request.getContextPath()%>/images/edit.gif">[应收款结算]&nbsp;</a>
           		<a href="###" onclick="showHistory('<%=id %>');"  >
           		<img alt="应收款清款记录" src="<%=request.getContextPath()%>/images/edit.gif">[应收款清款记录]&nbsp;</a>
            </td>
         </tr>
    		<%
    		}
            %>
	  		
	<%
	}
		sameCount = 0 ;
}%>
	</table>
</div>

</form>
</body>
</html>