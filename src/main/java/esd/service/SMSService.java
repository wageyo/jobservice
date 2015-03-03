package esd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import esd.bean.Job;
import esd.bean.Resume;
import esd.common.Sender;

/**
 * 短信发送服务类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-27
 */
@Service
public class SMSService {

	@Value("${sms.username}")
	private String username;
	
	@Value("${sms.password}")
	private String password;
	
	/**
	 *发送一条消息 
	 * @param phone
	 * @param message
	 * @return
	 */
	public Boolean sendOneMessage(String phone, String message){
		Sender sender = new Sender(username,password);
		//发送短信
		String result = sender.massSend(phone, message, null, null);
		//截取发送条数num
		String num = result.substring(result.indexOf("num")+4,result.indexOf("num")+5);
		System.out.println(num);
		//截取errid
		String errid = result.substring(result.indexOf("errid")+6,result.indexOf("errid")+7);
		//如果发送成功, 则返回true
		if("1".equals(num)&&"0".equals(errid)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 向指定简历发送推送的职位
	 * @param resume
	 * @param jobList
	 */
	public Boolean sendTuiSongJob(Resume resume, List<Job> jobList){
		String phone = resume.getPhone();	//电话号码
		String msg = "向您推送的职位:";
		for(int i=0;i<jobList.size();i++){
			Job job = jobList.get(i);
			msg += job.getName();
		}
		Boolean bl = sendOneMessage(phone,msg);
		return bl;
	}
	
	
	
//	public static void main(String[] args) {
//		String re = "num=1&success=18246188443&faile=&err=���ͳɹ���&errid=0";
//		//截取发送条数num
//		String num = re.substring(re.indexOf("num")+4,re.indexOf("num")+5);
//		System.out.println(num);
//		//截取errid
//		String errid = re.substring(re.indexOf("errid")+6,re.indexOf("errid")+7);
//		System.out.println(errid);
//	}
}
