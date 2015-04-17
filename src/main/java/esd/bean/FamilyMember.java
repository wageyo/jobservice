package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 家庭成员信息
 * 
 * @author Administrator
 * 
 */
@Component
public class FamilyMember extends PrimaryKeyInt {

	// 家庭情况
	private String relation; // 关系
	private String name; // 姓名
	private Integer age; // 年龄
	private String unit; // 所在单位
	private String position; // 职务
	private String phone; // 联系电话
	private Resume resume; // 属于哪个简历

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

}
