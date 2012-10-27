<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("sceneryYFZK")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_GROUP_BASE", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_GROUP_BASE", "pageSize"));
		totalPage=(Integer)rd.getAttr("sceneryYFZK", "pagesCount");
		rowsCount = (Integer)rd.getAttr("sceneryYFZK", "rowsCount");
	}
%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/js/dataXML/prototype.js"></script>
<script src="<%=request.getContextPath()%>/js/dataXML/linkage.js"></script>
<script type="text/javascript">
	function findByLike(){
		document.dj_form.action="scenery_yfzk.?TA_GROUP_BASE@pageNO=1&TA_GROUP_BASE@pageSize=10";
		document.dj_form.submit();
		}
</script>
<title>�Ƶ�Ӧ���˿����</title>
</head>
<body>
	<form  name="dj_form" method="post">
<div id="lable"><span>����Ӧ���˿����</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td align="right">�źţ�</td>
			<td><input type="text" name="th" /></td>
			<td>��ֹ���ڣ�</td>
			<td><input type="text" name="bdate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/>��<input type="text" name="edate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
			<td>������</td>
			<td><select name="pro_id" id="pro0" USEDATA="dataSrc0" SUBCLASS="1"></select>
			  	<select name="city_id" id="city0" USEDATA="dataSrc0" SUBCLASS="2"></select>
			</td>
		</tr>
		<tr>
			<td>�������ƣ�</td>
			<td><input name="sceneryName"/></td>
			<td><a href="###" onclick="findByLike();"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>����</td>
			<td>��������</td>
			<td>�ź�</td>
			<td>����</td>
			<td>Ӧ���˿�</td>
		</tr>
		<%
		int zj=0;
		int rows=rd.getTableRowsCount("sceneryYFZK");
		for(int a=0;a<rows;a++){%>
		<tr>
			<td><%=rd.getStringByDI("sceneryYFZK","pro",a) %>&gt;<%=rd.getStringByDI("sceneryYFZK","city",a) %></td>
			<td><%=rd.getStringByDI("sceneryYFZK","cmpny_name",a) %></td>
			<td><%=rd.getStringByDI("sceneryYFZK","g_id",a) %></td>
			<td><%=rd.getStringByDI("sceneryYFZK","persons",a) %></td>
			<td><%=rd.getStringByDI("sceneryYFZK","qdje",a) %></td>
		</tr>
		<%
		zj+=Integer.parseInt(rd.getStringByDI("sceneryYFZK","qdje",a));
		}
		%>
	</table>
</div>
<div id="list-table" style=""><span style="font-size: 14px;">�ܼ�:<font color="red"><%=zj %></font>Ԫ</span></div>
<div id="page">
	<%String url="hotel_yfzk.?TA_GROUP_BASE@pageSize=10&TA_GROUP_BASE@pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rd.getAttr("sceneryYFZK","rowsCount")==null?"0":rd.getAttr("sceneryYFZK","rowsCount") %> ����¼��
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
<script type="text/javascript">
var linkage = new Linkage("dataSrc0", "<%=request.getContextPath()%>/main/data/Hotel.xml");
linkage.init();
</script>