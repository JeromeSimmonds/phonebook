package com.jeromesimmonds.phonebook.web.be;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeromesimmonds.phonebook.web.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Jerome Simmonds
 *
 */
public class RESTResponse<T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RESTResponse.class);
	
	public static final String JSON_ERROR = "{\"error\":{\"message\":\"An error occured while serializing data\",\"code\":1}}";
	
	public static final int ERRORCODE_SERIALIZATION = 1;
	public static final int ERRORCODE_AUTHENTICATION = 2;
	public static final int ERRORCODE_MISSINGPARAMETER = 3;
	public static final int ERRORCODE_BUSINESSRULE = 4;

	private String warning;
	private String errorMessage;
	private int errorCode = 0;
	private T result;
	private Map<String, Object> properties;
	
	/*public String getXML() {
		StringBuilder oReturn = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><response>");
		oReturn.append("<status>").append(status).append("</status>");
		oReturn.append("<errorCode>").append(errorCode).append("</errorCode>");
		oReturn.append("<errorMessage>").append(errorMessage == null ? "" : errorMessage).append("</errorMessage>");
		oReturn.append("<result>").append(value == null ? "" : value).append("</result>");
		oReturn.append("</response>");
		return oReturn.toString();
	}*/

	public String getJSON() {
		Map<String, Object> json = new HashMap<String, Object>();
		if (warning != null)
			json.put(Constants.WARNING, warning);
		if (errorMessage != null || errorCode > 0) {
			Map<String, Object> error = new HashMap<String, Object>();
			if (errorMessage != null)
				error.put(Constants.MESSAGE, errorMessage);
			if (errorCode > 0)
				error.put(Constants.CODE, errorCode);
			json.put(Constants.ERROR, error);
		}
		if (result != null)
			json.put(Constants.RESULT, result);
		if (properties != null)
			for (String k : properties.keySet())
				json.put(k, properties.get(k));
			
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		try {
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage());
			return JSON_ERROR;
		}
	}

	public void addProperty(String key, Object value) {
		if (properties == null) properties = new HashMap<String, Object>();
		properties.put(key, value);
	}
	
	public String getWarning() {
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public void setError(String message, int code) {
		errorMessage = message;
		errorCode = code;
	}
}
