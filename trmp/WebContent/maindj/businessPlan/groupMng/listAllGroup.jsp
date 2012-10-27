<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 0;
	if(rd.getTableRowsCount("rsAllGroupList")>0){
		
	   	pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsAllGroupList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("rsAllGroupList", "rowsCount");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>�Ŷ��б�</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>
<style type="text/css">
* { margin:0; padding:0; word-wrap:break-word; word-break:break-all; }
a { text-decoration: none; }

ul, li { list-style:none; }
html { overflow:-moz-scrollbars-vertical; } /* ��Firefox��ʼ����ʾ������*/

/*������ʽ��ʼ*/
#navigation { 
	width:140px;
	padding:0px; 
	margin:0px; 
	background:none;
	height:28px;	
}
#navigation ul li { 
	margin-left:5px;
	margin-right:14px;
	float:left; 
	
	position: relative ;
}
#navigation ul li a { 
	display:block;
	padding:2px 8px; 
	background:#EEEEEE; 
}
#navigation ul li a:hover { 
	background:none; 
	color:green;
	font-weight:700;
}
#navigation ul li ul{
	background-color: #88C366;
	position: absolute;
	width: 100px;
	overflow:hidden;
	display:none;
}
#navigation ul li:hover ul{
	background-color: #88C366;
	position: absolute;
	width: 100px;
	display:block;
	
}
#navigation ul li ul li{
	border-bottom: 2px solid #BBB;
	text-align: center;
	width: 100%;
}
/*������ʽ����*/
</style>
<script type="text/javascript">
function queryGroupList(){
	document.djListGroup.action.name="djQueryGroupList.?pageNO=1&pageSize=10";
	document.djListGroup.submit();
}
function openUrl(url){
	window.location.href=url;
}
$(function(){
	   $("#navigation ul li:has(ul)").hover(function(){
			$(this).children("ul").stop(true,true).slideDown(400);
     },function(){
		    $(this).children("ul").stop(true,true).slideUp();
		});
})
</script>
</head>
<body>
<form name="djListGroup" method="post">
<div id="lable" >
<div style="float:left;"><span>�ؽӵ���&nbsp;&nbsp;&nbsp;&nbsp;</span></div>
  <div style="float:left;">
	
	<font color="red">��</font>δʵʩ&nbsp;&nbsp;<font color="green">��</font>��ʵʩ
  </div>
</div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td>�źţ�</td>
			<td><input type="text" name="groupID"></td>
			<td align="right">�������ڣ�</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" /></td>
			<td align="right">��·���ƣ�</td>
			<td><input type="text" name="xlmc" />
				
			</td>
			<td align="right">��״̬��</td>
			<td>
				<select name="state">
					<option value="">***��ѡ��***</option>
					<option value="1">���ƻ�</option>
					<option value="2">ʵʩ��</option>
					<option value="3">��ʵʩ</option>
					<option value="4">�����/������</option>
					<option value="5">������</option>
					<option value="6">�ѱ���</option>
				</select>
			</td>
			<td align="right">�Ƿ񲵻أ�</td>
			<td>
				<select name="isBack">
					<option value="">***��ѡ��***</option>
					<option value="Y">��</option>
					<option value="N">��</option>
				</select>
			</td>
			<td><a href="###" onclick="queryGroupList();"><img alt="����" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	</table>
