package com.jeromesimmonds.phonebook.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jerome Simmonds
 *
 */
public class Utils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);
	
	private static final String	HEADER_X_FORWARDED_FOR	= "X-Forwarded-For";
	private static final Pattern IP_PATTERN =  Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");
	private static final Pattern PRIVATE_IP_PATTERN = Pattern.compile("(^127\\.0\\.0\\.1)|(^10\\.)|(^172\\.1[6-9]\\.)|(^172\\.2[0-9]\\.)|(^172\\.3[0-1]\\.)|(^192\\.168\\.)");
	
	/*
	 * This method is implemented to solve the issue of load balance 
	 * on production servers, which sets the IP on the header instead. 
	 * So we first check the header then the HttpServletRequest.
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String result = null;
		String ips = request.getHeader(HEADER_X_FORWARDED_FOR);
		if (ips != null) {
			if (LOGGER.isDebugEnabled()) LOGGER.debug("IPs in header: " + ips);
			Matcher matcher = IP_PATTERN.matcher(ips);
			
			// filtering private addresses
			while (matcher.find() && result == null) {
				Matcher privateIPMatcher = PRIVATE_IP_PATTERN.matcher(matcher.group(1));
				if (!privateIPMatcher.find()) result = matcher.group(1);
			}
		}
		if (ips == null) result = request.getRemoteAddr();
		if (LOGGER.isDebugEnabled()) LOGGER.debug("Getting user IP: " + result);
		return result;
	}
}
