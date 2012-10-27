<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<title>餐厅报销</title>
<script type="text/javascript">
//输入只能为数字
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
var num;
jQuery(document).ready(function(){
	num=jQuery(".select-dinner").size();
});

function addDinner(){
	var d=jQuery(".select-dinner").size();
	//jQuery("#addDinner").click(function(){
		var num=jQuery(".select-dinner").size();
		if(jQuery("#city"+(num-1)).val()==""){
			alert("请填写完整后再添加");
			return false;
		};
		jQuery("#dinnerDiv").append('<table class="datas select-dinner" width="98%" id="dinner'+num+'"><tr>'+
		'<td colspan="4"><span>餐厅报销</span> </td></tr>'+
		'<input type="hidden" name="TA_ZT_BXCT/TID['+num+']" value="<%=rd.getString("TID")%>">'+
		 '<tr><td colspan="4">餐厅名称：'+
		'<select name="TA_ZT_BXCT/SF['+num+']" id="pro'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="1"></select>'+
		'<select name="TA_ZT_BXCT/CITYID['+num+']" id="city'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="2"></select>'+
		'<select name="TA_ZT_BXCT/CT['+num+']" id="ct'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="3"></select>'+
		'用餐日期：<input type="text" name="TA_ZT_BXCT/YCRQ['+num+']" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/>'+
		'</td></tr>'+
		'<tr  class="none" ><td class="dinnerInfo"><table class="datas" style="text-align: center">'+
		'<tr id="first-tr"><td >早/正 </td><td >价格</td><td >餐数</td><td >人数</td></tr>'+
		'<tr><td>早餐</td><td><input name="TA_ZT_BXCT/ZCJG['+num+']" class="bf_jg" onkeydown="checkNum1(this)" onchange="sumPrice()" value="0"  /></td>'+
		'<td><input name="TA_ZT_BXCT/ZCCS['+num+']" class="bf_cs" onkeydown="checkNum()" onchange="sumPrice()" value="0"  /></td>'+
		'<td><input name="TA_ZT_BXCT/ZCRS['+num+']" class="bf_rs" onkeydown="checkNum1(this)" onchange="sumPrice()" value="0"  />人</td'+
		'</tr><tr><td>正餐</td><td><input name="TA_ZT_BXCT/ZHCJG['+num+']" class="dn_jg"  onkeydown="checkNum1(this)" value="0"  onchange="sumPrice()" /> </td>'+
		'<td><input name="TA_ZT_BXCT/ZHCCS['+num+']" class="dn_cs"  onkeydown="checkNum()" onchange="sumPrice()" value="0"  /></td>'+
		'<td><input name="TA_ZT_BXCT/ZHCRS['+num+']" class="dn_rs"  onkeydown="checkNum1(this)" onchange="sumPrice()" value="0"  />人</td>'+
		'</tr><tr>'+
		'<td colspan="4" align="left">备注：<textarea rows="" cols="80" name="TA_ZT_BXCT/BZ['+num+']"> </textarea></td>'+
		'</tr><tr  > <td align="right" colspan="5">'+
		'<font color="red">签单小计：</font><input name="TA_ZT_BXCT/QDXJJE['+num+']" type="text" class="smallInput qdxj" value="0" onkeydown="checkNum1(this)"  onchange="sumPrice()"/>/元&nbsp;&nbsp;&nbsp;'+
		'<font color="red">现付小计：</font><input name="TA_ZT_BXCT/XFXJJE['+num+']" type="text" readonly="readonly" class="smallInput xfxj" value="0"/>/元&nbsp;&nbsp;&nbsp;'+
		'<font color="red">共计：</font>   <input name="TA_ZT_BXCT/HJ['+num+']" type="text"   readonly="readonly" class="smallInput gj" value="0"/>/元'+
		'<input type="hidden" name="TA_ZT_BXCT/BXZT['+num+']" value="Y"/>'+
		'<input type="hidden" name="TA_ZT_BXCT/BXR['+num+']" value="<%=sd.getString("userno") %>"/>'+
		'</td></tr></table></td></tr></table>');
		var linkage = new Linkage("dataSrc"+d, "<%=request.getContextPath()%>/main/data/Dinning-Roomz.xml");
		linkage.init();
		d+=1;
		//});

}

