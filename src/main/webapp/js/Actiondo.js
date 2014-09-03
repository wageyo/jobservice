//默认的ajax为post/json
$.ajaxSetup({
    type: "POST"
    //dataType: 'json'
});
if(user_classtype == 1){
	user_regurl = '/user/companyreg'
}else{
	user_regurl = '/user/personreg'
}
var loginstr = '<div  id="mylayerlogin" class="popLogin_Main"><h3 class="popLogin_title">   <span style="float:right; font-size:14px; margin-right:10px;"><a id="layerloginclose" href="javascript:void(0);" onclick="hidelogin()">&times;</a></span> 用户登录</h3> <div class="popLogin">    <table width="100%" border="0" cellspacing="0" cellpadding="0"><tr><td width="80px" align="right">    用户名：　</td><td>    <input id="layerusername" type="text" /></td></tr><tr><td align="right">    密&nbsp;&nbsp;&nbsp;&nbsp;码：　</td><td>    <input id="layerpassword" type="password" /></td></tr><tr><td>    &nbsp;</td><td>    <input style="width: 60px; cursor: pointer;" type="button" id="layerlogin" value="登录" onclick="loginsite()" /><span><a href="/user/getpassword" target="_blank">忘记密码？</a></span></td></tr><tr><td>    &nbsp;</td><td>    <a href="'+user_regurl+'">注册新用户</a></td></tr>    </table></div></div>';
var jubaostr = '<div  id="jubaoform" class="popLogin_Main"><h3 class="popLogin_title">   <span style="float:right; font-size:14px; margin-right:10px;"><a id="jubaoclose" href="javascript:void(0);" onclick="hidelogin()">&times;</a></span> 信息举报：感谢您协助我们管理网站！</h3> <div class="popLogin">    <table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>    <td width="80px" align="right">联系手机：　    </td>    <td><input id="jubaotel" type="text" />    </td></tr><tr>    <td align="right">举报理由：　    </td>    <td><input id="jubaocontent" type="text" />    </td></tr><tr>    <td>&nbsp;    </td>    <td><input style="width: 60px; cursor: pointer;" type="button" id="layerjubao" value="提交" onclick="savejubao()" />  </td></tr>   </table></div></div>';
var vipstr = '<div  id="jobdownform" class="popLogin_Main"><h3 class="popLogin_title">   <span style="float:right; font-size:14px; margin-right:10px;"><a id="jubaoclose" href="javascript:void(0);" onclick="hidelogin()">&times;</a></span> VIP服务提醒：</h3> <div class="popLogin">    <table width="100%" border="0" cellspacing="0" cellpadding="0"><tr>    <td align="right">您的可下载简历数为零，不能下载简历。办理VIP会员，点击此处了解<a href="/about?id=6" target="_blank">VIP办理流程</a></td></tr>   </table></div></div>';

