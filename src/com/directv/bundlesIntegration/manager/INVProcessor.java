package com.directv.bundlesIntegration.manager;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.directv.broadbandBundles.ui.model.output.ResponseDTO;
import com.directv.bundlesIntegration.exception.NVCException;

// TODO: Auto-generated Javadoc
/**
 * The Interface INVProcessor.
 */
public interface INVProcessor {

	/**
	 * Process submitted info.
	 *
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the response dto
	 * @throws NVCException the nVC exception
	 */
	public ResponseDTO processSubmittedInfo(HttpServletRequest request, HttpServletResponse response) throws NVCException;

	/**
	 * Process nv customizations.
	 *
	 * @param xmlCustomizationInput the xml customization input
	 * 
	 * @return the map< string,? extends object>
	 * @throws NVCException the nVC exception
	 */
	public Map<String, ? extends Object> processNVCustomizations(String xmlCustomizationInput) throws NVCException;

	/**
	 * Cancel bundle.
	 *
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the response dto
	 * @throws NVCException the nVC exception
	 */
	public ResponseDTO cancelBundle(HttpServletRequest request, HttpServletResponse response) throws NVCException;
}
