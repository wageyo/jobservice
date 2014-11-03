package esd.bean;

import java.util.Date;

public class PrimaryKey {

	private Integer id; // 主键
	private Date createDate; // 创建时间
	private Integer updateCheck; // 更新验证
	private Date updateDate; // 更新时间
	private String logUser;	//记录操作人
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getUpdateCheck() {
		return updateCheck;
	}
	public void setUpdateCheck(Integer updateCheck) {
		this.updateCheck = updateCheck;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getLogUser() {
		return logUser;
	}
	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}

	

}
