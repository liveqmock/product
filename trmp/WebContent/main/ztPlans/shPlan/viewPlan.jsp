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
<title>ҵ�����</title>
</head>
	
<body>
<form  name="p_shplan_form" method="post">

<div id="lable"><span >ҵ�����</span></div>
<div id="bm-table">
<inuput type="hidden" name="userno" value="<%=sd.getString("userno")%>">
<inuput type="hidden" id="groupId" value="<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>">
<table id="groupDiv" class="datas">
			<tr>
				<td colspan="4"><span>��&nbsp;>>>&nbsp;<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&nbsp;&nbsp;������Ϣ</span></td>
			</tr>
			<tr>
			   <td align="right">&nbsp;&nbsp;�������ڣ�</td>
			   <td><font color="red"><%=rd.getStringByDI("TA_ZT_GROUPs","BEGIN_DATE",0) %></font></td>
			   <td align="right">�������ڣ�</td>
			   <td><font color="red"><%=rd.getStringByDI("TA_ZT_GROUPs","END_DATE",0) %></font></td>
			</tr>
			<tr>
				<td align="right">��·���ƣ�</td>
				<%String XLMC = rd.getStringByDI("TA_ZT_GROUPs","XLMC",0); %>
				<td><font color="red"><%=XLMC.length()<=50?XLMC:XLMC.substring(0,50)+"..."  %></font></td>
				<td align="right"> �źű�ţ�
					<input type="hidden" value="28723" name="group_fkId"/>
				</td>
				<td><%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %></td>
			</tr>
			<tr>
				<td align="right">��ע��</td>
				<%String TSYQ = rd.getStringByDI("TA_ZT_GROUPs","COMMENTS",0); %>
				<td colspan="3"> <%=TSYQ.length()<=70?TSYQ:TSYQ.substring(0,70)+"..."  %></td>
			</tr>
	</table>
	<table class="datas">
		<tr>
			<td  colspan="2"><span>������ϸ</span></td>
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
					��������:<font color="red"><%=rd.getStringByDI("selectGuideFY2","DYXM",i) %></font>&nbsp;&nbsp;
					<font color="red">�������:</font><%=rd.getStringByDI("selectGuideFY2","DYLK",i) %>Ԫ&nbsp;&nbsp;
					<font color="red">������:</font><%=rd.getStringByDI("selectGuideFY2","DFF",i) %>Ԫ
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
		<td ><span>�Ƶ���úϼ�</span> <span class="showPointer" title="��ʾ�Ƶ������ϸ" onclick="return GB_myShow('�Ƶ�ƻ�','<%=request.getContextPath()%>/main/ztPlans/hotelPlan/ztSelectHotelPlanInfo.?TA_ZT_JHHOTEL/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>','800','850','')">��ʾ��ϸ</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">ǩ������ܼƣ�</font><%=!qdzszj.equals("")?qdzszj:0 %>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">�ָ�����ܼƣ�</font><%=!xfzszj.equals("")?xfzszj:0 %>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">�ܼƣ�</font><%=!zszj.equals("")?zszj:0 %>/Ԫ
		</td>
	</tr>
</table>
<table class="datas" style="margin-top: 3px;">
  <tr>
	<td><span>�������úϼ�</span> <span class="showPointer" title="��ʾ����������ϸ" onclick="return GB_myShow('�����ƻ�','<%=request.getContextPath()%>/main/ztPlans/dinnerPlan/ztListDinnerPlan.?TA_ZT_GROUP/ID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_ZT_JHCT/ID=&TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>','800','850','')">��ʾ��ϸ</span></td>
  </tr>
  <tr>
	<td align="right">
	  <font color="red">ǩ������ܼƣ�</font>
	  <%=!qdctzj.equals("")?qdctzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">�ָ�����ܼƣ�</font>
	  <%=!xfctzj.equals("")?xfctzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">�ܼƣ�</font>
	  <%=!ctzj.equals("")?ctzj:0 %>/Ԫ</td>
  </tr>
</table>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>������úϼ�</span> <span class="showPointer" title="��ʾ���������ϸ" onclick="return GB_myShow('�鿴����ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/sceneryPlan/ztInitSceneryPlan.?TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_ZT_JHJD/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&flag=view','800','700','')">��ʾ��ϸ</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">ǩ������ܼƣ�</font><%=!qdjdzj.equals("")?qdjdzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">�ָ�����ܼƣ�</font><%=!xfjdzj.equals("")?xfjdzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">�ܼƣ�</font><%=!jdzj.equals("")?jdzj:0 %>/Ԫ
		</td>
	</tr>
</table>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>�ؽӷ��úϼ�</span> <span class="showPointer" title="��ʾ�ؽӷ�����ϸ" onclick="return GB_myShow('�鿴�ؽ���ƻ���Ϣ','<%=request.getContextPath() %>/main/ztPlans/groundTravePlan/ztInitGroundPlan.?TA_ZT_JHDJ/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&flag=view','800','600','')">��ʾ��ϸ</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">ǩ������ܼƣ�</font><%=!qddjzj.equals("")?qddjzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">�ָ�����ܼƣ�</font><%=!xfdjzj.equals("")?xfdjzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">�ܼƣ�</font><%=!djzj.equals("")?djzj:0 %>/Ԫ
		</td>
	</tr>
