package com.directv.bundlesIntegration.util;

import java.util.ResourceBundle;

// TODO: Auto-generated Javadoc
/**
 * The Class NVCErrorUtils.
 */
public class NVCErrorUtils {

	/** The Constant rb. */
	private static final ResourceBundle rb = ResourceBundle.getBundle("com.directv.bundlesIntegration.properties.Errormessages");

	/** The Constant UNEXPECTED_EXCEPTION_OCCURS. */
	public static final String UNEXPECTED_EXCEPTION_OCCURS = "UNEXPECTED_EXCEPTION_OCCURS";

	/** The Constant DISPATCH_EXCEPTION_OCCURS. */
	public static final String DISPATCH_EXCEPTION_OCCURS = "DISPATCH_EXCEPTION_OCCURS";

	/**
	 * Gets the message.
	 * 
	 * @param messageKey the message key
	 * @return the message
	 */
	public static String getMessage(String messageKey) {
		return rb.getString(messageKey);
	}

	/**
	 * Format error message.
	 * 
	 * @param messageKey the message key
	 * @param args the args
	 * @return the string
	 */
	public static String formatErrorMessage(String messageKey, Object... args) {
		return String.format(rb.getString(messageKey), args);
	}

}
