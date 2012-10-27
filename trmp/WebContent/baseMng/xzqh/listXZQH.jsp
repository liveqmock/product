<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("XZQHs")>0){
     pageNO=Integer.parseInt((String)rd.getAttr("XZQH", "pageNO"));
	 pageSize=Integer.parseInt((String)rd.getAttr("XZQH", "pageSize"));
		totalPage=(Integer)rd.getAttr("XZQHs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("XZQHs", "rowsCount");
	}

	int layer = 1;
	String layerStr = rd.getString("layer");
	if(!"0".equals(rd.getString("PID")) && !"".equals(layerStr)) {
		
		layer = Integer.parseInt(layerStr) + 1;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/public-jq.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jqModal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>


<link rel="stylesheet" href="<%=request.getContextPath()%>/js/thickbox/thickbox.css" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/thickbox/thickbox.js"></script>

<title>��������ά��</title>
<script type="text/javascript">
$(function(){
	$('[type=checkbox]').click(function (){
		var checkClass = $(this).attr("class");
		if(checkClass){
			if($("."+checkClass).is(":checked")){
				$("#"+checkClass).attr("checked","checked");
			}else{
				$("#"+checkClass).removeAttr("checked");
			}
		}
		var checkID = $(this).attr("id");
		if(checkID){
			if($("#"+checkID).is(":checked")){
				$("."+checkID).each(function (){
					$(this).attr("checked","checked");
				});
			}else{
				$("."+checkID).each(function (){
					$(this).removeAttr("checked");
				});
			}
		}
	});
});
function enableInput(){
	var rs = false;
	var objs = document.xzqh_list.elements;
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
		if(confirm('�˲������޷��ָ�,ȷ��ɾ����ѡ����?')){
			document.xzqh_list.action="selectDel.";
			document.xzqh_list.submit();
			}
		}
</script>
</head>
<body>
<form name="xzqh_list" method="post">
<div id="lable"><span>��������ά��</span></div>
<div id="ex3a" class="jqmDialog">
	<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC jqDrag">��������ѯ</div></div></div>
	<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">
	<div class="jqmdMSG">
	�������ƣ�<input type="text" name="ta_travelagency/CMPNY_NAME" id="CMPNY_NAME" class="text_input"/>&nbsp;
		 <input type="hidden" name="ta_travelagency/CMPNY_NAME[0]@relation" class="text_input" value="like" id="cmpnyNameOld"/>
		  <input type="button" value="��ѯ" class="bnt" onClick="findTravelagency();"/>
	</div>
	
	</div></div></div>
	<input type="image" src="<%=request.getContextPath() %>/images/if-select-img/close.gif" class="jqmdX jqmClose" />
</div>
	<div id="top-select">
		<div class="select-div" onclick="TB_show('������������','<%=request.getContextPath()%>/baseMng/xzqh/add.jsp?pid=<%=rd.getString("PID") %>&layer=<%=layer %>*TB_iframe=true&height=300&width=350','');"><span id="add-icon"></span>
		 <span class="text">���</span>
		</div>
		<div class="select-div" onclick="del_select()">
			<span id="del-icon"></span> 
			<span class="text">ɾ��</span>
		</div>
	</div>

<div id="list-table">
	<input type="hidden" value="<%=rd.getString("PID")%>" name="PID"/>
	<table class="datas" >
		<tr id="first-tr">
			<td id="selectAll">ȫѡ<input type="checkbox" id="checkboxTop"></input></td>
			<td>����</td>
			<td>����</td>
		</tr>
		<%int cityRows=rd.getTableRowsCount("XZQHs");for(int x=0;x<cityRows;x++){%>
		<tr>
			<td><input type="checkbox" id="checkboxEle" class="checkboxTop" name="XZQH/CHECKBOX[<%=x%>]" value="<%=rd.getStringByDI("XZQHs","ID",x) %>"></input></td>
			<td><%=rd.getStringByDI("XZQHs","name",x)%></td>
			<td>
			<a href="###" onclick="TB_show('�༭��������','<%=request.getContextPath() %>/getLandkreiseById.?XZQH/id=<%=rd.getStringByDI("XZQHs","ID",x) %>*TB_iframe=true&height=300&width=350','');">
				<img alt="�༭" src="<%=request.getContextPath()%>/images/edit.gif">&nbsp;[�޸�]
			</a>-
			<a href="<%=request.getContextPath()%>/delLandkreise.?XZQH/ID=<%=rd.getStringByDI("XZQHs","ID",x) %>&pid=<%=rd.getString("PID") %>" onclick="return confirm('�˲������޷��ָ�,ȷ��ɾ����?')">
				<img alt="ɾ��" src="<%=request.getContextPath()%>/images/del-icon.gif">&nbsp;[ɾ��]
			</a>
		</td>
		</tr>
		<%} %>
	</table>
</div>
<div id="page">
	<%String url=request.getContextPath()+"/baseMng/xzqh/listLandkreise.?XZQH@pageSize=10&XZQH/PID="+rd.getString("PID")+"&XZQH@pageNO=";%>
	<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					��<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> ҳ��
					��<%=rd.getAttr("XZQHs","rowsCount")==null?"0":rd.getAttr("XZQHs","rowsCount") %> ����¼��
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



