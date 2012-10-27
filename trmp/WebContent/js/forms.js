
	
function ClickOnCheckBox(cb,frm){
	if(cb.checked == true){
		selectAllCheckBox(frm);
	}else{
		unselectAllCheckBox(frm);
	}
}	

//select all checkbox in a form;
function selectAllCheckBox(frm){
	var objs = frm.elements;
	if(objs==null){
		return;
	}
	var len=objs.length;
	for(var i=0;i<len;i++){
		if(objs.item(i).tagName=="INPUT" && objs.item(i).type=="checkbox"){
			objs.item(i).checked=true;
		}
	}
	return;
}

//unselct all check box in a form
function unselectAllCheckBox(frm){
	var objs = frm.elements;
	if(objs==null){
		return;
	}
	var len=objs.length;
	for(var i=0;i<len;i++){
		if(objs.item(i).tagName=="INPUT" && objs.item(i).type=="checkbox"){
			objs.item(i).checked=false;
		}
	}
	return;
}

function countOfSelectedCheckBox(frm){
	var count=0;
	var objs = frm.elements;
	if(objs==null){
		return count;
	}
	
	var len=objs.length;
	for(var i=0;i<len;i++){
	//alert(objs.item(i).tagName+"&"+objs.item(i).type);
		if(objs.item(i).tagName=="INPUT" && objs.item(i).type=="checkbox"){
			if(objs.item(i).checked==true){
				count++;
			}
		}
	}
	//alert(count);
	return count;
}

function getSelectValue(frm){
	var count=0;
	var objs = frm.elements;
	if(objs==null){
		return count;
	}
	var len=objs.length;
	for(var i=0;i<len;i++){
		if(objs.item(i).tagName=="INPUT" && objs.item(i).type=="checkbox"){
			if(objs.item(i).checked==true){
				count++;
			}
		}
	}
	return count;
}

function validate(frm){
	var objs=frm.elements;
	if(objs==null){
		return true;
	}
	var len=objs.length;
	for(var i=0;i<len;i++){
		var obj=objs.item(i);
		if(obj.tagName=="INPUT" && obj.type=="text"){			
			var clsName=obj.className;
			if(clsName==null||clsName==''||clsName==""){
				continue;
			}
			var value=obj.value;
			if(value=="" && clsName.indexOf("m_")==0){
				alert("输入焦点所在的编辑框不能为空！");
				obj.focus();
				return false;
			}
			if(clsName.indexOf("date")>=0){
				if(value!="" && IsDate(value)==false){
					alert("输入焦点所在的日期格式错误！正确的日期格式为:YYYY-MM-DD,例如2000-1-1");
					obj.focus();
					return false;
				}
			}else if(clsName.indexOf("integer")>=0){
				if(value!="" && IsInteger(value)==false){
					alert("输入焦点所在的编辑框中不是一个合法的整数!");
					obj.focus();
					return false;
				}
			}else if(clsName.indexOf("number")>=0){
				if(value!="" && IsNumber(value)==false){
					alert("输入焦点所在的编辑框中不是一个合法的数字!");
					obj.focus();
					return false;
				}
			}	
		}
	}	
}

function init(frm){
	var objs=frm.elements;
	var initFocusSet=false;
	if(objs==null){
		return true;
	}
	var len=objs.length;
	for(var i=0;i<len;i++){
		var obj=objs.item(i);
		if(obj.tagName=="INPUT"){			
			var clsName=obj.className;
			if(clsName==null||clsName==''||clsName==""){
				continue;
			}
			if(initFocusSet==false && clsName.indexOf("focus")>=0){
				initFocusSet=true;
				obj.focus();
			}				
			if(clsName.indexOf("m_")==0){
				obj.style.backgroundColor="#90EE90";
			}
		}
	}	
}


