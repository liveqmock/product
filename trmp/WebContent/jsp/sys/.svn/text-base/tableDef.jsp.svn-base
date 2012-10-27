<%@page contentType="text/html;charset=GBK"%>
<%@include file="/jsp/common.jsp"%>
<%
	String daoName=request.getParameter("daoName");
%>
<head>
<title>数据实体详细信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<script src="../../js/forms.js" type="text/javascript"></script>
</head>

<body onload="init(document.forms(0))">
<form name="info" method="POST" action="updateTabUsrInfo." onsubmit="javascript:return validate(document.forms(0));">
  <input type="hidden" name="daoName" value="<%=daoName%>">
  <p><b>您正在更新一个表的自定义信息：</b> </p>
  <i>当前表基本信息： </i> 
  <table width="100%" border="1" cellspacing="0" cellpadding="0" class="hci">
    <tr> 
      <td width="20%" class="head">表名</td>
      <td width="30%" class="content">
        <input type="text" name="DRMTable/tableName" value="<%=rd.getString("DRMTables","tableName",0)%>" size="30" readonly>
        </td>
      <td width="20%" class="head">显示名称 </td>
      <td width="30%" class="content">
        <input type="text" name="DRMTable/displayName" size="20" maxlength="60" value="<%=rd.getString("DRMTables","displayName",0)%>">
        </td>
    </tr>
  </table>
<table height="10" width="100%"><tr><td>&nbsp;</td></tr></table>
  <i>该表的字段列表：</i> 
  <table width="100%" border="1" cellspacing="0" cellpadding="0" class="hci" id="detail">
    <tr class="head"> 
      <td width="8%">字段名</td>
      <td width="8%"> 
        <div align="center">显示名称</div>
      </td>
      <td width="8%"> 
        <div align="center">显示类型</div>
      </td>
      <td width="8%"> 
        <div align="center">显示顺序</div>
      </td>
      <td width="6%"> 
        <div align="center">是否主键</div>
      </td>
      <td width="8%"> 
        <div align="center">字段来源</div>
      </td>
      <td width="8%"> 
        <div align="center">引用表名称</div>
      </td>
      <td width="8%"> 
        <div align="center">引用字段</div>
      </td>
      <td width="8%"> 
        <div align="center">可检索</div>
      </td>
    </tr>
    <%	int rows=rd.getTableRowsCount("DRMColumns");for(int i=0;i<rows;i++){%>
    <tr class="content"> 
      <td width="8%" height="26"> 
        <input type="text" name="DRMColumn/colName[<%=i%>]" readonly value="<%=rd.getStringByDI("DRMColumns","colName",i)%>" size="20">
      </td>
      <td width="8%" height="26"> 
        <input type="text" name="DRMColumn/displayName[<%=i%>]" size="16" maxlength="10" value="<%=rd.getStringByDI("DRMColumns","displayName",i)%>">
      </td>
      <td width="8%" height="26"> 
        <select name="DRMColumn/displayType[<%=i%>]">
          <option value="T" <%=rd.getStringByDI("DRMColumns","displayType",i).compareToIgnoreCase("T")==0?"selected":""%>>文本框</option>
          <option value="C" <%=rd.getStringByDI("DRMColumns","displayType",i).compareToIgnoreCase("C")==0?"selected":""%>>复选框</option>
          <option value="R" <%=rd.getStringByDI("DRMColumns","displayType",i).compareToIgnoreCase("R")==0?"selected":""%>>单选框</option>
          <option value="S" <%=rd.getStringByDI("DRMColumns","displayType",i).compareToIgnoreCase("S")==0?"selected":""%>>下拉列表</option>
        </select>
      </td>
      <td width="8%" height="26"> 
        <input type="text" name="DRMColumn/displaySeq[<%=i%>]" size="5" maxlength="10" value="<%=rd.getStringByDI("DRMColumns","displaySeq",i)%>" class="integer_">
      </td>
      <td width="6%" height="26"> 
        <input type="checkbox" name="DRMColumn/isPK[<%=i%>]" value="Y" <%=rd.getStringByDI("DRMColumns","isPK",i).compareToIgnoreCase("Y")==0?"checked":""%>>
      </td>
      <td width="8%" height="26"> 
        <select name="DRMColumn/datasource[<%=i%>]">
          <option value="NULL" <%=rd.getStringByDI("DRMColumns","datasource",i).compareToIgnoreCase("NULL")==0?"selected":""%>>手工输入</option>
          <option value="FK" <%=rd.getStringByDI("DRMColumns","datasource",i).compareToIgnoreCase("FK")==0?"selected":""%>>外键选择</option>
          <option value="BUSI" <%=rd.getStringByDI("DRMColumns","datasource",i).compareToIgnoreCase("BUSI")==0?"selected":""%>>业务字典</option>
        </select>
      </td>
      <td width="8%" height="26"> 
        <input type="text" name="DRMColumn/refTabName[<%=i%>]" size="8" maxlength="10" value="<%=rd.getStringByDI("DRMColumns","refTabName",i)%>">
      </td>
      <td width="8%" height="26"> 
        <input type="text" name="DRMColumn/refColName[<%=i%>]" size="8" maxlength="10" value="<%=rd.getStringByDI("DRMColumns","refColName",i)%>">
      </td>
      <td width="8%" height="26"> 
        <input type="checkbox" name="DRMColumn/canSearch[<%=i%>]" size="8" maxlength="10" value="Y" <%=rd.getStringByDI("DRMColumns","canSearch",i)!=null && rd.getStringByDI("DRMColumns","canSearch",i).compareToIgnoreCase("Y")==0?"checked":"" %>>
      </td>
    </tr>
    <%}%>
  </table>
    <input type="submit" name="btnSubmit" value="更新" class="button">
    <input type="reset" name="btnReset" value="复位" class="button">
</form>
</body>

