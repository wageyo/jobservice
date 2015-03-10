package esd.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import esd.bean.Job;
import esd.bean.Resume;
import esd.common.Sender;
import esd.dao.UserDao;

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
	private static Logger log = Logger.getLogger(SMSService.class);
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 发送一条消息
	 * 
	 * @param phone
	 * @param message
	 * @return
	 */
	public Boolean sendOneMessage(String phone, String message) {
		Sender sender = new Sender(username, password);
		// 发送短信
		String result = sender.massSend(phone, message, null, null);
		// 截取发送条数num
		String num = result.substring(result.indexOf("num") + 4,
				result.indexOf("num") + 5);
		// 截取errid
		String errid = result.substring(result.indexOf("errid") + 6,
				result.indexOf("errid") + 7);
		// 如果发送成功, 则返回true
		if ("1".equals(num) && "0".equals(errid)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 向指定简历发送推送的职位
	 * 
	 * @param resume
	 * @param jobList
	 */
	public Boolean sendTuiSongJob(Resume resume, List<Job> jobList, String illegalFileUrl) {
		String phone = resume.getPhone(); // 电话号码
		//校验电话规则, 不符合规则 则返回false; 不存在则使用其账号表中的电话, 如仍不存在则返回false;
		if(phone == null ||"".equals(phone)){
			phone = userDao.getById(resume.getUser().getId()).getPhone();
		}
		//如果此人的电话号码仍为空的话, 则不予发送
		if(phone == null ||"".equals(phone)){
			return false;
		}
		String msg = "向您推荐的就业信息:\n";
		for (int i = 0; i < jobList.size(); i++) {
			Job job = jobList.get(i);
			msg += (i + 1) + ". " + job.getName() + "["; // 岗位名称
			msg += job.getCompany().getName() + "]_"; // 企业名称名称
			msg += job.getWorkPlace().getName() + "_"; // 工作地点
			String canjiyaoqiu = "";	//残疾要求
			if(job.getDisabilityPart() == null && job.getDisabilityLevel() == null){
				canjiyaoqiu = "暂无";
			}else{
				canjiyaoqiu = job.getDisabilityPart()+" "+job.getDisabilityLevel();
			}
			msg += "要求: "+canjiyaoqiu+" ,工资:"+job.getSalary() +"\n";	//薪资
		}
		msg += "残联就业指导中心电话: 13077731875  13152689506";
		//处理非法字符
		msg = dealIllegalContent(msg,illegalFileUrl);
		Boolean bl = sendOneMessage(phone, msg);
		log.info("推送短信内容：" + msg);
		log.info("推送内容字符长度: " +msg.length());
		return bl;
	}

	/**
	 * 处理短信中可能存在的非法字符, 暂时统一加上  ,
	 * @param content
	 */
	private String dealIllegalContent(String content,String illegalFileUrl){
//		String fileurl = "d:" + File.separator + "非法关键字.txt";
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(illegalFileUrl + File.separator+"file"+File.separator+"非法关键字.txt"),"gb2312"));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				Pattern pattern = Pattern.compile(line);
				Matcher matcher = pattern.matcher(content);
//					log.info(matcher.find());
				if(matcher.find()){
					log.info("/*******************相同的啦************************/ 第 " );
					log.info("原字符串内容: "+content);
					String tmp = "";
					for(char c: line.toCharArray()){
						tmp +=c + ",";
					}
					log.info("非法字符: "+ line);
					log.info("要替换的非法字符: " + tmp);
					String replaceResult = matcher.replaceAll(tmp);
					log.info("替换处理后的内容： " + replaceResult);
					content =replaceResult;
				}
			}
			br.close();
		}catch(Exception e){
			log.info("替换非法字符时发生错误.");
		}
		return content;
	}
}
