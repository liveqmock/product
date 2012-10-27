<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="/common.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	int pageNo=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	int tRows = rd.getTableRowsCount("TA_ZT_INSURANCEs");
	if(tRows > 0){
		pageNo=Integer.parseInt((String)rd.getString("pageNo"));
	 	pageSize=Integer.parseInt((String)rd.getString("pageSize"));
	 	totalPage=(Integer)rd.getAttr("TA_ZT_INSURANCEs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_ZT_INSURANCEs", "rowsCount");
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>���յ����б�</title>
<link href="<%=request.getContextPath()%>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>

<script type="text/javascript">

function findByLike(){
	document.insuranceFileList.submit();
}

function newInsurance(){

	window.location.href="initInsuranceFile.";
}
function editInsurance(url){

	window.location.href=url;
}
</script>
</head>
<body>
<form name="insuranceFileList" action="initInsuranceFileList.?pageNo=1&pageSize=10"  method="post">
<div id="lable">
  <span>���յ����б�</span>
</div>
<div id="top-select">
  <div class="select-div" onclick="newInsurance();"><span id="add-icon"></span>
	<span class="text">��ӱ���</span>
  </div>
</div>
<div id="thisSelect-table">
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td align="right">�������ƣ�</td>
			<td><input type="text" name="BXLBMC" /></td>
			<td><a href="###" onclick="findByLike();"><img alt="����" src="<%=request.getContextPath()%>/images/search.gif"/></a></td>
		</tr>
	</table>
</div>

<div id="list-table">
<table class="datas">
	<tr id="first-tr">
        <td>��������</td>
		<td>���ճɱ�</td>
		<td>����۸�</td>
		<td>�ɱ���ʽ</td>
        <td >����</td>
	</tr>
	<%
		
		for(int i = 0; i < tRows; i++){
			String insuranceID = rd.getStringByDI("TA_ZT_INSURANCEs", "ID", i);
	%>
		<tr>
	  		<td><%=rd.getStringByDI("TA_ZT_INSURANCEs", "BXLBMC", i) %></td>
			<td><%=rd.getStringByDI("TA_ZT_INSURANCEs", "BXCB", i) %></td>
			<td><%=rd.getStringByDI("TA_ZT_INSURANCEs", "JYJG", i) %></td>
			<td><%if("1".equals(rd.getStringByDI("TA_ZT_INSURANCEs", "CBFS", i))){out.print("�������");}else{out.print("��ʵ�ʳɱ�����");} %></td>
			<td>
				<a href="###"onclick="editInsurance('<%=request.getContextPath()%>/baseMng/insuranceFile/initInsuranceFile.?TA_ZT_INSURANCE/ID=<%=insuranceID %>')" >
				<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif"/>&nbsp;[�޸�]</a>
				<a href="<%=request.getContextPath()%>/baseMng/insuranceFile/delInsuranceFile.?TA_ZT_INSURANCE/ID=<%=insuranceID %>" 
					onclick="return confirm('�˲�����ɾ����˱������������Ϣ,�����޷��ָ�,ȷ��ɾ����?')">
				<img alt="ɾ��" src="<%=request.getContextPath()%>/images/del-icon.gif"/>&nbsp;[ɾ��]</a>
            </td>
         </tr>	
	<%} %>	
      
</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/baseMng/insuranceFile/initInsuranceFileList.?pageSize=10&pageNo=";%>
	<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNo,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span class="prev-page" onclick='query("<%=pageNo-1<1?1:pageNo-1 %>","P","<%=(int)Math.min(pageNo,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNo,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rowsCount %> ����¼��
					ÿҳ <%=pageSize%>��
	</span>
	<span class="next-page" onclick='query("<%=pageNo+1>totalPage?totalPage:pageNo+1 %>","N","<%=(int)Math.min(pageNo,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNo,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage"/> ҳ
	</span>
	<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNo,totalPage)%>","<%=url %>")'></span>
</div>
</form>
</body>
</html>
