$(document).ready(function(){
	companyInit();
});
//初始化  
function companyInit() {  
	var businessLicenseImport = $('#businessLicenseImport');
    var businessLicense=	$('#businessLicense');
	var businessLicenseImg = $('#businessLicenseImg');
    g_AjxUploadImg(businessLicenseImport, businessLicense, businessLicenseImg);
	var institutionalFrameworkImport = $('#institutionalFrameworkImport');
    var institutionalFramework=	$('#institutionalFramework');
	var institutionalFrameworkImg = $('#institutionalFrameworkImg');
    g_AjxUploadImg(institutionalFrameworkImport, institutionalFramework, institutionalFrameworkImg);
	var taxRegistrationImport = $('#taxRegistrationImport');
    var taxRegistration=	$('#taxRegistration');
	var taxRegistrationImg = $('#taxRegistrationImg');
    g_AjxUploadImg(taxRegistrationImport, taxRegistration, taxRegistrationImg);

	//保存按钮点击事件
	$('#SaveAllBtn').click(function(){
		$('#companyInfo').submit();
	});
}
function g_AjxUploadImg(btn, img, newsImage) {  
	//定义绑定上传按钮事件
	var button = btn;
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
				'imageId':img.val()
			});
			
		},
		onComplete : function(file,response){
			var result = response.substring(0,7);
			if(result != 'success'){
				alert('上传图片失败,'+response);
			}else{
				//刷新新上传的图片
				var id = response.substring(7);
				newsImage.attr('src','');
				newsImage.attr('src',server.url + 'image/downloadPic/'+id);
				newsImage.val(id);
				img.val(id) ;
				
			}
			button.val('上传图片');
			this.enable();
		}
	});
}
function check(){
	//姓名
	var name = $('#name').val();
	if(name == null || name == ''){
		alert('单位名称不能为空!');
		$('#name').focus();
		return false;
	}
	//法人代表
	var corporateRepresentative = $('#corporateRepresentative').val();
	if(corporateRepresentative == null || corporateRepresentative == ''){
		alert('法人代表不能为空!');
		$('#corporateRepresentative').focus();
		return false;
	}
	// 组织机构代码
	var organizationCode = $('#organizationCode').val();
	if(organizationCode == null || organizationCode == ''){
		alert('组织机构代码不能为空!');
		$('#organizationCode').focus();
		return false;
	}	
	//税务编码
	var taxCode = $('#taxCode').val();
	if(taxCode == null || taxCode == ''){
		alert('税务编码不能为空!');
		$('#taxCode').focus();
		return false;
	}	
	//电话/手机
	var telephone = $('#telephone').val();
	if(telephone == null || telephone == ''){
        alert('电话/手机号码不能为空!');
		$('#telephone').focus();
		return false;
	}
	//联系人
	var contactPerson = $('#contactPerson').val();
	if(contactPerson == null || contactPerson == ''){
		alert('请填写联系人!');
		$('#contactPerson').focus();
		return false;
	}
	return true;
}