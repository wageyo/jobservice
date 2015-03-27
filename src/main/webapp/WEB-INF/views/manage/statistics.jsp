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
	<script src="${contextPath}/js/lib/jquery-1.11.1.js"></script>
	<script src="${contextPath}/js/bootstrap/js/bootstrap.js"></script>
	<script src="${contextPath}/js/lib/highcharts/highcharts.js"></script>
	<script src="${contextPath}/js/lib/highcharts/exporting.js"></script>
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
			
				<div class="container-fluid">
				
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
							 $(function(){
								 //汉化
								 Highcharts.setOptions({
									chart: {
										style: {
											fontFamily:'Microsoft YaHei, Arial'
										}
									},
						            lang: {
						            	//加载中ing 显示的文本 汉化
						            	loading:'加载中...',
						            	
						            	//导出相关文本汉化
						            	contextButtonTitle:'打印或导出图表',
										printChart:"打印图表",
										downloadJPEG: "下载JPEG格式图片" , 
										downloadPDF: "下载PDF文档"  ,
										downloadPNG: "下载PNG格式图片"  ,
										downloadSVG: "下载SVG格式矢量图" , 
										exportButtonTitle: "导出图片"
						            }
						        });
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
											//返回json类型数据, 根据数据绘制图表
											drawChart(e.dataList,e.area);
											//根据数据 生成表格
											var strTd ='<tr>';
											$.each(e.dataList,function(index,item){
												strTd += '<td>';
												strTd += item;
												strTd += '</td>'
											});
											strTd += '</tr>'
											$('.div-statistics-table tbody').html(strTd);
										}
									});
								}
								
								/**
								 * 根据参数数据绘制图表
								 **/
								function drawChart(dataList,area){
									$('#div-statistics-chart').highcharts({                   //图表展示容器，与div的id保持一致
								        chart: {
								            type: 'column'                         //指定图表的类型，默认是折线图（line）
								        },
								        title: {
								            text: area + '残疾人就业统计数据'      //指定图表标题
								        },
								        xAxis: {
								        	text: 'x轴标题',
								            categories: ['企业总数', '职位总数', '招聘人数','企业平均职位数','注册残疾人数','创建简历数','已就业人数']   //指定x轴分组
								        },
								        yAxis: {
								            title: {
								                text: ''                  //指定y轴的标题
								            }
								        },
								        series: [{                                 //指定数据列
								            name: area,                          //数据列名
								            data: dataList                      //数据
								        }]
								    });
								}
							
							</script>
							<!-- 显示图表数据的div区域 -->
							<div id='div-statistics-chart' style="float:left;width:700px;"></div>
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
							
							<div class="div-statistics-table">
								<table >
									<thead>
										<tr>
											<th>
												企业总数
											</th>
											<th>
												职位总数
											</th>
											<th>
												招聘人数
											</th>
											<th>
												企业平均职位数
											</th>
											<th>
												注册残疾人数
											</th>
											<th>
												创建简历数
											</th>
											<th>
												已就业人数
											</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
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
