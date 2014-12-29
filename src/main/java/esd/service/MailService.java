package esd.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service
public class MailService {
	private String host = "smtp.163.com";
	
	/**
	 * 暂时使用此邮箱
	 */
	private String user = "supertestabc@163.com";
	private String pwd = "abc123";
	private String from = "supertestabc@163.com";
	
	/**
	 * 过几天使用以下的邮箱
	 */
//	private String user = "jobservicehelper@163.com";
//	private String pwd = "superesd123$%^";
//	private String from = "jobservicehelper@163.com";
	
	
//	private String to = "254043921@qq.com";
	private String subjectUserName = "残疾人就业信息网找回用户名";
	private String subjectPassWord = "残疾人就业信息网找回密码";
	/**
	 * 向目标邮箱发送密码
	 */
	public Boolean sendUserNamePwd(String email,String type,String val) {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.transport.protocol", "smtp");
		Session session = Session.getInstance(props);
		session.setDebug(true);

		// 创建一个邮件对象 message
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					email));
			// 整封邮件
			MimeMultipart bodyMultipart = new MimeMultipart();

			// 邮件主题显示的内容
			MimeBodyPart bodyPart = new MimeBodyPart();
//			MimeBodyPart attach1 = new MimeBodyPart();
			bodyMultipart.addBodyPart(bodyPart);
//			bodyMultipart.addBodyPart(attach1);
			// body内的内容
			String typeContent ="";
			if("username".equals(type)){
				message.setSubject(subjectUserName);
				typeContent = "用_户_名_";
			}else if("password".equals(type)){
				message.setSubject(subjectPassWord);
				typeContent = "密_码_";
			}else{
				typeContent = " 传 递 的 类 型 参 数 为 空,  请 联 系 管 理 员";
			}
			String content = "您_的_"+typeContent+"为: <b>" + val + "</b>, 请_妥_善_保_管.";
			bodyPart.setContent(content,
					"text/html;charset=UTF-8");

			// 将body的内容保存到邮件内
			message.setContent(bodyMultipart);
			// 保存更改信息

//			// 给附件attach1附加上文件
//			DataSource ds = new FileDataSource("d:\\10000000.png");
//			DataHandler dh = new DataHandler(ds);
//			attach1.setDataHandler(dh);
//			try {
//				attach1.setFileName(MimeUtility.encodeText("10000000.png"));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			message.saveChanges();

			// 传输对象
			Transport transport = session.getTransport("smtp");
			// 建立连接
			transport.connect(host, user, pwd);
			// 发送邮件
			transport.sendMessage(message, message.getAllRecipients());
			// 关闭传输
			transport.close();
			return true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
//	public static void main(String[] args) {
//		MailService ms = new MailService();
//		ms.sendPwd("254043921@qq.com", "laidawoya123$%^50");
//	}

}
