//脚本扩容
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
//=======================================网站常用参数====================================

   var basePath = "/zmkm/";
   var pageSize=10;
   var max_export_num=100000;//最大导出记录数量10W
//=======================================前端路由========================================
   //前端路由分发 加载不同的jsp
   toJsp=function(jspPath){
	   //var jspPath="jsp"+window.location.href.split('#')[1]+".jsp";
	   //alert(jspPath);
		$.ajax({
			type: 'get',
			url: basePath + jspPath,
			dataType : 'text',
			success: function(data){
				//alert(data);
				$("#main_content").html(data);
			}
		});
  }
  var routes = {
   
    '/user' : function(){toJsp('backend/jsp/user.html')},
    '/card' : function(){toJsp('backend/jsp/card.html')},
  };

  var router = Router(routes);
  router.init();
//==========================================返回jsp================================
  getJsp=function(jspPath){

   	var jspContent="";
		$.ajax({
			type: 'get',
			url: basePath + jspPath,
			dataType : 'text',
            async: false,
			success: function(data){
				jspContent=data
			}
		});
		return jspContent;
   }
//=======================================分页========================================
   var operationHtml='<a  class="addNew " onclick="initEditDialog();editDialog(pkid)">修改</a>'+
      			     '<a  class="addNew " onclick="updateStatus(status,pkid)">caozuo</a>';
   toPage = function(gotoPage){
	   
	   $("#searchText").val($.trim($("#searchText").val()));
	   var url=basePath+$("#modelName").val();
		//获取查询条件信息
	   var param=$("#searchFrom").serialize();
		//获取排序信息
	   var orderby='';
	   $(".sort").each(function(){
			var name=$(this).attr("name");
			var value=$(this).attr("value");
			orderby+=(name+"="+value+"&");
	   });
	      $.ajax({
	          type:'get',
	          url:url+"/page?pageNum="+gotoPage+"&"+"pageSize="+pageSize+"&"+param+"&"+orderby,
	          dataType:'json',
	          async: false,
	          success:function(response){
	           	  if(response.responseCode=='false'){
	              	   layer.msg(response.responseMark,{time:2000});
	              }
	              var tBody=$('#dataTable').children('tbody');
	              tBody.html('');
	              var thArr=[];
	              $('#dataTable').find('th').each(function(){
	                  thArr.push($(this).attr('id'));
	              })
	              var list=response.obj.list;
	              for(var i=0;i<list.length;i++){
	                  var newTr=$('<tr></tr>');
	                  for(var j=0;j<thArr.length;j++) {
	                      var newTd = $('<td></td>');
	                      //判断是否是操作列，填充td内容，
	                      var tdContent;
	                      if (thArr[j] == "operation") {
	                          newTd.addClass('operation');
	                          tdContent = operationHtml.replace(new RegExp('pkid','g'),list[i].pkid);
	                          if(list[i].status=="0"||list[i].status=="2"){
	                        	  tdContent = tdContent.replace(new RegExp('status','g'),'1');
	                        	  tdContent = tdContent.replace(new RegExp('caozuo','g'),'启用');
	                          }else{
	                        	  tdContent = tdContent.replace(new RegExp('status','g'),'0');
	                        	  tdContent = tdContent.replace(new RegExp('caozuo','g'),'禁用');
	                          }
	                      }else if(thArr[j]=="pkid"){
	                    	  newTd.css("display","none");
	                    	  tdContent = list[i][thArr[j]];
	                      } else {
	                          tdContent = list[i][thArr[j]];
	                      }
	                      newTd.html(tdContent);
	                      newTd.appendTo(newTr);
	                  }
	                  newTr.appendTo(tBody);
	              }
	 			 totalpage=response.obj.totalpage==0?1:response.obj.totalpage
	 			 initPageInfo(totalpage,gotoPage);
	          }
	      })
	      //子页面需要实现render方法
	      render();
  	 }
	//================================================分页组件=============================================
	function initPageInfo(totalpage,pagenum){
		$("#page").jqPaginator({
			totalPages: totalpage,
			visiblePages: 10,
			currentPage: pagenum,
			//prev:false,
			//next:false,
			onPageChange: function (num, type) {
				if(type!="init"){
					toPage(num,10);
				}
			}
		 });
	}
	//================================================排序方法=============================================
  	 sort = function(e){
   		//只支持单列排序
         $(".sort").each(function(){
         	if(e==this){
         		if($(e).attr("value")==""){
         			$(e).attr("value","desc");
         			$(e).text("↑");
         		}else if($(e).attr("value")=="desc"){
         			$(e).attr("value","asc");
         			$(e).text("↓");
         		}else{
         			$(e).attr("value","");
         			$(e).text("↑↓");
         		}
         	}else{
             	$(this).attr("value","");
             	$(this).text("↑↓");
         	}
         });
         toPage(1);
 	}
 	//提取表单参数
  	$.fn.form2Json = function()    
  	{    
  	   var o = {};    
  	   var a = this.serializeArray();    
  	   $.each(a, function() {    
  	       if (o[this.name]) {    
  	           if (!o[this.name].push) {    
  	               o[this.name] = [o[this.name]];    
  	           }    
  	           o[this.name].push(this.value || '');    
  	       } else {    
  	           o[this.name] = this.value || '';    
  	       }    
  	   });    
  	   return o;    
  	}; 
  	//将返回json对象的值绑定到表单
  	$.fn.bindForm = function(obj){
  		var key,value,tagName,type,arr;
  		for(x in obj){
  			key = x;
  			value = obj[x];
  			this.find("[name='"+key+"'],[name='"+key+"[]']").each(function(){
  				tagName = $(this)[0].tagName;
  				type = $(this).attr('type');
  				if(tagName=='INPUT'){
  					if(type=='radio'){
  						$(this).attr('checked',$(this).val()==value);
  					}else if(type=='checkbox'){
  						arr = value.split(',');
  						for(var i =0;i<arr.length;i++){
  							if($(this).val()==arr[i]){
  								$(this).attr('checked',true);
  								break;
  							}
  						}
  					}else{
  						$(this).val(value);
  					}
  				}else if(tagName=='SELECT' || tagName=='TEXTAREA'){
  					$(this).val(value);
  				}
  				
  			});
  		}
  	}
 	// =============================新增信息========================
  	addDialog=function(){
  	    //$('.dataForm input').attr("value", '');
  	    var popContent = $('#add_pop').html();
  	    newLayer=layer.open({
  	    	title:'新增',
  	        type: 1,
  	        skin: 'layui-layer-demo', //样式类名
  	        shift: 2,
  	        area: ['800px', '550px'],
  	        maxmin: true,
  	        shadeClose: true, //开启遮罩关闭
  	        content: popContent
  	    });
  	}
  	// 提交新增表单
  	add = function () {
  		
  		var jsonStrData =$(".add_form").eq(1).form2Json();
  		jsonStrData = JSON.stringify(jsonStrData);
  		//alert(jsonStrData);
  	    var url=basePath+$("#modelName").val();
  	    $.ajax({
  	        type: 'POST',
  	        url: url,
  	        data: jsonStrData,
  	        success: function (response) {
  	        	layer.close(newLayer);
  	            //console.log(response);
  	            var responseObj=JSON.parse(response);
  	            var responseCode=responseObj.responseCode;
  	            if(responseCode=="false"){
  	                layer.msg(responseObj.responseMark,{time:3000})
  	            }else{
  	                layer.msg('新增成功',{time:1000})
  	                toPage(parseInt($(".active").children().text()));
  	            }
  	        }
  	    });
  	}