/** 
LogicalValue:用于判断对象的值是否符合条件，现已提供的选择有： 
       integer：整型，还可判断正整型和负整型 
       number ：数值型，同样可判断正负 
       date  ：日期型，可支持以自定义分隔符的日期格式，缺省是以'-'为分隔符 
       string ：判断一个字符串包括或不包括某些字符 
返回值： 
  true或false 

参数： 
ObjStr ：对象标识符——对象名； 
ObjType：对象类型('integer','number','date','string'之一) 

其他说明： 
  当对象值为空时，则返回错误。 

Author:PPDJ   

例子： 
  example 1:要求检验输入框text1的输入数据是否是“整型”数据，若不是，则提示 
    if (!LogicalValue('text1','integer')) alert('Error: Your must input a integer number'); 
  example 2:要求检验输入框text1的输入数据是否是“正整型”数据，若不是，则提示   
    if (!LogicalValue('text1','integer','+')) alert('Error: Your must input a positive integer number'); 
  example 3:要求检验输入框text1的输入数据是否是“负整型”数据，若不是，则提示   
    if (!LogicalValue('text1','integer','-')) alert('Error: Your must input a negative integer number'); 
  exmaple 4:要求检验输入框text1的输入数据是否是数值，若不是，则提示   
    if (!LogicalValue('text1','number')) alert('Error: Your must input a number'); 
  exmaple 5:要求检验输入框text1的输入数据是否是“正”数值，若不是，则提示   
    if (!LogicalValue('text1','number','+')) alert('Error: Your must input a number'); 
  exmaple 6:要求检验输入框text1的输入数据是否是“负”数值，若不是，则提示   
    if (!LogicalValue('text1','number','-')) alert('Error: Your must input a number'); 
  example 7:要求检验输入框text1的输入数据是否是日期型，若不是，则提示 
    if (!LogicalValue('text1','date')) alert('Error: Your must input a logical date value'); 
    若它的分隔符为A，“A”是一个变量，如常用的'-'和'/'，则用如下语法 
    if (!LogicalValue('text1','date',A)) alert('Error: Your must input a logical date value'); 
    或当分隔符为'/'时 
    if (!LogicalValue('text1','date','/')) alert('Error: Your must input a logical date value'); 
    当分隔符为'-'时，可不要参数'-'，可带上 
  example 8:要求检验输入框text1的输入表示颜色的字符串是否合理，若不合理，则提示 
    if (!LogicalValue('text1','string','0123456789ABCDEFabcdef')) alert('Error: Your must input a logical color value'); 
  example 9:要求检验输入框text1的输入表示密码的字符串是否不含“'"@#$%&^*”这些字符，若含有，则提示 
    if (!LogicalValue('text1','string',''"@#$%&^*',false)) alert('Error: Your password can not contain '"@#$%&^*'); 
    其中false表示不包含有某些字符，true表示必须是哪些字符，缺省值为true 
*/ 
function LogicalValue(ObjStr,ObjType) 
{ 
  var str=''; 
  if ((ObjStr==null) || (ObjStr=='') || ObjType==null) 
  { 
   alert('函数LogicalValue缺少参数'); 
   return false; 
  } 
  var obj = document.all(ObjStr); 
  if (obj.value=='') return false; 
  for (var i=2;i<arguments.length;i++) 
  {  
   if (str!='') 
     str += ','; 
   str += 'arguments['+i+']'; 
  } 
  str=(str==''?'obj.value':'obj.value,'+str); 
  var temp=ObjType.toLowerCase(); 
  if (temp=='integer') 
  { 
    return eval('IsInteger('+str+')'); 
  } 
  else if (temp=='number') 
  { 
   return eval('IsNumber('+str+')'); 
  } 
  else if (temp=='string') 
  { 
   return eval('SpecialString('+str+')'); 
  } 
  else if (temp=='date') 
  { 
   return eval('IsDate('+str+')'); 
  } 
  else 
  { 
   alert('"'+temp+'"类型在现在版本中未提供'); 
   return false; 
  } 
} 

/** 
IsInteger: 用于判断一个数字型字符串是否为整形， 
  还可判断是否是正整数或负整数，返回值为true或false 
string: 需要判断的字符串 
sign: 若要判断是正负数是使用，是正用'+'，负'-'，不用则表示不作判断 
Author: PPDJ 
sample: 
  var a = '123'; 
  if (IsInteger(a)) 
  { 
   alert('a is a integer'); 
  } 
  if (IsInteger(a,'+')) 
  { 
   alert(a is a positive integer); 
  } 
  if (IsInteger(a,'-')) 
  { 
   alert('a is a negative integer'); 
  } 
*/ 

function IsInteger(string ,sign) 
{  
  var integer; 
  if ((sign!=null) && (sign!='-') && (sign!='+')) 
  { 
   alert('IsInter(string,sign)的参数出错：nsign为null或"-"或"+"'); 
   return false; 
  } 
  integer = parseInt(string); 
  if (isNaN(integer)) 
  { 
   return false; 
  } 
  else if (integer.toString().length==string.length) 
  {  
   if ((sign==null) || (sign=='-' && integer<0) || (sign=='+' && integer>0)) 
   { 
     return true; 
   } 
   else 
     return false;  
  } 
  else 
   return false; 
} 

/** 
IsDate: 用于判断一个字符串是否是日期格式的字符串 

返回值： 
  true或false 
   
参数： 
DateString： 需要判断的字符串 
Dilimeter ： 日期的分隔符，缺省值为'-' 

Author: PPDJ 
sample: 
  var date = '1999-1-2'; 
  if (IsDate(date)) 
  { 
   alert('You see, the default separator is "-"); 
  } 
  date = '1999/1/2"; 
  if (IsDate(date,'/')) 
  { 
   alert('The date's separator is "/"); 
  } 
*/ 

