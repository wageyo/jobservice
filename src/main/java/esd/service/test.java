package esd.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import esd.common.Sender;

public class test {
	private static Logger log = Logger.getLogger(test.class);
	
	public static void main(String[] args) throws IOException {
		String name = "zll88"; // 企业用户名
		String pwd = "a123456";// 企业密码
		// 发送对象
		Sender sender = new Sender(name, pwd);
		// 短信内容
		// String content ="心里莫名的急躁, 唉...by 于富.(这是java测试案例, 无需回复.)";
		// String content
		// ="你好，我是于富，<br/>正在使用名商通给你发信息呢，呜呜哈哈嘎嘎。\t\t\t(这是java测试案例)\n\n\n本公司及董事会全体成员保证信息披露的内容真实、准确、完整，没有虚假记载、误导性陈述或重大遗漏。你好，我是于富，正在使用名商通给你发信息呢，呜呜哈哈嘎嘎。(这是java测试案例)本公司及董事会全体成员保证信息披露的内容真实、准确、完整，没有虚假记载、误导性陈述或重大遗漏。你好，我是于富，正在使用名商通给你发信息呢，呜呜哈哈嘎嘎。(这是java测试案例)本公司及董事会全体成员保证信息披露的内容真实、";
		String content = "你好，我是小明，<br/>正在使用名商通给你发信息呢，呜呜哈哈嘎嘎。\t\t\t(这是java测试案例)\n\n\n本公司及董事会全体成员保证信息披露的内容真实、准确、完整，没有虚假记载、误导性陈述或重大遗漏。你好，我是于富，正在使用名商通给你发信息呢，呜呜哈哈嘎嘎。(这是java测试案例)本公司及董事会全体成员保证信息披露的内容真实、准确、完整，没有虚假记载、误导性陈述或重大遗漏。你好，我是于富，正在使用名商通给你发信息呢，呜呜哈哈嘎嘎。(这是java测试案例)本公司及董事会全体成员保证信息披露的内容真实、";
		// 测试发送的内容
		 String checkResult = sender.checkContent(content);
		 log.info("测试结果: "+checkResult);
		// 发送短信
		// String result = sender.massSend("13804802181", content, null, null);
		// log.info("发送结果: "+result);
		// String fee = sender.getFee();
		// log.info("剩余费用: "+fee);
		// String decodedFee = "";

		// try {
		// // byte[] b = fee.getBytes("gb2312");
		// // decodedFee = new String(b,"utf-8");
		// String utf8 = URLDecoder.decode(result, "GB2312");
		// log.info(utf8);
		// } catch (UnsupportedEncodingException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		// log.info("剩余费用: "+decodedFee);

		// String strDefalt = "";
		// String strDefaltEncode = "";
		// String strChangeEncode = "";
		//
		// try {
		// strDefalt = "我的心不乱";
		// strDefaltEncode = new String("我的心不乱".getBytes(),"utf-8");
		// strChangeEncode = new String("我的心不乱".getBytes("gbk"),"shift-jis");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// log.info("Default charsetName="+Charset.defaultCharset().name());
		// log.info("strDefalt="+strDefalt);
		// log.info("strDefaltEncode="+strDefaltEncode);
		// log.info("strChangeEncode="+strChangeEncode);

		//
		String[] arr={"123自民党123","我们的自民党是好朋友","联系我们","士大夫离开家联系他"};
		String fileurl = "d:" + File.separator + "非法关键字.txt";
		for(int i=0;i<arr.length;i++){
			String str = arr[i];
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileurl),"gb2312"));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				Pattern pattern = Pattern.compile(line);
				Matcher matcher = pattern.matcher(str);
//				log.info(matcher.find());
				if(matcher.find()){
					log.info(i +"个循环" +"/*******************相同的啦************************/ 第 " );
					log.info("原字符串内容: "+str);
					
					String tmp = "";
					for(char c: line.toCharArray()){
						tmp +=c + ",";
					}
					log.info("非法字符: "+ line);
					log.info("要替换的非法字符: " + tmp);
					String replaceResult = matcher.replaceAll(tmp);
					log.info("替换处理后的内容： " + replaceResult);
				}
			}
			br.close();
		}
		// log.info(new String(b));
	}
}
