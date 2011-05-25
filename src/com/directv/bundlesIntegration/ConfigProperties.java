package com.directv.bundlesIntegration;

import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigProperties.
 * 
 */
public class ConfigProperties {

	/** The properties. */
	static HashMap<String, String> properties = new HashMap<String, String>();

	/** The bundle. */
	static ResourceBundle bundle = null;

	/** The p. */
	static Properties p = new Properties();
	static {
		bundle = ResourceBundle.getBundle("com.directv.bundlesIntegration.properties.Application");

	}
  
	/**
	 * Gets the value.
	 * 
	 * @param key the key
	 * @return the value
	 */
	public static String getValue(String key) {
		return bundle.getString(key);
	}

}
