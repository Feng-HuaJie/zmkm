
//常用的常量
var arg1=["","恶搞","改编","汉译","纯音乐配词","自创歌词"];
var arg2=["男","女"];
var arg3=["大陆","港澳台","韩国","欧美","日本","其他"];
var arg4=["国语","粤语","英语","韩语","日语","其他"];
var arg5=['<font color="red">禁用</font>','<font color="green">正常</font>','</font  color="yellow">待审核</font>'];
var arg6=["","邮箱注册","手机注册","qq互联","新浪微博"];
var arg7=["普通用户","驻站词人"];
var arg8=["QQ","微信"];
getZZCR = function(){
	
 	var url=baseUrl+'user/list?pageNum=1&pageSize=1000000&userClass=1';
 	var responseObj;
	$.ajax({
	     type: 'GET',
	     async: false,
	     url: url,
	     success: function (response) {
	         responseObj=JSON.parse(response);
	         var responseCode=responseObj.responseCode;
	         if(responseCode=="false"){
	             layer.msg(responseObj.responseMark,{time:3000})
	             return;
	         }
	     }
	});
	
	var options='';
	$.each(responseObj.obj,
		function(index,e){
			var newOption='<option value="'+e.pkid+'">'+e.userName+'</option>';
			options+=newOption;
		}
	)
	return options;
 }
getGradeSelect = function(all){
	 
 	var url=baseUrl+'basGrade/list?pageNum=1&pageSize=1000000';
 	var responseObj;
	$.ajax({
	     type: 'GET',
	     async: false,
	     url: url,
	     success: function (response) {
	         responseObj=JSON.parse(response);
	         var responseCode=responseObj.responseCode;
	         if(responseCode=="false"){
	             layer.msg(responseObj.responseMark,{time:3000})
	             return;
	         }
	     }
	});
//	var newSelect=$('<select id="'+id+'" name="gradeCode"></select>');
//	//alert($("select[name='schoolCode']").text());
//	$.each(responseObj.obj,
//		function(index,e){
//			var newOption=$('<option></option>');
//			newOption.text(e.gradeName);
//			newOption.val(e.gradeCode);
//			newSelect.append(newOption);
//		}
//	)
//	return newSelect;
	var options='';
	if(null!=all){
		options+='<option value="">'+all+'</option>';
	}
	$.each(responseObj.obj,
		function(index,e){
			var newOption='<option value="'+e.gradeCode+'">'+e.gradeName+'</option>';
			options+=newOption;
		}
	)
	return options;
 }
getDistrictSelect = function(id){
	 
 	var url=baseUrl+'basDistrict/list?pageNum=1&pageSize=1000000';
 	var responseObj;
	$.ajax({
	     type: 'GET',
	     async: false,
	     url: url,
	     success: function (response) {
	         responseObj=JSON.parse(response);
	         var responseCode=responseObj.responseCode;
	         if(responseCode=="false"){
	             layer.msg(responseObj.responseMark,{time:3000})
	             return;
	         }
	     }
	});
	var newSelect=$('<select id="'+id+'" name="districtCode"></select>');
	//alert($("select[name='schoolCode']").text());
	$.each(responseObj.obj,
		function(index,e){
			var newOption=$('<option></option>');
			newOption.text(e.districtName);
			newOption.val(e.districtCode);
			newSelect.append(newOption);
		}
	)
	return newSelect;
 }