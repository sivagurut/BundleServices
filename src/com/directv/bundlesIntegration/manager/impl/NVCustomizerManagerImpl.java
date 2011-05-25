package com.directv.bundlesIntegration.manager.impl;

import java.net.URLEncoder;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import com.directv.broadbandBundles.ui.model.input.CustomizationModel;
import com.directv.broadbandBundles.ui.model.output.CustomizationSelection;
import com.directv.broadbandBundles.ui.model.output.CustomizationSelectionItem;
import com.directv.broadbandBundles.ui.model.output.ModelResponseDTO;
import com.directv.bundlesIntegration.exception.NVCException;
import com.directv.bundlesIntegration.manager.INVCustomizerManager;
import com.directv.bundlesIntegration.modifications.NonVideoCustomizerProcessor;
import com.directv.bundlesIntegration.modifications.ResponseToModelCustomizations;
import com.directv.bundlesIntegration.modifications.XmlToModelCustomizations;
import com.directv.bundlesIntegration.util.XMLUllity;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ResponseDocument;

// TODO: Auto-generated Javadoc
/**
 * The Class NVCustomizerManagerImpl.
 */
@Service
public class NVCustomizerManagerImpl implements INVCustomizerManager {

	/** The logger. */
	private static Logger logger = Logger.getLogger(NVCustomizerManagerImpl.class);

	/**
	 * Process nv customizations.
	 * @param xmlCustomizationInput 
	 * 
	 * @return the customization model
	 * @throws NVCException the nVC exception
	 */
	public CustomizationModel processNVCustomizations(String xmlCustomizationInput) throws NVCException {

		CustomizationModel model = null;
		logger.debug("Entering NVCustomizerManagerImpl :: processNVCustomizations(String xmlCustomizationInput)");
		try {
			// Calling Jim's Transformer to get CustomizationModel from xml file
			model = XmlToModelCustomizations.getModelFromXml(xmlCustomizationInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * Process nv customizations after form is submitted.
	 *
	 * @param customSelection the custom selection
	 * @param modelResponseDto the model response dto
	 * @return the model response dto
	 */
	@Override
	public ModelResponseDTO processNVCustomizations(CustomizationSelection customSelection, ModelResponseDTO modelResponseDto, HashSet<String> sensitiveIds) {
		logger.debug("Entering NVCustomizerManagerImpl :: processNVCustomizations(CustomizationSelection customSelection, ModelResponseDTO modelResponseDto, HashSet<String> sensitiveIds)");
		//Calling Jim's webservice
		NonVideoCustomizerProcessor nonVideoProcessor = new NonVideoCustomizerProcessor();
		return nonVideoProcessor.processInfo(customSelection, sensitiveIds, modelResponseDto);
	}
}
