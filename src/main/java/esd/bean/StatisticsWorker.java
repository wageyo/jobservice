package esd.bean;

import java.text.DecimalFormat;

/**
 * 采集人统计数据--仅供前台显示用的bean类, 不做mapper映射, 没有实体表对应
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-3-7
 */
public class StatisticsWorker {

	private Area area; // 所属地区名
	private Integer numberUser; // 残疾人数量
	private Integer numberResume; // 简历数量
	private Integer numberHired; // 已经就业人数
	private String averageResume; // 平均每人创建的简历数 ==简历数量/残疾人数量
	private String averageHired; // 就业比率 = 已经就业人数/残疾人数量
	private String checkStatus; // 当前所处的审核状态

	private DecimalFormat df2 = new DecimalFormat("0.00");

	
	@Override
	public String toString() {
		return "StatisticsWorker [area=" + area + ", numberUser=" + numberUser
				+ ", numberResume=" + numberResume + ", numberHired="
				+ numberHired + ", averageResume=" + averageResume
				+ ", averageHired=" + averageHired + ", checkStatus="
				+ checkStatus + "]";
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Integer getNumberUser() {
		return numberUser;
	}

	public void setNumberUser(Integer numberUser) {
		this.numberUser = numberUser;
	}

	public Integer getNumberResume() {
		return numberResume;
	}

	public void setNumberResume(Integer numberResume) {
		this.numberResume = numberResume;
	}

	public Integer getNumberHired() {
		return numberHired;
	}

	public void setNumberHired(Integer numberHired) {
		this.numberHired = numberHired;
	}

	public String getAverageResume() {
		if(this.numberUser==null ||this.numberUser==0){
			return "0.00";
		}
		this.averageResume = df2.format(this.numberResume/this.numberUser);
		return averageResume;
	}

	public void setAverageResume(String averageResume) {
		this.averageResume = averageResume;
	}

	public String getAverageHired() {
		if(this.numberUser==null ||this.numberUser==0){
			return "0.00";
		}
		this.averageHired = df2.format(this.numberHired/this.numberUser);
		return this.averageHired;
	}

	public void setAverageHired(String averageHired) {
		this.averageHired = averageHired;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}


}
