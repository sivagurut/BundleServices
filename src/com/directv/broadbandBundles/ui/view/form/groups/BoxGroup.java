package com.directv.broadbandBundles.ui.view.form.groups;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 5/9/11
 * Time: 12:50 PM
 * Model to hold elements of a checkbox or radio group
 */
public class BoxGroup
{
    private String label;
    private boolean allowBlank;
    private String labelStyle;

    public String getLabelStyle()
    {
        return labelStyle;
    }

    public void setLabelStyle(String labelStyle)
    {
        this.labelStyle = labelStyle;
    }

    private ArrayList<BoxGroupItem> checkboxItems;

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public boolean isAllowBlank()
    {
        return allowBlank;
    }

    public void setAllowBlank(boolean allowBlank)
    {
        this.allowBlank = allowBlank;
    }

    public ArrayList<BoxGroupItem> getCheckboxItems()
    {
        return checkboxItems;
    }

    public void setCheckboxItems(ArrayList<BoxGroupItem> checkboxItems)
    {
        this.checkboxItems = checkboxItems;
    }

    @Override
    public String toString()
    {
        return "CheckBoxGroup{" +
                "label='" + label + '\'' +
                ", allowBlank=" + allowBlank +
                ", labelStyle='" + labelStyle + '\'' +
                ", checkboxItems=" + checkboxItems +
                '}';
    }
}
