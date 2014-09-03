package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 录入员类
 * 
 * @author Administrator
 * 
 */
@Component
public class AdminInfo extends PrimaryKey {

	private String name; // 名称
	private String address; // 详细地址

	private User user; // 账户表
	private Area area; // 所属地区

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "AdminRecorder [ id = " + super.getId() + ", name = "
				+ this.name + ", address = " + this.address + ", area = "
				+ this.area + ", user = " + this.user + "]";
	}

	public AdminInfo() {
	}

	public AdminInfo(int id) {
		super.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

}
