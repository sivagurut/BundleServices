package com.directv.broadbandBundles.ui.view.form.elements;

import com.directv.broadbandBundles.ui.model.input.Customization;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/28/11
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GenericGroupElement
{
    public void addGroupElementToForm(Customization customization,
                                      StringBuffer b,
                                      boolean firstFieldSetItem) throws Exception;
}