//==============================================================弹窗=======================================================
  	// 显示修改弹窗
  	editDialog = function (pkid) {
  		
  		 var url=basePath+$("#modelName").val()+"/"+pkid;
  		 var responseObj; 
  	     $.ajax({
  	        type: 'GET',
  	        url: url,
  	        async:false,
  	        success: function (response) {
  	            responseObj=JSON.parse(response);
  	            var responseCode=responseObj.responseCode;
  	            if(responseCode=="false"){
  	                layer.msg(responseObj.responseMark,{time:3000})
  	                return;
  	            }else{
  	            }
  	        }
  	    });
  	    var popContent = $('#edit_pop').html();
  	    editLayer=layer.open({
  	      	title:'修改',
  	        type: 1,
  	        skin: 'layui-layer-demo', //样式类名
  	        shift: 2,
  	        area: ['800px', '550px'],
  	        maxmin: true,
  	        shadeClose: true, //开启遮罩关闭
  	        content: popContent
  	    });
  	    $(".edit_form").eq(1).bindForm(responseObj.obj);
  	}
//==============================================================弹窗=======================================================
  	//提交修改弹窗
  	edit = function () {
  		
  		var jsonStrData =$(".edit_form").eq(1).form2Json();
  		jsonStrData = JSON.stringify(jsonStrData);
  		//alert(jsonStrData);
  	    var url=basePath+$("#modelName").val();
  	    $.ajax({
  	        type: 'PUT',
  	        url: url,
  	        data: jsonStrData,
  	        success: function (response) {
  	        	layer.close(editLayer);
  	            //console.log(response);
  	            var responseObj=JSON.parse(response);
  	            var responseCode=responseObj.responseCode;
  	            if(responseCode=="false"){
  	                layer.msg(responseObj.responseMark,{time:3000})
  	            }else{
  	                layer.msg('修改成功',{time:1000})
  	                toPage(parseInt($(".active").children().text()));
  	            }
  	        }
  	    });
  	}
  //==============================================================导入=======================================================
  	importDialog = function(){
  	    event.preventDefault();
  	    //$('.dataForm input').attr("value", '');
  	    var popContent = $('.import_pop').html();
  	    newLayer=layer.open({
  	    	title:'新增',
  	        type: 1,
  	        skin: 'layui-layer-demo', //样式类名
  	        shift: 2,
  	        area: ['600px', '300px'],
  	        maxmin: true,
  	        shadeClose: true, //开启遮罩关闭
  	        content: popContent
  	    });
  	}
  	// ============================删除记录=====================
  	//支持多个id“，”拼接
