package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 投递简历记录类,发布的招聘信息的申请记录,即每条招聘信息的所有投递简历的记录
 * 
 * @author Administrator
 * 
 */
@Component
public class Record extends PrimaryKey {

	private Integer rID; // 简历ID
	private String rTitle; // 简历名
	private String rName; // 姓名
	private String rGender; // 性别
	private String rDisabilityCategory; // 残疾类别
	private Integer rAge; // 年龄
	private String rEducation; // 学历
	private String rMajor; // 专业
	private String rSchool; // 毕业学校
	private Integer uID; // 个人ID

	private Integer jID; // 投递职位ID
	private String jName; // 职位名称
	private String jSalary; // 职位薪水
	private String jDescription; // 职位描述
	private String jContactTel; // 职位联系电话
	private String jContactPerson; // 职位联系人
	private String jNature; // 工作性质
	private Integer cID; // 公司ID

	public Record() {
	}

	public Record(Integer rId, Integer jId) {
		this.rID = rId;
		this.jID = jId;
	}

	@Override
	public String toString() {
		return "Record [rID=" + rID + ", rTitle=" + rTitle + ", rName=" + rName
				+ ", rGender=" + rGender + ", rDisabilityCategory="
				+ rDisabilityCategory + ", rAge=" + rAge + ", rEducation="
				+ rEducation + ", rMajor=" + rMajor + ", rSchool=" + rSchool
				+ ", jID=" + jID + ", jName=" + jName + ", jSalary=" + jSalary
				+ ", jDescription=" + jDescription + ", jContactTel="
				+ jContactTel + ", jContactPerson=" + jContactPerson
				+ ", jNature=" + jNature + "]";
	}

	public Integer getrID() {
		return rID;
	}

	public void setrID(Integer rID) {
		this.rID = rID;
	}

	public String getrTitle() {
		return rTitle;
	}

	public void setrTitle(String rTitle) {
		this.rTitle = rTitle;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public String getrGender() {
		return rGender;
	}

	public void setrGender(String rGender) {
		this.rGender = rGender;
	}

	public String getrDisabilityCategory() {
		return rDisabilityCategory;
	}

	public void setrDisabilityCategory(String rDisabilityCategory) {
		this.rDisabilityCategory = rDisabilityCategory;
	}

	public Integer getrAge() {
		return rAge;
	}

	public void setrAge(Integer rAge) {
		this.rAge = rAge;
	}

	public String getrEducation() {
		return rEducation;
	}

	public void setrEducation(String rEducation) {
		this.rEducation = rEducation;
	}

	public String getrMajor() {
		return rMajor;
	}

	public void setrMajor(String rMajor) {
		this.rMajor = rMajor;
	}

	public String getrSchool() {
		return rSchool;
	}

	public void setrSchool(String rSchool) {
		this.rSchool = rSchool;
	}

	public Integer getuID() {
		return uID;
	}

	public void setuID(Integer uID) {
		this.uID = uID;
	}

	public Integer getjID() {
		return jID;
	}

	public void setjID(Integer jID) {
		this.jID = jID;
	}

	public String getjName() {
		return jName;
	}

	public void setjName(String jName) {
		this.jName = jName;
	}

	public String getjSalary() {
		return jSalary;
	}

	public void setjSalary(String jSalary) {
		this.jSalary = jSalary;
	}

	public String getjDescription() {
		return jDescription;
	}

	public void setjDescription(String jDescription) {
		this.jDescription = jDescription;
	}

	public String getjContactTel() {
		return jContactTel;
	}

	public void setjContactTel(String jContactTel) {
		this.jContactTel = jContactTel;
	}

	public String getjContactPerson() {
		return jContactPerson;
	}

	public void setjContactPerson(String jContactPerson) {
		this.jContactPerson = jContactPerson;
	}

	public String getjNature() {
		return jNature;
	}

	public void setjNature(String jNature) {
		this.jNature = jNature;
	}

	public Integer getcID() {
		return cID;
	}

	public void setcID(Integer cID) {
		this.cID = cID;
	}

}
