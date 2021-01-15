package com.jobsngigs.companyuserregistrationmicroservice.configuration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class MailHelper {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	private String generateTextForEmail(String name, String url) {
		Map<String,Object> replaceVariables = new HashMap<String,Object>();
		replaceVariables.put("name", name);
		replaceVariables.put("url", url);
		final String templateFile = "confirmationMail";
		String generatedEmail = this.templateEngine.process(templateFile, new Context(Locale.getDefault(),replaceVariables));
		return generatedEmail;
	}
		
	public boolean sendActivationEmail(String to, String name, String text) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom("no-reply@gmail.com");
			helper.setTo(to);
			helper.setSubject("JobsnGigs: Confirm your Account!");
			helper.setText(generateTextForEmail(name,text),true);
			mailSender.send(message);
			return true;
		}
		catch(MessagingException e){
			log.info("Exception thrown,{}",e);
			return false;
		}
	}

	public Boolean sendConfirmationMessage(String to, String name) {
		try {
			MimeMessage confirmationEmail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(confirmationEmail, true);
			helper.setFrom("no-reply@gmail.com");
			helper.setTo(to);
			helper.setSubject("JobsNGigs: Account Confirmation");
			String text = "<div><h3 align='center'>Your account has been activated!!Thank you.</div></h3>";
			helper.setText(text, true);
			mailSender.send(confirmationEmail);
			return true;
		}catch (MessagingException e) {
			log.info("Exception thrown,{}",e);
			return false;
		}
	}

}
