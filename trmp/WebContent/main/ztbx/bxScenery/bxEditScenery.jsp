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
	 jQuery("#addScenery").click(function(){
		var num=jQuery(".select-scenery").size();
		if(jQuery("#city"+(num-1)).val()==""){
				alert("请填写完整后再添加");
				return false;
		}
		 jQuery("#sceneryDiv").append('<table class="datas select-scenery" width="95%" id="scenery'+num+'">'+
		'<tr><td colspan="4"><span>景点计调</span> <span class="showPointer" onclick="delTab(\'scenery'+num+'\')">删除</span></td> </tr>'+
		'<tr>'+
		'<td width="45%">请选择景点：'+
		' <select name="TA_ZT_BXJD/SFID['+num+']" id="pro'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="1"></select>'+
		' <select name="TA_ZT_BXJD/CSID['+num+']" class="cityid" id="city'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="2" onchange="getSceneryInfo(this.value,\'sceneryInfo'+num+'\')"></select>'+
		'<input type="hidden" name="TA_ZT_BXJD/TID['+num+']" value="<%=rd.getStringByDI("TA_ZT_BXJD","TID",0) %>"/>'+
		'</td></tr>'+
		'<tr ><td colspan="4" id="sceneryInfo'+num+'"></td></tr></table>');
			var linkage = new Linkage("dataSrc"+num, "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
			linkage.init();
		 });
});

function delTab(tab){
	jQuery("#"+tab).remove();
}
function getSceneryInfo(id,tdName){
	if(id!=""){
		var last=jQuery(".cityid").size();
		if(last>1){
			for(var a=0;a<last-1;a++){
				var city=jQuery(".cityid:eq("+a+")").val();
				if(id==city){
					alert("不能重复选择");
					return false;
					}
				}
			}
	  var obj=jQuery.ajax({url:"ztInitSceneryListRbt.?TA_SCENERY_POINT/CITY_ID="+id,async:true,dataType:"HTML",cache:false,success:function(){
		  jQuery("#"+tdName).parents("tr").attr("style","");
		  jQuery("#"+tdName).html(obj.responseText);
			}});
	}
}

//根据景点ID查询所有价格
function queryAllPriceByScenery(id,itemTab,radioID,JDID){

	var dwj = jQuery("input[name='"+radioID+"']:checked").val();
	if(jQuery("#"+itemTab+" .checkbox").attr("checked") == true){
		var obj = jQuery.ajax({url:"ztInitSceneryPriceListRbt.?JDID="+JDID+"&dwj="+dwj,async:true,dataType:"HTML",cache:false,success:function(){
			jQuery("#"+itemTab+" .priceInfo").html(obj.responseText);
			jQuery("#"+itemTab+" .priceInfo").parents("tr").attr("style","");
			}});
	}else{
		jQuery("#"+itemTab+" table.sceneryPrice").remove();
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
		var price=this.value;
		var num=jQuery("#"+tab+" .sc_num:eq("+i+")").val();
		tmp+=parseFloat(price*num);
		});
	jQuery("#"+tab+" .qdxj").attr("readonly","");
	jQuery("#"+tab+" .gj").val(Math.round(tmp*100)/100);

	var qdxj=jQuery("#"+tab+" .qdxj").val();
	if(qdxj>tmp){
		jQuery("#"+tab+" .qdxj").val(Math.round(tmp*100)/100);
		qdxj=tmp;
		}
	jQuery("#"+tab+" .xfxj").val(Math.round((tmp-qdxj)*100)/100);
	
	var qdzj=0;
	var xfzj=0;
	jQuery(".qdxj").each(function(i){
		{
		var qdxj=this.value;
		var xfxj=jQuery(".xfxj:eq("+i+")").val();
			qdzj+=parseFloat(qdxj);
			xfzj+=parseFloat(xfxj);
			}});
	jQuery("#qdzj").val(Math.round(qdzj*100)/100);
	jQuery("#xfzj").val(Math.round(xfzj*100)/100);
	jQuery("#zj").val(Math.round((xfzj+qdzj)*100)/100);
}
function noWay(tab){
	if(jQuery("#"+tab+" .checkbox").attr("checked") == false){
		alert("此景点已经业务审核通过，变更无效！");
		jQuery("#"+tab+" .checkbox").attr("checked","checked");
	}
}
function fall(){
	alert("无法修改！");
	jQuery("#zj").focus();
	return false;
}

function djScenerySub(){
	var last=jQuery(".cityid").size();
	for(var a=0;a<last;a++){
		var cityid=jQuery(".cityid:eq("+a+")").val();
		if(""==cityid){
			alert("请填写完整");
			return false;
			}
		}
	var ck=true;
	jQuery(".select-scenery").each(function(){
		var ifCK=0;
		var tab=this.id;
		jQuery("#"+tab+" [type='checkbox']").each(function(){
			if(this.checked==true){
				ifCK+=1;
			}
			});
		if(ifCK==0){
		alert("没有选择景点的地区请删除");
		ck=false;
			}
	});
	if(ck==false){
		return false;
	}
	document.djSceneryForm.submit();
	reload();
}

