<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript">
jQuery(document).ready(function(){
	

	
	 jQuery(".showPointer").hover(function(){
		jQuery(this).css({cursor:'pointer'});
	 });
});
	 function addScenery(){
		var num=jQuery(".select-scenery").size();
		if(jQuery("#city"+(num-1)).val()==""){
				alert("请填写完整后再添加");
				return false;
		}
		 jQuery("#sceneryDiv").append('<table class="datas select-scenery" width="95%" id="scenery'+num+'">'+
		'<tr><td colspan="4"><span>景点计划</span>&nbsp&nbsp&nbsp<span class="showPointer" onclick="addScenery();">&nbsp;&nbsp;添加</span>&nbsp&nbsp&nbsp <span class="showPointer" onclick="delTab(\'scenery'+num+'\')">删除</span></td> </tr>'+
		'<tr>'+
		'<td  colspan="4">请选择景点：'+
		' <select name="TA_ZT_JHJD/SFID['+num+']" id="pro'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="1"></select>'+
		' <select name="TA_ZT_JHJD/CSID['+num+']" class="cityid" id="city'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="2" onchange="getSceneryInfo(this.value,\'sceneryInfo'+num+'\',\'scenery'+num+'\')"></select>'+
		'<input type="hidden" name="TA_ZT_JHJD/TID['+num+']" value="<%=rd.getStringByDI("TA_ZT_JHJD","TID",0) %>"/>'+
		'</td></tr>'+
		'<tr ><td colspan="4" id="sceneryInfo'+num+'"></td></tr></table>');
			var linkage = new Linkage("dataSrc"+num, "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
			linkage.init();
		 }

function delTab(tab){
	jQuery("#"+tab).remove();
	sumPrice(tab);
}
function getSceneryInfo(id,tdName,tbName){
	jQuery("#"+tdName+" .select-scenery").remove();
	if(id!=""){
		var last=jQuery(".cityid").size();
		if(last>1){
			for(var i = 0; i < last; i++){
				var city=jQuery(".cityid:eq("+i+")").val();
					for(var j = 0; j < last; j++){
						var city1=jQuery(".cityid:eq("+j+")").val();
							if(i!=j){
								if(city1==city){
									alert("不能重复选择");
									jQuery("#"+tbName+" .cityid").val(null);
									return false;
								}
							}
					}
				}
		}
	  var obj=jQuery.ajax({url:"ztInitSceneryList.?TA_SCENERY_POINT/CITY_ID="+id+"&TA_SCENERY_POINT/ywlb=z",async:true,dataType:"HTML",cache:false,success:function(){
		  jQuery("#"+tdName).parents("tr").attr("style","");
		  jQuery("#"+tdName).html(obj.responseText);
			}});
	}
}


//使用输入只能为数字
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
	var tmp=0;
	jQuery("#"+tab+" .sc_price").each(function(i){
		var price=parseFloat(this.value);
		var num=jQuery("#"+tab+" .sc_num:eq("+i+")").val();
		tmp+=parseFloat(price*num);
		});
	jQuery("#"+tab+" .gj").val(Math.round(tmp*100)/100);
	var xfxj=0;
	var qdxj=jQuery("#"+tab+" .qdxj").val();
	var xfxj=jQuery("#"+tab+" .xfxj").val();
	if(xfxj>tmp){
		alert("现付金额大于金额总计！");
		jQuery("#"+tab+" .xfxj").val(0);
	}else{
		qdxj=tmp-xfxj;
		jQuery("#"+tab+" .qdxj").val(Math.round(qdxj*100)/100);
	}
	
	
	var qdzj=0;
	var xfzj=0;
	var gjzj=0;
	jQuery(".qdxj").each(function(i){
		 qdzj+=parseFloat(this.value);
		 xfzj+=parseFloat(jQuery(".xfxj:eq("+i+")").val());
		 gjzj+=parseFloat(jQuery(".gj:eq("+i+")").val());
	});
	jQuery("#qdzj").val(Math.round(qdzj*100)/100);
	jQuery("#xfzj").val(Math.round(xfzj*100)/100);
	jQuery("#zj").val(Math.round(gjzj*100)/100);
}

