<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
     pageNO=Integer.parseInt((String)rd.getString("pageNO"));
	 pageSize=Integer.parseInt((String)rd.getString("pageSize"));
	 Object objTotalP = rd.getAttr("rsAllDDH", "pagesCount");
	 if(null == objTotalP)
		 totalPage = 0;
	 else
		totalPage=(Integer)objTotalP;
	 Object objRowsC = rd.getAttr("rsAllDDH", "rowsCount");
	 if(null == objRowsC)
		 rowsCount = 0;
	 else
		rowsCount = (Integer)objRowsC;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�ҵ�δͨ�����˶���</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>

<script type="text/javascript">
  function doSub(ddh,gKey,action){
	window.location.href="signUpEditIint.?ddh="+ddh+"&groupKey="+gKey+"&TA_ZT_GATHER/GATHER_ID=&dmsm/jglx=4&action="+action+"&wtgDz=Y";
  }
  function postData(){
	document.myForm.submit();
  }

  function cancleOrder(){

	  window.location.href="";
  }
</script>
</head>
<body>
<form action="wtgDzOfLines.?dzzt=2&pageNO=1&pageSize=10" name="myForm" method="post">
<div id="lable">
<div style="float: left"><span >�ҵ�δͨ�����˶���</span></div><script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
</div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="2" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td align="right">�������ڣ�<input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/>
			</td>
			<td>��<input type="text" name="eDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/></td>
			<td align="right">��·���ƣ�</td>
			<td><input type="text" name="lineName" /></td>
			<td>�źţ�</td>
			<td><input type="text" name="groupID" /></td>
		</tr>
		<tr>
		  <td>�� �� �ţ�<input type="text" name="djh" style="width: 80px;"/></td>
		  <td><a href="###" onclick="postData();"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">	
  <table class="datas" >
	<tr id="first-tr">
	  <td width="15%">������/��������</td>
	  <td width="30%">��·����</td>
  	  <td width="25%">�۸�</td>
	  <td width="10%">�ο��տ�</td>
	  <td width="10%">״̬</td>
	  <td width="10%">����</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("rsAllDDH");
		for(int i=0;i<rows;i++) {
			
			String ddh = rd.getStringByDI("rsAllDDH","ddh",i);
			String groupID = rd.getStringByDI("rsAllDDH","th",i);
			String bDate = rd.getStringByDI("rsAllDDH","begin_date",i);
			String days = rd.getStringByDI("rsAllDDH","days",i);
			String isconfirm = rd.getStringByDI("rsAllDDH","ddconfirm",i);
			String yi_sk = rd.getStringByDI("rsAllDDH","yi_sk",i);
			String line_name = rd.getStringByDI("rsAllDDH","line_name",i);
			String ysrs = rd.getStringByDI("rsAllDDH","ysrs",i);
			
			
	%>
	<tr>
	  <td>
	  <%
	  out.println(ddh);
	  out.println("<br>");
	  %>
	  <a href="###" onclick="return GB_myShow('','queryByDDH.?TA_ZT_VISITOR/M_ID=<%=ddh %>');">
	  <%
	  int row2 = rd.getTableRowsCount("rsPriceOfAllDDH");
	  for(int j=0;j<row2;j++){
		  String ddhID = rd.getStringByDI("rsPriceOfAllDDH","id",j);
		  String personCount = rd.getStringByDI("rsPriceOfAllDDH","person_count",j);
		  String priceTpNm = rd.getStringByDI("rsPriceOfAllDDH","dmsm1",j);
		  if(ddhID.equals(ddh) && !"0".equals(personCount)){
	  %>
			 <%=priceTpNm %>:(<%=personCount%>��)<br>
			  
	  <%
		  }
	  }
	  %>
	  </a>
	  </td>
	  <td><img alt="�ȵ���·" src="<%=request.getContextPath() %>/images/hot3.gif">&nbsp;
	  	<a href="###" id="select-condition" onclick="return GB_myShow('','queryByDDHForAll.?ddh=<%=ddh %>&tid=<%=groupID %>&DMSM/JTGJ=2');"><%=line_name  %></a><br>
	  	�źţ�<%=groupID %><br>
	  	<%=bDate.substring(0,10) %>(<%=days %>��)
	  </td>
	  <td>
	 <%
	  for(int j=0;j<row2;j++) {
		  String ddhID = rd.getStringByDI("rsPriceOfAllDDH","id",j);
		  String personCount = rd.getStringByDI("rsPriceOfAllDDH","person_count",j);
		  String th = rd.getStringByDI("rsPriceOfAllDDH","th",j);
		  String ms = rd.getStringByDI("rsPriceOfAllDDH","ms",j);
		  if(ddhID.equals(ddh) && !"0".equals(personCount)){
			 
	  %> 
	  <font color="red"><%=rd.getStringByDI("rsPriceOfAllDDH","dmsm1",j)%></font>:	���мۣ�<%=ms %> ͬ�мۣ�<%=th %></br>
	 <%	}
	  }%>
	  </td>
	  <td><%=yi_sk %></td>
	  <td><%=isconfirm %></td>
	  <td>
		<a href="###" onclick="doSub('<%=ddh %>','<%=groupID %>','edit');">  �޸�</a>
<%
String roleID = sd.getString("USERROLEST");
boolean isTrue = false;
if(!"".equals(roleID)){
	
	roleID = roleID.substring(1,roleID.length()-1);
	String[] roleIDs = roleID.split(",");
	for(int j=0;j<roleIDs.length;j++){
		if("admin".equals(roleIDs[j].trim()) || "seller".equals(roleIDs[j].trim())){
			isTrue = true;
			break;
		}
	}
}

%>
	  </td>
	</tr>
	<%
} %> 
  </table>
</div>	
<div id="page">
	<%String url=request.getContextPath()+"/main/moneyMng/account/wtgDzOfLines.?dzzt=2&pageSize=10&pageNO=";%>
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