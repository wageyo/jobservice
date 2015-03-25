package esd.bean;

import java.text.DecimalFormat;

/**
 * 企业统计数据--仅供前台显示用的bean类, 不做mapper映射, 没有实体表对应
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-3-7
 */
public class StatisticsCompany {

	private Area area; // 所属地区名
	private Integer numberCompany; // 企业数量
	private Integer numberJob; // 岗位数
	private Integer numberHire; // 招聘的总人数
	private float averageHire; // 平均每家企业招聘人数 ==总招聘人数/企业数
	private float averageJob; // 平均每家企业拥有职位. 小数点后两位 = 总职位数/企业数
	private String checkStatus; // 当前所处的审核状态

	private DecimalFormat df2 = new DecimalFormat("0.00");


	@Override
	public String toString() {
		return "StatisticsCompany [area=" + area + ", numberCompany="
				+ numberCompany + ", numberJob=" + numberJob + ", numberHire="
				+ numberHire + ", averageHire=" + averageHire + ", averageJob="
				+ averageJob + ", checkStatus=" + checkStatus + "]";
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Integer getNumberCompany() {
		return numberCompany;
	}

	public void setNumberCompany(Integer numberCompany) {
		this.numberCompany = numberCompany;
	}

	public Integer getNumberJob() {
		return numberJob;
	}

	public void setNumberJob(Integer numberJob) {
		this.numberJob = numberJob;
	}

	public Integer getNumberHire() {
		return numberHire;
	}

	public void setNumberHire(Integer numberHire) {
		this.numberHire = numberHire;
	}

	public float getAverageHire() {
		if(this.numberCompany<=0){
			return 0;
		}
		this.averageHire = this.numberHire/this.numberCompany;
		return this.averageHire;
	}

	public void setAverageHire(float averageHire) {
		this.averageHire = averageHire;
	}

	public float getAverageJob() {
		if(this.numberCompany<=0){
			return 0;
		}
		this.averageJob = this.numberJob/this.numberCompany;
		return this.averageJob;
	}

	public void setAverageJob(float averageJob) {
		this.averageJob = averageJob;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

}
