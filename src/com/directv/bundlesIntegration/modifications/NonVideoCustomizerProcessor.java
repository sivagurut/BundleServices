package com.directv.bundlesIntegration.modifications;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.directv.broadbandBundles.customization.service.CustomizationsClient;
import com.directv.broadbandBundles.customization.translator.ModelToRequestCustomizations;
import com.directv.broadbandBundles.customization.translator.ResponseToXmlCustomizations;
import com.directv.broadbandBundles.ui.model.input.CustomizationModel;
import com.directv.broadbandBundles.ui.model.output.CustomizationSelection;
import com.directv.broadbandBundles.ui.model.output.ModelResponseDTO;
import com.directv.broadbandBundles.ui.model.output.ResponseDTO;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ResponseDocument;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.ProcessNonVideoCustomizationsRequestEntity;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.ProcessNonVideoCustomizationsResponseEntity;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/25/11
 * Time: 10:24 AM
 * Processes an NVC post
 * 1 - parses the post data
 * 2 - builds a request to Bridevine to send the post data
 * 3 - gets a response from Bridgevine
 * 4 - determine if there are more customizations
 * 5 - if there are then send them onto the browser
 * 6 - else punch out to edge system
 */
public class NonVideoCustomizerProcessor {

	private final Log logger = LogFactory.getLog(getClass());

	public static final String CHARACTER_SET = "UTF-8";

