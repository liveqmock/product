<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<title>酒店计划</title>
<script type="text/javascript">

	function checkNum(){
		if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
			  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
			    event.returnValue=false;
	}
	
	function checkBoxNum(obj){
		var cnum=jQuery(".select-hotel").size();
		var hz=document.getElementsByName("TA_ZT_JHHOTEL/SFHZ["+(cnum-1)+"]");
		for(var i=0;i<hz.length;i++){
			if(hz[i]==obj && hz[i].checked){
				hz[i].checked=true;
			}else{
				hz[i].checked=false;
			}
		}
	}
	
jQuery(document).ready(function(){
	var d=jQuery(".select-hotel").size();
	jQuery(".showPointer").hover(function(){
		jQuery(this).css({cursor:'pointer'});
	});
});
	function addHotel(){
		var d=jQuery(".select-hotel").size();
		 var num=jQuery(".select-hotel").size();
		 if(jQuery("#id_hotel"+(num-1)).val()==""){
			alert("请填写完整后再添加");
			return false;
			 }
		 jQuery("#hotelDiv").append('<table class="datas select-hotel" width="95%" id="hotel'+num+'">'+
					'<tr><td colspan="4"><span>酒店计划</span> <span  class="showPointer" onclick="addHotel()">添加</span>&nbsp&nbsp&nbsp<span onclick="delTab(\'hotel'+num+'\')" class="showPointer">删除</span></td></tr>'+
					'<input type="hidden" name="TA_DJ_JHHTOLE/TID['+num+']" value="<%=rd.getString("TID")%>">'+
					'<input type="hidden" name="TA_ZT_JHHOTEL/ZDR['+num+']" value="<%=rd.getString("userno")%>">'+
				'<tr><td colspan="4">请选择酒店：'+
				   '<select name="TA_ZT_JHHOTEL/SF['+num+']" id="pro'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="1"></select> '+
		  		   '<select name="TA_ZT_JHHOTEL/CITY['+num+']" id="city'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="2"></select> '+
		           '<select name="TA_ZT_JHHOTEL/XQ['+num+']" id="area'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="3"></select> '+
		           '<select name="TA_ZT_JHHOTEL/XJ['+num+']" id="level'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="4"></select> '+
		           '<select name="TA_ZT_JHHOTEL/JDID['+num+']" class="hotelid" id="id_hotel'+num+'" USEDATA="dataSrc'+d+'" SUBCLASS="5" onchange="getHotelInfo(this.value,\'hotelInfo'+num+'\','+num+')"></select>'+
		           '</td></tr>'+
		           '<tr><td colspan="4">'+
		           '&nbsp;<font color="red">含桌早</font><input type="checkBox" value="1" name="TA_ZT_JHHOTEL/SFHZ['+num+']" onclick="checkBoxNum(this);"/>'+ 
				   '&nbsp;<font color="red">含自助早</font><input type="checkBox" value="2" name="TA_ZT_JHHOTEL/SFHZ['+num+']" onclick="checkBoxNum(this);"/>'+ 
				   '&nbsp;<font color="red">含打包早</font><input type="checkBox" value="3" name="TA_ZT_JHHOTEL/SFHZ['+num+']" onclick="checkBoxNum(this);"/>'+
				   '&nbsp;入住时间：<input type="text" name="TA_ZT_JHHOTEL/RZ_TIME['+num+']" onFocus="WdatePicker({startDate:\'%y-%M-%d %H:%m:%s\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',alwaysUseStartDate:true});"/>'+
					'&nbsp;入住天数：<input type="text" name="TA_ZT_JHHOTEL/TS['+num+']" onkeydown="checkNum()" class="smallInput ts" onchange="SumPrice()"/>'+
					'&nbsp;司陪住宿：<input type="text" name="TA_ZT_JHHOTEL/SFZSF['+num+']" onkeydown="checkNum()" class="smallInput spzsf" onchange="SumPrice()"/>'+
				   '</td></tr>'+
				   '<td align="left">联系人：</td>'+
					'<td><input name="TA_ZT_JHHOTEL/LXR['+num+']" id="lxrname'+num+'" value="" maxlength="10"/></td>'+
					'<td align="left">联系电话:</td>'+
					'<td><input name="TA_ZT_JHHOTEL/LXRDH['+num+']" id="lxrphone'+num+'" value="" maxlength="11"/></td>'+
				   '<tr ><td colspan="4" id="hotelInfo'+num+'">'+
					'</td></tr><tr><td  colspan="4">'+
					'备注：<textarea rows="" cols="90" name="TA_ZT_JHHOTEL/BZ['+num+']"></textarea></td></tr>'+
				   '<tr><td align="right" colspan="4">'+
			'<font color="red">签单小计金额：</font><input value="0" type="text" name="TA_ZT_JHHOTEL/QDXJ['+num+']" class="smallInput qdxj" onchange="sumRs()" onkeydown="checkNum()"/>/元&nbsp;&nbsp;&nbsp;'+
			'<font color="red">现付小计金额：</font><input value="0" type="text" name="TA_ZT_JHHOTEL/XFXJ['+num+']"  readonly="readonly" class="smallInput xfxj"/>/元&nbsp;&nbsp;&nbsp;'+
			'<font color="red">共计：</font>   <input  type="text" value="0" name="TA_ZT_JHHOTEL/HJ['+num+']" readonly="readonly" class="smallInput gj" />/元</td></tr></table>');
			var linkage = new Linkage("dataSrc"+d, "<%=request.getContextPath()%>/main/data/Hotelz.xml");
			linkage.init();
			d+=1;
			//alert(num);
	}

	function delTab(tab){
		jQuery("#"+tab).remove();
		SumPrice();
		sumRs();
		//alert(jQuery("#hotelDiv").html());
	}

	function SumPrice(){
		 var zj=0;
		 var allhj=0;
		 var xiaoji=0;
		jQuery("table.select-hotel").each(function(j){
			var tmp=0;
			var alltmp=0;
			var tabName=this.id;
			var ts=jQuery(".ts:eq("+j+")").val();
			var spzsf=jQuery(".spzsf:eq("+j+")").val();
			jQuery("#"+tabName+" input.hotelPrice").each(function(i){
				var num=jQuery("#"+tabName+" input.hotelNum:eq("+i+")").val();
				if(""!=num){
					xiaoji=this.value*num;
				}
				if(""==num){
					jQuery("#"+tabName+" input.hotelNum:eq("+i+")").val(0);
				}
				jQuery("#"+tabName+" input.xiaoji:eq("+i+")").val(Math.round(xiaoji*100)/100);

				tmp+=xiaoji;
				if(""!=spzsf && ""!=ts){
					alltmp=parseFloat(tmp*ts)+parseFloat(spzsf*ts);
				}
				if(""!=ts && ""==spzsf){
					alltmp=tmp*ts;
				}
				if(""!=spzsf && ""==ts){
					alltmp=parseFloat(tmp)+parseFloat(spzsf);
				}
				if(""==spzsf && ""==ts){
					alltmp=tmp;
				}
				
				zj+=xiaoji;
				
			jQuery("#"+tabName+" input.gj").val(Math.round(alltmp*100)/100);
			jQuery("#"+tabName+" input.qdxj").attr("readonly","");
		});
		});
			sumRs();
	}
	function sumRs(){
		 var qdzj=0;
		 var xfzj=0;
		jQuery(".qdxj").each(function(i){
			var qd=jQuery(".qdxj:eq("+i+")").val();
			var gj=jQuery(".gj:eq("+i+")").val();
			if(parseInt(qd)>parseInt(gj)){
				jQuery(".qdxj:eq("+i+")").val(Math.round(gj*100)/100);
				qd=gj;
				}
			jQuery(".xfxj:eq("+i+")").val(Math.round((gj-qd)*100)/100);
			qdzj+=parseInt(qd);
			xfzj+=(gj-qd);
			});
			jQuery("#qiandan").val(Math.round(qdzj*100)/100);
			jQuery("#xianfu").val(Math.round(xfzj*100)/100);
			jQuery("#zongji").val(Math.round((qdzj+xfzj)*100)/100);
	}
	
	function getHotelInfo(id,tdName,num){
		if(id!=""){
			var last=jQuery(".hotelid").size();
			if(last>1){
				for(var a=0;a<last;a++){
					var hotel=jQuery(".hotelid:eq("+a+")").val();
					for(var b=0;b<last;b++){
						var hotel2=jQuery(".hotelid:eq("+b+")").val();
						if(a!=b){
							if(hotel==hotel2){
								alert("不能重复选择");
								return false;
							}
						}
					}
				}
			}

			var obj=jQuery.ajax({url:"ztGetHotelLXR.?id="+id+"&ywlb=z",
				async:true,
				dataType:"json",
				cache:false,
				success:function(data){
					jQuery("#lxrname"+num).val(data[0].name);
					jQuery("#lxrphone"+num).val(data[0].phone);
				},
				error: function (XMLHttpRequest, textStatus, errorThrown) { 
		            alert(errorThrown); 
		   	 	}
			});				
			
			//保存数据返回值更改html内容
			var obj=jQuery.ajax({url:"ztGetHotelPlanInfo.?id="+id+"&num="+num,async:true,dataType:"HTML",cache:false,success:function(){
				jQuery("#"+tdName).parents("tr").attr("style","");
				jQuery("#"+tdName).html(obj.responseText);
			}});

			SumPrice();
		}
		
	}
	
	var ts=jQuery(".tszj").val(); //旅游总天数
	function p_editHotelPlan(){
		var num=jQuery(".select-hotel").size();
		 if(jQuery("#id_hotel"+(num-1)).val()==""){
				alert("酒店信息请填写完整");
				return false;
		 }

		 var tmp=0;
		 jQuery(".ts").each(function(i){
			 var ts=jQuery(".ts:eq("+i+")").val();
			 tmp+=parseInt(ts);
		 });
		 if(tmp>ts){
			 alert("住宿天数超出");
			 return false;
		 }

		document.p_hotelplan_form.action="ztUpdateHotelPlan.?";
		document.p_hotelplan_form.submit();
		reload();
	}
	
	function reload(){
		parent.parent.location.reload(); 
		parent.parent.GB_hide(); 
	}
