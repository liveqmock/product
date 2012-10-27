<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    <%@include file="/common.jsp" %>
    <%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("TA_TICKETs")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("TA_TICKET", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("TA_TICKET", "pageSize"));
		totalPage=(Integer)rd.getAttr("TA_TICKETs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("TA_TICKETs", "rowsCount");
	}
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/selectAll.js"></script>
<script type="text/javascript">
function enableInput(){
	var rs = false;
	var objs = document.ticket_form.elements;
	if(objs==null){
		return false;
	}
	var len=objs.length;
	var checkbox;
	for(var i=0;i<len;i++){
		if(objs.item(i).tagName=="INPUT" && objs.item(i).type=="checkbox" && objs.item(i).name!=''){
			checkbox=objs.item(i); 
			//var ts=checkbox.name.split('[')[1];
			//var index=(ts.substring(0,ts.length-1));
			if(checkbox.checked){
				//document.getElementById("XYJBXXB/SFZMHM["+index+"]").disabled=false;
				rs = true;
			}
		}
	}
	return rs;
}
function del_select(){
	var rst= enableInput();
	if(rst==false){
		return false;
	}
	if(confirm('�˲������޷��ָ�,ȷ��ɾ������ѡ��?')){
		document.ticket_form.action="batchDelTicket.?";
		document.ticket_form.submit();
	}
}
function getTicketByName(){
	document.ticket_form.action="getTicketByName.?TA_TICKET@pageNO=1&TA_TICKET@pageSize=10";
	document.ticket_form.submit();
}
//���Ʊ����Ϣ
function newTicket(url){
	window.location.href=url;
}
//�޸�Ʊ����Ϣ
function editTicket(url){
	window.location.href=url;
}
</script>
<title>Ʊ�������Ϣ</title>
</head>
<body>
<form  name="ticket_form" method="post">
<div id="lable"><span>Ʊ�������Ϣά��</span></div>
	<div id="top-select">
	<div class="select-div" >
	  <span class="text" id="select-condition">��ѯ����</span>
	</div>
		<div class="select-div" onclick="newTicket('<%=request.getContextPath()%>/to_addTicket.?DMSM/KHYH=8&city_id=<%=rd.getStringByDI("TA_TICKET","CITY_ID",0) %>')"><span id="add-icon"></span>
		 <span class="text">���</span>
		</div>
		<div class="select-div" onclick="del_select()"><span id="del-icon"></span> <span
			class="text">ɾ��</span>
		</div>
	</div>
	
<div id="ex3a" class="jqmDialog">
	<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC jqDrag">��������ѯ</div></div></div>
	<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">
	<div class="jqmdMSG">
	���������ƣ�<input type="text" name="name" />&nbsp;
			  <input type="button" value="��ѯ"  onClick="getTicketByName();"/>
	</div>
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>
	
	

<div id="list-table">
	<input type="hidden" value="<%=rd.getStringByDI("TA_TICKET","CITY_ID",0)%>" name="city_id"/>
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll"><input type="checkbox" id="checkboxTop"></input></td>
			<td>����������</td>
			<td>��˾��ַ</td>
			<td>ҵ��Ա-�ֻ�</td>
			<td>�ܸ�����-�ֻ�</td>
			<td>����</td>
		</tr>
		<%int ticketRows=rd.getTableRowsCount("TA_TICKETs");for(int h=0;h<ticketRows;h++){%>
		<tr>
			<td><input type="checkbox" id="checkboxEle" name="TICKET/CHECKBOX[<%=h%>]" value="<%=rd.getStringByDI("TA_TICKETs","TICKET_ID",h) %>"></input></td>
			<td><%=rd.getStringByDI("TA_TICKETs","CMPNY_NAME",h)%></td>
			<td><%=rd.getStringByDI("TA_TICKETs","CMPNY_ADDRESS",h)%></td>
			<td><%=rd.getStringByDI("TA_TICKETs","BUSINESS_NAME",h)%>-<%=rd.getStringByDI("TA_TICKETs","BUSINESS_MOBILE",h)%></td>
			<td><%=rd.getStringByDI("TA_TICKETs","CHIEF_NAME",h)%>-<%=rd.getStringByDI("TA_TICKETs","CHIEF_MOBILE",h)%></td>
			<td>
				<a href="###" onclick="editTicket('<%=request.getContextPath()%>/getTicketById.?TA_TICKET/TICKET_ID=<%=rd.getStringByDI("TA_TICKETs","TICKET_ID",h)%>&DMSM/KHYH=8&TA_TICKET/CITY_ID=<%=rd.getStringByDI("TA_TICKETs","CITY_ID",0) %>')">
					<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[�޸�]
				</a>
				-
				<a href="<%=request.getContextPath() %>/delTicket.?id=<%=rd.getStringByDI("TA_TICKETs","TICKET_ID",h)%>&city_id=<%=rd.getStringByDI("TA_TICKETs","city_id",0) %>" onclick="return confirm('�˲������޷��ָ�,ȷ��ɾ����?')">
				<img alt="ɾ��" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[ɾ��]
				</a>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/baseMng/ticket/getAllTicket.?TA_TICKET/CITY_ID="+rd.getStringByDI("TA_TICKETs","CITY_ID",0)+"&ywlb="+rd.getString("ywlb")+"&TA_TICKET@pageSize=10&TA_TICKET@pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rd.getAttr("TA_TICKETs","rowsCount")==null?"0":rd.getAttr("TA_TICKETs","rowsCount") %> ����¼��
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