<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common.js"></script>

<script type="text/javascript">
        var GB_ROOT_DIR = "<%=request.getContextPath()%>/greybox/";
    </script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/greybox/AJS.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/greybox/AJS_fx.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/greybox/gb_scripts.js"></script>
    <link href="<%=request.getContextPath()%>/greybox/gb_styles.css" rel="stylesheet" type="text/css" media="all" />


<script type="text/javascript">
GB_myShow = function(caption, url, /* optional */ height, width, callback_fn) {
    var options = {
        caption: caption,
        height: 500,// 500,
        width: width || 700,
        fullscreen: true,
        overlay_click_close: true,
        show_loading: false,
        callback_fn: callback_fn
    };
    var win = new GB_Window(options);
    return win.show(url);
};
</script>

<script type="text/javascript">
	function showIdea(ck){
		if(true==ck){
			document.getElementById("idea").style.display="block";
		}else{
			document.getElementById("idea").style.display="none";
		}
	}
</script>
<title>业务审核</title>
</head>
	
<body>
<form  name="p_shplan_form" method="post">

<div id="lable"><span >业务审核</span></div>
<div id="bm-table">
<inuput type="hidden" name="userno" value="<%=sd.getString("userno")%>">
<inuput type="hidden" id="groupId" value="<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>">
<table id="groupDiv" class="datas">
			<tr>
				<td colspan="4"><span>团&nbsp;>>>&nbsp;<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&nbsp;&nbsp;基本信息</span></td>
			</tr>
			<tr>
			   <td align="right">&nbsp;&nbsp;出团日期：</td>
			   <td><font color="red"><%=rd.getStringByDI("TA_ZT_GROUPs","BEGIN_DATE",0) %></font></td>
			   <td align="right">返团日期：</td>
			   <td><font color="red"><%=rd.getStringByDI("TA_ZT_GROUPs","END_DATE",0) %></font></td>
			</tr>
			<tr>
				<td align="right">线路名称：</td>
				<%String XLMC = rd.getStringByDI("TA_ZT_GROUPs","XLMC",0); %>
				<td><font color="red"><%=XLMC.length()<=50?XLMC:XLMC.substring(0,50)+"..."  %></font></td>
				<td align="right"> 团号编号：
					<input type="hidden" value="28723" name="group_fkId"/>
				</td>
				<td><%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %></td>
			</tr>
			<tr>
				<td align="right">备注：</td>
				<%String TSYQ = rd.getStringByDI("TA_ZT_GROUPs","COMMENTS",0); %>
				<td colspan="3"> <%=TSYQ.length()<=70?TSYQ:TSYQ.substring(0,70)+"..."  %></td>
			</tr>
	</table>
	<table class="datas">
		<tr>
			<td  colspan="2"><span>导游明细</span></td>
		</tr>
		<%
			int dyRows=rd.getTableRowsCount("selectGuideFY2");
			int dffzj = 0;
			for(int i=0;i<dyRows;i++){
				String dffStr = rd.getStringByDI("selectGuideFY2","DFF",i);
				if("".equals(dffStr)){dffStr="0";}
				dffzj+=Integer.parseInt(dffStr);
		%>
		<tr>
			<td align="right">
				<div >
					导游姓名:<font color="red"><%=rd.getStringByDI("selectGuideFY2","DYXM",i) %></font>&nbsp;&nbsp;
					<font color="red">导游领款:</font><%=rd.getStringByDI("selectGuideFY2","DYLK",i) %>元&nbsp;&nbsp;
					<font color="red">导服费:</font><%=rd.getStringByDI("selectGuideFY2","DFF",i) %>元
				</div>
			</td>
			
		</tr>
		<%} %>
	</table>
	<%
		String qdzszj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDZSZJ",0);
		String xfzszj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFZSZJ",0);
		String zszj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","ZSZJ",0);
		String qdctzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDCTZJ",0);
		String xfctzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFCTZJ",0);
		String ctzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","CTZJ",0);
		String qdjdzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDJDZJ",0);
		String xfjdzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFJDZJ",0);
		String jdzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","JDZJ",0);		
		String qdpwzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDPWZJ",0);		
		String xfpwzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFPWZJ",0);		
		String pwzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","PWZJ",0);		
		String qdclzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","QDCLZJ",0);
		String xfclzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","XFCLZJ",0);
		String clzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","CLZJ",0);
		String sxfzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","SXFZJ",0);
		
		String qddjzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJQDZJ",0);
		String xfdjzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJXFZJ",0);
		String djzj=rd.getStringByDI("TA_TDJDXXZJB_ZTs","DJZJ",0);
	%>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>酒店费用合计</span> <span class="showPointer" title="显示酒店费用明细" onclick="return GB_myShow('酒店计划','<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztSelectHotelPlanInfo.?TA_ZT_JHHOTEL/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>','800','850','')">显示明细</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">签单金额总计：</font><%=!qdzszj.equals("")?qdzszj:0 %>/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付金额总计：</font><%=!xfzszj.equals("")?xfzszj:0 %>/元&nbsp;&nbsp;&nbsp;
			<font color="red">总计：</font><%=!zszj.equals("")?zszj:0 %>/元
		</td>
	</tr>