</script>
</head>

<body>
<form  name="p_hotelplan_form" method="post">
<div class="width-99">
<input type="hidden" name="userno" value="1"></input>
<div id="lable"><span >酒店计划</span></div>
<input type="hidden" name="" value="<%=rd.getStringByDI("TA_ZT_GROUPs","DAYS",0)%>" class="tszj">
<input type="hidden" name="TA_ZT_JHHOTEL/TID" value="<%=rd.getString("TID")%>">
<input type="hidden" name="TA_ZT_JHHOTEL/ZDR" value="<%=rd.getString("userno")%>">
<div id="bm-table">
	<div id="hotelDiv">
	<%
		int hotelPlanRows=rd.getTableRowsCount("hotelPlanList");
		String qdzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDZSZJ",0);
		String xfzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFZSZJ",0);
		String zj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","ZSZJ",0);
		for(int i=0;i<hotelPlanRows;i++){
			String id=rd.getStringByDI("hotelPlanList","ID",i);
	%>
	<script type="text/javascript">
		function checkBox<%=i%>(obj){
			var hz=document.getElementsByName("TA_ZT_JHHOTEL/SFHZ[<%=i %>]");
			for(var i=0;i<hz.length;i++){
				if(hz[i]==obj && hz[i].checked){
					hz[i].checked=true;
				}else{
					hz[i].checked=false;
				}
			}
		}
	</script>
	<table class="datas select-hotel" width="95%" id="hotel<%=i %>">
			<tr>
				<td colspan="4">
				<span>酒店计划</span>
				<%if(i==0){ %><span  class="showPointer" onclick="addHotel()">添加</span><%}else{ %><span  class="showPointer" onclick="addHotel('hotel<%=i %>')">添加</span>&nbsp&nbsp&nbsp<span onclick="delTab('hotel<%=i %>')" class="showPointer">删除</span><%} %>
				</td>
			</tr>
			<tr>
			  <td colspan="4">请选择酒店： 
			 	<select name="TA_ZT_JHHOTEL/SF[<%=i %>]" id="pro<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="1"></select>
			  	<select name="TA_ZT_JHHOTEL/CITY[<%=i %>]" id="city<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="2"></select>
			  	<select name="TA_ZT_JHHOTEL/XQ[<%=i %>]" id="area<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="3"></select>
			  	<select name="TA_ZT_JHHOTEL/XJ[<%=i %>]" id="level<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="4"></select>
			  	<select name="TA_ZT_JHHOTEL/JDID[<%=i %>]" class="hotelid" id="id_hotel<%=i %>" USEDATA="dataSrc<%=i %>" SUBCLASS="5" onchange="getHotelInfo(this.value,'hotelInfo<%=i %>',<%=i%>);"></select>
			  </td>
			</tr>
			<tr>
			  <td colspan="4">
                &nbsp;<font color="red">含桌早</font><input type="checkBox" name="TA_ZT_JHHOTEL/SFHZ[<%=i %>]" value="1" <%if("1".equals(rd.getStringByDI("hotelPlanList","SFHZ",i))){ %>checked="checked"<%} %> onclick="checkBox<%=i%>(this);"/> 
				&nbsp;<font color="red">含自助早</font><input type="checkBox" name="TA_ZT_JHHOTEL/SFHZ[<%=i %>]" value="2" <%if("2".equals(rd.getStringByDI("hotelPlanList","SFHZ",i))){ %>checked="checked"<%} %> onclick="checkBox<%=i%>(this);"/> 
				&nbsp;<font color="red">含打包早</font><input type="checkBox" name="TA_ZT_JHHOTEL/SFHZ[<%=i %>]" value="3" <%if("3".equals(rd.getStringByDI("hotelPlanList","SFHZ",i))){ %>checked="checked"<%} %> onclick="checkBox<%=i%>(this);"/>
				&nbsp;入住时间：<input type="text" name="TA_ZT_JHHOTEL/RZ_TIME[<%=i %>]" value="<%=rd.getStringByDI("hotelPlanList","RZ_TIME",i)%>" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});"/>
				&nbsp;入住天数：<input type="text" name="TA_ZT_JHHOTEL/TS[<%=i %>]" value="<%=rd.getStringByDI("hotelPlanList","TS",i)%>" onkeydown="checkNum()" class="smallInput ts" onchange="SumPrice()"/> 
				&nbsp;司陪住宿：<input type="text" name="TA_ZT_JHHOTEL/SFZSF[<%=i %>]" value="<%=rd.getStringByDI("hotelPlanList","SFZSF",i)%>" onkeydown="checkNum()" class="smallInput spzsf" onchange="SumPrice()"/> 
			  </td>
			</tr>
			<tr>
				<td align="left">联系人：</td>
				<td>
					<input name="TA_ZT_JHHOTEL/LXR[<%=i %>]" id="lxrname<%=i %>" value="<%=rd.getStringByDI("hotelPlanList","LXR",i)%>" maxlength="10"/>
				</td>
				<td align="left">联系电话:</td>
				<td>
					<input name="TA_ZT_JHHOTEL/LXRDH[<%=i %>]" id="lxrphone<%=i %>" value="<%=rd.getStringByDI("hotelPlanList","LXRDH",i)%>" maxlength="11"/>
				</td>
			</tr>
			<tr >
				<td colspan="4" id="hotelInfo<%=i %>">
					<table class="datas" style="text-align: center">
						<tr id="first-tr">
							<td>房间类型</td>
							<td>价格</td>
							<td>房间数</td>
							<td>合计</td>
						</tr>
						<%
							String jdid=rd.getStringByDI("hotelPlanList","JDID",i);
							int hotelPlanJgPriceRows=rd.getTableRowsCount("hotelPlanJgList");
							for(int p=0;p<hotelPlanJgPriceRows;p++){
								String jdjhid=rd.getStringByDI("hotelPlanJgList","JDJHID",p);
								if(id.equals(jdjhid)){
						%>
						<tr>
							<td><%=rd.getStringByDI("hotelPlanJgList","PRICENAME",p)%><input type="hidden" name="hotel<%=jdid%>/priceName[<%=p %>]" value="<%=rd.getStringByDI("hotelPlanJgList","dmz",p)%>" onkeydown="checkNum()"></td>
							<td><input type="text" value="<%=rd.getStringByDI("hotelPlanJgList","JG",p)%>" name="hotel<%=jdid%>/price[<%=p %>]" class="hotelPrice" onchange="SumPrice()" onkeydown="checkNum()">元/间</td>
							<td><input type="text" value="<%=rd.getStringByDI("hotelPlanJgList","FJS",p)%>" class="hotelNum" onchange="SumPrice()" name="hotel<%=jdid%>/roomNum[<%=p %>]" onkeydown="checkNum()"/>/间</td>
							<td><input type="text" value="<%=Integer.parseInt(rd.getStringByDI("hotelPlanJgList","JG",p))*Integer.parseInt(rd.getStringByDI("hotelPlanJgList","FJS",p))%>" class="xiaoji" readonly="readonly"/>元</td>
						</tr>
						<%} }%>
					</table>
				</td>
			</tr>
			<tr>
			<td  colspan="4">
				备注：<textarea rows="2" cols="90" name="TA_ZT_JHHOTEL/BZ[<%=i %>]"><%=rd.getStringByDI("hotelPlanList","BZ",i)%></textarea>
			</td>
			</tr>
		<tr>
			<td align="right" colspan="4">
			<font color="red">签单小计金额：</font><input type="text" value="<%=rd.getStringByDI("hotelPlanList","QDXJ",i) %>" name="TA_ZT_JHHOTEL/QDXJ[<%=i %>]" onkeydown="checkNum()" class="smallInput qdxj" onchange="sumRs()"/ >/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付小计金额：</font><input type="text" value="<%=rd.getStringByDI("hotelPlanList","XFXJ",i) %>" name="TA_ZT_JHHOTEL/XFXJ[<%=i %>]"  readonly="readonly" class="smallInput xfxj"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">共计：</font>   <input  type="text" value="<%=rd.getStringByDI("hotelPlanList","HJ",i) %>"  name="TA_ZT_JHHOTEL/HJ[<%=i %>]" readonly="readonly" class="smallInput gj"/>/元
			</td>
		</tr>

  </table>
  <%} %>