</table>
<table class="datas" style="margin-top: 3px;">
	<tr>
		<td ><span>Ʊ����úϼ�</span> <span class="showPointer" title="��ʾƱ�������ϸ" onclick="return GB_myShow('Ʊ��Ƶ�','<%=request.getContextPath()%>/main/ztPlans/ticketPlan/ztInitTicketP.?TA_ZT_JHPW/tID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&dmsm/JTGJ=2&flag=view&TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_ZT_JHPWMX/tid=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>','800','850','')">��ʾ��ϸ</span></td>
	</tr>
	<tr>
		<td align="right">
			<font color="red">�����ѽ���ܼƣ�</font><%=!sxfzj.equals("")?sxfzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">ǩ������ܼƣ�</font><%=!qdpwzj.equals("")?qdpwzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">�ָ�����ܼƣ�</font><%=!xfpwzj.equals("")?xfpwzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp;
			<font color="red">�ܼƣ�</font><%=!pwzj.equals("")?pwzj:0 %>/Ԫ
		</td>
	</tr>
</table>

<table class="datas" >
  <tr>
	<td><span>�������úϼ�</span> <span class="showPointer" title="��ʾ����������ϸ" onclick="return GB_myShow('�����ƻ���ϸ','<%=request.getContextPath() %>/main/ztPlans/vehPlan/ztInitVehPlan.?TA_ZT_JHCL/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&TA_TDJDXXZJB_ZT/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&flag=view','800','800','')">��ʾ��ϸ</span></td>
  </tr>
  <tr>
	<td align="right">
	  <font color="red">ǩ������ܼƣ�</font>
	  <%=!qdclzj.equals("")?qdclzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">�ָ�����ܼƣ�</font>
	  <%=!xfclzj.equals("")?xfclzj:0 %>/Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">�ܼƣ�</font>
	  <%=!clzj.equals("")?clzj:0 %>/Ԫ</td>
  </tr>
</table>
<%
	String gw=rd.getStringByDI("TA_ZT_GROUPs","GW",0);
	if("1".equals(gw)){
%>
<table class="datas" >
  <tr>
	<td><span>������Ϣ</span> <span class="showPointer" title="��ʾ���������ϸ" onclick="return GB_myShow('������Ϣ���','<%=request.getContextPath() %>/main/ztPlans/ShoppingPlan/ztInitShopPlan.?TA_ZT_JHGW/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&flag=view','800','800','')">��ʾ��ϸ</span></td>
  </tr>
  <tr>
	<td align="right">
		�������ݣ�<font color="red"><%int newShopRows=rd.getTableRowsCount("TA_ZT_JHGWs");  for(int i = 0; i < newShopRows; i++){String SFXZ= rd.getStringByDI("TA_ZT_JHGWs","SFXZ",i); if("Y".equals(SFXZ)){out.print(rd.getStringByDI("TA_ZT_JHGWs","GWDMC",i)+"&nbsp;");}} %></font>
  </tr>
</table>
<%} %>
<%
	String jiad=rd.getStringByDI("TA_ZT_GROUPs","JD",0);
	if("1".equals(jiad)){
%>
<table class="datas" >
  <tr>
	<td><span>�ӵ���Ϣ</span> <span class="showPointer" title="��ʾ�ӵ������ϸ" onclick="return GB_myShow('�ӵ���Ϣ���','<%=request.getContextPath() %>/main/ztPlans/plusPlan/ztInitNewScenery4Edit.?TA_ZT_JHJIAD/TID=<%=rd.getStringByDI("TA_ZT_GROUPs","ID",0) %>&flag=view','800','800','')">��ʾ��ϸ</span></td>
  </tr>
  <tr>
	<td align="right">
		�ӵ����ݣ�<font color="red"><%int newSceneryRows = rd.getTableRowsCount("TA_ZT_JHJIADs");for(int i=0;i<rd.getTableRowsCount("rsScenerys");i++){out.print(rd.getStringByDI("rsScenerys","jdmc",i)+" ");} %></font>
  </tr>
</table>
<%} %>
<table class="datas" style="margin-top: 3px;">
  <tr>
	<td><span>������</span></td>
  </tr>
  <tr>
	<td align="right">
	  <font color="red">ǩ����</font>
	  <%
	  	float qdzs=qdzszj.length()>0?Float.parseFloat(qdzszj):0;
	  	float qdct=qdctzj.length()>0?Float.parseFloat(qdctzj):0;
	  	float qdjd=qdjdzj.length()>0?Float.parseFloat(qdjdzj):0;
	  	float qdpw=qdpwzj.length()>0?Float.parseFloat(qdpwzj):0;
	  	float qdcl=qdclzj.length()>0?Float.parseFloat(qdclzj):0;
	  	float qddj=qddjzj.length()>0?Float.parseFloat(qddjzj):0;
	  %>
	  <%=qdzs+qdct+qdjd+qdpw+qdcl+qddj %>Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">�ָ���</font>
	  <%
	  	float xfzs=xfzszj.length()>0?Float.parseFloat(xfzszj):0;
	  	float xfct=xfctzj.length()>0?Float.parseFloat(xfctzj):0;
	  	float xfjd=xfjdzj.length()>0?Float.parseFloat(xfjdzj):0;
	  	float xfpw=xfpwzj.length()>0?Float.parseFloat(xfpwzj):0;
	  	float xfcl=xfclzj.length()>0?Float.parseFloat(xfclzj):0;
	  	float xfdj=xfdjzj.length()>0?Float.parseFloat(xfdjzj):0;
	  %>
	  <%=xfzs+xfct+xfjd+xfpw+xfcl+xfdj %>Ԫ&nbsp;&nbsp;&nbsp; 
	  <font color="red">�ܼƣ�</font>
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
	  <%=jqzj %>Ԫ
	   <font color="red">Ӫ�գ�</font><%=yskje %>Ԫ
	   <font color="red">����ë����</font>
	   <%=((yskje-jqzj)*100)/100 %>Ԫ
	  </td>
	  
  </tr>
</table>
<table class="datas">
	<tr>
		<td  colspan="2"><span>�������</span></td>
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
	<input type="button" id="button" value="�� ��" onclick="window.history.go(-1)"/>
</div>

</form>
</body>
</html>