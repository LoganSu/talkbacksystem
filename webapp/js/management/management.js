$(function(){
	//客户服务已完成事件
//	$(document).on("click",".customerServiceFinish",function(){
//		$("#tableShowList").bootstrapTable('refresh', {
//			url: $path+"/mc/customerService/showList.do?status=finish",
//		});
//		$("#myModal").modal("hide");
//		return false;
//	})
	//客户服务未完成事件
//	$(document).on("click",".customerServiceUnfinish",function(){
//		$("#tableShowList").bootstrapTable('refresh', {
//			url: $path+"/mc/customerService/showList.do?status=unfinish",
//		});
//		$("#myModal").modal("hide");
//		return false;
//	})
	//工单服务已完成事件
	$(document).on("click",".repairsFinish",function(){
		$("#tableShowList").bootstrapTable('refresh', {
			url: $path+"/mc/repairs/showList.do?status=finish",
		});
		$("#myModal").modal("hide");
		return false;
	})
	//工单服务未完成事件
	$(document).on("click",".repairsUnfinish",function(){
		$("#tableShowList").bootstrapTable('refresh', {
			url: $path+"/mc/repairs/showList.do?status=unfinish",
		});
		$("#myModal").modal("hide");
		return false;
	})
	
	//工单服务正在处理
	$(document).on("click",".repairsFinishing",function(){
		$("#tableShowList").bootstrapTable('refresh', {
			url: $path+"/mc/repairs/showList.do?status=repairsFinishing",
		});
		$("#myModal").modal("hide");
		return false;
	})
	
	 //部门添加修改（跳转到保存页面）
	$(document).on("click",".departmentAdd",function(){
		var url = $(this).attr("rel");
		var title = $(this).html();
		var data="";
		//修改保存共用（修改需要传参数）
		if(title.indexOf("修改")!=-1){
			//获取选择的id
			 data= getSelectedIds();
			var selects = $("#tableShowList").bootstrapTable('getSelections');
			//ids= 长度小于4说明没有id
			 if(selects.length!=1){
				 hiAlert("提示","请选择一条操作数据！");
				 return false;
			 }
		}
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html(title);
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
		})
	})
		//公司确定按钮
	  $(document).on("click","#companyDepartmentsaveForm .sure",function(){
		   var url=$path+"/mc/department/companySaveOrUpdate.do";
		   var param = $("#companyDepartmentsaveForm").serialize();
		   var id = $("#companyDepartmentsaveForm input[name='id']").val();
		   
			 if(zTreeObj){
				 var nodes = zTreeObj.getCheckedNodes(true);
		    	  var arr = new Array();
		    	  $.each(nodes,function(i,obj){
		    		  arr.push(obj.id);
		    	  });
		    	  var parr = arr.join("&domainIds=");
		    	  var treecheckbox = "&domainIds="+parr;
		    	  param=param+treecheckbox;
		      }
		   $.post(url,param,function($data){
			   if($data){
				   hiAlert("提示",$data);
			   }else{
				   $("#tableShowList").bootstrapTable('refresh', {
					   url: $path+"/mc/department/showList.do"
				   });

				   //更新之后关闭弹出框
				   if(id){
					   $("#unnormalModal").modal("hide");
				   }else{
					   //添加之后清空输入框
					   var params = $(".departmentsaveForm").find(".form-control");
					   $.each(params,function(i,obj){
						   $(obj).val("");
					   })
				   }
				   var zTreeOnClick =  function(event, treeId, treeNode){
						$("#showRightArea").load($path+"/mc/department/departmentshowPage.do?module=departmentTable&modulePath=/department&parentId="+treeNode.id);
					 }

					  zTreeObj = zTree("managementDepartmentTree", ["id","name","level"],[],$path+"/mc/department/getNodes.do",false,{"Y": "ps", "N": "ps"},zTreeOnClick, null)
			   }
			 });
	   })
		//部门确定按钮
	  $(document).on("click","#departmentsaveForm .sure",function(){
		   var url=$path+"/mc/department/saveOrUpdate.do";
		   var param = $("#departmentsaveForm").serialize();
//		   var parentId = $("#departmentsaveForm input[name='parentId']").val();
		   var id = $("#departmentsaveForm input[name='id']").val();
		   $.post(url,param,function($data){
			   if($data){
				   hiAlert("提示",$data);
			   }else{
				 $("#tableShowList").bootstrapTable('refresh', {
						url: $path+"/mc/department/showList.do"
					});
				 zTreeObj.reAsyncChildNodes(null, "refresh");

				 //更新之后关闭弹出框
				 if(id){
					$("#unnormalModal").modal("hide");
				 }else{
					 //添加之后清空输入框
					 var params = $(".departmentsaveForm").find(".form-control");
					 $.each(params,function(i,obj){
						 $(obj).val("");
					 })
					 
				 }
				 var zTreeOnClick =  function(event, treeId, treeNode){
						$("#showRightArea").load($path+"/mc/department/departmentshowPage.do?module=departmentTable&modulePath=/department&parentId="+treeNode.id);
					 }
				  zTreeObj = zTree("managementDepartmentTree", ["id","name","level"],[],$path+"/mc/department/getNodes.do",false,{"Y": "ps", "N": "ps"},zTreeOnClick, null)
			   }
			 });
	   })
	   
	//分组管理 人员列表页面
	$(document).on("click",".workerGroupList",function(){
		var groupId = $(this).attr("rel");
		var url = $path+"/mc/workerGroup/toAddWorker.do";
		$.post(url,"id="+groupId,function(addHtml){
			var title = "人员列表";
	        $("#unnormalModalLabel").html(title);
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
		})
	})
	 //分组管理  添加人员按钮
	$(document).on("click","#workerGroupAddWorkersaveForm .sure",function(){
		   var url=$path+"/mc/workerGroup/addWorker.do";
		   var param = $("#workerGroupAddWorkersaveForm").serialize();
		   var groupId = $("#workerGroupAddWorkersaveForm .groupId").val();
			 var nodes = zTreeObj.getCheckedNodes(true);
			 var treecheckbox;
		      if(nodes){
		    	  var arr = new Array();
		    	  $.each(nodes,function(i,obj){
		    		  arr.push(obj.id);
		    	  });
		    	  var parr = arr.join("&treecheckbox=");
		    	  treecheckbox = "&treecheckbox="+parr;
		    	  param=param+treecheckbox;
		      }
		   $.post(url,param,function($data){
			   if($data){
				   hiAlert("提示",$data);
			   }else{
				 $("#workerGroupAddWorkerShowTable").bootstrapTable('refresh', {
					 url: $path+"/mc/workerGroup/showgroupWorkerList.do?id="+groupId
					});
//				 //更新之后关闭弹出框
//				 if(id){
//					$("#unnormalModal").modal("hide");
//				 }else{
//					 //添加之后清空输入框
//					 $.each(params,function(i,obj){
//						 $(obj).val("");
//					 })
//					 
//				 }
			   }
			 });
	})
	
	
})
//---- 方法-------------
//递归显示部门树
var showDepartmentTree = function(departmentTree,select){
	if(departmentTree){
		$.each(departmentTree,function(i,obj){
//			var select = $("#workerSearchForm .departmentId")
			var padding = obj.layer*20;
			var option='<option style="padding-left: '+padding+'px;" value="'+obj.id+'">'+obj.departmentName+'</option>';
			select.append(option);
			var subTree = obj.departmentTree;
			showDepartmentTree(subTree,select);
		});
	}
}