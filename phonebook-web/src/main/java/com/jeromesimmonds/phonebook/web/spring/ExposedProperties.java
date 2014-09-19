package com.jeromesimmonds.phonebook.web.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Jerome Simmonds
 *
 */
public class ExposedProperties  {

	private Map<String, Object> properties;

	// Properties we want to expose
	@Value("${avatar.path}")
	private String avatarPath;

	public Map<String, Object> getProperty() {
		if (properties == null) {
			properties = new HashMap<String, Object>();
			// Properties we want to expose
			properties.put("avatar.path", avatarPath);
		}
        return properties;
    }
}
