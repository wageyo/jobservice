<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<style type="text/css">
	.span-width{
		display:-moz-inline-block;
		display:inline-block;
		width:200px;
		text-align:right;
	}
	#pwd-edit li{
		margin:5px;
		float:left;
	}
	#pwd-edit li a{
		margin:0px;
	}
</style>

<div class="manage">
	<div class="viwe-title" style="width:50%;margin: auto auto;">修改密码</div>
	<div id="" class="system" style="margin:20px auto;width:50%;text-align:center;">
		<!-- 修改密码 -->
		<ul id="pwd-edit">
			<li><span class="span-width">原&nbsp;&nbsp;密&nbsp;&nbsp;码:&nbsp;</span><input id="password" type="text" onfocus="password.focus_pwd()" onblur="password.blur_pwd()"/><span id="notice_password" style="text-align:left;color:red;font-size:10px;padding-left:5px;" class="span-width"></span></li>
			<li><span class="span-width">新&nbsp;&nbsp;密&nbsp;&nbsp;码:&nbsp;</span><input id="new_password" type="text" onfocus="password.focus_newpwd()" onblur="password.blur_newpwd()" /><span id="notice_new_password" style="text-align:left;color:red;font-size:10px;padding-left:5px;"  class="span-width"></span></li>
			<li><span class="span-width">确认密码:&nbsp;</span><input id="confirm_password" onblur="password.blur_conpwd()" type="text" /><span id="notice_confirm_password" style="text-align:left;color:red;font-size:10px;padding-left:5px;"  class="span-width"></span></li>
			<li style="text-align:center;width:100%;"><a class="blocklink" style="float:none;" href="javascript:password.submit();">确定</a></li>
		</ul>


		<script type="text/javascript">
		
		password={};
		var reg_pwd = /[a-zA-Z0-9_]{5,20}/;
		
		//原密码校验
		password.focus_pwd = function(){
			$('#notice_password').html('5-20位的英文,数字或下划线"_"');
		};
		password.blur_pwd = function(){
			var oldPassWord = $('#password').val();
			if(!reg_pwd.test(oldPassWord)){
				$('#notice_password').html('×');
			}else{
				$('#notice_password').html('√');
			}
		};
		
		//新密码校验
		password.focus_newpwd = function(){
			$('#notice_new_password').html('5-20位的英文,数字或下划线"_"');
		};
		password.blur_newpwd = function(){
			var newPassWord = $('#new_password').val();
			if(!reg_pwd.test(newPassWord)){
				$('#notice_new_password').html('×');
			}else{
				$('#notice_new_password').html('√');
			}
		};
		
		//确认密码
		password.blur_conpwd = function(){
			var newPassWord = $('#new_password').val();
			var confirm_password = $('#confirm_password').val();
			if(!reg_pwd.test(confirm_password)){
				$('#notice_confirm_password').html('×');
			}else{
				if(newPassWord == confirm_password){
					$('#notice_confirm_password').html('√');
				}else{
					$('#notice_confirm_password').html('两次输入的密码不一致');
				}
			}
		};
		
		/*
			提交修改密码
		*/
		password.submit=function(){

			var oldPassWord = $('#password').val();
			var newPassWord = $('#new_password').val();
			var confirm_password = $('#confirm_password').val();
			if(!reg_pwd.test(oldPassWord)){
				$('#notice_password').html('×');
				$('#password').focus();
				return;
			}
			if(!reg_pwd.test(newPassWord)){
				$('#notice_new_password').html('×');
				$('#new_password').focus();
				return;
			}
			if(!reg_pwd.test(confirm_password)){
				$('#notice_confirm_password').html('×');
				$('#confirm_password').focus();
				return;
			}
			if(newPassWord != confirm_password){
				$('#notice_confirm_password').html('两次输入的密码不一致');
				return;
			}
			$.ajax({
					url : '/jobservice/manage/password_edit',
						type:'post',	
						data:{'oldPassWord':oldPassWord,
							newPassWord:newPassWord	
						},
						success:function(data){
							if(data.notice == true || data.notice == 'true'){
								$('#password').val('');
								$('#new_password').val('');
								$('#confirm_password').val('');
								$('#notice_password').html('');
								$('#notice_new_password').html('');
								$('#notice_confirm_password').html('');
								alert("修改成功");
							}else{
								alert(data.notice);
							}
						},error:function(){
							alert("发生错误。");
						}
					});
		};
		
		
		</script>

	</div>
</div>


