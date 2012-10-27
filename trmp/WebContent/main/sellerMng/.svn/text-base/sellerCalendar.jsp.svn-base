<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk" />
    <title><%=rd.getStringByDI("rsDateOfLinePlan","line_name",0) %>-报名时间表</title>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.js"></script>
   	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/treeAndAllCss.css" />
</head>
<%
String spareDate = request.getParameter("spareDate");
%>
<body>
    <script type="text/javascript">

    	var mydate=new Date("<%=spareDate %>");
        var $ = function (id) {
            return "string" == typeof id ? document.getElementById(id) : id;
        };
        //    alert($);

        var Class = {
            create: function () {
                return function () {
                    this.initialize.apply(this, arguments);
                }
            }
        }

        var Extend = function (destination, source) {
            for (var property in source) {
                destination[property] = source[property];
            }
            return destination;
        }


        var Calendar = Class.create();
        Calendar.prototype = {
            initialize: function (container, options) {
                this.Container = $(container); //容器(table结构)
                this.Days = []; //日期对象列表
                this.SetOptions(options);

                this.Year = this.options.Year || new Date().getFullYear();
                this.Month = this.options.Month || new Date().getMonth() + 1;
                this.SelectDay = this.options.SelectDay ? new Date(this.options.SelectDay) : null;
                this.onSelectDay = this.options.onSelectDay;
                this.onToday = this.options.onToday;
                this.onFinish = this.options.onFinish;

                this.Draw();
            },
            //设置默认属性
            SetOptions: function (options) {
                this.options = {//默认值
                    Year: mydate.getFullYear(), //显示年
                    Month: mydate.getMonth()+1, //显示月
                    SelectDay: null, //选择日期
                    onSelectDay: function () { }, //在选择日期触发
                    onToday: function () { }, //在当天日期触发
                    onFinish: function () { } //日历画完后触发
                };
                Extend(this.options, options || {});
            },
            //当前月
            NowMonth: function () {
                this.PreDraw(new Date("<%=spareDate %>"));
            },
            //上一月
            PreMonth: function () {
                this.PreDraw(new Date(this.Year, this.Month - 2, 1));
            },
            //下一月
            NextMonth: function () {
                this.PreDraw(new Date(this.Year, this.Month, 1));
            },
            //上一年
            PreYear: function () {
                this.PreDraw(new Date(this.Year - 1, this.Month - 1, 1));
            },
            //下一年
            NextYear: function () {
                this.PreDraw(new Date(this.Year + 1, this.Month - 1, 1));
            },
            //根据日期画日历
            PreDraw: function (date) {
                //再设置属性
                this.Year = date.getFullYear(); this.Month = date.getMonth() + 1;
                //重新画日历
                this.Draw();
            },
            //画日历
            Draw: function () {
                //用来保存日期列表
                var arr = [];
                //用当月第一天在一周中的日期值作为当月离第一天的天数
                for (var i = 1, firstDay = new Date(this.Year, this.Month - 1, 1).getDay(); i <= firstDay; i++) { arr.push(0); }
                //用当月最后一天在一个月中的日期值作为当月的天数
                for (var i = 1, monthDay = new Date(this.Year, this.Month, 0).getDate(); i <= monthDay; i++) { arr.push(i); }
                //清空原来的日期对象列表
                this.Days = [];
                //插入日期
                var frag = document.createDocumentFragment();
                while (arr.length) {
                    //每个星期插入一个tr
                    var row = document.createElement("tr");
                    //每个星期有7天
                    for (var i = 1; i <= 7; i++) {
                        var cell = document.createElement("td"); cell.innerHTML = "&nbsp;";
                        if (arr.length) {
                            var d = arr.shift();
                            if (d) {
                                cell.innerHTML = d;
                                this.Days[d] = cell;
                                var on = new Date(this.Year, this.Month - 1, d);
                                //判断是否今日
                                this.IsSame(on, new Date()) && this.onToday(cell);
                                //判断是否选择日期
                                this.SelectDay && this.IsSame(on, this.SelectDay) && this.onSelectDay(cell);
                            }
                        }
                        row.appendChild(cell);
                    }
                    frag.appendChild(row);
                }
                //先清空内容再插入(ie的table不能用innerHTML)
                while (this.Container.hasChildNodes()) { this.Container.removeChild(this.Container.firstChild); }
                this.Container.appendChild(frag);
                //附加程序
                this.onFinish();
            },
            //判断是否同一日
            IsSame: function (d1, d2) {
                return (d1.getFullYear() == d2.getFullYear() && d1.getMonth() == d2.getMonth() && d1.getDate() == d2.getDate());
            }
        }

        function go2URL(url){

        	//window.parent.parent.GB_hide(); 
			//parent.parent.location.href=url;        	
        }
    </script>
    
    

    <div class="Calendar" id="iddddd">
        <div id="idCalendarPre" title="上一月">
         	   查看上月</div>
        <div id="idCalendarToday">
        	<span id="idCalendarYear"></span>年 <span id="idCalendarMonth"></span>月&nbsp;&nbsp;
        </div>
        <div id="idCalendarNext" title="下一月">
            查看下月</div>
      
        <table cellspacing="0" class="datas">
            <thead>
                <tr>
                    <td>星期日</td>
                    <td>星期一</td>
                    <td>星期二</td>
                    <td>星期三</td>
                    <td>星期四</td>
                    <td>星期五</td>
                    <td>星期六</td>
                </tr>
            </thead>
            <tbody id="idCalendar">
            </tbody>
        </table>
    </div>
    <script language="JavaScript" type="text/javascript">
        var cale = new Calendar("idCalendar", {
            SelectDay: mydate.setDate(mydate.getDate()),
            onSelectDay: function (o) { o.className = "onSelect"; },
            onToday: function (o) { o.className = "onToday"; },
            onFinish: function () {
                $("idCalendarYear").innerHTML = this.Year; $("idCalendarMonth").innerHTML = this.Month;
                var arr = new Array();
<%
int rows = rd.getTableRowsCount("rsDateOfLinePlan");
String lineid = rd.getStringByDI("rsDateOfLinePlan","line_id",0);
String groupID = rd.getStringByDI("rsDateOfLinePlan","groupID",0);
for(int i=0;i<rows;i++){
	
	String planDate = rd.getStringByDI("rsDateOfLinePlan","planoflinedate",i);
	String lineID = rd.getStringByDI("rsDateOfLinePlan","line_id",i);
	String maxPersonCount = rd.getStringByDI("rsDateOfLinePlan","maxpersoncount",i);
	String spare = rd.getStringByDI("rsDateOfLinePlan","spare",i);
	String state = rd.getStringByDI("rsDateOfLinePlan","FTZT",i);
	String stateNm = rd.getStringByDI("rsDateOfLinePlan","FTZT",i);
	String gID = rd.getStringByDI("rsDateOfLinePlan","groupID",i);
	if("1".equals(state))
		state = "正常";
	else
		state = "已封团";
%>
        arr[<%=i%>] = { year: "<%=planDate %>", total: "<%=maxPersonCount %>", left: "<%=spare %>" , state: "<%=state %>", stateNm: "<%=stateNm %>", firstGroup:"<%=gID %>" };
<%
}
%>
    for (var j = 0, len = arr.length; j < len; j++) {
       //精确到年份日期
       if (this.Year == arr[j]['year'].split("-")[0] && this.Month == arr[j]['year'].split("-")[1]) {
         //如果日期Days首位为0，则去除0
         var state = arr[j]['stateNm'];
       	 var url;
       	 if(state == '1'){
       		url = "initSignUp.?lineID=<%=lineid %>&gID="+arr[j]['firstGroup']
       		    +"&state=<%=rd.getString("action")%>&dmsm/jglx=4&TA_ZT_GATHER/LINE_ID=<%=lineid %>&TA_ZT_GATHER/ORGID=<%=sd.getString("orgid") %>&"
       		    +"bDate="+this.Year+"-"+arr[j]['year'].split("-")[1]+"-"+arr[j]['year'].split("-")[2];
       	 }else{

			url = "###";
       	 }
         
         this.Days[arr[j]['year'].split("-")[2].substring(0, 1) == "0" ? arr[j]['year'].split("-")[2].substring(1, 2) : arr[j]['year'].split("-")[2].substring(0, 2)].innerHTML 
         = (arr[j]['year'].split("-")[2].substring(0, 1) == "0" ? arr[j]['year'].split("-")[2].substring(1, 2) : arr[j]['year'].split("-")[2].substring(0, 2)) + 
         "<a href='"+url+"');\" >" + 
         "</br>" + "总数：" + arr[j]['total'] + "</br>" + "剩余：" + arr[j]['left'] + "</br>"+ "团状态："+arr[j]['state']+
         "</a>"+
         "</br>";
       }
     }
  }
});

        $("idCalendarPre").onclick = function () { cale.PreMonth(); }
        $("idCalendarNext").onclick = function () { cale.NextMonth(); }

        //$("idCalendarPreYear").onclick = function () { cale.PreYear(); }
        //$("idCalendarNextYear").onclick = function () { cale.NextYear(); }
        //$("idCalendarNow").onclick = function () { cale.NowMonth(); }
        
        
    </script>
    <table class="datas">
    	<tr>
    		<td align="center">
    			<br>&nbsp;<input type="button" id="button" value="    返        回    " onclick="window.history.go(-1)"/><br><br>
    		</td>
    	</tr>
    </table>
</body>
</html>
