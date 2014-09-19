package com.jeromesimmonds.phonebook.web.tag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

import com.jeromesimmonds.phonebook.core.be.PhoneNumber;
import com.jeromesimmonds.phonebook.web.Constants;

/**
 * @author Jerome Simmonds
 *
 */
public class Functions {
	
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
	//private static final Pattern PONCTUATION = Pattern.compile("[.!?:;,]");

	public static String slugify(String text) {
		if (text == null || text.length() == 0)
			return Constants.EMPTY;
		String result = WHITESPACE.matcher(text).replaceAll("-");
		//result = PONCTUATION.matcher(text).replaceAll("-");
		result = Normalizer.normalize(result, Form.NFD);
		result = NONLATIN.matcher(result).replaceAll("-");
		return result.toLowerCase(Locale.ENGLISH).replaceAll("-+", "-");
	}

	public static String urlEncode(String value, String charset) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, charset);
	}
	
	public static String phoneNumberType(PhoneNumber number) {
		switch (number.getType()) {
			case PhoneNumber.TYPE_HOME: return Constants.HOME;
			case PhoneNumber.TYPE_OFFICE: return Constants.OFFICE;
			case PhoneNumber.TYPE_MOBILE: return Constants.MOBILE;
			default: return Constants.OTHER;
		}
	}
}
