<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<div class="manage">
	<div class="viwe-title">系统设置</div>
	<div id="" class="system" style="margin-left: 150px;margin-top: 30px">

		<dl>
			<dt>审核内容:</dt>

		</dl>
		<dl>
			<dt>是否审核&nbsp“职位信息”:</dt>
			<dd id="">
				&nbsp审核<input type="radio" value="on" name="audit_job" /> &nbsp 不审核<input value="off" name="audit_job" type="radio" />
			</dd>
		</dl>
		<dl>
			<dt>是否审核&nbsp“企业信息”:</dt>
			<dd id="">
				&nbsp审核<input type="radio" value="on" name="audit_company" /> &nbsp 不审核<input type="radio" value="off" name="audit_company" />
			</dd>
		</dl>
		<dl>
			<dt>是否审核&nbsp“简历信息”:</dt>
			<dd id="">
				&nbsp审核<input type="radio" value="on" name="audit_resume" /> &nbsp 不审核<input type="radio" value="off" name="audit_resume" />
			</dd>
		</dl>
		<dl>
			<dt>是否审核&nbsp“账户信息”:</dt>
			<dd id="">
				&nbsp审核<input type="radio" value="on" name="audit_user" /> &nbsp 不审核<input type="radio" value="off" name="audit_user" />
			</dd>
		</dl>

		<dl>
			<dt></dt>

		</dl>
		<script type="text/javascript">
		
		systemManager={};
		systemManager.status=new Array();
		/*
			初始化 开关状态
		*/
		systemManager.init=function(){
			$.ajax({
					url : '/jobservice/manage/status',
						type:'post',	
						success:function(data){
						  	 $("input[name='audit_user']").val([data.user]);//用户
						     $("input[name='audit_company']").val([data.company]);//企业
						     $("input[name='audit_job']").val([data.job]);//职位
						     $("input[name='audit_resume']").val([data.resume]);//简历
						},error:function(){
							alert("获取菜单状态数据发生错误。");
						}
					});
		};
	
		/*
			设置 开关状态
		*/
		systemManager.submit=function(){

			var params=new Array();
			params.push($("input[name='audit_user']:checked").val());//用户
			params.push($("input[name='audit_company']:checked").val());//企业
			params.push($("input[name='audit_job']:checked").val());//职位
			params.push($("input[name='audit_resume']:checked").val());//简历
			$.ajax({
					url : '/jobservice/manage/setstatus',
						type:'post',	
						data:{'params':params},
						success:function(data){
							if(data==true){
								alert("设置成功");
							}else{
								alert("设置失败");
							}
						},error:function(){
							alert("发生错误。");
						}
					});
		};
			
		
		
		$(function(){
		systemManager.init();
		
		});
		
		</script>

		<div align="center">
			<a class="blocklink" style="margin-top: 20px" href="javascript:systemManager.submit();">确定</a>
		</div>
	</div>
</div>


