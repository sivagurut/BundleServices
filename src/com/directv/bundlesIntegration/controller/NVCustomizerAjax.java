package com.directv.bundlesIntegration.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.directv.bundlesIntegration.exception.NVCException;
import com.directv.bundlesIntegration.manager.INVProcessor;

// TODO: Auto-generated Javadoc
/**
 * The Class NVCustomizerAjax loads the data initially.
 */
@Controller
@RequestMapping("getData.json")
public class NVCustomizerAjax {

	/** The logger. */
	private static Logger logger = Logger.getLogger(NVCustomizerAjax.class);

	/** The Constant CHARACTER_SET. */
	private static final String CHARACTER_SET = "UTF-8";

	/** The Constant CUSTOMIZATION_PARAM_NAME. */
	private static final String CUSTOMIZATION_PARAM_NAME = "xmlCustomizationInput";

	/** The processor. */
	private INVProcessor processor;

	/**
	 * Sets the processor.
	 *
	 * @param processor the new processor
	 */
	@Autowired
	public void setProcessor(INVProcessor processor) {
		this.processor = processor;
	}

	/**
	 * Process nv customizations.
	 *
	 * @param request the request
	 * @return the map< string,? extends object>
	 * @throws NVCException the nVC exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> processNVCustomizations(HttpServletRequest request) throws NVCException {
		logger.info("Entering NVCustomizerAjax :: loadData(HttpServletRequest request)");
		Map<String, ? extends Object> responseData = null;
		try {
			responseData = processor.processNVCustomizations(URLDecoder.decode(request.getParameter(CUSTOMIZATION_PARAM_NAME), CHARACTER_SET));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return responseData;
	}
}
