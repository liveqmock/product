<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>组团财务对账</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script src="<%=request.getContextPath() %>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>


<script type="text/javascript">
function findByLike1(){//tab1 查询action
	document.zt_cwdz_form1.action="initOrderNoList4Sub.?state=0&target=0&pageSize=10&pageNO=1";
	document.zt_cwdz_form1.submit();
}
function findByLike2(){//tab2 查询action
	document.zt_cwdz_form2.action="confirmingBillList.?pageSize=10&pageNO=1";
	document.zt_cwdz_form2.submit();
}
function findByLike3(){//tab3 查询action
	document.zt_cwdz_form3.action="successBillList.?pageSize=10&pageNO=1";
	document.zt_cwdz_form3.submit();
}
function submitBill(DDH){//tab1 提交订单 action
	window.location.href="submitBill.?pageSize=10&pageNO=1&DDH="+DDH;
}
function cwdzSubmit2(DDH){
	document.zt_cwdz_form2.action="CwdzSubmit2.?pageSize=10&pageNO=1&DDH="+DDH;
	document.zt_cwdz_form2.submit();
}
$(function() {//获取tabs焦点
	$("#tabs").tabs({selected:<%=rd.getString("target").equals("")?request.getParameter("target"):rd.getString("target")%>});
});

function ddxz(xz,xzzj){//多项全选 全不选 checkbox选择器方法
	if(jQuery("."+xzzj).is(":checked")){
		jQuery("."+xz).each(function(i){
			jQuery("."+xz+":eq("+i+")").attr("checked","checked");
		});
	}else{
		jQuery("."+xz).each(function(i){
			jQuery("."+xz+":eq("+i+")").removeAttr("checked");
		});
	}
}
function openPeople1(iName,groupId){
	if( $("#"+iName+" .groupTd div").size()==0){
		 var obj=$.ajax({
		 	 url:"notConfirmBillDetail.?groupId="+groupId+"&state=0",
			 async:true,
			 dataType:"HTML",
			 cache:false,
			 success:function(){
				 $("#"+iName).attr("style","");
		  		 $("#"+iName+" .groupTd").html(obj.responseText);
			 }
		});
	}else{
		if($("#"+iName).is(":hidden")){
			 $("#"+iName).css({display:"block"});
		}else{
			 $("#"+iName).css({display:"none"});
		}
	}
}
function openPeople2(iName,groupId){
	if( $("#"+iName+" .groupTd div").size()==0){
		 var obj=$.ajax({
		 	 url:"confirmingBillDetail.?groupId="+groupId+"&state=1",
			 async:true,
			 dataType:"HTML",
			 cache:false,
			 success:function(){
				 $("#"+iName).attr("style","");
		  		 $("#"+iName+" .groupTd").html(obj.responseText);
			 }
		});
	}else{
		if($("#"+iName).is(":hidden")){
			 $("#"+iName).css({display:"block"});
		}else{
			 $("#"+iName).css({display:"none"});
		}
	}
}
function openPeople3(iName,groupId){
	if( $("#"+iName+" .groupTd div").size()==0){
		 var obj=$.ajax({
		 	 url:"successBillDetail.?groupId="+groupId+"&state=2",
			 async:true,
			 dataType:"HTML",
			 cache:false,
			 success:function(){
				 $("#"+iName).attr("style","");
		  		 $("#"+iName+" .groupTd").html(obj.responseText);
			 }
		});
	}else{
		if($("#"+iName).is(":hidden")){
			 $("#"+iName).css({display:"block"});
		}else{
			 $("#"+iName).css({display:"none"});
		}
	}
}
</script>
</head>
<body>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1" onclick="findByLike1();">未对账账单记录</a></li>
		<li><a href="#tabs-2" onclick="findByLike2();">对账中账单记录</a></li>
		<li><a href="#tabs-3" onclick="findByLike3();">已对账账单记录</a></li>
	</ul>
	
<div id="tabs-1">
<%
	int pageNO=0;
	int pageSize=0;
	int totalPage =0;
	int rowsCount = 0;
	if(rd.getTableRowsCount("rsCwdzInfo")>0){
		pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsCwdzInfo", "pagesCount");
		rowsCount=(Integer)rd.getAttr("rsCwdzInfo", "rowsCount");
	}
