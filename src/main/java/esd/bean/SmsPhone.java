package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 短信自定义发送电话列表
 * 
 * @author Administrator
 * 
 */
@Component
public class SmsPhone extends PrimaryKey_String {

	private String name; // 标题
	private String phone; // 手机号码
	private String remark; // 备注
	private Area area; // 所属地区

	@Override
	public String toString() {
		return "SmsPhone [name=" + name + ", phone=" + phone + ", remark="
				+ remark + ", area=" + area + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

}
