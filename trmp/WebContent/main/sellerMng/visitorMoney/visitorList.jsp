<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
     pageNO=Integer.parseInt((String)rd.getString("pageNO"));
	 pageSize=Integer.parseInt((String)rd.getString("pageSize"));
	 Object objTotalP = rd.getAttr("rsVisitorMoney", "pagesCount");
	 if(null == objTotalP)
		 totalPage = 0;
	 else
		totalPage=(Integer)objTotalP;
	 Object objRowsC = rd.getAttr("rsVisitorMoney", "rowsCount");
	 if(null == objRowsC)
		 rowsCount = 0;
	 else
		rowsCount = (Integer)objRowsC;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�ҵĶ���</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/common.js"></script>

<script type="text/javascript"> 
function close() { 
parent.parent.location.reload(); 
parent.parent.GB_hide(); 
} 
</script> 





<script type="text/javascript">
  function doSub(ddh,gKey,action){
	window.location.href="listVisitorMoney.?ddh="+ddh+"&groupKey="+gKey+"&TA_GATHER/GATHER_ID=&dmsm/jglx=4";
  }
  function postData(){
	document.myForm.submit();
  }
  
  //function query(v){

	//	window.location.href="listVisitorMoney.?pageNO=1&pageSize=10&ddState="+v;
  //}
</script>
</head>
<body>
<form action="listVisitorMoney.?pageNO=1&pageSize=10" name="myForm" method="post">
<div id="lable">
<div style="float: left"><span >�ο��տ�</span></div>
</div>
<div id="thisSelect-table">
	<table >
		<tr>
			<td rowspan="2" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td align="right">�������ڣ�<input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/>
			</td>
			<td>��<input type="text" name="eDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/></td>
			<td align="right">&nbsp;&nbsp;��·���ƣ�</td>
			<td><input type="text" name="lineName" /></td>
		  <td>&nbsp;&nbsp;�� �� �ţ�<input type="text" name="ddh" style="width: 80px;"/></td>
		  <td><a href="###" onclick="postData();"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">	
  <table class="datas" >
	<tr id="first-tr">
	  <td width="13%">������</td>
	  <td width="32%">��·����</td>
	  <td width="15%">�ο�</td>
  	  <td width="10%">�۸�</td>
	  <td width="10%">�ο��տ�</td>
	  <td width="7%">״̬</td>
	  <td width="10%">����</td>
	</tr>
	<%
		int rows = rd.getTableRowsCount("rsVisitorMoney");
		for(int i=0;i<rows;i++) {
			
			String ddh = rd.getStringByDI("rsVisitorMoney","ddh",i);
			String groupKey = rd.getStringByDI("rsVisitorMoney","groupKey",i);
			String bDate = rd.getStringByDI("rsVisitorMoney","begin_date",i);
			String days = rd.getStringByDI("rsVisitorMoney","days",i);
			String ddConfirm = rd.getStringByDI("rsVisitorMoney","dd_confirm",i);
			String yi_sk = rd.getStringByDI("rsVisitorMoney","yi_sk",i);
			String yin_sk = rd.getStringByDI("rsVisitorMoney","yin_sk",i);
			String spare = rd.getStringByDI("rsVisitorMoney","spare",i);
			String line_name = rd.getStringByDI("rsVisitorMoney","line_name",i);
			String ysrs = rd.getStringByDI("rsVisitorMoney","ysrs",i);
			String visiNm = rd.getStringByDI("rsVisitorMoney","visitor_nm",i);
			String visiTel = rd.getStringByDI("rsVisitorMoney","tel",i);
			
			String jsj = rd.getStringByDI("rsVisitorMoney","jsj",i);
			
	%>
	<tr>
	  <td>
	  <%=ddh %>
	  </td>
	  <td><img alt="�ȵ���·" src="<%=request.getContextPath() %>/images/hot3.gif">&nbsp;
	  	<a id="select-condition" href="viewTravelList.?groupId=<%=groupKey %>&lineId="><%=line_name  %></a><br>
	  	�źţ�<%=groupKey %><br>
	  	�������ڣ�<%=bDate.substring(0,10) %>(<%=days %>��)
	  </td>
	  <td>
	    ����:<%=visiNm %>/�绰:<%=visiTel %><br>
	<a href="queryByDDH.?TA_ZT_VISITOR/M_ID=<%=ddh %>">
	    ������ <%
	  int row2 = rd.getTableRowsCount("rsPersonPrice");
	  for(int j=0;j<row2;j++) {
		  String ddhID = rd.getStringByDI("rsPersonPrice","id",j);
		  String personCount = rd.getStringByDI("rsPersonPrice","person_count",j);
		  String priceType = rd.getStringByDI("rsPersonPrice","pt",j);
		  if(ddhID.equals(ddh) && !"0".equals(personCount)){
			 
	  %> 
	  	<%=priceType %>:<%=personCount %>��<br>
	 <%	}
	  }%></a>
	  </td>
	  <td>
	  	�ܼ۸�<font color="red"><%=yin_sk %></font><br>
	  	����ۣ�<font color="red"><%=jsj %></font><br>
	  </td>
	  <td>
	  	Ӧ�տ<font color="red"><%=yin_sk %></font><br>
	  	���տ<font color="red"><%=yi_sk %></font><br>
	  	���տ<font color="red"><%=spare %></font><br>
	  </td>
	  <td>
<%
if("0".equals(ddConfirm))out.print("��ȷ��");
if("1".equals(ddConfirm))out.print("��ȷ��");
if("2".equals(ddConfirm))out.print("��ȡ��");
%>
	  </td>
	  <td>
	  <a href="queryForView.?ddh=<%=ddh %>&tid=<%=groupKey %>">�鿴 </a> 
	<%
	if(!spare.equals("0")){
	%>
	  <br>
	  <a href="OpMoneyInit.?ddh=<%=ddh %>&tid=<%=groupKey %>&action=add">�տ�</a>
	<%
	}
	if(!"2".equals(ddConfirm)){
	%>
	  <br>
	  <a href="OpMoneyInit.?ddh=<%=ddh %>&tid=<%=groupKey %>&action=back">�˿�(ȡ������)</a><br>
	<%
	}%>
	  </td>
	</tr>
	<%} %> 
  </table>
</div>	
<div id="page">
	<%String url=request.getContextPath()+"/main/sellerMng/listVisitorMoney.?pageSize=10&pageNO=";%>
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