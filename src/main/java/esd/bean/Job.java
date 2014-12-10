package esd.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 招聘职位类,企业发布的招聘信息
 * 
 * @author Administrator
 * 
 */
@Component
public class Job extends PrimaryKey {

	private String name; // 职位名称
	private Integer hireNumber; // 招聘人数
	private String salary; // 薪水
	private String education; // 最低学历
	private String experience; // 要求的工作经验年限
	private String gender; // 性别
	private String age; // 年龄限制
	private String description; // 岗位描述
	private String provideBenefit; // 工资和其他福利
	private String contactPerson; // 联系人
	private String contactTel; // 联系电话
	private String contactEmail; // 联系邮箱
	private Integer viewCount; // 被浏览次数
	private String nature; // 职位性质
	private Integer effectiveDays; // 有效天数--提供给前台传递后台 数据使用,返回前台时, 则表示剩余天数
	private Boolean isActiveEffectiveTime; // 是否查询 超过有效期--查询使用, 表示是否开启有效天数 使用
	private Date effectiveTime; // 岗位有效日期截至

	private Area workPlace; // 工作地
	private boolean bed; // 是否提供住宿
	private boolean lunch; // 是否提供工作餐
	private String checkStatus; // 是否显示/是否通过审核
	private String mark; // 关键字

	private Company company; // 归属公司
	private Area area; // 职位所属地区外键
	private JobCategory jobCategory; // 职位类别

	// 显示用
	private String salaryScope; // 查询用的salary范围
	
	private String targetName; // 查询条件

	public Job() {
	}

	public Job(Integer id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "Job [name=" + name + ", hireNumber=" + hireNumber + ", salary="
				+ salary + ", education=" + education + ", experience="
				+ experience + ", gender=" + gender + ", description="
				+ description + ", provideBenefit=" + provideBenefit
				+ ", contactPerson=" + contactPerson + ", contactTel="
				+ contactTel + ", contactEmail=" + contactEmail
				+ ", viewCount=" + viewCount + ", nature=" + nature
				+ ", effectiveDays=" + effectiveDays
				+ ", isActiveEffectiveTime=" + isActiveEffectiveTime
				+ ", effectiveTime=" + effectiveTime + ", workPlace="
				+ workPlace + ", bed=" + bed + ", lunch=" + lunch
				+ ", checkStatus=" + checkStatus + ", mark=" + mark
				+ ", company=" + company + ", area=" + area + ", jobCategory="
				+ jobCategory + ", salaryScope=" + salaryScope + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHireNumber() {
		return hireNumber;
	}

	public void setHireNumber(Integer hireNumber) {
		this.hireNumber = hireNumber;
	}

	public String getSalary() {
		//处理, 防止前台数据显示不规范
				if(salary == null){
					salary = "";
				}
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getGender() {
		//处理, 防止前台数据显示不规范
		if(gender == null){
			gender = "";
		}
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProvideBenefit() {
		return provideBenefit;
	}

	public void setProvideBenefit(String provideBenefit) {
		this.provideBenefit = provideBenefit;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public Integer getEffectiveDays() {
		return effectiveDays;
	}

	public void setEffectiveDays(Integer effectiveDays) {
		this.effectiveDays = effectiveDays;
	}

	public Boolean getIsActiveEffectiveTime() {
		return isActiveEffectiveTime;
	}

	public void setIsActiveEffectiveTime(Boolean isActiveEffectiveTime) {
		this.isActiveEffectiveTime = isActiveEffectiveTime;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Area getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(Area workPlace) {
		this.workPlace = workPlace;
	}

	public boolean isBed() {
		return bed;
	}

	public void setBed(boolean bed) {
		this.bed = bed;
	}

	public boolean isLunch() {
		return lunch;
	}

	public void setLunch(boolean lunch) {
		this.lunch = lunch;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public JobCategory getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(JobCategory jobCategory) {
		this.jobCategory = jobCategory;
	}

	public String getSalaryScope() {
		return salaryScope;
	}

	public void setSalaryScope(String salaryScope) {
		this.salaryScope = salaryScope;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	
}
