/*********************************************/
/*************** 简历页面js脚本 ***************/
/*********************************************/

$(function () {
	
	//初始化浮动功能贴条
	initFloatTips();
});

var tips;
var theTop = 0/*这是默认高度,越大越往下*/;
var old = theTop;
function initFloatTips() {
	tips = document.getElementById('floatTips');
	moveTips();
};
function moveTips() {
	var tt = 50;
	if (window.innerHeight) {
		pos = window.pageYOffset
	} else if (document.documentElement
			&& document.documentElement.scrollTop) {
		pos = document.documentElement.scrollTop
	} else if (document.body) {
		pos = document.body.scrollTop;
	}
	pos = pos - tips.offsetTop + theTop;
	pos = tips.offsetTop + pos / 10;
	if (pos < theTop)
		pos = theTop;
	if (pos != old) {
		tips.style.top = pos + "px";
		tt = 10;
	}
	old = pos;
	setTimeout(moveTips, tt);
}

function daYin() {
	window.print();
	return false;
}

//打开发送邀请弹出窗
function pupopen(){
    $('#bg').css('display','block');
    $('#popbox').css('display','block');
}

//关闭发送邀请弹出窗
function pupclose(){
	$('#bg').css('display','none');
    $('#popbox').css('display','none');
}

//想某职位发送邀请
function sendInvite(){
	var rid = $('#resumeid').val();
	var jid = $('#choosenJob').val();
	if(jid ==0 || '0' == jid){
		alert('请选择推荐的岗位');
		$('#choosenJob').focus();
		return;
	}
	var leaveMessage = $('#leaveMessage').val();
	$.ajax({
		url: server.url + 'company/sendInvite',
		type:'post',
		data:{rid:rid,jid:jid,comment:leaveMessage},
		dataType:'json',
		success:function(data){
			if(data.notice == 'success'){
				alert('邀请发送成功!');
			}else{
				alert(data.notice);
			}
			//清空留言内容
			$('#leaveMessage').val('');
			pupclose();	//关闭窗口
		},
		error:function(){
			alert('发送邀请发生错误, 请重新尝试或者联系管理人员.');
		}
		
	});
	
	
	
}