function delTab(){
	//jQuery("#"+tab).remove();
	var rows=jQuery(".select-dinner").size()-1;
	if(rows>0){
		var idx = parseInt(rows);
		jQuery("#dinnerDiv .select-dinner:eq("+idx+")").remove();
		rows--;
	}
	sumPrice();
}
//delTab(\'dinner'+num+'\')
var hzs=jQuery(".hzszj").val(); //酒店含早餐数
var ts=jQuery("tszj").val(); //旅游总天数
function sumPrice(){
	jQuery("#dinnerDiv table").each(function(i){
		var bf_jg=jQuery("input.bf_jg:eq("+i+")").val();
		var bf_cs=jQuery("input.bf_cs:eq("+i+")").val();
		var bf_rs=jQuery("input.bf_rs:eq("+i+")").val();
		var dn_jg=jQuery("input.dn_jg:eq("+i+")").val();
		var dn_cs=jQuery("input.dn_cs:eq("+i+")").val();
		var dn_rs=jQuery("input.dn_rs:eq("+i+")").val();

		var tmp=0;
		if((bf_cs-hzs)>0){
			tmp=parseFloat(bf_jg*(bf_cs-hzs)*bf_rs+dn_jg*dn_cs*dn_rs);
		}else{
			tmp=parseFloat(bf_jg*bf_cs*bf_rs+dn_jg*dn_cs*dn_rs);
		}
		var tmp=0;
		
		if(""==bf_cs){
			jQuery("input.bf_cs:eq("+i+")").val(0);
		}
		if(""==dn_cs){
			jQuery("input.dn_cs:eq("+i+")").val(0);
		}
		if(""==bf_jg){
			jQuery("input.bf_jg:eq("+i+")").val(0);
		}
		if(""==bf_rs){
			jQuery("input.bf_rs:eq("+i+")").val(0);
		}
		if(""==dn_jg){
			jQuery("input.dn_jg:eq("+i+")").val(0);
		}
		if(""==dn_rs){
			jQuery("input.dn_rs:eq("+i+")").val(0);
		}
		
		tmp=parseFloat(bf_jg*bf_cs*bf_rs+dn_jg*dn_cs*dn_rs);
		jQuery("input.gj:eq("+i+")").val(tmp);
		var qdxj=jQuery("input.qdxj:eq("+i+")").val();
		if(qdxj>tmp){
			jQuery("input.qdxj:eq("+i+")").val(Math.round(tmp*100)/100);
			qdxj=tmp;
			}
		jQuery("input.xfxj:eq("+i+")").val(Math.round((tmp-qdxj)*100)/100);
		
		var qdzj=0;
		var xfzj=0;
		jQuery(".qdxj").each(function(j){
				var qdxj=this.value;
				var xfxj=jQuery(".xfxj:eq("+j+")").val();
				qdzj+=parseFloat(qdxj);
				xfzj+=parseFloat(xfxj);
			});
		jQuery("#qdzj").val(Math.round(qdzj*100)/100);
		jQuery("#xfzj").val(Math.round(xfzj*100)/100);
		jQuery("#zj").val(Math.round((qdzj+xfzj)*100)/100);
	});
	
}

function p_editDinner(){
	var num=jQuery(".select-dinner").size();
	if(jQuery("#city"+(num-1)).val()==""){
		alert("请把信息填写完整");
		return false;
	}
	
	var bftmp=0;
	jQuery(".bf_cs").each(function(i){
		var bf=jQuery(".bf_cs:eq("+i+")").val();
		bftmp+=parseFloat(bf);
	});
	if((parseFloat(bftmp)+parseFloat(hzs)-parseFloat(ts))>0){
		alert("早餐数超出");
		return false;
	}
	
	document.p_dinnerbx_form.action="zteditDjDinnerBx.?";
	document.p_dinnerbx_form.submit();
	reload();
}

function reload(){
	parent.parent.location.reload(); 
	parent.parent.GB_hide(); 
}
</script>
</head>

<body>
<form name="p_dinnerbx_form" method="post">
<input type="hidden" name="TA_ZT_BXCT/TID" value="<%=rd.getString("TID")%>"/>
<input type="hidden" value="<%=rd.getStringByDI("bxHzs","hzs",0)%>" class="hzszj" />
<input type="hidden" value="<%=rd.getStringByDI("TA_ZT_GROUPs","TS",0)%>" class="tszj" />
<div id="lable"><span >餐厅报销</span>
			<%
				String hzs=rd.getStringByDI("bxHzs","hzs",0);
				if("".equals(hzs)){hzs="0";}
				String ts=rd.getStringByDI("TA_ZT_GROUPs","TS",0);
				if(0<Integer.parseInt(hzs)){
			%>
			(<span style="color:red; font-weight:100;text-decoration:underline" class="showPointer" title="修改酒店计划信息">
			 <a style="color:red;" href="<%=request.getContextPath()%>/main/ztbx/bxHotel/ztreturnInitDjHotelBx.?TA_ZT_GROUP/ID=<%=rd.getString("TID")%>&TA_ZT_BXHOTEL/TID=<%=rd.getString("TID") %>&TID=<%=rd.getString("TID") %>&flag=edit&TS=<%=ts%>">酒店已含早餐<%=hzs %>餐(总天数<%=ts %>)  点击查看详情</a></span>)
			<%} %>
			&nbsp;&nbsp;&nbsp;<span class="showPointer" id="addDinner" onclick="addDinner()">添加</span>
			<span class="showPointer" onclick="delTab()">删除</span>
