package esd.bean;

public class PrimaryKey_String {

	private String id; // 主键
	private String createDate; // 创建时间
	private Integer updateCheck; // 更新次数检查
	private String updateDate; // 更新时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getUpdateCheck() {
		return updateCheck;
	}

	public void setUpdateCheck(Integer updateCheck) {
		this.updateCheck = updateCheck;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
