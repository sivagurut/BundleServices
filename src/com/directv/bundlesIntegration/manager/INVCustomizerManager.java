package com.directv.bundlesIntegration.manager;

import java.util.HashSet;

import com.directv.broadbandBundles.ui.model.input.CustomizationModel;
import com.directv.broadbandBundles.ui.model.output.CustomizationSelection;
import com.directv.broadbandBundles.ui.model.output.ModelResponseDTO;
import com.directv.bundlesIntegration.exception.NVCException;

// TODO: Auto-generated Javadoc
/**
 * The Interface INVCustomizerManager.
 */
public interface INVCustomizerManager {

	/**
	 * Process nv customizations.
	 *
	 * @param xmlCustomizationInput the xml customization input
	 * @return the customization model
	 * @throws NVCException the nVC exception
	 */
	public CustomizationModel processNVCustomizations(String xmlCustomizationInput) throws NVCException;

	/**
	 * Process nv customizations.
	 *
	 * @param customSelection the custom selection
	 * @param modelResponseDto the model response dto
	 * @return the model response dto
	 */
	public ModelResponseDTO processNVCustomizations(CustomizationSelection customSelection, ModelResponseDTO modelResponseDto, HashSet<String> sensitiveIds);

}