%>   
<form  name="zt_cwdz_form1" method="post">
	  <table class="datas" width="100%">
		<tr>
		  <td width="6%">团开始日期：</td>
		  <td width="12%"><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td width="6%">团结束日期：</td>
		  <td width="12%"><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td width="25%" colspan="2"></td>	
		</tr>
		
		<tr>
		  <td>团号：</td>
		  <td><input type="text" name="groupID" /></input></td>
		  <td>线路名称：</td>
		  <td><input type="text"  name="xlmc"/></td>
		<td >
			订单号：
		</td>
		<td>
			<input type="text" name="ddh" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="###" onclick="findByLike1();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>		
	  </table>


	<table class="datas" >
	<%
		int RowsNo11 = rd.getTableRowsCount("rsCwdzInfo");
		for(int i = 0; i < RowsNo11; i++){
			    String groupID = rd.getString("rsCwdzInfo","ID",i);//团号
				String XLMC = rd.getString("rsCwdzInfo","XLMC",i);
				String USERFROM = rd.getString("rsCwdzInfo","szlxsmc",i);
				String BDATE = rd.getString("rsCwdzInfo","begin_date",i);
				String EDATE = rd.getString("rsCwdzInfo","end_date",i);
				String ET = rd.getString("rsCwdzInfo","adult_count",i);
				String CR = rd.getString("rsCwdzInfo","children_count",i);
				String BX = rd.getString("rsCwdzInfo","BX",i);
				String THJ = rd.getString("rsCwdzInfo","THJ",i);
				String JJ = rd.getString("rsCwdzInfo","JJ",i);
				String DDS = rd.getString("rsCwdzInfo","dds",i);
				Double YFKHJ = Double.parseDouble(THJ)+Double.parseDouble(BX)+Double.parseDouble(JJ);
				int RS = Integer.parseInt(ET)+Integer.parseInt(CR);
	%>
		<tr id="first-tr">
			<td align="center" width="10%">团号</td>
			<td align="center" width="20%">线路名称</td>
			<td align="center" width="20%">供应商</td>
			<td align="center" colspan="2" width="10%">日期	</td>
			<td align="center" width="5%">人数</td>
			<td align="center" width="5%">订单数</td>
			<td align="center" width="15%">应付款</td>
			<td align="center" width="5%">状态</td>
		</tr>
		<tr  class="ykorderList<%=i%>"  onclick="openPeople1('ykorderList<%=i%>','<%=groupID%>');" style="color: green;" >
            <td align="center" > <%=groupID %></td>
	  		<td align="center" > <%=XLMC %></td>
	  		<td align="center" > <%=USERFROM %></td>
	  		<td align="center" colspan="2">从<%=BDATE %><br>至<%=EDATE %> </td>	
	  		<td align="center" > <%=RS %></td>
	  		<td align="center" > <%=DDS %></td>
	  		<td align="center" > <%=YFKHJ %>(<%=THJ %>+<%=BX %>+<%=JJ %>)</td>
	  		<td align="center" > 未对账</td>
         </tr>	
		<tr id="ykorderList<%=i%>" style="display: none;">
			<td class="groupTd" colspan="9">
			</td>
		</tr>
         <%} %>
	</table>
	<div id="page">
		<%String url=request.getContextPath()+"/main/moneyMng/account/initOrderNoList4Sub.?pageSize=10&pageNO=";%>
		<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
		<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
		<span class="pagination-sum">
						第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
						共<%=rowsCount %> 条记录，
						每页 <%=pageSize%>条
		</span>
		<span class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
		<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
		<span class="go-to-page" >
			跳转到第：<input type="text" id="gotopage"/> 页
		</span>
		<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
	</div>
</form>
</div>










