//go
function go(totalPage,current,url){
	var pageNO = document.getElementById("gotopage").value;
	if(pageNO==null||pageNO<1||pageNO>totalPage){
		alert("��������ȷ����ֵ");
		return false;
	}else if(current==pageNO){
		alert("�Ѿ��ǵ�һҳ");
		return false;
	}else if(current==pageNO){
		alert("�Ѿ������һҳ");
		return false;
	}
	query(pageNO,"",current,url,totalPage);
}

function query(pageNO,STATE,current,url,totalPage){
	if(current==1&&(STATE=="F"||STATE=="P")){
		alert("�Ѿ��ǵ�һҳ");
		return false;
	}else if(current==totalPage&&(STATE=="N"||STATE=="L")){
		alert("�Ѿ������һҳ");
		return false;
	}
	//window.location.href="listTravelagency.?ta_travelagency/TRAVEL_AGC_ID=&ta_travelagency@pageSize=10&ta_travelagency@pageNO="+pageNO;
	window.location.href=url+pageNO;
}
/*���,�޸ĵ���*/
function newWindow(url,w,h){
	var sw=Math.floor((window.screen.width/2-w/2));
	var sh=Math.floor((window.screen.height/2-h/2));
	window.open(url,'obj','width='+w+', height='+h+', top='+sh+', left='+sw+', toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
}

/*�ϴ�Ԥ��ͼƬ*/
function showImg(Imgsrc,num){
	 document.getElementById("img"+num).src=Imgsrc;
	 document.getElementById("img"+num).style.display="block";
}
/*�ж���û��ѡ��ͼƬ*/
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
/*ֻ������������*/
function checkNum(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105||event.keyCode==9)))
		    event.returnValue=false;
}
/* ֻ�������ֺ�С����*/
function checkNumX(){
	if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
		  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105||event.keyCode==190||event.keyCode==9)))
		    event.returnValue=false;
}
function checkNum1(obj)
{   
	//�Ȱѷ����ֵĶ��滻�����������ֺ�.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//���뱣֤��һ��Ϊ���ֶ�����.
	obj.value = obj.value.replace(/^\./g,"");
	//��ֻ֤�г���һ��.��û�ж��.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	//��֤.ֻ����һ�Σ������ܳ�����������
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
  *   ����:interval,�ַ������ʽ����ʾҪ��ӵ�ʱ����. 
  *   ����:number,��ֵ���ʽ����ʾҪ��ӵ�ʱ�����ĸ���. 
  *   ����:date,ʱ�����. 
  *   ����:�µ�ʱ�����. 
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