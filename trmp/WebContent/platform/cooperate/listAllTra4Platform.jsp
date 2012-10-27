<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp"%>
<%
	int pageNO=1;
	int pageSize=1;
	int totalPage =1;
	int rowsCount = 1;
	if(rd.getTableRowsCount("rsAllOrgs")>0){
		
	   	pageNO=Integer.parseInt(rd.getString("pageNO"));
		pageSize=Integer.parseInt(rd.getString("pageSize"));
		totalPage=(Integer)rd.getAttr("rsAllOrgs", "pagesCount");
		rowsCount = (Integer)rd.getAttr("rsAllOrgs", "rowsCount");
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryui/themes/start/jqueryui.css" >
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cooperate/enterprise.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryui/jqueryui.js" ></script>


<title>企业信息平台</title>

<script type="text/javascript">
$(function(){
	$( "input:submit,input:button").button();
});
function findByLike(){
	document.djGroupPrint_form.action.name="<%=request.getContextPath()%>/platform/cooperate/trasPlatform.?pageNO=1&pageSize=10";
	document.djGroupPrint_form.submit();
}

function zzjs_net(){
    //创建一个div元素
    var popupDiv = document.createElement("div");
    //给这个元素设置属性与样式
    popupDiv.setAttribute("id","popupAddr");
    popupDiv.style.border = "1px solid #ccc";
    popupDiv.style.width = "600px";
    popupDiv.style.height = "400px";
    popupDiv.style.background = "#fff";
    popupDiv.style.zIndex = 99;
    popupDiv.style.position = "absolute";
    //让弹出层在页面中垂直左右居中
    var arrayPageSize = getPageSize();
    var arrayPageScroll = getPageScroll();
    //alert(arrayPageScroll);
    //alert(arrayPageSize);
    popupDiv.style.top = (arrayPageScroll[1] + ((arrayPageSize[3] - 35 - 400) / 2) + 'px') ;
    popupDiv.style.left = (((arrayPageSize[0] - 20 - 600) / 2) + 'px');
    //创建背景层
    var bodyBack = document.createElement("div");
    bodyBack.setAttribute("id","bodybg")
    bodyBack.style.width = "100%";
    bodyBack.style.height = (arrayPageSize[1] + 35 + 'px');
    bodyBack.style.zIndex = 98;
    bodyBack.style.position = "absolute";
    bodyBack.style.top = 0;
    bodyBack.style.left = 0;
    bodyBack.style.filter = "alpha(opacity=20)";
    bodyBack.style.opacity = 0.2;
    bodyBack.style.background = "#000";
    //收工插入到目标元素之后
    var mybody = document.getElementById("test");
    insertAfter(popupDiv,mybody);
    insertAfter(bodyBack,mybody);
    //弹出层内容
    popupDiv.innerHTML = addrHTML();
    init_check();
    //init_check_event();
    $("#main_city div span").css("color","black");
    $("#all_province div span").css("color","black");
}
//元素插入另一个元素之后
function insertAfter(newElement, targetElement){
    var parent = targetElement.parentNode;
    if(parent.lastChild == targetElement)
    {
        parent.appendChild(newElement);
    }
    else
    {
        parent.insertBefore(newElement, targetElement.nextSibling);
    }
}
//获取滚动条的高度
function getPageScroll(){
    var yScroll;
    if (self.pageYOffset) {
        yScroll = self.pageYOffset;
    } else if (document.documentElement && document.documentElement.scrollTop){     // Explorer 6 Strict
        yScroll = document.documentElement.scrollTop;
    } else if (document.body) {// all other Explorers
        yScroll = document.body.scrollTop;
    }
    arrayPageScroll = new Array('',yScroll)
    return arrayPageScroll;
}
//获取页面实际大小
function getPageSize(){
    var xScroll, yScroll;
    if (window.innerHeight && window.scrollMaxY) {
        xScroll = document.body.scrollWidth;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
        xScroll = document.body.scrollWidth;
        yScroll = document.body.scrollHeight;
    } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
        xScroll = document.body.offsetWidth;
        yScroll = document.body.offsetHeight;
    }
    var windowWidth, windowHeight;
    if (self.innerHeight) {    // all except Explorer
        windowWidth = self.innerWidth;
        windowHeight = self.innerHeight;
    } else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
        windowWidth = document.documentElement.clientWidth;
        windowHeight = document.documentElement.clientHeight;
    } else if (document.body) { // other Explorers
        windowWidth = document.body.clientWidth;
        windowHeight = document.body.clientHeight;
    }
    // for small pages with total height less then height of the viewport
    if(yScroll < windowHeight){
        pageHeight = windowHeight;
    } else {
        pageHeight = yScroll;
    }
    // for small pages with total width less then width of the viewport
    if(xScroll < windowWidth){
        pageWidth = windowWidth;
    } else {
        pageWidth = xScroll;
    }
    arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight)
    return arrayPageSize;
}
//关闭弹出层
function closeLayer(obj){
    obj.style.display = "none";
    document.getElementById("bodybg").style.display = "none";
    return false;
}
//拖动函数
function mousedown(e){
    var obj = document.getElementById("popupAddr");
    var e = window.event ? window.event : e;
    obj.startX = e.clientX - obj.offsetLeft;
    obj.startY = e.clientY - obj.offsetTop;
    document.onmousemove = mousemove;
    var temp = document.attachEvent ? document.attachEvent("onmouseup",mouseup) : document.addEventListener("mouseup",mouseup,"");
}
function mousemove(e){
    var obj = document.getElementById("popupAddr");
    var e = window.event ? window.event : e;
    with(obj.style)
    {
        left = e.clientX - obj.startX + "px";
        top = e.clientY - obj.startY + "px";
    }
}
function mouseup(e){
    document.onmousemove = "";
    var temp = document.detachEvent ? document.detachEvent("onmouseup",mouseup) : document.addEventListener("mouseup",mouseup,"");
}
//END拖动函数
//弹出层内容
function addrHTML() {
    //文字
    var TITLE = "请选择区域范围";
    var CLOSE = '<span style="cursor:pointer;" onclick="javascript:closeLayer(this.parentNode.parentNode.parentNode.parentNode);">[关闭]</span>';
    //图片地址
    var TITLEBG = "<%=request.getContextPath()%>/images/div4XZQH/div_title_bg.gif";
    var ICN = "<%=request.getContextPath()%>/images/div4XZQH/div_point_ico.gif";
    var htmlDiv = '';
    htmlDiv += '<div style="width:100%;font-size:12px;">';
    //头部
    htmlDiv += '<div style="background:url('+TITLEBG+') repeat-x;height:40px;color:#fff;cursor:move;" onmousedown="mousedown(arguments[0])">';
    htmlDiv += '<span style="line-height:30px;padding-left:22px;float:left;background:url('+ ICN +') no-repeat 6px 8px;">';
    htmlDiv += TITLE;
    htmlDiv += '</span>';
    htmlDiv += '<span style="float:right;padding-right:12px;line-height:30px;">';
    htmlDiv += CLOSE;
    htmlDiv += '</span>';
    htmlDiv += '</div>';
    //END头部
    //内容部分
    htmlDiv += '<div id="container" style="width:600px;height:400px;"></div>';
    htmlDiv += '<div id="float_lay"></div>';
    //END内容部分
    htmlDiv += '</div>';
    return htmlDiv;
}
//全国行政区划键值匹配数组
var ja=[];
<%=rd.getString("proAndCity")%>
//主要城市数据字典
var maincity=<%=rd.getString("mainCity") %>;
//所有省份数据字典
var allprov=<%=rd.getString("province") %>;
var isIE = /msie/.test(navigator.userAgent.toLowerCase());
var containerID = "container";
var floatID = "float_lay";
function init_check(){
    _container = document.getElementById(containerID);
    _float = document.getElementById(floatID);
    _float.onmouseover = function(){this.style.display = 'block';}
    _float.onmouseout = function(){this.style.display = 'none';}
    //三个区域的创建
    _selectCity = document.createElement("div");
    
    _mainCity = document.createElement("div");
    var m_h3 = document.createElement("h4"); m_h3.innerHTML = "主要城市：";
    _mainCity.appendChild(m_h3);
    _allProvince = document.createElement("div");
    var a_h3 = document.createElement("h4"); a_h3.innerHTML = "所有省份：";
    _allProvince.appendChild(a_h3);
    var div = [],h = [],input  = [],span = [];
    _selectCity.id = "select_city"; _mainCity.id = "main_city"; _allProvince.id = "all_province";
    _container.appendChild(_selectCity); _container.appendChild(_mainCity); _container.appendChild(_allProvince);
    //主要城市部分check的创建
    for (var i = 0 ; i < maincity.length ;i++){
        div[i] = document.createElement("div");
        _mainCity.appendChild(div[i]);
        h[i] = document.createElement("h5");
        div[i].appendChild(h[i]);
        h[i].innerHTML = maincity[i][0];
        for (var j=0 ; j < maincity[i][1].length ; j++){
            input[j] = document.createElement("input");
            input[j].type = "hidden";
            input[j].value = maincity[i][1][j];
            span[j] = document.createElement("span");
            div[i].appendChild(span[j]);
            span[j].appendChild(input[j]);
            span[j].appendChild(document.createTextNode(ja[maincity[i][1][j]]));
            span[j].onclick= function(){
            	openEasy($(this).text(),$(this).children().val());
            };
        }
    }
    //所有省份check的创建(不包括其他)
    for (var i=0 ; i < allprov.length - 1; i++){
        div[i] = document.createElement("div");
        _allProvince.appendChild(div[i]);
        h[i] = document.createElement("h5");
        div[i].appendChild(h[i]);
        h[i].innerHTML = allprov[i][0];
        for (var j=0 ; j < allprov[i][1].length ; j++){
            span[j] = document.createElement("span");
            span[j].id = allprov[i][1][j];
            span[j].innerHTML = ja[allprov[i][1][j]];
            div[i].appendChild(span[j]);
            span[j].onclick = function(evt){
                if(_float.style.display == 'none'){
                    var e = evt || window.event;
                    _float.style.left = e.clientX-document.getElementById("popupAddr").offsetLeft + "px";
                    _float.style.top = e.clientY-document.getElementById("popupAddr").offsetTop + "px";
                    _float.style.display = 'block';
                    _float.className = this.id;
                    createLay(this.id);
                }
            }
            span[j].onmouseover = function(){
                if(_float.className == this.id)
                    _float.style.display = 'block';
            }
            span[j].onmouseout = function(){
                _float.style.display = 'none';
            }
        }
    }
    //所有省份check中其他的创建
    div[0] = document.createElement("div");
    _allProvince.appendChild(div[0]);
    h[0] = document.createElement("h5");
    div[0].appendChild(h[0]);
    h[0].innerHTML = allprov[4][0];
    for (var j=0 ; j < allprov[4][1].length ; j++){
        input[j] = document.createElement("input");
        input[j].type = "hidden";
        input[j].value = allprov[4][1][j];
        span[j] = document.createElement("span");
        div[0].appendChild(span[j]);
        span[j].appendChild(input[j]);
        span[j].appendChild(document.createTextNode(ja[allprov[4][1][j]]));
        span[j].onclick= function(){
        	openEasy($(this).text(),$(this).children().val());
        }
    }
}
//为浮动层创建数据
	function createLay(id){
	    if(id.substr(0,1) != '0')
	        var num = parseInt(id);
	    else
	        var num = parseInt(id.substr(1,4));
	    var n;
	    var span = [],input = [];
	    _float.innerHTML = "";
	    input[num] = document.createElement("input");
	    input[num].type = "hidden";
	    input[num].value = id;
	    span[num] = document.createElement("span");
	    span[num].appendChild(input[num]);
	    span[num].appendChild(document.createTextNode(ja[id]));
	    span[num].onclick= function(){
	    	openEasy($(this).text(),$(this).children().val());
	    }
	    _float.appendChild(span[num]);
	    _float.appendChild(document.createElement("br"));
	    for (var i = num+100; true; i=i+100){
	        //if(num > 950)
	            n = i.toString();
	        //else
	            //n = '0' + i.toString();
	        if(ja[n] == null) break;
	        else{
	            input[n] = document.createElement("input");
	            input[n].type = "hidden";
	            input[n].value = n;
	            span[n] = document.createElement("span");
	            span[n].appendChild(input[n]);
	            span[n].appendChild(document.createTextNode(ja[n]));
	            span[n].onclick= function(){
	            	openEasy($(this).text(),$(this).children().val());
	            }
	            _float.appendChild(span[n]);
	        }
	    }
	    $("#float_lay span").css("color","black");
	    $("#float_lay span").css("font-weight","normal");
	}
	function openEasy(name,id){
		window.location.href="<%=request.getContextPath()%>/platform/cooperate/trasPlatform.?xzqh="+id+"&pageNO=1&pageSize=20";
		$("#popupAddr,#bodybg").css("display","none");
	}
