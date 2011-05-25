package com.directv.bundlesIntegration.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.directv.broadbandBundles.ui.model.input.Customization;
import com.directv.broadbandBundles.ui.model.input.CustomizationGroup;
import com.directv.broadbandBundles.ui.model.input.CustomizationList;
import com.directv.broadbandBundles.ui.model.input.CustomizationModel;
import com.directv.broadbandBundles.ui.model.output.ResponseDTO;
import com.directv.bundlesIntegration.util.NVCConstants;
import com.directv.bundlesIntegration.util.NVCErrorUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class NVCValidator validates the form data.
 */
public class NVCValidator {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(NVCValidator.class);

	/** The Constant ERROR_MESSAGE_COMPLETE. */
	private static final String ERROR_MESSAGE_COMPLETE = "errormsg.completed";

	/** The Constant ERROR_MESSAGE_LENGTH. */
	private static final String ERROR_MESSAGE_LENGTH = "errormsg.length";

	/** The Constant ERROR_REGULAR_EXP. */
	private static final String ERROR_REGULAR_EXP = "errormsg.regular.exp";

	/**
	 * Validate.
	 * 
	 * @param custModel the cust model
	 * @return the response dto
	 */
	public static ResponseDTO validate(CustomizationModel custModel) {

		logger.debug("Entering NVCValidator :: validate(CustomizationModel custModel)");

		List<String> errorMessages = new ArrayList<String>();
		for (CustomizationGroup customizationGroup : custModel.getGroupList().getCustomizationGroup()) {
			for (CustomizationList customizationList : customizationGroup.getCustomizationList()) {
				for (Customization customization : customizationList.getCustomization()) {

					// Doing the Required field validation
					if (customization.isRequired()) {
						if (customization.getTextValue() == null || customization.getTextValue().trim().equals("")) {
							// Getting the error message from the response xml
							if (!customization.isRegularExpressionValidationFlag() && customization.getValidationErrorString() != null
									&& !customization.getValidationErrorString().trim().equals("")) {
								// Set id in the error msg to access form field for setting the error class
								errorMessages.add(customization.getValidationErrorString() + ":" + customization.getCustomizationId());
							} else { // If not set the default error message
								// Set id in the error msg to access form field for setting its class as invalid
								errorMessages.add(NVCErrorUtils.getMessage(ERROR_MESSAGE_COMPLETE) + " " + customization.getName() + ":"
										+ customization.getCustomizationId());
							}
						}
					}

					// Doing the MaxLength validation
					if (customization.getTextValue() != null && !customization.getTextValue().trim().equals("")) {
						if (customization.isLengthValidationFlag()) {
							if (customization.getTextValue().length() > customization.getMaximumTextLength()) {
								// Getting the error message from the response xml
								if (customization.getValidationErrorString() != null && !customization.getValidationErrorString().trim().equals("")) {
									errorMessages.add(customization.getValidationErrorString() + ":" + customization.getCustomizationId());
								} else { // If not set the default error message
									errorMessages.add(customization.getName() + " " + NVCErrorUtils.getMessage(ERROR_MESSAGE_LENGTH) + " "
											+ customization.getMaximumTextLength() + ":" + customization.getCustomizationId());
								}
							}
						}
					}

					// Regular Expression validation
					if (customization.isRegularExpressionValidationFlag()) {
						if (customization.getRegularExpression() != null && customization.getRegularExpression().trim() != "") {
							Pattern p = Pattern.compile(customization.getRegularExpression().trim());
							Matcher m = p.matcher(customization.getTextValue().trim());
							if (!m.find()) {
								// Getting the error message from the response xml
								if (customization.getValidationErrorString() != null && !customization.getValidationErrorString().trim().equals("")) {
									errorMessages.add(customization.getValidationErrorString() + ":" + customization.getCustomizationId());
								} else { // If not set the default error message
									errorMessages.add(customization.getName() + " " + NVCErrorUtils.getMessage(ERROR_REGULAR_EXP) + ":"
											+ customization.getCustomizationId());
								}
							}
						}
					}
				}
			}
		}
		// If there are no error messages then return success
		if (errorMessages.size() == 0) {
			return returnSuccess();
		} else { // If there are error messages then return those error messages
			return returnErrors(errorMessages);
		}
	}

	/**
	 * Return errors.
	 * 
	 * @param errorMessages the error messages
	 * @return the response dto
	 */
	private static ResponseDTO returnErrors(List<String> errorMessages) {

		logger.debug("Entering NVCValidator :: returnErrors(List<String> errorMessages) :: " + errorMessages.size());
		for (String error : errorMessages) {
			logger.debug("NVCValidator :: returnErrors(List<String> errorMessages) :: " + error);
		}
		ResponseDTO response = new ResponseDTO();
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		// Setting status
		List<String> errorMain = new ArrayList<String>();
		errorMain.add(NVCConstants.ERROR_MAIN);
		map.put(NVCConstants.STATUS_MAIN, errorMain);

		//Setting error messages
		map.put(NVCConstants.ERROR, errorMessages);

		response.setResponseData(map);
		logger.debug("Exiting NVCValidator :: returnErrors(List<String> errorMessages)");
		return response;
	}

	/**
	 * Return success.
	 * 
	 * @return the response dto
	 */
	private static ResponseDTO returnSuccess() {

		logger.debug("Entering NVCValidator :: returnSuccess()");
		// No error messages so return success
		ResponseDTO response = new ResponseDTO();

		// Setting status 
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> successMain = new ArrayList<String>();
		successMain.add(NVCConstants.SUCCESS_MAIN);
		map.put(NVCConstants.STATUS_MAIN, successMain);

		// Setting message
		List<String> successMessage = new ArrayList<String>();
		successMessage.add("Data submitted successfully");
		map.put(NVCConstants.SUCCESS, successMessage);

		response.setResponseData(map);
		logger.debug("Exiting NVCValidator :: returnSuccess()");
		return response;
	}
}
