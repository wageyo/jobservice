package esd.bean;

import org.springframework.stereotype.Component;

/**
 * IP bean类
 * 
 * @author Administrator
 * 
 */
@Component
public class IP2City {

	private Integer id;
	private String ipStart; // 开始ip段
	private String ipEnd; // 结束ip段
	private String city; // 城市
	private String provider; // 提供商

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIpStart() {
		return ipStart;
	}

	public void setIpStart(String ipStart) {
		this.ipStart = ipStart;
	}

	public String getIpEnd() {
		return ipEnd;
	}

	public void setIpEnd(String ipEnd) {
		this.ipEnd = ipEnd;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
