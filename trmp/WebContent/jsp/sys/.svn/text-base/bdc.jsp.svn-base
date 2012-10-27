<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.database.datadict.DataDict"%>
<%@page import="java.util.HashMap"%>
<%@taglib prefix="menu" uri="/WEB-INF/tld/menu.tld"%>
<%
  int rows=10;
  String daoName="core";
  String tName="DRMBizDataDict";
  String tNames="DRMBizDataDicts";
  HashMap dicts=(HashMap)application.getAttribute(SysVar.APP_SDCS);
  DataDict dc=(DataDict)dicts.get(daoName);
  if(dc==null){
      dc=(DataDict)application.getAttribute(SysVar.APP_SDC);
  }
  BizData rd =(BizData) request.getAttribute(SysVar.REQ_DATA);
  String rowsStr= request.getParameter("rows");
  try{
  	rows=Integer.valueOf(rowsStr).intValue();
  }catch(Exception e){
  }
  if(rows<10){
  	rows=10;
  }
  rows=rows+rd.getTableRowsCount(tNames);
%>
<html>
  <head>
    <title>业务字典项维护</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <link rel="stylesheet" href="../../css/style.css" type="text/css">
    <link rel="stylesheet" href="../../css/menu.css" type="text/css">	
    <script src="../../js/forms.js" type="text/javascript"></script>
    <script language="javascript">
    <!--
        function init_form(frm){
            init(frm);
        }
        
        function check_form(frm){
          if(validate(frm)==false){
          	frm.action="saveBDC.";
          	return false;
          }else{
          	return true;
          }
        }
        
        function query(){
        	var frm = document.forms(0);
        	location.href="queryBDC.?DRMBizDataDict/tableName="+frm.elements("DRMBizDataDict/tableName").value+"&DRMBizDataDict/colName="+frm.elements("DRMBizDataDict/colName").value;
        }
        
        function submitForm(){
           var frm = document.forms(0);
           frm.action="saveBDC.";
           frm.submit();           
        }  
    //-->
    </script>
  </head>
  <body onload="init_form(document.forms(0))">
  <jsp:include page="/jsp/head.jsp"/>
  <menu:showBar x="12" y="16" height="23" width="100%"/>
  <form name="info" method="post" action="saveBDC." onsubmit="return check_form(document.forms(0))">
  <input type="hidden" name="orderBy" value="colCode">
    <p>字典项列表</p>
    <table class="hci" border="0" cellpadding="0" width="500">
      <tr>
        <td class="head" width="15%">表名</td>
        <td class="content"  width="35%">
          <input name="<%=tName%>/tableName" type="text" value="<%=rd.getString(tName,"tableName",0)%>" size="20" maxlength="<%=dc.getFieldLength(tName,"TABLENAME")%>"  class="<%=dc.getCSSClass(tName,"TABLENAME")%>">
        </td>
        <td class="head" width="15%">字段名</td>
        <td class="content"  width="25%">
          <input name="<%=tName%>/colName" type="text" value="<%=rd.getString(tName,"colName",0)%>" size="20" maxlength="<%=dc.getFieldLength(tName,"COLNAME")%>"  class="<%=dc.getCSSClass(tName,"COLNAME")%>">
        </td>
        <td class="content"  width="10%">
          <input type="button" name="btnQuery" value="查询" onclick="javascript:query();">
        </td>
      </tr>
    </table>
    <p>code-value对列表</p>
    <table class="hci" border="0" cellpadding="0" width="500">          
      <tr>
        <td class="head" width="50%" colspan="2">字段code</td>
        <td class="head" width="15%" colspan="2">字段name</td>
      </tr>
      <%for(int i=0;i<rows;i++){%>
      <tr>
        <td class="content"  width="50%" colspan="2">
          <input name="<%=tNames%>/COLCODE[<%=i%>]" type="text" value="<%=rd.getStringByDI(tNames,"colCode",i)%>" size="20" maxlength="<%=dc.getFieldLength(tName,"COLCODE")%>">
        </td>
        <td class="content"  width="50%"  colspan="2">
          <input name="<%=tNames%>/COLVALUE[<%=i%>]" type="text" value="<%=rd.getStringByDI(tNames,"colValue",i)%>" size="20" maxlength="<%=dc.getFieldLength(tName,"COLVALUE")%>">
        </td>
      </tr>
      <%}%>
    </table>
    <input type="button" name="btnSubmit" value="提交" onclick="javascript:submitForm();">
    <input type="reset" name="btnReset" value="重置">
  </form>
  <menu:show/>
  </body>
</html>