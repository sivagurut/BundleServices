package com.directv.broadbandBundles.ui.model.output;

import java.io.Serializable;

import com.directv.broadbandBundles.ui.model.input.CustomizationModel;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelResponseDTO.
 */
public class ModelResponseDTO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1549083650050605226L;

	/** The Constant STATUS_PUNCHOUT. */
	public static final String STATUS_PUNCHOUT = "PunchOut";

	/** The Constant STATUS_MORE_CUSTOMIZATIONS. */
	public static final String STATUS_MORE_CUSTOMIZATIONS = "MoreCustomizations";

	/** The status. */
	private String status;

	/** The model if there are more more customizations. */
	private CustomizationModel model = new CustomizationModel();

	/** The response dto if its a punchout. */
	private ResponseDTO responseDto = new ResponseDTO();

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public CustomizationModel getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(CustomizationModel model) {
		this.model = model;
	}

	/**
	 * Gets the response dto.
	 *
	 * @return the response dto
	 */
	public ResponseDTO getResponseDto() {
		return responseDto;
	}

	/**
	 * Sets the response dto.
	 *
	 * @param responseDto the new response dto
	 */
	public void setResponseDto(ResponseDTO responseDto) {
		this.responseDto = responseDto;
	}

}