function showConfirm(showMsg) {
    if (showMsg != "")
        return confirm(showMsg);
    else
        return confirm("确定执行该操作？");
};
function loginform(isshowlogin){
	$.blockUI({ message: loginstr });
}
function jubao(){
	$.blockUI({ message: jubaostr });
}
function savejubao(){
	thisurl = window.location.href;
	tel = $.trim($("#jubaotel").val());
	content = $.trim($("#jubaocontent").val());
	var phonereg = /^1[3|4|5|8][0-9]\d{4,8}$/;
	if (!phonereg.test(tel)){
		alert("请输入正确的手机号码！"); return ;
	}
	if(content.length == 0){
		alert("请输入举报理由！"); return ;
	}
    $.ajax({
        url: "/user/savejubao",
		data:{url:thisurl,content:escape(content),tel:tel},
        success: function (result) {
			if(result.d.flag){
				alert(result.d.msg);
				hidelogin();
			}else{
				alert(result.d.msg);
			}
        }
    });
}
function checklogin(islogin,isshowlogin){
	if(islogin){
		return true;
	}else{
		if(isshowlogin == true){
			$.blockUI({ message: loginstr });
		}
		return false ;
	}
}
function hidelogin(){
        $("#layerusername").val("");
        $("#layerpassword").val("");
        $("#jubaotel").val("");
        $("#jubaocontent").val("");
        $.unblockUI();
        return false;
}
function loginsite() {
	var username = $.trim($("#layerusername").val()); var password = $("#layerpassword").val();
    if (username.length == 0) {
        alert("请输入用户名！");
        return false;
    }
    else if (password.length == 0) {
        alert("请输入密码！");
        return false;
    }

    $.ajax({
        url: "/user/login",
		data:{userName:encodeURIComponent(username),password:encodeURIComponent(password)},
        beforeSend: function() { 
           $(".loading").html("正在登录中. . .");
        },
        success: function(result) {
            if (result.d.flag == true) {
                window.location.reload();
            }
            else { 
                alert(result.d.msg);
            }
        },
        error: function(httpRequest, textStatus, errorThrown) {
            alert("登录过程出错，请重新登录！"); 
        }
    });
}
//个人保存职位搜索器
function AddSearchList(){
	var sea_obj = new Object();var sea_str = '';
	aa = $("#TbPosType").val();
	bb = $("#TbTrade").val();
	cc = $("#DdMonthlySalary").val();
	dd = $("#DdWorkAge").val();
	ee = $("#DdDegree").val();
	ff = $("#DdUpdateTime").val();
	gg = $("#DdJianZhi").val();
	hh = $("#TbSearch").val();
	if(sys_isarea == 1){ii = $("#DdArea").val();}else{ii = '';}
    $.ajax({
        url: "/actiondo/AddSearchList",
		data:{categories:aa,trade:bb,salary:cc,experience:dd,edu:ee,uptime:ff,jobtype:escape(gg),key:escape(hh),area2:ii,user_classtype:user_classtype},
        success: function (result) {
			if(checklogin(result.d.islogin,true)){
				reurl(result);
				return ;
			}
        }
    });
}
//个人关注公司
function collect(companyid){
    $.ajax({
        url: "/actiondo/collect",
		data:{companyid:companyid,user_classtype:user_classtype},
        success: function (result) {
			if(checklogin(result.d.islogin,true)){
				if(result.d.flag){
					$("#LibCollectCom").html("已经关注");
					$("#collect"+companyid).html(result.d.collnum);
				}else{
					reurl(result);
				}
				return ;
			}
        }
    });
}
//个人投送简历至公司
function sendresume(jobid){
	if(jobid != null){
		ids = jobid;
	}else{
		ids = get_allid(); 
	}
	if(ids.lenght == 0){
		alert("请勾选职位信息");
		return ;
	}
    $.ajax({
        url: "/actiondo/sendresume",
		data:{ids:ids,user_classtype:user_classtype},
        success: function (result) {
			if(checklogin(result.d.islogin,true)){
				reurl(result);
				return ;
			}
        }
    });
}
//跳转至完善资料页
function reurl(result){
	if(result.d.consummate == false){
		var conf = confirm(result.d.msg + ' 点击确定跳转至管理中心！');
		if(conf == true) {
			if(user_classtype == 1){
				window.location.href="/company/company/";
			}else{
				window.location.href="/person/resume/";
			}
		}
	}else{
		alert(result.d.msg);
	}
	return ;
}
//个人收藏职位
function addfav(jobid){
	if(jobid != null){
		ids = jobid;
	}else{
		ids = get_allid();  
	}
	if(ids.lenght == 0){
		alert("请勾选职位信息");
		return ;
	}
    $.ajax({
        url: "/actiondo/addfav",
		data:{ids:ids,user_classtype:user_classtype},
        success: function (result) {
			if(checklogin(result.d.islogin,true)){
				reurl(result);
				return ;
			}
        }
    });
}
//公司下载个人
function Jobbox_down(id){
    $.ajax({
        url: "/actiondo/Jobbox_down",
		data:{id:id,user_classtype:user_classtype},
        success: function (result) {
			if(checklogin(result.d.islogin,true)){
				if(result.d.flag){
					$("#person_tel").html("<img src='/show/tel?id="+result.d.msg+"'/>");
					$("#pernum").html(result.d.pernum);
				}else{
					if(result.d.msg == 'Jobbox_down'){
						$.blockUI({ message: vipstr });
					}else{
						reurl(result);
					}
				}
				return ;
			}
        }
    });
}
//公司收藏个人
function Jobbox_fav(id){
	if(id != null){
		id = id;
	}else{
		id = get_allid();  
	}
	if(id.lenght == 0){
		alert("请勾选个人简历");
		return ;
	}
    $.ajax({
        url: "/actiondo/Jobbox_fav",
		data:{id:id,user_classtype:user_classtype},
        success: function (result) {
			if(checklogin(result.d.islogin,true)){
				reurl(result);
				return ;
			}
        }
    });
}

/*
//个人查看企业联系方式
function loadcomtel(id){
    $.ajax({
        url: "/actiondo/loadcomtel",
		data:{id:id,user_classtype:user_classtype},
        success: function (result) {
			if(checklogin(result.d.islogin,false)){
				if(result.d.flag){
					$("#company_tel").html("<img src='/show/tel?id="+result.d.msg+"' style='vertical-align: text-bottom;'/>");
				}else{
					$("#company_tel").html(result.d.msg);
				}
				return ;
			}else{
				$("#company_tel").html("请登录个人帐号后查看"); return ;
			}
        }
    });
}
//企业查看个人联系方式
function loadpertel(id){
    $.ajax({
        url: "/actiondo/loadpertel",
		data:{id:id,user_classtype:user_classtype},
        success: function (result) {
			if(checklogin(result.d.islogin,false)){
				if(result.d.flag){
					$("#person_tel").html("<img src='/show/tel?id="+result.d.msg+"' style='vertical-align: text-bottom;'/>");
				}else{
					$("#person_tel").html(result.d.msg);
				}
				return ;
			}else{
				$("#person_tel").html("如需联系方式请下载该简历"); return ;
			}
        }
    });
}
*/


//是否显示loading
var isLoadShow = false;
var showAjaxId;

//初次加载
$(function () {

	$('.loading').hide();
    //ajax操作，开始时的操作
    $(".loading").ajaxStart(function () {
        isLoadShow = true;
        showAjaxId = setTimeout("if (isLoadShow) $('.loading').show();", 800);
    });

    //ajax操作，结束时的操作
    $(".loading").ajaxStop(function () {
        clearTimeout(showAjaxId);
        isLoadShow = false;
        $(this).hide();
    });

});