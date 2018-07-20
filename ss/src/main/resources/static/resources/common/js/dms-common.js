var lastupdate  = "";
var dms = function(){
	var pageContent = $('.page-container');

	return {
		//ajax请求封装
		ajaxUtil : function(url,data,fun){
			$.ajax({
				async: true,
				type: "POST",
				url: url,
				data: data,
				success: function(result) {
					if( fun ){
						//console.log(JSON.stringify(result))
						fun(result);
					}
				},
				error:function(result){
					console.log("ajax错误消息:"+JSON.stringify(result))
				},
				complete:function(){
					App.unblockUI(pageContent);
				},
				beforeSend:function(){
					App.blockUI(pageContent, false);
				}
			});
		},
		//获取URL参数
		getUrlParam : function(url,name){
			var searchString = url,i, val, params = searchString.split("&");

	        for (i = 0; i < params.length; i++) {
	            val = params[i].split("=");
	            if (val[0] == name) {
	                return unescape(val[1]);
	            }
	        }
	        return null;
		},
		//判断字符是否为空
		isNotEmpty : function(str){
			return str != null && str != undefined  && str != "" && str != "undefined";
		},
		dataValidation : function(opts){
			var submitForm = opts.submitForm,rules = opts.rules ,submitHandler = opts.submitHandler;
			if( !submitForm || submitForm.length == 0 ){
				submitForm.log("submitForm is empty !");
			}
			submitForm.validate({
				errorElement: 'span', //default input error message container
				errorClass: 'help-block', // default input error message class
				focusInvalid: false, // do not focus the last invalid input
				ignore: "",
				rules: rules,
				messages: opts.messages,
				errorPlacement: function (error, element) { // render error placement for each input type
					var icon = $(element).parent('.input-icon').children('i');
					icon.removeClass('fa-check').addClass("fa-warning");
					if (opts.messages) {
						icon.attr("data-original-title", error.text()).tooltip();
						// icon.attr("data-original-title", error.text()).tooltip({'container': 'body'}); // 使用form-group时，该设置无法显示提示
					}
				},
				highlight: function (element) { // hightlight error inputs
					$(element).closest('.form-group').addClass('has-error');
				},
				success: function (label, element) {
					var icon = $(element).parent('.input-icon').children('i');
					$(element).closest('.form-group').removeClass('has-error').addClass('has-success'); // set success class to the control group
					icon.removeClass("fa-warning").addClass("fa-check");
				},
				submitHandler: function (form) {
					if( submitHandler ){
						submitHandler(form);
					}else{
						console.log("submitHandler is empty !");
					}
				}
			});
		},//end
		showModal:function(url){ //显示弹出层
			var modal = $(".modal-dialog");
			//清空modal下面的html
			modal.empty();
			modal.load( url );
			$(modal).parent().modal("show");
		},//end
		hideModal:function(url){ //显示弹出层
			var modal = $(".modal-dialog");
			//清空modal下面的html
			modal.empty();
			modal.load( url );
			$(modal).parent().modal("hide");
		},
		bindAjaxSelect:function(opts){
			var url = opts.url,data = opts.data,dom = opts.dom,fieldId = opts.fieldId,fieldName = opts.fieldName;
			var	defaultOpt = opts.defaultOpt;

			if( !(dom && dom.length > 0) ){
				console.log( "dom is not find !");
				return ;
			}
			if( defaultOpt ){
				dom.append("<option value=" + defaultOpt.fieldId + ">" + defaultOpt.fieldName + "</option>");
			}
			dms.ajaxUtil(url,data,function(result){
				if( result && dom.length > 0){
					dom.empty();
					if( defaultOpt ){
						dom.append("<option value=" + defaultOpt.fieldId + ">" + defaultOpt.fieldName + "</option>");
					}
					for(var i = 0 ; i < result.length;i++){
						dom.append("<option value=" + result[i][fieldId] + ">" + result[i][fieldName] + "</option>");
					}
				}
			});
		},
		dataTableInit:function(ajaxUrl,opts){
            if (!jQuery().dataTable) {
                return;
            }

            var defaultOpts = {
	                "iDisplayLength": 20,//页大小
	                "sPaginationType": "bootstrap",
					"bFilter": false, //搜索栏
					"bLengthChange": false, //每页显示的记录数
					"bSort": false, //是否支持排序功能
					"bInfo": true, //显示表格信息
					//"sAjaxSource":ajaxUrl,
					"aLengthMenu":["Jack", 10],
					"bPaginate": true,  //是否显示分页
					"bProcessing" : true,//加载数据时候是否显示进度条
					"bServerSide" : true,//是否从服务加载数据
					"sServerMethod":'POST',//请求方式
	                "oLanguage": {
	                    "sLengthMenu": "每页显示 _MENU_ 条记录",
						"sZeroRecords": "对不起，查询不到任何相关数据",
						"sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
						"sInfoEmpty": "找不到相关数据",
						"sInfoFiltered": "数据表中共为 _MAX_ 条记录)",
						"sProcessing": "正在加载中...",
	                    "oPaginate": {
	                        "sPrevious": "上一页",
	                        "sNext": "下一页"
	                    }
	                },
	                "aoColumnDefs": [{
	                        'bSortable': false,
	                        'aTargets': [0]
	                    }
	                ]
					//end
	            }
            if(opts){
            	$.each(opts,function(key,val){
            		defaultOpts[key] = val;
				});
            }
            var oTable = $('#dataTable').dataTable(defaultOpts);
            //设置多选框,清理查询框等dataTable公共操作
			jQuery('#dataTable .group-checkable').change(function () {
                var set = jQuery(this).attr("data-set");
                var checked = jQuery(this).is(":checked");
                jQuery(set).each(function () {
                    if (checked) {
                        $(this).attr("checked", true);
                    } else {
                        $(this).attr("checked", false);
                    }
                    $(this).parents('tr').toggleClass("active");
                });
                jQuery.uniform.update(set);

            });
			//jQuery('#group_table_wrapper .row-fluid').eq(0).html('');// 清除查询框
            jQuery('#dataTable_wrapper .dataTables_filter input').addClass("form-control input-small"); // modify table search input
            jQuery('#dataTable_wrapper .dataTables_length select').addClass("form-control input-xsmall"); // modify table per page dropdown
            jQuery('#dataTable_wrapper .dataTables_length select').select2(); // initialize select2 dropdown
		},
		createdEditBtn:function(url,data){
			var editBtn = $("<a>").attr("class","btn btn-xs grey").attr("href","javascript:;");
			editBtn.append( $("<i>").attr("class","fa fa-edit").text("") )
			editBtn.attr("data-toggle","tooltip").attr("data-placement","top").attr("title","编辑");
			if(url.indexOf("?") > -1){
				url += "&_" + new Date().getTime()
			}else{
				url += "?_" + new Date().getTime()
			}
//			alert(JSON.stringify(data));
//			if( data != null ){
//				$.each(data,function(key,val){
//					url += "&" + key + "=" + val;
//				});
//			}
			editBtn.click(function(){
				$(".modal-hidden-param-input").val( JSON.stringify(data));
				dms.showModal(url);
			});
			return editBtn;
		},
		createdHandleBtn:function(url,data){
			var editBtn = $("<a>").attr("class","btn btn-xs grey").attr("href","javascript:;");
			editBtn.append( $("<i>").attr("class","fa fa-edit").text("") )
			editBtn.attr("data-toggle","tooltip").attr("data-placement","top").attr("title","处理");
			if(url.indexOf("?") > -1){
				url += "&_" + new Date().getTime()
			}else{
				url += "?_" + new Date().getTime()
			}
//			if( data != null ){
//				$.each(data,function(key,val){
//					url += "&" + key + "=" + val;
//				});
//			}
			editBtn.click(function(){
				$(".modal-hidden-param-input").val( JSON.stringify(data));
				dms.showModal(url);
			});
			return editBtn;
		},
		createdDeltBtn:function(url,data,dataTable){
			var delBtn = $("<a>").attr("class","btn btn-xs grey").attr("href","javascript:;");
			delBtn.append( $("<i>").attr("class","fa fa-trash-o").text("") );
			delBtn.attr("data-toggle","tooltip").attr("data-placement","top").attr("title","删除");
			if(url.indexOf("?") > -1){
				url += "&_" + new Date().getTime()
			}else{
				url += "?_" + new Date().getTime()
			}
			if( data != null ){
				$.each(data,function(key,val){
					url += "&" + key + "=" + val;
				});
			}

			delBtn.click(function(){
				bootbox.confirm("确认删除选择的项?", function(result) {
					console.log( JSON.stringify ( result ));

					if(result == true || result == "true"){

						dms.ajaxUtil(url,data,function(result){
							//console.log( JSON.stringify ( result ));

							if( result && (result.result == true || result.result == "true") ){
								bootbox.alert("删除成功!",function(){
									var table = $('#dataTable').dataTable();
									if(dataTable){
										table = dataTable;
									}
									table.fnDraw();
		            			});
							}else{
								bootbox.alert("删除失败!" + result.msg);
							}
						});
					}
                });
			});
			return delBtn;
		}
		//end
	};
}();
