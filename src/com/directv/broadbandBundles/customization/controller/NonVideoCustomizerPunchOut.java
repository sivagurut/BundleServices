package com.directv.broadbandBundles.customization.controller;

import com.directv.broadbandBundles.customization.logger.SensitiveDataLogger;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ResponseDocument;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/25/11
 * Time: 10:04 AM
 * When the NVC customizations are completed (or a failure occurs) return control to the calling system
 */
public class NonVideoCustomizerPunchOut
{

    private static final Log logger = LogFactory.getLog("com.directv.broadbandBundles.customization.controller.NonVideoCustomizerPunchOut");

    public static final String CHARACTER_SET = "UTF-8";

    private static final String CUSTOMIZATION_PARAM_NAME = "xmlCustomizationOutput";

    public static void punchOut(HttpServletResponse response,
                                ResponseDocument xmlResponse,
                                String returnURL,
                                long visitId)
            throws ServletException, IOException
    {
        try
        {
            //don't log unless masking sensitive data
            //the following is just so we can log the request while masking sensitive data
            SensitiveDataLogger.logDocumentSensitiveData(xmlResponse);

            //UTF-8 encode response XML
            String customizationXmlOutput = URLEncoder.encode("" + xmlResponse, CHARACTER_SET);

            //build a new form for browser
            //it will post the xml output back to the Edge System
            StringBuffer jsonResponse = new StringBuffer();

            jsonResponse.append("{ success: true, punchOut: true, returnURL: '").append(returnURL).append("', ").
                    append(CUSTOMIZATION_PARAM_NAME).append(": '").append(customizationXmlOutput).append("'}");

            logger.debug("Punch out page <" + visitId + "> :: returnURL <" + returnURL + ">");

            ServletOutputStream out = response.getOutputStream();
            out.print(jsonResponse.toString());
            out.flush();
        }
        catch (Exception e)
        {
            logger.fatal("Fatal error while punching out: ", e);
            StackTraceElement[] steArray = e.getStackTrace();
            if (steArray != null)
            {
                for (StackTraceElement ste : steArray)
                {
                    logger.fatal("at " + ste.toString());
                }
            }

            //@todo if returnURL okay then still punch out with error
            //otherwise forward to error page
        }
    }

}

