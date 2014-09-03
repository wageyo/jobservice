package esd.bean;

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
	private String description; // 岗位描述
	private String provideBenefit; // 工资和其他福利
	private String contactPerson; // 联系人
	private String contactTel; // 联系电话
	private String contactEmail; // 邮箱
	private Integer viewCount; // 被浏览次数
	private String nature; // 职位性质
	private String effectiveTime; // 有效期
	private boolean bed; // 是否提供住宿
	private boolean lunch; // 是否提供工作餐
	private String checkStatus; // 是否显示/是否通过审核
	private String mark; // 关键字
	private String workPlace; // 工作地

	private Area area; // 职位所属地区外键
	private Company company; // 归属公司
	private JobCategory jobCategory; // 职位类别
	// 显示用
	private String salaryScope; // 查询用的salary范围

	@Override
	public String toString() {
		return "Job [name=" + name + ", hireNumber=" + hireNumber + ", salary="
				+ salary + ", education=" + education + ", experience="
				+ experience + ", gender=" + gender + ", description="
				+ description + ", provideBenefit=" + provideBenefit
				+ ", contactPerson=" + contactPerson + ", contactTel="
				+ contactTel + ", contactEmail=" + contactEmail
				+ ", viewCount=" + viewCount + ", nature=" + nature
				+ ", effectiveTime=" + effectiveTime + ", bed=" + bed
				+ ", lunch=" + lunch + ", checkStatus=" + checkStatus
				+ ", mark=" + mark + ", area=" + area + ", company=" + company
				+ ", jobCategory=" + jobCategory + ", salaryScope="
				+ salaryScope + "]";
	}

	public Job() {
	}

	public Job(Integer id) {
		this.setId(id);
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

	public String getProvideBenefit() {
		return provideBenefit;
	}

	public void setProvideBenefit(String provideBenefit) {
		this.provideBenefit = provideBenefit;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

}
