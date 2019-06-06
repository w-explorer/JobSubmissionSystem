package com.cdtu.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cdtu.model.PublishWork;

@Service
public class SendEmailService {
	private @Resource(name = "courseService") CourseService courseService;
	@Async
	public  void sendPasswordByEmail(String email,String password) {
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
		msg.setText("【成工云班课】密码：:"+password+";请勿重复点击发送，也不要透露给他人！"); // 文本信息
		try {
			mailSender.send(msg);
			System.out.println("邮件发送成功!"); // 没有报错就是好消息 :-)
		} catch (MailException ex) {
			System.err.println("发送失败:" + ex.getMessage());
		}
	}
	//老师发布作业邮箱通知大家
	@Async
	public  void sendWorkInfoByEmail(List<String> EmailList,PublishWork publishWork) {
		// TODO Auto-generated method stub
		String[] strings = new String[EmailList.size()];
		strings = EmailList.toArray(strings);
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
		
		String cName = courseService.selectCnameByCid(publishWork.getcId());
		for (String string : strings) {
			if(!"null".equals(string)){
				msg.setTo(string); // 收件人邮箱
				msg.setSubject("熊猫课堂"); // 标题
				msg.setText("【作业提醒】："+cName+"新增活动：【"+publishWork.getPwName()+"】;请于--"+publishWork.getPwEnd()+"--之前完成作业"); // 文本信息
				try {
					mailSender.send(msg);
					System.out.println("邮件发送成功!"); // 没有报错就是好消息 :-)
				} catch (MailException ex) {
					System.err.println("发送失败:" + ex.getMessage());
				}
			}
		}
		
	}
	
	
//	public static void main(String[] args) {
//		ArrayList<String> list =new ArrayList<String>();
//		list.add("2515106327@qq.com");
//		list.add("null");
//		SendEmailService emailService = new SendEmailService();
//		PublishWork publishWork = new PublishWork();
//		emailService.sendWorkInfoByEmail(list,publishWork);
//		System.out.println("进行中。。。");
//		
//	}

}