</table>
<table class="datas" style="margin-top: 3px;">
  <tr>
	<td><span>餐厅费用合计</span> <span class="showPointer" title="显示餐厅费用明细" onclick="return GB_myShow('餐厅计划','<%=request.getContextPath()%>/main/ztPlans/dinnerPlan/ztListDinnerPlan.?TA_ZT_GROUP/ID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_ZT_JHCT/ID=&TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>','800','850','')">显示明细</span></td>
  </tr>
  <tr>
	<td align="right">
	  <font color="red">签单金额总计：</font>
	  <%=!qdctzj.equals("")?qdctzj:0 %>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">现付金额总计：</font>
	  <%=!xfctzj.equals("")?xfctzj:0 %>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">总计：</font>
	  <%=!ctzj.equals("")?ctzj:0 %>/元</td>
  </tr>
</table>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>景点费用合计</span> <span class="showPointer" title="显示景点费用明细" onclick="return GB_myShow('查看景点计划信息','<%=request.getContextPath() %>/main/ztPlans/sceneryPlan/ztInitSceneryPlan.?TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_ZT_JHJD/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&flag=view','800','700','')">显示明细</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">签单金额总计：</font><%=!qdjdzj.equals("")?qdjdzj:0 %>/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付金额总计：</font><%=!xfjdzj.equals("")?xfjdzj:0 %>/元&nbsp;&nbsp;&nbsp;
			<font color="red">总计：</font><%=!jdzj.equals("")?jdzj:0 %>/元
		</td>
	</tr>
</table>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>地接费用合计</span> <span class="showPointer" title="显示地接费用明细" onclick="return GB_myShow('查看地接社计划信息','<%=request.getContextPath() %>/main/ztPlans/groundTravePlan/ztInitGroundPlan.?TA_ZT_JHDJ/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&flag=view','800','600','')">显示明细</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">签单金额总计：</font><%=!qddjzj.equals("")?qddjzj:0 %>/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付金额总计：</font><%=!xfdjzj.equals("")?xfdjzj:0 %>/元&nbsp;&nbsp;&nbsp;
			<font color="red">总计：</font><%=!djzj.equals("")?djzj:0 %>/元
		</td>
	</tr>
</table>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>票务费用合计</span> <span class="showPointer" title="显示票务费用明细" onclick="return GB_myShow('票务计调','<%=request.getContextPath()%>/main/ztPlans/ticketPlan/ztInitTicketP.?TA_ZT_JHPW/tID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&dmsm/JTGJ=2&flag=view&TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_ZT_JHPWMX/tid=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>','800','850','')">显示明细</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">手续费金额总计：</font><%=!sxfzj.equals("")?sxfzj:0 %>/元&nbsp;&nbsp;&nbsp;
			<font color="red">签单金额总计：</font><%=!qdpwzj.equals("")?qdpwzj:0 %>/元&nbsp;&nbsp;&nbsp;
			<font color="red">现付金额总计：</font><%=!xfpwzj.equals("")?xfpwzj:0 %>/元&nbsp;&nbsp;&nbsp;
			<font color="red">总计：</font><%=!pwzj.equals("")?pwzj:0 %>/元
		</td>
	</tr>
</table>

<table class="datas" >
  <tr>
	<td><span>车辆费用合计</span> <span class="showPointer" title="显示车辆费用明细" onclick="return GB_myShow('车调计划明细','<%=request.getContextPath() %>/main/ztPlans/vehPlan/ztInitVehPlan.?TA_ZT_JHCL/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&flag=view','800','800','')">显示明细</span></td>
  </tr>
  <tr>
	<td align="right">
	  <font color="red">签单金额总计：</font>
	  <%=!qdclzj.equals("")?qdclzj:0 %>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">现付金额总计：</font>
	  <%=!xfclzj.equals("")?xfclzj:0 %>/元&nbsp;&nbsp;&nbsp; 
	  <font color="red">总计：</font>
	  <%=!clzj.equals("")?clzj:0 %>/元</td>
  </tr>
