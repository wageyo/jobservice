/*
 * Copyright (c) 2014 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package esd.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import esd.bean.Company;
import esd.bean.Job;
import esd.bean.Resume;
import esd.dao.AreaDao;

public class PoiCreateExcel {

	@Autowired
	private static AreaDao aDao;

	/**
	 * 导出职位信息
	 * 
	 * @param FilePath
	 * @param jobList
	 * @return
	 */
	public static boolean createJobExcel(String FilePath, List<Job> jobList) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建一个头部Excel的单元格
		HSSFRow headRow = sheet.createRow(0);
		HSSFCell headell = headRow.createCell(0);
		// 设置单元格的样式格式
		headell = headRow.createCell(0);
		headell.setCellValue("职位名称");
		headell = headRow.createCell(1);
		headell.setCellValue("招聘人数");
		headell = headRow.createCell(2);
		headell.setCellValue("薪水");
		headell = headRow.createCell(3);
		headell.setCellValue("最低学历");
		headell = headRow.createCell(4);
		headell.setCellValue("要求的工作经验年限");
		headell = headRow.createCell(5);
		headell.setCellValue("性别");

		headell = headRow.createCell(6);
		headell.setCellValue("年龄限制");
		headell = headRow.createCell(7);
		headell.setCellValue("岗位描述");
		headell = headRow.createCell(8);
		headell.setCellValue("工资和其他福利");
		headell = headRow.createCell(9);
		headell.setCellValue("联系人");
		headell = headRow.createCell(10);
		headell.setCellValue("联系电话");

		headell = headRow.createCell(11);
		headell.setCellValue("联系邮箱");
		headell = headRow.createCell(12);
		headell.setCellValue("被浏览次数");
		headell = headRow.createCell(13);
		headell.setCellValue("职位性质");
		headell = headRow.createCell(14);
		headell.setCellValue("有效天数");

		headell = headRow.createCell(15);
		headell.setCellValue("是否查询 超过有效期");
		headell = headRow.createCell(16);
		headell.setCellValue("岗位有效日期截至");
		headell = headRow.createCell(17);
		headell.setCellValue("工作地");
		headell = headRow.createCell(18);
		headell.setCellValue("是否提供住宿");
		headell = headRow.createCell(19);
		headell.setCellValue("是否提供工作餐");
		
		headell = headRow.createCell(20);
		headell.setCellValue("是否显示/是否通过审核");
		headell = headRow.createCell(21);
		headell.setCellValue("关键字");
		headell = headRow.createCell(22);
		headell.setCellValue("归属公司");
		headell = headRow.createCell(23);
		headell.setCellValue("职位所属地区外键");
		headell = headRow.createCell(24);
		headell.setCellValue("职位类别");

		
		for (int i = 1; i <= jobList.size(); i++) {
			Job job = jobList.get(i - 1);
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i);
			HSSFCell cell = row.createCell(0);
			// 设置单元格的样式格式
			cell = row.createCell(0);
			cell.setCellValue(job.getName());
			cell = row.createCell(1);
			cell.setCellValue(job.getHireNumber());
			cell = row.createCell(2);
			cell.setCellValue(job.getSalary());
			cell = row.createCell(3);
			cell.setCellValue(job.getEducation());
			cell = row.createCell(4);
			cell.setCellValue(job.getExperience());
			cell = row.createCell(5);
			cell.setCellValue(job.getGender());
			
			cell = row.createCell(6);
			cell.setCellValue(job.getAge());
			cell = row.createCell(7);
			cell.setCellValue(job.getDescription());
			cell = row.createCell(8);
			cell.setCellValue(job.getProvideBenefit());
			cell = row.createCell(9);
			cell.setCellValue(job.getContactPerson());
			cell = row.createCell(10);
			cell.setCellValue(job.getContactTel());
			
			cell = row.createCell(11);
			cell.setCellValue(job.getContactEmail());
			if(job.getViewCount()!=null){
				cell = row.createCell(12);
				cell.setCellValue(job.getViewCount());
			}
			cell = row.createCell(13);
			cell.setCellValue(job.getNature());
			cell = row.createCell(14);
			cell.setCellValue(job.getEffectiveDays());
			cell = row.createCell(15);
			//	cell.setCellValue(job.getIsActiveEffectiveTime());
		
			if(job.getEffectiveTime() != null){
				SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
				String effectiveTimeString=  dateFm.format(job.getEffectiveTime());
					cell = row.createCell(16);
					cell.setCellValue(effectiveTimeString);
			}
			
			if (job.getWorkPlace() != null) {
				if (job.getWorkPlace().getCode() != null
						&& !"".equals(job.getWorkPlace().getCode())) {
					cell = row.createCell(17);
					cell.setCellValue(job.getWorkPlace().getName());
				}
			}
			
			if(job.isBed()==true){
				cell = row.createCell(18);
				cell.setCellValue("是");
			}else if(job.isBed()==false){
				cell = row.createCell(18);
				cell.setCellValue("否");
			}
			
			if(job.isLunch()==true){
				cell = row.createCell(19);
				cell.setCellValue("是");
			}else if(job.isLunch()==false){
				cell = row.createCell(19);
				cell.setCellValue("否");
			}
			
			cell = row.createCell(20);
			cell.setCellValue(job.getCheckStatus());
			cell = row.createCell(21);
			cell.setCellValue(job.getMark());
			
			if (job.getCompany() != null) {
			
					cell = row.createCell(22);
					cell.setCellValue(job.getCompany().getName());
			}
			if (job.getArea() != null) {
				if (job.getArea().getCode() != null
						&& !"".equals(job.getArea().getCode())) {
					cell = row.createCell(23);
					cell.setCellValue(job.getArea().getName());
				}
			}
			
			if (job.getJobCategory() != null) {
				if (job.getJobCategory().getCode() != null
						&& !"".equals(job.getJobCategory().getCode())) {
					cell = row.createCell(24);
					cell.setCellValue(job.getJobCategory().getName());
				}
			}
		}
		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.flush();
			os.close();
			jobList.clear();
			jobList = null;
			os = null;
			wb = null;
			System.gc();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 导出单位信息
	 * 
	 * @param FilePath
	 * @param companyList
	 * @return
	 */
	public static boolean createComapnyExcel(String FilePath, List<Company> companyList) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建一个头部Excel的单元格
		HSSFRow headRow = sheet.createRow(0);
		HSSFCell headell = headRow.createCell(0);
		// 设置单元格的样式格式
		headell = headRow.createCell(0);
		headell.setCellValue("公司名称");
		headell = headRow.createCell(1);
		headell.setCellValue("法人");
		headell = headRow.createCell(2);
		headell.setCellValue("联系人");
		headell = headRow.createCell(3);
		headell.setCellValue("联系电话");
		headell = headRow.createCell(4);
		headell.setCellValue("联系部门");
		headell = headRow.createCell(5);
		headell.setCellValue("传真");

		headell = headRow.createCell(6);
		headell.setCellValue("公司邮箱");
		headell = headRow.createCell(7);
		headell.setCellValue("公司详细地址");
		headell = headRow.createCell(8);
		headell.setCellValue("公司简介");
		headell = headRow.createCell(9);
		headell.setCellValue("组织机构代码");
		headell = headRow.createCell(10);
		headell.setCellValue("工商登记号码");

		headell = headRow.createCell(11);
		headell.setCellValue("税务编码");
		headell = headRow.createCell(12);
		headell.setCellValue("社保登记证号");
		headell = headRow.createCell(13);
		headell.setCellValue("网站ID");
		headell = headRow.createCell(14);
		headell.setCellValue("市劳网号");
		headell = headRow.createCell(15);
		headell.setCellValue("企业规模");

		headell = headRow.createCell(16);
		headell.setCellValue("企业性质");
		headell = headRow.createCell(17);
		headell.setCellValue(" 经济类型");
		headell = headRow.createCell(18);
		headell.setCellValue("备注");
		headell = headRow.createCell(19);
		headell.setCellValue("被浏览次数");
