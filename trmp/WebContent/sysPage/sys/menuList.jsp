<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%@page import="java.net.URLEncoder"%>
<%
	String parentMenuName = rd.getStringByDI("DRMMenus","parentMenuName",0).equals("")?rd.getString("parentMenuName"):rd.getStringByDI("DRMMenus","parentMenuName",0);
	String menuLevel = rd.getStringByDI("DRMMenus","MENULEVEL",0);
	if(menuLevel.equals("")){
		
		menuLevel = rd.getString("MENULEVEL");
		int mLevel = Integer.parseInt(menuLevel);
		mLevel += 1;
		menuLevel = String.valueOf(mLevel);
	}
	String menuName = rd.getStringByDI("DRMMenus","menuName",0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<title>系统菜单列表</title>
<script type="text/javascript">
	function deleteMenu(){
		document.menuForm.action="deleteMenu.";
		document.menuForm.submit();
	}

	function addNewMenu(menuName){
		location.href="queryMenu.?DispMode=N&parentMenuName=<%=parentMenuName%>&menuLevel=<%=menuLevel %>";
	}	

	/**
	*	checkBox的全选和全中选
	*/
	$(function() {	
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
	/**
	 *	end
	 */
//-->
</script>
</head>
<body>
<form name="menuForm" method="post" action="">
  <input type="hidden" name="parentMenuName" value="<%=parentMenuName.equals("0")?"0":parentMenuName%>">
  <div id="lable"><b>菜单列表</b>&nbsp;&nbsp;&nbsp;
    <a href="javascript:addNewMenu();">增加同级新菜单</a> &nbsp;&nbsp;&nbsp;
    <%if(!parentMenuName.equals("0")){%><a href="javascript:history.go(-1);">回到上级菜单列表</a><%}%>
  </div>
  
  <div id="list-table">
	<table class="datas" >
    <tr id="first-tr">
      <td width="5%"><input type="checkbox" id="checkboxMenu">全选</td>
      <td width="20%">菜单显示名称</td>
      <td width="45%">对应链接</td>
      <td width="10%">菜单级别</td>
      <td width="10%">显示顺序</td>
      <td width="10%">操作</td>
    </tr>
    <%int rows=rd.getTableRowsCount("DRMMenus");
	  for(int i=0;i<rows;i++){
	%>
    <tr> 
      <td><input type="checkbox" class="checkboxMenu" name="<%="DRMMenu/menuName["+i+"]"%>" value="<%=rd.getStringByDI("DRmMenus","menuName",i)%>"></td>
      <td><a href="queryMenu.?DispMode=U&DRMMenu/menuName=<%=URLEncoder.encode(rd.getStringByDI("DRmMenus","menuName",i),"GBK")%>"><%=rd.getStringByDI("DRMMenus","menuLabel",i)%></a>&nbsp;</td>
      <td><%=rd.getStringByDI("DRMMenus","funcNO",i)%>&nbsp;</td>
      <td><%=rd.getStringByDI("DRMMenus","MENULEVEL",i)%>&nbsp;</td>
      <td><%=rd.getStringByDI("DRMMenus","dispOrder",i)%>&nbsp;</td>      
      <td><A href="queryMenuList.?DRMMenu/parentMenuName=<%=rd.getStringByDI("DRmMenus","menuName",i)%>&MODULETYPE=<%=rd.getStringByDI("DRmMenus","MODULETYPE",i) %>&MENULEVEL=<%=rd.getStringByDI("DRmMenus","MENULEVEL",i) %>&DRMMENU@ORDERBY=DISPORDER">下级菜单</A>        
      </td>      
    </tr>
    <%}%>
  	<tr>
  	  <td align="left" colspan="6">&nbsp;<input type="button" id="button" name="btnDelete" value="删除选中菜单" onclick="javascript:deleteMenu();"></td>
  	</tr>
  </table>
  </div>
</form>
</body>
</html>	