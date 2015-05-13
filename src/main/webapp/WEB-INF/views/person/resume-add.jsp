<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>添加简历</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="keywords" content="残疾人招聘信息,残疾人就业信息,残疾人人才网,残疾人找工作" />
	<meta content="残疾人招聘就业" name="description" />
	
	<link rel="shortcut icon" href="${contextPath}/images/HomePageImage/favicon.ico" type="image/x-icon" />
	<link href="${contextPath}/css/Public.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageHeader.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/HomePageFooter.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicStatusBar.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/SetHeaderStyle.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicframeFour.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicTableOne.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/PublicAccordion.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/ManagePositions.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/css/comp_info_edit.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/js/lib/ajaxupload.3.6.js"></script>
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${contextPath}/js/widget.js"></script>
	<script type="text/javascript" src="${contextPath}/js/check/resume-check.js"></script>
	<script type="text/javascript" src="${contextPath}/js/uploadAttachment.js"></script>
</head>
<body>
	<div id="container" class="container">
	
		<!-- 顶部工具条栏 -->
		<jsp:include page="../formatter/status-bar.jsp" />
		
		<!-- 头部导航及图片栏目 -->
		<jsp:include page="../formatter/header.jsp" />
		<div class="clearboth"></div>
		
		<!-- ******* 中部内容显示区 ******* start ********** -->
		<div id="content" class="content">
			
			<!-- 上部区域xx中心提示文字 -->
            <div class="positiontopbg">
                <div class="positiontopleft">
                    <span style="cursor:pointer;" onclick="javascript:window.location.href = '${contextPath}/user/goCenter'">个人管理中心</span></div>
                <div class="positiontopright">
                </div>
            </div>
            
            <!-- 下部具体内容显示   start -->
            <div class="positionbottom">
                <div class="positionleftline">
                </div>
                
                <!----------- 管理中心左边菜单栏  ------start ----------->
                <jsp:include page="left-nav.jsp" />
                <!----------- 管理中心左边菜单栏  ------end ----------->
                
                <div class="positionrightline">
                </div>
                <!----------- 管理中心右边 详细内容显示  ------start ----------->
                <div id="main0" class="positionmiddletext">
                    <div class="contraction" >
                        <div class="contractiontop">
                            <div class="contractionleft">
                            </div>
                            <div class="contractiontoptext">
                                <span class="spanFirst">当前位置:</span><span class="spanSecond">个人管理中心 &gt;&gt; 简历管理 &gt;&gt; 添加简历</span></div>
                            <div class="contractionright">
                            </div>
                            <div class="contractionclick">
                            </div>
                        </div>
                    </div>
                	
                	<form action="${contextPath }/secure/resume/save" method="post" onsubmit="return resume_check('save');" id="resumeInfo">
                        <div class="contraction" >
                        <!-- 第一部分 基本信息 begin -->
                        	<div class="contractiontop">
                                 <div class="contractionleft">
                                 </div>
                                 <div class="contractiontoptext">
                                     <span class="form_title">基本信息</span><span class="spanFirst">(*必填项)</span></div>
                                 <div class="contractionright">
                                 </div>
                                 <div class="contractionclickone">
                                 </div>
                             </div>
                            <div class="contractioncontent">
                                <table class="tbl1" >
                                    <tbody>
                                     <tr>
                                         <td class="textTop">
                                          		 <em style="display: inline;">* </em>简历标题：
                                         </td>
                                         <td>
                                         	<input id="title" name="title" type="text" value="" type="text" />
                                         	<input id="attachment" name="attachment" type="hidden" value="" />
                                         	<input type="button" name="file" value="上传附件" id="picFileImport" />
                                         </td>
                                     </tr>
                                     <tr>
                                         <td class="textTop">
                                          		 <em style="display: inline;">* </em>姓名：
                                         </td>
                                         <td>
                                         	<input id="name" name="name" type="text" value="" type="text" /> 请填写个人真实姓名，以便求职
                                         </td>
                                     </tr>
                                     <tr>
                                         <td class="textTop">
                                          		 性别：
                                         </td>
                                         <td>
                                         	<select name="gender" id="gender">
                                             	<c:forEach items="${params }" var="p">
													<c:if test="${p.type == 'gender' }">
														<option value="${p.value }">${p.name }</option>
													</c:if>
												</c:forEach>
                                             </select>
                                         </td>
                                     </tr>
                                     <tr>
                                         <td class="textTop">
                                          		 民族：
                                         </td>
                                         <td>
                                         	<input id="race" name="race" type="text" value="" type="text" />
                                         </td>
                                     </tr>
                                     <tr>
                                         <td class="textTop">
                                          		<em style="display: inline;">* </em>出生日期：
                                         </td>
                                         <td>
                                         	<select name="year" id="year" class="select_border" style="width:70px;">
												<option value="0">请选择</option>
												<c:forEach begin="1955" end="1999" var="i">
														<option value="${i}">${i }年</option>
												</c:forEach>
											</select>
											<select name="month" id="month" class="select_border" style="width:70px;">
												<option value="0">请选择</option>
												<c:forEach begin="1" end="12" var="i">
														<option value="${i}">${i }月</option>
												</c:forEach>
											</select>
											<select name="day" id="day" class="select_border" style="width:70px;" onchange="change_birth()">
												<option value="0">请选择</option>
												<c:forEach begin="1" end="31" var="i">
														<option value="${i}">${i }日</option>
												</c:forEach>
											</select>
											<input type="hidden" name="birth" id="birth" />
                                         </td>
                                     </tr>
                                     <tr>
                                         <td class="textTop">
                                          		 户口状况：
                                         </td>
                                         <td>
                                         	<select name="hukouStatus" id="hukouStatus">
                                             	<c:forEach items="${params }" var="p">
													<c:if test="${p.type == 'huKou' }">
														<option value="${p.value }">${p.name }</option>
													</c:if>
												</c:forEach>
                                             </select>
                                         </td>
                                     </tr>
                                     <tr>
                                         <td class="textTop">
                                          		户籍所在地：
                                         </td>
                                         <td>
                                         	<select name="area_lv12" id="area_lv12" class="select_border" style="width:150px;">
												<c:forEach items="${provinceList }" var="area">
														<option value="${area.code }">${area.name }</option>
												</c:forEach>
											</select>
											<select name="area_lv22" id="area_lv22" class="select_border" style="width:150px;">
												<option value="">请选择城市</option>
											</select>
											<select name="homeTown" id="area_lv32" class="select_border" style="width:150px;">
												<option value="">请选择区域</option>
											</select>
                                         </td>
                                     </tr>
                                     <tr>
                                         <td class="textTop">
                                          		婚姻状况：
                                         </td>
                                         <td>
                                         	<select name="marriage" id="marriage">
                                             	<c:forEach items="${params }" var="p">
													<c:if test="${p.type == 'marriage' }">
														<option value="${p.value }">${p.name }</option>
													</c:if>
												</c:forEach>
                                             </select>
                                         </td>
                                     </tr>
                                     <tr>
                                         <td class="textTop">
                                          		 户籍地址 ：
                                         </td>
                                         <td>
                                         	<input id="hukouAddress" name="hukouAddress" type="text" value="" type="text" style="width:300px;"/>
                                         </td>
                                     </tr>
                                     <tr>
                                         <td class="textTop">
                                          		 现居居地：
                                         </td>
                                         <td>
                                         	<input id="address" name="address" type="text" value="" type="text" style="width:300px;"/>
                                         </td>
                                     </tr>
                                    </tbody>
                                 </table>
                             </div>
                        	<div class="clearboth"></div>
                        <!-- 第一部分 基本信息 end -->
                        
                        <!-- 第二部分 联系方式  begin -->
                        	<div class="contractiontop">
                                 <div class="contractionleft">
                                 </div>
                                 <div class="contractiontoptext">
                                     <span class="form_title">联系方式</span><span class="spanFirst">(*必填项)</span></div>
                                 <div class="contractionright">
                                 </div>
                                 <div class="contractionclickone">
                                 </div>
                             </div>
                            <div class="contractioncontent">
                                <table class="tbl1" >
                                    <tbody>
	                                    <tr>
	                                         <td class="textTop">
	                                          		 电话/手机：
	                                         </td>
	                                         <td>
	                                         	<input id="phone" name="phone" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		 联系QQ：
	                                         </td>
	                                         <td>
	                                         	<input id="qq" name="qq" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		邮箱：
	                                         </td>
	                                         <td>
	                                         	<input id="email" name="email" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                 </tbody>
                                 </table>
                             </div>
                        	<div class="clearboth"></div>
                        <!-- 第二部分 联系方式 end -->
                         
                        <!-- 第三部分 残疾情况  begin -->
                        	<div class="contractiontop">
                                 <div class="contractionleft">
                                 </div>
                                 <div class="contractiontoptext">
                                     <span class="form_title">残疾情况</span><span class="spanFirst">(*必填项)</span></div>
                                 <div class="contractionright">
                                 </div>
                                 <div class="contractionclickone">
                                 </div>
                             </div>
                            <div class="contractioncontent">
                                <table class="tbl1" >
                                    <tbody>
	                                    <tr>
	                                         <td class="textTop">
	                                          		<em style="display: inline;">* </em> 残疾证号：
	                                         </td>
	                                         <td>
	                                         	<input id="disabilityCard" name="disabilityCard" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		<em style="display: inline;">* </em> 残疾类别：
	                                         </td>
	                                         <td>
	                                         	<select name="disabilityCategory" id="disabilityCategory">
	                                             	<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'disabilityCategory' }">
															<option value="${p.value }">${p.name }</option>
														</c:if>
													</c:forEach>
	                                             </select>
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		<em style="display: inline;">* </em> 残疾等级：
	                                         </td>
	                                         <td>
	                                         	<select name="disabilityLevel" id="disabilityLevel">
	                                             	<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'disabilityLevel' }">
															<option value="${p.value }">${p.name }</option>
														</c:if>
													</c:forEach>
	                                             </select>
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		<em style="display: inline;">* </em> 残疾部位：
	                                         </td>
	                                         <td>
	                                         	<select name="disabilityPart" id="disabilityPart">
	                                             	<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'disabilityPart' }">
															<option value="${p.value }">${p.name }</option>
														</c:if>
													</c:forEach>
	                                             </select>
	                                         </td>
	                                     </tr>
	                                 </tbody>
                                 </table>
                             </div>
                        	<div class="clearboth"></div>
                        <!-- 第三部分 残疾情况 end -->
                        
                        <!-- 第四部分 求职意向  begin -->
                        	<div class="contractiontop">
                                 <div class="contractionleft">
                                 </div>
                                 <div class="contractiontoptext">
                                     <span class="form_title">求职意向</span><span class="spanFirst">(*必填项)</span></div>
                                 <div class="contractionright">
                                 </div>
                                 <div class="contractionclickone">
                                 </div>
                             </div>
                            <div class="contractioncontent">
                                <table class="tbl1" >
                                    <tbody>
	                                    <tr>
	                                         <td class="textTop">
	                                          		就业失业登记证号：
	                                         </td>
	                                         <td>
	                                         	<input id="shiYeHao" name="shiYeHao" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		<em style="display: inline;">* </em> 职位类型：
	                                         </td>
	                                         <td>
	                                         	<select name="jobNature" id="jobNature">
	                                             	<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'jobNature' }">
															<option value="${p.value }">${p.name }</option>
														</c:if>
													</c:forEach>
	                                             </select>
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		<em style="display: inline;">* </em> 期望职位类别：
	                                         </td>
	                                         <td>
	                                         	<select name="jobCategory_lv1" id="jobCategory_lv1" class="select_border" style="width:150px;">
													<c:forEach items="${jcList }" var="p">
														<option value="${p.code }">${p.name }</option>
													</c:forEach>
												</select>
												<select name="jobCategory_lv2" id="jobCategory_lv2" class="select_border" style="width:150px;">
													<option value="">请选择</option>
												</select>
												<select name="desireJob" id="jobCategory_lv3" class="select_border" style="width:150px;">
													<option value="">请选择</option>
												</select>
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		<em style="display: inline;">* </em> 期望工作地：
	                                         </td>
	                                         <td>
	                                         	<select name="area_lv1" id="area_lv1" class="select_border" style="width:150px;">
													<c:forEach items="${provinceList }" var="area">
															<option value="${area.code }">${area.name }</option>
													</c:forEach>
												</select>
												<select name="area_lv2" id="area_lv2" class="select_border" style="width:150px;">
													<option value="">请选择城市</option>
												</select>
												<select name="desireAddress" id="area_lv3" class="select_border" style="width:150px;">
													<option value="">请选择区域</option>
												</select>
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		期望薪水：
	                                         </td>
	                                         <td>
	                                         	<select name="desireSalary" id="desireSalary">
	                                             	<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'salary' }">
															<option value="${p.value }">${p.name }</option>
														</c:if>
													</c:forEach>
	                                             </select>
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		其他福利待遇：
	                                         </td>
	                                         <td>
	                                         	<c:forEach items="${params }" var="p">
													<c:if test="${p.type == 'benefit' }">
														<input type="checkbox" name="${p.value}" value="1" />${p.name }&nbsp;&nbsp; 
													</c:if>
												</c:forEach>
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		其他要求：
	                                         </td>
	                                         <td>
	                                         	<textarea name="provideOther" id="provideOther" rows="2" cols="20" style="height:80px;width:619px;font-size: 12px; padding: 6px;"></textarea>
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		到岗时间：
	                                         </td>
	                                         <td>
	                                         	<c:forEach items="${params }" var="p">
												<c:if test="${p.type == 'state' }">
													<input type="radio" name="state" value="${p.value }" />${p.name }<br />
												</c:if>
											</c:forEach>
	                                         </td>
	                                     </tr>
	                                 </tbody>
                                 </table>
                             </div>
                        	<div class="clearboth"></div>
                        <!-- 第四部分 求职意向 end -->
                        
                        <!-- 第五部分 教育程度  begin -->
                        	<div class="contractiontop">
                                 <div class="contractionleft">
                                 </div>
                                 <div class="contractiontoptext">
                                     <span class="form_title">教育程度</span><span class="spanFirst">(*必填项)</span></div>
                                 <div class="contractionright">
                                 </div>
                                 <div class="contractionclickone">
                                 </div>
                             </div>
                            <div class="contractioncontent">
                                <table class="tbl1" >
                                    <tbody>
	                                    <tr>
	                                         <td class="textTop">
                                          		<em style="display: inline;">* </em> 学历：
	                                         </td>
	                                         <td>
	                                         	<select name="education" id="education">
	                                             	<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'education' }">
															<option value="${p.value }">${p.name }</option>
														</c:if>
													</c:forEach>
	                                             </select>
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
                                          		专业：
	                                         </td>
	                                         <td>
	                                         	<input id="major" name="major" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
                                          		毕业院校：
	                                         </td>
	                                         <td>
	                                         	<input id="school" name="school" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
                                          		特长：
	                                         </td>
	                                         <td>
	                                         	<input id="experts" name="experts" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
                                          		培训情况或需求：
	                                         </td>
	                                         <td>
	                                         	<input id="training" name="training" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
	                                          		自我评价：
	                                         </td>
	                                         <td>
	                                         	<textarea name="selfEvaluation" id="selfEvaluation" rows="2" cols="20" style="height:80px;width:619px;font-size: 12px; padding: 6px;"></textarea>
	                                         </td>
	                                     </tr>
	                                 </tbody>
                                 </table>
                             </div>
                        	<div class="clearboth"></div>
                        <!-- 第五部分 教育程度 end -->
                        
                        <!-- 第六部分 工作经历 begin -->
                        	<div class="contractiontop">
                                 <div class="contractionleft">
                                 </div>
                                 <div class="contractiontoptext">
                                     <span class="form_title">工作经历</span><span class="spanFirst">(*必填项)</span>, 点击右边的 <span style="color:blue;">"新增"</span> 按钮添加额外的工作经验</div>
                                 <div class="contractionright">
                                 </div>
                                 <div class="contractionclickone" style="padding-top:5px;">
									<a href="javascript:add_tr();"><span style="color: white;background-color: blue;letter-spacing: 2px;padding: 1px 5px;border-radius: 5px;">新增</span></a>
                                 </div>
                             </div>
                            <div class="contractioncontent">
                                <table class="tbl1" id="tableExperience">
                                    <tbody>
	                                	<tr>
	                                         <td class="textTop">
                                          		职业测评情况：
	                                         </td>
	                                         <td colspan="3">
	                                         	<input id="careerTest" name="careerTest" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
                                          		办理情况：
	                                         </td>
	                                         <td colspan="3">
	                                         	<input id="processState" name="processState" type="text" value="" type="text" />
	                                         </td>
	                                     </tr>
	                                     <tr>
	                                         <td class="textTop">
                                          		总工作年限：
	                                         </td>
	                                         <td colspan="3">
	                                         	<select name="experience" id="experience">
	                                             	<c:forEach items="${params }" var="p">
														<c:if test="${p.type == 'experience' }">
															<option value="${p.value }">${p.name }</option>
														</c:if>
													</c:forEach>
	                                             </select>
	                                         </td>
	                                     </tr>
	                                     
	                                     <!-- 工作经历样板 begin -->
	                                     <tr>
	                                     	<td class="textTop" style="text-align:center;">工作经历：<br/><span style="color:red;font-size:18px;">样板</span></td>
	                                     	<td colspan="3">
	                                     		<p>所在公司：<input style="color: gray;" name="example_companyName" type="text" value="哈尔滨达达科技有限公司" readonly="readonly"/>在职时间：<input style="color: gray;" name="example_workTime" type="text" value="2013.1.2~~2014.8.4" readonly="readonly"/></p>
	                                     		<p>所在职位：<input style="color: gray;" name="example_jobName" type="text" value="财务总监" readonly="readonly"/>离职原因：<input style="color: gray;" name="example_leaveReason" type="text" value="家庭有所变动, 需要到其他城市去." readonly="readonly"/></p>
	                                     		<p><span>自我评价：</span><textarea rows="4" cols="100" name="example_jobContent" style="color:gray;width:522px;" readonly="readonly">自我评价,所负责的事物等等.</textarea></p>
                                     		</td>
                                     	</tr>
	                                     
	                                     <!-- 工作经历样板 end -->
	                                 </tbody>
                                 </table>
                             </div>
                        	<div class="clearboth"></div>
                        <!-- 第六部分 工作经历 end -->
                         </div>
                         <div class="clearboth"></div>
                         <input class="saveBtn" id="SaveAllBtn" type="button" onclick="resume_save();"/>
					</form>
                    
                    <div class="clearboth"></div>
                </div>
                <!----------- 管理中心右边 详细内容显示  ------end ----------->
                
            </div>
            <!-- 下部具体内容显示   end -->
        




		</div>
		<!-- ******* 中部内容显示区 ******* end ********** -->
		
		<!-- 尾部footer区 -->
		<div class="clearboth"></div>
		<jsp:include page="../formatter/footer.jsp" />
	</div>
</body>
</html>