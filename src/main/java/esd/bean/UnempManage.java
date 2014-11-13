package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 就业管理/办理情况
 * 
 * @author Administrator
 * 
 */
@Component
public class UnempManage extends PrimaryKey {

	private String time; // 办理持续时间
	private String content; // 办理情况
	private Resume resume; // 所属简历

	public UnempManage() {
	}

	public UnempManage(Integer id) {
		super.setId(id);
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

}
