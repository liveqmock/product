<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common.jsp"%>
<%	
	/*page support;*/
	int rows=rd.getTableRowsCount("HREMPLOYEEs");;
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rows>0){
		pageNO=Integer.parseInt((String)rd.getAttr("HREMPLOYEE", "pageNO"));
		pageSize=Integer.parseInt((String)rd.getAttr("HREMPLOYEE", "pageSize"));
		totalPage=(Integer)rd.getAttr("HREMPLOYEEs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("HREMPLOYEEs", "rowsCount");
		
	}
%>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/menu.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<title>Ա��ͨѶ¼</title>
<script language="javascript">
function queryByLike(){
	document.empForm.action="queryEmpByLike.?DispMode=U&ORGID=<%=rd.getStringByDI("HREMPLOYEE","orgid",0)%>&HREMPLOYEE@pageNO=1&HREMPLOYEE@pageSize=10";
	document.empForm.submit();
}

function selectAndClose(id,org,nm) {
	$("#userFrom",window.parent.parent.parent.document).val(id);
	//parent.parent.parent.document.getElementById("userFromNm").value = org+"-"+nm;
	$("#userFromNm",window.parent.parent.parent.document).val(org);
	$("#username",window.parent.parent.parent.document).val(nm);
	parent.parent.parent.GB_hide(); 
}
</script> 
<style type="text/css">
#page{ height: 25px;}
#page span{float: left;margin-top: 5px;}
.first-page{background: url(<%=request.getContextPath()%>/images/first-page.gif) no-repeat;width: 16px;height: 16px;cursor: pointer}
.prev-page{background:url(<%=request.getContextPath()%>/images/prev-page.gif) no-repeat;width: 16px;height: 16px;cursor: pointer}
.go-to-page{margin-left: 5px;}
.go-to-page input{width: 30px;height: 18px;}
.next-page{background:url(<%=request.getContextPath()%>/images/next-page.gif) no-repeat;width: 16px;height: 16px;cursor: pointer}
.last-page{background:url(<%=request.getContextPath()%>/images/last-page.gif) no-repeat;width: 16px;height: 16px;cursor: pointer}
.go-to-page-icon{background:url(<%=request.getContextPath()%>/images/go-to-page.gif) no-repeat;width: 14px;height: 14px;margin-left: 3px;cursor: pointer;}
</style>
</head>

<body>
<form name="empForm" method="post" >
Ա����ţ� 	<input type="text" name="empid" size="10" maxlength="10" >
Ա��������	<input type="text" name="empname" size="16" maxlength="16" >
			<input type="hidden" name="orgid" value="<%=rd.getStringByDI("HREMPLOYEE","orgid",0)%>">
  <input type="button" name="btnSelect" value="��ѯ" class="button" onclick="queryByLike()">
  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="hci">
    <tr class="head" > 
      <td width="10%" > 
        <div align="center">���</div>
      </td>
      <td width="12%"> 
        <div align="center">����</div>
      </td>
      <td width="10%">
        <div align="center">�Ա�</div>
      </td>
      <td width="21%"> 
        <div align="center">������֯</div>
      </td>
      <td width="12%"> 
        <div align="center">�绰</div>
      </td>
      <td width="12%">�ƶ��绰</td>
      <td>����</td>
    </tr>
    <%for(int i=0;i<rows;i++){%>
    <tr class="content" > 
      <td width="10%"><%=rd.getStringByDI("HREMPLOYEEs","empID",i)%>&nbsp;</td>
      <td width="12%">
      <a href="###" onclick="selectAndClose('<%=rd.getStringByDI("HREMPLOYEEs","empID",i)%>','<%=rd.getStringByDI("hrorganizations","Name",0)%>','<%=rd.getStringByDI("HREMPLOYEEs","empname",i)%>');">
      <%=rd.getStringByDI("HREMPLOYEEs","empname",i)%>&nbsp;
      </a>
      </td>
      <td width="10%"><%if("1".equals(rd.getStringByDI("HREMPLOYEEs","sex",i)))out.print("��");else{out.print("Ů");}%></td>
      <td width="21%"><%=rd.getStringByDI("hrorganizations","Name",0)%>|<%=rd.getStringByDI("HREMPLOYEEs","orgName",i)%>&nbsp;</td>
      <td width="12%"><%=rd.getStringByDI("HREMPLOYEEs","emptel",i)%>&nbsp;</td>
      <td width="12%"><%=rd.getStringByDI("HREMPLOYEEs","sjhm",i)%>&nbsp;</td>
      <td><a href="<%=request.getContextPath() %>/delEmp.?HREMPLOYEE/EMPID=<%=rd.getStringByDI("HREMPLOYEEs","EMPID",i) %>&orgid=<%=rd.getStringByDI("HREMPLOYEEs","orgid",i)%>&dispMode=U" onclick="return confirm('�˲������޷��ָ�,ȷ��ɾ����?')">ɾ��</a>-<a href="<%=request.getContextPath() %>/queryEmp.?HREMPLOYEE/EMPID=<%=rd.getStringByDI("HREMPLOYEEs","EMPID",i) %>&dispMode=U">�޸�</a></td>
    </tr>
    <%}%>
  </table>  
<div id="page">
	<%String url="getContactListForUserAdd.?DispMode=U&HREMPLOYEE/ORGID="+rd.getStringByDI("HREMPLOYEE","orgid",0)+"&HREMPLOYEE@pageSize=10&HREMPLOYEE@pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rd.getAttr("HREMPLOYEEs","rowsCount")==null?"0":rd.getAttr("HREMPLOYEEs","rowsCount") %> ����¼��
					ÿҳ <%=pageSize%>��
	</span>
	<span title="��һҳ" class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span title="βҳ" class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		��ת���ڣ�<input type="text" id="gotopage" /> ҳ
	</span>
	<span title="��ת" class="go-to-page-icon"  onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>  
</form>
</body>
</html>	