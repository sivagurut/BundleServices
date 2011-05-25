package com.directv.broadbandBundles.ui.controller;

import com.directv.broadbandBundles.customization.translator.ModelToFormCustomizations;
import com.directv.broadbandBundles.ui.model.input.CustomizationModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 5/3/11
 * Time: 3:21 PM
 * Handles displaying the Customization Input model to the browser
 */
public class NonVideoCustomizerRender
{

private static final Log logger = LogFactory.getLog("com.directv.broadbandBundles.customization.controller.NonVideoCustomizerRender");

    public static final String SENSITIVE_IDS = "SENSITIVE_IDS";

    public static HashSet<String> renderGetUI(CustomizationModel model, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        StringBuffer b = new StringBuffer();
        HashSet<String> sensitiveIds = null;

        try
        {
            sensitiveIds = ModelToFormCustomizations.setFormFromModel(model, b);
            //save the sensitive IDs for processing
            request.getSession(false).setAttribute(SENSITIVE_IDS, sensitiveIds);
        }
        catch (Exception e)
        {
            logger.error(e);
            StackTraceElement[] steArray = e.getStackTrace();
            if (steArray != null)
            {
                for (StackTraceElement ste : steArray)
                {
                    logger.error("at " + ste.toString());
                }
            }
        }

        logger.debug("********************************** bundles JS:" + b.toString());

        ServletOutputStream out = response.getOutputStream();
        out.println(b.toString());
        out.flush();

        return sensitiveIds;
    }

}
