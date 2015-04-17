package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 账户类
 * 
 * @author Administrator
 * 
 */
@Component
public class User extends PrimaryKeyInt {

	private String loginName; // 账号
	private String passWord; // 密码
	private String email; // 注册的邮箱
	private String phone; // 联系电话
	private String identity; // 账号类型:admin-管理员用户,individual-个人用户,company-公司用户,sa-超级管理员账号
	private String checkStatus; // 审核状态
	private Integer authority; // 权限： 999为管理员权限
	private String title; // 个性标题名
	private String nickName; // 用户昵称
	private String headTitle; // 用户头像图片名
	private byte[] headImage;// 用户头像图片
	private Area area;// 账号所属地地区外键

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

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
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

	public String getHeadTitle() {
		return headTitle;
	}

	public void setHeadTitle(String headTitle) {
		this.headTitle = headTitle;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public byte[] getHeadImage() {
		return headImage;
	}

	public void setHeadImage(byte[] headImage) {
		this.headImage = headImage;
	}

}