</div>
<div id="list-table" >
  <table class="datas" >
	<tr id="first-tr">
	  <td width="12%">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</td>
	  <td width="20%">��·����</td>
  	  <td width="15%">��������</td>
	  <td width="8%">����</td>
	<%--   <td width="12%">�۸�</td> --%>
	  <td width="20%">�Ƶ�״̬</td>
	  <td width="18%">������ȷ�ϼ�</td>
	  <td width="7%">����</td>
	</tr>
	<%
	String roleID = sd.getString("USERROLEST");
	boolean isTrue = false;
	if(!"".equals(roleID)){
		
		roleID = roleID.substring(1,roleID.length()-1);
		String[] roleIDs = roleID.split(",");
		for(int i=0;i<roleIDs.length;i++){
			if("business".equals(roleIDs[i].trim()) ){
				isTrue = true;
				break;
			}
		}
	}
		int rows = rd.getTableRowsCount("rsAllGroupList");
		for(int i=0;i<rows;i++) {
			int index = 9999 - i;
			String ID = rd.getString("rsAllGroupList","ID",i);
			String XLID = rd.getString("rsAllGroupList","XLID",i);
			String XLMC = rd.getString("rsAllGroupList","XLMC",i);
			String BEGIN_DATE = rd.getString("rsAllGroupList","BEGIN_DATE",i);
			String DTRQ = rd.getString("rsAllGroupList","DTRQ",i);
			String CRRS = rd.getString("rsAllGroupList","CRRS",i);
			String ETRS = rd.getString("rsAllGroupList","ETRS",i);
			String STATE = rd.getString("rsAllGroupList","STATE",i);
			String isBack = rd.getString("rsAllGroupList","isback",i);
			int state = Integer.parseInt(STATE);//��״̬
			String gw = rd.getString("rsAllGroupList","gw",i);//����
			String jd = rd.getString("rsAllGroupList","jd",i);//�ӵ�
			String hoteltid = rd.getString("rsAllGroupList","hotel",i);//�Ƶ�
			String dinnerroom = rd.getString("rsAllGroupList","dinnerroom",i);//����
			String veh = rd.getString("rsAllGroupList","veh",i);//����
			String travel = rd.getString("rsAllGroupList","travel",i);//�ؽ�
			String guide = rd.getString("rsAllGroupList","guide",i);//����
			String shop = rd.getString("rsAllGroupList","shop",i);//����
			String scenery = rd.getString("rsAllGroupList","scenery",i);//����
			String jiad = rd.getString("rsAllGroupList","jiad",i);//�ӵ�
			String ticket = rd.getString("rsAllGroupList","ticket",i);//Ʊ��
			String other = rd.getString("rsAllGroupList","other",i);//����
			//��״̬��1�����ƻ���2��ʵʩ�У�3����ʵʩ/����ˣ�4�������/��������5�������У�6���ѱ���/����� ���״̬��Y����Nδ���
	%>
	<tr>
	  <td><a href="###" onclick="TB_show('�鿴�Ż�����Ϣ','<%=request.getContextPath()%>/maindj/businessPlan/groupMng/djInitUpdateGroup.?dmsm/xlqy=20&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&DMSM/JTGJ=2&DMSM/JGLX=4&TA_DJ_TZTS/TID=<%=ID %>&TA_DJ_TZTS/orgid=<%=sd.getString("orgid") %>&TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&XZQH/PID=0&action=view*TB_iframe=true&height=400&width=800','')" title=""><%=ID %></a></td>
	  <td>
	  	<img alt="�ȵ���·" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
	  	<a href="###" onclick="" title="<%=XLMC %>"><%=XLMC.length()<=20?XLMC:XLMC.substring(0,20)+"..."  %></a>
	  </td>
	  <td>
	  	<%=BEGIN_DATE %>
	  </td>
	  <td>
		  ����:<font color="red"><%=CRRS %></font>��<br>
		  ��ͯ:<font color="red"><%=ETRS %></font>��
	  </td>
	  <td>
		&nbsp;<%if("Y".equals(hoteltid)){%><font color="green">��</font><%}else{%><font color="red">��</font><%} %>�Ƶ�
		&nbsp;<%if("Y".equals(dinnerroom)){%><font color="green">��</font><%}else{%><font color="red">��</font><%} %>����
		&nbsp;<%if("Y".equals(veh)){%><font color="green">��</font><%}else{%><font color="red">��</font><%} %>����
		&nbsp;<%if("Y".equals(travel)){%><font color="green">��</font><%}else{%><font color="red">��</font><%} %>�ؽ�
		&nbsp;<%if("Y".equals(guide)){%><font color="green">��</font><%}else{%><font color="red">��</font><%} %>����<p>
		&nbsp;<%if("Y".equals(scenery)){%><font color="green">��</font><%}else{%><font color="red">��</font><%} %>����
		&nbsp;<%if("Y".equals(ticket)){%><font color="green">��</font><%}else{%><font color="red">��</font><%} %>Ʊ��
		&nbsp;<%if("Y".equals(shop)){%><font color="green">��</font><%}else{%><font color="red">��</font><%} %>����
		&nbsp;<%if("Y".equals(jiad)){%><font color="green">��</font><%}else{%><font color="red">��</font><%} %>�ӵ�
		&nbsp;<%if("Y".equals(other)){%><font color="green">��</font><%}else{%><font color="red">��</font><%} %>����
		
	  	<%if(isBack.equals("Y")){out.print("<p>���뱻����");} %>
	  </td>
	  <td>
	  <%
	  
	  int rowTzts = rd.getTableRowsCount("rsTZTS");
	  for(int j=0;j<rowTzts;j++) {
		  String tztsKeyID = rd.getString("rsTZTS","id",j);
		  String groupID = rd.getString("rsTZTS","tid",j);
		  String ztsID = rd.getString("rsTZTS","ztsid",j);
		  String cmpnyName = rd.getString("rsTZTS","cmpny_name",j);
		  if(groupID.equals(ID)) {
	  %>
	  <%=cmpnyName %><br>
	  <a href="###" onclick="TB_show('�ϴ�ȷ�ϼ�','<%=request.getContextPath()%>/maindj/businessPlan/groupMng/initConfirmForZTS.?TA_TRAVELAGENCY/TRAVEL_AGC_ID=<%=ztsID %>&id=<%=tztsKeyID %>*TB_iframe=true&height=300&width=500','');" >
	    <img alt="�ϴ�ȷ�ϼ�" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[�ϴ�]</a>
	  <a href="###" onclick="TB_show('�鿴ȷ�ϼ�','<%=request.getContextPath()%>/maindj/businessPlan/groupMng/viewConfirmByZTS.?TA_DJ_QRJ/TZTSID=<%=tztsKeyID %>*TB_iframe=true&height=400&width=800','');">
		<img alt="�鿴ȷ�ϼ�" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[�鿴]</a><br>
		  
	  <%  }
	  }
	  %>
	  </td>
	  <td style="vertical-align: middle;" align="center">
	  	<div id="navigation">
	  	  <ul>
	  	  <%if(state < 3 || isBack.equals("Y")){ %>
		  	<li style="z-index:<%=index%>">
			  	<a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ">
			  	   <img alt="ҵ��Ƶ�" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[ҵ��Ƶ�]
			  	 </a>
				<ul>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=0&mdName=hotelMd&mdUrl=djAjaxHotelInfo.?groupId=<%=ID%>">�Ƶ갲��</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=1&mdName=dinnerMd&mdUrl=djAjaxDinnerInfo.?groupId=<%=ID%>">��������</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=2&mdName=ticketMd&mdUrl=djAjaxTicketInfo.?groupId=<%=ID%>">Ʊ����</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=3&mdName=carMd&mdUrl=djAjaxCarInfo.?groupId=<%=ID%>,dmsm/cllx=13">��������</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=4&mdName=sceneryMd&mdUrl=djAjaxSceneryInfo.?groupId=<%=ID%>">���㰲��</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=5&mdName=travelMd&mdUrl=djAjaxTravelInfo.?groupId=<%=ID%>">�ؽӰ���</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=6&mdName=shopMd&mdUrl=djAjaxShopInfo.?groupId=<%=ID%>">���ﰲ��</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=7&mdName=plusMd&mdUrl=djAjaxPlusInfo.?groupId=<%=ID%>">�ӵ㰲��</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=8&mdName=otherMd&mdUrl=djAjaxOtherInfo.?groupId=<%=ID%>">��������</a></li>
					 <li><a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&TA_DJ_LINEDETAI4G@orderBy=RQ&md=9&mdName=guideMd&mdUrl=djAjaxGuideInfo.?groupId=<%=ID%>">���ΰ���</a></li>
				</ul>
			</li>
			<%}else{ %>
				<li style="z-index:<%=index%>">
			  	  <a href="<%=request.getContextPath()%>/maindj/businessPlan/plan/djInitPlan.?TA_DJ_GROUP/ID=<%=ID %>&TA_DJ_GROUP/orgid=<%=sd.getString("orgid") %>&dmsm/jtgj=2&dmsm/cllx=13&dmsm/jdxj=6&TA_DJ_LINEDETAI4G/TID=<%=ID %>&TA_DJ_LINEDETAI4G/orgid=<%=sd.getString("orgid") %>&action=view">
			  	    <img alt="�鿴ҵ��Ƶ�" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[�鿴�Ƶ���Ϣ]
			  	  </a>
			  	</li>
			<%} %>
		  </ul>
		</div>
	  </td>
	</tr>
	<%} %>
  </table>
</div>	
<div id="page">
	<%String url=request.getContextPath()+"/maindj/businessPlan/groupMng/djQueryAllGroupList.?DMSM/JDXJ=6&pageSize=10&pageNO=";%>
	<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rowsCount %> ����¼��
					ÿҳ <%=pageSize%>��
	</span>
	<span class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage"/> ҳ
	</span>
	<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>
</form>
</body>
</html>
