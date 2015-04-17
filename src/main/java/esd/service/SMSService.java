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
	 * 发送短信--不对短信内容进行非法校验更正
	 * 
	 * @param phone格式为单个或多个电话, 单个电话时直接传入电话号码即可, 例如 "13812345678"; 多个时中间用","隔开, 例如  "138123445678,13512312312,13112541251", 末尾不用",", 一次最多99个电话.
	 * @param message 短信内容, 最大不超过340
	 * @return
	 */
	public Boolean sendMessage(String phone, String message) {
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
		if ((Integer.parseInt(num) >= 1) && "0".equals(errid)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 发送短信--对短信内容进行非法校验更正
	 * 
	 * @param phone格式为单个或多个电话, 单个电话时直接传入电话号码即可, 例如 "13812345678"; 多个时中间用","隔开, 例如  "138123445678,13512312312,13112541251", 末尾不用",", 一次最多99个电话.
	 * @param message 短信内容, 最大不超过340
	 * @return
	 */
	public Boolean sendMessage(String phone, String message,String illegalFileUrl) {
		
		//处理非法关键字
		message = dealIllegalContent(message, illegalFileUrl);
		
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
		if ((Integer.parseInt(num) >= 1) && "0".equals(errid)) {
			log.info("************************短信发送成功******************************");
			log.info("发送条数: "+num);
			log.info("************************短信发送成功******************************");
			return Boolean.TRUE;
		}
		String err = result.substring(result.indexOf("err")+4,result.indexOf("errid")-1);
		errid = result.substring(result.indexOf("errid")+6);
		log.info("************************短信发送失败******************************");
		log.info("错误原因: "+err);
		log.info("错误代码: "+errid);
		log.info("************************短信发送失败******************************");
		return Boolean.FALSE;
	}

	/**
	 * 向指定简历发送推送的职位
	 * 
	 * @param resume
	 * @param jobList
	 * @param illegalFileUrl --路径为项目根路径
	 */
	public Boolean sendTuiSongJob(Resume resume, List<Job> jobList,
			String illegalFileUrl) {
		String phone = resume.getPhone(); // 电话号码
		// 校验电话规则, 不符合规则 则返回false; 不存在则使用其账号表中的电话, 如仍不存在则返回false;
		if (phone == null || "".equals(phone)) {
			phone = userDao.getById(resume.getUser().getId()).getPhone();
		}
		// 如果此人的电话号码仍为空的话, 则不予发送
		if (phone == null || "".equals(phone)) {
			return false;
		}
		String msg = "广西壮族自治区残疾人劳动就业指导中心向您推荐的招聘信息:\n";
		for (int i = 0; i < jobList.size(); i++) {
			Job job = jobList.get(i);
			msg += (i + 1) + ". " + job.getName() + ", "; // 职位名称
			msg += job.getCompany().getName() + ", "; // 企业名称名称
			msg += job.getWorkPlace().getName() + ", "; // 工作地点
			// String canjiyaoqiu = ""; //残疾要求
			// if(job.getDisabilityPart() == null && job.getDisabilityLevel() ==
			// null){
			// canjiyaoqiu = "暂无";
			// }else{
			// canjiyaoqiu =
			// job.getDisabilityPart()+" "+job.getDisabilityLevel();
			// }
			msg += job.getSalary() + "\n"; // 薪资
		}
		msg += "联系人:\t职业指导科小何、小易\t联系电话:\t0771-3186952\t0771-3186953\n联系地址:\t南宁市罗文大道48号(残疾人事业园1楼就业服务大厅)\t更多招聘信息请登录广西壮族自治区残疾人就业信息网，网址：http://116.11.253.249:9217/jobservice";
		// 处理非法字符
		msg = dealIllegalContent(msg, illegalFileUrl);
		
		//总字符数
		int total = msg.length();
		//每条信息最多字符数
		int size = 300;
		//页码数
		int page = (total%size == 0)?(total/size):((total/size)+1);
		Boolean bl = false;
		if(page >= 2){
			for(int i=1;i<=page;i++){
				int beginIndex = (i-1)*size;
				int endIndex = i*size;
				if(endIndex >=total){
					endIndex = total;
				}
				String perMsg = "("+i+"/"+page+")" + msg.substring(beginIndex,endIndex);
				bl = sendMessage(phone, perMsg);
			}
		}else{
			bl = sendMessage(phone, msg);
		}
		log.info("推送短信内容：" + msg);
		log.info("推送短信长度: " + msg.length());
		log.info("推送分页页数: " + page);
		return bl;
	}

	/**
	 * 处理短信中可能存在的非法字符, 暂时统一加上 ,
	 * 
	 * @param content
	 */
	private String dealIllegalContent(String content, String illegalFileUrl) {
		// String fileurl = "d:" + File.separator + "非法关键字.txt";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(illegalFileUrl + File.separator
							+ "file" + File.separator + "非法关键字.txt"), "gb2312"));
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				Pattern pattern = Pattern.compile(line);
				Matcher matcher = pattern.matcher(content);
				// log.info(matcher.find());
				if (matcher.find()) {
					log.info("/*******************相同的啦************************/ 第 ");
					log.info("原字符串内容: " + content);
					String tmp = "";
					for (char c : line.toCharArray()) {
						tmp += c + ",";
					}
					log.info("非法字符: " + line);
					log.info("要替换的非法字符: " + tmp);
					String replaceResult = matcher.replaceAll(tmp);
					log.info("替换处理后的内容： " + replaceResult);
					content = replaceResult;
				}
			}
			br.close();
		} catch (Exception e) {
			log.info("替换非法字符时发生错误.");
		}
		return content;
	}

	public static void main(String[] args) {
//		String msg = "广西壮族自治区残疾人劳动就业指导中心向您推荐的招聘信息招聘信息:"
//				+ "1. 统计员, 广西南宁嘉泰水泥制品有限公司, 广西壮族自治区, 1000-2000元"
//				+ "2. 电销专员, 广西龙星汽车销售有限公司, 广西壮族自治区, 面议"
//				+ "3. 文员, 广西佳利工贸有限公司, 广西壮族自治区, 1000-2000元"
//				+ "4. 仓库保管员, 广西创宁电力工程有限公司, 广西壮族自治区, 1000-2000元"
//				+ "5. 营业员, 广西一心医药有限公司, 广西壮族自治区, 1000-2000元"
//				+ "联,系,人:	职业指导科小何、小易	联,系,电话:	0771-3186952	0771-3186953"
//				+ "联,系,地址:	南宁市罗文大道48号残疾人事业园1楼就业服务大厅	更多招聘信息请登录广西壮族自治区残疾人就业信息网，网址：http://116.11.253.249:9217/jobservice";
//
//		//总字符数
//		int total = msg.length();
//		//每条信息最多字符数
//		int size = 300;
//		//页码数
//		int page = (total%size == 0)?(total/size):((total/size)+1);
//		System.out.println(page);
//		for(int i=1;i<=page;i++){
//			System.out.println("***************************************");
//			int beginIndex = (i-1)*size;
//			int endIndex = i*size;
//			if(endIndex >=total){
//				endIndex = total;
//			}
//			String perMsg = msg.substring(beginIndex,endIndex) + "("+i+"/"+page+")";
//			System.out.println(perMsg);
//		}
		
		String result = "num=0&success=&faile=13804802181&err=该企业用户余额不足&errid=6013";
		String err = result.substring(result.indexOf("err")+4,result.indexOf("errid")-1);
		System.out.println(err);
		String errid = result.substring(result.indexOf("errid")+6);
		System.out.println(errid);
	}
}
