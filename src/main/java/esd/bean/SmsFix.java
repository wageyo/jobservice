package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 各地区名商通账号密码
 * @author yufu
 * @email ilxly01@126.com
 * 2015-5-6
 */
@Component
public class SmsFix extends PrimaryKeyString {

	private String prefix; // 前缀
	private String suffix; // 后缀
	private String remark; // 备注
	private Area area; // 所属地区

	@Override
	public String toString() {
		return "SmsFix [prefix=" + prefix + ", suffix=" + suffix + ", remark="
				+ remark + ", area=" + area + "]";
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
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
