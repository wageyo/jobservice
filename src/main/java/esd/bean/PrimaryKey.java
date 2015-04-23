package esd.bean;

import java.util.Date;

/**
 * 主键共有属性类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-4-17
 */
public class PrimaryKey {

	private Date createDate; // 创建时间
	private Integer updateCheck; // 更新验证
	private Date updateDate; // 更新时间
	private String logUser; // 记录操作人
	private Boolean isActive; // 是否激活/使用 状态 默认为true, false为未使用或屏蔽状态

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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
