package com.cdtu.util;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class SendEmail {
	public static void sendPasswordByEmail(String email,String password) {
		//
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		// 参考QQ邮箱帮助中心
		mailSender.setHost("smtp.qq.com"); // QQ邮箱smtp发送服务器地址
		//mailSender.setPort(465); // QQ这个端口不可用
		mailSender.setPort(587);// 端口号
		mailSender.setUsername("2515106327@qq.com"); // 使用你自己的账号
		mailSender.setPassword("oxdrlcjksayedjbj"); // 授权码-发短信获取
		// 邮件信息
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("2515106327@qq.com"); // 发件人邮箱
		msg.setTo(email); // 收件人邮箱
		msg.setSubject("找回密码邮件"); // 标题
		msg.setText("这是您的密码:"+password); // 文本信息
		try {
			mailSender.send(msg);
			System.out.println("邮件发送成功!"); // 没有报错就是好消息 :-)
		} catch (MailException ex) {
			System.err.println("发送失败:" + ex.getMessage());
		}
	}

}
