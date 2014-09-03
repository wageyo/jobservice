package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 经营范围类
 * 
 * @author Administrator
 * 
 */
@Component
public class BusinessScope {

	private Integer id;
	private String code; // 专业代码
	private String name; // 专业名称
	private String mark; // 标识

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "BusinessScope [ code = " + this.code + ", name = " + this.name
				+ ", mark = " + this.mark + "]";
	}

	public BusinessScope() {
	}

	public BusinessScope(Integer id) {
		this.id = id;
	}

	public BusinessScope(String code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
