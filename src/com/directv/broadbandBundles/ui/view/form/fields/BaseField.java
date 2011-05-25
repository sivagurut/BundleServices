package com.directv.broadbandBundles.ui.view.form.fields;

import com.directv.broadbandBundles.ui.model.input.Customization;
import com.directv.broadbandBundles.ui.model.input.CustomizationItem;
import com.directv.broadbandBundles.ui.view.form.elements.BaseElement;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 5/9/11
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseField extends BaseElement
{
    protected String getCustomizationAnchor(Customization customization)
        {
            String anchor;
            if (customization.getType().equals("DropDown"))
            {
                int maxLength = 0;
                for (CustomizationItem item: customization.getItem())
                {
                    if (item.getText().length() > maxLength) maxLength = item.getText().length();
                }

                //allow for the "choose..." display to show in dropdown
                if (maxLength < 7) maxLength = 7;

                customization.setMaximumTextLength(maxLength);
            }

            if (customization.getMaximumTextLength() > 0)
            {
                anchor = 5 + customization.getMaximumTextLength() + "%";
            }
            else
            {
                    anchor = "75%";
            }

            return anchor;

        }


}
