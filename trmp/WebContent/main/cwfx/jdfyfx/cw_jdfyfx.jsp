<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("jdInfo")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_GROUP_BASE", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_GROUP_BASE", "pageSize"));
		totalPage=(Integer)rd.getAttr("jdInfo", "pagesCount");
		rowsCount = (Integer)rd.getAttr("jdInfo", "rowsCount");
	}
%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>

<script type="text/javascript">
	function findByLike(){
		document.jd_form.action="cw_jdfyfx.?TA_GROUP_BASE@pageNO=1&TA_GROUP_BASE@pageSize=10";
		document.jd_form.submit();
		}
</script>
<title>�ӵ���÷���</title>
</head>
<body>
	<form  name="jd_form" method="post">
<div id="lable"><span>�ӵ���÷���</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td align="right">�źţ�</td>
			<td><input type="text" name="th" /></td>
			<td>��ֹ���ڣ�</td>
			<td><input type="text" name="bdate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/>��<input type="text" name="edate" onFocus="WdatePicker({isShowClear:false,readOnly:true});"/></td>
			<td><a href="###" onclick="findByLike();"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>�ź�</td>
			<td>�ӵ�����</td>
			<td>���Ѷ�</td>
			<td>����</td>
			<td>��˾���</td>
			<td>�������</td>
			<td>˾�����</td>
		</tr>
		<%
		int rows=rd.getTableRowsCount("jdInfo");
		int zj=0;
		for(int a=0;a<rows;a++){%>
		<tr>
			<td><%=rd.getStringByDI("jdInfo","g_id",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","pro",a) %>&gt;&gt;<%=rd.getStringByDI("jdInfo","city",a) %>&gt;&gt;<%=rd.getStringByDI("jdInfo","cmpny_name",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","xfe",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","rs",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","yjgs",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","dytc",a) %></td>
			<td><%=rd.getStringByDI("jdInfo","sjtc",a) %></td>
		</tr>
		<%
		zj+=Integer.parseInt(rd.getStringByDI("jdInfo","xfe",a));
		}
		%>
	</table>
</div>
<div id="list-table" style=""><span style="font-size: 14px;">�ܼ�:<font color="red"><%=zj%></font>Ԫ</span></div>
<div id="page">
	<%String url="cw_jdfyfx.?TA_GROUP_BASE@pageSize=10&TA_GROUP_BASE@pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rd.getAttr("jdInfo","rowsCount")==null?"0":rd.getAttr("jdInfo","rowsCount") %> ����¼��
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