//		headell = headRow.createCell(20);
//		headell.setCellValue("是否显示");
		
		for (int i = 1; i <= companyList.size(); i++) {
			Company company = companyList.get(i - 1);
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i);
			HSSFCell cell = row.createCell(0);
			// 设置单元格的样式格式
			//公司名称
			cell = row.createCell(0);
			cell.setCellValue(company.getName());
			// 法人
			cell = row.createCell(1);
			cell.setCellValue(company.getCorporateRepresentative());
			// 联系人
			cell = row.createCell(2);
			cell.setCellValue(company.getContactPerson());
			// 联系电话
			cell = row.createCell(3);
			cell.setCellValue(company.getTelephone());
			// 联系部门
			cell = row.createCell(4);
			cell.setCellValue(company.getContactDept());
			// 传真
			cell = row.createCell(5);
			cell.setCellValue(company.getFax());
			// 公司邮箱
			cell = row.createCell(6);
			cell.setCellValue(company.getEmail());
			// 公司详细地址
			cell = row.createCell(7);
			cell.setCellValue(company.getAddress());
			//  公司简介
			cell = row.createCell(8);
			cell.setCellValue(company.getIntroduction());
			// 组织机构代码
			cell = row.createCell(9);
			cell.setCellValue(company.getOrganizationCode());
			//工商登记号码
			cell = row.createCell(10);
			cell.setCellValue(company.getCommercialCode());
			// 税务编码
			cell = row.createCell(11);
			cell.setCellValue(company.getTaxCode());
			// 社保登记证号
			cell = row.createCell(12);
			cell.setCellValue(company.getSocialSecurityCode());
			// 网站ID
			cell = row.createCell(13);
			cell.setCellValue(company.getWebSiteId());
			//  市劳网号
			cell = row.createCell(14);
			cell.setCellValue(company.getLaoWangCode());
			//  企业规模
			cell = row.createCell(15);
			cell.setCellValue(company.getScale());
			// 企业性质
			cell = row.createCell(16);
			cell.setCellValue(company.getNature());
			// 经济类型
			cell = row.createCell(17);
			cell.setCellValue(company.getEconomyType());
			// 备注
			cell = row.createCell(18);
			cell.setCellValue(company.getRemark());
			// 被浏览次数
			if(company.getViewCount()!=null){
				cell = row.createCell(19);
				cell.setCellValue(company.getViewCount());
			}