</div>
<div id="bm-table">
<div id="dinnerDiv">
		<%
			int dinnerRows=rd.getTableRowsCount("dinnerBxList");
			String qdzj=rd.getStringByDI("bxctJDXXZJB","QDZJ",0);
			String xfzj=rd.getStringByDI("bxctJDXXZJB","XFZJ",0);
			String zj=rd.getStringByDI("bxctJDXXZJB","ZJ",0);
			if(dinnerRows>0){
				for(int i=0;i<dinnerRows;i++){
		%>
		<table class="datas select-dinner" width="98%" id="dinner<%=i %>">
		  <tr>
			<td colspan="4">
				地区：
			  <select name="TA_ZT_BXCT/SF[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
			  <select name="TA_ZT_BXCT/CITYID[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
			  <select name="TA_ZT_BXCT/CT[<%=i %>]" id="ct<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3"></select>&nbsp;用餐日期：
			  <input type="text" name="TA_ZT_BXCT/YCRQ[<%=i %>]" value="<%=rd.getStringByDI("dinnerBxList","YCRQ",i) %>" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/>
			</td>
		
		  </tr>
		  <tr  class="none" >
		  <td class="dinnerInfo">
		  			<table class="datas" style="text-align: center">
					<tr id="first-tr">
						<td >早/正 </td>
						<td >价格</td>
						<td >餐数</td>
						<td >人数</td>
					</tr>
					<tr>
						<td>早餐</td>
						<td><input name="TA_ZT_BXCT/ZCJG[<%=i %>]" class="bf_jg" onkeydown="checkNum1(this)" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerBxList","ZCJG",i) %>" /></td>
						<td><input name="TA_ZT_BXCT/ZCCS[<%=i %>]" class="bf_cs" onkeydown="checkNum()" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerBxList","ZCCS",i) %>" /></td>
						<td><input name="TA_ZT_BXCT/ZCRS[<%=i %>]" class="bf_rs" onkeydown="checkNum1(this)" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerBxList","ZCRS",i) %>" />人</td>
		
					</tr>
					<tr>
						<td>正餐</td>
						<td><input name="TA_ZT_BXCT/ZHCJG[<%=i %>]" class="dn_jg"  onkeydown="checkNum1(this)" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerBxList","ZHCJG",i) %>" /> </td>
						<td><input name="TA_ZT_BXCT/ZHCCS[<%=i %>]" class="dn_cs"  onkeydown="checkNum()" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerBxList","ZHCCS",i) %>" /></td>
						<td><input name="TA_ZT_BXCT/ZHCRS[<%=i %>]" class="dn_rs"  onkeydown="checkNum1(this)" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerBxList","ZHCRS",i) %>" />人</td>
					</tr>
					<tr>
						<td colspan="4" align="left">备注：<textarea rows="" cols="80" name="TA_ZT_BXCT/BZ[<%=i %>]"> <%=rd.getStringByDI("dinnerBxList","BZ",i) %></textarea></td>
					</tr>
			<tr> 
				<td align="right" colspan="5">
				<font color="red">签单小计：</font><input name="TA_ZT_BXCT/QDXJ[<%=i %>]" type="text" class="smallInput qdxj" onkeydown="checkNum1(this)" onchange="sumPrice()" value="<%=rd.getStringByDI("dinnerBxList","QDXJJE",i) %>"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">现付小计：</font><input name="TA_ZT_BXCT/XFXJ[<%=i %>]" type="text" readonly="readonly" class="smallInput xfxj"  value="<%=rd.getStringByDI("dinnerBxList","XFXJJE",i) %>"/>/元&nbsp;&nbsp;&nbsp;
				<font color="red">共计：</font>   <input name="TA_ZT_BXCT/HJ[<%=i %>]" type="text"   readonly="readonly" class="smallInput gj"  value="<%=rd.getStringByDI("dinnerBxList","HJ",i) %>"/>/元
				</td>
			</tr>
			</table>
			</td>
			</tr>
		</table>
		<%} }else{%>
		<table class="datas select-dinner" width="98%" id="dinner0">
		  <tr>
			<td colspan="4"><span>餐厅报销</span>&nbsp;&nbsp;&nbsp;<span class="showPointer" id="addDinner">添加</span></td>
		  </tr>
		  <tr>
			<td colspan="4">
				地区：
			  <select name="TA_ZT_BXCT/SF[0]" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
			  <select name="TA_ZT_BXCT/CITYID[0]" id="city0" USEDATA="dataSrc0" SUBCLASS="2"></select>
			  <select name="TA_ZT_BXCT/CT[0]" id="ct0" USEDATA="dataSrc0" SUBCLASS="3"></select>&nbsp;用餐日期：
			  <input type="text" name="TA_ZT_BXCT/YCRQ[0]" value="" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/>
			</td>
		
		  </tr>
		  <tr  class="none" >
		  <td class="dinnerInfo">
		  			<table class="datas" style="text-align: center">
					<tr id="first-tr">
						<td >早/正 </td>
						<td >价格</td>
						<td >餐数</td>
						<td >人数</td>
					</tr>
					<tr>
						<td>早餐</td>
						<td><input name="TA_ZT_BXCT/ZCJG[0]" class="bf_jg" onkeydown="checkNum1(this)" onchange="sumPrice()" value="0"  /></td>
						<td><input name="TA_ZT_BXCT/ZCCS[0]" class="bf_cs" onkeydown="checkNum()" onchange="sumPrice()" value="0"  /></td>
						<td><input name="TA_ZT_BXCT/ZCRS[0]" class="bf_rs" onkeydown="checkNum1(this)" onchange="sumPrice()" value="0"  />人</td>
		
					</tr>
					<tr>
						<td>正餐</td>
						<td><input name="TA_ZT_BXCT/ZHCJG[0]" class="dn_jg"  onkeydown="checkNum1(this)" onchange="sumPrice()" value="0"  /> </td>
						<td><input name="TA_ZT_BXCT/ZHCCS[0]" class="dn_cs"  onkeydown="checkNum()" onchange="sumPrice()" value="0"  /></td>
						<td><input name="TA_ZT_BXCT/ZHCRS[0]" class="dn_rs"  onkeydown="checkNum1(this)" onchange="sumPrice()" value="0"  />人</td>
					</tr>
					<tr>
						<td colspan="4" align="left">备注：<textarea rows="" cols="80" name="TA_ZT_BXCT/BZ[0]"> </textarea></td>
					</tr>
		
					<tr  > 
						<td align="right" colspan="5">
						<font color="red">签单小计：</font><input name="TA_ZT_BXCT/QDXJ[0]" type="text" class="smallInput qdxj" value="0" onkeydown="checkNum1(this)" onchange="sumPrice()"/>/元&nbsp;&nbsp;&nbsp;
						<font color="red">现付小计：</font><input name="TA_ZT_BXCT/XFXJ[0]" type="text" readonly="readonly" class="smallInput xfxj" value="0"/>/元&nbsp;&nbsp;&nbsp;
						<font color="red">共计：</font>   <input name="TA_ZT_BXCT/HJ[0]" type="text"   readonly="readonly" class="smallInput gj" value="0"/>/元
						</td>
					</tr>
					</table>
					</td>
					</tr>
		</table>
		<%} %>
