package esd.bean;

/**
 * 常用参数表
 * 
 * @author Administrator
 * 
 */
public class Parameter {
	private String id;
	private String name; // 名字
	private String value;
	private String type; // 何种参数,参数类型
	private String mark; // 备注
	private Area area; // code编码

	@Override
	public String toString() {
		return "Parameter [id=" + id + ", name=" + name + ", value=" + value
				+ ", type=" + type + ", area=" + area + ", mark=" + mark + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
