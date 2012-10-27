<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="UTF-8"%>
<%@page import="com.dream.bizsdk.common.databus.BizData"%>
<%@page import="com.dream.bizsdk.common.SysVar"%>
<%
	BizData rd = (BizData) request.getAttribute(SysVar.REQ_DATA);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<script language="Javascript">
    function $(id)
    {
        return document.getElementById(id);
    }

    function selectSingle()
    {
        var objFrom = $('selectFrom');
        var opt;
        if(objFrom.selectedIndex > -1)
        {
            var objTo = $('selectTo');
            for(var  i=0;i<objFrom.length;i++){
                if(objFrom[i].selected) {

                    opt = objFrom[i];
                if(!hasSelectedOption(opt))
                {
                    objTo.options[objTo.options.length] = new Option(opt.text, opt.value);
                }
                }
             }

        }
    }

    function selectAll()
    {
        var objFrom = $('selectFrom');
        var objTo = $('selectTo');

        for(var i = 0; i<objFrom.options.length; i++)
        {
            var opt = objFrom.options[i];
            if(!hasSelectedOption(opt))
            {
                objTo.options[objTo.options.length] = new Option(opt.text, opt.value);
            }
        }
    }

    function hasSelectedOption(opt)
    {
        var isExist = false;
        var objTo = $('selectTo');
        for(var j = 0; j<objTo.options.length; j++)
        {
            if(opt.value == objTo.options[j].value)
            {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    function deselectSingle()
    {
        var objTo = $('selectTo');
        if(objTo.selectedIndex > -1)
        {
            objTo.options[objTo.selectedIndex] = null;
        }
    }

    function deselectAll()
    {
        var objTo = $('selectTo');
        objTo.options.length = 0;    
    }


    function getSelectedOptions()
    {
        var objTo = $('selectTo');
        var arr = new Array();
        for(var i= 0; i<objTo.options.length; i++)
        {
            //arr[i] = [objTo.options[i].text, objTo.options[i].value];
         
                objTo.options[i].selected=true;
         
        }
       document.createDict.submit();
    }
</script>
<style type="text/css">
.btn {
	color: #050;
	font-family: 'trebuchet ms', helvetica, sans-serif;
	font-size: small;
	font-weight: bold;
	width: 25px;
}
</style>
</head>
<body>
<form name="createDict" method="post"
	action="createDict.?daoName=<%=request.getParameter("daoName")%>">
<table>
	<tr>
		<td width="45%"><select multiple id="selectFrom"
			style="width: 200px; height: 300px">
			<%
				if (rd != null) {
					java.util.Vector v = (java.util.Vector) rd.get("tables");
					for (int i = 0; i < v.size(); i++) {
			%>
			<option value="<%=v.get(i)%>"><%=v.get(i)%></option>
			<%
				}
				}
			%>
		</select></td>
		<td width="10%" align="center"><input type="button"
			id="btnSelectSingle" value=">" class="btn" onclick="selectSingle()" /><br>
		<input type="button" id="btnSelectAll" value=">>" class="btn"
			onclick="selectAll()" /><br>
		<input type="button" id="btnDeselectSingle" value="<" class="
			btn" onclick="deselectSingle()" /><br>
		<input type="button" id="btnUnselectAll" value="<<" class="
			btn" onclick="deselectAll()" /></td>
		<td width="45%"><select multiple name="toBuild[]" id="selectTo"
			style="width: 200px; height: 300px">

		</select></td>

	</tr>
	<tr>
		<td colspan="3" align="right"><input type="button" id="btnOK"
			value="OK" onclick="getSelectedOptions()" /> <input type="button"
			id="btnCancel" value="Cancel" onclick="javascript: window.close();" />
		</td>
	</tr>
</table>
</form>
</body>
</html>