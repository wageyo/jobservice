<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	
	<title>统计数据查询</title>
	<meta http-equiv="keywords" content="残疾人,就业,招聘">
	<meta http-equiv="description" content="残疾人就业招聘网站">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/bootstrap/css/bootstrap-combined.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/backdoor/main.css" />
	<script src="${contextPath}/js/bootstrap/js/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/lib/ichart/ichart.1.2.js"></script>
	<script src="${contextPath}/js/manage/common.js"></script>
</head>

<body>
	
	<!-- 整个页面div  开始 -->
	<div class="main-body">
	
		<!-- 头部 div -->
		<%@ include file="header.jsp" %>
		
		<!-- 中间主体div -->
		<div class="manage-body">
		
			<!-- 左侧菜单div -->
			<%@ include file="body-left.jsp" %>
			
			<!-- 右侧详细内容div begin -->
			<div class="manage-body-right">
			
				<div class="container">
				
					<!-- 下方结果显示框  开始 -->
					<div class="row">
						
						<!-- 显示企业招聘信息统计数据 begin -->
						<div class="span6" style="width: 90%;text-align: center;">
						<!-- 	<h3 class="text-success">
								企业招聘信息统计数据
							</h3> -->
						</div>
						<div class="span12" >
							<script>
							//定义数据
							/*var data = [
								{name : 'H',value : 7,color:'#a5c2d5'},
							   	{name : 'E',value : 5,color:'#cbab4f'},
							   	{name : 'L',value : 12,color:'#76a871'},
							   	{name : 'L',value : 12,color:'#76a871'},
							   	{name : 'O',value : 15,color:'#a56f8f'},
							   	{name : 'W',value : 13,color:'#c12c44'},
							   	{name : 'O',value : 15,color:'#a56f8f'},
							   	{name : 'R',value : 18,color:'#9f7961'},
							   	{name : 'L',value : 12,color:'#76a871'},
							   	{name : 'D',value : 4,color:'#6f83a5'}
							 ];	*/
							/**
							 * 异步请求后台数据
							 **/
							function getDataFromController(acode){
								var url = getRootPath() + '/manage/statistics/getStatistics/' + acode;
								$.ajax({
									url : url,
									type : 'POST',
									dataType : 'json',
									async : false,
									success : function(e) {
										//返回json类型数据
										drawChart(e.dataList,e.endScale,e.area);
									}
								});
							}
							/**
							 * 根据参数数据绘制图表
							 **/
							function drawChart(data,endScale,area){
								var scale_space = endScale/10;
								var chart = new iChart.Column2D({
									render : 'canvasDiv',//渲染的Dom目标,canvasDiv为Dom的ID
									data: data,//绑定数据
									title : area + '残疾人就业统计数据',//设置标题
									width : 1000,//设置宽度，默认单位为px
									height : 500,//设置高度，默认单位为px
									shadow:true,//激活阴影
									shadow_color:'#c7c7c7',//设置阴影颜色
									coordinate:{//配置自定义坐标轴
										scale:[{//配置自定义值轴
											 position:'left',//配置左值轴	
											 start_scale:0,//设置开始刻度为0
											 end_scale:endScale,//设置结束刻度为26
											 scale_space:scale_space,//设置刻度间距
											 listeners:{//配置事件
												parseText:function(t,x,y){//设置解析值轴文本
													return {text:t+""};
												}
											}
										}]
									}
								});
								//调用绘图方法开始绘图
								chart.draw();
							}
							 $(function(){
								 //绘制图表, 并初始化数据
								 getDataFromController('${acode}');
								 
								 //右边地区栏添加点击事件
								 $('.div-statistics-area ul li span').click(function(){
									 //点击后样式改变
									 $(this).css({'background':'rgb(130, 110, 255)','color':'white'}).parent().siblings().find('span').css({'background':'white','color':'black'});
									 getDataFromController($(this).attr('acode'));
								 });
								 
								// drawChart(data);
							});
							
							</script>
							<!-- 显示图表数据的div区域 -->
							<div id='canvasDiv' style="float:left;"></div>
							<div class="div-statistics-area">
								<ul class="unstyled">
									<c:forEach items="${areaList }" var="area" varStatus="i">
										<li style="text-indent: 2em;height:25px;text-indent:25px;">
											<span acode="${area.code }" 
											<c:if test="${i.index == 0 }">style="background:rgb(130, 110, 255);color:white"</c:if>
											 >${area.name }</span>
										<li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<!-- 显示企业招聘信息统计数据  end -->
						
					</div>
					<!-- 下方结果显示框  开始 -->
					
				</div>
			</div>
			<!-- 右侧详细内容div end  -->
			
			<!-- 让body-right div的高度跟随内容自动变化 -->
			<div style="font: 0px/0px sans-serif;clear: both;display: block"> </div>
		</div>
		
		<!-- 尾部div -->
		<%@ include file="footer.jsp" %>
		
	</div>
	<!-- 整个页面div  结束 -->
</body>
</html>
