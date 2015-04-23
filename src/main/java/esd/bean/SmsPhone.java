package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 短信自定义发送电话列表
 * 
 * @author Administrator
 * 
 */
@Component
public class SmsPhone extends PrimaryKeyString {

	private String name; // 标题
	private String phone; // 手机号码
	private String remark; // 备注
	private Area area; // 所属地区
	private Boolean isOk;

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

	/**
	 * 校验是否通过--不做数据库映射用, 只在上传导入电话号码时使用
	 * @return
	 */
	public Boolean getIsOk() {
		return isOk;
	}

	/**
	 * 校验是否通过--不做数据库映射用, 只在上传导入电话号码时使用
	 * @param isOk
	 */
	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

}
