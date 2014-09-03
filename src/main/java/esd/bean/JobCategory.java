package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 行业/职位类型类
 * 
 * @author Administrator
 * 
 */
@Component
public class JobCategory {

	private String code; // 职位代码
	private String name; // 职位名称
	private String mark; // 标记:hot等...

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "JobCategory [ code = " + this.code + ", name = " + this.name
				+ ", mark = " + this.mark + "]";
	}

	public JobCategory() {
	}

	public JobCategory(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
