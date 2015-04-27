$(document).ready(function(){
		//定义绑定上传按钮事件
		var button = $('#picFileImport');
		/*
		 * 异步 上传图片方法函数
		 */
		new AjaxUpload(button, {
			action: server.url + 'filegags/uploadFile',
			name: 'pic',// 更改上传的文件名
			autoSubmit:true,
			type:'POST',
			data: {},
			onSubmit : function(file , ext){
				button.val('上传图片中ing...');
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
					'userid':$('#userid').val()
				});
				
			},
			onComplete : function(file,response){
				var notice = response.substring(0,7);
		//		alert('notice: ' + notice);
				if(notice != 'success'){
					alert('上传图片失败,'+response);
				}else{
					//刷新新上传的图片
					var fileid = response.substring(7);
		//			alert('fileid: ' + fileid);
					$('#headImage').attr('src','');
					$('#headImage').attr('src',server.url + 'filegags/downloadFile/'+fileid);
				}
				button.val('上传图片');
				this.enable();
			}
		});
	});