</div> 

<table class="datas" style="margin-top: 3px;">
  <tr>
	<td><span>费用合计</span></td>
  </tr>
  <tr>

	<td align="right">
	  <font color="red">签单金额总计：</font>
	  <input type="text" name="TA_TDBXZJXXB_ZT/BXCTQD" id="qdzj" readonly="readonly" value="<%="".equals(qdzj)?"0":qdzj %>" class="smallInput"/>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">现付金额总计：</font>
	  <input type="text" name="TA_TDBXZJXXB_ZT/BXCTXF" id="xfzj" readonly="readonly" value="<%="".equals(xfzj)?"0":xfzj %>"  class="smallInput"/>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">总计：</font>
	  <input type="text" name="TA_TDBXZJXXB_ZT/CTHJ" id="zj" readonly="readonly" value="<%="".equals(zj)?"0":zj %>" class="smallInput" />/元</td>

  </tr>
</table>
</div>
<div align="center" id="last-sub">
<%if(!"view".equals(rd.getString("flag"))){ %>
	<input type="button" id="button" value="提交" onclick="p_editDinner();"/>
	<%} %>
	<input type="button" id="button" value="关闭" onclick="javascript:window.parent.parent.GB_hide();"/>
</div>
</form>
</body>
</html>
<script type="text/javascript">
	<%
	if(dinnerRows>0){
	for(int v=0;v<dinnerRows;v++){
	%>
	var linkage = new Linkage("dataSrc<%=v%>", "<%=request.getContextPath()%>/main/data/Dinning-Roomz.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("dinnerBxList","SF",v)%>",1);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("dinnerBxList","CITYID",v)%>",2);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("dinnerBxList","CT",v)%>",3);
	<%}}else{%>
	var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Dinning-Roomz.xml");
	linkage.init();
	<%}%>
</script>
