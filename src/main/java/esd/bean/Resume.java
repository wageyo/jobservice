package esd.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 简历类
 * 
 * @author Administrator
 * 
 */
@Component
public class Resume extends PrimaryKey {

	// 基本信息字段
	private String title; // 简历名称
	private String name; // 姓名
	private String gender; // 性别
	private String birth; // 出生日期
	private String identityCard; // 身份证号
	private String race; // 民族
	private String marriage; // 婚姻状况
	private String hukou; // 户口所在地
	private String hukouAddress; // 户籍地址--即身份证上的地址
	private String hukouStatus; // 户口状况 --本市,农村等类别
	private String address; // 详细住址/现居住地
	private String zipcode; // 邮政编码
	private String phone; // 电话
	private String email;
	private String qq;
	private String disabilityCategory; // 残疾类别
	private String disabilityCard; // 残疾证号
	private String disabilityLevel; // 残疾级别
	private String disabilityPart; // 残疾部位
	private String workAbility; // 有无劳动能力
	private String homeTown; // 籍贯
	private String politicalStatus; // 政治面貌
	private Integer age; // 年龄--由出生日期得到
	private float height; // 身高 cm
	private float weight; // 体重 kg

	// 教育背景部分
	private String education; // 学历
	private String major; // 专业
	private String school; // 毕业学校及专业
	private String zhiCheng; // 职称
	private String shiYeHao; // 就业失业登记证 号
	private String experts; // 特长
	private String training; // 培训情况

	// 工作总年限
	private String experience; // 工作年限
	private String workExperience; // 工作经历

	// 自我评价
	private String selfEvaluation; // 自我评价/ 职业目标
	private String attachment; // 简历附件

	// 求职意向
	private String jobNature; // 工作性质
	private String desireJob; // 期望职位
	private String desireAddress; // 期望工作地点
	private String desireSalary; // 期望工资
	private boolean provideFoodAndRoom;// 是否提供食宿
	private boolean provideRoom; // 是否提供住宿
	private boolean provideFood; // 提供工作餐
	private boolean provideInsurance; // 提供保险
	private String provideOther; // 其他
	private boolean workShift; // 可否三班
	private String state; // 目前状态

	// 简历的自身属性
	private boolean isDefault; // 是否是默认被选中要投递的简历
	private String checkStatus; // 审核情况, 是否给予显示
	private Integer viewCount; // 被浏览次数
	private String careerTest; // 职业测评情况
	private String processState; // 办理情况

	private User user; // 账户表外键
	private Area area; // 简历所属地区code

	// 教育背景, 家庭成员, 工作经历, 就业管理
	// private List<EducationBackground> educationBackgroundList = new
	// ArrayList<EducationBackground>();
	// private List<FamilyMember> familyMemberList = new
	// ArrayList<FamilyMember>();
	private List<WorkExperience> workExperienceList = new ArrayList<WorkExperience>(); // 工作经历
	private List<UnempManage> unempManageList = new ArrayList<UnempManage>(); // 就业管理

	public Resume() {
	}

