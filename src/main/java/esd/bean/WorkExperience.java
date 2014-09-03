package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 工作经历
 * 
 * @author Administrator
 * 
 */
@Component
public class WorkExperience extends PrimaryKey {

	private String companyName; // 企业名称
	private String jobName; // 职位名称
	private String jobContent; // 工作内容/成绩
	private String workTime; // 在职时间
	private String leaveReason; // 离职原因
	private String evaluation; // 评价
	private Resume resume; // 属于哪个简历

	@Override
	public String toString() {
		return "WorkExperience [companyName=" + companyName + ", jobName="
				+ jobName + ", jobContent=" + jobContent + ", workTime="
				+ workTime + ", leaveReason=" + leaveReason + ", evaluation="
				+ evaluation + ", resume=" + resume + "]";
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobContent() {
		return jobContent;
	}

	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

}