</table>
<%
	String gw=rd.getStringByDI("TA_ZT_GROUPs","GW",0);
	if("1".equals(gw)){
%>
<table class="datas" >
  <tr>
	<td><span>购物信息</span> <span class="showPointer" title="显示购物费用明细" onclick="return GB_myShow('购物信息审核','<%=request.getContextPath() %>/main/ztPlans/ShoppingPlan/ztInitShopPlan.?TA_ZT_JHGW/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&flag=view','800','800','')">显示明细</span></td>
  </tr>
  <tr>
	<td align="right">
		购物内容：<font color="red"><%int newShopRows=rd.getTableRowsCount("TA_ZT_JHGWs");  for(int i = 0; i < newShopRows; i++){String SFXZ= rd.getStringByDI("TA_ZT_JHGWs","SFXZ",i); if("Y".equals(SFXZ)){out.print(rd.getStringByDI("TA_ZT_JHGWs","GWDMC",i)+"&nbsp;");}} %></font>
  </tr>
</table>
<%} %>
<%
	String jiad=rd.getStringByDI("TA_ZT_GROUPs","JD",0);
	if("1".equals(jiad)){
%>
<table class="datas" >
  <tr>
	<td><span>加点信息</span> <span class="showPointer" title="显示加点费用明细" onclick="return GB_myShow('加点信息审核','<%=request.getContextPath() %>/main/ztPlans/plusPlan/ztInitNewScenery4Edit.?TA_ZT_JHJIAD/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&flag=view','800','800','')">显示明细</span></td>
  </tr>
  <tr>
	<td align="right">
		加点内容：<font color="red"><%int newSceneryRows = rd.getTableRowsCount("TA_ZT_JHJIADs");for(int i=0;i<rd.getTableRowsCount("rsScenerys");i++){out.print(rd.getStringByDI("rsScenerys","jdmc",i)+" ");} %></font>
  </tr>
</table>
<%} %>
<table class="datas" style="margin-top: 3px;">
  <tr>
	<td><span>团利润</span></td>
  </tr>
  <tr>
	<td align="right">
	  <font color="red">签单：</font>
	  <%
	  	float qdzs=qdzszj.length()>0?Float.parseFloat(qdzszj):0;
	  	float qdct=qdctzj.length()>0?Float.parseFloat(qdctzj):0;
	  	float qdjd=qdjdzj.length()>0?Float.parseFloat(qdjdzj):0;
	  	float qdpw=qdpwzj.length()>0?Float.parseFloat(qdpwzj):0;
	  	float qdcl=qdclzj.length()>0?Float.parseFloat(qdclzj):0;
	  	float qddj=qddjzj.length()>0?Float.parseFloat(qddjzj):0;
	  %>
	  <%=qdzs+qdct+qdjd+qdpw+qdcl+qddj %>元&nbsp;&nbsp;&nbsp; 
	  <font color="red">现付：</font>
	  <%
	  	float xfzs=xfzszj.length()>0?Float.parseFloat(xfzszj):0;
	  	float xfct=xfctzj.length()>0?Float.parseFloat(xfctzj):0;
	  	float xfjd=xfjdzj.length()>0?Float.parseFloat(xfjdzj):0;
	  	float xfpw=xfpwzj.length()>0?Float.parseFloat(xfpwzj):0;
	  	float xfcl=xfclzj.length()>0?Float.parseFloat(xfclzj):0;
	  	float xfdj=xfdjzj.length()>0?Float.parseFloat(xfdjzj):0;
	  %>
	  <%=xfzs+xfct+xfjd+xfpw+xfcl+xfdj %>元&nbsp;&nbsp;&nbsp; 
	  <font color="red">总计：</font>
	  <%
	  	float zs=zszj.length()>0?Float.parseFloat(zszj):0;
	  	float ct=ctzj.length()>0?Float.parseFloat(ctzj):0;
	  	float jd=jdzj.length()>0?Float.parseFloat(jdzj):0;
	  	float pw=pwzj.length()>0?Float.parseFloat(pwzj):0;
	  	float cl=clzj.length()>0?Float.parseFloat(clzj):0;
	  	float dj=djzj.length()>0?Float.parseFloat(djzj):0;
	  	
	  	String spzsf=rd.getStringByDI("TA_ZT_JHHOTELs","SFZSF",0);
	  	float sp=spzsf.length()>0?Float.parseFloat(spzsf):0;
	  	String ysk=rd.getStringByDI("selectYsk","ddysk",0);
   		float yskje=ysk.length()>0?Float.parseFloat(ysk):0;
   		float jqzj = zs+ct+jd+pw+cl+dj+dffzj;
	  %>
	  <%=jqzj %>元
	   <font color="red">营收：</font><%=yskje %>元
	   <font color="red">账面毛利：</font>
	   <%=((yskje-jqzj)*100)/100 %>元
	  </td>
	  
  </tr>
</table>
<table class="datas">
	<tr>
		<td  colspan="2"><span>审批意见</span></td>
	</tr>
	<tr>
		<td  colspan="2">
			<%
				int rows=rd.getTableRowsCount("SPYJ");
				if(rows>0){
					for(int i=0;i<rows;i++){
			%>
				<%=rd.getStringByDI("SPYJ","USERNAME",i)%>:
				<%=rd.getStringByDI("SPYJ","SPYJ",i)%>;<br/>
			<%			
					}
				} %>
		</td>
	</tr>
</table>
</div>
<%
	String ID=rd.getStringByDI("TA_ZT_GROUPs","ID",0);
%>
<script type="text/javascript">
	function selPrint(){
		if(document.getElementById("checkbox").checked==true){
			window.location.href="<%=request.getContextPath() %>/main/ztPlans/ztQueryGroupPrintList.?pageNO=1&pageSize=10";
		}
	}
</script>
<div align="center" id="last-sub">
	<input type="button" id="button" value="返 回" onclick="window.history.go(-1)"/>
</div>

</form>
</body>
</html>