<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common.jsp"%>
<%
	//URL when click on a Organization node in the tree;
	String url = request.getParameter("URL");
	//if not specified,set default value;
	if(url==null){
		url="queryOrg.?DispMode=U&";
	}
	//the target window that will display the above URL;		
	String target=request.getParameter("target");
	//if not specified,set default value '_top';
	if(target==null){
		target="_top";
	}
%><head>
<link rel="StyleSheet" href="<%=request.getContextPath() %>/css/tree.css" type="text/css">
<link rel="StyleSheet" href="<%=request.getContextPath() %>/css/style.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath() %>/js/tree.js"></script>

<script language="JavaScript">    
	/**   
	*根据传入的id显示右键菜单   
	*/    
	function showMenu(id) {

		menuForm.id.value = id;
		if("" == id)    
		{    
			popMenu(itemMenu,80,"1000");    
		}    
		else    
		{    
			popMenu(itemMenu,80,"1111");    
		}    
			event.returnValue=false;    
			event.cancelBubble=true;    
			return false;    
	}    
	/**   
	*显示弹出菜单   
	*menuDiv:右键菜单的内容   
	*width:行显示的宽度   
	*rowControlString:行控制字符串，0表示不显示，1表示显示，如“101”，则表示第1、3行显示，第2行不显示   
	*/    
	function popMenu(menuDiv,width,rowControlString)    
	{    
		//创建弹出菜单    
		var pop=window.createPopup();    
		//设置弹出菜单的内容    
		pop.document.body.innerHTML=menuDiv.innerHTML;    
		var rowObjs=pop.document.body.all[0].rows;   
		//获得弹出菜单的行数    
		var rowCount=rowObjs.length;    
		//循环设置每行的属性    
		for(var i=0;i<rowObjs.length;i++)    
		{    
			//如果设置该行不显示，则行数减一    
			var hide=rowControlString.charAt(i)!='1';    
			if(hide){    
				rowCount--;    
			}    
			//设置是否显示该行    
			rowObjs[i].style.display=(hide)?"none":"";    
			//设置鼠标滑入该行时的效果    
			rowObjs[i].cells[0].onmouseover=function()    
			{    
				this.style.background="C3DFEA";    
				this.style.color="red";    
			}    
			//设置鼠标滑出该行时的效果    
			rowObjs[i].cells[0].onmouseout=function(){    
				this.style.background="#D0D0D0";    
				this.style.color="#000000";    
			}    
		}    
		//屏蔽菜单的菜单    
		pop.document.oncontextmenu=function()    
		{    
				return false;    
		}    
		//选择右键菜单的一项后，菜单隐藏    
		pop.document.onclick=function()    
		{    
				pop.hide();    
		}    
		//显示菜单    
		pop.show(event.clientX-1,event.clientY,width,rowCount*20,document.body); 
		return true;    
	}    
	function create() {

		var orgID = document.getElementById("id").value;
		var frameid = parent.document.getElementById("orgRight"); 
		frameid.src = "queryOrg.?DispMode=N&HROrganization/orgID="+orgID;
	}
	function update()    
	{    
		alert("update" + menuForm.id.value + "!");    
	}    
	function del() {    
		var orgID = document.getElementById("id").value;
		window.location.href="deleteOrg.?HROrganization/orgID="+orgID;
	}    
	function select()    
	{    
		alert("select" + menuForm.id.value + "!");    
	}    
	function clickMenu()    
	{    
		alert("you click a menu!");    
	}    
</script>


<link rel="StyleSheet" href="<%=request.getContextPath() %>/js/dtree/dtree.css" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/dtree/dtree.js"></script>
</head>



<div class="dtree">
	<script type="text/javascript">
		<!--

		d = new dTree('d','<%=request.getContextPath() %>/js/dtree/img/');
		d.add('0','-1','<a href="<%=url%>HROrganization/orgID=0" oncontextmenu=showMenu("0") target="orgRight">组织机构树</a>','','','');
	<%
	int topRows = rd.getTableRowsCount("rsTopOrg");
	int secRows = rd.getTableRowsCount("rsSecOrg");
	for(int i=0;i<topRows;i++) {
		String orgID = rd.getStringByDI("rsTopOrg","orgID",i);
		String parentOrgID =  rd.getStringByDI("rsTopOrg","parent_orgID",i);
		String orgName = rd.getStringByDI("rsTopOrg","name",i);
	%>
		d.add(<%=orgID %>
		,<%=parentOrgID %>
		,'<a href="<%=url%>HROrganization/orgID=<%=orgID%>" oncontextmenu=showMenu("<%=orgID%>") target="orgRight"><%=orgName%></a>','','','');
	<%
		for(int j=0;j<secRows;j++){
			String orgIDSec = rd.getStringByDI("rsSecOrg","orgID",j);
			String orgParentOrgIDSec = rd.getStringByDI("rsSecOrg","parent_orgID",j);
			String orgNameSec = rd.getStringByDI("rsSecOrg","name",j);
			if(orgID.equals(orgParentOrgIDSec)){
	%>
	d.add(<%=orgIDSec %>
	,<%=orgParentOrgIDSec %>
	,'<a href="<%=url%>HROrganization/orgID=<%=orgIDSec%>" oncontextmenu=showMenu("<%=orgIDSec%>") target="orgRight"><%=orgNameSec%></a>','','','');
	<%
				for(int k=0;k<secRows;k++){
					String orgIDLast = rd.getStringByDI("rsSecOrg","orgID",k);
					String orgParentOrgIDLast = rd.getStringByDI("rsSecOrg","parent_orgID",k);
					String orgNameLast = rd.getStringByDI("rsSecOrg","name",k);
					if(orgIDSec.equals(orgParentOrgIDLast)){
	%>
	d.add(<%=orgIDLast %>
	,<%=orgParentOrgIDLast %>
	,'<a href="<%=url%>HROrganization/orgID=<%=orgIDLast%>" oncontextmenu=showMenu("<%=orgIDLast%>") target="orgRight"><%=orgNameLast%></a>','','','');
	<%
					}
				}
			}
		}
	} %>
		d.config.target="orgRight";
		document.write(d);

		//-->
	</script>
	

</div>
<form name = "menuForm">    
     <!--隐藏框，用来保存选择的菜单的id值-->
     <input type="hidden" name="id" value="">
</form>
<!-- 右键开始 -->    
<div id="itemMenu" style="display:none">    
  <table border="1" width="100%" bgcolor="#D0D0D0" style=" border:#000000 thin;" cellspacing="0" cellpadding="0">    
    <tr>    
      <td style="cursor:hand;font-size:12px" align="center" height="20" onclick="parent.create();">新增</td>    
    </tr>      
    <tr>    
      <td style="cursor:hand;font-size:12px" align="center" height="20" onclick="parent.del();">删除</td>    
    </tr><!-- 
    <tr>    
      <td style="cursor:hand;font-size:12px" align="center" onclick="" height="20">更新</td>    
    </tr>-->
  </table>    
</div>    
<!-- 右键结束 -->