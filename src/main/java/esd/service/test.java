package esd.service;

import esd.common.Sender;

public class test {

	public static void main(String[] args) {
		String name = "zll88";	//企业用户名
		String pwd = "a123456";//企业密码
		//发送对象
		Sender sender = new Sender(name, pwd);
		//短信内容
	//	String content ="心里莫名的急躁, 唉...by 于富.(这是java测试案例, 无需回复.)";
		String content ="你好，我是于富，正在使用名商通给你发信息呢，呜呜哈哈嘎嘎。(这是java测试案例)本公司及董事会全体成员保证信息披露的内容真实、准确、完整，没有虚假记载、误导性陈述或重大遗漏。你好，我是于富，正在使用名商通给你发信息呢，呜呜哈哈嘎嘎。(这是java测试案例)本公司及董事会全体成员保证信息披露的内容真实、准确、完整，没有虚假记载、误导性陈述或重大遗漏。你好，我是于富，正在使用名商通给你发信息呢，呜呜哈哈嘎嘎。(这是java测试案例)本公司及董事会全体成员保证信息披露的内容真实、";
		//测试发送的内容
	//	System.out.println("测试结果: "+sender.checkContent(content));
		//发送短信
		String result = sender.massSend("13804577933", content, null, null);
		System.out.println("发送结果: "+result);
		String fee = sender.getFee();
		System.out.println("剩余费用: "+fee);
//		String decodedFee = "";
//		try {
//			byte[] b = fee.getBytes("gb2312");
//			decodedFee = new String(b,"utf-8");
//			
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
//		System.out.println("剩余费用: "+decodedFee);
		
//		String strDefalt = "";  
//		String strDefaltEncode = "";  
//		String strChangeEncode = "";  
//		
//		try {  
//		    strDefalt = "我的心不乱";  
//		    strDefaltEncode = new String("我的心不乱".getBytes(),"utf-8");  
//		    strChangeEncode = new String("我的心不乱".getBytes("gbk"),"shift-jis");  
//		} catch (UnsupportedEncodingException e) {  
//		    e.printStackTrace();  
//		}  
//		System.out.println("Default charsetName="+Charset.defaultCharset().name());  
//		System.out.println("strDefalt="+strDefalt);  
//		System.out.println("strDefaltEncode="+strDefaltEncode);  
//		System.out.println("strChangeEncode="+strChangeEncode);  
	}
}