	public Resume(Integer id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "Resume [title=" + title + ", name=" + name + ", gender="
				+ gender + ", birth=" + birth + ", identityCard="
				+ identityCard + ", race=" + race + ", marriage=" + marriage
				+ ", hukou=" + hukou + ", hukouAddress=" + hukouAddress
				+ ", hukouStatus=" + hukouStatus + ", address=" + address
				+ ", zipcode=" + zipcode + ", phone=" + phone + ", email="
				+ email + ", qq=" + qq + ", disabilityCategory="
				+ disabilityCategory + ", disabilityCard=" + disabilityCard
				+ ", disabilityLevel=" + disabilityLevel + ", disabilityPart="
				+ disabilityPart + ", workAbility=" + workAbility
				+ ", homeTown=" + homeTown + ", politicalStatus="
				+ politicalStatus + ", age=" + age + ", height=" + height
				+ ", weight=" + weight + ", education=" + education
				+ ", major=" + major + ", school=" + school + ", zhiCheng="
				+ zhiCheng + ", shiYeHao=" + shiYeHao + ", experts=" + experts
				+ ", training=" + training + ", experience=" + experience
				+ ", workExperience=" + workExperience + ", selfEvaluation="
				+ selfEvaluation + ", attachment=" + attachment
				+ ", jobNature=" + jobNature + ", desireJob=" + desireJob
				+ ", desireAddress=" + desireAddress + ", desireSalary="
				+ desireSalary + ", provideFoodAndRoom=" + provideFoodAndRoom
				+ ", provideRoom=" + provideRoom + ", provideFood="
				+ provideFood + ", provideInsurance=" + provideInsurance
				+ ", provideOther=" + provideOther + ", workShift=" + workShift
				+ ", state=" + state + ", isDefault=" + isDefault
				+ ", checkStatus=" + checkStatus + ", viewCount=" + viewCount
				+ ", careerTest=" + careerTest + ", processState="
				+ processState + ", user=" + user + ", area=" + area + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getHukou() {
		return hukou;
	}

	public void setHukou(String hukou) {
		this.hukou = hukou;
	}

	public String getHukouAddress() {
		return hukouAddress;
	}

	public void setHukouAddress(String hukouAddress) {
		this.hukouAddress = hukouAddress;
	}

	public String getHukouStatus() {
		return hukouStatus;
	}

	public void setHukouStatus(String hukouStatus) {
		this.hukouStatus = hukouStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getDisabilityCategory() {
		return disabilityCategory;
	}

	public void setDisabilityCategory(String disabilityCategory) {
		this.disabilityCategory = disabilityCategory;
	}

	public String getDisabilityCard() {
		return disabilityCard;
	}

	public void setDisabilityCard(String disabilityCard) {
		this.disabilityCard = disabilityCard;
	}

	public String getDisabilityLevel() {
		return disabilityLevel;
	}

	public void setDisabilityLevel(String disabilityLevel) {
		this.disabilityLevel = disabilityLevel;
	}

	public String getDisabilityPart() {
		return disabilityPart;
	}

	public void setDisabilityPart(String disabilityPart) {
		this.disabilityPart = disabilityPart;
	}

	public String getWorkAbility() {
		return workAbility;
	}

	public void setWorkAbility(String workAbility) {
		this.workAbility = workAbility;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getZhiCheng() {
		return zhiCheng;
	}

	public void setZhiCheng(String zhiCheng) {
		this.zhiCheng = zhiCheng;
	}

	public String getShiYeHao() {
		return shiYeHao;
	}

	public void setShiYeHao(String shiYeHao) {
		this.shiYeHao = shiYeHao;
	}

	public String getExperts() {
		return experts;
	}

	public void setExperts(String experts) {
		this.experts = experts;
	}

	public String getTraining() {
		return training;
	}

	public void setTraining(String training) {
		this.training = training;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public String getSelfEvaluation() {
		return selfEvaluation;
	}

	public void setSelfEvaluation(String selfEvaluation) {
		this.selfEvaluation = selfEvaluation;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getJobNature() {
		return jobNature;
	}

	public void setJobNature(String jobNature) {
		this.jobNature = jobNature;
	}

	public String getDesireJob() {
		return desireJob;
	}

	public void setDesireJob(String desireJob) {
		this.desireJob = desireJob;
	}

	public String getDesireAddress() {
		return desireAddress;
	}

	public void setDesireAddress(String desireAddress) {
		this.desireAddress = desireAddress;
	}

	public String getDesireSalary() {
		return desireSalary;
	}

	public void setDesireSalary(String desireSalary) {
		this.desireSalary = desireSalary;
	}

	public boolean isProvideFoodAndRoom() {
		return provideFoodAndRoom;
	}

	public void setProvideFoodAndRoom(boolean provideFoodAndRoom) {
		this.provideFoodAndRoom = provideFoodAndRoom;
	}

	public boolean isProvideRoom() {
		return provideRoom;
	}

	public void setProvideRoom(boolean provideRoom) {
		this.provideRoom = provideRoom;
	}

	public boolean isProvideFood() {
		return provideFood;
	}

	public void setProvideFood(boolean provideFood) {
		this.provideFood = provideFood;
	}

	public boolean isProvideInsurance() {
		return provideInsurance;
	}

	public void setProvideInsurance(boolean provideInsurance) {
		this.provideInsurance = provideInsurance;
	}

	public String getProvideOther() {
		return provideOther;
	}

	public void setProvideOther(String provideOther) {
		this.provideOther = provideOther;
	}

	public boolean isWorkShift() {
		return workShift;
	}

	public void setWorkShift(boolean workShift) {
		this.workShift = workShift;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getCareerTest() {
		return careerTest;
	}

	public void setCareerTest(String careerTest) {
		this.careerTest = careerTest;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<WorkExperience> getWorkExperienceList() {
		return workExperienceList;
	}

	public void setWorkExperienceList(List<WorkExperience> workExperienceList) {
		this.workExperienceList = workExperienceList;
	}

	public List<UnempManage> getUnempManageList() {
		return unempManageList;
	}

	public void setUnempManageList(List<UnempManage> unempManageList) {
		this.unempManageList = unempManageList;
	}

}
