package esd.bean;

import org.springframework.stereotype.Component;

/**
 * 短信发送历史
 * @author yufu
 * @email ilxly01@126.com
 * 2015-4-29
 */
@Component
public class SmsHistory extends PrimaryKeyString {
	
	private String phonenumber;		//电话号码
	private String content;//短信内容
	private Boolean isSuccess;//发送是否成功
	
	
	@Override
	public String toString() {
		return "SmsHistory [phonenumber=" + phonenumber + ", content="
				+ content + ", isSuccess=" + isSuccess + "]";
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}


}
