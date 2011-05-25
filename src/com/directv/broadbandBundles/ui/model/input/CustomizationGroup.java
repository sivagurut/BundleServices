package com.directv.broadbandBundles.ui.model.input;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 4/11/11
 * Time: 3:47 PM
 * Bridgevine Customization Group
 */
public class CustomizationGroup
{
    private String groupTitle;
    private long rank;
    private String instructionText;
    private ArrayList<CustomizationList> customizationList;

    public String getGroupTitle()
    {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle)
    {
        this.groupTitle = groupTitle;
    }

    public long getRank()
    {
        return rank;
    }

    public void setRank(long rank)
    {
        this.rank = rank;
    }

    public ArrayList<CustomizationList> getCustomizationList()
    {
        return customizationList;
    }

    public void setCustomizationList(ArrayList<CustomizationList> customizationList)
    {
        this.customizationList = customizationList;
    }

    public String getInstructionText()
    {
        return instructionText;
    }

    public void setInstructionText(String instructionText)
    {
        this.instructionText = instructionText;
    }

    @Override
    public String toString()
    {
        return "CustomizationGroup{" +
                "groupTitle='" + groupTitle + '\'' +
                ", rank=" + rank +
                ", instructionText='" + instructionText + '\'' +
                ", customizationList=" + customizationList +
                '}';
    }
}
