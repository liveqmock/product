<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
 <%@include file="/common.jsp" %>
 <%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("queryAllPWList")>0){
		pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("queryAllPWList", "pagesCount");
		rowsCount = (Integer)rd.getAttr("queryAllPWList", "rowsCount");
	}
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�ؽӲ������</title>
<link href="<%=request.getContextPath() %>/css/treeAndAllCss.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/datepicker/WdatePicker.js"></script>
<title>Ʊ��ǩ���б�</title>


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
        height: 500,// || 500,
        width: width || 700,
        fullscreen: true,
        show_loading: false,
        callback_fn: callback_fn
    }
    var win = new GB_Window(options);
    return win.show(url);
}
</script>





<script type="text/javascript">

function findByLike(){
	document.dj_guide_form.Action="AllPWList.?&pageSize=10&pageNO=1 ";
	document.dj_guide_form.submit();
}
</script>
</head>

<body>
<form  name="dj_guide_form" method="post">
<div id="lable"><span>Ʊ��ǩ���б�</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>��<br/><br/>��</span></td>
			<td align="right">�źţ�</td>
			<td><input type="text" name="groupID" /></td>
			<td align="right">���������ƣ�</td>
			<td><input type="text"  name="dgdmc"/></td>
			<td><a href="###" onclick="findByLike()"><img alt="����" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
		</tr>
	</table>
</div>
<div id="list-table">
	<table class="datas" >
		<tr id="first-tr">
			<td>�ź�</td>
			<td>����������</td>
			<td>�������ַ</td>
			<td>ǩ���ϼ�</td>
			<td>������</td>
			<td>����</td>
		</tr>
		<%
		int rows = rd.getTableRowsCount("queryAllPWList");
		for(int i=0;i<rows;i++) {
			String tid = rd.getStringByDI("queryAllPWList","id",i);//�ź�
			String id = rd.getStringByDI("queryAllPWList","dgd",i);//������ID
			String dgdm = rd.getStringByDI("queryAllPWList","cmpny_name",i);//��������
			String dgddz = rd.getStringByDI("queryAllPWList","cmpny_address",i);//�������ַ
			String qdxj = rd.getStringByDI("queryAllPWList","qdxj",i);//ǩ��С��
			String hkje=rd.getStringByDI("queryAllPWList","hkje",i);//������
		%>
		<tr>
            <td><%=tid %></td>
	  		<td><%=dgdm %></td>
	  		<td><%=dgddz %></td>
	  		<td><%=qdxj %>Ԫ</td>
	  		<td><%=hkje %>Ԫ</td>	  
	  		<td>
	  			<a href="###" onclick="javascript:GB_myShow('','/trm/maindj/financeMng/getBXPWByTid.?TA_DJ_QDQKJL4PW/DGDID=<%=id %>&TA_DJ_QDQKJL4PW/TID=<%=tid %>','400','400','')" >
           		<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">[����ǩ��]&nbsp;</a>
           		<a href="###" onclick="javascript:GB_myShow('','/trm/maindj/financeMng/queryPWlist.?TA_DJ_QDQKJL4PW/DGDID=<%=id %>&TA_DJ_QDQKJL4PW/TID=<%=tid %>','400','600','')" >
           		<img alt="�鿴" src="<%=request.getContextPath()%>/images/edit.gif">[�鿴����嵥]&nbsp;</a>
            </td>
         </tr>	
	<%} %>		
			
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/maindj/financeMng/AllPWList.?pageSize=10&pageNO=";%>
	<span title="��ҳ" class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span title="��һҳ" class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rd.getAttr("queryAllPWList","rowsCount")==null?"0":rd.getAttr("queryAllPWList","rowsCount") %> ����¼��
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