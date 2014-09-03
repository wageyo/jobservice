package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 账户类
 * 
 * @author Administrator
 * 
 */
@Component
public class User extends PrimaryKey {

	private String loginName; // 账号
	private String passWord; // 密码
	private String identity; // 账号类型
	private String email; // 注册的邮箱
	private String phone; // 联系电话
	private Area area;
	private String checkStatus; // 审核状态
	private Integer authority; // 权限值
	private String title; // 标题
	private String nickName; // 昵称

	public User() {
	}

	public User(Integer id) {
		super.setId(id);
	}

	public User(String loginName) {
		this.loginName = loginName;
	}

	public User(String loginName, String passWord) {
		this.loginName = loginName;
		this.passWord = passWord;
	}

	@Override
	public String toString() {
		return "User [loginName=" + loginName + ", passWord=" + passWord
				+ ", identity=" + identity + ", email=" + email + ", phone="
				+ phone + ", area=" + area + ", checkStatus=" + checkStatus
				+ ", authority=" + authority + ", title=" + title
				+ ", nickName=" + nickName + "]";
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