function IsDate(DateString , Dilimeter) 
{ 
  if (DateString==null) return false; 
  if (Dilimeter=='' || Dilimeter==null) 
   Dilimeter = '-'; 
  var tempy=''; 
  var tempm=''; 
  var tempd=''; 
  var tempArray; 
  if (DateString.length<8 && DateString.length>10) 
    return false;    
  tempArray = DateString.split(Dilimeter); 
  if (tempArray.length!=3) 
   return false; 
  if (tempArray[0].length==4) 
  { 
   tempy = tempArray[0]; 
   tempd = tempArray[2]; 
  } 
  else 
  { 
   tempy = tempArray[2]; 
   tempd = tempArray[1]; 
  } 
  tempm = tempArray[1]; 
  if(tempm.charAt(0)=="0"){
  	tempm=tempm.substring(1,tempm.length);
  }
  if(tempd.charAt(0)=="0"){
  	tempd=tempd.substring(1,tempd.length);
  }
  var tDateString = tempy + '/'+tempm + '/'+tempd+' 8:0:0';//加八小时是因为我们处于东八区 
  var tempDate = new Date(tDateString); 
  if (isNaN(tempDate)) {
   return false; 
  }
 if (((tempDate.getUTCFullYear()).toString()==tempy) && (tempDate.getMonth()==parseInt(tempm)-1) && (tempDate.getDate()==parseInt(tempd))) 
  { 
   return true; 
  } 
  else 
  { 
   return false; 
  } 
} 

/** 
IsNumber: 用于判断一个数字型字符串是否为数值型， 
  还可判断是否是正数或负数，返回值为true或false 
string: 需要判断的字符串 
sign: 若要判断是正负数是使用，是正用'+'，负'-'，不用则表示不作判断 
Author: PPDJ 
sample: 
  var a = '123'; 
  if (IsNumber(a)) 
  { 
   alert('a is a number'); 
  } 
  if (IsNumber(a,'+')) 
  { 
   alert(a is a positive number); 
  } 
  if (IsNumber(a,'-')) 
  { 
   alert('a is a negative number'); 
  } 
*/ 

function IsNumber(string,sign) 
{ 
  var number; 
  if (string==null) return false; 
  if ((sign!=null) && (sign!='-') && (sign!='+')) 
  { 
   alert('IsNumber(string,sign)的参数出错：nsign为null或"-"或"+"'); 
   return false; 
  } 
  number = new Number(string); 
  if (isNaN(number)) 
  { 
   return false; 
  } 
  else if ((sign==null) || (sign=='-' && number<0) || (sign=='+' && number>0)) 
  { 
   return true; 
  } 
  else 
   return false; 
} 

/** 
SpecialString: 用于判断一个字符串是否含有或不含有某些字符 

返回值： 
  true或false 
   
参数： 
string   ： 需要判断的字符串 
compare  ： 比较的字符串(基准字符串) 
BelongOrNot： true或false，“true”表示string的每一个字符都包含在compare中， 
       “false”表示string的每一个字符都不包含在compare中 

Author: PPDJ 
sample1: 
  var str = '123G'; 
  if (SpecialString(str,'1234567890')) 
  { 
   alert('Yes, All the letter of the string in '1234567890''); 
  } 
  else 
  { 
   alert('No, one or more letters of the string not in '1234567890''); 
  } 
  结果执行的是else部分 
sample2: 
  var password = '1234'; 
  if (!SpecialString(password,''"@#$%',false)) 
  { 
   alert('Yes, The password is correct.'); 
  } 
  else 
  { 
   alert('No, The password is contain one or more letters of '"@#$%''); 
  }  
  结果执行的是else部分 
*/ 
function SpecialString(string,compare,BelongOrNot) 
{ 
  if ((string==null) || (compare==null) || ((BelongOrNot!=null) && (BelongOrNot!=true) && (BelongOrNot!=false))) 
  { 
   alert('function SpecialString(string,compare,BelongOrNot)参数错误'); 
   return false; 
  } 
  if (BelongOrNot==null || BelongOrNot==true) 
  { 
   for (var i=0;i<string.length;i++) 
   { 
     if (compare.indexOf(string.charAt(i))==-1) 
      return false 
   } 
   return true; 
  } 
  else 
  { 
   for (var i=0;i<string.length;i++) 
   { 
     if (compare.indexOf(string.charAt(i))!=-1) 
      return false 
   } 
   return true; 
  } 
} 