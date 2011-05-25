package com.directv.broadbandBundles.ui.model.output;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class ResponseDTO.
 */
public class ResponseDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8738968544540156909L;

	/** The url. */
	private String url;

	/** The cus response. */
	private String cusResponse;

	/** The response data. */
	private Map<String, List<String>> responseData = new HashMap<String, List<String>>();

	//	/** The is immediate flag. */
	//	private boolean isImmediateSubmissionFlag;

	/**
	 * Gets the customization response.
	 *
	 * @return the cusResponse
	 */
	public String getCusResponse() {
		return cusResponse;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Sets the cus response.
	 *
	 * @param cusResponse the new cus response
	 */
	public void setCusResponse(String cusResponse) {
		this.cusResponse = cusResponse;
	}

	/**
	 * Gets the response data.
	 *
	 * @return the response data
	 */
	public Map<String, List<String>> getResponseData() {
		return responseData;
	}

	/**
	 * Sets the response data.
	 *
	 * @param responseData the response data
	 */
	public void setResponseData(Map<String, List<String>> responseData) {
		this.responseData = responseData;
	}
}
