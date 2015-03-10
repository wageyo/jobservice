<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
<div class="footer" id="footer">
	<div style="clear: both"></div>
	<!-- 脚部 begin -->
	<div class="footertext">
		<p>
			<a href="${contextPath }/index">首页</a>&nbsp;|&nbsp;
			<a href="http://www.hrbesd.com" target="_blank">关于我们</a>&nbsp;|&nbsp;
			<a href="#">意见反馈</a>&nbsp;|&nbsp;
			<a href="${contextPath }/contact" >联系我们</a>&nbsp;|&nbsp;
			<a href="javascript:alert('建设中');" target="_blank" title="站长统计">站长统计</a>&nbsp;|&nbsp;
			<a href="${contextPath }/loginManage/login" title="登陆管理后台" style="color:rgb(69, 69, 243);">管理后台</a>
		</p>
		<p>&copy; 2012 广西壮族自治区残疾人劳动就业指导中心</p>
		<p>联系电话：3186952/3186953 &nbsp;&nbsp;&nbsp;&nbsp;公司地址：南宁市罗文大道48号广西残疾人事业园 &nbsp;&nbsp;&nbsp;&nbsp;邮政编码：53001</p>
	</div>
	<!-- 脚部 end -->
	
	<!-- 脚部弹出层 begin -->
	<style type="text/css">
		.alertWindow{
			width: 100%;
			height: 100%; 
			background:rgba(0,0,0,0.5);
			position: fixed; 
			left:0px; 
			top: 0px; 
			z-index: 9999;
		}
		.alertWindow .alertWindowBody{
			width: 400px; 
			height: 200px;
			background: #FFF;
			margin: 180px auto;
			border: 3px solid #A396FF
		}
		.alertWindow .alertWindowBody .alertWindowcloseButtonDiv{
			width: inherit;
			height: 20px;
		}
		.alertWindow .alertWindowBody .alertWindowcloseButtonDiv .alertWindowCloseButton{
			float: right; 
			width: 10px; 
			height: 20px;
			margin-right:8px;
			margin-top: 5px;
			color:#6066FD;
			cursor: pointer;
		}
		.alertWindow .alertWindowBody .alertWindowcloseButtonDiv .alertWindowCloseButton:hover{
			font-size:15px;
			font-weight:bold;
		}
		.alertWindow .alertWindowBody .alertWindowTitle{
			margin-top:10px;
			text-align:center;
			font-size: 18px;
			font-weight: normal;
			color: #7866FF;
		}
		.alertWindow .alertWindowBody .alertWindowContent{
			width:360px;
			height: 60px;
			padding: 10px 20px;
			text-align:center;
			font-size: 15px;
			color: #7F7F7F;
		}
		.alertWindow .alertWindowBody .alertWindowSureClose{
			width: 100px;
			height: 40px;
			background:#7D6ACC;
			border:none;
			position: relative;
			bottom: 18px;
			font-size:18px;
			color:#FFFFFF;
			-webkit-border-radius: 10px;
			-moz-border-radius: 10px;
			border-radius: 5px;
			cursor: pointer;
		}
	</style>
	<script type="text/javascript">
		/*
		    alertWindow by yufu
		    消息框
		 */
		jQuery.extend({
		    alertWindow:function(title,content){
		        var title = title; //标题
		        var content = content; //内容
		        var color1= "#FF7C00"; //背景颜色
		        //查找body中是否存在该消息框
		        if($("body").find(".alertWindow").length===0){
		        //不存在
		            var alertHtml = '<div class="alertWindow">'+
		                                '<div class="alertWindowBody">'+
		                                    '<div class="alertWindowcloseButtonDiv">'+
		                                        '<div class="alertWindowCloseButton">X</div>'+
		                                    '</div>'+
		                                    '<h1 class="alertWindowTitle">'+title+'</h1>'+
		                                    '<div class="alertWindowContent">'+content+'</div>'+
		                                    '<p><input class="alertWindowSureClose" type="button" value="确  定" style=""></p>'+
		                                '</div>'+
		                           '</div>';
		            $("body").append(alertHtml);
		            /*
		             绑定事件
		             */
		            var $alertWindow = $(".alertWindow"); //窗口对象
		            //右上角关闭按钮
		            $(".alertWindowCloseButton").click(function(){
		                $alertWindow.hide();
		            });
		            //确定按钮
		            $(".alertWindowSureClose").click(function(){
		                $alertWindow.hide();
		            });
		        }else{
		        //存在
		            //设置标题
		            $(".alertWindowTitle").text(title);
		            //设置内容
		            $(".alertWindowContent").text(content);
		            //显示
		            $(".alertWindow").show();
		        }
		    }
		});
	</script>
	
	<c:if test="${message != null}">
		<input type="hidden" value="${messageType }" id="messageType" />
		<input type="hidden" value="${message }" id="message" />
		<script type="text/javascript">
			var message = $('#message').val();
			jQuery.alertWindow("提示信息",message);
		</script>
	</c:if>
	<!-- 脚部弹出层 end -->
	
</div>