package com.directv.bundlesIntegration.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.directv.broadbandBundles.ui.model.output.ResponseDTO;
import com.directv.bundlesIntegration.exception.NVCException;
import com.directv.bundlesIntegration.manager.INVProcessor;

// TODO: Auto-generated Javadoc
/**
 * The Class NVCustomizerController handles the onSubmit event.
 */
@Controller
@RequestMapping("submitData.json")
public class NVCustomizerController {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(NVCustomizerController.class);

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
	 * Gets the data after form data is processed.
	 *
	 * @param request the request
	 * 
	 * @return the data
	 * @throws NVCException the nVC exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	ResponseDTO getData(HttpServletRequest request, HttpServletResponse response ) throws NVCException {
		logger.info("Entering NVCustomizerController :: getData(HttpServletRequest request)");
		return processor.processSubmittedInfo(request, response);
	}
}