<div id="tabs-2">
<%
	int pageNO2=0;
	int pageSize2=0;
	int totalPage2 =0;
	int rowsCount2 = 0;
	if(rd.getTableRowsCount("rsCwdzInfo")>0){
		pageNO2=Integer.parseInt(rd.getString("pageNO"));
		pageSize2=Integer.parseInt(rd.getString("pageSize"));
		totalPage2=(Integer)rd.getAttr("rsCwdzInfo", "pagesCount");
		rowsCount2=(Integer)rd.getAttr("rsCwdzInfo", "rowsCount");
	}
%>   
<form  name="zt_cwdz_form2" method="post">
	  <table class="datas" width="100%">
		<tr>
		  <td width="6%">团开始日期：</td>
		  <td width="12%"><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td width="6%">团结束日期：</td>
		  <td width="12%"><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td width="25%" colspan="2"></td>	
		</tr>
		
		<tr>
		  <td>团号：</td>
		  <td><input type="text" name="groupID" /></input></td>
		  <td>线路名称：</td>
		  <td><input type="text"  name="xlmc"/></td>
		<td >
			订单号：
		</td>
		<td>
			<input type="text" name="ddh" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="###" onclick="findByLike2();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>		
	  </table>

<table class="datas" >
	<%
		int RowsNo21 = rd.getTableRowsCount("rsCwdzInfo");
		for(int i = 0; i < RowsNo21; i++){
			    String groupID = rd.getString("rsCwdzInfo","ID",i);//团号
				String XLMC = rd.getString("rsCwdzInfo","XLMC",i);
				String USERFROM = rd.getString("rsCwdzInfo","szlxsmc",i);
				String BDATE = rd.getString("rsCwdzInfo","begin_date",i);
				String EDATE = rd.getString("rsCwdzInfo","end_date",i);
				String ET = rd.getString("rsCwdzInfo","adult_count",i);
				String CR = rd.getString("rsCwdzInfo","children_count",i);
				String BX = rd.getString("rsCwdzInfo","BX",i);
				String THJ = rd.getString("rsCwdzInfo","THJ",i);
				String JJ = rd.getString("rsCwdzInfo","JJ",i);
				String DDS = rd.getString("rsCwdzInfo","dds",i);
				Double YFKHJ = Double.parseDouble(THJ)+Double.parseDouble(BX)+Double.parseDouble(JJ);
				int RS = Integer.parseInt(ET)+Integer.parseInt(CR);
	%>
		<tr id="first-tr">
			<td align="center" width="10%">团号</td>
			<td align="center" width="20%">线路名称</td>
			<td align="center" width="20%">供应商</td>
			<td align="center" colspan="2" width="10%">日期	</td>
			<td align="center" width="5%">人数</td>
			<td align="center" width="5%">订单数</td>
			<td align="center" width="15%">应付款</td>
			<td align="center" width="5%">状态</td>
		</tr>
		<tr  class="groupList<%=i%>"  onclick="openPeople2('groupList<%=i%>','<%=groupID%>');" style="color: green;" >
            <td align="center" > <%=groupID %></td>
	  		<td align="center" > <%=XLMC %></td>
	  		<td align="center" > <%=USERFROM %></td>
	  		<td align="center" colspan="2">从<%=BDATE %><br>至<%=EDATE %> </td>	
	  		<td align="center" > <%=RS %></td>
	  		<td align="center" > <%=DDS %></td>
	  		<td align="center" > <%=YFKHJ %>(<%=THJ %>+<%=BX %>+<%=JJ %>)</td>
	  		<td align="center" > 对账中</td>
         </tr>	
		<tr id="groupList<%=i%>" style="display: none;">
			<td class="groupTd" colspan="9">
			</td>
		</tr>
         <%} %>
	</table>
	<div id="page">
		<%String url2=request.getContextPath()+"/main/moneyMng/account/confirmingBillList.?pageSize=10&pageNO=";%>
		<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url2%>","<%=totalPage%>")'></span>		
		<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url2%>","<%=totalPage%>")'></span>
		<span class="pagination-sum">
						第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
						共<%=rowsCount %> 条记录，
						每页 <%=pageSize%>条
		</span>
		<span class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url2%>","<%=totalPage%>")'></span>
		<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url2%>","<%=totalPage%>")'></span>
		<span class="go-to-page" >
			跳转到第：<input type="text" id="gotopage"/> 页
		</span>
		<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url2 %>")'></span>
	</div>
