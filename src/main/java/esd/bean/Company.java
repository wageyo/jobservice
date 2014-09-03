package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 企业用户的基本信息
 * 
 * @author Administrator
 * 
 */
@Component
public class Company extends PrimaryKey {

	// 基本信息
	private String name; // 公司名称
	private String corporateRepresentative; // 法人
	private String contactPerson; // 联系人
	private String telephone; // 联系电话
	private String contactDept;// 联系部门
	private String fax; // 传真
	private String email; // 公司邮箱
	private String address; // 公司详细地址
	private String Integerroduction; // 公司简介

	private String organizationCode; // 组织机构代码
	private String commercialCode; // 工商登记号码
	private String taxCode; // 税务编码
	private String socialSecurityCode; // 社保登记证号
	private String webSiteId; // 网站ID
	private String laoWangCode; // 市劳网号
	private String scale; // 企业规模
	private String nature; // 企业性质
	private String economyType; // 经济类型
	private String remark; // 备注

	private Integer viewCount; // 被浏览次数

	private String checkStatus; // 是否显示, 用户需要管理员审核通过后才能予以显示
	private User user; // 账号表外键
	private Area area; // 所在地区
	private BusinessScope businessScope; // 所属行业/经营范围

	@Override
	public String toString() {
		return "Company [name=" + name + ", corporateRepresentative="
				+ corporateRepresentative + ", contactPerson=" + contactPerson
				+ ", telephone=" + telephone + ", contactDept=" + contactDept
				+ ", fax=" + fax + ", email=" + email + ", address=" + address
				+ ", Integerroduction=" + Integerroduction + ", organizationCode="
				+ organizationCode + ", commercialCode=" + commercialCode
				+ ", taxCode=" + taxCode + ", socialSecurityCode="
				+ socialSecurityCode + ", webSiteId=" + webSiteId
				+ ", laoWangCode=" + laoWangCode + ", scale=" + scale
				+ ", nature=" + nature + ", economyType=" + economyType
				+ ", remark=" + remark + ", viewCount=" + viewCount
				+ ", checkStatus=" + checkStatus + ", user=" + user + ", area="
				+ area + ", businessScope=" + businessScope + "]";
	}

	public Company() {
	}

	public Company(Integer id) {
		super.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCorporateRepresentative() {
		return corporateRepresentative;
	}

	public void setCorporateRepresentative(String corporateRepresentative) {
		this.corporateRepresentative = corporateRepresentative;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getContactDept() {
		return contactDept;
	}

	public void setContactDept(String contactDept) {
		this.contactDept = contactDept;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroduction() {
		return Integerroduction;
	}

	public void setIntroduction(String Integerroduction) {
		this.Integerroduction = Integerroduction;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getCommercialCode() {
		return commercialCode;
	}

	public void setCommercialCode(String commercialCode) {
		this.commercialCode = commercialCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getSocialSecurityCode() {
		return socialSecurityCode;
	}

	public void setSocialSecurityCode(String socialSecurityCode) {
		this.socialSecurityCode = socialSecurityCode;
	}

	public String getWebSiteId() {
		return webSiteId;
	}

	public void setWebSiteId(String webSiteId) {
		this.webSiteId = webSiteId;
	}

	public String getLaoWangCode() {
		return laoWangCode;
	}

	public void setLaoWangCode(String laoWangCode) {
		this.laoWangCode = laoWangCode;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getEconomyType() {
		return economyType;
	}

	public void setEconomyType(String economyType) {
		this.economyType = economyType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
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

	public BusinessScope getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(BusinessScope businessScope) {
		this.businessScope = businessScope;
	}

}
