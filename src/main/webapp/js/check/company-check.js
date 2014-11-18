

function company_check(){
	//姓名
	var name = $('#name').val();
	if(name == null || name == ''){
		alert('企业名称不能为空!');
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
	//工商登记证号
	var commercialCode = $('#commercialCode').val();
	if(commercialCode == null || commercialCode == ''){
		alert('工商登记号码不能为空!');
		$('#commercialCode').focus();
		return false;
	}
	//电话/手机
	var telephone = $('#telephone').val();
	if(telephone == null || telephone == ''){
        alert('电话/手机号码不能为空!');
		$('#telephone').focus();
		return false;
	}
/*	if(!verify.checkTel(telephone)){
        alert('请检查你输入的电话/手机号码!');
		$('#telephone').focus();
		return false;
	}	*/
	//联系人
	var contactPerson = $('#contactPerson').val();
	if(contactPerson == null || contactPerson == ''){
		alert('请填写联系人!');
		$('#contactPerson').focus();
		return false;
	}
	return true;
}