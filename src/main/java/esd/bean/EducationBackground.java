package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 教育背景信息
 * 
 * @author Administrator
 * 
 */
@Component
public class EducationBackground extends PrimaryKey {
	private String time; // 学习时间
	private String school; // 毕业院校
	private String major; // 专业
	private String education; // 学历
	private String certificate; // 证书
	private Resume resume; // 属于哪个简历

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

}