//  	Delete = function (pkid) {
//  		
//  	    //var pkid = $(obj).parents('tr').children('td').eq(0).text();
//  	    var url=basePath+$("#modelName").val()+"/"+pkid;
//  	    layer.confirm('您确定要删除此信息？', {
//  	        btn: ['确定', '取消'] //按钮
//  	    }, function () {
//  	        $.ajax({
//  	            type:'DELETE',
//  	            url:url,
//  	            success: function (response) {
//  	                //console.log(response)
//  	                var responseObj=JSON.parse(response);
//  	                var responseCode=responseObj.responseCode;
//  	                if(responseCode=="false"){
//  	                    layer.msg(responseObj.responseMark,{time:3000})
//  	                }else{
//  	                	layer.msg('已删除',{time:1000})
//  	                    toPage($('.pageInput').val());
//  	                }
//  	            }
//  	        });
//  	    }, function () {
//  	        layer.msg('已撤销删除',{time:1000})
//  	    });
//  	}
  	// ============================导出=====================
  	Export = function(){
  		layer.confirm('您确定要导出当前查询结果到excel吗？', { btn: ['确定', '取消'] },
			function () {	 
	  		    //获取查询条件信息
	  	        var param=$("#searchFrom").serialize();
	  	        //获取排序信息
	  	        var orderby='';
	  	        $(".sort").each(function(){
	  	        	var name=$(this).attr("name");
	  	        	var value=$(this).val();
	  	        	orderby+=(name+"="+value+"&");
	  	        });
	  	        var url=basePath+$("#modelName").val()+"/"+"export?pageNum=1&pageSize="+max_export_num+"&"+param+"&"+orderby;
	  	        //alert(url);
	  	        window.location.href=url;
	  	        layer.msg('导出中...',{time:1000})
	  		},function () {
	  	        layer.msg('已撤销导出',{time:1000})
	  	    }
  		);
  	}
  	// ============================导入=====================
  	Import = function(){
        //获取查询条件信息
        var $importForm=$(".import_form");
        $importForm.attr("method", "post");
        $importForm.attr("action", basePath+$("#modelName").val()+"/import");
        $importForm.attr("enctype", "multipart/form-data");
        $importForm.submit(); 
        //alert(11);
        //toPage($('.pageInput').val());
  	}
  	// ============================软删除=====================
  	updateStatus = function (status,ids) {
  		
  		var caozuoStr;
  		status=="1"?caozuoStr="启用":caozuoStr="禁用";
  	    var url=basePath+$("#modelName").val()+"/status?status="+status+"&ids="+ids;
  	    //var data={};
  	    //data["status"]=status;
  	    //data["ids"]=ids;
  	    //var jsonData = JSON.stringify(data);
  	    layer.confirm('您确定要'+caozuoStr+'此信息？', {
  	        btn: ['确定', '取消'] ,//按钮
  	    }, function () {
  	        $.ajax({
  	            type:'PUT',
  	            url:url,
  	            success: function (response) {
  	                //console.log(response)
  	                var responseObj=JSON.parse(response);
  	                var responseCode=responseObj.responseCode;
  	                if(responseCode=="false"){
  	                    layer.msg(responseObj.responseMark,{time:3000})
  	                }else{
  	                	layer.msg('已'+caozuoStr,{time:1000})
  	                    toPage(parseInt($(".active").children().text()));
  	                }
  	            }
  	        });
  	    }, function () {
  	        layer.msg('已撤销'+caozuoStr,{time:1000})
  	    });
  	}
  	//======================取消操作关闭对话框======================
  	cancel=function(){
  	    layer.closeAll();
  	    layer.msg('已取消操作',{time:1000})
  	}  	 
  	//=======================================左侧菜单脚本事件=========================================
	$(function(){
		 var w_height=$(window).height();
		 var w_width=$(window).width();
		 //$("body").css("height",w_height-50);
		 $(".right").css("height",w_height-50);
		$(".a1").click(function(){
			if($(this).parent().hasClass('active-flag')){
				$(this).parent().removeClass('active-flag');
				$(this).find('.arrow').addClass('fa-angle-left');
				$(this).find('.arrow').removeClass('fa-angle-down');
			}else{
				$(this).parent().addClass('active-flag') 	
				$(this).find('.arrow').addClass("fa-angle-down");
				$(this).find('.arrow').removeClass("fa-angle-left");
			}
		});
		$(".a2").click(function(){
			$('.a2').each(function(){
				$(this).removeClass('active-a2');
			})
			$(this).addClass('active-a2');
		});
	});