</div>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>酒店费用合计</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">签单金额总计：</font><input name="TA_TDJDXXZJB_ZT/QDZSZJ" value="<%="".equals(qdzj)?"0":qdzj %>"  type="text" id="qiandan" readonly="readonly" class="smallInput"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付金额总计：</font><input name="TA_TDJDXXZJB_ZT/XFZSZJ" value="<%="".equals(xfzj)?"0":xfzj %>"  type="text" id="xianfu" readonly="readonly" name="xfzj" class="smallInput"/>/元&nbsp;&nbsp;&nbsp;
			<font color="red">总计：</font>   <input name="TA_TDJDXXZJB_ZT/ZSZJ" type="text" value="<%="".equals(zj)?"0":zj %>" readonly="readonly" id="zongji" class="smallInput"/>/元
		</td>
	</tr>
</table>
</div>
<div align="center" id="last-sub">
	<input type="hidden" name="TA_ZT_JHHOTEL/JHZT" value="Y"/>
	<input type="hidden" name="TA_ZT_JHHOTEL/JHZDR" value="<%=sd.getString("userno") %>"/>
	<input type="button" id="button" value="提交" onclick="p_editHotelPlan();"/>
	<input type="button" id="button" value="关闭" onclick="javascript:window.parent.parent.GB_hide();"/>
</div>
</div>
</form>
</body>
</html>
<script type="text/javascript">
	<%
	for(int v=0;v<hotelPlanRows;v++){
	%>
	var linkage = new Linkage("dataSrc<%=v%>", "<%=request.getContextPath()%>/main/data/Hotelz.xml");
	linkage.init();
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelPlanList","SF",v)%>",1);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelPlanList","CITY",v)%>",2);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelPlanList","XQ",v)%>",3);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelPlanList","LEVEL_ID",v)%>",4);
	linkage.initLinkage("dataSrc<%=v%>","<%=rd.getStringByDI("hotelPlanList","JDID",v)%>",5);
	<%}%>
</script>