</script>

</head>

<body>
<form  name="djGroupPrint_form" method="post">
<div id="lable"><span>企业信息平台</span></div>
<div  id="thisSelect-table" >
	<table>
		<tr>
			<td rowspan="3" width="40" align="center" ><span>搜<br/><br/>索</span></td>
			<td align="right">企业名称：</td>
			<td><input type="text" name="orgName"></input></td>
			<td><a href="###" onclick="findByLike();"><img alt="搜索" src="<%=request.getContextPath() %>/images/search.gif"></a></td>
			<td><input type="button" value="选择地区" id="test" onclick="zzjs_net();"/></td>
		</tr>
	</table>
</div>
<%
	int rows=rd.getTableRowsCount("rsAllOrgs");
	for(int i=0;i<rows;i++){
		String curr=rd.getStringByDI("rsAllOrgs","curr",i);
		String curr_name=rd.getStringByDI("rsAllOrgs","curr_name",i);
		String CMP_INTRO = rd.getStringByDI("rsAllOrgs","CMP_INTRO",i);
		String privinceName = rd.getStringByDI("rsAllOrgs","privinceName",i);
		String regDate = rd.getStringByDI("rsAllOrgs","registerdate",i);
%>
<div class="prolist">
	<div class="act">
		<a href="trasPlatQuery.?curr=<%=curr %>"
			target="_blank" rel="nofollow" class="inquire"><img
			src="<%=request.getContextPath() %>/images/contact_now_s_cn.gif" alt="发联系信" /></a>
	</div>
	<div class="cntC">
		<p>
			<strong><a href="<%=request.getContextPath() %>/platform/cooperate/traPlatQuery.?hrorganization/orgid=<%=curr %>"><%=curr_name %></a></strong>
			<span class="gray">[<%=regDate %>]</span>
		</p>
		<p><%=CMP_INTRO.length()<=100?CMP_INTRO:CMP_INTRO.substring(0,100)+"..."  %></p>
		<p>
			<img src="<%=request.getContextPath() %>/images/gold_member_s_cn.gif" alt="金牌会员" border="0" class="valign" />
			<img src="<%=request.getContextPath() %>/images/audited_cn.gif" alt="认证供应商" border="0" class="valign" />
			<img src="<%=request.getContextPath() %>/images/certification_member_s_cn.gif" alt="实名会员" border="0" class="valign" />
		</p>
	</div>
	<div class="cgray"><%=privinceName %></div>
</div>
<%	} %>
<div id="page">
	<%String url=request.getContextPath()+"/platform/cooperate/cooperateList.?pageSize=10&pageNO=";%>
	<span class="first-page" onclick='query("1","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>		
	<span class="prev-page" onclick='query("<%=pageNO-1<1?1:pageNO-1 %>","P","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="pagination-sum">
					第<%=Math.min(pageNO,totalPage)%>/<%=totalPage%> 页，
					共<%=rowsCount %> 条记录，
					每页 <%=pageSize%>条
	</span>
	<span class="next-page" onclick='query("<%=pageNO+1>totalPage?totalPage:pageNO+1 %>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="last-page" onclick='query("<%=totalPage%>","N","<%=(int)Math.min(pageNO,totalPage)%>","<%=url%>","<%=totalPage%>")'></span>
	<span class="go-to-page" >
		跳转到第：<input type="text" id="gotopage"/> 页
	</span>
	<span class="go-to-page-icon" onclick='go("<%=totalPage%>","<%=(int)Math.min(pageNO,totalPage)%>","<%=url %>")'></span>
</div>
</form>
<div id="result"></div>
</body>
</html>