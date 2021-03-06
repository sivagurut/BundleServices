package com.directv.broadbandBundles.ui.controller;

import com.directv.broadbandBundles.customization.controller.NonVideoCustomizerPunchIn;
import com.directv.broadbandBundles.customization.controller.NonVideoCustomizerPunchOut;
import com.directv.broadbandBundles.customization.service.CustomizationsClient;
import com.directv.broadbandBundles.customization.translator.FormToModelCustomizations;
import com.directv.broadbandBundles.customization.translator.ModelToRequestCustomizations;
import com.directv.broadbandBundles.customization.translator.ResponseToModelCustomizations;
import com.directv.broadbandBundles.customization.translator.ResponseToXmlCustomizations;
import com.directv.broadbandBundles.ui.model.input.CustomizationModel;
import com.directv.broadbandBundles.ui.model.output.CustomizationSelection;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ResponseDocument;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.ProcessNonVideoCustomizationsRequestEntity;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.ProcessNonVideoCustomizationsResponseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

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
public class NonVideoCustomizerProcessor extends HttpServlet
{

    private final Log logger = LogFactory.getLog(getClass());

    public void service(HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException, IOException
    {

        //@todo fix if no session then punch out with no visitID.
        CustomizationSelection selectionModel = null;
        try
        {
            HttpSession session = request.getSession(false);
            if (session == null)
            {
                throw new Exception("No session.  Either a timeout or session did not stick.");
            }

            CustomizationModel model = (CustomizationModel) request.getSession(false).getAttribute(NonVideoCustomizerPunchIn.CUSTOMIZATION_PARAM_NAME);
            @SuppressWarnings("unchecked")
            HashSet<String> sensitiveIds = (HashSet<String>) request.getSession(false).getAttribute(NonVideoCustomizerRender.SENSITIVE_IDS);

            //parse the form data and use the input model to build the UI output model
            selectionModel = FormToModelCustomizations.getModelFromForm(request, model, sensitiveIds);

            logger.debug("selectionModel:" + selectionModel);
            //using the UI model build the request to Brigevine
            ProcessNonVideoCustomizationsRequestEntity customizationRequest = ModelToRequestCustomizations.
                    getRequestFromModel(selectionModel, sensitiveIds);
            //send the request to bridgevine and get the response

            ProcessNonVideoCustomizationsResponseEntity customizationResponse = CustomizationsClient.
                    getResponse(customizationRequest, selectionModel.getVisitId());

            if (customizationResponse == null)
            {
                logger.error("Customization Response is null");
                //error, empty response
                ResponseDocument xmlResponse = ResponseToXmlCustomizations.getErrorXmlFromModel(selectionModel,
                                                                                                "Null Response received, EI Middleware Failure.");
                //punch out to Edge System with error
                NonVideoCustomizerPunchOut.punchOut(response, xmlResponse, selectionModel.getPunchOutURL(),
                                                    selectionModel.getVisitId());
            }
            //if we got a message and it's not one of the recoverable messages, punch out
            else if (customizationResponse.getMessageList() != null &&
                    customizationResponse.getMessageList().getMessage() != null &&
                    customizationResponse.getMessageList().getMessage().length > 0 &&
                    customizationResponse.getMessageList().getMessage()[0].getMessageDetail() != null &&
                    ! (customizationResponse.getMessageList().getMessage()[0].getMessageDetail().getMessageCode().equals("600009") ||
                     customizationResponse.getMessageList().getMessage()[0].getMessageDetail().getMessageCode().equals("600008") ||
                     customizationResponse.getMessageList().getMessage()[0].getMessageDetail().getMessageCode().equals("600004")))
            {
                logger.error("Customization Message List - problems");
                //error(s) returned, pass on to edge system
                ResponseDocument xmlResponse = ResponseToXmlCustomizations.getErrorXmlFromResponse(
                        customizationResponse, selectionModel);
                //punch out to Edge System with error
                NonVideoCustomizerPunchOut.punchOut(response, xmlResponse, selectionModel.getPunchOutURL(),
                                                    selectionModel.getVisitId());
            }
            //more customizations? or punch out?
            else if (customizationResponse.getVisitId() != null && customizationResponse.getCustomizationsCompletedFlag())
            {
                logger.error("getCustomizationsCompletedFlag is true");

                //punch out
                //first translate Bridgevine response into XML
                ResponseDocument xmlResponse = ResponseToXmlCustomizations.getXmlFromResponse(customizationResponse);
                //punch out to Edge System with XML
                NonVideoCustomizerPunchOut.punchOut(response, xmlResponse, selectionModel.getPunchOutURL(),
                                                    selectionModel.getVisitId());
            }
            else
            {
                //more customizations
                logger.debug("more customizations");

                //new input model
                model = ResponseToModelCustomizations.getModelFromResponse(customizationResponse, selectionModel);

                //store customizations in session
                request.getSession(true).setAttribute(NonVideoCustomizerPunchIn.CUSTOMIZATION_PARAM_NAME, model);

                //forward request to Initiator for transfer of customizations to UI
                RequestDispatcher rd = request.getRequestDispatcher("/NonVideoCustomizerInitiate.dtv");
                rd.forward(request, response);
            }
        }
        catch (Exception e)
        {
            //more customizations
            logger.fatal("Fatal error sending customizations: " + e);
            StackTraceElement[] steArray = e.getStackTrace();
            if (steArray != null)
            {
                for (StackTraceElement ste : steArray)
                {
                    logger.error("at " + ste.toString());
                }
            }

            //fatal error
            ResponseDocument xmlResponse = ResponseToXmlCustomizations.getErrorXmlFromModel(selectionModel, e.toString());
            //punch out to Edge System with XML
            NonVideoCustomizerPunchOut.punchOut(response, xmlResponse, selectionModel.getPunchOutURL(), selectionModel.getVisitId());
        }
    }

}
