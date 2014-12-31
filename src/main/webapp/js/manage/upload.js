/***********************************************
 *******  后台管理页面框架脚本--上传图片用  ********
 ***********************************************/

$(document).ready(function() {
	//定义绑定上传按钮事件
	var button = $('#picFileImport');
	/*
	 * 异步 上传图片方法函数
	 */
	new AjaxUpload(button, {
		action: server.url + 'image/uploadNewsPic',
		name: 'pic',// 更改上传的文件名
		autoSubmit:true,
		type:'POST',
		data: {},
		onSubmit : function(file , ext){
			button.val('上传中...');
			/**
			 *	①验证上传文件格式
			 **/
			
	/*		if(!(ext && /^(jpg|xlsx)$/.test(ext))){
				$.messager.alert('提示','您上传的文件格式不对, 或者不是excel文件, 请重新选择','info');
				$('#picfileTitle').val('');
				return false;
			}	*/
			/**
			 *	②设置上传参数
			 **/
			this.setData({
				'imageId':$('#imageId').val()
			});
			
		},
		onComplete : function(file,response){
			var result = response.substring(0,7);
			if(result != 'success'){
				alert('上传图片失败,'+response);
			}else{
				//刷新新上传的图片
				var id = response.substring(7);
				$('#newsImage').attr('src','');
				$('#newsImage').attr('src',server.url + 'image/downloadPic/'+id);
				$('#imageId').val(id);
			}
			button.val('上传图片');
			this.enable();
		}
	});
});


	
	