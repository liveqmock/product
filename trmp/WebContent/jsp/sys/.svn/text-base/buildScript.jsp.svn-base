<%@page contentType="text/html;charset=GBK"%>
<%@include file="/jsp/common.jsp"%>
<%
  String daoName=request.getParameter("daoName");
%>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=GBK">
      <link rel="stylesheet" href="../../css/style.css" type="text/css">
      <link rel="stylesheet" href="../../css/menu.css" type="text/css">
      <title>为数据库表生成SQL初始化脚本</title>
      <script src="../../js/selectOps.js" type="text/javascript"></script>
      <script type="text/javascript">

        function add(){
        	moveSelectedOptions(document.forms[0].elements["tables[]"],document.forms[0].elements["tableName[]"]);
        	
        	var tables=document.getElementById("toSelect");
        	for(i=0;i<tables.options.length;i++){
				if(tables.options.item[i].selected=true){
					 var oOption = document.createElement("OPTION");
					  
					  oOption.text=tables.options.item[i].text;
					  oOption.value=tables.options.item[i].value;
					  oOption.selected=true;
					  tables.add(oOption,null);
					}
            	}
        	
        }
        
        function del(){
        	moveSelectedOptions(document.forms[0].elements["tables[]"],document.forms[0].elements["tables[]"]);
        }
                
        function validate(){
        	if(optionsCount(document.forms[0].getElementsByID("tables[]"))<1){
        		alert("对不起，您需要选择至少一个表!");
        		return false;
        	}else{
        		selectAllOptions(document.forms[0].getElementsByID("tables[]"));
        		return true;
        	}
        }

      </script>
    </head>
    <body>
  <jsp:include page="/jsp/head.jsp"/>
  <menu:showBar height="23" width="100%"/> 
    
    <center>
	<form name="p" action="buildScript." method="post" onsubmit="javascript:return validate();">
	  <input type="hidden" name="daoName" value="<%=daoName%>">
	  <div>	  
	  <table width="500" cellpadding="0" cellspacing="1" border="0">
	  <tr>
	    <td width="100%" colspan="3">
	      <p>请从左侧的的列表中选择您将要生成SQL脚本的数据表，并将其添加到右侧的列表中。所生成的脚本是对表中的每条记录生成一个insert语句。</p>
	    </td>
	  </tr>	
	  <tr>
	    <td rowspan="4" width="40%">
            <select name="tables[]" id="toSelect" multiple size="20" style="width:200px">
	    <%int rows = rd.getTableRowsCount("dbTables");
	      for(int i=0;i<rows;i++){
	        String tableName=rd.getStringByDI("dbTables","tableName",i);%>
	        <option value="<%=tableName%>"><%=tableName%></option>
	    <%}%>
	    </select>
	    </td>
	    <td width="20%">&nbsp;</td>
	    <td rowspan="4" width="40%">
	    <select name="tableName[]" id="selected" multiple size="20" style="width:200px">
	    </select>
            </td>
	  </tr>
	  <tr>
	    <td width="20%" align="center">
	      <input type="button" name="btnAdd" value=">" onclick="javascript:add();">
	    </td>
	  </tr>
	  <tr>
	    <td width="20%" align="center">
	      <input type="button" name="btnAdd" value="<" onclick="javascript:del();">			
	    </td>
	  </tr>
	    <td width="20%">&nbsp;</td>
	  </tr>
	  </table>
	  </div>
	  <input type="submit" name="btnSubmit" value="提交">
	  <input type="reset" name="btnReset" value="重置">
	</form>
    </center>
    <menu:show/>    
    </body>
</html>