$(function(){
	//添加、修改
	$(document).on("click",".adPulishAdd",function(){
		var url =$path+"/mc/adPublish/toSaveOrUpdate.do";
		var title = $(this).html();
		var data="";
		//修改保存共用（修改需要传参数）
		if(title=="修改"){
			//获取选择的id
			 data= getSelectedIds();
			var selects = $("#tableShowList").bootstrapTable('getSelections');
			//ids= 长度小于4说明没有id
			 if(selects.length!=1){
				 hiAlert("提示","请选择一条操作数据！");
				 return false;
			 }
			 if(selects[0].publishOperator){
				 hiAlert("提示","已发布内容不能修改！");
				 return false;
			 }
			 //判断是否是本运营商信息
			 if(selects[0].selfStr=="否"){
				 hiAlert("提示","非本运营商信息不能修改！");
				 return false;
			}
		}
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html(title);
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
		})
	});
	//发布
	$(document).on("click",".adPulishPush",function(){
		var url =$path+"/mc/adPublish/publish.do";
		var but = $(this);
		var title = $(this).html();
		//获取选择的id
		var selects = $("#tableShowList").bootstrapTable('getSelections');
		//ids= 长度小于4说明没有id
		 if(selects.length!=1){
			 hiAlert("提示","请选择一条操作数据！");
			 return false;
		 }
		 for(var i=0;i<selects.length;i++){
			 var item = selects[i];
//			 alert(item.selfStr);
			if(item.selfStr=="否"){
				 hiAlert("提示","请选择本运营商发布的广告发布！");
				 return false;
			}
		 }
		 var ids = getSelectedIds();
		 
		 bootstrapQ.confirm('<span style="color:black;font-size:16px">您确定要发布吗？</span>',function(){
			 $.post(url,ids,function($data){
	            	if(!$data){
						refresh();
					}else{
						hiAlert("提示",$data);
					}
	            });
		},function(){
//			alert('点击了取消');
		});
		 
//		 but.scojs_confirm({
//		        content: "您确定要发布吗？",
//		        param:ids,
//		        action: function(param) {
		            
		            
//		        }
//		      });	
	});
	
	
	//行内详情
	$(document).on("click",".adPublishDetail",function(){
		var url =$path+"/mc/adPublish/toSaveOrUpdate.do";
		var id = $(this).attr("rel");
		var data="ids="+id+"&opraterType=1";
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("详情");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
		})
	});
	
	
	//行内修改
	$(document).on("click",".adPublishUpdate",function(){
		var url =$path+"/mc/adPublish/toSaveOrUpdate.do";
		var id = $(this).attr("rel");
		var data="ids="+id;
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("修改");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
		})
	});
	
	
	//行内删除
	$(document).on("click",".adPublishDelete",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/adPublish/delete.do";
			bootstrapQ.confirm('<span style="color:black;font-size:16px">您确定要删除记录吗？</span>',function(){
				 $.post(url,ids="+id",function($data){
		            	if(!$data){
							refresh();
						}else{
							hiAlert("提示",$data);
						}
		            });
			},function(){
//				alert('点击了取消');
			});
//		if(confirm("确定要删除该数据吗？")){
//			//获取已选择记录id
//			$.post(url,"ids="+id,function($data){
//				if(!$data){
//					refresh();
//				}else{
//					hiAlert("提示",$data);
//				}
//			})
//		}
	 })
	 //删除
	 $(document).on("click","#thumbnailDiv .btn-danger",function(){
 		var $this=$(this);
 		var picId = $this.parent().parent().parent().children("input[name='picId']").val();
 		if(picId){
 			$.post($path+'/mc/adPublish/deletePic.do','picId='+picId,function($data){
 				if($data){
 					hiAlert("提示",$data);
 				}else{
 					$this.parent().parent().parent().remove();
 				}
 				
 			});
 		}
 	 })
 	 
 	 
 	  //删除按钮
	 $(document).on("click",".adPulishPushDelete",function(){
			 var selects = $("#tableShowList").bootstrapTable('getSelections');
			 var but = $(this);
				//ids= 长度小于4说明没有id
				 if(selects.length<1){
					 hiAlert("提示","请选择一条操作数据！");
					 return false;
				 }
				 for(var i=0;i<selects.length;i++){
					 var item = selects[i];
//					 alert(item.selfStr);
					if(item.selfStr=="否"){
						 hiAlert("提示","请选择本运营商发布的广告删除！");
						 return false;
					}
				 }
				 var ids = getSelectedIds();
				var url = $path+"/mc/adPublish/delete.do";
				bootstrapQ.confirm('<span style="color:black;font-size:16px">您确定要删除记录吗？</span>',function(){
					 $.post(url,ids,function($data){
			            	if(!$data){
								refresh();
							}else{
								hiAlert("提示",$data);
							}
			            });
				},function(){
//					alert('点击了取消');
				});
				
		 })
})