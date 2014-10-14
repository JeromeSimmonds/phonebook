package com.jeromesimmonds.phonebook.web.helper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.jeromesimmonds.phonebook.core.be.User;
import com.jeromesimmonds.phonebook.web.be.Email;

/**
 * @author Jerome Simmonds
 *
 */
public class EmailHelperImpl implements EmailHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailHelperImpl.class);

	private final String TEMPLATES_PATH = "emailTemplates/"; // in resources
	private final String TEMPLATE_FORGOTPASSWORD = "forgotPassword";
	private final String TEMPLATE_SIGNUP = "signUp";
	private final String TEMPLATE_CHANGEEMAIL = "changeEmail";
	private final String TEMPLATE_CHANGEEMAILCANCEL = "changeEmailCancel";
	private final String DOMAIN = "domain";
	private final String TOKEN = "token";

	@Value("${domain}")
	private String domain;

	private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    
    private String fromContact;
	private String fromNoReply;
	
	private static class MessagePreparator implements MimeMessagePreparator {
		private static final String	UTF8 = "UTF-8";
		
		private Email email;
		
		public MessagePreparator(Email email) {
			this.email = email;
		}
		
		@Override
		public void prepare(MimeMessage mimeMessage) throws Exception {
			MimeMessageHelper msg = new MimeMessageHelper(mimeMessage, isMultipart(), UTF8);
			msg.setTo(email.getTo());
			if (email.getBcc() != null && email.getBcc().length > 0) msg.setBcc(email.getBcc());
			if (email.getCc() != null && email.getCc().length > 0) msg.setCc(email.getCc());
			msg.setSubject(email.getSubject());
			msg.setFrom(email.getFrom());
			if (isMultipart()) {
				msg.setText(email.getText(), email.getHTML());
			} else if (email.getHTML() != null) {
				msg.setText(email.getHTML(), true);
			} else {
				msg.setText(email.getText());
			}
		}

		private boolean isMultipart() {
			return email.getHTML() != null && email.getText() != null;
		}
	}

	private Email prepareEmail(String templateName, Map<String, Object> values) throws Exception {
		Email email = new Email();
		Locale locale = LocaleContextHolder.getLocale();
		email.setSubject(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, TEMPLATES_PATH + templateName + "_subject.vm", "UTF-8", values));
		email.setHTML(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, TEMPLATES_PATH + templateName + "_body.vm", "UTF-8", values));
		return email;
	}
	
	public void send(final Email email) throws Exception {
		if (email == null) throw new Exception("Email cannot be null.");
		if (email.getTo() == null) throw new Exception("TO address cannot be null.");
		if (email.getFrom() == null) throw new Exception("FROM address cannot be null.");
		try {
			MimeMessagePreparator oPreparator = new MessagePreparator(email);
			mailSender.send(oPreparator);
		} catch (MailException e) {
			LOGGER.error("Error sending email (from: " + email.getFrom() + " to: " + email.getFrom() + ")", e);
			throw new Exception(e);
		}
	}

	public void sendForgotPasswordEmail(User user, String token) throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(TOKEN, token);
		values.put(DOMAIN, domain);
		Email oEmail = prepareEmail(TEMPLATE_FORGOTPASSWORD, values);
		oEmail.setTo(new InternetAddress(user.getEmail()));
		oEmail.setFrom(new InternetAddress(fromNoReply));
		send(oEmail);
	}

	public void sendSignUpEmail(User user, String token) throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(TOKEN, token);
		values.put(DOMAIN, domain);
		Email oEmail = prepareEmail(TEMPLATE_SIGNUP, values);
		oEmail.setTo(new InternetAddress(user.getEmail()));
		oEmail.setFrom(new InternetAddress(fromNoReply));
		send(oEmail);
	}

	@Override
	public void sendChangeEmail(User user, String token) throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(TOKEN, token);
		Email oEmail = prepareEmail(TEMPLATE_CHANGEEMAIL, values);
		oEmail.setTo(new InternetAddress(user.getEmail()));
		oEmail.setFrom(new InternetAddress(fromNoReply));
		send(oEmail);
	}
	
	@Override
	public void sendChangeEmailCancel(User user, String token, String previousEmailAddress) throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put(TOKEN, token);
		Email oEmail = prepareEmail(TEMPLATE_CHANGEEMAILCANCEL, values);
		oEmail.setTo(new InternetAddress(previousEmailAddress));
		oEmail.setFrom(new InternetAddress(fromNoReply));
		send(oEmail);
	}
	
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public String getFromContact() {
		return fromContact;
	}
	public void setFromContact(String fromContact) {
		this.fromContact = fromContact;
	}
	public String getFromNoReply() {
		return fromNoReply;
	}
	public void setFromNoReply(String fromNoReply) {
		this.fromNoReply = fromNoReply;
	}
}
