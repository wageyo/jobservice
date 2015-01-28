package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 白名单列表
 * 
 * @author Administrator
 * 
 */
@Component
public class WhiteList extends PrimaryKey_String {

	private String title; // 域名名称
	private String domainName; // 域名
	private String fullName; // 可能需要的全限定名
	private String remark; // 备注

	@Override
	public String toString() {
		return "WhiteList [title=" + title + ", domainName=" + domainName
				+ ", fullName=" + fullName + ", remark=" + remark + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
