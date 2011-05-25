package com.directv.bundlesIntegration.manager.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.directv.bundlesIntegration.modifications.FormToModelCustomizations;
import com.directv.broadbandBundles.ui.model.input.Customization;
import com.directv.broadbandBundles.ui.model.input.CustomizationDTO;
import com.directv.broadbandBundles.ui.model.input.CustomizationGroup;
import com.directv.broadbandBundles.ui.model.input.CustomizationGroupList;
import com.directv.broadbandBundles.ui.model.input.CustomizationItem;
import com.directv.broadbandBundles.ui.model.input.CustomizationList;
import com.directv.broadbandBundles.ui.model.input.CustomizationModel;
import com.directv.broadbandBundles.ui.model.output.CustomizationSelection;
import com.directv.broadbandBundles.ui.model.output.ModelResponseDTO;
import com.directv.broadbandBundles.ui.model.output.ResponseDTO;
import com.directv.bundlesIntegration.controller.NVCValidator;
import com.directv.bundlesIntegration.exception.NVCException;
import com.directv.bundlesIntegration.manager.INVCustomizerManager;
import com.directv.bundlesIntegration.manager.INVProcessor;
import com.directv.bundlesIntegration.util.NVCConstants;
import com.directv.ei.schemas.entities.v31.MessageCategoryTypesEntity;
import com.directv.ei.schemas.entities.v31.MessageDetailEntity;
import com.directv.ei.schemas.entities.v31.MessageEntity;
import com.directv.ei.schemas.entities.v31.MessageListEntity;
import com.directv.ei.schemas.entities.v31.MessageSeverityTypesEntity;
import com.directv.ei.schemas.entities.v31.MessageStatusTypesEntity;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ResponseDocument;

// TODO: Auto-generated Javadoc
/**
 * The Class NVProcessorImpl.
 */
@Service
public class NVProcessorImpl implements INVProcessor {

	/** The logger. */
	private static Logger logger = Logger.getLogger(NVProcessorImpl.class);

	/** The Constant MODEL_DATA. */
	private static final String MODEL_DATA = "modelData";

	/** The Constant IMMEDIATE_SUBMIT_FLAG. */
	private static final String IMMEDIATE_SUBMIT_FLAG = "immediateSubmitFlag";
	
	/** The Constant HIDDEN_JSON_DATA. */
	public static final String HIDDEN_JSON_DATA = "hiddenJsonObj";

	/** The customizer manager. */
	private INVCustomizerManager customizerManager;

	/**
	 * Sets the customizer manager.
	 *
	 * @param customizerManager the new customizer manager
	 */
	@Autowired
	public void setCustomizerManager(INVCustomizerManager customizerManager) {
		this.customizerManager = customizerManager;
	}

	/**
	 * Process nv customizations.
	 *
	 * @param xmlCustomizationInput the xml customization input
	 * @return the map< string,? extends object>
	 * @throws NVCException the nVC exception
	 */
	public Map<String, ? extends Object> processNVCustomizations(String xmlCustomizationInput) throws NVCException {

		logger.info("Entering NVProcessorImpl :: processNVCustomizations(String xmlCustomizationInput)");
		// Getting the data for the first time
		CustomizationModel custModel = customizerManager.processNVCustomizations(xmlCustomizationInput);
		HashMap<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put(MODEL_DATA, custModel);
		//This has been set to remove EXTJS validation in client side
		//if immediateSubmission flag is true
		modelMap.put(IMMEDIATE_SUBMIT_FLAG, String.valueOf(custModel.isImmediateSubmissionFlag()));

		logger.info("Exiting NVProcessorImpl :: processNVCustomizations(String xmlCustomizationInput)");
		return modelMap;
	}
	
