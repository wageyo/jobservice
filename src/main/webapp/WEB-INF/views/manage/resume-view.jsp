<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<br /><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div id="job-view" class="jobView manage" >
	<div class="viwe-title">查看简历信息</div>
	<div style="margin: auto;width: 1000px">
		<dl>
			<dt>
				<br /> <span class="textTitle"> 基本信息</span>
			</dt>
		</dl>
		<dl>
			<dt>简历名称:</dt>
			<dd id="title" class="longText"></dd>
		</dl>
		<dl>
			<dt>姓名:</dt>
			<dd id="name"></dd>

			<dt>性别:</dt>
			<dd id="gender"></dd>

			<dt>出生日期:</dt>
			<dd id="birth"></dd>
		</dl>
		<dl>
			<dt>身份证号:</dt>
			<dd id="identityCard"></dd>

			<dt>民族:</dt>
			<dd id="race"></dd>

			<dt>婚姻状况:</dt>
			<dd id="marriage"></dd>
		</dl>
		<dl>
			<dt>邮政编码:</dt>
			<dd id="zipCode"></dd>
			<dt>电话:</dt>
			<dd id="phone"></dd>
			<dt>email:</dt>
			<dd id="email"></dd>
		</dl>
		<dl>
			<dt>qq:</dt>
			<dd id="qq"></dd>
			<dt>残疾类别:</dt>
			<dd id="disabilityCategory"></dd>
			<dt>残疾证号:</dt>
			<dd id="disabilityCard"></dd>
		</dl>
		<dl>
			<dt>残疾级别:</dt>
			<dd id="disabilityLevel"></dd>
			<dt>残疾部位:</dt>
			<dd id="disabilityPart"></dd>
		</dl>
		<dl>
			<dt>有无劳动能力:</dt>
			<dd >
					<div class="textField"  id="workAbility"></div>
			</dd>
		</dl>
		<dl>
			<dt>籍贯:</dt>
			<dd id="homeTown"></dd>
			<dt>政治面貌:</dt>
			<dd id="politicalStatus"></dd>
			<dt>年龄:</dt>
			<dd id="age"></dd>
		</dl>
		<dl>
			<dt>身高 cm:</dt>
			<dd id="height"></dd>
			<dt>体重 cm:</dt>
			<dd id="weight"></dd>
			<dt>户口所在地:</dt>
			<dd id="hukou"></dd>
		</dl>
		<dl>
			<dt>户口状况:</dt>
			<dd id="hukouStatus"></dd>
		</dl>
		<dl>
			<dt>户籍地址:</dt>
			<dd id="hukouAddress" class="longText"></dd>
		</dl>
		<dl>
			<dt>详细住址:</dt>
			<dd id="address" class="longText"></dd>
		</dl>
		<dl>
			<dt>
				<br /> <span class="textTitle"> 教育背景</span>
			</dt>
		</dl>
		<dl>
			<dt>毕业学校:</dt>
			<dd id="school" class="longText"></dd>
		</dl>
		<dl>
			<dt>学历:</dt>
			<dd id="education"></dd>
			<dt>专业:</dt>
			<dd id="major"></dd>
			<dt>特长:</dt>
			<dd id="experts"></dd>
		</dl>
		<dl>
			<dt>职称:</dt>
			<dd id="zhiCheng"></dd>
			<dt>就业失业登记证:</dt>
			<dd id="shiYeHao"></dd>
		</dl>
		<dl>
			<dt>培训情况:</dt>
			<dd >
					<div class="textField"  id="training"></div>
			</dd>
		</dl>
		<dl>
			<dt>
				<br /> <span class="textTitle"> 求职意向</span>
			</dt>
		</dl>
		<dl>
			<dt>工作性质:</dt>
			<dd id="jobNature"></dd>
			<dt>期望职位:</dt>
			<dd id="desireJob"></dd>
			<dt>期望工作地点:</dt>
			<dd id="desireAddress"></dd>
		</dl>
		<dl>
			<dt>其他:</dt>
			<dd >
			<div class="textField" id="provideOther"></div>
			</dd>
		</dl>
		<dl>
			<dt>目前状态:</dt>
			<dd>
				<div class="textField" id="state"></div>
			</dd>
		</dl>
		<dl>
			<dt>
				<br /> <span class="textTitle"> 工作经历</span>
			</dt>
		</dl>
		<dl>
			<dt>工作经历:</dt>
			<dd>
				<div class="textField" id="workExperience"></div>
			</dd>
		</dl>
		<dl>
			<dt>自我评价:</dt>
			<dd>
				<div class="textField" id="selfEvaluation"></div>
			</dd>
		</dl>
	</div>
</div>
