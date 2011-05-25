package com.directv.broadbandBundles.ui.view.form.elements;

import com.directv.broadbandBundles.ui.model.input.Customization;
import com.directv.broadbandBundles.ui.model.input.CustomizationItem;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 5/9/11
 * Time: 5:06 PM
 * Base class for all non-grouped elements - helper/utility methods
 */
public class BaseElement
{
    //private final Log logger = LogFactory.getLog(getClass());

    public static String stripTags(String text)
    {

        if (text == null) return null;

        for (int i = 0; i < 2; i++)
        {
            int lt = text.indexOf("<");
            int gt = text.indexOf(">");
            if (gt > -1)
            {
                if (lt > 0)
                {
                    text = text.substring(0, lt) + text.substring(gt + 1);
                }
                else
                {
                    text = text.substring(gt + 1);
                }
            }
        }
        return text;

    }

    public String escapeQuote(String text)
    {
        text = text.replaceAll("\\\"", "\\\\\"");
        return text;
    }

    public String escapeSingleQuote(String text)
    {
        text = text.replaceAll("'", "\\\\\\\\'");
        return text;
    }

    protected String getCustomizationPrice(CustomizationItem item)
    {
        if (item.getPrice() != null && item.getPrice().getPricePeriodType() != null)
        {
            return "$" + item.getPrice().getInitialAmount() + " " + item.getPrice().getPricePeriodType();
        }

        return null;
    }

    protected String getCustomizationName(Customization customization)
    {
        String name = BaseElement.stripTags(customization.getName());
        if (customization.isRequired())
        {
            name += "<span style=\"color:red; font-size: 14px;\">*</span>";
        }

        if (customization.getHelpType() != null &&
                (!customization.getHelpType().equals("InlineHTML")) &&
                customization.getHelpText() != null && customization.getHelpTitle() != null)
        {
            name += getCustomizationHelpLink(customization);
        }

        return name;
    }

    protected String getCustomizationHelpLink(Customization customiztion)
    {
        String link = "";

        //logger.debug("item.getHelpType()=" + customiztion.getHelpType());

        if (customiztion.getHelpType() != null &&
                (customiztion.getHelpType().equals("ModalPopup") || customiztion.getHelpType().equals("MouseOver")) &&
                customiztion.getHelpTitle() != null &&
                customiztion.getHelpText() != null)
        {
            link = " <a href=\"#\" style=\"color: #0099CC; font-weight: normal; font-family:arial,sans-serif; font-size: 12px;\" onClick=\"showAlert('" + escapeSingleQuote(customiztion.getName()) + "',";
            link += "'" + escapeSingleQuote(customiztion.getHelpText()) + "');\">";
            link += "What's this?</a>";

          //  logger.debug("link=" + link);

            return link;
        }

        return link;
    }

    public static void main(String[] args)
    {
        BaseElement be = new BaseElement();
        System.out.println("x=" + be.escapeSingleQuote("A Social Security Number and/or Driver's License Number are required when submitting an order. We cannot continue to process your order without this information. By placing this order, I authorize the required credit check through a credit check with a nationwide credit bureau or use of existing information on file with the service provider's family of companies."));

    }

}
