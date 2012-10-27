<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<title>购物返利计算公式</title>
<script type="text/javascript">
	$(function(){
		$('[type=checkbox]').click(function (){
			var checkClass = $(this).attr("class");
			if(checkClass){
				if($("."+checkClass).is(":checked")){
					$("#"+checkClass).attr("checked","checked");
				}else{
					$("#"+checkClass).removeAttr("checked");
				}
			}
			var checkID = $(this).attr("id");
			if(checkID){
				if($("#"+checkID).is(":checked")){
					$("."+checkID).each(function (){
						$(this).attr("checked","checked");
					});
				}else{
					$("."+checkID).each(function (){
						$(this).removeAttr("checked");
					});
				}
			}
		});
	});
	//BUTTON样式初始化
	$(function(){
		$("input:submit,input:button").button();
		//动态添加
		$("#divAdd").bind("click",function(){
			var trRows = $(".rowItem").size();
			$("#percentOfShop").append('<tr class="rowItem">'+
					  '<td>'+
					  	'<input type="checkbox" name="TA_SHOPFORMULA/ID['+trRows+']" id="checkboxEle"/></td>'+
					  	'<input type="hidden" name="TA_SHOPFORMULA/orgid['+trRows+']" value="<%=sd.getString("orgid") %>"/>'+
					  '<td>'+
					  	  '<input type="text" name="TA_SHOPFORMULA/PERSONS['+trRows+']"/> &times; '+ 
					  	  '<input type="text" name="TA_SHOPFORMULA/PRICE['+trRows+']"/> &#43; '+
						  '<input type="text" name="TA_SHOPFORMULA/PERCENT['+trRows+']"/>%'+
					  '</td>'+
					'</tr>');
		});
		//提交
		$("#submitBtn").bind("click",function(){
			document.percentForm.submit();
		});

		//删除checked
		$("#divDel").bind("click",function(){
			$(".selAll:checked").each(function(){
				$(".rowItem:eq("+$(".selAll").index(this)+")").remove();
		});
		
	});
	});

	function showMsg(){
		alert("保存成功！");
	}
</script>
</head>
<body >
<form name="percentForm" method="post" action="saveShopPercent.">
<div id="lable"><span>购物返利计算公式   </span></div>
<div class="select-div" id="divAdd">
  <span id="add-icon"></span> 
  <span class="text">添加</span>
</div>
<div class="select-div" id="divDel">
  <span id="del-icon"></span> 
  <span	class="text">删除</span>
</div>
<div id="list-table">
  <table class="datas" id="percentOfShop">
	<tr id="first-tr">
	  <td width="15" id="selectAll"><input type="checkbox" id="selAll"></input></td>
	  <td>人数 &times; 单价(人头费) &#43; 消费额百分比(%)</td>
	</tr>
<%
int rows = rd.getTableRowsCount("TA_SHOPFORMULAs");
if(rows > 0){
	for(int i=0;i<rows;i++){
		String id = rd.getStringByDI("TA_SHOPFORMULAs","id",i);
		String persons = rd.getStringByDI("TA_SHOPFORMULAs","PERSONS",i);
		String price = rd.getStringByDI("TA_SHOPFORMULAs","PRICE",i);
		String percent = rd.getStringByDI("TA_SHOPFORMULAs","PERCENT",i);
%>
	<tr class="rowItem">
	  <td>
	  	<input type="checkbox" name="TA_SHOPFORMULA/ID[<%=i%>]" class="selAll" value="<%=id %>"/>
	  	<input type="hidden" name="TA_SHOPFORMULA/orgid[<%=i%>]" value="<%=sd.getString("orgid") %>"/>
	  </td>
	  <td><input type="text" name="TA_SHOPFORMULA/PERSONS[<%=i %>]" value="<%=persons %>"/> &times; 
	  	  <input type="text" name="TA_SHOPFORMULA/PRICE[<%=i %>]" value="<%=price %>"/> &#43; 
	  	  <input type="text" name="TA_SHOPFORMULA/PERCENT[<%=i %>]" value="<%=percent %>"/>%
	  </td>
	</tr>
<%	}
}else{
%>
	
<%
}%>
  </table>

	<table class="datas">
	  <tr>
		<td align="center">
		  <input type="button" id="submitBtn" value="提    交"/>&nbsp;&nbsp;
		  <input type="button" value="返    回" onclick="window.history.go(-1)"/>
		</td>
	  </tr>
	</table>
</div>	
</form>	
</body>
</html>