//			cell = row.createCell(20);
//			cell.setCellValue(company.getCheckStatus());
			
		}
		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.flush();
			os.close();
			companyList.clear();
			companyList = null;
			os = null;
			wb = null;
			System.gc();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 导出简历信息
	 * 
	 * @param FilePath
	 * @param resumeList
	 * @return
	 */
	public static boolean createResumeExcel(String FilePath, List<Resume> resumeList) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建一个头部Excel的单元格
		HSSFRow headRow = sheet.createRow(0);
		HSSFCell headell = headRow.createCell(0);
		// 设置单元格的样式格式
		headell = headRow.createCell(0);
		headell.setCellValue("简历名称");
		headell = headRow.createCell(1);
		headell.setCellValue("姓名");
		headell = headRow.createCell(2);
		headell.setCellValue("性别");
		headell = headRow.createCell(3);
		headell.setCellValue("出生日期");
		headell = headRow.createCell(4);
		headell.setCellValue("身份证号");
		headell = headRow.createCell(5);
		headell.setCellValue("民族");

		headell = headRow.createCell(6);
		headell.setCellValue("婚姻状况");
		headell = headRow.createCell(7);
		headell.setCellValue("户籍所在地");
		headell = headRow.createCell(8);
		headell.setCellValue("户口地址");
		headell = headRow.createCell(9);
		headell.setCellValue("户口状况");
		headell = headRow.createCell(10);
		headell.setCellValue("详细住址");

		headell = headRow.createCell(11);
		headell.setCellValue("邮政编码");
		headell = headRow.createCell(12);
		headell.setCellValue("电话");
		headell = headRow.createCell(13);
		headell.setCellValue("电子邮箱");
		headell = headRow.createCell(14);
		headell.setCellValue("QQ号码");
		headell = headRow.createCell(15);
		headell.setCellValue("残疾类别");

		headell = headRow.createCell(16);
		headell.setCellValue("残疾证号");
		headell = headRow.createCell(17);
		headell.setCellValue(" 残疾级别");
		headell = headRow.createCell(18);
		headell.setCellValue("残疾部位");
		headell = headRow.createCell(19);
		headell.setCellValue("有无劳动能力");
		headell = headRow.createCell(20);
		headell.setCellValue("籍贯");
		
		

		headell = headRow.createCell(21);
		headell.setCellValue("政治面貌");
		headell = headRow.createCell(22);
		headell.setCellValue("年龄");
		headell = headRow.createCell(23);
		headell.setCellValue("身高 cm");
		headell = headRow.createCell(24);
		headell.setCellValue("体重 kg");
		headell = headRow.createCell(25);
		headell.setCellValue("就业状况");

		headell = headRow.createCell(26);
		headell.setCellValue("学历");
		headell = headRow.createCell(27);
		headell.setCellValue("专业");
		headell = headRow.createCell(28);
		headell.setCellValue("毕业学校及专业");
		headell = headRow.createCell(29);
		headell.setCellValue("职称");
		headell = headRow.createCell(30);
		headell.setCellValue("就业失业登记证 号");

		headell = headRow.createCell(31);
		headell.setCellValue("特长");
		headell = headRow.createCell(32);
		headell.setCellValue("培训情况");
		headell = headRow.createCell(33);
		headell.setCellValue("工作年限");
		headell = headRow.createCell(34);
		headell.setCellValue("工作经历");
		headell = headRow.createCell(35);
		headell.setCellValue("自我评价/职业目标");

		headell = headRow.createCell(36);
		headell.setCellValue("简历附件");
		headell = headRow.createCell(37);
		headell.setCellValue("工作性质");
		headell = headRow.createCell(38);
		headell.setCellValue("期望职位");
		headell = headRow.createCell(39);
		headell.setCellValue("期望工作地点");
		headell = headRow.createCell(40);
		headell.setCellValue("期望工资");
		
		headell = headRow.createCell(41);
		headell.setCellValue("是否提供食宿");
		headell = headRow.createCell(42);
		headell.setCellValue("是否提供住宿");
		headell = headRow.createCell(43);
		headell.setCellValue("提供工作餐");
		headell = headRow.createCell(44);
		headell.setCellValue("提供保险");
		headell = headRow.createCell(45);
		headell.setCellValue("其他");

		headell = headRow.createCell(46);
		headell.setCellValue("可否三班");
		headell = headRow.createCell(47);
		headell.setCellValue("目前状态");
		headell = headRow.createCell(48);
		headell.setCellValue("是否是默认被选中要投递的简历");
		headell = headRow.createCell(49);
		headell.setCellValue("审核情况");

		headell = headRow.createCell(50);
		headell.setCellValue("被浏览次数");
		headell = headRow.createCell(51);
		headell.setCellValue("职业测评情况");
		for (int i = 1; i <= resumeList.size(); i++) {
			Resume resume = resumeList.get(i - 1);
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i);
			HSSFCell cell = row.createCell(0);
			// 设置单元格的样式格式
			cell = row.createCell(0);
			cell.setCellValue(resume.getTitle());
			cell = row.createCell(1);
			cell.setCellValue(resume.getName());
			cell = row.createCell(2);
			cell.setCellValue(resume.getGender());
			cell = row.createCell(3);
			cell.setCellValue(resume.getBirth());
			cell = row.createCell(4);
			cell.setCellValue(resume.getIdentityCard());
			cell = row.createCell(5);
			cell.setCellValue(resume.getRace());
			
			cell = row.createCell(6);
			cell.setCellValue(resume.getMarriage());
			if (resume.getHukou() != null) {
				if (resume.getHukou().getCode() != null
						&& !"".equals(resume.getHukou().getCode())) {
					cell = row.createCell(7);
					cell.setCellValue(resume.getHukou().getName());
				}
			}
			
			cell = row.createCell(8);
			cell.setCellValue(resume.getHukouAddress());
			cell = row.createCell(9);
			cell.setCellValue(resume.getHukouStatus());
			cell = row.createCell(10);
			cell.setCellValue(resume.getAddress());
			
			cell = row.createCell(11);
			cell.setCellValue(resume.getZipcode());
			cell = row.createCell(12);
			cell.setCellValue(resume.getPhone());
			cell = row.createCell(13);
			cell.setCellValue(resume.getEmail());
			cell = row.createCell(14);
			cell.setCellValue(resume.getQq());
			cell = row.createCell(15);
			cell.setCellValue(resume.getDisabilityCategory());
			
			cell = row.createCell(16);
			cell.setCellValue(resume.getDisabilityCard());
			cell = row.createCell(17);
			cell.setCellValue(resume.getDisabilityLevel());
			cell = row.createCell(18);
			cell.setCellValue(resume.getDisabilityPart());
			cell = row.createCell(19);
			cell.setCellValue(resume.getWorkAbility());
			cell = row.createCell(20);
			cell.setCellValue(resume.getHomeTown());
			
			cell = row.createCell(21);
			cell.setCellValue(resume.getPoliticalStatus());
			cell = row.createCell(22);
			cell.setCellValue(resume.getAge());
			cell = row.createCell(23);
			cell.setCellValue(resume.getHeight());
			cell = row.createCell(24);
			cell.setCellValue(resume.getWeight());
			cell = row.createCell(25);
			cell.setCellValue(resume.getProcessState());
			
			cell = row.createCell(26);
			cell.setCellValue(resume.getEducation());
			cell = row.createCell(27);
			cell.setCellValue(resume.getMajor());
			cell = row.createCell(28);
			cell.setCellValue(resume.getSchool());
			cell = row.createCell(29);
			cell.setCellValue(resume.getZhiCheng());
			cell = row.createCell(30);
			cell.setCellValue(resume.getShiYeHao());
			
			cell = row.createCell(31);
			cell.setCellValue(resume.getExperts());
			cell = row.createCell(32);
			cell.setCellValue(resume.getTraining());
			cell = row.createCell(33);
			cell.setCellValue(resume.getExperience());
			cell = row.createCell(34);
			cell.setCellValue(resume.getWorkExperience());
			cell = row.createCell(35);
			
			cell.setCellValue(resume.getSelfEvaluation());
			cell = row.createCell(36);
			cell.setCellValue(resume.getAttachment());
			cell = row.createCell(37);
			cell.setCellValue(resume.getJobNature());
			if (resume.getDesireJob() != null) {
				if (resume.getDesireJob().getCode() != null
						&& !"".equals(resume.getDesireJob().getCode())) {
					cell = row.createCell(38);
					System.out.println(resume.getDesireJob().getName()+"********8");
					cell.setCellValue(resume.getDesireJob().getName());
				}
			}
			
			if (resume.getDesireAddress() != null) {
				if (resume.getDesireAddress().getCode() != null
						&& !"".equals(resume.getDesireAddress().getCode())) {
					cell = row.createCell(39);
					cell.setCellValue(resume.getDesireAddress().getName());
				}
			}
			
			
			cell = row.createCell(40);
			cell.setCellValue(resume.getDesireSalary());
			
		
			if(resume.isProvideFoodAndRoom()==true){
				cell = row.createCell(41);
				cell.setCellValue("是");
			}else if(resume.isProvideFoodAndRoom()==false){
				cell = row.createCell(41);
				cell.setCellValue("否");
			}
	
			
			if(resume.isProvideRoom()==true){
				cell = row.createCell(42);
				cell.setCellValue("是");
			}else if(resume.isProvideRoom()==false){
				cell = row.createCell(42);
				cell.setCellValue("否");
			}
			
			if(resume.isProvideFood()==true){
				cell = row.createCell(43);
				cell.setCellValue("是");
			}else if(resume.isProvideFood()==false){
				cell = row.createCell(43);
				cell.setCellValue("否");
			}
			if(resume.isProvideInsurance()==true){
				cell = row.createCell(44);
				cell.setCellValue("是");
			}else if(resume.isProvideInsurance()==false){
				cell = row.createCell(44);
				cell.setCellValue("否");
			}
			cell = row.createCell(45);
			cell.setCellValue(resume.getProvideOther());
			if(resume.isWorkShift()==true){
				cell = row.createCell(46);
				cell.setCellValue("是");
			}else if(resume.isWorkShift()==false){
				cell = row.createCell(46);
				cell.setCellValue("否");
			}
			cell = row.createCell(47);
			cell.setCellValue(resume.getState());
			
			if(resume.getIsDefault()==true){
				cell = row.createCell(48);
				cell.setCellValue("是");
			}else if(resume.getIsDefault()==false){
				cell = row.createCell(48);
				cell.setCellValue("否");
			}
			
			cell = row.createCell(49);
			cell.setCellValue(resume.getCheckStatus());
			
			if(resume.getViewCount()!=null){
				cell = row.createCell(50);
				cell.setCellValue(resume.getViewCount());
			}
			cell = row.createCell(51);
			cell.setCellValue(resume.getCareerTest());
			
		}
		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.flush();
			os.close();
			resumeList.clear();
			resumeList = null;
			os = null;
			wb = null;
			System.gc();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
