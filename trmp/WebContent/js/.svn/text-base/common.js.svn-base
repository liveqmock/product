//go
function go(totalPage,current,url){
	var pageNO = document.getElementById("gotopage").value;
	if(pageNO==null||pageNO<1||pageNO>totalPage){
		alert("请输入正确的数值");
		return false;
	}else if(current==pageNO){
		alert("已经是第一页");
		return false;
	}else if(current==pageNO){
		alert("已经是最后一页");
		return false;
	}
	query(pageNO,"",current,url,totalPage);
}

function query(pageNO,STATE,current,url,totalPage){
	if(current==1&&(STATE=="F"||STATE=="P")){
		alert("已经是第一页");
		return false;
	}else if(current==totalPage&&(STATE=="N"||STATE=="L")){
		alert("已经是最后一页");
		return false;
	}
	//window.location.href="listTravelagency.?ta_travelagency/TRAVEL_AGC_ID=&ta_travelagency@pageSize=10&ta_travelagency@pageNO="+pageNO;
	window.location.href=url+pageNO;
}
/*添加,修改弹窗*/
function newWindow(url,w,h){
	var sw=Math.floor((window.screen.width/2-w/2));
	var sh=Math.floor((window.screen.height/2-h/2));
	window.open(url,'obj','width='+w+', height='+h+', top='+sh+', left='+sw+', toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
}

/*上传预览图片*/
function showImg(Imgsrc,num){
	 document.getElementById("img"+num).src=Imgsrc;
	 document.getElementById("img"+num).style.display="block";
}
/*判断有没有选择图片*/
function selectImg(){
	var obj=document.getElementsByTagName("input");
	var p=0;
	for(var a=0;a<obj.length;a++){
		if(obj.item(a).type=="file"&&obj.item(a).value!=""){
			p++;
			}
		}
	return p;
}
/*只接受数字输入*/
function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105||event.keyCode==9)))
		    event.returnValue=false;
}
/* 只接受数字和小数点*/
function checkNumX(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105||event.keyCode==190||event.keyCode==9)))
		    event.returnValue=false;
}
function checkNum1(obj)
{   
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}
function checkPinYin(){
	if(event.keyCode==220)
	    event.returnValue=false;
}
function delTabRow(tableID, nums, id) {
	var tab=document.getElementById(tableID);
	if(document.getElementById(tableID).rows.length/nums==1){
		return false;
	}
	for(var a=0;a<nums;a++){
		var row=document.getElementById(tableID).rows.length;
		tab.deleteRow(row-1);
	}
	if(id != ''){
		var tableID = document.getElementById(tableID);
		lineNo = tableID.rows.length/nums;
		lineNo = lineNo-2;
		document.getElementById(id+lineNo).focus();
	}
}


function dateAdd(interval,number,date) 
{ 
/* 
  *---------------   dateAdd(interval,number,date)   ----------------- 
  *   dateAdd(interval,number,date)   
  *   参数:interval,字符串表达式，表示要添加的时间间隔. 
  *   参数:number,数值表达式，表示要添加的时间间隔的个数. 
  *   参数:date,时间对象. 
  *   返回:新的时间对象. 
  *   var   now   =   new   Date(); 
  *   var   newDate   =   DateAdd( "d ",5,now); 
  *---------------   DateAdd(interval,number,date)   ----------------- 
  */ 
        switch(interval) 
        { 
                case   "y "   :   { 
                        date.setFullYear(date.getFullYear()+number); 
                        return   date; 
                        break; 
                } 
                case   "q "   :   { 
                        date.setMonth(date.getMonth()+number*3); 
                        return   date; 
                        break; 
                } 
                case   "m "   :   { 
                        date.setMonth(date.getMonth()+number); 
                        return   date; 
                        break; 
                } 
                case   "w "   :   { 
                        date.setDate(date.getDate()+number*7); 
                        return   date; 
                        break; 
                } 
                case   "d "   :   { 
                        date.setDate(date.getDate()+number); 
                        return   date; 
                        break; 
                } 
                case   "h "   :   { 
                        date.setHours(date.getHours()+number); 
                        return   date; 
                        break; 
                } 
                case   "m "   :   { 
                        date.setMinutes(date.getMinutes()+number); 
                        return   date; 
                        break; 
                } 
                case   "s "   :   { 
                        date.setSeconds(date.getSeconds()+number); 
                        return   date; 
                        break; 
                } 
                default   :   { 
                        date.setDate(d.getDate()+number); 
                        return   date; 
                        break; 
                } 
        } 
}