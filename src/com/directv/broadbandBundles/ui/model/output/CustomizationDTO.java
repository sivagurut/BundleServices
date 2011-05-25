package com.directv.broadbandBundles.ui.model.output;

import java.io.Serializable;

import com.directv.broadbandBundles.ui.model.input.CustomizationModel;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomizationDTO.
 */
public class CustomizationDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3375706497617874175L;

	/** The model data. */
	private CustomizationModel modelData = new CustomizationModel();

	/**
	 * Gets the model data.
	 * 
	 * @return the model data
	 */
	public CustomizationModel getModelData() {
		return modelData;
	}

	/**
	 * Sets the model data.
	 * 
	 * @param modelData the new model data
	 */
	public void setModelData(CustomizationModel modelData) {
		this.modelData = modelData;
	}

}
