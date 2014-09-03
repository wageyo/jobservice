



var manageCommon={};

/*
 * 	审核 切换按钮状态
 * 待审核
 * */
manageCommon.selectStatus1=function(){
	$(".batchAudit").removeAttr("disabled");//启用通过按钮
	$(".batchRefusal").removeAttr("disabled");//启用拒绝按钮
};

/*
 * 	未通过
 * */
manageCommon.selectStatus2=function(){
	$(".batchRefusal").attr({"disabled":"disabled"});	//隐藏拒绝通过按钮
	$(".batchAudit").removeAttr("disabled");//启用通过按钮
};

/*
 * 	已通过
 * */
manageCommon.selectStatus3=function(){
	//显示拒绝通过按钮
	$(".batchRefusal").removeAttr("disabled");//启用拒绝按钮
	$(".batchAudit").attr({"disabled":"disabled"});	//禁用拒绝通过按钮
};