function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}
</script>
<title>景点报账</title>
</head>
<body>
<form action="ztSceneryGeneralRbt." name="djSceneryForm" method="post">
<div id="lable"><span >景点报账</span></div>
<div id="bm-table">
<div id="sceneryDiv">
<%
//所选择的省市信息
int rowsTop = rd.getTableRowsCount("rbtCityDis");
int priceRow = rd.getTableRowsCount("rbtSceneryPrice");
int SceneryRows = rd.getTableRowsCount("rbtSceneryInfo");
if(rowsTop > 0) {
	out.print("<input type='hidden' name='state' value='Edit'>");
	for(int i = 0; i < rowsTop; i++){	
		String DisCSID = rd.getStringByDI("rbtCityDis","CSID",i);
		
%>
<table class="datas select-scenery" width="95%" id="scenery<%=i %>">
  <tr>
	<td colspan="4"><span>景点报账</span>
	<%if(i==0){ %>
		<!--<span class="showPointer" id="addScenery">添加</span>
	--><%}else{ %>
		<!--<span class="showPointer" onclick="delTab('scenery<%=i %>')">删除</span>
	--><%} %>
	</td>
	
  </tr>
  <tr>
	<td>请选择景点：
	  <select name="TA_ZT_BXJD/SFID[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
	  <select name="TA_ZT_BXJD/CSID[<%=i %>]"  class="cityid" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2" ></select>
	  <input type="hidden" name="TA_ZT_BXJD/TID[<%=i %>]" value="<%=rd.getStringByDI("TA_ZT_BXJD","TID",0) %>"/>
	</td>
  </tr>
  <%
  	int tmp1 = 0;
	for(int j = 0; j < SceneryRows; j++){
		String CSID = rd.getStringByDI("rbtSceneryInfo","CSID",j);
		String JDID = rd.getStringByDI("rbtSceneryInfo","JDID",j);//景点ID（基础数据）
		String cmpny_name = rd.getStringByDI("rbtSceneryInfo","cmpny_name",j);
		String city = rd.getStringByDI("rbtSceneryInfo","csid",j);
		String xfxj = rd.getStringByDI("rbtSceneryInfo","xfxj",j);
		String qdxj = rd.getStringByDI("rbtSceneryInfo","qdxj",j);
		String hj = rd.getStringByDI("rbtSceneryInfo","hj",j);
		String sfxz = rd.getStringByDI("rbtSceneryInfo","sfxz",j);
		String BXID = rd.getStringByDI("rbtSceneryInfo","id",j);
		String dwj = rd.getStringByDI("rbtSceneryInfo","jgqj",j);
		String bz = rd.getStringByDI("rbtSceneryInfo","BZ",j);
		if(DisCSID.equals(CSID)){
  %>
  <tr>
	<td colspan="4" class="sceneryTd" id="sceneryInfo<%=i %>">
	  <table class="datas select-scenery" width="95%" id="itemTab<%=JDID %>">
		<tr>
	  	  <td width="8%" align="right">景点名称：
	  	  </td>
	      <td width="15%"><font color="red"><%=cmpny_name %></font></td>
	  	  <td width="20%">
	  		<input name="dwj<%=JDID %>" type="radio" value="1" <%if("1".equals(dwj)){ %>checked="checked"<%}else{%>disabled="disabled"<%} %> id="dwj"/>永久
	  		<input name="dwj<%=JDID %>" type="radio" value="2" <%if("2".equals(dwj)){ %>checked="checked"<%}else{%>disabled="disabled"<%} %> id="dwj"/>淡季价格
	  		<input name="dwj<%=JDID %>" type="radio" value="3" <%if("3".equals(dwj)){ %>checked="checked"<%}else{%>disabled="disabled"<%} %> id="dwj"/>旺季价格
	  	  </td>
	  	  <td width="10%">
	  	  	<input type="hidden" name="JDID<%=CSID %>/JDID[<%=tmp1 %>]" value="<%=JDID %>" />
	   		<input type="checkbox" name="SFXZ<%=JDID %>" <%if("Y".equals(sfxz)){ %>checked="checked"<%} %>
	   	   value="Y" class="checkbox" onclick="noWay('itemTab<%=JDID %>');"/>勾中选择此景点
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
				String RS = rd.getStringByDI("rbtSceneryPrice","RS",k);
				String JGMC = rd.getStringByDI("rbtSceneryPrice","JGMC",k);
				String JGMCID = rd.getStringByDI("rbtSceneryPrice","JGMCID",k);
				String JG = rd.getStringByDI("rbtSceneryPrice","JG",k);
				String upPK = rd.getStringByDI("rbtSceneryPrice","BXID",k);
				if(upPK.equals(BXID)){
			%>
			  <tr>
			 	 <td width="32%"><%=JGMC %>
				  <input type="hidden" name="TA_ZT_BXJDJG<%=JDID %>/JGMCID[<%=tmp %>]" value="<%=JGMCID %>"/>
				  <input type="hidden" name="TA_ZT_BXJDJG<%=JDID %>/JGMC[<%=tmp %>]" value="<%=JGMC %>" />
				</td>
				<td width="32%"><%=JG %>
				  <input type="hidden" name="TA_ZT_BXJDJG<%=JDID %>/JG[<%=tmp %>]" value="<%=JG %>" class="sc_price"/>
				</td>
				<td width="36%">
				  <input type="text" name="TA_ZT_BXJDJG<%=JDID %>/RS[<%=tmp %>]" class="sc_num" value="<%=RS %>"  onkeydown="checkNum()" onchange="sumPrice('scenery<%=JDID%>_tab')"/>
				</td>
			  </tr>	
			 
			<%	
				tmp ++;
				}
			}
			%>
			 <tr>
				<td colspan="4" align="left">备注：<textarea rows="" cols="80" name="BZ<%=JDID %>"><%=bz %></textarea></td>
			</tr>
			  <tr>
			    <td align="right" colspan="4">
				  <font color="red">签单小计金额：</font>
				  <input type="text" name="QDXJ<%=JDID %>"  value="<%=qdxj %>" class="smallerInput qdxj" readonly="readonly"  onchange="sumPrice('scenery<%=JDID%>_tab')" onkeydown="checkNum1(this)"/>/元&nbsp;&nbsp;&nbsp;
				  <font color="red">现付小计金额：</font>
				  <input type="text" name="XFXJ<%=JDID %>" value="<%=xfxj %>"  readonly="readonly" class="smallerInput xfxj"/>/元&nbsp;&nbsp;&nbsp;
				  <font color="red">共计：</font>   
				  <input type="text" name="HJ<%=JDID %>" value="<%=hj %>" readonly="readonly" class="smallerInput gj" />/元
			    </td>
			  </tr>
			</table>
		  </td>
		</tr>
</table>
	</td>
  </tr>
</table>
				<%
				tmp1++;
						}
					}
				}
			}else{
				%>
<table class="datas select-scenery" width="95%" id="scenery0">
  <tr>
	<td colspan="4"><span>景点报账</span> <span class="showPointer" id="addScenery">添加</span></td>
  </tr>
  <tr>
	<td>请选择景点：
  	  <select name="TA_ZT_BXJD/SFID[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
	  <select name="TA_ZT_BXJD/CSID[0]"  class="cityid" id="city0" USEDATA="dataSrc0" SUBCLASS="2" 
	  		  onchange="getSceneryInfo(this.value,'sceneryInfo0')"></select>
	  <input type="hidden" name="TA_ZT_BXJD/TID[0]" value="<%=rd.getStringByDI("TA_ZT_BXJD","TID",0) %>"/>
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
	  <input type="text" name="TA_TDBXZJXXB_ZT/BX_JDQD" id="qdzj" readonly="readonly" value="<%=rd.getStringByDI("rbtJDXXZJB","QD",0) %>" class="smallerInput"/>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">现付金额总计：</font>
	  <input type="text" name="TA_TDBXZJXXB_ZT/BX_JDXF" id="xfzj" readonly="readonly" value="<%=rd.getStringByDI("rbtJDXXZJB","XF",0) %>" class="smallerInput"/>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">总计：</font>
	  <input type="text" name="TA_TDBXZJXXB_ZT/JINDHJ" id="zj" readonly="readonly" value="<%=rd.getStringByDI("rbtJDXXZJB","ZJ",0) %>" class="smallerInput"/>/元</td>
  </tr>
</table>
</div>
<div align="center" id="last-sub">
	<input type="hidden" name="userno" value="<%=sd.getString("userno") %>" />
<%if(!"view".equals(rd.getString("flag"))){ %>
	<input type="button" id="button" value="提交" onclick="djScenerySub();"/>
<%} %>
	<input type="button" id="button" value="返回" onclick="reload();"/>

	
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
	linkage.initLinkage("dataSrc<%=h%>","<%=rd.getStringByDI("rbtCityDis","sfid",h)%>",1);
	linkage.initLinkage("dataSrc<%=h%>","<%=rd.getStringByDI("rbtCityDis","csid",h)%>",2);
<%	}
}else{%>
	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Sceneryz.xml");
	linkage.init();
<%
} %>
</script>
