package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 就业管理
 * 
 * @author Administrator
 * 
 */
@Component
public class UnempManage extends PrimaryKey {

	private String assessment; // 职业测评情况
	private String procedure; // 办理情况
	private String status; // 就业情况
	private Resume resume; // 所属简历

	public UnempManage() {
	}

	public UnempManage(Integer id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "unempManage [assessment=" + assessment + ", procedure="
				+ procedure + ", status=" + status + ", resume=" + resume + "]";
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

}
