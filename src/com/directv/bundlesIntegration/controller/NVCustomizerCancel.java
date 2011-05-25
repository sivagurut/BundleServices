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
 * The Class NVCustomizerCancel, cancel handler.
 */
@Controller
@RequestMapping("cancelBundle.json")
public class NVCustomizerCancel {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(NVCustomizerCancel.class);

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
	 * Cancel bundle.
	 *
	 * @param request the request
	 * @param response the response
	 * 
	 * @return the response dto
	 * @throws NVCException the nVC exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	ResponseDTO cancelBundle(HttpServletRequest request, HttpServletResponse response) throws NVCException {
		logger.debug("Entering NVCustomizerAjax :: cancelBundle(HttpServletRequest request, HttpServletResponse response)");
		return processor.cancelBundle(request, response);
	}
}
