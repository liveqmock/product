<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 0;
	if(rd.getTableRowsCount("rsGroupPrintList")>0){
		
	   	pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsGroupPrintList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("rsGroupPrintList", "rowsCount");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>���������б�</title>


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
        height: 500,// 900,
        width: width || 850,
        fullscreen: true,
        show_loading: false,
        callback_fn: callback_fn
    };
    var win = new GB_Window(options);
    return win.show(url);
};
GB_myShow1 = function(caption, url, /* optional */ height, width, callback_fn) {
    var options = {
        caption: caption,
        height: 500,// 900,
        width: width || 800,
        fullscreen: true,
        show_loading: false,
        callback_fn: callback_fn
    };
    var win = new GB_Window(options);
    return win.show(url);
};
</script>

<script type="text/javascript">
function showDjLineDetail(XLID) {
	GB_myShow('��·�г���ϸ','showDjLineDetail.?TA_ZT_LINEMNG/XLID='+XLID);
}

function findByLike(){
	document.djGroupPrint_form.action.name="ztinitGroupPrintList.?pageNO=1&pageSize=10";
	document.djGroupPrint_form.submit();
}
</script>

</head>

<body>
<form  name="djGroupPrint_form" method="post">
<div id="lable"><span>���ݴ�ӡ�б�</span></div>
<div id="thisSelect-table">
	<table  >
		<tr>
			<td rowspan="3" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td align="right">�źţ�</td>
			<td><input type="text" name="groupId" /></td>
			<td align="right">�������ڣ�</td>
			<td><input type="text" name="bDate" onFocus="WdatePicker({isShowClear:false,readOnly:true});" /></td>
			<td><a href="###" onclick="findByLike()"><img alt="����" src="/trm/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td width="15%">�ź�</td>
			<td width="35%">��·����</td>
			<td width="15%">��������</td>
			<td width="15%">��������</td>
			<td width="20%">����</td>
		</tr>
		<%
			int rows=rd.getTableRowsCount("rsGroupPrintList");
			for(int i=0;i<rows;i++){
				String ID=rd.getStringByDI("rsGroupPrintList","ID",i);
				String XLID = rd.getStringByDI("rsGroupPrintList","LINE_ID",i);
				String XLMC=rd.getStringByDI("rsGroupPrintList","XLMC",i);
				String BEGIN_DATE = rd.getStringByDI("rsGroupPrintList","BEGIN_DATE",i);
				String END_DATE = rd.getStringByDI("rsGroupPrintList","END_DATE",i);
				String STATE = rd.getStringByDI("rsGroupPrintList","STATE",i);
		%>
		<tr>
			<td><%=ID %></td>
			<td>
			  <img alt="�ȵ���·" src="<%=request.getContextPath()%>/images/hot3.gif"/>&nbsp;
			  <a href="###" onclick="" title="<%=XLMC %>"><%=XLMC.length()<=20?XLMC:XLMC.substring(0,20)+"..."  %></a>
			</td>
			<td><%=BEGIN_DATE %></td>
			<td><%=END_DATE %></td>
			<td align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="###" onclick="return GB_myShow('','<%=request.getContextPath() %>/main/ztPlans/printGroupPlan/ztshowGroupPrint.?TA_ZT_GROUP/ID=<%=ID %>&TA_TDJDXXZJB_ZT/TID=<%=ID %>&id=<%=ID %>','1000','850')"><img alt="" src="<%=request.getContextPath() %>/images/Print.gif">&nbsp;[��ӡ��������]</a><br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="###" onclick="return GB_myShow('','printYkmd.?groupID=<%=ID %>','1000','850');"><img alt="" src="<%=request.getContextPath() %>/images/Print.gif">&nbsp;[��ӡ�ο�����]</a><br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="###" onclick="return GB_myShow1('','viewTravelList.?groupId=<%=ID %>&lineId=<%=XLID %>');"><img alt="" src="<%=request.getContextPath() %>/images/Print.gif">&nbsp;[��ӡ�г̵�]</a><br>
			</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/main/ztPlans/djQueryGroupPrintList.?pageSize=10&pageNO=";%>
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