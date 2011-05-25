package com.directv.bundlesIntegration.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class NVCException.
 */
public class NVCException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new nVC exception.
	 * 
	 * @param message the message
	 */
	public NVCException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new nVC exception.
	 */
	public NVCException() {

	}

	/**
	 * Overridden Method.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		String message = super.getMessage();
		return message;
	}
}
