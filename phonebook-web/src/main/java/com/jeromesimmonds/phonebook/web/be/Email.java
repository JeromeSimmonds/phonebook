package com.jeromesimmonds.phonebook.web.be;

import javax.mail.internet.InternetAddress;

/**
 * @author Jerome Simmonds
 *
 */
public class Email {

	private String html;
	private String text;
	private InternetAddress[] to;
	private InternetAddress[] bcc;
	private InternetAddress[] cc;
	private InternetAddress from;
	private String subject;

	public String getHTML() {
		return html;
	}
	public void setHTML(String html) {
		this.html = html;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public InternetAddress getFrom() {
		return from;
	}
	public void setFrom(InternetAddress from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public InternetAddress[] getTo() {
		return to;
	}
	public void setTo(InternetAddress... to) {
		this.to = to;
	}
	public InternetAddress[] getBcc() {
		return bcc;
	}
	public void setBcc(InternetAddress... bcc) {
		this.bcc = bcc;
	}
	public InternetAddress[] getCc() {
		return cc;
	}
	public void setCc(InternetAddress... cc) {
		this.cc = cc;
	}
}
