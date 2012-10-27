<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<title>导游计划</title>
<script type="text/javascript">
	function insertGuidePlan(){
		document.p_guide_form.submit();
		reload();
	}	
	function reload(){
		parent.parent.location.reload(); 
		parent.parent.GB_hide(); 
	}
	
	var row=<%=rd.getTableRowsCount("selectGuideInfo")%>;
	function addGuide(){
		jQuery("#guide").append(
			'<tr class="guideType">'+
			'<td>'+
			'<input type="hidden" name="TA_ZT_JHDY/TID['+parseInt(row+1)+']" value="<%=rd.getString("TID")%>">'+
			'<select id="dyid'+parseInt(row+1)+'" name="TA_ZT_JHDY/DYID['+parseInt(row+1)+']" onchange="setDyxm(this.value);">'+
			<%	
				for(int i=0;i<rd.getTableRowsCount("SelectAllGuide");i++){	
			%>
			'<option value="<%=rd.getStringByDI("SelectAllGuide","USERNO",i) %>"><%=rd.getStringByDI("SelectAllGuide","USERNAME",i) %></option>'+
			<%} %>
			'</select>'+
			'<input type="hidden" name="TA_ZT_JHDY/DYXM['+parseInt(row+1)+']" id="dyxm'+parseInt(row+1)+'" value="<%=rd.getStringByDI("SelectAllGuide","USERNAME",0) %>"/></td>'+
			'<td><input name="TA_ZT_JHDY/DYLK['+parseInt(row+1)+']"/></td>'+
			'<td><input name="TA_ZT_JHDY/DFF['+parseInt(row+1)+']"/></td>'+
			'<td><input type="checkbox" name="TA_ZT_JHDY/BXR['+parseInt(row+1)+']" value="Y" class="bx" onclick="checkBox('+parseInt(row+1)+')"></input></td>'+
		  	'</tr>');
		row++;
		//alert(jQuery("#guide").html());
	}
	
	function delTableRow(){
		var num=jQuery("tr.guideType").size()-1;
		if(num>0){
			$("tr.guideType:eq("+num+")").remove();
			num--;
		}
	}
	
	//根据下拉列表的索引，取出下拉列表的显示值，即导游姓名置入hidden域TA_ZT_JHDY/DYXM
	function setDyxm(idx){
		jQuery("tr.guideType").each(function(i){
			var dyxm=document.getElementById("dyid"+i).options[document.getElementById("dyid"+i).selectedIndex].text;
			document.getElementById("dyxm"+i).value=dyxm;
			//alert(dyxm);
		});		
	}
	
	function checkBox(str){
		jQuery(".guideType").each(function(i){
			if(i==str){
				jQuery(".bx:eq("+i+")").attr("checked");
			}else{
				jQuery(".bx:eq("+i+")").removeAttr("checked");
			}
		});
	}

</script>
</head>

<body>
<form name="p_guide_form" action="ztAddGuidePlan." method="post">
<div id="lable"><span >导游计调</span><span class="showPointer" title="增加导游" onclick="addGuide();">增加</span>
	<span class="showPointer" title="删除导游" onclick="delTableRow();">删除</span></div>
<div id="bm-table">
<div id="guideDiv">
<span>选择导游↓</span>
	<input type="hidden" name="TA_ZT_JHDY/TID[0]" value="<%=rd.getString("TID")%>">
<table id="guide" class="datas" width="98%"  style="text-align:center">
  <tr id="first-tr">
	<td>导游</td>
	<td>导游实际领款</td>
	<td>导服费</td>
	<td>指定他报销</td>
  </tr>
  <tr class="guideType">
  	<td>
		<select id="dyid0" name="TA_ZT_JHDY/DYID[0]" onchange="setDyxm(this.value);">
			<%
				for(int i=0;i<rd.getTableRowsCount("SelectAllGuide");i++){
					String userNO = rd.getStringByDI("SelectAllGuide","USERno",i);
			%>
			<option value="<%=rd.getStringByDI("SelectAllGuide","userNO",i) %>"><%=rd.getStringByDI("SelectAllGuide","USERNAME",i) %></option>
			<%} %>
		</select>
		<input type="hidden" name="TA_ZT_JHDY/DYXM[0]" id="dyxm0" value="<%=rd.getStringByDI("SelectAllGuide","USERNAME",0) %>"/>
	</td>
	<td><input name="TA_ZT_JHDY/DYLK[0]"/></td>
	<td><input name="TA_ZT_JHDY/DFF[0]"/></td>
	<td><input type="checkbox" name="TA_ZT_JHDY/BXR[0]" value="Y" class="bx" checked="checked" onclick="checkBox(0)"></input></td>
  </tr>
</table>

</div>
</div>
<div align="center" id="last-sub">
	<input type="hidden" name="TA_ZT_JHDY/ZDR" value="<%=sd.getString("userno") %>"/>
	<input type="button" id="button" value="提交" onclick="insertGuidePlan();"/>
	<input type="button" id="button" value="关闭" onclick="javascript:window.parent.parent.GB_hide();"/>
</div>
<input type="hidden" value="1" name="userno" />
</form>
</body>
</html>