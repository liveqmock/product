<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" ></meta>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"></link>
<title>购物计划</title>
<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery("#newShop").click(function(){
		var num=jQuery(".select-shop").size();
		if(jQuery("#city"+(num-1)).val()==""){
			alert("请填写完整后再添加");
			return false;
		}
		jQuery("#shopDiv").append('<table class="datas select-shop" width="98%" id="shop'+num+'">'+
				'<tr><td><span>购物点计调</span>  <span  class="showPointer" onclick="delTab(\'shop'+num+'\')">删除</span></td></tr>'+
				'<tr><td>请选择地区：<select name="TA_ZT_JHGW/SFID['+num+']" id="pro'+num+'" USEDATA="dataSrc'+num+'" SUBCLASS="1"></select>'+
			'<select name="TA_ZT_JHGW/CSID['+num+']" id="city'+num+'" class="cityid" USEDATA="dataSrc'+num+'" SUBCLASS="2" onchange="showShop(this.value,\'store'+num+'\',\'shop'+num+'\')"></select>'+
			'</td></tr><tr><td id="store'+num+'"></td></tr></table>');
		var linkage = new Linkage("dataSrc"+num, "<%=request.getContextPath()%>/main/data/Shopz.xml");
		linkage.init();
		});
});
	function showShop(city,tdName,tbName){
		jQuery("#"+tdName+" .select-shop").remove();
		if(""!=city){
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
		var obj=jQuery.ajax({url:"ztGetAllShopAndRTF.?ywlb=z&TID=<%=rd.getStringByDI("TA_ZT_JHGW","TID",0)%>&city="+city,cache:false,success:function(){
			jQuery("#"+tdName).html(obj.responseText);
			}});
		}
	}
	function delTab(tab){
		jQuery("#"+tab).remove();
	}
	function dj_addShopPlan(){
		var last=jQuery(".cityid").size();
		for(var a=0;a<last;a++){
			var cityid=jQuery(".cityid:eq("+a+")").val();
			if(""==cityid){
				alert("请填写完整");
				return false;
				}
			}
		var ck=true;
		jQuery(".select-shop").each(function(){
			var ifCK=0;
			var tab=this.id;
			jQuery("#"+tab+" [type='checkbox']").each(function(){
				if(this.checked==true){
					ifCK+=1;
				}
				});
			if(ifCK==0){
			alert("没有选择购物点的地区请删除");
			ck=false;
				}
		});
		if(ck==false){
			return false;
		}
		var result=0;
		jQuery(".rtf").each(function(i){
			if(this.value==""){
				this.value = 0;
				result++;
			}
		});
		if(result>0){
			return false;
		}
		document.ztShoppingForm.submit();
		reload();
	}
	function reload(){
		parent.parent.location.reload(); 
		parent.parent.GB_hide(); 
	}
</script>
</head>
<body>
<form action="ztShopGeneral." name="ztShoppingForm" method="post">