//根据景点ID查询所有价格
function queryAllPriceByScenery(id,itemTab,radioID,JDID){
	
	var dwj = jQuery("input[name='"+radioID+"']:checked").val();
	if(jQuery("input[name='SFXZ"+JDID+"']:checked").val()=='Y'){
		var obj = jQuery.ajax({url:"ztInitSceneryPriceList.?JDID="+JDID+"&dwj="+dwj,async:true,dataType:"HTML",cache:false,success:function(){
			jQuery("#"+itemTab+" .priceInfo").html(obj.responseText);
			jQuery("#"+itemTab+" .priceInfo").parents("tr").attr("style","");
			if(jQuery("#"+itemTab+" .sc_price").val()==null){
				alert("此景点无此类价格，请重新选择价格类型！");
				jQuery("#"+itemTab+" .checkbox").removeAttr("checked");
				jQuery("#"+itemTab+" table.sceneryPrice").remove();
				return false;
			}
			
		}});
	}else{
		jQuery("#"+itemTab+" table.sceneryPrice").remove();
	}
}

function ztScenerySub(){
	var last=jQuery(".cityid").size();
	for(var a=0;a<last;a++){
		var cityid=jQuery(".cityid:eq("+a+")").val();
		if(""==cityid){
			alert("请填写完整");
			return false;
			}
		}
	var ck=true;
	var ifCK=0;
	jQuery(".select-scenery").each(function(){
		
		var tab=this.id;
		jQuery("#"+tab+" [type='checkbox']").each(function(){
			if(this.checked==true){
				ifCK+=1;
			}
			});
		
	});
	if(ifCK<=0){
		alert("一个城市下面请至少选中一个景点！");
		return false;
	}
	if(ck==false){
		return false;
	}

	
	document.ztSceneryForm.submit();
	reload();
}

