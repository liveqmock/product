<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common.jsp"%>
<%@page import="java.net.URLEncoder"%>
<html>
<head>
<link rel="stylesheet" href="../../css/style.css" type="text/css">
<link rel="stylesheet" href="../../css/menu.css" type="text/css">
<title>我的基本信息</title>
</head>
<body>
<jsp:include page="/jsp/head.jsp"/>
<menu:showBar height="23" width="100%"/>

<form name="empInfo" method="post" action="updateMyInfo.">
<input type=hidden name="HREmployee/empID[0]@oldValue" value="<%=rd.get("HREmployee","empID",0)%>">
<input type=hidden name="HREmployee/empID" value="<%=rd.get("HREmployee","empID",0)%>">
  <p>您现在可以更改您的基本信息 </p>
  <table width="100%" border="0" cellspacing="1" cellpadding="0" class="hci">
    <tr> 
      <td width="22%" class="head">员工编号</td>
      <td width="78%" class="content"> <%=rd.getString("HREmployee","empID",0)%> </td>
    </tr>
    <tr> 
      <td width="22%" class="head">家庭电话</td>
      <td width="78%" class="content"> 
        <input type="text" name="HREmployee/telH" value="<%=rd.getString("HREmployee","telH",0)%>">
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">工作电话</td>
      <td width="78%" class="content"> 
        <input type="text" name="HREmployee/tel" value="<%=rd.getString("HREmployee","tel",0)%>">
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">移动电话</td>
      <td width="78%" class="content"> 
        <input type="text" name="HREmployee/mobileTel" value="<%=rd.getString("HREmployee","mobileTel",0)%>">
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">EMail</td>
      <td width="78%" class="content"> 
        <input type="text" name="HREmployee/email" value="<%=rd.getString("HREmployee","email",0)%>" size="64">
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">地址</td>
      <td width="78%" class="content"> 
        <input type="text" name="HREmployee/address" value="<%=rd.getString("HREmployee","address",0)%>" size="64">
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">邮政编码</td>
      <td width="78%" class="content"> 
        <input type="text" name="HREmployee/zipcode" value="<%=rd.getString("HREmployee","zipcode",0)%>">
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">婚姻状况</td>
      <td width="78%" class="content"> 
        <select name="HREmployee/marriage">
          <option value="Y" <%=rd.getString("HREmployee","marriage",0).compareTo("Y")==0?"selected":""%>>已婚</option>
          <option value="N" <%=rd.getString("HREmployee","marriage",0).compareTo("N")==0?"selected":""%>>未婚</option>
        </select>
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">紧急联系人</td>
      <td width="78%" class="content"> 
        <input type="text" name="HREmployee/emContact" value="<%=rd.getString("HREmployee","emContact",0)%>">
      </td>
    </tr>
    <tr> 
      <td width="22%" class="head">紧急联系电话</td>
      <td width="78%" class="content"> 
        <input type="text" name="HREmployee/emContactTel" value="<%=rd.getString("HREmployee","emContactTel",0)%>" size="12" maxlength="12">
      </td>
    </tr>
  </table>
  <p> 
    <input type="submit" name="Submit" value="提交" class="button">&nbsp;&nbsp;<input type="reset" name="btnReset" value="重置" class="button">
  </p>
</form>
<menu:show/>
</body>
</html>