package com.directv.broadbandBundles.ui.model.input;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/11/11
 * Time: 3:41 PM
 * Bridgevine Customization List
 */
public class CustomizationList
{
    private ArrayList<Customization> customization;

    public ArrayList<Customization> getCustomization()
    {
        return customization;
    }

    public void setCustomization(ArrayList<Customization> customization)
    {
        this.customization = customization;
    }

    @Override
    public String toString()
    {
        return "CustomizationList{" +
                "customization=" + customization +
                '}';
    }
}