	public ModelResponseDTO processInfo(CustomizationSelection selectionModel, HashSet<String> sensitiveIds, ModelResponseDTO modelResponseDto) {

		//Jim's commented code
		//@todo fix if no session then punch out with no visitID.
		//CustomizationSelection selectionModel = null;
		try {

			//Jim's commented code. [Reason] - Since session is not needed and sensitiveIds we are sending as input param
			//HttpSession session = request.getSession(false);
			//if (session == null) {
			//	throw new Exception("No session.  Either a timeout or session did not stick.");
			//}
			//CustomizationModel model = (CustomizationModel) request.getSession(false)
			//		.getAttribute(NonVideoCustomizerPunchIn.CUSTOMIZATION_PARAM_NAME);
			//@SuppressWarnings("unchecked")
			//HashSet<String> sensitiveIds = (HashSet<String>) request.getSession(false).getAttribute(NonVideoCustomizerRender.SENSITIVE_IDS);
			//parse the form data and use the input model to build the UI output model
			//selectionModel = FormToModelCustomizations.getModelFromForm(request, model, sensitiveIds);

			logger.debug("selectionModel:" + selectionModel);

			//using the UI model build the request to Brigevine
			ProcessNonVideoCustomizationsRequestEntity customizationRequest = ModelToRequestCustomizations.getRequestFromModel(selectionModel,
					sensitiveIds);

			//send the request to bridgevine and get the response
			ProcessNonVideoCustomizationsResponseEntity customizationResponse = CustomizationsClient.getResponse(customizationRequest, selectionModel
					.getVisitId());

			if (customizationResponse == null) {

				logger.error("Customization Response is null");
				//error, empty response
				ResponseDocument xmlResponse = ResponseToXmlCustomizations.getErrorXmlFromModel(selectionModel,
						"Null Response received, EI Middleware Failure.");

				//Jim's commented code. [Reason] - Punchout through servlet is not possible since we have ajax call
				//punch out to Edge System with error
				//NonVideoCustomizerPunchOut.punchOut(response, xmlResponse, selectionModel.getPunchOutURL(), selectionModel.getVisitId());

				//New code
				return punchOut(modelResponseDto, xmlResponse, selectionModel.getPunchOutURL());

			}
			//if we got a message and it's not one of the recoverable messages, punch out
			else if (customizationResponse.getMessageList() != null
					&& customizationResponse.getMessageList().getMessage() != null
					&& customizationResponse.getMessageList().getMessage().length > 0
					&& customizationResponse.getMessageList().getMessage()[0].getMessageDetail() != null
					&& !(customizationResponse.getMessageList().getMessage()[0].getMessageDetail().getMessageCode().equals("600009")
							|| customizationResponse.getMessageList().getMessage()[0].getMessageDetail().getMessageCode().equals("600008") || customizationResponse
							.getMessageList().getMessage()[0].getMessageDetail().getMessageCode().equals("600004"))) {

				logger.error("Customization Message List - problems");
				//error(s) returned, pass on to edge system
				ResponseDocument xmlResponse = ResponseToXmlCustomizations.getErrorXmlFromResponse(customizationResponse, selectionModel);

				//Jim's commented code. [Reason] - Punchout through servlet is not possible since ajax call
				//punch out to Edge System with error
				//NonVideoCustomizerPunchOut.punchOut(response, xmlResponse, selectionModel.getPunchOutURL(), selectionModel.getVisitId());

				//New code
				return punchOut(modelResponseDto, xmlResponse, selectionModel.getPunchOutURL());

			}
			//more customizations? or punch out?
			else if (customizationResponse.getVisitId() != null && customizationResponse.getCustomizationsCompletedFlag()) {

				logger.error("getCustomizationsCompletedFlag is true");

				//punch out
				//first translate Bridgevine response into XML
				ResponseDocument xmlResponse = ResponseToXmlCustomizations.getXmlFromResponse(customizationResponse);

				//Jim's Commented code. [Reason] - Punchout through servlet is not possible since ajax call
				//punch out to Edge System with XML
				//NonVideoCustomizerPunchOut.punchOut(response, xmlResponse, selectionModel.getPunchOutURL(), selectionModel.getVisitId());

				//New code
				return punchOut(modelResponseDto, xmlResponse, selectionModel.getPunchOutURL());

			} else {

				//more customizations
				logger.debug("more customizations");

				//new input model
				CustomizationModel model = ResponseToModelCustomizations.getModelFromResponse(customizationResponse, selectionModel);

				//Jim's commented code. [Reason] - Since session is not required any more
				//store customizations in session
				//request.getSession(true).setAttribute(NonVideoCustomizerPunchIn.CUSTOMIZATION_PARAM_NAME, model);
				//forward request to Initiator for transfer of customizations to UI
				//RequestDispatcher rd = request.getRequestDispatcher("/NonVideoCustomizerInitiate.dtv");
				//rd.forward(request, response);

				//New code
				return moreCustomizations(modelResponseDto, model);
			}
		} catch (Exception e) {

			//more customizations
			logger.fatal("Fatal error sending customizations: " + e);
			StackTraceElement[] steArray = e.getStackTrace();
			if (steArray != null) {
				for (StackTraceElement ste : steArray) {
					logger.error("at " + ste.toString());
				}
			}

			//fatal error
			ResponseDocument xmlResponse = ResponseToXmlCustomizations.getErrorXmlFromModel(selectionModel, e.toString());

			//Jim's commented code. [Reason] - Since punchout through servlet is not possible since ajax call
			//punch out to Edge System with XML
			//NonVideoCustomizerPunchOut.punchOut(response, xmlResponse, selectionModel.getPunchOutURL(), selectionModel.getVisitId());

			//New code
			return punchOut(modelResponseDto, xmlResponse, "http://den-e44237.la.frd.directv.com:7001/NonVideoCustomizer/C3SimulatorPunchOut");
		}
	}

	//New code
	private ModelResponseDTO moreCustomizations(ModelResponseDTO modelResponseDto, CustomizationModel model) {

		//Setting the status as MoreCustomizations so that in NVCProcessorImpl we are 
		//able to identify if return data from NonVideoCustomizerProcessor is a punchOut or more customizations
		modelResponseDto.setStatus(ModelResponseDTO.STATUS_MORE_CUSTOMIZATIONS);
		//This model data we will be converting to json in NVCProcessorImpl and send to nvcForm.js
		modelResponseDto.setModel(model);
		return modelResponseDto;
	}

	//New code
	private ModelResponseDTO punchOut(ModelResponseDTO modelResponseDto, ResponseDocument xmlResponse, String punchOutURL) {

		//Setting the status as PunchOut so that in NVCProcessorImpl we are 
		//able to identify is return data from NonVideoCustomizerProcessor is a punchout or more customizations
		modelResponseDto.setStatus(ModelResponseDTO.STATUS_PUNCHOUT);
		ResponseDTO responseDto = new ResponseDTO();
		responseDto.setUrl(punchOutURL);
		try {
			//Encoding the data before punching out
			responseDto.setCusResponse(URLEncoder.encode(xmlResponse.toString(), CHARACTER_SET));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		modelResponseDto.setResponseDto(responseDto);
		return modelResponseDto;
	}
}
