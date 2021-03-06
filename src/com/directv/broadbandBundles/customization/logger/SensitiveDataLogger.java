package com.directv.broadbandBundles.customization.logger;

import com.directv.broadbandBundles.ui.masker.StringMasker;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.NonVideoCustomizationEntity;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.NonVideoCustomizationGroupEntity;
import com.directv.ei.schemas.wsdl.nonVideoServices.v30.processNonVideoCustomizations.ResponseDocument;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.XmlException;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 5/19/11
 * Time: 10:25 AM
 * Masks sensitive data before logging
 */
public class SensitiveDataLogger
{
    private static final Log logger = LogFactory.getLog("com.directv.broadbandBundles.customization.logger.SensitiveDataLogger");


    public static void logXmlSensitiveData(String xml) throws XmlException
    {
        logDocumentSensitiveData(ResponseDocument.Factory.parse(xml));
    }

    public static void logDocumentSensitiveData(ResponseDocument doc)
    {
        if (doc.getResponse() != null &&
                doc.getResponse().getCustomizationGroupList() != null &&
                doc.getResponse().getCustomizationGroupList().getCustomizationGroupArray() != null)
        {
            for (NonVideoCustomizationGroupEntity group : doc.getResponse().getCustomizationGroupList().getCustomizationGroupArray())
            {
                if (group.getCustomizationList() != null &&
                        group.getCustomizationList().getCustomizationArray() != null)
                {
                    for (NonVideoCustomizationEntity customization : group.getCustomizationList().getCustomizationArray())
                    {
                        if (customization.getSensitiveFlag())
                        {
                            if (customization.getSensitiveTextValue() != null)
                            {
                                if (customization.getSensitiveTextValue().length() > 8)
                                {
                                    //if sensitive value is long then mask all but last 4
                                    customization.setSensitiveTextValue(new StringMasker(0, customization.getSensitiveTextValue().length() - 5).mask(customization.getSensitiveTextValue()));
                                }
                                else
                                {
                                    //if sensitive value is short then mask entire field
                                    customization.setSensitiveTextValue(new StringMasker().mask(customization.getSensitiveTextValue()));
                                }
                            }
                        }
                    }
                }
            }
        }
        logger.debug(doc);
    }
}
