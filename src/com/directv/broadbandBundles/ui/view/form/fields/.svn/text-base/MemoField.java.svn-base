package com.directv.broadbandBundles.ui.view.form.fields;

import com.directv.broadbandBundles.ui.model.input.Customization;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/1/11
 * Time: 11:04 AM
 * EXT-JS Textarea
 */
public class MemoField extends BaseField implements GenericField
{
    public void setOptions(StringBuffer b,
                           Customization customization,
                           boolean isFirst) throws Exception
    {
        String label;
        String labelStyle;

        if (customization.getHelpType()!= null && customization.getHelpType().equals("InlineHTML"))
        {
            label = escapeQuote(stripTags(customization.getHelpText()));
            labelStyle = LabelField.LABEL_STYLE_HELP;


            if (customization.getName() != null)
            {
                //add label, always provided
                new LabelField().setOptions(b, escapeQuote(getCustomizationName(customization)), LabelField.LABEL_STYLE_LABEL, isFirst);
                isFirst = false;
            }
        }
        else
        {
            label = escapeQuote(getCustomizationName(customization));
            labelStyle = LabelField.LABEL_STYLE_LABEL;
        }
        //make sure we got a max length
        if (customization.getMaximumTextLength() < 1) customization.setMaximumTextLength(50);



        if (! isFirst)
        {
            b.append("\r\n,");
        }
        b.append("\r\n{").
                append("\r\nxtype: 'textarea'").
                append("\r\n,fieldLabel: '").append(label).append("'").
                append("\r\n,labelStyle: '").append(labelStyle).append("'").
                append("\r\n,allowBlank: ").append(!customization.isRequired()).
                append("\r\n,name: '").append(customization.getCustomizationId()).append("'").
                append("\r\n,anchor: '").append(getCustomizationAnchor(customization)).append("'").
                append("\r\n,maxLength: '").append(customization.getMaximumTextLength()).append("'").
                append("\r\n,style: {textTransform: 'uppercase'}");

        if (customization.getTextValue() != null && customization.getTextValue().length() > 0)
        {
           b.append("\r\n,value : '").append(customization.getTextValue()).append("'");
        }
        if (customization.getRegularExpression() != null && customization.getRegularExpression().length() > 0)
        {
           b.append("\r\n,regex : /").append(customization.getRegularExpression()).append("/").
            append("\r\n,regexText: '").append(customization.getValidationErrorString()).append("'");
        }

        b.append("\r\n}");
    }

}
