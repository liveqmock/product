<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("skList")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_ZT_GROUP", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_ZT_GROUP", "pageSize"));
		totalPage=(Integer)rd.getAttr("skList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("skList", "rowsCount");
	}
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<title>ɢ�͵Ǽ��б�</title>
<script type="text/javascript">
	function findByLike(){
		document.skdj_form.action="getAllSKDJ.?TA_ZT_GROUP@pageNO=1&TA_ZT_GROUP@pageSize=10";
		document.skdj_form.submit();
		}
</script>
<style type="text/css">
</style>
</head>
<body >
		<form  name="skdj_form" method="post">
<div id="lable" ><span>ɢ�͵Ǽ��б�</span></div>
<div id="thisSelect-table" >
	<table  >
		<tr>
			<td rowspan="3" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td align="right">�źţ�</td>
			<td><input type="text" name="th" /></td>
			<td align="right">�����ţ�</td>
			<td><input type="text" name="ddh"  /></td>
			<td align="right">�տ����ڣ�</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/>��:
			<input type="text" name="eDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" style="width: 80px;"/>
			</td>
			</tr>
			<tr>
			<td align="right">��·���ƣ�</td>
			<td><input type="text" name="lineN"  /></td>
			<td align="right">��Ӧ�̣�</td>
			<td><input type="text" name="gys"  /></td>
			<td colspan="2" align="right"><a href="###" onclick="findByLike();"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>�ź�</td>
			<td>������</td>
			<td>�տ�����</td>
			<td>�ŵ�����</td>
			<td>��������</td>
			<td>�绰</td>
			<td>��������</td>
			<td>��·</td>
			<td>����</td>
			<td>��׼</td>
			<td>�г̼۸�</td>
			<td>���ճɱ�</td>
			<td>���ռ۸�</td>
			<td>ͬ�м�</td>
			<td>����</td>
		</tr>
	<%int skdjRows=rd.getTableRowsCount("skList");
	for(int a=0;a<skdjRows;a++){
		String orderid = rd.getStringByDI("skList","DDH",a);
	%>	
		<tr>
			<td><%=rd.getStringByDI("skList","id",a) %></td>
			<td><%=orderid %></td>
			<td><%=rd.getStringByDI("skList","regedit_time",a).substring(0,10)%></td>
			<td><%=rd.getStringByDI("skList","cmpny_name",a) %></td>
			<td><%=rd.getStringByDI("skList","visitor_nm",a) %></td>
			<td><%=rd.getStringByDI("skList","tel",a) %></td>
			<td><%=rd.getStringByDI("skList","begin_date",a).substring(0,10) %></td>
			<td><%=rd.getStringByDI("skList","xlmc",a) %></td>
			<td><%=rd.getStringByDI("skList","ysrs",a) %></td>
			<td>
				<%int pRows=rd.getTableRowsCount("priceList");
				for(int p=0;p<pRows;p++) {
					if(orderid.equals(rd.getStringByDI("priceList","orderid",p))){
					%>
					<%=rd.getStringByDI("priceList","dmsm1",p) %>:���м�:<%=rd.getStringByDI("priceList","price_ms",p) %>/ͬ�м�:<%=rd.getStringByDI("priceList","price_th",p) %>/��ͼ�:<%=rd.getStringByDI("priceList","price_zd",p) %><br>
				<%	} 
				}%>
			</td>
			<td><%=rd.getStringByDI("skList","yi_sk",a) %></td>
			<td><%=rd.getStringByDI("skList","cb",a) %></td>
			<td><%=rd.getStringByDI("skList","bx",a) %></td>
			<td>
				<%
				for(int p=0;p<pRows;p++) {
					if(orderid.equals(rd.getStringByDI("priceList","orderid",p))){
					%>
					<%=rd.getStringByDI("priceList","dmsm1",p) %>��<%=rd.getStringByDI("priceList","price_th",p) %><br>
				<%	} 
				}%>
			</td>
			<td><%=rd.getStringByDI("skList","tlr",a) %></td>
		</tr>
		<%} %>
		<input type="hidden" name="userno" value="<%=sd.getString("userno") %>"/>
	</table>
</div>

<div id="page">
	<%String url="getAllHotelPlan.?TA_ZT_GROUP@pageSize=10&TA_ZT_GROUP@pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rd.getAttr("skList","rowsCount")==null?"0":rd.getAttr("skList","rowsCount") %> ����¼��
					ÿҳ <%=pageSize%>��
	</span>
	<span title="��һҳ" class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span title="βҳ" class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage"/> ҳ
	</span>
	<span title="��ת" class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>
</form>
</body>
</html>