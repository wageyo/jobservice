<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
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
		<p>&copy; 2013 hrbesd.com, Inc. 哈尔滨亿时代数码科技开发有限公司, All rights reserved. 黑ICP备10202513号</p>
	</div>
	<!-- 脚部 end -->
	
	<!-- 脚部弹出层 begin -->
	<script type="text/javascript">
		/*
		    alertWindow by taozhi
		    消息框
		 */
		jQuery.extend({
		    alertWindow:function(title,content,bgcolor){
		        var title = title; //标题
		        var content = content; //内容
		        var color1; //背景颜色
		        if(bgcolor===undefined){
		            color1 = "#FF7C00";
		        }else{
		            color1 = bgcolor;
		        }
		        //查找body中是否存在该消息框
		        if($("body").find(".alertWindow1").length===0){
		        //不存在
		            var alertHtml = '<div  class="alertWindow1" style="width: 100%;height: 100%; background:rgba(0,0,0,0.5);position: fixed; left:0px; top: 0px; z-index: 9999;">'+
		                                '<div  style="width: 400px; height: 200px;background: #FFF;margin: 180px auto;border: 2px solid #CFCFCF; border-bottom: 10px solid '+color1+';">'+
		                                    '<div  style="width: inherit;height: 20px;">'+
		                                        '<div class="alertWindowCloseButton1" style="float: right; width: 10px; height: 20px;margin-right:5px;font-family:\'microsoft yahei\';color:'+color1+';cursor: pointer;">X</div>'+
		                                    '</div>'+
		                                    '<h1 class="alertWindowTitle" style="margin-top:20px;text-align:center;font-family:\'宋体\';font-size: 18px;font-weight: normal;color: '+color1+';">'+title+'</h1>'+
		                                    '<div class="alertWindowContent" style="width:360px;px;height: 60px;padding-left:20px;padding-right:20px;text-align:center;font-size: 15px;color: #7F7F7F;">'+content+'</div>'+
		                                    '<p><input class="alertWindowCloseSure1" type="button" value="确定" style="width: 100px;height: 50px;background:'+color1+';border:none;position: relative;bottom: 18px;font-size:18px;color:#FFFFFF;-webkit-border-radius: 10px;-moz-border-radius: 10px;border-radius: 10px;cursor: pointer;"></p>'+
		                                '</div>'+
		                           '</div>';
		            $("body").append(alertHtml);
		            /*
		             绑定事件
		             */
		            var $alertWindow = $(".alertWindow1"); //窗口对象
		            //右上角关闭按钮
		            $(".alertWindowCloseButton1").click(function(){
		                $alertWindow.hide();
		            });
		            //确定按钮
		            $(".alertWindowCloseSure1").click(function(){
		                $alertWindow.hide();
		            });
		        }else{
		        //存在
		            //设置标题
		            $(".alertWindowTitle").text(title);
		            //设置内容
		            $(".alertWindowContent").text(content);
		            //显示
		            $(".alertWindow1").show();
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