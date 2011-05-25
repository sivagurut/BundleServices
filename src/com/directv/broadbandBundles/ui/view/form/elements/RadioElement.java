package com.directv.broadbandBundles.ui.view.form.elements;

import com.directv.broadbandBundles.ui.view.form.fields.RadioGroupField;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/28/11
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class RadioElement extends BaseGroupElement implements GenericGroupElement
{
    public GenericGroupField getField()
    {
        return new RadioGroupField();
    }

    public String getItemType()
    {
        return "linkradio";
    }

}

