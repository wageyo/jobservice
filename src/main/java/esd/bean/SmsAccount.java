package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 各地区名商通账号
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-5-4
 */
@Component
public class SmsAccount extends PrimaryKeyString {

	private String username; // 前缀
	private String password; // 后缀
	private String remark; // 备注
	private Area area; // 所属地区

	@Override
	public String toString() {
		return "SmsAccount [username=" + username + ", password=" + password
				+ ", remark=" + remark + ", area=" + area + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