function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}
</script>
<title>景点计划</title>
</head>
<body>
<form action="ztSceneryGeneral." name="ztSceneryForm" method="post">
<div id="lable"><span >景点计划</span><span class="showPointer" onclick="addScenery()">&nbsp;&nbsp;添加</span></div>
<div id="bm-table">
<div id="sceneryDiv">
<%
//所选择的省市信息
int rowsTop = rd.getTableRowsCount("sqlCityDis");
int priceRow = rd.getTableRowsCount("sqlSceneryPrice");
int SceneryRows = rd.getTableRowsCount("sqlSceneryInfo");
if(rowsTop > 0) {
	out.print("<input type='hidden' name='state' value='Edit'>");
	for(int i = 0; i < rowsTop; i++){	
		String DisCSID = rd.getStringByDI("sqlCityDis","CSID",i);
		
%>
<table class="datas select-scenery" width="95%" id="scenery<%=i %>">
  <tr>
	<td colspan="4"><span>景点计划</span>
		<span class="showPointer" onclick="addScenery()">&nbsp;&nbsp;添加</span>&nbsp;&nbsp;
		<span class="showPointer" onclick="delTab('scenery<%=i %>')">删除</span>
	</td>
	
  </tr>
  <tr>
	<td colspan="4">请选择景点：
	  <select name="TA_ZT_JHJD/SFID[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
	  <select name="TA_ZT_JHJD/CSID[<%=i %>]"  class="cityid" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2" 
		  	  onchange="getSceneryInfo(this.value,'sceneryInfo<%=i %>','scenery<%=i %>')"></select>
	  <input type="hidden" name="TA_ZT_JHJD/TID[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_JHJD","TID",0) %>"/>
	</td>
  </tr>
  <tr>
	<td colspan="4" class="sceneryTd" id="sceneryInfo<%=i %>">
  <%
  	int tmp1 = 0;
  	String flag=rd.getString("flag");
	for(int j = 0; j < SceneryRows; j++){
		String CSID = rd.getStringByDI("sqlSceneryInfo","CSID",j);
		String JDID = rd.getStringByDI("sqlSceneryInfo","JDID",j);//景点ID（基础数据）
		String cmpny_name = rd.getStringByDI("sqlSceneryInfo","cmpny_name",j);
		String city = rd.getStringByDI("sqlSceneryInfo","csid",j);
		String xfxj = rd.getStringByDI("sqlSceneryInfo","xfxj",j);
		String qdxj = rd.getStringByDI("sqlSceneryInfo","qdxj",j);
		String hj = rd.getStringByDI("sqlSceneryInfo","hj",j);
		String sfxz = rd.getStringByDI("sqlSceneryInfo","sfxz",j);
		String jhid = rd.getStringByDI("sqlSceneryInfo","id",j);
		String dwj = rd.getStringByDI("sqlSceneryInfo","jgqj",j);
		
		if(DisCSID.equals(CSID)){
  %>
  
	  <table class="datas select-scenery" width="100%" id="itemTab<%=JDID %>">
		<tr>
	  	  <td width="8%" align="right">景点名称：
	  	  </td>
	      <td width="15%"><font color="red"><%=cmpny_name %></font></td>
	  	  <td width="20%">
	  		<input name="dwj<%=JDID %>" type="radio" value="1" <%if("1".equals(dwj)){ %>checked="checked"<%} %> id="dwj" onclick="queryAllPriceByScenery(this.value,'itemTab<%=JDID %>','dwj<%=JDID %>','<%=JDID %>');"/>永久
	  		<input name="dwj<%=JDID %>" type="radio" value="2" <%if("2".equals(dwj)){ %>checked="checked"<%} %> id="dwj" onclick="queryAllPriceByScenery(this.value,'itemTab<%=JDID %>','dwj<%=JDID %>','<%=JDID %>');"/>淡季价格
	  		<input name="dwj<%=JDID %>" type="radio" value="3" <%if("3".equals(dwj)){ %>checked="checked"<%} %> id="dwj" onclick="queryAllPriceByScenery(this.value,'itemTab<%=JDID %>','dwj<%=JDID %>','<%=JDID %>');"/>旺季价格
	  	  </td>
	  	  <td width="10%">
	  	  	<input type="hidden" name="JDID<%=CSID %>/JDID[<%=tmp1 %>]" value="<%=JDID %>" />
	   		<input type="checkbox" name="SFXZ<%=JDID %>" <%if("Y".equals(sfxz)){ %>checked="checked"<%} %>
	   	   onclick="queryAllPriceByScenery(this.value,'itemTab<%=JDID %>','dwj<%=JDID %>','<%=JDID %>');sumPrice('scenery<%=JDID%>_tab');" 
	   	   value="Y" class="checkbox" <%if(("view").equals(flag)){ %>disabled="disabled"<%} %> />勾中选择此景点
	  	  </td>
		</tr>
		<tr>
		  <td class="priceInfo" colspan="4">
		  	<table class="datas sceneryPrice" style="text-align: center" id="scenery<%=JDID%>_tab">

		  	  <tr id="first-tr">
				<td >价格名称</td>
				<td >价格</td>
				<td >人数</td>
			  </tr>
		<%
			
			int tmp = 0;
			for(int k = 0;k < priceRow; k++) {
				String RS = rd.getStringByDI("sqlSceneryPrice","RS",k);
				String JGMC = rd.getStringByDI("sqlSceneryPrice","JGMC",k);
				String JGMCID = rd.getStringByDI("sqlSceneryPrice","JGMCID",k);
				String JG = rd.getStringByDI("sqlSceneryPrice","JG",k);
				String upPK = rd.getStringByDI("sqlSceneryPrice","JHID",k);
				if(upPK.equals(jhid)){
			%>
			  <tr>
			 	 <td width="32%"><%=JGMC %>
				  <input type="hidden" name="TA_ZT_JHJDJG<%=JDID %>/JGMCID[<%=tmp %>]" value="<%=JGMCID %>"/>
				  <input type="hidden" name="TA_ZT_JHJDJG<%=JDID %>/JGMC[<%=tmp %>]" value="<%=JGMC %>" />
				</td>
				<td width="32%"><%=JG %>
				  <input type="hidden" name="TA_ZT_JHJDJG<%=JDID %>/JG[<%=tmp %>]" value="<%=JG %>" class="sc_price"/>
				</td>
				<td width="36%">
				  <input type="text" name="TA_ZT_JHJDJG<%=JDID %>/RS[<%=tmp %>]" class="sc_num" value="<%=RS %>"  onkeydown="checkNum()" onchange="sumPrice('scenery<%=JDID%>_tab')"/>
				</td>
			  </tr>	
			<%	
				tmp ++;
				}
			}
			%>
			  <tr>
			    <td align="right" colspan="4">
			    <font color="red">签单小计金额：</font>
				  <input type="text" name="QDXJ<%=JDID %>"  value="<%=qdxj %>" class="smallerInput qdxj" readonly="readonly"  onchange="sumPrice('scenery<%=JDID%>_tab')" onkeydown="checkNum1(this)"/>/元&nbsp;&nbsp;&nbsp;
			    <font color="red">现付小计金额：</font>
				  <input type="text" name="XFXJ<%=JDID %>" value="<%=xfxj %>" onchange="sumPrice('scenery<%=JDID%>_tab')" class="smallerInput xfxj" onkeydown="checkNum1(this)"/>/元&nbsp;&nbsp;&nbsp;
				  <font color="red">共计：</font>   
				  <input type="text" name="HJ<%=JDID %>" value="<%=hj %>" readonly="readonly" class="smallerInput gj" />/元
			    </td>
			  </tr>
			</table>
		  </td>
		</tr>
</table>

				<%
				tmp1++;
						}
					}%>
	</td>
  </tr>
</table>
					
					<% 
				}
			}else{
				%>
<table class="datas select-scenery" width="95%" id="scenery0">
  <tr>
	<td colspan="4"><span>景点计划</span> <span class="showPointer" onclick="addScenery()">&nbsp;&nbsp;添加</span></td>
  </tr>
  <tr>
	<td>请选择景点：
  	  <select name="TA_ZT_JHJD/SFID[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
	  <select name="TA_ZT_JHJD/CSID[0]"  class="cityid" id="city0" USEDATA="dataSrc0" SUBCLASS="2" 
	  		  onchange="getSceneryInfo(this.value,'sceneryInfo0','scenery0')"></select>
	  <input type="hidden" name="TA_ZT_JHJD/TID[0]" value="<%=rd.getStringByDI("TA_ZT_JHJD","TID",0) %>"/>
	</td>
  </tr>
  <tr style="display: none">
	<td colspan="4" id="sceneryInfo0"></td>
  </tr>
</table>
<%
}
%>
</div>
<table class="datas" >
  <tr>
	<td><span>费用合计</span></td>
  </tr>
  <tr>
	<td align="right">
	<font color="red">签单金额总计：</font>
	  <input type="text" name="TA_TDJDXXZJB_ZT/QDJDZJ" id="qdzj" readonly="readonly" value="<%= "".equals(rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDJDZJ",0))?0:rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDJDZJ",0) %>" class="smallerInput"/>/元&nbsp;&nbsp;&nbsp; 
	<font color="red">现付金额总计：</font>
	  <input type="text" name="TA_TDJDXXZJB_ZT/XFJDZJ" id="xfzj" readonly="readonly" value="<%= "".equals(rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFJDZJ",0))?0:rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFJDZJ",0) %>" class="smallerInput"/>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">总计：</font>
	  <input type="text" name="TA_TDJDXXZJB_ZT/JDZJ" id="zj" readonly="readonly" value="<%= "".equals(rd.getStringByDI("TA_TDJDXXZJB_ZTs","JDZJ",0))?0:rd.getStringByDI("TA_TDJDXXZJB_ZTs","JDZJ",0) %>" class="smallerInput"/>/元</td>
  </tr>
</table>
</div>
<div align="center" id="last-sub">
	<input type="hidden" name="userno" value="<%=sd.getString("userno") %>" />
<%if(!"view".equals(rd.getString("flag"))){ %>
	<input type="button" id="button" value="提交" onclick="ztScenerySub();"/>
	<input type="button" id="button" value="返回" onclick="reload();"/>
<%} %>

	
</div>
</form>
</body>
</html>
<script type="text/javascript">
<%
if(rowsTop > 0) {
	for(int h = 0;h < rowsTop; h++){
%>
	var linkage = new Linkage("dataSrc<%=h%>", "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=h%>","<%=rd.getStringByDI("sqlCityDis","sfid",h)%>",1);
	linkage.initLinkage("dataSrc<%=h%>","<%=rd.getStringByDI("sqlCityDis","csid",h)%>",2);
<%	}
}else{%>
	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
	linkage.init();
<%
} %>
</script>
