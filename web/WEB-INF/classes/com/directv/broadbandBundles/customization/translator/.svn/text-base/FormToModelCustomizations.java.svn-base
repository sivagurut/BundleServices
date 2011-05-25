package com.directv.broadbandBundles.customization.translator;

import com.directv.broadbandBundles.ui.masker.StringMasker;
import com.directv.broadbandBundles.ui.model.input.CustomizationModel;
import com.directv.broadbandBundles.ui.model.output.CustomizationSelection;
import com.directv.broadbandBundles.ui.model.output.CustomizationSelectionItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 5/2/11
 * Time: 1:42 PM
 * Takes the data submitted by a user and builds the CustomizationSelection object used to send data to Bridgevine.
 */
public class FormToModelCustomizations
{
    private static final Log logger = LogFactory.getLog("com.directv.broadbandBundles.customization.translator.FormToModelCustomizations");


    public static CustomizationSelection getModelFromForm(HttpServletRequest request,
                                                          CustomizationModel model,
                                                          HashSet<String> sensitiveIds) throws Exception
    {
        CustomizationSelection selection = new CustomizationSelection();
        ArrayList<CustomizationSelectionItem> items = new ArrayList<CustomizationSelectionItem>();

        //do not rely on visit Id from browser, could be modified, provided on form only for tracking purposes.
        selection.setVisitId(model.getVisitId());
        selection.setImmediateSubmission(model.isImmediateSubmissionFlag());
        selection.setPunchOutURL(model.getPunchOutURL());

        //dump all request params for debugging
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements())
        {
            String name = (String) names.nextElement();
            String value = request.getParameter(name);

            //eat the visit ID param, only set on form for tracking purposes
            if (!name.equals(ModelToFormCustomizations.VISIT_ID))
            {
                CustomizationSelectionItem item = new CustomizationSelectionItem();
                item.setCustomizationId(name);
                item.setValue(value.toUpperCase());
                items.add(item);

                //don't log sensitive data
                String logValue = item.getValue();
                if (sensitiveIds.contains(name))
                {
                    if (logValue != null)
                    {
                        if (logValue.length() > 8)
                        {
                            //long enough to mask all but last 4
                            logValue = new StringMasker(0, logValue.length() - 5).mask(logValue);
                        }
                        else
                        {
                            //short value, mask the whole thing
                            logValue = new StringMasker().mask( logValue );
                        }
                    }
                }
                logger.debug("********************************** parm name <" + name + "> :: value<" + logValue + ">");
            }
        }

        selection.setItems(items);

        return selection;
    }

}