	/**
	 * Process Submitted form data.
	 *
	 * @param request HttpServletRequest 
	 * @param httpResponse HttpServletResponse
	 * 
	 * @return response ResponseDTO
	 * @throws NVCException the nVC exception
	 */
	@SuppressWarnings("unchecked")
	public ResponseDTO processSubmittedInfo(HttpServletRequest request, HttpServletResponse httpResponse) throws NVCException {

		logger.info("Entering NVProcessorImpl :: processSubmittedInfo(HttpServletRequest request)");

		ResponseDTO response = null;

		// These mapping resources are required to convert the jsonData to Bean
		Map mappingResources = new HashMap();
		mappingResources.put("modelData", CustomizationModel.class);
		mappingResources.put("groupList", CustomizationGroupList.class);
		mappingResources.put("customizationList", CustomizationList.class);
		mappingResources.put("customizationGroup", CustomizationGroup.class);
		mappingResources.put("customization", Customization.class);
		mappingResources.put("item", CustomizationItem.class);

		logger.info("NVProcessorImpl :: processSubmittedInfo(HttpServletRequest request) :: HiddenJSONValue :: "
				+ request.getParameter("hiddenJsonObj"));

		// Converting json data to bean
		CustomizationModel custModel = (CustomizationModel) JSONObject.toBean(JSONObject.fromObject(request.getParameter("hiddenJsonObj")),
				CustomizationModel.class, mappingResources);

		// Setting the values from the form to bean
		setValues(request, custModel);

		logger.info("NVProcessorImpl :: processSubmittedInfo(HttpServletRequest request)" );
		if (!custModel.isImmediateSubmissionFlag()) {
			// Validate the data that user entered
			response = NVCValidator.validate(custModel);
		} else {
			// No need to validate the data due to immediate submission requirement
			response = getResponse();
		}

		// If there are no validation errors then we can call the web service for processing
		if (response.getResponseData().get(NVCConstants.STATUS_MAIN).get(0).equalsIgnoreCase(NVCConstants.SUCCESS_MAIN)) {

			HashSet<String> sensitiveIds = getSensitiveIds(custModel);
			// Setting the values from CustomizationModel bean to CustomizationSelection bean
			CustomizationSelection customSelection = new CustomizationSelection();
			try {
				customSelection = FormToModelCustomizations.getModelFromForm(request, custModel, sensitiveIds);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			// Calling the manager layer to get modelResponseDto
			// that will contain the status if we need to PunchOut or it contain MoreCustomizations
			ModelResponseDTO modelResponseDto = new ModelResponseDTO();
			modelResponseDto = customizerManager.processNVCustomizations(customSelection, modelResponseDto, sensitiveIds);

			// PunchOut
			if (modelResponseDto.getStatus().equals(ModelResponseDTO.STATUS_PUNCHOUT)) {

				System.out.println("PunchOut URL :: " + modelResponseDto.getResponseDto().getUrl());
				return redirectURL(response, modelResponseDto.getResponseDto());
			}
			// More customizations
			else if (modelResponseDto.getStatus().equals(ModelResponseDTO.STATUS_MORE_CUSTOMIZATIONS)) {

				// Again re render the form data if there is more customizations
				CustomizationDTO custDto = new CustomizationDTO();
				custDto.setModelData(modelResponseDto.getModel());
				// Converting the bean to json object
				JSONObject jsonObject = JSONObject.fromObject(custDto);
				response.setCusResponse(jsonObject.toString());
				logger.info("NVProcessorImpl :: processSubmittedInfo(HttpServletRequest request) :: NextFormJSONValue :: " + jsonObject.toString());
			}
		}
		return response;
	}

	private HashSet<String> getSensitiveIds(CustomizationModel custmizationModel) {
		HashSet<String> sensitiveIds = new HashSet<String>();
		if (custmizationModel != null && custmizationModel.getGroupList() != null) {
			for (CustomizationGroup customizationGroup : custmizationModel.getGroupList().getCustomizationGroup()) {
					for (CustomizationList customizationList : customizationGroup.getCustomizationList()) {
							for (Customization customization : customizationList.getCustomization()) {
								if (customization.isSensitiveFlag()) {
									sensitiveIds.add(customization.getCustomizationId());
								}
							}
						}
				}
			}
		return sensitiveIds;
	}

	/**
	 * Gets the response.
	 *
	 * @param immediateSubmission the immediate submission
	 * @return the response
	 */
	private ResponseDTO getResponse() {

		logger.info("Entering NVProcessorImpl :: getResponse() :: " );
		ResponseDTO response = new ResponseDTO();
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		// Setting status 
		List<String> successMain = new ArrayList<String>();
		successMain.add(NVCConstants.SUCCESS_MAIN);
		map.put(NVCConstants.STATUS_MAIN, successMain);

		// Setting message
		List<String> successMessage = new ArrayList<String>();
		successMessage.add("Data submitted successfully");
		map.put(NVCConstants.SUCCESS, successMessage);

		response.setResponseData(map);
		logger.info("Exiting NVProcessorImpl :: getResponse(boolean immediateSubmission)");
		return response;
	}

	/**
	 * Redirect url.
	 *
	 * @param response the response
	 * @param responseDTO 
	 * @return the response dto
	 */
	private ResponseDTO redirectURL(ResponseDTO response, ResponseDTO responseDTOFromWS) {

		ResponseDTO responseNew = null;
		responseNew = (response != null) ? response : responseDTOFromWS;

		logger.info("Entering NVProcessorImpl :: redirectURL(ResponseDTO response)");
		Map<String, List<String>> map = new HashMap<String, List<String>>();

		//Setting status
		List<String> redirectMain = new ArrayList<String>();
		redirectMain.add(NVCConstants.REDIRECT_MAIN);
		map.put(NVCConstants.STATUS_MAIN, redirectMain);

		//Setting Url and xmlCustomizationOutput
		List<String> dataToTransfer = new ArrayList<String>();
		dataToTransfer.add(responseDTOFromWS.getUrl());
		dataToTransfer.add(responseDTOFromWS.getCusResponse());
		map.put(NVCConstants.DATA, dataToTransfer);

		responseNew.setResponseData(map);
		logger.info("Exiting NVProcessorImpl :: redirectURL(ResponseDTO response)");
		return responseNew;
	}

	/**
	 * Sets the values.
	 *
	 * @param request the request
	 * @param custModel the cust model
	 */
	private void setValues(HttpServletRequest request, CustomizationModel custModel) {

		logger.info("Entering NVProcessorImpl :: setValues(HttpServletRequest request, CustomizationModel custModel)");
		// Getting the values from the form and setting those values in the bean
		for (CustomizationGroup customizationGroup : custModel.getGroupList().getCustomizationGroup()) {
			for (CustomizationList customizationList : customizationGroup.getCustomizationList()) {
				for (Customization customization : customizationList.getCustomization()) {

					customization.setTextValue(request.getParameter(customization.getCustomizationId()));
					// Only since selection possible in drop down or radio button
					if (customization.getType().equals(NVCConstants.RADIO_BUTTONS) || customization.getType().equals(NVCConstants.DROP_DOWN)) {
						for (CustomizationItem item : customization.getItem()) {
							if (customization.getTextValue() != null && item.getId().equals(customization.getTextValue())) {
								item.setSelected(true);
							} else {
								item.setSelected(false);
							}
						}
					} else if (customization.getType().equals(NVCConstants.CHECK_BOX) || customization.getType().equals(NVCConstants.CHECK_BOX_LIST)) {
						// Since multiple selection is possible in check box its done separately
						// To identify all the selected items
						String[] selectedItems = request.getParameterValues(customization.getCustomizationId());
						List<String> selectedItemIds = new ArrayList<String>();
						for (String selectedItemId : selectedItems) {
							selectedItemIds.add(selectedItemId);
						}
						for (CustomizationItem item : customization.getItem()) {
							if (selectedItemIds.size() > 0 && selectedItemIds.contains(item.getId())) {
								item.setSelected(true);
							} else {
								item.setSelected(false);
							}
						}
					}
				}
			}
		}
		logger.info("Exiting NVProcessorImpl :: setValues(HttpServletRequest request, CustomizationModel custModel)");
	}

	/**
	 * Cancel bundle.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the response dto
	 * @throws NVCException the nVC exception
	 */
	public ResponseDTO cancelBundle(HttpServletRequest request, HttpServletResponse resp) throws NVCException {

		ResponseDocument doc = ResponseDocument.Factory.newInstance();
		com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ProcessNonVideoCustomizationsResponseEntity response = doc
				.addNewResponse();

		// Set CustomizationsCompletedFlag as incomplete
		response.setCustomizationsCompletedFlag(false);
		long visitId = 0;
		// Set visitId
		if (request.getParameter(NVCConstants.VISIT_ID) != null) {
			visitId = Long.parseLong(request.getParameter(NVCConstants.VISIT_ID));
			response.setVisitId(visitId);
		}
		//Add message
		response.addNewMessageList();

		MessageListEntity messageList = response.getMessageList();
		messageList.addNewMessage();

		MessageEntity message = messageList.getMessageArray(0);
		message.setMessageStatus(MessageStatusTypesEntity.FAILURE);
		message.setProvider("NVC");
		message.addNewMessageDetail();

		MessageDetailEntity messageDetail = message.getMessageDetail();
		messageDetail.setMessageCode("2");
		messageDetail.setMessageText("NVC exiting due to bundle cancellation.");
		messageDetail.setMessageSeverity(MessageSeverityTypesEntity.ERROR);
		messageDetail.setMessageCategory(MessageCategoryTypesEntity.SYSTEM);
		String errorMessage = "Bundle Cancelled.";
		messageDetail.setAdditionalInformation(errorMessage);

		//punchout
		String returnURL = request.getParameter(NVCConstants.RETURN_URL);

		//Offshore commented code. [Reason] - Since punchout through servlet is not possible since ajax call
		//try {
		//	NonVideoCustomizerPunchOut.punchOut(resp, doc, returnURL, response.getVisitId());
		//} catch (ServletException e1) {
		//	e1.printStackTrace();
		//} catch (IOException e1) {
		//	e1.printStackTrace();
		//}

		//Offshore new code
		ResponseDTO responseDto = new ResponseDTO();
		responseDto.setUrl(returnURL);
		try {
			//Encoding the data before punching out
			responseDto.setCusResponse(URLEncoder.encode(doc.toString(), NVCConstants.UTF_FILE_FORMAT));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return redirectURL(null, responseDto);
	}
}
