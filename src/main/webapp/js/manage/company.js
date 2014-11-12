/***********************************************
 *******  后台管理页面框架脚本--企业管理  ********
 ***********************************************/

//收集参数并进入后台查询方法
function query(page,checkStatus){
	var paramStr = '';	//查询字符串
	var totalPage = $('#totalPage').val();
	if(page == null || page == undefined || page <= 1 || page == ''){
		paramStr += 'page=1'; 
	}else if(page >= totalPage){
		paramStr += 'page=' + totalPage;
	}else{
		paramStr += 'page=' + page;
	}
	var targetName = $('#targetName').val();
	if(targetName != null && targetName != undefined && targetName != ''){
		paramStr += '&targetName=' + targetName; 
	}
	if(checkStatus == null || checkStatus == undefined || checkStatus == ''){
		var checkStatus = $('#checkStatus').val();
		paramStr += '&checkStatus=' + checkStatus; 
	}else{
		paramStr += '&checkStatus=' + checkStatus; 
	}
	// 跳转提交
	window.location.href = getRootPath() + '/manage/company/company_list?' + paramStr;
}

//参数下拉框点击事件
function selectButton(valueButton, nameButton, value, name){
	//给对应控件 赋value值
	$('#'+valueButton).val(value);
	//给对应控件 赋name值
	$('#'+nameButton).text(name);
}

//保存, 通过, 拒绝, 返回  综合方法
function updateEntity(submitType,objId){
	//返回
	if(submitType == 'return'){
		window.history.back();
		return;
	}
	//拒绝
	if(submitType == 'refuse'){
		$.ajax({
			url:server.url + 'manage/company/refuse/'+objId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.notice == 'success'){
					alert('操作成功!');
					//重新载入页面--以刷新
					window.location.reload();
					return;
				}else{
					alert(data.notice);
				}
			}
		});
	}
	//通过
	if(submitType == 'approve'){
		$.ajax({
			url:server.url + 'manage/company/approve/'+objId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.notice == 'success'){
					alert('操作成功!');
					//重新载入页面--以刷新
					window.location.reload();
					return;
				}else{
					alert(data.notice);
				}
			}
		});
	}
	//保存
	if(submitType == 'save'){
		var param = checkObject();
		if(!param){
			return false;
		}
		$.ajax({
			url:server.url + 'manage/company/edit',
			type:'post',
			dataType:'json',
			data:{
				id:param.id,
				name:param.name,
				corporateRepresentative:param.corporateRepresentative,
				organizationCode:param.organizationCode,
				commercialCode:param.commercialCode,
				taxCode:param.taxCode,
				socialSecurityCode:param.socialSecurityCode,
				webSiteId:param.webSiteId,
				laoWangCode:param.laoWangCode,
				scale:param.scale,
				nature:param.nature,
				economyType:param.economyType,
				businessScope:param.businessScope,
				introduction:param.introduction,
				telephone:param.telephone,
				contactPerson:param.contactPerson,
				contactDept:param.contactDept,
				fax:param.fax,
				email:param.email,
				address:param.address
			},
			success:function(data){
				if(data.notice == 'success'){
					alert('保存成功!');
				}else{
					alert(data.notice);
				}
			}
		});
		
	}
	return;
}

//校验提交表单中所有控件方法
function checkObject(){
	var param  = new Object();
	var objId = $('#objId').val();
	if(objId == null || objId == ''){
		alert('传递的参数有误, 请刷新页面重新尝试.');
		return false;
	}
	param.id = objId;
	var companyName = $('#companyName').val();
	if(companyName == null || companyName == ''){
		$('#companyName').focus();
		return fasle;
	}
	param.name = companyName;
	var corporateRepresentative = $('#corporateRepresentative').val();
	if(corporateRepresentative != null && corporateRepresentative != ''){
		param.corporateRepresentative = corporateRepresentative;
	}
	var organizationCode = $('#organizationCode').val();
	if(organizationCode != null && organizationCode != ''){
		param.organizationCode = organizationCode;
	}
	var commercialCode = $('#commercialCode').val();
	if(commercialCode == null || commercialCode == ''){
		alert('填写工商登记号码.');
		$('#commercialCode').focus();
		return fasle;
	}
	var taxCode = $('#taxCode').val();
	if(taxCode != null && taxCode != ''){
		param.taxCode = taxCode;
	}
	var socialSecurityCode = $('#socialSecurityCode').val();
	if(socialSecurityCode != null && socialSecurityCode != ''){
		param.socialSecurityCode = socialSecurityCode;
	}
	var webSiteId = $('#webSiteId').val();
	if(webSiteId != null && webSiteId != ''){
		param.webSiteId = webSiteId;
	}
	var laoWangCode = $('#laoWangCode').val();
	if(laoWangCode != null && laoWangCode != ''){
		param.laoWangCode = laoWangCode;
	}
	var scale = $('#scale').val();
	if(scale != null && scale != ''){
		param.scale = scale;
	}
	var nature = $('#nature').val();
	if(nature != null && nature != ''){
		param.nature = nature;
	}
	var economyType = $('#economyType').val();
	if(economyType != null && economyType != ''){
		param.economyType = economyType;
	}
	var businessScope = $('#businessScope').val();
	if(businessScope != null && businessScope != ''){
		param.businessScope = businessScope;
	}
	var introduction = $('#introduction').val();
	if(introduction != null && introduction != ''){
		param.introduction = introduction;
	}
	var telephone = $('#telephone').val();
	if(telephone != null && telephone != ''){
		param.telephone = telephone;
	}
	var contactPerson = $('#contactPerson').val();
	if(contactPerson != null && contactPerson != ''){
		param.contactPerson = contactPerson;
	}
	var contactDept = $('#contactDept').val();
	if(contactDept != null && contactDept != ''){
		param.contactDept = contactDept;
	}
	var fax = $('#fax').val();
	if(fax != null && fax != ''){
		param.fax = fax;
	}
	var email = $('#email').val();
	if(email != null && email != ''){
		param.email = email;
	}
	var address = $('#address').val();
	if(address != null && address != ''){
		param.address = address;
	}
	return param;
}