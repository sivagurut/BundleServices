package com.directv.broadbandBundles.ui.view.form.fields;

import com.directv.broadbandBundles.ui.view.form.elements.BaseElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/22/11
 * Time: 2:39 PM
 * EXT-JS Label
 */
public class LabelField extends BaseElement

{
    public static String LABEL_STYLE_INSTRUCTION_TEXT = "font-weight: bold; font-family:arial,sans-serif; font-size: 12px; color: blue; ";
    public static String LABEL_STYLE_LABEL = "font-weight: bold; font-family:arial,sans-serif; font-size: 12px;";
    public static String LABEL_STYLE_HELP = "font-weight: normal; font-family:arial,sans-serif; font-size: 11px;";
    public static String LABEL_STYLE_ERROR_TEXT = "font-weight: bold; font-family:arial,sans-serif; font-size: 12px; color: red; ";

    private final Log logger = LogFactory.getLog(getClass());

    public void setOptions(StringBuffer b, String fieldLabel, String labelStyle, boolean isFirst)
    {
//        logger.debug("fieldLabel:" + fieldLabel );
//        logger.debug("isFirst=" + isFirst);
        if (! isFirst)
        {
            b.append("\r\n,");
//            logger.debug("added comma for:" + fieldLabel );
        }
        b.append("\r\n{").
                append("\r\nxtype: 'label'").
                append("\r\n,fieldLabel: \"").append(escapeQuote(fieldLabel)).append("\"").
                append("\r\n,labelStyle: '").append(labelStyle).append("'").
                append("\r\n}");

    }
}
