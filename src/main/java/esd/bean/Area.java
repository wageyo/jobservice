package esd.bean;

import org.springframework.stereotype.Component;

@Component
public class Area {

	private String code; // 地区代码 - 主键
	private String name; // 地名
	private String pyName; // 地名全拼
	private String abbr; // 地名缩写
	private String mark; // 标记，例如热点城市等等

	// 虚属性, 前台显示用
	private int personNumber;
	private int recorderNumber;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "area [code = " + code + ", name = " + name + ", pyName = "
				+ pyName + ", mark = " + mark + "]";
	}

	public Area() {
	}

	public Area(String code) {
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

	public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public int getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(int personNumber) {
		this.personNumber = personNumber;
	}

	public int getRecorderNumber() {
		return recorderNumber;
	}

	public void setRecorderNumber(int recorderNumber) {
		this.recorderNumber = recorderNumber;
	}

}
