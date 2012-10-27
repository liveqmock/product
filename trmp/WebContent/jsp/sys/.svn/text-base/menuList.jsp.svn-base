<%@include file="../common.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.net.URLEncoder"%>
<%
	String parentMenuName = (String)rd.get("parentMenuName");
%>
<html>
<head>
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">	
<title>系统菜单列表</title>
<script language="javascript" src="../../js/forms.js"></script>
<script language="javascript">
<!--
	function deleteMenu(){
		f.action="deleteMenu.";
		f.submit();
	}

	function toParentLevel(){
		location.href="querySameLevelMenu.?menuName=<%=parentMenuName%>";
	}	
	
	function addNewMenu(){
		location.href="queryMenu.?DispMode=N&parentMenuName=<%=parentMenuName==null?"":parentMenuName%>";
	}	
//-->
</script>
</head>
<body>
<!-- show the menu bar-->
<jsp:include page="/jsp/head.jsp"/>
<menu:menuBar x="12" y="16" height="23" width="100%"/>

<form name="f" method="post" action="">
  <input type="hidden" name="parentMenuName" value="<%=parentMenuName==null?"":parentMenuName%>">
  <p><b>菜单列表</b>&nbsp;&nbsp;<%=parentMenuName==null?"当前为顶级菜单列表":"本级菜单的上级菜单为<font color=red>"+parentMenuName+"</font>" %>&nbsp;&nbsp;&nbsp;
    <a href="javascript:addNewMenu();">增加同级新菜单</a> &nbsp;&nbsp;&nbsp;<%if(parentMenuName!=null){%><a href="javascript:toParentLevel();">回到上级菜单列表</a><%}%>
  </p>
  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="hci">
    <tr class="head"> 
      <td width="10%"> 
        <input type="checkbox" name="sa" value="checkbox" onclick="javascript:ClickOnCheckBox(f.sa,f);">
        全选</td>
      <td width="20%"> 
        <div align="center">菜单ID</div>
      </td>
      <td width="20%"> 
        <div align="center">菜单显示名称</div>
      </td>
      <td width="30%"> 
        <div align="center">对应链接</div>
      </td>
      <td width="10%"> 
        <div align="center">显示顺序</div>
      </td>
      <td width="10%"> 
        <div align="center">&nbsp</div>
      </td>
    </tr>
    <%int rows=rd.getTableRowsCount("DRMMenus");
	  for(int i=0;i<rows;i++){%>
    <tr class="content"> 
      <td width="10%"> 
        <input type="checkbox" name="<%="DRMMenu/menuName["+i+"]"%>" value="<%=rd.getStringByDI("DRmMenus","menuName",i)%>">
      </td>
      <td width="20%"><A href="queryMenu.?DispMode=U&DRMMenu/menuName=<%=URLEncoder.encode(rd.getStringByDI("DRmMenus","menuName",i),"GBK")%>"><%=rd.getStringByDI("DRMMenus","menuName",i)%></A>&nbsp;</td>
      <td width="20%"><%=rd.getStringByDI("DRMMenus","menuLabel",i)%>&nbsp;</td>
      <td width="30%"><%=rd.getStringByDI("DRMMenus","funcNO",i)%>&nbsp;</td>
      <td width="10%"><%=rd.getStringByDI("DRMMenus","dispOrder",i)%>&nbsp;</td>      
      <td width="10%"> 
        <A href="querySubMenu.?parentMenuName=<%=URLEncoder.encode(rd.getStringByDI("DRmMenus","menuName",i),"GBK")%>">下级菜单</A>        
      </td>      
    </tr>
    <%}%>
  </table>  
  <input type="button" name="btnDelete" value="删除选中菜单" onclick="javascript:deleteMenu();" class="button">
</form>
<!-- show the menu-->
<menu:menuItems/>
</body>
</html>	