</form>
</div>









<div id="tabs-3">
 <form  name="zt_cwdz_form3" method="post">
	  <table class="datas" width="100%">
		<tr>
		  <td width="6%">团开始日期：</td>
		  <td width="12%"><input type="text" name="bdate" id="dateB" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td width="6%">团结束日期：</td>
		  <td width="12%"><input type="text" name="edate" id="dateE" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
		  <td width="25%" colspan="2"></td>	
		</tr>
		
		<tr>
		  <td>团号：</td>
		  <td><input type="text" name="groupID" /></input></td>
		  <td>线路名称：</td>
		  <td><input type="text"  name="xlmc"/></td>
		  <td >
			订单号：
		  </td>
		  <td>
			<input type="text" name="ddh" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="###" onclick="findByLike3();"><img alt="搜索" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>		
	  </table>

<table class="datas" >
	<%
		int RowsNo31 = rd.getTableRowsCount("rsCwdzInfo");
		for(int i = 0; i < RowsNo31; i++){
			    String groupID = rd.getString("rsCwdzInfo","ID",i);//团号
				String XLMC = rd.getString("rsCwdzInfo","XLMC",i);
				String USERFROM = rd.getString("rsCwdzInfo","szlxsmc",i);
				String BDATE = rd.getString("rsCwdzInfo","begin_date",i);
				String EDATE = rd.getString("rsCwdzInfo","end_date",i);
				String ET = rd.getString("rsCwdzInfo","adult_count",i);
				String CR = rd.getString("rsCwdzInfo","children_count",i);
				String BX = rd.getString("rsCwdzInfo","BX",i);
				String THJ = rd.getString("rsCwdzInfo","THJ",i);
				String JJ = rd.getString("rsCwdzInfo","JJ",i);
				String DDS = rd.getString("rsCwdzInfo","dds",i);
				Double YFKHJ = Double.parseDouble(THJ)+Double.parseDouble(BX)+Double.parseDouble(JJ);
				int RS = Integer.parseInt(ET)+Integer.parseInt(CR);
	%>
		<tr id="first-tr">
			<td align="center" width="10%">团号</td>
			<td align="center" width="20%">线路名称</td>
			<td align="center" width="20%">供应商</td>
			<td align="center" colspan="2" width="10%">日期	</td>
			<td align="center" width="5%">人数</td>
			<td align="center" width="5%">订单数</td>
			<td align="center" width="15%">应付款</td>
			<td align="center" width="5%">状态</td>
		</tr>
		<tr  class="yczdList<%=i%>"  onclick="openPeople3('yczdList<%=i%>','<%=groupID%>');" style="color: green;" >
            <td align="center" > <%=groupID %></td>
	  		<td align="center" > <%=XLMC %></td>
	  		<td align="center" > <%=USERFROM %></td>
	  		<td align="center" colspan="2">从<%=BDATE %><br>至<%=EDATE %> </td>	
	  		<td align="center" > <%=RS %></td>
	  		<td align="center" > <%=DDS %></td>
	  		<td align="center" > <%=YFKHJ %>(<%=THJ %>+<%=BX %>+<%=JJ %>)</td>
	  		<td align="center" > 已对账</td>
         </tr>	
		<tr id="yczdList<%=i%>" style="display: none;">
			<td class="groupTd" colspan="9">
			</td>
		</tr>
         <%} %>
	</table>
	<div id="page">
		<%String url3=request.getContextPath()+"/main/moneyMng/account/successBillList.?pageSize=10&pageNO=";%>
		<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url3%>","<%=totalPage%>")'></span>		
		<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url3%>","<%=totalPage%>")'></span>
		<span class="pagination-sum">
						第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
						共<%=rowsCount %> 条记录，
						每页 <%=pageSize%>条
		</span>
		<span class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url3%>","<%=totalPage%>")'></span>
		<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url3%>","<%=totalPage%>")'></span>
		<span class="go-to-page" >
			跳转到第：<input type="text" id="gotopage"/> 页
		</span>
		<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url3 %>")'></span>
	</div>
</form>
</div>



</div>
</html>