<div id="lable"><span >购物点计划</span><span  class="showPointer" id="newShop">&nbsp;&nbsp;添加</span></div>
<div id="bm-table">
<div id="shopDiv">
	<%
	int cityInfoRows=rd.getTableRowsCount("sqlCityInfo");
	if(cityInfoRows > 0){
		out.print("<input type='hidden' name='state' value='Edit'>");
		for(int i = 0;i < cityInfoRows; i++){
	%>
		<table class="datas select-shop" width="98%" id="shop<%=i %>">
			<tr>
				<td >
				<span>购物点计调</span>
				<span  class="showPointer" onclick="delTab('shop<%=i %>')">删除</span>
				</td>
			</tr>
		<tr>
			<td>请选择地区：
			<select name="TA_ZT_JHGW/SFID[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
			<select name="TA_ZT_JHGW/CSID[<%=i %>]" id="city<%=i %>" class="cityid" USEDATA="dataSrc<%=i %>" SUBCLASS="2" onchange="showShop(this.value,'store<%=i %>','shop<%=i %>')"></select>
			</td>
		</tr>
		<tr>
			<td id="store<%=i %>">
			   <div>
				<%
				int h = 0;
				int GWDrows=rd.getTableRowsCount("shopListEditGWD");
				for(int j = 0; j < GWDrows; j++){
					if(rd.getStringByDI("shopListEditGWD","csid",j).equals(rd.getStringByDI("sqlCityInfo","csid",i))){
				%>
					<% if(j==1){out.print("&nbsp;&nbsp;&nbsp;");}else{out.print("&nbsp;");}%>
					<input type="hidden" name="TA_ZT_JHGW<%=rd.getStringByDI("sqlCityInfo","csid",i)%>/GWDID[<%=h%>]" value="<%=rd.getStringByDI("shopListEditGWD","GWDID",j) %>" />
					<input type="hidden" name="TA_ZT_JHGW<%=rd.getStringByDI("sqlCityInfo","csid",i)%>/GWDMC[<%=h%>]" value="<%=rd.getStringByDI("shopListEditGWD","GWDMC",j) %>" />
					<input type="checkbox" name="TA_ZT_JHGW<%=rd.getStringByDI("sqlCityInfo","csid",i)%>/SFXZ[<%=h%>]" value="Y" <%if("Y".equals(rd.getStringByDI("shopListEditGWD","SFXZ",j)))out.print("checked='checked'"); %>/>
					<font color="red"><%=rd.getStringByDI("shopListEditGWD","GWDMC",j) %></font>
					
					<%
						int g = 0;
						int RTFrows=rd.getTableRowsCount("shopListEditRTF");
						for(int k = 0; k < RTFrows; k++){
							
							if(rd.getStringByDI("shopListEditRTF","GWDID",k).equals(rd.getStringByDI("shopListEditGWD","GWDID",j))){
								String sfIdEdit=rd.getStringByDI("shopListEditRTF","CSID",k);//省份ID
								String sfNameEdit=rd.getStringByDI("shopListEditRTF","NAME",k);//省份名称
								String rtfEdit=rd.getStringByDI("shopListEditRTF","RTF",k);//人头费
					%>
							&nbsp;&nbsp;&nbsp;&nbsp;<font color="blue"><%= sfNameEdit%></font>=> 人头费:
							
							<input type="hidden" name="TA_ZT_GWRTF<%=rd.getStringByDI("shopListEditGWD","GWDID",j) %>/CSID[<%=g %>]" value="<%=sfIdEdit %>"/>
							<input type="text" name="TA_ZT_GWRTF<%=rd.getStringByDI("shopListEditGWD","GWDID",j) %>/RTF[<%=g %>]" value="<%=rtfEdit %>" class="smallerInput rtf" maxlength="2"/>
					<%
						g++;}
						}
					%>
					</br>
				<%
					h++;}
				}
				%>
			   </div>
			</td>
		</tr>
	</table>
	<%
	}
		}else{
	%>
	<table class="datas select-shop" width="98%" id="shop0">
			<tr>
				<td >
				<span>购物点计调</span>
				<span  class="showPointer" onclick="delTab('shop0')">&nbsp;&nbsp;删除</span>
				</td>
			</tr>
		<tr>
			<td>请选择地区：
			<select name="TA_ZT_JHGW/SFID[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
			<select name="TA_ZT_JHGW/CSID[0]" id="city0" class="cityid" USEDATA="dataSrc0" SUBCLASS="2" onchange="showShop(this.value,'store0')"></select>
			</td>
		</tr>
		<tr>
			<td id="store0"></td>
		</tr>
	</table>
<%} %>
</div>
</div>
<div align="center" id="last-sub">
<%if("view".equals(rd.getString("flag"))){ %>
	<input type="button" id="button" value="关闭" onclick="reload();"/>
<%}else{ %>
	<input type="button" id="button" value="提交" onclick="dj_addShopPlan();"/>
	<input type="button" id="button" value="关闭" onclick="reload();"/>
<%} %>
</div>
<input type="hidden" name="JHZDR" value="<%=sd.getString("userno") %>" />
<input type="hidden" name="TA_ZT_JHGW/TID" value="<%=rd.getStringByDI("TA_ZT_JHGW","TID",0)%>" />
</form>
</body>
</html>
<script type="text/javascript">
<%	
	if(cityInfoRows > 0){
		for(int l = 0;l < cityInfoRows; l++){
%>
			var linkage = new Linkage("dataSrc<%=l%>", "<%=request.getContextPath()%>/main/data/Shopz.xml");
			linkage.init();
			linkage.initLinkage("dataSrc<%=l%>","<%=rd.getStringByDI("sqlCityInfo","sfid",l)%>",1);
			linkage.initLinkage("dataSrc<%=l%>","<%=rd.getStringByDI("sqlCityInfo","csid",l)%>",2);
<%
		}
	}
	else
	{
%>
		var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Shopz.xml");
		linkage.init();
<%
	}
%>
</script>